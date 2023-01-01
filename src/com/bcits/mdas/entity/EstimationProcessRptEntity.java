/**
 * 
 */
package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Tarik
 *
 */

@Entity
@Table(name = "ESTIMATION_PROCESS_RPT", schema = "METER_DATA")
@NamedQueries({
		// @NamedQuery(name="EstimationProcessRptEntity.getAuditData", query = "select e
		// from meter_data.estimation_process_rpt e where e.meter_number=:mtrno and
		// \"date\"(a.est_app_date)=:estdate")
		// @NamedQuery(name="EstimationProcessRptEntity.getAssignRuleId", query="")
		// @NamedQuery(name="EstimationProcessRptEntity.getAssignRuleId", query="select
		// a from ValidationProcessReportEntity a where a.myKey.rule_id=:rule_id and
		// a.myKey.meter_number=:meter_number and a.myKey.est_date=:est_date"),
//	@NamedQuery(name="ValidationProcessReportEntity.getValidationReportData" , query="select a from ValidationProcessReportEntity a where a.zone=:zone and a.circle=:circle and a.division=:division and a.subdivision=:subdivision and a.myKey.v_rule_id=:v_rule_id and a.myKey.monthyear=:monthyear"),

})
public class EstimationProcessRptEntity {

	@Id
	@Column(name = "id", insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "rule_id")
	private String rule_id;

	@Column(name = "meter_number")
	private String meter_number;

	@Column(name = "est_date")
	private Timestamp est_date;

	@Column(name = "rulename")
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

	@Column(name = "subdivision")
	private String subdivision;
	
	@Column(name = "town_code")
	private String town_code;

	@Column(name = "est_from")
	private String est_from;

	@Column(name = "est_to")
	private String est_to;

	@Column(name = "est_app_date")
	private Timestamp est_app_date;

	@Column(name = "data_type")
	private String data_type;

	@Column(name = "parameter")
	private String parameter;

	@Column(name = "kwh")
	private Double kwh;

	@Column(name = "est_kwh")
	private Double est_kwh;

	@Column(name = "kvah")
	private Double kvah;

	@Column(name = "est_kvah")
	private Double est_kvah;

	@Column(name = "kw")
	private Double kw;

	@Column(name = "est_kw")
	private Double est_kw;

	@Column(name = "kva")
	private Double kva;

	@Column(name = "est_kva")
	private Double est_kva;
	
	@Column(name = "estimated_by")
	private String estimatedBy;

	@Column(name = "edited_date")
	private Timestamp editeddate;

	@Column(name = "table_name")
	private String tableName;
	
	@Column(name = "tabel_id")
	private Integer tableId;

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

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getZone() {
		return zone;
	}

	public String getEstimatedBy() {
		return estimatedBy;
	}

	public void setEstimatedBy(String estimatedBy) {
		this.estimatedBy = estimatedBy;
	}

	public Timestamp getEditeddate() {
		return editeddate;
	}

	public void setEditeddate(Timestamp editeddate) {
		this.editeddate = editeddate;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer id2) {
		this.tableId = id2;
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

	public String getEst_from() {
		return est_from;
	}

	public void setEst_from(String est_from) {
		this.est_from = est_from;
	}

	public String getEst_to() {
		return est_to;
	}

	public void setEst_to(String est_to) {
		this.est_to = est_to;
	}

	public Timestamp getEst_app_date() {
		return est_app_date;
	}

	public void setEst_app_date(Timestamp est_app_date) {
		this.est_app_date = est_app_date;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Double getKwh() {
		return kwh;
	}

	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}

	public Double getEst_kwh() {
		return est_kwh;
	}

	public void setEst_kwh(Double est_kwh) {
		this.est_kwh = est_kwh;
	}

	public Double getKvah() {
		return kvah;
	}

	public void setKvah(Double kvah) {
		this.kvah = kvah;
	}

	public Double getEst_kvah() {
		return est_kvah;
	}

	public void setEst_kvah(Double est_kvah) {
		this.est_kvah = est_kvah;
	}

	public Double getKw() {
		return kw;
	}

	public void setKw(Double kw) {
		this.kw = kw;
	}

	public Double getEst_kw() {
		return est_kw;
	}

	public void setEst_kw(Double est_kw) {
		this.est_kw = est_kw;
	}

	public Double getKva() {
		return kva;
	}

	public void setKva(Double kva) {
		this.kva = kva;
	}

	public Double getEst_kva() {
		return est_kva;
	}

	public void setEst_kva(Double est_kva) {
		this.est_kva = est_kva;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public Timestamp getEst_date() {
		return est_date;
	}

	public void setEst_date(Timestamp est_date) {
		this.est_date = est_date;
	}

	public String getMeter_number() {
		return meter_number;
	}

	public void setMeter_number(String meter_number) {
		this.meter_number = meter_number;
	}

	public String getTown_code() {
		return town_code;
	}

	public void setTown_code(String town_code) {
		this.town_code = town_code;
	}

}
