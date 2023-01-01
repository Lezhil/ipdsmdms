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
@Table(name = "substation_details", schema = "meter_data")
@NamedQueries({
	@NamedQuery(name="SubstationDetailsEntity.getSubstationBySSTpCode",query="select s from SubstationDetailsEntity s where s.sstpid=:sstpid order by sstpid"),
})
public class SubstationDetailsEntity 
{
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "ss_id")
	private String ssid;
	
	@Column(name = "ss_name")
	private String ss_name;
	
	@Column(name = "ss_capacity")
	private Double sscapacity;
	
	@Column(name = "office_id")
	private Long officeid;

    @Column(name = "parent_id")
	private Integer parentid;
	
	@Column(name = "sstp_id")
	private String sstpid;
	
	@Column(name = "tp_parent_id")
	private String tpparentid;
	
	@Column(name = "deleted")
	private String deleted;
	
	@Column(name = "entry_by")
	private String entry_by;
	
	@Column(name="entry_date")
	private Timestamp entry_date;
	
    @Column(name = "update_by")
	private String update_by;
	
	@Column(name = "update_date")
	private Timestamp update_date;
	
	@Column(name="parent_subdivision")
	private String psdivision;
	
	@Column(name="parent_feeder_voltage")
	private Double pfvoltage;
	
	@Column(name="parent_feeder")
	private String pfeeder;
	
	@Column(name="flag")
	private String flag;
	
	@Column(name="parent_town")
	private String parent_town;
	
	@Column(name="latitude")
	private Double latitude;
	
	@Column(name="longitude")
	private Double longitude;
	
	@Column(name="dcuno")
	private String dcuno;
	
	@Column(name="substation_mva")
	private  Double substationMva;
	
	@Column(name="filename")
	private  String filename;
	
	@Column(name="server_filepath")
	private  String server_filepath;
	
	@Column(name="sld_file")
	private  byte[] sld_file;
	

	public byte[] getSld_file() {
		return sld_file;
	}

	public void setSld_file(byte[] sld_file) {
		this.sld_file = sld_file;
	}

	public String getServer_filepath() {
		return server_filepath;
	}

	public void setServer_filepath(String server_filepath) {
		this.server_filepath = server_filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public String getDcuno() {
		return dcuno;
	}

	public void setDcuno(String dcuno) {
		this.dcuno = dcuno;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getParent_town() {
		return parent_town;
	}

	public void setParent_town(String parent_town) {
		this.parent_town = parent_town;
	}

	public String getSs_name() {
		return ss_name;
	}

	public void setSs_name(String ss_name) {
		this.ss_name = ss_name;
	}

	public Double getSscapacity() {
		return sscapacity;
	}

	public void setSscapacity(Double sscapacity) {
		this.sscapacity = sscapacity;
	}

	public Long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(Long officeid) {
		this.officeid = officeid;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public String getSstpid() {
		return sstpid;
	}

	public void setSstpid(String sstpid) {
		this.sstpid = sstpid;
	}

	public String getTpparentid() {
		return tpparentid;
	}

	public void setTpparentid(String tpparentid) {
		this.tpparentid = tpparentid;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getEntry_by() {
		return entry_by;
	}

	public void setEntry_by(String entry_by) {
		this.entry_by = entry_by;
	}

	public Timestamp getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Timestamp entry_date) {
		this.entry_date = entry_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public Double getPfvoltage() {
		return pfvoltage;
	}

	public void setPfvoltage(Double pfvoltage) {
		this.pfvoltage = pfvoltage;
	}

	public String getPfeeder() {
		return pfeeder;
	}

	public void setPfeeder(String pfeeder) {
		this.pfeeder = pfeeder;
	}

	public String getPsdivision() {
		return psdivision;
	}

	public void setPsdivision(String psdivision) {
		this.psdivision = psdivision;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	
	public Double getSubstationMva() {
		return substationMva;
	}

	public void setSubstationMva(Double substationMva) {
		this.substationMva = substationMva;
	}

}
