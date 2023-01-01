package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="EVENT_MASTER",schema="meter_data")
@NamedQueries({
		@NamedQuery(name="EventMaster.findAll",query="SELECT e FROM EventMaster e ORDER BY e.eventDescription"),
		@NamedQuery(name="EventMaster.findDesc",query="SELECT e.eventDescription FROM EventMaster e Where e.eventCode=:code"),
		@NamedQuery(name="EventMaster.findtampertype",query="SELECT e.eventDescription FROM EventMaster e where e.eventCode=:eventcode"),
		@NamedQuery(name="EventMaster.catgoryList",query="SELECT DISTINCT e.category FROM EventMaster e where e.category is not null ")
		
		
})
public class EventMaster 
{
	@Id
	@Column(name="EVENT_ID")
	private int id;
	
	@Column(name="EVENT_CODE")
	private int eventCode;
	
	@Column(name="EVENT")
	private String eventDescription;
	@Column(name="category")
	private String category;
	@Column(name="event_status")
	private String eventStatus;
	@Column(name="event_config")
	private String eventConfig;
	
	public EventMaster() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEventCode() {
		return eventCode;
	}

	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getEventConfig() {
		return eventConfig;
	}

	public void setEventConfig(String eventConfig) {
		this.eventConfig = eventConfig;
	}

	
	
	

}
