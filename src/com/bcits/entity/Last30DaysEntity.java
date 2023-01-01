package com.bcits.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "last_30_days_data", schema = "meter_data")
public class Last30DaysEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "communicating")
	private String communicating;

	@Column(name = "noncommunicating")
	private String noncommunicating;

	@Column(name = "total")
	private String total;

	@Column(name = "date")
	private Date date;

	@Column(name = "insert_time")
	private Timestamp insert_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommunicating() {
		return communicating;
	}

	public void setCommunicating(String communicating) {
		this.communicating = communicating;
	}

	public String getNoncommunicating() {
		return noncommunicating;
	}

	public void setNoncommunicating(String noncommunicating) {
		this.noncommunicating = noncommunicating;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}

}
