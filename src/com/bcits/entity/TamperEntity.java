package com.bcits.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name ="TAMPER",schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="TamperEntity.getTamperType",query="SELECT DISTINCT(tamperType) from TamperEntity where tamperType is not null ORDER BY tamperType"),
	
	@NamedQuery(name="TamperEntity.checkMeterNo",query="SELECT count(*) from TamperEntity where rdngMonth=:rdngMonth and meterNo=:meterNo"),
})
public class TamperEntity {
	
	@Id
	/*@SequenceGenerator(name="tamper_seq",sequenceName="tamper_seq")
	@GeneratedValue(generator="tamper_seq")*/
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	
	@Column(name="ID")
	private long id;
	
	@Column(name="METERNO")
	private String meterNo;
	
	@Column(name="TAMPERTYPE")
	private String tamperType;
	
	@Column(name="TDATE")
	private String tDate;
	
	@Column(name="OCCURRED_DATE")
	private Date occurredDate;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="RESTORED_DATE")
	private Date restoredDate;
	
	@Column(name="CURRENT_STATUS")
	private String currentStatus;
	
	@Column(name="TIMESTAMP")
	private String timeStamp;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="RDNGMONTH")
	private int rdngMonth;
	
	public Date getOccurredDate() {
		return occurredDate;
	}

	public void setOccurredDate(Date occurredDate) {
		this.occurredDate = occurredDate;
	}

	public Date getRestoredDate() {
		return restoredDate;
	}

	public void setRestoredDate(Date restoredDate) {
		this.restoredDate = restoredDate;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getTamperType() {
		return tamperType;
	}

	public void setTamperType(String tamperType) {
		this.tamperType = tamperType;
	}

	public String gettDate() {
		return tDate;
	}

	public void settDate(String tDate) {
		this.tDate = tDate;
	}

	public int getRdngMonth() {
		return rdngMonth;
	}

	public void setRdngMonth(int rdngMonth) {
		this.rdngMonth = rdngMonth;
	}

	
	

}
