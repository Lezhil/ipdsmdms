package com.bcits.mdas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(name = "npp_dt_rpt_monthly_calculation",  schema = "METER_DATA",uniqueConstraints = { @UniqueConstraint(columnNames = { "tpdtid","monthyear" }) })

public class NppDTrptMonthlyCalculation {
	
	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private AMIKeyNppDTrptMonthlyCalculation myKey; // String tpdtid, String monthyear
		
	@Column(name = "fdr_id")
	private String fdrId;
	
	@Column(name = "tp_fdr_id")
	private String tpFdrId;
		
	@Column(name = "officeid")
	private Integer officeid;	
	
	@Column(name = "fdrtype")
	private String fdrtype;
	
	@Column(name = "towncode")
	private String towncode;
	
	@Column(name = "dt_id")
	private String dtId;
			
	@Column(name = "meterno")
	private String meterNo;
	
	
	
	@Column(name = "dtname")
	private String dtName;
		
	@Column(name = "feedername")
	private String feederName;	
	
	@Column(name = "power_fail_freq")
	private Integer powerFailFreq;	//numeric
	
	@Column(name = "power_fail_duration")
	private Integer powerFailDuration;	//numeric
	
	@Column(name = "minimum_voltage")
	private Double minimumVoltage;	//numeric
	
	@Column(name = "maxCurrent")
	private Double maxCurrent;	//numeric
	
	@Column(name = "inputEnergy")
	private Double inputEnergy;	//numeric
	
	@Column(name = "exportEnergy")
	private Double exportEnergy;	//numeric
	
	@Column(name = "time_stamp")
	private Timestamp timeStamp;
	
	@Column(name = "update_time")
	private Timestamp updateTime;
	
		
	//Consumer Count Columns
	@Column(name = "ht_industrial_consumer_count")
	private Double htIndustrialConsumerCount;
	
	@Column(name = "ht_commercial_consumer_count")
	private Double htCommercialConsumerCount;
	
	@Column(name = "lt_industrial_consumer_count")
	private Double ltIndustrialConsumerCount;

	@Column(name = "lt_commercial_consumer_count")
	private Double ltCommercialConsumerCount;
	
	@Column(name = "lt_domestic_consumer_count")
	private Double ltDomesticConsumerCount;
	
	@Column(name = "govt_consumer_count")
	private Double govtConsumerCount;
	
	
	@Column(name = "agri_consumer_count")
	private Double agriConsumerCount;
	
	@Column(name = "others_consumer_count")
	private Double others_consumer_count;
	
	@Column(name = "hut_consumer_count")
	private Double hutConsumerCount;
		
		
	//Energy Billed Columns
	@Column(name = "ht_industrial_energy_billed")
	private Double htIndustrialEnergyBilled;
	
	@Column(name = "ht_commercial_energy_billed")
	private Double ht_commercialEnergyBilled;
	
	@Column(name = "lt_industrial_energy_billed")
	private Double lt_industrial_energy_billed;
	
	@Column(name = "lt_domestic_energy_billed")
	private Double lt_domestic_energy_billed;
	
	@Column(name = "lt_commercial_energy_billed")
	private Double lt_commercial_energy_billed;
		
	@Column(name = "govt_energy_billed")
	private Double govt_energy_billed;
	
	@Column(name = "agri_energy_billed")
	private Double agri_energy_billed;
	
	@Column(name = "others_energy_billed")
	private Double others_energy_billed;
	
	@Column(name = "hut_energy_billed")
	private Double hut_energy_billed;
	
	
	
	//Amount Billed Columns
	@Column(name = "ht_industrial_amount_billed")
	private Double ht_industrial_amount_billed;
	
	@Column(name = "ht_commercial_amount_billed")
	private Double ht_commercial_amount_billed;
	
	@Column(name = "lt_industrial_amount_billed")
	private Double lt_industrial_amount_billed;
	
	@Column(name = "lt_commercial_amount_billed")
	private Double lt_commercial_amount_billed;
	
	@Column(name = "lt_domestic_amount_billed")
	private Double lt_domestic_amount_billed;
	
	@Column(name = "govt_amount_billed")
	private Double govt_amount_billed;
		
	@Column(name = "agri_amount_billed")
	private Double agri_amount_billed;
	
	@Column(name = "others_amount_billed")
	private Double others_amount_billed;
	
	@Column(name = "hut_amount_billed")
	private Double hut_amount_billed;
	
	
	//Amount Collecte Columns
	@Column(name = "ht_industrial_amount_collected")
	private Double ht_industrial_amount_collected;
	
	@Column(name = "ht_commercial_amount_collected")
	private Double ht_commercial_amount_collected;
	
	@Column(name = "lt_industrial_amount_collected")
	private Double lt_industrial_amount_collected;
	
	@Column(name = "lt_commercial_amount_collected")
	private Double lt_commercial_amount_collected;
	
	@Column(name = "lt_domestic_amount_collected")
	private Double lt_domestic_amount_collected;
			
	@Column(name = "govt_amount_collected")
	private Double govt_amount_collected;
	
	@Column(name = "agri_amount_collected")
	private Double agri_amount_collected;
	
	@Column(name = "others_amount_collected")
	private Double others_amount_collected;
	
	@Column(name = "hut_amount_collected")
	private Double hut_amount_collected;
	
	
	//Open Access Units
	@Column(name = "open_access_units")
	private Double open_access_units;
	
	public AMIKeyNppDTrptMonthlyCalculation getMyKey() {
		return myKey;
	}





	public void setMyKey(AMIKeyNppDTrptMonthlyCalculation myKey) {
		this.myKey = myKey;
	}





	public String getFdrId() {
		return fdrId;
	}





	public void setFdrId(String fdrId) {
		this.fdrId = fdrId;
	}





	public String getTpFdrId() {
		return tpFdrId;
	}





	public void setTpFdrId(String tpFdrId) {
		this.tpFdrId = tpFdrId;
	}





	public Integer getOfficeid() {
		return officeid;
	}





	public void setOfficeid(Integer officeid) {
		this.officeid = officeid;
	}





	public String getFdrtype() {
		return fdrtype;
	}





	public void setFdrtype(String fdrtype) {
		this.fdrtype = fdrtype;
	}





	public String getTowncode() {
		return towncode;
	}
	
	public void setTowncode(String towncode) {
		this.towncode = towncode;
	}





	public String getDtId() {
		return dtId;
	}





	public void setDtId(String dtId) {
		this.dtId = dtId;
	}





	public String getMeterNo() {
		return meterNo;
	}





	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}





	public String getDtName() {
		return dtName;
	}





	public void setDtName(String dtName) {
		this.dtName = dtName;
	}





	public String getFeederName() {
		return feederName;
	}





	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}





	public Integer getPowerFailFreq() {
		return powerFailFreq;
	}





	public void setPowerFailFreq(Integer powerFailFreq) {
		this.powerFailFreq = powerFailFreq;
	}





	public Integer getPowerFailDuration() {
		return powerFailDuration;
	}





	public void setPowerFailDuration(Integer powerFailDuration) {
		this.powerFailDuration = powerFailDuration;
	}





	public Double getMinimumVoltage() {
		return minimumVoltage;
	}





	public void setMinimumVoltage(Double minimumVoltage) {
		this.minimumVoltage = minimumVoltage;
	}





	public Double getMaxCurrent() {
		return maxCurrent;
	}





	public void setMaxCurrent(Double maxCurrent) {
		this.maxCurrent = maxCurrent;
	}





	public Double getInputEnergy() {
		return inputEnergy;
	}





	public void setInputEnergy(Double inputEnergy) {
		this.inputEnergy = inputEnergy;
	}





	public Double getExportEnergy() {
		return exportEnergy;
	}





	public void setExportEnergy(Double exportEnergy) {
		this.exportEnergy = exportEnergy;
	}





	public Timestamp getTimeStamp() {
		return timeStamp;
	}





	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}





	public Timestamp getUpdateTime() {
		return updateTime;
	}





	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}





	public Double getHtIndustrialConsumerCount() {
		return htIndustrialConsumerCount;
	}





	public void setHtIndustrialConsumerCount(Double htIndustrialConsumerCount) {
		this.htIndustrialConsumerCount = htIndustrialConsumerCount;
	}





	public Double getHtCommercialConsumerCount() {
		return htCommercialConsumerCount;
	}





	public void setHtCommercialConsumerCount(Double htCommercialConsumerCount) {
		this.htCommercialConsumerCount = htCommercialConsumerCount;
	}





	public Double getLtIndustrialConsumerCount() {
		return ltIndustrialConsumerCount;
	}





	public void setLtIndustrialConsumerCount(Double ltIndustrialConsumerCount) {
		this.ltIndustrialConsumerCount = ltIndustrialConsumerCount;
	}





	public Double getLtCommercialConsumerCount() {
		return ltCommercialConsumerCount;
	}





	public void setLtCommercialConsumerCount(Double ltCommercialConsumerCount) {
		this.ltCommercialConsumerCount = ltCommercialConsumerCount;
	}





	public Double getLtDomesticConsumerCount() {
		return ltDomesticConsumerCount;
	}





	public void setLtDomesticConsumerCount(Double ltDomesticConsumerCount) {
		this.ltDomesticConsumerCount = ltDomesticConsumerCount;
	}





	public Double getGovtConsumerCount() {
		return govtConsumerCount;
	}





	public void setGovtConsumerCount(Double govtConsumerCount) {
		this.govtConsumerCount = govtConsumerCount;
	}





	public Double getAgriConsumerCount() {
		return agriConsumerCount;
	}





	public void setAgriConsumerCount(Double agriConsumerCount) {
		this.agriConsumerCount = agriConsumerCount;
	}





	public Double getOthers_consumer_count() {
		return others_consumer_count;
	}





	public void setOthers_consumer_count(Double others_consumer_count) {
		this.others_consumer_count = others_consumer_count;
	}





	public Double getHutConsumerCount() {
		return hutConsumerCount;
	}





	public void setHutConsumerCount(Double hutConsumerCount) {
		this.hutConsumerCount = hutConsumerCount;
	}





	public Double getHtIndustrialEnergyBilled() {
		return htIndustrialEnergyBilled;
	}





	public void setHtIndustrialEnergyBilled(Double htIndustrialEnergyBilled) {
		this.htIndustrialEnergyBilled = htIndustrialEnergyBilled;
	}





	public Double getHt_commercialEnergyBilled() {
		return ht_commercialEnergyBilled;
	}





	public void setHt_commercialEnergyBilled(Double ht_commercialEnergyBilled) {
		this.ht_commercialEnergyBilled = ht_commercialEnergyBilled;
	}





	public Double getLt_industrial_energy_billed() {
		return lt_industrial_energy_billed;
	}





	public void setLt_industrial_energy_billed(Double lt_industrial_energy_billed) {
		this.lt_industrial_energy_billed = lt_industrial_energy_billed;
	}





	public Double getLt_domestic_energy_billed() {
		return lt_domestic_energy_billed;
	}





	public void setLt_domestic_energy_billed(Double lt_domestic_energy_billed) {
		this.lt_domestic_energy_billed = lt_domestic_energy_billed;
	}





	public Double getLt_commercial_energy_billed() {
		return lt_commercial_energy_billed;
	}





	public void setLt_commercial_energy_billed(Double lt_commercial_energy_billed) {
		this.lt_commercial_energy_billed = lt_commercial_energy_billed;
	}





	public Double getGovt_energy_billed() {
		return govt_energy_billed;
	}





	public void setGovt_energy_billed(Double govt_energy_billed) {
		this.govt_energy_billed = govt_energy_billed;
	}





	public Double getAgri_energy_billed() {
		return agri_energy_billed;
	}





	public void setAgri_energy_billed(Double agri_energy_billed) {
		this.agri_energy_billed = agri_energy_billed;
	}





	public Double getOthers_energy_billed() {
		return others_energy_billed;
	}





	public void setOthers_energy_billed(Double others_energy_billed) {
		this.others_energy_billed = others_energy_billed;
	}





	public Double getHut_energy_billed() {
		return hut_energy_billed;
	}





	public void setHut_energy_billed(Double hut_energy_billed) {
		this.hut_energy_billed = hut_energy_billed;
	}





	public Double getHt_industrial_amount_billed() {
		return ht_industrial_amount_billed;
	}





	public void setHt_industrial_amount_billed(Double ht_industrial_amount_billed) {
		this.ht_industrial_amount_billed = ht_industrial_amount_billed;
	}





	public Double getHt_commercial_amount_billed() {
		return ht_commercial_amount_billed;
	}





	public void setHt_commercial_amount_billed(Double ht_commercial_amount_billed) {
		this.ht_commercial_amount_billed = ht_commercial_amount_billed;
	}





	public Double getLt_industrial_amount_billed() {
		return lt_industrial_amount_billed;
	}





	public void setLt_industrial_amount_billed(Double lt_industrial_amount_billed) {
		this.lt_industrial_amount_billed = lt_industrial_amount_billed;
	}





	public Double getLt_commercial_amount_billed() {
		return lt_commercial_amount_billed;
	}





	public void setLt_commercial_amount_billed(Double lt_commercial_amount_billed) {
		this.lt_commercial_amount_billed = lt_commercial_amount_billed;
	}





	public Double getLt_domestic_amount_billed() {
		return lt_domestic_amount_billed;
	}





	public void setLt_domestic_amount_billed(Double lt_domestic_amount_billed) {
		this.lt_domestic_amount_billed = lt_domestic_amount_billed;
	}





	public Double getGovt_amount_billed() {
		return govt_amount_billed;
	}





	public void setGovt_amount_billed(Double govt_amount_billed) {
		this.govt_amount_billed = govt_amount_billed;
	}





	public Double getAgri_amount_billed() {
		return agri_amount_billed;
	}





	public void setAgri_amount_billed(Double agri_amount_billed) {
		this.agri_amount_billed = agri_amount_billed;
	}





	public Double getOthers_amount_billed() {
		return others_amount_billed;
	}





	public void setOthers_amount_billed(Double others_amount_billed) {
		this.others_amount_billed = others_amount_billed;
	}





	public Double getHut_amount_billed() {
		return hut_amount_billed;
	}





	public void setHut_amount_billed(Double hut_amount_billed) {
		this.hut_amount_billed = hut_amount_billed;
	}





	public Double getHt_industrial_amount_collected() {
		return ht_industrial_amount_collected;
	}





	public void setHt_industrial_amount_collected(
			Double ht_industrial_amount_collected) {
		this.ht_industrial_amount_collected = ht_industrial_amount_collected;
	}





	public Double getHt_commercial_amount_collected() {
		return ht_commercial_amount_collected;
	}





	public void setHt_commercial_amount_collected(
			Double ht_commercial_amount_collected) {
		this.ht_commercial_amount_collected = ht_commercial_amount_collected;
	}





	public Double getLt_industrial_amount_collected() {
		return lt_industrial_amount_collected;
	}





	public void setLt_industrial_amount_collected(
			Double lt_industrial_amount_collected) {
		this.lt_industrial_amount_collected = lt_industrial_amount_collected;
	}





	public Double getLt_commercial_amount_collected() {
		return lt_commercial_amount_collected;
	}





	public void setLt_commercial_amount_collected(
			Double lt_commercial_amount_collected) {
		this.lt_commercial_amount_collected = lt_commercial_amount_collected;
	}





	public Double getLt_domestic_amount_collected() {
		return lt_domestic_amount_collected;
	}





	public void setLt_domestic_amount_collected(Double lt_domestic_amount_collected) {
		this.lt_domestic_amount_collected = lt_domestic_amount_collected;
	}





	public Double getGovt_amount_collected() {
		return govt_amount_collected;
	}





	public void setGovt_amount_collected(Double govt_amount_collected) {
		this.govt_amount_collected = govt_amount_collected;
	}





	public Double getAgri_amount_collected() {
		return agri_amount_collected;
	}





	public void setAgri_amount_collected(Double agri_amount_collected) {
		this.agri_amount_collected = agri_amount_collected;
	}





	public Double getOthers_amount_collected() {
		return others_amount_collected;
	}





	public void setOthers_amount_collected(Double others_amount_collected) {
		this.others_amount_collected = others_amount_collected;
	}





	public Double getHut_amount_collected() {
		return hut_amount_collected;
	}





	public void setHut_amount_collected(Double hut_amount_collected) {
		this.hut_amount_collected = hut_amount_collected;
	}





	public Double getOpen_access_units() {
		return open_access_units;
	}





	public void setOpen_access_units(Double open_access_units) {
		this.open_access_units = open_access_units;
	}





	@Embeddable
	public static  class AMIKeyNppDTrptMonthlyCalculation implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "tpdtid")
		private String tpdtid;	

		@Column(name = "monthyear")
		private String monthyear;	//varchar
		
		public AMIKeyNppDTrptMonthlyCalculation(String tpdtid, String monthyear) {
			super();
			this.tpdtid = tpdtid;
			this.monthyear = monthyear;
		}

		public String getTpdtid() {
			return tpdtid;
		}

		public void setTpdtid(String tpdtid) {
			this.tpdtid = tpdtid;
		}

		public String getMonthyear() {
			return monthyear;
		}

		public void setMonthyear(String monthyear) {
			this.monthyear = monthyear;
		}

		public AMIKeyNppDTrptMonthlyCalculation(){
		
		}
	    
	}
	
}
