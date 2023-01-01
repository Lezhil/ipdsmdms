package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="meter_group_meters",schema="meter_data")
public class MeterGroupMeters {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="meter_number")
	private String meter_number;
	@Column(name="meter_group_name")
	private String meter_group_name;
	@Column(name="time_stamp")
	private Timestamp time_stamp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMeter_number() {
		return meter_number;
	}
	public void setMeter_number(String meter_number) {
		this.meter_number = meter_number;
	}
	public String getMeter_group_name() {
		return meter_group_name;
	}
	public void setMeter_group_name(String meter_group_name) {
		this.meter_group_name = meter_group_name;
	}
	public Timestamp getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}
	

}
