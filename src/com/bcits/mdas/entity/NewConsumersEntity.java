package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@Entity
@Table(name = "NEW_CONSUMERS", schema = "VCLOUDENGINE")
@NamedQueries({
	 })
public class NewConsumersEntity {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "sitecode")
	private String sitecode;
	
	@Column(name = "mrcode")
	private String mrcode;
	
	@Column(name = "zone")
	private String zone;
	
	@Column(name = "circle")
	private String circle;
	
	@Column(name = "division")
	private String division;
	
	@Column(name = "district")
	private String district;
	
	@Column(name = "subdivision")
	private String subdivision;
	
	@Column(name = "substation")
	private String substation;
	
	@Column(name = "addrsub")
	private String addrsub;
	
	@Column(name = "village")
	private String village;
	
	@Column(name = "fdrname")
	private String fdrname;
	
	@Column(name = "fdrcode")
	private String fdrcode;
	
	@Column(name = "mtrno")
	private String mtrno;
	
	@Column(name = "mtrmake")
	private String mtrmake;
	
	@Column(name = "dlms")
	private String dlms;
	
	@Column(name = "mtr_firmware")
	private String mtr_firmware;
	
	@Column(name = "year_of_man")
	private String year_of_man;
	
	@Column(name = "ct_ratio")
	private String ct_ratio;
	
	@Column(name = "pt_ratio")
	private String pt_ratio;
	
	@Column(name = "mf")
	private String mf;
	
	@Column(name = "comm")
	private String comm;
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "model_no")
	private String model_no;
	
	@Column(name = "voltage_kv")
	private String voltage_kv;
	
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
	
	@Column(name = "image")
	private byte[] image;
	
	@Column(name = "modem_sl_no")
	private String modem_sl_no;
	
	@Column(name = "imei_no")
	private String imei_no;
	
	@Column(name = "sim_imsi")
	private String sim_imsi;
	
	@Column(name = "sim_ccid")
	private String sim_ccid;
	
	@Column(name = "new_meter_no")
	private String new_meter_no;
	
	@Column(name = "new_meter_make")
	private String new_meter_make;
	
	@Column(name = "new_meter_year")
	private String new_meter_year;
	
	@Column(name = "new_dlms")
	private String new_dlms;
	
	@Column(name = "new_ct_ratio")
	private String new_ct_ratio;
	
	@Column(name = "new_pt_ratio")
	private String new_pt_ratio;
	
	@Column(name = "timestamp")
	private String timestamp;
	
	@Column(name = "syncstatus")
	private String syncstatus;
	
	@Column(name = "installstatus")
	private String installstatus;
	
	@Column(name = "deviceid")
	private String deviceid;
	
	@Column(name = "appversion")
	private String appversion;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "sealone")
	private String sealone;
	
	@Column(name = "sealtwo")
	private String sealtwo;
	
	@Column(name = "sealthree")
	private String sealthree;
	
	@Column(name = "sealfour")
	private String sealfour;
	
	@Column(name = "sealfive")
	private String sealfive;
	
	@Column(name = "sealsix")
	private String sealsix;
	
	@Column(name = "image_one")
	private byte[] image_one;
	
	@Column(name = "image_two")
	private byte[] image_two;
	
	@Column(name = "image_three")
	private byte[] image_three;
	
	@Column(name = "image_four")
	private byte[] image_four;
	
	@Column(name = "image_five")
	private byte[] image_five;
	
	@Column(name = "panelspace")
	private String panelspace;
	
	@Column(name = "powersupply")
	private String powersupply;
	
	@Column(name = "enclosed")
	private String enclosed;
	
	@Column(name = "cablelength")
	private String cablelength;
	
	@Column(name = "simonenetwork")
	private String simonenetwork;
	
	@Column(name = "simtwonetwork")
	private String simtwonetwork;
	
	@Column(name = "simonesignal")
	private String simonesignal;
	
	@Column(name = "simtwosignal")
	private String simtwosignal;
	
	@Column(name = "datetimestamp")
	private String datetimestamp;

	
	
	public NewConsumersEntity(String sitecode, String mrcode, String zone,
			String circle, String division, String district,
			String subdivision, String substation, String addrsub,
			String village, String fdrname, String fdrcode, String mtrno,
			String mtrmake, String dlms, String mtr_firmware,
			String year_of_man, String ct_ratio, String pt_ratio, String mf,
			String comm, String longitude, String latitude, String model_no,
			String voltage_kv, String customer_name, String customer_mobile,
			String customer_address, String accno, String consumerstatus,
			String tariffcode, String kworhp, String sanload,
			String contractdemand, String mrname, String kno, String discom,
			byte[] image, String modem_sl_no, String imei_no, String sim_imsi,
			String sim_ccid, String new_meter_no, String new_meter_make,
			String new_meter_year, String new_dlms, String new_ct_ratio,
			String new_pt_ratio, String timestamp, String syncstatus,
			String installstatus, String deviceid, String appversion,
			String date, String sealone, String sealtwo, String sealthree,
			String sealfour, String sealfive, String sealsix, byte[] image_one,
			byte[] image_two, byte[] image_three, byte[] image_four,
			byte[] image_five, String panelspace, String powersupply,
			String enclosed, String cablelength, String simonenetwork,
			String simtwonetwork, String simonesignal, String simtwosignal,
			String datetimestamp) {
		this.sitecode = sitecode;
		this.mrcode = mrcode;
		this.zone = zone;
		this.circle = circle;
		this.division = division;
		this.district = district;
		this.subdivision = subdivision;
		this.substation = substation;
		this.addrsub = addrsub;
		this.village = village;
		this.fdrname = fdrname;
		this.fdrcode = fdrcode;
		this.mtrno = mtrno;
		this.mtrmake = mtrmake;
		this.dlms = dlms;
		this.mtr_firmware = mtr_firmware;
		this.year_of_man = year_of_man;
		this.ct_ratio = ct_ratio;
		this.pt_ratio = pt_ratio;
		this.mf = mf;
		this.comm = comm;
		this.longitude = longitude;
		this.latitude = latitude;
		this.model_no = model_no;
		this.voltage_kv = voltage_kv;
		this.customer_name = customer_name;
		this.customer_mobile = customer_mobile;
		this.customer_address = customer_address;
		this.accno = accno;
		this.consumerstatus = consumerstatus;
		this.tariffcode = tariffcode;
		this.kworhp = kworhp;
		this.sanload = sanload;
		this.contractdemand = contractdemand;
		this.mrname = mrname;
		this.kno = kno;
		this.discom = discom;
		this.image = image;
		this.modem_sl_no = modem_sl_no;
		this.imei_no = imei_no;
		this.sim_imsi = sim_imsi;
		this.sim_ccid = sim_ccid;
		this.new_meter_no = new_meter_no;
		this.new_meter_make = new_meter_make;
		this.new_meter_year = new_meter_year;
		this.new_dlms = new_dlms;
		this.new_ct_ratio = new_ct_ratio;
		this.new_pt_ratio = new_pt_ratio;
		this.timestamp = timestamp;
		this.syncstatus = syncstatus;
		this.installstatus = installstatus;
		this.deviceid = deviceid;
		this.appversion = appversion;
		this.date = date;
		this.sealone = sealone;
		this.sealtwo = sealtwo;
		this.sealthree = sealthree;
		this.sealfour = sealfour;
		this.sealfive = sealfive;
		this.sealsix = sealsix;
		this.image_one = image_one;
		this.image_two = image_two;
		this.image_three = image_three;
		this.image_four = image_four;
		this.image_five = image_five;
		this.panelspace = panelspace;
		this.powersupply = powersupply;
		this.enclosed = enclosed;
		this.cablelength = cablelength;
		this.simonenetwork = simonenetwork;
		this.simtwonetwork = simtwonetwork;
		this.simonesignal = simonesignal;
		this.simtwosignal = simtwosignal;
		this.datetimestamp = datetimestamp;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getSitecode() {
		return sitecode;
	}



	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}



	public String getMrcode() {
		return mrcode;
	}



	public void setMrcode(String mrcode) {
		this.mrcode = mrcode;
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



	public String getDistrict() {
		return district;
	}



	public void setDistrict(String district) {
		this.district = district;
	}



	public String getSubdivision() {
		return subdivision;
	}



	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
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



	public String getDlms() {
		return dlms;
	}



	public void setDlms(String dlms) {
		this.dlms = dlms;
	}



	public String getMtr_firmware() {
		return mtr_firmware;
	}



	public void setMtr_firmware(String mtr_firmware) {
		this.mtr_firmware = mtr_firmware;
	}



	public String getYear_of_man() {
		return year_of_man;
	}



	public void setYear_of_man(String year_of_man) {
		this.year_of_man = year_of_man;
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



	public String getModel_no() {
		return model_no;
	}



	public void setModel_no(String model_no) {
		this.model_no = model_no;
	}



	public String getVoltage_kv() {
		return voltage_kv;
	}



	public void setVoltage_kv(String voltage_kv) {
		this.voltage_kv = voltage_kv;
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



	public String getDiscom() {
		return discom;
	}



	public void setDiscom(String discom) {
		this.discom = discom;
	}



	public byte[] getImage() {
		return image;
	}



	public void setImage(byte[] image) {
		this.image = image;
	}



	public String getModem_sl_no() {
		return modem_sl_no;
	}



	public void setModem_sl_no(String modem_sl_no) {
		this.modem_sl_no = modem_sl_no;
	}



	public String getImei_no() {
		return imei_no;
	}



	public void setImei_no(String imei_no) {
		this.imei_no = imei_no;
	}



	public String getSim_imsi() {
		return sim_imsi;
	}



	public void setSim_imsi(String sim_imsi) {
		this.sim_imsi = sim_imsi;
	}



	public String getSim_ccid() {
		return sim_ccid;
	}



	public void setSim_ccid(String sim_ccid) {
		this.sim_ccid = sim_ccid;
	}



	public String getNew_meter_no() {
		return new_meter_no;
	}



	public void setNew_meter_no(String new_meter_no) {
		this.new_meter_no = new_meter_no;
	}



	public String getNew_meter_make() {
		return new_meter_make;
	}



	public void setNew_meter_make(String new_meter_make) {
		this.new_meter_make = new_meter_make;
	}



	public String getNew_meter_year() {
		return new_meter_year;
	}



	public void setNew_meter_year(String new_meter_year) {
		this.new_meter_year = new_meter_year;
	}



	public String getNew_dlms() {
		return new_dlms;
	}



	public void setNew_dlms(String new_dlms) {
		this.new_dlms = new_dlms;
	}



	public String getNew_ct_ratio() {
		return new_ct_ratio;
	}



	public void setNew_ct_ratio(String new_ct_ratio) {
		this.new_ct_ratio = new_ct_ratio;
	}



	public String getNew_pt_ratio() {
		return new_pt_ratio;
	}



	public void setNew_pt_ratio(String new_pt_ratio) {
		this.new_pt_ratio = new_pt_ratio;
	}



	public String getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}



	public String getSyncstatus() {
		return syncstatus;
	}



	public void setSyncstatus(String syncstatus) {
		this.syncstatus = syncstatus;
	}



	public String getInstallstatus() {
		return installstatus;
	}



	public void setInstallstatus(String installstatus) {
		this.installstatus = installstatus;
	}



	public String getDeviceid() {
		return deviceid;
	}



	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}



	public String getAppversion() {
		return appversion;
	}



	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getSealone() {
		return sealone;
	}



	public void setSealone(String sealone) {
		this.sealone = sealone;
	}



	public String getSealtwo() {
		return sealtwo;
	}



	public void setSealtwo(String sealtwo) {
		this.sealtwo = sealtwo;
	}



	public String getSealthree() {
		return sealthree;
	}



	public void setSealthree(String sealthree) {
		this.sealthree = sealthree;
	}



	public String getSealfour() {
		return sealfour;
	}



	public void setSealfour(String sealfour) {
		this.sealfour = sealfour;
	}



	public String getSealfive() {
		return sealfive;
	}



	public void setSealfive(String sealfive) {
		this.sealfive = sealfive;
	}



	public String getSealsix() {
		return sealsix;
	}



	public void setSealsix(String sealsix) {
		this.sealsix = sealsix;
	}



	public byte[] getImage_one() {
		return image_one;
	}



	public void setImage_one(byte[] image_one) {
		this.image_one = image_one;
	}



	public byte[] getImage_two() {
		return image_two;
	}



	public void setImage_two(byte[] image_two) {
		this.image_two = image_two;
	}



	public byte[] getImage_three() {
		return image_three;
	}



	public void setImage_three(byte[] image_three) {
		this.image_three = image_three;
	}



	public byte[] getImage_four() {
		return image_four;
	}



	public void setImage_four(byte[] image_four) {
		this.image_four = image_four;
	}



	public byte[] getImage_five() {
		return image_five;
	}



	public void setImage_five(byte[] image_five) {
		this.image_five = image_five;
	}



	public String getPanelspace() {
		return panelspace;
	}



	public void setPanelspace(String panelspace) {
		this.panelspace = panelspace;
	}



	public String getPowersupply() {
		return powersupply;
	}



	public void setPowersupply(String powersupply) {
		this.powersupply = powersupply;
	}



	public String getEnclosed() {
		return enclosed;
	}



	public void setEnclosed(String enclosed) {
		this.enclosed = enclosed;
	}



	public String getCablelength() {
		return cablelength;
	}



	public void setCablelength(String cablelength) {
		this.cablelength = cablelength;
	}



	public String getSimonenetwork() {
		return simonenetwork;
	}



	public void setSimonenetwork(String simonenetwork) {
		this.simonenetwork = simonenetwork;
	}



	public String getSimtwonetwork() {
		return simtwonetwork;
	}



	public void setSimtwonetwork(String simtwonetwork) {
		this.simtwonetwork = simtwonetwork;
	}



	public String getSimonesignal() {
		return simonesignal;
	}



	public void setSimonesignal(String simonesignal) {
		this.simonesignal = simonesignal;
	}



	public String getSimtwosignal() {
		return simtwosignal;
	}



	public void setSimtwosignal(String simtwosignal) {
		this.simtwosignal = simtwosignal;
	}



	public String getDatetimestamp() {
		return datetimestamp;
	}



	public void setDatetimestamp(String datetimestamp) {
		this.datetimestamp = datetimestamp;
	}



	public NewConsumersEntity() {

	}
}
