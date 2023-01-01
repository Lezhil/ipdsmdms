package com.bcits.utility.amr;

import java.util.Date;



public class ModelBills implements Comparable<ModelBills> {
	String kWh,kvah,pf,kva,kvaDate;
	Date date;
	
	public String getPf() {
		return pf;
	}
	public void setPf(String pf) {
		this.pf = pf;
	}
	public String getKva() {
		return kva;
	}
	public void setKva(String kva) {
		this.kva = kva;
	}
	public String getKvaDate() {
		return kvaDate;
	}
	public void setKvaDate(String kvaDate) {
		this.kvaDate = kvaDate;
	}
	 
	public String getKvah() {
		return kvah;
	}
	public void setKvah(String kvah) {
		this.kvah = kvah;
	}
	public String getkWh() {
		return kWh;
	}
	public void setkWh(String kWh) {
		this.kWh = kWh;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int compareTo(ModelBills o) {
		 return getDate().compareTo(o.getDate());
	}
}
