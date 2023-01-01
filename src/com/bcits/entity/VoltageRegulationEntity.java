package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="voltage_regulation_rpt",schema="meter_data")
public class VoltageRegulationEntity {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="office_id")
	private String officeId;
	@Column(name="location_type")
	private String locationType;
	@Column(name="location_code")
	private String locationCode;
	@Column(name="month_year")
	private String monthYear;
	@Column(name="meter_sr_number")
	private String meterSerialNum;
	@Column(name="range1")
	private Long range1;
	@Column(name="range2")
	private Long range2;
	@Column(name="range3")
	private Long range3;
	@Column(name="range4")
	private Long range4;
	@Column(name="no_load_duration")
	private Double noLoadDuration;
	@Column(name="power_of_duration")
	private Double powerOffDuration;
	@Column(name="time_stamp")
	private Timestamp timeStamp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getMeterSerialNum() {
		return meterSerialNum;
	}
	public void setMeterSerialNum(String meterSerialNum) {
		this.meterSerialNum = meterSerialNum;
	}
	public Long getRange1() {
		return range1;
	}
	public void setRange1(Long range1) {
		this.range1 = range1;
	}
	public Long getRange2() {
		return range2;
	}
	public void setRange2(Long range2) {
		this.range2 = range2;
	}
	public Long getRange3() {
		return range3;
	}
	public void setRange3(Long range3) {
		this.range3 = range3;
	}
	public Long getRange4() {
		return range4;
	}
	public void setRange4(Long range4) {
		this.range4 = range4;
	}
	public Double getNoLoadDuration() {
		return noLoadDuration;
	}
	public void setNoLoadDuration(Double noLoadDuration) {
		this.noLoadDuration = noLoadDuration;
	}
	public Double getPowerOffDuration() {
		return powerOffDuration;
	}
	public void setPowerOffDuration(Double hours) {
		this.powerOffDuration = hours;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		return "VoltageRegulationEntity [id=" + id + ", officeId=" + officeId + ", locationType=" + locationType
				+ ", locationCode=" + locationCode + ", monthYear=" + monthYear + ", meterSerialNum=" + meterSerialNum
				+ ", range1=" + range1 + ", range2=" + range2 + ", range3=" + range3 + ", range4=" + range4
				+ ", noLoadDuration=" + noLoadDuration + ", powerOffDuration=" + powerOffDuration + ", timeStamp="
				+ timeStamp + "]";
	}
	
}
