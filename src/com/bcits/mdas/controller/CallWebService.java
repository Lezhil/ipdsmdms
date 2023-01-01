/**
 * 
 */
package com.bcits.mdas.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author User
 *
 */
public class CallWebService {

	/**
	 * @param args
	 */
	static String url = "http://192.168.10.200:8050/virtualMeter/meterSamples";
	
	public static void mains(String[] args) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("interValTime", "23-06-2020 16:35:00");
		obj.put("startMeterNo", "M0000000");
		obj.put("endtMeterNo", "M0000005");
		sendRequest(url, obj.toString());
	}
	
	public static void main(String[] args) {

//			System.out.println(count+"-----"+startId);
//		String url = "http://192.168.10.200:8050/virtualMeter/meterSamples";
		String url = "http://localhost:8050/virtualMeter/meterSamples";

//				System.out.println("Meter Sample URL : "+url);
		String result = null;

		try {

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			JSONObject obj = new JSONObject();
			obj.put("interValTime", "23-06-2020 16:35:00");
			obj.put("startMeterNo", "M0000000");
			obj.put("endMeterNo", "M0009999");

			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);
			HttpResponse response = httpClient.execute(httpRequest);
			result = new BasicResponseHandler().handleResponse(response);
			System.out.println(result);

		} catch (Exception e) {
//					result=null;
			e.printStackTrace();
		}

	}
	private static String sendRequest(String  urlStr, String data)  throws Exception {
	    URL url = new URL(urlStr);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	    conn.setUseCaches(false);
	    conn.setDoInput(true);
	    conn.setDoOutput(true);
	    conn.setRequestMethod("POST");
	    
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setRequestProperty("Accept", "application/json");
    	
        
	    try {
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();

	        BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));

	        String output;
	        StringBuilder builder = new StringBuilder();
	        while ((output = br.readLine()) != null) {
	        	builder.append(output);
	        }
	        System.out.println(builder);
	        return builder.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	       return e.getMessage();
	    }

	}
}
