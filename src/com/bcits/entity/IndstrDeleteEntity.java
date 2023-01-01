package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "indstr_delete", schema = "meter_data")
public class IndstrDeleteEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "ssfdrstrcode")
	private Integer ssfdrstrcode;
	@Column(name = "del_dt")
	private Timestamp del_dt;
	@Column(name = "enton")
	private Timestamp enton;
	@Column(name = "entby")
	private String entby;
	@Column(name = "ip_id")
	private String ip_id;
	@Column(name = "type")
	private String type;
	@Column(name = "flag")
	private String flag;
	@Column(name = "delete_dt")
	private Timestamp delete_dt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSsfdrstrcode() {
		return ssfdrstrcode;
	}

	public void setSsfdrstrcode(Integer ssfdrstrcode) {
		this.ssfdrstrcode = ssfdrstrcode;
	}

	public Timestamp getDel_dt() {
		return del_dt;
	}

	public void setDel_dt(Timestamp del_dt) {
		this.del_dt = del_dt;
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

	public Timestamp getDelete_dt() {
		return delete_dt;
	}

	public void setDelete_dt(Timestamp delete_dt) {
		this.delete_dt = delete_dt;
	}

}
