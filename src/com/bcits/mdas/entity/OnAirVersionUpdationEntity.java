package com.bcits.mdas.entity;

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
@Table(name = "onairversionupdation", schema = "VCLOUDENGINE")
@NamedQueries({ @NamedQuery(name = "OnAirVersionUpdationEntity.findAll", query = "SELECT oavue FROM OnAirVersionUpdationEntity oavue"),
	            @NamedQuery(name = "OnAirVersionUpdationEntity.checkVersionExist", query = "SELECT COUNT(*) FROM OnAirVersionUpdationEntity o WHERE o.apkVersion=:version"),
	        	@NamedQuery(name = "OnAirVersionUpdationEntity.latestVersionfield", query = "SELECT oavue FROM OnAirVersionUpdationEntity oavue where oavue.remarks =:remarks"),
	            @NamedQuery(name = "OnAirVersionUpdationEntity.latestVersion", query = "SELECT oavue FROM OnAirVersionUpdationEntity oavue where oavue.remarks = 'SURVEY'")
	         })
public class OnAirVersionUpdationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "ID")
	private Integer id;

	@Column(name = "APKNAME")
	private String apkName;

	@Column(name = "VERSION")
	private String apkVersion;

	@Column(name = "UPLOADEDBY")
	private String uploadedyBy;

	@Column(name = "UPLOADED_DATE")
	private Timestamp timestamp;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "APK_PATH")
	private String apkPath;

	@Column(name = "RELEASEFLAG")
	private String releaseFlag;
	
	/*@Column(name = "groups")
	private String grp;*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	public String getUploadedyBy() {
		return uploadedyBy;
	}

	public void setUploadedyBy(String uploadedyBy) {
		this.uploadedyBy = uploadedyBy;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getApkPath() {
		return apkPath;
	}

	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}

	public String getReleaseFlag() {
		return releaseFlag;
	}

	public void setReleaseFlag(String releaseFlag) {
		this.releaseFlag = releaseFlag;
	}


	public OnAirVersionUpdationEntity() {

	}

	/*public String getGrp() {
		return grp;
	}

	public void setGrp(String grp) {
		this.grp = grp;
	}*/
	
}
