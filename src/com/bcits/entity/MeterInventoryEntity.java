package com.bcits.entity;

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
@Table(name="METER_INVENTORY",schema="meter_data")
@NamedQueries({									
	@NamedQuery(name="METER_INVENTORY.getMeterDetailsData",query="select model from MeterInventoryEntity model"),
	@NamedQuery(name="METER_INVENTORY.checkMeterNoExist",query="select model from MeterInventoryEntity model where model.meterno=:meterno"),
	@NamedQuery(name="METER_INVENTORY.getMeterInventoryEntity",query="select model from MeterInventoryEntity model where model.meterno=:meterno"),
	@NamedQuery(name="METER_INVENTORY.getALLInstockMeters",query="select distinct meterno from MeterInventoryEntity model where model.meter_status=:meter_status "),
	@NamedQuery(name="METER_INVENTORY.getLocInstockMeters",query="select distinct meterno from MeterInventoryEntity model where model.meter_status=:meter_status and model.strLoc=:strLoc"),
	@NamedQuery(name="METER_INVENTORY.getMetersBasedOnStatus",query="select model from MeterInventoryEntity model where model.meter_status=:meter_status"),
	@NamedQuery(name="METER_INVENTORY.getDataOnMetermake",query="select model from MeterInventoryEntity model where model.meter_make=:metermake"),
	@NamedQuery(name="METER_INVENTORY.getDataOnManufactureyear",query="select model from MeterInventoryEntity model where model.month=:manufactureyear and month IS NOT NULL"),
	@NamedQuery(name="METER_INVENTORY.updateStatus",query="UPDATE MeterInventoryEntity m set m.meter_status=:status  where m.meterno=:meterno"),
	
})
public class MeterInventoryEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="METERNO")
	private String meterno;
	
	@Column(name="METER_MAKE")
	private String meter_make;
	
	@Column(name="METER_MODEL")
	private String meter_model;
	
	@Column(name="CT_RATIO")
	private Double ct_ratio;
	
	@Column(name="PT_RATIO")
	private Double pt_ratio;
	
	@Column(name="METER_ACCURACY_CLASS")
	private String meter_accuracy_class;
	
	@Column(name="METER_CURRENT_RATING")
	private String meter_current_rating;
	
	@Column(name="METER_VOLTAGE_RATING")
	private String meter_voltage_rating;
	
	@Column(name="METER_CONNECTION_TYPE")
	private Short meter_connection_type;
	
	@Column(name="METER_COMMISIONING")
	private String meter_commisioning;
	
	@Column(name="METER_IP_PERIOD")
	private String meter_ip_period;
	
	@Column(name="METER_CONSTANT")
	private Double meter_constant;
	
	@Column(name="TENDER_NO")
	private String tender_no;
	
	@Column(name="MANUFACTURE_YEAR_MONTH")
	private String month;
	
	@Column(name="WARRENTY_YEARS")
	private String warrenty_years;
	
	@Column(name="METER_STATUS")
	private String meter_status;
	
	@Column(name="ENTRYDATE")
	private Timestamp entrydate;
	
	@Column(name="ENTRYBY")
	private String entryby;
	
	@Column(name="UPDATEDATE")
	private Timestamp updatedate;
	
	@Column(name="UPDATEBY")
	private String updateby;
	@Column(name="mrflag")
	private String mrFlag;
	@Column(name="store_loc")
	private String strLoc;
	@Column(name="store_desc")
	private String strDesc;
	@Column(name="connection_type")
	private String connection_type;
	@Column(name="meterdigit")
	private Integer meterdigit;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Double getCt_ratio() {
		return ct_ratio;
	}

	public void setCt_ratio(Double ct_ratio) {
		this.ct_ratio = ct_ratio;
	}

	public Double getPt_ratio() {
		return pt_ratio;
	}

	public void setPt_ratio(Double pt_ratio) {
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

	public Short getMeter_connection_type() {
		return meter_connection_type;
	}

	public void setMeter_connection_type(Short meter_connection_type) {
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

	public Double getMeter_constant() {
		return meter_constant;
	}

	public void setMeter_constant(Double meter_constant) {
		this.meter_constant = meter_constant;
	}

	public String getTender_no() {
		return tender_no;
	}

	public void setTender_no(String tender_no) {
		this.tender_no = tender_no;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

	public String getEntryby() {
		return entryby;
	}

	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}

	public Timestamp getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public String getMrFlag() {
		return mrFlag;
	}

	public void setMrFlag(String mrFlag) {
		this.mrFlag = mrFlag;
	}

	public String getStrLoc() {
		return strLoc;
	}

	public void setStrLoc(String strLoc) {
		this.strLoc = strLoc;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public String getConnection_type() {
		return connection_type;
	}

	public void setConnection_type(String connection_type) {
		this.connection_type = connection_type;
	}

	public Integer getMeterdigit() {
		return meterdigit;
	}

	public void setMeterdigit(Integer meterdigit) {
		this.meterdigit = meterdigit;
	}

	

	
}
