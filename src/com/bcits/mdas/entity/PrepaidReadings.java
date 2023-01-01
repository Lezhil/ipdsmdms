package com.bcits.mdas.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity 
@Table(name="prepaid_reading", schema="meter_data",uniqueConstraints = { @UniqueConstraint(columnNames = { "mtrno","date" }) })

@NamedQueries({
	@NamedQuery(name="PrepaidReadings.getAllReadingsByMtrnoDate", query="select r from PrepaidReadings r where keyReadings.mtrno=:mtrno and  to_char(keyReadings.date,'YYYY-MM-DD')>=:fromdate and to_char(keyReadings.date,'YYYY-MM-DD')<=:todate order by  keyReadings.date asc"),
	@NamedQuery(name="PrepaidReadings.getPreviousDaysReading", query="select r from PrepaidReadings r where keyReadings.mtrno=:mtrno and  keyReadings.date=(select max(keyReadings.date) from PrepaidReadings pr where keyReadings.mtrno=:mtrno and to_char(keyReadings.date,'YYYY-MM-DD')<:date) ")
})

public class PrepaidReadings {

	//@Id
	@Column(name="id", insertable=false, updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@EmbeddedId
	private KeyReadings keyReadings;
	
	
	
	@Column(name="consumption")
	private Double consumption;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="comsumption_remaining")
	private Double comsumption_remaining;
	
	@Column(name="balance")
	private Double balance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Double getConsumption() {
		return consumption;
	}

	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getComsumption_remaining() {
		return comsumption_remaining;
	}

	public void setComsumption_remaining(Double comsumption_remaining) {
		this.comsumption_remaining = comsumption_remaining;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
	@Embeddable
	public static  class KeyReadings implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "mtrno")
		private String mtrno;
		
		@Column(name = "date")
		private Date date;
		 
		public KeyReadings(String mtrno, Date date) {
			super();
			this.mtrno = mtrno;
			this.date = date;
		}

		public KeyReadings(){
			 
		}

		public String getMtrno() {
			return mtrno;
		}

		public void setMtrno(String mtrno) {
			this.mtrno = mtrno;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	    
	}


	public KeyReadings getKeyReadings() {
		return keyReadings;
	}

	public void setKeyReadings(KeyReadings keyReadings) {
		this.keyReadings = keyReadings;
	}
	
	
	
}
