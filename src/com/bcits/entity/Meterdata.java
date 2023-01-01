package com.bcits.entity;

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

@Table(name = "meter_list" , schema="meter_data")
@NamedQueries({
@NamedQuery(name = "Meterdata.checkMeterExists", query = "SELECT COUNT(*) FROM Meterdata m WHERE m.meter_serial_no=:metrno"),
})
public class Meterdata {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="ID")
	private int id;
	
	
	@Column(name = "meter_serial_no")
	private String meter_serial_no;
	
	@Column(name = "METER_TYPE")
	private String meter_type;
	
	@Column(name = "METER_MAKE")
	private String meter_make;
	
	@Column(name = "METER_OWNERSHIP")
	private String meter_ownership;
	
	@Column(name = "NO_OF_DIGIT_TYPE")
	private String no_of_digit_type;
	
	@Column(name = "METER_CT_RATIO")
	private String meter_ct_ratio;
	
	@Column(name = "METER_PT_RATIO")
	private String meter_pt_ratio;
	
	@Column(name = "METER_STATUS")
	private String meter_status;
	
	
	@Column(name = "CONNECTION_STATUS")
	private String connection_status;

	
	public String getConnection_status() {
		return connection_status;
	}

	public void setConnection_status(String connection_status) {
		this.connection_status = connection_status;
	}

	@Column(name="date")
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMeter_status() {
		return meter_status;
	}

	public void setMeter_status(String meter_status) {
		this.meter_status = meter_status;
	}



	public String getMeter_serial_no() {
		return meter_serial_no;
	}

	public void setMeter_serial_no(String meter_serial_no) {
		this.meter_serial_no = meter_serial_no;
	}

	public String getMeter_type() {
		return meter_type;
	}

	public void setMeter_type(String meter_type) {
		this.meter_type = meter_type;
	}

	public String getMeter_make() {
		return meter_make;
	}

	public void setMeter_make(String meter_make) {
		this.meter_make = meter_make;
	}

	public String getMeter_ownership() {
		return meter_ownership;
	}

	public void setMeter_ownership(String meter_ownership) {
		this.meter_ownership = meter_ownership;
	}

	public String getNo_of_digit_type() {
		return no_of_digit_type;
	}

	public void setNo_of_digit_type(String no_of_digit_type) {
		this.no_of_digit_type = no_of_digit_type;
	}

	public String getMeter_ct_ratio() {
		return meter_ct_ratio;
	}

	public void setMeter_ct_ratio(String meter_ct_ratio) {
		this.meter_ct_ratio = meter_ct_ratio;
	}

	public String getMeter_pt_ratio() {
		return meter_pt_ratio;
	}

	public void setMeter_pt_ratio(String meter_pt_ratio) {
		this.meter_pt_ratio = meter_pt_ratio;
	}
	
	
	
	
	
}
