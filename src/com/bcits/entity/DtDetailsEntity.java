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
@Table(name="dtdetails" , schema="meter_data")
@NamedQueries({
	@NamedQuery(name="DtDetailsEntity_getData",query="SELECT model FROM DtDetailsEntity model  ORDER BY id ASC"),
	@NamedQuery(name="dtdetails.getDtDetailsByDttpid",query="SELECT model FROM DtDetailsEntity model where model.dttpid=:dttpid and model.tp_town_code=:tp_towncode"),
	@NamedQuery(name="dtdetails.getDtDetailsBydt_tpid",query="SELECT model FROM DtDetailsEntity model where model.dttpid=:dttpid"),
	@NamedQuery(name="DtDetailsEntity.getDtLocationDetails",query="select d.subdivision,d.dtname,d.dtcapacity,d.crossdt,d.dt_id, d.meterno from DtDetailsEntity d where d.meterno=:mtrNum or d.dt_id=:dtId"),
	@NamedQuery(name="DtDetailsEntity.getDtByMeterno",query="select d  from DtDetailsEntity d where d.meterno=:meterno"),
	@NamedQuery(name="DtDetailsEntity.getDtByFdrcodeMrtId",query="select d from DtDetailsEntity d where d.dttpid=:dttpid and d.tpparentid=:tpparentid and d.meterno=:meterno and d.tp_town_code=:tp_town_code"),
	@NamedQuery(name="DtDetailsEntity.getDtDetailsByTownFdrDtId",query="select d from DtDetailsEntity d where d.dttpid=:dttpid and d.tpparentid=:tpparentid and d.tp_town_code=:tp_town_code"),
})
public class DtDetailsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="id")
	private long id; 
	
	@Column(name="CROSSDT")
	private Integer crossdt; 
	
	@Column(name="DT_ID")
	private String dt_id;
	
	@Column(name="DTTYPE")
	private String dttype;
	

	@Column(name="DTNAME")
	private String dtname; 
	
	@Column(name="DTCAPACITY")
	private Double dtcapacity; 
	
	@Column(name="PHASE")
	private Integer phase; 
	
	@Column(name="OFFICEID")
	private Long officeid;
	
	
	@Column(name="PARENTID")
	private String parentid; 
	
	@Column(name="DTTPID")
	private String dttpid; 
	
	@Column(name="TPPARENTID")
	private String tpparentid; 
	
	@Column(name="METERNO")
	private String meterno;
	
	@Column(name="METERMANUFACTURE")
	private String metermanufacture;
	
	@Column(name="MF")
	private Double mf;
	
	@Column(name="CONSUMPTIONPERC")
	private Double consumptionperc; 
	
	@Column(name="DELETED")
	private Integer deleted;
	
	@Column(name="ENTRYBY")
	private String entryby;
	
	@Column(name="ENTRYDATE")
	private Timestamp entrydate;
	
	@Column(name="UPDATE")
	private String update;
	
	@Column(name="UPDATEDATE")
	private Timestamp updatedate;
	
	@Column(name="volt_mf")
	private Double volt_mf;
	
	@Column(name="current_mf")
	private Double current_mf;
	
	@Column(name="SUBDIVISION")
	private String subdivision;
	
	@Column(name="PARENT_FEEDER")
	private String parent_feeder;

	@Column(name="PARENT_SUBSTATION")
	private String parent_substation;

	@Column(name="meterchangedate")
	private Timestamp meterchangedate;
	
	@Column(name="mfchangedate")
	private Timestamp mfchangedate;
	
	@Column(name="meterchangeflag")
	private Integer meterchangeflag;
	
	@Column(name="mfflag")
	private Integer mfflag;
	
	@Column(name="tp_town_code")
	private String tp_town_code;
	
	@Column(name="olddtmtr")
	private String olddtmtr;
	
	@Column(name="geo_cord_x")
	private Double geo_cord_x;
	
	@Column(name="geo_cord_y")
	private Double geo_cord_y;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getCrossdt() {
		return crossdt;
	}

	public void setCrossdt(Integer crossdt) {
		this.crossdt = crossdt;
	}

	public String getDt_id() {
		return dt_id;
	}

	public void setDt_id(String dt_id) {
		this.dt_id = dt_id;
	}

	public String getDttype() {
		return dttype;
	}

	public void setDttype(String dttype) {
		this.dttype = dttype;
	}

	public String getDtname() {
		return dtname;
	}

	public void setDtname(String dtname) {
		this.dtname = dtname;
	}

	public Double getDtcapacity() {
		return dtcapacity;
	}

	public void setDtcapacity(Double dtcapacity) {
		this.dtcapacity = dtcapacity;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public Long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(Long officeid) {
		this.officeid = officeid;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getDttpid() {
		return dttpid;
	}

	public void setDttpid(String dttpid) {
		this.dttpid = dttpid;
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

	public String getMetermanufacture() {
		return metermanufacture;
	}

	public void setMetermanufacture(String metermanufacture) {
		this.metermanufacture = metermanufacture;
	}

	public Double getMf() {
		return mf;
	}

	public void setMf(Double mf) {
		this.mf = mf;
	}

	public Double getConsumptionperc() {
		return consumptionperc;
	}

	public void setConsumptionperc(Double consumptionperc) {
		this.consumptionperc = consumptionperc;
	}


	public String getEntryby() {
		return entryby;
	}

	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public Timestamp getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
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

	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public String getParent_feeder() {
		return parent_feeder;
	}

	public void setParent_feeder(String parent_feeder) {
		this.parent_feeder = parent_feeder;
	}

	public String getParent_substation() {
		return parent_substation;
	}

	public void setParent_substation(String parent_substation) {
		this.parent_substation = parent_substation;
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

	public Integer getMeterchangeflag() {
		return meterchangeflag;
	}

	public void setMeterchangeflag(Integer meterchangeflag) {
		this.meterchangeflag = meterchangeflag;
	}

	public Integer getMfflag() {
		return mfflag;
	}

	public void setMfflag(Integer mfflag) {
		this.mfflag = mfflag;
	}

	
	@Override
	public String toString() {
		return "DtDetailsEntity [id=" + id + ", crossdt=" + crossdt
				+ ", dt_id=" + dt_id + ", dttype=" + dttype + ", dtname="
				+ dtname + ", dtcapacity=" + dtcapacity + ", phase=" + phase
				+ ", officeid=" + officeid + ", parentid=" + parentid
				+ ", dttpid=" + dttpid + ", tpparentid=" + tpparentid
				+ ", meterno=" + meterno + ", metermanufacture="
				+ metermanufacture + ", mf=" + mf + ", consumptionperc="
				+ consumptionperc + ", deleted=" + deleted + ", entryby="
				+ entryby + ", entrydate=" + entrydate + ", update=" + update
				+ ", updatedate=" + updatedate + ", volt_mf=" + volt_mf
				+ ", current_mf=" + current_mf + ", subdivision=" + subdivision
				+ ", parent_feeder=" + parent_feeder + ", parent_substation="
				+ parent_substation + ", meterchangedate=" + meterchangedate
				+ ", mfchangedate=" + mfchangedate + ", meterchangeflag="
				+ meterchangeflag + ", mfflag=" + mfflag + "]";
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getTp_town_code() {
		return tp_town_code;
	}

	public void setTp_town_code(String tp_town_code) {
		this.tp_town_code = tp_town_code;
	}

	public String getOlddtmtr() {
		return olddtmtr;
	}

	public void setOlddtmtr(String olddtmtr) {
		this.olddtmtr = olddtmtr;
	}

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
	
	}
