package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ASSESMENT")
@NamedQueries({
	@NamedQuery(name="Assesment.findAll",query="SELECT a FROM Assesment a ORDER BY a.billmonth desc")
})
public class Assesment
{

	@Id
	@SequenceGenerator(name="assId",sequenceName="ASSMT_ID")
	@GeneratedValue(generator="assId")
	@Column(name="ID")
	private int id;
	
	@Column(name="BILLMONTH")
	private String billmonth;
	
	@Column(name="TOTALREADS")
	private int totalReads;
	
	@Column(name="TOTALASSESEDCONSUMERS")
	private int totalAssesedConsumers;
	
	@Column(name="TOTALASSESMENT")
	private double totalAssesment;
	
	public Assesment()
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

	public String getBillmonth()
	{
		return billmonth;
	}

	public void setBillmonth(String billmonth)
	{
		this.billmonth = billmonth;
	}

	public int getTotalReads()
	{
		return totalReads;
	}

	public void setTotalReads(int totalReads)
	{
		this.totalReads = totalReads;
	}

	public int getTotalAssesedConsumers()
	{
		return totalAssesedConsumers;
	}

	public void setTotalAssesedConsumers(int totalAssesedConsumers)
	{
		this.totalAssesedConsumers = totalAssesedConsumers;
	}

	public double getTotalAssesment() {
		return totalAssesment;
	}

	public void setTotalAssesment(double totalAssesment) {
		this.totalAssesment = totalAssesment;
	}

	
	
	
	
}
