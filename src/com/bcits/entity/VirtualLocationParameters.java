package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="virtual_location_parameters",schema="meter_data")
public class VirtualLocationParameters {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="subdivision")
	private String subdivision;
	@Column(name="vl_id")
	private String vlID;
	@Column(name="vl_name")
	private String vl_name;
	@Column(name="location_type")
	private String location_type;
	@Column(name="read_time")
	private Timestamp readTime;
	@Column(name="kwh")
	private Double kwh;
	@Column(name="kvah")
	private Double kvah;
	@Column(name="kw")
	private Double kw;
	@Column(name="kva")
	private Double kva;
	@Column(name="pf")
	private Double pf;
	@Column(name="time_stamp")
	private Timestamp timeStamp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	public String getVlID() {
		return vlID;
	}
	public void setVlID(String vlID) {
		this.vlID = vlID;
	}
	public String getVl_name() {
		return vl_name;
	}
	public void setVl_name(String vl_name) {
		this.vl_name = vl_name;
	}
	public String getLocation_type() {
		return location_type;
	}
	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}
	public Timestamp getReadTime() {
		return readTime;
	}
	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
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
	public Double getKw() {
		return kw;
	}
	public void setKw(Double kw) {
		this.kw = kw;
	}
	public Double getKva() {
		return kva;
	}
	public void setKva(Double kva) {
		this.kva = kva;
	}
	public Double getPf() {
		return pf;
	}
	public void setPf(Double pf) {
		this.pf = pf;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
	
	
}
