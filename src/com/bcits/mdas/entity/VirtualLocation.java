package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="virtual_location",schema="meter_data")

@NamedQueries({
		@NamedQuery(name="VirtualLocation.all",query="select v from VirtualLocation v"),
		@NamedQuery(name="VirtualLocation.findvl",query="select v from VirtualLocation v where v.vlName=:vl"),
		@NamedQuery(name="VirtualLocation.findvldetails",query="select v from VirtualLocation v where v.vlID=:vl"),
		@NamedQuery(name="VirtualLocation.getvlnames",query="select v.vlName from VirtualLocation v")
		//@NamedQuery(name="VirtualLocation.maxID",query="select max(to_number(substring(v.vl_id from 3 for 5),'9999')) from VirtualLocation v")
})
public class VirtualLocation {
	@Id
	@Column(name="vl_id")
	private String vlID;
	@Column(name="vl_name")
	private String vlName;
	@Column(name="location_type")
	private String locationType;
	@Column(name="location_code")
	private String locationCode;
	@Column(name="entry_date")
	private Timestamp entryDate;
	@Column(name="entry_by")
	private String entryBy;
	@Column(name="update_date")
	private Timestamp updateDate;
	@Column(name="update_by")
	private String updateBy;
	@Column(name="subdivision")
	private String subdivision;
	public String getVlID() {
		return vlID;
	}
	public void setVlID(String vlID) {
		this.vlID = vlID;
	}
	public String getVlName() {
		return vlName;
	}
	public void setVlName(String vlName) {
		this.vlName = vlName;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public Timestamp getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}
	public String getEntryBy() {
		return entryBy;
	}
	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	

}
