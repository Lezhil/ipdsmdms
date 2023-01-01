package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transaction_event_log_ami")
public class TransactionEventLogAMI {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="meter_number")
	private String meterNumber;
	@Column(name="rtc")
	private Timestamp rtc;
	@Column(name="tela1")
	private String tela1;
	@Column(name="tela2")
	private String tela2;
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
	public Timestamp getRtc() {
		return rtc;
	}
	public void setRtc(Timestamp rtc) {
		this.rtc = rtc;
	}
	public String getTela1() {
		return tela1;
	}
	public void setTela1(String tela1) {
		this.tela1 = tela1;
	}
	public String getTela2() {
		return tela2;
	}
	public void setTela2(String tela2) {
		this.tela2 = tela2;
	}
	
	

}
