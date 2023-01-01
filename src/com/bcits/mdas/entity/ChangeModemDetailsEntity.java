package com.bcits.mdas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "MODEM_CHANGE_DETAILS",  schema = "METER_DATA",uniqueConstraints = { @UniqueConstraint(columnNames = "ID") })

public class ChangeModemDetailsEntity {
	
	@Id
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="circle")
	private String circle;
	
	@Column(name="division")
	private String division;
	
	@Column(name="zone")
	private String zone;
	
	@Column(name="subdivision")
	private String subdivision;
	
	@Column(name="substation")
	private String substation;
	
	@Column(name="feedername")
	private String feedername;
	
	@Column(name="feedercode")
	private String feedercode;
	
	@Column(name="old_imei_no")
	private String old_imei_no;
	
	@Column(name="old_sim_no")
	private String old_sim_no;
	
	@Column(name="mtr_no")
	private String mtr_no;
	
	@Column(name="new_imei_no")
	private String new_imei_no;
	
	@Column(name="created_by")
	private String created_by;
	
	@Column(name="created_date")
	private Date created_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public String getSubstation() {
		return substation;
	}

	public void setSubstation(String substation) {
		this.substation = substation;
	}

	public String getFeedername() {
		return feedername;
	}

	public void setFeedername(String feedername) {
		this.feedername = feedername;
	}

	public String getFeedercode() {
		return feedercode;
	}

	public void setFeedercode(String feedercode) {
		this.feedercode = feedercode;
	}

	public String getOld_imei_no() {
		return old_imei_no;
	}

	public void setOld_imei_no(String old_imei_no) {
		this.old_imei_no = old_imei_no;
	}

	public String getOld_sim_no() {
		return old_sim_no;
	}

	public void setOld_sim_no(String old_sim_no) {
		this.old_sim_no = old_sim_no;
	}

	public String getMtr_no() {
		return mtr_no;
	}

	public void setMtr_no(String mtr_no) {
		this.mtr_no = mtr_no;
	}

	public String getNew_imei_no() {
		return new_imei_no;
	}

	public void setNew_imei_no(String new_imei_no) {
		this.new_imei_no = new_imei_no;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}


	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
	@Override
	public String toString() {
		return "ChangeModemDetailsEntity [id=" + id + ", circle=" + circle + ", division=" + division + ", zone="
				+ zone + ", subdivision=" + subdivision + ", substation=" + substation + ", feedername="
				+ feedername + ", feedercode=" + feedercode + ", old_imei_no=" + old_imei_no + ", old_sim_no="
				+ old_sim_no + ", mtr_no=" + mtr_no + ", new_imei_no=" + new_imei_no + ", created_by=" + created_by
				+ ", created_date=" + created_date + "]";
	}
	
}
