package com.bcits.mdas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="METER_CHANGE",schema="METER_DATA")
public class MeterChange 
{
	@Id
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
    @Column(name="zone")
	private String zone;
	
	@Column(name="circle")
	private String circle;
	
	@Column(name="division")
	private String division;
	
	@Column(name="subdivision")
	private String subdivision;
	
	@Column(name="substation")
	private String substation;
	
	@Column(name="consumer_name")
	private String consumer_name;
	
	@Column(name="old_mtr_no")
	private String old_mtr_no;
	
	@Column(name="new_mtr_no")
	private String new_mtr_no;
	
	@Column(name="created_by")
	private String created_by;
	
	@Column(name="created_date")
	private Date created_date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getSubstation() {
		return substation;
	}

	public void setSubstation(String substation) {
		this.substation = substation;
	}

	public String getConsumer_name() {
		return consumer_name;
	}

	public void setConsumer_name(String consumer_name) {
		this.consumer_name = consumer_name;
	}

	public String getold_mtr_no() {
		return old_mtr_no;
	}

	public void setold_mtr_no(String old_mtr_no) {
		this.old_mtr_no = old_mtr_no;
	}

	public String getNew_mtr_no() {
		return new_mtr_no;
	}

	public void setNew_mtr_no(String new_mtr_no) {
		this.new_mtr_no = new_mtr_no;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getOld_mtr_no() {
		return old_mtr_no;
	}

	public void setOld_mtr_no(String old_mtr_no) {
		this.old_mtr_no = old_mtr_no;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	

}
