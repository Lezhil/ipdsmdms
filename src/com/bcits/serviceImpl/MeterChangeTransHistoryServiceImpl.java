package com.bcits.serviceImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.MeterChangeTransHistory;
import com.bcits.mdas.mqtt.BillUpdateThread;
import com.bcits.mdas.utility.FilterUnit;
import com.bcits.service.MeterChangeTransHistoryService;

@Repository
public class MeterChangeTransHistoryServiceImpl extends GenericServiceImpl<MeterChangeTransHistory>
		implements MeterChangeTransHistoryService {

	@Override
	public String callService(JSONObject obj, String meteMake) {
		System.out.println("Jsondata--" + obj);
		System.out.println("MeterMake--" + meteMake);
		String result = "";
		String url = "";
	

		if ("GENUS".equalsIgnoreCase(meteMake)) {
			url = FilterUnit.genusMeterChangeUrl;
			System.out.println("  Genus request url  "+ url);
			
			try {
				HttpClient httpClient =HttpClientBuilder.create().build();
				HttpPost httpRequest = new HttpPost(url);
				httpRequest.setHeader("Content-Type", "application/json");
				StringEntity body = new StringEntity(obj.toString());
				System.out.println("Json Request for GENUS: " + obj.toString());
				

				try {
					DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		            Date date = new Date();
		            String dt = dateFormat.format(date);
					writeTrace("meterChange", obj.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				httpRequest.setEntity(body);
				HttpResponse response = httpClient.execute(httpRequest);
				
				String res = new BasicResponseHandler().handleResponse(response);
				System.out.println("Result from Genus: " + res);
				
			
				/*
				 * JSONObject genusObj = new JSONObject(res); String resp =
				 * genusObj.optString("response"); String errorMessage
				 * =genusObj.optString("errorMessage");
				 */
				 	 
				 
				 if("success".equalsIgnoreCase(res))
				 { 
					 result="success";
				 
				 } else{ 
					 result="errorMessage";
				 
				 }
				 
				//JSONArray recs = new JSONArray(res);
			
				
				 
				
			}catch (Exception e) {
				result="failure";
				e.printStackTrace();		
			}
			return result;
		}
		
			
		
		if ("ANALOGICS".equalsIgnoreCase(meteMake)) {
			url = FilterUnit.analogicsMeterChangeUrl;
			System.out.println("analogics request url"+  url);
			
			try {
				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpPost httpRequest = new HttpPost(url);
				httpRequest.setHeader("Content-Type", "application/json");
				StringEntity body = new StringEntity(obj.toString());
				System.out.println("Json Request for Analogics: " + obj.toString());
				
				try {
					DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		            Date date = new Date();
		            String dt = dateFormat.format(date);
					writeTrace("meterChange", obj.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		
				
				httpRequest.setEntity(body);
				HttpResponse response = httpClient.execute(httpRequest);
				String res = new BasicResponseHandler().handleResponse(response);
				System.out.println("Result from Analogics: " + res);
				
			
				  JSONObject anlgObj = new JSONObject(res); 
				  String resp =anlgObj.optString("response"); 
				  String errorMessage =anlgObj.optString("errorMessage"); 
				  
				  
				 if("@ACK".equalsIgnoreCase(resp)) {
					  
				 result="success"; 
				 
				 } 
				  if("NCK".equalsIgnoreCase(resp)) {
					  
					  result=errorMessage; 
					  
					  }
				 
				
				
			} catch (Exception e) {
				result="failure";
				e.printStackTrace();		
			}
		}
		return result;

	}
	
	
	
	

	private void writeTrace(String fileName, String data) {
		try {
			PrintWriter fileWriter = null;
			try {
				fileWriter = new PrintWriter(
						new BufferedWriter(new FileWriter(getFolderPath() + "/" + fileName + ".txt", true)));
				fileWriter.println(data);
			} catch (IOException ex) {
				throw new RuntimeException(ex.getMessage());
			} finally {
				if (fileWriter != null) {
					fileWriter.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getFolderPath() {
		try {
			String month = FilterUnit.logFolder + "/" + new SimpleDateFormat("yyyyMM").format(new Date());
			String day = month + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
			if (FilterUnit.folderExists(month)) {
				if (FilterUnit.folderExists(day)) {
					return day;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
