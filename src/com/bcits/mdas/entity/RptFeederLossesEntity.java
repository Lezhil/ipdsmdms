package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "rpt_ea_feeder_losses" , schema="meter_data")

	public class RptFeederLossesEntity {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)

		@Column(name ="rec_id")
		private int id;
		
		@Column(name = "month_year")
		private String monthYear;
		
		@Column(name = "office_id")
		private Integer officeId;
		
		@Column(name = "fdr_id")
		private String fdrId;
		
		@Column(name = "tp_fdr_id")
		private String tpFdrId; 
		
		@Column(name = "fdr_name")
		private String fdrName;
		
		@Column(name = "meter_sr_number")
		private String meterSrNumber;
		
		@Column(name = "tot_consumers")
		private Integer totConsumers;
		
		@Column(name = "unit_supply")
		private Double unitSupply;
		
		@Column(name = "unit_billed")
		private Double unitBilled;
		
		@Column(name = "amt_billed")
		private Double amtBilled;
		
		@Column(name = "amt_collected")
		private Double amtCollected;
		
		@Column(name = "tech_loss")
		private Double techLoss;
			
		@Column(name = "timestamp")
		private Timestamp timestamp;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getMonthYear() {
			return monthYear;
		}

		public void setMonthYear(String monthYear) {
			this.monthYear = monthYear;
		}

		public Integer getOfficeId() {
			return officeId;
		}

		public void setOfficeId(Integer officeId) {
			this.officeId = officeId;
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

		public String getFdrName() {
			return fdrName;
		}

		public void setFdrName(String fdrName) {
			this.fdrName = fdrName;
		}

		public String getMeterSrNumber() {
			return meterSrNumber;
		}

		public void setMeterSrNumber(String meterSrNumber) {
			this.meterSrNumber = meterSrNumber;
		}

		public Integer getTotConsumers() {
			return totConsumers;
		}

		public void setTotConsumers(Integer totConsumers) {
			this.totConsumers = totConsumers;
		}

		public Double getUnitSupply() {
			return unitSupply;
		}

		public void setUnitSupply(Double unitSupply) {
			this.unitSupply = unitSupply;
		}

		public Double getUnitBilled() {
			return unitBilled;
		}

		public void setUnitBilled(Double unitBilled) {
			this.unitBilled = unitBilled;
		}

		public Double getAmtBilled() {
			return amtBilled;
		}

		public void setAmtBilled(Double amtBilled) {
			this.amtBilled = amtBilled;
		}

		public Double getAmtCollected() {
			return amtCollected;
		}

		public void setAmtCollected(Double amtCollected) {
			this.amtCollected = amtCollected;
		}

		public Double getTechLoss() {
			return techLoss;
		}

		public void setTechLoss(Double techLoss) {
			this.techLoss = techLoss;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
		}

		
}




