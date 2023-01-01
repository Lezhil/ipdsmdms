/**
 * 
 */
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

/**
 * @author Tarik
 *
 */
@Entity
@Table(name="BOUNDARY_DETAILS",schema="meter_data")

@NamedQueries({
	
	@NamedQuery(name="BoundaryMetersEntity.getMeterDetailsByFdrcode", query="select b from BoundaryMetersEntity b where b.tp_feedercode=:tp_feedercode"),
	
})

public class BoundaryMetersEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
	private int id;
	
	@Column(name="feedercode")
	private String feedercode;
	
	@Column(name="tp_feedercode")
	private String tp_feedercode;
	
	@Column(name="feedername")
	private String feedername;
	
	@Column(name="ss_code")
	private String ss_code;
	
	@Column(name="tp_sscode")
	private String tp_sscode;
	
	@Column(name="sdocode")
	private Integer sdocode;
	
	@Column(name="tp_sdocode")
	private String tp_sdocode;
	
	@Column(name="boundary_name")
	private String boundary_name;
	
	@Column(name="boundary_location")
	private String boundary_location;
	
	@Column(name="meter_installed")
	private int meter_installed;
	
	@Column(name="meter_no")
	private String meter_no;
	
	@Column(name="meter_make")
	private String meter_make;
	
	@Column(name="ct_ratio")
	private String ct_ratio;
	
	@Column(name="pt_ratio")
	private String pt_ratio;
	
	@Column(name="mf")
	private Integer  mf;
	
	@Column(name="meter_ratio")
	private String meter_ratio;
	
	@Column(name="imp_exp")
	private String imp_exp;
	
	@Column(name="createdby")
	private String  createdby;
	
	@Column(name="createddate")
	private Timestamp  createddate;
	
	@Column(name="boundary_id")
	private String boundary_id;

	public String getBoundary_id() {
		return boundary_id;
	}

	public void setBoundary_id(String boundary_id) {
		this.boundary_id = boundary_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFeedercode() {
		return feedercode;
	}

	public void setFeedercode(String feedercode) {
		this.feedercode = feedercode;
	}

	public String getTp_feedercode() {
		return tp_feedercode;
	}

	public void setTp_feedercode(String tp_feedercode) {
		this.tp_feedercode = tp_feedercode;
	}

	public String getFeedername() {
		return feedername;
	}

	public void setFeedername(String feedername) {
		this.feedername = feedername;
	}

	public String getSs_code() {
		return ss_code;
	}

	public void setSs_code(String ss_code) {
		this.ss_code = ss_code;
	}

	public String getTp_sscode() {
		return tp_sscode;
	}

	public void setTp_sscode(String tp_sscode) {
		this.tp_sscode = tp_sscode;
	}

	public Integer getSdocode() {
		return sdocode;
	}

	public void setSdocode(Integer sdocode) {
		this.sdocode = sdocode;
	}

	public String getTp_sdocode() {
		return tp_sdocode;
	}

	public void setTp_sdocode(String tp_sdocode) {
		this.tp_sdocode = tp_sdocode;
	}

	public String getBoundary_name() {
		return boundary_name;
	}

	public void setBoundary_name(String boundary_name) {
		this.boundary_name = boundary_name;
	}

	public String getBoundary_location() {
		return boundary_location;
	}

	public void setBoundary_location(String boundary_location) {
		this.boundary_location = boundary_location;
	}

	public int getMeter_installed() {
		return meter_installed;
	}

	public void setMeter_installed(int meter_installed) {
		this.meter_installed = meter_installed;
	}

	public String getMeter_no() {
		return meter_no;
	}

	public void setMeter_no(String meter_no) {
		this.meter_no = meter_no;
	}

	public String getMeter_make() {
		return meter_make;
	}

	public void setMeter_make(String meter_make) {
		this.meter_make = meter_make;
	}

	public String getCt_ratio() {
		return ct_ratio;
	}

	public void setCt_ratio(String ct_ratio) {
		this.ct_ratio = ct_ratio;
	}

	public String getPt_ratio() {
		return pt_ratio;
	}

	public void setPt_ratio(String pt_ratio) {
		this.pt_ratio = pt_ratio;
	}

	public Integer getMf() {
		return mf;
	}

	public void setMf(Integer mf) {
		this.mf = mf;
	}

	public String getMeter_ratio() {
		return meter_ratio;
	}

	public void setMeter_ratio(String meter_ratio) {
		this.meter_ratio = meter_ratio;
	}

	public String getImp_exp() {
		return imp_exp;
	}

	public void setImp_exp(String imp_exp) {
		this.imp_exp = imp_exp;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}
	
	

}
