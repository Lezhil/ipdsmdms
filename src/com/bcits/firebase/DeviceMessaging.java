package com.bcits.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.service.AndroidService;

@Controller
public class DeviceMessaging {
	//

	@Autowired
	AndroidService androidService;

	@RequestMapping(value = "/updateFcmTokenMobile", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody void updateFcmTokenMobile(@RequestBody String data) throws JSONException {
		System.err.println("updateFcmTokenMobile==================="+data);
		try {
			JSONObject obj = new JSONObject(data);
			String fcmToken = obj.optString("fcm_token");
			String userName = obj.optString("userName");
			try {
				String query="update meter_data.ncpt_customers set fcm_token='"+fcmToken+"' where customer_login_name='"+userName+"'";
				System.out.println("queryComp=============================>"+query);
				int count = androidService.ncpt_rrno_insertion(query);
				if (count > 0) {
					System.out.println("TOKEN UPDATED : "+userName);
				} else {
					System.out.println("TOKEN FAILED UPDATE : "+userName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,value="txManageroracle")
	@RequestMapping(value = "/updateFcmTokenPrabandhUser", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody void updateFcmTokenPrabandhUser(@RequestBody String data) throws JSONException {
		System.out.println("updateFcmTokenPrabandhUser==================="+data);
		try {
			JSONObject obj = new JSONObject(data);
			String fcmToken = obj.optString("fcm_token");
			String userName = obj.optString("userName");
			try {
				String query="UPDATE BSMARTJVVNL.USERS set FCM_TOKEN='"+fcmToken+"' where USER_LOGIN_NAME='"+userName+"'";
				System.out.println("queryComp=============================>"+query);
				int count = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).executeUpdate();
				if (count > 0) {
					System.out.println("TOKEN UPDATED : "+userName);
				} else {
					System.out.println("TOKEN FAILED UPDATE : "+userName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@RequestMapping(value = "/sendNotificationToPrabandh/{title}/{message}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object sendNotificationToTopic(@PathVariable String title,@PathVariable String message) throws Exception {
		String user="Admin JVVNL";
		FireMessageCordova f = new FireMessageCordova(title, message,user,user);
		String topic="com.bcits.jvvnlmrfinder";
		return f.sendToTopic(topic); //TO TOPIC
	}

	@RequestMapping(value = "/sendNotificationToPrabandh", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String sendNotificationToPrabandh(@RequestBody String data) throws JSONException {
		System.out.println("sendNotificationToPrabandh==================="+data);
		try {
			JSONObject obj = new JSONObject(data);
			String to = obj.getString("to");
			String toId = obj.getString("toId");
			String message = obj.getString("message");
			String imei = obj.getString("IMEI");
			String querySender="select AA.empname, AA.mobile, AA.email, AA.mr_code, AA.feeder_code, AA.sitecode, BB.deviceid from  vcloudengine.employee AA, vcloudengine.deviceallocation BB WHERE BB.deviceid='"+imei+"' AND BB.mrcode=AA.mr_code AND cast ( BB.sdocode as text)=AA.sitecode";

			System.out.println(querySender);
			List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(querySender).getResultList();
			if(list!=null && list.size()>0){
				Object [] userInfo=list.get(0);
				String sender=(userInfo[0]==null)?"": String.valueOf(userInfo[0]);
				String mobile=(userInfo[1]==null)?"": String.valueOf(userInfo[1]);
				String email=(userInfo[2]==null)?"": String.valueOf(userInfo[2]);
				String mr_code=(userInfo[3]==null)?"": String.valueOf(userInfo[3]);
				String feeder_code=(userInfo[4]==null)?"": String.valueOf(userInfo[4]);
				String sitecode=(userInfo[5]==null)?"": String.valueOf(userInfo[5]);
				String deviceid=(userInfo[6]==null)?"": String.valueOf(userInfo[6]);
				String senderId=mr_code+"---"+sitecode;

				String queryReceiver="select USER_NAME, DESIGNATION,MOBILENO,EMAIL_ID,OFFICE_CODE,FCM_TOKEN from USERS where USER_LOGIN_NAME='"+toId+"'";

				System.out.println(queryReceiver);
				List<Object[]> list2=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(queryReceiver).getResultList();
				if(list2!=null && list2.size()>0){
					Object [] receiverInfo=list2.get(0);
					String receiverName=(receiverInfo[0]==null)?"": String.valueOf(receiverInfo[0]);
					String designation=(receiverInfo[1]==null)?"": String.valueOf(receiverInfo[1]);
					String receiveMobile=(receiverInfo[2]==null)?"": String.valueOf(receiverInfo[2]);
					String receiveEmail=(receiverInfo[3]==null)?"": String.valueOf(receiverInfo[3]);
					String receiveSiteCode=(receiverInfo[4]==null)?"": String.valueOf(receiverInfo[4]);
					String fcmToken=(receiverInfo[5]==null)?"": String.valueOf(receiverInfo[5]);

					FireMessageCordova f =new FireMessageCordova(sender, message, sender, senderId);
					return f.sendToToken(fcmToken);

				}else{
					return "RECEIVER NOT FOUND";
				}


			}else{
				return "SENDER NOT FOUND";
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return "SENDING FAILED";
	}
	
	@RequestMapping(value = "/getOfficersListForDevice", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getOfficersListForDevice(@RequestBody String data) throws JSONException {
		System.out.println("in getOfficersListForDevice JVVNL..........."+data);
		try {
			String imei = new JSONObject(data).getString("IMEI");

			String querySender="select AA.empname, AA.mobile, AA.email, AA.mr_code, AA.feeder_code, AA.sitecode, BB.deviceid from  vcloudengine.employee AA, vcloudengine.deviceallocation BB WHERE BB.deviceid='"+imei+"' AND BB.mrcode=AA.mr_code AND cast ( BB.sdocode as text)=AA.sitecode";
		
			System.out.println(querySender);
			List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(querySender).getResultList();
			if(list!=null && list.size()>0){
				Object [] userInfo=list.get(0);
				String sender=(userInfo[0]==null)?"": String.valueOf(userInfo[0]);
				String mobile=(userInfo[1]==null)?"": String.valueOf(userInfo[1]);
				String email=(userInfo[2]==null)?"": String.valueOf(userInfo[2]);
				String mr_code=(userInfo[3]==null)?"": String.valueOf(userInfo[3]);
				String feeder_code=(userInfo[4]==null)?"": String.valueOf(userInfo[4]);
				String sitecode=(userInfo[5]==null)?"": String.valueOf(userInfo[5]);
				String deviceid=(userInfo[6]==null)?"": String.valueOf(userInfo[6]);
				String senderId=mr_code+"---"+sitecode;


				String queryReceiver="SELECT A.USER_NAME, A.MOBILENO, B.DESIGNATION, A.EMAIL_ID, A.OFFICE_TYPE, A.FCM_TOKEN,A.USER_LOGIN_NAME  FROM BSMARTJVVNL.USERS A, BSMARTJVVNL.DESIGNATION B WHERE A.OFFICE_CODE = '"+sitecode+"' AND A.DESIGNATION=B.ID "
//+" AND  (B.DESIGNATION IN ('SE','XEN','AEN','ARO','JEN')) "
+" ORDER BY A.FCM_TOKEN DESC, B.DESIGNATION, A.USER_NAME";
				
				System.out.println(queryReceiver);
				List<Map<String, String>> result = new ArrayList<>(); 
				List<Object[]> list2=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(queryReceiver).getResultList();
				if (list2.size() > 0) {
					for (int i = 0; i < list2.size(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						Object[] res = (Object[]) list2.get(i);
						map.put("empname", (res[0] == null) ? "NA" : res[0].toString());
						map.put("mobile", (res[1] == null) ? "NA" : res[1].toString());
						map.put("designation", (res[2] == null) ? "NA" : res[2].toString());
						map.put("email", (res[3] == null) ? "NA" : res[3].toString());
						map.put("sub_division", (res[4] == null) ? "NA" : res[4].toString());
						map.put("fcm_token", (res[5] == null) ? "NA" : res[5].toString());
						map.put("user_id", (res[6] == null) ? "NA" : res[6].toString());
						result.add(map);
					}
					System.out.println(result);
					return result;
				}else{
					return "RECEIVER NOT FOUND";
				}


			}else{
				return "SENDER NOT FOUND";
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			return exception.getMessage();
		}
	}
}

