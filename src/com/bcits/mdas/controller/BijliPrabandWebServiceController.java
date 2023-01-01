package com.bcits.mdas.controller;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.EmailGateway;
import com.bcits.entity.MessagingSettings;
import com.bcits.entity.NetworkPathGateWay;
import com.bcits.entity.SmsGateway;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.EmailGatewayService;
import com.bcits.service.MasterService;
import com.bcits.service.MessageSettingsService;
import com.bcits.service.NetworkGateWayService;
import com.bcits.service.SmsGatewayService;

@Controller
public class BijliPrabandWebServiceController {

	/*@PersistenceContext(unitName="mdm")
	protected EntityManager entityManager;*/
	
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager entityManagerNew;
	
	@Autowired
	private SmsGatewayService smsGatewayService;
	
	@Autowired
	private EmailGatewayService emailGatewayService;
	
	@Autowired
	private MessageSettingsService messageSettingsService;
	
	@Autowired
	private MasterService masterService;
	
	@Autowired
	ConsumerMasterService consumerMasterService;
	
	@Autowired
	private NetworkGateWayService networkgatewayservice;
	
	

	
	@RequestMapping(value = "/getmeterdatacommunication", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object getLessBilledMobilesCount(@RequestBody String data) throws Exception {
		sout("Hitting.. getmeterdatacommunication");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		String type = new JSONObject(data).getString("type");
		String Val = new JSONObject(data).getString("value");
		int sdoLength=sdoCode.length();

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		
		String mainQuery="";
		if("IMEI".equals(type)) {
			mainQuery="SELECT meter_number,imei,date,last_communication FROM meter_data.modem_communication WHERE imei='"+Val+"' ORDER BY last_communication DESC";
		} else if("METER".equals(type)) {
			mainQuery="SELECT meter_number,imei,date,last_communication FROM meter_data.modem_communication WHERE meter_number='"+Val+"' ORDER BY last_communication DESC";
		} 
		sout(mainQuery);
		
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		System.err.println(list.size());
		System.err.println(list.isEmpty());
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				map.put("meter_number",(obj[0]==null)?"0" :obj[0]);
				map.put("imei",(obj[1]==null)?"0" :obj[1]);
				map.put("date",(obj[2]==null)?"0" :obj[2]);
				map.put("last_communication",(obj[3]==null)?"0" :obj[3]);
				result.add(map);
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/getmeterdatacommunicationall", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object getmeterdatacommunicationall(@RequestBody String data) throws Exception {
		sout("Hitting.. getmeterdatacommunicationall");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		
		String mainQuery="SELECT meter_number, imei, date, last_communication  FROM meter_data.modem_communication WHERE date=CURRENT_DATE ORDER BY last_communication DESC";
		sout(mainQuery);
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				map.put("meter_number",(obj[0]==null)?"0" :obj[0]);
				map.put("imei",(obj[1]==null)?"0" :obj[1]);
				map.put("date",(obj[2]==null)?"0" :obj[2]);
				map.put("last_communication",(obj[3]==null)?"0" :obj[3]);
				result.add(map);
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/get360meterview", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object get360meterview(@RequestBody String data) throws Exception {
		sout("Hitting.. get360meterview");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		String category = new JSONObject(data).getString("category");
		String meterNum = new JSONObject(data).getString("mtrNum");
		String fromDate = new JSONObject(data).getString("fdate");
		String toDate = new JSONObject(data).getString("tdate");
		
		Date d=new Date();
		Calendar c=Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -1);
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		
		String presentDate = dateFormat.format(Calendar.getInstance().getTime());
	    String yesterDay = dateFormat.format(c.getTime());
	    //String yesterDay = dateFormat.format(c.getTime());
	    
	    String[] str=presentDate.split("-");
	    
		System.out.println("presentDate==firstDate>"+presentDate);
		System.out.println("yesterDay==>"+str[0]+"-"+str[1]+"-"+"01");
		
		String frmDate=presentDate;
		String tDate=str[0]+"-"+str[1]+"-"+"01";
		int sdoLength=sdoCode.length();

		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		Date d1=new Date();
		String month=sdf.format(d1);
		
		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		if(category.equalsIgnoreCase("1"))
		{
			//Consumercategory
			String mainQuery="SELECT ma.circle,ma.phoneno2,ma.division,ma.address1,ma.sdoname,ma.accno,m.metrno,ma.tariffcode,ma.industrytype,ma.kno,ma.name,ma.supplytype \n" +
					" FROM meter_data.metermaster m, meter_data.master ma WHERE m.rdngmonth='"+month+"' AND m.metrno='"+meterNum+"'\n" +
					"AND m.accno=ma.accno ";
			sout(mainQuery);
			List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
			for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
				Object[] obj=(Object[]) iterator.next();
				Map<String, Object> map=new HashMap<String, Object>();

				if(obj[0]!=null){
					map.put("circle",(obj[0]==null)?"0" :obj[0]);
					map.put("mobileno",(obj[1]==null)?"0" :obj[1]);
					map.put("division",(obj[2]==null)?"0" :obj[2]);
					map.put("address",(obj[3]==null)?"0" :obj[3]);
					map.put("sdoname",(obj[4]==null)?"0" :obj[4]);
					map.put("accno",(obj[5]==null)?"0" :obj[5]);
					map.put("metrno",(obj[6]==null)?"0" :obj[6]);
					map.put("tariffcode",(obj[7]==null)?"0" :obj[7]);
					map.put("industype",(obj[8]==null)?"0" :obj[8]);
					map.put("kno",(obj[9]==null)?"0" :obj[9]);
					map.put("name",(obj[10]==null)?"0" :obj[10]);
					map.put("supplytype",(obj[11]==null)?"0" :obj[11]);
					result.add(map);
				}
			}

			
		}
		
		else if(category.equalsIgnoreCase("2"))
		{
			//Eventcategory
			/*String mainQuery="SELECT	meter_number,	event_time,event_code, ( case when (to_number(event_code, '999')-lag(to_number(event_code, '999'), 1) OVER (ORDER BY event_time))=1 then " + 
					" concat(event_time,',',lag(event_time, 1) OVER (ORDER BY event_time))  END) as duration,	v_r,	v_y,	v_b,	i_r,	i_y,	i_b,	pf_r,	pf_y,	pf_b,	kwh " + 
					" FROM	meter_data.events WHERE	meter_number = '"+meterNum+"' AND to_char(event_time, 'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY 	event_time DESC";
			*/
		String 	mainQuery="SELECT	meter_number,to_char(event_time,'yyyy-MM-dd HH24:MI:SS') event_time,event_code, ( case when (to_number(event_code, '999')-lag(to_number(event_code, '999'), 1) OVER (ORDER BY event_time))=1 then " + 
				" concat(event_time,',',lag(event_time, 1) OVER (ORDER BY event_time))  END) as duration,	v_r,	v_y,	v_b,	i_r,	i_y,	i_b,	pf_r,	pf_y,	pf_b,	kwh " + 
				" FROM	meter_data.events WHERE	meter_number = '"+meterNum+"' ";
			sout(mainQuery);
			String ecode="";
			List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
			for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
				Object[] obj=(Object[]) iterator.next();
				Map<String, Object> map=new HashMap<String, Object>();

				if(obj[0]!=null){
					try {
					map.put("meter_number",(obj[0]==null)?"0" :obj[0]);
					map.put("event_time",(obj[1]==null)?"0" :obj[1]);
					map.put("event_desc",(obj[2]==null)?"0" :obj[2]);
					ecode=getEventDesc(obj[2].toString());
					map.put("event_code",(ecode==null)?"0" :ecode);
					map.put("duration",(obj[3]==null)?"0" :obj[3]);
					map.put("vr",(obj[4].toString()!=null)?obj[4].toString(): "N/A");
					map.put("vy",(obj[5].toString()!=null)?obj[5].toString(): "N/A");
					map.put("vb",(obj[6].toString()!=null)?obj[6].toString(): "N/A");
					map.put("ir",(obj[7].toString()!=null)?obj[7].toString(): "N/A");
					map.put("iy",(obj[8].toString()!=null)?obj[8].toString(): "N/A");
					map.put("ib",(obj[9].toString()!=null)?obj[9].toString(): "N/A");
					map.put("pf_r",(obj[10].toString()!=null)?obj[10].toString(): "N/A");
					map.put("pf_y",(obj[11].toString()!=null)?obj[11].toString(): "N/A");
					map.put("pf_b",(obj[12].toString()!=null)?obj[12].toString(): "N/A");
					map.put("kwh",(obj[13].toString()!=null)?obj[13].toString(): "N/A");
					result.add(map);
					}
					catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
		
		else if(category.equalsIgnoreCase("3"))
		{
			//instantcategory
			String mainQuery="SELECT meter_number,kwh,kvah,kva,i_r_angle,i_y_angle,i_b_angle,i_r,i_y,i_b,v_r,v_y,v_b,pf_r,pf_y,pf_b,pf_threephase,"
					+ "frequency,power_kw,kvar,kvarh_lag,kvarh_lead,power_off_count,power_off_duration,tamper_count FROM meter_data.amiinstantaneous WHERE meter_number='"+meterNum+"' " + 
					 " AND read_time=(SELECT  max(read_time) FROM meter_data.amiinstantaneous WHERE meter_number='"+meterNum+"')";
			sout(mainQuery);
			List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
			for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
				Object[] obj=(Object[]) iterator.next();
				Map<String, Object> map=new HashMap<String, Object>();

				if(obj[0]!=null){
					map.put("meter_number",(obj[0]==null)?"0" :obj[0]);
					map.put("kwh",(obj[1]==null)?"0" :obj[1]);
					map.put("kvah",(obj[2]==null)?"0" :obj[2]);
					map.put("kva",(obj[3]==null)?"0" :obj[3]);
					map.put("i_r_angle",(obj[4]==null)?"0" :obj[4]);
					map.put("i_y_angle",(obj[5]==null)?"0" :obj[5]);
					map.put("i_b_angle",(obj[6]==null)?"0" :obj[6]);
					map.put("i_r",(obj[7]==null)?"0" :obj[7]);
					map.put("i_y",(obj[8]==null)?"0" :obj[8]);
					map.put("i_b",(obj[9]==null)?"0" :obj[9]);
					map.put("v_r",(obj[10]==null)?"0" :obj[10]);
					map.put("v_y",(obj[11]==null)?"0" :obj[11]);
					map.put("v_b",(obj[12]==null)?"0" :obj[12]);
					map.put("pf_r",(obj[13]==null)?"0" :obj[13]);
					map.put("pf_y",(obj[14]==null)?"0" :obj[14]);
					map.put("pf_b",(obj[15]==null)?"0" :obj[15]);
					map.put("pf_threephase",(obj[16]==null)?"0" :obj[16]);
					map.put("frequency",(obj[17]==null)?"0" :obj[17]);
					map.put("power_kw",(obj[18]==null)?"0" :obj[18]);
					map.put("kvar",(obj[19]==null)?"0" :obj[19]);
					map.put("kvarh_lag",(obj[20]==null)?"0" :obj[20]);
					map.put("kvarh_lead",(obj[21]==null)?"0" :obj[21]);
					map.put("power_off_count",(obj[22]==null)?"0" :obj[22]);
					map.put("power_off_duration",(obj[23]==null)?"0" :obj[23]);
					map.put("tamper_count",(obj[24]==null)?"0" :obj[24]);
					result.add(map);
				}
			}
		}
		
		else if(category.equalsIgnoreCase("4"))
		{
			//Load Surveycategory
/*			String mainQuery="SELECT meter_number,read_time,v_r,v_y,v_b,i_r,i_y,i_b,kwh,kvarh_lag,kvarh_lead,kvah,frequency FROM meter_data.load_survey WHERE meter_number='"+meterNum+"' AND read_time >= to_date('"+frmDate+"', 'YYYY-MM-DD') +  INTERVAL  '30 minutes' and read_time <= to_date('"+tDate+"', 'YYYY-MM-DD') +interval '24 hours' ORDER BY read_time";
*/			String mainQuery="SELECT meter_number,to_char(read_time,'yyyy-MM-dd HH:mm:ss') as read_time ,v_r,v_y,v_b,i_r,i_y,i_b,kwh,kvarh_lag,kvarh_lead,kvah,frequency FROM meter_data.load_survey WHERE meter_number='"+meterNum+"' ";

			sout(mainQuery);
			List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
			for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
				Object[] obj=(Object[]) iterator.next();
				Map<String, Object> map=new HashMap<String, Object>();

				if(obj[0]!=null){
					map.put("meter_number",(obj[0]==null)?"0" :obj[0]);
					map.put("read_time",(obj[1]==null)?"0" :obj[1]);
					map.put("v_r",(obj[2]==null)?"0" :obj[2]);
					map.put("v_y",(obj[3]==null)?"0" :obj[3]);
					map.put("v_b",(obj[4]==null)?"0" :obj[4]);
					map.put("i_r",(obj[5]==null)?"0" :obj[5]);
					map.put("i_y",(obj[6]==null)?"0" :obj[6]);
					map.put("i_b",(obj[7]==null)?"0" :obj[7]);
					map.put("kwh",(obj[8]==null)?"0" :obj[8]);
					map.put("kvarh_lag",(obj[9]==null)?"0" :obj[9]);
					map.put("kvarh_lead",(obj[10]==null)?"0" :obj[10]);
					map.put("kvah",(obj[11]==null)?"0" :obj[11]);
					map.put("frequency",(obj[12]==null)?"0" :obj[12]);
					result.add(map);
				}
			}
		}
		
		else if(category.equalsIgnoreCase("5"))
		{
			//dailyBasiscategory
		/*	String mainQuery="SELECT DISTINCT to_char(read_time,'yyyy-MM-dd HH:mm:ss') as modem_time, " + 
					" max(v_r) as v_r,max(v_y) as v_y,max(v_b) as v_b,max(i_r) as i_r,max(i_y) as i_y,max(i_b) as i_b,sum(kwh) as kwh, " + 
					" sum(kvarh_lag) as kvarh_lag,sum(kvarh_lead) as kvarh_lead,sum(kvah) as kvah " + 
					" FROM meter_data.load_survey WHERE meter_number=:mtrNo and to_char(read_time,'yyyy-MM-dd') BETWEEN :frmDate AND :tDate " + 
					" GROUP BY to_char(read_time,'yyyy-MM-dd HH:mm:ss') ORDER BY modem_time";*/
			
			String mainQuery="SELECT DISTINCT to_char(read_time,'yyyy-MM-dd HH:mm:ss') as modem_time, " + 
					" max(v_r) as v_r,max(v_y) as v_y,max(v_b) as v_b,max(i_r) as i_r,max(i_y) as i_y,max(i_b) as i_b,sum(kwh) as kwh, " + 
					" sum(kvarh_lag) as kvarh_lag,sum(kvarh_lead) as kvarh_lead,sum(kvah) as kvah " + 
					" FROM meter_data.load_survey WHERE meter_number=:mtrNo  " + 
					" GROUP BY to_char(read_time,'yyyy-MM-dd HH:mm:ss') ORDER BY modem_time";
			sout(mainQuery);
			/*List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).setParameter("mtrNo", meterNum).setParameter("frmDate", frmDate).setParameter("tDate", tDate).getResultList();*/
			
			List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).setParameter("mtrNo", meterNum).getResultList();
			for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
				Object[] obj=(Object[]) iterator.next();
				Map<String, Object> map=new HashMap<String, Object>();

				if(obj[0]!=null){
					map.put("modem_time",(obj[0]==null)?"0" :obj[0]);
					map.put("v_r",(obj[1]==null)?"0" :obj[1]);
					map.put("v_y",(obj[2]==null)?"0" :obj[2]);
					map.put("v_b",(obj[3]==null)?"0" :obj[3]);
					map.put("i_r",(obj[4]==null)?"0" :obj[4]);
					map.put("i_y",(obj[5]==null)?"0" :obj[5]);
					map.put("i_b",(obj[6]==null)?"0" :obj[6]);
					map.put("kwh",(obj[7]==null)?"0" :obj[7]);
					map.put("kvarh_lag",(obj[8]==null)?"0" :obj[8]);
					map.put("kvarh_lead",(obj[9]==null)?"0" :obj[9]);
					map.put("kvah",(obj[10]==null)?"0" :obj[10]);
					result.add(map);
				}
			}
		}
		
		
		else if(category.equalsIgnoreCase("6"))
		{
			//billingcategory
ArrayList<String> strlist=billmonths(frmDate,tDate);

String s="";
for(int i=0;i<strlist.size();i++){
	String t=strlist.get(i);
	//s+="'"+t+"'";
	s+=t;
	if(i<(strlist.size()-1)){
		s+=",";
	}
}
					System.out.println("-----------S--------- "+s);
				String[] billmonth=s.split(",");
			String mainQuery="select billing_date,kwh ,kvah,kva,date_kva,reactive_imp_active_imp,reactive_exp_active_exp,\r\n" + 
					"kwh_tz1,kwh_tz2,kwh_tz3,kwh_tz4,kwh_tz5,kwh_tz6,kwh_tz7,kwh_tz8,\r\n" + 
					"kvah_tz1,kvah_tz2,kvah_tz3,kvah_tz4,kvah_tz5,kvah_tz6,kvah_tz7,kvah_tz8,demand_kw,occ_date_kw,kvarh_lag,kvarh_lead,AA.kwh-AA.PreviousKwh as kwh_consumption,AA.kvah-AA.Previouskvah as kvah_consumption FROM (SELECT " + 
					" 	*,LAG(kwh) OVER ( ORDER BY billing_date ) AS PreviousKwh,  LAG(kvah) OVER ( ORDER BY billing_date ) AS PreviousKvah,LAG(billing_date) OVER ( ORDER BY billing_date ) AS PEV_billing_date " + 
					" FROM meter_data.bill_history A  WHERE A .meter_number ='"+meterNum+"' " + 
					" AND A .billing_date in ('"+billmonth[0]+"','"+billmonth[1]+"','"+billmonth[2]+"','"+billmonth[3]+"','"+billmonth[4]+"') order by billing_date "  + 
					" ) AA where AA.PreviousKwh is not null  ORDER BY AA.billing_date desc";
			sout(mainQuery);
			List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
			for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
				Object[] obj=(Object[]) iterator.next();
				Map<String, Object> map=new HashMap<String, Object>();

				if(obj[0]!=null){
					map.put("billing_date",(obj[0]==null)?"0" :obj[0]);
					map.put("kwh",(obj[1]==null)?"0" :obj[1]);
					map.put("kvah",(obj[2]==null)?"0" :obj[2]);
					map.put("kva",(obj[3]==null)?"0" :obj[3]);
					map.put("date_kva",(obj[4]==null)?"0" :obj[4]);
					map.put("reactive_imp_active_imp",(obj[5]==null)?"0" :obj[5]);
					map.put("reactive_exp_active_exp",(obj[6]==null)?"0" :obj[6]);
					
					map.put("kwh_tz1",(obj[7]==null)?"0" :obj[7]);
					map.put("kwh_tz2",(obj[8]==null)?"0" :obj[8]);
					map.put("kwh_tz3",(obj[9]==null)?"0" :obj[9]);
					map.put("kwh_tz4",(obj[10]==null)?"0" :obj[10]);
					map.put("kwh_tz5",(obj[11]==null)?"0" :obj[11]);
					map.put("kwh_tz6",(obj[12]==null)?"0" :obj[12]);
					map.put("kwh_tz7",(obj[13]==null)?"0" :obj[13]);
					map.put("kwh_tz8",(obj[14]==null)?"0" :obj[14]);
					
					map.put("kvah_tz1",(obj[15]==null)?"0" :obj[7]);
					map.put("kvah_tz2",(obj[16]==null)?"0" :obj[8]);
					map.put("kvah_tz3",(obj[17]==null)?"0" :obj[9]);
					map.put("kvah_tz4",(obj[18]==null)?"0" :obj[10]);
					map.put("kvah_tz5",(obj[19]==null)?"0" :obj[11]);
					map.put("kvah_tz6",(obj[20]==null)?"0" :obj[12]);
					map.put("kvah_tz7",(obj[21]==null)?"0" :obj[13]);
					map.put("kvah_tz8",(obj[22]==null)?"0" :obj[14]);
					
					map.put("demand_kw",(obj[23]==null)?"0" :obj[7]);
					map.put("occ_date_kw",(obj[24]==null)?"0" :obj[8]);
					map.put("kvarh_lag",(obj[25]==null)?"0" :obj[9]);
					map.put("kvarh_lead",(obj[26]==null)?"0" :obj[10]);
					map.put("kwh_consumption",(obj[27]==null)?"0" :obj[11]);
					map.put("kvah_consumption",(obj[28]==null)?"0" :obj[12]);
					
					result.add(map);
				}
			}
		}
		return result;

	}
	
	@RequestMapping(value = "/tamperAnalysis", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object tamperAnalysis(@RequestBody String data) throws Exception {
		sout("Hitting.. tamperAnalysis");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		
		/*String mainQuery=" SELECT em.event, COALESCE(A.ecount,0) as Count FROM meter_data.event_master em LEFT JOIN ( SELECT event_code, count(*) as ecount FROM meter_data.events WHERE date(event_time)=CURRENT_DATE GROUP BY event_code)A "
				+ "ON em.event_code=cast(A.event_code as INTEGER)  ORDER BY Count DESC";*/
		String mainQuery="SELECT evm.event,count(DISTINCT e.meter_number) from meter_data.events e,meter_data.event_master evm \r\n" + 
				"WHERE evm.event_code=to_number(e.event_code,'99999')   \r\n" + 
				"GROUP BY e.event_code,evm.event ORDER BY e.event_code";
		sout(mainQuery);
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				map.put("event",(obj[0]==null)?"N/A" :obj[0]);
				map.put("Count",(obj[1]==null)?"0" :obj[1]);
				
				result.add(map);
			}
		}

		return result;
	}
	
	
	@RequestMapping(value = "/getExceptionEvent", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object getExceptionEvent(@RequestBody String data) throws Exception {
		sout("Hitting.. get360meterview");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		String category = new JSONObject(data).getString("category");
		String fromDate = new JSONObject(data).getString("fdate");
		String toDate = new JSONObject(data).getString("tdate");
		
	    
	    String[] str1=fromDate.split("-");
	    String[] str2=toDate.split("-");
	    
		
		
		String frmDate=str1[2]+"-"+str1[1]+"-"+str1[0];
		String tDate=str2[2]+"-"+str2[1]+"-"+str2[0];
		
		System.out.println(frmDate);
		System.out.println(tDate);
		
		
		int sdoLength=sdoCode.length();

		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		Date d1=new Date();
		String month=sdf.format(d1);
		
		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

	
System.out.println("******getEventDetails**** : "+fromDate+ " : "+toDate);
		
		String mainQuery="SELECT meter_number,(SELECT DISTINCT m3.ACCNO FROM meter_data.master m3 CROSS JOIN meter_data.metermaster m4\n" +
				"WHERE m3.accno = m4.accno AND m4.metrno= e.meter_number LIMIT 1) AS ACCNO,(SELECT DISTINCT m5. NAME FROM\n" +
				"meter_data.MASTER m5 CROSS JOIN meter_data.METERMASTER m6 WHERE m5.ACCNO = m6.ACCNO AND m6.METRNO = e.meter_number\n" +
				"LIMIT 1) AS NAME,e.event_code,(SELECT DISTINCT m3.division FROM meter_data.MASTER m3 CROSS JOIN meter_data.METERMASTER m4\n" +
				"WHERE m3.ACCNO = m4.ACCNO AND m4.METRNO = e.meter_number LIMIT 1) AS division,\n" +
				"(SELECT DISTINCT m3.circle FROM meter_data.MASTER m3 CROSS JOIN meter_data.METERMASTER m4 WHERE m3.ACCNO = m4.ACCNO\n" +
				"AND m4.METRNO = e.meter_number LIMIT 1) AS circle,em.event,to_char(e.event_time,'yyyy-MM-dd HH24:MI:SS') event_time,e.v_r,e.v_y,e.v_b,e.i_r,e.i_y,e.i_b,e.pf_r,e.pf_y,e.pf_b,\n" +
				"(SELECT DISTINCT M .CONTRACTDEMAND FROM meter_data.MASTER M CROSS JOIN meter_data.METERMASTER mm WHERE M .ACCNO = mm.ACCNO\n" +
				"AND mm.METRNO = e.meter_number LIMIT 1) AS CONTRACTDEMAND,e.kwh FROM meter_data.events e CROSS JOIN meter_data.EVENT_MASTER em\n" +
				"WHERE e.event_code=cast(em.event_code AS TEXT)AND (to_char( e.event_time, 'DD-MM-YYYY') BETWEEN '"+frmDate+"' AND '"+tDate+"') AND e.event_code='"+category+"' ORDER BY meter_number";
			sout(mainQuery);
			List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
			for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
				Object[] obj=(Object[]) iterator.next();
				Map<String, Object> map=new HashMap<String, Object>();

				if(obj[0]!=null){
					map.put("meter_number",(obj[0]==null)?"0" :obj[0]);
					map.put("accno",(obj[1]==null)?"0" :obj[1]);
					map.put("name",(obj[2]==null)?"0" :obj[2]);
					map.put("event_code",(obj[3]==null)?"0" :obj[3]);
					map.put("division",(obj[4]==null)?"0" :obj[4]);
					map.put("circle",(obj[5]==null)?"0" :obj[5]);
					map.put("event",(obj[6]==null)?"0" :obj[6]);
					map.put("event_time",(obj[7]==null)?"0" :obj[7]);
					
					map.put("v_r",(obj[8]==null)?"0" :obj[8]);
					map.put("v_y",(obj[9]==null)?"0" :obj[9]);
					map.put("v_b",(obj[10]==null)?"0" :obj[10]);
					map.put("i_r",(obj[11]==null)?"0" :obj[11]);
					map.put("i_y",(obj[12]==null)?"0" :obj[12]);
					map.put("i_b",(obj[13]==null)?"0" :obj[13]);
					map.put("pf_r",(obj[14]==null)?"0" :obj[14]);
					map.put("pf_y",(obj[15]==null)?"0" :obj[15]);
					map.put("pf_b",(obj[16]==null)?"0" :obj[16]);
					
					map.put("contractdemand",(obj[17]==null)?"0" :obj[17]);
					map.put("kwh",(obj[18]==null)?"0" :obj[18]);
					result.add(map);
				}
			}

		return result;

	}
	
	
	
	
	@RequestMapping(value = "/mdasSubdivsionList", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object mdasSubdivsionList(@RequestBody String data) throws Exception {
		sout("Hitting.. mdasSubdivsionList");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION
		

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		
		String mainQuery="";
		/*	mainQuery="SELECT D.subdivision,COALESCE (D.total,0)as total,COALESCE (D.active, 0) as active, COALESCE (C.uploaded, 0) AS uploaded,COALESCE (D.lastactive,0) as lastactive FROM ( SELECT A.subdivision,A.total, B.active,B.lastactive FROM ( SELECT subdivision,COUNT (*) as total FROM meter_data.master_main  GROUP BY subdivision) AS A LEFT JOIN (  SELECT X.subdivision,X.active,Y.lastactive FROM( SELECT subdivision, count(*) AS active FROM meter_data.master_main a,(SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number)b WHERE a.mtrno=b.meter_number   GROUP BY	subdivision) X LEFT JOIN ( SELECT mm.subdivision, COUNT (DISTINCT mc.meter_number) as lastactive FROM meter_data.modem_communication mc, meter_data.master_main mm WHERE mc.meter_number = mm.mtrno  AND mc. DATE = (CURRENT_DATE - 1)  GROUP BY mm.subdivision )Y ON X.subdivision=Y.subdivision  ) B ON A.subdivision=B.subdivision ) D LEFT JOIN ( SELECT mm. subdivision, COUNT ( CASE WHEN mstat.create_status = 1 THEN 1 END) AS uploaded FROM meter_data.xml_upload_status mstat, meter_data.master_main mm WHERE mm.mtrno = mstat.meter_number  AND mstat.file_date = (CURRENT_DATE - 1) GROUP BY mm. subdivision ) C ON D.subdivision=C.subdivision;\r\n" + 
					"";
		*/	
			
	 		 mainQuery="SELECT D.subdivision,COALESCE (D.total,0)as total,COALESCE (D.active, 0) as active, COALESCE (C.uploaded, 0) AS uploaded,COALESCE (D.lastactive,0) as lastactive FROM ( SELECT A.subdivision,A.total, B.active,B.lastactive FROM ( SELECT subdivision,COUNT (*) as total FROM meter_data.master_main  GROUP BY subdivision) AS A LEFT JOIN (  SELECT X.subdivision,X.active,Y.lastactive FROM( SELECT subdivision, count(*) AS active FROM meter_data.master_main a,(SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number)b WHERE a.mtrno=b.meter_number   GROUP BY	subdivision) X LEFT JOIN ( SELECT mm.subdivision, COUNT (DISTINCT mc.meter_number) as lastactive FROM meter_data.modem_communication mc, meter_data.master_main mm WHERE mc.meter_number = mm.mtrno  AND mc. DATE = (CURRENT_DATE - 1)  GROUP BY mm.subdivision )Y ON X.subdivision=Y.subdivision  ) B ON A.subdivision=B.subdivision ) D LEFT JOIN ( SELECT mm. subdivision, COUNT ( CASE WHEN mstat.create_status = 1 THEN 1 END) AS uploaded FROM meter_data.xml_upload_status mstat, meter_data.master_main mm WHERE mm.mtrno = mstat.meter_number  AND mstat.file_date = (CURRENT_DATE - 1) GROUP BY mm. subdivision ) C ON D.subdivision=C.subdivision;";
	 		 sout(mainQuery);
		
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		System.err.println(list.size());
		System.err.println(list.isEmpty());
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				/*map.put("subdiv",(obj[0]==null)?"0" :obj[0]);
				map.put("tot",(obj[1]==null)?"0" :obj[1]);
				map.put("act",(obj[2]==null)?"0" :obj[2]);
				map.put("upload",(obj[3]==null)?"0" :obj[3]);
				map.put("inact",(obj[4]==null)?"0" :obj[4]);*/
				
				
				
				String location=(null!=obj[0])?obj[0].toString():"-";
				String total=(null!=obj[1])?obj[1].toString():"0";
				String active=(null!=obj[2])?obj[2].toString():"0";
				String uploaded=(null!=obj[3])?obj[3].toString():"0";
				String lastActive=(null!=obj[4])?obj[4].toString():"0";
				
				
				int inactive=Integer.parseInt(total)-Integer.parseInt(active);
				int notUploaded=Integer.parseInt(lastActive)-Integer.parseInt(uploaded);
				int activePercentage=Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(active)/Double.parseDouble(total)*100)));
				int uploadPercentage=Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(uploaded)/Double.parseDouble(total)*100)));
				
				System.out.println(location+" "+total+" "+active+" "+uploaded+" "+inactive+" "+notUploaded+" "+activePercentage+" "+uploadPercentage);
				
				map.put("location",location);
				map.put("total",total);
				map.put("active",active);
				map.put("uploaded",uploaded);
				map.put("lastActive",lastActive);
				map.put("inactive",inactive);
				map.put("notUploaded",notUploaded);
				map.put("activePercentage",activePercentage);
				map.put("uploadPercentage",uploadPercentage);
				result.add(map);
			}
		}

		return result;
	}
	
	
	

	@RequestMapping(value = "/mdasdashboard", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object mdasdashboard(@RequestBody String data) throws Exception {
		sout("Hitting.. getmeterdatacommunication");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		
		try {

		
		String mainQuery="";
			mainQuery="SELECT  COUNT (accno) AS mtrCOUNT,COUNT (DISTINCT zone) AS ZONECOUNT, COUNT (DISTINCT circle) AS CIRCLECOUNT, COUNT (DISTINCT division) AS DIVISIONCOUNT, COUNT (DISTINCT subdivision) AS SUBDIVISIONCOUNT, COUNT (DISTINCT substation) AS SUBSTATIONCOUNT FROM meter_data.master_main" + 
					"";
	sout(mainQuery);
		
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				map.put("totalMeters",(obj[0]==null)?"0" :obj[0]);
				map.put("zonecount",(obj[1]==null)?"0" :obj[1]);
				map.put("circlecount",(obj[2]==null)?"0" :obj[2]);
				map.put("divisioncount",(obj[3]==null)?"0" :obj[3]);
				map.put("subdivisioncount",(obj[4]==null)?"0" :obj[4]);
				map.put("substationcount",(obj[5]==null)?"0" :obj[5]);
				result.add(map);
			}
		}

		
		String secondQuery="";
		secondQuery="SELECT( SELECT count(*) FROM meter_data.master_main a, ( SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number)b WHERE a.mtrno=b.meter_number   ) AS comm, ( SELECT count(*) FROM meter_data.master_main a LEFT JOIN ( SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number )b ON a.mtrno=b.meter_number  WHERE b.meter_number is NULL  ) notcomm, ( SELECT COUNT (*) FROM meter_data.master_main WHERE mtr_change_date = to_char( (CURRENT_DATE - 1), 'YYYY-MM-DD' )  ) AS mtrchng, ( SELECT count(*) FROM meter_data.master_main a, ( SELECT DISTINCT meter_number from meter_data.modem_communication WHERE date=CURRENT_DATE GROUP BY meter_number )b WHERE a.mtrno=b.meter_number  ) AS active \r\n";
sout(secondQuery);
	
	List<Object[]> list2=entityManagerNew.createNativeQuery(secondQuery).getResultList();
	for(Iterator<?> iterator=list2.iterator();iterator.hasNext();){
		Object[] obj=(Object[]) iterator.next();
		Map<String, Object> map=new HashMap<String, Object>();

		if(obj[0]!=null){
			map.put("commun",(obj[0]==null)?"0" :obj[0]);
			map.put("notcomm",(obj[1]==null)?"0" :obj[1]);
			map.put("mtrchng",(obj[2]==null)?"0" :obj[2]);
			map.put("active",(obj[3]==null)?"0" :obj[3]);
			result.add(map);
		}
	}

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	@RequestMapping(value = "/mdascommuniactionStatus", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object mdascommuniactionStatus(@RequestBody String data) throws Exception {
		sout("Hitting.. getmeterdatacommunication");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		
		String mainQuery="SELECT circle,division,subdivision, \"count\"(*),\n" +
				"\"count\"(CASE WHEN c.meter_number is not null then 1 END) as active,\n" +
				"\"count\"(CASE WHEN c.meter_number is null then 1 END) as inactive,\n" +
				"\"count\"(CASE WHEN c.difhr>24 AND c.ldate is not NULL AND c.difhr<=120 THEN 1 END) as inc24h,\n" +
				"\"count\"(CASE WHEN c.difhr>120 AND c.ldate is not NULL AND c.difhr<=240 THEN 1 END) as inc5d,\n" +
				"\"count\"(CASE WHEN c.difhr>240 AND c.ldate is not NULL AND c.difhr<=480 THEN 1 END) as inc10d,\n" +
				"\"count\"(CASE WHEN c.difhr>480 AND c.ldate is not NULL AND c.difhr<=720 THEN 1 END) as inc20d,\n" +
				"\"count\"(CASE WHEN c.difhr>720  THEN 1 END) as inc30d \n" +
				"from meter_data.master_main m LEFT JOIN\n" +
				"(\n" +
				"SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
				"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
				"  FROM meter_data.modem_communication GROUP BY meter_number\n" +
				") c on m.mtrno=c.meter_number\n" +
				"GROUP BY circle,division,subdivision ORDER BY circle,division,subdivision";
		sout(mainQuery);
		try {
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		System.err.println(list.size());
		System.err.println(list.isEmpty());
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				map.put("circle",(obj[0]==null)?"0" :obj[0].toString());
				map.put("division",(obj[1]==null)?"0" :obj[1].toString());
				map.put("subdivision",(obj[2]==null)?"0" :obj[2].toString());
				map.put("totconsumer",(obj[3]==null)?"0" :obj[3].toString());
				map.put("actmtr",(obj[4]==null)?"0" :obj[4].toString());
				map.put("totinactmtr",(obj[5]==null)?"0" :obj[5].toString());
				map.put("inact24",(obj[6]==null)?"0" :obj[6].toString());
				map.put("inact5",(obj[7]==null)?"0" :obj[7].toString());
				map.put("inact10",(obj[8]==null)?"0" :obj[8].toString());
				map.put("inact20",(obj[9]==null)?"0" :obj[9].toString());
				map.put("inact30",(obj[10]==null)?"0" :obj[10].toString());
				result.add(map);
			}
		}

		return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return result;
		}
	}
	
	
	
	@RequestMapping(value = "/mdmSubdivsionList", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object mdmSubdivsionList(@RequestBody String data) throws Exception {
		sout("Hitting.. getmeterdatacommunication");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();
		
		SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
		
		 SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday=dateFormat.format(cal.getTime());
		cal.add(Calendar.MONTH, -1);
		String billmonth = sdfBillDate.format(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		String billmonth1 = sdfBillDate.format(cal.getTime());
		Date d1=new Date();
		String today=dateFormat.format(d1);
		String today1=sdf.format(d1);
		System.out.println("today--"+today);

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		
		String mainQuery="";
		mainQuery="SELECT A.SUBDIV AS sdoname,A.SUBDIV, coalesce (A.TOTAL,0)TOTAL,coalesce (B.ACTIVE,0)ACTIVE,(coalesce( A.TOTAL,0)-coalesce (B.ACTIVE,0)) AS BALANCE  FROM" + 
				"(select subdiv,count(subdiv) AS TOTAL from meter_data.metermaster where rdngmonth='"+billmonth1+"' GROUP BY subdiv)a" + 
				"left join " + 
				"(select subdiv,count(subdiv)AS ACTIVE from meter_data.metermaster where metrno in(" + 
				"select DISTINCT meter_number from meter_data.load_survey where to_char(read_time, 'YYYY-MM-dd')='"+today1+"') and rdngmonth='"+billmonth1+"' GROUP BY subdiv)b" + 
				"ON A.SUBDIV=B.SUBDIV  " + 
				"";
	sout(mainQuery);
		
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		System.err.println(list.size());
		System.err.println(list.isEmpty());
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				map.put("sdoname",(obj[0]==null)?"0" :obj[0]);
				map.put("subdiv",(obj[1]==null)?"0" :obj[1]);
				map.put("TOTAL",(obj[2]==null)?"0" :obj[2]);
				map.put("active",(obj[3]==null)?"0" :obj[3]);
				map.put("balance",(obj[4]==null)?"0" :obj[4]);
				result.add(map);
			}
		}

		
/*		String secondQuery="";
		secondQuery="SELECT D.subdivision,COALESCE (D.total,0)as total,COALESCE (D.active, 0) as active, COALESCE (C.uploaded, 0) AS uploaded,COALESCE (D.lastactive,0) as lastactive FROM ( SELECT A.subdivision,A.total, B.active,B.lastactive FROM ( SELECT subdivision,COUNT (*) as total FROM meter_data.master_main  GROUP BY subdivision) AS A LEFT JOIN (  SELECT X.subdivision,X.active,Y.lastactive FROM( SELECT subdivision, count(*) AS active FROM meter_data.master_main a,(SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number)b WHERE a.mtrno=b.meter_number   GROUP BY	subdivision) X LEFT JOIN ( SELECT mm.subdivision, COUNT (DISTINCT mc.meter_number) as lastactive FROM meter_data.modem_communication mc, meter_data.master_main mm WHERE mc.meter_number = mm.mtrno  AND mc. DATE = (CURRENT_DATE - 1)  GROUP BY mm.subdivision )Y ON X.subdivision=Y.subdivision  ) B ON A.subdivision=B.subdivision ) D LEFT JOIN ( SELECT mm. subdivision, COUNT ( CASE WHEN mstat.create_status = 1 THEN 1 END) AS uploaded FROM meter_data.xml_upload_status mstat, meter_data.master_main mm WHERE mm.mtrno = mstat.meter_number  AND mstat.file_date = (CURRENT_DATE - 1) GROUP BY mm. subdivision ) C ON D.subdivision=C.subdivision;\r\n" + 
				"";
sout(secondQuery);*/


		return result;
	}
	
	
	
	@RequestMapping(value = "/mdmdashboard", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object mdmdashboard(@RequestBody String data) throws Exception {
		sout("Hitting.. getmeterdatacommunication");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();

	SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		
		String billmonth=sdfBillDate.format(new Date());
		
		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		
		try {

	
		String mainQuery="";
		mainQuery="SELECT count(distinct metrno) FROM meter_data.metermaster WHERE rdngmonth='"+billmonth+"' and metrno in(SELECT DISTINCT meter_number \n" +
  			  "FROM meter_data.load_survey WHERE to_char(read_time,'yyyyMM')='"+billmonth+"')";
	sout(mainQuery);
		
	
	
		Integer activecount=((BigInteger)entityManagerNew.createNativeQuery(mainQuery).getSingleResult()).intValue();
		
		Long totalConsumers = masterService.FindTotalConsumerCount();
			
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("totalConsumers",(totalConsumers==null)?"0" :totalConsumers);
				map.put("activecount",(activecount==null)?"0" :activecount);
				map.put("inactive", totalConsumers-activecount);
	

result.add(map);

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/mdminstalitionDetails", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object mdminstalitionDetails(@RequestBody String data) throws Exception {
		sout("Hitting.. getmeterdatacommunication");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();
		
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
		Date d1=new Date();
		String today=sdf.format(d1);
		SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		
		String billmonth=sdfBillDate.format(new Date());

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}
		
			 today=sdf.format(new Date());

		

		String mainQuery="SELECT A.SUBDIV AS sdoname,A.SUBDIV, coalesce (A.TOTAL,0)TOTAL,coalesce (B.ACTIVE,0)ACTIVE,(coalesce( A.TOTAL,0)-coalesce (B.ACTIVE,0)) AS BALANCE  FROM\n" +
				 "(select subdiv,count(subdiv) AS TOTAL from meter_data.metermaster where rdngmonth='"+billmonth+"' GROUP BY subdiv)a\n" +
				 "left join \n" +
				 "(select subdiv,count(subdiv)AS ACTIVE from meter_data.metermaster where metrno in(\n" +
				 "select DISTINCT meter_number from meter_data.load_survey where to_char(read_time, 'YYYY-MM-dd')='"+today+"') and rdngmonth='"+billmonth+"' GROUP BY subdiv)b\n" +
				 "ON A.SUBDIV=B.SUBDIV  ";
	sout(mainQuery);
		
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		System.err.println(list.size());
		System.err.println(list.isEmpty());
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				map.put("sdoname",(obj[0]==null)?"0" :obj[0].toString());
				map.put("subdiv",(obj[1]==null)?"0" :obj[1].toString());
				map.put("TOTAL",(obj[2]==null)?"0" :obj[2].toString());
				map.put("active",(obj[3]==null)?"0" :obj[3].toString());
				map.put("balance",(obj[4]==null)?"0" :obj[4].toString());
				result.add(map);
			}
		}

		
/*		String secondQuery="";
		secondQuery="SELECT D.subdivision,COALESCE (D.total,0)as total,COALESCE (D.active, 0) as active, COALESCE (C.uploaded, 0) AS uploaded,COALESCE (D.lastactive,0) as lastactive FROM ( SELECT A.subdivision,A.total, B.active,B.lastactive FROM ( SELECT subdivision,COUNT (*) as total FROM meter_data.master_main  GROUP BY subdivision) AS A LEFT JOIN (  SELECT X.subdivision,X.active,Y.lastactive FROM( SELECT subdivision, count(*) AS active FROM meter_data.master_main a,(SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number)b WHERE a.mtrno=b.meter_number   GROUP BY	subdivision) X LEFT JOIN ( SELECT mm.subdivision, COUNT (DISTINCT mc.meter_number) as lastactive FROM meter_data.modem_communication mc, meter_data.master_main mm WHERE mc.meter_number = mm.mtrno  AND mc. DATE = (CURRENT_DATE - 1)  GROUP BY mm.subdivision )Y ON X.subdivision=Y.subdivision  ) B ON A.subdivision=B.subdivision ) D LEFT JOIN ( SELECT mm. subdivision, COUNT ( CASE WHEN mstat.create_status = 1 THEN 1 END) AS uploaded FROM meter_data.xml_upload_status mstat, meter_data.master_main mm WHERE mm.mtrno = mstat.meter_number  AND mstat.file_date = (CURRENT_DATE - 1) GROUP BY mm. subdivision ) C ON D.subdivision=C.subdivision;\r\n" + 
				"";
sout(secondQuery);*/


		return result;
	}
	
	
	
	@RequestMapping(value = "/mdmtamper", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object mdmtamper(@RequestBody String data) throws Exception {
		sout("Hitting.. getmeterdatacommunication");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();
		
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
		Date d1=new Date();
		String today=sdf.format(d1);
		SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		
		String billmonth=sdfBillDate.format(new Date());

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}
		
			 today=sdf.format(new Date());

		

			String mainQuery="SELECT evm.event,count(DISTINCT e.meter_number) from meter_data.events e,meter_data.event_master evm \n" +
						"WHERE evm.event_code=to_number(e.event_code,'99999')  AND evm.event_code in ('101','102','301','302')\n" +
						"GROUP BY e.event_code,evm.event ORDER BY e.event_code";
				
	sout(mainQuery);
		
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		System.err.println(list.size());
		System.err.println(list.isEmpty());
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				map.put("evnt",(obj[0]==null)?"0" :obj[0].toString());
				map.put("evtct",(obj[1]==null)?"0" :obj[1].toString());
				result.add(map);
			}
		}




		return result;
	}
	
	
	
	
   
    
	
	@RequestMapping(value = "/getCircleWiseMDM", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object getCircleWiseMDM(@RequestBody String data) throws Exception {
		sout("Hitting.. getEventList");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday=dateFormat.format(cal.getTime());
		cal.add(Calendar.MONTH, -1);
		String billmonth = sdfBillDate.format(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		String billmonth1 = sdfBillDate.format(cal.getTime());
		Date d1=new Date();
		String today=dateFormat.format(d1);
		System.out.println("today--"+today);
		
		 String totalCircles = "SELECT DISTINCT(M.circle) FROM meter_data.master M ";
			
		    List<String> totalCircle= entityManagerNew.createNativeQuery(totalCircles).getResultList();
		    for (String circle : totalCircle) {
		    	 Map<String, Object> map=new HashMap<String,Object>();
		    	 
		    	// System.out.println(circle);
		    	// String circleMeterCount =  "SELECT count(*) as total_meters FROM meter_data.master M INNER JOIN meter_data.metermaster MM ON M.accno = MM.accno WHERE   M.circle='"+circle+"' AND  MM.rdngmonth='"+billmonth1+"' GROUP BY  M.circle";
		    	 String circleMeterCount =  "SELECT count(*) as total_meters FROM  meter_data.metermaster MM  WHERE \n" +
		    			 "  MM.circle='"+circle+"' AND  MM.rdngmonth='"+billmonth1+"' GROUP BY  MM.circle";
		    	 int  meterCount= Integer.parseInt(String.valueOf(entityManagerNew.createNativeQuery(circleMeterCount).getSingleResult()));
		    	// System.err.println("meterCount=="+circleMeterCount+ "  --"+meterCount);
		    	 
		    	 
		    	 String uploadedCount = "SELECT count(*) as total_meters FROM meter_data.master M INNER JOIN meter_data.metermaster MM ON M.accno = MM.accno \n" +
		    			 "WHERE   M.circle='"+circle+"' AND  MM.rdngmonth='"+billmonth1+"' AND MM.metrno IN(\n" +
		    			 " SELECT DISTINCT meter_number FROM meter_data.load_survey WHERE to_char(read_time,'dd-MM-YYYY')='"+today+"'\n" +
		    			 ")";
		    	
		    	 
		    	// String uploadedCount=
				 System.err.println("circle wise upload count--"+uploadedCount);
				int  uploadCountData= Integer.parseInt(String.valueOf(consumerMasterService.getCustomEntityManager("postgresMdas").createNativeQuery(uploadedCount).getSingleResult()));
				// System.err.println("uploadedCount=="+uploadedCount);
				
				map.put("uploadCount", meterCount);
		    	map.put("acquired",uploadCountData );
		    	map.put("pending",meterCount- uploadCountData);
		    	map.put("zone", circle);
		    	
		    	result.add(map);
			}
		    
		    return result;
		    
	}
	
	
	@RequestMapping(value = "/getEventList", method = { RequestMethod.POST, RequestMethod.GET }, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody Object getEventList(@RequestBody String data) throws Exception {
		sout("Hitting.. getEventList");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sdoCode = new JSONObject(data).getString("sdoCode");
		int sdoLength=sdoCode.length();

		String subLocationTitle="";// FOR GROUP BY SUB LOCATION

		switch (sdoLength) {
		case 0://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 1://CORPORTATE LEVEL
			subLocationTitle="CIRCLE";
			break;
		case 4://CIRCLE LEVEL
			subLocationTitle="DIVISION";

			break;
		case 5://DIVISION LEVEL
			subLocationTitle="SUBDIVISION";

			break;
		case 6://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;

		case 7://SUB-DIVISION LEVEL
			subLocationTitle="SECTION";
			break;
		}

		
		String mainQuery="SELECT event_code,event from meter_data.event_master ORDER BY event";
		sout(mainQuery);
		List<Object[]> list=entityManagerNew.createNativeQuery(mainQuery).getResultList();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();

			if(obj[0]!=null){
				map.put("event_code",(obj[0]==null)?"0" :obj[0]);
				map.put("event",(obj[1]==null)?"0" :obj[1]);
				
				result.add(map);
			}
		}

		return result;
	}
	private String getEventDesc(String eCode)
	{
	String  eDesc="";
	
	eDesc=(String) entityManagerNew.createNativeQuery("SELECT event from meter_data.event_master where event_code='"+eCode+"' ").getSingleResult() ;
System.err.println(eDesc);
		return eDesc;
	}
	
	
	private ArrayList<String> billmonths(String frmDate,String tDate)
	{
		System.out.println("inside Daily Load Survey Data==>"+frmDate+"==>"+tDate);
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-01 00:00:00");
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MONTH, 0);
			
			Calendar now1 = Calendar.getInstance();
			now1.add(Calendar.MONTH, -1);
			
			Calendar now2 = Calendar.getInstance();
			now2.add(Calendar.MONTH, -2);
			
			Calendar now3 = Calendar.getInstance();
			now3.add(Calendar.MONTH, -3);
			
			Calendar now4 = Calendar.getInstance();
			now4.add(Calendar.MONTH, -4);
			
			Calendar now5 = Calendar.getInstance();
			now5.add(Calendar.MONTH, -5);

			
			//now.add(Calendar.MONTH, -1);
		   // String presentDate = dateFormat.format(Calendar.getInstance().getTime());
		    String yesterDay = dateFormat.format(now.getTime());
		    System.err.println("the bill months are====>"+dateFormat.format(now.getTime()));
		    ArrayList<String> strlist=new ArrayList<>();
		    strlist.add(dateFormat.format(now.getTime()));
		    strlist.add(dateFormat.format(now1.getTime()));
		    strlist.add(dateFormat.format(now2.getTime()));
		    strlist.add(dateFormat.format(now3.getTime()));
		    strlist.add(dateFormat.format(now4.getTime()));
		    strlist.add(dateFormat.format(now5.getTime()));
		    return strlist;
	}
	
	
	
	
	
	private void sout(String message){
		System.out.println(message);
	}
	
	
	
	
	
	
	/*GATE WAY CODE*/
	
	
	
	@RequestMapping(value="/SMSGatewaySettings",method=RequestMethod.GET)
	public String SMSGatewaySettings(@ModelAttribute("smsGatewaySettings") SmsGateway sms,@ModelAttribute("MessageSettings") MessagingSettings sms2,ModelMap model,HttpServletRequest request)
	{
		System.err.println("In SMSGatewaySettings");
		SmsGateway list = smsGatewayService.findUser();	
		MessagingSettings list1 = messageSettingsService.findUser();	
		model.addAttribute("element", list);
		model.addAttribute("message", list1);
		model.put("results", "notDisplay");
		return "adminSMSGatewaySettings";
	}
	
	@RequestMapping(value="/updateSmsGatewaySettings",method=RequestMethod.POST)
	public String updateSmsGatewaySettings(@ModelAttribute("smsGatewaySettings") SmsGateway sms,@ModelAttribute("MessageSettings") MessagingSettings sms2,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{		
		
		System.err.println("updateSmsGatewaySettings Data");
		try 
		{
			 sms.setLoggedUser((String)request.getSession(true).getAttribute("userName"));
			 smsGatewayService.update(sms);	
			 model.put("results", "SMS Gateway Setting updated successfully");
		} 
		catch (Exception e) {
			model.put("results", "Error while updating SMS Gateway Setting");
			e.printStackTrace();
		}
		SmsGateway list = smsGatewayService.findUser();	
		MessagingSettings list1 = messageSettingsService.findUser();	
		model.addAttribute("element", list);
		model.addAttribute("message", list1);		
		return "adminSMSGatewaySettings";

	} 
	
	@RequestMapping(value="/updateMessageSettings",method=RequestMethod.POST)
	public String updateMessageSettings(@ModelAttribute("smsGatewaySettings") SmsGateway sms1,@ModelAttribute("MessageSettings") MessagingSettings sms,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{	
		System.err.println("updateMessageSettings Data");
		try 
		{
			    sms.setLoggedUser((String)request.getSession(true).getAttribute("username"));
			    messageSettingsService.customupdatemdas(sms);	
			    model.put("results", "SMS Message Setting updated successfully");
		} catch (Exception e) 
		{
			model.put("results", "SMS Message Setting updated successfully");
			e.printStackTrace();
		}
		SmsGateway list = smsGatewayService.findUser();	
		MessagingSettings list1 = messageSettingsService.findUser();	
		model.addAttribute("element", list);
		model.addAttribute("message", list1);
		return "adminSMSGatewaySettings";

	} 
	
	
	@RequestMapping(value="/EmailGatewaySettings",method=RequestMethod.GET)
	public String emailGatewaySettings(@ModelAttribute("emailGatewaySettings") EmailGateway email,@ModelAttribute("MessageSettings") MessagingSettings email1,ModelMap model,HttpServletRequest request)
	{
		System.err.println("In emailGatewaySettings");
		EmailGateway list  = emailGatewayService.findUser();
		MessagingSettings list1 = messageSettingsService.findUser();	
		model.addAttribute("element", list);
		model.addAttribute("message", list1);
		model.put("results","notDisplay");
		return "adminEmailGatewaySettings";
	}
	
	@RequestMapping(value="/updateEmailGatewaySettings",method=RequestMethod.POST)
	public String updateEmailGatewaySettings(@ModelAttribute("emailGatewaySettings") EmailGateway email,BindingResult bingingResult,@ModelAttribute("MessageSettings") MessagingSettings email1,ModelMap model,HttpServletRequest request)
	{	
		System.err.println(email.getId());
		System.err.println(email.getSmtpHost());
		System.err.println("updateEmailGatewaySettings Data");
		try {
			email.setLoggedUser((String)request.getSession(true).getAttribute("username"));
		    emailGatewayService.customupdatemdas(email);	
		    model.put("results", "Email Gateway Settings updated Succesfully");
		} catch (Exception e) {
			 model.put("results", "Email Gateway Settings updated Succesfully");
			e.printStackTrace();
		}	     
	    EmailGateway list  = emailGatewayService.findUser();
		MessagingSettings list1 = messageSettingsService.findUser();	
		model.addAttribute("element", list);
		model.addAttribute("message", list1);
		return "adminEmailGatewaySettings";

	} 
	
	@RequestMapping(value="/updateEmailMessageSettings",method=RequestMethod.POST)
	public String updateEmailMessageSettings(@ModelAttribute("MessageSettings") MessagingSettings sms,BindingResult bingingResult,@ModelAttribute("emailGatewaySettings") EmailGateway email,ModelMap model,HttpServletRequest request)
	{		
		System.err.println("updateMessageSettings Data");
		try {
			 sms.setLoggedUser((String)request.getSession(true).getAttribute("userName"));	  
			  messageSettingsService.customupdatemdas(sms);
			  model.put("results", "Email Message Settings updated Succesfully");
		} catch (Exception e) {
			model.put("results", "Email Message Settings updated Succesfully");
			e.printStackTrace();
		}	   	  		
	    EmailGateway list  = emailGatewayService.findUser();
		MessagingSettings list1 = messageSettingsService.findUser();	
		model.addAttribute("element", list);
		model.addAttribute("message", list1);
		return "adminEmailGatewaySettings";
	} 
	
	
	
	@RequestMapping(value="/networkGateWay",method=RequestMethod.GET)
	public String networkgateway(@ModelAttribute("networkgateway") NetworkPathGateWay networkgateway,ModelMap model,HttpServletRequest request)
	{
		try {
		System.err.println("In networkgateway");
		NetworkPathGateWay list  = networkgatewayservice.findUser();
		model.addAttribute("element", list);
		model.put("results","notDisplay");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "adminNetworkGateWay";
	}
	
	
	@RequestMapping(value="/updateadminNetworkGateWaySettings",method=RequestMethod.POST)
	public String updateadminNetworkGateWaySettings(@ModelAttribute("networkgateway") NetworkPathGateWay email,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{	
		System.err.println(email.getId());
		System.err.println("updateEmailGatewaySettings Data");
		try {
			email.setLoggedUser((String)request.getSession(true).getAttribute("username"));
			email.setTimestamp(new Date());
			networkgatewayservice.customUpdate(email);	
		    model.put("results", "Network Gateway Settings updated Succesfully");
		} catch (Exception e) {
			 model.put("results", "Network Gateway Settings updated Succesfully");
			e.printStackTrace();
		}	     
		NetworkPathGateWay list  = networkgatewayservice.findUser();
		model.addAttribute("element", list);
		return "adminNetworkGateWay";

	} 
	
}




