package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
/*@Table(name="D1_DATA2" , schema="mdm_test")*/
@Table(name="D1_DATA" , schema="mdm_test")
public class D1_data implements java.io.Serializable 
{

	@Id
	/*@SequenceGenerator(name = "d1Id", sequenceName = "d1_data_id")
	 @GeneratedValue(generator = "d1Id")	*/
	/*@Id
	@GeneratedValue(strategy=GenerationType.AUTO)*/
	
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="D1_ID")
	private int d1Id;
	
	@Column(name="CDF_ID")
	private int CdfId;
	
	@Column(name="METER_CLASS")
	private String meterClass;
	
	@Column(name="METER_TYPE")
	private String meterType;
	
	@Column(name="MANUFACTURER_CODE")
	private String manufacturerCode;
	
	@Column(name="MANUFACTURER_NAME")
	private String manufacturerName;
	
	@Column(name="METERDATE")
	private String meterDate;
	
	public D1_data()
	{
		
	}


	public int getD1Id()
	{
		return d1Id;
	}


	public void setD1Id(int d1Id)
	{
		this.d1Id = d1Id;
	}


	public int getCdfId()
	{
		return CdfId;
	}


	public void setCdfId(int cdfId)
	{
		CdfId = cdfId;
	}


	public String getMeterClass()
	{
		return meterClass;
	}


	public void setMeterClass(String meterClass)
	{
		this.meterClass = meterClass;
	}


	public String getMeterType()
	{
		return meterType;
	}


	public void setMeterType(String meterType)
	{
		this.meterType = meterType;
	}


	public String getManufacturerCode()
	{
		return manufacturerCode;
	}


	public void setManufacturerCode(String manufacturerCode)
	{
		this.manufacturerCode = manufacturerCode;
	}


	public String getManufacturerName()
	{
		return manufacturerName;
	}


	public void setManufacturerName(String manufacturerName)
	{
		this.manufacturerName = manufacturerName;
	}


	public String getMeterDate()
	{
		return meterDate;
	}


	public void setMeterDate(String meterDate)
	{
		this.meterDate = meterDate;
	}
	
	
}
