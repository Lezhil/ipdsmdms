package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="legacy_tracker" , schema="meter_data")
public class LegacyTrackerEntity {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public int getResponsesize() {
		return responsesize;
	}

	public void setResponsesize(int responsesize) {
		this.responsesize = responsesize;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getReadtime() {
		return readtime;
	}

	public void setReadtime(Timestamp readtime) {
		this.readtime = readtime;
	}

	public String getMonthyear() {
		return monthyear;
	}

	public void setMonthyear(String monthyear) {
		this.monthyear = monthyear;
	}

	@Column(name="servicename")
	private String servicename;
	
	@Column(name="responsesize")
	private int responsesize;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="readtime")
	private Timestamp readtime;
	
	@Column(name="monthyear")
	private String monthyear;

	

}
