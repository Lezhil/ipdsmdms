package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SDOJCC",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="SdoJcc.findAll",query="SELECT s FROM SdoJcc s ORDER BY s.sdoCode" ),
	@NamedQuery(name="SdoJcc.getsubDivisionName",query="SELECT DISTINCT(s.sdoName) FROM SdoJcc s WHERE s.sdoCode=:sdoCode" ),
	@NamedQuery(name="SdoJcc.getAllCircle",query="SELECT DISTINCT(s.circle) FROM SdoJcc s ORDER BY s.circle" ),
	@NamedQuery(name="SdoJcc.getAllDivisonBasedOnCircle",query="SELECT DISTINCT(s.division) FROM SdoJcc s WHERE s.circle=:circle ORDER BY s.division ASC" ),
	@NamedQuery(name="SdoJcc.getAllSubDivisonBasedOnDivision",query="SELECT DISTINCT(s.sdoName),s.sdoCode FROM SdoJcc s WHERE s.division=:division ORDER BY s.sdoName ASC" ),
	@NamedQuery(name="SdoJcc.getDistALLDivision",query="SELECT DISTINCT(s.division) FROM SdoJcc s  ORDER BY s.division ASC" ),
	@NamedQuery(name="SdoJcc.getDistALLSdoCodes",query="SELECT DISTINCT(s.sdoCode) FROM SdoJcc s  ORDER BY s.sdoCode ASC" ),
	@NamedQuery(name="SdoJcc.getDistALLSdoNames",query="SELECT DISTINCT(s.sdoName) FROM SdoJcc s  ORDER BY s.sdoName ASC" ),
	//@NamedQuery(name="SdoJcc.getDistMNPSDOJCC",query="SELECT DISTINCT(s.mnp) FROM SdoJcc s WHERE s.mnp is NOT NULL ORDER BY s.mnp ASC" ),
	@NamedQuery(name="SdoJcc.getAllDetailsForAccno",query="SELECT s FROM SdoJcc s WHERE sdoCode=:sdoCode"),
	@NamedQuery(name="SdoJcc.getDivisionByCIR",query="SELECT DISTINCT(s.division) FROM SdoJcc s where s.circle=:circle ORDER BY s.division ASC"),
	@NamedQuery(name="SdoJcc.getSUBDivisionByCIR",query="SELECT DISTINCT(s.sdoName) FROM SdoJcc s where s.division=:division ORDER BY s.sdoName ASC"),
	
	@NamedQuery(name="SdoJcc.getDataFromsdoJcc",query="SELECT s.circle,s.division,s.sdoName,s.sdoCode FROM SdoJcc s where s.sdoCode=:sdoCode"),
	
	
})
public class SdoJcc
{
	@Id
	/*@SequenceGenerator(name = "sdojccId", sequenceName = "SDOJCC_ID")
	@GeneratedValue(generator = "sdojccId")	*/
	@Column(name="ID")
	private int id;
	
	@Column(name="SDOCODE")
	private String sdoCode;
	
	@Column(name="SDONAME")
	private String sdoName;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="DIVISION")
	private String division;
	
	@Column(name="RDNGMONTH")
	private String readingMonth;

	@Column(name="CIRCLE")
	private String circle;
	
	/*@Column(name="NEWSDOCODE")
	private String newsdocode;
	
	@Column(name="MNP")
	private String mnp;*/
	
	public SdoJcc()
	{
		
	}

/*	public String getMnp() {
		return mnp;
	}

	public void setMnp(String mnp) {
		this.mnp = mnp;
	}*/

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getSdoCode()
	{
		return sdoCode;
	}

	public void setSdoCode(String sdoCode)
	{
		this.sdoCode = sdoCode;
	}

	public String getSdoName()
	{
		return sdoName;
	}

	public void setSdoName(String sdoName)
	{
		this.sdoName = sdoName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getDivision()
	{
		return division;
	}

	public void setDivision(String division)
	{
		this.division = division;
	}

	public String getReadingMonth()
	{
		return readingMonth;
	}

	public void setReadingMonth(String readingMonth)
	{
		this.readingMonth = readingMonth;
	}
	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

/*	public String getNewsdocode() {
		return newsdocode;
	}

	public void setNewsdocode(String newsdocode) {
		this.newsdocode = newsdocode;
	}
	
	*/
}
