package com.bcits.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
/**
 * Metermaster entity. @author Manjunath Kotagi
 */

@Entity

@Table(name = "METERMASTER" , schema="meter_data")

@NamedQueries({
	@NamedQuery(name = "MeterMaster.getData", query = "SELECT model FROM MeterMaster model WHERE model.rdngmonth=:month ORDER BY model.id DESC"),
	@NamedQuery(name = "MeterMaster.getDatabasedonmonthmeternumber", query = "SELECT model FROM MeterMaster model WHERE model.metrno=:metrno AND model.rdngmonth<=:rdngmonth ORDER BY model.id DESC"),
	@NamedQuery(name = "MeterMaster.updateMeterMasterData", query = "UPDATE MeterMaster m SET m.currdngkwh = :currdngkwh ,m.currrdngkvah =:currrdngkvah,m.currdngkva = :currdngkva,m.mtrclass=:mtrclass,m.pf=:pf WHERE m.accno=:accno AND m.metrno=:metrno AND m.rdngmonth = :rdngmonth"),
	@NamedQuery(name = "MeterMaster.getMeterMakeWiseData", query = "select distinct m.mtrmake,count(*) as cn from MeterMaster m where m.rdngmonth=:rdngmonth AND m.accno IN (select mm.accno from Master mm where mm.consumerstatus   in('R'))group by m.mtrmake"),
	@NamedQuery(name = "MeterMaster.getMaxMonthYear", query = "select MAX(m.rdngmonth) FROM MeterMaster m"),
	@NamedQuery(name = "MeterMaster.searchByAccNo", query = "select m FROM MeterMaster m WHERE m.accno =:accNo AND m.rdngmonth =:rdgMonth"),
	@NamedQuery(name = "MeterMaster.searchByMtrNo", query = "select m FROM MeterMaster m WHERE m.metrno =:mtrNo AND m.rdngmonth =:rdgMonth"),
	@NamedQuery(name = "MeterMaster.getAllBillingData", query = "select m FROM MeterMaster m WHERE   m.rdngmonth =:rdgMonth and xcurrdngkwh is not null"),
	
	@NamedQuery(name = "MeterMaster.updateConnectionDetails", query = "UPDATE MeterMaster m  SET m.metrno=:meterNo, m.mtrtype=:mtrtype, m.mtrmake=:mtrMake,m.ctrn=:ctrn, m.ctrd=:ctrd,m.mf=:mf,m.prkwh=:initialRdg,m.prevmeterstatus=:pStatus,m.mcst=:cStatus,m.dlms=:dlms WHERE m.accno =:accno AND rdngmonth =:rdngmonth"),
	
	@NamedQuery(name = "MeterMaster.FindAccountNumber", query = "SELECT m.accno FROM MeterMaster m WHERE m.metrno=:metrno AND m.rdngmonth =:rdngmonth"),
	@NamedQuery(name = "MeterMaster.UpdateAccountNumber", query = "UPDATE MeterMaster mm SET mm.accno=:accno,mm.sdocode=:sdocode WHERE mm.metrno=:metrno "),
	
	@NamedQuery(name = "MeterMaster.FindAllData", query = "SELECT m FROM MeterMaster m WHERE m.accno=:accno AND m.metrno=:metrno AND m.rdngmonth=:rdngmonth"),
// old @NamedQuery(name = "MeterMaster.UpdateManualReadingAccount", query = "UPDATE MeterMaster m SET m.prevmeterstatus=:prevmeterstatus,m.readingdate =:readingdate,m.prkva=:prkva,m.prkwh=:prkwh,m.prkvah=:prkvah,m.remark=:remark, m.unitskva=:unitskva,m.unitskvah=:unitskvah,m.unitskwh=:unitskwh,m.xcurrdngkva=:xcurrdngkva,m.xcurrdngkwh=:xcurrdngkwh,m.xcurrrdngkvah=:xcurrrdngkvah,m.newseal=:newseal,m.oldseal=:oldseal,m.xpf=:xpf,m.pf=:pf,m.mrname=:mrname,m.mrino=:mrino,m.mrdstatus=:mrdstatus,m.readingremark=:readingremark,m.currdngkva=:currdngkva,m.currdngkwh=:currdngkwh,m.currrdngkvah=:currrdngkvah,m.dname=:dname WHERE m.accno=:accno AND m.metrno=:metrno AND m.rdngmonth=:rdngmonth"),
	
	@NamedQuery(name = "MeterMaster.UpdateManualReadingAccount", query = "UPDATE MeterMaster m SET m.prevmeterstatus=:prevmeterstatus,m.readingdate =:readingdate,m.prkva=:prkva,m.prkwh=:prkwh,m.prkvah=:prkvah,m.remark=:remark, m.unitskva=:unitskva,m.unitskvah=:unitskvah,m.unitskwh=:unitskwh,m.xcurrdngkva=:xcurrdngkva,m.xcurrdngkwh=:xcurrdngkwh,m.xcurrrdngkvah=:xcurrrdngkvah,m.newseal=:newseal,m.oldseal=:oldseal,m.xpf=:xpf,m.pf=:pf,m.mrname=:mrname,m.mrino=:mrino,m.mrdstatus=:mrdstatus,m.readingremark=:readingremark,m.currdngkva=:currdngkva,m.currdngkwh=:currdngkwh,m.currrdngkvah=:currrdngkvah,m.dname=:dname,m.xmldate=:xmldate,m.t1kwh=:t1kwh,m.t2kwh=:t2kwh,m.t3kwh=:t3kwh,m.t4kwh=:t4kwh,m.t5kwh=:t5kwh,m.t6kwh=:t6kwh,m.t7kwh=:t7kwh,m.t8kwh=:t8kwh,m.t1kvah=:t1kvah,m.t2kvah=:t2kvah,m.t3kvah=:t3kvah,m.t4kvah=:t4kvah,m.t5kvah=:t5kvah,m.t6kvah=:t6kvah,m.t7kvah=:t7kvah,m.t8kvah=:t8kvah WHERE m.accno=:accno AND m.metrno=:metrno AND m.rdngmonth=:rdngmonth"),
	
	@NamedQuery(name = "MeterMaster.getDatabaseMeterMasterData", query ="SELECT  model FROM MeterMaster model WHERE model.accno=:accno  AND model.rdngmonth<=:rdngmonth ORDER BY model.rdngmonth DESC"),
	@NamedQuery(name = "MeterMaster.getDatabaseMeterMasterData1", query ="SELECT  model FROM MeterMaster model WHERE model.metrno=:metrno AND model.rdngmonth<=:rdngmonth ORDER BY model.rdngmonth DESC"),
	@NamedQuery(name=  "MeterMaster.getDatabaseMeterMasterData2",query="SELECT  model FROM MeterMaster model WHERE model.newseal=:newseal ORDER BY model.rdngmonth DESC"),
	@NamedQuery(name = "MeterMaster.FindAllData1", query = "SELECT m FROM MeterMaster m WHERE m.metrno=:metrno AND m.rdngmonth=:rdngmonth"),
	@NamedQuery(name = "MeterMaster.FindAllData2", query = "SELECT m FROM MeterMaster m WHERE m.accno=:accno AND m.rdngmonth=:rdngmonth"),

	@NamedQuery(name = "MeterMaster.getDistinctMeterMasterData", query="SELECT DISTINCT m FROM MeterMaster m WHERE m.accno=:accno AND m.rdngmonth=:rdngmonth"),
	@NamedQuery(name = "MeterMaster.getDistinctMeterMasterData1", query="SELECT  m FROM MeterMaster m WHERE m.metrno=:metrno AND m.rdngmonth=:rdngmonth"),
	@NamedQuery(name = "MeterMaster.FindMeterNumber", query = "SELECT m.metrno FROM MeterMaster m WHERE m.accno=:oldaccno AND m.rdngmonth =:rdngmonth"),	

	@NamedQuery(name = "MeterMaster.checkExist", query = "SELECT m.metrno FROM MeterMaster m  WHERE m.metrno=:metrno"),
	@NamedQuery(name = "MeterMaster.CheckMeterNo", query = "SELECT m FROM MeterMaster m  WHERE m.metrno=:metrno"),
	@NamedQuery(name = "MeterMaster.checkMeterExist", query = "SELECT m.metrno FROM MeterMaster m  WHERE m.metrno=:metrno AND m.accno<>:accno"),
	@NamedQuery(name = "MeterMaster.checkMeterExistOnCurrentMonth", query = "SELECT m.metrno FROM MeterMaster m  WHERE m.metrno=:metrno AND m.accno<>:accno AND m.rdngmonth=:rdngmonth"),


//	@NamedQuery(name = "MeterMaster.getdata1", query ="SELECT b.metrno,a.accno,a.name,a.address1,b.rdngmonth,b.prkwh,b.prkvah,b.prkva,b.oldseal,a.mrname,a.tadesc,a.phoneno,b.mtrmake FROM Master a,MeterMaster b WHERE a.accno=b.accno and b.rdngmonth=:readingMonth AND a.consumerstatus LIKE 'R'  order by a.accno"),

	@NamedQuery(name = "MeterMaster.getdata1", query ="SELECT b.metrno,a.accno,a.name ,a.address1,b.rdngmonth,b.prkwh ,b.prkvah,b.prkva ,b.oldseal,a.mrname,a.tadesc,a.phoneno,b.mtrmake,a.industrytype FROM Master a,MeterMaster b WHERE a.accno=b.accno and b.rdngmonth=:readingMonth AND a.consumerstatus LIKE 'R' AND b.readingdate IS NULL  order by a.accno"),
	 @NamedQuery(name="MeterMaster.getFeedersBasedOnAllZone",query="SELECT  f FROM MeterMaster f"),
	 @NamedQuery(name = "MeterMaster.findSubdByCircleDiv", query ="SELECT DISTINCT(m.subdiv) From MeterMaster m where m.circle LIKE :circle and m.division LIKE :division and m.division IS NOT NULL and m.subdiv IS NOT NULL GROUP BY m.subdiv ORDER BY m.subdiv"),
	//Added by shivanand 
	@NamedQuery(name = "MeterMaster.updateMobileDataToMeterMaster", query = "UPDATE MeterMaster m SET m.currdngkwh = :currdngkwh ,m.mrino =:mrino,m.currrdngkvah =:currrdngkvah,m.currdngkva = :currdngkva,m.pf=:pf, m.newseal = :newseal,m.mrname = :mrname,m.readingremark = :readingremark,m.remark = :remark,m.unitskwh = :unitskwh,m.unitskvah = :unitskvah,m.readingdate = :readingdate,m.datestamp = :datestamp,m.oldseal = :oldseal,m.mm = :mm,m.demandType = :demandType ,m.cmri = :cmri, m.uploadstatus = :uploadstatus WHERE m.accno=:accno AND m.rdngmonth = :rdngmonth"),
	@NamedQuery(name = "MeterMaster.getMFforTheGivenAccnumber", query = "SELECT m FROM MeterMaster m  WHERE m.accno=:accno AND m.rdngmonth = :rdngmonth"),
	@NamedQuery(name = "MeterMaster.checkMeterExists", query = "SELECT COUNT(*) FROM MeterMaster m WHERE m.metrno=:metrno AND m.rdngmonth=:rdngmonth"),
	@NamedQuery(name="MeterMaster.findTotalNOI",query="SELECT COUNT(*) FROM MeterMaster M WHERE M.rdngmonth=:rdngmonth AND M.readingdate IS NOT NULL AND M.accno IN(SELECT MM.accno FROM Master MM WHERE MM.consumerstatus LIKE 'R')"),
	@NamedQuery(name="MeterMaster.findTotalPending",query="SELECT COUNT(*) FROM MeterMaster M WHERE M.rdngmonth=:rdngmonth AND M.readingdate IS NULL AND M.accno IN(SELECT MM.accno FROM Master MM WHERE MM.consumerstatus LIKE 'R')"),
	@NamedQuery(name="MeterMaster.FindAllAccounts",query="SELECT DISTINCT(m.accno) FROM MeterMaster m WHERE m.accno LIKE '%157'"),
//	@NamedQuery(name = "MeterMaster.updateUploadStatus", query = "UPDATE MeterMaster m SET m.uploadstatus = :uploadstatus WHERE M.accno=:consumerid AND M.rdngmonth=:billmonth ")

	//@NamedQuery(name = "MeterMaster.getData", query = "SELECT model FROM MeterMaster model WHERE model.rdngmonth=:month and model.discom=:discom ORDER BY model.id DESC"),
	@NamedQuery(name="MeterMaster.getDatabaseMeterMasterForKno",query="SELECT  model FROM MeterMaster model WHERE model.kno=:kno ORDER BY model.rdngmonth DESC"),

	@NamedQuery(name="MeterMaster.findAccDATA",query="select m FROM MeterMaster m WHERE m.accno =:accno AND m.rdngmonth =:rdngmonth ORDER BY rdngmonth DESC"),
	@NamedQuery(name = "MeterMaster.getDataa", query = "SELECT model FROM MeterMaster model WHERE model.rdngmonth=:month and model.discom=:discom ORDER BY model.id DESC"),

	@NamedQuery(name = "MeterMaster.updateAMRConnection", query = "UPDATE MeterMaster m  SET m.rdngmonth=:Rdngmonth, m.accno=:accno, m.modem_no=:modem_no,m.metrno=:meterno, m.mf=:mf,m.ctrd=:ctrd,"
				+ "m.ctrn=:ctrn,m.prkwh=:prkwh,m.mtrmake=:mtrmake WHERE m.accno =:accno AND rdngmonth =:Rdngmonth"),
	
	
   @NamedQuery(name = "MeterMaster.getSyncDataAMI", query = "SELECT model FROM MeterMaster model WHERE model.rdngmonth=:month and model.rmsflag=:rmsflag ORDER BY model.metrno DESC"),
				
				
})
public class MeterMaster implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	// Fields
	private Long id;
	private Long consumerid;
	private String accno;
	private String metrno;
	private Integer rdngmonth;
	private Integer meterstatus;
	private String prevmeterstatus;
	private Date readingdate;
	private Short mancode;
	private String meterversion;
	private Integer phase;
	private Double ctrn;
	private Double ctrd;
	private String amprating;
	private Double currdngkwh;
	private Double currrdngkvah;
	private Double currdngkva;
	private Byte todzone;
	private Integer mfyear;
	private String mtrclass;
	private String ctptmake;
	private Double mf;
	@Column(name = "SDOCODE" )
	private Integer sdocode;
	public Integer getSdocode() {
		return sdocode;
	}

	public void setSdocode(Integer sdocode) {
		this.sdocode = sdocode;
	}

	private String oldseal;
	/*public CmriNumber getCmriNumber() {
		return cmriNumber;
	}

	public void setCmriNumber(CmriNumber cmriNumber) {
		this.cmriNumber = cmriNumber;
	}*/

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}

	private String newseal;
	private Double prkwh;
	private Double prkvah;
	private Double prkva;
	private Double unitskwh;
	private Double unitskvah;
	private Double unitskva;
	private String mrdstatus;
	private Double pf;
	private Double ppf;
	private String mtrtype;
	private String mrname;
	private String readingremark;
	private String remark;
	private Date datestamp;
	private String oldmeterno;
	private Integer entry;
	private String dname;
	private String ddate;
	private Date xmldate;
	private Short oldmf;
	private Double xcurrdngkwh;
	private Double xcurrrdngkvah;
	private Double xcurrdngkva;
	private Double xpf;
	private String mtrmake;
	private String username;
	private Integer rtc;
	private String mcst;
	private Integer mm;
	private String mrino;
	private Integer mrd;
	private String dlms;

	private Master master;
	private CmriNumber cmriNumber;
	
	@Column(name = "UPLOADSTATUS")
	private Integer uploadstatus;
	
	@Column(name = "DEMANDTYPE")
	private String demandType;

	@Column(name = "CIRCLE")
	private String circle;
	
	@Column(name = "SUBDIV")
	private String subdiv;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "DIVISION")
	private String division;
	
	@Column(name = "CMRI")
	private String cmri;
	
	@Column(name = "HTMANUAL")
	private Integer htmanual;
	
	@Column(name="KWHE")
	private Double kwhe;
	
	@Column(name="KVHE")
	private Double kvhe;
	
	@Column(name="KVAE")
	private Double kvae;
	
	@Column(name="PFE")
	private Double pfe;
	
	@Column(name="DISCOM")
	private String discom;
	
	@Column(name = "T1KWH" , precision = 20, scale = 6)
	private Double t1kwh;

	@Column(name = "T2KWH", precision = 20, scale = 6)
	private Double t2kwh;
	
	@Column(name = "T3KWH" , precision = 20, scale = 6)
	private Double t3kwh;
	
	@Column(name = "T4KWH" , precision = 20, scale = 6)
	private Double t4kwh;
	
	@Column(name = "T5KWH", precision = 20, scale = 6)
	private Double t5kwh;
	
	@Column(name = "T6KWH" , precision = 20, scale = 6)
	private Double t6kwh;
	
	@Column(name = "T7KWH" , precision = 20, scale = 6)
	private Double t7kwh;
	
	@Column(name = "T8KWH" , precision = 20, scale = 6)
	private Double t8kwh;
	
	
	
	@Column(name = "T1KVAH" , precision = 20, scale = 6)
	private Double t1kvah;
	
	@Column(name="RMSFLAG" )
	private String rmsflag;
	
	
	public String getRmsflag() {
		return rmsflag;
	}

	public void setRmsflag(String rmsflag) {
		this.rmsflag = rmsflag;
	}

	public Double getT1kwh() {
		return t1kwh;
	}

	public void setT1kwh(Double t1kwh) {
		this.t1kwh = t1kwh;
	}

	@Column(name = "T2KVAH" , precision = 20, scale = 6)
	private Double t2kvah;
	
	@Column(name = "T3KVAH" , precision = 20, scale = 6)
	private Double t3kvah;
	
	@Column(name = "T4KVAH" , precision = 20, scale = 6)
	private Double t4kvah;
	
	@Column(name = "T5KVAH" , precision = 20, scale = 6)
	private Double t5kvah;
	
	@Column(name = "T6KVAH" , precision = 20, scale = 6)
	private Double t6kvah;
	
	@Column(name = "T7KVAH" , precision = 20, scale = 6)
	private Double t7kvah;
	
	@Column(name = "T8KVAH" , precision = 20, scale = 6)
	private Double t8kvah;
	
	@Column(name = "T1KVAV")
	private Double t1kvav;
	
	@Column(name = "T2KVAV")
	private Double t2kvav;
	
	@Column(name = "T3KVAV")
	private Double t3kvav;
	
	@Column(name = "T4KVAV")
	private Double t4kvav;
	
	@Column(name = "T5KVAV")
	private Double t5kvav;
	
	@Column(name = "T6KVAV")
	private Double t6kvav;
	
	@Column(name = "T7KVAV")
	private Double t7kvav;
	
	@Column(name = "T8KVAV")
	private Double t8kvav;

	
	public Double getT2kwh() {
		return t2kwh;
	}

	public void setT2kwh(Double t2kwh) {
		this.t2kwh = t2kwh;
	}

	public Double getT3kwh() {
		return t3kwh;
	}

	public void setT3kwh(Double t3kwh) {
		this.t3kwh = t3kwh;
	}

	public Double getT4kwh() {
		return t4kwh;
	}

	public void setT4kwh(Double t4kwh) {
		this.t4kwh = t4kwh;
	}

	public Double getT5kwh() {
		return t5kwh;
	}

	public void setT5kwh(Double t5kwh) {
		this.t5kwh = t5kwh;
	}

	public Double getT6kwh() {
		return t6kwh;
	}

	public void setT6kwh(Double t6kwh) {
		this.t6kwh = t6kwh;
	}

	public Double getT7kwh() {
		return t7kwh;
	}

	public void setT7kwh(Double t7kwh) {
		this.t7kwh = t7kwh;
	}

	public Double getT8kwh() {
		return t8kwh;
	}

	public void setT8kwh(Double t8kwh) {
		this.t8kwh = t8kwh;
	}

	public Double getT1kvah() {
		return t1kvah;
	}

	public void setT1kvah(Double t1kvah) {
		this.t1kvah = t1kvah;
	}

	public Double getT2kvah() {
		return t2kvah;
	}

	public void setT2kvah(Double t2kvah) {
		this.t2kvah = t2kvah;
	}

	public Double getT3kvah() {
		return t3kvah;
	}

	public void setT3kvah(Double t3kvah) {
		this.t3kvah = t3kvah;
	}

	public Double getT4kvah() {
		return t4kvah;
	}

	public void setT4kvah(Double t4kvah) {
		this.t4kvah = t4kvah;
	}

	public Double getT5kvah() {
		return t5kvah;
	}

	public void setT5kvah(Double t5kvah) {
		this.t5kvah = t5kvah;
	}

	public Double getT6kvah() {
		return t6kvah;
	}

	public void setT6kvah(Double t6kvah) {
		this.t6kvah = t6kvah;
	}

	public Double getT7kvah() {
		return t7kvah;
	}

	public void setT7kvah(Double t7kvah) {
		this.t7kvah = t7kvah;
	}

	public Double getT8kvah() {
		return t8kvah;
	}

	public void setT8kvah(Double t8kvah) {
		this.t8kvah = t8kvah;
	}

	public Double getT1kvav() {
		return t1kvav;
	}

	public void setT1kvav(Double t1kvav) {
		this.t1kvav = t1kvav;
	}

	public Double getT2kvav() {
		return t2kvav;
	}

	public void setT2kvav(Double t2kvav) {
		this.t2kvav = t2kvav;
	}

	public Double getT3kvav() {
		return t3kvav;
	}

	public void setT3kvav(Double t3kvav) {
		this.t3kvav = t3kvav;
	}

	public Double getT4kvav() {
		return t4kvav;
	}

	public void setT4kvav(Double t4kvav) {
		this.t4kvav = t4kvav;
	}

	public Double getT5kvav() {
		return t5kvav;
	}

	public void setT5kvav(Double t5kvav) {
		this.t5kvav = t5kvav;
	}

	public Double getT6kvav() {
		return t6kvav;
	}

	public void setT6kvav(Double t6kvav) {
		this.t6kvav = t6kvav;
	}

	public Double getT7kvav() {
		return t7kvav;
	}

	public void setT7kvav(Double t7kvav) {
		this.t7kvav = t7kvav;
	}

	public Double getT8kvav() {
		return t8kvav;
	}

	public void setT8kvav(Double t8kvav) {
		this.t8kvav = t8kvav;
	}

	public String getAddress() {
		return address;
	}

	public String getConsumername() {
		return consumername;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setConsumername(String consumername) {
		this.consumername = consumername;
	}

	@Column(name="MODEM_NO")
	private String modem_no;

	@Column(name = "CONSUMERNAME")
	private String consumername;
	public String getModem_no() {
		return modem_no;
	}

	public void setModem_no(String modem_no) {
		this.modem_no = modem_no;
	}

	public Integer getHtmanual() {
		return htmanual;
	}

	public void setHtmanual(Integer htmanual) {
		this.htmanual = htmanual;
	}

	public Double getKwhe() {
		return kwhe;
	}

	public void setKwhe(Double kwhe) {
		this.kwhe = kwhe;
	}

	public Double getKvhe() {
		return kvhe;
	}

	public void setKvhe(Double kvhe) {
		this.kvhe = kvhe;
	}

	public Double getKvae() {
		return kvae;
	}

	public void setKvae(Double kvae) {
		this.kvae = kvae;
	}

	public Double getPfe() {
		return pfe;
	}

	public void setPfe(Double pfe) {
		this.pfe = pfe;
	}

	private String kno;

	// Property accessors

	@Id
	
	/*@SequenceGenerator(name = "METERMASTER_SEQUENCE", sequenceName = "METERMASTER_SEQUENCE")
	@GeneratedValue(generator = "METERMASTER_SEQUENCE")*/
	// @GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name ="ID")

	@GeneratedValue(strategy=GenerationType.IDENTITY)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CONSUMERID", precision = 10, scale = 0)
	public Long getConsumerid() {
		return this.consumerid;
	}

	public void setConsumerid(Long consumerid) {
		this.consumerid = consumerid;
	}	

	@Column(name = "ACCNO", length = 13)	
	public String getAccno() {
		return this.accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	
	
	
	
	
	public String getCmri() {
		return cmri;
	}

	public void setCmri(String cmri) {
		this.cmri = cmri;
	}

	public Integer getUploadstatus() {
		return uploadstatus;
	}

	public void setUploadstatus(Integer uploadstatus) {
		this.uploadstatus = uploadstatus;
	}

	public String getDemandType() {
		return demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	@Column(name = "METRNO", length = 20)
	public String getMetrno() {
		return this.metrno;
	}

	public void setMetrno(String metrno) {
		this.metrno = metrno;
	}

	@Column(name = "RDNGMONTH", nullable = false, precision = 6, scale = 0)
	public Integer getRdngmonth() {
		return this.rdngmonth;
	}

	public void setRdngmonth(Integer rdngmonth) {
		this.rdngmonth = rdngmonth;
	}

	@Column(name = "METERSTATUS", length = 2)
	public Integer getMeterstatus() {
		return meterstatus;
	}

	public void setMeterstatus(Integer meterstatus) {
		this.meterstatus = meterstatus;
	}
	
	@Column(name = "PREVMETERSTATUS", length = 50)
	public String getPrevmeterstatus() {
		return this.prevmeterstatus;
	}


	public void setPrevmeterstatus(String prevmeterstatus) {
		this.prevmeterstatus = prevmeterstatus;
	}

	@Column(name = "READINGDATE", length = 7)
	public Date getReadingdate() {
		return this.readingdate;
	}

	public void setReadingdate(Date readingdate) {
		this.readingdate = readingdate;
	}

	@Column(name = "MANCODE", precision = 3, scale = 0)
	public Short getMancode() {
		return this.mancode;
	}

	public void setMancode(Short mancode) {
		this.mancode = mancode;
	}

	@Column(name = "METERVERSION", length = 10)
	public String getMeterversion() {
		return this.meterversion;
	}

	public void setMeterversion(String meterversion) {
		this.meterversion = meterversion;
	}

	@Column(name = "PHASE", precision = 1, scale = 0)
	public Integer getPhase() {
		return this.phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	@Column(name = "CTRN", precision = 8)
	public Double getCtrn() {
		return this.ctrn;
	}

	public void setCtrn(Double ctrn) {
		this.ctrn = ctrn;
	}

	@Column(name = "CTRD", precision = 8)
	public Double getCtrd() {
		return this.ctrd;
	}

	public void setCtrd(Double ctrd) {
		this.ctrd = ctrd;
	}

	@Column(name = "AMPRATING", length = 12)
	public String getAmprating() {
		return this.amprating;
	}

	public void setAmprating(String amprating) {
		this.amprating = amprating;
	}

	@Column(name = "CURRDNGKWH", precision = 12, scale = 4)
	public Double getCurrdngkwh() {
		return this.currdngkwh;
	}

	public void setCurrdngkwh(Double currdngkwh) {
		this.currdngkwh = currdngkwh;
	}

	@Column(name = "CURRRDNGKVAH", precision = 12, scale = 4)
	public Double getCurrrdngkvah() {
		return this.currrdngkvah;
	}

	public void setCurrrdngkvah(Double currrdngkvah) {
		this.currrdngkvah = currrdngkvah;
	}

	@Column(name = "CURRDNGKVA", precision = 12, scale = 4)
	public Double getCurrdngkva() {
		return this.currdngkva;
	}

	public void setCurrdngkva(Double currdngkva) {
		this.currdngkva = currdngkva;
	}

	@Column(name = "TODZONE", precision = 2, scale = 0)
	public Byte getTodzone() {
		return this.todzone;
	}

	public void setTodzone(Byte todzone) {
		this.todzone = todzone;
	}

	@Column(name = "MFYEAR", precision = 6, scale = 0)
	public Integer getMfyear() {
		return this.mfyear;
	}

	public void setMfyear(Integer mfyear) {
		this.mfyear = mfyear;
	}

	@Column(name = "MTRCLASS", length = 15)
	public String getMtrclass() {
		return this.mtrclass;
	}

	public void setMtrclass(String mtrclass) {
		this.mtrclass = mtrclass;
	}

	@Column(name = "CTPTMAKE", length = 15)
	public String getCtptmake() {
		return this.ctptmake;
	}

	public void setCtptmake(String ctptmake) {
		this.ctptmake = ctptmake;
	}

	@Column(name = "MF", precision = 10)
	public Double getMf() {
		return this.mf;
	}

	public void setMf(Double mf) {
		this.mf = mf;
	}

	
	

	@Column(name = "OLDSEAL", length = 15)
	public String getOldseal() {
		return this.oldseal;
	}

	public void setOldseal(String oldseal) {
		this.oldseal = oldseal;
	}

	@Column(name = "NEWSEAL", length = 15)
	public String getNewseal() {
		return this.newseal;
	}

	public void setNewseal(String newseal) {
		this.newseal = newseal;
	}

	@Column(name = "PRKWH", precision = 12, scale = 4)
	public Double getPrkwh() {
		return this.prkwh;
	}

	public void setPrkwh(Double prkwh) {
		this.prkwh = prkwh;
	}

	@Column(name = "PRKVAH", precision = 12, scale = 4)
	public Double getPrkvah() {
		return this.prkvah;
	}

	public void setPrkvah(Double prkvah) {
		this.prkvah = prkvah;
	}

	@Column(name = "PRKVA", precision = 12, scale = 4)
	public Double getPrkva() {
		return this.prkva;
	}

	public void setPrkva(Double prkva) {
		this.prkva = prkva;
	}

	@Column(name = "UNITSKWH", precision = 12, scale = 4)
	public Double getUnitskwh() {
		return this.unitskwh;
	}

	public void setUnitskwh(Double unitskwh) {
		this.unitskwh = unitskwh;
	}

	@Column(name = "UNITSKVAH", precision = 12, scale = 4)
	public Double getUnitskvah() {
		return this.unitskvah;
	}

	public void setUnitskvah(Double unitskvah) {
		this.unitskvah = unitskvah;
	}

	@Column(name = "UNITSKVA", precision = 12, scale = 4)
	public Double getUnitskva() {
		return this.unitskva;
	}

	public void setUnitskva(Double unitskva) {
		this.unitskva = unitskva;
	}

	@Column(name = "MRDSTATUS", length = 50)
	public String getMrdstatus() {
		return this.mrdstatus;
	}

	public void setMrdstatus(String mrdstatus) {
		this.mrdstatus = mrdstatus;
	}

	@Column(name = "PF", precision = 15)
	public Double getPf() {
		return this.pf;
	}

	public void setPf(Double pf) {
		this.pf = pf;
	}

	@Column(name = "PPF", precision = 15)
	public Double getPpf() {
		return this.ppf;
	}

	public void setPpf(Double ppf) {
		this.ppf = ppf;
	}

	@Column(name = "MTRTYPE", length = 15)
	public String getMtrtype() {
		return this.mtrtype;
	}

	public void setMtrtype(String mtrtype) {
		this.mtrtype = mtrtype;
	}

	@Column(name = "MRNAME", length = 32)
	public String getMrname() {
		return this.mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	@Column(name = "READINGREMARK", length = 100)
	public String getReadingremark() {
		return this.readingremark;
	}

	public void setReadingremark(String readingremark) {
		this.readingremark = readingremark;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DATESTAMP", length = 7)
	public Date getDatestamp() {
		return this.datestamp;
	}

	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}

	@Column(name = "OLDMETERNO", length = 20)
	public String getOldmeterno() {
		return this.oldmeterno;
	}

	public void setOldmeterno(String oldmeterno) {
		this.oldmeterno = oldmeterno;
	}

	@Column(name = "ENTRY", precision = 1, scale = 0)
	public Integer getEntry() {
		return this.entry;
	}

	public void setEntry(Integer entry) {
		this.entry = entry;
	}

	@Column(name = "DNAME", length = 25)
	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	@Column(name = "DDATE", length = 25)
	public String getDdate() {
		return this.ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}

	@Column(name = "XMLDATE", length = 7)
	public Date getXmldate() {
		return this.xmldate;
	}

	public void setXmldate(Date xmldate) {
		this.xmldate = xmldate;
	}

	@Column(name = "OLDMF", precision = 4, scale = 0)
	public Short getOldmf() {
		return this.oldmf;
	}

	public void setOldmf(Short oldmf) {
		this.oldmf = oldmf;
	}

	@Column(name = "XCURRDNGKWH", precision = 12, scale = 4)
	public Double getXcurrdngkwh() {
		return this.xcurrdngkwh;
	}

	public void setXcurrdngkwh(Double xcurrdngkwh) {
		this.xcurrdngkwh = xcurrdngkwh;
	}

	@Column(name = "XCURRRDNGKVAH", precision = 12, scale = 4)
	public Double getXcurrrdngkvah() {
		return this.xcurrrdngkvah;
	}

	public void setXcurrrdngkvah(Double xcurrrdngkvah) {
		this.xcurrrdngkvah = xcurrrdngkvah;
	}

	@Column(name = "XCURRDNGKVA", precision = 12, scale = 4)
	public Double getXcurrdngkva() {
		return this.xcurrdngkva;
	}

	public void setXcurrdngkva(Double xcurrdngkva) {
		this.xcurrdngkva = xcurrdngkva;
	}

	@Column(name = "XPF", precision = 15)
	public Double getXpf() {
		return this.xpf;
	}

	public void setXpf(Double xpf) {
		this.xpf = xpf;
	}

	@Column(name = "MTRMAKE", length = 25)
	public String getMtrmake() {
		return this.mtrmake;
	}

	public void setMtrmake(String mtrmake) {
		this.mtrmake = mtrmake;
	}

	@Column(name = "USERNAME", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "RTC", precision = 1, scale = 0)
	public Integer getRtc() {
		return this.rtc;
	}

	public void setRtc(Integer rtc) {
		this.rtc = rtc;
	}

	@Column(name = "MCST", length = 1)
	public String getMcst() {
		return this.mcst;
	}

	public void setMcst(String mcst) {
		this.mcst = mcst;
	}

	@Column(name = "MM", precision = 1, scale = 0)
	public Integer getMm() {
		return this.mm;
	}

	public void setMm(Integer mm) {
		this.mm = mm;
	}

	@Column(name = "MRINO", length = 10)
	public String getMrino() {
		return this.mrino;
	}

	public void setMrino(String mrino) {
		this.mrino = mrino;
	}

	@Column(name = "MRD", precision = 1, scale = 0)
	public Integer getMrd() {
		return this.mrd;
	}

	public void setMrd(Integer mrd) {
		this.mrd = mrd;
	}


	/*@ManyToOne(cascade=CascadeType.ALL)*/
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="accno",insertable=false,updatable=false)
	public Master getMaster() {
		return master;
	}
	
	public void setMaster(Master master) {
		this.master = master;
	}



	/*public CmriNumber getCmriNumber() {
		return cmriNumber;
	}

	public void setCmriNumber(CmriNumber cmriNumber) {
		this.cmriNumber = cmriNumber;
	}
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "KNO", length = 13)
	public String getkno() {
		return this.kno;
	}

	public void setkno(String kno) {
		this.kno = kno;
	}

	public String getDlms() {
		return dlms;
	}

	public void setDlms(String dlms) {
		this.dlms = dlms;
	}

	public String getDiscom() {
		return discom;
	}

	public void setDiscom(String discom) {
		this.discom = discom;
	}

	public String getCircle() {
		return circle;
	}

	public String getSubdiv() {
		return subdiv;
	}

	public String getDivision() {
		return division;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public void setSubdiv(String subdiv) {
		this.subdiv = subdiv;
	}

	public void setDivision(String division) {
		this.division = division;
	}


	


}
