package com.bcits.serviceImpl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;






import com.bcits.entity.AlarmDefinition;
import com.bcits.service.AlarmDefinitionService;


@Repository
public class AlarmDefinitionServiceImpl extends GenericServiceImpl<AlarmDefinition> implements AlarmDefinitionService
{
	@Override
	public List<AlarmDefinition> getAlarmEntityByAccnum(String accNum){
		String sql="select * from meter_data.alarm_definition WHERE location_id='"+accNum+"'";
		List<AlarmDefinition> list=new ArrayList<>();
		try {
			
			list=postgresMdas.createNativeQuery(sql, AlarmDefinition.class).getResultList();
			//System.out.println("size==="+list.size());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
		
	}
	
	@Override
	public List<AlarmDefinition> getAlarmEntityBylocationCode(String locType){
		String sql="select * from meter_data.alarm_definition WHERE location_type='"+locType+"'";
		List<AlarmDefinition> list=new ArrayList<>();
		try {
			
			list=postgresMdas.createNativeQuery(sql, AlarmDefinition.class).getResultList();
			System.out.println("size==="+list.size());
			System.out.println(sql);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
		
	}

	@Override
	public List<Object[]> dispatchAlarms(String zone, String circle,
			String town,  String loctype, String fromdate,
			String todate)
			{
		
		List<Object []> list = null;
		String sql ="SELECT town_ipds,loc_type,loc_identity,loc_name,alarm_setting,alarm_name,user_type,user_identity,user_name,email_notification,sms_notification,email,mobile,notify_date,notify_status,error_des FROM(SELECT loc_type,loc_identity,loc_name,alarm_setting,alarm_name,user_type,user_identity,user_name,email_notification,sms_notification,email,mobile,notify_date,notify_status, error_des,time_stamp,town_id  FROM meter_data.alarms_dispatch_details where loc_type like '"+loctype+"' AND (to_char(time_stamp,'YYYY-MM-dd') BETWEEN '"+fromdate+"' and '"+todate+"')\n" +
				")A,\n" +
				"(SELECT zone,circle,division,town_ipds,tp_towncode FROM meter_data.amilocation where  zone like '"+zone+"' AND circle like '"+circle+"' AND town_ipds like '"+town+"' )B\n" +
				"WHERE  A.town_id=b.tp_towncode";
				/*
						 * "SELECT town_ipds,loc_type,loc_identity,loc_name,alarm_setting,alarm_name,user_type,user_identity,user_name,email_notification,sms_notification,email,mobile,notify_date,notify_status,error_des FROM(SELECT loc_type,loc_identity,loc_name,alarm_setting,alarm_name,user_type,user_identity,user_name,email_notification,sms_notification,email,mobile,notify_date,notify_status, error_des,time_stamp  FROM meter_data.alarms_dispatch_details)A,\n"
						 * + "(SELECT zone,circle,division,town_ipds FROM meter_data.amilocation)B\n" +
						 * "WHERE B.zone like '"+zone+"' AND B.circle like '"
						 * +circle+"' AND B.town_ipds like '"+town+"'  AND A.loc_type like '"
						 * +loctype+"' AND (to_char(A.time_stamp,'YYYY-MM-dd') BETWEEN '"
						 * +fromdate+"' and '"+todate+"')";
						 */
		list=postgresMdas.createNativeQuery(sql).getResultList();
		return list;
	}

	@Override
	public List<?> getAlarmDetails(String accno, String mtrno, String radioval) {
		List<?> list=null;
		String qry="";
		try {
			if("accno".equals(radioval)) {
			qry="select p.*,q.town_ipds from \n" +
					" (SELECT m.SUBDIVISION,d.LOCATION_ID,m.CUSTOMER_NAME,m.MTRNO,d.ALARM_NAME FROM METER_DATA.ALARM_DEFINITION d LEFT JOIN METER_DATA.MASTER_MAIN m \n" +
					"ON d.LOCATION_ID=m.location_id WHERE m.location_id = '"+accno+"' GROUP BY m.SUBDIVISION,d.LOCATION_ID,m.CUSTOMER_NAME,m.MTRNO,d.ALARM_NAME)p,\n" +
					"(select town_ipds,subdivision from meter_data.amilocation)q where p.subdivision=q.subdivision";
			}else {
			qry=" select p.*,q.town_ipds from \n" +
					"(SELECT m.SUBDIVISION,d.LOCATION_ID,m.CUSTOMER_NAME,m.MTRNO,d.ALARM_NAME FROM METER_DATA.ALARM_DEFINITION d LEFT JOIN METER_DATA.MASTER_MAIN m \n" +
					"ON d.LOCATION_ID=m.location_id WHERE MTRNO = '"+mtrno+"' GROUP BY m.SUBDIVISION,d.LOCATION_ID,m.CUSTOMER_NAME,m.MTRNO,d.ALARM_NAME)p,\n" +
					"(select town_ipds,subdivision from meter_data.amilocation)q where p.subdivision=q.subdivision";
			}
			list=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<?> getAlarmPriorityDet(String accno) {
	  List<?> list=null;
	  String qry="";
	  try {
		qry="SELECT distinct d.EVENTS,m.EVENT,d.PRIORITY FROM METER_DATA.ALARM_DEFINITION d LEFT JOIN METER_DATA.EVENT_MASTER m \n" +
				"ON cast(d.EVENTS as INTEGER)=m.EVENT_CODE WHERE LOCATION_ID = '"+accno+"'";
		list=postgresMdas.createNativeQuery(qry).getResultList();

	} catch (Exception e) {
		e.printStackTrace();
	}
		return list;
	}

	@Override
	public int getAlarmRemoveDet(String mtrno, String accno, String radioval) {
		 int result=0;
		  String qry="";
		  try {
			  if("accno".equals(radioval)) {
				  qry="delete FROM meter_data.ALARM_DEFINITION WHERE LOCATION_ID='"+accno+"'";
			  }else {
				  qry="DELETE FROM METER_DATA.ALARM_DEFINITION WHERE LOCATION_ID IN(select LOCATION_ID from METER_DATA.MASTER_MAIN where MTRNO='"+mtrno+"')";
			  }
				result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
			return result;
	}

	@Override
	public List<?> showAlarmDetailsbyAlarmname(String alarmName, String officeType, String officeName) {
		List<Object[]> list=new ArrayList<>();
		String subQry="";
		if("region".equalsIgnoreCase(officeName)) {
			subQry=" and m.zone like '"+officeType+"'";
		}
		else if("cirle".equalsIgnoreCase(officeName)) {
			subQry=" and m.circle like '"+officeType+"'";
		}
		else {
			subQry="";
		}
		String qry=" select p.*,q.town_ipds from \n" +
				"(SELECT m.SUBDIVISION,d.LOCATION_ID,m.CUSTOMER_NAME,m.MTRNO,d.ALARM_NAME FROM METER_DATA.ALARM_DEFINITION d LEFT JOIN METER_DATA.MASTER_MAIN m \n" +
				"ON d.LOCATION_ID=m.location_id WHERE alarm_name = '"+alarmName+"'"+ subQry +" GROUP BY m.SUBDIVISION,d.LOCATION_ID,m.CUSTOMER_NAME,m.MTRNO,d.ALARM_NAME)p,\n" +
				"(select town_ipds,subdivision from meter_data.amilocation)q where p.subdivision=q.subdivision ";
		try {
			list=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<?> getAlarmPriorityDetbyAlarmName(String alarmName, String locationId) {
		List<?> list=null;
		  String qry="";
		  try {
			qry="SELECT distinct d.EVENTS,m.EVENT,d.PRIORITY FROM METER_DATA.ALARM_DEFINITION d LEFT JOIN METER_DATA.EVENT_MASTER m \n" +
					"ON cast(d.EVENTS as INTEGER)=m.EVENT_CODE WHERE alarm_name like '"+alarmName+"' and LOCATION_ID = '"+locationId+"'";
			list=postgresMdas.createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
			return list;
	}

	@Override
	public int getalarmRemoveDetailsbyAlarmName(String alarmName, String checkboxes) {
		
		String[] toBeDelete = checkboxes.split(",");
		int res=0;
		int result=0;
		String qry="";
		try {
		int[] toBeDeleted = new int[toBeDelete.length];
		for (int i = 0; i < toBeDelete.length; i++) {
			toBeDeleted[i]=Integer.parseInt(toBeDelete[i]);
			qry="delete FROM meter_data.ALARM_DEFINITION WHERE alarm_name like '"+alarmName+"' and  LOCATION_ID='"+toBeDeleted[i]+"'";
			res=getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
			result+=res;
		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}
		return result;
	}


}
