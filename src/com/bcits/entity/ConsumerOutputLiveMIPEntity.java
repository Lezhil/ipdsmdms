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
@Table(name = "CONSUMER_OUTPUT_LIVE_MIP")
@NamedQueries({
	@NamedQuery(name = "ConsumerOutputLiveMIPEntity.findAll", query = "SELECT colme FROM ConsumerOutputLiveMIPEntity colme ")


})

public class ConsumerOutputLiveMIPEntity {
	
	 @Id	
	 @SequenceGenerator(name="coutid",sequenceName="COUT_ID")
	 @GeneratedValue(generator="coutid")
	 @Column(name="ID")
	 private long id;
	
	   public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "meterno")
	private String meterno;
	
	@Column(name = "consumerid")
	private String consumerid;
	
	@Column(name = "consumername")
	private String consumername;
	
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "cmrid")
	private String cmrid;
	
	@Column(name = "billmonth")
	private String billmonth;
	
	@Column(name = "pkwh")
	private String pkwh;
	
	@Column(name = "pkvah")
	private String pkvah;
	
	@Column(name = "pkva")
	private String pkva;
	
	@Column(name = "pkw")
	private String pkw;
	
	@Column(name = "ppf")
	private String ppf;
	
	@Column(name = "ckwh")
	private String ckwh;
	
	@Column(name = "ckvah")
	private String ckvah;
	
	@Column(name = "ckva")
	private String ckva;
	
	
	@Column(name = "ckw")
	private String ckw;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "oldseal")
	private String oldseal;
	
	@Column(name = "currentseal")
	private String currentseal;
	
	@Column(name = "meterremarks")
	private String meterremarks;
	
	@Column(name = "otherremarks")
	private String otherremarks;
	
	
	
	@Column(name = "photoid")
	private String photoid;
	
	@Column(name = "mrname")
	private String mrname;
	
	@Column(name = "servertomobiledate")
	private String servertomobiledate;
	
	@Column(name = "syncstatus")
	private String syncstatus;
	
	@Column(name = "devicefirmwareversion")
	private String devicefirmwareversion;
	
	@Column(name = "submitdatetimestamp")
	private String submitdatetimestamp;
	
	@Column(name = "submitstatus")
	private String submitstatus;
	
	@Column(name = "extra1")
	private String extra1;
	
	@Column(name = "extra2")
	private String extra2;
	
	@Column(name = "extra3")
	private String extra3;
	
	@Column(name = "extra4")
	private String extra4;
	
	@Column(name = "extra5")
	private String extra5;
	
	@Column(name = "sbmno")
	private String sbmno;
	
	@Column(name = "industrytype")
	private String industryType;
	
	@Column(name = "demandtype")
	private String demandType;
	
	
	
	
	

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getDemandType() {
		return demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public String getConsumerid() {
		return consumerid;
	}

	public void setConsumerid(String consumerid) {
		this.consumerid = consumerid;
	}

	public String getConsumername() {
		return consumername;
	}

	public void setConsumername(String consumername) {
		this.consumername = consumername;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCmrid() {
		return cmrid;
	}

	public void setCmrid(String cmrid) {
		this.cmrid = cmrid;
	}

	public String getBillmonth() {
		return billmonth;
	}

	public void setBillmonth(String billmonth) {
		this.billmonth = billmonth;
	}

	public String getPkwh() {
		return pkwh;
	}

	public void setPkwh(String pkwh) {
		this.pkwh = pkwh;
	}

	public String getPkvah() {
		return pkvah;
	}

	public void setPkvah(String pkvah) {
		this.pkvah = pkvah;
	}

	public String getPkva() {
		return pkva;
	}

	public void setPkva(String pkva) {
		this.pkva = pkva;
	}

	public String getPkw() {
		return pkw;
	}

	public void setPkw(String pkw) {
		this.pkw = pkw;
	}

	public String getPpf() {
		return ppf;
	}

	public void setPpf(String ppf) {
		this.ppf = ppf;
	}

	public String getCkwh() {
		return ckwh;
	}

	public void setCkwh(String ckwh) {
		this.ckwh = ckwh;
	}

	public String getCkvah() {
		return ckvah;
	}

	public void setCkvah(String ckvah) {
		this.ckvah = ckvah;
	}

	public String getCkva() {
		return ckva;
	}

	public void setCkva(String ckva) {
		this.ckva = ckva;
	}

	public String getCkw() {
		return ckw;
	}

	public void setCkw(String ckw) {
		this.ckw = ckw;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getOldseal() {
		return oldseal;
	}

	public void setOldseal(String oldseal) {
		this.oldseal = oldseal;
	}

	public String getCurrentseal() {
		return currentseal;
	}

	public void setCurrentseal(String currentseal) {
		this.currentseal = currentseal;
	}

	public String getMeterremarks() {
		return meterremarks;
	}

	public void setMeterremarks(String meterremarks) {
		this.meterremarks = meterremarks;
	}

	public String getOtherremarks() {
		return otherremarks;
	}

	public void setOtherremarks(String otherremarks) {
		this.otherremarks = otherremarks;
	}

	public String getPhotoid() {
		return photoid;
	}

	public void setPhotoid(String photoid) {
		this.photoid = photoid;
	}

	public String getMrname() {
		return mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	public String getServertomobiledate() {
		return servertomobiledate;
	}

	public void setServertomobiledate(String servertomobiledate) {
		this.servertomobiledate = servertomobiledate;
	}

	public String getSyncstatus() {
		return syncstatus;
	}

	public void setSyncstatus(String syncstatus) {
		this.syncstatus = syncstatus;
	}

	public String getDevicefirmwareversion() {
		return devicefirmwareversion;
	}

	public void setDevicefirmwareversion(String devicefirmwareversion) {
		this.devicefirmwareversion = devicefirmwareversion;
	}

	public String getSubmitdatetimestamp() {
		return submitdatetimestamp;
	}

	public void setSubmitdatetimestamp(String submitdatetimestamp) {
		this.submitdatetimestamp = submitdatetimestamp;
	}

	public String getSubmitstatus() {
		return submitstatus;
	}

	public void setSubmitstatus(String submitstatus) {
		this.submitstatus = submitstatus;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public String getExtra3() {
		return extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}

	public String getExtra4() {
		return extra4;
	}

	public void setExtra4(String extra4) {
		this.extra4 = extra4;
	}

	public String getExtra5() {
		return extra5;
	}

	public void setExtra5(String extra5) {
		this.extra5 = extra5;
	}

	public String getSbmno() {
		return sbmno;
	}

	public void setSbmno(String sbmno) {
		this.sbmno = sbmno;
	}
	
	
	
	
	
	
	

}
