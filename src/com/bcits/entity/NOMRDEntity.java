package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="NOTMRD")
@NamedQueries({
	@NamedQuery(name="NOMRDEntity.GetAllData",query="SELECT m FROM NOMRDEntity m")
})
/*Author: Shivananad Doddamani*/
public class NOMRDEntity 
{
	@Id
	@Column(name="ID", length = 10)
	private int id;
	
	@Column(name="RDNGMONTH")
	private int rdngmonth;
	
	@Column(name="MRNAME")
    private String mrname;
	
	@Column(name="TADESC")
    private String tadesc ;
	
	
	@Column(name="ACCNO")
    private String accno;
	
	
	@Column(name="METRNO")
    private String meterno;
	
	
	
	@Column(name="NAME")
    private String name;
	
	
	@Column(name="READINGREMARK")
    private String readingremark;
	
	
	@Column(name="MRINO")
    private String mrino;
	
	
	
	@Column(name="READINGDATE")
    private String readingdate;
	

	
	
	@Column(name="REAMRK")
    private String remark;
	
	
	
	@Column(name="SBMTOSERVERDATE")
    private Date sbmtoserverdate;
	
	@Column(name="VERSION")
    private String version;
	
	@Column(name="REASON")
    private String reason;
	
	@Column(name="EXTRA1")
    private String extra1;
	
	
	@Column(name="EXTRA2")
    private String extra2;
	
	
	@Column(name="EXTRA3")
    private String extra3;
	
	@Column(name="EXTRA4")
    private String extra4;
	
	
	@Column(name="EXTRA5")
    private String extra5;
	
	
	
	

	public String getExtra3() {
		return extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRdngmonth() {
		return rdngmonth;
	}

	public void setRdngmonth(int rdngmonth) {
		this.rdngmonth = rdngmonth;
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

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReadingremark() {
		return readingremark;
	}

	public void setReadingremark(String readingremark) {
		this.readingremark = readingremark;
	}

	public String getMrino() {
		return mrino;
	}

	public void setMrino(String mrino) {
		this.mrino = mrino;
	}


	
	
	
	
	public String getReadingdate() {
		return readingdate;
	}

	public void setReadingdate(String readingdate) {
		this.readingdate = readingdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSbmtoserverdate() {
		return sbmtoserverdate;
	}

	public void setSbmtoserverdate(Date sbmtoserverdate) {
		this.sbmtoserverdate = sbmtoserverdate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
 
}
