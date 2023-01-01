package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="indstructuremas",schema="meter_data")
public class IndStructureMasEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "strcode")
	private Integer strcode;
	@Column(name = "fcode")
	private Integer fcode;
	@Column(name = "sscode")
	private Integer sscode;
	@Column(name = "strname")
	private String strname;
	@Column(name = "locno")
	private Integer locno;
	@Column(name = "afss1")
	private Integer afss1;
	@Column(name = "affcode1")
	private Integer affcode1;
	@Column(name = "afss2")
	private Integer afss2;
	@Column(name = "affcode2")
	private Integer affcode2;
	@Column(name = "afss3")
	private Integer afss3;
	@Column(name = "affcode3")
	private Integer affcode3;
	@Column(name = "afss4")
	private Integer afss4;
	@Column(name = "affcode4")
	private Integer affcode4;
	@Column(name = "aecode")
	private String aecode;
	@Column(name = "inchargepostcode")
	private String inchargepostcode;
	@Column(name = "add1")
	private String add1;
	@Column(name = "add2")
	private String add2;
	@Column(name = "place")
	private String place;
	@Column(name = "aestrcode")
	private Integer aestrcode;
	@Column(name = "vcode")
	private Integer vcode;
	@Column(name = "crby")
	private String crby;
	@Column(name = "load")
	private Double load;
	@Column(name = "dtkva")
	private Double dtkva;
	@Column(name = "aeno")
	private Integer aeno;
	@Column(name = "aecodedummy")
	private String aecodedummy;
	@Column(name = "ip")
	private String ip;
	@Column(name = "entrytime")
	private String entrytime;
	@Column(name = "entrydt")
	private Timestamp entrydt;
	@Column(name = "ccoden")
	private Integer ccoden;
	@Column(name = "ccode")
	private Integer ccode;
	@Column(name = "sub_fcode")
	private Integer sub_fcode;
	@Column(name = "spurcode")
	private Integer spurcode;
	@Column(name = "dt_cap_no")
	private Integer dt_cap_no;
	@Column(name = "dt_load_no")
	private Integer dt_load_no;
	@Column(name = "villcode_no")
	private Integer villcode_no;
	@Column(name = "ssfdrstrcode")
	private Integer ssfdrstrcode;
	@Column(name = "cptag")
	private String cptag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStrcode() {
		return strcode;
	}

	public void setStrcode(Integer strcode) {
		this.strcode = strcode;
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

	public String getStrname() {
		return strname;
	}

	public void setStrname(String strname) {
		this.strname = strname;
	}

	public Integer getLocno() {
		return locno;
	}

	public void setLocno(Integer locno) {
		this.locno = locno;
	}

	public Integer getAfss1() {
		return afss1;
	}

	public void setAfss1(Integer afss1) {
		this.afss1 = afss1;
	}

	public Integer getAffcode1() {
		return affcode1;
	}

	public void setAffcode1(Integer affcode1) {
		this.affcode1 = affcode1;
	}

	public Integer getAfss2() {
		return afss2;
	}

	public void setAfss2(Integer afss2) {
		this.afss2 = afss2;
	}

	public Integer getAffcode2() {
		return affcode2;
	}

	public void setAffcode2(Integer affcode2) {
		this.affcode2 = affcode2;
	}

	public Integer getAfss3() {
		return afss3;
	}

	public void setAfss3(Integer afss3) {
		this.afss3 = afss3;
	}

	public Integer getAffcode3() {
		return affcode3;
	}

	public void setAffcode3(Integer affcode3) {
		this.affcode3 = affcode3;
	}

	public Integer getAfss4() {
		return afss4;
	}

	public void setAfss4(Integer afss4) {
		this.afss4 = afss4;
	}

	public Integer getAffcode4() {
		return affcode4;
	}

	public void setAffcode4(Integer affcode4) {
		this.affcode4 = affcode4;
	}

	public String getAecode() {
		return aecode;
	}

	public void setAecode(String aecode) {
		this.aecode = aecode;
	}

	public String getInchargepostcode() {
		return inchargepostcode;
	}

	public void setInchargepostcode(String inchargepostcode) {
		this.inchargepostcode = inchargepostcode;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getAestrcode() {
		return aestrcode;
	}

	public void setAestrcode(Integer aestrcode) {
		this.aestrcode = aestrcode;
	}


	public Integer getVcode() {
		return vcode;
	}

	public void setVcode(Integer vcode) {
		this.vcode = vcode;
	}

	public String getCrby() {
		return crby;
	}

	public void setCrby(String crby) {
		this.crby = crby;
	}

	public Double getLoad() {
		return load;
	}

	public void setLoad(Double load) {
		this.load = load;
	}

	public Double getDtkva() {
		return dtkva;
	}

	public void setDtkva(Double dtkva) {
		this.dtkva = dtkva;
	}

	public Integer getAeno() {
		return aeno;
	}

	public void setAeno(Integer aeno) {
		this.aeno = aeno;
	}

	public String getAecodedummy() {
		return aecodedummy;
	}

	public void setAecodedummy(String aecodedummy) {
		this.aecodedummy = aecodedummy;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(String entrytime) {
		this.entrytime = entrytime;
	}

	public Timestamp getEntrydt() {
		return entrydt;
	}

	public void setEntrydt(Timestamp entrydt) {
		this.entrydt = entrydt;
	}

	public Integer getCcoden() {
		return ccoden;
	}

	public void setCcoden(Integer ccoden) {
		this.ccoden = ccoden;
	}

	public Integer getCcode() {
		return ccode;
	}

	public void setCcode(Integer ccode) {
		this.ccode = ccode;
	}

	public Integer getSub_fcode() {
		return sub_fcode;
	}

	public void setSub_fcode(Integer sub_fcode) {
		this.sub_fcode = sub_fcode;
	}

	public Integer getSpurcode() {
		return spurcode;
	}

	public void setSpurcode(Integer spurcode) {
		this.spurcode = spurcode;
	}

	public Integer getDt_cap_no() {
		return dt_cap_no;
	}

	public void setDt_cap_no(Integer dt_cap_no) {
		this.dt_cap_no = dt_cap_no;
	}

	public Integer getDt_load_no() {
		return dt_load_no;
	}

	public void setDt_load_no(Integer dt_load_no) {
		this.dt_load_no = dt_load_no;
	}

	public Integer getVillcode_no() {
		return villcode_no;
	}

	public void setVillcode_no(Integer villcode_no) {
		this.villcode_no = villcode_no;
	}

	public Integer getSsfdrstrcode() {
		return ssfdrstrcode;
	}

	public void setSsfdrstrcode(Integer ssfdrstrcode) {
		this.ssfdrstrcode = ssfdrstrcode;
	}

	public String getCptag() {
		return cptag;
	}

	public void setCptag(String cptag) {
		this.cptag = cptag;
	}

}
