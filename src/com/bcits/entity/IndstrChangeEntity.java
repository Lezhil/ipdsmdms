package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "indstr_change", schema = "meter_data")
public class IndstrChangeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "ssfdrstrcode_old")
	private Integer ssfdrstrcode_old;
	@Column(name = "ssfdrstrcode_new")
	private Integer ssfdrstrcode_new;
	@Column(name = "change_st")
	private Timestamp change_st;
	@Column(name = "enton")
	private Timestamp enton;
	@Column(name = "entby")
	private String entby;
	@Column(name = "ip_id")
	private String ip_id;
	@Column(name = "reason")
	private String reason;
	@Column(name = "type")
	private String type;
	@Column(name = "flag")
	private String flag;
	@Column(name = "updt_dt")
	private Timestamp updt_dt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSsfdrstrcode_old() {
		return ssfdrstrcode_old;
	}

	public void setSsfdrstrcode_old(Integer ssfdrstrcode_old) {
		this.ssfdrstrcode_old = ssfdrstrcode_old;
	}

	public Integer getSsfdrstrcode_new() {
		return ssfdrstrcode_new;
	}

	public void setSsfdrstrcode_new(Integer ssfdrstrcode_new) {
		this.ssfdrstrcode_new = ssfdrstrcode_new;
	}

	public Timestamp getChange_st() {
		return change_st;
	}

	public void setChange_st(Timestamp change_st) {
		this.change_st = change_st;
	}

	public Timestamp getEnton() {
		return enton;
	}

	public void setEnton(Timestamp enton) {
		this.enton = enton;
	}

	public String getEntby() {
		return entby;
	}

	public void setEntby(String entby) {
		this.entby = entby;
	}

	public String getIp_id() {
		return ip_id;
	}

	public void setIp_id(String ip_id) {
		this.ip_id = ip_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Timestamp getUpdt_dt() {
		return updt_dt;
	}

	public void setUpdt_dt(Timestamp updt_dt) {
		this.updt_dt = updt_dt;
	}

}
