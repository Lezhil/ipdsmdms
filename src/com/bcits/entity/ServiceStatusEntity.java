package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="service_status",schema="meter_data")
public class ServiceStatusEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="id")
	private Integer id; 
	
	@Column(name="SERVICE_NAME")
	private String service_name;
	
	@Column(name="SERVICE_STATUS")
	private String service_status;
	
	@Column(name="LAST_SER_STA_CHAN_DATE")
	private Timestamp last_ser_sta_chan_date;
	
	@Column(name="ENTRY_BY")
	private String entry_by;
	
	@Column(name="ENTRY_DATE")
	private Timestamp entry_date;
	
	@Column(name="SERVICE_TYPE")
	private String service_type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getService_status() {
		return service_status;
	}

	public void setService_status(String service_status) {
		this.service_status = service_status;
	}

	public Timestamp getLast_ser_sta_chan_date() {
		return last_ser_sta_chan_date;
	}

	public void setLast_ser_sta_chan_date(Timestamp last_ser_sta_chan_date) {
		this.last_ser_sta_chan_date = last_ser_sta_chan_date;
	}

	public String getEntry_by() {
		return entry_by;
	}

	public void setEntry_by(String entry_by) {
		this.entry_by = entry_by;
	}

	public Timestamp getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Timestamp entry_date) {
		this.entry_date = entry_date;
	}
	

}