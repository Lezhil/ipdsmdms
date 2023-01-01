package com.bcits.mdas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(name = "BILL_HISTORY",  schema = "METER_DATA",uniqueConstraints = { @UniqueConstraint(columnNames = { "BILLING_DATE","METER_NUMBER" }) })
@NamedQueries({
	
	//@NamedQuery(name = "AmrBillsEntity.getBillHistory", query = "select a from AmrBillsEntity a where a.myKey.meterNumber=:meterno and to_char(a.myKey.billingDate, 'YYYY-MM-DD' ) in ('2017-08-01','2017-09-01','2017-10-01','2017-11-01') ORDER BY a.myKey.billingDate desc ")
	  @NamedQuery(name = "AmrBillsEntity.getBillHistoryFirst", query = "select a from AmrBillsEntity a where a.myKey.meterNumber=:meterno and to_char(a.myKey.billingDate, 'YYYY-MM-DD' ) =:fileDate ORDER BY a.myKey.billingDate desc "),
	  @NamedQuery(name = "AmrBillsEntity.getBillHistory", query = "SELECT a from AmrBillsEntity a where a.myKey.meterNumber=:meterno and  to_char(a.myKey.billingDate, 'YYYY-MM-DD HH24:MI:SS') like '%01 00:00:00' AND  a.myKey.billingDate<= :fileDate   ORDER BY a.myKey.billingDate desc ")	,
	  @NamedQuery(name="AmrBillsEntity.getbillHistoryDetails", query="SELECT a from AmrBillsEntity a where a.myKey.meterNumber=:meterno and to_date(to_char(a.myKey.billingDate, 'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN to_date(:from, 'YYYY-MM-DD') AND to_date(:to, 'YYYY-MM-DD')")
	  /*@NamedQuery(name="AmrBillsEntity.getbillHistory6months", query="SELECT a from AmrBillsEntity a where a.myKey.meterNumber=:meterno and to_char(a.myKey.billingDate, 'yyyy-MM-dd HH:mm:ss') IN (:listMonths1,:listMonths2,:listMonths3,:listMonths4,:listMonths5)")*/
	  
})
public class AmrBillsEntity implements Comparable<AmrBillsEntity>{

	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private KeyBills myKey; // READ_TIME, METER NUMBER
	   
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "time_stamp")
	private Timestamp timeStamp;
	
	@Column(name = "server_time")
	private Timestamp serverTime;	
	
	@Column(name = "imei")
	private String imei;	//varchar
	
	@Column(name = "sys_pf_billing")
	private Double sysPfBilling=0.0;	//numeric
	
	@Column(name = "kwh_tz1")
	private Double kwhTz1 =0.0;//	numeric

	@Column(name = "kwh_tz2")
	private Double kwhTz2 =0.0;//	numeric
	
	@Column(name = "kwh_tz3")
	private Double kwhTz3 =0.0;//	numeric
	
	@Column(name = "kwh_tz4")
	private Double kwhTz4 =0.0;//	numeric
	
	@Column(name = "kwh_tz5")
	private Double kwhTz5 =0.0;//	numeric
	
	@Column(name = "kwh_tz6")
	private Double kwhTz6 =0.0;//	numeric
	
	@Column(name = "kwh_tz7")
	private Double kwhTz7 =0.0;//	numeric
	
	@Column(name = "kwh_tz8")
	private Double kwhTz8 =0.0;//	numeric
	
	@Column(name = "kwh")
	private Double kwh =0.0;	//numeric
	
	@Column(name = "kvah")
	private Double kvah =0.0;	//numeric
	
	@Column(name = "kvarh_lag")
	private Double kvarhLag =0.0;	//numeric
	
	@Column(name = "kvarh_lead")
	private Double kvarhLead =0.0;//	numeric
	
	@Column(name = "kvah_tz1")
	private Double kvahTz1 =0.0;//	numeric
	
	@Column(name = "kvah_tz2")
	private Double kvahTz2 =0.0;//	numeric
	
	@Column(name = "kvah_tz3")
	private Double kvahTz3=0.0;//	numeric

	@Column(name = "kvah_tz4")
	private Double kvahTz4 =0.0;//	numeric
	
	@Column(name = "kvah_tz5")
	private Double kvahTz5 =0.0;//	numeric
	
	@Column(name = "kvah_tz6")
	private Double kvahTz6 =0.0;//	numeric
	
	@Column(name = "kvah_tz7")
	private Double kvahTz7 =0.0;//	numeric
	
	@Column(name = "kvah_tz8")
	private Double kvahTz8 =0.0;//	numeric
	
	@Column(name = "demand_kw")
	private Double demandKw =0.0;	//numeric
	
	@Column(name = "occ_date_kw")
	private String occDateKw;//	numeric

	@Column(name = "kw_tz1")
	private Double kwTz1 =0.0;	//numeric
	
	@Column(name = "kw_tz2")
	private Double kwTz2 =0.0;	//numeric
	
	@Column(name = "kw_tz3")
	private Double kwTz3 =0.0;	//numeric
	
	@Column(name = "kw_tz4")
	private Double kwTz4 =0.0;	//numeric
	
	@Column(name = "kw_tz5")
	private Double kwTz5 =0.0;	//numeric
	
	@Column(name = "kw_tz6")
	private Double kwTz6 =0.0;	//numeric
	
	@Column(name = "kw_tz7")
	private Double kwTz7 =0.0;	//numeric
	
	@Column(name = "kw_tz8")
	private Double kwTz8 =0.0;	//numeric
	
	@Column(name = "date_kw_tz1")
	private String dateKwTz1;//	numeric
	
	@Column(name = "date_kw_tz2")
	private String dateKwTz2;//	numeric
	
	@Column(name = "date_kw_tz3")
	private String dateKwTz3;//	numeric
	
	@Column(name = "date_kw_tz4")
	private String dateKwTz4;//	numeric
	
	@Column(name = "date_kw_tz5")
	private String dateKwTz5;//	numeric
	
	@Column(name = "date_kw_tz6")
	private String dateKwTz6;//	numeric
	
	@Column(name = "date_kw_tz7")
	private String dateKwTz7;//	numeric
	
	@Column(name = "date_kw_tz8")
	private String dateKwTz8;//	numeric
	
	@Column(name = "kva")
	private Double kva =0.0;	//numeric
	
	@Column(name = "date_kva")
	private String dateKva;//	numeric
	
	@Column(name = "trans_id")
	private String transId;	//varchar
	
	@Column(name = "meter_id")
	private String meterId;	//varchar

	@Column(name = "kva_tz1")
	private Double kvaTz1 =0.0;	//numeric
	
	@Column(name = "kva_tz2")
	private Double kvaTz2 =0.0;	//numeric
	
	@Column(name = "kva_tz3")
	private Double kvaTz3 =0.0;	//numeric
	
	@Column(name = "kva_tz4")
	private Double kvaTz4 =0.0;	//numeric
	
	@Column(name = "kva_tz5")
	private Double kvaTz5 =0.0;	//numeric
	
	@Column(name = "kva_tz6")
	private Double kvaTz6 =0.0;	//numeric
	
	@Column(name = "kva_tz7")
	private Double kvaTz7 =0.0;	//numeric
	
	@Column(name = "kva_tz8")
	private Double kvaTz8 =0.0;	//numeric
	
	@Column(name = "date_kva_tz1")
	private String dateKvaTz1;//	numeric
	
	@Column(name = "date_kva_tz2")
	private String dateKvaTz2;//	numeric
	
	@Column(name = "date_kva_tz3")
	private String dateKvaTz3;//	numeric
	
	@Column(name = "date_kva_tz4")
	private String dateKvaTz4;//	numeric
	
	@Column(name = "date_kva_tz5")
	private String dateKvaTz5;//	numeric
	
	@Column(name = "date_kva_tz6")
	private String dateKvaTz6;//	numeric
	
	@Column(name = "date_kva_tz7")
	private String dateKvaTz7;//	numeric
	
	@Column(name = "date_kva_tz8")
	private String dateKvaTz8;//	numeric
	
	
	@Column(name = "bill_power_on_duration")
	private String billPowerOnDuration;
	
	@Column(name = "bill_kwh_export")
	private String billKwhExport;
	
	@Column(name = "bill_kvah_export")
	private String billKvahExport;
	
	@Column(name = "reactive_imp_active_imp")
	private String reactiveImpActiveImp;
	
	@Column(name = "reactive_imp_active_exp")
	private String reactiveImpActiveExp;
	
	@Column(name = "reactive_exp_active_exp")
	private String reactiveExpActiveExp;
	
	@Column(name = "reactive_exp_active_imp")
	private String reactiveExpActiveImp;
	
	@Column(name = "flag")
	private String flag;//	String
	
	@Column(name="read_from")
	private String read_from;
	
	@Column(name = "kwh_exp_tz1")
	private Double  kwh_exp_tz1 =0.0;


	@Column(name = "kwh_exp_tz2")
	private Double  kwh_exp_tz2 =0.0;

	@Column(name = "kwh_exp_tz3")
	private Double  kwh_exp_tz3 =0.0;

	@Column(name = "kwh_exp_tz4")
	private Double  kwh_exp_tz4 =0.0;

	@Column(name = "kwh_exp_tz5")
	private Double  kwh_exp_tz5 =0.0;

	@Column(name = "kwh_exp_tz6")
	private Double  kwh_exp_tz6 =0.0;

	@Column(name = "kwh_exp_tz7")
	private Double  kwh_exp_tz7 =0.0;

	@Column(name = "kwh_exp_tz8")
	private Double  kwh_exp_tz8 =0.0;

	@Column(name = "kvah_exp_tz1")
	private Double  kvah_exp_tz1 =0.0;

	@Column(name = "kvah_exp_tz2")
	private Double  kvah_exp_tz2 =0.0;

	@Column(name = "kvah_exp_tz3")
	private Double  kvah_exp_tz3 =0.0;

	@Column(name = "kvah_exp_tz4")
	private Double  kvah_exp_tz4 =0.0;

	@Column(name = "kvah_exp_tz5")
	private Double  kvah_exp_tz5 =0.0;

	@Column(name = "kvah_exp_tz6")
	private Double  kvah_exp_tz6 =0.0;

	@Column(name = "kvah_exp_tz7")
	private Double  kvah_exp_tz7 =0.0;

	@Column(name = "kvah_exp_tz8")
	private Double  kvah_exp_tz8 =0.0;

	@Column(name = "kw_exp")
	private Double  kw_exp =0.0;

	@Column(name = "kw_exp_tz1")
	private Double  kw_exp_tz1 =0.0;

	@Column(name = "kw_exp_tz2")
	private Double  kw_exp_tz2 =0.0;

	@Column(name = "kw_exp_tz3")
	private Double  kw_exp_tz3 =0.0;

	@Column(name = "kw_exp_tz4")
	private Double  kw_exp_tz4 =0.0;

	@Column(name = "kw_exp_tz5")
	private Double  kw_exp_tz5 =0.0;

	@Column(name = "kw_exp_tz6")
	private Double  kw_exp_tz6 =0.0;

	@Column(name = "kw_exp_tz7")
	private Double  kw_exp_tz7 =0.0;

	@Column(name = "kw_exp_tz8")
	private Double  kw_exp_tz8 =0.0;

	@Column(name = "date_kw_exp")
	private String date_kw_exp;

	@Column(name = "date_kw_exp_tz1")
	private String date_kw_exp_tz1;

	@Column(name = "date_kw_exp_tz2")
	private String date_kw_exp_tz2;

	@Column(name = "date_kw_exp_tz3")
	private String date_kw_exp_tz3;

	@Column(name = "date_kw_exp_tz4")
	private String date_kw_exp_tz4;

	@Column(name = "date_kw_exp_tz5")
	private String date_kw_exp_tz5;

	@Column(name = "date_kw_exp_tz6")
	private String date_kw_exp_tz6;

	@Column(name = "date_kw_exp_tz7")
	private String date_kw_exp_tz7;

	@Column(name = "date_kw_exp_tz8")
	private String date_kw_exp_tz8;

	@Column(name = "kva_exp")
	private Double  kva_exp =0.0;

	@Column(name = "kva_exp_tz1")
	private Double  kva_exp_tz1 =0.0;

	@Column(name = "kva_exp_tz2")
	private Double  kva_exp_tz2 =0.0;

	@Column(name = "kva_exp_tz3")
	private Double  kva_exp_tz3 =0.0;

	@Column(name = "kva_exp_tz4")
	private Double  kva_exp_tz4 =0.0;

	@Column(name = "kva_exp_tz5")
	private Double  kva_exp_tz5 =0.0;

	@Column(name = "kva_exp_tz6")
	private Double  kva_exp_tz6 =0.0;

	@Column(name = "kva_exp_tz7")
	private Double  kva_exp_tz7 =0.0;

	@Column(name = "kva_exp_tz8")
	private Double  kva_exp_tz8 =0.0;

	@Column(name = "date_kva_exp")
	private String date_kva_exp;

	@Column(name = "date_kva_exp_tz1")
	private String date_kva_exp_tz1;

	@Column(name = "date_kva_exp_tz2")
	private String date_kva_exp_tz2;

	@Column(name = "date_kva_exp_tz3")
	private String date_kva_exp_tz3;

	@Column(name = "date_kva_exp_tz4")
	private String date_kva_exp_tz4;

	@Column(name = "date_kva_exp_tz5")
	private String date_kva_exp_tz5;

	@Column(name = "date_kva_exp_tz6")
	private String date_kva_exp_tz6;

	@Column(name = "date_kva_exp_tz7")
	private String date_kva_exp_tz7;

	@Column(name = "date_kva_exp_tz8")
	private String date_kva_exp_tz8;

	@Column(name = "tamper_count")
	private String tamper_count;


	
	
	public String getFlag() {
		return flag;
	}

	public String getRead_from() {
		return read_from;
	}

	public void setRead_from(String read_from) {
		this.read_from = read_from;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getBillPowerOnDuration() {
		return billPowerOnDuration;
	}

	public void setBillPowerOnDuration(String billPowerOnDuration) {
		this.billPowerOnDuration = billPowerOnDuration;
	}

	public String getBillKwhExport() {
		return billKwhExport;
	}

	public void setBillKwhExport(String billKwhExport) {
		this.billKwhExport = billKwhExport;
	}

	public String getBillKvahExport() {
		return billKvahExport;
	}

	public void setBillKvahExport(String billKvahExport) {
		this.billKvahExport = billKvahExport;
	}

	public String getReactiveImpActiveImp() {
		return reactiveImpActiveImp;
	}

	public void setReactiveImpActiveImp(String reactiveImpActiveImp) {
		this.reactiveImpActiveImp = reactiveImpActiveImp;
	}

	public String getReactiveImpActiveExp() {
		return reactiveImpActiveExp;
	}

	public void setReactiveImpActiveExp(String reactiveImpActiveExp) {
		this.reactiveImpActiveExp = reactiveImpActiveExp;
	}

	public String getReactiveExpActiveExp() {
		return reactiveExpActiveExp;
	}

	public void setReactiveExpActiveExp(String reactiveExpActiveExp) {
		this.reactiveExpActiveExp = reactiveExpActiveExp;
	}

	public String getReactiveExpActiveImp() {
		return reactiveExpActiveImp;
	}

	public void setReactiveExpActiveImp(String reactiveExpActiveImp) {
		this.reactiveExpActiveImp = reactiveExpActiveImp;
	}

	

	public KeyBills getMyKey() {
		return myKey;
	}

	public void setMyKey(KeyBills myKey) {
		this.myKey = myKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Timestamp getServerTime() {
		return serverTime;
	}

	public void setServerTime(Timestamp serverTime) {
		this.serverTime = serverTime;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Double getSysPfBilling() {
		return sysPfBilling;
	}

	public void setSysPfBilling(Double sysPfBilling) {
		this.sysPfBilling = sysPfBilling;
	}

	public Double getKwhTz1() {
		return kwhTz1;
	}

	public void setKwhTz1(Double kwhTz1) {
		this.kwhTz1 = kwhTz1;
	}

	public Double getKwhTz2() {
		return kwhTz2;
	}

	public void setKwhTz2(Double kwhTz2) {
		this.kwhTz2 = kwhTz2;
	}

	public Double getKwhTz3() {
		return kwhTz3;
	}

	public void setKwhTz3(Double kwhTz3) {
		this.kwhTz3 = kwhTz3;
	}

	public Double getKwhTz4() {
		return kwhTz4;
	}

	public void setKwhTz4(Double kwhTz4) {
		this.kwhTz4 = kwhTz4;
	}

	public Double getKwhTz5() {
		return kwhTz5;
	}

	public void setKwhTz5(Double kwhTz5) {
		this.kwhTz5 = kwhTz5;
	}

	public Double getKwhTz6() {
		return kwhTz6;
	}

	public void setKwhTz6(Double kwhTz6) {
		this.kwhTz6 = kwhTz6;
	}

	public Double getKwhTz7() {
		return kwhTz7;
	}

	public void setKwhTz7(Double kwhTz7) {
		this.kwhTz7 = kwhTz7;
	}

	public Double getKwhTz8() {
		return kwhTz8;
	}

	public void setKwhTz8(Double kwhTz8) {
		this.kwhTz8 = kwhTz8;
	}

	public Double getKwh() {
		return kwh;
	}

	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}

	public Double getKvah() {
		return kvah;
	}

	public void setKvah(Double kvah) {
		this.kvah = kvah;
	}

	public Double getKvarhLag() {
		return kvarhLag;
	}

	public void setKvarhLag(Double kvarhLag) {
		this.kvarhLag = kvarhLag;
	}

	public Double getKvarhLead() {
		return kvarhLead;
	}

	public void setKvarhLead(Double kvarhLead) {
		this.kvarhLead = kvarhLead;
	}

	public Double getKvahTz1() {
		return kvahTz1;
	}

	public void setKvahTz1(Double kvahTz1) {
		this.kvahTz1 = kvahTz1;
	}

	public Double getKvahTz2() {
		return kvahTz2;
	}

	public void setKvahTz2(Double kvahTz2) {
		this.kvahTz2 = kvahTz2;
	}

	public Double getKvahTz3() {
		return kvahTz3;
	}

	public void setKvahTz3(Double kvahTz3) {
		this.kvahTz3 = kvahTz3;
	}

	public Double getKvahTz4() {
		return kvahTz4;
	}

	public void setKvahTz4(Double kvahTz4) {
		this.kvahTz4 = kvahTz4;
	}

	public Double getKvahTz5() {
		return kvahTz5;
	}

	public void setKvahTz5(Double kvahTz5) {
		this.kvahTz5 = kvahTz5;
	}

	public Double getKvahTz6() {
		return kvahTz6;
	}

	public void setKvahTz6(Double kvahTz6) {
		this.kvahTz6 = kvahTz6;
	}

	public Double getKvahTz7() {
		return kvahTz7;
	}

	public void setKvahTz7(Double kvahTz7) {
		this.kvahTz7 = kvahTz7;
	}

	public Double getKvahTz8() {
		return kvahTz8;
	}

	public void setKvahTz8(Double kvahTz8) {
		this.kvahTz8 = kvahTz8;
	}

	public Double getDemandKw() {
		return demandKw;
	}

	public void setDemandKw(Double demandKw) {
		this.demandKw = demandKw;
	}

	public String getOccDateKw() {
		return occDateKw;
	}

	public void setOccDateKw(String occDateKw) {
		this.occDateKw = occDateKw;
	}

	public Double getKwTz1() {
		return kwTz1;
	}

	public void setKwTz1(Double kwTz1) {
		this.kwTz1 = kwTz1;
	}

	public Double getKwTz2() {
		return kwTz2;
	}

	public void setKwTz2(Double kwTz2) {
		this.kwTz2 = kwTz2;
	}

	public Double getKwTz3() {
		return kwTz3;
	}

	public void setKwTz3(Double kwTz3) {
		this.kwTz3 = kwTz3;
	}

	public Double getKwTz4() {
		return kwTz4;
	}

	public void setKwTz4(Double kwTz4) {
		this.kwTz4 = kwTz4;
	}

	public Double getKwTz5() {
		return kwTz5;
	}

	public void setKwTz5(Double kwTz5) {
		this.kwTz5 = kwTz5;
	}

	public Double getKwTz6() {
		return kwTz6;
	}

	public void setKwTz6(Double kwTz6) {
		this.kwTz6 = kwTz6;
	}

	public Double getKwTz7() {
		return kwTz7;
	}

	public void setKwTz7(Double kwTz7) {
		this.kwTz7 = kwTz7;
	}

	public Double getKwTz8() {
		return kwTz8;
	}

	public void setKwTz8(Double kwTz8) {
		this.kwTz8 = kwTz8;
	}

	public String getDateKwTz1() {
		return dateKwTz1;
	}

	public void setDateKwTz1(String dateKwTz1) {
		this.dateKwTz1 = dateKwTz1;
	}

	public String getDateKwTz2() {
		return dateKwTz2;
	}

	public void setDateKwTz2(String dateKwTz2) {
		this.dateKwTz2 = dateKwTz2;
	}

	public String getDateKwTz3() {
		return dateKwTz3;
	}

	public void setDateKwTz3(String dateKwTz3) {
		this.dateKwTz3 = dateKwTz3;
	}

	public String getDateKwTz4() {
		return dateKwTz4;
	}

	public void setDateKwTz4(String dateKwTz4) {
		this.dateKwTz4 = dateKwTz4;
	}

	public String getDateKwTz5() {
		return dateKwTz5;
	}

	public void setDateKwTz5(String dateKwTz5) {
		this.dateKwTz5 = dateKwTz5;
	}

	public String getDateKwTz6() {
		return dateKwTz6;
	}

	public void setDateKwTz6(String dateKwTz6) {
		this.dateKwTz6 = dateKwTz6;
	}

	public String getDateKwTz7() {
		return dateKwTz7;
	}

	public void setDateKwTz7(String dateKwTz7) {
		this.dateKwTz7 = dateKwTz7;
	}

	public String getDateKwTz8() {
		return dateKwTz8;
	}

	public void setDateKwTz8(String dateKwTz8) {
		this.dateKwTz8 = dateKwTz8;
	}

	public Double getKva() {
		return kva;
	}

	public void setKva(Double kva) {
		this.kva = kva;
	}

	public String getDateKva() {
		return dateKva;
	}

	public void setDateKva(String dateKva) {
		this.dateKva = dateKva;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public Double getKvaTz1() {
		return kvaTz1;
	}

	public void setKvaTz1(Double kvaTz1) {
		this.kvaTz1 = kvaTz1;
	}

	public Double getKvaTz2() {
		return kvaTz2;
	}

	public void setKvaTz2(Double kvaTz2) {
		this.kvaTz2 = kvaTz2;
	}

	public Double getKvaTz3() {
		return kvaTz3;
	}

	public void setKvaTz3(Double kvaTz3) {
		this.kvaTz3 = kvaTz3;
	}

	public Double getKvaTz4() {
		return kvaTz4;
	}

	public void setKvaTz4(Double kvaTz4) {
		this.kvaTz4 = kvaTz4;
	}

	public Double getKvaTz5() {
		return kvaTz5;
	}

	public void setKvaTz5(Double kvaTz5) {
		this.kvaTz5 = kvaTz5;
	}

	public Double getKvaTz6() {
		return kvaTz6;
	}

	public void setKvaTz6(Double kvaTz6) {
		this.kvaTz6 = kvaTz6;
	}

	public Double getKvaTz7() {
		return kvaTz7;
	}

	public void setKvaTz7(Double kvaTz7) {
		this.kvaTz7 = kvaTz7;
	}

	public Double getKvaTz8() {
		return kvaTz8;
	}

	public void setKvaTz8(Double kvaTz8) {
		this.kvaTz8 = kvaTz8;
	}

	public String getDateKvaTz1() {
		return dateKvaTz1;
	}

	public void setDateKvaTz1(String dateKvaTz1) {
		this.dateKvaTz1 = dateKvaTz1;
	}

	public String getDateKvaTz2() {
		return dateKvaTz2;
	}

	public void setDateKvaTz2(String dateKvaTz2) {
		this.dateKvaTz2 = dateKvaTz2;
	}

	public String getDateKvaTz3() {
		return dateKvaTz3;
	}

	public void setDateKvaTz3(String dateKvaTz3) {
		this.dateKvaTz3 = dateKvaTz3;
	}

	public String getDateKvaTz4() {
		return dateKvaTz4;
	}

	public void setDateKvaTz4(String dateKvaTz4) {
		this.dateKvaTz4 = dateKvaTz4;
	}

	public String getDateKvaTz5() {
		return dateKvaTz5;
	}

	public void setDateKvaTz5(String dateKvaTz5) {
		this.dateKvaTz5 = dateKvaTz5;
	}

	public String getDateKvaTz6() {
		return dateKvaTz6;
	}

	public void setDateKvaTz6(String dateKvaTz6) {
		this.dateKvaTz6 = dateKvaTz6;
	}

	public String getDateKvaTz7() {
		return dateKvaTz7;
	}

	public void setDateKvaTz7(String dateKvaTz7) {
		this.dateKvaTz7 = dateKvaTz7;
	}

	public String getDateKvaTz8() {
		return dateKvaTz8;
	}

	public void setDateKvaTz8(String dateKvaTz8) {
		this.dateKvaTz8 = dateKvaTz8;
	}

	@Embeddable
	public static  class KeyBills implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "meter_number")
		private String meterNumber;	

		@Column(name = "billing_date")
		private Timestamp billingDate;	//varchar


		public Timestamp getReadTime() {
			return billingDate;
		}

		public void setReadTime(Timestamp billingDate) {
			this.billingDate = billingDate;
		}

		public String getMeterNumber() {
			return meterNumber;
		}

		public void setMeterNumber(String meterNumber) {
			this.meterNumber = meterNumber;
		}

		public KeyBills(String meterNumber, Timestamp billingDate) {
			super();
			this.meterNumber = meterNumber;
			this.billingDate = billingDate;
		}

		public KeyBills(){
			 
		}
	    
	}
	
	public AmrBillsEntity() {

	}

	@Override
	public int compareTo(AmrBillsEntity amrBillsEntity) {
		
		Timestamp t1=this.getMyKey().getReadTime();
		Timestamp t2=amrBillsEntity.getMyKey().getReadTime();
		if(t1.after(t2))
		{
			return -1;
		}
		else if(t1.before(t2))
		{
			return 1;
		}else 
		{
			return 0;
		}
		
	}

	public void setReadTime(Timestamp timestamp2) {
		// TODO Auto-generated method stub
		
	}

	public Double getKwh_exp_tz1() {
		return kwh_exp_tz1;
	}

	public void setKwh_exp_tz1(Double kwh_exp_tz1) {
		this.kwh_exp_tz1 = kwh_exp_tz1;
	}

	public Double getKwh_exp_tz2() {
		return kwh_exp_tz2;
	}

	public void setKwh_exp_tz2(Double kwh_exp_tz2) {
		this.kwh_exp_tz2 = kwh_exp_tz2;
	}

	public Double getKwh_exp_tz3() {
		return kwh_exp_tz3;
	}

	public void setKwh_exp_tz3(Double kwh_exp_tz3) {
		this.kwh_exp_tz3 = kwh_exp_tz3;
	}

	public Double getKwh_exp_tz4() {
		return kwh_exp_tz4;
	}

	public void setKwh_exp_tz4(Double kwh_exp_tz4) {
		this.kwh_exp_tz4 = kwh_exp_tz4;
	}

	public Double getKwh_exp_tz5() {
		return kwh_exp_tz5;
	}

	public void setKwh_exp_tz5(Double kwh_exp_tz5) {
		this.kwh_exp_tz5 = kwh_exp_tz5;
	}

	public Double getKwh_exp_tz6() {
		return kwh_exp_tz6;
	}

	public void setKwh_exp_tz6(Double kwh_exp_tz6) {
		this.kwh_exp_tz6 = kwh_exp_tz6;
	}

	public Double getKwh_exp_tz7() {
		return kwh_exp_tz7;
	}

	public void setKwh_exp_tz7(Double kwh_exp_tz7) {
		this.kwh_exp_tz7 = kwh_exp_tz7;
	}

	public Double getKwh_exp_tz8() {
		return kwh_exp_tz8;
	}

	public void setKwh_exp_tz8(Double kwh_exp_tz8) {
		this.kwh_exp_tz8 = kwh_exp_tz8;
	}

	public Double getKvah_exp_tz1() {
		return kvah_exp_tz1;
	}

	public void setKvah_exp_tz1(Double kvah_exp_tz1) {
		this.kvah_exp_tz1 = kvah_exp_tz1;
	}

	public Double getKvah_exp_tz2() {
		return kvah_exp_tz2;
	}

	public void setKvah_exp_tz2(Double kvah_exp_tz2) {
		this.kvah_exp_tz2 = kvah_exp_tz2;
	}

	public Double getKvah_exp_tz3() {
		return kvah_exp_tz3;
	}

	public void setKvah_exp_tz3(Double kvah_exp_tz3) {
		this.kvah_exp_tz3 = kvah_exp_tz3;
	}

	public Double getKvah_exp_tz4() {
		return kvah_exp_tz4;
	}

	public void setKvah_exp_tz4(Double kvah_exp_tz4) {
		this.kvah_exp_tz4 = kvah_exp_tz4;
	}

	public Double getKvah_exp_tz5() {
		return kvah_exp_tz5;
	}

	public void setKvah_exp_tz5(Double kvah_exp_tz5) {
		this.kvah_exp_tz5 = kvah_exp_tz5;
	}

	public Double getKvah_exp_tz6() {
		return kvah_exp_tz6;
	}

	public void setKvah_exp_tz6(Double kvah_exp_tz6) {
		this.kvah_exp_tz6 = kvah_exp_tz6;
	}

	public Double getKvah_exp_tz7() {
		return kvah_exp_tz7;
	}

	public void setKvah_exp_tz7(Double kvah_exp_tz7) {
		this.kvah_exp_tz7 = kvah_exp_tz7;
	}

	public Double getKvah_exp_tz8() {
		return kvah_exp_tz8;
	}

	public void setKvah_exp_tz8(Double kvah_exp_tz8) {
		this.kvah_exp_tz8 = kvah_exp_tz8;
	}

	public Double getKw_exp() {
		return kw_exp;
	}

	public void setKw_exp(Double kw_exp) {
		this.kw_exp = kw_exp;
	}

	public Double getKw_exp_tz1() {
		return kw_exp_tz1;
	}

	public void setKw_exp_tz1(Double kw_exp_tz1) {
		this.kw_exp_tz1 = kw_exp_tz1;
	}

	public Double getKw_exp_tz2() {
		return kw_exp_tz2;
	}

	public void setKw_exp_tz2(Double kw_exp_tz2) {
		this.kw_exp_tz2 = kw_exp_tz2;
	}

	public Double getKw_exp_tz3() {
		return kw_exp_tz3;
	}

	public void setKw_exp_tz3(Double kw_exp_tz3) {
		this.kw_exp_tz3 = kw_exp_tz3;
	}

	public Double getKw_exp_tz4() {
		return kw_exp_tz4;
	}

	public void setKw_exp_tz4(Double kw_exp_tz4) {
		this.kw_exp_tz4 = kw_exp_tz4;
	}

	public Double getKw_exp_tz5() {
		return kw_exp_tz5;
	}

	public void setKw_exp_tz5(Double kw_exp_tz5) {
		this.kw_exp_tz5 = kw_exp_tz5;
	}

	public Double getKw_exp_tz6() {
		return kw_exp_tz6;
	}

	public void setKw_exp_tz6(Double kw_exp_tz6) {
		this.kw_exp_tz6 = kw_exp_tz6;
	}

	public Double getKw_exp_tz7() {
		return kw_exp_tz7;
	}

	public void setKw_exp_tz7(Double kw_exp_tz7) {
		this.kw_exp_tz7 = kw_exp_tz7;
	}

	public Double getKw_exp_tz8() {
		return kw_exp_tz8;
	}

	public void setKw_exp_tz8(Double kw_exp_tz8) {
		this.kw_exp_tz8 = kw_exp_tz8;
	}

	public String getDate_kw_exp() {
		return date_kw_exp;
	}

	public void setDate_kw_exp(String date_kw_exp) {
		this.date_kw_exp = date_kw_exp;
	}

	public String getDate_kw_exp_tz1() {
		return date_kw_exp_tz1;
	}

	public void setDate_kw_exp_tz1(String date_kw_exp_tz1) {
		this.date_kw_exp_tz1 = date_kw_exp_tz1;
	}

	public String getDate_kw_exp_tz2() {
		return date_kw_exp_tz2;
	}

	public void setDate_kw_exp_tz2(String date_kw_exp_tz2) {
		this.date_kw_exp_tz2 = date_kw_exp_tz2;
	}

	public String getDate_kw_exp_tz3() {
		return date_kw_exp_tz3;
	}

	public void setDate_kw_exp_tz3(String date_kw_exp_tz3) {
		this.date_kw_exp_tz3 = date_kw_exp_tz3;
	}

	public String getDate_kw_exp_tz4() {
		return date_kw_exp_tz4;
	}

	public void setDate_kw_exp_tz4(String date_kw_exp_tz4) {
		this.date_kw_exp_tz4 = date_kw_exp_tz4;
	}

	public String getDate_kw_exp_tz5() {
		return date_kw_exp_tz5;
	}

	public void setDate_kw_exp_tz5(String date_kw_exp_tz5) {
		this.date_kw_exp_tz5 = date_kw_exp_tz5;
	}

	public String getDate_kw_exp_tz6() {
		return date_kw_exp_tz6;
	}

	public void setDate_kw_exp_tz6(String date_kw_exp_tz6) {
		this.date_kw_exp_tz6 = date_kw_exp_tz6;
	}

	public String getDate_kw_exp_tz7() {
		return date_kw_exp_tz7;
	}

	public void setDate_kw_exp_tz7(String date_kw_exp_tz7) {
		this.date_kw_exp_tz7 = date_kw_exp_tz7;
	}

	public String getDate_kw_exp_tz8() {
		return date_kw_exp_tz8;
	}

	public void setDate_kw_exp_tz8(String date_kw_exp_tz8) {
		this.date_kw_exp_tz8 = date_kw_exp_tz8;
	}

	public Double getKva_exp() {
		return kva_exp;
	}

	public void setKva_exp(Double kva_exp) {
		this.kva_exp = kva_exp;
	}

	public Double getKva_exp_tz1() {
		return kva_exp_tz1;
	}

	public void setKva_exp_tz1(Double kva_exp_tz1) {
		this.kva_exp_tz1 = kva_exp_tz1;
	}

	public Double getKva_exp_tz2() {
		return kva_exp_tz2;
	}

	public void setKva_exp_tz2(Double kva_exp_tz2) {
		this.kva_exp_tz2 = kva_exp_tz2;
	}

	public Double getKva_exp_tz3() {
		return kva_exp_tz3;
	}

	public void setKva_exp_tz3(Double kva_exp_tz3) {
		this.kva_exp_tz3 = kva_exp_tz3;
	}

	public Double getKva_exp_tz4() {
		return kva_exp_tz4;
	}

	public void setKva_exp_tz4(Double kva_exp_tz4) {
		this.kva_exp_tz4 = kva_exp_tz4;
	}

	public Double getKva_exp_tz5() {
		return kva_exp_tz5;
	}

	public void setKva_exp_tz5(Double kva_exp_tz5) {
		this.kva_exp_tz5 = kva_exp_tz5;
	}

	public Double getKva_exp_tz6() {
		return kva_exp_tz6;
	}

	public void setKva_exp_tz6(Double kva_exp_tz6) {
		this.kva_exp_tz6 = kva_exp_tz6;
	}

	public Double getKva_exp_tz7() {
		return kva_exp_tz7;
	}

	public void setKva_exp_tz7(Double kva_exp_tz7) {
		this.kva_exp_tz7 = kva_exp_tz7;
	}

	public Double getKva_exp_tz8() {
		return kva_exp_tz8;
	}

	public void setKva_exp_tz8(Double kva_exp_tz8) {
		this.kva_exp_tz8 = kva_exp_tz8;
	}

	public String getDate_kva_exp() {
		return date_kva_exp;
	}

	public void setDate_kva_exp(String date_kva_exp) {
		this.date_kva_exp = date_kva_exp;
	}

	public String getDate_kva_exp_tz1() {
		return date_kva_exp_tz1;
	}

	public void setDate_kva_exp_tz1(String date_kva_exp_tz1) {
		this.date_kva_exp_tz1 = date_kva_exp_tz1;
	}

	public String getDate_kva_exp_tz2() {
		return date_kva_exp_tz2;
	}

	public void setDate_kva_exp_tz2(String date_kva_exp_tz2) {
		this.date_kva_exp_tz2 = date_kva_exp_tz2;
	}

	public String getDate_kva_exp_tz3() {
		return date_kva_exp_tz3;
	}

	public void setDate_kva_exp_tz3(String date_kva_exp_tz3) {
		this.date_kva_exp_tz3 = date_kva_exp_tz3;
	}

	public String getDate_kva_exp_tz4() {
		return date_kva_exp_tz4;
	}

	public void setDate_kva_exp_tz4(String date_kva_exp_tz4) {
		this.date_kva_exp_tz4 = date_kva_exp_tz4;
	}

	public String getDate_kva_exp_tz5() {
		return date_kva_exp_tz5;
	}

	public void setDate_kva_exp_tz5(String date_kva_exp_tz5) {
		this.date_kva_exp_tz5 = date_kva_exp_tz5;
	}

	public String getDate_kva_exp_tz6() {
		return date_kva_exp_tz6;
	}

	public void setDate_kva_exp_tz6(String date_kva_exp_tz6) {
		this.date_kva_exp_tz6 = date_kva_exp_tz6;
	}

	public String getDate_kva_exp_tz7() {
		return date_kva_exp_tz7;
	}

	public void setDate_kva_exp_tz7(String date_kva_exp_tz7) {
		this.date_kva_exp_tz7 = date_kva_exp_tz7;
	}

	public String getDate_kva_exp_tz8() {
		return date_kva_exp_tz8;
	}

	public void setDate_kva_exp_tz8(String date_kva_exp_tz8) {
		this.date_kva_exp_tz8 = date_kva_exp_tz8;
	}

	public String getTamper_count() {
		return tamper_count;
	}

	public void setTamper_count(String tamper_count) {
		this.tamper_count = tamper_count;
	}
	
}
