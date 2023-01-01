package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ALLOWEDDEVICEVERSIONS",schema="VCLOUDENGINE")
@NamedQueries
({
		@NamedQuery(name = "AllowedDeviceVersionsEntity.findAll", query = "SELECT alv FROM AllowedDeviceVersionsEntity alv WHERE alv.allowedFlag='allowed'"),
		@NamedQuery(name = "AllowedDeviceVersionsEntity.findAllField", query = "SELECT alv FROM AllowedDeviceVersionsEntity alv WHERE alv.allowedFlag='allowed' and alv.remarks = :remarks"),
		@NamedQuery(name = "AllowedDeviceVersionsEntity.findAllVersionsToAllow", query = "SELECT alv FROM AllowedDeviceVersionsEntity alv WHERE alv.allowedFlag='not allowed'"),
		@NamedQuery(name = "AllowedDeviceVersionsEntity.updateAllowedStatus", query = "UPDATE AllowedDeviceVersionsEntity alv SET alv.allowedFlag=:status WHERE alv.id=:id")
})
public class AllowedDeviceVersionsEntity 
{

	@Id
	/*
	 * @SequenceGenerator(name = "alloweddevver_id", sequenceName =
	 * "alloweddevver_id")
	 * 
	 * @GeneratedValue(generator = "alloweddevver_id")
	 */
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="VERSION_NAME")
	private String version_name;
	
	@Column(name="DATETIMESTAMP")
	private Timestamp dateTimeStamp;
	
	@Column(name="REMARKS")
	private String remarks;

	@Column(name = "APPNAME")
	private String appName;

	@Column(name = "allowedflag")
	private String allowedFlag;



	
	public String getAllowedFlag() {
		return allowedFlag;
	}

	public void setAllowedFlag(String allowedFlag) {
		this.allowedFlag = allowedFlag;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public AllowedDeviceVersionsEntity() 
	{
		
	}

	public AllowedDeviceVersionsEntity(Integer id, String version_name,
			Timestamp dateTimeStamp, String remarks, String appName) {
		super();
		this.id = id;
		this.version_name = version_name;
		this.dateTimeStamp = dateTimeStamp;
		this.remarks = remarks;
		this.appName = appName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	public Timestamp getDateTimeStamp() {
		return dateTimeStamp;
	}

	public void setDateTimeStamp(Timestamp dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
