package com.bcits.mdas.utility;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import com.bcits.utility.SmsCredentialsDetailsBean;
import com.businessobjects.report.web.json.JSONObject;


public class SendDocketInfoSMS implements Runnable {
	
	public static JSONObject jsonobj=new JSONObject();

	private final String number;
	private final String message;
	private final String userName;
	private final String gateWayUserName;
	private final String gateWayPassword;
	private final String gateWayURL;
	private final String smsGatewaySid;
	private final String smsGatewayFL;
	private final String smsGatewayGwid;
	
	public SendDocketInfoSMS(SmsCredentialsDetailsBean smsCredentialsDetailsBean)
	{
		this.number = "91"+smsCredentialsDetailsBean.getNumber();
		this.userName = smsCredentialsDetailsBean.getUserName();
		this.message = smsCredentialsDetailsBean.getMessage();
		this.gateWayUserName = smsCredentialsDetailsBean.getSmsGatewayUsername();
		this.gateWayPassword = smsCredentialsDetailsBean.getSmsGatewayPwd();
		this.gateWayURL = smsCredentialsDetailsBean.getSmsGatewayURL();
		this.smsGatewaySid = smsCredentialsDetailsBean.getSmsGatewaySid();
		this.smsGatewayFL = smsCredentialsDetailsBean.getSmsGatewayFL();
		this.smsGatewayGwid = smsCredentialsDetailsBean.getSmsGatewayGwid();
	}
	


	@Override
	public void run()
	{
		 HttpClient client=null;
		 PostMethod post=null;
		 String sURL;
		 client = new HttpClient(new MultiThreadedHttpConnectionManager());
		 client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);//set
		 sURL = gateWayURL;
		 post = new PostMethod(sURL);
		 //give all in string
		 
		 /*SMS Lane
		 post.addParameter("user", gateWayUserName);
		 post.addParameter("password", gateWayPassword);
		 post.addParameter("msisdn", number);
		 post.addParameter("msg", "Dear "+userName+", Your Application has been Submitted on "+regDate+" with Application ID : "+applicationId+". Use this Application ID for tracking the status of your Application.CESC-NCMS.");
		 post.addParameter("sid",smsGatewaySid);
		 post.addParameter("fl", smsGatewayFL);
		 post.addParameter("GWID", smsGatewayGwid);*/
		 
		 
		/*GUPSHUP*/
		post.addParameter("method", smsGatewayGwid);
		post.addParameter("send_to", number);
		post.addParameter("msg", message);//
		post.addParameter("msg_type",smsGatewaySid);
		post.addParameter("userid", gateWayUserName);
		post.addParameter("auth_scheme","plain");
		post.addParameter("password", gateWayPassword);
		post.addParameter("&mask", "CESCTX");
	 	post.addParameter("v", smsGatewayFL);
		post.addParameter("format", "text");
		 
		 
		 
		 
		  //PUSH the URL 
		 String sent = "";
		 try 
			{
				int statusCode = client.executeMethod(post);
				if (statusCode != HttpStatus.SC_OK) 
				BCITSLogger.logger.error("Method failed: " + post.getStatusLine());
				else
				BCITSLogger.logger.info("************* MESSAGE SENT SUCCESSFULY *************");
				BCITSLogger.logger.info("statusCode --------"+statusCode);
				BCITSLogger.logger.info("line 1 ------------"+post.getStatusLine().toString());
				BCITSLogger.logger.info("SC_OK -------------"+HttpStatus.SC_OK);
				BCITSLogger.logger.info("line 2 ------------"+post.getResponseBodyAsString());
				
				sent=post.getResponseBodyAsString().toString();
				if(post.getResponseBodyAsString().contains("Failed"))
					BCITSLogger.logger.info("Failed ------------"+sent);
				else
					BCITSLogger.logger.info("Success ------------"+sent);
				
				System.out.println("SMS Status : "+sent);
				
				jsonobj.put("Msg", statusCode);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				post.releaseConnection();
			}

	}
}
