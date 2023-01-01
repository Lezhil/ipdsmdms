package com.bcits.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "AMR_FULL_METER_DATA", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "METER_NUMBER", "READING_MONTH" }) })
@NamedQueries({

})
public class AmrMeterData {

	@EmbeddedId // FOR MAKING UNIQUE KEY
	private CustomKey myKey; // EVENT CODE, EVENT TIME, METER NUMBER

	@Column(name = "CREATED_TIME")
	private Date createdTime;

	@Column(name = "MANUFACTURER")
	private String manufacturer;

	@Column(name = "LOGICAL_NAME")
	private String logicalName;

	@Column(name = "METER_TYPE")
	private String meterType;

	@Column(name = "FIRMWARE_VERSION")
	private String firmwareVersion;

	@Column(name = "YEAR_OF_MANUFACTURE")
	private String yearOfManufacture;

	@Column(name = "METER_DATE")
	private String meterDate;

	@Column(name = "KWH")
	private String kwh;

	@Column(name = "KVAH")
	private String kvah;

	@Column(name = "KVA")
	private String kva;

	@Column(name = "VOLTAGE_1")
	private String voltage1;

	@Column(name = "VOLTAGE_2")
	private String voltage2;

	@Column(name = "VOLTAGE_3")
	private String voltage3;

	@Column(name = "LINE_CURRENT_1")
	private String lineCurrent1;

	@Column(name = "LINE_CURRENT_2")
	private String lineCurrent2;

	@Column(name = "LINE_CURRENT_3")
	private String lineCurrent3;

	@Column(name = "POWER_FACOR_1")
	private String powerFacor1;

	@Column(name = "POWER_FACOR_2")
	private String powerFacor2;

	@Column(name = "POWER_FACOR_3")
	private String powerFacor3;

	@Column(name = "PF_3_PHASE")
	private String pf3Phase;

	@Column(name = "FREQUENCY")
	private String frequency;

	@Column(name = "BILLING_DATE")
	private String billingDate;

	@Column(name = "NO_OF_POWER_FAILURS")
	private String noOfPowerFailurs;

	@Column(name = "POWER_FAIL_DURATION")
	private String powerFailDuration;

	@Column(name = "CUMULATIVE_TAMPER_COUNT")
	private String cumulativeTamperCount;

	@Column(name = "SIGNED_ACTIVE_POWER")
	private String signedActivePower;

	@Column(name = "CUMULATIVE_BILLING_COUNT")
	private String cumulativeBillingCount;

	@Column(name = "CUMULATIVE_PROGRAMMING_COUNT")
	private String cumulativeProgrammingCount;

	@Column(name = "CUMULATIVE_ENERGY_KVARH_LAG")
	private String cumulativeEnergyKvarhLag;

	@Column(name = "CUMULATIVE_ENERGY_KVARH_LEAD")
	private String cumulativeEnergyKvarhLead;

	@Column(name = "SIGNED_REACTIVE_POWER")
	private String signedReactivePower;

	@Column(name = "DEMAND_INTEGRATION_PERIOD")
	private String demandIntegrationPeriod;

	@Column(name = "INTERNAL_PT_RATIO")
	private String internalPtRatio;

	@Column(name = "INTERNAL_CT_RATIO")
	private String internalCtRatio;

	@Column(name = "EVENT_HISTORY")
	private String eventHistory;

	@Column(name = "LOAD_SURVEY_DATA")
	@Lob
	private String loadSurveyData;

	@Column(name = "BILLING_HISTORY")
	private String billingHistory;

	@Column(name = "COLUMN_STRUCTURE")
	private String columnStructure;

	@Column(name = "TAKEN_TIME")
	private String takenTime;

	public AmrMeterData() {

	}

	public CustomKey getMyKey() {
		return myKey;
	}

	public void setMyKey(CustomKey myKey) {
		this.myKey = myKey;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getLogicalName() {
		return logicalName;
	}

	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setYearOfManufacture(String yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	public String getMeterDate() {
		return meterDate;
	}

	public void setMeterDate(String meterDate) {
		this.meterDate = meterDate;
	}

	public String getKwh() {
		return kwh;
	}

	public void setKwh(String kwh) {
		this.kwh = kwh;
	}

	public String getKvah() {
		return kvah;
	}

	public void setKvah(String kvah) {
		this.kvah = kvah;
	}

	public String getKva() {
		return kva;
	}

	public void setKva(String kva) {
		this.kva = kva;
	}

	public String getVoltage1() {
		return voltage1;
	}

	public void setVoltage1(String voltage1) {
		this.voltage1 = voltage1;
	}

	public String getVoltage2() {
		return voltage2;
	}

	public void setVoltage2(String voltage2) {
		this.voltage2 = voltage2;
	}

	public String getVoltage3() {
		return voltage3;
	}

	public void setVoltage3(String voltage3) {
		this.voltage3 = voltage3;
	}

	public String getLineCurrent1() {
		return lineCurrent1;
	}

	public void setLineCurrent1(String lineCurrent1) {
		this.lineCurrent1 = lineCurrent1;
	}

	public String getLineCurrent2() {
		return lineCurrent2;
	}

	public void setLineCurrent2(String lineCurrent2) {
		this.lineCurrent2 = lineCurrent2;
	}

	public String getLineCurrent3() {
		return lineCurrent3;
	}

	public void setLineCurrent3(String lineCurrent3) {
		this.lineCurrent3 = lineCurrent3;
	}

	public String getPowerFacor1() {
		return powerFacor1;
	}

	public void setPowerFacor1(String powerFacor1) {
		this.powerFacor1 = powerFacor1;
	}

	public String getPowerFacor2() {
		return powerFacor2;
	}

	public void setPowerFacor2(String powerFacor2) {
		this.powerFacor2 = powerFacor2;
	}

	public String getPowerFacor3() {
		return powerFacor3;
	}

	public void setPowerFacor3(String powerFacor3) {
		this.powerFacor3 = powerFacor3;
	}

	public String getPf3Phase() {
		return pf3Phase;
	}

	public void setPf3Phase(String pf3Phase) {
		this.pf3Phase = pf3Phase;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public String getNoOfPowerFailurs() {
		return noOfPowerFailurs;
	}

	public void setNoOfPowerFailurs(String noOfPowerFailurs) {
		this.noOfPowerFailurs = noOfPowerFailurs;
	}

	public String getPowerFailDuration() {
		return powerFailDuration;
	}

	public void setPowerFailDuration(String powerFailDuration) {
		this.powerFailDuration = powerFailDuration;
	}

	public String getCumulativeTamperCount() {
		return cumulativeTamperCount;
	}

	public void setCumulativeTamperCount(String cumulativeTamperCount) {
		this.cumulativeTamperCount = cumulativeTamperCount;
	}

	public String getSignedActivePower() {
		return signedActivePower;
	}

	public void setSignedActivePower(String signedActivePower) {
		this.signedActivePower = signedActivePower;
	}

	public String getCumulativeBillingCount() {
		return cumulativeBillingCount;
	}

	public void setCumulativeBillingCount(String cumulativeBillingCount) {
		this.cumulativeBillingCount = cumulativeBillingCount;
	}

	public String getCumulativeProgrammingCount() {
		return cumulativeProgrammingCount;
	}

	public void setCumulativeProgrammingCount(String cumulativeProgrammingCount) {
		this.cumulativeProgrammingCount = cumulativeProgrammingCount;
	}

	public String getCumulativeEnergyKvarhLag() {
		return cumulativeEnergyKvarhLag;
	}

	public void setCumulativeEnergyKvarhLag(String cumulativeEnergyKvarhLag) {
		this.cumulativeEnergyKvarhLag = cumulativeEnergyKvarhLag;
	}

	public String getCumulativeEnergyKvarhLead() {
		return cumulativeEnergyKvarhLead;
	}

	public void setCumulativeEnergyKvarhLead(String cumulativeEnergyKvarhLead) {
		this.cumulativeEnergyKvarhLead = cumulativeEnergyKvarhLead;
	}

	public String getSignedReactivePower() {
		return signedReactivePower;
	}

	public void setSignedReactivePower(String signedReactivePower) {
		this.signedReactivePower = signedReactivePower;
	}

	public String getDemandIntegrationPeriod() {
		return demandIntegrationPeriod;
	}

	public void setDemandIntegrationPeriod(String demandIntegrationPeriod) {
		this.demandIntegrationPeriod = demandIntegrationPeriod;
	}

	public String getInternalPtRatio() {
		return internalPtRatio;
	}

	public void setInternalPtRatio(String internalPtRatio) {
		this.internalPtRatio = internalPtRatio;
	}

	public String getInternalCtRatio() {
		return internalCtRatio;
	}

	public void setInternalCtRatio(String internalCtRatio) {
		this.internalCtRatio = internalCtRatio;
	}

	public String getEventHistory() {
		return eventHistory;
	}

	public void setEventHistory(String eventHistory) {
		this.eventHistory = eventHistory;
	}

	public String getLoadSurveyData() {
		return loadSurveyData;
	}

	public void setLoadSurveyData(String loadSurveyData) {
		this.loadSurveyData = loadSurveyData;
	}

	public String getBillingHistory() {
		return billingHistory;
	}

	public void setBillingHistory(String billingHistory) {
		this.billingHistory = billingHistory;
	}

	public String getColumnStructure() {
		return columnStructure;
	}

	public void setColumnStructure(String columnStructure) {
		this.columnStructure = columnStructure;
	}

	public String getTakenTime() {
		return takenTime;
	}

	public void setTakenTime(String takenTime) {
		this.takenTime = takenTime;
	}

	@Embeddable
	public static class CustomKey implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "METER_NUMBER")
		private String meterNumber;

		@Column(name = "READING_MONTH")
		private String readingMonth;

		public String getMeterNumber() {
			return meterNumber;
		}

		public void setMeterNumber(String meterNumber) {
			this.meterNumber = meterNumber;
		}

		public String getReadingMonth() {
			return readingMonth;
		}

		public void setReadingMonth(String readingMonth) {
			this.readingMonth = readingMonth;
		}

		public CustomKey(String meterNumber, String readingMonth) {
			super();
			this.meterNumber = meterNumber;
			this.readingMonth = readingMonth;
		}

		public CustomKey() {

		}

	}

}
