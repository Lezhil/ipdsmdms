package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="service_order_details", schema="meter_data")
@NamedQueries({
	@NamedQuery(name="ServiceOrderDetails.findById", query="SELECT u FROM ServiceOrderDetails u where id=:id"),
	})

public class ServiceOrderDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="office_id")
	private String office_id;
	
	@Column(name="so_number")
	private String so_number;
	
	@Column(name="location_type")
	private String location_type;
	
	@Column(name="location_code")
	private String location_code;
	
	@Column(name="device_type")
	private String device_type;
	
	@Column(name="issue")
	private String issue;
	
	@Column(name="issue_date")
	private Timestamp issue_date;
	
	@Column(name="so_create_date")
	private Timestamp so_create_date;
	
	@Column(name="emails")
	private String emails;
	
	@Column(name="sos_status")
	private String sos_status;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="entry_by")
	private String entry_by;
	
	@Column(name="entry_date")
	private Timestamp entry_date;
	
	@Column(name="update_by")
	private String update_by;
	
	@Column(name="update_date")
	private Timestamp update_date;
	
	@Column(name="entered_to")
	private String entered_to;
	
	@Column(name="corrective_action")
	private String corrective_action;
	
	@Column(name="notified_status")
	private String notified_status;
	
	@Column(name="meter_sr_number")
	private String meter_sr_number;
	
	@Column(name="issue_type")
	private String issue_type;
	
	public String getIssue_type() {
		return issue_type;
	}

	public void setIssue_type(String issue_type) {
		this.issue_type = issue_type;
	}

	public String getMeter_sr_number() {
		return meter_sr_number;
	}

	public void setMeter_sr_number(String meter_sr_number) {
		this.meter_sr_number = meter_sr_number;
	}

	

	public String getEntered_to() {
		return entered_to;
	}

	public void setEntered_to(String entered_to) {
		this.entered_to = entered_to;
	}

	public String getCorrective_action() {
		return corrective_action;
	}

	public void setCorrective_action(String corrective_action) {
		this.corrective_action = corrective_action;
	}

	public String getNotified_status() {
		return notified_status;
	}

	public void setNotified_status(String notified_status) {
		this.notified_status = notified_status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getSo_number() {
		return so_number;
	}

	public void setSo_number(String so_number) {
		this.so_number = so_number;
	}

	public String getLocation_type() {
		return location_type;
	}

	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Timestamp getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(Timestamp issue_date) {
		this.issue_date = issue_date;
	}

	public Timestamp getSo_create_date() {
		return so_create_date;
	}

	public void setSo_create_date(Timestamp so_create_date) {
		this.so_create_date = so_create_date;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getSos_status() {
		return sos_status;
	}

	public void setSos_status(String sos_status) {
		this.sos_status = sos_status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEntry_by() {
		return entry_by;
	}

	public void setEntry_by(String entry_by) {
		this.entry_by = entry_by;
	}

	public Timestamp getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Timestamp entry_date) {
		this.entry_date = entry_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	@Column(name="sms")
	private String sms;
	
	

}
