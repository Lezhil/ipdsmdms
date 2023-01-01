package com.bcits.mdas.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.DisconnectionList;
import com.bcits.mdas.entity.AMIInstantaneousEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.OnDemandTransaction;
import com.bcits.mdas.jsontoobject.OnDemand;
import com.bcits.mdas.jsontoobject.RegisterValues;
import com.bcits.mdas.jsontoobject.Samples;
import com.bcits.mdas.service.DisconnectionListService;
import com.bcits.mdas.service.OndemandTranService;
import com.google.gson.Gson;

@Controller
public class MeterOperations {
	@Autowired
	HESController hesc;
	
	@Autowired
	DisconnectionListService disconnectionListService;
	
	
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager entityManager;
	@Autowired
	public OndemandTranService ods;
	
	public static String domainconnectionListURL = "http://1.23.144.187:8081/bsmartjvvnl/";
	public static String domainconnectionListURL2 = "http://192.168.2.152:8081/bsmartjvvnl/";
	public static String notificationURL = "http://jvvnlrms.com:7070/bsmartJVVNL/";
	
	@RequestMapping(value = "/meterStatus", method = { RequestMethod.GET,RequestMethod.POST, })
	public	String meterStatus() {
		return "meterInformation";

	}
	
	@RequestMapping(value = "/getStatus", method = {RequestMethod.POST, RequestMethod.GET  })
	public	String getMeterStatus(HttpServletRequest request,Model map,@RequestParam("action")String actionq  ) throws JSONException {
		String meterno=request.getParameter("meterNo");
		
		String response = hesc.powerSwitch(meterno);
		
		
		
		String qry = "select meter_no,acc_no,kno,disconnection_date,final_reading,reconnection_date from meter_data.disconnection_list where meter_no =  '"+meterno+"' ";
		
		List<?> result = null;
		try {
			result =    disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		JSONObject obj = new JSONObject(response);
		System.out.println("This is the repsonse" +obj.getString("powerSwitch"));
		String powerSwitch;
		String action;
		if(obj.getString("powerSwitch").equalsIgnoreCase("DISABLED_POWER_OFF")){
			
			powerSwitch = "Disconnected";
			action = "connect";
		}else {
			
			powerSwitch = "Connected";
			action = "Disconnect";
		}
		
		map.addAttribute("deviceId" , meterno);
		map.addAttribute("powerSwitch" , powerSwitch);
		map.addAttribute("action" , action);
		map.addAttribute("status" , powerSwitch);
		map.addAttribute("meterData" ,result);
			/*map.addAttribute("disCount" ,result[0]);
		map.addAttribute("LastDisconn" ,result[2]);
		map.addAttribute("LastReconn" ,result[3]);*/
		
		return "meterInformation";

	}
	@RequestMapping(value = "/getMeterStatusforApp", method = {RequestMethod.POST, RequestMethod.GET  })
	public	@ResponseBody String getMeterStatusforApp(HttpServletRequest request,Model map ) throws JSONException {
		String kno=request.getParameter("kno");
		String meterno;
		
		String qry = "SELECT mtrno , accno, sdocode FROM  meter_data.master_main where kno = '"+kno+"' ";
		
		Object[] result = null;
		try {
			result =    (Object[]) disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		meterno = result[0].toString();
		 /*accno = result[1].toString();
		 sdocode = result[2].toString();*/
		
		
		String response = hesc.powerSwitch(meterno);
		
		JSONObject obj = new JSONObject(response);
		System.out.println("This is the repsonse" +obj.getString("powerSwitch"));
		String powerSwitch;
		String action;
		if(obj.getString("powerSwitch").equalsIgnoreCase("DISABLED_POWER_OFF")){
			
			powerSwitch = "Disconnected";
			action = "connect";
		}else {
			
			powerSwitch = "Connected";
			action = "Disconnect";
		}
		
		map.addAttribute("deviceId" , meterno);
		map.addAttribute("powerSwitch" , powerSwitch);
		map.addAttribute("action" , action);
		map.addAttribute("status" , powerSwitch);
		map.addAttribute("connCount" ,"3");
		map.addAttribute("disCount" ,"4");
		
		return powerSwitch;

	}

	@RequestMapping(value = "/changeStatus", method = {RequestMethod.POST, RequestMethod.GET  })
	public	String getchangeStatus(HttpServletRequest request,Model map,@RequestParam("action")String actionq  ) throws JSONException, UnsupportedEncodingException {
		String meterno=request.getParameter("meterNo");
		
		String  currentStatus = null;
		String  OndemandStatus = null;
		String finalreading;
		String powerSwitch;
		String action;
		String 	accno;
		String sdocode;
		 String resBillmonth="201812";
		 String resBillingCycle= "1";
		 String resScodate= "21-12-2018";
		 String response ;
		 String ir = "5.5";
		 String oldfr = "7.5";
		 String kno ;
		//Notification String for mobile app 
		 String msg  ;
		 String title  ;
		
		 String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		 
		String res = hesc.powerSwitchAction(meterno, actionq);
		
		DisconnectionList dsentity = new DisconnectionList();
		
		
		
		MasterMainEntity mmentEntity = new MasterMainEntity();
		
		JSONObject obj = new JSONObject(res);
		
		
			String qry = "SELECT kno , accno, sdocode FROM  meter_data.master_main where mtrno = '"+meterno+"' ";
		
		Object[] result = null;
		Object[] result2 = null;
		
	
		try {
			result =    (Object[]) disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
							
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* meterno = result[0].toString();*/
		kno =  result[0].toString();
		 accno = result[1].toString();
		 sdocode = result[2].toString();
		 
		 	
		
		 
		 finalreading = getFinaleReadingNew(meterno);
			String[] finalreadingnew = finalreading.split(" ");
			
			double Kwh = Double.parseDouble(finalreadingnew[0]);
			double finalkwh = Kwh/1000;
			System.out.println("#####This is the kwh " +finalkwh);
			
		
		
		if(obj.getString("powerSwitch").equalsIgnoreCase("DISABLED_POWER_OFF")){
			
			powerSwitch = "Disconnected";
			action = "connect";
			currentStatus = "disconnected";
			OndemandStatus = "Disconnection";
			
			
		/*	int adCount = dsiconncount+1;
			Date currentDate = new Date();
			mmentEntity.setDisconnCount(dsiconncount+1);
			mmentEntity.setLastDisconnDate(new Date());*/
			
			/*String update = "update  meter_data.master_main  set disconn_count = '"+adCount+"' and  last_disconn_date'"+currentDate +"'  where mtrno =  '"+meterno+"' ";
			try {
				disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(masterMainqry).executeUpdate();	
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			 
			
			 String updateURL = domainconnectionListURL+"disConnectedConsumer?accno="+accno+"&sitecode="
				 		+ ""+sdocode+"&finalrdng="+finalkwh+"&billmonth="+resBillmonth+"&billingCycle="+resBillingCycle+"&scodate="+resScodate;
		     	
		        		try {
		        			response = 	genericWebService(updateURL);	
		        		System.out.println("This is the repsonse from RMS " + response);
						} catch (Exception e) {
							return "Web services failed";
						}
		        title = "Meter Disconnected";		
		        msg  ="Your meter("+meterno+") has been disconnected";		
		        
		        dsentity.setAccNo(accno);
		        dsentity.setmeterNo(meterno);
		        dsentity.setFinalReading(Double.toString(finalkwh));
		        dsentity.setDisconnectionDate(new Date() );
		      
		        dsentity.setKno(kno);
		        
		        
		        
		        
		        
		        
		        //Notification URL		
		        		String notifyURL = 	notificationURL+"triggerAmiNotificationToMobile?title="+ URLEncoder.encode(title,"UTF-8")+"&message="
							 		+ ""+URLEncoder.encode(msg,"UTF-8")+"&accno="+accno+"&sdocode="+sdocode+"&kno="+kno+"&meterNo="+meterno;	
		        		
		        		System.out.println("This is notification #########" +notifyURL);
		        		
		        		String notifyResponse = 	genericWebService(notifyURL);	
		        		System.out.println("This is notification ############" +notifyResponse);
		        		
		        		
		        		try {
		        			response = 	genericWebService(notifyURL);	
		        		System.out.println("This is the repsonse from RMS " + response);
						} catch (Exception e) {
							return "Web services failed";
						}
		        		
			
		}else {
			
			powerSwitch = "Connected";
			action = "Disconnect";
			currentStatus = "connected";
			OndemandStatus = "Reconnection";
	
			
			
		/*	mmentEntity.setReconnectCount(reconncount+1);
			mmentEntity.setLastReconnDate(new Date());*/
			
			
			String updateURL = domainconnectionListURL+"ReconnectedFromAMI?accno="+accno+"&sdocode="
				 		+ ""+sdocode+"&newmeterno="+meterno+"&billmonth="+resBillmonth+"&billperiod="+resBillingCycle+"&ir="+finalkwh+""
				 				+ "&oldfr="+finalkwh+"&scodate="+resScodate;
		        
		       
		        		try {
		        			response = 	genericWebService(updateURL);	
		        		System.out.println("This is the repsonse from RMS " + response);
		        		title = "Meter-Connected";
		        		  msg  ="Your meter("+meterno+") has been connected.";
		        		/*String notifyURL = 	notificationURL+"triggerAmiNotificationToMobile?title="+title.replace(" ","")+"&message="
						 		+ ""+msg.replace(" ","")+"&accno="+accno+"&sdocode="+sdocode+"&kno="+kno+"&meterNo="+meterno;	
		        		*/
		        		  
		        		  String notifyURL = 	notificationURL+"triggerAmiNotificationToMobile?title="+ URLEncoder.encode(title,"UTF-8")+"&message="
							 		+ ""+URLEncoder.encode(msg,"UTF-8")+"&accno="+accno+"&sdocode="+sdocode+"&kno="+kno+"&meterNo="+meterno;
		        		System.out.println("This is notification #########" +notifyURL);
		        		
		        		String notifyResponse = 	genericWebService(notifyURL);	
		        		System.out.println("This is notification ############" +notifyResponse);
		        		
		        		
		        		
						} catch (Exception e) {
							return "Web services failed";
						}
		        		title = "Meter Reconnected";
		        		
		        	    
				        dsentity.setAccNo(accno);
				        dsentity.setmeterNo(meterno);
				        dsentity.setFinalReading(Double.toString(finalkwh));
				        
				        dsentity.setReconnectionDate(new Date());
				        dsentity.setKno(kno);
		        		
		}
		
		
		map.addAttribute("deviceId" , meterno);
		map.addAttribute("powerSwitch" , powerSwitch);
		map.addAttribute("action" , action);
		map.addAttribute("status" , powerSwitch);
		map.addAttribute("success" , currentStatus);
	
		disconnectionListService.customupdateBySchema(dsentity, "postgresMdas");
        
      
        
        return "meterInformation";

	}
	
	
	
	public String HESconnectDisconnect(String meterno , String action){
		String res = null;
		try {
			res = hesc.powerSwitchAction(meterno, action);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	return res;
		
	}
	
	
	
	@RequestMapping(value = "/connectDisconnect", method = {RequestMethod.POST, RequestMethod.GET  })
	public @ResponseBody	String getconnection(HttpServletRequest request,Model map  ) throws JSONException {
		/*String meterno=request.getParameter("meterNo");*/
		String kno = request.getParameter("kno");//* "210463039019";
		String actionq = request.getParameter("action");
		/*String meterno = request.getParameter("action");*/
		String  currentStatus = null;
		String  OndemandStatus = null;
		String powerSwitch;
		String meterno ;/*= "4613450" ;*/
		String accno ;
		String action;
		String sdocode;
		String finalreading;
		String response ;
		
		 DisconnectionList dsentity2 = new DisconnectionList();
		 String resBillmonth="201812";
		 String resBillingCycle= "1";
		 String resScodate= "21-12-2018";
		
		String qry = "SELECT mtrno , accno, sdocode FROM  meter_data.master_main where kno = '"+kno+"' ";
		
		Object[] result = null;
		try {
			result =    (Object[]) disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 meterno = result[0].toString();
		 accno = result[1].toString();
		 sdocode = result[2].toString();
		 
		System.out.println("This is the meterno "+meterno);
		String res = hesc.powerSwitchAction(meterno, actionq);
		
		
		OnDemandTransaction odt=new OnDemandTransaction();
		JSONObject obj = new JSONObject(res);
		JSONObject objResponse = new JSONObject();
		
				
		System.out.println("This is the repsonse" +obj.getString("powerSwitch"));
		
		
		finalreading = getFinaleReadingNew(meterno);
		String[] finalreadingnew = finalreading.split(" ");
	
		double Kwh = Double.parseDouble(finalreadingnew[0]);
		double finalkwh = Kwh/1000;
		System.out.println("#####This is the kwh " +finalkwh);
		
		
		
        
        
        if(obj.getString("powerSwitch").equalsIgnoreCase("DISABLED_POWER_OFF")){
        	OndemandStatus = "Disconnection";
			objResponse.put("Response", "Successfull Disconnected");
			
			String updateURL = domainconnectionListURL+"disConnectedConsumer?accno="+accno+"&sitecode="
				 		+ ""+sdocode+"&finalrdng="+finalkwh+"&billmonth="+resBillmonth+"&billingCycle="+resBillingCycle+"&scodate="+resScodate;
		   
		       
		        		try {
		        			response = 	genericWebService(updateURL);	
		        			dsentity2.setAccNo(accno);
		     		        dsentity2.setmeterNo(meterno);
		     		        dsentity2.setFinalReading(Double.toString(finalkwh));
		     		        dsentity2.setDisconnectionDate(new Date() );
		     		         dsentity2.setKno(kno);
		     		        disconnectionListService.customupdateBySchema(dsentity2, "postgresMdas");
		        			
						} catch (Exception e) {
							return "Web services failed";
						}
			
			
			
		}else {
			OndemandStatus = "Reconnection";
			objResponse.put("Response", "Successfull Reconnected");
			String updateURL = domainconnectionListURL
					+ "ReconnectedFromAMI?accno=" + accno + "&sdocode=" + ""
					+ sdocode + "&newmeterno=" + meterno + "&billmonth="
					+ resBillmonth + "&billperiod=" + resBillingCycle + "&ir="
					+ finalkwh + "" + "&oldfr=" + finalkwh + "&scodate="
					+ resScodate;

			try {
				response = genericWebService(updateURL);

				dsentity2.setAccNo(accno);
				dsentity2.setmeterNo(meterno);
				dsentity2.setFinalReading(Double.toString(finalkwh));

				dsentity2.setReconnectionDate(new Date());
				dsentity2.setKno(kno);

				disconnectionListService.customupdateBySchema(dsentity2,
				"postgresMdas");
			
			} catch (Exception e) {
				return "Web services failed";
			}

		}
        
        odt.setType(OndemandStatus);
        odt.setMeterNumber(meterno);
        odt.setOndemTime(new Timestamp(new Date().getTime()));	
        
        ods.customupdateBySchema(odt, "postgresMdas");
        return response;
      
	}
	
	
	//Connect and disconnection of meter  from web application 
	@RequestMapping(value = "/connectDisconnectWeb", method = {RequestMethod.POST, RequestMethod.GET  })
	public @ResponseBody	String connectDisconnectWeb(HttpServletRequest request,Model map  ) throws JSONException {
		/*String meterno=request.getParameter("meterNo");*/
		String kno = request.getParameter("kno");//* "210463039019";
		String actionq = request.getParameter("action");
		/*String meterno = request.getParameter("action");*/
		String  currentStatus = null;
		String  OndemandStatus = null;
		String powerSwitch;
		String meterno ;/*= "4613450" ;*/
		String accno ;
		String action;
		String sdocode;
		String finalreading;
		 String response ;
		
		 DisconnectionList dsentity2 = new DisconnectionList();
		 String resBillmonth="201901";
		 String resBillingCycle= "1";
		 String resScodate= "09-01-2019";
		
		String qry = "SELECT mtrno , accno, sdocode FROM  meter_data.master_main where kno = '"+kno+"' ";
		
		Object[] result = null;
		try {
			result =    (Object[]) disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 meterno = result[0].toString();
		 accno = result[1].toString();
		 sdocode = result[2].toString();
		 
		System.out.println("This is the meterno "+meterno);
		String res = hesc.powerSwitchAction(meterno, actionq);
		
		
		OnDemandTransaction odt=new OnDemandTransaction();
		JSONObject obj = new JSONObject(res);
		JSONObject objResponse = new JSONObject();
		finalreading = getFinaleReadingNew(meterno);
		String[] finalreadingnew = finalreading.split(" ");
	
		double Kwh = Double.parseDouble(finalreadingnew[0]);
		double finalkwh = Kwh/1000;
		System.out.println("#####This is the kwh " +finalkwh);
		
				
		System.out.println("This is the repsonse" +obj.getString("powerSwitch"));

		if (obj.getString("powerSwitch").equalsIgnoreCase("DISABLED_POWER_OFF")) {
			objResponse.put("Response", "Successfull Disconnected");

			String updateURL = domainconnectionListURL
					+ "disConnectedConsumer?accno=" + accno + "&sitecode=" + ""
					+ sdocode + "&finalrdng=" + finalkwh + "&billmonth="
					+ resBillmonth + "&billingCycle=" + resBillingCycle
					+ "&scodate=" + resScodate;

			try {
				response = genericWebService(updateURL);

				dsentity2.setAccNo(accno);
				dsentity2.setmeterNo(meterno);
				dsentity2.setFinalReading(Double.toString(finalkwh));
				dsentity2.setDisconnectionDate(new Date());
				dsentity2.setKno(kno);
				disconnectionListService.customupdateBySchema(dsentity2,
						"postgresMdas");

				return response;
			} catch (Exception e) {
				return "Web services failed";
			}

		} else {

			String updateURL = domainconnectionListURL
					+ "ReconnectedFromAMI?accno=" + accno + "&sdocode=" + ""
					+ sdocode + "&newmeterno=" + meterno + "&billmonth="
					+ resBillmonth + "&billperiod=" + resBillingCycle + "&ir="
					+ finalkwh + "" + "&oldfr=" + finalkwh + "&scodate="
					+ resScodate;

			try {
				response = genericWebService(updateURL);

				dsentity2.setAccNo(accno);
				dsentity2.setmeterNo(meterno);
				dsentity2.setFinalReading(Double.toString(finalkwh));

				dsentity2.setReconnectionDate(new Date());
				dsentity2.setKno(kno);

				disconnectionListService.customupdateBySchema(dsentity2,
						"postgresMdas");

				System.out.println("This is the repsonse from RMS " + response);
			} catch (Exception e) {
				return "Web services failed";
			}
			objResponse.put("Response", "Successfull Reconnected");

		}

		return response;

	}
	
	
/*	@RequestMapping(value = "/changeStatus", method = { RequestMethod.GET })
	public	String changeStatus() {
		String meter_id = "";
		String action = "";
		hesc.powerSwitchAction(meter_id, action);
		
		return "meterInformation";

	}
	*/
		
	@RequestMapping(value = "/disConnectionList", method = { RequestMethod.GET , RequestMethod.POST })
	public	String getdisConnectionList(ModelMap model) throws ParseException {
		String meter_id = "";
		String action = "";
		/*hesc.powerSwitchAction(meter_id, action);*/
		try {
			List <?> result = getDisconnectionList("2104440" , "201811") ;
			if(result.equals(null)){
				model.addAttribute("result" , null);
				return "disConnectionList"; 
			}
			
			
			System.out.println(result);
			model.addAttribute("result" ,result);
		//	scheduledDisconnectionList();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "disConnectionList";

	}
	
	
	@RequestMapping(value = "/reConnectionList", method = { RequestMethod.GET , RequestMethod.POST })
	public	String getReConnectionList(ModelMap model) throws ParseException, JSONException {
		String meter_id = "";
		String action = "";
		//getFinaleReading();
		/*hesc.powerSwitchAction(meter_id, action);*/
		try {
			List <?> result = getReconnectionList("2104440") ;
			System.out.println(result);
			model.addAttribute("result" ,result);
		//	scheduledDisconnectionList();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "reConnectionList";

	}
	
	
	
	/*
	
	@RequestMapping(value = "/onDemandProfile/INSTANTANEOUS/{profileType}/{meterId}/{stadate}/{stopdate}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String instantaneous(HttpServletRequest req,
			@PathVariable String profileType, @PathVariable String meterId,
			@PathVariable String stadate, @PathVariable String stopdate)
			throws ParseException {
			
			public double getFinaleReading() throws JSONException{
		String s = hesc.onDemandProfileAMI("INSTANTANEOUS", "GS5500133", "25-11-2018",	"26-11-2018");
		
	JSONObject OBJ = new JSONObject(s);
	System.out.println(OBJ.toString());
		// JsonObject obj = new JsonParser().parse(s).getAsJsonObject();
		if(s==null){
			return 0;
			
		}
		else{
		Gson gson = new Gson();
		OnDemand ins = gson.fromJson(s, OnDemand.class);
		Samples[] sam = ins.getSamples();

		for (int i = 0; i < sam.length; i++) {

			AMIInstantaneousEntity entity = new AMIInstantaneousEntity();
            OnDemandTransaction odt=new OnDemandTransaction();
			entity.setTimeStamp(new Timestamp(new Date().getTime()));

			Samples sa = sam[i];
			String time = sa.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
					Locale.ENGLISH);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
					);
			
			Timestamp t=null;
			
			RegisterValues[] rv = sa.getRegisterValues();
			for (int r = 0; r < rv.length; r++) {
				String obc = rv[r].getFormattedRegisterObisCode();
				String atr = rv[r].getAttributeId();
				
				if (obc.equalsIgnoreCase("1.0.1.8.0.255")) {
				System.out.println("This is KWH value ######: " +rv[r].getFormattedValue());
				

				} 

			}
			
		} 
		
		return 12;
		}
			}*/
	
	@RequestMapping(value = "/manualDisconnection", method = { RequestMethod.GET , RequestMethod.POST})
	public  String manualDisconnection(HttpServletRequest request,ModelMap model) throws ParseException, JSONException {
		String meter_id = "GS5500134";
		String action = "";
		String meterno=request.getParameter("meterno");
		System.out.println("meterno--->"+meterno);
		String response = hesc.powerSwitchAction(meterno, "disconnect");
		
		JSONObject obj = new JSONObject(response);
		if(obj.has("deviceId")){
			System.out.println("This is the response" +obj.getString("powerSwitch") );
		}
		
		try {
			List <?> result = getDisconnectionList("2104440" , "201811") ;
			if(result.equals(null)){
				model.addAttribute("result" , null);
				return "disConnectionList"; 
			}
			
			
			System.out.println(result);
			model.addAttribute("result" ,result);
		//	scheduledDisconnectionList();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		model.addAttribute("Success" , "Disconnected Successfully");
		return "disConnectionList";
	}
	
	@RequestMapping(value = "/manualReconnection", method = { RequestMethod.GET, RequestMethod.POST })
	public	String manualReconnection(HttpServletRequest request,ModelMap model) throws ParseException, JSONException {
		
		String action = "";
		String meterno=request.getParameter("meterno");
		String response = hesc.powerSwitchAction(meterno, "connect");
		
		JSONObject obj = new JSONObject(response);
		if(obj.has("deviceId")){
			System.out.println("This is the response" +obj.getString("powerSwitch") );
		}
		
		try {
			List <?> result = getReconnectionList("2104440") ;
			System.out.println(result);
			model.addAttribute("result" ,result);
		//	scheduledDisconnectionList();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("Success" , "Reconnected Successfully");
		return "reConnectionList";

	}

	public void meterDisconnection(){
		
		String qry = "SELECT meter_no,id FROM meter_data.disconnection_list WHERE connection_status is NULL AND  disconnection_date is NULL ";
		
	 List<Object[]> discList = null ;
	 try {
		//DisconnectionList diconList = new DisconnectionList();
		 discList = disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		
		 System.out.println("This is the list " + discList);
		 for (Object[] meterNo : discList) {
			 DisconnectionList disonEntity = null;
			 try {
				 disonEntity = disconnectionListService.find(new BigInteger(meterNo[1].toString()).intValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			 String result = hesc.powerSwitchAction(meterNo[0].toString(), "disconnect");
			 
			 //get KWH valu from instaneous value
			 
			 
			 System.out.println("Disconnection Successfull #######" +result);
			 if (result == null ){
				System.out.println("No Response from server"); 
			 }else{	
				 JSONObject jsonObject=null;
 				try {
 					jsonObject=new JSONObject(result);
				 String status =  jsonObject.getString("powerSwitch");
				 
				 disonEntity.setConnectionStatus(status);
				 disonEntity.setDisconnectionDate(new Timestamp(System.currentTimeMillis()));
				 
				 disconnectionListService.customupdateBySchema(disonEntity, "postgresMdas");
				 
				 
				 // update status to AMR
			/*	 
				 String resAccNo = disonEntity.getAccNo();
				 String resSitecode= "2104440";
				 String resfinRdgn= "100";
				 String resBillmonth="201811";
				 String resBillingCycle= "2";
				 String resScodate= "21-11-2018";
				 
				 
				 String updateURL = domainconnectionListURL2+"disConnectedConsumer?accno="+resAccNo+"&sitecode="
				 		+ ""+resSitecode+"&finalrdng="+resfinRdgn+"&billmonth="+resBillmonth+"&billingCycle="+resBillingCycle+"&scodate="+resScodate;
				 
				 
				 String response = gerericWebService(updateURL);
				 JSONObject jo = null ; 
				 try {
					 new JSONObject(updateURL);
					 
					 
					 
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				 
				}catch(Exception e){
					e.printStackTrace();
				}
		
		}
		 
		 }
		 
	} catch (Exception e) {
		// TODO: handle exception
	}
		
	}
	
public void meterReConnection(){
		
		String qry = "select meter_no from meter_data.disconnection_list where request_type = 'RC' and reconnection_date is null and connection_status is null ";
		
	 List<Object[]> discList = null ;
	 try {
		//DisconnectionList diconList = new DisconnectionList();
		 discList = disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		
		
		 for (Object[] meterNo : discList) {
			 DisconnectionList disonEntity = null;
			 try {
				 disonEntity = disconnectionListService.find(new BigInteger(meterNo[1].toString()).intValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			 String result = hesc.powerSwitchAction(meterNo[0].toString(), "connect");
			 
			 //get KWH valu from instaneous value
			 
			 
			 System.out.println("Disconnection Successfull #######" +result);
			 if (result == null ){
				System.out.println("No Response from server"); 
			 }else{	
				 JSONObject jsonObject=null;
 				try {
 					jsonObject=new JSONObject(result);
				 String status =  jsonObject.getString("powerSwitch");
				 
				 disonEntity.setConnectionStatus(status);
				 disonEntity.setDisconnectionDate(new Timestamp(System.currentTimeMillis()));
				 disconnectionListService.customupdateBySchema(disonEntity, "postgresMdas");
				 
				 
				 // update status to AMR
			/*	 
				 String resAccNo = disonEntity.getAccNo();
				 String resSitecode= "2104440";
				 String resfinRdgn= "100";
				 String resBillmonth="201811";
				 String resBillingCycle= "2";
				 String resScodate= "21-11-2018";
				 
				 
				 String updateURL = domainconnectionListURL2+"disConnectedConsumer?accno="+resAccNo+"&sitecode="
				 		+ ""+resSitecode+"&finalrdng="+resfinRdgn+"&billmonth="+resBillmonth+"&billingCycle="+resBillingCycle+"&scodate="+resScodate;
				 
				 
				 String response = gerericWebService(updateURL);
				 JSONObject jo = null ; 
				 try {
					 new JSONObject(updateURL);
					 
					 
					 
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				 
				}catch(Exception e){
					e.printStackTrace();
				}
		
		}
		 
		 }
		 
	} catch (Exception e) {
		// TODO: handle exception
	}
		
	}
	
	
	public List<?> getDisconnectionList(String sitecode , String billmonth) throws JSONException, ParseException 
	{
		
		String url = domainconnectionListURL+"getDisConnectionList?sitecode="+sitecode+"&billmonth="+billmonth;
		
		DisconnectionList disList = new DisconnectionList();
		System.out.println(" get data "+url);
		String response = genericWebService(url);
		//String response = NcmsLthtWebServices.authenticatedGenericWebService(url);
		JSONArray array = new JSONArray(response);
		System.out.println("array length ===> "+array.length());
		List<Map<String, Object>> binder = new ArrayList<Map<String, Object>>();
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"
				);
		
		Timestamp t=null;
		for (int i = 0;i<array.length();i++) 
		{
			System.out.println("inside loop");
				Map<String, Object> data = new HashMap<>();
				
				JSONObject obj = array.getJSONObject(i);
				 
				 if(obj.has("accno"))
				 {
			        data.put("accno", obj.getString("accno"));
			        disList.setAccNo (obj.getString("accno"));
				 }
				 
				 if(obj.has("meterno"))
				 {
			        data.put("meterno", obj.getString("meterno"));
			        disList.setmeterNo(obj.getString("meterno"));
				 }
				 if(obj.has("balance"))
				 {
			        data.put("balance", obj.getString("balance"));
			        disList.setBalance (obj.getString("balance"));
				 }
				 if(obj.has("name"))
				 {
			        data.put("name", obj.getString("name"));
			        disList.setName(obj.getString("name"));
				 }
				 
				 if(obj.has("tariffcode"))
				 {
			        data.put("tariffcode", obj.getString("tariffcode"));
			        disList.setTariffcode (obj.getString("tariffcode"));
				 }
				 
				 if(obj.has("feedercode"))
				 {
			        data.put("feedercode", obj.getString("feedercode"));
			        disList.setFeederCode(obj.getString("feedercode"));
				 }
				 if(obj.has("duedate"))
				 {
			        data.put("duedate", obj.getString("duedate"));
			       disList.setDuedate(new Timestamp(format.parse(obj.getString("duedate")).getTime())) ;             
				 }
				 
				 if(obj.has("lastpayment"))
				 {
			        data.put("lastpayment", obj.getString("lastpayment"));
			        
			     // disList.setLastpayment(new Timestamp(format.parse(obj.getString("lastpayment")).getTime())) ;           
				 }
				 if(obj.has("sdocode"))
				 {
			        data.put("sdocode", obj.getString("sdocode"));
			        disList.setSdocode (obj.getString("sdocode"));
				 }
				 
				 binder.add(data);
				
					disList.setRequestType("DC");
					disList.setRequestedDate(new Timestamp(System.currentTimeMillis()));
					 
				 disconnectionListService.customupdateBySchema(disList, "postgresMdas");
		}
	//	 meterDisconnection();
		 return binder;
		
	}
	
	
	public List<?> getReconnectionList(String sitecode) throws JSONException, ParseException 
	{
		
		String url = domainconnectionListURL+"getReConnectionList?sitecode="+sitecode;
		
		DisconnectionList disList = new DisconnectionList();
		System.out.println(" get data "+url);
		String response = genericWebService(url);
		//String response = NcmsLthtWebServices.authenticatedGenericWebService(url);
		JSONArray array = new JSONArray(response);
		System.out.println("array length ===> "+array.length());
		List<Map<String, Object>> binder = new ArrayList<Map<String, Object>>();
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"
				);
		
		Timestamp t=null;
		for (int i = 0;i<array.length();i++) 
		{
			System.out.println("inside loop");
				Map<String, Object> data = new HashMap<>();
				
				JSONObject obj = array.getJSONObject(i);
				 
				 if(obj.has("sdocode"))
				 {
			        data.put("sdocode", obj.getString("sdocode"));
			        disList.setSdocode (obj.getString("sdocode"));
				 }
				 
				 if(obj.has("accno"))
				 {
			        data.put("accno", obj.getString("accno"));
			        disList.setAccNo(obj.getString("accno"));
				 }
				 if(obj.has("kno"))
				 {
			        data.put("kno", obj.getString("kno"));
			       disList.setKno(obj.getString("kno"));
				 }
				 if(obj.has("name"))
				 {
			        data.put("name", obj.getString("name"));
			        disList.setName(obj.getString("name"));
				 }
				 
				 if(obj.has("consumerStatus"))
				 {
			        data.put("consumerStatus", obj.getString("consumerStatus"));
			      /*  disList.se (obj.getString("consumerStatus"));*/
				 }
				 
				 if(obj.has("feedercode"))
				 {
			        data.put("feedercode", obj.getString("feedercode"));
			        disList.setFeederCode(obj.getString("feedercode"));
				 }
				 if(obj.has("meterno"))
				 {
					 data.put("meterno", obj.getString("meterno"));
				     disList.setmeterNo(obj.getString("meterno"));           
				 }
				 
				disList.setRequestType("RC");
				disList.setRequestedDate(new Timestamp(System.currentTimeMillis()));
				 binder.add(data);
				
			 disconnectionListService.customupdateBySchema(disList, "postgresMdas");
		}
		// meterReconnection();
	//	double x = getFinaleReading();
	//	System.out.println("X value ####" +x);
		 return binder;
		
	}
	
	 public static String  genericWebService(String targetURL) 
		{
			HttpURLConnection conn 		= 	null;
			String response 			= 	"error: " + "POST" + " failed ";
			String url                  =   targetURL;
		    String type 				=   "application/json";
		    String method 				=   "POST";

		    System.out.println("targetURL new ===> "+targetURL);
			try
			{
				URL u = new URL(url);
				
		        conn = (HttpURLConnection)u.openConnection();
				conn.setRequestProperty("Content-Type", type);
				conn.setRequestProperty("Accept", type);
				conn.setRequestMethod(method);
				String line = "";
				StringBuffer sb = new StringBuffer();
				BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()) );
				while((line = input.readLine()) != null)
				    sb.append(line);
				input.close();
				conn.disconnect();  
				response = sb.toString();
				return response;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				try 
				{
					if (conn != null)
					conn.disconnect();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			return null;
		}
	 
	 Timestamp getTimeStamp(String value) {
			//String dt = "10-Jan-2017";
			String pattern = "yyyy-MMM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date date = null;
			try {
				date = simpleDateFormat.parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				
			}
			System.out.println("Date:" + date);
			java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
			
		return timestamp;
	 }
	 
		@RequestMapping(value = "/auditTrails", method = {RequestMethod.POST, RequestMethod.GET  })
		public	String getauditTrails(HttpServletRequest request,Model map) throws JSONException {
			
			
			return "auditTrails";

		}

		@RequestMapping(value = "/getAuditTrailData", method = {RequestMethod.POST, RequestMethod.GET  })
		public	String getAuditTrailData(HttpServletRequest request,Model map) throws JSONException {
			
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String qry = "select *from meter_data.ondemand_transaction WHERE  to_char(time_stamp, 'yyyy-MM-dd') BETWEEN '"+fromDate+"' AND '"+toDate+"'";
			List<?> list = null ;
			
			try {
				list =   disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
				System.out.println(list.toString());
				System.out.println("This is the qry ---->"+qry);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			map.addAttribute("list" , list);
			map.addAttribute("fromDate",request.getParameter("fromDate"));
			map.addAttribute("toDate",request.getParameter("toDate"));
			return "auditTrails";

		}
		
		
		@RequestMapping(value = "/BIandAnalytics", method = {RequestMethod.POST, RequestMethod.GET  })
		public	String BIandAnalytics(HttpServletRequest request,Model map) throws JSONException {
			
			
			return "BIandAnalytics";

		}
		
		@RequestMapping(value = "/BIandAnalyticsData", method = {RequestMethod.POST, RequestMethod.GET  })
		public	String BIandAnalyticsData(HttpServletRequest request,Model map) throws JSONException {
			
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String qry = "select *from meter_data.load_survey WHERE  to_char(time_stamp, 'yyyy-MM-dd') BETWEEN '"+fromDate+"' AND '"+toDate+"'";
			String qryMax = "select meter_number ,MAX(i_r), MAX(i_y),MAX(i_b),MAX(v_r),MAX(v_y),MAX(v_b),MAX(kwh),MAX(kvarh_lead),MAX (kvarh_lag),MAX(kwh_imp),MAX(block_energy_kwh_exp), MAX(block_energy_kvah_exp)  from meter_data.load_survey WHERE  to_char(time_stamp, 'yyyy-MM-dd') BETWEEN '"+fromDate+"' AND '"+toDate+"' GROUP BY meter_number";

			List<?> list = null ;
			List<?> Maxlist = null ;
			try {
				list =   disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
				Maxlist =disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qryMax).getResultList();
				System.out.println(list.toString());
				System.out.println("This is the qry ---->"+qry);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			map.addAttribute("Maxlist" , Maxlist);
			map.addAttribute("list" , list);
			map.addAttribute("fromDate",request.getParameter("fromDate"));
			map.addAttribute("toDate",request.getParameter("toDate"));
			return "BIandAnalytics";

		}
		
		
		@RequestMapping(value="/getDataCompare",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List getDataCompare(HttpServletRequest request,ModelMap model)
		{
			String viewTime=request.getParameter("viewTime");
			String meterno=request.getParameter("meterno");
			viewTime=viewTime.replace(".0","");
			
			System.out.println("inside getMetrDetailsAstMngt--"+meterno);
			String qry="";
			List list=null;
			try {
				qry="select time_stamp, meter_number,event_time, i_r, i_y, i_b, v_r , v_b, v_y, v_b,kwh from meter_data.events where meter_number = '"+meterno+"'  and  to_char(event_time, 'yyyy-MM-dd HH:mi:ss') = '"+viewTime+"'  ";
				System.out.println("getAddress---"+qry);
				list=entityManager.createNativeQuery(qry).getResultList();
				System.out.println("List----->>>" +list);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
			
		}
		
		
		public String getFinaleReadingNew(String meterNo) throws JSONException{

			SimpleDateFormat todaysDate = new SimpleDateFormat("yyyy-MM-dd'T' HH:mm:ss'+'0SSS", Locale.US);
			String stadate =todaysDate.format(new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L));
			String stopdate =  todaysDate.format(new Date());
					
				String s = hesc.onDemandProfileAMI("INSTANTANEOUS", meterNo ,stadate, stopdate);
					
				JSONObject OBJ = new JSONObject(s);
				System.out.println(OBJ.toString());
				String kwhValue = null;
					// JsonObject obj = new JsonParser().parse(s).getAsJsonObject();
					if(s==null){
						return "Unable to connect API";
						
					}
					else{
					Gson gson = new Gson();
					OnDemand ins = gson.fromJson(s, OnDemand.class);
					Samples[] sam = ins.getSamples();

					for (int i = 0; i < sam.length; i++) {

						AMIInstantaneousEntity entity = new AMIInstantaneousEntity();
			            OnDemandTransaction odt=new OnDemandTransaction();
						entity.setTimeStamp(new Timestamp(new Date().getTime()));

						Samples sa = sam[i];
						String time = sa.getTime();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
								Locale.ENGLISH);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
								);
						
						Timestamp t=null;
						
						RegisterValues[] rv = sa.getRegisterValues();
						for (int r = 0; r < rv.length; r++) {
							String obc = rv[r].getFormattedRegisterObisCode();
							String atr = rv[r].getAttributeId();
							
							if (obc.equalsIgnoreCase("1.0.1.8.0.255")) {
							System.out.println("This is KWH value ######: " +rv[r].getFormattedValue());
							kwhValue=rv[r].getFormattedValue();	
							} 

						}
						
					} 
					
					return kwhValue;
					}
						}
		
		
		
// For SPDC Disconnection Web-Services
		
		@RequestMapping(value = "/connectDisconnectAPI", method = {RequestMethod.POST, RequestMethod.GET  })
		public @ResponseBody	String connectDisconnectAPI(HttpServletRequest request,Model map  ) throws JSONException {
			/*String meterno=request.getParameter("meterNo");*/
			String kno = request.getParameter("kno");//* "210463039019";
			String actionq = request.getParameter("action");
			/*String meterno = request.getParameter("action");*/
			String  currentStatus = null;
			String  OndemandStatus = null;
			String powerSwitch;
			String meterno ;/*= "4613450" ;*/
			String accno ;
			String action;
			String sdocode;
			String finalreading;
			String response = null ;
			String title;
			String msg;
			
			
			 DisconnectionList dsentity2 = new DisconnectionList();
			 String resBillmonth="201812";
			 String resBillingCycle= "1";
			 String resScodate= "21-12-2018";
			
			String qry = "SELECT mtrno , accno, sdocode FROM  meter_data.master_main where kno = '"+kno+"' ";
			
			Object[] result = null;
			try {
				result =    (Object[]) disconnectionListService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
						
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			 meterno = result[0].toString();
			 accno = result[1].toString();
			 sdocode = result[2].toString();
			 
			System.out.println("This is the meterno "+meterno);
			String res = hesc.powerSwitchAction(meterno, actionq);
			
			
			OnDemandTransaction odt=new OnDemandTransaction();
			JSONObject obj = new JSONObject(res);
			JSONObject objResponse = new JSONObject();
			
					
			System.out.println("This is the repsonse" +obj.getString("powerSwitch"));
			
			
			finalreading = getFinaleReadingNew(meterno);
			String[] finalreadingnew = finalreading.split(" ");
		
			double Kwh = Double.parseDouble(finalreadingnew[0]);
			double finalkwh = Kwh/1000;
			System.out.println("#####This is the kwh " +finalkwh);
			
	        if(obj.getString("powerSwitch").equalsIgnoreCase("DISABLED_POWER_OFF")){
	        	OndemandStatus = "Disconnection";
				objResponse.put("Response", "Successfull Disconnected");
				
				/*String updateURL = domainconnectionListURL+"disConnectedConsumer?accno="+accno+"&sitecode="
					 		+ ""+sdocode+"&finalrdng="+finalkwh+"&billmonth="+resBillmonth+"&billingCycle="+resBillingCycle+"&scodate="+resScodate;*/
				title = "Meter-Disconnected";
      		  msg  ="Your meter("+meterno+") has been disconnected.";
			       
			        		try {
			        			/*response = 	genericWebService(updateURL);*/	
			        			dsentity2.setAccNo(accno);
			     		        dsentity2.setmeterNo(meterno);
			     		        dsentity2.setFinalReading(Double.toString(finalkwh));
			     		        dsentity2.setDisconnectionDate(new Date() );
			     		         dsentity2.setKno(kno);
			     		        disconnectionListService.customupdateBySchema(dsentity2, "postgresMdas");
			     		       String notifyURL = 	notificationURL+"triggerAmiNotificationToMobile?title="+ URLEncoder.encode(title,"UTF-8")+"&message="
								 		+ ""+URLEncoder.encode(msg,"UTF-8")+"&accno="+accno+"&sdocode="+sdocode+"&kno="+kno+"&meterNo="+meterno;
			        		System.out.println("This is notification #########" +notifyURL);
			        		
			        		String notifyResponse = 	genericWebService(notifyURL);	
			        		System.out.println("This is notification ############" +notifyResponse);
			        		response = "Meter disconnected";
			        			
							} catch (Exception e) {
								return "Web services failed";
							}
				
				
				
			}else {
				OndemandStatus = "Reconnection";
				objResponse.put("Response", "Successfull Reconnected");
				String updateURL = domainconnectionListURL
						+ "ReconnectedFromAMI?accno=" + accno + "&sdocode=" + ""
						+ sdocode + "&newmeterno=" + meterno + "&billmonth="
						+ resBillmonth + "&billperiod=" + resBillingCycle + "&ir="
						+ finalkwh + "" + "&oldfr=" + finalkwh + "&scodate="
						+ resScodate;

				try {
					/*response = genericWebService(updateURL);*/

					dsentity2.setAccNo(accno);
					dsentity2.setmeterNo(meterno);
					dsentity2.setFinalReading(Double.toString(finalkwh));

					dsentity2.setReconnectionDate(new Date());
					dsentity2.setKno(kno);

					disconnectionListService.customupdateBySchema(dsentity2,
					"postgresMdas");
				response= "Meter Connected";
				} catch (Exception e) {
					return "Web services failed";
				}

			}
	        
	        odt.setType(OndemandStatus);
	        odt.setMeterNumber(meterno);
	        odt.setOndemTime(new Timestamp(new Date().getTime()));	
	        
	        ods.customupdateBySchema(odt, "postgresMdas");
	        return response;
	      
		}
		
		
}
