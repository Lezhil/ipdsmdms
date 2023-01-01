package com.bcits.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="alarms_hst",schema="meter_data")
public class AlarmHistory implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="office_id")
	private String officeId;
	@Column(name="location_type")
	private String locatioTtype;
	@Column(name="location_code")
	private String locationCode;
	@Column(name="town_id")
	private String townId;
	@Column(name="location_name")
	private String locationName;
	@Column(name="alarm_setting")
	private String alarmSetting;
	@Column(name="alarm_type")
	private String alarmType;
	@Column(name="alarm_name")
	private String alarmName;@Column(name="alarm_priority")
	private String alarm_priority;
	@Column(name="alarm_date")
	private Timestamp alarm_date;
	@Column(name="time_stamp")
	private Timestamp time_stamp;
	@Column(name="ack_msg")
	private String ack_msg;
	@Column(name="ack_date")
	private Timestamp ackDate;
	@Column(name="ack_by")
	private String ackBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getLocatioTtype() {
		return locatioTtype;
	}
	public void setLocatioTtype(String locatioTtype) {
		this.locatioTtype = locatioTtype;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getAlarmSetting() {
		return alarmSetting;
	}
	public void setAlarmSetting(String alarmSetting) {
		this.alarmSetting = alarmSetting;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getAlarmName() {
		return alarmName;
	}
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}
	public String getAlarm_priority() {
		return alarm_priority;
	}
	public void setAlarm_priority(String alarm_priority) {
		this.alarm_priority = alarm_priority;
	}
	public Timestamp getAlarm_date() {
		return alarm_date;
	}
	public void setAlarm_date(Timestamp alarm_date) {
		this.alarm_date = alarm_date;
	}
	public Timestamp getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}
	public String getAck_msg() {
		return ack_msg;
	}
	public void setAck_msg(String ack_msg) {
		this.ack_msg = ack_msg;
	}
	public Timestamp getAckDate() {
		return ackDate;
	}
	public void setAckDate(Timestamp timestamp) {
		this.ackDate = timestamp;
	}
	public String getAckBy() {
		return ackBy;
	}
	public void setAckBy(String ackBy) {
		this.ackBy = ackBy;
	}
	public String getTownId() {
		return townId;
	}
	public void setTownId(String townId) {
		this.townId = townId;
	}
	@Override
	public String toString() {
		return "AlarmHistory [id=" + id + ", officeId=" + officeId
				+ ", locatioTtype=" + locatioTtype + ", locationCode="
				+ locationCode + ", locationName=" + locationName
				+ ", alarmSetting=" + alarmSetting + ", alarmType=" + alarmType
				+ ", alarmName=" + alarmName + ", alarm_priority="
				+ alarm_priority + ", alarm_date=" + alarm_date
				+ ", time_stamp=" + time_stamp + ", ack_msg=" + ack_msg
				+ ", ackDate=" + ackDate + ", ackBy=" + ackBy + "]";
	}
	

}
