package com.bcits.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="alarm_definition",schema="meter_data")
public class AlarmDefinition implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	

	@Column(name="location_type")
	private String locationtype;
	
	@Column(name="location_code")
	private String locationcode;
	
	@Column(name="events")
	private String events;

	@Column(name="location_id")
	private String locationid;
	
	@Column(name="communication")
	private Boolean communication;
	
	@Column(name="validations")
	private String validations;
	
	@Column(name="email_ids")
	private String emailids;
	
	@Column(name="sms")
	private String sms;
	
	@Column(name="entry_by")
	private String entryby;
	
	@Column(name="priority")
	private String priority;
	
	@Column(name="alarm_name")
	private String alarmname;
	
	
	@Column(name="entry_date")
	private Timestamp entrydate;
	
	@Column(name="update_by")
	private String updateby;
	
	@Column(name="update_date")
	private Timestamp updatedate;
	
	@Column(name="town_id")
	private String townId;
	
	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getAlarmname() {
		return alarmname;
	}

	public void setAlarmname(String alarmname) {
		this.alarmname = alarmname;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Boolean getCommunication() {
		return communication;
	}

	public void setCommunication(Boolean communication) {
		this.communication = communication;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

	public Timestamp getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}


	public String getLocationtype() {
		return locationtype;
	}

	public void setLocationtype(String locationtype) {
		this.locationtype = locationtype;
	}

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	

	

	public String getValidations() {
		return validations;
	}

	public void setValidations(String validations) {
		this.validations = validations;
	}

	public String getEmailids() {
		return emailids;
	}

	public void setEmailids(String emailids) {
		this.emailids = emailids;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getEntryby() {
		return entryby;
	}

	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}

	

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String list) {
		this.locationid = list;
	}
	@Override
	public String toString() {
		return "AlarmDefinition [id=" + id + ", locationtype=" + locationtype
				+ ", locationcode=" + locationcode + ", events=" + events
				+ ", locationid=" + locationid + ", communication="
				+ communication + ", validations=" + validations
				+ ", emailids=" + emailids + ", sms=" + sms + ", entryby="
				+ entryby + ", priority=" + priority + ", alarmname="
				+ alarmname + ", entrydate=" + entrydate + ", updateby="
				+ updateby + ", updatedate=" + updatedate + "]";
	}

	
	
	
	

}
