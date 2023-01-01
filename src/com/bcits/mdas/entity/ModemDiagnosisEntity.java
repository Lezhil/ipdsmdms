package com.bcits.mdas.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@Entity
@Table(name = "MODEM_DIAGNOSIS", schema = "METER_DATA")
@NamedQueries({

})
public class ModemDiagnosisEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "METER_NUMBER")
	private String meterNumber;

	@Column(name = "imei")
	private String imei;

	@Column(name = "DATE")
	private Date date;

	@Column(name = "TRACKED_TIME")
	private Timestamp trackedTime;

	@Column(name = "TIME_STAMP")
	private Timestamp timeStamp;

	@Column(name = "METER_TYPE")
	private String meterType;

	@Column(name = "DATA_TYPE")
	private String dataType;

	@Column(name = "DIAG_TYPE")
	private String diagType;

	@Column(name = "STATUS")
	private String status;

	public ModemDiagnosisEntity() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getTrackedTime() {
		return trackedTime;
	}

	public void setTrackedTime(Timestamp trackedTime) {
		this.trackedTime = trackedTime;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDiagType() {
		return diagType;
	}

	public void setDiagType(String diagType) {
		this.diagType = diagType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
