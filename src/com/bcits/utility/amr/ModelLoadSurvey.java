package com.bcits.utility.amr;

import java.util.Calendar;
import java.util.Date;

public class ModelLoadSurvey implements Comparable<ModelLoadSurvey> {
	String kvah = "", kWh = "", currentR = "", currentY = "", currentB = "", voltageR = "", voltageY = "",
			voltageB = "", kvarhLag = "", kvarhLead = "", pf = "", kw = "", kva = "";
	Date date;

	public Date getDate() {
		return date;
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

	public String getCurrentR() {
		return currentR;
	}

	public void setCurrentR(String currentR) {
		this.currentR = currentR;
	}

	public String getCurrentY() {
		return currentY;
	}

	public void setCurrentY(String currentY) {
		this.currentY = currentY;
	}

	public String getCurrentB() {
		return currentB;
	}

	public void setCurrentB(String currentB) {
		this.currentB = currentB;
	}

	public String getVoltageR() {
		return voltageR;
	}

	public void setVoltageR(String voltageR) {
		this.voltageR = voltageR;
	}

	public String getVoltageY() {
		return voltageY;
	}

	public void setVoltageY(String voltageY) {
		this.voltageY = voltageY;
	}

	public String getVoltageB() {
		return voltageB;
	}

	public void setVoltageB(String voltageB) {
		this.voltageB = voltageB;
	}

	public String getKvarhLag() {
		return kvarhLag;
	}

	public void setKvarhLag(String kvarhLag) {
		this.kvarhLag = kvarhLag;
	}

	public String getKvarhLead() {
		return kvarhLead;
	}

	public void setKvarhLead(String kvarhLead) {
		this.kvarhLead = kvarhLead;
	}

	public String getPf() {
		return pf;
	}

	public void setPf(String pf) {
		this.pf = pf;
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public String getKva() {
		return kva;
	}

	public void setKva(String kva) {
		this.kva = kva;
	}

	public void setDate(Date loadDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(loadDate);
		cal.add(Calendar.MINUTE, -30);//Reduce 30 minutes to the load survey date to make compatible with XML time
		this.date = cal.getTime();
	}

	@Override
	public int compareTo(ModelLoadSurvey o) {
		return getDate().compareTo(o.getDate());
	}
}
