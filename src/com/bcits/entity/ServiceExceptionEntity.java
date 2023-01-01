package com.bcits.entity;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="service_exception_log",schema="meter_data")
public class ServiceExceptionEntity {
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="id")
	private int id;
	
	
	@Column(name="SERVICE_NAME")
	private String service_name;
	
	@Column(name="REQUESTER")
	private String requester;
	
	@Column(name="PROVIDER")
	private String provider;
	
	@Column(name="EXCEPTION")
	private String exception;
	
	@Column(name="NOTIFIED")
	private Boolean notified;
	
	@Column(name="ACK_STATUS")
	private Boolean ack_status;
	
	@Column(name="ACK_BY")
	private String ack_by;
	
	
	@Column(name="TIME_STAMP")
	private Timestamp time_stamp;
	
	@Column(name="EXCEPTIONTIME")
	private Timestamp exceptiontime;
	 
	@Column(name="ACKDATE")
	private Timestamp ackdate;
	
	@Column(name="ACKMESSAGE")
	private String ackmessage;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Boolean getNotified() {
		return notified;
	}

	public void setNotified(Boolean notified) {
		this.notified = notified;
	}

	public Boolean getAck_status() {
		return ack_status;
	}

	public void setAck_status(Boolean ack_status) {
		this.ack_status = ack_status;
	}

	public String getAck_by() {
		return ack_by;
	}

	public void setAck_by(String ack_by) {
		this.ack_by = ack_by;
	}

	public Timestamp getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}

	public Timestamp getExceptiontime() {
		return exceptiontime;
	}

	public void setExceptiontime(Timestamp exceptiontime) {
		this.exceptiontime = exceptiontime;
	}

	public Timestamp getAckdate() {
		return ackdate;
	}

	public void setAckdate(Timestamp ackdate) {
		this.ackdate = ackdate;
	}

	public String getAckmessage() {
		return ackmessage;
	}

	public void setAckmessage(String ackmessage) {
		this.ackmessage = ackmessage;
	}

}
