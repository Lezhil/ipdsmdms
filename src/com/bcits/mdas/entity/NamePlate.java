package com.bcits.mdas.entity;

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
@Table(name="name_plate",schema="METER_DATA")
@NamedQueries({
	@NamedQuery(name="NamePlate.getNodeList",query="select n.meter_serial_number,n.nodeId from NamePlate n where n.nodeId is not null"),
	@NamedQuery(name="NamePlate.getNamePlateDataByMeterNo",query="select n from NamePlate n where n.meter_serial_number is not null and n.meter_serial_number=:meterid")
})
public class NamePlate {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Id
	@Column(name="meter_serial_number")
	private String meter_serial_number;
	
	@Column(name="device_id")
	private String device_id;
	
	@Column(name="manufacturer_name")
	private String manufacturer_name;
	
	@Column(name="firmware_version")
	private String firmware_version;
	
	@Column(name="meter_type")
	private String meter_type;
	
	@Column(name="meter_catagory")
	private String meter_catagory;
	
	@Column(name="current_rating")
	private String current_rating;
	
	/*@Column(name="name_plate2")
	private String name_plate2;
	@Column(name="name_plate3")
	private String name_plate3;*/
	@Column(name="year_of_manufacture")
	private String year_of_manufacture;
	
	@Column(name="nflag")
	private String flag;
	
	@Column(name = "hardware_version")
	private String hardwareVersion;
	
	@Column(name="created_time")
	private Timestamp createdTime;
	
	@Column(name="updated_time")
	private Timestamp updatedTime;
	
	@Column(name = "node_id")
	private String nodeId;
	
	@Column(name = "ct_ratio")
	private Double ct_ratio;
	
	@Column(name = "pt_ratio")
	private Double pt_ratio;
	
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getHardwareVersion() {
		return hardwareVersion;
	}
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public Timestamp getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMeter_serial_number() {
		return meter_serial_number;
	}
	public void setMeter_serial_number(String meter_serial_number) {
		this.meter_serial_number = meter_serial_number;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getManufacturer_name() {
		return manufacturer_name;
	}
	public void setManufacturer_name(String manufacturer_name) {
		this.manufacturer_name = manufacturer_name;
	}
	public String getFirmware_version() {
		return firmware_version;
	}
	public void setFirmware_version(String firmware_version) {
		this.firmware_version = firmware_version;
	}
	public String getMeter_type() {
		return meter_type;
	}
	public void setMeter_type(String meter_type) {
		this.meter_type = meter_type;
	}
	public String getMeter_catagory() {
		return meter_catagory;
	}
	public void setMeter_catagory(String meter_catagory) {
		this.meter_catagory = meter_catagory;
	}
	public String getCurrent_rating() {
		return current_rating;
	}
	public void setCurrent_rating(String current_rating) {
		this.current_rating = current_rating;
	}
	/*public String getName_plate2() {
		return name_plate2;
	}
	public void setName_plate2(String name_plate2) {
		this.name_plate2 = name_plate2;
	}
	public String getName_plate3() {
		return name_plate3;
	}
	public void setName_plate3(String name_plate3) {
		this.name_plate3 = name_plate3;
	}*/
	public String getYear_of_manufacture() {
		return year_of_manufacture;
	}
	public void setYear_of_manufacture(String year_of_manufacture) {
		this.year_of_manufacture = year_of_manufacture;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
	

}
