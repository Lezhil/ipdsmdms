package com.bcits.entity;

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
@Table(name="tod_definition" , schema="meter_data")
@NamedQueries({
	@NamedQuery(name="TodDefinitionEntity.getAllTODSlots",query="select t from TodDefinitionEntity t order by todno")
})
public class TodDefinitionEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="TOD_NO")
	private String todno;
	
	@Column(name="START_TIME")
	private String start_time;
	
	@Column(name="END_TIME")
	private String end_time;
	
	@Column(name="ENTRY_DATE")
	private Timestamp entrydate;
	
	@Column(name="ENTRY_BY")
	private String entryby;
	
	@Column(name="UPDATE_TIME")
	private Timestamp update_time;
	
	@Column(name="UPDATE_BY")
	private String update_by;

	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTodno() {
		return todno;
	}

	public void setTodno(String todno) {
		this.todno = todno;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

	public String getEntryby() {
		return entryby;
	}

	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	@Override
	public String toString() {
		return "TodDefinitionEntity [todno=" + todno + ", start_time="
				+ start_time + ", end_time=" + end_time + ", entrydate="
				+ entrydate + ", entryby=" + entryby + ", update_time="
				+ update_time + ", update_by=" + update_by + ", id=" + id + "]";
	}

	
	

}
