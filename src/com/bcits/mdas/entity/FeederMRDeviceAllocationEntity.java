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
@Table(name = "feeder_survey_deviceallocation", schema = "dholpur")
@NamedQueries({ 
	@NamedQuery(name = "FeederMRDeviceAllocationEntity.findAll", query = "SELECT mrda FROM FeederMRDeviceAllocationEntity mrda"),
	@NamedQuery(name = "FeederMRDeviceAllocationEntity.DevicesExitOrNot", query = "SELECT COUNT(*) FROM FeederMRDeviceAllocationEntity d"),
	@NamedQuery(name = "FeederMRDeviceAllocationEntity.checkAllocated", query = "SELECT COUNT(*) FROM FeederMRDeviceAllocationEntity d WHERE d.deviceid LIKE :deviceid AND d.mrCode LIKE :mrcode"),
	@NamedQuery(name = "FeederMRDeviceAllocationEntity.findSdoCodeByMrcode", query = "SELECT mrda FROM FeederMRDeviceAllocationEntity mrda where mrda.mrCode=:mrCode"),
	@NamedQuery(name = "FeederMRDeviceAllocationEntity.findBySdoCode", query = "SELECT m.mrCode FROM FeederMRDeviceAllocationEntity m where m.sdoCode=:sdoCode"),
	@NamedQuery(name = "FeederMRDeviceAllocationEntity.findforpaymentapp", query = "SELECT mrda FROM FeederMRDeviceAllocationEntity mrda where mrda.mrCode=:mrCode AND mrda.sdoCode=:sdoCode AND mrda.deviceid=:deviceid")
	
	
})
public class FeederMRDeviceAllocationEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "MRCODE")
	private String mrCode;
	
	@Column(name = "DEVICEID")
	private String deviceid;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "DATESTAMP")
	private Timestamp timestamp;

	@Column(name = "SDOCODE")
	private Integer sdoCode;
	
    /*@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SDOCODE",insertable = false, updatable = false, nullable = false) 
	private SiteLocationEntity siteLocationEntity;*/

	/*@OneToOne
	@JoinColumn(name="MRCODE",referencedColumnName="MRCODE",insertable=false,updatable=false)
    private MRMasterEntity mrMaster;*/
	
	public String getMrCode() {
		return mrCode;
	}

	public void setMrCode(String mrCode) {
		this.mrCode = mrCode;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getSdoCode() {
		return sdoCode;
	}

	public void setSdoCode(Integer sdoCode) {
		this.sdoCode = sdoCode;
	}

	
	public FeederMRDeviceAllocationEntity() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

/*public SiteLocationEntity getSiteLocationEntity() {
		return siteLocationEntity;
	}

	public void setSiteLocationEntity(SiteLocationEntity siteLocationEntity) {
		this.siteLocationEntity = siteLocationEntity;
	}
*/
	/*public MRMasterEntity getMrMaster() {
		return mrMaster;
	}

	public void setMrMaster(MRMasterEntity mrMaster) {
		this.mrMaster = mrMaster;
	}*/

	
}
