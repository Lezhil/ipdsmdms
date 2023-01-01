package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ALARMS_DISPATCH_DETAILS",schema="meter_data")
public class AlarmDispatchEntity {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rec_id")
	private int rec_id;

	@Column(name="office_id")
	private String office_id;
	
	@Column(name="loc_type")
	private String loc_type;
	
	@Column(name="loc_identity")
	private String loc_identity;
	
	@Column(name="loc_name")
	private String loc_name;
	
	@Column(name="alarm_setting")
	private String alarm_setting;
	
	@Column(name="alarm_type")
	private String alarm_type;
	
	@Column(name="alarm_name")
	private String alarm_name;
	
	@Column(name="alarm_priority")
	private String alarm_priority;
	
	@Column(name="user_type")
	private String user_type;
	
	@Column(name="user_identity")
	private String user_identity;
	
	@Column(name="user_name")
	private String user_name;
	
	@Column(name="email_notification")
	private Boolean email_notification;
	
	@Column(name="sms_notification")
	private Boolean sms_notification;
	
	@Column(name="email")
	private String email;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="notify_date")
	private Timestamp notify_date;
	
	@Column(name="notify_status")
	private String notify_status;
	
	@Column(name="error_des")
	private String error_des;
	@Column(name="town_id")
	private String townId;
	
	@Column(name="time_stamp")
	private Timestamp time_stamp;

	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public int getRec_id() {
		return rec_id;
	}

	public void setRec_id(int rec_id) {
		this.rec_id = rec_id;
	}

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getLoc_type() {
		return loc_type;
	}

	public void setLoc_type(String loc_type) {
		this.loc_type = loc_type;
	}

	public String getLoc_identity() {
		return loc_identity;
	}

	public void setLoc_identity(String loc_identity) {
		this.loc_identity = loc_identity;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getAlarm_setting() {
		return alarm_setting;
	}

	public void setAlarm_setting(String alarm_setting) {
		this.alarm_setting = alarm_setting;
	}

	public String getAlarm_type() {
		return alarm_type;
	}

	public void setAlarm_type(String alarm_type) {
		this.alarm_type = alarm_type;
	}

	public String getAlarm_name() {
		return alarm_name;
	}

	public void setAlarm_name(String alarm_name) {
		this.alarm_name = alarm_name;
	}

	public String getAlarm_priority() {
		return alarm_priority;
	}

	public void setAlarm_priority(String alarm_priority) {
		this.alarm_priority = alarm_priority;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUser_identity() {
		return user_identity;
	}

	public void setUser_identity(String user_identity) {
		this.user_identity = user_identity;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Boolean getEmail_notification() {
		return email_notification;
	}

	public void setEmail_notification(Boolean email_notification) {
		this.email_notification = email_notification;
	}

	public Boolean getSms_notification() {
		return sms_notification;
	}

	public void setSms_notification(Boolean sms_notification) {
		this.sms_notification = sms_notification;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Timestamp getNotify_date() {
		return notify_date;
	}

	public void setNotify_date(Timestamp notify_date) {
		this.notify_date = notify_date;
	}

	public String getNotify_status() {
		return notify_status;
	}

	public void setNotify_status(String notify_status) {
		this.notify_status = notify_status;
	}

	public String getError_des() {
		return error_des;
	}

	public void setError_des(String error_des) {
		this.error_des = error_des;
	}

	public Timestamp getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}
	

}
