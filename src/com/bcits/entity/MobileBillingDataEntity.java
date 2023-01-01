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
@Table(name = "SBMDOWNLOADDATA",schema="mdm_test")
@NamedQueries({

		@NamedQuery(name = "MobileBillingDataEntity.getConsumerDataForMobile", query = "SELECT m FROM MobileBillingDataEntity m where m.mrname =:mrname and m.billmonth =:billmonth and m.uploadstatus =:uploadstatus"),
		@NamedQuery(name = "MobileBillingDataEntity.GetMrname", query = "SELECT DISTINCT m.mrname FROM MobileBillingDataEntity m ORDER BY m.mrname"),
		@NamedQuery(name = "MobileBillingDataEntity.GetCurrBillMonth", query = "SELECT COUNT(*) FROM MobileBillingDataEntity M WHERE M.billmonth=:billmonth"),
		@NamedQuery(name = "MobileBillingDataEntity.updateUploadStatus", query = "UPDATE MobileBillingDataEntity m SET m.uploadstatus = :uploadstatus WHERE M.consumerid=:consumerid AND M.billmonth=:billmonth ")

})
/* Author: Ved Prakash Mishra */
public class MobileBillingDataEntity {
	@Id
	@SequenceGenerator(name = "sbmSeq", sequenceName = "SBM_ID")
	@GeneratedValue(generator = "sbmSeq")
	@Column(name = "SBMDID")
	private Integer sbmdid;

	@Column(name = "CONSUMERID")
	private String consumerid;

	@Column(name = "METRNO")
	private String metrno;

	@Column(name = "CONSUMERNAME")
	private String consumername;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "BILLMONTH")
	private Integer billmonth;

	@Column(name = "PKWH")
	private Double pkwh;

	@Column(name = "PKVAH")
	private Double pkvah;

	@Column(name = "PKVA")
	private Double pkva;

	@Column(name = "OLDSEAL")
	private String oldseal;

	@Column(name = "MRNAME")
	private String mrname;

	@Column(name = "UPLOADSTATUS")
	private Integer uploadstatus;

	@Column(name = "TADESC")
	private String tadesc;

	@Column(name = "PHONENO")
	private String phoneno;

	@Column(name = "MTRMAKE")
	private String mtrmake;

	@Column(name = "INDUSTRYTYPE")
	private String industryType;

	@Column(name = "SDONAME")
	private String sdoName;

	@Column(name = "SDOCODE")
	private String sdoCode;

	@Column(name = "MNP")
	private String mnp;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "CIRCLE")
	private String circle;

	@Column(name = "TN")
	private String tn;

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public Integer getUploadstatus() {
		return uploadstatus;
	}

	public void setUploadstatus(Integer uploadstatus) {
		this.uploadstatus = uploadstatus;
	}

	public String getMtrmake() {
		return mtrmake;
	}

	public void setMtrmake(String mtrmake) {
		this.mtrmake = mtrmake;
	}

	public Integer getSbmdid() {
		return sbmdid;
	}

	public void setSbmdid(Integer sbmdid) {
		this.sbmdid = sbmdid;
	}

	public String getConsumerid() {
		return consumerid;
	}

	public void setConsumerid(String consumerid) {
		this.consumerid = consumerid;
	}

	public String getMetrno() {
		return metrno;
	}

	public void setMetrno(String metrno) {
		this.metrno = metrno;
	}

	public String getConsumername() {
		return consumername;
	}

	public void setConsumername(String sonsumername) {
		this.consumername = sonsumername;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getBillmonth() {
		return billmonth;
	}

	public void setBillmonth(Integer billmonth) {
		this.billmonth = billmonth;
	}

	public Double getPkwh() {
		return pkwh;
	}

	public void setPkwh(Double pkwh) {
		this.pkwh = pkwh;
	}

	public Double getPkvah() {
		return pkvah;
	}

	public void setPkvah(Double pkvah) {
		this.pkvah = pkvah;
	}

	public Double getPkva() {
		return pkva;
	}

	public void setPkva(Double pkva) {
		this.pkva = pkva;
	}

	public String getOldseal() {
		return oldseal;
	}

	public void setOldseal(String oldseal) {
		this.oldseal = oldseal;
	}

	public String getMrname() {
		return mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	public String getTadesc() {
		return tadesc;
	}

	public void setTadesc(String tadesc) {
		this.tadesc = tadesc;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getSdoName() {
		return sdoName;
	}

	public void setSdoName(String sdoName) {
		this.sdoName = sdoName;
	}

	public String getSdoCode() {
		return sdoCode;
	}

	public void setSdoCode(String sdoCode) {
		this.sdoCode = sdoCode;
	}

	public String getMnp() {
		return mnp;
	}

	public void setMnp(String mnp) {
		this.mnp = mnp;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}
	
	

}
