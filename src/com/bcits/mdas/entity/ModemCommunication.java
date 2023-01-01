package com.bcits.mdas.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "MODEM_COMMUNICATION",  schema = "METER_DATA",uniqueConstraints = { @UniqueConstraint(columnNames = { "meter_number","DATE" }) })

@NamedQueries({
		// @NamedQuery(name = "AmrInstantaneousEntity.getD1", query = "SELECT ai
		// FROM AmrInstantaneousEntity ai")
		// @NamedQuery(name = "AmrInstantaneousEntity.getD1", query = "SELECT
		// ai.meterNumber,ai.readTime,ai.timeStamp,ai.modemTime FROM
		// AmrInstantaneousEntity ai")

})
public class ModemCommunication {

	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private KeyCommunication myKey; // DATE, METER NUMBER
	
	@Column(name = "imei")
	private String imei;

	@Column(name = "last_communication")
	private Timestamp lastCommunication;

	@Column(name = "last_sync_inst")
	private Timestamp lastSyncInst;

	@Column(name = "last_sync_event")
	private Timestamp lastSyncEvent;

	@Column(name = "last_sync_load")
	private Timestamp lastSyncLoad;

	@Column(name = "last_sync_bill")
	private Timestamp lastSyncBill;

	@Column(name = "total_communication")
	private Long totalCommunication;

	@Column(name = "total_inst")
	private Long totalInst;

	@Column(name = "total_event")
	private Long totalEvent;

	@Column(name = "total_load")
	private Long totalLoad;

	@Column(name = "total_bill")
	private Long totalBill;

	@Column(name = "signal")
	private String signal = "0";

	@Column(name = "temperature")
	private String temperature = "0";

	@Column(name = "meter_conn_fail_count")
	private String meterConnFailCount = "0";

	@Column(name = "gprs_conn_fail_count")
	private String gprsConnFailCount = "0";
	
	@Column(name="read_from")
	private String read_from;
	
	@Column(name = "total_dailyload")
	private Long total_dailyload;
	
	@Column(name = "last_sync_dailyload")
	private Timestamp last_sync_dailyload;
	
	@Column(name = "last_insert_time")
	private Timestamp last_inserttime;

	
	public KeyCommunication getMyKey() {
		return myKey;
	}

	public void setMyKey(KeyCommunication myKey) {
		this.myKey = myKey;
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getMeterConnFailCount() {
		return meterConnFailCount;
	}

	public void setMeterConnFailCount(String meterConnFailCount) {
		this.meterConnFailCount = meterConnFailCount;
	}

	public String getGprsConnFailCount() {
		return gprsConnFailCount;
	}

	public void setGprsConnFailCount(String gprsConnFailCount) {
		this.gprsConnFailCount = gprsConnFailCount;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Timestamp getLastCommunication() {
		return lastCommunication;
	}

	public void setLastCommunication(Timestamp lastCommunication) {
		this.lastCommunication = lastCommunication;
	}

	public Timestamp getLastSyncInst() {
		return lastSyncInst;
	}

	public void setLastSyncInst(Timestamp lastSyncInst) {
		this.lastSyncInst = lastSyncInst;
	}

	public Timestamp getLastSyncEvent() {
		return lastSyncEvent;
	}

	public void setLastSyncEvent(Timestamp lastSyncEvent) {
		this.lastSyncEvent = lastSyncEvent;
	}

	public Timestamp getLastSyncLoad() {
		return lastSyncLoad;
	}

	public void setLastSyncLoad(Timestamp lastSyncLoad) {
		this.lastSyncLoad = lastSyncLoad;
	}

	public Timestamp getLastSyncBill() {
		return lastSyncBill;
	}

	public void setLastSyncBill(Timestamp lastSyncBill) {
		this.lastSyncBill = lastSyncBill;
	}

	public Long getTotalCommunication() {
		return totalCommunication;
	}

	public void setTotalCommunication(Long totalCommunication) {
		this.totalCommunication = totalCommunication;
	}

	public Long getTotalInst() {
		return totalInst;
	}

	public void setTotalInst(Long totalInst) {
		this.totalInst = totalInst;
	}

	public Long getTotalEvent() {
		return totalEvent;
	}

	public void setTotalEvent(Long totalEvent) {
		this.totalEvent = totalEvent;
	}

	public Long getTotalLoad() {
		return totalLoad;
	}

	public void setTotalLoad(Long totalLoad) {
		this.totalLoad = totalLoad;
	}

	public Long getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(Long totalBill) {
		this.totalBill = totalBill;
	}

	@Embeddable
	public static  class KeyCommunication implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "meter_number")
		private String meterNumber;
		
		@Column(name = "date")
		private Date date;
		 
		public KeyCommunication(String meterNumber, Date date) {
			super();
			this.meterNumber = meterNumber;
			this.date = date;
		}

		public KeyCommunication(){
			 
		}

		public String getMeterNumber() {
			return meterNumber;
		}

		public void setMeterNumber(String meterNumber) {
			this.meterNumber = meterNumber;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	    
	}
	
	
	public ModemCommunication() {

	}

	public String getRead_from() {
		return read_from;
	}

	public void setRead_from(String read_from) {
		this.read_from = read_from;
	}

	public Long getTotal_dailyload() {
		return total_dailyload;
	}

	public void setTotal_dailyload(Long total_dailyload) {
		this.total_dailyload = total_dailyload;
	}

	public Timestamp getLast_sync_dailyload() {
		return last_sync_dailyload;
	}

	public void setLast_sync_dailyload(Timestamp last_sync_dailyload) {
		this.last_sync_dailyload = last_sync_dailyload;
	}

	public Timestamp getLast_inserttime() {
		return last_inserttime;
	}

	public void setLast_inserttime(Timestamp last_inserttime) {
		this.last_inserttime = last_inserttime;
	}

}
