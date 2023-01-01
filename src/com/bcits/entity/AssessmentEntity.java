package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ASSESSMENT",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="AssessmentEntity.getReport",query="SELECT c FROM AssessmentEntity c WHERE c.billmonth = :billmonth AND c.circle = :circle AND c.category = :category AND c.tamper_type = :tamperType AND c.metrno=:meterNo" )
})

public class AssessmentEntity
{
	@Id
	//@SequenceGenerator(name = "assementName", sequenceName = "ASSESSMENT_SEQ")
	//@GeneratedValue(generator = "assementName")	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="BILLMONTH")
	private int billmonth;
	
	@Column(name="CIRCLE")
	private String circle;
	
	@Column(name="DIVISION")
	private String division;
	
	@Column(name="SUBDIV")
	private String subdiv;
	
	@Column(name="CATEGORY")
	private String category;
	
	@Column(name="METRNO")
	private String metrno;
	
	@Column(name="MTRMAKE")
	private String mtrmake;

	@Column(name="MF")
	private double mf;
	
	@Column(name="TAMPER_TYPE")
	private String tamper_type;
	
	@Column(name="OCCDATE")
	private String occdate;
	
	@Column(name="RESTDATE")
	private String restdate;
	
	@Column(name="DURATION")
	private String duration;
	
	@Column(name="UNITS")
	private int units;
	
	@Column(name="ACTUAL_UNITS")
	private int actual_units;
	
	@Column(name="UNITS_TOBECHARGE")
	private int units_tobecharge;
	
	@Column(name="CDF_ID")
	private int cdf_id;
	
	@Column(name="D5_ID")
	private String d5_id;
	
	@Column(name="RATE")
	private double rate;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="INDUSTRYTYPE")
	private String industrytype;
	
	@Column(name="SANLOAD")
	private double sanload;
	
	@Column(name="LORID")
	private int lorid;
	
	@Column(name="DISCOM")
	private String discom;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getBillmonth() {
		return billmonth;
	}

	public void setBillmonth(int billmonth) {
		this.billmonth = billmonth;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSubdiv() {
		return subdiv;
	}

	public void setSubdiv(String subdiv) {
		this.subdiv = subdiv;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMetrno() {
		return metrno;
	}

	public void setMetrno(String metrno) {
		this.metrno = metrno;
	}

	public String getMtrmake() {
		return mtrmake;
	}

	public void setMtrmake(String mtrmake) {
		this.mtrmake = mtrmake;
	}

	public double getMf() {
		return mf;
	}

	public void setMf(double mf) {
		this.mf = mf;
	}

	public String getTamper_type() {
		return tamper_type;
	}

	public void setTamper_type(String tamper_type) {
		this.tamper_type = tamper_type;
	}
	

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public int getActual_units() {
		return actual_units;
	}

	public void setActual_units(int actual_units) {
		this.actual_units = actual_units;
	}

	public int getUnits_tobecharge() {
		return units_tobecharge;
	}

	public void setUnits_tobecharge(int units_tobecharge) {
		this.units_tobecharge = units_tobecharge;
	}

	public int getCdf_id() {
		return cdf_id;
	}

	public void setCdf_id(int cdf_id) {
		this.cdf_id = cdf_id;
	}

	public String getD5_id() {
		return d5_id;
	}

	public void setD5_id(String d5_id) {
		this.d5_id = d5_id;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIndustrytype() {
		return industrytype;
	}

	public void setIndustrytype(String industrytype) {
		this.industrytype = industrytype;
	}

	public double getSanload() {
		return sanload;
	}

	public void setSanload(double sanload) {
		this.sanload = sanload;
	}

	public int getLorid() {
		return lorid;
	}

	public void setLorid(int lorid) {
		this.lorid = lorid;
	}

	public String getOccdate() {
		return occdate;
	}

	public void setOccdate(String occdate) {
		this.occdate = occdate;
	}

	public String getRestdate() {
		return restdate;
	}

	public void setRestdate(String eventtime1) {
		this.restdate = eventtime1;
	}

	public String getDiscom() {
		return discom;
	}

	public void setDiscom(String discom) {
		this.discom = discom;
	}
}
