package com.bcits.mdas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prepaid_payment", schema="meter_data")
public class PrepaidPayments {

	@Id
	@Column(name="id", insertable=false, updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="mtrno")
	private String mtrno;
	
	@Column(name="accno")
	private String accno;
	
	@Column(name="kno")
	private String kno;
	
	@Column(name="recno")
	private String recno;
	
	@Column(name="rec_date")
	private Date rec_date;
	
	@Column(name="amount")
	private Double amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMtrno() {
		return mtrno;
	}

	public void setMtrno(String mtrno) {
		this.mtrno = mtrno;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}

	public String getRecno() {
		return recno;
	}

	public void setRecno(String recno) {
		this.recno = recno;
	}

	public Date getRec_date() {
		return rec_date;
	}

	public void setRec_date(Date rec_date) {
		this.rec_date = rec_date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
