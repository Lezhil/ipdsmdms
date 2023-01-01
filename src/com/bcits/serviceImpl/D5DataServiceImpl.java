
package com.bcits.serviceImpl;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.AlarmHistory;
import com.bcits.entity.Alarms;
import com.bcits.entity.D5Data;
import com.bcits.service.D5DataService;
import com.bcits.utility.MDMLogger;

@Repository
public class D5DataServiceImpl extends GenericServiceImpl<D5Data> implements D5DataService
{
	
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<D5Data> findAll(String meterNo,String billMonth,ModelMap model)
	{	
		List<D5Data> list= postgresMdas.createNamedQuery("D5Data.Events").setParameter("meterNo", meterNo).setParameter("billMonth", Integer.parseInt(billMonth)).getResultList();
		model.put("eventsData", list);	
		model.put("meterNo", meterNo);
		model.put("portletTitle", "Event Details");
		model.put("selectedMonth", billMonth);
		if(list.size()==0)
		{
			model.put("msg", "No data found for entered Meter number and selected yearMonth");
		}
		String eventCode="";
		/*if(list.size()>0)
		{
			for (int i = 0; i < list.size(); i++) 
			{
				eventCode=eventCode+list.get(i).getEventCode()+",";
			}
			MDMLogger.logger.info("coming eventCode "+eventCode);
			try {			
				final String queryString = "SELECT e FROM EventMaster e,D5Data d5 WHERE d5.eventCode=e.eventCode AND e.eventCode IN("+eventCode.substring(0,eventCode.length()-1)+")";
				Query query = postgresMdas.createQuery(queryString);
				List<EventMaster> list2=query.getResultList();						
				model.put("eventDescription", list2);			
			} 
			catch (RuntimeException re) 
			{
				re.printStackTrace();
				throw re;
			}
		}*/
		return list;
	}

	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getEventDetails(int eventCode,String fromDate,String toDate,String eventCat)
	{
		//System.out.println("******getEventDetails**** : "+fromDate+ " : "+toDate);
		String qry="";
		List<BigDecimal> s=postgresMdas.createNativeQuery("select event_code from meter_data.event_master where category='"+eventCat+"'").getResultList();
		String fs="";
		for(BigDecimal sl:s){
			fs+="'"+sl.toString()+"',";
		}
		
		if(eventCode==0){
			qry="SELECT meter_number,(SELECT DISTINCT m3.ACCNO FROM meter_data.master m3 CROSS JOIN meter_data.metermaster m4\n" +
					"WHERE m3.accno = m4.accno AND m4.metrno= e.meter_number LIMIT 1) AS ACCNO,(SELECT DISTINCT m5. NAME FROM\n" +
					"meter_data.MASTER m5 CROSS JOIN meter_data.METERMASTER m6 WHERE m5.ACCNO = m6.ACCNO AND m6.METRNO = e.meter_number\n" +
					"LIMIT 1) AS NAME,e.event_code,(SELECT DISTINCT m3.division FROM meter_data.MASTER m3 CROSS JOIN meter_data.METERMASTER m4\n" +
					"WHERE m3.ACCNO = m4.ACCNO AND m4.METRNO = e.meter_number LIMIT 1) AS division,\n" +
					"(SELECT DISTINCT m3.circle FROM meter_data.MASTER m3 CROSS JOIN meter_data.METERMASTER m4 WHERE m3.ACCNO = m4.ACCNO\n" +
					"AND m4.METRNO = e.meter_number LIMIT 1) AS circle,em.event,e.event_time,e.v_r,e.v_y,e.v_b,e.i_r,e.i_y,e.i_b,e.pf_r,e.pf_y,e.pf_b,\n" +
					"(SELECT DISTINCT M .CONTRACTDEMAND FROM meter_data.MASTER M CROSS JOIN meter_data.METERMASTER mm WHERE M .ACCNO = mm.ACCNO\n" +
					"AND mm.METRNO = e.meter_number LIMIT 1) AS CONTRACTDEMAND,e.kwh FROM meter_data.events e CROSS JOIN meter_data.EVENT_MASTER em\n" +
					"WHERE e.event_code=cast(em.event_code AS TEXT)AND (to_char( e.event_time, 'DD-MM-YYYY') BETWEEN '"+fromDate+"' AND '"+toDate+"') AND "
					+ "e.event_code in ("+fs.substring(0, fs.length() - 1)+") ORDER BY meter_number";
		}
		else{
			qry="SELECT meter_number,(SELECT DISTINCT m3.ACCNO FROM meter_data.master m3 CROSS JOIN meter_data.metermaster m4\n" +
					"WHERE m3.accno = m4.accno AND m4.metrno= e.meter_number LIMIT 1) AS ACCNO,(SELECT DISTINCT m5. NAME FROM\n" +
					"meter_data.MASTER m5 CROSS JOIN meter_data.METERMASTER m6 WHERE m5.ACCNO = m6.ACCNO AND m6.METRNO = e.meter_number\n" +
					"LIMIT 1) AS NAME,e.event_code,(SELECT DISTINCT m3.division FROM meter_data.MASTER m3 CROSS JOIN meter_data.METERMASTER m4\n" +
					"WHERE m3.ACCNO = m4.ACCNO AND m4.METRNO = e.meter_number LIMIT 1) AS division,\n" +
					"(SELECT DISTINCT m3.circle FROM meter_data.MASTER m3 CROSS JOIN meter_data.METERMASTER m4 WHERE m3.ACCNO = m4.ACCNO\n" +
					"AND m4.METRNO = e.meter_number LIMIT 1) AS circle,em.event,e.event_time,e.v_r,e.v_y,e.v_b,e.i_r,e.i_y,e.i_b,e.pf_r,e.pf_y,e.pf_b,\n" +
					"(SELECT DISTINCT M .CONTRACTDEMAND FROM meter_data.MASTER M CROSS JOIN meter_data.METERMASTER mm WHERE M .ACCNO = mm.ACCNO\n" +
					"AND mm.METRNO = e.meter_number LIMIT 1) AS CONTRACTDEMAND,e.kwh FROM meter_data.events e CROSS JOIN meter_data.EVENT_MASTER em\n" +
					"WHERE e.event_code=cast(em.event_code AS TEXT)AND (to_char( e.event_time, 'DD-MM-YYYY') BETWEEN '"+fromDate+"' AND '"+toDate+"') AND e.event_code='"+eventCode+"' ORDER BY meter_number";
			//System.err.println(qry);
			
		
		}
		return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		}
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> eventSummary(String fromDate){
		String qry="select a.cat, (select count(*) from meter_data.events where to_char(event_time, 'DD-MM-YYYY')='"+fromDate+"' and to_number(event_code,'9999') in (select event_code from meter_data.event_master where category= a.cat)) as countf from \n" +
				"(select DISTINCT category as cat from meter_data.event_master where category is not null ) a";
		return getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> showEventDetails(String billmonth, ModelMap model)
	{
		System.out.println("billmonth--"+billmonth);
		String query="";
		String query1="";
		List<Object[]> list1=null;
		List<Object[]> list2=null;
		try
		{
			//query="SELECT EVM.EVENT,COUNT(DISTINCT D5D.CDF_ID) FROM D5_DATA D5D, EVENT_MASTER EVM WHERE  EVM.EVENT_CODE=D5D.EVENT_CODE AND TO_CHAR(D5D.EVENT_TIME,'yyyyMM')='"+billmonth+"' AND D5D.EVENT_CODE IN('1','2','3','23','19') GROUP BY  D5D.EVENT_CODE,EVM.EVENT ORDER BY D5D.EVENT_CODE";

			/*query="SELECT event,d5count FROM mdm_test.DASHD5";
			query1="SELECT READINGREMARK,COUNT(*) FROM mdm_test.METERMASTER WHERE READINGREMARK LIKE 'Meter Defective' AND RDNGMONTH="+billmonth+" AND DISCOM='UHBVN' GROUP BY READINGREMARK";
*/
			//query="SELECT EVM.EVENT,COUNT(DISTINCT D5D.CDF_ID) FROM mdm_test.D5_DATA D5D, mdm_test.EVENT_MASTER EVM WHERE  EVM.EVENT_CODE=D5D.EVENT_CODE AND TO_CHAR(D5D.EVENT_TIME,'yyyyMM')='"+billmonth+"' AND D5D.EVENT_CODE IN('1','2','3','23','19') GROUP BY  D5D.EVENT_CODE,EVM.EVENT ORDER BY D5D.EVENT_CODE";
			//query="SELECT 	EVM.EVENT,	COUNT (DISTINCT D5D.meter_number) FROM 	meter_data.events D5D, 	meter_data.EVENT_MASTER EVM WHERE 	EVM.EVENT_CODE = to_number(D5D.EVENT_CODE,'9999') AND TO_CHAR(D5D.EVENT_TIME, 'yyyyMM') = '"+billmonth+"' AND D5D.EVENT_CODE IN ('1', '2', '3', '23', '19') GROUP BY 	D5D.EVENT_CODE, 	EVM.EVENT ORDER BY 	D5D.EVENT_CODE";
			query="SELECT evm.event,count(DISTINCT e.meter_number) from meter_data.events e,meter_data.event_master evm \n" +
					"WHERE evm.event_code=to_number(e.event_code,'99999')  AND evm.event_code in ('101','102','301','302')\n" +
					"GROUP BY e.event_code,evm.event ORDER BY e.event_code";
			
			query1="SELECT READINGREMARK,COUNT(*) FROM meter_data.METERMASTER WHERE READINGREMARK LIKE 'Meter Defective' AND RDNGMONTH="+billmonth+" GROUP BY READINGREMARK";
			
			//System.out.println("Sensetive tampers--"+query);
			MDMLogger.logger.info("==============================>query"+query);
			MDMLogger.logger.info("==============================>query1"+query1);
			list1=postgresMdas.createNativeQuery(query).getResultList();
			list2=postgresMdas.createNativeQuery(query1).getResultList();
			if(list2.size()>0)
			{
				model.put("defectiveList", list2);
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		MDMLogger.logger.info("==============================>query"+list1.size());
		return list1;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getalaramData(String fromdate, String todate,String alaramtype, String metertype, HttpServletRequest request,ModelMap model) {
		List<Object[]> list1=null;
		System.err.println(alaramtype);
		String qry=null;
		String eventcodes=null; 
		
		if(alaramtype.equalsIgnoreCase("'Tamper'"))
		{
			List<BigDecimal> s=postgresMdas.createNativeQuery("select event_code from meter_data.event_master where category="+alaramtype).getResultList();
		String fs="";
		for(BigDecimal sl:s){
			fs+="'"+sl.toString()+"',";
		}
			eventcodes=fs.substring(0, fs.length() - 1);
			
		}
		else if(alaramtype.equalsIgnoreCase("'Tamper','13'"))
		{
			List<BigDecimal> s=postgresMdas.createNativeQuery("select event_code from meter_data.event_master where category='Tamper'").getResultList();
			String fs="";
			for(BigDecimal sl:s){
				fs+="'"+sl.toString()+"',";
			}
			eventcodes=fs+"'101','102'";
			
		}
		else if(alaramtype.equalsIgnoreCase("'13'"))
		{
			eventcodes="'101','102'";
			
		}
		
		else if(alaramtype.equalsIgnoreCase("'Tamper','1178'"))
		{
			List<BigDecimal> s=postgresMdas.createNativeQuery("select event_code from meter_data.event_master where category in ('Tamper','Others')").getResultList();
			String fs="";
			for(BigDecimal sl:s){
				fs+="'"+sl.toString()+"',";
			}
			eventcodes=fs.substring(0, fs.length() - 1);
		}
		else if(alaramtype.equalsIgnoreCase("'13','1178'"))
		{
			List<BigDecimal> s=postgresMdas.createNativeQuery("select event_code from meter_data.event_master where category='Others'").getResultList();
			String fs="";
			for(BigDecimal sl:s){
				fs+="'"+sl.toString()+"',";
			}
			eventcodes=fs+"'101','102'";
		}
		
		else if(alaramtype.equalsIgnoreCase("'1178'"))
		{
			List<BigDecimal> s=postgresMdas.createNativeQuery("select event_code from meter_data.event_master where category in ('Others')").getResultList();
			String fs="";
			for(BigDecimal sl:s){
				fs+="'"+sl.toString()+"',";
			}
			eventcodes=fs.substring(0, fs.length() - 1);
		}
		else 
		{
			List<BigDecimal> s=postgresMdas.createNativeQuery("select event_code from meter_data.event_master where category in ('Tamper','Others')").getResultList();
			String fs="";
			for(BigDecimal sl:s){
				fs+="'"+sl.toString()+"',";
			}
			eventcodes=fs+"'101','102'";
		}
		
		 qry="SELECT d5.time_stamp,M .subdivision,M .fdrcategory,M .mtrno,d5.event_code,0 as eventstatus,e.event,d5.v_r,d5.v_y,d5.v_b,"+
				"d5.i_r,d5.i_y,d5.i_b,d5.id,0 as cdf_id FROM meter_data.events d5,meter_data.master_main M,meter_data.event_master e "+
				 "WHERE d5.event_code = cast(e.event_code as TEXT)AND d5.meter_number = M .mtrno AND event_time BETWEEN '"+fromdate+"'" +
				 "AND '"+todate+"' AND CAST (M .fdrcategory AS TEXT) IN ('LT') AND CAST (d5.event_code AS TEXT) IN ("+eventcodes+") ORDER BY M .mtrno";
	
		
		/*else
		{
			 qry="SELECT d5.time_stamp,M .subdivision,M .fdrcategory,M .mtrno,d5.event_code,0 as eventstatus,e.event,d5.v_r,d5.v_y,d5.v_b,"+
						"d5.i_r,d5.i_y,d5.i_b,d5.id,0 as cdf_id FROM meter_data.events d5,meter_data.master_main M,meter_data.event_master e "+
						 "WHERE d5.event_code = cast(e.event_code as TEXT)AND d5.meter_number = M .mtrno AND event_time BETWEEN '"+fromdate+"'" +
						 "AND '"+todate+"' AND CAST (M .fdrcategory AS TEXT) IN ('LT') AND CAST (d5.event_code AS TEXT) is not "+eventcodes+" ORDER BY M .mtrno";
		}*/
		//System.err.println("qry--------- "+qry);
		list1=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		//System.err.println(list1.size());
		if(list1.size()==0)
		{
			model.put("msg", "Data not Found");
		}
		return list1;
	}
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> gettotalAlaramData(String valtype) {
		List<Object[]> list1=null;
		String qry="";
		if(valtype.equalsIgnoreCase("EON")){
			List<BigDecimal> s=postgresMdas.createNativeQuery("select event_code from meter_data.event_master where event_status='O'").getResultList();
			String fs="";
			for(BigDecimal sl:s){
				fs+="'"+sl.toString()+"',";
			}
			qry="SELECT d5.time_stamp,M .subdivision,M .fdrcategory,M .mtrno,d5.event_code,0 as eventstatus,e.event,d5.v_r,d5.v_y,d5.v_b,d5.i_r,d5.i_y,d5.i_b,d5.id,0 as cdf_id FROM meter_data.events d5,meter_data.master_main M,meter_data.event_master e WHERE d5.event_code in ("+fs.substring(0, fs.length() - 1)+") and d5.event_code = cast(e.event_code as TEXT)AND d5.meter_number = M .mtrno ORDER BY d5.time_stamp";
			
		}
		else if(valtype.equalsIgnoreCase("all")){
			 qry="SELECT d5.time_stamp,M .subdivision,M .fdrcategory,M .mtrno,d5.event_code,0 as eventstatus,e.event,d5.v_r,d5.v_y,d5.v_b,"+
					"d5.i_r,d5.i_y,d5.i_b,d5.id,0 as cdf_id FROM meter_data.events d5,meter_data.master_main M,meter_data.event_master e "+
					 "WHERE d5.event_code = cast(e.event_code as TEXT)AND d5.meter_number = M .mtrno ORDER BY d5.time_stamp";
		}
		else if(valtype.equalsIgnoreCase("EOFF")){
			List<BigDecimal> s=postgresMdas.createNativeQuery("select event_code from meter_data.event_master where event_status='R'").getResultList();
			String fs="";
			for(BigDecimal sl:s){
				fs+="'"+sl.toString()+"',";
			}
			
			qry="SELECT d5.time_stamp,M .subdivision,M .fdrcategory,M .mtrno,d5.event_code,0 as eventstatus,e.event,d5.v_r,d5.v_y,d5.v_b,d5.i_r,d5.i_y,d5.i_b,d5.id,0 as cdf_id FROM meter_data.events d5,meter_data.master_main M,meter_data.event_master e WHERE d5.event_code in ("+fs.substring(0, fs.length() - 1)+") and d5.event_code = cast(e.event_code as TEXT)AND d5.meter_number = M .mtrno ORDER BY d5.time_stamp";
			
		}
		
	
		
		
		list1=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		
		return list1;
	}
	


	@Override
	public  List<Object[]> geteventdata()
	{
		List<Object[]> list1=null;
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MINUTE, -59);
		    String totime = dateFormat.format(Calendar.getInstance().getTime());
		    String fromtime = dateFormat.format(now.getTime());
		    System.out.println(fromtime );    
		    System.out.println(totime );    
			String	qry="SELECT c.meterno,TO_CHAR(d5.event_time,'DD-MM-YYYY'),m.circle,m.division,m.subdiv,d5.cdf_id FROM mdm_test.d5_data d5 ,mdm_test.cdf_data c ,mdm_test.master m\n" +
				"WHERE d5.cdf_id=c.cdf_id and c.accno=m.accno and event_time BETWEEN  '"+fromtime+"' AND '"+totime+"' \n" +
				"AND m.category IN( 'HT') AND d5.event_code='13' GROUP BY d5.cdf_id,TO_CHAR(d5.event_time,'DD-MM-YYYY'),m.circle,m.division,m.subdiv,c.meterno";
		
			list1=postgresMdas.createNativeQuery(qry).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	return list1;
	}


	
	
	
	

	
}
