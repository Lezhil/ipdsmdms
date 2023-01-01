package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="indfeedermas",schema="meter_data")
public class IndFeederMasEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private	int	id;
	@Column(name="fcode")
	private	Integer	fcode;
	@Column(name="sscode")
	private	Integer	sscode;
	@Column(name="fname")
	private	String	fname;
	@Column(name="fvkv")
	private	Integer	fvkv;
	@Column(name="lengthmf")
	private	Integer	lengthmf;
	@Column(name="nospur")
	private	Integer	nospur;
	@Column(name="nodt")
	private	Integer	nodt;
	@Column(name="crby")
	private	String	crby;
	@Column(name="ccode")
	private	Integer	ccode;
	@Column(name="ftype")
	private	String	ftype;
	@Column(name="fmaecode")
	private	String	fmaecode;
	@Column(name="mfcode")
	private	Integer	mfcode;
	@Column(name="fmae2")
	private	String	fmae2;
	@Column(name="fmae3")
	private	String	fmae3;
	@Column(name="fmae4")
	private	String	fmae4;
	@Column(name="ip")
	private	String	ip;
	@Column(name="detime")
	private	Timestamp	detime;
	@Column(name="moditime")
	private	Timestamp	moditime;
	@Column(name="occode")
	private	Integer	occode;
	@Column(name="no_sub_feeder")
	private	Integer	no_sub_feeder;
	@Column(name="peakkva")
	private	Double	peakkva;
	@Column(name="peakkvadt")
	private	Timestamp	peakkvadt;
	@Column(name="peakamps")
	private	Double	peakamps;
	@Column(name="peakampsdt")
	private	Timestamp	peakampsdt;
	@Column(name="dtloadkva")
	private	Double	dtloadkva;
	@Column(name="htloadkva")
	private	Double	htloadkva;
	@Column(name="pkaddlloadkva")
	private	Double	pkaddlloadkva;
	@Column(name="pkaddlloadamps")
	private	Double	pkaddlloadamps;
	@Column(name="energyac_tag")
	private	String	energyac_tag;
	@Column(name="editenergyac_tag")
	private	String	editenergyac_tag;
	@Column(name="length1")
	private	Double	length1;
	@Column(name="size1")
	private	String	size1;
	@Column(name="length2")
	private	Double	length2;
	@Column(name="size2")
	private	String	size2;
	@Column(name="length3")
	private	Double	length3;
	@Column(name="size3")
	private	String	size3;
	@Column(name="length4")
	private	Double	length4;
	@Column(name="size4")
	private	String	size4;
	@Column(name="maxct")
	private	Integer	maxct;
	@Column(name="in_out")
	private	String	in_out;
	@Column(name="ssfcode")
	private	Integer	ssfcode;
	@Column(name="cptag")
	private	String	cptag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getFcode() {
		return fcode;
	}
	public void setFcode(Integer fcode) {
		this.fcode = fcode;
	}
	public Integer getSscode() {
		return sscode;
	}
	public void setSscode(Integer sscode) {
		this.sscode = sscode;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public Integer getFvkv() {
		return fvkv;
	}
	public void setFvkv(Integer fvkv) {
		this.fvkv = fvkv;
	}
	public Integer getLengthmf() {
		return lengthmf;
	}
	public void setLengthmf(Integer lengthmf) {
		this.lengthmf = lengthmf;
	}
	public Integer getNospur() {
		return nospur;
	}
	public void setNospur(Integer nospur) {
		this.nospur = nospur;
	}
	public Integer getNodt() {
		return nodt;
	}
	public void setNodt(Integer nodt) {
		this.nodt = nodt;
	}
	public String getCrby() {
		return crby;
	}
	public void setCrby(String crby) {
		this.crby = crby;
	}
	public Integer getCcode() {
		return ccode;
	}
	public void setCcode(Integer ccode) {
		this.ccode = ccode;
	}
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public String getFmaecode() {
		return fmaecode;
	}
	public void setFmaecode(String fmaecode) {
		this.fmaecode = fmaecode;
	}
	public Integer getMfcode() {
		return mfcode;
	}
	public void setMfcode(Integer mfcode) {
		this.mfcode = mfcode;
	}
	public String getFmae2() {
		return fmae2;
	}
	public void setFmae2(String fmae2) {
		this.fmae2 = fmae2;
	}
	public String getFmae3() {
		return fmae3;
	}
	public void setFmae3(String fmae3) {
		this.fmae3 = fmae3;
	}
	public String getFmae4() {
		return fmae4;
	}
	public void setFmae4(String fmae4) {
		this.fmae4 = fmae4;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Timestamp getDetime() {
		return detime;
	}
	public void setDetime(Timestamp detime) {
		this.detime = detime;
	}
	public Timestamp getModitime() {
		return moditime;
	}
	public void setModitime(Timestamp moditime) {
		this.moditime = moditime;
	}
	public Integer getOccode() {
		return occode;
	}
	public void setOccode(Integer occode) {
		this.occode = occode;
	}
	public Integer getNo_sub_feeder() {
		return no_sub_feeder;
	}
	public void setNo_sub_feeder(Integer no_sub_feeder) {
		this.no_sub_feeder = no_sub_feeder;
	}
	public Double getPeakkva() {
		return peakkva;
	}
	public void setPeakkva(Double peakkva) {
		this.peakkva = peakkva;
	}
	public Timestamp getPeakkvadt() {
		return peakkvadt;
	}
	public void setPeakkvadt(Timestamp peakkvadt) {
		this.peakkvadt = peakkvadt;
	}
	public Double getPeakamps() {
		return peakamps;
	}
	public void setPeakamps(Double peakamps) {
		this.peakamps = peakamps;
	}
	public Timestamp getPeakampsdt() {
		return peakampsdt;
	}
	public void setPeakampsdt(Timestamp peakampsdt) {
		this.peakampsdt = peakampsdt;
	}
	public Double getDtloadkva() {
		return dtloadkva;
	}
	public void setDtloadkva(Double dtloadkva) {
		this.dtloadkva = dtloadkva;
	}
	public Double getHtloadkva() {
		return htloadkva;
	}
	public void setHtloadkva(Double htloadkva) {
		this.htloadkva = htloadkva;
	}
	public Double getPkaddlloadkva() {
		return pkaddlloadkva;
	}
	public void setPkaddlloadkva(Double pkaddlloadkva) {
		this.pkaddlloadkva = pkaddlloadkva;
	}
	public Double getPkaddlloadamps() {
		return pkaddlloadamps;
	}
	public void setPkaddlloadamps(Double pkaddlloadamps) {
		this.pkaddlloadamps = pkaddlloadamps;
	}
	public String getEnergyac_tag() {
		return energyac_tag;
	}
	public void setEnergyac_tag(String energyac_tag) {
		this.energyac_tag = energyac_tag;
	}
	public String getEditenergyac_tag() {
		return editenergyac_tag;
	}
	public void setEditenergyac_tag(String editenergyac_tag) {
		this.editenergyac_tag = editenergyac_tag;
	}
	public Double getLength1() {
		return length1;
	}
	public void setLength1(Double length1) {
		this.length1 = length1;
	}
	public String getSize1() {
		return size1;
	}
	public void setSize1(String size1) {
		this.size1 = size1;
	}
	public Double getLength2() {
		return length2;
	}
	public void setLength2(Double length2) {
		this.length2 = length2;
	}
	public String getSize2() {
		return size2;
	}
	public void setSize2(String size2) {
		this.size2 = size2;
	}
	public Double getLength3() {
		return length3;
	}
	public void setLength3(Double length3) {
		this.length3 = length3;
	}
	public String getSize3() {
		return size3;
	}
	public void setSize3(String size3) {
		this.size3 = size3;
	}
	public Double getLength4() {
		return length4;
	}
	public void setLength4(Double length4) {
		this.length4 = length4;
	}
	public String getSize4() {
		return size4;
	}
	public void setSize4(String size4) {
		this.size4 = size4;
	}
	public Integer getMaxct() {
		return maxct;
	}
	public void setMaxct(Integer maxct) {
		this.maxct = maxct;
	}
	public String getIn_out() {
		return in_out;
	}
	public void setIn_out(String in_out) {
		this.in_out = in_out;
	}
	public Integer getSsfcode() {
		return ssfcode;
	}
	public void setSsfcode(Integer ssfcode) {
		this.ssfcode = ssfcode;
	}
	public String getCptag() {
		return cptag;
	}
	public void setCptag(String cptag) {
		this.cptag = cptag;
	}


}
