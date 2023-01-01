package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cmri_upload_status",schema="meter_data")
public class CmriUploadStatusEntity {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// DEFAULT VALUE IS 0
	
	@Column(name = "time_stamp")
	private Timestamp timeStamp;
	
	@Column(name="meter_number")
	private String meternumber;
	
	@Column(name="month")
	private String month;
	
	@Column(name="file_date")
	private Timestamp filedate;

	@Column(name = "insta_status")
	private Integer instaStatus = 0;// DEFAULT VALUE IS 0

	@Column(name = "load_status")
	private Integer loadStatus = 0;// DEFAULT VALUE IS 0

	@Column(name = "event_status")
	private Integer eventStatus = 0;// DEFAULT VALUE IS 0

	@Column(name = "bill_status")
	private Integer billStatus = 0;// DEFAULT VALUE IS 0

	@Column(name = "upload_status")
	private String uploadStatus;// DEFAULT VALUE IS 0

	@Column(name = "fail_reason")
	private String failReason;

	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "parsed")
	private Integer parsed;// DEFAULT VALUE IS 0
	
	@Column(name="read_from")
	private String readfrom;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMeternumber() {
		return meternumber;
	}

	public void setMeternumber(String meternumber) {
		this.meternumber = meternumber;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Timestamp getFiledate() {
		return filedate;
	}

	public void setFiledate(Timestamp filedate) {
		this.filedate = filedate;
	}

	public Integer getInstaStatus() {
		return instaStatus;
	}

	public void setInstaStatus(Integer instaStatus) {
		this.instaStatus = instaStatus;
	}

	public Integer getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(Integer loadStatus) {
		this.loadStatus = loadStatus;
	}

	public Integer getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(Integer eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getParsed() {
		return parsed;
	}

	public void setParsed(Integer parsed) {
		this.parsed = parsed;
	}

	public String getReadfrom() {
		return readfrom;
	}

	public void setReadfrom(String readfrom) {
		this.readfrom = readfrom;
	}
	
}
