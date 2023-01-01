package com.bcits.mdas.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_MAIN", schema = "METER_DATA")
@NamedQueries({
	@NamedQuery(name="MasterMainEntity.getFeederDataAccno",query="SELECT fm FROM MasterMainEntity fm where fm.accno = :accno"),
	 @NamedQuery(name="MasterMainEntity.getAllFeederDataMobile",query="SELECT abi FROM MasterMainEntity abi where imeino=:imeiNo"),
	 @NamedQuery(name="MasterMainEntity.getFeederData",query="SELECT fm FROM MasterMainEntity fm where fm.mtrno = :mtrNo"),
	 
	 @NamedQuery(name="MasterMainEntity.getFeederDataInfo",query="SELECT fm.mtrno,fm.zone,fm.circle FROM MasterMainEntity fm where fm.mtrno = :mtrNo"),
	 @NamedQuery(name="MasterMainEntity.getMeterDetailsForXml",query="SELECT fm.mtrno, fm.fdrcode, fm.fdrname, fm.zone, fm.circle, fm.division FROM MasterMainEntity fm where  fm.modem_sl_no is not null and fm.modem_sl_no !='' "),
	 //ABove check weather IMEI number is already exits in xml_upload table
	 @NamedQuery(name="MasterMainEntity.getCorporateDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,	COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	MasterMainEntity FM"),
	 @NamedQuery(name="MasterMainEntity.getZoneDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	MasterMainEntity FM WHERE FM.zone=:zone"),
	 @NamedQuery(name="MasterMainEntity.getCircleDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	MasterMainEntity FM WHERE FM.circle=:circle and FM.zone=:circle"),
	 @NamedQuery(name="MasterMainEntity.getDivisionDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	MasterMainEntity FM WHERE FM.division=:division"),
	 @NamedQuery(name="MasterMainEntity.getSubDivisionDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	MasterMainEntity FM WHERE FM.subdivision=:subdivision"),
	 @NamedQuery(name="MasterMainEntity.getSubStationDetailCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrCOUNT,COUNT (DISTINCT FM.zone) AS ZONECOUNT,	COUNT (DISTINCT FM.circle) AS CIRCLECOUNT,	COUNT (DISTINCT FM.division) AS DIVISIONCOUNT,	COUNT (DISTINCT FM.subdivision) AS SUBDIVISIONCOUNT,COUNT (DISTINCT FM.substation) AS SUBSTATIONCOUNT, COUNT(DISTINCT FM.fdrcode) FROM 	MasterMainEntity FM WHERE FM.subdivision=:subStation"),
	 
	 @NamedQuery(name="MasterMainEntity.getZones",query="SELECT  FM.zone,count(DISTINCT FM.fdrcode) as ct FROM 	MasterMainEntity FM GROUP BY FM.zone"),
	 @NamedQuery(name="MasterMainEntity.getCircles",query="SELECT  FM.circle as name,count(DISTINCT FM.fdrcode) as count FROM 	MasterMainEntity FM WHERE FM.zone=:zone GROUP BY FM.circle"),
	 @NamedQuery(name="MasterMainEntity.getDivision",query="SELECT  FM.division as name,count(DISTINCT FM.fdrcode) as count FROM 	MasterMainEntity FM WHERE FM.circle=:circle   GROUP BY FM.division"),
	 @NamedQuery(name="MasterMainEntity.getSubDivision",query="SELECT  FM.subdivision as name,count(DISTINCT FM.fdrcode) as count FROM 	MasterMainEntity FM WHERE FM.division=:division GROUP BY FM.subdivision"),
	 @NamedQuery(name="MasterMainEntity.getSubStation",query="SELECT  FM.substation as name,count(DISTINCT FM.fdrcode) as count FROM 	MasterMainEntity FM WHERE FM.subdivision=:subdivision GROUP BY FM.substation"),

	 
	 @NamedQuery(name="MasterMainEntity.getModemCount",query="SELECT  COUNT (DISTINCT FM.modem_sl_no) AS modemCount FROM 	MasterMainEntity FM"),
	 @NamedQuery(name="MasterMainEntity.getTtlMtrCount",query="SELECT  COUNT (DISTINCT FM.mtrno) AS mtrnoCount FROM 	MasterMainEntity FM"),
	 
	 @NamedQuery(name="MasterMainEntity.getZonesModemCount",query="SELECT  FM.zone,count(DISTINCT FM.modem_sl_no) FROM 	MasterMainEntity FM GROUP BY FM.zone"),
	 
	 @NamedQuery(name="MasterMainEntity.getFeedersBasedOn",query="SELECT  f FROM MasterMainEntity f where f.zone=:zone and f.circle=:circle and f.division=:division and f.subdivision=:subdivision"),
	 @NamedQuery(name="MasterMainEntity.getFeedersBasedOnAllZone",query="SELECT  f FROM MasterMainEntity f"),
	 @NamedQuery(name="MasterMainEntity.getFeedersBasedOnAllCircle",query="SELECT  f FROM MasterMainEntity f where f.zone=:zone"),
	 @NamedQuery(name="MasterMainEntity.getFeedersBasedOnAllDivison",query="SELECT  f FROM MasterMainEntity f where f.zone=:zone and f.circle=:circle"),
	 @NamedQuery(name="MasterMainEntity.getFeedersBasedOnAllSubdivsion",query="SELECT  f FROM MasterMainEntity f where f.zone=:zone and f.circle=:circle and f.division=:division"),
	 
	 @NamedQuery(name="MasterMainEntity.getEntityByMtrNO",query="SELECT f FROM MasterMainEntity f where  f.mtrno=:mtrno"),
	 @NamedQuery(name="MasterMainEntity.getEntityByImeiNoAndMtrNO",query="SELECT  f FROM MasterMainEntity f where f.modem_sl_no=:modem_sl_no and f.mtrno=:mtrno"),
	 @NamedQuery(name="MasterMainEntity.getFeedersBasedOnImei",query="SELECT  f FROM MasterMainEntity f where f.modem_sl_no like :modem_sl_no"),
	 @NamedQuery(name="MasterMainEntity.getFeedersBasedOnMeterNo",query="SELECT  f FROM MasterMainEntity f where f.mtrno like :mtrno"),
	 @NamedQuery(name="MasterMainEntity.getEntityByMtrNOandTcode",query="SELECT f FROM MasterMainEntity f where f.mtrno=:mtrno and f.town_code=:town_code"),
	 
	 //Yogesh Qrys
	 
	 @NamedQuery(name="MasterMainEntity.findDistinctZones",query="select DISTINCT abi.zone from MasterMainEntity abi where abi.zone <> '' and abi.zone is not null "),
	 @NamedQuery(name="MasterMainEntity.findDistinctCircle",query="select DISTINCT abi.circle from MasterMainEntity abi where abi.circle <> '' and abi.circle is not null and abi.zone = :zone "),
	 @NamedQuery(name="MasterMainEntity.findDistinctDivision",query="select DISTINCT abi.division from MasterMainEntity abi where abi.division <> '' and abi.division is not null and abi.zone = :zone and abi.circle = :circle "),
	 @NamedQuery(name="MasterMainEntity.findDistinctSubdivision",query="select DISTINCT abi.subdivision from MasterMainEntity abi where abi.subdivision <> '' and abi.subdivision is not null and abi.zone = :zone and abi.circle = :circle and abi.division = :division "),
	 @NamedQuery(name="MasterMainEntity.findDistinctsubstation",query="select DISTINCT abi.substation from MasterMainEntity abi where abi.substation <> '' and abi.substation is not null and abi.zone = :zone and abi.circle = :circle and abi.division = :division and abi.subdivision = :subdivision "),
	 @NamedQuery(name="MasterMainEntity.findall",query="select abi from MasterMainEntity abi where abi.zone = :zone and abi.circle = :circle and abi.division = :division and abi.subdivision = :subdivision and feedsurvey = '0' and modem_sl_no is null "),
	 @NamedQuery(name="MasterMainEntity.getEntityByAccnoSubstation",query="select abi from MasterMainEntity abi where abi.accno = :accno and abi.subdivision = :subdivision"),
	 @NamedQuery(name="MasterMainEntity.getFeedersBasedOnAccNo",query="SELECT  f FROM MasterMainEntity f where f.accno like :accno"),
	 
	 //TO GET ALL METER DATA
	
	@NamedQuery(name="MasterMainEntity.getMeterData",query="SELECT a FROM MasterMainEntity a where mtrno=:mtrno OR accno=:mtrno"),
	@NamedQuery(name="MasterMainEntity.getMeterDataByKno",query="SELECT a FROM MasterMainEntity a where kno=:kno"),
	@NamedQuery(name="MasterMainEntity.getConsumptionRecords",query="select a from MasterMainEntity a where a.zone = :zone"),
	@NamedQuery(name="MasterMainEntity.getDataByMeterType",query="SELECT a FROM MasterMainEntity a where meter_type=:meter_type"),
	@NamedQuery(name="MasterMainEntity.getFeederDetailsByID",query="SELECT a FROM MasterMainEntity a where a.mla=:mla"),
	@NamedQuery(name="MasterMainEntity.getFdrCategory",query="select DISTINCT a.fdrcategory from MasterMainEntity a"),
	@NamedQuery(name="MasterMainEntity.getAllMetersBySubDivision",query="select DISTINCT a.mtrno from MasterMainEntity a where a.sdocode=:sitecode and a.fdrcategory=:fdecategory"),
	
	@NamedQuery(name="MasterMainEntity.getAllMtrNos",query="select DISTINCT m.mtrno from MasterMainEntity m"),
	@NamedQuery(name="MasterMainEntity.getAllKNos",query="select DISTINCT m.kno from MasterMainEntity m"),
	@NamedQuery(name="MasterMainEntity.getConsumerLocationDetails",query="select m.subdivision,m.fdrcategory,m.customer_name,m.accno,m.kno,m.mtrno from MasterMainEntity m where m.mtrno=:mtrno or accno=:accno"),
	
	
})
public class MasterMainEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

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
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "latitude")
	private String latitude;
	
	
	@Column(name = "MTR_CHANGE_DATE")
	private String mtr_change_date;
	
	@Column(name = "OLD_MTR_NO")
	private String old_mtr_no;
	
	@Column(name = "feeder_type")
	private String feeder_type;
	
	@Column(name = "feeder_status")
	private String feeder_status;
	
	@Column(name = "non_availabilityOf_data")
	private String non_availabilityOf_data;
	
	@Column(name = "last_communicated_date")
	private Date last_communicated_date;
	


	@Column(name = "model_no")
	private String model_no;
	

	@Column(name = "tender_no")
	private String tender_no;
	
	@Column(name = "dlms")
	private String dlms;
	
	@Column(name = "voltage_kv")
	private String voltage_kv;

	
	@Column(name = "installation_date")
	private Date installation_date;
	
	@Column(name = "customer_name")
	private String customer_name;
	
	
	@Column(name = "customer_mobile")
	private String customer_mobile;
	
	
	@Column(name = "customer_address")
	private String customer_address;
	
	
	@Column(name = "accno")
	private String accno;
	
	
	@Column(name = "consumerstatus")
	private String consumerstatus;
	
	@Column(name = "tariffcode")
	private String tariffcode;
	
	@Column(name = "kworhp")
	private String kworhp;
	
	@Column(name = "sanload")
	private String sanload;
	
	@Column(name = "contractdemand")
	private String contractdemand;
	
	@Column(name = "mrname")
	private String mrname;
	
	@Column(name = "kno")
	private String kno;
	
	@Column(name = "discom")
	private String discom;
	
	@Column(name = "town_code")
	private String town_code;
	
	@Column(name = "hes_type")
	private String hes_type;
	
	@Column(name = "create_time")
	private Timestamp create_time;
	
	@Column(name = "location_id")
	private String location_id;
	
	@Column(name = "remarks_date")
	private Timestamp remarks_date;

	public Timestamp getRemarks_date() {
		return remarks_date;
	}

	public void setRemarks_date(Timestamp remarks_date) {
		this.remarks_date = remarks_date;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public String getHes_type() {
		return hes_type;
	}

	public void setHes_type(String hes_type) {
		this.hes_type = hes_type;
	}

	public String getDiscom() {
		return discom;
	}

	public void setDiscom(String discom) {
		this.discom = discom;
	}
	

	@Column(name = "meter_type")
	private String meter_type;
	


	@Column(name="sdocode")
	private String sdocode;
	

	public String getInstallation() {
		return installation;
	}

	public void setInstallation(String installation) {
		this.installation = installation;
	}

	@Column(name = "installation")
	private String installation;
	@Column(name="phase")
	private String phase;
	
	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_mobile() {
		return customer_mobile;
	}

	public void setCustomer_mobile(String customer_mobile) {
		this.customer_mobile = customer_mobile;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getConsumerstatus() {
		return consumerstatus;
	}

	public void setConsumerstatus(String consumerstatus) {
		this.consumerstatus = consumerstatus;
	}

	public String getTariffcode() {
		return tariffcode;
	}

	public void setTariffcode(String tariffcode) {
		this.tariffcode = tariffcode;
	}

	public String getKworhp() {
		return kworhp;
	}

	public void setKworhp(String kworhp) {
		this.kworhp = kworhp;
	}

	public String getSanload() {
		return sanload;
	}

	public void setSanload(String sanload) {
		this.sanload = sanload;
	}

	public String getContractdemand() {
		return contractdemand;
	}

	public void setContractdemand(String contractdemand) {
		this.contractdemand = contractdemand;
	}

	public String getMrname() {
		return mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}

	
	
	public String getVoltage_kv() {
		return voltage_kv;
	}

	public void setVoltage_kv(String voltage_kv) {
		this.voltage_kv = voltage_kv;
	}

	public Date getInstallation_date() {
		return installation_date;
	}

	public void setInstallation_date(Date installation_date) {
		this.installation_date = installation_date;
	}

	public Date getLast_communicated_date() {
		return last_communicated_date;
	}

	public void setLast_communicated_date(Date last_communicated_date) {
		this.last_communicated_date = last_communicated_date;
	}
	
	public String getDlms() {
		return dlms;
	}

	public void setDlms(String dlms) {
		this.dlms = dlms;
	}

	public String getModel_no() {
		return model_no;
	}

	public void setModel_no(String model_no) {
		this.model_no = model_no;
	}

	public String getTender_no() {
		return tender_no;
	}

	public void setTender_no(String tender_no) {
		this.tender_no = tender_no;
	}

	public String getNon_availabilityOf_data() {
		return non_availabilityOf_data;
	}

	public void setNon_availabilityOf_data(String non_availabilityOf_data) {
		this.non_availabilityOf_data = non_availabilityOf_data;
	}

	public String getFeeder_status() {
		return feeder_status;
	}

	public void setFeeder_status(String feeder_status) {
		this.feeder_status = feeder_status;
	}

	public String getFeeder_type() {
		return feeder_type;
	}

	public void setFeeder_type(String feeder_type) {
		this.feeder_type = feeder_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMtr_change_date() {
		return mtr_change_date;
	}

	public void setMtr_change_date(String mtr_change_date) {
		this.mtr_change_date = mtr_change_date;
	}

	public String getOld_mtr_no() {
		return old_mtr_no;
	}

	public void setOld_mtr_no(String old_mtr_no) {
		this.old_mtr_no = old_mtr_no;
	}

	
	
	
	
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

	public MasterMainEntity() {

	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getMeter_type() {
		return meter_type;
	}

	public void setMeter_type(String meter_type) {
		this.meter_type = meter_type;
	}



	public String getSdocode() {
		return sdocode;
	}

	public void setSdocode(String sdocode) {
		this.sdocode = sdocode;
	}

	public String getTown_code() {
		return town_code;
	}

	public void setTown_code(String town_code) {
		this.town_code = town_code;
	}

	public String getLocation_id() {
		return location_id;
	}

	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}

	

	
	
	
	
	
	
	
}
