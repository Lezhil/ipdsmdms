package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="MR_DETAILS",schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="MeterReaderDetailsEntity.getAllData",query="SELECT m FROM MeterReaderDetailsEntity m ORDER BY m.mrname"),
    @NamedQuery(name="MeterReaderDetailsEntity.getDevice",query="SELECT M.device FROM MeterReaderDetailsEntity M WHERE M.device=:device"),
    @NamedQuery(name="MeterReaderDetailsEntity.getDupliMrname",query="SELECT M.mrname FROM MeterReaderDetailsEntity M WHERE M.mrname=:mrname"),
	// Added by Shivanand
	@NamedQuery(name="MeterReaderDetailsEntity.getMRname", query="SELECT M FROM MeterReaderDetailsEntity M WHERE M.device=:device")
	



})
/*Author: Ved Prakash Mishra*/
public class MeterReaderDetailsEntity 
{
	
  /*@Id
  @SequenceGenerator(name = "mriId", sequenceName = "MR_ID")
  @GeneratedValue(generator = "mriId")	
  
  @Column(name="MRID")
  private Integer mriid;*/
  
  
  @Column(name="NAME")
  private String mrname;
  
  @Id
  @Column(name="SBMNO")
  private String device;
  
  @Column(name="USER_ID")
  private String userid;
  
  @Column(name="DATE_STAMP")
  private Date datestamp;

public String getMrname() {
	return mrname;
}

public void setMrname(String mrname) {
	this.mrname = mrname;
}

public String getDevice() {
	return device;
}

public void setDevice(String device) {
	this.device = device;
}

public String getUserid() {
	return userid;
}

public void setUserid(String userid) {
	this.userid = userid;
}

public Date getDatestamp() {
	return datestamp;
}

public void setDatestamp(Date datestamp) {
	this.datestamp = datestamp;
}
  
}
