package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SCHEDULER_PARSEFILE_STATUS", schema="mdm_test")
public class MobileGenStatusEntity1 {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int Id;
	
	@Column(name="FILENAME")
	private String filename;
	
	@Column(name="DATES_OF_FILES")
	private Date dates_of_files;
	
	@Column(name="DB_DATE")
	private Date db_date;
	
	@Column(name="BILLMONTH")
	private String billmonth;
	
	@Column(name="METERNO")
	private String meterno;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="FILEPATH")
	private String filepath;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Date getDates_of_files() {
		return dates_of_files;
	}

	public void setDates_of_files(Date dates_of_files) {
		this.dates_of_files = dates_of_files;
	}

	public Date getDb_date() {
		return db_date;
	}

	public void setDb_date(Date db_date) {
		this.db_date = db_date;
	}

	public String getBillmonth() {
		return billmonth;
	}

	public void setBillmonth(String billmonth) {
		this.billmonth = billmonth;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	
	
	
}
