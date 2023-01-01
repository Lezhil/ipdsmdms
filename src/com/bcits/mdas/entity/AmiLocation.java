package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.NamedQueries;


@Entity
@Table(name="amilocation", schema="meter_data")
@NamedQueries({
	@NamedQuery(name="AmiLocation.circlecode",query="select DISTINCT a.circleCode from AmiLocation a where a.circle=:circle"),
	@NamedQuery(name="AmiLocation.divisioncode",query="select DISTINCT a.divisionCode from AmiLocation a where a.division=:division"),
	@NamedQuery(name="AmiLocation.subdivisioncode",query="select DISTINCT a.sitecode from AmiLocation a where a.subDivision=:sdoname"),
	@NamedQuery(name="AmiLocation.circleName",query="select DISTINCT a.circle from AmiLocation a where a.circleCode=:circleCode"),
	@NamedQuery(name="AmiLocation.divisionName",query="select DISTINCT a.division from AmiLocation a where a.divisionCode=:divisionCode"),
	@NamedQuery(name="AmiLocation.subdivisionName",query="select DISTINCT a.subDivision from AmiLocation a where a.sitecode=:sitecode"),
	@NamedQuery(name="AmiLocation.getSdocodeByCircle",query="select DISTINCT a.sitecode from AmiLocation a where a.circle=:circle"),
	@NamedQuery(name="AmiLocation.getSdocodeByDivision",query="select DISTINCT a.sitecode from AmiLocation a where a.circle=:circle and a.division=:division"),
	@NamedQuery(name="AmiLocation.getAllsubdivisioncode",query="select DISTINCT a.sitecode from AmiLocation a"),
    @NamedQuery(name="AmiLocation.getAllData",query="select a from AmiLocation a order by a.id desc"),
    @NamedQuery(name="AmiLocation.getAllExistingZones",query="select distinct zone from AmiLocation order by 1"),
    @NamedQuery(name="AmiLocation.getAllExistingCircles", query="select distinct circle from AmiLocation where zone=:zone order by 1"),
    @NamedQuery(name="AmiLocation.getAllExistingDivisionsUnderCircle", query="select distinct division from AmiLocation where zone=:zone and circle=:circle order by 1"),
    @NamedQuery(name="AmiLocation.getAllExistingSubDivisionsUnderDivision",query="select distinct subDivision from AmiLocation where zone=:zone and circle=:circle and division=:division order by 1"),
    @NamedQuery(name="AmiLocation.getAllExistingTownUnderSubDivision",query="select distinct town_ipds from AmiLocation where zone=:zone and circle=:circle and division=:division and subDivision=:subDivision order by 1"),
    @NamedQuery(name="AmiLocation.getAllExistingSectionUnderTown",query="select distinct section from AmiLocation where zone=:zone and circle=:circle and division=:division and subDivision=:subDivision and town_ipds=:town_ipds order by 1"),
    @NamedQuery(name="AmiLocation.getLocationDetails", query="select a from AmiLocation a where a.tp_zonecode like :tp_zonecode and a.tp_circlecode like :tp_circlecode and a.tp_divcode like :tp_divcode and a.tp_subdivcode like :tp_subdivcode and a.tp_towncode like :tp_towncode"),
    @NamedQuery(name="AmiLocation.getAmiLocationDetails", query="select a from AmiLocation a where a.tp_circlecode like :tp_circlecode and a.tp_divcode like :tp_divcode and a.tp_subdivcode like :tp_subdivcode and a.tp_towncode like :tp_towncode and a.tp_sectioncode like :tp_sectioncode"),
	//@NamedQuery(name="AmiLocation.divByCircle",query="select D",)
	//@NamedQuery(name="AmiLocation.subdivisionName",query="select DISTINCT a.subdivision from AmiLocation a where a.sitecode=:sitecode")
})
public class AmiLocation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="company")
	private String comapny;
	@Column(name="zone")
	private String zone;
	@Column(name="zone_code")
	private Integer zoneCode;
	@Column(name="circle_code")
	private Integer circleCode;
	@Column(name="circle")
	private String circle;
	@Column(name="division_code")
	private Integer divisionCode;
	@Column(name="division")
	private String division;
	@Column(name="sitecode")
	private Integer sitecode;
	@Column(name="subdivision")
	private String subDivision;
	@Column(name="town_rapdrp")
	private String town_rapdrp;
	@Column(name="town_ipds")
	private String town_ipds;
	@Column(name="tp_zonecode")
	private String tp_zonecode;
	@Column(name="tp_circlecode")
	private String tp_circlecode;
	@Column(name="tp_divcode")
	private String tp_divcode;
	@Column(name="tp_subdivcode")
	private String tp_subdivcode;
	@Column(name="tp_sectioncode")
	private String tp_sectioncode;
	@Column(name="tp_towncode")
	private String tp_towncode;
	@Column(name="section")
	private String section;
	@Column(name="discom")
	private String discom;
	@Column(name="discom_code")
	private Integer discom_code;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComapny() {
		return comapny;
	}
	public void setComapny(String comapny) {
		this.comapny = comapny;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public Integer getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(Integer circleCode) {
		this.circleCode = circleCode;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public Integer getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(Integer divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public Integer getSitecode() {
		return sitecode;
	}
	public void setSitecode(Integer sitecode) {
		this.sitecode = sitecode;
	}
	public String getSubDivision() {
		return subDivision;
	}
	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}
	public Integer getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(Integer zoneCode) {
		this.zoneCode = zoneCode;
	}
	
	public String getTown_rapdrp() {
		return town_rapdrp;
	}
	public void setTown_rapdrp(String town_rapdrp) {
		this.town_rapdrp = town_rapdrp;
	}
	public String getTown_ipds() {
		return town_ipds;
	}
	public void setTown_ipds(String town_ipds) {
		this.town_ipds = town_ipds;
	}
	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getTp_zonecode() {
		return tp_zonecode;
	}
	public void setTp_zonecode(String tp_zonecode) {
		this.tp_zonecode = tp_zonecode;
	}
	public String getTp_circlecode() {
		return tp_circlecode;
	}
	public void setTp_circlecode(String tp_circlecode) {
		this.tp_circlecode = tp_circlecode;
	}
	public String getTp_divcode() {
		return tp_divcode;
	}
	public void setTp_divcode(String tp_divcode) {
		this.tp_divcode = tp_divcode;
	}
	public String getTp_subdivcode() {
		return tp_subdivcode;
	}
	public void setTp_subdivcode(String tp_subdivcode) {
		this.tp_subdivcode = tp_subdivcode;
	}
	public String getTp_sectioncode() {
		return tp_sectioncode;
	}
	public void setTp_sectioncode(String tp_sectioncode) {
		this.tp_sectioncode = tp_sectioncode;
	}
	public String getTp_towncode() {
		return tp_towncode;
	}
	public void setTp_towncode(String tp_towncode) {
		this.tp_towncode = tp_towncode;
	}

	public String getDiscom() {
		return discom;
	}
	public void setDiscom(String discom) {
		this.discom = discom;
	}
	public Integer getDiscom_code() {
		return discom_code;
	}
	public void setDiscom_code(Integer discom_code) {
		this.discom_code = discom_code;
	}
	@Override
	public String toString() {
		return "AmiLocation [id=" + id + ", comapny=" + comapny + ", zone="
				+ zone + ", circleCode=" + circleCode + ", circle=" + circle
				+ ", divisionCode=" + divisionCode + ", division=" + division
				+ ", sitecode=" + sitecode + ", subDivision=" + subDivision
				+ "]";
	}
	

}
