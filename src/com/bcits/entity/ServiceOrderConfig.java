package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="so_config",schema="meter_data")
public class ServiceOrderConfig {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="event_code")
	private int event_code;
	@Column(name="meter_alarm_type")
	private String meter_alarm_type;
	@Column(name="status")
	private String status;
	@Column(name="so_config_actice_or_deactive_time")
	private Timestamp sctime;
	public int getEvent_code() {
		return event_code;
	}
	public void setEvent_code(int event_code) {
		this.event_code = event_code;
	}
	public String getMeter_alarm_type() {
		return meter_alarm_type;
	}
	public void setMeter_alarm_type(String meter_alarm_type) {
		this.meter_alarm_type = meter_alarm_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getSctime() {
		return sctime;
	}
	public void setSctime(Timestamp sctime) {
		this.sctime = sctime;
	}

}
