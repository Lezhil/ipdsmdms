package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="input_energy",schema="meter_data")
public class InputEnergy {
		
	
	  @Id
	  
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  
	  @Column(name="id") private int id;
	
	@Column(name="billmonth")
	private String billmonth;
	@Column(name="mtrno")
	private String mtrno;
	@Column(name="time_stamp")
	private Date time_stamp;
	@Column(name="kwh_imp")
	private int kwh_imp;

	
	 public int getId() { 
		 return id; 
		 }
	 public void setId(int id) { 
		 this.id = id; 
		 
	 }
	
	public String getBillmonth() {
		return billmonth;
	}
	public void setBillmonth(String billmonth) {
		this.billmonth = billmonth;
	}
	public String getMtrno() {
		return mtrno;
	}
	public void setMtrno(String mtrno) {
		this.mtrno = mtrno;
	}
	public Date getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}
	public int getKwh_imp() {
		return kwh_imp;
	}
	public void setKwh_imp(int kwh_imp) {
		this.kwh_imp = kwh_imp;
	}
	
	
	
	
}
