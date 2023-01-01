package com.bcits.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="process_tracker", schema="meter_data")

public class ProcessTracker implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="process_name")
	private String process_name;
	@Column(name="last_process_time")
	private Timestamp last_process_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProcess_name() {
		return process_name;
	}
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	public Timestamp getLast_process_time() {
		return last_process_time;
	}
	public void setLast_process_time(Timestamp last_process_time) {
		this.last_process_time = last_process_time;
	}
	@Override
	public String toString() {
		return "ProcessTracker [id=" + id + ", process_name=" + process_name
				+ ", last_process_time=" + last_process_time + "]";
	}
	
	
}
