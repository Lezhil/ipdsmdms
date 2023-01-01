package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SMSGATEWAYSETTINGS",schema="meter_data")
@NamedQueries({	
	
	@NamedQuery(name="SmsGateway.findUser",query="SELECT s FROM SmsGateway s")
	
	})


public class SmsGateway {

	
	@Id
	 @SequenceGenerator(name = "smsGatewaySettingstId", sequenceName = "SMSGATEWAYSETTINGS_ID")
	 @GeneratedValue(generator = "smsGatewaySettingstId")
	@Column(name="ID")
	private int id;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="PASSWORD")
	private String password;
	
	
	@Column(name="URL")
	private String url;
	
	@Column(name="MOBILENO")
	private String mobileno;
	
	@Column(name="SENDERID")
	private String senderId;
	
	@Column(name="LOGGEDUSER")
	private String loggedUser;
	
	public SmsGateway()
	{
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}
	
	
	
}
