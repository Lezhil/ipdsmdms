package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="meter_job_types", schema="meter_data")
public class MeterJobTypes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="job_name")
	private String jobName;
	@Column(name="type")
	private String type;
	@Column(name="active")
	private String active;
	@Column(name="time_stamp")
	private Timestamp timestamp;
	@Column(name="activation_time")
	private String activation_time;
	@Column(name="profile_type")
	private String profile_type;
	@Column(name="cap_per_or_dem_per")
	private String capturePeriodORDemandperiod;
	@Column(name="day_of_month")
	private int day_of_month;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getActivation_time() {
		return activation_time;
	}
	public void setActivation_time(String activation_time) {
		this.activation_time = activation_time;
	}
	public String getProfile_type() {
		return profile_type;
	}
	public void setProfile_type(String profile_type) {
		this.profile_type = profile_type;
	}
	public String getCapturePeriodORDemandperiod() {
		return capturePeriodORDemandperiod;
	}
	public void setCapturePeriodORDemandperiod(String capturePeriodORDemandperiod) {
		this.capturePeriodORDemandperiod = capturePeriodORDemandperiod;
	}
	public int getDay_of_month() {
		return day_of_month;
	}
	public void setDay_of_month(int day_of_month) {
		this.day_of_month = day_of_month;
	}

}
