package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ANGLE")
@NamedQueries({
	@NamedQuery(name="PfAngle.getLeadAngle",query="SELECT p FROM PfAngle p WHERE p.pfLead=:pfLead OR  p.pfLead > :pfLead")
	
})
public class PfAngle
{
	@Id
	@Column(name = "ID")
	private int pfAngleID;
	
	@Column(name = "ANGLE")
	private String pfAngle;

	@Column(name = "LEAD")
	private float pfLead;
	
	@Column(name = "LAG")
	private float pfLag;
	
	public PfAngle()
	{
		
	}

	public int getPfAngleID()
	{
		return pfAngleID;
	}

	public void setPfAngleID(int pfAngleID)
	{
		this.pfAngleID = pfAngleID;
	}

	public String getPfAngle()
	{
		return pfAngle;
	}

	public void setPfAngle(String pfAngle)
	{
		this.pfAngle = pfAngle;
	}

	public float getPfLead()
	{
		return pfLead;
	}

	public void setPfLead(float pfLead)
	{
		this.pfLead = pfLead;
	}

	public float getPfLag()
	{
		return pfLag;
	}

	public void setPfLag(float pfLag)
	{
		this.pfLag = pfLag;
	}
	
	
}
