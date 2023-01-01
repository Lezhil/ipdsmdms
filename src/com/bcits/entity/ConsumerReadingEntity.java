package com.bcits.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CONSUMERREADINGS",schema="meter_data")
@NamedQueries({									
	@NamedQuery(name="CONSUMERREADINGS.getReadingData",query="select model from ConsumerReadingEntity model where model.kno=:kno and model.billmonth=:rdngmonth"),
})
public class ConsumerReadingEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="ACCNO")
	private String accno;
	
	@Column(name="METERNO")
	private String meterno;
	
	@Column(name="KNO")
	private String kno;
	
	@Column(name="BILLMONTH")
	private String billmonth;
	
	@Column(name="RDATE")
	private Timestamp rdate;
	
	@Column(name="KWH")
	private double kwh;
	
	@Column(name="KVAH")
	private double kvah;
	
	@Column(name="KVA")
	private double kva;
	
	@Column(name="KWOCCDATE")
	private Timestamp kwoccdate;
	
	@Column(name="KVAOCCDATE")
	private Timestamp kvaoccdate;
	
	@Column(name="TIMESTAMP")
	private Timestamp timestamp;
	
	@Column(name="FLAG")
	private int flag;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}

	public String getBillmonth() {
		return billmonth;
	}

	public void setBillmonth(String billmonth) {
		this.billmonth = billmonth;
	}

	public Timestamp getRdate() {
		return rdate;
	}

	public void setRdate(Timestamp rdate) {
		this.rdate = rdate;
	}

	public double getKwh() {
		return kwh;
	}

	public void setKwh(double kwh) {
		this.kwh = kwh;
	}

	public double getKvah() {
		return kvah;
	}

	public void setKvah(double kvah) {
		this.kvah = kvah;
	}

	public double getKva() {
		return kva;
	}

	public void setKva(double kva) {
		this.kva = kva;
	}

	public Timestamp getKwoccdate() {
		return kwoccdate;
	}

	public void setKwoccdate(Timestamp kwoccdate) {
		this.kwoccdate = kwoccdate;
	}

	public Timestamp getKvaoccdate() {
		return kvaoccdate;
	}

	public void setKvaoccdate(Timestamp kvaoccdate) {
		this.kvaoccdate = kvaoccdate;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
