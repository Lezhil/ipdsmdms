package com.bcits.serviceImpl;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.AlarmDispatchEntity;
import com.bcits.entity.Alarms;
import com.bcits.entity.ProcessTracker;
import com.bcits.entity.User;
import com.bcits.mdas.controller.AndroidConsumerController;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.VirtualLocation;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.utility.SendModemAlertViaMail;
import com.bcits.service.AlarmDispatchService;
import com.bcits.service.AlarmService;
import com.bcits.service.ProcessTrackerService;
import com.bcits.service.UserService;
@Repository
public class AlarmServiceImpl extends GenericServiceImpl<Alarms> implements AlarmService{

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;
	@Autowired
	private ProcessTrackerService processTrackerService;
	@Autowired
	private AndroidConsumerController androidconsumercontroller;
	@Autowired
	private AlarmDispatchService alarmdispatchService;
	@Autowired
	private UserService userService;
	@Override
	public void deleteRecord(int id) {
		//Optional<Alarms> alarmOptional=find(id);
		//List<Alarms> a=(List<Alarms>) find(id);
		//if(!a.isEmpty()){
		System.err.println(id);
		delete(id);
		//}
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void processEventAlarmData() {
		
		System.out.println("hi.............");
		final String alarm_type="event";
		String mailNotifyStatus="";
		Timestamp timestamp = new Timestamp(new Date().getTime());
		//System.out.println("in cron jobbbbb");
		int processRes=0;
		String msg="";
		/*String sql="select * from meter_data.master_main\n" +
				"SELECT m.accno, m.kno,m.subdivision, q.* FROM meter_data.master_main m, \n" +
				"(\n" +
				"SELECT e.event_code,e.meter_number,e.event_time,\n" +
				"d.location_type,d.location_code,d.location_id,d.priority,d.alarm_name \n" +
				"FROM meter_data.events e,\n" +
				"( SELECT * FROM meter_data.alarm_definition WHERE (events != '' AND events is not NULL) ) d WHERE e.event_code=d.events ORDER BY time_stamp DESC NULLS LAST\n" +
				") q WHERE m.mtrno=q.meter_number";
		String qry="select m.customer_name,location_code,location_type,location_id,CAST(events AS int),priority,alarm_name,entry_date,e.event,email_ids,sms\n" +
				"from meter_data.alarm_definition, meter_data.master_main m, meter_data.event_master e,meter_data.events ev where e.event_code =CAST(events AS int) \n" +
				"AND events !='' \n" +
				"and location_id=m.accno \n" +
				"AND events is NOT NULL  \n" +
				"AND ev.time_stamp > (SELECT last_process_time from meter_data.process_tracker WHERE process_name='event_alarm_generation') and ev.event_code=events ";*/
		// last changed 17102019
		/*
		 * String
		 * qry="select m.customer_name,location_code,location_type,location_id,CAST(events AS int),priority,alarm_name,entry_date,e.event\n"
		 * +
		 * "from meter_data.alarm_definition, meter_data.master_main m, meter_data.event_master e,meter_data.events ev where e.event_code =CAST(events AS int) \n"
		 * + "AND events !='' \n" + "and location_id=m.accno \n" +
		 * "AND events is NOT NULL  \n" +
		 * "AND ev.time_stamp > (SELECT last_process_time from meter_data.process_tracker WHERE process_name='alarm_generation_event') and ev.event_code=events and ev.meter_number=(select mtrno from meter_data.master_main where accno=location_id)"
		 * ;
		 */
		//(SELECT last_process_time from meter_data.process_tracker WHERE process_name='alarm_generation_event')
		String qry= "select  distinct m.customer_name,m.sdocode,location_type,a.location_id,CAST(events AS int),priority,alarm_name,entry_date,e.event,EMAIL_IDS,SMS,town_id\r\n" + 
				"								from meter_data.alarm_definition a, meter_data.master_main m, meter_data.event_master e,meter_data.events ev where e.event_code =CAST(events AS int) AND events !='' and m.customer_name !='' and m.customer_name is not null \r\n" + 
				"								and a.location_id=m.location_id \r\n" + 
				"								AND events is NOT NULL AND ev.event_time > (SELECT last_process_time from meter_data.process_tracker WHERE process_name='alarm_generation_event')\r\n" + 
				"								 and ev.event_code=events and ev.meter_number in (select mtrno from meter_data.master_main m,meter_data.alarm_definition a where m.location_id=a.location_id)";
		String trackQry="UPDATE meter_data.process_tracker SET last_process_time = NOW() WHERE id='1' ;";
		List<?> list=new ArrayList<>();
		try {
			
			list=postgresMdas.createNativeQuery(qry).getResultList();
			processRes=postgresMdas.createNativeQuery(trackQry).executeUpdate();
			//System.out.println("in cron jobbbbb"+list.size()+processRes);
			//System.out.println(qry);
			for(int i=0;i<list.size();i++){
				Object[] li=(Object[]) list.get(i);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			    Date parsedDate = dateFormat.parse(li[7].toString());
			    Timestamp alarm_date = new java.sql.Timestamp(parsedDate.getTime());
				//System.out.println(li[0].toString()+"in"+li[1].toString()+" the"+li[2].toString()+" processing alarm data"+li[3].toString()+"time==="+li[4].toString());
				Alarms alarm=new Alarms();
				alarm.setOfficeId((li[1]==null?"" :li[1].toString()));
				alarm.setLocatioTtype((li[2]==null?"" :li[2].toString()));
				alarm.setLocationCode((li[3]==null?"" :li[3].toString()));
				alarm.setLocationName((li[0]==null?"" :li[0].toString()));
				alarm.setAlarmSetting((li[6]==null?"" :li[6].toString()));
				alarm.setAlarmType(alarm_type);
				alarm.setAlarm_priority((li[5]==null?"" :li[5].toString()));
				alarm.setAlarmName((li[8]==null?"" :li[8].toString()));
				alarm.setAlarm_date(alarm_date);
				alarm.setTownId((li[11]==null?"" :li[11].toString()));
				alarm.setTime_stamp(timestamp);
				
				//alarm.setAlarm_date(li[4].toString());
				
				save(alarm);
				User user = null;
				if(!li[9].toString().isEmpty()){
					System.out.println("in email");
					 user=userService.getDataByEmailId(li[9].toString());
					mailNotifyStatus=sendEmailNotification(li[9].toString(),li[11].toString(),li[2].toString(),li[0].toString(),li[8].toString(),li[5].toString(),alarm_type,li[6].toString(),li[3].toString());
				}
				//System.out.println("in middel====");
				if(!li[10].toString().isEmpty()){
					//System.out.println("in sms");
					//mob,sdo,loctype,locname,alarmname,alarm priority,alarmtype
				// msg=sendSmsNotification(li[10].toString(),li[1].toString(),li[2].toString(),li[3].toString(),li[8].toString(),li[5].toString(),alarm_type);
				}
				AlarmDispatchEntity ad= new AlarmDispatchEntity();
				ad.setOffice_id((li[1]==null?"" :li[1].toString()));
				ad.setLoc_type((li[2]==null?"" :li[2].toString()));
				ad.setLoc_identity((li[3]==null?"" :li[3].toString()));
				ad.setLoc_name((li[0]==null?"" :li[0].toString()));
				ad.setAlarm_setting((li[6]==null?"" :li[6].toString()));
				ad.setAlarm_type(alarm_type);
				ad.setAlarm_name((li[8]==null?"" :li[8].toString()));
				ad.setAlarm_priority((li[5]==null?"" :li[5].toString()));
				ad.setTownId((li[11]==null?"" :li[11].toString()));
				//ad.setEmail(li1[0].toString());
				//ad.setMobile(li1[1].toString());
				ad.setNotify_date(alarm_date);
				ad.setTime_stamp(timestamp);
				if(user!=null) {
				ad.setUser_name(user.getUsername());
				ad.setUser_type("Discom User");
				ad.setUser_identity(user.getUsername());
				}
				ad.setEmail(li[9].toString());
				if("delivered".equalsIgnoreCase(mailNotifyStatus)) {
					ad.setEmail_notification(true);
				}
				else {
					ad.setEmail_notification(false);
					ad.setError_des("Email Server Error");
				}
				try {
					//ObjectMapper map=new ObjectMapper();
					//System.out.println(map.writeValueAsString(map));
					alarmdispatchService.customSave(ad);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public String sendEmailNotification(String to,String sdocode,String loctype,String locname,String alarmname,String priority, String alarm_type,String alarmSetting,String locCode) {
		//to="sowjanya.medisetti@bcits.in";
		String sendermailid = null;
		String sendermailpassword = null;
		String body=null;
		
		System.out.println("alarm ----"+sdocode);
		System.out.println("alarm ----"+loctype);
		System.out.println("alarm ----"+locname);
		System.out.println("alarm ----"+alarmname);
		System.out.println("alarm ----"+priority);
		System.out.println("alarm ----"+alarm_type);
		System.out.println("alarm ----"+alarmSetting);
		System.out.println("alarm ----"+locCode);;
		
		try {
			 String queryString = "select sendermailid,sendermailpassword from meter_data.emailgatewaysettings";
			 List<Object[]> queryUpload = entityManager.createNativeQuery(queryString).getResultList();
			 
			 if(queryUpload.size()>0){
				 sendermailid =(String)queryUpload.get(0)[0];
				 sendermailpassword=(String)queryUpload.get(0)[1];	
				}
			 
			 String cc="msowjanya2014@gmail.com";
				String subject=" Alarm ("+priority+") at "+loctype+"  - "+locname+"";
				String newbody="";
				 body=" <span>Dear Sir,</span><br><br><span>Alarm Details given below:-</span><br><br>\r\n" ; 
				body+=newbody;	
				body+=	"<div style='overflow: scroll;'> <table style='font-size: 9pt;'>\r\n" + 
			     		" <tr>\r\n" + 
			     		"  <th style='text-align: center;'>Circle</th><td>"+sdocode+"</td></tr>\r\n" + 
			     		"  <th >Location Type</th><td>"+loctype+"</td></tr>\r\n" + 
			     		"  <th >Location Identity</th>  <td>"+locCode+"</td></tr>\r\n" + 
			     		"  <th >Location Name</th><td>"+locname+"</td></tr>\r\n" + 
			     		"  <th >Alarm Setting </th><td>"+alarmSetting+"</td></tr>\r\n" +
			     		"  <th >Alarm Type</th><td>"+alarm_type+"</td></tr>\r\n" +
			     		"  <th >Alarm Name</th><td>"+alarmname+"</td></tr>\r\n" +
			     		"  <th >Alarm Priority</th><td>"+priority+"</td></tr>\r\n" +
			     		" </tr>\r\n";
//				body+=" <tr>\r\n" + 
//			     		" <td>"+sdocode+"</td>\r\n" + 
//			     		"  <td>"+loctype+"</td>\r\n" + 
//			     		"  <td>"+locCode+"</td>\r\n" + 
//			     		"  <td>"+locname+"</td>\r\n" + 
//			     		"  <td>"+alarmSetting+"</td>\r\n" + 
//			     		"  <td>"+alarm_type+"</td>\r\n" + 
//			     		"  <td>"+alarmname+"</td>\r\n" + 
//			     		"  <td>"+priority+"</td>\r\n" + 
//			     		" </tr>\r\n";
				body+=" </table></div>\r\n";
		     	
		     	body+="<style> table { border-collapse: collapse; } table, th, td { border: 1px solid black;padding: 0px; } </style>";
		     	body+="<br><br><br><br><br><br><br><br>------------------------------------------------------------------------------------------------------------------<br>"
		     			+ "<p>********  THIS IS AN AUTO GENERATED E-MAIL. PLEASE DO NOT REPLY. ******** </p>";
			
		     	
				new Thread(new SendModemAlertViaMail(subject,body,to,cc,sendermailid,sendermailpassword)).run();
				return "delivered";
				}catch(Exception e) {
			       e.printStackTrace();
			       return "fail";

		}
		
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void processValidationsAlarmData() {
		// TODO Auto-generated method stub
		
		final String alarm_type="validation_failure";
		Timestamp timestamp = new Timestamp(new Date().getTime());
		int processRes=0;
		
		String qry="select m.customer_name, a.location_code,v.location_type,a.location_id,a.validations,priority,a.alarm_name,v.createddate,v.v_rulename from meter_data.alarm_definition a,\n" +
				"meter_data.validation_process_rpt v,meter_data.master_main m\n" +
				"where validations !='' and a.location_id=m.location_id AND validations is NOT NULL and validations=v.v_rule_id AND v.createddate > (SELECT last_process_time from meter_data.process_tracker where process_name='alarm_generation_validation') and v.v_rule_id=a.validations and v.location_id=a.location_id  ORDER BY v.createddate";
		String trackQry="UPDATE meter_data.process_tracker SET last_process_time = NOW() WHERE id='2' ;";

		List<?> list=new ArrayList<>();
		try {
			list=postgresMdas.createNativeQuery(qry).getResultList();
			processRes=postgresMdas.createNativeQuery(trackQry).executeUpdate();
			//System.out.println(qry);
			for(int i=0;i<list.size();i++){
				Object[] li=(Object[]) list.get(i);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			    Date parsedDate = dateFormat.parse(li[7].toString());
			    Timestamp alarm_date = new java.sql.Timestamp(parsedDate.getTime());
				System.out.println(li[0].toString()+"in"+li[1].toString()+" the"+li[2].toString()+" processing alarm data"+li[3].toString()+"time==="+li[4].toString());
				Alarms alarm=new Alarms();
				alarm.setOfficeId(li[1].toString());
				alarm.setLocatioTtype(li[2].toString());
				alarm.setLocationCode(li[3].toString());
				alarm.setLocationName(li[0].toString());
				alarm.setAlarmSetting(li[6].toString());
				alarm.setAlarmType(alarm_type);
				alarm.setAlarm_priority(li[5].toString());
				alarm.setAlarmName(li[8].toString());
				alarm.setAlarm_date(alarm_date);
				alarm.setTime_stamp(timestamp);
				
				//alarm.setAlarm_date(li[4].toString());
				
				save(alarm);
				//System.out.println("in before save====");
				if(!li[9].toString().isEmpty()){
					System.out.println("in email");
				sendEmailNotification(li[9].toString(),li[1].toString(),li[2].toString(),li[0].toString(),li[8].toString(),li[5].toString(),alarm_type,li[6].toString(),li[3].toString());
				}
				//System.out.println("in middel====");
				if(!li[10].toString().isEmpty()){
					System.out.println("in sms");
					//mob,sdo,loctype,locname,alarmname,alarm priority,alarmtype
				// String msg = sendSmsNotification(li[10].toString(),li[1].toString(),li[2].toString(),li[3].toString(),li[8].toString(),li[5].toString(),alarm_type);
				}
					
					//saving data in dispatch table
					
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		
	}

	

	public String sendSmsNotification(String mobileNum,String sdocode,String loctype,String locname,String alarmname,String priority, String alarm_type) {
		// TODO Auto-generated method stub
		System.out.println("in sms====");
		if(alarmname.length()>20) {
			alarmname=alarmname.substring(0, 17);
		}
		//String message="t"+"->"+"sss"+"->"+"Consumer"+"->"+"12213"+"->"+"Test"+"->"+"M";
		//SDOCODE<12156> ML: <Consumer> <8606411111> <Event> <Voltage Missing> Priority:<M>
		//String message=sdocode+"->"+loctype+"->"+locname+"->"+alarmname+"->"+priority.substring(0).toUpperCase();
		//mob,sdo,loctype,locname,alarmname,alarm priority,alarmtype
		String message="SDOCODE <"+sdocode+"> ML: <"+loctype+"> <"+locname+"> <"+alarm_type+"> <"+alarmname+"> Priority:<"+priority.substring(0,1).toUpperCase()+">";
		System.err.println(message);
		String result=androidconsumercontroller.sendSMSGUPSHUP(message, mobileNum);
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getAmiCircleByZone(String zone) {
		if("All".equalsIgnoreCase(zone)){
			zone="%";
		}
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT circle from meter_data.amilocation WHERE zone like '" + zone + "'";
			// String sql="SELECT DISTINCT circle from meter_data.master WHERE zone like
			// '"+zone+"' and circle not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getAmiDivisionByCircle(String zone, String circle) {
		if("All".equalsIgnoreCase(zone)){
			zone="%";
		}if("All".equalsIgnoreCase(circle)){
			circle="%";
		}
			List<FeederMasterEntity> list = null;
			try {
				
				String sql = "SELECT DISTINCT division from meter_data.amilocation WHERE zone like '" + zone
						+ "' and circle LIKE '" + circle + "'";
				// String sql="SELECT DISTINCT division from meter_data.master WHERE zone like
				// '"+zone+"' and "
				// + "circle LIKE '"+circle+"' and division not like ''";
				list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				System.out.println(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
	}

	@Override
	public List<?> getSubdivByDivisionByCircleAmi(String zone, String circle,
			String division) {
		// TODO Auto-generated method stub
		List list = null;
		
		try {
			if(zone.equalsIgnoreCase("All"))
			{
				zone="%";
			}
			if(circle.equalsIgnoreCase("All"))
			{
				circle="%";
			}
			if(division.equalsIgnoreCase("All"))
			{
				division="%";
			}
		      String sql="SELECT DISTINCT subdivision from meter_data.amilocation WHERE zone like '"+zone+"'"
		      		+ " and circle like '"+circle+"' and division like '"+division+"'";
		     
			System.err.println("sdoname--"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		
			
			//list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean findMeterLocation(String mtrNum, String locationType) {
		String sql="";
		List<?> mtrLoc=new ArrayList<>();
		if("Consumer".equalsIgnoreCase(locationType)) {
			sql="select * from meter_data.consumermaster where meterno='"+mtrNum+"'";
			
		}
		else if("Dt".equalsIgnoreCase(locationType)) {
			sql="select * from meter_data.dtdetails where meterno='"+mtrNum+"'";
		}
		else if("Feeder".equalsIgnoreCase(locationType)) {
			sql="select * from meter_data.feederdetails where meterno='"+mtrNum+"' and CAST(crossfdr AS varchar)='0'";
		}
		else if("boundary".equalsIgnoreCase(locationType)) {
			sql="select * from meter_data.feederdetails where meterno='"+mtrNum+"' and CAST(crossfdr AS varchar)='1'";
		}
		System.err.println("  SQL+++++>"+sql);
		mtrLoc=postgresMdas.createNativeQuery(sql).getResultList();
		if(!mtrLoc.isEmpty()){
			return true;
		}
		return false;
	}

	@Override
	public List<String> getAccountDetails(String officeType, String officeName) {
		List<String> locId=new ArrayList<>();
		String subQry="";
		if("region".equalsIgnoreCase(officeName)) {
			subQry=" where zone like '"+officeType+"'";
		}
		else if("cirle".equalsIgnoreCase(officeName)) {
			subQry=" where circle like '"+officeType+"'";
		}
		else {
			subQry="";
		}
		try {
			String qry="SELECT distinct location_id  from meter_data.master_main "+subQry+"";
			locId=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locId;
	}
	
	@Override
	public List<String> getMtrnos(String officeType, String officeName) {
		List<String> locId=new ArrayList<>();
		String subQry="";
		if("region".equalsIgnoreCase(officeName)) {
			subQry=" where zone like '"+officeType+"'";
		}
		else if("cirle".equalsIgnoreCase(officeName)) {
			subQry=" where circle like '"+officeType+"'";
		}
		else {
			subQry="";
		}
		try {
			String qry="SELECT mtrno  from meter_data.master_main "+subQry+"";
			locId=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locId;
	}

	@Override
	public List<String> getAlarmName(String officeType, String officeName) {
		List<String> alarmNames=new ArrayList<>();
		String subQry="";
		if("region".equalsIgnoreCase(officeName)) {
			subQry=" where zone like '"+officeType+"'";
		}
		else if("cirle".equalsIgnoreCase(officeName)) {
			subQry=" where circle like '"+officeType+"'";
		}
		else {
			subQry="";
		}
		try {
			String qry="select distinct alarm_name FROM meter_data.alarm_definition where location_id in (select location_id from meter_data.master_main "+subQry+")";
			alarmNames=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alarmNames;
	}

	
	

}
