package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity

@Table(name="RDNGMONTH" , schema="meter_data")

@NamedQueries({
	@NamedQuery(name="RdngMonth.findAll",query="SELECT r FROM RdngMonth r")
})
public class RdngMonth
{

	@Id
	@Column(name="RMONTH")
	private int rmonth;
	
	public RdngMonth()
	{
		
	}

	public int getRmonth()
	{
		return rmonth;
	}

	public void setRmonth(int rmonth)
	{
		this.rmonth = rmonth;
	}
	
	
}
