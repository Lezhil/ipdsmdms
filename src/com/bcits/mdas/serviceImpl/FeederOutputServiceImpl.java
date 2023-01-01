package com.bcits.mdas.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.FeederOutputEntity;
import com.bcits.mdas.entity.SurveyOutputMobileEntity;
import com.bcits.mdas.service.FeederOutputService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class FeederOutputServiceImpl extends GenericServiceImpl<FeederOutputEntity> implements FeederOutputService 
{
	
	@Override
	public List<?> getCircle()
	{
		
		return postgresMdas.createNativeQuery("SELECT DISTINCT circle from  meter_data.master").getResultList();
	}

	@Override
	public List<?> getDistrictByCircle(String circle) {
		
		return postgresMdas.createNativeQuery("SELECT DISTINCT district from  meter_data.master where circle=:circle").setParameter("circle", circle).getResultList();
	}

	@Override
	public List<?> getSubStationByDistrict(String circle, String district) {
		
		return postgresMdas.createNativeQuery("SELECT DISTINCT substation from  meter_data.master where circle=:circle and district=:district").setParameter("circle", circle).setParameter("district", district).getResultList();
	}

	@Override
	public List<?> getFeederByStation(String circle, String district,String substation)
			
	{
		String qry="SELECT id, FEEDER_NAME,FEEDER_CODE,METER_NUMBER,METER_MAKE,DLMS,PORT_CONFIGURATION,NETWORK_AVAILABILITY,CT_RATIO,PT_RATIO,MF,LATITUDE,LONGITUDE from vcloudengine.feeder_output where SUBSTATION =:substation";
		//System.out.println(qry);
		return postgresMdas.createNativeQuery(qry).setParameter("substation", substation).getResultList();
	}

	@Override
	public Object getFeederAllData(String id)
	{
		//System.out.println(">>>>>>>>>>>>>"+"SELECT id,\"SITECODE\", \"FEEDER_NAME\",\"FEEDER_CODE\",\"METER_NUMBER\",\"METER_MAKE\",\"METER_YEAR\",\"PORT_CONFIGURATION\",\"SUBSTATION\" from vcloudengine.feeder_output where id='"+id+"'");
		return postgresMdas.createNativeQuery("SELECT id, SITECODE, FEEDER_NAME,FEEDER_CODE,METER_NUMBER,METER_MAKE,METER_YEAR,PORT_CONFIGURATION,SUBSTATION from vcloudengine.feeder_output where id=:id").setParameter("id", Integer.parseInt(id)).getSingleResult();
	}

	@Override
	public byte[] getImage(HttpServletRequest request,HttpServletResponse response, int id,String imagetype)
	{
		System.out.println("Image ID="+id);
		try
		{
		//System.out.println(">>>>>>>>>>"+"SELECT * from vcloudengine.feeder_output where id="+id+"");	
		FeederOutputEntity fd=(FeederOutputEntity)postgresMdas.createNativeQuery("SELECT * from vcloudengine.feeder_output where id=:id",FeederOutputEntity.class).setParameter("id", id).getSingleResult();
		response.setContentType("image/jpeg/png");
		if(imagetype.equalsIgnoreCase("Front"))
		{
		if(fd.getFront_image()!=null)
		 {
			 byte photo[] =fd.getFront_image();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
        }
		else
		{
			return null;
		}
		}
		else if(imagetype.equalsIgnoreCase("Left"))
		{
	    if(fd.getLeft_image()!=null)
		{
			 byte photo[] =fd.getLeft_image();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
		}
	    else
		{
			return null;
		}
	    }
		else if(imagetype.equalsIgnoreCase("Right"))
		{
	    if(fd.getRight_image()!=null)
		{
			 byte photo[] =fd.getRight_image();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
		}
	    else
		{
			return null;
		}
	    }
		else if(imagetype.equalsIgnoreCase("TTL"))
		{
	    if(fd.getTtb_image()!=null)
		{
			 byte photo[] =fd.getTtb_image();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
		}
	    else
		{
			return null;
		}
	    }
		else if(imagetype.equalsIgnoreCase("Port"))
		{
	    if(fd.getPort_image()!=null)
		{
			 byte photo[] =fd.getPort_image();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
		}
	    else
		{
			return null;
		}
	    }
		else
		{
			return null;
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	
	
	
	@Override
	public byte[] getConsumerImage(HttpServletRequest request,HttpServletResponse response, int id,String imagetype)
	{
		System.out.println("Image ID="+id);
		try
		{
		//System.out.println(">>>>>>>>>>"+"SELECT * from vcloudengine.feeder_output where id="+id+"");	
		SurveyOutputMobileEntity fd=(SurveyOutputMobileEntity)postgresMdas.createNativeQuery("SELECT * from meter_data.survey_output where id=:id",SurveyOutputMobileEntity.class).setParameter("id", id).getSingleResult();
		response.setContentType("image/jpeg/png");
		if(imagetype.equalsIgnoreCase("Front"))
		{
		if(fd.getMeter_image()!=null)
		 {
			 byte photo[] =fd.getMeter_image();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
        }
		else
		{
			return null;
		}
		}
		else if(imagetype.equalsIgnoreCase("Left"))
		{
	    if(fd.getMcrreportimage()!=null)
		{
			 byte photo[] =fd.getMcrreportimage();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
		}
	    else
		{
			return null;
		}
	    }
		else if(imagetype.equalsIgnoreCase("Right"))
		{
	    if(fd.getNewmeterimage()!=null)
		{
			 byte photo[] =fd.getNewmeterimage();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
		}
	    else
		{
			return null;
		}
	    }
		else if(imagetype.equalsIgnoreCase("TTL"))
		{
	    if(fd.getPremise_image()!=null)
		{
			 byte photo[] =fd.getPremise_image();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
		}
	    else
		{
			return null;
		}
	    }
//		else if(imagetype.equalsIgnoreCase("Port"))
//		{
//	    if(fd.getPort_image()!=null)
//		{
//			 byte photo[] =fd.getPort_image();    	
//	    	 OutputStream ot = null;
//	    	 try {
//				ot = response.getOutputStream();
//				ot.write(photo);
//				ot.close(); 
//			} catch (IOException e) {
//				
//				e.printStackTrace();
//			}   	
//	         Base64 b=new Base64();
//	         
//			 return b.encodeBase64(photo);
//		}
//	    else
//		{
//			return null;
//		}
//	    }
		else
		{
			return null;
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
			
	}
	
	

	@Override
	public List<Map<String, Object>> getSurveyData(String fromdate,ModelMap model) 
	{
		String qry="SELECT  A.ftotal,B.stotal from\n" +
				" (SELECT count(*)  as ftotal FROM vcloudengine.feeder_output WHERE to_date(timestaken, 'dd/MM/yyyy')<= to_date(:fromdate, 'dd/MM/yyyy') and state !='')A,\n" +
				"(SELECT count(*) as stotal FROM vcloudengine.substation_output WHERE to_date(timestamp, 'dd/MM/yyyy')<= to_date(:fromdate, 'dd/MM/yyyy') and state !='') B";
	    System.out.println("+++++++++++++"+qry);
	    
	    List<?> list=postgresMdas.createNativeQuery(qry).setParameter("fromdate", fromdate).getResultList();
	    
	    for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj1 = (Object[]) iterator.next();
		    
			if(((BigInteger)obj1[0]).intValue()==0 && ((BigInteger)obj1[1]).intValue()==0)
			{
				model.put("mgs3","notdisplay");
				System.out.println("zero record");
			}
			else
			{
			model.put("feeder",obj1[0]);
			model.put("substation",obj1[1]);
			System.out.println("zero not record");
			model.put("mgs3","display");
			}						
			System.out.println("end");			
		}	     	    
		return null;
	}

	@Override
	public List<Map<String, Object>> getFeederAllDAta(String fromdate,
			ModelMap model) {
		List<Map<String, Object>> result=new ArrayList<>();
		
		
		String qry="select  district,substation,feeder_name,feeder_code,meter_number,meter_make,dlms,port_configuration,network_availability \n" +
				"from vcloudengine.feeder_output where  to_date(timestaken, 'dd/MM/yyyy')<= to_date(:fromdate, 'dd/MM/yyyy') and state !=''\n" +
				"order by district,substation";
		System.out.println(">>>>>>>>"+qry);
		List<?> list=postgresMdas.createNativeQuery(qry).setParameter("fromdate", fromdate).getResultList();
		   
		if(list.size()>0)
		{
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj1 = (Object[]) iterator.next();
			Map<String, Object> data=new HashMap<>();
			data.put("district", obj1[0]);
			data.put("substation", obj1[1]);
			data.put("feedername", obj1[2]);
			data.put("feedercode", obj1[3]);
			data.put("meterno", obj1[4]);
			data.put("metermake", obj1[5]);
			data.put("dlms", obj1[6]);
			data.put("portconfig", obj1[7]);
			data.put("network", obj1[8]);
			result.add(data);
		}
		model.put("msg", "display");
	    model.put("msg2", "");
		}else
		{
		model.put("msg", "");
	    model.put("msg2", "");
	    model.put("msg3", "notdisplay");}
		
		return result;
	}

	@Override
	public List<Map<String, Object>> getSurveyAllData(ModelMap model)
	{
		String qry="SELECT A.state,A.stotal,coalesce(B.totalfeeder, 0) as totalfeeder,coalesce(B.DLMS, 0) as DLMS,coalesce(B.nonDLMS, 0) as nonDLMS from\n" +
				"(SELECT  state, count(*) as stotal FROM vcloudengine.substation_output  where state !=''\n" +
				"GROUP BY state)A\n" +
				"left JOIN\n" +
				"(SELECT  state,count(*)as totalfeeder,\n" +
				"sum(case when dlms='DLMS' then 1 else 0 end)as DLMS,\n" +
				"sum(case when dlms='Non-DLMS' then 1 else 0 end)  as nonDLMS \n" +
				"FROM vcloudengine.feeder_output where state !=''\n" +
				"GROUP BY state)B\n" +
				"on A.state=B.state";
	    System.out.println("+++++++++++++"+qry);
	    
	    List<?> list=postgresMdas.createNativeQuery(qry).getResultList();
	    List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
	    
		
		
				for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) 
				{
			    Object[] obj1 = (Object[]) iterator.next();
                Map<String, Object> mapdata=new HashMap<>();
				mapdata.put("state",obj1[0]);
				mapdata.put("stotal",obj1[1]);
				mapdata.put("totalfeeder",obj1[2]);
				mapdata.put("dlms",obj1[3]);
				mapdata.put("nondlms",obj1[4]);
		    	data.add(mapdata);
				}
	    
		return data;
		
		
	}

	@Override
	public List<Map<String, Object>> getSurveyDataByZone(ModelMap model,String state)
			 {
		      String qry="SELECT A.zone,A.stotal,coalesce(B.totalfeeder, 0) as totalfeeder,coalesce(B.DLMS, 0) as DLMS,coalesce(B.nonDLMS, 0) as nonDLMS from\n" +
		    		  "	(SELECT  zone, count(*) as stotal FROM vcloudengine.substation_output where state='"+state+"'\n" +
		    		  "	GROUP BY zone)A\n" +
		    		  "	left JOIN\n" +
		    		  "(SELECT  zone,count(*)as totalfeeder,\n" +
		    		  "	sum(case when dlms='DLMS' then 1 else 0 end)as DLMS,\n" +
		    		  "	sum(case when dlms='Non-DLMS' then 1 else 0 end)  as nonDLMS \n" +
		    		  "	FROM vcloudengine.feeder_output where state='"+state+"'\n" +
		    		  "	GROUP BY zone)B\n" +
		    		  "	on A.zone=B.zone ORDER BY zone";
		
	        	
		      System.out.println("+++++++++++++"+qry);
			    
			    List<?> list=postgresMdas.createNativeQuery(qry).getResultList();
			    List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
			    
						for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) 
						{
					    Object[] obj1 = (Object[]) iterator.next();
		                Map<String, Object> mapdata=new HashMap<>();
						mapdata.put("zone",obj1[0]);
						mapdata.put("stotal",obj1[1]);
						mapdata.put("totalfeeder",obj1[2]);
						mapdata.put("dlms",obj1[3]);
						mapdata.put("nondlms",obj1[4]);
				    	data.add(mapdata);
						}
						model.put("ZoneDiv", "display");
						model.put("state", state);
			    
				return data;
	        }

	@Override
	public List<Map<String, Object>> getSurveyDataByCircle(ModelMap model,String zone,String state)
			 {

	      String qry="SELECT A.circle,A.stotal,coalesce(B.totalfeeder, 0) as totalfeeder,coalesce(B.DLMS, 0) as DLMS,coalesce(B.nonDLMS, 0) as nonDLMS from\n" +
	    		  "				(SELECT  circle, count(*) as stotal FROM vcloudengine.substation_output where state='"+state+"' and zone ='"+zone+"'\n" +
	    		  "				GROUP BY circle)A\n" +
	    		  "				left JOIN\n" +
	    		  "				(SELECT  circle,count(*)as totalfeeder,\n" +
	    		  "				sum(case when dlms='DLMS' then 1 else 0 end)as DLMS,\n" +
	    		  "				sum(case when dlms='Non-DLMS' then 1 else 0 end)  as nonDLMS \n" +
	    		  "				FROM vcloudengine.feeder_output where state='"+state+"' and zone ='"+zone+"'\n" +
	    		  "				GROUP BY circle)B\n" +
	    		  "				on A.circle=B.circle ORDER BY circle";
	
      	
	      System.out.println("+++++++++++++"+qry);
		    
		    List<?> list=postgresMdas.createNativeQuery(qry).getResultList();
		    List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		    
					for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) 
					{
				    Object[] obj1 = (Object[]) iterator.next();
	                Map<String, Object> mapdata=new HashMap<>();
					mapdata.put("circle",obj1[0]);
					mapdata.put("stotal",obj1[1]);
					mapdata.put("totalfeeder",obj1[2]);
					mapdata.put("dlms",obj1[3]);
					mapdata.put("nondlms",obj1[4]);
			    	data.add(mapdata);
					}

					model.put("districtDiv", "display");
					model.put("zone", zone);
					model.put("state", state);
		    
			return data;
      
		
	             	
	         }

	@Override
	public List<Map<String, Object>> getFeederData(ModelMap model,String state,String zone,String circle)
			{
		String qry="SELECT A.division,A.stotal,coalesce(B.totalfeeder, 0) as totalfeeder,coalesce(B.DLMS, 0) as DLMS,coalesce(B.nonDLMS, 0) as nonDLMS from\n" +
				"				(SELECT  division, count(*) as stotal FROM vcloudengine.substation_output where state='"+state+"' and zone ='"+zone+"' and circle='"+circle+"' \n" +
				"				GROUP BY division)A\n" +
				"				left JOIN\n" +
				"				(SELECT  division,count(*)as totalfeeder,\n" +
				"				sum(case when dlms='DLMS' then 1 else 0 end)as DLMS,\n" +
				"				sum(case when dlms='Non-DLMS' then 1 else 0 end)  as nonDLMS \n" +
				"				FROM vcloudengine.feeder_output where state='"+state+"' and zone ='"+zone+"' and circle like '"+circle+"'\n" +
				"				GROUP BY division)B\n" +
				"				on A.division=B.division ORDER BY division";
	
    	
	      System.out.println("+++++++++++++"+qry);
		    
		    List<?> list=postgresMdas.createNativeQuery(qry).getResultList();
		    List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		    
					for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) 
					{
				    Object[] obj1 = (Object[]) iterator.next();
	                Map<String, Object> mapdata=new HashMap<>();
					mapdata.put("division",obj1[0]);
					mapdata.put("stotal",obj1[1]);
					mapdata.put("totalfeeder",obj1[2]);
					mapdata.put("dlms",obj1[3]);
					mapdata.put("nondlms",obj1[4]);
			    	data.add(mapdata);
					}
					model.put("divisiondiv", "display");
					model.put("state", state);
					model.put("zone", zone);
					model.put("circle", circle);
		    
			return data;
    
	        }

	@Override
	public List<Map<String, Object>> getSubDivisionData(ModelMap model,String state, String zone, String circle, String division)
	      {
		String qry="SELECT A.subdivision,A.stotal,coalesce(B.totalfeeder, 0) as totalfeeder,coalesce(B.DLMS, 0) as DLMS,coalesce(B.nonDLMS, 0) as nonDLMS from\n" +
				"	(SELECT  subdivision, count(*) as stotal FROM vcloudengine.substation_output  WHERE division='"+division+"'  and circle='"+circle+"'\n" +
				"	GROUP BY subdivision)A\n" +
				"	left JOIN\n" +
				"   (SELECT  subdivision,count(*)as totalfeeder,\n" +
				"	sum(case when dlms='DLMS' then 1 else 0 end)as DLMS,\n" +
				"	sum(case when dlms='Non-DLMS' then 1 else 0 end)  as nonDLMS \n" +
				"	FROM vcloudengine.feeder_output  WHERE division='"+division+"' and circle='"+circle+"'\n" +
				"	GROUP BY subdivision)B\n" +
				"	on A.subdivision=B.subdivision ORDER BY subdivision";
	
    	
	      System.out.println("+++++++++++++"+qry);
		    
		    List<?> list=postgresMdas.createNativeQuery(qry).getResultList();
		    List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		    
					for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) 
					{
				    Object[] obj1 = (Object[]) iterator.next();
	                Map<String, Object> mapdata=new HashMap<>();
					mapdata.put("subdivision",obj1[0]);
					mapdata.put("stotal",obj1[1]);
					mapdata.put("totalfeeder",obj1[2]);
					mapdata.put("dlms",obj1[3]);
					mapdata.put("nondlms",obj1[4]);
			    	data.add(mapdata);
					}
					model.put("subdivisiondiv", "display");
					model.put("state", state);
					model.put("zone", zone);
					model.put("circle", circle);
					model.put("division", division);
		    
			return data;
    
		
		
		
		
	      }

	@SuppressWarnings("unchecked")
	@Override
	public List<FeederOutputEntity> findDistinctZones() {
		List<FeederOutputEntity> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("FeederOutputEntity.findDistinctZones").getResultList();
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
	public List<FeederOutputEntity> findDistinctCircle(String zone) {
		List<FeederOutputEntity> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("FeederOutputEntity.findDistinctCircle")
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
	public List<FeederOutputEntity> findDistinctDivision(String zone,String circle) {
		List<FeederOutputEntity> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("FeederOutputEntity.findDistinctDivision")
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
	public List<FeederOutputEntity> findDistinctSubDivision(String zone,String circle, String division) {
		List<FeederOutputEntity> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("FeederOutputEntity.findDistinctSubdivision")
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
	public List<FeederOutputEntity> findDistinctSubstation(String zone,	String circle, String division, String subdivision) {
		List<FeederOutputEntity> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("FeederOutputEntity.findDistinctsubstation")
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
	public List<FeederOutputEntity> findAllFeedersforModem(String zone,String circle, String division, String subdivision,String substation) {
		List<FeederOutputEntity> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("FeederOutputEntity.findall")
					.setParameter("zone",zone )
					.setParameter("circle",circle )
					.setParameter("division",division )
					.setParameter("subdivision",subdivision )
					.setParameter("substation",substation )
					.getResultList();
		}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return list;
	}

	public Object getViewOnImageMtrData(HttpServletRequest request, String mtrNumber,ModelMap model)
	{		
		System.out.println("inside getViewOnImageMtrData==>>"+mtrNumber);
		//return postgresMdas.createNamedQuery("FeederOutputEntity.getViewOnMapMtrData").setParameter("mtrNumber", mtrNumber).getResultList();
		//return postgresMdas.createNativeQuery("SELECT id, SITECODE, FEEDER_NAME,FEEDER_CODE,METER_NUMBER,METER_MAKE,METER_YEAR,PORT_CONFIGURATION,SUBSTATION from vcloudengine.feeder_output where meter_number=:mtrNumber").setParameter("mtrNumber", mtrNumber).getSingleResult();
		//return postgresMdas.createNativeQuery("SELECT id, sdocode, consumername,accno,meterno,newmetermake from meter_data.survey_output where meterno=:mtrNumber").setParameter("mtrNumber", mtrNumber).getSingleResult();
		return postgresMdas.createNativeQuery("SELECT id, sdocode, consumername,accno,meterno,newmetermake from meter_data.survey_output where kno=:mtrNumber").setParameter("mtrNumber", mtrNumber).getSingleResult();
		
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public byte[] findOnlyImage(ModelMap model,HttpServletRequest request,HttpServletResponse response,String mtrNumber) 
	{
		BCITSLogger.logger.info("In findOnlyImage-Image display"+mtrNumber);
		int k=0;

		int t=0;
		byte image[]=null;
		try
		{
		List<FeederOutputEntity> hh=  postgresMdas.createNamedQuery("FeederOutputEntity.findOnlyImage").setParameter("mtrNumber",mtrNumber).getResultList();

		
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
    		bt=hh.get(0).getFront_image();
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

	

}
