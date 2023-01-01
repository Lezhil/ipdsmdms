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
@Table(name = "DEVICEMASTER", schema = "VCLOUDENGINE")
@NamedQueries({
		@NamedQuery(name = "MRDeviceEntity.findAll", query = "SELECT mrd FROM MRDeviceEntity mrd where cast(mrd.sdoCode as string) like :sdoCode "),
		@NamedQuery(name = "MRDeviceEntity.findMatchedDeviceIds", query = "SELECT mrde.deviceid FROM MRDeviceEntity mrde WHERE mrde.sdoCode=:sdoCode"),
		@NamedQuery(name = "MRDeviceEntity.getMatchedDeviceIdsForAllocation", query = "SELECT mrde.deviceid FROM MRDeviceEntity mrde WHERE  mrde.allocatedflag='not allocated' AND mrde.approvalStatus LIKE 'APPROVED'"),
		//by narendra for gescom
		//@NamedQuery(name = "MRDeviceEntity.ApproveDevice", query = "UPDATE MRDeviceEntity mrde SET mrde.approvalStatus='APPROVED',mrde.status='Registered' WHERE  mrde.deviceid=:deviceid"),
		@NamedQuery(name = "MRDeviceEntity.ApproveDevice", query = "UPDATE MRDeviceEntity mrde SET mrde.approvalStatus='APPROVED' WHERE  mrde.deviceid=:deviceid"),
		//@NamedQuery(name = "MRDeviceEntity.GetNotAllocatedDevice", query = "SELECT d FROM MRDeviceEntity d WHERE d.deviceid NOT IN (SELECT d2.deviceid FROM MRDeviceAllocationEntity d2) AND d.approvalStatus LIKE 'APPROVED'"),
		@NamedQuery(name = "MRDeviceEntity.GetNotAllocatedDevice", query = "SELECT d FROM MRDeviceEntity d WHERE d.allocatedflag LIKE 'NOT ALLOCATED' AND d.approvalStatus LIKE 'APPROVED' AND d.sdoCode=:sdoCode order by d.deviceid"),
		@NamedQuery(name = "MRDeviceEntity.findByDevice", query = "SELECT mrde.deviceid FROM MRDeviceEntity mrde WHERE mrde.deviceid=:deviceid"),
		//@NamedQuery(name = "MRDeviceEntity.update", query = "UPDATE MRDeviceEntity m SET m.status=:status WHERE m.deviceid=:deviceId"),
		//@NamedQuery(name = "MRDeviceEntity.updateStatus", query = "UPDATE MRDeviceEntity mrde SET mrde.status=:status WHERE  mrde.deviceid=:deviceid"),
		@NamedQuery(name = "MRDeviceEntity.updateDeviceSdocode", query = "UPDATE MRDeviceEntity mrde SET mrde.allocatedflag=:allostatus WHERE  mrde.deviceid=:deviceid"),
		@NamedQuery(name = "MRDeviceEntity.updateDeviceMaster", query = "UPDATE MRDeviceEntity mrde SET mrde.allocatedflag=:allostatus WHERE  mrde.deviceid=:deviceid"),
		@NamedQuery(name = "MRDeviceEntity.findByPk", query = "SELECT mrde.id FROm MRDeviceEntity mrde WHERE  mrde.deviceid=:deviceid"),
})

public class MRDeviceEntity {


	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "DEVICEID")
	private String deviceid;

	@Column(name = "MAKE")
	private String make;

	@Column(name = "GPRSSIMNO")
	private String gprsSimNum;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "DATESTAMP")
	private Timestamp timestamp;

	@Column(name = "SDOCODE")
	private Integer sdoCode;
	
	@Column(name = "ALLOCATEDFLAG")
	private String allocatedflag;
	
	@Column(name = "DEVICETYPE")
	private String deviceType;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "approvalstatus")
	private String approvalStatus;
	
	@Column(name = "providedby")
	private String providedby;
	
	@Column(name = "simslot")
	private String simslot;
	
	/*@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SDOCODE",insertable = false, updatable = false, nullable = false) 
	private SiteLocationEntity siteLocationEntity;*/
	

	public MRDeviceEntity() 
	{

	}


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getGprsSimNum() {
		return gprsSimNum;
	}

	public void setGprsSimNum(String gprsSimNum) {
		this.gprsSimNum = gprsSimNum;
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


	public void setAllocatedflag(String allocatedflag) {
		this.allocatedflag = allocatedflag;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getAllocatedflag() {
		return allocatedflag;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}


	public String getProvidedby() {
		return providedby;
	}


	public void setProvidedby(String providedby) {
		this.providedby = providedby;
	}


	@Override
	public String toString() {
		return "MRDeviceEntity [id=" + id + ", deviceid=" + deviceid
				+ ", make=" + make + ", gprsSimNum=" + gprsSimNum
				+ ", username=" + username + ", timestamp=" + timestamp
				+ ", sdoCode=" + sdoCode + ", allocatedflag=" + allocatedflag
				+ ", deviceType=" + deviceType + ", status=" + status
				+ ", approvalStatus=" + approvalStatus + ", providedby="
				+ providedby + "]";
	}


	public String getSimslot() {
		return simslot;
	}


	public void setSimslot(String simslot) {
		this.simslot = simslot;
	}


	/*public SiteLocationEntity getSiteLocationEntity() {
		return siteLocationEntity;
	}


	public void setSiteLocationEntity(SiteLocationEntity siteLocationEntity) {
		this.siteLocationEntity = siteLocationEntity;
	}*/
	
	
}
