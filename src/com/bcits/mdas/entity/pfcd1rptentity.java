package com.bcits.mdas.entity;
	
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "pfc_d1_rpt" , schema="meter_data")

	public class pfcd1rptentity {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)

		@Column(name ="rec_id")
		private int id;
		
		@Column(name = "month_year")
		private String monthYear;
		
		@Column(name = "town")
		private String town;
		
		@Column(name = "town_type")
		private String townType;
		
		@Column(name = "baseline_loss")
		private Double baselineLoss;
		
		@Column(name = "bill_eff")
		private Double billEff;
		
		@Column(name = "coll_eff")
		private Double collEff;
		
		@Column(name = "atc_loss")
		private Double atcLoss;
			
		@Column(name = "time_stamp")
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

		public String getTown() {
			return town;
		}

		public void setTown(String town) {
			this.town = town;
		}

		public String getTownType() {
			return townType;
		}

		public void setTownType(String townType) {
			this.townType = townType;
		}

		public Double getBaselineLoss() {
			return baselineLoss;
		}

		public void setBaselineLoss(Double baselineLoss) {
			this.baselineLoss = baselineLoss;
		}

		public Double getBillEff() {
			return billEff;
		}

		public void setBillEff(Double billEff) {
			this.billEff = billEff;
		}

		public Double getCollEff() {
			return collEff;
		}

		public void setCollEff(Double collEff) {
			this.collEff = collEff;
		}

		public Double getAtcLoss() {
			return atcLoss;
		}

		public void setAtcLoss(Double atcLoss) {
			this.atcLoss = atcLoss;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
		}

		
}


