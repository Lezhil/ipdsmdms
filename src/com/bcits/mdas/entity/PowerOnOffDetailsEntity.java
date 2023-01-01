/**
 * 
 */
package com.bcits.mdas.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Tarik
 *
 */
@Entity
@Table(name="power_onoff_details", schema="meter_data")
public class PowerOnOffDetailsEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name="fkeyid")
	private Integer fkeyid;
	
//	@OneToMany
//    @JoinColumn(name = "id")
//    private PowerOnOffEntity user;
	
	@Column(name="feedercode")
	private String feedercode;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="meterid")
	private String meterid;
	
	@Column(name="occurence_time")
	private Timestamp occurence_time;
	
	@Column(name="restoration_time")
	private Timestamp restoration_time;
	
	@Column(name="duration")
	private Integer duration;
	
	@Column(name="time_stamp")
	private Timestamp time_stamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getFkeyid() {
		return fkeyid;
	}

	public void setFkeyid(Integer fkeyid) {
		this.fkeyid = fkeyid;
	}

	public String getFeedercode() {
		return feedercode;
	}

	public void setFeedercode(String feedercode) {
		this.feedercode = feedercode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMeterid() {
		return meterid;
	}

	public void setMeterid(String meterid) {
		this.meterid = meterid;
	}

	public Timestamp getOccurence_time() {
		return occurence_time;
	}

	public void setOccurence_time(Timestamp occurence_time) {
		this.occurence_time = occurence_time;
	}

	public Timestamp getRestoration_time() {
		return restoration_time;
	}

	public void setRestoration_time(Timestamp restoration_time) {
		this.restoration_time = restoration_time;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Timestamp getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}
	
	

}
