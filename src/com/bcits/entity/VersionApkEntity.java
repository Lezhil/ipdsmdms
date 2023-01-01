package com.bcits.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="VERSION_APK")
@NamedQueries({
	@NamedQuery(name="VersionApkEntity.GetApkMaxVersion",query="select MAX(m.version) FROM VersionApkEntity m where remarks='MIP'"),
	@NamedQuery(name="VersionApkEntity.GetApkDetails",query="select m FROM VersionApkEntity m where m.version =:version and  remarks='MIP'"),
	@NamedQuery(name="VersionApkEntity.GetApkMaxVersionAmr",query="select MAX(m.version) FROM VersionApkEntity m where remarks='AMR'"),
	@NamedQuery(name="VersionApkEntity.GetApkDetailsAmr",query="select m FROM VersionApkEntity m where m.version =:version AND remarks='AMR'")


})

public class VersionApkEntity {
	
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="APKNAME")
	private String apkname;
	
	@Column(name="VERSION")
	private String version;
	
	@Column(name="APK_PATH")
	private String apk_path;
	
	@Column(name="UOPLOADED_BY")
	private String uploaded_by;
	
	
	@Column(name="UPLOADED_DATE")
	private String uploaded_date;
	
	@Column(name="REMARKS")
	private String remarks;
	
	

	public String getUploaded_by() {
		return uploaded_by;
	}

	public void setUploaded_by(String uploaded_by) {
		this.uploaded_by = uploaded_by;
	}

	public String getUploaded_date() {
		return uploaded_date;
	}

	public void setUploaded_date(String uploaded_date) {
		this.uploaded_date = uploaded_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApkname() {
		return apkname;
	}

	public void setApkname(String apkname) {
		this.apkname = apkname;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getApk_path() {
		return apk_path;
	}

	public void setApk_path(String apk_path) {
		this.apk_path = apk_path;
	}
	
}
