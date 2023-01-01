package com.bcits.mdas.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
	public static final String url = "http://jvvnlrms.com:7070/bsmartJVVNL/triggerAmiNotificationToMobile";

	public  void notification(String title, String message, String accno,
			String sdocode, String kno, String meterNo) {
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			builder.setParameter("title", title)
					.setParameter("message", message)
					.setParameter("accno", accno)
					.setParameter("sdocode", sdocode)
					.setParameter("kno", kno)
					.setParameter("meterNo", meterNo);
			HttpGet post = new HttpGet(builder.build());
			HttpResponse response = httpClient.execute(post);
			String res = new BasicResponseHandler().handleResponse(response);
			// System.out.println("res: " + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void messageNotify(String title, String message, String accno,
			String sdocode, String kno, String meterNo) {
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			builder.setParameter("title", title)
					.setParameter("message", message)
					.setParameter("accno", accno)
					.setParameter("sdocode", sdocode)
					.setParameter("kno", kno)
					.setParameter("meterNo", meterNo);
			HttpGet post = new HttpGet(builder.build());
			HttpResponse response = httpClient.execute(post);
			String res = new BasicResponseHandler().handleResponse(response);
			// System.out.println("res: " + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) {
		notification("test", "test", "24080571", "2104630", "210463011190",
				"4613402");
	}*/

}
