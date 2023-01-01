package com.bcits.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "pfc_d7_rpt_intermediate" ,schema="meter_data",uniqueConstraints={@UniqueConstraint(columnNames = { "month_year","town" })})
public class PfcD7IntermediateEntity {
	

	@EmbeddedId
	private pfcD7IntermediateKey myKey;

	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="rec_id",insertable=false,updatable=false)
	private int recid;
	
	/*@Column(name = "month_year")
	private String monthYear;
	
	@Column(name = "town")
	private String town;*/
	
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

	@Column(name = "readtime")
	private Timestamp readtime;

	
	public int getRecid() {
		return recid;
	}

	public void setRecid(int recid) {
		this.recid = recid;
	}

	public double getConsumerCnt() {
		return consumerCnt;
	}

	public void setConsumerCnt(double consumerCnt) {
		this.consumerCnt = consumerCnt;
	}

	public double getOnlinePayment() {
		return onlinePayment;
	}

	public void setOnlinePayment(double onlinePayment) {
		this.onlinePayment = onlinePayment;
	}

	public double getTotalAmtCollected() {
		return totalAmtCollected;
	}

	public void setTotalAmtCollected(double totalAmtCollected) {
		this.totalAmtCollected = totalAmtCollected;
	}

	public double getOnlineAmt() {
		return onlineAmt;
	}

	public void setOnlineAmt(double onlineAmt) {
		this.onlineAmt = onlineAmt;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setConsumerCnt(Double consumerCnt) {
		this.consumerCnt = consumerCnt;
	}

	public void setOnlinePayment(Double onlinePayment) {
		this.onlinePayment = onlinePayment;
	}

	public void setTotalAmtCollected(Double totalAmtCollected) {
		this.totalAmtCollected = totalAmtCollected;
	}

	public void setOnlineAmt(Double onlineAmt) {
		this.onlineAmt = onlineAmt;
	}

	
	
	@Embeddable
	public static  class pfcD7IntermediateKey implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "month_year")
		private String monthYear;
		
		@Column(name = "town")
		private String town;
		
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
		  
		public pfcD7IntermediateKey(String monthYear, String town) {
			super();
			this.monthYear = monthYear;
			this.town = town;
		}

		public pfcD7IntermediateKey() {
			
		}
	}



	public pfcD7IntermediateKey getMyKey() {
		return myKey;
	}

	public void setMyKey(pfcD7IntermediateKey myKey) {
		this.myKey = myKey;
	}

	public Timestamp getReadtime() {
		return readtime;
	}

	public void setReadtime(Timestamp readtime) {
		this.readtime = readtime;
	}
	


}
