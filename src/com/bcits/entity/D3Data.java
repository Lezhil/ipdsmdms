package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="D3_DATA" ,schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="D3Data.getDetailsBasedOnMeterNo",query="SELECT d3 FROM D3Data d3 WHERE d3.cdfData.meterNo=:meterNo AND d3.cdfData.billmonth=:billMonth")
})
public class D3Data
{

	
	/*@SequenceGenerator(name = "d3Id", sequenceName = "D3_DATA_ID")
	 @GeneratedValue(generator = "d3Id")	
	@Id*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="D3_ID")
	private int d1Id;
	
	@Column(name="CDF_ID")
	private int CdfId;
	
	@Column(name="D3_01_ENERGY")
	private float d3_01_Energy;
	
	@Column(name="D3_02_ENERGY")
	private float d3_02_Energy;
	
	@Column(name="D3_03_ENERGY")
	private float d3_03_Energy;
	
	//new
	

	@Column(name="billing_date")
	private Timestamp billing_date;
	
	
	public Timestamp getBilling_date() {
		return billing_date;
	}

	public void setBilling_date(Timestamp billing_date) {
		this.billing_date = billing_date;
	}

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
	
	
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CDF_ID",insertable = false, updatable = false, nullable = false) 
	private CDFData cdfData;
	
	public D3Data()
	{
		
	}

	public int getD1Id()
	{
		return d1Id;
	}

	public void setD1Id(int d1Id)
	{
		this.d1Id = d1Id;
	}

	public int getCdfId()
	{
		return CdfId;
	}

	public void setCdfId(int cdfId)
	{
		CdfId = cdfId;
	}

	public float getD3_01_Energy()
	{
		return d3_01_Energy;
	}

	public void setD3_01_Energy(float d3_01_Energy)
	{
		this.d3_01_Energy = d3_01_Energy;
	}

	public float getD3_02_Energy()
	{
		return d3_02_Energy;
	}

	public void setD3_02_Energy(float d3_02_Energy)
	{
		this.d3_02_Energy = d3_02_Energy;
	}

	public float getD3_03_Energy()
	{
		return d3_03_Energy;
	}

	public void setD3_03_Energy(float d3_03_Energy)
	{
		this.d3_03_Energy = d3_03_Energy;
	}

	public CDFData getCdfData() {
		return cdfData;
	}

	public void setCdfData(CDFData cdfData) {
		this.cdfData = cdfData;
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
	
	
	
}
