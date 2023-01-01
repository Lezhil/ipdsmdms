package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ondemand_transaction",schema="meter_data")
public class OnDemandTransaction {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="type")
	private String type;
	@Column(name="meternumber")
	private String meterNumber;
	@Column(name="time_stamp")
	private Timestamp ondemTime;
	
	@Column(name="transID")
	private String transID;
	
	public String getTransID() {
		return transID;
	}
	public void setTransID(String transID) {
		this.transID = transID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public Timestamp getOndemTime() {
		return ondemTime;
	}
	public void setOndemTime(Timestamp ondemTime) {
		this.ondemTime = ondemTime;
	}
	

}
