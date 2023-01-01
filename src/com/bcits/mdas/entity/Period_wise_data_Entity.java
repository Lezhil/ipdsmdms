package com.bcits.mdas.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Period_wise_data_Entity {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	@Column(name = "town_code")
	private String town_code;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "total_meter")
	private Integer totalmeter;
	
	@Column(name = "communicating")
	private Integer communicating;
	
	@Column(name = "non_communicating")
	private Integer noncommunicating;
	
	@Column(name = "total_dt_meter")
	private Integer totaldtmeter;
	
	@Column(name = "dt_communicating")
	private Integer dtcommunicating;
	
	@Column(name = "dt_non_communicating")
	private Integer dtnoncommunicating;
	
	@Column(name = "total_feeder_meter")
	private Integer totalfeedermeter;
	
	
	@Column(name = "fm_commuicating")
	private Integer fmcommuicating;
	
	@Column(name = "fm_non_commuicating")
	private Integer fmnoncommuicating;
	
	@Column(name = "bm_communicating")
	private Integer bmcommunicating;
	
	
	@Column(name = "bm_non_communicating")
	private Integer bmnoncommunicating;
	
	@Column(name = "zone")
	private String zone;
	
	
	@Column(name = "circle")
	private String circle;
	
	@Column(name = "insert_time")
	private Timestamp inserttime;
	
	
	@Column(name = "town")
	private String town;

	public int getId() {
		return id;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String  getTown_code() {
		return town_code;
	}

	public void setTown_code(String  town_code) {
		this.town_code = town_code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTotalmeter() {
		return totalmeter;
	}

	public void setTotalmeter(Integer totalmeter) {
		this.totalmeter = totalmeter;
	}

	public Integer getCommunicating() {
		return communicating;
	}

	public void setCommunicating(Integer communicating) {
		this.communicating = communicating;
	}

	public Integer getNoncommunicating() {
		return noncommunicating;
	}

	public void setNoncommunicating(Integer noncommunicating) {
		this.noncommunicating = noncommunicating;
	}

	public Integer getTotaldtmeter() {
		return totaldtmeter;
	}

	public void setTotaldtmeter(Integer totaldtmeter) {
		this.totaldtmeter = totaldtmeter;
	}

	public Integer getDtcommunicating() {
		return dtcommunicating;
	}

	public void setDtcommunicating(Integer dtcommunicating) {
		this.dtcommunicating = dtcommunicating;
	}

	public Integer getDtnoncommunicating() {
		return dtnoncommunicating;
	}

	public void setDtnoncommunicating(Integer dtnoncommunicating) {
		this.dtnoncommunicating = dtnoncommunicating;
	}

	public Integer getTotalfeedermeter() {
		return totalfeedermeter;
	}

	public void setTotalfeedermeter(Integer totalfeedermeter) {
		this.totalfeedermeter = totalfeedermeter;
	}

	public Integer getFmcommuicating() {
		return fmcommuicating;
	}

	public void setFmcommuicating(Integer fmcommuicating) {
		this.fmcommuicating = fmcommuicating;
	}

	public Integer getFmnoncommuicating() {
		return fmnoncommuicating;
	}

	public void setFmnoncommuicating(Integer fmnoncommuicating) {
		this.fmnoncommuicating = fmnoncommuicating;
	}

	public Integer getBmcommunicating() {
		return bmcommunicating;
	}

	public void setBmcommunicating(Integer bmcommunicating) {
		this.bmcommunicating = bmcommunicating;
	}

	public Integer getBmnoncommunicating() {
		return bmnoncommunicating;
	}

	public void setBmnoncommunicating(Integer bmnoncommunicating) {
		this.bmnoncommunicating = bmnoncommunicating;
	}

	

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public Timestamp getInserttime() {
		return inserttime;
	}

	public void setInserttime(Timestamp inserttime) {
		this.inserttime = inserttime;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
