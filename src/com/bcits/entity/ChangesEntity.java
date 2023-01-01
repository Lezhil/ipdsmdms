package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.context.annotation.Lazy;

@Entity
@Table(name = "CHANGES")
@Lazy(false)
public class ChangesEntity {

	@Id
	@SequenceGenerator(name = "changesId", sequenceName = "CHANGES_ID")
	@GeneratedValue(generator = "changesId")
	@Column(name = "ID")
	private long id;

	@Column(name = "ACCNO")
	private String accno;

	@Column(name = "NAME")
	private String name;

	@Column(name = "RDNGMONTH")
	private int rdgMonth;

	@Column(name = "OLDNEW")
	private String oldNew;

	@Column(name = "PREVMETERSTATUS")
	private String prevMtrStatus;

	@Column(name = "TADESC")
	private String tadesc;

	@Column(name = "METRNO")
	private String meterno;

	@Column(name = "CST")
	private String cst;

	@Column(name = "MTRMAKE")
	private String mtrmake;

	@Column(name = "MRNAME")
	private String mrname;

	@Column(name = "TARIFFCODE")
	private String tariffCode;

	@Column(name = "KWORHP")
	private String kwOrHp;

	@Column(name = "SANLOAD")
	private double sanload;

	@Column(name = "MTRTYPE")
	private String mtrType;

	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "CD")
	private String cd;

	@Column(name = "CTRN")
	private double ctrn;

	@Column(name = "CTRD")
	private double ctrd;

	@Column(name = "MF")
	private double mf;

	@Column(name = "SUPPLYTYPE")
	private String supplyType;

	@Column(name = "DATESTAMP")
	private Date dateStamp;

	@Column(name = "TIMESTAMP")
	private String timeStamp;

	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "KNO")
	private String kno;
	
	@Column(name = "SDOCODE")
	private Integer sdocode;

	public ChangesEntity()
	{
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRdgMonth() {
		return rdgMonth;
	}

	public void setRdgMonth(int rdgMonth) {
		this.rdgMonth = rdgMonth;
	}

	public String getOldNew() {
		return oldNew;
	}

	public void setOldNew(String oldNew) {
		this.oldNew = oldNew;
	}

	public String getPrevMtrStatus() {
		return prevMtrStatus;
	}

	public void setPrevMtrStatus(String prevMtrStatus) {
		this.prevMtrStatus = prevMtrStatus;
	}

	public String getTadesc() {
		return tadesc;
	}

	public void setTadesc(String tadesc) {
		this.tadesc = tadesc;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public String getMtrmake() {
		return mtrmake;
	}

	public void setMtrmake(String mtrmake) {
		this.mtrmake = mtrmake;
	}

	public String getMrname() {
		return mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	public String getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}

	public String getKwOrHp() {
		return kwOrHp;
	}

	public void setKwOrHp(String kwOrHp) {
		this.kwOrHp = kwOrHp;
	}

	public double getSanload() {
		return sanload;
	}

	public void setSanload(double sanload) {
		this.sanload = sanload;
	}

	public String getMtrType() {
		return mtrType;
	}

	public void setMtrType(String mtrType) {
		this.mtrType = mtrType;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public double getCtrn() {
		return ctrn;
	}

	public void setCtrn(double ctrn) {
		this.ctrn = ctrn;
	}

	public double getCtrd() {
		return ctrd;
	}

	public void setCtrd(double ctrd) {
		this.ctrd = ctrd;
	}

	public double getMf() {
		return mf;
	}

	public void setMf(double mf) {
		this.mf = mf;
	}

	public String getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}

	public Integer getSdocode() {
		return sdocode;
	}

	public void setSdocode(Integer sdocode) {
		this.sdocode = sdocode;
	}

	
}
