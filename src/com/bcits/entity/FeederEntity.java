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
@Table(name = "FEEDERDETAILS", schema = "meter_data")
@NamedQueries({	
	@NamedQuery(name="FeederEntity.getDetailsById", query="select a from FeederEntity a where a.boundary_id=:boundary_id"),	
	@NamedQuery(name="FeederEntity.meterNumbers", query="select a.meterno from FeederEntity a where a.meterno is not null and a.meterno not like ''"),
	@NamedQuery(name="FeederEntity.getFeederDetailsByFeederId", query="select a from FeederEntity a where a.tpfdrid=:tpfdrid and a.tpTownCode=:tpTownCode and a.crossfdr='0' and (a.deleted='0' or a.deleted is null)"),
	@NamedQuery(name="FeederEntity.getFeederDetailsByMeterno", query="select a from FeederEntity a where a.meterno=:meterno"),
	@NamedQuery(name="FeederEntity.getBoundaryDetailsByFeederId", query="select a from FeederEntity a where a.tpfdrid=:tpfdrid and a.tpTownCode=:tpTownCode and a.boundary_id=:boundary_id and a.crossfdr='1' and (a.deleted='0' or a.deleted is null)"),
	@NamedQuery(name="FeederEntity.getMtrExistOnFeeder", query="select a from FeederEntity a where a.tpfdrid=:tpfdrid and a.crossfdr='0' and (a.deleted='0' or a.deleted is null) and a.meterno is not null "),
	@NamedQuery(name="FeederEntity.checkMeterAttachedonFeeder", query="select a from FeederEntity a where a.tpfdrid=:tpfdrid  and a.crossfdr='0' and (a.deleted='0' or a.deleted is null) and a.meterno=:meterno"),
	@NamedQuery(name="FeederEntity.getBoundaryByFeederMrtId", query="select a from FeederEntity a where a.tpfdrid=:tpfdrid and a.boundary_id=:boundary_id  and a.meterno=:meterno and a.crossfdr='1' and (a.deleted='0' or a.deleted is null) and a.meterno is not null"),
	@NamedQuery(name="FeederEntity.getFeederBySubstationMrtId",query="select a from FeederEntity a where a.tpTownCode=:tpTownCode and a.tpfdrid=:tpfdrid and a.tpparentid=:tpparentid and a.meterno=:meterno and a.meterno is not null and a.crossfdr='0' and (a.deleted='0' or a.deleted is null)"),
			
})

public class FeederEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "CROSSFDR")
	private int crossfdr;

	@Column(name = "VOLTAGELEVEL")
	private Double voltagelevel;

	@Column(name = "FEEDERNAME")
	private String feedername;

	@Column(name = "OFFICEID")
	private int officeid;

	@Column(name = "PARENTID")
	private String parentid;

	@Column(name = "TPPARENTID")
	private String tpparentid;

	@Column(name = "METERNO")
	private String meterno;

	@Column(name = "MANUFACTURER")
	private String manufacturer;

	@Column(name = "MF")
	private Double mf;

	@Column(name = "CONSUMPPERCENT")
	private Double consumppercent;


	@Column(name = "ENTRYBY")
	private String entryby;

	@Column(name = "ENTRYDATE")
	private Timestamp entrydate;

	@Column(name = "UPDATEBY")
	private String updateby;

	@Column(name = "UPDATEDATE")
	private Timestamp updatedate;

	@Column(name = "TP_FDR_ID")
	private String tpfdrid;

	@Column(name = "FDR_ID")
	private String fdrid;

	@Column(name = "volt_mf")
	private Double volt_mf;

	@Column(name = "current_mf")
	private Double current_mf;

	@Column(name = "deleted")
	private Integer deleted;
	
	@Column(name="meter_installed")
	private Integer meter_installed;
	
	@Column(name = "meterchangedate")
	private Timestamp meterchangedate;
	
	
	@Column(name="mfchangedate")
	private Timestamp mfchangedate;
	
	@Column(name = "mfflag")
	private Integer mfflag;
	
	
	@Column(name="meterchangeflag")
	private Integer meterchangeflag;
	
	@Column(name="oldfdrmtrno")
	private String oldfdrmtrno;
	
	@Column(name="feeder_type")
	private String feeder_type;
	
	@Column (name="geo_cord_x")
	private Double geo_cord_x;
	
	@Column (name="geo_cord_y")
	private Double geo_cord_y;

	public Double getGeo_cord_x() {
		return geo_cord_x;
	}

	public void setGeo_cord_x(Double geo_cord_x) {
		this.geo_cord_x = geo_cord_x;
	}

	public Double getGeo_cord_y() {
		return geo_cord_y;
	}

	public void setGeo_cord_y(Double geo_cord_y) {
		this.geo_cord_y = geo_cord_y;
	}

	public Integer getMeter_installed() {
		return meter_installed;
	}

	public void setMeter_installed(Integer meter_installed) {
		this.meter_installed = meter_installed;
	}

	public Boolean getBoundry_feeder() {
		return boundry_feeder;
	}

	public void setBoundry_feeder(Boolean boundry_feeder) {
		this.boundry_feeder = boundry_feeder;
	}

	@Column(name = "CT_RATIO")
	private String ct_ratio;

	@Column(name = "PT_RATIO")
	private String pt_ratio;

	@Column(name = "METER_RATIO")
	private String meter_ratio;

	@Column(name = "BOUNDARY_ID")
	private String boundary_id;

	@Column(name = "BOUNDARY_NAME")
	private String boundary_name;

	@Column(name = "BOUNDARY_LOCATION")
	private String boundary_location;

	@Column(name = "IMP_EXP")
	private String imp_exp;
	
	@Column(name = "tp_town_code")
	private String tpTownCode;
	
	
//	@Column(name="TP_SDOCODE")
//	private String tp_sdocode;
	
	public String getTpTownCode() {
		return tpTownCode;
	}

	public void setTpTownCode(String tpTownCode) {
		this.tpTownCode = tpTownCode;
	}

	@Column(name = "BOUNDRY_FEEDER")
	private Boolean boundry_feeder;

	


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

	public String getMeter_ratio() {
		return meter_ratio;
	}

	public void setMeter_ratio(String meter_ratio) {
		this.meter_ratio = meter_ratio;
	}

	public String getBoundary_id() {
		return boundary_id;
	}

	public void setBoundary_id(String boundary_id) {
		this.boundary_id = boundary_id;
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

	public String getImp_exp() {
		return imp_exp;
	}

	public void setImp_exp(String imp_exp) {
		this.imp_exp = imp_exp;
	}

//	public String getTp_sdocode() {
//		return tp_sdocode;
//	}
//
//	public void setTp_sdocode(String tp_sdocode) {
//		this.tp_sdocode = tp_sdocode;
//	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getFdrid() {
		return fdrid;
	}

	public Double getVolt_mf() {
		return volt_mf;
	}

	public void setVolt_mf(Double volt_mf) {
		this.volt_mf = volt_mf;
	}

	public Double getCurrent_mf() {
		return current_mf;
	}

	public void setCurrent_mf(Double current_mf) {
		this.current_mf = current_mf;
	}

	public void setFdrid(String fdrid) {
		this.fdrid = fdrid;
	}

	public String getTpfdrid() {
		return tpfdrid;
	}

	public void setTpfdrid(String tpfdrid) {
		this.tpfdrid = tpfdrid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getVoltagelevel() {
		return voltagelevel;
	}

	public void setVoltagelevel(Double voltagelevel) {
		this.voltagelevel = voltagelevel;
	}

	public String getFeedername() {
		return feedername;
	}

	public void setFeedername(String feedername) {
		this.feedername = feedername;
	}

	public int getOfficeid() {
		return officeid;
	}

	public void setOfficeid(int officeid) {
		this.officeid = officeid;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getTpparentid() {
		return tpparentid;
	}

	public void setTpparentid(String tpparentid) {
		this.tpparentid = tpparentid;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Double getMf() {
		return mf;
	}

	public void setMf(Double mf) {
		this.mf = mf;
	}



	public Double getConsumppercent() {

		return consumppercent;
	}

	public void setConsumppercent(Double consumppercent) {
		this.consumppercent = consumppercent;
	}

	public String getEntryby() {
		return entryby;
	}

	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

	public Timestamp getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public int getCrossfdr() {
		return crossfdr;
	}

	public void setCrossfdr(int crossfdr) {
		this.crossfdr = crossfdr;
	}

	public Timestamp getMeterchangedate() {
		return meterchangedate;
	}

	public void setMeterchangedate(Timestamp meterchangedate) {
		this.meterchangedate = meterchangedate;
	}

	public Timestamp getMfchangedate() {
		return mfchangedate;
	}

	public void setMfchangedate(Timestamp mfchangedate) {
		this.mfchangedate = mfchangedate;
	}

	public Integer getMfflag() {
		return mfflag;
	}

	public void setMfflag(Integer mfflag) {
		this.mfflag = mfflag;
	}

	public Integer getMeterchangeflag() {
		return meterchangeflag;
	}

	public void setMeterchangeflag(Integer meterchangeflag) {
		this.meterchangeflag = meterchangeflag;
	}

	public String getOldfdrmtrno() {
		return oldfdrmtrno;
	}

	public void setOldfdrmtrno(String oldfdrmtrno) {
		this.oldfdrmtrno = oldfdrmtrno;
	}

	public String getFeeder_type() {
		return feeder_type;
	}

	public void setFeeder_type(String feeder_type) {
		this.feeder_type = feeder_type;
	}
	
	
	
	
}
