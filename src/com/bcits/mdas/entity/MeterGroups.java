package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="meter_groups", schema="meter_data")
public class MeterGroups {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="meter_group_name")
	private String meter_group_name;
	@Column(name="meter_group_type")
	private String meter_group_type;
	@Column(name="response_code")
	private String response_code;
	@Column(name="timestamp")
	private Timestamp timestamp;
	@Column(name="status")
	private String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMeter_group_name() {
		return meter_group_name;
	}
	public void setMeter_group_name(String meter_group_name) {
		this.meter_group_name = meter_group_name;
	}
	public String getMeter_group_type() {
		return meter_group_type;
	}
	public void setMeter_group_type(String meter_group_type) {
		this.meter_group_type = meter_group_type;
	}
	public String getResponse_code() {
		return response_code;
	}
	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
