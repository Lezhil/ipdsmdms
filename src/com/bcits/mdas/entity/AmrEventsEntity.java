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
@Table(name = "EVENTS",  schema = "METER_DATA",uniqueConstraints = { @UniqueConstraint(columnNames = { "event_code", "event_time","meter_number" }) })
/*@NamedQueries({
	@NamedQuery(name = "HclMasterDataEntity.getPhoneDataSurvey", query = "SELECT bp.sdoName,bp.name,bp.address,bp.phone,bp.groupCode,bp.binderId,bp.legacyBinderId,bp.legacyAcctNo,bp.consumerCategory,bp.mtrSerialNbr,bp.meterNo,bp.saStatus,bp.subStation,bp.feeder,bp.poleNo,bp.dt ,bp.mrCode ,bp.myKey.acctId,bp.myKey.sdoCode  FROM HclMasterDataEntity bp Where bp.myKey.sdoCode=:sdoCode and  bp.mrCode=:mrCode and  bp.myKey.acctId IS NOT NULL and bp.surveyDbDate IS NULL")
	
	@NamedQuery(name= "AmrEventsEntity.getRecords", query= "select a.modemTime,a.vB from AmrEventsEntity a where a.meterNumber=:meterno ")


})*/

@NamedQueries
({
	
	@NamedQuery(name = "AmrEventsEntity.getRecords", query = "select a from AmrEventsEntity a where a.myKey.meterNumber=:meterno and to_char(a.myKey.eventTime, 'YYYY-MM-DD' ) =:fileDate ORDER BY a.myKey.eventTime")
})
public class AmrEventsEntity {

	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private KeyEvent myKey; // EVENT CODE, EVENT TIME, METER NUMBER
	   
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
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
	
	@Column(name="flag")
	private String flag; //varchar
	
	@Column(name="read_from")
	private String read_from;
	
	public String getRead_from() {
		return read_from;
	}

	public void setRead_from(String read_from) {
		this.read_from = read_from;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public KeyEvent getMyKey() {
		return myKey;
	}

	public void setMyKey(KeyEvent myKey) {
		this.myKey = myKey;
	}


	@Embeddable
	public static  class KeyEvent implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "meter_number")
		private String meterNumber;	

		@Column(name = "event_code")
		private String eventCode;	//varchar

		@Column(name = "event_time")
		private Timestamp eventTime;	

		
		
		public String getEventCode() {
			return eventCode;
		}

		public void setEventCode(String eventCode) {
			this.eventCode = eventCode;
		}

		public String getMeterNumber() {
			return meterNumber;
		}

		public void setMeterNumber(String meterNumber) {
			this.meterNumber = meterNumber;
		}

		public Timestamp getEventTime() {
			return eventTime;
		}

		public void setEventTime(Timestamp eventTime) {
			this.eventTime = eventTime;
		}

		public KeyEvent(String meterNumber, String eventCode,Timestamp eventTime) {
			super();
			this.meterNumber = meterNumber;
			this.eventTime = eventTime;
			this.eventCode=eventCode;
		}

		public KeyEvent(){
			
		}
	    
	}
	
	public AmrEventsEntity() {

	}
	
}
