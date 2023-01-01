package com.bcits.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="D4_LOAD_DATA" , schema="mdm_test")
@NamedQueries({
@NamedQuery(name="D4CdfData.getDetailsBasedOnId",query="SELECT d4 FROM D4CdfData d4 WHERE d4.meterNo=:meterNo AND TO_CHAR(d4.dayProfileDate,'dd-mm-yyyy')=:dayOfProfile ORDER BY d4.ipInterval"),
@NamedQuery(name="D4CdfData.getDuplicateDetails",query="SELECT d4 FROM D4CdfData d4 WHERE d4.meterNo=:meterNo AND TO_CHAR(d4.dayProfileDate,'dd-mm-yyyy')=:dayOfProfile"),
@NamedQuery(name="D4CdfData.getDetails",query="SELECT d4 FROM D4CdfData d4,CDFData c WHERE c.id=d4.cdfId AND d4.meterNo=:meterNo AND d4.billmonth=:billMonth"),
	
})
public class D4CdfData
{

	@Id
	 /*@SequenceGenerator(name = "d4Id", sequenceName = "d4_load_id")
	 @GeneratedValue(generator = "d4Id")	*/
	 @GeneratedValue(strategy = GenerationType.IDENTITY)

	/*@Id
	@GeneratedValue(strategy=GenerationType.AUTO)*/
	@Column(name="D4_LOAD_ID")
	private int d4LoadId;
	
	@Column(name="CDF_ID")
	private int cdfId;
	
	@Column(name="METERNO")
	private String meterNo;
	
	@Column(name="IP_INTERVAL")
	private int ipInterval;
	
	@Column(name="BILLMONTH")
	private String billmonth;
	
	@Column(name = "DAY_PROFILE_DATE")
	private Date dayProfileDate;
	
	@Column(name = "KVAVALUE")
	private String kvaValue;
	
	@Column(name = "INTERVALPERIOD")
	private String intervalPeriod;
	
	@Column(name = "KWHVALUE")
	private String kwhValue;
	
	@Column(name = "PFVALUE")
	private String pfValue;

	@Column(name = "VR")
	private String vrValue;

	@Column(name = "VY")
	private String vyValue;

	@Column(name = "VB")
	private String vbValue;

	@Column(name = "AR")
	private String arValue;

	@Column(name = "AY")
	private String ayValue;

	@Column(name = "AB")
	private String abValue;
	

	@Column(name = "time_stamp")
	private Timestamp timeStamp;
	
	@Column(name = "read_time")
	private Timestamp readTime;
	
	public Timestamp getReadTime() {
		return readTime;
	}

	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

	@Column(name = "modem_time")
	private Timestamp modemTime;	
	
	@Column(name = "imei")
	private String imei;	//varchar
	
	@Column(name = "i_r")
	private Double iR;	//numeric
	
	@Column(name = "i_y")
	private Double iY;//	numeric
	
	@Column(name = "i_b")
	private Double iB;	//numeric
	
	@Column(name = "v_r")
	private Double vR;//	numeric
	
	@Column(name = "v_y")
	private Double vY;	//numeric
	
	@Column(name = "v_b")
	private Double vB;	//numeric
	
	@Column(name = "kwh")
	private Double kwh;	//numeric
	
	@Column(name = "kvah")
	private Double kvah;	//numeric
	
	@Column(name = "kwh_exp")
	private Double kwhExp;//	numeric
	
	@Column(name = "kwh_imp")
	private Double kwhImp;	//numeric
	
	@Column(name = "structure_size")
	private Integer structureSize;//	numeric
 
	@Column(name = "trans_id")
	private String transId;	//varchar
	
	@Column(name = "frequency")
	private Double frequency;	//numeric
	
	@Column(name = "kvarh_lag")
	private Double kvarhLag;	//numeric
	
	@Column(name = "kvarh_lead")
	private Double kvarhLead;//	numeric
	
	@Column(name = "kvarh_q1")
	private Double kvarhQ1;	//numeric
	
	@Column(name = "kvarh_q2")
	private Double kvarhQ2;	//numeric
	
	@Column(name = "kvarh_q3")
	private Double kvarhQ3;	//numeric
	
	@Column(name = "kvarh_q4")
	private Double kvarhQ4;//	numeric
	
	@Column(name = "netkwh")
	private Double netKwh;//	numeric
	

	public D4CdfData()
	{
		
	}

	public int getD4LoadId()
	{
		return d4LoadId;
	}

	public void setD4LoadId(int d4LoadId)
	{
		this.d4LoadId = d4LoadId;
	}

	public int getCdfId()
	{
		return cdfId;
	}

	public void setCdfId(int cdfId)
	{
		this.cdfId = cdfId;
	}

	public String getMeterNo()
	{
		return meterNo;
	}

	public void setMeterNo(String meterNo)
	{
		this.meterNo = meterNo;
	}

	


	public int getIpInterval()
	{
		return ipInterval;
	}

	public void setIpInterval(int ipInterval)
	{
		this.ipInterval = ipInterval;
	}

	public String getBillmonth()
	{
		return billmonth;
	}

	public void setBillmonth(String billmonth)
	{
		this.billmonth = billmonth;
	}

	public Date getDayProfileDate()
	{
		return dayProfileDate;
	}

	public void setDayProfileDate(Date dayProfileDate)
	{
		this.dayProfileDate = dayProfileDate;
	}

	

	public String getIntervalPeriod()
	{
		return intervalPeriod;
	}

	public void setIntervalPeriod(String intervalPeriod)
	{
		this.intervalPeriod = intervalPeriod;
	}

	public String getKvaValue()
	{
		return kvaValue;
	}

	public void setKvaValue(String kvaValue)
	{
		this.kvaValue = kvaValue;
	}

	public String getKwhValue()
	{
		return kwhValue;
	}

	public void setKwhValue(String kwhValue)
	{
		this.kwhValue = kwhValue;
	}

	public String getPfValue()
	{
		return pfValue;
	}

	public void setPfValue(String pfValue)
	{
		this.pfValue = pfValue;
	}

	public String getVrValue() {
		return vrValue;
	}

	public void setVrValue(String vrValue) {
		this.vrValue = vrValue;
	}

	public String getVyValue() {
		return vyValue;
	}

	public void setVyValue(String vyValue) {
		this.vyValue = vyValue;
	}

	public String getVbValue() {
		return vbValue;
	}

	public void setVbValue(String vbValue) {
		this.vbValue = vbValue;
	}

	public String getArValue() {
		return arValue;
	}

	public void setArValue(String arValue) {
		this.arValue = arValue;
	}

	public String getAyValue() {
		return ayValue;
	}

	public void setAyValue(String ayValue) {
		this.ayValue = ayValue;
	}

	public String getAbValue() {
		return abValue;
	}

	public void setAbValue(String abValue) {
		this.abValue = abValue;
	}


	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Timestamp getModemTime() {
		return modemTime;
	}

	public void setModemTime(Timestamp modemTime) {
		this.modemTime = modemTime;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	/*public Double getiR() {
		return iR;
	}

	public void setiR(Double iR) {
		this.iR = iR;
	}

	public Double getiY() {
		return iY;
	}

	public void setiY(Double iY) {
		this.iY = iY;
	}

	public Double getiB() {
		return iB;
	}

	public void setiB(Double iB) {
		this.iB = iB;
	}

	public Double getvR() {
		return vR;
	}

	public void setvR(Double vR) {
		this.vR = vR;
	}

	public Double getvY() {
		return vY;
	}

	public void setvY(Double vY) {
		this.vY = vY;
	}

	public Double getvB() {
		return vB;
	}

	public void setvB(Double vB) {
		this.vB = vB;
	}*/

	/*public Double getKwh() {
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
	}*/

	public Double getKwhExp() {
		return kwhExp;
	}

	public void setKwhExp(Double kwhExp) {
		this.kwhExp = kwhExp;
	}

	public Double getKwhImp() {
		return kwhImp;
	}

	public void setKwhImp(Double kwhImp) {
		this.kwhImp = kwhImp;
	}

	public Integer getStructureSize() {
		return structureSize;
	}

	public void setStructureSize(Integer structureSize) {
		this.structureSize = structureSize;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
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

	public Double getKvarhQ1() {
		return kvarhQ1;
	}

	public void setKvarhQ1(Double kvarhQ1) {
		this.kvarhQ1 = kvarhQ1;
	}

	public Double getKvarhQ2() {
		return kvarhQ2;
	}

	public void setKvarhQ2(Double kvarhQ2) {
		this.kvarhQ2 = kvarhQ2;
	}

	public Double getKvarhQ3() {
		return kvarhQ3;
	}

	public void setKvarhQ3(Double kvarhQ3) {
		this.kvarhQ3 = kvarhQ3;
	}

	public Double getKvarhQ4() {
		return kvarhQ4;
	}

	public void setKvarhQ4(Double kvarhQ4) {
		this.kvarhQ4 = kvarhQ4;
	}

	public Double getNetKwh() {
		return netKwh;
	}

	public void setNetKwh(Double netKwh) {
		this.netKwh = netKwh;
	}

	public Double getiR() {
		return iR;
	}

	public void setiR(Double iR) {
		this.iR = iR;
	}

	public Double getiY() {
		return iY;
	}

	public void setiY(Double iY) {
		this.iY = iY;
	}

	public Double getiB() {
		return iB;
	}

	public void setiB(Double iB) {
		this.iB = iB;
	}

	public Double getvR() {
		return vR;
	}

	public void setvR(Double vR) {
		this.vR = vR;
	}

	public Double getvY() {
		return vY;
	}

	public void setvY(Double vY) {
		this.vY = vY;
	}

	public Double getvB() {
		return vB;
	}

	public void setvB(Double vB) {
		this.vB = vB;
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

	
	
	
	
}
