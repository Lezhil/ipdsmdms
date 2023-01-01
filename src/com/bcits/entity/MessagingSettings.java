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
@Table(name="MESSAGINGSETTINGS",schema="meter_data")

@NamedQueries({
	@NamedQuery(name="MessagingSettings.findUser",query="select s from MessagingSettings s ")
})
public class MessagingSettings {

	@Id
	 @SequenceGenerator(name = "messageSettingsId", sequenceName = "MESSAGINGSETTINGS_ID")
	 @GeneratedValue(generator = "messageSettingsId")
	@Column(name="ID")
	private int id;
	
	@Column(name="SMSALERTENABLED")
	private String smsAlert;
	
	
	@Column(name="EMAILALERTENABLED")
	private String emailAlert;
	
	@Column(name="LOGGEDUSER")
	private String loggedUser;
	
	@Column(name="SCHEDULEDSMSALERTTIME")
	private String smsScheduleTime;
	
	@Column(name="SCHEDULEDEMAILALERTTIME")
	private String emailScheduleTime;
	
	@Column(name="SCHEDULEDSMSALERTENABLED")
	private String smsScheduleAlert;
	
	@Column(name="SCHEDULEDEMAILALERTENABLED")
	private String emailScheduleAlert;
	
	
	public String getSmsScheduleTime() {
		return smsScheduleTime;
	}



	public void setSmsScheduleTime(String smsScheduleTime) {
		this.smsScheduleTime = smsScheduleTime;
	}



	public String getEmailScheduleTime() {
		return emailScheduleTime;
	}



	public void setEmailScheduleTime(String emailScheduleTime) {
		this.emailScheduleTime = emailScheduleTime;
	}



	public String getSmsScheduleAlert() {
		return smsScheduleAlert;
	}



	public void setSmsScheduleAlert(String smsScheduleAlert) {
		this.smsScheduleAlert = smsScheduleAlert;
	}



	public String getEmailScheduleAlert() {
		return emailScheduleAlert;
	}



	public void setEmailScheduleAlert(String emailScheduleAlert) {
		this.emailScheduleAlert = emailScheduleAlert;
	}



	public MessagingSettings()
	{
		
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getSmsAlert() {
		return smsAlert;
	}



	public void setSmsAlert(String smsAlert) {
		this.smsAlert = smsAlert;
	}



	public String getEmailAlert() {
		return emailAlert;
	}



	public void setEmailAlert(String emailAlert) {
		this.emailAlert = emailAlert;
	}



	public String getLoggedUser() {
		return loggedUser;
	}



	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}
	
	
	
}
