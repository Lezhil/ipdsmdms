/**
 * 
 */
package com.bcits.mdas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Tarik
 *
 */

@Entity
@Table(name = "LOAD_SURVEY_MONTHLY_CONSUMPTION", schema = "METER_DATA", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "mtrno", "billmonth" }) })
@NamedQueries({
	
	@NamedQuery(name="LoadSurveyMonthlyConsumptionEntity.getAssignRuleId", query="select a from LoadSurveyMonthlyConsumptionEntity a where a.myKey.mtrno=:mtrno and a.myKey.billmonth=:billmonth"),
//	@NamedQuery(name="LoadSurveyMonthlyConsumptionEntity.getValidationReportData" , query="select a from LoadSurveyMonthlyConsumptionEntity a where a.zone=:zone and a.circle=:circle and a.division=:division and a.subdivision=:subdivision and a.myKey.v_rule_id=:v_rule_id and a.myKey.monthyear=:monthyear"),

})
public class LoadSurveyMonthlyConsumptionEntity {

	@EmbeddedId // FOR MAKING UNIQUE KEY
	private KeyLoadServyUniqueId myKey; 
	
	@Column(name = "accno")
	private String accno;

	@Column(name = "kno")
	private String kno;

	@Column(name = "kwh")
	private Double kwh;
	
	@Column(name = "kvah")
	private Double kvah;
	
	@Column(name = "kva")
	private Double kva;
	

	public KeyLoadServyUniqueId getMyKey() {
		return myKey;
	}

	public void setMyKey(KeyLoadServyUniqueId myKey) {
		this.myKey = myKey;
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

	public Double getKwh() {
		return kwh;
	}

	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}

	public Double getKvah() {
		return kvah;
	}

	public void setKvah(Double kvah) {
		this.kvah = kvah;
	}

	public Double getKva() {
		return kva;
	}

	public void setKva(Double kva) {
		this.kva = kva;
	}

	@Embeddable
	public static class KeyLoadServyUniqueId implements Serializable {

		private static final long serialVersionUID = 2L;

		@Column(name = "mtrno")
		private String mtrno;

		@Column(name = "billmonth")
		private String billmonth;

		public KeyLoadServyUniqueId(String mtrno, String billmonth) {

			this.billmonth = billmonth;
			this.mtrno = mtrno;
		}

		public KeyLoadServyUniqueId() {

		}

		public String getMtrno() {
			return mtrno;
		}

		public void setMtrno(String mtrno) {
			this.mtrno = mtrno;
		}

		public String getBillmonth() {
			return billmonth;
		}

		public void setBillmonth(String billmonth) {
			this.billmonth = billmonth;
		}
	}


}
