package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TRANSACTION_MASTER",schema="mdm_test")
public class TransactionMaster {

	@Id
	@Column(name="TRANSACTION_ID")
	private int id;
	
	@Column(name="TRANSACTION_CODE")
	private int transactionCode;
	
	@Column(name="TRANSACTION")
	private String transactionDesc;
	
	public TransactionMaster() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(int transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionDesc() {
		return transactionDesc;
	}

	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}
	
	
	
	
}
