package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER", schema = "METER_DATA")
@NamedQueries({
	 @NamedQuery(name="FeederMasterEntity.getAllFeederDataMobile",query="SELECT abi FROM FeederMasterEntity abi where imeino=:imeiNo"),
	 @NamedQuery(name="FeederMasterEntity.getDistinctDistrict",query="select DISTINCT district FROM FeederMasterEntity abi where district <> 'null'"),
	 @NamedQuery(name="FeederMasterEntity.findAll",query="SELECT fm FROM FeederMasterEntity fm where fm.district = :district and fdrname is not null and fdrname <> '0' and feedsurvey = '0' "),
	 @NamedQuery(name="FeederMasterEntity.getFeederData",query="SELECT fm FROM FeederMasterEntity fm where fm.mtrno = :mtrNo"),
	 @NamedQuery(name="FeederMasterEntity.getMeterDetailsForXml",query="SELECT fm.mtrno, fm.fdrcode, fm.fdrname, fm.zone, fm.circle, fm.division FROM FeederMasterEntity fm where  fm.modem_sl_no is not null and fm.modem_sl_no !='' "),
	 
	 @NamedQuery(name="FeederMasterEntity.getCorporateDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,	COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	FeederMasterEntity FM"),
	 @NamedQuery(name="FeederMasterEntity.getZoneDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	FeederMasterEntity FM WHERE FM.zone=:zone"),
	 @NamedQuery(name="FeederMasterEntity.getCircleDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	FeederMasterEntity FM WHERE FM.circle=:circle and FM.zone=:circle"),
	 @NamedQuery(name="FeederMasterEntity.getDivisionDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	FeederMasterEntity FM WHERE FM.division=:division"),
	 @NamedQuery(name="FeederMasterEntity.getSubDivisionDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	FeederMasterEntity FM WHERE FM.subdivision=:subdivision"),
	 @NamedQuery(name="FeederMasterEntity.getSubStationDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	FeederMasterEntity FM WHERE FM.subdivision=:subStation"),
	 
	 @NamedQuery(name="FeederMasterEntity.getZones",query="SELECT  FM.zone,count(DISTINCT FM.fdrcode) as ct FROM 	FeederMasterEntity FM GROUP BY FM.zone"),
	 @NamedQuery(name="FeederMasterEntity.getCircles",query="SELECT  FM.circle as name,count(DISTINCT FM.fdrcode) as count FROM 	FeederMasterEntity FM WHERE FM.zone=:zone GROUP BY FM.circle"),
	 @NamedQuery(name="FeederMasterEntity.getDivision",query="SELECT  FM.division as name,count(DISTINCT FM.fdrcode) as count FROM 	FeederMasterEntity FM WHERE FM.circle=:circle and FM.zone=:circle GROUP BY FM.division"),
	 @NamedQuery(name="FeederMasterEntity.getSubDivision",query="SELECT  FM.subdivision as name,count(DISTINCT FM.fdrcode) as count FROM 	FeederMasterEntity FM WHERE FM.division=:division GROUP BY FM.subdivision"),
	 @NamedQuery(name="FeederMasterEntity.getSubStation",query="SELECT  FM.substation as name,count(DISTINCT FM.fdrcode) as count FROM 	FeederMasterEntity FM WHERE FM.subdivision=:subdivision GROUP BY FM.substation"),

	 
	 @NamedQuery(name="FeederMasterEntity.getModemCount",query="SELECT  COUNT (DISTINCT FM.modem_sl_no) AS modemCount FROM 	FeederMasterEntity FM"),
	 @NamedQuery(name="FeederMasterEntity.getTtlMtrCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrnoCount FROM 	FeederMasterEntity FM"),
	 
	 @NamedQuery(name="FeederMasterEntity.getZonesModemCount",query="SELECT  FM.zone,count(DISTINCT FM.modem_sl_no) FROM 	FeederMasterEntity FM GROUP BY FM.zone"),
	

	 /*	 @NamedQuery(name="FeederMasterEntity.findAll",query="SELECT B.* FROM (select DISTINCT d.fdrcode as frd,count(*) from FeederMasterEntity d where d.district = :district and d.fdrcode is not null and d.fdrcode <> '0' GROUP BY d.fdrcode"
		 		+ "HAVING count(*) = 1 ORDER BY count(*) desc)A LEFT JOIN ( SELECT fdrcode as fdrc,d.* FROM FeederMasterEntity d where d.district = :district and d.fdrcode is not null and d.fdrcode <> '0')B on A.frd=B.fdrc")*/

	 })
public class FeederMasterEntity {
	
	/*@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;*/

	@Column(name = "ZONE")
	private String zone;
	
	@Column(name = "CIRCLE")
	private String circle;
	
	@Column(name = "DIVISION")
	private String division;
	
	@Column(name = "SUBDIVISION")
	private String subdivision;
	
	@Column(name = "SUBSTATION")
	private String substation;

	@Column(name = "ADDRSUB")
	private String addrsub;

	@Column(name = "VILLAGE")
	private String village;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "MLA")
	private String mla;

	@Column(name = "CENAME")
	private String cename;

	@Column(name = "SENAME")
	private String sename;
	
	@Column(name = "XEN_NAME")
	private String xen_name;
	
	@Column(name = "JEN_NAME")
	private String jen_name;
	
	@Column(name = "MODEMS")
	private String modems;

	@Id
	@Column(name = "FDRNAME")
	private String fdrname;

	@Column(name = "FDRCODE")
	private String fdrcode;

	@Column(name = "FDRTYPE")
	private String fdrtype;
	
	@Column(name = "FINAME")
	private String finame;
	
	@Column(name = "FDRCATEGORY")
	private String fdrcategory;
	
	@Column(name = "MTRNO")
	private String mtrno;
	
	@Column(name = "MTRMAKE")
	private String mtrmake;
	
	@Column(name = "YEAR_OF_MAN")
	private String year_of_man;
	
	@Column(name = "MTR_FIRMWARE")
	private String mtr_firmware;
	
	@Column(name = "MODEM_SL_NO")
	private String modem_sl_no;
	
	@Column(name = "SIMNO")
	private String simno;
	
	@Column(name = "CT_RATIO")
	private String ct_ratio;
	
	@Column(name = "PT_RATIO")
	private String pt_ratio;
	
	@Column(name = "MF")
	private String mf;
	
	
	@Column(name = "COMM")
	private String comm;
	
	@Column(name = "FDRLAT")
	private String fdrlat;
	
	@Column(name = "FDRLONG")
	private String fdrlong;
	
	public String getYear_of_man() {
		return year_of_man;
	}

	public void setYear_of_man(String year_of_man) {
		this.year_of_man = year_of_man;
	}

	public String getMtr_firmware() {
		return mtr_firmware;
	}

	public void setMtr_firmware(String mtr_firmware) {
		this.mtr_firmware = mtr_firmware;
	}

	public String getModem_sl_no() {
		return modem_sl_no;
	}

	public void setModem_sl_no(String modem_sl_no) {
		this.modem_sl_no = modem_sl_no;
	}

	public String getSimno() {
		return simno;
	}

	public void setSimno(String simno) {
		this.simno = simno;
	}

	public String getSubstation() {
		return substation;
	}

	public void setSubstation(String substation) {
		this.substation = substation;
	}

	public String getAddrsub() {
		return addrsub;
	}

	public void setAddrsub(String addrsub) {
		this.addrsub = addrsub;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getModems() {
		return modems;
	}

	public void setModems(String modems) {
		this.modems = modems;
	}

	public String getFdrname() {
		return fdrname;
	}

	public void setFdrname(String fdrname) {
		this.fdrname = fdrname;
	}

	public String getFdrcode() {
		return fdrcode;
	}

	public void setFdrcode(String fdrcode) {
		this.fdrcode = fdrcode;
	}

	public String getFdrtype() {
		return fdrtype;
	}

	public void setFdrtype(String fdrtype) {
		this.fdrtype = fdrtype;
	}

	public String getFiname() {
		return finame;
	}

	public void setFiname(String finame) {
		this.finame = finame;
	}

	public String getMtrno() {
		return mtrno;
	}

	public void setMtrno(String mtrno) {
		this.mtrno = mtrno;
	}

	public String getMtrmake() {
		return mtrmake;
	}

	public void setMtrmake(String mtrmake) {
		this.mtrmake = mtrmake;
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

	public String getMf() {
		return mf;
	}

	public void setMf(String mf) {
		this.mf = mf;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public String getMla() {
		return mla;
	}

	public void setMla(String mla) {
		this.mla = mla;
	}

	public String getCename() {
		return cename;
	}

	public void setCename(String cename) {
		this.cename = cename;
	}

	public String getSename() {
		return sename;
	}

	public void setSename(String sename) {
		this.sename = sename;
	}

	public String getXen_name() {
		return xen_name;
	}

	public void setXen_name(String xen_name) {
		this.xen_name = xen_name;
	}

	public String getJen_name() {
		return jen_name;
	}

	public void setJen_name(String jen_name) {
		this.jen_name = jen_name;
	}

	public String getFdrcategory() {
		return fdrcategory;
	}

	public void setFdrcategory(String fdrcategory) {
		this.fdrcategory = fdrcategory;
	}

	public String getFdrlat() {
		return fdrlat;
	}

	public void setFdrlat(String fdrlat) {
		this.fdrlat = fdrlat;
	}

	public String getFdrlong() {
		return fdrlong;
	}

	public void setFdrlong(String fdrlong) {
		this.fdrlong = fdrlong;
	}

	public FeederMasterEntity() {

	}
	
	
	
	
	
	
	
}
