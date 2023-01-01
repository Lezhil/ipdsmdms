package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "MODEM_INSTALLATION", schema = "VCLOUDENGINE")
@NamedQueries({
	 @NamedQuery(name="ModemInstallationEntity.findAll",query="SELECT fm FROM ModemInstallationEntity fm where installed = '0' "),

	 })
public class ModemInstallationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "zone")
	private String zone;
	
	@Column(name = "circle")
	private String circle;
	
	@Column(name = "district")
	private String district;

	@Column(name = "division")
	private String division;

	@Column(name = "subdivision")
	private String subdivision;

	@Column(name = "substation")
	private String substation;

	@Column(name = "accno")
	private String accno;

	@Column(name = "customer_name")
	private String customer_name;

	@Column(name = "meter_no")
	private String meter_no;

	@Column(name = "meter_make")
	private String meter_make;

	@Column(name = "meter_year")
	private String meter_year;

	@Column(name = "dlms")
	private String dlms;

	@Column(name = "ct_ratio")
	private String ct_ratio;

	@Column(name = "pt_ratio")
	private String pt_ratio;

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

	@Column(name = "servertosbmdate")
	private String servertosbmdate;

	@Column(name = "deviceid")
	private String deviceid;

	@Column(name = "version")
	private String version;

	@Column(name = "photo")
	private byte[] photo;

	@Column(name = "timestaken")
	private String timestaken;

	@Column(name = "oldsealone")
	private String oldsealone;

	@Column(name = "oldsealtwo")
	private String oldsealtwo;

	@Column(name = "oldsealthree")
	private String oldsealthree;

	@Column(name = "newsealone")
	private String newsealone;

	@Column(name = "newsealtwo")
	private String newsealtwo;
	
	@Column(name = "newsealthree")
	private String newsealthree;

	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "oldsealfour")
	private String oldsealfour;
	
	@Column(name = "oldsealfive")
	private String oldsealfive;

	@Column(name = "newsealfour")
	private String newsealfour;
	
	@Column(name = "newsealfive")
	private String newsealfive;

	@Column(name = "extra1")
	private String extra1;
	
	@Column(name = "extra2")
	private String extra2;
	


	public ModemInstallationEntity() {

	}






	public ModemInstallationEntity(String state, String zone, String circle,
			String district, String division, String subdivision,
			String substation, String accno, String customer_name,
			String meter_no, String meter_make, String meter_year, String dlms,
			String ct_ratio, String pt_ratio, String modem_sl_no,
			String imei_no, String sim_imsi, String sim_ccid,
			String new_meter_no, String new_meter_make, String new_meter_year,
			String new_dlms, String new_ct_ratio, String new_pt_ratio,
			String servertosbmdate, String deviceid, String version,
			byte[] photo, String timestaken, String oldsealone,
			String oldsealtwo, String oldsealthree, String newsealone,
			String newsealtwo, String newsealthree, String remarks,String oldsealfour,
			String oldsealfive,String newsealfour, String newsealfive, String extra11, String extra22) {
		super();
		this.state = state;
		this.zone = zone;
		this.circle = circle;
		this.district = district;
		this.division = division;
		this.subdivision = subdivision;
		this.substation = substation;
		this.accno = accno;
		this.customer_name = customer_name;
		this.meter_no = meter_no;
		this.meter_make = meter_make;
		this.meter_year = meter_year;
		this.dlms = dlms;
		this.ct_ratio = ct_ratio;
		this.pt_ratio = pt_ratio;
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
		this.servertosbmdate = servertosbmdate;
		this.deviceid = deviceid;
		this.version = version;
		this.photo = photo;
		this.timestaken = timestaken;
		this.oldsealone = oldsealone;
		this.oldsealtwo = oldsealtwo;
		this.oldsealthree = oldsealthree;
		this.newsealone = newsealone;
		this.newsealtwo = newsealtwo;
		this.newsealthree = newsealthree;
		this.remarks = remarks;
		this.oldsealfour=oldsealfour;
		this.oldsealfive=oldsealfive;
		this.newsealfour=newsealfour;
		this.newsealfive=newsealfive;
		this.extra1=extra1;
		this.extra2=extra2;
	}






	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
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



	public String getDistrict() {
		return district;
	}



	public void setDistrict(String district) {
		this.district = district;
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



	public String getSubstation() {
		return substation;
	}



	public void setSubstation(String substation) {
		this.substation = substation;
	}



	public String getAccno() {
		return accno;
	}



	public void setAccno(String accno) {
		this.accno = accno;
	}



	public String getcustomer_name() {
		return customer_name;
	}



	public void setcustomerr_name(String customer_name) {
		this.customer_name = customer_name;
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



	public String getMeter_year() {
		return meter_year;
	}



	public void setMeter_year(String meter_year) {
		this.meter_year = meter_year;
	}



	public String getDlms() {
		return dlms;
	}



	public void setDlms(String dlms) {
		this.dlms = dlms;
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



	public String getServertosbmdate() {
		return servertosbmdate;
	}



	public void setServertosbmdate(String servertosbmdate) {
		this.servertosbmdate = servertosbmdate;
	}



	public String getDeviceid() {
		return deviceid;
	}



	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public byte[] getPhoto() {
		return photo;
	}



	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}



	public String getTimestaken() {
		return timestaken;
	}



	public void setTimestaken(String timestaken) {
		this.timestaken = timestaken;
	}






	public String getOldsealone() {
		return oldsealone;
	}






	public void setOldsealone(String oldsealone) {
		this.oldsealone = oldsealone;
	}






	public String getOldsealtwo() {
		return oldsealtwo;
	}






	public void setOldsealtwo(String oldsealtwo) {
		this.oldsealtwo = oldsealtwo;
	}






	public String getOldsealthree() {
		return oldsealthree;
	}






	public void setOldsealthree(String oldsealthree) {
		this.oldsealthree = oldsealthree;
	}






	public String getNewsealone() {
		return newsealone;
	}






	public void setNewsealone(String newsealone) {
		this.newsealone = newsealone;
	}






	public String getNewsealtwo() {
		return newsealtwo;
	}






	public void setNewsealtwo(String newsealtwo) {
		this.newsealtwo = newsealtwo;
	}






	public String getNewsealthree() {
		return newsealthree;
	}






	public void setNewsealthree(String newsealthree) {
		this.newsealthree = newsealthree;
	}






	public String getRemarks() {
		return remarks;
	}






	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}






	public String getOldsealfour() {
		return oldsealfour;
	}






	public void setOldsealfour(String oldsealfour) {
		this.oldsealfour = oldsealfour;
	}






	public String getOldsealfive() {
		return oldsealfive;
	}






	public void setOldsealfive(String oldsealfive) {
		this.oldsealfive = oldsealfive;
	}






	public String getNewsealfour() {
		return newsealfour;
	}






	public void setNewsealfour(String newsealfour) {
		this.newsealfour = newsealfour;
	}






	public String getNewsealfive() {
		return newsealfive;
	}






	public void setNewsealfive(String newsealfive) {
		this.newsealfive = newsealfive;
	}






	public String getExtra1() {
		return extra1;
	}






	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}






	public String getExtra2() {
		return extra2;
	}






	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}


	


	

}
