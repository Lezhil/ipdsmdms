package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="prepaid_master", schema="meter_data")

@NamedQueries({
	
	@NamedQuery(name="PrepaidMaster.getDataByMtrno", query="select m from PrepaidMaster m where mtrno=:mtrno"),
	
})

public class PrepaidMaster {

	@Id
	@Column(name="id",  insertable=false, updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="mtrno")
	private String mtrno;
	
	@Column(name="accno")
	private String accno;
	
	@Column(name="kno")
	private String kno;
	
	@Column(name="last_recharge")
	private Double last_recharge;
	
	@Column(name="recno")
	private String recno;
	
	@Column(name="balance")
	private Double balance;
	
	@Column(name="unit_balance")
	private Double unit_balance;

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

	public Double getLast_recharge() {
		return last_recharge;
	}

	public void setLast_recharge(Double last_recharge) {
		this.last_recharge = last_recharge;
	}

	public String getRecno() {
		return recno;
	}

	public void setRecno(String recno) {
		this.recno = recno;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getUnit_balance() {
		return unit_balance;
	}

	public void setUnit_balance(Double unit_balance) {
		this.unit_balance = unit_balance;
	}
	
	
	
}
