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
@Table(name = "DEVICEALLOCATION", schema = "VCLOUDENGINE")
@NamedQueries({ 
	@NamedQuery(name = "MRDeviceAllocationEntity.findAll", query = "SELECT mrda FROM MRDeviceAllocationEntity mrda where cast(mrda.sdoCode as string) like:sdoCode"),
	@NamedQuery(name = "MRDeviceAllocationEntity.DevicesExitOrNot", query = "SELECT COUNT(*) FROM MRDeviceAllocationEntity d"),
	@NamedQuery(name = "MRDeviceAllocationEntity.checkAllocated", query = "SELECT COUNT(*) FROM MRDeviceAllocationEntity d WHERE d.deviceid LIKE :deviceid AND d.mrCode LIKE :mrcode"),
	@NamedQuery(name = "MRDeviceAllocationEntity.findSdoCodeByMrcode", query = "SELECT mrda FROM MRDeviceAllocationEntity mrda where mrda.mrCode=:mrCode"),
	@NamedQuery(name = "MRDeviceAllocationEntity.findBySdoCode", query = "SELECT m.mrCode FROM MRDeviceAllocationEntity m where m.sdoCode=:sdoCode"),
	@NamedQuery(name = "MRDeviceAllocationEntity.findforpaymentapp", query = "SELECT mrda FROM MRDeviceAllocationEntity mrda where mrda.mrCode=:mrCode AND mrda.sdoCode=:sdoCode AND mrda.deviceid=:deviceid"),
	@NamedQuery(name = "MRDeviceAllocationEntity.isUserExist", query = " SELECT h FROM MRDeviceAllocationEntity h WHERE h.sdoCode = :sdoCode AND h.deviceid=:deviceid AND h.mrCode=:mrCode"),
	@NamedQuery(name = "MRDeviceAllocationEntity.getLoginCredentials", query = " SELECT h FROM MRDeviceAllocationEntity h WHERE h.deviceid=:deviceid")
	
})
public class MRDeviceAllocationEntity {

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

	
	public MRDeviceAllocationEntity() {

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
