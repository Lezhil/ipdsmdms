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
@Table(name="surveyordetails", schema="meter_data")
@NamedQueries({
	@NamedQuery(name="SurveyorDetailsEntity.surveyorData",query="select s from SurveyorDetailsEntity s "),
	@NamedQuery(name="SurveyorDetailsEntity.activeSurveyorList",query="select s.suid,s.surveyorname from SurveyorDetailsEntity s where s.surveyorstatus='ACTIVE' "),
	@NamedQuery(name="SurveyorDetailsEntity.surveyorDetails",query="select s from SurveyorDetailsEntity s where s.id=:id ")
})
public class SurveyorDetailsEntity {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="SURVEYORNAME")
	private String surveyorname;
	
	@Column(name="IDENTITY")
	private String identity;
	
	@Column(name="SURVEYORSTATUS")
	private String surveyorstatus;
	
	@Column(name="ENTRYDATE")
	private Timestamp entrydate;
	
	@Column(name="ENTRYBY")
	private String entryby;
	
	@Column(name="UPDATEDDATE")
	private Timestamp updateddate;
	
	@Column(name="UPDATEBY")
	private String updateby;
	@Column(name="suid")
	private String suid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSurveyorname() {
		return surveyorname;
	}

	public void setSurveyorname(String surveyorname) {
		this.surveyorname = surveyorname;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getSurveyorstatus() {
		return surveyorstatus;
	}

	public void setSurveyorstatus(String surveyorstatus) {
		this.surveyorstatus = surveyorstatus;
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

	public Timestamp getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	@Override
	public String toString() {
		return "SurveyorDetailsEntity [id=" + id + ", surveyorname="
				+ surveyorname + ", identity=" + identity + ", surveyorstatus="
				+ surveyorstatus + ", entrydate=" + entrydate + ", entryby="
				+ entryby + ", updateddate=" + updateddate + ", updateby="
				+ updateby + "]";
	}

	public String getSuid() {
		return suid;
	}

	public void setSuid(String suid) {
		this.suid = suid;
	}
	
	
}
