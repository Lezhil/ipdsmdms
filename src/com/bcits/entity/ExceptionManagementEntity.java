package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EXCEPTION_MANAGEMENT" ,schema="METER_DATA")
public class ExceptionManagementEntity {
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	
	@Column(name="METERNO")
	private String meterno;
	
	@Column(name="EVENTS")
	private String events;
	
	@Column(name="EXCEPTIONS")
	private String exceptions;
	
	@Column(name="BILLMONTH")
	private String billmonth;
	
	@Column(name="CONSUMPTION")
	private String consumption;
	
	@Column(name="POWEROUTAGE")
	private String poweroutage;
	
	@Column(name="FLAG")
	private String flag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getExceptions() {
		return exceptions;
	}

	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}

	public String getBillmonth() {
		return billmonth;
	}

	public void setBillmonth(String billmonth) {
		this.billmonth = billmonth;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public String getPoweroutage() {
		return poweroutage;
	}

	public void setPoweroutage(String poweroutage) {
		this.poweroutage = poweroutage;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	

}
