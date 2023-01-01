package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meter_issue_or_return_transaction", schema = "meter_data")
public class MeterIssueOrReturnTransaction {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	@Column(name = "suid")
	private String suid;
	@Column(name = "trans_type")
	private String transType;
	@Column(name = "meterno")
	private String meterNo;
	@Column(name = "meter_status")
	private String meterStatus;
	@Column(name = "insert_by")
	private String insertBy;
	@Column(name = "insert_time")
	private Timestamp insertTime;
	@Column(name="issue_or_return_time")
	private Timestamp issueOrReturnTime;
	@Column(name="store_loc")
	private String storeLoc;
	
	
	public String getSuid() {
		return suid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSuid(String suid) {
		this.suid = suid;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getMeterStatus() {
		return meterStatus;
	}

	public void setMeterStatus(String meterStatus) {
		this.meterStatus = meterStatus;
	}

	public String getInsertBy() {
		return insertBy;
	}

	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy;
	}

	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Timestamp getIssueOrReturnTime() {
		return issueOrReturnTime;
	}

	public void setIssueOrReturnTime(Timestamp issueOrReturnTime) {
		this.issueOrReturnTime = issueOrReturnTime;
	}

	public String getStoreLoc() {
		return storeLoc;
	}

	public void setStoreLoc(String storeLoc) {
		this.storeLoc = storeLoc;
	}

}
