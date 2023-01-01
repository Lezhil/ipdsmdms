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
@Table(name = "VALIDATION_RULE_MST",  schema = "METER_DATA")
@NamedQueries({
	@NamedQuery(name="VeeRuleEntity.findAllVeeRules" , query="SELECT s FROM VeeRuleEntity s  where  s.ruleid NOT IN('VEE05','VEE16','VEE07','VEE17','VEE18','VEE19','VEE13','VEE14','VEE15') order by s.ruleid "),
	@NamedQuery(name="VeeRuleEntity.findAllActiveVeeRules" , query="SELECT s FROM VeeRuleEntity s where s.is_active=TRUE order by s.ruleid "),
	@NamedQuery(name="VeeRuleEntity.getVeeRuleById" , query="SELECT s FROM VeeRuleEntity s where s.ruleid=:ruleid"),
	@NamedQuery(name="VeeRuleEntity.getEstimationRulesByVeeRuleId",query="SELECT v.ruleid,v.rulename FROM VeeRuleEntity v WHERE v.ruleid=:ruleid and v.trigger_v_rule=:trigger_v_rule")
})
public class VeeRuleEntity {	
	   
	  @Id
	  @Column(name = "id", insertable=false,updatable=false)
	  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  private Long id;
	 	
	@Column(name = "v_rule_id")
	private String ruleid;	//varchar
	
	@Column(name = "v_rule_name")
	private String rulename;	//varchar
	
	@Column(name = "estimation_rule")
	private String erulename;
	
	@Column(name = "threshold_high")
	private Double hgthrlimit;	
	
	@Column(name = "threshold_low")
	private Double lwthrlimit;	
	
	@Column(name = "universal")
	private Boolean universal_v_rule;
	
	@Column(name = "auto_apply")
	private Boolean auto_v_rule;
	
	@Column(name = "raise_alarm")
	private Boolean alarm_v_rule;
	
	@Column(name = "trig_auto_estimate")
	private Boolean trigger_v_rule;

	@Column(name = "entry_date")
	private Timestamp entry_date;
	
	@Column(name = "entry_by")
	private String entry_by;
	
	@Column(name = "update_date")
	private Timestamp update_date;	
	
	@Column(name = "update_by")
	private String update_by;
	
	@Column(name="is_active")
	private Boolean is_active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleid() {
		return ruleid;
	}

	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}

	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	public String getErulename() {
		return erulename;
	}

	public void setErulename(String erulename) {
		this.erulename = erulename;
	}

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

	public Boolean getUniversal_v_rule() {
		return universal_v_rule;
	}

	public void setUniversal_v_rule(Boolean universal_v_rule) {
		this.universal_v_rule = universal_v_rule;
	}

	public Boolean getAuto_v_rule() {
		return auto_v_rule;
	}

	public void setAuto_v_rule(Boolean auto_v_rule) {
		this.auto_v_rule = auto_v_rule;
	}

	public Boolean getAlarm_v_rule() {
		return alarm_v_rule;
	}

	public void setAlarm_v_rule(Boolean alarm_v_rule) {
		this.alarm_v_rule = alarm_v_rule;
	}

	public Boolean getTrigger_v_rule() {
		return trigger_v_rule;
	}

	public void setTrigger_v_rule(Boolean trigger_v_rule) {
		this.trigger_v_rule = trigger_v_rule;
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

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	
	
}
