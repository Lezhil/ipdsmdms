package com.bcits.mdas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "MODEM_LIFECYLE",  schema = "METER_DATA")
@NamedQueries({
	@NamedQuery(name="ModemLifeCycleEntity.getLifeCycleDataByImei", query="select l from ModemLifeCycleEntity l where l.imei=:imei order by edate"),
	
})


public class ModemLifeCycleEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="imei")
	private String imei;
	
	@Column(name="events")
	private String events;
	
	@Column(name="location")
	private String location;
	
	@Column(name="edate")
	private Date edate;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}
	
	
}
