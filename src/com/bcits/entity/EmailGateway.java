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
@Table(name="EMAILGATEWAYSETTINGS",schema="meter_data")
@NamedQueries({	
	
	@NamedQuery(name="EmailGateway.findUser",query="SELECT s FROM EmailGateway s "),
	@NamedQuery(name="EmailGateway.findUserById",query="SELECT s FROM EmailGateway s where s.id=:id")
	
	})

public class EmailGateway {
	
	
	@Id
	@SequenceGenerator(name = "emailGatewaySettingstId", sequenceName = "EMAILGATEWAYSETTINGS_ID")
	 @GeneratedValue(generator = "emailGatewaySettingstId")
	@Column(name="ID")
	private int id;
	
	@Column(name="SMTPHOST")
	private String smtpHost;
	
	@Column(name="SMTPPORT")
	private String smtpPort;

	@Column(name="SSLENABLED")
	private String ssl;
	
	@Column(name="SMTPAUTH")
	private String smtpAuth;
	
	@Column(name="SENDERMAILID")
	private String mailId;
	
	@Column(name="SENDERMAILPASSWORD")
	private String mailPassword;
	
	@Column(name="LOGGEDUSER")
	private String loggedUser;
	
	public EmailGateway()
	{
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSsl() {
		return ssl;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
	}

	public String getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	
	
}
