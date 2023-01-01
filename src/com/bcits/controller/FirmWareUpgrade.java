package com.bcits.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.controller.HESController;

@Controller
public class FirmWareUpgrade {

	@RequestMapping(value = "/firmwareupgrade", method={RequestMethod.POST,RequestMethod.GET})
	public String showFirmwareUpgradePage(ModelMap model) {
		System.out.println("firmwareupgrade page.");
		
		List<String> meters=new ArrayList<>();
		String res=callAPI(1000,0,"STATIC");
		if(!res.equalsIgnoreCase("NA")) {
			try{
				JSONArray jsarr = new JSONArray(res);
				JSONObject jsobj = null;
				for(int i=0;i<jsarr.length();i++){
					jsobj = new JSONObject(jsarr.get(i).toString());
					//System.err.println(i+" "+jsobj.getString("name") + " " + jsobj.getString("type"));
					meters.add(jsobj.getString("name"));
				}
			}catch(JSONException e){
				meters.add("NA");
				e.printStackTrace();
			}
		}else{
			meters.add("NA");
		}
		model.addAttribute("meters",meters);
		return "firmwareupgrade";
	}
	
	public static String callAPI(int count,int startId,String type){
		try{
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient = HESController.createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(HESController.hesurl + "/meterGroups");
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + HESController.authStringenc);
			JSONObject obj = new JSONObject();
			obj.put("count",count);
			obj.put("startId",startId);
			obj.put("type",type);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);
			//HttpResponse response = httpClient.execute(httpRequest);
			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);
			return res;
		}
		catch(Exception e){
			e.printStackTrace();
			return "NA";
		}
	}
	
	@RequestMapping(value = "/createMeterJob", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String callApiFirmWareUpgrade(HttpServletRequest request){
		String firmwaretype="FIRMWARE_IMAGE";
		String firmwareImage=request.getParameter("frmimage");//"HEX_ENCODED_FIRMWARE_DATA";
		String firmwareHash=request.getParameter("frmhash");//"HEX_ENCODED_MD5_HASH_OF_FIRMARE_DATA";
		String firmwareIdent=request.getParameter("frmindent");//"FIRMWAREV1";
		
		String jobName="JOB_XYZ";
		String jobType="METER_FIRMWARE_UPDATE";
		String meterGroup=request.getParameter("mtrgroup");//"GROUP_ABC";
		String scheduledTime="yyyy-MM-dd'T'HH:mm:ssX";
		String timeoutTime="yyyy-MM-dd'T'HH:mm:ssX";
		
		System.out.println("meterGroup="+meterGroup+"\nfirmwareImage="+firmwareImage+"\nfirmwareHash="+firmwareHash+"\nfirmwareIdent="+firmwareIdent);
		
		JSONObject json1=new JSONObject();
		JSONObject json2=new JSONObject();
		JSONObject json3=new JSONObject();
		JSONArray jsarr1=new JSONArray();
		try{
			json3.put("type",firmwaretype);
			json3.put("firmwareImage",firmwareImage);
			json3.put("firmwareHash",firmwareHash);
			json3.put("firmwareIdent",firmwareIdent);
			jsarr1.put(json3);
			json2.put("commands",jsarr1);
			json1.put("jobConfiguration",json2);
			json1.put("jobName",jobName);
			json1.put("jobType",jobType);
			json1.put("meterGroup",meterGroup);
			json1.put("scheduledTime",scheduledTime);
			json1.put("timeoutTime",timeoutTime);
			System.out.println(json1);
			try{
				//HttpClient httpClient = HttpClientBuilder.create().build();
				HttpClient httpClient = HESController.createHttpClient_AcceptsUntrustedCerts();
				HttpPost httpRequest = new HttpPost(HESController.hesurl + "/meterJob");
				httpRequest.setHeader("Content-Type", "application/json");
				httpRequest.setHeader("Authorization", "Basic " + HESController.authStringenc);
				StringEntity body = new StringEntity(json1.toString());
				httpRequest.setEntity(body);
				HttpResponse response = httpClient.execute(httpRequest);
				String res = new BasicResponseHandler().handleResponse(response);
				return res;
			}
			catch(Exception e){
				e.printStackTrace();
				return "ERROR1 " + e.getMessage();
			}
		}
		catch(JSONException e){
			e.printStackTrace();
			return "ERROR2 " + e.getMessage();
		}
	}
	
	public static String manageMeterJob() throws JSONException{
		String meterjob="";
		JSONObject jsobj=new JSONObject();
		jsobj.put("instruction","START");
		try{
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(HESController.hesurl + "/manageMeterJob/" + meterjob);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + HESController.authStringenc);
			StringEntity body = new StringEntity(jsobj.toString());
			httpRequest.setEntity(body);
			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);
			return res;
		}
		catch(Exception e){
			e.printStackTrace();
			return "NA";
		}
	}

	public static String queryMeterJob() throws JSONException{
		String meterjob="";
		try{
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(HESController.hesurl + "/queryMeterJob/" + meterjob);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + HESController.authStringenc);
			//StringEntity body = new StringEntity(jsobj.toString());
			//httpRequest.setEntity(body);
			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);
			return res;
		}
		catch(Exception e){
			e.printStackTrace();
			return "NA";
		}
	}
}
