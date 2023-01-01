package com.bcits.mdas.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.FeederEntity;
import com.bcits.entity.MeterChangeTransHistory;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.SurveyOutputMobileEntity;
import com.bcits.mdas.entity.VirtualLocation;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.mdas.utility.Colours;
import com.bcits.serviceImpl.GenericServiceImpl;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
@Repository
public class MasterMainServiceImpl extends GenericServiceImpl<MasterMainEntity> implements MasterMainService 
{
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Object getAllFeedersMobile(String imeiNo) 
	{
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getAllFeederDataMobile").setParameter("imeiNo", imeiNo). getResultList();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getDistinctCircle()
	{
		List<MasterMainEntity> list=null;
		try
		{
			String sql="SELECT DISTINCT circle FROM dholpur.feedermaster ";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getDivisionByCircle(String circle, ModelMap model)
	{
		String sql="";
		List<Object[]> list=null;
		try 
		{
			//sql="SELECT DISTINCT division FROM dholpur.feedermaster WHERE circle='"+circle+"'";
			sql="SELECT DISTINCT division FROM dholpur.feedermaster WHERE circle=:circle";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).setParameter("circle", circle).getResultList();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getSubdivByDivisionByCircle(String circle,String division, ModelMap model)
	{
		String sql="";
		List<Object[]> list=null;
		try 
		{
			sql="SELECT DISTINCT office_code,office_name FROM dholpur.feedermaster WHERE circle='"+circle+"' and division='"+division+"' order by office_name";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getFeederBySubByDiv(String circle, String division, String subdiv,ModelMap model)
	{
		String sql="";
		List<Object[]> list=null;
		try 
		{
			sql="SELECT DISTINCT unin_feeder,feeder_name FROM dholpur.feedermaster WHERE circle='"+circle+"' and division='"+division+"' and office_code='"+subdiv+"' ORDER BY unin_feeder ";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> findAll(String district)
	{
		List<MasterMainEntity> list=null;
		try
		{
			list=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.findAll").setParameter("district", district).getResultList();
			//model.addAttribute("feederMasterList",list);
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> findDistricts()
	{
		List<MasterMainEntity> list=null;
		try
		{
			list=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getDistinctDistrict").getResultList();
			//model.addAttribute("feederMasterList",list);
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getDistinctFeederName(String sdocode, ModelMap model,HttpServletRequest request)
	{
		String sql="";
		List<Object[]> list=null;
		try 
		{
			sql="SELECT DISTINCT feeder_name FROM dholpur.feedermaster WHERE office_code='"+sdocode+"'";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getDistinctFeederCode(String sdocode,String feederName, ModelMap model,HttpServletRequest request)
	{
		String sql="";
		List<Object[]> list=null;
		try 
		{
			sql="SELECT DISTINCT unin_feeder FROM dholpur.feedermaster WHERE office_code='"+sdocode+"' and feeder_name='"+feederName+"'";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	//@Transactional(propagation=Propagation.REQUIRED)
	public int updateMrImeiByFeederNo(String sdoNo,String fdrName,String fdrNo, String mrNo, String imeiNo,HttpServletRequest request) 
	{
		int i = 0;
		
		String sql = "UPDATE dholpur.feedermaster SET imeino='"+imeiNo+"',mrcode='"+mrNo+"' WHERE office_code like '"+sdoNo+"' "
				+ "and feeder_name like '"+fdrName+"' and unin_feeder like '"+fdrNo+"'";
		System.out.println("inside updateMrImeiByFeederNo Sql Query==>"+sql);
		try 
		{
			i = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return i;
	}
	
	@Override
	//@Transactional(propagation=Propagation.REQUIRED)
	public int updateNullMrImeiByFeederNo(String deviceidPk, HttpServletRequest request) 
	{
		int i = 0;
		
		String sql = "UPDATE dholpur.feedermaster SET imeino='',mrcode='' WHERE imeino like '"+deviceidPk+"'";
		try 
		{
			i = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return i;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getFeederData(String mtrNo)
	{
		System.out.println(mtrNo);
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeederData").setParameter("mtrNo", mtrNo).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getFeederDataInfo(String mtrNo,String zone,String circle)
	{
		System.out.println(mtrNo);
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeederDataInfo").setParameter("mtrNo", mtrNo).setParameter("zone",zone).setParameter("circle", circle).getResultList();
	}
	
	
	@Override
	public List<MasterMainEntity> getMeterDataByKno(String kno)
	
	{
		System.out.println(kno);
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getMeterDataByKno", MasterMainEntity.class).setParameter("kno", kno).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getMeterDetailsForXml() {
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getMeterDetailsForXml").getResultList();
	}
	
	@Override
	public List<?> getCorporateDetailCount() {
		
		System.out.println("=============Feerder Zone Counts==============="+getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getCircleDetailCount").unwrap(org.hibernate.Query.class).getQueryString());

		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getCorporateDetailCount").getResultList();
				
		
	}
	
	
	@Override
	public List<?> getZoneDetailCount(String zone) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getZoneDetailCount").setParameter("zone", zone).getResultList();
	}
	
	@Override
	public List<?> getCircleDetailCount(String circle) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getCircleDetailCount").setParameter("circle", circle).getResultList();
	}
	
	@Override
	public List<?> getDivisionDetailCount(String division) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getDivisionDetailCount").setParameter("division", division).getResultList();
	}
	
	@Override
	public List<?> getSubDivisionDetailCount(String subdivision) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getSubDivisionDetailCount").setParameter("subdivision", subdivision).getResultList();
	}
	
	@Override
	public List<?> getSubStationDetailCount(String subStation) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getSubStationDetailCount").setParameter("subStation", subStation).getResultList();
	}
	


	@Override
	public List<?> getZones() {
		// TODO Auto-generated method stub
		List<?> lis=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getZones").getResultList();
		return lis;
	}
	
	@Override
	public List<?> getCircles(String zone) {
		// TODO Auto-generated method stub
		List<?> lis=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getCircles").setParameter("zone", zone).getResultList();
		return lis;
	}
	
	@Override
	public List<?> getDivisions(String circle) {
		// TODO Auto-generated method stub
		List<?> lis=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getDivision").setParameter("circle", circle).getResultList();
		return lis;
	}
	
	@Override
	public List<?> getSubDivisions(String division) {
		// TODO Auto-generated method stub
		List<?> lis=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getSubDivision").setParameter("division", division).getResultList();
		return lis;
	}
	
	
	@Override
	public List<?> getSubStations(String subdivision) {
		// TODO Auto-generated method stub
		List<?> lis=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getSubStation").setParameter("subdivision", subdivision).getResultList();
		return lis;
	}
	

	@Override
	public int getModemCount() {
		// TODO Auto-generated method stub MasterMainEntity.getModemCount
		List<Long> lis=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getModemCount").getResultList();
		return lis.get(0).intValue();
	}
	
	
	@Override
	public List<?> getFailUploadCount() {

		//String sql="select count(DISTINCT meter_number) from meter_data.xml_failed_info where date_part('day',age(current_date,date(time_stamp)))<=1";
		String sql="SELECT count(distinct mm.zone)as zone, "
				+"count(distinct mm.mtrno)as total, "
				+"COUNT ( "
				+"CASE "
				+"WHEN mstat.upload_status = 1 THEN "
				+"1 "
				+"END "
				+") AS uploaded, "
				+"COUNT ( "
				+"CASE "
				+"WHEN mstat.upload_status = 0 THEN "
				+"1 "
				+"END "
				+") AS failed "
				+"FROM "
				+"meter_data.xml_upload_status mstat , meter_data.master_main mm "
				+"WHERE mstat.file_date= CURRENT_DATE-1 and mstat.meter_number=mm.mtrno";
		System.out.println("--------------upload fail----------------"+sql);
		//List<BigInteger> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).g();
		List<Object[]> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		/*for ( Iterator<?> iterator=li.iterator();iterator.hasNext();){
			final Object[] values = (Object[]) iterator.next();
			System.out.println(values[0]+"--------upload file test--------");
		}*/
		
		for (Object[] object : li) {
			System.out.println(object[0]+"--------upload file test--------");
			System.out.println(object[1]+"--------upload file test--------");
			System.out.println(object[2]+"--------upload file test--------");
			System.out.println(object[3]+"--------upload file test--------");
			
		}
		
		return li;
	}
	
	
	@Override
	public List<?> getFailUploadCount(String type1, String value1) {

		//String sql="select count(DISTINCT meter_number) from meter_data.xml_failed_info where date_part('day',age(current_date,date(time_stamp)))<=1";
		String sql="SELECT :type1, "
				+"count(distinct mm.mtrno)as total, "
				+"COUNT ( "
				+"CASE "
				+"WHEN mstat.upload_status = 1 THEN "
				+"1 "
				+"END "
				+") AS uploaded, "
				+"COUNT ( "
				+"CASE "
				+"WHEN mstat.upload_status = 0 THEN "
				+"1 "
				+"END "
				+") AS failed "
				+"FROM "
				+"meter_data.xml_upload_status mstat , meter_data.master_main mm "
				+"WHERE mstat.file_date= CURRENT_DATE-1 and mstat.meter_number=mm.mtrno and mm."+type1+"=:value1 group by  mm."+type1;
		System.out.println("--------------upload fail----------------"+sql);
		//List<BigInteger> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).setParameter("type1", type1).setParameter("value1", value1).getResultList();
			//return li.get(0).intValue();
		return li;
	}
	
	
	
	
	@Override
	public List<?> getFailUploadCountSublevel() {

		String sql="SELECT "
				+"mm. ZONE, "
				+"count(distinct mm.mtrno)as total, "
				+"COUNT ( "
				+"CASE "
				+"WHEN mstat.upload_status = 1 THEN "
				+"1 "
				+"END "
				+") AS uploaded, "
				+"COUNT ( "
				+"CASE "
				+"WHEN mstat.upload_status = 0 THEN "
				+"1 "
				+"END "
				+") AS failed "
				+"FROM "
				+"meter_data.xml_upload_status mstat, "
				+"meter_data.master_main mm "
				+"WHERE "
				+"mm.mtrno = mstat.meter_number "
				+"AND mstat.file_date = (CURRENT_DATE - 1) "
				+"GROUP BY "
				+"mm. ZONE";
		
		
		
		System.out.println("-----------zone upload count-------------"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		
			return li;
	}
	
	@Override
	public List<?> getFailUploadCountSublevel(String type, String sub_type,String value) {
System.out.println("getFailUploadCountSublevel======type======"+type);
System.out.println("getFailUploadCountSublevel=====sub_type======="+sub_type);
System.out.println("getFailUploadCountSublevel======value======"+value);
		String sql="SELECT "
				+"mm."+sub_type
				+",count(distinct mm.mtrno)as total, "
				+"COUNT ( "
				+"CASE "
				+"WHEN mstat.upload_status = 1 THEN "
				+"1 "
				+"END "
				+") AS uploaded, "
				+"COUNT ( "
				+"CASE "
				+"WHEN mstat.upload_status = 0 THEN "
				+"1 "
				+"END "
				+") AS failed "
				+"FROM "
				+"meter_data.xml_upload_status mstat, "
				+"meter_data.master_main mm "
				+"WHERE "
				+"mm.mtrno = mstat.meter_number "
				+"AND mstat.file_date = (CURRENT_DATE - 1) "
				+"AND mm."+type+"='"+value
				+"' GROUP BY "
				+"mm. ZONE,mm."+sub_type;
		
		
		System.out.println("-----------"+type+"="+value+" upload count-------------"+sql);
		List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		
			return li;
	}

	@Override
	public int getUploadedCount() {
		String sql="select "
				+"(select count(DISTINCT mtrno) from meter_data.master_main ) "
				+"-  "
				+"(select count(DISTINCT meter_number) from meter_data.xml_failed_info where date_part('day',age(current_date,date(time_stamp)))<=1) AS Difference"; 
		System.out.println(sql);
		List<BigInteger> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		
			return li.get(0).intValue();
	}

	@Override
	public int getTtlMtrCount() {
		// TODO Auto-generated method stub
		List<Long> lis=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getTtlMtrCount").getResultList();
		return lis.get(0).intValue();
	}
	
	

	@Override
	public List<?> getModemCountZone() {
		// TODO Auto-generated method stub
		List<?> lis=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getZonesModemCount").getResultList();
		return lis;
	}
	
	

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public  List<?> getFeederInfo(ModelMap model, HttpServletRequest request) {
	
		List<?> list=null;
	
		
		try 
        {
        	String subStation1="33 KV Mirzapur";
        	if(subStation1!=null)
        	{
        		String query="SELECT mm.fdrname,mm.fdrcode,mm.mtrno,mm.modem_sl_no FROM meter_data.master_main  mm where substation='33 KV Mirzapur'";
        		list=getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
        		model.put("results", "noFeederData");
    			if (list.size() > 0) 
    	        {
    	        	model.put("feederInfoList", list);
    	        }
    	        else
    	        {
    	        	model.put("results", "Data not available.");
    	        }
        		
        		
        	}
        	
    		
       	}
    		 
             catch (Exception e) 
    		{
    			e.printStackTrace();
    		}
    		return list;
	
	}
	
	


	@Override
	public ModelMap getFdrMeterInfo(String FEEDERNAME, String FEEDERCODE)
	{
		ModelMap model = new ModelMap();
		//                         0          1         2       3              4      5         
		String query = "SELECT mm.mtrno,mm.mtrmake,mm.dlms,mm.year_of_man,mm.comm,mm.modem_sl_no FROM meter_data.master_main mm where mm.fdrname='"+FEEDERNAME+"' and mm.fdrcode='"+FEEDERCODE+"' ";
		Query q= getCustomEntityManager("postgresMdas").createNativeQuery(query);
		System.out.println("q=="+q);
		List list=q.getResultList();
		
		Object[] val=(Object[]) list.get(0);
		if(val.length>0)
		{
			if(val[0]==null) 
			{
				model.put("mtrno","NOData");
			}
			else
			{
				model.put("mtrno",val[0]);
			}	
			if(val[1]==null) 
			{
				model.put("mtrmake","NOData");
			}
			else
			{
				model.put("mtrmake",val[1]);
			}
			if(val[2]==null) 
			{
				model.put("dlms","NOData");
			}
			else
			{
				model.put("dlms",val[2]);
			}
			if(val[3]==null) 
			{
				System.out.println("year_of_man====NO DATA======");
				model.put("year_of_man","NOData");
			}
			else
			{
				System.out.println("year_of_man=========="+val[3]);
				model.put("year_of_man",val[3]);
			}
			if(val[4]==null) 
			{
				model.put("comm","NOData");
			}
			else
			{
				model.put("comm",val[4]);
			}
			if(val[5]==null) 
			{
				model.put("modem_sl_no","NOData");
			}
			else
			{
				model.put("modem_sl_no",val[5]);
			}
		
		}
		System.out.println("List=="+list.size());
	
		return model;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getDistinctZone()
	{
		List<MasterMainEntity> list=null;
		try
		{
			String sql="SELECT DISTINCT zone from meter_data.master_main";
			//String sql="SELECT DISTINCT zone from meter_data.master where zone not like ''";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getCircleByZone(String zone, ModelMap model)
	{
		List<MasterMainEntity> list=null;
		try
		{
			String sql="SELECT DISTINCT circle from meter_data.master_main WHERE zone like '"+zone+"'";
		//	System.err.println(sql);
			//String sql="SELECT DISTINCT circle from meter_data.master WHERE zone like '"+zone+"' and circle not like ''";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getDivisionByCircle(String zone, String circle, ModelMap model)
	{
		List<MasterMainEntity> list=null;
		try
		{
			String sql="SELECT DISTINCT division from meter_data.master_main WHERE zone like '"+zone+"' and circle LIKE '"+circle+"'";
			//String sql="SELECT DISTINCT division from meter_data.master WHERE zone like '"+zone+"' and "
					//+ "circle LIKE '"+circle+"' and division not like ''";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getSubdivByDivisionByCircle(String zone, String circle,String division, ModelMap model)
	{
		List<MasterMainEntity> list=null;
		try
		{
			String sql="SELECT DISTINCT subdivision from meter_data.master_main WHERE zone like '"+zone+"' and circle LIKE '"+circle+"' and division like '"+division+"'";
			//String sql="SELECT DISTINCT subdivision from meter_data.master WHERE zone like '"+zone+"' and circle LIKE '"+circle+"' "
					//+ "and division like '"+division+"' and subdivision not like ''";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getSStationBySubByDiv(String zone, String circle, String division,String subdiv, ModelMap model)
	{
		List<MasterMainEntity> list=null;
		try
		{
			String sql="SELECT DISTINCT substation from meter_data.master_main WHERE zone like '"+zone+"' and circle LIKE '"+circle+"' and division like '"+division+"' and subdivision like '"+subdiv+"'";
			//String sql="SELECT DISTINCT substation from meter_data.master WHERE zone like '"+zone+"' and "
					//+ "circle LIKE '"+circle+"' and division like '"+division+"' and subdivision like '"+subdiv+"' and substation not like ''";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getDistinctSubDivision()
	{
		List<MasterMainEntity> list=null;
		try
		{
			String sql="SELECT DISTINCT subdivision from meter_data.master_main";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getSStationBySub(String subdiv, ModelMap model)
	{
		List<MasterMainEntity> list=null;
		try
		{
			String sql="SELECT DISTINCT substation from meter_data.master_main WHERE subdivision like '"+subdiv+"'";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public Object getD1GData(String mtrNo) {
		String query ="SELECT pt_ratio as G7,ct_ratio as G8,mtrmake as G22,mtr_firmware as G17,year_of_man as G1177,dlms as G1194 from meter_data.master_main where mtrno='"+mtrNo+"' ";
		Query q= getCustomEntityManager("postgresMdas").createNativeQuery(query);
		
		List list=q.getResultList();
		try {
			return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//graph
	
	@Override
	public List<?> getDistinctSubStation(String queryWhere) {
	/*	System.out.println("enter to srvimpl");
		List<?> subStations=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getDistinctSubstation").getResultList();
		return subStations;*/
		System.out.println("getting loaded");
	
			List<?> list = null;
			//String qry = "SELECT id,stock_name from stockmarket.stock_table";
			String where =queryWhere.replace("WHERE", "AND");
			String query="SELECT  DISTINCT f.substation FROM meter_data.MASTER_MAIN f WHERE f.substation is NOT NULL "+where+" ORDER BY f.substation";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
			System.out.println("list count=="+list.size());
			
			return (List<?>) list;

		}

	@Override
	public List<?> getFeederBySubstn(String substation) {
		List<?> list=null;
		String query="SELECT f.fdrname FROM meter_data.MASTER_MAIN f WHERE f.substation='"+substation+"'";
		
		list = getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
		int count=list.size();
		System.out.println("count=="+count);
	 
		return list ;
	}

	@SuppressWarnings("unused")
    @Override
    public  JSONArray getTimeAndPhases(String substation, String date) throws ParseException {
        System.out.println("come to query");
        List<?> list=null;
        /*String query="SELECT read_time,meter_number,i_r,i_y,i_b FROM meter_data.load_survey WHERE to_char(read_time, 'YYYY-MM-DD HH24:MI:SS') like '2017-11-13%' AND meter_number='HRT64902' ORDER BY read_time DESC";
        */
        JSONArray ojarray= new JSONArray();
        JSONObject ojobj=new JSONObject();
        JSONArray jarraySubStn= new JSONArray();
        JSONArray jarraySubStnDatas= new JSONArray();

        /*String[] strarry=new String[]{"00:00:00","00:30:00","01:00:00","01:30:00","02:00:00","02:30:00","03:00:00","03:30:00"
,"04:00:00","04:30:00","05:00:00","05:30:00","06:00:00","06:30:00","07:00:00","07:30:00",
"08:00:00","08:30:00","09:00:00","09:30:00","10:00:00","10:30:00","11:00:00","11:30:00"
,"12:00:00","12:30:00","13:00:00","13:30:00","14:00:00","14:30:00","15:00:00","15:30:00",
"16:00:00","16:30:00","17:00:00","17:30:00","18:00:00","18:30:00","19:00:00","19:30:00",
"20:00:00","20:30:00","21:00:00","21:30:00","22:00:00","22:30:00",,};*/

        String[] strarry=new String[]{"23:30:00","23:00:00","22:30:00","22:00:00","21:30:00","21:00:00","20:30:00","20:00:00",
"19:30:00","19:00:00","18:30:00","18:00:00","17:30:00","17:00:00","16:30:00","16:00:00",
"15:30:00","15:00:00","14:30:00","14:00:00","13:30:00","13:00:00","12:30:00","12:00:00","11:30:00","11:00:00",
"10:30:00","10:00:00","09:30:00","09:00:00","08:30:00","08:00:00","07:30:00","07:00:00",
"06:30:00","06:00:00","05:30:00","05:00:00","04:30:00","04:00:00","03:30:00","03:00:00",
"02:30:00","02:00:00","01:30:00","01:00:00","00:30:00","00:00:00"};


        for(int i=0;i<48;i++){
            JSONArray jarraySubStnData0= new JSONArray();
            jarraySubStnDatas.put(jarraySubStnData0);
        }
        try{

        String querySubStn="SELECT fdrname,mtrno from meter_data.master_main WHERE substation='"+substation+"'";
        System.err.println("---query feeder sun station---:"+querySubStn);
        list=getCustomEntityManager("postgresMdas").createNativeQuery(querySubStn).getResultList();
        for (Iterator<?> iteratorSubStn = list.iterator(); iteratorSubStn.hasNext();) {
            final Object[] value = (Object[]) iteratorSubStn.next();
            jarraySubStn.put(value[0].toString());


            String query="SELECT ls.read_time,ls.meter_number,ls.i_r,ls.i_y,ls.i_b,mm.fdrname FROM meter_data.load_survey ls,meter_data.master_main mm WHERE mm.mtrno=ls.meter_number  and to_char(ls.read_time, 'DD-MM-YYYY HH24:MI:SS') like '"+date+"%' AND  ls.meter_number ='"+value[1].toString()+"' ORDER BY ls.read_time desc";
list=getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
            System.out.println("query==========="+query);
            int count=list.size();
            System.out.println("count=="+count);

             String phase=null;
             String starttime=null;
             String fdr=null;
             int j=0;
             if(!list.isEmpty()){
                for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

                        final Object[] values = (Object[]) iterator.next();
                        starttime=values[0].toString();
                        String st1=starttime.substring(11, 19);
                        System.err.println("no data------"+st1+"------"+strarry[j]);
                        while(!st1.equals(strarry[j])){
                            System.err.println("no data------------"+strarry[j]);
                            fdr=value[0].toString();
                            phase="NoData";
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", fdr+"_"+phase+"( startTime :"+strarry[j]+")");
                            jsonObject.put("color","#ccc");
                            jsonObject.put("y",30);
                            ((JSONArray) jarraySubStnDatas.get(j)).put(jsonObject);
                            j++;
                        }

if(!(values[2].toString().equalsIgnoreCase("0.00000"))&&!(values[3].toString().equalsIgnoreCase("0.00000")) && !(values[3].toString().equalsIgnoreCase("0.00000")))
                         {
 phase="threePhase";
                         }
                         else if((values[2].toString().equalsIgnoreCase("0.00000"))&&(values[3].toString().equalsIgnoreCase("0.00000")) && (values[3].toString().equalsIgnoreCase("0.00000")))
                         {
                             phase="poweroff";
                         }
                         else if((values[2].toString().equalsIgnoreCase("0.00000"))&&!(values[3].toString().equalsIgnoreCase("0.00000")) && !(values[3].toString().equalsIgnoreCase("0.00000"))||
 (!values[2].toString().equalsIgnoreCase("0.00000"))&&(values[3].toString().equalsIgnoreCase("0.00000")) && !(values[3].toString().equalsIgnoreCase("0.00000"))||
 (!values[2].toString().equalsIgnoreCase("0.00000"))&&!(values[3].toString().equalsIgnoreCase("0.00000")) && (values[3].toString().equalsIgnoreCase("0.00000")))
                         {
                             phase="singlePhase";
                         }
                         else if((!values[2].toString().equalsIgnoreCase("0.00000"))&&(values[3].toString().equalsIgnoreCase("0.00000")) && (values[3].toString().equalsIgnoreCase("0.00000"))||
 (!values[2].toString().equalsIgnoreCase("0.00000"))&&(values[3].toString().equalsIgnoreCase("0.00000")) && !(values[3].toString().equalsIgnoreCase("0.00000"))||
 (values[2].toString().equalsIgnoreCase("0.00000"))&&!(values[3].toString().equalsIgnoreCase("0.00000")) && !(values[3].toString().equalsIgnoreCase("0.00000")))
                         {
                             phase="twoPhase";
                         }

                        fdr=values[5].toString();

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", fdr+"_"+phase+"( startTime :"+st1+")");
 if(phase.equalsIgnoreCase("singlePhase")){
                             jsonObject.put("color", Colours.YELLOW);
                            // jsonObject.put("starttime", starttime);
                         }
                         else if(phase.equalsIgnoreCase("poweroff")){
                             jsonObject.put("color", Colours.RED);
                            // jsonObject.put("starttime", starttime);
                         }
                         else if(phase.equalsIgnoreCase("threePhase")){
                             jsonObject.put("color","#00A439");
                             //jsonObject.put("starttime", starttime);
                         }
                         else if(phase.equalsIgnoreCase("twoPhase")){
                             jsonObject.put("color", Colours.ORANGE);
                            // jsonObject.put("starttime", starttime);
                         }
                         /*JSONArray jarr=new JSONArray();
                         jarr.put(30);*/
                         jsonObject.put("y",30);
                         ((JSONArray) jarraySubStnDatas.get(j)).put(jsonObject);
                         //jarraySubStnDatas.put(j,jsonObject);
                         j++;
                    }

                while(j<=47){

                    fdr=value[0].toString();
                    phase="NoData";
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", fdr+"_"+phase+"( startTime :"+strarry[j]+")");
                    jsonObject.put("color","#ccc");
                    jsonObject.put("y",30);
                    ((JSONArray) jarraySubStnDatas.get(j)).put(jsonObject);
                    j++;
                }

            }else{
                for(int i=0;i<strarry.length;i++){
                    fdr=value[0].toString();
                    phase="NoData";
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", fdr+"_"+phase+"( startTime :"+strarry[i]+")");
                    jsonObject.put("color","#ccc");
                    jsonObject.put("y",30);
                    ((JSONArray) jarraySubStnDatas.get(j)).put(jsonObject);
                    j++;
            }
            }
        }
        JSONArray outDataJArray=new JSONArray();
        for(int i=0;i<jarraySubStnDatas.length();i++){
            JSONObject dataJObj=new JSONObject();
            dataJObj.put("data",jarraySubStnDatas.get(i));
            outDataJArray.put(dataJObj);
        }
        ojobj.put("fdrArray", jarraySubStn);
        ojobj.put("dataArray", outDataJArray);

        ojarray.put(ojobj);


        }catch(Exception e){
            e.printStackTrace();
        }
        return ojarray;
    }
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getDistinctZoneByLogin(String userName,ModelMap model, HttpServletRequest request)
	{
		System.out.println("getDistinctZoneByLogin==>"+userName);
		if(userName.equalsIgnoreCase("DHBVN") || userName.equalsIgnoreCase("DHBVN") || userName.equalsIgnoreCase("DHBVN"))
		{
			userName=userName;
		}
		else if(userName.equalsIgnoreCase("RECMDAS") || userName.equalsIgnoreCase("Admin"))
		{
			userName="%";
		}
		List<MasterMainEntity> list=null;
		try
		{
			String sql="SELECT DISTINCT zone from meter_data.master_main where zone like '"+userName+"'";
			//String sql="SELECT DISTINCT zone from meter_data.master where zone not like ''";
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<MasterMainEntity> getFeedersBasedOn(String zone, String circle, String division, String subdiv) {
		try {
			
			if("ALL".equalsIgnoreCase(zone)) {
				return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeedersBasedOnAllZone", MasterMainEntity.class).getResultList();
			} else if("ALL".equalsIgnoreCase(circle)) {
				return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeedersBasedOnAllCircle", MasterMainEntity.class).setParameter("zone", zone).getResultList();
			} else if("ALL".equalsIgnoreCase(division)) {
				return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeedersBasedOnAllDivison", MasterMainEntity.class).setParameter("zone", zone).setParameter("circle", circle).getResultList();
			} else if("ALL".equalsIgnoreCase(subdiv)) {
				return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeedersBasedOnAllSubdivsion", MasterMainEntity.class).setParameter("zone", zone).setParameter("circle", circle).setParameter("division", division).getResultList();
			} else {
				return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeedersBasedOn", MasterMainEntity.class).setParameter("zone", zone).setParameter("circle", circle).setParameter("division", division).setParameter("subdivision", subdiv).getResultList();
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public MasterMainEntity getEntityByImeiNoAndMtrNO(String imei, String mtrno) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getEntityByImeiNoAndMtrNO",MasterMainEntity.class).setParameter("modem_sl_no", imei).setParameter("mtrno", mtrno).getSingleResult();
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@Override
	public MasterMainEntity getEntityByMtrNO(String mtrno) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getEntityByMtrNO",MasterMainEntity.class).setParameter("mtrno", mtrno).getSingleResult();
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	@Override
	public List<MasterMainEntity> getFeedersBasedOnImei(String imei) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeedersBasedOnImei",MasterMainEntity.class).setParameter("modem_sl_no", imei+"%").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<MasterMainEntity> getFeedersBasedOnUniqueImei(String imei) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeedersBasedOnImei",MasterMainEntity.class).setParameter("modem_sl_no", imei).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<MasterMainEntity> getFeedersBasedOnMeterNo(String mtrno) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeedersBasedOnMeterNo",MasterMainEntity.class).setParameter("mtrno", mtrno+"%").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateMasterForAllChangesMeterNo() {
		
		String meterChangeQry="SELECT CC.fdrname,CC.mtrno,CC.circle,CC.division,CC.subdivision,CC.substation, DD.*,case when CC.mtrno=DD.meter_number then 'SAME' else 'METER CHANGED' END as status,CC.mtrmake,CC.dlms FROM (SELECT * from meter_data.master_main)CC JOIN(SELECT AA.imei,AA.maxDate,BB.meter_number  from (select imei,max(date) as maxDate from meter_data.modem_communication GROUP BY imei) AA LEFT JOIN meter_data.modem_communication BB ON AA.maxDate= BB.date AND AA.imei=BB.imei)DD ON CC.modem_sl_no=DD.imei AND CC.mtrno != DD.meter_number ORDER BY CC.circle LIMIT 4";
		
				System.out.println("=======meterChangeQry=========="+meterChangeQry);
				Query queryUpload= getCustomEntityManager("postgresMdas").createNativeQuery(meterChangeQry);
				List<?> lis=null;
				try {
					lis = queryUpload.getResultList();
				}catch (Exception e) {
					e.printStackTrace();
				}
				int i=0;
				if(lis!=null) {
					for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();)
					{
						 Object[] values = (Object[]) iterator.next();
						 /*Map<String, Object> data=new HashMap<>();
						 data.put("rowNo", i);
						 data.put("fdName",(values[0]==null)?"--":values[0]);
						 data.put("OldmtrNo",(values[1]==null)?"--":values[1]);
						 data.put("circle",(values[2]==null)?"--":values[2]);
						 data.put("division",(values[3]==null)?"--":values[3]);
						 data.put("subDivision",(values[4]==null)?"--":values[4]);
						 data.put("subStation",(values[5]==null)?"--":values[5]);
						 data.put("IMEI", (values[6]==null)?"--":values[6]);
						 data.put("maxDate", (values[7]==null)?"--":values[7]);
						 data.put("newMtrNo",(values[8]==null)?"--":values[8]);
						 data.put("status",(values[9]==null)?"--":values[9]);
						 data.put("mtrMake",(values[10]==null)?"--":values[10]);
						 data.put("dlms",(values[11]==null)?"--":values[11]);*/
						 
							try {

								/*String query = "update meter_data.master_main set mtr_change_date = '" + values[7]
										+ "',old_mtr_no='" + ((String)values[1]).trim() + "',mtrno='" + ((String)values[8]).trim()
										+ "' where trim(modem_sl_no) = '" + ((String)values[6]).trim() + "' and mtrno='" + ((String)values[1]).trim() + "'";
								System.out.println("Schedular Update Master QUERY===" + query);
								value = getCustomEntityManager("postgresMdas").createNativeQuery(query).executeUpdate();*/
								
								MasterMainEntity mainEntity=getEntityByImeiNoAndMtrNO(((String)values[6]).trim(),((String)values[1]).trim());
								mainEntity.setMtr_change_date(new SimpleDateFormat("yyyy-MM-DD").format((Date)values[7]));
								mainEntity.setOld_mtr_no((String)values[1]);
								mainEntity.setMtrno((String)values[8]);
								update(mainEntity);
								i++;
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						
						 /*list.add(data);
						 i++;*/
					}
				}
				
		
		
		return i;
		
	}

	@Override
	public List<?> executeSelectQueryRrnList(String sql) {
		try {
			return getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
    public Object[] getAllModemDetails() {
        Object[] modem={"0","0","0"};
        try {
            /*String query="SELECT A.total,B.installed,(A.total-B.installed)as instock FROM \n" +
                    "(\n" +
                    "SELECT count(*)as total FROM meter_data.modem_master)A,(\n" +
                    "SELECT count(*)as installed FROM meter_data.modem_master WHERE attached_mtrno is not null\n" +
                    ")B";*/
           /* String query="SELECT count(*), count(CASE WHEN attached_mtrno IS NOT NULL THEN 1 END) as installed, "
            		+ "count(CASE WHEN attached_mtrno IS NULL THEN 1 END) as notinstalled, "
            		+ "count(CASE WHEN working_status='0' THEN 1 END) as notworking FROM meter_data.modem_master";
            */
            String query="SELECT count(*), "
                            +" count(CASE WHEN installed='1' THEN 1 END) as installed, "
                            +"  count(CASE WHEN installed='0' THEN 1 END) as notinstalled, "
                            +" count(CASE WHEN working_status='0' THEN 1 END) as notworking FROM meter_data.modem_master";
            
            System.out.println("getting modem_master Data qry : "+query);
            return (Object[]) getCustomEntityManager("postgresMdas").createNativeQuery(query).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return modem;
        }
    }

	@Override
	public List<String> getSubStationsBasedOn(String zone, String circle, String division, String subdiv) {
try {		String qry="SELECT  DISTINCT substation FROM meter_data.master_main ";
			String qryLast="";
			if("ALL".equalsIgnoreCase(zone)) {
				
			} else if("ALL".equalsIgnoreCase(circle)) {
				qryLast=" WHERE ZONE='"+zone+"'";
			} else if("ALL".equalsIgnoreCase(division)) {
				qryLast=" WHERE ZONE='"+zone+"' AND CIRCLE='"+circle+"'";
			} else if("ALL".equalsIgnoreCase(subdiv)) {
				qryLast=" WHERE ZONE='"+zone+"' AND CIRCLE='"+circle+"' AND DIVISION='"+division+"'";
			} else {
				qryLast=" WHERE ZONE='"+zone+"' AND CIRCLE='"+circle+"' AND DIVISION='"+division+"' AND SUBDIVISION='"+subdiv+"'";
			}
			qry+=qryLast;
			System.out.println("getSubStationsBasedOn : "+qry);
			return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	// YOGESH METHODS
	@SuppressWarnings("unchecked")
	@Override
	public List<MasterMainEntity> findDistinctZones() {
		List<MasterMainEntity> list=null;
		try
		{
			list=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.findDistinctZones").getResultList();
			//model.addAttribute("feederMasterList",list);
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MasterMainEntity> findDistinctCircle(String zone) {
		List<MasterMainEntity> list=null;
		try
		{
			list=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.findDistinctCircle")
					.setParameter("zone",zone ).getResultList();
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterMainEntity> findDistinctDivision(String zone,String circle) {
		List<MasterMainEntity> list=null;
		try
		{
			list=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.findDistinctDivision")
					.setParameter("zone",zone )
					.setParameter("circle",circle )
					.getResultList();
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterMainEntity> findDistinctSubDivision(String zone,String circle, String division) {
		List<MasterMainEntity> list=null;
		try
		{
			list=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.findDistinctSubdivision")
					.setParameter("zone",zone )
					.setParameter("circle",circle )
					.setParameter("division",division )
					.getResultList();
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterMainEntity> findDistinctSubstation(String zone,	String circle, String division, String subdivision) {
		List<MasterMainEntity> list=null;
		try
		{
			list=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.findDistinctsubstation")
					.setParameter("zone",zone )
					.setParameter("circle",circle )
					.setParameter("division",division )
					.setParameter("subdivision",subdivision )
					.getResultList();
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterMainEntity> findAllFeedersforModem(String zone,String circle, String division, String subdivision,String substation) {
		List<MasterMainEntity> list=null;
		try
		{
			list=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.findall")
					.setParameter("zone",zone )
					.setParameter("circle",circle )
					.setParameter("division",division )
					.setParameter("subdivision",subdivision )
					.getResultList();
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}

	@Transactional
	@Override
	public void updateImeiLastComm() {
		String sql="UPDATE meter_data.master_main SET modem_sl_no=A.imei , last_communicated_date=A.lastcom FROM " +
				"( SELECT imei,max(last_communication) as lastcom,meter_number " +
				"FROM meter_data.modem_communication GROUP BY meter_number, imei ORDER BY imei " +
				")A WHERE A.meter_number=mtrno AND modem_sl_no is NULL";
		postgresMdas.unwrap(Session.class).getTransaction().begin();
		int i =	getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		postgresMdas.unwrap(Session.class).getTransaction().commit();
		System.out.println("No of master Updated with imei : "+i);	
		
		//FOR UPDATING THE MODEM_MASTER
		String sql1="UPDATE meter_data.modem_master\n" +
				"SET attached_mtrno = A.meter_number,installed='1'\n" +
				"FROM\n" +
				"	(\n" +
				"		SELECT\n" +
				"			imei,\n" +
				"			MAX (last_communication) AS lastcom,\n" +
				"			meter_number\n" +
				"		FROM\n" +
				"			meter_data.modem_communication\n" +
				"		\n" +
				"		GROUP BY\n" +
				"			meter_number,\n" +
				"			imei\n" +
				"		ORDER BY\n" +
				"			imei\n" +
				"	) A\n" +
				"WHERE\n" +
				"	A .imei = modem_imei\n" +
				"AND attached_mtrno IS NULL";
		postgresMdas.unwrap(Session.class).getTransaction().begin();
		int i1 =	getCustomEntityManager("postgresMdas").createNativeQuery(sql1).executeUpdate();
		postgresMdas.unwrap(Session.class).getTransaction().commit();
		System.out.println("No of modem_master Updated with meter NO : "+i1);	
		
	}
	@Transactional
	@Override
	public void updateInstallationDate() {
		
		
		String sql="UPDATE meter_data.master_main SET installation_date=A.fstdate, installation='1' FROM " +
				"(  SELECT meter_number,  min (last_communication) as fstdate FROM meter_data.modem_communication GROUP BY meter_number " +
				")A WHERE A.meter_number=mtrno AND installation_date is NULL";
		postgresMdas.unwrap(Session.class).getTransaction().begin();
		int i =	postgresMdas.createNativeQuery(sql).executeUpdate();
		postgresMdas.unwrap(Session.class).getTransaction().commit();
		System.out.println("No of master Updated with Installation Date : "+i);	
		
	}

	@Override
	public MasterMainEntity getEntityByAccnoSubstation(String accno, String subdivision) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getEntityByAccnoSubstation",MasterMainEntity.class)
				.setParameter("accno", accno)
				.setParameter("subdivision", subdivision). getResultList().get(0);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MasterMainEntity> getFeederDataAccno(String mtrNo)
	{
		return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeederDataAccno").setParameter("accno", mtrNo).getResultList();
	}
	@Override
	public List<MasterMainEntity> getFeedersBasedOnAccno(String accno) {
		System.out.println("here--->"+accno);
		try {
			System.out.println("accnooo=-->"+accno);
			return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeedersBasedOnAccNo",MasterMainEntity.class).setParameter("accno",accno).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int checkMeterExist(String newMtrno)
	{
		String sql="SELECT * FROM meter_data.master_main WHERE mtrno ='"+newMtrno+"'";
		List<?> lst=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		System.out.println("list size===>"+lst.size());
		try
		{
			if(lst.isEmpty())
			{
				return 0;
			}else
			{
				return 1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();return 1;
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MasterMainEntity> getOldMeterData(String oldMeterNo) 
	{
		List<MasterMainEntity> list=getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getMeterData").setParameter("mtrno", oldMeterNo).getResultList();
		return list;
	}

	@Override
	public List<?> getMeterData(String mtrNo) {
		List result=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		Date d1=new Date();
		String month=sdf.format(d1);
		
		try {
			String qry="SELECT ma.circle,ma.phoneno2,ma.division,ma.address1,ma.sdoname,ma.accno,m.metrno,ma.tariffcode,ma.industrytype,ma.kno,ma.name,ma.supplytype \n" +
					" FROM meter_data.metermaster m, meter_data.master ma WHERE m.rdngmonth='"+month+"' AND m.metrno='"+mtrNo+"'\n" +
					"AND m.accno=ma.accno ";
			//System.out.println("get Meterdetails query--"+qry);
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterMainEntity> getmeters() {
		List list=null;
		String qry="Select distinct(mtrno) from meter_data.master_main";
		list=postgresMdas.createNativeQuery(qry).getResultList();
		
		return list;
	}

	@Override
	public List<?> getAllSubdivisions() {
		String qry="Select distinct(subdivision) from meter_data.master_main order by subdivision";
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public List<MasterMainEntity> getDataByMeterType(String meter_type) {
		return postgresMdas.createNamedQuery("MasterMainEntity.getDataByMeterType", MasterMainEntity.class).setParameter("meter_type", meter_type).getResultList();
	}

	@Override
	public List<MasterMainEntity> getConsumptionRecords(String zone)
	{
		//return postgresMdas.createNamedQuery("MasterMainEntity.getConsumptionRecords", MasterMainEntity.class).setParameter("zone", zone).getResultList();
		String query = "select meter_number, to_char(read_time, 'yyyyMM') as billmonth,SUM(COALESCE(kwh,0)),to_char(read_time, 'yyyy-MM-dd') from meter_data.load_survey  group by meter_number,read_time";
		System.out.println("Consumption curve ---> " +query);
		return postgresMdas.createNativeQuery(query).getResultList();
		
	}

	@Override
	public List<?> getViewDataBySubdivision(HttpServletRequest req) 
	{
		List<?> data=new ArrayList<>();
		List<?> data2=new ArrayList<>();
		List<Object> count=new ArrayList<>();
		
		
		String subdivision=req.getParameter("subdivision");
		try 
		{
			String qry1="select AA.gateway+AA.nongateway as DCU,AA.cmnmeter+AA.noncmnmeter as meter,AA.gateway as commDCU,AA.nongateway as noncommDCU,AA.cmnmeter as commMETER,AA.noncmnmeter as noncommMETER from \n" +
					"(SELECT sum(case when status='LOGIN' and node_type='MESH' then 1 else 0 end) as cmnmeter,sum(case when status ='LOGOUT' and node_type='MESH' then 1 else 0 end) as noncmnmeter,sum(case when status='LOGIN' and node_type='GATEWAY' then 1 else 0 end) as gateway,sum(case when status ='LOGOUT' and node_type='GATEWAY' then 1 else 0 end) as nongateway   from meter_data.search_nodes where date(time_stamp)=date(now()) and gateway_node_id is not null)AA";
			
			String qry="select nodedata.node_id,nodedata.latitude,nodedata.longitude,nodedata.status,nodedata.sdocode,nodedata.dcu_sn_number,nodedata.dcu_name,gatewaycount.metercount from \n" +
					"(select AB.node_id,AB.latitude,AB.longitude,AB.status,CD.sdocode,CD.dcu_sn_number,CD.dcu_name from \n" +
					"(select node_id,latitude,longitude, case when status='LOGIN' then 'C' else 'N' end as status from meter_data.search_nodes where date(time_stamp)=date(now()) and node_type not like 'MESH' )AB\n" +
					"left join\n" +
					"(select sdocode,dcu_sn_number,dcu_name,node_id from meter_data.dcu_master where sdocode='"+subdivision+"')CD\n" +
					"on AB.node_id=CD.node_id)nodedata\n" +
					"left join \n" +
					"(select gateway_node_id,count(*) as metercount from meter_data.search_nodes where date(time_stamp)=date(now()) and gateway_node_id is not null\n" +
					"group by gateway_node_id)gatewaycount\n" +
					"on nodedata.node_id=gatewaycount.gateway_node_id;";
			
			System.out.println("Query==="+qry);
			data=postgresMdas.createNativeQuery(qry).getResultList();
			data2=postgresMdas.createNativeQuery(qry1).getResultList();
			count.add(data2);
			count.add(data);
		} catch (Exception e) 
		{
			
		}
		return count;
	}
	
	
	@Override
	public MasterMainEntity getFeederDetailsByID(String mla) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getFeederDetailsByID", MasterMainEntity.class)
					.setParameter("mla", mla)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> getAllMeterNumByCategoryAndSubDIV(String sitecode, String fdrCategory) {
		List<String> list=new ArrayList<>();
		String sql="select distinct mtrno from meter_data.master_main where sdocode='"+sitecode+"' and fdrcategory='"+fdrCategory+"'\r\n" + 
				"";
		try {
			list=postgresMdas.createNativeQuery(sql).getResultList();
					//postgresMdas.createNamedQuery("MasterMainEntity.getAllMetersBySubDivision").setParameter("sitecode", sitecode).setParameter("fdrCategory", fdrCategory).getResultList();
			return list;
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSurveyDataImages(String kno) {
		
			Object obj=null;
		try {
			obj=postgresMdas.createNativeQuery("select * from meter_data.survey_output where kno='"+kno+"'").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public byte[] findOnlyImage(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, String imageid) {
		BCITSLogger.logger.info("In findOnlyImage-Image display"+imageid);
		int id=Integer.parseInt(imageid);
		byte image[]=null;
		try
		{
		List<SurveyOutputMobileEntity> hh=  postgresMdas.createNamedQuery("SurveyOutputMobileEntity.getMeterImageOnly").setParameter("id",id).getResultList();

		
		//model.put("imageees", hh);
		//System.out.println(hh.size());
		
		response.setContentType("image/jpeg/png");
    	byte bt[] = null;
    	OutputStream ot = null;
    	ot = response.getOutputStream();	
    	boolean directRead=false;
    	boolean manualRead=false;
    	if(hh.size()>0)
    	{
    		/*directRead=hh.get(0).getIsoptical().trim().startsWith("1");
    		manualRead=hh.get(0).getIsoptical().trim().startsWith("0");*/
    		bt=hh.get(0).getNewmeterimage();
    		if(bt.length>1)
    		{
    			ot.write(bt);
            	ot.close();
    		}
    		else if(directRead)
    		{

    			//String pathname = "/home/bcits/CESCLIVE/BsmartCloud/apache-tomcat-7.0.12/bin/directread.png";
    			String pathname = "C:\\Users\\bcitsws3\\Downloads\\directread.jpg";
    			BufferedImage originalImage = ImageIO.read(new File(pathname));
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ImageIO.write( originalImage, "png", baos );
    			baos.flush();
    			byte[] imageInByte = baos.toByteArray();
    			ot.write(imageInByte);
    			baos.close();
    		}
    		else if(manualRead && bt.length==0)
    		{
    			//String pathname = "/home/bcits/CESCLIVE/BsmartCloud/apache-tomcat-7.0.12/bin/manualreading.png";
    			String pathname = "C:\\Users\\bcitsws3\\Downloads\\manualreading.jpg";
    			BufferedImage originalImage = ImageIO.read(new File(pathname));
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ImageIO.write( originalImage, "png", baos );
    			baos.flush();
    			byte[] imageInByte = baos.toByteArray();
    			ot.write(imageInByte);
    			baos.close();
    		}
    		/*if(bt.length==0)
    		{
    			String pathname = "/home/bcits/CESCLIVE/BsmartCloud/apache-tomcat-7.0.12/bin/noImage.png";
    			
    			BufferedImage originalImage = ImageIO.read(new File(pathname));
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ImageIO.write( originalImage, "png", baos );
    			baos.flush();
    			byte[] imageInByte = baos.toByteArray();
    			ot.write(imageInByte);
    			baos.close();
    			
    		}
    		else
    		{*/
    			
    		/*}*/
    	}
    	else
    	{
    		
    	}
       Base64 b=new Base64();
       //BCITSLogger.logger.info("================>BT"+bt.length);
       image=b.encodeBase64(bt);
		/*return b.encodeBase64(bt);*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return image;    	
		
		
	}
	
    public List<?> getallcirclelist() {
		
		String query="select distinct circle from meter_data.amilocation order by circle";
	
		
		List<Object[]> list= postgresMdas.createNativeQuery(query).getResultList();
		
		return list;
	}
    
  public Object getSubdivisionbycircle(String circle) {
		
		String query="select distinct subdivision from meter_data.amilocation where circle='"+circle+"' order by subdivision";
		
		List<Object[]> list= postgresMdas.createNativeQuery(query).getResultList();
		
		return list;
	}
  
  @Override
	public List<Object[]> getALLcriticaldata() {
		
		String qry=" SELECT circle,division,subdivision,mtrno,accno,event_time,customer_name,event_desp,to_char(CURRENT_TIMESTAMP-event_time,'DD HH24:MI:SS')\n" +
				" from meter_data.critical_event   ORDER BY event_time\n" +
				" ";
		List<Object[]> list= postgresMdas.createNativeQuery(qry).getResultList();
		
		return list;
	}

	@Override
	public List<MasterMainEntity> getDistinctMeterNos() {

	List<MasterMainEntity> meters=new ArrayList<MasterMainEntity>();
			meters= postgresMdas.createNamedQuery("MasterMainEntity.getAllMtrNos").getResultList();
	return meters;
}

	@Override
	public List<MasterMainEntity> getDistinctKNos() {
		List<MasterMainEntity> knos=new ArrayList<MasterMainEntity>();
		knos= postgresMdas.createNamedQuery("MasterMainEntity.getAllKNos").getResultList();
        return knos;
	}

	@Override
	public List<?> getmeterDataByNOdeID(HttpServletRequest request) {
		List<?> data=new ArrayList<>();
		try 
		{
			String nodeid=request.getParameter("nodeid");
			String sdocode=request.getParameter("sdocode");
		/*String 	qry="SELECT matsrmain.customer_name,matsrmain.customer_address,matsrmain.accno,matsrmain.model_no,matsrmain.mtrno,matsrmain.fdrcode,  meterdata.gateway_node_id,meterdata.node_id,meterdata.latitude,meterdata.longitude,meterdata.parent_node_id,meterdata.status,meterdata.parentid,meterdata.plat,meterdata.plong from \n" +
				"(SELECT  child.gateway_node_id,child.node_id,child.latitude,child.longitude,child.parent_node_id,child.status,parent.node_id as parentid,parent.latitude as plat,parent.longitude as plong from\n" +
				"(select gateway_node_id,node_id,latitude,longitude,parent_node_id, case when status='LOGIN' then 'C' else 'N' end as status  from  meter_data.search_nodes  where  date(time_stamp)=date(now()) and parent_node_id='"+nodeid+"'\n" +
				"UNION all\n" +
				"SELECT gateway_node_id,node_id,latitude,longitude ,parent_node_id, case when status='LOGIN' then 'C' else 'N' end as status from meter_data.search_nodes where  parent_node_id in (select DISTINCT node_id  from  meter_data.search_nodes  where  parent_node_id like  '"+nodeid+"' and date(time_stamp)=date(now())) AND date(time_stamp)=date(now())\n" +
				"ORDER BY gateway_node_id,node_id)child\n" +
				"LEFT JOIN\n" +
				"(select node_id,latitude,longitude from  meter_data.search_nodes  where  date(time_stamp)=date(now()) and node_id in (\n" +
				"select DISTINCT parent_node_id  from  meter_data.search_nodes  where  date(time_stamp)=date(now()) and parent_node_id='"+nodeid+"'\n" +
				"UNION all\n" +
				"SELECT DISTINCT parent_node_id from meter_data.search_nodes where  parent_node_id in (select DISTINCT node_id  from  meter_data.search_nodes  where  parent_node_id like  '"+nodeid+"' and date(time_stamp)=date(now())) AND date(time_stamp)=date(now())))parent\n" +
				"on child.parent_node_id=parent.node_id\n" +
				"where child.latitude !='0' and child.latitude is not null)meterdata\n" +
				"LEFT JOIN\n" +
				" (SELECT master.customer_name,master.customer_address,master.accno,master.model_no,master.mtrno,master.fdrcode,nameplate.node_id from \n" +
				"(SELECT node_id,meter_serial_number from  meter_data.name_plate)nameplate\n" +
				" LEFT JOIN \n" +
				"(SELECT customer_name,customer_address,accno,model_no,mtrno,fdrcode from meter_data.master_main)master\n" +
				" on nameplate.meter_serial_number=master.mtrno)matsrmain\n" +
				"on meterdata.node_id=matsrmain.node_id where meterdata.latitude !='0' and meterdata.latitude is not null and meterdata.plat !='0' and meterdata.plong is not null";*/
			
		String qry="SELECT matsrmain.customer_name,matsrmain.customer_address,matsrmain.accno,matsrmain.model_no,matsrmain.mtrno,matsrmain.fdrcode,  meterdata.gateway_node_id,meterdata.node_id,meterdata.latitude,meterdata.longitude,meterdata.parent_node_id,meterdata.status,meterdata.parentid,meterdata.plat,meterdata.plong,matsrmain.dtccode from \n" +
				"(SELECT  child.gateway_node_id,child.node_id,child.latitude,child.longitude,child.parent_node_id,child.status,parent.node_id as parentid,parent.latitude as plat,parent.longitude as plong from\n" +
				"(select gateway_node_id,node_id,latitude,longitude,parent_node_id, case when status='LOGIN' then 'C' else 'N' end as status  from  meter_data.search_nodes  where  date(time_stamp)=date(now()) and parent_node_id='"+nodeid+"'\n" +
				"UNION all\n" +
				"SELECT gateway_node_id,node_id,latitude,longitude ,parent_node_id, case when status='LOGIN' then 'C' else 'N' end as status from meter_data.search_nodes where  parent_node_id in (select DISTINCT node_id  from  meter_data.search_nodes  where  parent_node_id like  '"+nodeid+"' and date(time_stamp)=date(now())) AND date(time_stamp)=date(now())\n" +
				"ORDER BY gateway_node_id,node_id)child\n" +
				"LEFT JOIN\n" +
				"(select node_id,latitude,longitude from  meter_data.search_nodes  where  date(time_stamp)=date(now()) and node_id in (\n" +
				"select DISTINCT parent_node_id  from  meter_data.search_nodes  where  date(time_stamp)=date(now()) and parent_node_id='"+nodeid+"'\n" +
				"UNION all\n" +
				"SELECT DISTINCT parent_node_id from meter_data.search_nodes where  parent_node_id in (select DISTINCT node_id  from  meter_data.search_nodes  where  parent_node_id like  '"+nodeid+"' and date(time_stamp)=date(now())) AND date(time_stamp)=date(now())))parent\n" +
				"on child.parent_node_id=parent.node_id\n" +
				"where child.latitude !='0' and child.latitude is not null)meterdata\n" +
				"LEFT JOIN\n" +
				" (select MM.customer_name,MM.customer_address,MM.accno,MM.model_no,MM.mtrno,MM.fdrcode,MM.node_id,DTCDATA.dtccode from \n" +
				"(SELECT master.customer_name,master.customer_address,master.accno,master.model_no,master.mtrno,master.fdrcode,nameplate.node_id from \n" +
				"(SELECT node_id,meter_serial_number from  meter_data.name_plate)nameplate\n" +
				" LEFT JOIN \n" +
				"(SELECT customer_name,customer_address,accno,model_no,mtrno,fdrcode from meter_data.master_main where sdocode='"+sdocode+"' )master\n" +
				" on nameplate.meter_serial_number=master.mtrno)MM\n" +
				"left join \n" +
				"(select rrno,dtccode from spdcl.consumer_mapping  where sitecode='"+sdocode+"')DTCDATA\n" +
				"on MM.accno=DTCDATA.rrno where MM.node_id is not null)matsrmain\n" +
				"on meterdata.node_id=matsrmain.node_id where meterdata.latitude !='0' and meterdata.latitude is not null and meterdata.plat !='0' and meterdata.plong is not null";	
		System.out.println(qry);
			data=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public List getMeterDataForReplacement(String oldMeterNo) {
		List data=new ArrayList<>();
		try {
			/*String qry="SELECT C.*,B.lastloadDate,B.lastloadkwh FROM\n" +
					"(SELECT A.meter_number,MA.accno,MA.loc_name as consumername,A.lastcommunication,A.lkwh FROM\n" +
					"(SELECT max(read_time)AS lastcommunication,meter_number,kwh/1000 as lkwh \n" +
					"FROM meter_data.amiinstantaneous WHERE meter_number='4613430' \n" +
					" GROUP BY read_time,meter_number,kwh ORDER BY read_time desc LIMIT 1)A,\n" +
					"(SELECT ZONE,circle,division,subdivision,fdrcategory AS LOC_TYPE,mtrno as MeterSrNo,\n" +
					"accno , customer_name AS LOC_NAME ,mtrmake AS MeterMake\n" +
					" FROM meter_data.master_main m WHERE m.mtrno='4613430')MA WHERE\n" +
					"MA.MeterSrNo=A.meter_number)C\n" +
					"LEFT JOIN\n" +
					"(SELECT max(read_time) AS lastloadDate ,meter_number,kwh/1000 as lastloadkwh FROM meter_data.load_survey l WHERE meter_number='4613430'\n" +
					" GROUP BY read_time,meter_number,lastloadkwh ORDER BY read_time desc LIMIT 1)B\n" +
					"ON C.meter_number=B.meter_number";*/
			
			String qry="SELECT C.*,B.lastloadDate,B.lastloadkwh FROM\n" +
						"(SELECT A.meter_number,MA.accno,MA.loc_name as consumername,A.lastcommunication,A.lkwh FROM\n" +
						"(SELECT max(a.read_time)AS lastcommunication,a.meter_number,a.kwh/1000 as lkwh \n" +
						"FROM meter_data.amiinstantaneous a,meter_data.master_main m  WHERE m.accno='"+oldMeterNo+"'  AND a.meter_number=m.mtrno\n" +
						" GROUP BY a.read_time,a.meter_number,a.kwh ORDER BY a.read_time desc LIMIT 1)A,\n" +
						"(SELECT ZONE,circle,division,subdivision,fdrcategory AS LOC_TYPE,mtrno as MeterSrNo,\n" +
						"accno , customer_name AS LOC_NAME ,mtrmake AS MeterMake\n" +
						" FROM meter_data.master_main m WHERE m.accno='"+oldMeterNo+"')MA WHERE\n" +
						"MA.MeterSrNo=A.meter_number)C\n" +
						"LEFT JOIN\n" +
						"(SELECT max(read_time) AS lastloadDate ,meter_number,kwh/1000 as lastloadkwh FROM meter_data.load_survey l,meter_data.master_main m \n" +
						" WHERE l.meter_number=m.mtrno AND m.accno='"+oldMeterNo+"'\n" +
						" GROUP BY read_time,meter_number,lastloadkwh ORDER BY read_time desc LIMIT 1)B\n" +
						"ON C.meter_number=B.meter_number";
			System.out.println("qry--"+qry);
			data=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public int mtrReplaceUpdateNewMtrno(String oldMeterNo, String newmeterno,
			String mtrchangeDate,String mf) {
		int count=0;
		try {
			//String qry="update meter_data.master_main SET mtrno='"+newmeterno+"',old_mtr_no='"+oldMeterNo+"',mtr_change_date='"+mtrchangeDate+"' WHERE mtrno='"+oldMeterNo+"'";
			String qry="update meter_data.master_main SET mtrno='"+newmeterno+"',old_mtr_no='"+oldMeterNo+"',mtr_change_date='"+mtrchangeDate+"',mf='"+mf+"' \n" +
					"WHERE mtrno='"+oldMeterNo+"'";
			System.out.println("qry--"+qry);
			count=postgresMdas.createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<MeterChangeTransHistory> getAllMeterChangeData() {
		List<MeterChangeTransHistory> result=new ArrayList<>();
		try {
			result= getCustomEntityManager("postgresMdas").createNamedQuery("METERCHANGE_TRANSHISTORY.getAllData").getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<MasterMainEntity> getConsumerLocData(String mtrNum, String accNum) {
		List<MasterMainEntity> consumerLocDetails=new ArrayList<>();
		consumerLocDetails=postgresMdas.createNamedQuery("MasterMainEntity.getConsumerLocationDetails").setParameter("mtrno", mtrNum).setParameter("accno", accNum).getResultList();
		return consumerLocDetails;
	}

	@Override
	public List<Object[]> getFeederLocData(String mtrNum) {
		List<Object[]> feederLocDetails=new ArrayList<>();
		System.out.println("inside master manin ..............." +mtrNum);
		
		String sql="";
		if(mtrNum.isEmpty() && !mtrNum.isEmpty()) {
			sql="select a.subdivision,f.*  from \n" +
					"(select feedername,crossfdr,fdr_id,meterno,officeid from meter_data.feederdetails  where meterno='"+mtrNum+"' ) f,\n" +
					"(select sitecode, subdivision from meter_data.amilocation ) a where a.sitecode= f.officeid";
		}
	    if(mtrNum.isEmpty() && !mtrNum.isEmpty()) {
			sql="select a.subdivision,f.*  from \n" +
					"(select feedername,crossfdr,fdr_id,meterno,officeid from meter_data.feederdetails  where meterno='"+mtrNum+"' ) f,\n" +
					"(select sitecode, subdivision from meter_data.amilocation ) a where a.sitecode= f.officeid";
		}
	    if(!mtrNum.isEmpty() && !mtrNum.isEmpty()) {
		sql="select a.subdivision,f.*  from \n" +
				"(select feedername,crossfdr,fdr_id,meterno,officeid from meter_data.feederdetails  where meterno='"+mtrNum+"' or meterno='"+mtrNum+"' ) f,\n" +
				"(select sitecode, subdivision from meter_data.amilocation ) a where a.sitecode= f.officeid";
		
		}try {
			System.out.println("Qry"+sql);
			feederLocDetails=postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return feederLocDetails;
	}

	@Override
	public List<Object[]> getDtLocData(String mtrNum, String dtId) {
		
		List<Object[]> dtLocDetails=new ArrayList<>();
		String sql="";
		if(mtrNum.isEmpty() && !dtId.isEmpty()) {
			sql="select a.subdivision,f.*  from \n" +
					"(select dtname,crossdt,dt_id,meterno,officeid,dtcapacity from meter_data.dtdetails  where dt_id='"+dtId+"' ) f,\n" +
					"(select sitecode, subdivision from meter_data.amilocation ) a where a.sitecode= f.officeid";
		}
	    if(dtId.isEmpty() && !mtrNum.isEmpty()) {
			sql="select a.subdivision,f.*  from \n" +
					"(select dtname,crossdt,dt_id,meterno,officeid,dtcapacity from meter_data.dtdetails  where meterno='"+mtrNum+"' ) f,\n" +
					"(select sitecode, subdivision from meter_data.amilocation ) a where a.sitecode= f.officeid";
		}
	    if(!dtId.isEmpty() && !mtrNum.isEmpty()) {
		sql="select a.subdivision,f.*  from \n" +
				"(select dtname,crossdt,dt_id,meterno,officeid,dtcapacity from meter_data.dtdetails  where meterno='"+mtrNum+"' or dt_id='"+dtId+"' ) f,\n" +
				"(select sitecode, subdivision from meter_data.amilocation ) a where a.sitecode= f.officeid";
		
		}try {
			System.out.println("Qry"+sql);
			dtLocDetails=postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
//		List<DtDetailsEntity> dtLocDetails=new ArrayList<>();
//		try {
//			dtLocDetails=postgresMdas.createNamedQuery("DtDetailsEntity.getDtLocationDetails").setParameter("dtId", dtId).setParameter("mtrNum", mtrNum).getResultList();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		// TODO Auto-generated method stub
		return dtLocDetails;
	}


	@Override
	public void getMeterDetailsPdf(HttpServletRequest request, HttpServletResponse response, String circle,
			String division, String subdiv,String towncode,String townname,String location) {

		String divisionn="",subdivv="",towncodee="";
		String cir="";
		
		
		try {
		
			if(circle.equalsIgnoreCase("%")){
				cir="ALL";
			}else{
				cir=circle;
			}
			if(division=="%"){
			}else{
			}
			if(subdiv=="%"){
			}else{
			}
			if(towncode.equalsIgnoreCase("All"))
			{
				towncodee="%";
			}else {
				towncodee=towncode;
			}
			/*
			 * if(location=="BM") { locationtype="BOUNDARY METER"; } if(location=="FM") {
			 * locationtype="FEEDER METER"; } if(location=="DT") { locationtype="DT"; }
			 */

			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer= PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("Meter Details ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Circle :"+cir,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Division :"+div,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Sub-Division :"+sub,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
		    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		   
		    headerCell = new PdfPCell(new Phrase("Location Type :"+location,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> MeterData1=null;
			if(location.equalsIgnoreCase("BOUNDARY METER")) {
				query="SELECT mtrno,customer_name,boundary_id,fdrcategory,division,circle,kno FROM meter_data.master_main m\n" +
						"LEFT JOIN meter_data.feederdetails f ON m.mtrno=f.meterno\n" +
						"where\n" +
						" circle LIKE '"+circle+"' AND division LIKE '%'\n" +
						"AND subdivision LIKE '%' AND town_code like '"+towncodee+"'  and fdrcategory like 'BOUNDARY METER' ORDER BY mtrno";
				//System.err.println(query);
			}
			if(location.equalsIgnoreCase("FEEDER METER")) {
				query="SELECT mtrno,customer_name,tp_fdr_id,fdrcategory,division,circle,kno FROM meter_data.master_main m LEFT JOIN meter_data.feederdetails f ON m.mtrno=f.meterno\n" +
						"where\n" +
						" circle LIKE '"+circle+"' AND division LIKE '%'\n" +
						"AND subdivision LIKE '%' AND town_code like '"+towncodee+"'  and fdrcategory like 'FEEDER METER' ORDER BY mtrno";
			//System.err.println("BOUNDARY--"+query);	
			}
			if(location.equalsIgnoreCase("DT")) {
				query="SELECT mtrno,customer_name,dttpid,fdrcategory,division,circle,kno FROM meter_data.master_main m\n" +
						"LEFT JOIN meter_data.dtdetails d ON m.mtrno=d.meterno\n" +
						"where\n" +
						" circle LIKE '"+circle+"' AND division LIKE '%'\n" +
						"AND m.subdivision LIKE '%' AND town_code like '"+towncodee+"' and fdrcategory like 'DT' ORDER BY mtrno";
				
			}
			MeterData1=postgresMdas.createNativeQuery(query).getResultList();
			
			   PdfPTable parameterTable = new PdfPTable(7);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("MeterNo",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LocationName",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LocationId",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LocationType",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Division",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Circle",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < MeterData1.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=MeterData1.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 

	            		}
	            	}
	            }
	           
	           document.add(parameterTable);
	           
	           document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
		document.close();
	    response.setHeader("Content-disposition", "attachment; filename=ManageMeters.pdf");
		response.setContentType("application/pdf");
		ServletOutputStream outstream = response.getOutputStream();
		baos.writeTo(outstream);
		outstream.flush();
		outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public MasterMainEntity getEntityByMtrNOandTcode(String towncode, String mtrno) {
		MasterMainEntity result=null;
		try {
			result= getCustomEntityManager("postgresMdas").createNamedQuery("MasterMainEntity.getEntityByMtrNOandTcode",MasterMainEntity.class)
					.setParameter("town_code", towncode)
					.setParameter("mtrno", mtrno)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean getmetrno(String mtrno) {
		List<MasterMainEntity> vll=postgresMdas.createNamedQuery("MasterMainEntity.getFeederData").setParameter("mtrNo", mtrno).getResultList();
		if(!vll.isEmpty()){
			return true;
		}
		return false;
	}

	
	
}
