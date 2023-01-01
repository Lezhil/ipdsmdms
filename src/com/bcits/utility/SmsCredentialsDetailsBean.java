package com.bcits.utility;

import java.io.Serializable;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class SmsCredentialsDetailsBean implements Serializable {

	/**
	 * 
	 */
private static final long serialVersionUID = 1L;
	
	private String number;
	
	private String userName;
	
	private String message;
	
	private final String smsGatewayURL = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SMS.Gateway.URL");
	
	private final String smsGatewaySid = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SMS.Gateway.sid");
	
	private final String smsGatewayFL = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SMS.Gateway.fl");
	
	private final String smsGatewayGwid = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SMS.Gateway.GWID");
	
	private final String smsGatewayUsername = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SMS.Gateway.Username");
	
	private final String smsGatewayPwd = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SMS.Gateway.Password");

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSmsGatewayURL() {
		return smsGatewayURL;
	}

	public String getSmsGatewaySid() {
		return smsGatewaySid;
	}

	public String getSmsGatewayFL() {
		return smsGatewayFL;
	}

	public String getSmsGatewayGwid() {
		return smsGatewayGwid;
	}

	public String getSmsGatewayUsername() {
		return smsGatewayUsername;
	}

	public String getSmsGatewayPwd() {
		return smsGatewayPwd;
	}
	
}
