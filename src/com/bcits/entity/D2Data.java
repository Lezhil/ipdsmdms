package com.bcits.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name="D2_DATA" , schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="D2Data.AllDetails",query="SELECT d2 FROM D2Data d2 WHERE d2.cdfData.meterNo=:meterNo AND d2.cdfData.billmonth=:billMonth"),
	@NamedQuery(name="D2Data.getmeterData",query="SELECT d2 FROM D2Data d2 WHERE d2.cdfData.meterNo=:meterNo AND to_char(d2.readTime, 'yyyy-mm-dd')=:billMonth")

	
})
public class D2Data 
{
	/*@Id
	@SequenceGenerator(name = "d2Id", sequenceName = "D2_DATA_ID")
	 @GeneratedValue(generator = "d2Id")*/	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "D2_ID")
	private int id;
	
	@Column(name = "CDF_ID")
	private int cdfId;
	
	@Column(name = "R_PHASE_VAL")
	private float rPhaseVal;
	
	@Column(name = "B_PHASE_VAL")
	private float bPhaseVal;

	@Column(name = "Y_PHASE_VAL")
	private float yPhaseVal;
	
	@Column(name = "R_PHASE_LINE_VAL")
	private float rPhaseLineVal;
	
	@Column(name = "Y_PHASE_LINE_VAL")
	private float yPhaseLineVal;
	
	@Column(name = "B_PHASE_LINE_VAL")
	private float bPhaseLineVal;
	
	@Column(name = "R_PHASE_ACTIVE_VAL")
	private float rPhaseActiveVal;
	
	@Column(name = "Y_PHASE_ACTIVE_VAL")
	private float yPhaseActiveVal;
	
	@Column(name = "B_PHASE_ACTIVE_VAL")
	private float bPhaseActiveVal;
	
	@Column(name = "R_PHASE_PF_VAL")
	private float rPhasePfVal;
	
	@Column(name = "Y_PHASE_PF_VAL")
	private float yPhasePfVal;
	
	@Column(name = "B_PHASE_PF_VAL")
	private float bPhasePfVal;
	
	@Column(name = "AVG_PF_VAL")
	private float avgPfVal;
	
	@Column(name = "ACTIVE_POWER_VAL")
	private float activePowerVal;
	
	@Column(name = "MF")
	private double mf;
	
	@Column(name="R_PHASE_CURRENT_ANGLE")
	private String rPhaseCuurentAngle;
	
	@Column(name="Y_PHASE_CURRENT_ANGLE")
	private String yPhaseCuurentAngle;
	
	@Column(name="B_PHASE_CURRENT_ANGLE")
	private String bPhaseCuurentAngle;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CDF_ID",insertable = false, updatable = false, nullable = false) 
	private CDFData cdfData;
	
	@Column(name = "PHASE_SEQUENCE")
	private String phaseSequence;
	
	@Column(name = "D2_KWH")
	private float d2_kwh;

	
	//newly added columns
	
	@Column(name = "kvah")
	private Double kvah;	//numeric
	
	@Column(name = "kva")
	private Double kva;//	numeric
	
	@Column(name = "i_r_angle")
	private Double iRAngle;//	numeric
	
	@Column(name = "i_y_angle")
	private Double iYAngle;	//numeric
	
	@Column(name = "i_b_angle")
	private Double iBAngle;//	numeric
	
	@Column(name = "frequency")
	private Double frequency;	//numeric
	
	@Column(name = "kwh_exp")
	private Double kwhExp;//	numeric
	
	@Column(name = "kwh_imp")
	private Double kwhImp;	//numeric
	
	@Column(name = "kvah_exp")
	private Double kvahExp;	//numeric
	
	@Column(name = "kvah_imp")
	private Double kvahImp;	//numeric
	
	@Column(name = "power_kw")
	private Double powerKw;	//numeric
	
	@Column(name = "kvar")
	private Double kvar;	//numeric
	
	@Column(name = "kvarh_lag")
	private Double kvarhLag;	//numeric
	
	@Column(name = "kvarh_lead")
	private Double kvarhLead;	//numeric
	
	@Column(name = "power_off_count")
	private Integer powerOffCount;	//numeric
	
	@Column(name = "power_off_duration")
	private Integer powerOffDuration;	//numeric
	
	@Column(name = "md_reset_count")
	private Integer mdResetCount;	//numeric
	
	@Column(name = "programming_count")
	private Integer programmingCount;	//numeric
	
	@Column(name = "tamper_count")
	private Integer tamperCount;	//numeric
	
	@Column(name = "md_reset_date")
	private String mdResetDate;	//varchar
	
	@Column(name = "md_kw")
	private Double mdKw;	//numeric
	
	@Column(name = "date_md_kw")
	private String dateMdKw;	//varchar
	
	@Column(name = "md_kva")
	private Double mdKva;	//numeric
	
	@Column(name = "date_md_kva")
	private String dateMdKva;//	varchar
	
	
	@Column(name = "modem_time")
	private Timestamp modemTime;	
	
	@Column(name = "read_time")
	private Timestamp readTime;	
	
	@Column(name = "imei")
	private String imei;	//varchar
	
	@Column(name = "trans_id")
	private String transId;	//varchar
	
	@Column(name = "meter_number")
	private String meterNumber;	


	

	@Column(name = "date")
	private Date date;	//varchar
	
	//VISHWANATH SIR
/*	@Column(name = "R_PHASE_REACTIVE_VAL")
	private float rPhasereActiveVal;
	
	@Column(name = "Y_PHASE_REACTIVE_VAL") 
	private float yPhasereActiveVal;
	
	@Column(name = "B_PHASE_REACTIVE_VAL")
	private float bPhasereActiveVal;*/
	
	@Column(name = "R_PHASE_REACTIVE_CURRENT") 
	private float rphaseReactiveCurrent;
	
	@Column(name = "Y_PHASE_REACTIVE_CURRENT")
	private float yphaseReactiveCurrent;
	
	@Column(name = "B_PHASE_REACTIVE_CURRENT")
	private float bphaseReactiveCurrent;
	
	@Column(name = "R_VOLTAGE_ANGLE")      
	private float r_voltageAngle;
	
	@Column(name = "Y_VOLTAGE_ANGLE")
	private float y_voltageAngle;
	
	@Column(name = "B_VOLTAGE_ANGLE")
	private float b_voltageAngle;
	
/*	@Column(name = "ACTIVE_POWER")
	private float active_power;
	
	@Column(name = "APPARENT_POWER")
	private float apparent_power;*/
	

	public Double getKvah() {
		return kvah;
	}

	public void setKvah(Double kvah) {
		this.kvah = kvah;
	}

	public Double getKva() {
		return kva;
	}

	public void setKva(Double kva) {
		this.kva = kva;
	}

	public Double getiRAngle() {
		return iRAngle;
	}

	public void setiRAngle(Double iRAngle) {
		this.iRAngle = iRAngle;
	}

	public Double getiYAngle() {
		return iYAngle;
	}

	public void setiYAngle(Double iYAngle) {
		this.iYAngle = iYAngle;
	}

	public Double getiBAngle() {
		return iBAngle;
	}

	public void setiBAngle(Double iBAngle) {
		this.iBAngle = iBAngle;
	}

	
	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}

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

	public Double getKvahExp() {
		return kvahExp;
	}

	public void setKvahExp(Double kvahExp) {
		this.kvahExp = kvahExp;
	}

	public Double getKvahImp() {
		return kvahImp;
	}

	public void setKvahImp(Double kvahImp) {
		this.kvahImp = kvahImp;
	}

	public Double getPowerKw() {
		return powerKw;
	}

	public void setPowerKw(Double powerKw) {
		this.powerKw = powerKw;
	}

	public Double getKvar() {
		return kvar;
	}

	public void setKvar(Double kvar) {
		this.kvar = kvar;
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

	public Integer getPowerOffCount() {
		return powerOffCount;
	}

	public void setPowerOffCount(Integer powerOffCount) {
		this.powerOffCount = powerOffCount;
	}

	public Integer getPowerOffDuration() {
		return powerOffDuration;
	}

	public void setPowerOffDuration(Integer powerOffDuration) {
		this.powerOffDuration = powerOffDuration;
	}

	public Integer getMdResetCount() {
		return mdResetCount;
	}

	public void setMdResetCount(Integer mdResetCount) {
		this.mdResetCount = mdResetCount;
	}

	public Integer getProgrammingCount() {
		return programmingCount;
	}

	public void setProgrammingCount(Integer programmingCount) {
		this.programmingCount = programmingCount;
	}

	public Integer getTamperCount() {
		return tamperCount;
	}

	public void setTamperCount(Integer tamperCount) {
		this.tamperCount = tamperCount;
	}

	public String getMdResetDate() {
		return mdResetDate;
	}

	public void setMdResetDate(String mdResetDate) {
		this.mdResetDate = mdResetDate;
	}

	public Double getMdKw() {
		return mdKw;
	}

	public void setMdKw(Double mdKw) {
		this.mdKw = mdKw;
	}

	public String getDateMdKw() {
		return dateMdKw;
	}

	public void setDateMdKw(String dateMdKw) {
		this.dateMdKw = dateMdKw;
	}

	public Double getMdKva() {
		return mdKva;
	}

	public void setMdKva(Double mdKva) {
		this.mdKva = mdKva;
	}

	public String getDateMdKva() {
		return dateMdKva;
	}

	public void setDateMdKva(String dateMdKva) {
		this.dateMdKva = dateMdKva;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCdfId() {
		return cdfId;
	}

	public void setCdfId(int cdfId) {
		this.cdfId = cdfId;
	}


	public float getrPhaseVal()
	{
		return rPhaseVal;
	}

	public void setrPhaseVal(float rPhaseVal)
	{
		this.rPhaseVal = rPhaseVal;
	}

	public float getbPhaseVal()
	{
		return bPhaseVal;
	}

	public void setbPhaseVal(float bPhaseVal)
	{
		this.bPhaseVal = bPhaseVal;
	}

	public float getyPhaseVal()
	{
		return yPhaseVal;
	}

	public void setyPhaseVal(float yPhaseVal)
	{
		this.yPhaseVal = yPhaseVal;
	}

	public float getrPhaseLineVal()
	{
		return rPhaseLineVal;
	}

	public void setrPhaseLineVal(float rPhaseLineVal)
	{
		this.rPhaseLineVal = rPhaseLineVal;
	}

	public float getyPhaseLineVal()
	{
		return yPhaseLineVal;
	}

	public void setyPhaseLineVal(float yPhaseLineVal)
	{
		this.yPhaseLineVal = yPhaseLineVal;
	}

	public float getbPhaseLineVal()
	{
		return bPhaseLineVal;
	}

	public void setbPhaseLineVal(float bPhaseLineVal)
	{
		this.bPhaseLineVal = bPhaseLineVal;
	}

	public float getrPhaseActiveVal()
	{
		return rPhaseActiveVal;
	}

	public void setrPhaseActiveVal(float rPhaseActiveVal)
	{
		this.rPhaseActiveVal = rPhaseActiveVal;
	}

	public float getyPhaseActiveVal()
	{
		return yPhaseActiveVal;
	}

	public void setyPhaseActiveVal(float yPhaseActiveVal)
	{
		this.yPhaseActiveVal = yPhaseActiveVal;
	}

	public float getbPhaseActiveVal()
	{
		return bPhaseActiveVal;
	}

	public void setbPhaseActiveVal(float bPhaseActiveVal)
	{
		this.bPhaseActiveVal = bPhaseActiveVal;
	}

	public float getrPhasePfVal()
	{
		return rPhasePfVal;
	}

	public void setrPhasePfVal(float rPhasePfVal)
	{
		this.rPhasePfVal = rPhasePfVal;
	}

	public float getyPhasePfVal()
	{
		return yPhasePfVal;
	}

	public void setyPhasePfVal(float yPhasePfVal)
	{
		this.yPhasePfVal = yPhasePfVal;
	}

	public float getbPhasePfVal()
	{
		return bPhasePfVal;
	}

	public void setbPhasePfVal(float bPhasePfVal)
	{
		this.bPhasePfVal = bPhasePfVal;
	}

	public float getAvgPfVal()
	{
		return avgPfVal;
	}

	public void setAvgPfVal(float avgPfVal)
	{
		this.avgPfVal = avgPfVal;
	}

	public float getActivePowerVal()
	{
		return activePowerVal;
	}

	public void setActivePowerVal(float activePowerVal)
	{
		this.activePowerVal = activePowerVal;
	}

	public double getMf()
	{
		return mf;
	}

	public void setMf(double mf)
	{
		this.mf = mf;
	}

	public String getPhaseSequence() {
		return phaseSequence;
	}

	public void setPhaseSequence(String phaseSequence) {
		this.phaseSequence = phaseSequence;
	}

	public CDFData getCdfData() {
		return cdfData;
	}

	public void setCdfData(CDFData cdfData) {
		this.cdfData = cdfData;
	}

	public String getrPhaseCuurentAngle() {
		return rPhaseCuurentAngle;
	}

	public void setrPhaseCuurentAngle(String rPhaseCuurentAngle) {
		this.rPhaseCuurentAngle = rPhaseCuurentAngle;
	}

	public String getyPhaseCuurentAngle() {
		return yPhaseCuurentAngle;
	}

	public void setyPhaseCuurentAngle(String yPhaseCuurentAngle) {
		this.yPhaseCuurentAngle = yPhaseCuurentAngle;
	}

	public String getbPhaseCuurentAngle() {
		return bPhaseCuurentAngle;
	}

	public void setbPhaseCuurentAngle(String bPhaseCuurentAngle) {
		this.bPhaseCuurentAngle = bPhaseCuurentAngle;
	}

	public Timestamp getModemTime() {
		return modemTime;
	}

	public void setModemTime(Timestamp modemTime) {
		this.modemTime = modemTime;
	}

	public Timestamp getReadTime() {
		return readTime;
	}

	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getD2_kwh() {
		return d2_kwh;
	}

	public void setD2_kwh(float d2_kwh) {
		this.d2_kwh = d2_kwh;
	}


	/*public float getrPhasereActiveVal() {
		return rPhasereActiveVal;
	}


	public void setrPhasereActiveVal(float rPhasereActiveVal) {
		this.rPhasereActiveVal = rPhasereActiveVal;
	}

	public float getyPhasereActiveVal() {
		return yPhasereActiveVal;
	}

	public void setyPhasereActiveVal(float yPhasereActiveVal) {
		this.yPhasereActiveVal = yPhasereActiveVal;
	}

	public float getbPhasereActiveVal() {
		return bPhasereActiveVal;
	}

	public void setbPhasereActiveVal(float bPhasereActiveVal) {
		this.bPhasereActiveVal = bPhasereActiveVal;
	}*/

	public float getRphaseReactiveCurrent() {
		return rphaseReactiveCurrent;
	}

	public void setRphaseReactiveCurrent(float rphaseReactiveCurrent) {
		this.rphaseReactiveCurrent = rphaseReactiveCurrent;
	}

	public float getYphaseReactiveCurrent() {
		return yphaseReactiveCurrent;
	}

	public void setYphaseReactiveCurrent(float yphaseReactiveCurrent) {
		this.yphaseReactiveCurrent = yphaseReactiveCurrent;
	}

	public float getBphaseReactiveCurrent() {
		return bphaseReactiveCurrent;
	}

	public void setBphaseReactiveCurrent(float bphaseReactiveCurrent) {
		this.bphaseReactiveCurrent = bphaseReactiveCurrent;
	}

	public float getR_voltageAngle() {
		return r_voltageAngle;
	}

	public void setR_voltageAngle(float r_voltageAngle) {
		this.r_voltageAngle = r_voltageAngle;
	}

	public float getY_voltageAngle() {
		return y_voltageAngle;
	}

	public void setY_voltageAngle(float y_voltageAngle) {
		this.y_voltageAngle = y_voltageAngle;
	}

	public float getB_voltageAngle() {
		return b_voltageAngle;
	}

	public void setB_voltageAngle(float b_voltageAngle) {
		this.b_voltageAngle = b_voltageAngle;
	}

/*	public float getActive_power() {
		return active_power;
	}

	public void setActive_power(float active_power) {
		this.active_power = active_power;
	}

	public float getApparent_power() {
		return apparent_power;
	}

	public void setApparent_power(float apparent_power) {
		this.apparent_power = apparent_power;
	}
*/
}
