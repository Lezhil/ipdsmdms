package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
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
@Table(name="D5_DATA" , schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="D5Data.Events",query="SELECT d5.eventTime,d5.eventCode,d5.rPhaseVal,d5.yPhaseVal,d5.bPhaseVal,d5.rPhaseLineVal,d5.yPhaseLineVal,d5.bPhaseLineVal,d5.rPhasePfVal,d5.yPhasePfVal,d5.bPhasePfVal,d5.kwh,e.eventDescription FROM D5Data d5,CDFData c,EventMaster e WHERE d5.CdfId=c.id AND c.meterNo=:meterNo AND c.billmonth=:billMonth AND d5.eventCode=e.eventCode ORDER BY d5.eventTime DESC")
	/*@NamedQuery(name="D5Data.getEventDetails",query="SELECT c.meterNo,d5.d5Id,d5.eventCode,e.eventDescription,d5.eventTime,d5.eventStatus,"
			+ "d5.rPhaseVal,d5.bPhaseVal,d5.yPhaseVal,d5.rPhaseLineVal,d5.yPhaseLineVal,d5.bPhaseLineVal,d5.rPhaseActiveVal,d5.yPhaseActiveVal,d5.bPhaseActiveVal,"
			+ "d5.rPhasePfVal,d5.yPhasePfVal,d5.bPhasePfVal,d5.avgPfVal,d5.activePowerVal,d5.mf,d5.phaseSequence,(SELECT DISTINCT m.accno from Master m ,MeterMaster mm WHERE m.accno=mm.accno AND mm.metrno=c.meterNo LIMIT 1) ,"
			+ "(SELECT DISTINCT m.name from Master m ,MeterMaster mm WHERE m.accno=mm.accno AND mm.metrno=c.meterNo LIMIT 1),"
			+ "(SELECT DISTINCT m.contractdemand from Master m ,MeterMaster mm WHERE m.accno=mm.accno AND mm.metrno=c.meterNo LIMIT 1) "
			+ "FROM D5Data d5,CDFData c,EventMaster e WHERE d5.CdfId=c.id AND d5.eventCode=e.eventCode  AND d5.eventCode =:eventCode AND d5.eventTime BETWEEN :fromDate AND :toDate ORDER BY c.meterNo")*/
})


public class D5Data
{
	/*@Id
	@SequenceGenerator(name = "d5Id", sequenceName = "d5_data_id2")
	@GeneratedValue(generator = "d5Id")	*/
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	/*@Id
	@GeneratedValue(strategy=GenerationType.AUTO)*/
	 @Id
	   /* @SequenceGenerator(name="d5_id_new",
	                       sequenceName="d5_data_id_new",
	                       allocationSize=1)
	    @GeneratedValue(strategy = GenerationType.SEQUENCE,
	                    generator="d5_id_new")*/
	   
	@Column(name="D5_ID",updatable=false)
	private int d5Id;
	
	@Column(name="CDF_ID")
	private int CdfId;
	
	@Column(name="EVENT_CODE")
	private int eventCode;
	
	@Column(name="EVENT_TIME")
	private Timestamp eventTime;
	
	@Column(name="EVENT_STATUS")
	private String eventStatus;
	
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
	
	@Column(name = "PHASE_SEQUENCE")
	private String phaseSequence;
	
	@Column(name = "D5_KWH")
	private float d5_kwh;
	
	
	@Column(name = "time_stamp")
	private Timestamp timeStamp;
	
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
	
	@Column(name = "pf_r")
	private Double pfR;	//numeric
	
	@Column(name = "pf_y")
	private Double pfY;	//numeric
	
	@Column(name = "pf_b")
	private Double pfB;//	numeric
	
	@Column(name = "kwh")
	private Double kwh;	//numeric
	
	@Column(name = "kvah")
	private Double kvah;	//numeric
	
	@Column(name = "kwh_exp")
	private Double kwhExp;//	numeric
	
	@Column(name = "kwh_imp")
	private Double kwhImp;	//numeric
	
	@Column(name = "energy_kwh")
	private Double energyKwh;	//numeric
	
	@Column(name = "energy_kwh_import")
	private Double energyKwhImport;	//numeric
	
	@Column(name = "energy_kwh_export")
	private Double energyKwhExport;	//numeric
	
	@Column(name = "energy_kvah")
	private Double energyKvah;//	numeric
	
	@Column(name = "structure_size")
	private Integer structureSize;//	numeric
 
	@Column(name = "trans_id")
	private String transId;	//varchar
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CDF_ID",insertable = false, updatable = false, nullable = false)
	private CDFData cdfData;*/
	
	public String getEventStatus()
	{
		return eventStatus;
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

	public Double getPfR() {
		return pfR;
	}

	public void setPfR(Double pfR) {
		this.pfR = pfR;
	}

	public Double getPfY() {
		return pfY;
	}

	public void setPfY(Double pfY) {
		this.pfY = pfY;
	}

	public Double getPfB() {
		return pfB;
	}

	public void setPfB(Double pfB) {
		this.pfB = pfB;
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

	public Double getEnergyKwh() {
		return energyKwh;
	}

	public void setEnergyKwh(Double energyKwh) {
		this.energyKwh = energyKwh;
	}

	public Double getEnergyKwhImport() {
		return energyKwhImport;
	}

	public void setEnergyKwhImport(Double energyKwhImport) {
		this.energyKwhImport = energyKwhImport;
	}

	public Double getEnergyKwhExport() {
		return energyKwhExport;
	}

	public void setEnergyKwhExport(Double energyKwhExport) {
		this.energyKwhExport = energyKwhExport;
	}

	public Double getEnergyKvah() {
		return energyKvah;
	}

	public void setEnergyKvah(Double energyKvah) {
		this.energyKvah = energyKvah;
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

	public void setEventStatus(String eventStatus)
	{
		this.eventStatus = eventStatus;
	}

	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="D5_ID",referencedColumnName="D5_ID",insertable=false,updatable = false)
	private D5_Snapshot d5_Snapshot;
	
	
	
	public D5_Snapshot getD5_Snapshot() {
		return d5_Snapshot;
	}

	public void setD5_Snapshot(D5_Snapshot d5_Snapshot) {
		this.d5_Snapshot = d5_Snapshot;
	}

	public D5Data()
	{
		
	}

	public int getD5Id()
	{
		return d5Id;
	}

	public void setD5Id(int d5Id)
	{
		this.d5Id = d5Id;
	}

	public int getCdfId()
	{
		return CdfId;
	}

	public void setCdfId(int cdfId)
	{
		CdfId = cdfId;
	}

	public int getEventCode()
	{
		return eventCode;
	}

	public void setEventCode(int eventCode)
	{
		this.eventCode = eventCode;
	}

	public Timestamp getEventTime()
	{
		return eventTime;
	}

	public void setEventTime(Timestamp eventTime)
	{
		this.eventTime = eventTime;
	}

	public float getrPhaseVal() {
		return rPhaseVal;
	}

	public void setrPhaseVal(float rPhaseVal) {
		this.rPhaseVal = rPhaseVal;
	}

	public float getbPhaseVal() {
		return bPhaseVal;
	}

	public void setbPhaseVal(float bPhaseVal) {
		this.bPhaseVal = bPhaseVal;
	}

	public float getyPhaseVal() {
		return yPhaseVal;
	}

	public void setyPhaseVal(float yPhaseVal) {
		this.yPhaseVal = yPhaseVal;
	}

	public float getrPhaseLineVal() {
		return rPhaseLineVal;
	}

	public void setrPhaseLineVal(float rPhaseLineVal) {
		this.rPhaseLineVal = rPhaseLineVal;
	}

	public float getyPhaseLineVal() {
		return yPhaseLineVal;
	}

	public void setyPhaseLineVal(float yPhaseLineVal) {
		this.yPhaseLineVal = yPhaseLineVal;
	}

	public float getbPhaseLineVal() {
		return bPhaseLineVal;
	}

	public void setbPhaseLineVal(float bPhaseLineVal) {
		this.bPhaseLineVal = bPhaseLineVal;
	}

	public float getrPhaseActiveVal() {
		return rPhaseActiveVal;
	}

	public void setrPhaseActiveVal(float rPhaseActiveVal) {
		this.rPhaseActiveVal = rPhaseActiveVal;
	}

	public float getyPhaseActiveVal() {
		return yPhaseActiveVal;
	}

	public void setyPhaseActiveVal(float yPhaseActiveVal) {
		this.yPhaseActiveVal = yPhaseActiveVal;
	}

	public float getbPhaseActiveVal() {
		return bPhaseActiveVal;
	}

	public void setbPhaseActiveVal(float bPhaseActiveVal) {
		this.bPhaseActiveVal = bPhaseActiveVal;
	}

	public float getrPhasePfVal() {
		return rPhasePfVal;
	}

	public void setrPhasePfVal(float rPhasePfVal) {
		this.rPhasePfVal = rPhasePfVal;
	}

	public float getyPhasePfVal() {
		return yPhasePfVal;
	}

	public void setyPhasePfVal(float yPhasePfVal) {
		this.yPhasePfVal = yPhasePfVal;
	}

	public float getbPhasePfVal() {
		return bPhasePfVal;
	}

	public void setbPhasePfVal(float bPhasePfVal) {
		this.bPhasePfVal = bPhasePfVal;
	}

	public float getAvgPfVal() {
		return avgPfVal;
	}

	public void setAvgPfVal(float avgPfVal) {
		this.avgPfVal = avgPfVal;
	}

	public float getActivePowerVal() {
		return activePowerVal;
	}

	public void setActivePowerVal(float activePowerVal) {
		this.activePowerVal = activePowerVal;
	}

	public double getMf() {
		return mf;
	}

	public void setMf(double mf) {
		this.mf = mf;
	}

	public String getPhaseSequence() {
		return phaseSequence;
	}

	public void setPhaseSequence(String phaseSequence) {
		this.phaseSequence = phaseSequence;
	}

	public float getD5_kwh() {
		return d5_kwh;
	}

	public void setD5_kwh(float d5_kwh) {
		this.d5_kwh = d5_kwh;
	}
	
	/*public CDFData getCdfData() {
		return cdfData;
	}

	public void setCdfData(CDFData cdfData) {
		this.cdfData = cdfData;
	}*/
	
	

}
