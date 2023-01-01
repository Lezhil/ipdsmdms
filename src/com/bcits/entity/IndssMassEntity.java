package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="indssmas",schema="meter_data")
public class IndssMassEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")	
	private	int	id;	
	@Column(name = "sscode")	
	private	Integer	sscode;
	@Column(name = "ccode")	
	private	Integer	ccode;
	@Column(name = "rcode")	
	private	Integer	rcode;
	@Column(name = "ssname")	
	private	String	ssname;
	@Column(name = "sstype")	
	private	String	sstype;
	@Column(name = "hvkv")	
	private	Integer	hvkv;
	@Column(name = "hvfss1")	
	private	Integer	hvfss1;
	@Column(name = "hvfss2")	
	private	Integer	hvfss2;
	@Column(name = "hvfss3")	
	private	Integer	hvfss3;
	@Column(name = "hvfss4")	
	private	Integer	hvfss4;
	@Column(name = "hvfss5")	
	private	Integer	hvfss5;
	@Column(name = "hvfss1fedcode")	
	private	Integer	hvfss1fedcode;
	@Column(name = "hvfss2fedcode")	
	private	Integer	hvfss2fedcode;
	@Column(name = "hvfss3fedcode")	
	private	Integer	hvfss3fedcode;
	@Column(name = "hvfss4fedcode")	
	private	Integer	hvfss4fedcode;
	@Column(name = "sstag")	
	private	String	sstag;
	@Column(name = "lvkv1")	
	private	Integer	lvkv1;
	@Column(name = "lvkv2")	
	private	Integer	lvkv2;
	@Column(name = "lvkv3")	
	private	Integer	lvkv3;
	@Column(name = "lvkv4")	
	private	Integer	lvkv4;
	@Column(name = "ssadd1")	
	private	String	ssadd1;
	@Column(name = "ssadd2")	
	private	String	ssadd2;
	@Column(name = "ssadd3")	
	private	String	ssadd3;
	@Column(name = "ssph")	
	private	String	ssph;
	@Column(name = "ssip")	
	private	String	ssip;
	@Column(name = "nolvfed")	
	private	Integer	nolvfed;
	@Column(name = "fromdt")	
	private	Timestamp	fromdt;
	@Column(name = "todt")	
	private	Timestamp	todt;
	@Column(name = "vcode")	
	private	Integer	vcode;
	@Column(name = "oldsscode")	
	private	Integer	oldsscode;
	@Column(name = "pincode")	
	private	Integer	pincode;
	@Column(name = "occode")	
	private	Integer	occode;
	@Column(name = "ccodef1")	
	private	Integer	ccodef1;
	@Column(name = "ccodef2")	
	private	Integer	ccodef2;
	@Column(name = "ccodef3")	
	private	Integer	ccodef3;
	@Column(name = "ccodef4")	
	private	Integer	ccodef4;
	@Column(name = "villcode_no")	
	private	Integer	villcode_no;
	@Column(name = "blockcode_no")	
	private	Integer	blockcode_no;
	@Column(name = "opnccode")	
	private	Integer	opnccode;
	@Column(name = "cptag")	
	private	String	cptag;
	@Column(name = "entby")	
	private	String	entby;
	@Column(name = "enton")	
	private	Timestamp	enton;
	@Column(name = "peak_sofar")	
	private	Integer	peak_sofar;
	@Column(name = "peaksofar_dt")	
	private	Timestamp	peaksofar_dt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getSscode() {
		return sscode;
	}
	public void setSscode(Integer sscode) {
		this.sscode = sscode;
	}
	public Integer getCcode() {
		return ccode;
	}
	public void setCcode(Integer ccode) {
		this.ccode = ccode;
	}
	public Integer getRcode() {
		return rcode;
	}
	public void setRcode(Integer rcode) {
		this.rcode = rcode;
	}
	public String getSsname() {
		return ssname;
	}
	public void setSsname(String ssname) {
		this.ssname = ssname;
	}
	public String getSstype() {
		return sstype;
	}
	public void setSstype(String sstype) {
		this.sstype = sstype;
	}
	public Integer getHvkv() {
		return hvkv;
	}
	public void setHvkv(Integer hvkv) {
		this.hvkv = hvkv;
	}
	public Integer getHvfss1() {
		return hvfss1;
	}
	public void setHvfss1(Integer hvfss1) {
		this.hvfss1 = hvfss1;
	}
	public Integer getHvfss2() {
		return hvfss2;
	}
	public void setHvfss2(Integer hvfss2) {
		this.hvfss2 = hvfss2;
	}
	public Integer getHvfss3() {
		return hvfss3;
	}
	public void setHvfss3(Integer hvfss3) {
		this.hvfss3 = hvfss3;
	}
	public Integer getHvfss4() {
		return hvfss4;
	}
	public void setHvfss4(Integer hvfss4) {
		this.hvfss4 = hvfss4;
	}
	public Integer getHvfss5() {
		return hvfss5;
	}
	public void setHvfss5(Integer hvfss5) {
		this.hvfss5 = hvfss5;
	}
	public Integer getHvfss1fedcode() {
		return hvfss1fedcode;
	}
	public void setHvfss1fedcode(Integer hvfss1fedcode) {
		this.hvfss1fedcode = hvfss1fedcode;
	}
	public Integer getHvfss2fedcode() {
		return hvfss2fedcode;
	}
	public void setHvfss2fedcode(Integer hvfss2fedcode) {
		this.hvfss2fedcode = hvfss2fedcode;
	}
	public Integer getHvfss3fedcode() {
		return hvfss3fedcode;
	}
	public void setHvfss3fedcode(Integer hvfss3fedcode) {
		this.hvfss3fedcode = hvfss3fedcode;
	}
	public Integer getHvfss4fedcode() {
		return hvfss4fedcode;
	}
	public void setHvfss4fedcode(Integer hvfss4fedcode) {
		this.hvfss4fedcode = hvfss4fedcode;
	}
	public String getSstag() {
		return sstag;
	}
	public void setSstag(String sstag) {
		this.sstag = sstag;
	}
	public Integer getLvkv1() {
		return lvkv1;
	}
	public void setLvkv1(Integer lvkv1) {
		this.lvkv1 = lvkv1;
	}
	public Integer getLvkv2() {
		return lvkv2;
	}
	public void setLvkv2(Integer lvkv2) {
		this.lvkv2 = lvkv2;
	}
	public Integer getLvkv3() {
		return lvkv3;
	}
	public void setLvkv3(Integer lvkv3) {
		this.lvkv3 = lvkv3;
	}
	public Integer getLvkv4() {
		return lvkv4;
	}
	public void setLvkv4(Integer lvkv4) {
		this.lvkv4 = lvkv4;
	}
	public String getSsadd1() {
		return ssadd1;
	}
	public void setSsadd1(String ssadd1) {
		this.ssadd1 = ssadd1;
	}
	public String getSsadd2() {
		return ssadd2;
	}
	public void setSsadd2(String ssadd2) {
		this.ssadd2 = ssadd2;
	}
	public String getSsadd3() {
		return ssadd3;
	}
	public void setSsadd3(String ssadd3) {
		this.ssadd3 = ssadd3;
	}
	public String getSsph() {
		return ssph;
	}
	public void setSsph(String ssph) {
		this.ssph = ssph;
	}
	public String getSsip() {
		return ssip;
	}
	public void setSsip(String ssip) {
		this.ssip = ssip;
	}
	public Integer getNolvfed() {
		return nolvfed;
	}
	public void setNolvfed(Integer nolvfed) {
		this.nolvfed = nolvfed;
	}
	public Timestamp getFromdt() {
		return fromdt;
	}
	public void setFromdt(Timestamp fromdt) {
		this.fromdt = fromdt;
	}
	public Timestamp getTodt() {
		return todt;
	}
	public void setTodt(Timestamp todt) {
		this.todt = todt;
	}
	public Integer getVcode() {
		return vcode;
	}
	public void setVcode(Integer vcode) {
		this.vcode = vcode;
	}
	public Integer getOldsscode() {
		return oldsscode;
	}
	public void setOldsscode(Integer oldsscode) {
		this.oldsscode = oldsscode;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public Integer getOccode() {
		return occode;
	}
	public void setOccode(Integer occode) {
		this.occode = occode;
	}
	public Integer getCcodef1() {
		return ccodef1;
	}
	public void setCcodef1(Integer ccodef1) {
		this.ccodef1 = ccodef1;
	}
	public Integer getCcodef2() {
		return ccodef2;
	}
	public void setCcodef2(Integer ccodef2) {
		this.ccodef2 = ccodef2;
	}
	public Integer getCcodef3() {
		return ccodef3;
	}
	public void setCcodef3(Integer ccodef3) {
		this.ccodef3 = ccodef3;
	}
	public Integer getCcodef4() {
		return ccodef4;
	}
	public void setCcodef4(Integer ccodef4) {
		this.ccodef4 = ccodef4;
	}
	public Integer getVillcode_no() {
		return villcode_no;
	}
	public void setVillcode_no(Integer villcode_no) {
		this.villcode_no = villcode_no;
	}
	public Integer getBlockcode_no() {
		return blockcode_no;
	}
	public void setBlockcode_no(Integer blockcode_no) {
		this.blockcode_no = blockcode_no;
	}
	public Integer getOpnccode() {
		return opnccode;
	}
	public void setOpnccode(Integer opnccode) {
		this.opnccode = opnccode;
	}
	public String getCptag() {
		return cptag;
	}
	public void setCptag(String cptag) {
		this.cptag = cptag;
	}
	public String getEntby() {
		return entby;
	}
	public void setEntby(String entby) {
		this.entby = entby;
	}
	public Timestamp getEnton() {
		return enton;
	}
	public void setEnton(Timestamp enton) {
		this.enton = enton;
	}
	public Integer getPeak_sofar() {
		return peak_sofar;
	}
	public void setPeak_sofar(Integer peak_sofar) {
		this.peak_sofar = peak_sofar;
	}
	public Timestamp getPeaksofar_dt() {
		return peaksofar_dt;
	}
	public void setPeaksofar_dt(Timestamp peaksofar_dt) {
		this.peaksofar_dt = peaksofar_dt;
	}
	
	

}
