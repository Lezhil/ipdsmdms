package com.bcits.mdas.utility;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

public class SendSMSFromSI implements Runnable{
	
	private final String api_key;
	private final String message;
	private final String to;
	private final String method;
	private final String sender;
	private final String format;
	private String sURL;
	
	
	
	public SendSMSFromSI(SmsDetailsSIBean detailsSIBean) {
		super();
		this.api_key = detailsSIBean.getApi_key();
		this.message = detailsSIBean.getMessage();
		this.to = detailsSIBean.getTo();
		this.method = detailsSIBean.getMethod();
		this.sender = detailsSIBean.getSender();
		this.sURL = detailsSIBean.getsURL();
		this.format = detailsSIBean.getFormat();
	}



	@Override
	public void run() {
		
		HttpClient client=null;
		GetMethod get=null;
		
		sURL+="?api_key="+api_key;
		sURL+="&method="+method;
		sURL+="&to=91"+to;
		sURL+="&message="+message;
		sURL+="&sender="+sender;
		sURL+="&format="+format;
		
		client = new HttpClient(new MultiThreadedHttpConnectionManager());
		client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		get=new GetMethod(sURL);
		
		int statusCode;
		try {
			statusCode = client.executeMethod(get);
			if (statusCode != HttpStatus.SC_OK) 
				System.out.println("Method failed: " + get.getStatusLine());
				else
				System.out.println("************* MESSAGE SENT SUCCESSFULY *************");
				System.out.println("statusCode --------"+statusCode);
				System.out.println("line 1 ------------"+get.getStatusLine().toString());
				System.out.println("SC_OK -------------"+HttpStatus.SC_OK);
				System.out.println("line 2 ------------"+get.getResponseBodyAsString());
				
				String response=get.getStatusLine().toString();
		
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
