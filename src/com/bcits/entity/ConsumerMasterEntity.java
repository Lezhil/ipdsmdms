package com.bcits.entity;




import java.sql.Timestamp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="CONSUMERMASTER", schema="meter_data")
@NamedQueries({									
	@NamedQuery(name="CONSUMERMASTER.getdataByAccno",query="select model from ConsumerMasterEntity model where model.accno=:accno"),
	@NamedQuery(name="CONSUMERMASTER.getdataBymeterno",query="select model from ConsumerMasterEntity model where model.meterno=:meterno"),
	@NamedQuery(name="CONSUMERMASTER.getdataByKno",query="select model from ConsumerMasterEntity model where model.kno=:kno"),
	/*@NamedQuery(name="Consumer.updateModifyData", query = "UPDATE ConsumerMasterEntity m  SET m.accno=:accno, m.kno=:kno, m.meterno=:meterno,m.name=:name, m.address1=:address1,m.phoneno=:phoneno,"
			+ "m.email=:email,m.cd=:cd,m.kworhp=:kworhp,m.sanload=:sanload,m.mf=:mf,m.supplyvoltage=:supplyvoltage,m.tadesc=:tadesc,m.tariffcode=:tariffcode,m.consumerstatus=:consumerstatus,m.latitude=:lat,"
			+ "m.longitude=:long,m.prepaid=:prepaid,m.tod=:tod,m.tou=:tou,m.billperiodstartdate=:billdate,m.updateby=:updateBy,m.updatedate=:updateTime "
			+ "WHERE m.id =:id"),*/
	@NamedQuery(name="CONSUMERMASTER.getAllConsumerData",query= "select model from ConsumerMasterEntity model order by accno")
			
	

})
public class ConsumerMasterEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id; 
	
	@Column(name="SDOCODE")
	private String sdocode;
	
	@Column(name="ACCNO")
	private String accno;
	
	@Column(name="KNO")
	private String kno;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="ADDRESS1")
	private String address1;
	
	@Column(name="PHONENO")
	private Long phoneno;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="KWORHP")
	private String kworhp;
	
	@Column(name="SANLOAD")
	private Double sanload;
	
	@Column(name="CONLOAD")
	private Double conload;
	
	@Column(name="CD")
	private Double cd;
	
	@Column(name="SUPPLYVOLTAGE")
	private String supplyvoltage;
	
	@Column(name="TADESC")
	private String tadesc;
	
	@Column(name="CONSUMERSTATUS")
	private String consumerstatus;
	
	@Column(name="DTCODE")
	private String dtcode;
	
	@Column(name="FEEDERCODE")
	private String feedercode;
	
	@Column(name="TARIFFCODE")
	private String tariffcode;
	
	@Column(name="PREPAID")
	private int prepaid;
	
	@Column(name="TOD")
	private Integer tod;
	
	@Column(name="TOU")
	private Integer tou;
	
	@Column(name="LONGITUDE")
	private Double longitude;
	
	@Column(name="LATITUDE")
	private Double latitude;
	
	@Column(name="BILLPERIOD")
	private Integer billperiod;
	
	@Column(name="BILLPERIODSTARTDATE")
	private java.sql.Date billperiodstartdate;
	
	@Column(name="METERNO")
	private String meterno;
	
	@Column(name="MF")
	private Double mf;
	
	@Column(name="TPDTCODE")
	private String tpdtcode;
	
	@Column(name="TPFEEDERCODE")
	private String tpfeedercode;
	
	/*@Column(name="ENTRYDATE")
	private Date entrydate;*/
	
	@Column(name="ENTRYBY")
	private String entryby;
	
	/*@Column(name="UPDATEDATE")
	private Date updatedate;*/
	
	@Column(name="UPDATEBY")
	private String updateby;

	@Column(name="UPDATE_DATE")
	private Timestamp updatedate;
	
	@Column(name="ENTRY_DATE")
	private Timestamp entrydate;

	@Column(name="meterchangedate")
	private Timestamp meterchangedate;
	
	@Column(name="mfchangedate")
	private Timestamp mfchangedate;
	
	@Column(name="meterchangeflag")
	private Integer meterchangeflag;
	
	
	@Column(name="mfflag")
	private Integer mfflag;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSdocode() {
		return sdocode;
	}

	public void setSdocode(String sdocode) {
		this.sdocode = sdocode;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public Long getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(Long phoneno) {
		this.phoneno = phoneno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKworhp() {
		return kworhp;
	}

	public void setKworhp(String kworhp) {
		this.kworhp = kworhp;
	}

	public Double getSanload() {
		return sanload;
	}

	public void setSanload(Double sanload) {
		this.sanload = sanload;
	}

	public Double getConload() {
		return conload;
	}

	public void setConload(Double conload) {
		this.conload = conload;
	}

	public Double getCd() {
		return cd;
	}

	public void setCd(Double cd) {
		this.cd = cd;
	}

	public String getSupplyvoltage() {
		return supplyvoltage;
	}

	public void setSupplyvoltage(String supplyvoltage) {
		this.supplyvoltage = supplyvoltage;
	}

	public String getTadesc() {
		return tadesc;
	}

	public void setTadesc(String tadesc) {
		this.tadesc = tadesc;
	}

	public String getConsumerstatus() {
		return consumerstatus;
	}

	public void setConsumerstatus(String consumerstatus) {
		this.consumerstatus = consumerstatus;
	}

	public String getDtcode() {
		return dtcode;
	}

	public void setDtcode(String dtcode) {
		this.dtcode = dtcode;
	}

	public String getFeedercode() {
		return feedercode;
	}

	public void setFeedercode(String feedercode) {
		this.feedercode = feedercode;
	}

	public String getTariffcode() {
		return tariffcode;
	}

	public void setTariffcode(String tariffcode) {
		this.tariffcode = tariffcode;
	}

	public int getPrepaid() {
		return prepaid;
	}

	public void setPrepaid(int prepaid) {
		this.prepaid = prepaid;
	}

	public Integer getTod() {
		return tod;
	}

	public void setTod(Integer tod) {
		this.tod = tod;
	}

	public Integer getTou() {
		return tou;
	}

	public void setTou(Integer tou) {
		this.tou = tou;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getBillperiod() {
		return billperiod;
	}

	public void setBillperiod(Integer billperiod) {
		this.billperiod = billperiod;
	}

	public java.sql.Date getBillperiodstartdate() {
		return billperiodstartdate;
	}

	public void setBillperiodstartdate(java.sql.Date billperiodstartdate) {
		this.billperiodstartdate = billperiodstartdate;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public Double getMf() {
		return mf;
	}

	public void setMf(Double mf) {
		this.mf = mf;
	}

	public String getTpdtcode() {
		return tpdtcode;
	}

	public void setTpdtcode(String tpdtcode) {
		this.tpdtcode = tpdtcode;
	}

	public String getTpfeedercode() {
		return tpfeedercode;
	}

	public void setTpfeedercode(String tpfeedercode) {
		this.tpfeedercode = tpfeedercode;
	}

	public String getEntryby() {
		return entryby;
	}

	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public Timestamp getMeterchangedate() {
		return meterchangedate;
	}

	public void setMeterchangedate(Timestamp meterchangedate) {
		this.meterchangedate = meterchangedate;
	}

	public Timestamp getMfchangedate() {
		return mfchangedate;
	}

	public void setMfchangedate(Timestamp mfchangedate) {
		this.mfchangedate = mfchangedate;
	}

	public Integer getMeterchangeflag() {
		return meterchangeflag;
	}

	public void setMeterchangeflag(Integer meterchangeflag) {
		this.meterchangeflag = meterchangeflag;
	}

	public Integer getMfflag() {
		return mfflag;
	}

	public void setMfflag(Integer mfflag) {
		this.mfflag = mfflag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Timestamp getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

	
	
}
