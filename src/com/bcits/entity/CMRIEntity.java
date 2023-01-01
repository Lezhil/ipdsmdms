package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CMRI")
@NamedQueries({
	@NamedQuery(name="CMRIEntity.getCMRIIssueDetails",query="SELECT c FROM CMRIEntity c WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.billMonth=:billMonth"),
	@NamedQuery(name="CMRIEntity.getCMRIDataForRecieve",query="SELECT c FROM CMRIEntity c WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.mriNo=:mriNo"),
	@NamedQuery(name="CMRIEntity.FindCMRIIssues",query="SELECT c FROM CMRIEntity c WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.mriNo=:mriNo AND c.billMonth=:billMonth"),
	@NamedQuery(name="CMRIEntity.getCMRIIssueDButnotRecievecDetails",query="SELECT c FROM CMRIEntity c WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.billMonth=:billMonth AND TO_CHAR(c.rDate,'dd-mm-yyyy') IS NULL"),
	@NamedQuery(name="CMRIEntity.getCMRIIssueDAndRecievecDetails",query="SELECT c FROM CMRIEntity c WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.billMonth=:billMonth AND TO_CHAR(c.rDate,'dd-mm-yyyy') IS NOT NULL"),
	@NamedQuery(name="CMRIEntity.getCMRIRecievecButNotDumpedDetails",query="SELECT c FROM CMRIEntity c WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.billMonth=:billMonth AND TO_CHAR(c.rDate,'dd-mm-yyyy') IS NOT NULL AND (c.dumped IS NULL OR c.dumped LIKE 'NO')"),
	@NamedQuery(name="CMRIEntity.getCMRIRecievecAndDumpedDetails",query="SELECT c FROM CMRIEntity c WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.billMonth=:billMonth AND TO_CHAR(c.rDate,'dd-mm-yyyy') IS NOT NULL AND c.dumped LIKE 'YES'"),
	@NamedQuery(name="CMRIEntity.getCMRIPreparedDetails",query="SELECT c FROM CMRIEntity c WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.billMonth=:billMonth AND TO_CHAR(c.rDate,'dd-mm-yyyy') IS NOT NULL AND c.prepared LIKE 'YES'"),
	@NamedQuery(name="CMRIEntity.updateIssueDetails",query="UPDATE CMRIEntity c SET c.iDate=:iDate,c.subDiv=:subDiv,c.name=:name,c.accessories=:accessories WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.billMonth=:billMonth AND c.mriNo=:mriNo"),
	@NamedQuery(name="CMRIEntity.findAllData1",query="SELECT m FROM CMRIEntity m WHERE m.rDate=:rDate"),
	@NamedQuery(name="CMRIEntity.findAllData",query="SELECT m FROM CMRIEntity m WHERE m.name=:name"),
	@NamedQuery(name="CMRIEntity.FindCmri",query="SELECT m FROM CMRIEntity m ORDER  BY m.slNO"),
	@NamedQuery(name="CMRIEntity.updateRecieveDetails",query="UPDATE CMRIEntity c SET c.rDate=:rDate,c.mrSecure=:mrSecure,c.mrLnt=:mrLnt,c.mrGenusc=:mrGenusc,c.mrHplmacs=:mrHplmacs,c.mrlng=:mrlng,c.mipCmri=:mipCmri,c.mipMannual=:mipMannual,c.sipCmri=:sipCmri,c.sipMannual=:sipMannual,c.gxtFiles=:gxtFiles,c.mrRemark=:mrRemark,c.totalMrd=:totalMrd,c.mrGenusd=:mrGenusd,c.mrHpld=:mrHpld WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.billMonth=:billMonth AND c.mriNo=:mriNo"),
	@NamedQuery(name="CMRIEntity.getCMRIDifferenceDetails",query="SELECT c FROM CMRIEntity c WHERE TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate AND c.billMonth=:billMonth AND TO_CHAR(c.rDate,'dd-mm-yyyy') IS NOT NULL AND c.dumped LIKE 'YES' AND  (c.mrSecure<>c.secure OR c.mrLnt<>c.lnt OR c.mrGenusc<>c.gCommon OR c.mrGenusd<>c.gDlms OR c.mrHplmacs<>c.hplm OR c.mrHpld<>c.hpld OR c.mrlng<>c.lng)")	,
	@NamedQuery(name="CMRIEntity.getCMRIBasedOnMrName",query="SELECT c FROM CMRIEntity c WHERE c.name=:name AND TO_CHAR(c.rdgDate,'dd-mm-yyyy')=:rdgDate")

})
public class CMRIEntity 
{
	
 @Id	
 @SequenceGenerator(name="cmriId",sequenceName="CMRI_ID")
 @GeneratedValue(generator="cmriId")
 @Column(name="ID")
 private long id;
 
 @Column(name="SLNO")
 private long slNO;
 
 @Column(name="IDATE") 
 private Date iDate;
 
 @Column(name="SUBDIV") 
 private String subDiv;
 
 @Column(name="NAME")
 private String name;
 
 @Column(name="MRINO")
 private String  mriNo;
 
 @Column(name="MRSECURE")
 private long mrSecure;
 
 @Column(name="MRLNT")
 private long mrLnt;
 
 @Column(name="MRGENUSC")
 private long mrGenusc;
 
 @Column(name="MRHPLMACS")
 private long mrHplmacs;
 
 @Column(name="MRLNG")
 private long mrlng;
 
 @Column(name="RDATE")
 private Date rDate;
 
 @Column(name="SECURE")
 private long secure;
 
 @Column(name="LNT")
 private long lnt;
 
 @Column(name="GCOMMON")
 private long gCommon;
 
 @Column(name="GDLMS")
 private long gDlms;
 
 @Column(name="HPLM")
 private long hplm;
 
 @Column(name="HPLD")
 private long hpld;
 
 @Column(name="LNG")
 private long lng;
 
 @Column(name="DUMPED")
 private String dumped;
 
 @Column(name="PREPARED")
 private String prepared;
 
 @Column(name="PCNO")
 private long pcNo;
 
 @Column(name="DUMPEDBY")
 private String dumpedBy;
 
 @Column(name="REMARK")
 private String remark;
 
 @Column(name="ACCESSORIES")
 private String accessories; 
 
 @Column(name="USERNAME")
 private String userName; 
 
 @Column(name="MIPCMRI")
 private long mipCmri;
 
 @Column(name="MIPMANUAL")
 private long mipMannual;
 
 @Column(name="SIPCMRI")
 private long sipCmri;
 
 @Column(name="SIPMANUAL")
 private long sipMannual;

 @Column(name="BILLMONTH")
 private int billMonth;
 
 @Column(name="READINGDATE")
 private Date rdgDate;
 
 @Column(name="MRGENUSD")
 private long mrGenusd;
 
 @Column(name="MRHPLD")
 private long mrHpld;
 
 @Column(name="GXTFILES")
 private String gxtFiles;
 
 @Column(name="MRREMARK")
 private String mrRemark;
 
 @Column(name="TOTALMRD")
 private long totalMrd;
 
 @Column(name="MRDDUMPED")
 private long mrdDumped;

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public long getSlNO() {
	return slNO;
}

public void setSlNO(long slNO) {
	this.slNO = slNO;
}

public Date getiDate() {
	return iDate;
}

public void setiDate(Date iDate) {
	this.iDate = iDate;
}

public String getSubDiv() {
	return subDiv;
}

public void setSubDiv(String subDiv) {
	this.subDiv = subDiv;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getMriNo() {
	return mriNo;
}

public void setMriNo(String mriNo) {
	this.mriNo = mriNo;
}

public long getMrSecure() {
	return mrSecure;
}

public void setMrSecure(long mrSecure) {
	this.mrSecure = mrSecure;
}

public long getMrLnt() {
	return mrLnt;
}

public void setMrLnt(long mrLnt) {
	this.mrLnt = mrLnt;
}

public long getMrGenusc() {
	return mrGenusc;
}

public void setMrGenusc(long mrGenusc) {
	this.mrGenusc = mrGenusc;
}

public long getMrHplmacs() {
	return mrHplmacs;
}

public void setMrHplmacs(long mrHplmacs) {
	this.mrHplmacs = mrHplmacs;
}

public long getMrlng() {
	return mrlng;
}

public void setMrlng(long mrlng) {
	this.mrlng = mrlng;
}

public Date getrDate() {
	return rDate;
}

public void setrDate(Date rDate) {
	this.rDate = rDate;
}

public long getSecure() {
	return secure;
}

public void setSecure(long secure) {
	this.secure = secure;
}

public long getLnt() {
	return lnt;
}

public void setLnt(long lnt) {
	this.lnt = lnt;
}

public long getgCommon() {
	return gCommon;
}

public void setgCommon(long gCommon) {
	this.gCommon = gCommon;
}

public long getgDlms() {
	return gDlms;
}

public void setgDlms(long gDlms) {
	this.gDlms = gDlms;
}

public long getHplm() {
	return hplm;
}

public void setHplm(long hplm) {
	this.hplm = hplm;
}

public long getHpld() {
	return hpld;
}

public void setHpld(long hpld) {
	this.hpld = hpld;
}

public long getLng() {
	return lng;
}

public void setLng(long lng) {
	this.lng = lng;
}

public String getDumped() {
	return dumped;
}

public void setDumped(String dumped) {
	this.dumped = dumped;
}

public String getPrepared() {
	return prepared;
}

public void setPrepared(String prepared) {
	this.prepared = prepared;
}

public long getPcNo() {
	return pcNo;
}

public void setPcNo(long pcNo) {
	this.pcNo = pcNo;
}

public String getDumpedBy() {
	return dumpedBy;
}

public void setDumpedBy(String dumpedBy) {
	this.dumpedBy = dumpedBy;
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

public String getAccessories() {
	return accessories;
}

public void setAccessories(String accessories) {
	this.accessories = accessories;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public long getMipCmri() {
	return mipCmri;
}

public void setMipCmri(long mipCmri) {
	this.mipCmri = mipCmri;
}

public long getMipMannual() {
	return mipMannual;
}

public void setMipMannual(long mipMannual) {
	this.mipMannual = mipMannual;
}

public long getSipCmri() {
	return sipCmri;
}

public void setSipCmri(long sipCmri) {
	this.sipCmri = sipCmri;
}

public long getSipMannual() {
	return sipMannual;
}

public void setSipMannual(long sipMannual) {
	this.sipMannual = sipMannual;
}

public int getBillMonth() {
	return billMonth;
}

public void setBillMonth(int billMonth) {
	this.billMonth = billMonth;
}

public Date getRdgDate() {
	return rdgDate;
}

public void setRdgDate(Date rdgDate) {
	this.rdgDate = rdgDate;
}

public long getMrGenusd() {
	return mrGenusd;
}

public void setMrGenusd(long mrGenusd) {
	this.mrGenusd = mrGenusd;
}

public long getMrHpld() {
	return mrHpld;
}

public void setMrHpld(long mrHpld) {
	this.mrHpld = mrHpld;
}

public String getGxtFiles() {
	return gxtFiles;
}

public void setGxtFiles(String gxtFiles) {
	this.gxtFiles = gxtFiles;
}

public String getMrRemark() {
	return mrRemark;
}

public void setMrRemark(String mrRemark) {
	this.mrRemark = mrRemark;
}

public long getTotalMrd() {
	return totalMrd;
}

public void setTotalMrd(long totalMrd) {
	this.totalMrd = totalMrd;
}

public long getMrdDumped() {
	return mrdDumped;
}

public void setMrdDumped(long mrdDumped) {
	this.mrdDumped = mrdDumped;
}
 
}
