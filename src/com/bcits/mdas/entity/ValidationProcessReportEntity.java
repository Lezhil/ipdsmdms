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
@Table(name = "VALIDATION_PROCESS_RPT", schema = "METER_DATA", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "v_rule_id", "meter_number", "monthyear" }) })
@NamedQueries({
	
	@NamedQuery(name="ValidationProcessReportEntity.getAssignRuleId", query="select a from ValidationProcessReportEntity a where a.myKey.v_rule_id=:v_rule_id and a.myKey.meter_number=:meter_number and a.myKey.monthyear=:monthyear"),
	@NamedQuery(name="ValidationProcessReportEntity.getValidationReportData" , query="select a from ValidationProcessReportEntity a where a.zone like :zone and a.circle like :circle and a.town_code like :town_code and a.myKey.v_rule_id=:v_rule_id and a.myKey.monthyear=:monthyear"),
	@NamedQuery(name="ValidationProcessReportEntity.getallValidationReportData" , query="select a from ValidationProcessReportEntity a where a.myKey.v_rule_id=:v_rule_id and a.myKey.monthyear=:monthyear"),
	@NamedQuery(name="ValidationProcessReportEntity.getValidationReportDatabyzone" , query="select a from ValidationProcessReportEntity a where a.zone like :zone and a.myKey.v_rule_id=:v_rule_id and a.myKey.monthyear=:monthyear"),
	@NamedQuery(name="ValidationProcessReportEntity.getValidationReportDatabyCircle" , query="select a from ValidationProcessReportEntity a where a.zone like :zone and a.circle like :circle and a.myKey.v_rule_id=:v_rule_id and a.myKey.monthyear=:monthyear"),
	@NamedQuery(name="ValidationProcessReportEntity.getValidationReportDatabyDivision" , query="select a from ValidationProcessReportEntity a where a.zone like :zone and a.circle like :circle and a.division like :division and a.myKey.v_rule_id=:v_rule_id and a.myKey.monthyear=:monthyear"),
	@NamedQuery(name="ValidationProcessReportEntity.getValidationReportDatabySubDiv" , query="select a from ValidationProcessReportEntity a where a.zone like :zone and a.circle like :circle and a.myKey.v_rule_id=:v_rule_id and a.myKey.monthyear=:monthyear"),
})

public class ValidationProcessReportEntity {

	@Column(name = "id", insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@EmbeddedId // FOR MAKING UNIQUE KEY
	private KeyValidationUniqueId myKey; // RULUID, meter_number meter_number

	public KeyValidationUniqueId getMyKey() {
		return myKey;
	}

	public void setMyKey(KeyValidationUniqueId myKey) {
		this.myKey = myKey;
	}

	@Column(name = "v_rulename")
	private String rulename;

	@Column(name = "location_type")
	private String location_type;

	@Column(name = "location_id")
	private String location_id;

	@Column(name = "location_name")
	private String location_name;

	@Column(name = "zone")
	private String zone;

	@Column(name = "circle")
	private String circle;

	@Column(name = "division")
	private String division;

	@Column(name = "from_date")
	private String fromDate;
	
	@Column(name = "subdivision")
	private String subdivision;
	
	@Column(name = "town_code")
	private String town_code;
	
	@Column(name = "to_date")
	private String toDate;
	
	@Column(name = "threshold_high")
	private Double hgthrlimit;	
	
	@Column(name = "threshold_low")
	private Double lwthrlimit;

	public Double getHgthrlimit() {
		return hgthrlimit;
	}

	public void setHgthrlimit(Double hgthrlimit) {
		this.hgthrlimit = hgthrlimit;
	}

	public Double getLwthrlimit() {
		return lwthrlimit;
	}

	public void setLwthrlimit(Double lwthrlimit) {
		this.lwthrlimit = lwthrlimit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	public String getLocation_type() {
		return location_type;
	}

	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}

	public String getLocation_id() {
		return location_id;
	}

	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTown_code() {
		return town_code;
	}

	public void setTown_code(String town_code) {
		this.town_code = town_code;
	}

	@Embeddable
	public static class KeyValidationUniqueId implements Serializable {

		private static final long serialVersionUID = 2L;

		@Column(name = "v_rule_id")
		private String v_rule_id;

		@Column(name = "meter_number")
		private String meter_number;

		@Column(name = "monthyear")
		private String monthyear;

		public KeyValidationUniqueId(String v_rule_id, String meter_number, String monthyear) {

			this.v_rule_id = v_rule_id;
			this.monthyear = monthyear;
			this.meter_number = meter_number;
		}

		public KeyValidationUniqueId() {

		}

		public String getV_rule_id() {
			return v_rule_id;
		}

		public void setV_rule_id(String v_rule_id) {
			this.v_rule_id = v_rule_id;
		}

		public String getMeter_number() {
			return meter_number;
		}

		public void setMeter_number(String meter_number) {
			this.meter_number = meter_number;
		}

		public String getMonthyear() {
			return monthyear;
		}

		public void setMonthyear(String monthyear) {
			this.monthyear = monthyear;
		}

	}

}
