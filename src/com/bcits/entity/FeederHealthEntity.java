package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="feeder_health_rpt",schema="meter_data")
public class FeederHealthEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="office_id")
	private String officeId;
	@Column(name="fdr_id")
	private String feeder_id;
	@Column(name="tp_fdr_id")
	private String tpFeederId;
	@Column(name="month_year")
	private String monthyear;
	@Column(name="meter_sr_number")
	private String meterNumber;
	@Column(name="kwh")
	private Double kwh;
	@Column(name="kvah")
	private Double kvah;
	/*
	 * @Column(name="kva") private Double kva;
	 */
	@Column(name="pf")
	private Double pf;
	@Column(name="peak_kva")
	private double peakKva;
	@Column(name="peak_kva_date")
	private Timestamp peakKvaDate;
	@Column(name="kw")
	private Double kw;
	@Column(name="kvar")
	private Double kvar;
	@Column(name="i_r")
	private String ir;
	@Column(name="i_y")
	private String iy;
	@Column(name="i_b")
	private String ib;
	@Column(name="lf")
	private Double lf;
	@Column(name="power_off_duration")
	private String power_off_duration;
	@Column(name="power_off_count")
	private Long power_off_count;
	@Column(name="time_stamp")
	private Timestamp time_stamp;
	@Column(name="kvar_min")
	private Double minKvar;
	@Column(name="kvar_max")
	private Double maxKvar;
	@Column(name="town_id")
	private String townCode;
	@Column(name="minkva")
	private double minkva;

	public double getMinkva() {
		return minkva;
	}
	public void setMinkva(double minkva) {
		this.minkva = minkva;
	}
	public String getTownCode() {
		return townCode;
	}
	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}
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
	public String getFeeder_id() {
		return feeder_id;
	}
	public void setFeeder_id(String feeder_id) {
		this.feeder_id = feeder_id;
	}
	public String getTpFeederId() {
		return tpFeederId;
	}
	public void setTpFeederId(String tpFeederId) {
		this.tpFeederId = tpFeederId;
	}
	public String getMonthyear() {
		return monthyear;
	}
	public void setMonthyear(String monthyear) {
		this.monthyear = monthyear;
	}
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
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
	
	public Double getPf() {
		return pf;
	}
	public void setPf(Double pf) {
		this.pf = pf;
	}
	public double getPeakKva() {
		return peakKva;
	}
	public void setPeakKva(double d) {
		this.peakKva = d;
	}
	public Timestamp getPeakKvaDate() {
		return peakKvaDate;
	}
	public void setPeakKvaDate(Timestamp peakKvaDate) {
		this.peakKvaDate = peakKvaDate;
	}
	public Double getKw() {
		return kw;
	}
	public void setKw(Double kw) {
		this.kw = kw;
	}
	public Double getKvar() {
		return kvar;
	}
	public void setKvar(Double kvar) {
		this.kvar = kvar;
	}
	public String getIr() {
		return ir;
	}
	public void setIr(String ir) {
		this.ir = ir;
	}
	public String getIy() {
		return iy;
	}
	public void setIy(String iy) {
		this.iy = iy;
	}
	public String getIb() {
		return ib;
	}
	public void setIb(String ib) {
		this.ib = ib;
	}
	public Double getLf() {
		return lf;
	}
	public void setLf(Double lf) {
		this.lf = lf;
	}
	public String getPower_off_duration() {
		return power_off_duration;
	}
	public void setPower_off_duration(String power_off_duration) {
		this.power_off_duration = power_off_duration;
	}
	public Long getPower_off_count() {
		return power_off_count;
	}
	public void setPower_off_count(Long power_off_count) {
		this.power_off_count = power_off_count;
	}
	public Timestamp getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}
	public Double getMinKvar() {
		return minKvar;
	}
	public void setMinKvar(Double minKvar) {
		this.minKvar = minKvar;
	}
	public Double getMaxKvar() {
		return maxKvar;
	}
	public void setMaxKvar(Double maxKvar) {
		this.maxKvar = maxKvar;
	}

	
	@Override
	public String toString() {
		return "FeederHealthEntity [id=" + id + ", officeId=" + officeId + ", feeder_id=" + feeder_id + ", tpFeederId="
				+ tpFeederId + ", monthyear=" + monthyear + ", meterNumber=" + meterNumber + ", kwh=" + kwh + ", kvah="
				+ kvah + ", pf=" + pf + ", peakKva=" + peakKva + ", peakKvaDate=" + peakKvaDate
				+ ", kw=" + kw + ", kvar=" + kvar + ", ir=" + ir + ", iy=" + iy + ", ib=" + ib + ", lf=" + lf
				+ ", power_off_duration=" + power_off_duration + ", power_off_count=" + power_off_count
				+ ", time_stamp=" + time_stamp + ", minKvar=" + minKvar + ", maxKvar=" + maxKvar + "]";
	}
}
