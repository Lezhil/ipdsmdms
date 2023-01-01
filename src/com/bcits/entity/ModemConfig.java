package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="modem_configuration",schema="meter_data")

public class ModemConfig {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="MTR_NO")
	private String mtr_no;
	
	@Column(name="INSTANT_DATA")
	private String instant_data;
	
	@Column(name="HISTORY_DATA")
	private String history_data;
	
	@Column(name="LOADSURVEYPOLL")
	private String loadsurveypoll;
	
	@Column(name="LOADSURVEYDAYS")
	private String loadsurveydays;
	
	@Column(name="EVENT_DATA")
	private String event_data;
	
	@Column(name="MIDNIGHT")
	private String midnight;
	
	@Column(name="MIGRATE_MDM")
	private String migrate_mdm;
	
	@Column(name="TRANSACTION_DATA")
	private String transaction_data;
	
	@Column(name="RETRYINTERVAL")
	private String retryinterval;
	
	@Column(name="NOOFRETRY")
	private String noofretry;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMtr_no() {
		return mtr_no;
	}

	public void setMtr_no(String mtr_no) {
		this.mtr_no = mtr_no;
	}

	public String getInstant_data() {
		return instant_data;
	}

	public void setInstant_data(String instant_data) {
		this.instant_data = instant_data;
	}

	public String getHistory_data() {
		return history_data;
	}

	public void setHistory_data(String history_data) {
		this.history_data = history_data;
	}

	public String getLoadsurveypoll() {
		return loadsurveypoll;
	}

	public void setLoadsurveypoll(String loadsurveypoll) {
		this.loadsurveypoll = loadsurveypoll;
	}

	public String getLoadsurveydays() {
		return loadsurveydays;
	}

	public void setLoadsurveydays(String loadsurveydays) {
		this.loadsurveydays = loadsurveydays;
	}

	public String getEvent_data() {
		return event_data;
	}

	public void setEvent_data(String event_data) {
		this.event_data = event_data;
	}

	public String getMidnight() {
		return midnight;
	}

	public void setMidnight(String midnight) {
		this.midnight = midnight;
	}

	public String getMigrate_mdm() {
		return migrate_mdm;
	}

	public void setMigrate_mdm(String migrate_mdm) {
		this.migrate_mdm = migrate_mdm;
	}

	public String getTransaction_data() {
		return transaction_data;
	}

	public void setTransaction_data(String transaction_data) {
		this.transaction_data = transaction_data;
	}

	public String getRetryinterval() {
		return retryinterval;
	}

	public void setRetryinterval(String retryinterval) {
		this.retryinterval = retryinterval;
	}

	public String getNoofretry() {
		return noofretry;
	}

	public void setNoofretry(String noofretry) {
		this.noofretry = noofretry;
	}
	
	

}
