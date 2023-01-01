package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@Entity
@Table(name = "SUBSTATION_OUTPUT", schema = "VCLOUDENGINE")
@NamedQueries({
	
})
public class SubstationOutputEntity 
{
	@Column(name = "SITECODE")
	private String sitecode;
	
	@Column(name = "MRCODE")
	private String mrcode;

	@Column(name = "SUBSTATION_NAME")
	private String substation_name;
	
	@Column(name = "SUBSTAtION_ADDRESS")
	private String substation_address;
	
	@Column(name = "SUBSTATION_DISTRICT")
	private String substation_district;
	
	@Column(name = "timestamp")
	private String timestamp;

	@Column(name = "ACCURACY")
	private String accuracy;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "FIRST_IMAGE")
	private byte[] first_image;
	
	@Column(name = "SECOND_IMAGE")
	private byte[] second_image;
	
	@Column(name = "THIRD_IMAGE")
	private byte[] third_image;
	
	@Column(name = "FOURTH_IMAGE")
	private byte[] fourth_image;
	@Column(name = "FIFTH_IMAGE")
	private byte[] fifth_image;
	
	@Column(name = "SIXTH_IMAGE")
	private byte[] sixth_image;
	
	@Column(name = "SEVENTH_IMAGE")
	private byte[] seventh_image;
	
	@Column(name = "EIGHTH_IMAGE")
	private byte[] eighth_image;
	
	@Column(name = "NINTH_IMAGE")
	private byte[] ninth_image;
	
	@Column(name = "TENTH_IMAGE")
	private byte[] tenth_image;
	
	@Column(name = "IMEI")
	private String imei;

	@Column(name = "VERSION")
	private String version;
	
	@Id
	@Column(name = "id")
	private Integer id;

	public SubstationOutputEntity() {
		
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

	public String getSubstation_name() {
		return substation_name;
	}

	public void setSubstation_name(String substation_name) {
		this.substation_name = substation_name;
	}

	public String getSubstation_address() {
		return substation_address;
	}

	public void setSubstation_address(String substation_address) {
		this.substation_address = substation_address;
	}

	public String getSubstation_district() {
		return substation_district;
	}

	public void setSubstation_district(String substation_district) {
		this.substation_district = substation_district;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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

	public byte[] getFirst_image() {
		return first_image;
	}

	public void setFirst_image(byte[] first_image) {
		this.first_image = first_image;
	}

	public byte[] getSecond_image() {
		return second_image;
	}

	public void setSecond_image(byte[] second_image) {
		this.second_image = second_image;
	}

	public byte[] getThird_image() {
		return third_image;
	}

	public void setThird_image(byte[] third_image) {
		this.third_image = third_image;
	}

	public byte[] getFourth_image() {
		return fourth_image;
	}

	public void setFourth_image(byte[] fourth_image) {
		this.fourth_image = fourth_image;
	}

	public byte[] getFifth_image() {
		return fifth_image;
	}

	public void setFifth_image(byte[] fifth_image) {
		this.fifth_image = fifth_image;
	}

	public byte[] getSixth_image() {
		return sixth_image;
	}

	public void setSixth_image(byte[] sixth_image) {
		this.sixth_image = sixth_image;
	}

	public byte[] getSeventh_image() {
		return seventh_image;
	}

	public void setSeventh_image(byte[] seventh_image) {
		this.seventh_image = seventh_image;
	}

	public byte[] getEighth_image() {
		return eighth_image;
	}

	public void setEighth_image(byte[] eighth_image) {
		this.eighth_image = eighth_image;
	}

	public byte[] getNinth_image() {
		return ninth_image;
	}

	public void setNinth_image(byte[] ninth_image) {
		this.ninth_image = ninth_image;
	}

	public byte[] getTenth_image() {
		return tenth_image;
	}

	public void setTenth_image(byte[] tenth_image) {
		this.tenth_image = tenth_image;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
