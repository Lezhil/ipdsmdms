package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "pfc_d7_rpt_intermediate" , schema="meter_data")

public class PfcD7ReportIntermediateEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	@Column(name ="rec_id")
	private int id;
	
	@Column(name = "month_year")
	private String monthYear;
	
	@Column(name = "town")
	private String town;
	
	@Column(name = "consumer_cnt")
	private double consumerCnt;
	
	@Column(name = "online_payment")
	private double onlinePayment;
	
	@Column(name = "total_amt_collected")
	private double totalAmtCollected;
	
	@Column(name = "online_amt")
	private double onlineAmt;
		
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
	
	
}



