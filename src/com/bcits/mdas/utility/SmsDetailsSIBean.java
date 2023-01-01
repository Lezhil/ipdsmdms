package com.bcits.mdas.utility;

import java.io.Serializable;
import java.util.ResourceBundle;

public class SmsDetailsSIBean implements Serializable {

private static final long serialVersionUID = 1L;
	
	private String to;
	
	private String message;
	
	private final String sURL = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SISMS.Gateway.URL");
	
	private final String api_key = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SISMS.Gateway.APP_KEY");
	
	private final String method = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SISMS.Gateway.METHOD");
	
	private final String sender = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SISMS.Gateway.SENDOR");
	
	private final String format = ResourceBundle.getBundle("EmailAndSmsCredentials").getString("SISMS.Gateway.FORMAT");

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public String getsURL() {
		return sURL;
	}

	public String getApi_key() {
		return api_key;
	}

	public String getMethod() {
		return method;
	}

	public String getSender() {
		return sender;
	}

	public String getFormat() {
		return format;
	}
	
	
}
