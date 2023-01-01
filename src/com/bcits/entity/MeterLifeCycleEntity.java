package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="METER_LIFECYCLE", schema="meter_data")
@NamedQueries({
	@NamedQuery(name = "MeterLifeCycleEntity.searchmeterData", query = "SELECT e FROM MeterLifeCycleEntity e Where e.meter_no=:meterno and e.consumer_no is null"),
	@NamedQuery(name = "MeterLifeCycleEntity.searchData", query = "SELECT e FROM MeterLifeCycleEntity e Where e.meter_no=:meterno AND e.consumer_no=:accno and e.disconn_date is null"),
	@NamedQuery(name = "MeterLifeCycleEntity.searchDuplicateData", query = "SELECT e FROM MeterLifeCycleEntity e Where e.meter_no=:meterno and e.disconn_date is not null"),
	@NamedQuery(name = "MeterLifeCycleEntity.getAllData", query = "SELECT e FROM MeterLifeCycleEntity e"),
})
//@SequenceGenerator(name="seq", initialValue=7, allocationSize=100)
public class MeterLifeCycleEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	/*@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meter_life_cycle_o_seq")
	@SequenceGenerator(name = "meter_life_cycle_o_seq", sequenceName = "meter_life_cycle_o_seq")*/
	
	@Column(name = "id")
	private long id;

	@Column(name = "METER_NO")
	private String meter_no;

	@Column(name = "CONSUMER_NO")
	private String consumer_no;
	
	@Column(name = "INSTALLED_DATE")
	private Date installed_date;
	
	@Column(name = "INITIAL_READING")
	private Double initial_reading;
	
	@Column(name = "FINAL_READING")
	private Double final_reading;
	
	@Column(name = "METER_STATUS")
	private String meter_status;
	
	@Column(name = "TOTAL_READING")
	private Double total_reading;
	
	@Column(name = "DISCONN_DATE")
	private Date disconn_date;
	
	@Column(name = "MONTH_YEAR")
	private String month_year;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getId() {
		return id;
	}

	public String getMeter_no() {
		return meter_no;
	}

	public String getConsumer_no() {
		return consumer_no;
	}

	public Date getInstalled_date() {
		return installed_date;
	}

	public Double getInitial_reading() {
		return initial_reading;
	}

	public Double getFinal_reading() {
		return final_reading;
	}

	public String getMeter_status() {
		return meter_status;
	}

	public Double getTotal_reading() {
		return total_reading;
	}

	public Date getDisconn_date() {
		return disconn_date;
	}

	public String getMonth_year() {
		return month_year;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setMeter_no(String meter_no) {
		this.meter_no = meter_no;
	}

	public void setConsumer_no(String consumer_no) {
		this.consumer_no = consumer_no;
	}

	public void setInstalled_date(Date installed_date) {
		this.installed_date = installed_date;
	}

	public void setInitial_reading(Double initial_reading) {
		this.initial_reading = initial_reading;
	}

	public void setFinal_reading(Double final_reading) {
		this.final_reading = final_reading;
	}

	public void setMeter_status(String meter_status) {
		this.meter_status = meter_status;
	}

	public void setTotal_reading(Double total_reading) {
		this.total_reading = total_reading;
	}

	public void setDisconn_date(Date disconn_date) {
		this.disconn_date = disconn_date;
	}

	public void setMonth_year(String month_year) {
		this.month_year = month_year;
	}

	
}
