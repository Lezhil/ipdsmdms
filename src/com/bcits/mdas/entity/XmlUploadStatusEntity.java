package com.bcits.mdas.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "XML_UPLOAD_STATUS", schema = "METER_DATA", uniqueConstraints = {@UniqueConstraint(columnNames = { "METER_NUMBER", "FILE_DATE" }) })

@NamedQueries({
	@NamedQuery(name="XmlUploadStatusEntity.getByMeterAndDate", query="Select x from XmlUploadStatusEntity x where x.myKey.meterNumber= :meterNumber and to_char(x.myKey.fileDate,'YYYY-MM-DD')=:fileDate"),
})
public class XmlUploadStatusEntity {

	@EmbeddedId // FOR MAKING UNIQUE KEY
	private KeyUplaodStatus myKey; // METER_NUMBER, FILE_DATE

	@Column(name = "time_stamp")
	private Timestamp timeStamp;

	@Column(name = "insta_status")
	private Integer instaStatus = 0;// DEFAULT VALUE IS 0

	@Column(name = "load_status")
	private Integer loadStatus = 0;// DEFAULT VALUE IS 0

	@Column(name = "event_status")
	private Integer eventStatus = 0;// DEFAULT VALUE IS 0

	@Column(name = "bill_status")
	private Integer billStatus = 0;// DEFAULT VALUE IS 0

	@Column(name = "upload_status")
	private Integer uploadStatus = 0;// DEFAULT VALUE IS 0

	@Column(name = "fail_reason")
	private String failReason;

	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "create_status")
	private Integer create_status = 0;
	
	
	public Integer getCreate_status() {
		return create_status;
	}

	public void setCreate_status(Integer create_status) {
		this.create_status = create_status;
	}

	public KeyUplaodStatus getMyKey() {
		return myKey;
	}

	public void setMyKey(KeyUplaodStatus myKey) {
		this.myKey = myKey;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
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

	public Integer getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(Integer uploadStatus) {
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



	@Embeddable
	public static class KeyUplaodStatus implements Serializable {

		private static final long serialVersionUID = 1L;
		@Column(name = "meter_number")
		private String meterNumber;

		@Column(name = "file_date")
		private Date fileDate;

		public KeyUplaodStatus(String meterNumber, Date fileDate) {
			super();
			this.meterNumber = meterNumber;
			this.fileDate = fileDate;
		}

		public KeyUplaodStatus() {

		}
		public void setMeterNumber(String meterNumber) {
			this.meterNumber = meterNumber;
		}
		public String getMeterNumber() {
			return meterNumber;
		}

		public void setFileDate(Date fileDate) {
			this.fileDate = fileDate;
		}
		public Date getFileDate() {
			return fileDate;
		}
		

	}

	public XmlUploadStatusEntity() {

	}

}
