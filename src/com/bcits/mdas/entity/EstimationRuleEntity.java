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

@Entity
@Table(name = "ESTIMATION_RULE_MST",  schema = "METER_DATA")
@NamedQueries({
	@NamedQuery(name="EstimationRuleEntity.findAllESTRules" , query="SELECT s FROM EstimationRuleEntity s"),
	@NamedQuery(name="EstimationRuleEntity.getESTRuleById" , query="SELECT s FROM EstimationRuleEntity s where s.eruleid=:ID"),
	@NamedQuery(name="EstimationRuleEntity.findActiveRules" , query="SELECT e FROM EstimationRuleEntity e where e.is_active=True"),
})
public class EstimationRuleEntity {
	
	@Id
	@Column(name = "id", insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "e_rule_id")
	private String eruleid;
	
	@Column(name = "e_rule_name")
	private String erulename;
	
	@Column(name = "is_active")
	private Boolean is_active;
	
	@Column(name = "entry_date")
	private Timestamp entry_date;

	@Column(name = "entry_by")
	private String entry_by;

	@Column(name = "update_date")
	private Timestamp update_date;

	@Column(name = "update_by")
	private String update_by;
	
	@Column(name = "data_type")
	private String data_type;
	
	@Column(name = "parameter")
	private String parameter;
	
	@Column(name = "condition")
	private String condtion;
	
	@Column(name = "cond_value")
	private String condval;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEruleid() {
		return eruleid;
	}

	public void setEruleid(String eruleid) {
		this.eruleid = eruleid;
	}

	public String getErulename() {
		return erulename;
	}

	public void setErulename(String erulename) {
		this.erulename = erulename;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public Timestamp getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Timestamp entry_date) {
		this.entry_date = entry_date;
	}

	public String getEntry_by() {
		return entry_by;
	}

	public void setEntry_by(String entry_by) {
		this.entry_by = entry_by;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
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

	public String getCondtion() {
		return condtion;
	}

	public void setCondtion(String condtion) {
		this.condtion = condtion;
	}

	public String getCondval() {
		return condval;
	}

	public void setCondval(String condval) {
		this.condval = condval;
	}
	
	
	


}
