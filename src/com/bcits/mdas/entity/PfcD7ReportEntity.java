	package com.bcits.mdas.entity;
	
	import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "pfc_d7_rpt" , schema="meter_data")

	public class PfcD7ReportEntity {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)

		@Column(name ="rec_id")
		private int id;
		
		@Column(name = "month_year")
		private String monthYear;
		
		@Column(name = "town")
		private String town;
		
		@Column(name = "consumer_cnt")
		private Double consumerCnt;
		
		@Column(name = "online_payment")
		private Double onlinePayment;
		
		@Column(name = "total_amt_collected")
		private Double totalAmtCollected;
		
		@Column(name = "online_amt")
		private Double onlineAmt;
			
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

		public String getTown() {
			return town;
		}

		public void setTown(String town) {
			this.town = town;
		}

		public Double getConsumerCnt() {
			return consumerCnt;
		}

		public void setConsumerCnt(Double consumerCnt) {
			this.consumerCnt = consumerCnt;
		}

		public Double getOnlinePayment() {
			return onlinePayment;
		}

		public void setOnlinePayment(Double onlinePayment) {
			this.onlinePayment = onlinePayment;
		}

		public Double getTotalAmtCollected() {
			return totalAmtCollected;
		}

		public void setTotalAmtCollected(Double totalAmtCollected) {
			this.totalAmtCollected = totalAmtCollected;
		}

		public Double getOnlineAmt() {
			return onlineAmt;
		}

		public void setOnlineAmt(Double onlineAmt) {
			this.onlineAmt = onlineAmt;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
		}

}
