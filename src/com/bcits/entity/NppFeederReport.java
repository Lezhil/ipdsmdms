package com.bcits.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.bcits.entity.NPPDataEntity.KeyNPPData;

public class NppFeederReport {

	
	@EmbeddedId
	private KeyNPPData keyNPPData;
	
	
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;


	@Column(name="fdr_id")
	private String fdr_id;


	@Column(name="tp_fdr_id")
	private String tp_fdr_id;


	@Column(name="power_fail_freq")
	private Integer power_fail_freq;


	@Column(name="power_fail_duration")
	private Integer power_fail_duration;


	@Column(name="minimum_voltage")
	private Double minimum_voltage;


	@Column(name="max_current")
	private Double max_current;


	@Column(name="input_energy")
	private Double input_energy;


	@Column(name="export_energy")
	private Double export_energy;


	@Column(name="ht_industrial_consumer_count")
	private Integer ht_industrial_consumer_count;


	@Column(name="ht_commercial_consumer_count")
	private Integer ht_commercial_consumer_count;


	@Column(name="lt_industrial_consumer_count")
	private Integer lt_industrial_consumer_count;


	@Column(name="lt_commercial_consumer_count")
	private Integer lt_commercial_consumer_count;


	@Column(name="lt_domestic_consumer_count")
	private Integer lt_domestic_consumer_count;


	@Column(name="govt_consumer_count")
	private Integer govt_consumer_count;


	@Column(name="agri_consumer_count")
	private Integer agri_consumer_count;


	@Column(name="others_consumer_count")
	private Integer others_consumer_count;


	@Column(name="ht_industrial_energy_billed")
	private Double ht_industrial_energy_billed;


	@Column(name="ht_commercial_energy_billed")
	private Double ht_commercial_energy_billed;


	@Column(name="lt_industrial_energy_billed")
	private Double lt_industrial_energy_billed;


	@Column(name="lt_commercial_energy_billed")
	private Double lt_commercial_energy_billed;


	@Column(name="lt_domestic_energy_billed")
	private Double lt_domestic_energy_billed;


	@Column(name="govt_energy_billed")
	private Double govt_energy_billed;


	@Column(name="agri_energy_billed")
	private Double agri_energy_billed;


	@Column(name="others_energy_billed")
	private Double others_energy_billed;


	@Column(name="ht_industrial_amount_billed")
	private Double ht_industrial_amount_billed;


	@Column(name="ht_commercial_amount_billed")
	private Double ht_commercial_amount_billed;


	@Column(name="lt_industrial_amount_billed")
	private Double lt_industrial_amount_billed;


	@Column(name="lt_commercial_amount_billed")
	private Double lt_commercial_amount_billed;


	@Column(name="lt_domestic_amount_billed")
	private Double lt_domestic_amount_billed;


	@Column(name="govt_amount_billed")
	private Double govt_amount_billed;


	@Column(name="agri_amount_billed")
	private Double agri_amount_billed;


	@Column(name="others_amount_billed")
	private Double others_amount_billed;


	@Column(name="ht_industrial_amount_collected")
	private Double ht_industrial_amount_collected;


	@Column(name="ht_commercial_amount_collected")
	private Double ht_commercial_amount_collected;


	@Column(name="lt_industrial_amount_collected")
	private Double lt_industrial_amount_collected;


	@Column(name="lt_commercial_amount_collected")
	private Double lt_commercial_amount_collected;


	@Column(name="lt_domestic_amount_collected")
	private Double lt_domestic_amount_collected;


	@Column(name="govt_amount_collected")
	private Double govt_amount_collected;


	@Column(name="agri_amount_collected")
	private Double agri_amount_collected;


	@Column(name="others_amount_collected")
	private Double others_amount_collected;


	@Column(name="study_monthyear")
	private Integer study_monthyear;


	@Column(name="open_access_units")
	private Double open_access_units;


	@Column(name="officeid")
	private String officeid;


	@Column(name="time_stamp")
	private Date time_stamp;


	public KeyNPPData getKeyNPPData() {
		return keyNPPData;
	}


	public void setKeyNPPData(KeyNPPData keyNPPData) {
		this.keyNPPData = keyNPPData;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFdr_id() {
		return fdr_id;
	}


	public void setFdr_id(String fdr_id) {
		this.fdr_id = fdr_id;
	}


	public String getTp_fdr_id() {
		return tp_fdr_id;
	}


	public void setTp_fdr_id(String tp_fdr_id) {
		this.tp_fdr_id = tp_fdr_id;
	}


	public Integer getPower_fail_freq() {
		return power_fail_freq;
	}


	public void setPower_fail_freq(Integer power_fail_freq) {
		this.power_fail_freq = power_fail_freq;
	}


	public Integer getPower_fail_duration() {
		return power_fail_duration;
	}


	public void setPower_fail_duration(Integer power_fail_duration) {
		this.power_fail_duration = power_fail_duration;
	}


	public Double getMinimum_voltage() {
		return minimum_voltage;
	}


	public void setMinimum_voltage(Double minimum_voltage) {
		this.minimum_voltage = minimum_voltage;
	}


	public Double getMax_current() {
		return max_current;
	}


	public void setMax_current(Double max_current) {
		this.max_current = max_current;
	}


	public Double getInput_energy() {
		return input_energy;
	}


	public void setInput_energy(Double input_energy) {
		this.input_energy = input_energy;
	}


	public Double getExport_energy() {
		return export_energy;
	}


	public void setExport_energy(Double export_energy) {
		this.export_energy = export_energy;
	}


	public Integer getHt_industrial_consumer_count() {
		return ht_industrial_consumer_count;
	}


	public void setHt_industrial_consumer_count(Integer ht_industrial_consumer_count) {
		this.ht_industrial_consumer_count = ht_industrial_consumer_count;
	}


	public Integer getHt_commercial_consumer_count() {
		return ht_commercial_consumer_count;
	}


	public void setHt_commercial_consumer_count(Integer ht_commercial_consumer_count) {
		this.ht_commercial_consumer_count = ht_commercial_consumer_count;
	}


	public Integer getLt_industrial_consumer_count() {
		return lt_industrial_consumer_count;
	}


	public void setLt_industrial_consumer_count(Integer lt_industrial_consumer_count) {
		this.lt_industrial_consumer_count = lt_industrial_consumer_count;
	}


	public Integer getLt_commercial_consumer_count() {
		return lt_commercial_consumer_count;
	}


	public void setLt_commercial_consumer_count(Integer lt_commercial_consumer_count) {
		this.lt_commercial_consumer_count = lt_commercial_consumer_count;
	}


	public Integer getLt_domestic_consumer_count() {
		return lt_domestic_consumer_count;
	}


	public void setLt_domestic_consumer_count(Integer lt_domestic_consumer_count) {
		this.lt_domestic_consumer_count = lt_domestic_consumer_count;
	}


	public Integer getGovt_consumer_count() {
		return govt_consumer_count;
	}


	public void setGovt_consumer_count(Integer govt_consumer_count) {
		this.govt_consumer_count = govt_consumer_count;
	}


	public Integer getAgri_consumer_count() {
		return agri_consumer_count;
	}


	public void setAgri_consumer_count(Integer agri_consumer_count) {
		this.agri_consumer_count = agri_consumer_count;
	}


	public Integer getOthers_consumer_count() {
		return others_consumer_count;
	}


	public void setOthers_consumer_count(Integer others_consumer_count) {
		this.others_consumer_count = others_consumer_count;
	}


	public Double getHt_industrial_energy_billed() {
		return ht_industrial_energy_billed;
	}


	public void setHt_industrial_energy_billed(Double ht_industrial_energy_billed) {
		this.ht_industrial_energy_billed = ht_industrial_energy_billed;
	}


	public Double getHt_commercial_energy_billed() {
		return ht_commercial_energy_billed;
	}


	public void setHt_commercial_energy_billed(Double ht_commercial_energy_billed) {
		this.ht_commercial_energy_billed = ht_commercial_energy_billed;
	}


	public Double getLt_industrial_energy_billed() {
		return lt_industrial_energy_billed;
	}


	public void setLt_industrial_energy_billed(Double lt_industrial_energy_billed) {
		this.lt_industrial_energy_billed = lt_industrial_energy_billed;
	}


	public Double getLt_commercial_energy_billed() {
		return lt_commercial_energy_billed;
	}


	public void setLt_commercial_energy_billed(Double lt_commercial_energy_billed) {
		this.lt_commercial_energy_billed = lt_commercial_energy_billed;
	}


	public Double getLt_domestic_energy_billed() {
		return lt_domestic_energy_billed;
	}


	public void setLt_domestic_energy_billed(Double lt_domestic_energy_billed) {
		this.lt_domestic_energy_billed = lt_domestic_energy_billed;
	}


	public Double getGovt_energy_billed() {
		return govt_energy_billed;
	}


	public void setGovt_energy_billed(Double govt_energy_billed) {
		this.govt_energy_billed = govt_energy_billed;
	}


	public Double getAgri_energy_billed() {
		return agri_energy_billed;
	}


	public void setAgri_energy_billed(Double agri_energy_billed) {
		this.agri_energy_billed = agri_energy_billed;
	}


	public Double getOthers_energy_billed() {
		return others_energy_billed;
	}


	public void setOthers_energy_billed(Double others_energy_billed) {
		this.others_energy_billed = others_energy_billed;
	}


	public Double getHt_industrial_amount_billed() {
		return ht_industrial_amount_billed;
	}


	public void setHt_industrial_amount_billed(Double ht_industrial_amount_billed) {
		this.ht_industrial_amount_billed = ht_industrial_amount_billed;
	}


	public Double getHt_commercial_amount_billed() {
		return ht_commercial_amount_billed;
	}


	public void setHt_commercial_amount_billed(Double ht_commercial_amount_billed) {
		this.ht_commercial_amount_billed = ht_commercial_amount_billed;
	}


	public Double getLt_industrial_amount_billed() {
		return lt_industrial_amount_billed;
	}


	public void setLt_industrial_amount_billed(Double lt_industrial_amount_billed) {
		this.lt_industrial_amount_billed = lt_industrial_amount_billed;
	}


	public Double getLt_commercial_amount_billed() {
		return lt_commercial_amount_billed;
	}


	public void setLt_commercial_amount_billed(Double lt_commercial_amount_billed) {
		this.lt_commercial_amount_billed = lt_commercial_amount_billed;
	}


	public Double getLt_domestic_amount_billed() {
		return lt_domestic_amount_billed;
	}


	public void setLt_domestic_amount_billed(Double lt_domestic_amount_billed) {
		this.lt_domestic_amount_billed = lt_domestic_amount_billed;
	}


	public Double getGovt_amount_billed() {
		return govt_amount_billed;
	}


	public void setGovt_amount_billed(Double govt_amount_billed) {
		this.govt_amount_billed = govt_amount_billed;
	}


	public Double getAgri_amount_billed() {
		return agri_amount_billed;
	}


	public void setAgri_amount_billed(Double agri_amount_billed) {
		this.agri_amount_billed = agri_amount_billed;
	}


	public Double getOthers_amount_billed() {
		return others_amount_billed;
	}


	public void setOthers_amount_billed(Double others_amount_billed) {
		this.others_amount_billed = others_amount_billed;
	}


	public Double getHt_industrial_amount_collected() {
		return ht_industrial_amount_collected;
	}


	public void setHt_industrial_amount_collected(Double ht_industrial_amount_collected) {
		this.ht_industrial_amount_collected = ht_industrial_amount_collected;
	}


	public Double getHt_commercial_amount_collected() {
		return ht_commercial_amount_collected;
	}


	public void setHt_commercial_amount_collected(Double ht_commercial_amount_collected) {
		this.ht_commercial_amount_collected = ht_commercial_amount_collected;
	}


	public Double getLt_industrial_amount_collected() {
		return lt_industrial_amount_collected;
	}


	public void setLt_industrial_amount_collected(Double lt_industrial_amount_collected) {
		this.lt_industrial_amount_collected = lt_industrial_amount_collected;
	}


	public Double getLt_commercial_amount_collected() {
		return lt_commercial_amount_collected;
	}


	public void setLt_commercial_amount_collected(Double lt_commercial_amount_collected) {
		this.lt_commercial_amount_collected = lt_commercial_amount_collected;
	}


	public Double getLt_domestic_amount_collected() {
		return lt_domestic_amount_collected;
	}


	public void setLt_domestic_amount_collected(Double lt_domestic_amount_collected) {
		this.lt_domestic_amount_collected = lt_domestic_amount_collected;
	}


	public Double getGovt_amount_collected() {
		return govt_amount_collected;
	}


	public void setGovt_amount_collected(Double govt_amount_collected) {
		this.govt_amount_collected = govt_amount_collected;
	}


	public Double getAgri_amount_collected() {
		return agri_amount_collected;
	}


	public void setAgri_amount_collected(Double agri_amount_collected) {
		this.agri_amount_collected = agri_amount_collected;
	}


	public Double getOthers_amount_collected() {
		return others_amount_collected;
	}


	public void setOthers_amount_collected(Double others_amount_collected) {
		this.others_amount_collected = others_amount_collected;
	}


	public Integer getStudy_monthyear() {
		return study_monthyear;
	}


	public void setStudy_monthyear(Integer study_monthyear) {
		this.study_monthyear = study_monthyear;
	}


	public Double getOpen_access_units() {
		return open_access_units;
	}


	public void setOpen_access_units(Double open_access_units) {
		this.open_access_units = open_access_units;
	}


	public String getOfficeid() {
		return officeid;
	}


	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}


	public Date getTime_stamp() {
		return time_stamp;
	}


	public void setTime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}



	
}
