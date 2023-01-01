package com.bcits.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
/*@Table(name="CDF_DATA2" ,schema="mdm_test")*/
@Table(name="CDF_DATA" ,schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="CDFData.ValidateMeterExistance",query="SELECT c FROM CDFData c WHERE c.billmonth = :billmonth AND c.meterNo = :meterNo " ),
	@NamedQuery(name="CDFData.findPrevDataD4",query="SELECT c.id FROM CDFData c WHERE c.meterNo = :meterNo" ),
	@NamedQuery(name="CDFData.getRecentCdfId",query="SELECT c.id FROM CDFData c WHERE c.billmonth = :billmonth AND c.meterNo = :meterNo ORDER BY c.id DESC" ),
	@NamedQuery(name="CDFData.getExistanceData",query="SELECT c FROM CDFData c WHERE to_char(readDate, 'dd-MM-yyyy') = :sendingDate AND c.meterNo = :meterNo"),
	@NamedQuery(name="CDFData.getRecentChangeCdfId",query="SELECT c.id FROM CDFData c WHERE to_char(readDate, 'dd-MM-yyyy') = :sendingDate AND c.meterNo = :meterNo ORDER BY c.id DESC" )
})
public class CDFData
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CDF_ID")
	private int id;
	
	@Column(name="ACCNO")
	private String accountNo;
	
	@Column(name="BILLMONTH")
	private int billmonth;
	
	
	@Column(name="METERNO")
	private String meterNo;
	
	
	@Column(name="READDATE")
	private Date readDate;
	
	
	@Column(name="DBDATE")
	private Date dbDate;	
	
	@Column(name="DISCOM")
	private String discom;
	
	@Column(name="METER_DATE")
	private Timestamp meter_date;
	

	public Timestamp getMeter_date() {
		return meter_date;
	}

	public void setMeter_date(Timestamp meter_date) {
		this.meter_date = meter_date;
	}

	public String getDiscom() {
		return discom;
	}

	public void setDiscom(String discom) {
		this.discom = discom;
	}

	@ManyToOne
	@JoinColumn(name = "CDF_ID", referencedColumnName="CDF_ID", insertable = false, updatable = false, nullable = false) 
	private D1_data d1data;
	
	public CDFData()
	{
		
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getAccountNo()
	{
		return accountNo;
	}

	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
	}

	public int getBillmonth()
	{
		return billmonth;
	}

	public void setBillmonth(int billmonth)
	{
		this.billmonth = billmonth;
	}

	public String getMeterNo()
	{
		return meterNo;
	}

	public void setMeterNo(String meterNo)
	{
		this.meterNo = meterNo;
	}

	public Date getReadDate()
	{
		return readDate;
	}

	public void setReadDate(Date readDate)
	{
		this.readDate = readDate;
	}

	public Date getDbDate()
	{
		return dbDate;
	}

	public void setDbDate(Date dbDate)
	{
		this.dbDate = dbDate;
	}

	public D1_data getD1data() {
		return d1data;
	}

	public void setD1data(D1_data d1data) {
		this.d1data = d1data;
	}
	
}
