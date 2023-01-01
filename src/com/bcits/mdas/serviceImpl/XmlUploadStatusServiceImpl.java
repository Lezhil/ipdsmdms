package com.bcits.mdas.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.XmlUploadStatusEntity;
import com.bcits.mdas.entity.XmlUploadStatusEntity.KeyUplaodStatus;
import com.bcits.mdas.ftp.FTPUploadFile;
import com.bcits.mdas.ftp.XmlRECCreator;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.XmlUploadStatusService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class XmlUploadStatusServiceImpl extends GenericServiceImpl<XmlUploadStatusEntity>	implements	XmlUploadStatusService 
{
	@Autowired
	private AmrInstantaneousService amrInstantaneousService;
	
	@Autowired
	private AmrBillsService amrBillsService;
	
	@Autowired
	private FeederMasterService feederMasterService;
	
	@Autowired
	private AmrEventsService amrEventsService;
	
	@Autowired
	private AmrLoadService amrLoadService;
	
	
	public static final String SUCCESS="SUCCESS";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getXMLFilesByDate(String zn, String cir, String div, String subdiv,String fDate, ModelMap model)
	{
		List<String> list=null;
		if(cir.equalsIgnoreCase("All"))
		{
			cir="%";
		}
		else
		{
			cir=cir;
		}
		if(div.equalsIgnoreCase("All"))
		{
			div="%";
		}
		else
		{
			div=div;
		}
		if(subdiv.equalsIgnoreCase("All"))
		{
			subdiv="%";
		}
		else
		{
			subdiv=subdiv;
		}
		try
		{
			String sql="SELECT DISTINCT REPLACE(x.file_path,'\\',';') FROM meter_data.xml_upload_status x,meter_data.master_main m " +
					"WHERE x.meter_number=m.mtrno and x.upload_status='1' and to_char(x.file_date,'yyyy-MM-dd') " +
					"like '"+fDate+"' and m.zone like '"+zn+"' and m.circle like '"+cir+"' and " +
					"m.division like '"+div+"' and m.subdivision like '"+subdiv+"'";
			System.out.println("query getXMLFilesByDate==>"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
			/*if(list.size()==0 || list==null || list.isEmpty()) {
				System.out.println("if getXMLFilesByDate==>");
				model.put("results","No Data Available");
			}
			else {
				System.out.println("else getXMLFilesByDate==>");
				return list;
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Object> getMeterDetailsForFailedXml(String fileDate) {
		List<Object> list=null;
		try
		{
			String sql="SELECT mtrno FROM meter_data.master_main WHERE mtrno NOT IN( SELECT meter_number FROM meter_data.xml_upload_status WHERE file_date='"+fileDate+"' AND upload_status='1')";
			System.out.println("query getXMLFilesByDate==>"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<Object> getMeterDetailsForFailedXmlNew(String fileDate) {
		List<Object> list=null;
		try
		{
			String sql="SELECT mtrno FROM meter_data.master_main WHERE mtrno NOT IN( SELECT meter_number FROM meter_data.xml_upload_status WHERE file_date='"+fileDate+"' AND upload_status='1') AND mtrno in (SELECT meter_number from meter_data.modem_communication WHERE date='"+fileDate+"')";
			System.out.println("query getXMLFilesByDate==>"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Object> getLast30DaysDates(String from, String to) {
		List<Object> list=null;
		try
		{
			String sql="SELECT date_trunc('day', dd)\\:\\: date FROM generate_series( '"+from+"'\\:\\:timestamp , '"+to+"'\\:\\:timestamp , '1 day'\\:\\:interval) dd ;";
			System.out.println("query getXMLFilesByDate==>"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public XmlUploadStatusEntity getRecordByMeterNDate(String meterNumber, String fileDate) {
		try {
			//Date d=new SimpleDateFormat("yyyy-MM-dd").parse(fileDate);
			return postgresMdas.createNamedQuery("XmlUploadStatusEntity.getByMeterAndDate", XmlUploadStatusEntity.class).setParameter("meterNumber", meterNumber).setParameter("fileDate", fileDate).getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Object> getMeterNumbersForReUpload(String fileDate) {
		List<Object> list=null;
		try
		{
			String sql="SELECT m.mtrno FROM meter_data.master_main m, meter_data.xml_upload_status x WHERE m.mtrno=x.meter_number AND x.file_date='"+fileDate+"' AND x.upload_status=0 ";
			System.out.println("query getMeterNumbersForReUpload==>"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String generateXmlAndUpload(String meterNumber, String fileDate, FTPUploadFile ftp, String filePath){
		String uploadStatus;
		//GETTING DATA FOR XML CREATION
		 Object feederMasterEntity =feederMasterService.getD1GData(meterNumber);//GETTING MASTER DATA IN OBJECT FORMAT
			System.out.println(feederMasterEntity+"   FEEDER MASTER DATA----------------");
			
			if(feederMasterEntity==null) {
				return "NO MASTER DATA FOUND";
			}
		System.out.println("fileDate======="+fileDate);
		AmrInstantaneousEntity amrInstantaneousEntity = amrInstantaneousService.getD1DataForXml(meterNumber,fileDate);//GETTING INSTANTANEOUS DATA
		List<AmrBillsEntity> amrBill = amrBillsService.getRecords(meterNumber,fileDate);//GETTING BILL DATA
		List<AmrEventsEntity> amrEvents = amrEventsService.getRecords(meterNumber,fileDate); //GETTING EVENTS DATA
		List<AmrLoadEntity> amrLoad=new ArrayList<>();
		try {
			amrLoad = amrLoadService.getRecords(meterNumber,fileDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
	
		//INITIATING XML UPLOAD TRACKING SYSTEM
		XmlUploadStatusEntity xmlStatus= new XmlUploadStatusEntity();//FOR UPDATING THE XML CREATION AND UPLOAD STATUS
		try {
			xmlStatus.setMyKey(new KeyUplaodStatus(meterNumber, dateFormat.parse(fileDate)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xmlStatus.setTimeStamp(new Timestamp(new Date().getTime()));
		if(null!=amrInstantaneousEntity){
			xmlStatus.setInstaStatus(1);
		}
		if(null!=amrBill && amrBill.size()>0){
			xmlStatus.setBillStatus(1);
		}
		if(null!=amrEvents && amrEvents.size()>0){
			xmlStatus.setEventStatus(1);
		}
		if(null!=amrLoad && amrLoad.size()>0){
			xmlStatus.setLoadStatus(1);
		}
		
		String fileName=meterNumber+"_"+fileDate;
		String xmlFileName =filePath+"/"+fileName+".xml";
		
		XmlRECCreator c = new XmlRECCreator();
		
		
		
		
		if (amrInstantaneousEntity != null) {//CREATE XML HERE
			// String xmlCreatedResult = c.createXML(xmlFileName, amrInstantaneousEntity, amrBill, amrLoad, amrEvents,feederMasterEntity);
			String xmlCreatedResult = c.createXML(xmlFileName, amrInstantaneousEntity, amrBill, amrLoad, amrEvents,feederMasterEntity);
			if (xmlCreatedResult.equals(SUCCESS)) {
				String status = ftp.uploadFile(xmlFileName);
				System.out.println(status);
				if (status.equals(SUCCESS)) {
					xmlStatus.setUploadStatus(1);
					xmlStatus.setCreate_status(1);
					xmlStatus.setFilePath(xmlFileName);
					uploadStatus=SUCCESS;
				} else {
					xmlStatus.setUploadStatus(0);
					xmlStatus.setCreate_status(1);
					xmlStatus.setFailReason(status);
					uploadStatus= status;
				}
			} else {
				xmlStatus.setUploadStatus(0);
				xmlStatus.setCreate_status(0);
				xmlStatus.setFailReason(xmlCreatedResult);
				uploadStatus= xmlCreatedResult;
			}
		}else{
			xmlStatus.setUploadStatus(0);
			xmlStatus.setCreate_status(0);
			xmlStatus.setFailReason("XML NOT CREATED. NO DATA AVAILABLE");
			uploadStatus= "XML NOT CREATED. NO DATA AVAILABLE";
		}
		
		System.out.println(xmlStatus.getUploadStatus()+"   ===================" );
		
		if(this.update(xmlStatus) instanceof XmlUploadStatusEntity){  
			return uploadStatus;
		}else{
			return "FAILED TO UPDATE STATUS";
		}
		
	}

	@Override
	public List<String> getMtrNoForDownload(String zone, String circle, String division, String subdiv,String fDate) {
		
		String qryLast="";
		if("ALL".equalsIgnoreCase(zone)) {
			
		} else if("ALL".equalsIgnoreCase(circle)) {
			qryLast=" and m.ZONE='"+zone+"'";
		} else if("ALL".equalsIgnoreCase(division)) {
			qryLast=" and m.ZONE='"+zone+"' AND m.CIRCLE='"+circle+"'";
		} else if("ALL".equalsIgnoreCase(subdiv)) {
			qryLast=" and m.ZONE='"+zone+"' AND m.CIRCLE='"+circle+"' AND m.DIVISION='"+division+"'";
		} else {
			qryLast=" and m.ZONE='"+zone+"' AND m.CIRCLE='"+circle+"' AND m.DIVISION='"+division+"' AND m.SUBDIVISION='"+subdiv+"' ";
		}
		
		String sql="SELECT meter_number FROM meter_data.xml_upload_status x,meter_data.master_main m WHERE x.meter_number=m.mtrno and x.create_status='1' and x.file_date='"+fDate+"' ";
		sql=sql+qryLast;
		System.out.println("download xml qru===>> "+sql);
		try {
			List<String> list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
