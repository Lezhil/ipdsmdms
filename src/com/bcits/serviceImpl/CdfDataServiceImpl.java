package com.bcits.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.AssessmentEntity;
import com.bcits.entity.CDFData;
import com.bcits.service.AssessmentsService;
import com.bcits.service.CdfDataService;
import com.bcits.service.EventmasterService;
import com.bcits.service.MasterService;


@Repository
public class CdfDataServiceImpl extends GenericServiceImpl<CDFData> implements CdfDataService 
{

	@Autowired
	private MasterService masterService;
	
	@Autowired
	private AssessmentsService assessmentsService;
	
	@Autowired
	private EventmasterService eventmasterService;
	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.CdfDataService#findAll(java.lang.String, int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public  List<CDFData> findAll(String meterNo, int billmonth)
	{	
		
		List<CDFData> list =  postgresMdas.createNamedQuery("CDFData.ValidateMeterExistance").setParameter("billmonth", billmonth).setParameter("meterNo", meterNo).getResultList();
	    return list;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public  int getRecentCdfId(String meterNo, int billmonth)
	{	
		
		List list =  postgresMdas.createNamedQuery("CDFData.getRecentCdfId").setParameter("billmonth", billmonth).setParameter("meterNo", meterNo).getResultList();
	    int cdfId = (int)list.get(0);
		return cdfId;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public  int findPrevDataD4(String meterNo)
	{	
		
		List list =  postgresMdas.createNamedQuery("CDFData.findPrevDataD4").setParameter("meterNo", meterNo).getResultList();
	    return list.size();
	}

	@Override
	public int getMaxCdfId() 
	{
		String sql="select max(cdf_id) from mdm_test.cdf_data";
		int maxCdfId=Integer.parseInt(postgresMdas.createNativeQuery(sql).getSingleResult().toString());
		return maxCdfId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CDFData> checkFileExistanceForDay(String meterNo,
			Date deviceReadingDate) {
		List<CDFData> res=new ArrayList<>();
		
		/*SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		 SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
		 SimpleDateFormat sdf4 = new SimpleDateFormat("dd/MM/yyyy");*/
		 SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.err.println("got date===================>"+deviceReadingDate);
		 String date=sdf5.format(deviceReadingDate);
		 String[] datee=date.split(" ");
		 String[] d=datee[0].split("-");
		 String sendingDate=d[2]+"-"+d[1]+"-"+d[0];
		 System.err.println("sending date======>"+d[2]+"-"+d[1]+"-"+d[0]);
		try
		{
			//String sql="SELECT * FROM mdm_test.cdf_data WHERE to_char(readdate, 'dd-MM-yyyy')= '"+sendingDate+"' AND meterno like '"+meterNo+"'";
			res=postgresMdas.createNamedQuery("CDFData.getExistanceData").setParameter("sendingDate", sendingDate).setParameter("meterNo", meterNo).getResultList();
			//System.err.println("sql====>"+sql);
			//res=postgresMdas.createNativeQuery(sql).getResultList();
			return res;
			
		}catch(Exception e)
		{
			System.err.println("Exception======");
			e.printStackTrace();
			return  res;
		}
	}

	@Override
	public int getRecentChangedCdfId(String meterNo, Date deviceReadingDate)
	{
		SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.err.println("got date===================>"+deviceReadingDate);
		 String date=sdf5.format(deviceReadingDate);
		 String[] datee=date.split(" ");
		 String[] d=datee[0].split("-");
		 String sendingDate=d[2]+"-"+d[1]+"-"+d[0];
		 System.err.println("sending date======>"+d[2]+"-"+d[1]+"-"+d[0]);
		 
		List list =  postgresMdas.createNamedQuery("CDFData.getRecentChangeCdfId").setParameter("sendingDate", sendingDate).setParameter("meterNo", meterNo).getResultList();
	    int cdfId = (int)list.get(0);
		return cdfId;
	}
	
	@Override
	public String findData(String month,ModelMap model,HttpServletRequest request)
	{
		String eventstatus0=null,eventstatus1=null;
		String eventtime1 = null;
		String eventtime0 = null;
		String mf0=null,mf1=null;
		String eventcode0=null,eventcode1=null;
		String d5id0=null,d5id1=null,d5kwh0=null,d5kwh1=null;
		double rate=2.5,amount;
		long diffInMinute=0,diffInHour = 0,diffInDays=0;
		int  units = 0,actualunits = 0,unitstobecharge = 0;
		 Object[] obj;
		 String discom="UHBVN";
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String res=null;
		try{
			String qry1="SELECT CDF_ID,METERNO,ACCNO FROM meter_data.CDF_DATA WHERE BILLMONTH='"+month+"' AND DISCOM='"+discom+"'";
			List list=getCustomEntityManager("postgresMdas").createNativeQuery(qry1).getResultList();
			for (Iterator iterator1 = list.iterator(); iterator1.hasNext();)
			   {
				 Object[] objects1 = (Object[]) iterator1.next();
				 System.out.println("cdfid---------- "+objects1[0]);
				 
				 String qry2= "SELECT\n" +
				 " 0 as eventstatus,\n" +
				 "   event_code,\n" +
				 "	(select mf from meter_data.metermaster where rdngmonth=to_number(to_char(EVENT_TIME,	'YYYYMM'),'999999') and metrno=meter_number),\n" +
				 "	to_char(\n" +
				 "		EVENT_TIME,\n" +
				 "		'YYYY-MM-DD hh:mi:ss'\n" +
				 "	),\n" +
				 "	id,\n" +
				 "	KWH\n" +
				 "FROM\n" +
				 "	meter_data.events\n" +
				 "WHERE\n" +
				 "	meter_number = '"+objects1[0]+"'\n" +
				 "ORDER BY\n" +
				 "	event_code";
				// System.err.println("qry2----------- "+qry2);
				 List list1=getCustomEntityManager("postgresMdas").createNativeQuery(qry2).getResultList(); 
				 for (int i=0;i<list1.size();i++)
				   {
					 Object[] obj1 = (Object[])list1.get(0);
					 if(obj1[0].equals("1"))
					 {
						  obj = (Object[])list1.get(i+1);
					 }
					 else
					 {
						  obj = (Object[])list1.get(i);
					 }
					 if(obj[0].equals("0"))
					 {
						 eventstatus0="0";
						 mf0=String.valueOf(obj[1]);
						 eventcode0=String.valueOf(obj[2]);
						 eventtime0=String.valueOf(obj[3]);
						 d5id0=String.valueOf(obj[4]);
						 d5kwh0=String.valueOf(obj[5]);
						 eventstatus1=null;
						 
					 }
					 else
					 {
						 eventstatus1="1";
						 mf1=String.valueOf(obj[1]);
						 eventcode1=String.valueOf(obj[2]);
						 eventtime1=String.valueOf(obj[3]);
						 d5id1=String.valueOf(obj[4]);
						 d5kwh1=String.valueOf(obj[5]);
						
					 }
					 if(eventstatus0!=null && eventstatus1!=null && Integer.parseInt(d5id0)==(Integer.parseInt(d5id1)-1) && (Double.parseDouble(d5kwh1)-Double.parseDouble(d5kwh0))>0)
					 {
						 double diff=Double.parseDouble(d5kwh1)-Double.parseDouble(d5kwh0);
						   AssessmentEntity assessmentEntity=new AssessmentEntity();
						   assessmentEntity.setBillmonth(Integer.parseInt(month));
						   assessmentEntity.setMetrno(String.valueOf(objects1[1]));
						  // System.out.println("mf,eventcode,eventtime,d5id,eventstatus "+mf0+" "+mf1+" "+eventcode0+" "+eventcode1+" "+eventtime0+" "+eventtime1+" "+d5id0+" "+d5id1+" "+eventstatus0+" "+eventstatus1);
						
						   String qry3="SELECT CIRCLE,DIVISION,SUBDIV,CATEGORY,MTRMAKE,CONSUMERNAME,ADDRESS FROM meter_data.METERMASTER WHERE METRNO='"+objects1[1]+"' AND RDNGMONTH='"+month+"' AND DISCOM='"+discom+"'";
							 List list2=getCustomEntityManager("postgresMdas").createNativeQuery(qry3).getResultList();
								 Object[] obj2 = (Object[])list2.get(0);
								 assessmentEntity.setCircle(String.valueOf(obj2[0]));
								 assessmentEntity.setDivision(String.valueOf(obj2[1]));
								 assessmentEntity.setSubdiv(String.valueOf(obj2[2]));
								 assessmentEntity.setCategory(String.valueOf(obj2[3]));
								 assessmentEntity.setMtrmake(String.valueOf(obj2[4]));
								 assessmentEntity.setName(String.valueOf(obj2[5]));
								 assessmentEntity.setAddress(String.valueOf(obj2[6]));
								 assessmentEntity.setMf(Double.parseDouble(mf1));
						   
						   if(eventcode0.equals(eventcode1))
						   {
							   String tampertype=eventmasterService.gettampertypeDetails(String.valueOf(eventcode0));
							   assessmentEntity.setTamper_type(tampertype);
						   }
						   assessmentEntity.setOccdate(eventtime0);
						   assessmentEntity.setRestdate(eventtime1);
						   Date d1 = null;
						    Date d2 = null;

						    try {
								d1 = format.parse(eventtime0);
								 d2 = format.parse(eventtime1);
							} catch (Exception e) {
								e.printStackTrace();
							}
						   

						    //in milliseconds
						    long diff1 = d2.getTime() - d1.getTime();

						    long diffSeconds = diff1 / 1000 % 60;
						    long diffMinutes = diff1 / (60 * 1000) % 60;
						    long diffHours = diff1 / (60 * 60 * 1000) % 24;
						    long diffDays = diff1 / (24 * 60 * 60 * 1000);

						  

						    String duration=diffDays+" days "+diffHours+" hours "+diffMinutes+" minutes";
							    
						    assessmentEntity.setDuration(duration);
						   units=(int)(Double.parseDouble(mf0)*diff);
						   actualunits=Math.round(units/2)+units;
						   unitstobecharge=actualunits-units;
						   amount=unitstobecharge*rate;
						   assessmentEntity.setUnits(units);
						   assessmentEntity.setActual_units(actualunits);
						   assessmentEntity.setUnits_tobecharge(unitstobecharge);
						   assessmentEntity.setCdf_id(Integer.parseInt(String.valueOf(objects1[0])));
						   assessmentEntity.setD5_id(d5id0+"-"+d5id1);
						   assessmentEntity.setRate(6.75);
						   assessmentEntity.setAmount(amount);
						   String industrytype= masterService.getindustryType(String.valueOf(objects1[2]));
						   double sanload= masterService.getsanLoad(String.valueOf(objects1[2]));
						   assessmentEntity.setIndustrytype(industrytype);
						   assessmentEntity.setSanload(sanload);
						   assessmentEntity.setDiscom(discom);
						   try
						   {
							   
						   assessmentsService.save(assessmentEntity);
						   assessmentsService.flush();
						   System.out.println("data saved");
						   res="Data parsed succesfully";
						   }
						   catch(Exception e)
						   {
							   e.printStackTrace();
						   }
						  
					 }
					
				   }
					
				}
						model.addAttribute("result","Data parsed succesfully");
						
			}
			catch (Exception e)
			{
				res="Please try Again";
			 e.printStackTrace();
			}
		return res;
	}

	
	
}
