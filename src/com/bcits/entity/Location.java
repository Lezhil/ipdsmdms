package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;




@Entity
@Table(name="location_amr",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="location.getCircles",query="select circle from Location"),
	@NamedQuery(name="location.getDivision",query="select l.division from Location l where l.circle=:circle "),
	@NamedQuery(name="location.getAllData",query="select l from Location l order by l.id desc"),
	@NamedQuery(name="location.getAllExistingZones",query="select distinct zone from Location order by 1"),
	@NamedQuery(name="location.getAllExistingCircles",query="select distinct circle from Location where zone=:zone order by 1"),
	@NamedQuery(name="location.getAllExistingDivisionsUnderCircle",query="select distinct division from Location where zone=:zone and circle=:circle order by 1"),
	@NamedQuery(name="location.getAllExistingSubDivisionsUnderDivision",query="select distinct subdivision from Location where zone=:zone and circle=:circle and division=:division order by 1")
	
})
	

public class Location {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="zone")
	private String zone;
	@Column(name="circle")
	private String circle;
	@Column(name="division")
	private String division;
	@Column(name="subdivision")
	private String subdivision;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	
	
	

}
