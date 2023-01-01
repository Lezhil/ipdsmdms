package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PARSEFILESTATUS")
public class MobileGenStatusEntity {
	@Id
	@SequenceGenerator(name = "Id", sequenceName = "MOBILE_GEN_MTR_STATUS_SEQ")
	@GeneratedValue(generator = "Id")	

	@Column(name="ID")
	private int Id;
	
	@Column(name="CREATEDATE")
	private Date createdate;
	
	@Column(name="BILLMONTH")
	private String billmonth;
	
	@Column(name="METERNO")
	private String meterno;
	
	@Column(name="STATUS")
	private String status;

	@Column(name="FILENAME")
	private String filename;
	
	@Column(name="FILEPATH")
	private String filepath;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
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
