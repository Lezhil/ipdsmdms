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
@Table(name="process_schedular",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="ProcessSchedular.findAllUser", query="SELECT u FROM ProcessSchedular u"),
	@NamedQuery(name="ProcessSchedular.findById", query="SELECT u FROM ProcessSchedular u where id=:id"),
})
public class ProcessSchedular {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="process_type")
	private String processtype;
	
	@Column(name="process_name")
	private String processname;
	
	@Column(name="process_id")
	private String processid;
	
	@Column(name="occurance")
	private String occurance;
	
	@Column(name="occurance_type")
	private String occurancetype;
	
	@Column(name="occ_time")
	private String occurancetime;
	
	@Column(name="repeated_occ_time")
	private String repeatedocctime;
	
	@Column(name="created_by")
	private String createdby;
	
	@Column(name="created_date")
	private Timestamp createddate;
	
	@Column(name="updated_by")
	private String updatedby;
	
	@Column(name="updated_date")
	private Timestamp updateddate;

	public String getProcesstype() {
		return processtype;
	}

	public void setProcesstype(String processtype) {
		this.processtype = processtype;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getOccurance() {
		return occurance;
	}

	public void setOccurance(String occurance) {
		this.occurance = occurance;
	}

	public String getOccurancetype() {
		return occurancetype;
	}

	public void setOccurancetype(String occurancetype) {
		this.occurancetype = occurancetype;
	}

	public String getOccurancetime() {
		return occurancetime;
	}

	public void setOccurancetime(String dateandTimevalue) {
		this.occurancetime = dateandTimevalue;
	}

	public String getRepeatedocctime() {
		return repeatedocctime;
	}

	public void setRepeatedocctime(String repeatedocctime) {
		this.repeatedocctime = repeatedocctime;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Timestamp getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}
	
	
	
	

}
