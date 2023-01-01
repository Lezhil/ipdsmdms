package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dt_health_rpt", schema="meter_data")
public class DTHealthEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="office_id")
	private String officeId;
	
	@Column(name="dt_id")
	private String dtId;
	
	@Column(name="tp_dt_id")
	private String tpDtId;
	
	@Column(name="month_year")
	private String monthYear;
	
	@Column(name="meter_sr_number")
	private String meterSrNumber;
	
	@Column(name="avg_curr_r_ph")
	private Double avgCurrRph;
	
	@Column(name="avg_curr_y_ph")
	private Double avgCurrYph;
	
	@Column(name="avg_curr_b_ph")
	private Double avgCurrBph;
	
	@Column(name="unbalance_r_ph")
	private Double unbalanceRph;
	
	@Column(name="unbalance_y_ph")
	private Double unbalanceYph;
	
	@Column(name="unbalance_b_ph")
	private Double unbalanceBph;
	
	@Column(name="lf")
	private Double lf;
	
	@Column(name="uf")
	private Double uf;
	
	@Column(name="overload")
	private Boolean overload;
	
	@Column(name="underload")
	private Boolean underload;
	
	@Column(name="unbalance")
	private Boolean unbalance;
	
	@Column(name="power_on_duration")
	private String powerOnDuration;
	
	@Column(name="power_of_duration")
	private String powerOfDuration;
	
	@Column(name="total_duration")
	private String totalDuration;
	
	@Column(name="no_load_duration")
	private Double noLoadDuration;
	
	@Column(name="time_stamp")
	private Timestamp time_stamp;
	
	@Column(name="pf")
	private Double pf;
	
	@Column(name="kwh")
	private Double kwh;
	
	@Column(name="kvah")
	private Double kvah;
	
	@Column(name="range1")
	private Double range1;
	
	@Column(name="range2")
	private Double range2;
	
	@Column(name="range3")
	private Double range3;
	
	

	@Column(name="range4")
	private Double range4;
	
	public String getTp_town_code() {
		return tp_town_code;
	}

	public void setTp_town_code(String tp_town_code) {
		this.tp_town_code = tp_town_code;
	}

	@Column(name="kva_rating")
	private Double kva_rating;
	
	@Column(name="tp_town_code")
	private String tp_town_code;

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

	public String getDtId() {
		return dtId;
	}

	public void setDtId(String dtId) {
		this.dtId = dtId;
	}

	public String getTpDtId() {
		return tpDtId;
	}

	public void setTpDtId(String tpDtId) {
		this.tpDtId = tpDtId;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public String getMeterSrNumber() {
		return meterSrNumber;
	}

	public void setMeterSrNumber(String meterSrNumber) {
		this.meterSrNumber = meterSrNumber;
	}

	public Double getAvgCurrRph() {
		return avgCurrRph;
	}

	public void setAvgCurrRph(Double avgCurrRph) {
		this.avgCurrRph = avgCurrRph;
	}

	public Double getAvgCurrYph() {
		return avgCurrYph;
	}

	public void setAvgCurrYph(Double avgCurrYph) {
		this.avgCurrYph = avgCurrYph;
	}

	public Double getAvgCurrBph() {
		return avgCurrBph;
	}

	public void setAvgCurrBph(Double avgCurrBph) {
		this.avgCurrBph = avgCurrBph;
	}

	public Double getUnbalanceRph() {
		return unbalanceRph;
	}

	public void setUnbalanceRph(Double unbalanceRph) {
		this.unbalanceRph = unbalanceRph;
	}

	public Double getUnbalanceYph() {
		return unbalanceYph;
	}

	public void setUnbalanceYph(Double unbalanceYph) {
		this.unbalanceYph = unbalanceYph;
	}

	public Double getUnbalanceBph() {
		return unbalanceBph;
	}

	public void setUnbalanceBph(Double unbalanceBph) {
		this.unbalanceBph = unbalanceBph;
	}

	public Double getLf() {
		return lf;
	}

	public void setLf(Double lf) {
		this.lf = lf;
	}

	public Double getUf() {
		return uf;
	}

	public void setUf(Double uf) {
		this.uf = uf;
	}

	public Boolean getOverload() {
		return overload;
	}

	public void setOverload(Boolean overload) {
		this.overload = overload;
	}

	public Boolean getUnderload() {
		return underload;
	}

	public void setUnderload(Boolean underload) {
		this.underload = underload;
	}

	public Boolean getUnbalance() {
		return unbalance;
	}

	public void setUnbalance(Boolean unbalance) {
		this.unbalance = unbalance;
	}

	public String getPowerOnDuration() {
		return powerOnDuration;
	}

	public void setPowerOnDuration(String powerOnDuration) {
		this.powerOnDuration = powerOnDuration;
	}

	public String getPowerOfDuration() {
		return powerOfDuration;
	}

	public void setPowerOfDuration(String powerOfDuration) {
		this.powerOfDuration = powerOfDuration;
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Double getNoLoadDuration() {
		return noLoadDuration;
	}

	public void setNoLoadDuration(Double noLoadDuration) {
		this.noLoadDuration = noLoadDuration;
	}

	public Timestamp getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}

	public Double getPf() {
		return pf;
	}

	public void setPf(Double pf) {
		this.pf = pf;
	}

	public Double getKwh() {
		return kwh;
	}

	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}

	public Double getKvah() {
		return kvah;
	}

	public void setKvah(Double kvah) {
		this.kvah = kvah;
	}

	public Double getRange1() {
		return range1;
	}

	public void setRange1(Double range1) {
		this.range1 = range1;
	}

	public Double getRange2() {
		return range2;
	}

	public void setRange2(Double range2) {
		this.range2 = range2;
	}

	public Double getRange3() {
		return range3;
	}

	public void setRange3(Double range3) {
		this.range3 = range3;
	}

	public Double getRange4() {
		return range4;
	}

	public void setRange4(Double range4) {
		this.range4 = range4;
	}

	public Double getKva_rating() {
		return kva_rating;
	}

	public void setKva_rating(Double kva_rating) {
		this.kva_rating = kva_rating;
	}
	

}
