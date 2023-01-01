package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dt_multiple_meters",schema="meter_data")
public class DTMultipleMetersEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="dttpid")
	private String dttpid;
	
	@Column(name="dtname")
	private String dtname;
	
	@Column(name="town")
	private String town;
	
	@Column(name="kwh")
	private Double kwh;
	
	@Column(name="kvah")
	private Double kvah;
	
	@Column(name="kva")
	private Double kva;
	
	@Column(name="kw")
	private Double kw;
	
	@Column(name="pf")
	private Double pf;
	
	@Column(name="iptime")
	private Timestamp iptime;
	
	@Column(name="timestamp")
	private Timestamp timestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDttpid() {
		return dttpid;
	}

	public void setDttpid(String dttpid) {
		this.dttpid = dttpid;
	}

	public String getDtname() {
		return dtname;
	}

	public void setDtname(String dtname) {
		this.dtname = dtname;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
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

	public Double getKva() {
		return kva;
	}

	public void setKva(Double kva) {
		this.kva = kva;
	}

	public Double getKw() {
		return kw;
	}

	public void setKw(Double kw) {
		this.kw = kw;
	}

	public Double getPf() {
		return pf;
	}

	public void setPf(Double pf) {
		this.pf = pf;
	}

	public Timestamp getIptime() {
		return iptime;
	}

	public void setIptime(Timestamp iptime) {
		this.iptime = iptime;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
