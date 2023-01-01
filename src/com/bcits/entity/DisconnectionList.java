package com.bcits.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="disconnection_list" , schema="meter_data")
public class DisconnectionList {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="METER_NO")
	private String  meterNo;
	
	@Column(name="acc_no")
	private String  accNo;
	
	@Column(name="balance")
	private String  balance;
	
	@Column(name="name")
	private String  name;
		
	@Column(name="tariff_code")
	private String  tariffcode;
	
	@Column(name="feeder_code")
	private String  feederCode;
		
	@Column(name="final_reading")
	private String  finalReading;
	
	@Column(name="connection_status")
	private String  connectionStatus; 
	
	@Column(name="sdocode")
	private String  sdocode; 
	
	@Column(name="kno")
	private String  kno; 
	
	@Column(name="reconnection_at_amr")
	private String  reconnectionAtAMR; 
	
	@Column(name="disconnection_at_amr")
	private String  disconnectionAtAMR; 
		
	@Column(name="request_type")
	private String  requestType; 
	
	@Column(name="requested_date")
	private Timestamp  requestedDate; 
	
	@Column(name="reconnection_date")
	private Date  reconnectionDate; 
	
	@Column(name="disconnection_date")
	private Date  disconnectionDate;
	
	@Column(name="lastpayment_date")
	private Timestamp  lastpayment;
		
	@Column(name="due_date")
	private Timestamp  duedate; 
	
	public String getFinalReading() {
		return finalReading;
	}

	public void setFinalReading(String finalReading) {
		this.finalReading = finalReading;
	}

	public Date getDisconnectionDate() {
		return disconnectionDate;
	}

	public void setDisconnectionDate(Date disconnectionDate) {
		this.disconnectionDate = disconnectionDate;
	}

	public Timestamp getLastpayment() {
		return lastpayment;
	}

	public void setLastpayment(Timestamp lastpayment) {
		this.lastpayment = lastpayment;
	}

	public Timestamp getDuedate() {
		return duedate;
	}

	public void setDuedate(Timestamp duedate) {
		this.duedate = duedate;
	}

	public String getmeterNo() {
		return meterNo;
	}

	public void setmeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTariffcode() {
		return tariffcode;
	}

	public void setTariffcode(String tariffcode) {
		this.tariffcode = tariffcode;
	}

	public String getFeederCode() {
		return feederCode;
	}

	public void setFeederCode(String feederCode) {
		this.feederCode = feederCode;
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public String getSdocode() {
		return sdocode;
	}

	public void setSdocode(String sdocode) {
		this.sdocode = sdocode;
	}

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}

	public String getReconnectionAtAMR() {
		return reconnectionAtAMR;
	}

	public void setReconnectionAtAMR(String reconnectionAtAMR) {
		this.reconnectionAtAMR = reconnectionAtAMR;
	}

	public String getDisconnectionAtAMR() {
		return disconnectionAtAMR;
	}

	public void setDisconnectionAtAMR(String disconnectionAtAMR) {
		this.disconnectionAtAMR = disconnectionAtAMR;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Timestamp getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Timestamp requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Date getReconnectionDate() {
		return reconnectionDate;
	}

	public void setReconnectionDate(Date reconnectionDate) {
		this.reconnectionDate = reconnectionDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}


}
