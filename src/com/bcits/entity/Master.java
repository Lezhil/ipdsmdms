package com.bcits.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="MASTER" , schema="meter_data")
@NamedQueries({
	@NamedQuery(name="Master.FindTotalConsumerCount",query="SELECT COUNT(m.accno) AS CNT FROM Master m " ),
	
	@NamedQuery(name = "Master.updateConnectionDetails", query = "UPDATE Master m  SET m.name=:name,m.address1=:address,m.phoneno=:phoneNo,m.phoneno2=:phoneNo2,m.contractdemand=:cd,m.sanload=:sanLoad,m.remarks=:remarks,m.industrytype=:industryType,m.supplytype=:supplyType,m.supplyvoltage=:supplyvoltage,m.kworhp=:kwHp,m.consumerstatus=:cStatus,m.tariffcode=:tariff,m.tadesc=:taDesc,m.mrname=:mrname,m.tn=:tn,m.mnp=:mnp,m.kno=:kno,m.sdoname=:sdoname,m.division=:division,m.circle=:circle WHERE m.accno =:accno"),
	
	@NamedQuery(name="Master.UpdateOldAcc",query="UPDATE Master m SET m.accno=:accno,m.oldaccno=:oldaccno,m.sdocode=:sdocode,m.circle=:circle,m.division=:division,m.sdoname=:sdoname WHERE m.accno=:oldaccno"),
	@NamedQuery(name="Master.FindSDOName",query="SELECT m.sdoname FROM Master m WHERE m.accno=:accno"),
	@NamedQuery(name="Master.FindMrName",query="SELECT m.mrname FROM Master m WHERE m.accno=:accno "),
	//@NamedQuery(name="Master.FindMrNameByMtrNo",query="SELECT DISTINCT(m.mrname) FROM Master m,MeterMaster mm WHERE m.accno=mm.accno and m.accno=:accno"),
	@NamedQuery(name="Master.FindTrridcode",query="SELECT m.tariffcode FROM Master m WHERE m.accno=:accno"),
	@NamedQuery(name="Master.updateMrname",query="UPDATE Master m SET m.mrname=:mrname WHERE m.accno=:accno"),
	@NamedQuery(name="Master.checkAccnExists",query="SELECT COUNT(*) FROM Master m WHERE m.accno=:accno"),

	@NamedQuery(name="Master.findTotalInst",query="SELECT COUNT(*) FROM Master m WHERE m.consumerstatus LIKE 'R' AND m.accno in(SELECT MM.accno FROM MeterMaster MM WHERE MM.rdngmonth=:rdngmonth)"),
	// Added by shivanand
	@NamedQuery(name="Master.updatePhoneno",query="UPDATE Master m SET m.phoneno=:phoneno,m.industrytype=:industrytype WHERE m.accno=:accno"),

	@NamedQuery(name="Master.getAllMnp",query="SELECT DISTINCT(m.mnp) from Master m where m.mnp is not null AND MNP NOT IN('0') ORDER BY m.mnp"),
	//@NamedQuery(name="Master.FindAll",query="SELECT DISTINCT m.mrname FROM Master m WHERE m.mrname is NOT NULL ORDER BY m.mrname"),
	
	@NamedQuery(name="Master.FindAll",query="SELECT DISTINCT m.mrname FROM Master m WHERE m.mrname NOT IN ('0','NA','na')and m.consumerstatus LIKE 'R' and m.mrname is NOT NULL ORDER BY m.mrname"),
	//mrname respect to  sdocode
	@NamedQuery(name="Master.FindSdoCode",query="SELECT DISTINCT m.subdiv FROM Master m  ORDER BY m.subdiv "),
	
	@NamedQuery(name="Master.FindTadesc",query="SELECT DISTINCT m.tadesc FROM Master m WHERE m.tadesc is NOT NULL"),
	
	@NamedQuery(name="Master.FindMrNameOnSdoTde",query="SELECT DISTINCT m.mrname FROM Master m WHERE m.consumerstatus LIKE 'R' and m.subdiv=:sdocode and m.mrname is NOT NULL ORDER BY m.mrname"),
	
	@NamedQuery(name="Master.FindTadescCode",query="SELECT DISTINCT m.tadesc FROM Master m WHERE m.subdiv=:sdocode and  m.tadesc  is NOT NULL"),
	//data export
	@NamedQuery(name="Master.FindAllTadescCode",query="SELECT DISTINCT m.tadesc FROM Master m WHERE m.sdocode LIKE '%' and   m.tadesc is NOT NULL"),
	
	@NamedQuery(name="Master.FindMrNameForAllSDO",query="SELECT DISTINCT m.mrname FROM Master m WHERE  m.mrname is NOT NULL ORDER BY m.mrname"),
	
	@NamedQuery(name="Master.FindMrNameForAllTadesc",query="SELECT DISTINCT m.mrname FROM Master m WHERE m.subdiv=:sdocode  and  m.mrname is NOT NULL ORDER BY m.mrname"),
	
	@NamedQuery(name="Master.FindMrNameForAllST",query="SELECT DISTINCT m.mrname FROM Master m WHERE m.mrname NOT IN ('0','NA','na') and   m.mrname is NOT NULL ORDER BY m.mrname"),
	
	@NamedQuery(name="Master.FindAllCategory",query="SELECT DISTINCT m.tadesc FROM Master m WHERE   m.consumerstatus  LIKE 'R'  ORDER BY m.tadesc "),
	
	@NamedQuery(name="Master.getCount",query="SELECT count(*) FROM Master m WHERE   m.accno  LIKE :accno"),
	
	@NamedQuery(name="Master.getMrnameByDIV",query="SELECT DISTINCT m.mrname FROM Master m where m.division=:division AND m.consumerstatus  LIKE 'R' and m.mrname NOT IN ('0','NA','na') AND m.mrname is NOT NULL ORDER BY m.mrname"),

	@NamedQuery(name="Master.getMrnameByCIR",query="SELECT DISTINCT m.mrname FROM Master m where m.circle=:circle AND m.consumerstatus  LIKE 'R' and m.mrname NOT IN ('0','NA','na') AND m.mrname is NOT NULL ORDER BY m.mrname"),
	
	@NamedQuery(name="Master.getDistALLMNP",query="SELECT DISTINCT m.mnp FROM Master m where m.mnp is NOT NULL ORDER BY m.mnp"),

   @NamedQuery(name="Master.findSdoCodeBasedonCirle",query="SELECT DISTINCT m.sdocode FROM Master m where m.circle=:circle AND m.mrname=:mrname ORDER BY m.sdocode"),
   
   @NamedQuery(name="Master.findMrNameBasedonCirleSdoCode",query="SELECT DISTINCT m.mrname FROM Master m where m.circle=:circle  AND m.consumerstatus  LIKE 'R' ORDER BY m.mrname"),

   @NamedQuery(name="Master.findFirstMrName",query="SELECT DISTINCT m.mrname FROM Master m where m.circle=:circle  AND m.consumerstatus  LIKE 'R' AND m.mrname is NOT NULL ORDER BY m.mrname"),
   @NamedQuery(name="Master.findSecondSdoCodesByCircle",query="SELECT DISTINCT m.sdocode FROM Master m where m.circle=:circle  ORDER BY m.sdocode"),
   

   @NamedQuery(name="Master.findSecondMrName",query="SELECT DISTINCT m.mrname FROM Master m where m.circle=:circle and m.sdocode=:sdocode AND m.consumerstatus  LIKE 'R' AND m.mrname is NOT NULL ORDER BY m.mrname"),
  
   @NamedQuery(name="Master.FindAllTadescByMrname",query="SELECT DISTINCT m.tadesc FROM Master m WHERE m.circle=:circle and m.sdocode=:sdocode and m.mrname=:mrname and  m.tadesc is NOT NULL"),
	
   

   @NamedQuery(name="Master.getDivisionByCIR",query="SELECT DISTINCT m.division FROM Master m where m.circle=:circle AND m.consumerstatus  LIKE 'R'  AND m.division is NOT NULL ORDER BY m.division"),
   
   @NamedQuery(name="Master.getmnpByCIR",query="SELECT DISTINCT m.mnp FROM Master m where m.circle=:circle AND m.consumerstatus  LIKE 'R'  AND m.mnp is NOT NULL ORDER BY m.mnp"),
   
   @NamedQuery(name="Master.getTadescforMrWise",query="SELECT DISTINCT m.tadesc FROM Master m where  m.tadesc is NOT NULL ORDER BY m.tadesc"),

   @NamedQuery(name="Master.getCircleForMrWiseTotal",query="SELECT DISTINCT(m.circle) from Master m where m.circle is not null"),
   @NamedQuery(name="Master.getDivisionBycircle",query="SELECT DISTINCT m.division FROM Master m where m.circle=:circle AND m.division is NOT NULL ORDER BY m.division"),
   
   @NamedQuery(name="Master.getAllMnpOnSdoName",query="SELECT DISTINCT(m.mnp) from Master m where m.mnp is not null AND MNP NOT IN('0','NA') and sdoname like :sdoname and circle like :circle  ORDER BY m.mnp"),

   @NamedQuery(name="Master.getSDONameByCir",query="SELECT DISTINCT m.subdiv FROM Master m where m.circle=:circle  and m.subdiv is NOT NULL ORDER BY m.subdiv"),

   @NamedQuery(name="Master.findMrNamesBySdoNames",query="SELECT DISTINCT m.mrname FROM Master m where m.subdiv LIKE :sdoname  and m.mrname is NOT NULL ORDER BY m.mrname"),
   @NamedQuery(name = "Master.findSubdByCircle", query ="SELECT DISTINCT(m.subdiv) From Master m where m.circle LIKE :circle and m.division LIKE :division  and m.division IS NOT NULL and m.subdiv IS NOT NULL GROUP BY m.subdiv ORDER BY m.subdiv"),
   @NamedQuery(name="Master.getAllData",query="SELECT m FROM Master m where m.accno LIKE :accno "),
   @NamedQuery(name="Master.getAllDataMeter",query="SELECT m FROM Master m"),
   @NamedQuery(name="Master.getAllDataMeters",query="SELECT m FROM Master m where m.circle LIKE :circle and m.division LIKE :division and m.subdiv LIKE :sdoname"),
   @NamedQuery(name="Master.Findsanload",query="SELECT m.sanload from Master m where m.accno=:accno"),

   @NamedQuery(name="Master.getDistinctCircle",query="SELECT DISTINCT(m.circle) from Master m WHERE m.circle is not null ORDER BY m.circle"),



   @NamedQuery(name="Master.Findindustrytype",query="SELECT m.industrytype from Master m where m.accno=:accno"),
  @NamedQuery(name="Master.UpdateAMRDetails",query="UPDATE Master m SET m.accno=:accno,m.zone=:zone,m.circle=:circle,m.division=:division,m.sdoname=:subdivision,m.address1=:address ,"
					+ "m.status=:status,m.contractdemand=:cd,m.sanload=:sanload,m.supplytype=:supplytype,m.supplyvoltage=:suuplyVoltage,m.phoneno2=:mobileno, "
					+ "m.industrytype=:industryType,m.tariffcode=:tarrifcode,m.consumerstatus=:consumerStatus,m.remarks=:remarks WHERE m.accno=:accno"),
		
})

public class Master implements java.io.Serializable
{
	private Integer sdocode;
	//private String binderno;
	private String accno;
	private String customerref;
	private String areacode;
	private String name;
	private String address1;
	private String address2;
	private String address3;
	private String landmark;
	//private String gpslong;
	//private String gpslat;
	private String phoneno;
	private String faxmo;
	private String emailid;
	private String feedercode;
	private String tccode;
	private String cin;
	private String consumerstatus;
	private Date conndate;
	private Date reconndate;
	private String tariffcode;
	private String kworhp;
	private Double sanload;
	
	

	private Double conload;
	private Double contractdemand;
	private Date disscondate;
	private String ctpt;
	private String capstat;
	private Integer transrent;
	private String transowner;
	private Double avgpf;
	//private String billingagencycode;
	//private String metermake;
	private String industrytype;
	private String supplytype;
	private String mrcode;
	private Byte rdngday;
	//private String meterno;
	private String supplyvoltage;
	//private String tod;
	//private String dlms;
	private String intervalperiod;
	//private String phase;
	private String username;
	private Date datestamp;
	private String remarks;
	//private Long consumerid;
	//private Short cycCd;
	//private String cstsCd;
	private Short mnfCd;
	//private Short mf;
	private String sdoname;
	private String meterclass;
	//private String ptp;
	//private String pts;
	//private String ctp;
	//private String cts;
	private Double oldsanload;
	private Double oldcd;
	private String oldtariffcode;
	private String oldaccno;
	//private Double cd5;
	private String tadesc;
	//private Integer dec13;
	private Integer mm;
	private String mrname;
	private String tn;
	//private String opname;
	private BigDecimal phoneno2;
	
	@Column(name="DIVISION")
	private String division;
	
	@Column(name="CIRCLE")
	private String circle;
	
	@Column(name="SUBDIV")
	private String subdiv;
	
	
	@Column(name="MNP")
	private String mnp; 
	
	@Column(name="DISCOM")
	private String discom; 
	
	public String getDiscom() {
		return discom;
	}
	@Column(name="ZONE")
	private String zone;
	
	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="STATUS")
	private String status;

	public void setDiscom(String discom) {
		this.discom = discom;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	@Column(name = "KNO", length = 13)
	private String kno;
	
	public Master()
	{
		
	}
	
	@Column(name = "SDOCODE", precision = 7, scale = 0)
	public Integer getSdocode() {
		return this.sdocode;
	}

	public void setSdocode(Integer sdocode) {
		this.sdocode = sdocode;
	}

	/*@Column(name = "BINDERNO", length = 4)
	public String getBinderno() {
		return this.binderno;
	}

	public void setBinderno(String binderno) {
		this.binderno = binderno;
	}*/

	@Id
	@Column(name = "ACCNO", length = 13)
	public String getAccno() {
		return this.accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	@Column(name = "CUSTOMERREF", length = 12)
	public String getCustomerref() {
		return this.customerref;
	}

	public void setCustomerref(String customerref) {
		this.customerref = customerref;
	}

	@Column(name = "AREACODE", length = 1)
	public String getAreacode() {
		return this.areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ADDRESS1", length = 900)
	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "ADDRESS2", length = 100)
	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "ADDRESS3", length = 100)
	public String getAddress3() {
		return this.address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	@Column(name = "LANDMARK", length = 250)
	public String getLandmark() {
		return this.landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	/*@Column(name = "GPSLONG", length = 100)
	public String getGpslong() {
		return this.gpslong;
	}

	public void setGpslong(String gpslong) {
		this.gpslong = gpslong;
	}

	@Column(name = "GPSLAT", length = 100)
	public String getGpslat() {
		return this.gpslat;
	}

	public void setGpslat(String gpslat) {
		this.gpslat = gpslat;
	}
*/
	@Column(name = "PHONENO", length = 25)
	public String getPhoneno() {
		return this.phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	@Column(name = "FAXMO", length = 50)
	public String getFaxmo() {
		return this.faxmo;
	}

	public void setFaxmo(String faxmo) {
		this.faxmo = faxmo;
	}

	@Column(name = "EMAILID", length = 50)
	public String getEmailid() {
		return this.emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	@Column(name = "FEEDERCODE", length = 15)
	public String getFeedercode() {
		return this.feedercode;
	}

	public void setFeedercode(String feedercode) {
		this.feedercode = feedercode;
	}

	@Column(name = "TCCODE", length = 15)
	public String getTccode() {
		return this.tccode;
	}

	public void setTccode(String tccode) {
		this.tccode = tccode;
	}

	@Column(name = "CIN", length = 25)
	public String getCin() {
		return this.cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	@Column(name = "CONSUMERSTATUS", length = 1)
	public String getConsumerstatus() {
		return this.consumerstatus;
	}

	public void setConsumerstatus(String consumerstatus) {
		this.consumerstatus = consumerstatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONNDATE", length = 7)
	public Date getConndate() {
		return this.conndate;
	}

	public void setConndate(Date conndate) {
		this.conndate = conndate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RECONNDATE", length = 7)
	public Date getReconndate() {
		return this.reconndate;
	}

	public void setReconndate(Date reconndate) {
		this.reconndate = reconndate;
	}

	@Column(name = "TARIFFCODE", length = 10)
	public String getTariffcode() {
		return this.tariffcode;
	}

	public void setTariffcode(String tariffcode) {
		this.tariffcode = tariffcode;
	}

	@Column(name = "KWORHP", length = 10)
	public String getKworhp() {
		return this.kworhp;
	}

	public void setKworhp(String kworhp) {
		this.kworhp = kworhp;
	}

	@Column(name = "SANLOAD", precision = 8, scale = 3)
	public Double getSanload() {
		return this.sanload;
	}

	public void setSanload(Double sanload) {
		this.sanload = sanload;
	}

	@Column(name = "CONLOAD", precision = 8, scale = 3)
	public Double getConload() {
		return this.conload;
	}

	public void setConload(Double conload) {
		this.conload = conload;
	}

	@Column(name = "CONTRACTDEMAND", precision = 8, scale = 3)
	public Double getContractdemand() {
		return this.contractdemand;
	}

	public void setContractdemand(Double contractdemand) {
		this.contractdemand = contractdemand;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DISSCONDATE", length = 7)
	public Date getDisscondate() {
		return this.disscondate;
	}

	public void setDisscondate(Date disscondate) {
		this.disscondate = disscondate;
	}

	@Column(name = "CTPT", length = 5)
	public String getCtpt() {
		return this.ctpt;
	}

	public void setCtpt(String ctpt) {
		this.ctpt = ctpt;
	}

	@Column(name = "CAPSTAT", length = 4)
	public String getCapstat() {
		return this.capstat;
	}

	public void setCapstat(String capstat) {
		this.capstat = capstat;
	}

	@Column(name = "TRANSRENT", precision = 8, scale = 0)
	public Integer getTransrent() {
		return this.transrent;
	}

	public void setTransrent(Integer transrent) {
		this.transrent = transrent;
	}

	@Column(name = "TRANSOWNER", length = 4)
	public String getTransowner() {
		return this.transowner;
	}

	public void setTransowner(String transowner) {
		this.transowner = transowner;
	}

	@Column(name = "AVGPF", precision = 10)
	public Double getAvgpf() {
		return this.avgpf;
	}

	public void setAvgpf(Double avgpf) {
		this.avgpf = avgpf;
	}

	/*@Column(name = "BILLINGAGENCYCODE", length = 5)
	public String getBillingagencycode() {
		return this.billingagencycode;
	}

	public void setBillingagencycode(String billingagencycode) {
		this.billingagencycode = billingagencycode;
	}

	@Column(name = "METERMAKE", length = 25)
	public String getMetermake() {
		return this.metermake;
	}

	public void setMetermake(String metermake) {
		this.metermake = metermake;
	}*/

	@Column(name = "INDUSTRYTYPE", length = 50)
	public String getIndustrytype() {
		return this.industrytype;
	}

	public void setIndustrytype(String industrytype) {
		this.industrytype = industrytype;
	}

	@Column(name = "SUPPLYTYPE", length = 3)
	public String getSupplytype() {
		return this.supplytype;
	}

	public void setSupplytype(String supplytype) {
		this.supplytype = supplytype;
	}

	@Column(name = "MRCODE", length = 15)
	public String getMrcode() {
		return this.mrcode;
	}

	public void setMrcode(String mrcode) {
		this.mrcode = mrcode;
	}

	@Column(name = "RDNGDAY", precision = 2, scale = 0)
	public Byte getRdngday() {
		return this.rdngday;
	}

	public void setRdngday(Byte rdngday) {
		this.rdngday = rdngday;
	}

	/*@Column(name = "METERNO", length = 15)
	public String getMeterno() {
		return this.meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}*/

	@Column(name = "SUPPLYVOLTAGE", length = 10)
	public String getSupplyvoltage() {
		return this.supplyvoltage;
	}

	public void setSupplyvoltage(String supplyvoltage) {
		this.supplyvoltage = supplyvoltage;
	}

	/*@Column(name = "TOD", length = 1)
	public String getTod() {
		return this.tod;
	}

	public void setTod(String tod) {
		this.tod = tod;
	}*/

	/*@Column(name = "DLMS", length = 1)
	public String getDlms() {
		return this.dlms;
	}

	public void setDlms(String dlms) {
		this.dlms = dlms;
	}*/

	@Column(name = "INTERVALPERIOD", length = 5)
	public String getIntervalperiod() {
		return this.intervalperiod;
	}

	public void setIntervalperiod(String intervalperiod) {
		this.intervalperiod = intervalperiod;
	}

	/*@Column(name = "PHASE", length = 25)
	public String getPhase() {
		return this.phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}*/

	@Column(name = "USERNAME", length = 25)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATESTAMP", length = 7)
	public Date getDatestamp() {
		return this.datestamp;
	}

	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/*@Column(name = "CONSUMERID", precision = 10, scale = 0)
	public Long getConsumerid() {
		return this.consumerid;
	}

	public void setConsumerid(Long consumerid) {
		this.consumerid = consumerid;
	}

	@Column(name = "CYC_CD", precision = 4, scale = 0)
	public Short getCycCd() {
		return this.cycCd;
	}

	public void setCycCd(Short cycCd) {
		this.cycCd = cycCd;
	}

	@Column(name = "CSTS_CD", length = 10)
	public String getCstsCd() {
		return this.cstsCd;
	}

	public void setCstsCd(String cstsCd) {
		this.cstsCd = cstsCd;
	}*/

	@Column(name = "MNF_CD", precision = 4, scale = 0)
	public Short getMnfCd() {
		return this.mnfCd;
	}

	public void setMnfCd(Short mnfCd) {
		this.mnfCd = mnfCd;
	}

	/*@Column(name = "MF", precision = 4, scale = 0)
	public Short getMf() {
		return this.mf;
	}

	public void setMf(Short mf) {
		this.mf = mf;
	}*/

	@Column(name = "SDONAME", length = 50)
	public String getSdoname() {
		return this.sdoname;
	}

	public void setSdoname(String sdoname) {
		this.sdoname = sdoname;
	}

	@Column(name = "METERCLASS", length = 50)
	public String getMeterclass() {
		return this.meterclass;
	}

	public void setMeterclass(String meterclass) {
		this.meterclass = meterclass;
	}

	/*@Column(name = "PTP", length = 15)
	public String getPtp() {
		return this.ptp;
	}

	public void setPtp(String ptp) {
		this.ptp = ptp;
	}

	@Column(name = "PTS", length = 15)
	public String getPts() {
		return this.pts;
	}

	public void setPts(String pts) {
		this.pts = pts;
	}

	@Column(name = "CTP", length = 15)
	public String getCtp() {
		return this.ctp;
	}

	public void setCtp(String ctp) {
		this.ctp = ctp;
	}

	@Column(name = "CTS", length = 15)
	public String getCts() {
		return this.cts;
	}

	public void setCts(String cts) {
		this.cts = cts;
	}*/

	@Column(name = "OLDSANLOAD", precision = 8, scale = 3)
	public Double getOldsanload() {
		return this.oldsanload;
	}

	public void setOldsanload(Double oldsanload) {
		this.oldsanload = oldsanload;
	}

	@Column(name = "OLDCD", precision = 8, scale = 3)
	public Double getOldcd() {
		return this.oldcd;
	}

	public void setOldcd(Double oldcd) {
		this.oldcd = oldcd;
	}

	@Column(name = "OLDTARIFFCODE", length = 10)
	public String getOldtariffcode() {
		return this.oldtariffcode;
	}

	public void setOldtariffcode(String oldtariffcode) {
		this.oldtariffcode = oldtariffcode;
	}

	@Column(name = "OLDACCNO", length = 13)
	public String getOldaccno() {
		return this.oldaccno;
	}

	public void setOldaccno(String oldaccno) {
		this.oldaccno = oldaccno;
	}

	/*@Column(name = "CD5", precision = 15, scale = 3)
	public Double getCd5() {
		return this.cd5;
	}

	public void setCd5(Double cd5) {
		this.cd5 = cd5;
	}*/

	@Column(name = "TADESC", length = 15)
	public String getTadesc() {
		return this.tadesc;
	}

	public void setTadesc(String tadesc) {
		this.tadesc = tadesc;
	}

	/*@Column(name = "DEC13", precision = 1, scale = 0)
	public Integer getDec13() {
		return this.dec13;
	}

	public void setDec13(Integer dec13) {
		this.dec13 = dec13;
	}
*/
	@Column(name = "MM", precision = 1, scale = 0)
	public Integer getMm() {
		return this.mm;
	}

	public void setMm(Integer mm) {
		this.mm = mm;
	}

	@Column(name = "MRNAME", length = 30)
	public String getMrname() {
		return this.mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	@Column(name = "TN", length = 20)
	public String getTn() {
		return this.tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	/*@Column(name = "OPNAME", length = 50)
	public String getOpname() {
		return this.opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}*/

	@Column(name = "PHONENO2", precision = 25, scale = 0)
	public BigDecimal getPhoneno2() {
		return this.phoneno2;
	}

	public void setPhoneno2(BigDecimal phoneno2) {
		this.phoneno2 = phoneno2;
	}

	public String getMnp()
	{
		return mnp;
	}

	public void setMnp(String mnp)
	{
		this.mnp = mnp;
	}

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
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
	
	
}
