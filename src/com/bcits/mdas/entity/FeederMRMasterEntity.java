package com.bcits.mdas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
//@IdClass(MRMasterComPK.class)
@Table(name = "feeder_survey_mrmaster", schema = "dholpur")

/**
 * configuring queries by using @NamedQueries, @NamedQuery annotations for accessing data from db.
 * 
 *
 */
@NamedQueries({
	@NamedQuery(name = "FeederMRMasterEntity.findAllMRMasters", query = "SELECT mrm FROM FeederMRMasterEntity mrm ORDER by mrm.timestamp DESC"),
		@NamedQuery(name = "FeederMRMasterEntity.findMRMaster", query = "SELECT mrm FROM FeederMRMasterEntity mrm WHERE UPPER(mrm.mrCode) LIKE UPPER(:mrCode)"),
		@NamedQuery(name = "FeederMRMasterEntity.validateForMrMaster", query = "SELECT mrm FROM FeederMRMasterEntity mrm WHERE mrm.mrCode LIKE :mrCode AND mrm.password LIKE :password"),
		@NamedQuery(name = "FeederMRMasterEntity.getMobileNumFromMrMaster", query = "SELECT mrm.mobile FROM FeederMRMasterEntity mrm WHERE mrm.mrCode LIKE :mrCode AND mrm.password LIKE :password"),
		@NamedQuery(name = "FeederMRMasterEntity.updateMRMaster", query = "UPDATE FeederMRMasterEntity mrme SET mrme.mrName=:mrName,mrme.address=:address,mrme.mobile=:mobile,mrme.username=:username,mrme.timestamp=:timestamp WHERE mrme.mrCode=:mrCode"),
		//@NamedQuery(name = "FeederMRMasterEntity.findMatchedMRCodes", query = "SELECT mrme.mrCode FROM FeederMRMasterEntity mrme WHERE mrme.sdoCode = :sdoCode"),
		@NamedQuery(name = "FeederMRMasterEntity.removeMRMaster", query = "DELETE FROM FeederMRMasterEntity mrme WHERE mrme.mrCode=:mrCode"),
		@NamedQuery(name = "FeederMRMasterEntity.findMobileUser", query = "SELECT s FROM FeederMRMasterEntity s WHERE s.username=:USERNAME AND s.password =:PASSWORD"),
		@NamedQuery(name = "FeederMRMasterEntity.getMatchedMRCodesForAllocation", query = "SELECT mrme.mrCode FROM FeederMRMasterEntity mrme WHERE mrme.allocationFlag='not allocated'"),
		@NamedQuery(name = "FeederMRMasterEntity.GetNotAllocatedOperators", query = "SELECT mr.mrCode,mr.mrName FROM FeederMRMasterEntity mr WHERE mr.mrCode NOT IN (SELECT d2.mrCode FROM MRDeviceAllocationEntity d2)"),

		
		
})

public class FeederMRMasterEntity implements Serializable {

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "MRCODE")
	private String mrCode;


	@Column(name = "MRNAME")
	private String mrName;

	@Column(name = "MRADDRESS")
	private String address;


	@Column(name = "MRMOBILE")
	private String mobile;


	private String username;


    @OrderBy("timestamp DESC")
	@Column(name = "DATESTAMP")
	private Timestamp timestamp;

    /*@Id
	@Column(name = "SDOCODE")
	private Integer sdoCode;*/
	
	@Column(name="password")
	private String password;


	@Column(name = "allocatedFlag")
	private String allocationFlag;
	
	@Column(name="IMAGE")
	private byte[] image;

	/*@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumns({
		@JoinColumn(name = "MRCODE",referencedColumnName="MRCODE",updatable=false,insertable=false)	
		@JoinColumn(name = "SDOCODE",referencedColumnName="SDOCODE",updatable=false,insertable=false)	
	})
	private List<MrGroup> mrgroups;*/

	/**
	 * Default constructor.
	 */
	
	public FeederMRMasterEntity() {

	}
	

	/**
	 * Parameterized Constructor MRMasterEntity
	 * 
	 * @param MRMasterID
	 * @param MRCode
	 * @param MRName
	 * @param Address
	 * @param Mobile
	 * @param username
	 * @param timestamp
	 * @param SDOCode
	 */
	
	public FeederMRMasterEntity(String mrCode, String mrName, String address,
			String mobile, String username, Timestamp timestamp,
			Integer sdoCode, String password, String allocationFlag,
			byte[] image) {
		super();
		this.mrCode = mrCode;
		this.mrName = mrName;
		this.address = address;
		this.mobile = mobile;
		this.username = username;
		this.timestamp = timestamp;
		//this.sdoCode = sdoCode;
		this.password = password;
		this.allocationFlag = allocationFlag;
		this.image = image;
	}


	public String getAllocationFlag() {
		return allocationFlag;
	}

	


	public void setAllocationFlag(String allocationFlag) {
		this.allocationFlag = allocationFlag;
	}

	public String getMrCode() {
		return mrCode;
	}

	public void setMrCode(String mrCode) {
		this.mrCode = mrCode;
	}

	public String getMrName() {
		return mrName;
	}

	public void setMrName(String mrName) {
		this.mrName = mrName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public String setMobile(String mobile) {
		return this.mobile = mobile;
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

	/*public Integer getSdoCode() {
		return sdoCode;
	}

	public void setSdoCode(Integer sdoCode) {
		this.sdoCode = sdoCode;
	}*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}


	/*public List<MrGroup> getMrgroups() {
		return mrgroups;
	}


	public void setMrgroups(List<MrGroup> mrgroups) {
		this.mrgroups = mrgroups;
	}	*/
	
	
}


