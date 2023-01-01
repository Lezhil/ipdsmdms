package com.bcits.mdas.mqtt;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GenusServices {

	@RequestMapping(value = "/getNodesGenus", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String getNodes() {
		String url = "/api/hierarchy/GetNodes";
		try {
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			
			JSONObject obj = new JSONObject();
			obj.put("ClientId", 1);
			obj.put("ClientSecret", "HK36Wq5BoA3GdW/xeaVfrCfcDoYr7lF9");
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	@RequestMapping(value = "/syncMasterGenus", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String jobs() {
		String url = "/api/hierarchy/GetMeters";
		try {
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			
			JSONObject obj = new JSONObject();
			obj.put("ClientId", 1);
			obj.put("ClientSecret", "nPt2vbEOj5C4Y0uynxC5bg==");
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
}
