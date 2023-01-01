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

@Table(name="initial_meter_info", schema="meter_data")
@NamedQueries({
	@NamedQuery(name = "InitialMeterInfo.getInitialMeterInfo", query = "select a from InitialMeterInfo a where a.sync_status=0 and a.data_type='NamePlate'"),
	@NamedQuery(name = "InitialMeterInfo.getDTAppDetails", query = "select a from InitialMeterInfo a where a.sync_status=1 and a.data_type='MasterInfo' and a.type='DT' "),
//	@NamedQuery(name = "InitialMeterInfo.getDTAppDetailsTest", query = "select a from InitialMeterInfo a where a.circle=:circle and a.towncode:towncode and a.sync_status=1 and a.data_type='MasterInfo' and a.type='DT' "),
	@NamedQuery(name = "InitialMeterInfo.getDTNotAppDetailByMeter", query = "select a from InitialMeterInfo a where a.id=:id and a.meterid=:meterid "),
	@NamedQuery(name = "InitialMeterInfo.duplicateDTMeters", query = "update InitialMeterInfo a  set a.sync_status=2 , a.updatedby=:updatedby , a.updateddate=:updateddate where a.sync_status=0 and a.data_type='MasterInfo' and a.type='DT' and a.meterid=:meterid "),
	
	
	@NamedQuery(name = "InitialMeterInfo.initialMeterFeederDataSync", query = "select a from InitialMeterInfo a where a.sync_status=0 and a.data_type='MasterInfo' and lower(a.type)=lower('Feeder') order by time_stamp desc"),
	@NamedQuery(name = "InitialMeterInfo.initialMeterBoundaryDataSync", query = "select a from InitialMeterInfo a where a.sync_status=0 and a.data_type='MasterInfo' and lower(a.type)=lower('Boundary') order by time_stamp desc"),
	
})
public class InitialMeterInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name="clientid")
	private String clientid;
	@Column(name="region")
	private String region;
	@Column(name="regioncode")
	private String regioncode;
	@Column(name="circle")
	private String circle;
	@Column(name="circlecode")
	private String circlecode;
	@Column(name="subdivision")
	private String subdivision;
	@Column(name="subdivisioncode")
	private String subdivisioncode;
	@Column(name="section")
	private String section;
	@Column(name="sectioncode")
	private String sectioncode;
	@Column(name="substation")
	private String substation;
	@Column(name="substationcode")
	private String substationcode;
	@Column(name="meterid")
	private String meterid;
	@Column(name="metertype")
	private String metertype;
	@Column(name="meteripaddress")
	private String meteripaddress;
	@Column(name="externalctratio")
	private String externalctratio;
	@Column(name="firmwareversion")
	private String firmwareversion;
	@Column(name="simid")
	private String simid;
	@Column(name="ctwc")
	private String ctwc;
	@Column(name="wire")
	private String wire;
	@Column(name="meterclass")
	private String meterclass;
	@Column(name="currentrating")
	private String currentrating;
	@Column(name="voltagerating")
	private String voltagerating;
	@Column(name="communicationmedium")
	private String communicationmedium;
	@Column(name="operatingmode")
	private String operatingmode;
	@Column(name="manufacturername")
	private String manufacturername;
	@Column(name="meterprotocol")
	private String meterprotocol;
	@Column(name="metercommunicationmethodology")
	private String metercommunicationmethodology;
	@Column(name="isnetmeteringactive")
	private String isnetmeteringactive;
	@Column(name="communicationmodulefirmwareversion")
	private String communicationmodulefirmwareversion;
	@Column(name="meterconstant")
	private String meterconstant;
	@Column(name="communicationmode")
	private String communicationmode;
	@Column(name="townname")
	private String townname;
	@Column(name="towncode")
	private String towncode;
	@Column(name="manufacturerid")
	private String manufacturerid;
	@Column(name="manufactureyear")
	private String manufactureyear;
	@Column(name="mf")
	private String mf;
	@Column(name="lattitude")
	private String lattitude;
	@Column(name="longitude")
	private String longitude;
	@Column(name="metermodel")
	private String metermodel;
	@Column(name="capacity")
	private String capacity;
	@Column(name="ptratio")
	private String ptratio;
	@Column(name="metercommissioning")
	private String metercommissioning;
	@Column(name="ipperiod")
	private String ipperiod;
	@Column(name="ct_ratio")
	private String ct_ratio;
	@Column(name="type")
	private String type;
	@Column(name="boundaryname")
	private String boundaryname;
	@Column(name="boundaryid")
	private String boundaryid;
	@Column(name="boundarytype")
	private String boundarytype;
	@Column(name="feedercode")
	private String feedercode;
	@Column(name="feedername")
	private String feedername;
	@Column(name="dtcode")
	private String dtcode;
	@Column(name="dtname")
	private String dtname;
	
	@Column(name="time_stamp")
	private Date time_stamp;
	
	@Column(name="installation_date")
	private Date installationdate;
	
	@Column(name="sync_status")
	private Integer sync_status;
	
	@Column(name="data_type")
	private String data_type;
	
	@Column(name="division")
	private String division;
	
	@Column(name="division_code")
	private String division_code;
	
	@Column(name="meterdigit")
	private Integer meterdigit;
	
	@Column(name="updatedby")
	private String updatedby;
	
	@Column(name="updateddate")
	private Timestamp updateddate;
	
	@Column(name="feeder_type")
	private String feeder_type;
	
	@Column(name="sim_ipaddress")
	private String sim_ipaddress;
	
	@Column(name="sim_mobile_no")
	private String sim_mobile_no;
	
	@Column(name="imei_no")
	private String imei_no;
	
	@Column(name="meter_purchase_details")
	private String meter_purchase_details;
	
	@Column(name="dt_kva_rating")
	private String dt_kva_rating;
	
	@Column(name="dt_hv_voltage")
	private String dt_hv_voltage;
	
	@Column(name="dt_lv_voltage")
	private String dt_lv_voltage;
	
	@Column(name="dt_hv_amperes")
	private String dt_hv_amperes;
	
	@Column(name="dt_lv_amperes")
	private String dt_lv_amperes;
	
	@Column(name="dt_freq")
	private String dt_freq;
	
	@Column(name="dt_serial_no")
	private String dt_serial_no;
	
	@Column(name="dt_make")
	private String dt_make;
	
	@Column(name="dt_manufactured_monthyear")
	private String dt_manufactured_monthyear;
	
	@Column(name="dt_purchase_details")
	private String dt_purchase_details;
	
	@Column(name="dt_specification_std")
	private String dt_specification_std;
	
	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Timestamp getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}

	public Integer getMeterdigit() {
		return meterdigit;
	}

	public void setMeterdigit(Integer meterdigit) {
		this.meterdigit = meterdigit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getCirclecode() {
		return circlecode;
	}

	public void setCirclecode(String circlecode) {
		this.circlecode = circlecode;
	}

	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public String getSubdivisioncode() {
		return subdivisioncode;
	}

	public void setSubdivisioncode(String subdivisioncode) {
		this.subdivisioncode = subdivisioncode;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSectioncode() {
		return sectioncode;
	}

	public void setSectioncode(String sectioncode) {
		this.sectioncode = sectioncode;
	}

	public String getSubstation() {
		return substation;
	}

	public void setSubstation(String substation) {
		this.substation = substation;
	}

	public String getSubstationcode() {
		return substationcode;
	}

	public void setSubstationcode(String substationcode) {
		this.substationcode = substationcode;
	}

	public String getMeterid() {
		return meterid;
	}

	public void setMeterid(String meterid) {
		this.meterid = meterid;
	}

	public String getMetertype() {
		return metertype;
	}

	public void setMetertype(String metertype) {
		this.metertype = metertype;
	}

	public String getMeteripaddress() {
		return meteripaddress;
	}

	public void setMeteripaddress(String meteripaddress) {
		this.meteripaddress = meteripaddress;
	}

	public String getExternalctratio() {
		return externalctratio;
	}

	public void setExternalctratio(String externalctratio) {
		this.externalctratio = externalctratio;
	}

	public String getFirmwareversion() {
		return firmwareversion;
	}

	public void setFirmwareversion(String firmwareversion) {
		this.firmwareversion = firmwareversion;
	}

	public String getSimid() {
		return simid;
	}

	public void setSimid(String simid) {
		this.simid = simid;
	}

	public String getCtwc() {
		return ctwc;
	}

	public void setCtwc(String ctwc) {
		this.ctwc = ctwc;
	}

	public String getWire() {
		return wire;
	}

	public void setWire(String wire) {
		this.wire = wire;
	}

	public String getMeterclass() {
		return meterclass;
	}

	public void setMeterclass(String meterclass) {
		this.meterclass = meterclass;
	}

	public String getCurrentrating() {
		return currentrating;
	}

	public void setCurrentrating(String currentrating) {
		this.currentrating = currentrating;
	}

	public String getVoltagerating() {
		return voltagerating;
	}

	public void setVoltagerating(String voltagerating) {
		this.voltagerating = voltagerating;
	}

	public String getCommunicationmedium() {
		return communicationmedium;
	}

	public void setCommunicationmedium(String communicationmedium) {
		this.communicationmedium = communicationmedium;
	}

	public String getOperatingmode() {
		return operatingmode;
	}

	public void setOperatingmode(String operatingmode) {
		this.operatingmode = operatingmode;
	}

	public String getManufacturername() {
		return manufacturername;
	}

	public void setManufacturername(String manufacturername) {
		this.manufacturername = manufacturername;
	}

	public String getMeterprotocol() {
		return meterprotocol;
	}

	public void setMeterprotocol(String meterprotocol) {
		this.meterprotocol = meterprotocol;
	}

	public String getMetercommunicationmethodology() {
		return metercommunicationmethodology;
	}

	public void setMetercommunicationmethodology(String metercommunicationmethodology) {
		this.metercommunicationmethodology = metercommunicationmethodology;
	}

	public String getIsnetmeteringactive() {
		return isnetmeteringactive;
	}

	public void setIsnetmeteringactive(String isnetmeteringactive) {
		this.isnetmeteringactive = isnetmeteringactive;
	}

	public String getCommunicationmodulefirmwareversion() {
		return communicationmodulefirmwareversion;
	}

	public void setCommunicationmodulefirmwareversion(String communicationmodulefirmwareversion) {
		this.communicationmodulefirmwareversion = communicationmodulefirmwareversion;
	}

	public String getMeterconstant() {
		return meterconstant;
	}

	public void setMeterconstant(String meterconstant) {
		this.meterconstant = meterconstant;
	}

	public String getCommunicationmode() {
		return communicationmode;
	}

	public void setCommunicationmode(String communicationmode) {
		this.communicationmode = communicationmode;
	}

	public String getTownname() {
		return townname;
	}

	public void setTownname(String townname) {
		this.townname = townname;
	}

	public String getTowncode() {
		return towncode;
	}

	public void setTowncode(String towncode) {
		this.towncode = towncode;
	}

	public String getManufacturerid() {
		return manufacturerid;
	}

	public void setManufacturerid(String manufacturerid) {
		this.manufacturerid = manufacturerid;
	}

	public String getManufactureyear() {
		return manufactureyear;
	}

	public void setManufactureyear(String manufactureyear) {
		this.manufactureyear = manufactureyear;
	}

	public String getMf() {
		return mf;
	}

	public void setMf(String mf) {
		this.mf = mf;
	}

	public String getLattitude() {
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMetermodel() {
		return metermodel;
	}

	public void setMetermodel(String metermodel) {
		this.metermodel = metermodel;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getPtratio() {
		return ptratio;
	}

	public void setPtratio(String ptratio) {
		this.ptratio = ptratio;
	}

	public String getMetercommissioning() {
		return metercommissioning;
	}

	public void setMetercommissioning(String metercommissioning) {
		this.metercommissioning = metercommissioning;
	}

	public String getIpperiod() {
		return ipperiod;
	}

	public void setIpperiod(String ipperiod) {
		this.ipperiod = ipperiod;
	}

	public String getCt_ratio() {
		return ct_ratio;
	}

	public void setCt_ratio(String ct_ratio) {
		this.ct_ratio = ct_ratio;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBoundaryname() {
		return boundaryname;
	}

	public void setBoundaryname(String boundaryname) {
		this.boundaryname = boundaryname;
	}

	public String getBoundaryid() {
		return boundaryid;
	}

	public void setBoundaryid(String boundaryid) {
		this.boundaryid = boundaryid;
	}

	public String getBoundarytype() {
		return boundarytype;
	}

	public void setBoundarytype(String boundarytype) {
		this.boundarytype = boundarytype;
	}

	public String getFeedercode() {
		return feedercode;
	}

	public void setFeedercode(String feedercode) {
		this.feedercode = feedercode;
	}

	public String getFeedername() {
		return feedername;
	}

	public void setFeedername(String feedername) {
		this.feedername = feedername;
	}

	public String getDtcode() {
		return dtcode;
	}

	public void setDtcode(String dtcode) {
		this.dtcode = dtcode;
	}

	public String getDtname() {
		return dtname;
	}

	public void setDtname(String dtname) {
		this.dtname = dtname;
	}

	public Date getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}

	public Integer getSync_status() {
		return sync_status;
	}

	public void setSync_status(Integer sync_status) {
		this.sync_status = sync_status;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public Date getInstallationdate() {
		return installationdate;
	}

	public void setInstallationdate(Date installation_date) {
		this.installationdate = installation_date;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDivision_code() {
		return division_code;
	}

	public void setDivision_code(String division_code) {
		this.division_code = division_code;
	}

	public String getFeeder_type() {
		return feeder_type;
	}

	public void setFeeder_type(String feeder_type) {
		this.feeder_type = feeder_type;
	}

	public String getSim_ipaddress() {
		return sim_ipaddress;
	}

	public void setSim_ipaddress(String sim_ipaddress) {
		this.sim_ipaddress = sim_ipaddress;
	}

	public String getSim_mobile_no() {
		return sim_mobile_no;
	}

	public void setSim_mobile_no(String sim_mobile_no) {
		this.sim_mobile_no = sim_mobile_no;
	}

	public String getImei_no() {
		return imei_no;
	}

	public void setImei_no(String imei_no) {
		this.imei_no = imei_no;
	}

	public String getMeter_purchase_details() {
		return meter_purchase_details;
	}

	public void setMeter_purchase_details(String meter_purchase_details) {
		this.meter_purchase_details = meter_purchase_details;
	}

	public String getDt_kva_rating() {
		return dt_kva_rating;
	}

	public void setDt_kva_rating(String dt_kva_rating) {
		this.dt_kva_rating = dt_kva_rating;
	}

	public String getDt_hv_voltage() {
		return dt_hv_voltage;
	}

	public void setDt_hv_voltage(String dt_hv_voltage) {
		this.dt_hv_voltage = dt_hv_voltage;
	}

	public String getDt_lv_voltage() {
		return dt_lv_voltage;
	}

	public void setDt_lv_voltage(String dt_lv_voltage) {
		this.dt_lv_voltage = dt_lv_voltage;
	}

	public String getDt_hv_amperes() {
		return dt_hv_amperes;
	}

	public void setDt_hv_amperes(String dt_hv_amperes) {
		this.dt_hv_amperes = dt_hv_amperes;
	}
	
	public String getDt_lv_amperes() {
		return dt_lv_amperes;
	}

	public void setDt_lv_amperes(String dt_lv_amperes) {
		this.dt_lv_amperes = dt_lv_amperes;
	}

	public String getDt_freq() {
		return dt_freq;
	}

	public void setDt_freq(String dt_freq) {
		this.dt_freq = dt_freq;
	}

	public String getDt_serial_no() {
		return dt_serial_no;
	}

	public void setDt_serial_no(String dt_serial_no) {
		this.dt_serial_no = dt_serial_no;
	}

	public String getDt_make() {
		return dt_make;
	}

	public void setDt_make(String dt_make) {
		this.dt_make = dt_make;
	}

	public String getDt_manufactured_monthyear() {
		return dt_manufactured_monthyear;
	}

	public void setDt_manufactured_monthyear(String dt_manufactured_monthyear) {
		this.dt_manufactured_monthyear = dt_manufactured_monthyear;
	}

	public String getDt_purchase_details() {
		return dt_purchase_details;
	}

	public void setDt_purchase_details(String dt_purchase_details) {
		this.dt_purchase_details = dt_purchase_details;
	}

	public String getDt_specification_std() {
		return dt_specification_std;
	}

	public void setDt_specification_std(String dt_specification_std) {
		this.dt_specification_std = dt_specification_std;
	}

	
	
	
}
