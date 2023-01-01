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
@Table(name="D9_DATA" , schema="mdm_test") /*,schema="mdm_test"*/
@NamedQueries({
	@NamedQuery(name="D9Data.Transacions",query="SELECT d9.d9Id,d9.transactionCode,t.transactionDesc,d9.transactionDate FROM D9Data d9,CDFData c,TransactionMaster t WHERE d9.CdfId=c.id AND c.meterNo=:meterNo AND c.billmonth=:billMonth AND to_number(d9.transactionCode,'99999')=t.transactionCode ORDER BY d9.transactionDate DESC"),
	@NamedQuery(name="D9Data.TamperEventsData",query="SELECT d9.transactionCode,t.transactionDesc,COUNT(*) FROM D9Data d9,CDFData c,TransactionMaster t WHERE d9.CdfId=c.id  AND c.billmonth=:billMonth AND c.discom='UHBVN' AND d9.transactionCode=cast(t.transactionCode as text) GROUP BY d9.transactionCode,t.transactionDesc ORDER BY d9.transactionCode DESC ")
})

public class D9Data
{

	@Id
	/*@SequenceGenerator(name="d9Id",sequenceName="d9_data_id")
	@GeneratedValue(generator="d9Id")*/
	
	 @GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name="D9_ID")
	private int d9Id;
	
	@Column(name="CDF_ID")
	private int CdfId;
	
	@Column(name="TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name="DATETIME")
	private Date transactionDate;
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CDF_ID",insertable = false, updatable = false, nullable = false)
	private CDFData cdfData;*/
	
	public D9Data()
	{
		
	}

	public int getD9Id()
	{
		return d9Id;
	}

	public void setD9Id(int d9Id)
	{
		this.d9Id = d9Id;
	}

	public int getCdfId()
	{
		return CdfId;
	}

	public void setCdfId(int cdfId)
	{
		CdfId = cdfId;
	}

	public String getTransactionCode()
	{
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode)
	{
		this.transactionCode = transactionCode;
	}

	public Date getTransactionDate()
	{
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate)
	{
		this.transactionDate = transactionDate;
	}

	/*public CDFData getCdfData() {
		return cdfData;
	}

	public void setCdfData(CDFData cdfData) {
		this.cdfData = cdfData;
	}*/
	
}
