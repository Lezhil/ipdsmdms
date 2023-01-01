/**
 * 
 */
package com.bcits.mdas.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Tarik
 *
 */

@Entity
@Table(name="power_onoff", schema="meter_data")
public class PowerOnOffEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name="clientid")
	private String clientid;
	
	@Column(name="transid")
	private String transId;
	
	@Column(name="region")
	private String region;
	
	@Column(name="regioncode")
	private String regioncode;
	
	@Column(name="circle")
	private String circle;
	
	@Column(name="circlecode")
	private String circlecode;
	
//	@Column(name="division")
//	private String division;
//	@Column(name="division_code")
//	private String division_code;
//	@Column(name="subdivision")
//	private String subdivision;
//	@Column(name="subdivisioncode")
//	private String subdivisioncode;
//	@Column(name="section")
//	private String section;
//	@Column(name="sectioncode")
//	private String sectioncode;
	
	@Column(name="town")
	private String town;
	
	@Column(name="towncode")
	private String towncode;
	
	@Column(name="substation")
	private String substation;
	
	@Column(name="substationcode")
	private String substationcode;
	
	@Column(name="dcuno")
	private String dcuno;
	
	@Column(name="dcucode")
	private String dcucode;
	
	@Column(name="feedercode")
	private String feedercode;
	
	@Column(name="feedername")
	private String feedername;
	
	@Column(name="feeder_type")
	private String feeder_type;
	
	@Column(name="meterid")
	private String meterid;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="totalnooccurence")
	private Integer totalNoOccurence;
	
	@Column(name="totalpowerfailureduration")
	private Integer totalPowerFailureDuration;
	
	@Column(name="time_stamp")
	private Timestamp time_stamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getCirclecode() {
		return circlecode;
	}

	public void setCirclecode(String circlecode) {
		this.circlecode = circlecode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getTowncode() {
		return towncode;
	}

	public void setTowncode(String towncode) {
		this.towncode = towncode;
	}

	public String getSubstation() {
		return substation;
	}

	public void setSubstation(String substation) {
		this.substation = substation;
	}

	public String getSubstationcode() {
		return substationcode;
	}

	public void setSubstationcode(String substationcode) {
		this.substationcode = substationcode;
	}

	public String getDcuno() {
		return dcuno;
	}

	public void setDcuno(String dcuno) {
		this.dcuno = dcuno;
	}

	public String getDcucode() {
		return dcucode;
	}

	public void setDcucode(String dcucode) {
		this.dcucode = dcucode;
	}

	public String getFeedercode() {
		return feedercode;
	}

	public void setFeedercode(String feedercode) {
		this.feedercode = feedercode;
	}

	public String getFeedername() {
		return feedername;
	}

	public void setFeedername(String feedername) {
		this.feedername = feedername;
	}

	public String getFeeder_type() {
		return feeder_type;
	}

	public void setFeeder_type(String feeder_type) {
		this.feeder_type = feeder_type;
	}

	public String getMeterid() {
		return meterid;
	}

	public void setMeterid(String meterid) {
		this.meterid = meterid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTotalNoOccurence() {
		return totalNoOccurence;
	}

	public void setTotalNoOccurence(Integer totalNoOccurence) {
		this.totalNoOccurence = totalNoOccurence;
	}

	public Integer getTotalPowerFailureDuration() {
		return totalPowerFailureDuration;
	}

	public void setTotalPowerFailureDuration(Integer totalPowerFailureDuration) {
		this.totalPowerFailureDuration = totalPowerFailureDuration;
	}

	public Timestamp getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}	
	
	
	

}
