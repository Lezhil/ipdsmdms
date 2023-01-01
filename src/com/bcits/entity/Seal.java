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
@Table(name="SEAL")
@NamedQueries({
	@NamedQuery(name="Seal.getMaxSealCardNum",query="SELECT MAX(s.cardSealNo) FROM Seal s"),
	@NamedQuery(name="Seal.getMaxSealCardNum1",query="SELECT MAX(s.cardSealNo1) FROM Seal s"),
	@NamedQuery(name="Seal.singleupdate",query="UPDATE Seal s SET s.mrname=:mrname,s.iDate=:iDate,s.billmonth=:billmonth,s.dvision=:division,s.subDiv=:subDiv,s.issuedBy=:issuedBy,s.cardSealNo1=:cardSealNo1,s.revDate=:recievedDate WHERE s.sealNo LIKE :sealNo AND s.iDate IS NOT NULL"),
    @NamedQuery(name="Seal.checkSealExistOrNot",query="SELECT COUNT(*) FROM Seal s WHERE s.sealNo LIKE :sealNo"),
    @NamedQuery(name="Seal.SealReIssue",query="UPDATE Seal s SET s.mrname=:mrname,s.iDate=:iDate,s.billmonth=:billmonth,s.dvision=:division,s.subDiv=:subDiv,s.issuedBy=:issuedBy WHERE s.cardSealNo1=:cardSealNo1 AND s.cardSealNo1 NOT LIKE '0' AND s.revDate IS NOT NULL AND s.iDate IS NULL"),
    @NamedQuery(name="Seal.GetSealBunches",query="SELECT s.cardSealNo1,COUNT(*) FROM Seal s WHERE s.cardSealNo1 IS NOT NULL AND s.revDate IS NOT NULL AND s.iDate IS NULL GROUP BY s.cardSealNo1 ORDER BY s.cardSealNo1"),
	@NamedQuery(name="Seal.getSealsForMobileMR",query="SELECT s FROM Seal s where s.mrname =:mrname and s.billmonth =:billmonth AND s.remark IS NULL AND s.accNo IS NULL"),
	@NamedQuery(name="Seal.updateSealsStatusMobile",query="UPDATE Seal s SET s.accNo =:accNo, s.meterno=:meterno, s.remark=:remark, s.rmrname=:rmrname WHERE s.sealNo like :sealNo AND s.billmonth=:billmonth"),
	@NamedQuery(name="Seal.updatePendingSealFromMobile",query="UPDATE Seal s SET s.remark=:remark, s.sealNo=:sealNo, s.rmrname=:rmrname WHERE s.id=:id"),
	@NamedQuery(name="Seal.returnPendingSealFromMobile",query="UPDATE Seal s SET s.remark=:remark, s.sealNo=:sealNo, s.rmrname=:rmrname, s.mrname=:mrname, s.iDate=:iDate, s.dvision=:dvision, s.billmonth=:billmonth, s.subDiv=:subDiv, s.issuedBy=:issuedBy  WHERE s.id=:id"),
	@NamedQuery(name="Seal.updateSealsUSEDFromMobile",query="UPDATE Seal s SET s.accNo =:accNo, s.meterno=:meterno, s.remark=:remark, s.rmrname=:rmrname WHERE s.id=:id"),
	@NamedQuery(name="Seal.GetSealBySealNo",query="Select s from Seal s where sealNo=:sealNo"),
	@NamedQuery(name="Seal.GetTotalNoSeal",query="select count(*)  from Seal s where  s.mrname like :MRNAME and s.billmonth =:FromMonth \n" +
			" and s.accNo is  null and (s.remark is null or s.remark like 'RETURN')"),

	@NamedQuery(name="Seal.UpdateSealDataForNxtMnth",query="update  Seal s set s.billmonth=:ToMonth  where s.mrname like :mrname and s.billmonth =:FromMonth \n" +
			"and s.accNo is  null and (s.remark is null or s.remark like 'RETURN')"),
	
	/*@NamedQuery(name="Seal.UpdateSealIssueData",query="update  seal s set s.mrname=:mrname ,s.idate=:idate,s.billmonth=:billmonth, s.issuedBy=:issuedBy,s.cardSealNo=:cardSealNo  where s.sealno between :sealFrom and :sealTo"),*/
	
})
public class Seal
{
	 @Id
	 @SequenceGenerator(name="sealId",sequenceName="SEALID")
	 @GeneratedValue(generator="sealId")
	 @Column(name="ID")
	 private int id;
	 
	 @Column(name="IDNO")
	 private int idNo;
	 
	 @Column(name="SEALNO")
	 private String sealNo;
	 
	 @Column(name="RDATE")
	 private Date rDate;	 
	 
	 @Column(name="CMRI")
	 private String cmri;
	 
	 @Column(name="MRNAME")
	 private String mrname;
	 
	 @Column(name="IDATE")
	 private Date iDate;
	 
	 @Column(name="BILLMONTH")
	 private int billmonth;
	 
	 @Column(name="SEALUSED")
	 private int sealUsed;
	 
	 @Column(name="DIVISON")
	 private String dvision;
	 
	 @Column(name="SUBDIV")
	 private String subDiv;
	 
	 @Column(name="ISSUEDBY")
	 private String issuedBy;
	 
	 @Column(name="MOBILENO")
	 private String mobile;

	 @Column(name="REVDATE")
	 private Date revDate;
	 
	 @Column(name="CARDSLNO")
	 private Long cardSealNo;
	 
	 @Column(name="ACCNO")
	 private String accNo;
	 
	 @Column(name="CARDSLNO1")
	 private long cardSealNo1;
	 

	 @Column(name="METERNO")
	 private String meterno;
	 
	 @Column(name="REMARK")
	 private String remark;
	 
	 @Column(name="RMRNAME")
	 private String rmrname;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

	
	
	
	
	
	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	

	public String getRmrname() {
		return rmrname;
	}

	public void setRmrname(String rmrname) {
		this.rmrname = rmrname;
	}

	public Date getrDate() {
		return rDate;
	}

	public void setrDate(Date rDate) {
		this.rDate = rDate;
	}

	public String getCmri() {
		return cmri;
	}

	public void setCmri(String cmri) {
		this.cmri = cmri;
	}

	public String getMrname() {
		return mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	public Date getiDate() {
		return iDate;
	}

	public void setiDate(Date iDate) {
		this.iDate = iDate;
	}

	public int getBillmonth() {
		return billmonth;
	}

	public void setBillmonth(int billmonth) {
		this.billmonth = billmonth;
	}

	public int getSealUsed() {
		return sealUsed;
	}

	public void setSealUsed(int sealUsed) {
		this.sealUsed = sealUsed;
	}

	public String getDvision() {
		return dvision;
	}

	public void setDvision(String dvision) {
		this.dvision = dvision;
	}

	public String getSubDiv() {
		return subDiv;
	}

	public void setSubDiv(String subDiv) {
		this.subDiv = subDiv;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getIdNo() {
		return idNo;
	}

	public void setIdNo(int idNo) {
		this.idNo = idNo;
	}

	public Date getRevDate() {
		return revDate;
	}

	public void setRevDate(Date revDate) {
		this.revDate = revDate;
	}

	public Long getCardSealNo() {
		return cardSealNo;
	}

	public void setCardSealNo(Long cardSealNo) {
		this.cardSealNo = cardSealNo;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public long getCardSealNo1() {
		return cardSealNo1;
	}

	public void setCardSealNo1(long cardSealNo1) {
		this.cardSealNo1 = cardSealNo1;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}	 
