package com.bcits.mdas.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@IdClass(MeterInventoryMobileID.class)
@Table(name = "meter_inventory", schema = "meter_data")
@NamedQueries({ @NamedQuery(name = "MeterInventoryMobileEntity.findAll", query = "SELECT a FROM MeterInventoryMobileEntity a where a.meter_status = 'ISSUED' and a.mrflag = :mrflag and a.sitecode = :sitecode ")})

public class MeterInventoryMobileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "ID")
	private Integer id;

	@Id
	@Column(name = "METERNO")
	private String meterno;

	@Column(name = "METER_MAKE")
	private String meter_make	;

	@Column(name = "METER_MODEL")
	private String meter_model	;

	@Column(name = "CT_RATIO")
	private Integer ct_ratio	;

	@Column(name = "PT_RATIO")
	private Integer pt_ratio	;

	@Column(name = "meter_accuracy_class")
	private String meter_accuracy_class	;

	@Column(name = "meter_current_rating")
	private String meter_current_rating	;

	@Column(name = "meter_voltage_rating")
	private String meter_voltage_rating	;

	@Column(name = "meter_connection_type")
	private Integer meter_connection_type	;

	@Column(name = "meter_commisioning")
	private String meter_commisioning	;

	@Column(name = "meter_ip_period")
	private String meter_ip_period	;

	@Column(name = "meter_constant")
	private Integer meter_constant	;

	@Column(name = "tender_no")
	private String tender_no	;

	@Column(name = "manufacture_year_month")
	private String manufacture_year_month	;

	@Column(name = "warrenty_years")
	private String warrenty_years	;

	@Column(name = "meter_status")
	private String meter_status	;

	@Column(name = "entrydate")
	private Date entrydate	;

	@Column(name = "entryby")
	private String entryby	;

	@Column(name = "updatedate")
	private Date updatedate	;

	@Column(name = "updateby")
	private String updateby	;

	@Column(name = "mrflag")
	private String mrflag	;

	@Column(name = "sitecode")
	private String sitecode	;
	
	@Column(name = "store_loc")
	private String store_loc;
	
	@Column(name = "store_desc")
	private String store_desc;


	public MeterInventoryMobileEntity() {

	}


	public MeterInventoryMobileEntity(Integer id, String meterno, String meter_make, String meter_model,
			Integer ct_ratio, Integer pt_ratio, String meter_accuracy_class, String meter_current_rating,
			String meter_voltage_rating, Integer meter_connection_type, String meter_commisioning,
			String meter_ip_period, Integer meter_constant, String tender_no, String manufacture_year_month,
			String warrenty_years, String meter_status, Date entrydate, String entryby, Date updatedate,
			String updateby, String mrflag , String sitecode,String store_loc,String store_desc) {
		super();
		this.id = id;
		this.meterno = meterno;
		this.meter_make = meter_make;
		this.meter_model = meter_model;
		this.ct_ratio = ct_ratio;
		this.pt_ratio = pt_ratio;
		this.meter_accuracy_class = meter_accuracy_class;
		this.meter_current_rating = meter_current_rating;
		this.meter_voltage_rating = meter_voltage_rating;
		this.meter_connection_type = meter_connection_type;
		this.meter_commisioning = meter_commisioning;
		this.meter_ip_period = meter_ip_period;
		this.meter_constant = meter_constant;
		this.tender_no = tender_no;
		this.manufacture_year_month = manufacture_year_month;
		this.warrenty_years = warrenty_years;
		this.meter_status = meter_status;
		this.entrydate = entrydate;
		this.entryby = entryby;
		this.updatedate = updatedate;
		this.updateby = updateby;
		this.mrflag = mrflag;
		this.sitecode=sitecode;
		this.store_loc=store_loc;
		this.store_desc=store_desc;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getMeterno() {
		return meterno;
	}


	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}


	public String getMeter_make() {
		return meter_make;
	}


	public void setMeter_make(String meter_make) {
		this.meter_make = meter_make;
	}


	public String getMeter_model() {
		return meter_model;
	}


	public void setMeter_model(String meter_model) {
		this.meter_model = meter_model;
	}


	public Integer getCt_ratio() {
		return ct_ratio;
	}


	public void setCt_ratio(Integer ct_ratio) {
		this.ct_ratio = ct_ratio;
	}


	public Integer getPt_ratio() {
		return pt_ratio;
	}


	public void setPt_ratio(Integer pt_ratio) {
		this.pt_ratio = pt_ratio;
	}


	public String getMeter_accuracy_class() {
		return meter_accuracy_class;
	}


	public void setMeter_accuracy_class(String meter_accuracy_class) {
		this.meter_accuracy_class = meter_accuracy_class;
	}


	public String getMeter_current_rating() {
		return meter_current_rating;
	}


	public void setMeter_current_rating(String meter_current_rating) {
		this.meter_current_rating = meter_current_rating;
	}


	public String getMeter_voltage_rating() {
		return meter_voltage_rating;
	}


	public void setMeter_voltage_rating(String meter_voltage_rating) {
		this.meter_voltage_rating = meter_voltage_rating;
	}


	public Integer getMeter_connection_type() {
		return meter_connection_type;
	}


	public void setMeter_connection_type(Integer meter_connection_type) {
		this.meter_connection_type = meter_connection_type;
	}


	public String getMeter_commisioning() {
		return meter_commisioning;
	}


	public void setMeter_commisioning(String meter_commisioning) {
		this.meter_commisioning = meter_commisioning;
	}


	public String getMeter_ip_period() {
		return meter_ip_period;
	}


	public void setMeter_ip_period(String meter_ip_period) {
		this.meter_ip_period = meter_ip_period;
	}


	public Integer getMeter_constant() {
		return meter_constant;
	}


	public void setMeter_constant(Integer meter_constant) {
		this.meter_constant = meter_constant;
	}


	public String getTender_no() {
		return tender_no;
	}


	public void setTender_no(String tender_no) {
		this.tender_no = tender_no;
	}


	public String getManufacture_year_month() {
		return manufacture_year_month;
	}


	public void setManufacture_year_month(String manufacture_year_month) {
		this.manufacture_year_month = manufacture_year_month;
	}


	public String getWarrenty_years() {
		return warrenty_years;
	}


	public void setWarrenty_years(String warrenty_years) {
		this.warrenty_years = warrenty_years;
	}


	public String getMeter_status() {
		return meter_status;
	}


	public void setMeter_status(String meter_status) {
		this.meter_status = meter_status;
	}


	public Date getEntrydate() {
		return entrydate;
	}


	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}


	public String getEntryby() {
		return entryby;
	}


	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}


	public Date getUpdatedate() {
		return updatedate;
	}


	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}


	public String getUpdateby() {
		return updateby;
	}


	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}


	public String getMrflag() {
		return mrflag;
	}


	public void setMrflag(String mrflag) {
		this.mrflag = mrflag;
	}


	public String getSitecode() {
		return sitecode;
	}


	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}


	public String getStore_loc() {
		return store_loc;
	}


	public void setStore_loc(String store_loc) {
		this.store_loc = store_loc;
	}


	public String getStore_desc() {
		return store_desc;
	}


	public void setStore_desc(String store_desc) {
		this.store_desc = store_desc;
	}


}
class MeterInventoryMobileID implements Serializable
{
	private Integer id;
	private String meterno;


}
