package com.bcits.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="xml_upload_summary",schema="meter_data")
public class XmlUploadSummary {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int Id;
	
	@Column(name="meterno")
	private String meterno;
	
	@Column(name="filename")
	private String filename;
	
	@Column(name="uploaddate")
	private Timestamp uploaddate;
	
	@Column(name="g2date")
	private Timestamp g2date;

	@Column(name="g3date")
	private Timestamp g3date;
	
	@Column(name="status")
	private String status;
	
	@Column(name="month")
	private int month;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Timestamp getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(Timestamp uploaddate) {
		this.uploaddate = uploaddate;
	}

	public Timestamp getG2date() {
		return g2date;
	}

	public void setG2date(Timestamp g2date) {
		this.g2date = g2date;
	}

	public Timestamp getG3date() {
		return g3date;
	}

	public void setG3date(Timestamp g3date) {
		this.g3date = g3date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	

	

	
	
}
