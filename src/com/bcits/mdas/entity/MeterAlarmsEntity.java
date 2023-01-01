package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="meter_alarm",schema="meter_data")
public class MeterAlarmsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="meter_number")
	private String meterNumber;
	@Column(name="alarm_active")
	private String alarmActive;
	
	@Column(name="meter_alarm_id")
	private int meterAlarmId;
	@Column(name="alarm_time")
	private Timestamp alarmTime;
	@Column(name="sequence_number")
	private String sequenceNumber;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public String getAlarmActive() {
		return alarmActive;
	}
	public void setAlarmActive(String alarmActive) {
		this.alarmActive = alarmActive;
	}
	public int getMeterAlarmId() {
		return meterAlarmId;
	}
	public void setMeterAlarmId(int meterAlarmId) {
		this.meterAlarmId = meterAlarmId;
	}
	public Timestamp getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Timestamp alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	
}
