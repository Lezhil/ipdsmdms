package com.bcits.mdas.ftp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.XmlUploadStatusEntity;
import com.bcits.mdas.entity.XmlUploadStatusEntity.KeyUplaodStatus;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.XmlUploadStatusService;
import com.bcits.mdas.utility.FilterUnit;

public class CreateXmlThread implements Runnable {

	private AmrInstantaneousService amrInstantaneousService;
	private AmrBillsService amrBillsService;
	private AmrEventsService amrEventsService;
	private AmrLoadService amrLoadService;
	private MasterMainService feederMasterService;
	private XmlUploadStatusService xmlUplaodService;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	public static final String SUCCESS="SUCCESS";
	
	String meterNumber;
	String fileDate; 
	String filePath;
	
	
	

	public CreateXmlThread(String meterNumber, String fileDate, String filePath,
			AmrInstantaneousService amrInstantaneousService, AmrBillsService amrBillsService,
			AmrEventsService amrEventsService, AmrLoadService amrLoadService, MasterMainService feederMasterService,
			XmlUploadStatusService xmlUplaodService) {
		super();
		this.meterNumber = meterNumber;
		this.fileDate = fileDate;
		this.filePath = filePath;
		this.amrInstantaneousService = amrInstantaneousService;
		this.amrBillsService = amrBillsService;
		this.amrEventsService = amrEventsService;
		this.amrLoadService = amrLoadService;
		this.feederMasterService = feederMasterService;
		this.xmlUplaodService = xmlUplaodService;
	}

	@Override
	public void run() {
		//FTPUploadFile ftp=null;
		//System.out.println("thread started");
		try {
			//ftp=new FTPUploadFile();
			Object feederMasterEntity =feederMasterService.getD1GData(meterNumber);//GETTING MASTER DATA IN OBJECT FORMAT
			if(feederMasterEntity!=null) {
				
				AmrInstantaneousEntity amrInstantaneousEntity = amrInstantaneousService.getD1DataForXml(meterNumber,fileDate);//GETTING INSTANTANEOUS DATA
				List<AmrBillsEntity> amrBill = amrBillsService.getRecords(meterNumber,fileDate);//GETTING BILL DATA
				List<AmrEventsEntity> amrEvents = amrEventsService.getRecords(meterNumber,fileDate); //GETTING EVENTS DATA
				List<AmrLoadEntity> amrLoad = amrLoadService.getRecords(meterNumber,fileDate);//GETTING LOAD DATA
				
			
				XmlUploadStatusEntity xmlStatus= new XmlUploadStatusEntity();//FOR UPDATING THE XML CREATION AND UPLOAD STATUS
				xmlStatus.setMyKey(new KeyUplaodStatus(meterNumber, dateFormat.parse(fileDate)));
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
				
				if (amrInstantaneousEntity != null) {
					String xmlCreatedResult = c.createXML(xmlFileName, amrInstantaneousEntity, amrBill, amrLoad, amrEvents,feederMasterEntity);
					if (xmlCreatedResult.equals(SUCCESS)) {
						//String status = ftp.uploadFile(xmlFileName);
						String status = "FAILED";
						if (status.equals(SUCCESS)) {
							xmlStatus.setUploadStatus(1);
							xmlStatus.setCreate_status(1);
							xmlStatus.setFilePath(xmlFileName);
						} else {
							xmlStatus.setUploadStatus(0);
							xmlStatus.setCreate_status(1);
							xmlStatus.setFilePath(xmlFileName);
							//xmlStatus.setFailReason(status);
						}
					} else {
						xmlStatus.setUploadStatus(0);
						xmlStatus.setCreate_status(0);
						xmlStatus.setFailReason(xmlCreatedResult);
					}
				}else{
					xmlStatus.setUploadStatus(0);
					xmlStatus.setCreate_status(0);
					xmlStatus.setFailReason("XML NOT CREATED. NO DATA AVAILABLE");
				}
				//System.out.println("updating xmlupload status");
				xmlUplaodService.customupdatemdas(xmlStatus);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//ftp.closeFTP();
		}
		
	}

	private String getFolderPath(String fileDate){
		try {
			Date date =dateFormat.parse(fileDate);
			String month=FilterUnit.ftpSourceFolder+"/"+new SimpleDateFormat("yyyyMM").format(date);
			String day=month+"/"+new SimpleDateFormat("yyyyMMdd").format(date);
			if(FilterUnit.folderExists(month)){
				if(FilterUnit.folderExists(day)){
					return day;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
