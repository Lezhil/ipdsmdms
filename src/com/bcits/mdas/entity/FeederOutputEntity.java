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
@Table(name = "FEEDER_OUTPUT", schema = "VCLOUDENGINE")
@NamedQueries({
	 @NamedQuery(name="FeederOutputEntity.findDistinctZones",query="select DISTINCT abi.zone from FeederOutputEntity abi where abi.zone <> '' and abi.zone is not null "),
	 @NamedQuery(name="FeederOutputEntity.findDistinctCircle",query="select DISTINCT abi.circle from FeederOutputEntity abi where abi.circle <> '' and abi.circle is not null and abi.zone = :zone "),
	 @NamedQuery(name="FeederOutputEntity.findDistinctDivision",query="select DISTINCT abi.division from FeederOutputEntity abi where abi.division <> '' and abi.division is not null and abi.zone = :zone and abi.circle = :circle "),
	 @NamedQuery(name="FeederOutputEntity.findDistinctSubdivision",query="select DISTINCT abi.subdivision from FeederOutputEntity abi where abi.subdivision <> '' and abi.subdivision is not null and abi.zone = :zone and abi.circle = :circle and abi.division = :division "),
	 @NamedQuery(name="FeederOutputEntity.findDistinctsubstation",query="select DISTINCT abi.substation from FeederOutputEntity abi where abi.substation <> '' and abi.substation is not null and abi.zone = :zone and abi.circle = :circle and abi.division = :division and abi.subdivision = :subdivision "),
	 @NamedQuery(name="FeederOutputEntity.findall",query="select feeder_code,feeder_name,meter_number,meter_make,meter_year,dlms,port_configuration,substation,district,ct_ratio,pt_ratio,division,subdivision,state,zone,circle from FeederOutputEntity abi where abi.zone = :zone and abi.circle = :circle and abi.division = :division and abi.subdivision = :subdivision and abi.substation = :substation and installation = '0' "),
	 @NamedQuery(name="FeederOutputEntity.getViewOnMapMtrData" , query="SELECT h FROM FeederOutputEntity h WHERE h.meter_number=:mtrNumber"),
	 @NamedQuery(name="FeederOutputEntity.findOnlyImage", 	query="SELECT h FROM FeederOutputEntity h WHERE h.meter_number=:mtrNumber"),
	 })
public class FeederOutputEntity {

	
	@Column(name = "SITECODE")
	private String sitecode;

	@Column(name = "MRCODE")
	private String mrcode;

	@Column(name = "feeder_code")
	private String feeder_code;

	@Column(name = "feeder_name")
	private String feeder_name;

	@Column(name = "meter_number")
	private String meter_number;

	@Column(name = "meter_make")
	private String meter_make;

	@Column(name = "meter_year")
	private String meter_year;

	@Column(name = "dlms")
	private String dlms;

	@Column(name = "port_configuration")
	private String port_configuration;

	@Column(name = "substation")
	private String substation;

	@Column(name = "district")
	private String district;

	@Column(name = "panel_space")
	private String panel_space;

	@Column(name = "power_supply")
	private String power_supply;

	@Column(name = "enclosed_panel")
	private String enclosed_panel;

	@Column(name = "cable_length")
	private String cable_length;

	@Column(name = "network_availability")
	private String network_availability;

	@Column(name = "ct_ratio")
	private String ct_ratio;

	@Column(name = "pt_ratio")
	private String pt_ratio;

	@Column(name = "mf")
	private String mf;

	@Column(name = "timestaken")
	private String timestaken;

	@Column(name = "accuracy")
	private String accuracy;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "front_image")
	private byte[] front_image;
	@Column(name = "left_image")
	private byte[] left_image;
	@Column(name = "right_image")
	private byte[] right_image;

	@Column(name = "imei")
	private String imei;

	@Column(name = "version")
	private String version;

	@Column(name = "ttb_image")
	private byte[] ttb_image;

	@Column(name = "port_image")
	private byte[] port_image;

	@Column(name = "division")
	private String division;

	@Column(name = "subdivision")
	private String subdivision;
	
	@Column(name = "extra3")
	private String extra3;
	
	@Column(name = "extra4")
	private String extra4;
	
	@Column(name = "extra5")
	private String extra5;
	
	@Column(name = "extra6")
	private String extra6;
	
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

	@Column(name = "installation")
	private String installation="0";
	
	public FeederOutputEntity() {

	}


	public FeederOutputEntity(String sitecode, String mrcode, String feeder_code, String feeder_name,
			String meter_number, String meter_make, String meter_year, String dlms, String port_configuration,
			String substation, String district, String panel_space, String power_supply, String enclosed_panel,
			String cable_length, String network_availability, String ct_ratio, String pt_ratio, String mf,
			String timestaken, String accuracy, String latitude, String longitude, byte[] front_image,
			byte[] left_image, byte[] right_image, String imei, String version, byte[] ttb_image, byte[] port_image,
			String division,String subdivision,String extra3,String extra4,String extra5,String extra6,String state,
			String zone ,String circle) {
		super();
		this.sitecode = sitecode;
		this.mrcode = mrcode;
		this.feeder_code = feeder_code;
		this.feeder_name = feeder_name;
		this.meter_number = meter_number;
		this.meter_make = meter_make;
		this.meter_year = meter_year;
		this.dlms = dlms;
		this.port_configuration = port_configuration;
		this.substation = substation;
		this.district = district;
		this.panel_space = panel_space;
		this.power_supply = power_supply;
		this.enclosed_panel = enclosed_panel;
		this.cable_length = cable_length;
		this.network_availability = network_availability;
		this.ct_ratio = ct_ratio;
		this.pt_ratio = pt_ratio;
		this.mf = mf;
		this.timestaken = timestaken;
		this.accuracy = accuracy;
		this.latitude = latitude;
		this.longitude = longitude;
		this.front_image = front_image;
		this.left_image = left_image;
		this.right_image = right_image;
		this.imei = imei;
		this.version = version;
		this.ttb_image = ttb_image;
		this.port_image = port_image;
		this.division = division;
		this.subdivision = subdivision;
		this.extra3 = extra3;
		this.extra4 = extra4;
		this.extra5 = extra5;
		this.extra6 = extra6;
		this.state = state;
		this.zone = zone;
		this.circle = circle;
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

	public String getFeeder_code() {
		return feeder_code;
	}

	public void setFeeder_code(String feeder_code) {
		this.feeder_code = feeder_code;
	}

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}

	public String getMeter_number() {
		return meter_number;
	}

	public void setMeter_number(String meter_number) {
		this.meter_number = meter_number;
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

	public String getPort_configuration() {
		return port_configuration;
	}

	public void setPort_configuration(String port_configuration) {
		this.port_configuration = port_configuration;
	}

	public String getSubstation() {
		return substation;
	}

	public void setSubstation(String substation) {
		this.substation = substation;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPanel_space() {
		return panel_space;
	}

	public void setPanel_space(String panel_space) {
		this.panel_space = panel_space;
	}

	public String getPower_supply() {
		return power_supply;
	}

	public void setPower_supply(String power_supply) {
		this.power_supply = power_supply;
	}

	public String getEnclosed_panel() {
		return enclosed_panel;
	}

	public void setEnclosed_panel(String enclosed_panel) {
		this.enclosed_panel = enclosed_panel;
	}

	public String getCable_length() {
		return cable_length;
	}

	public void setCable_length(String cable_length) {
		this.cable_length = cable_length;
	}

	public String getNetwork_availability() {
		return network_availability;
	}

	public void setNetwork_availability(String network_availability) {
		this.network_availability = network_availability;
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

	public String getTimestaken() {
		return timestaken;
	}

	public void setTimestaken(String timestaken) {
		this.timestaken = timestaken;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public byte[] getFront_image() {
		return front_image;
	}

	public void setFront_image(byte[] front_image) {
		this.front_image = front_image;
	}

	public byte[] getLeft_image() {
		return left_image;
	}

	public void setLeft_image(byte[] left_image) {
		this.left_image = left_image;
	}

	public byte[] getRight_image() {
		return right_image;
	}

	public void setRight_image(byte[] right_image) {
		this.right_image = right_image;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public byte[] getTtb_image() {
		return ttb_image;
	}

	public void setTtb_image(byte[] ttb_image) {
		this.ttb_image = ttb_image;
	}

	public byte[] getPort_image() {
		return port_image;
	}

	public void setPort_image(byte[] port_image) {
		this.port_image = port_image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
