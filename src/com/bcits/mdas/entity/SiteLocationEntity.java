package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "LOCATION", schema = "VCLOUDENGINE")
@NamedQueries({
		@NamedQuery(name = "SiteLocationEntity.findAll", query = "SELECT loc FROM SiteLocationEntity loc"),
		@NamedQuery(name = "SiteLocationEntity.findAllSDOCodes", query = "SELECT loc.siteCode,loc.section,loc.dbUser,loc.newSitecode from SiteLocationEntity loc ORDER BY loc.siteCode"),
		@NamedQuery(name = "SiteLocationEntity.findSections", query = "SELECT loc.siteCode from SiteLocationEntity loc order by loc.siteCode"),
		@NamedQuery(name = "SiteLocationEntity.getSectionCodes", query = "select DISTINCT s.section,s.siteCode from SiteLocationEntity s ORDER BY s.siteCode"),
		@NamedQuery(name = "SiteLocationEntity.findDbUser", query = "SELECT loc.dbUser FROM SiteLocationEntity loc where loc.siteCode=:siteCode"),
		@NamedQuery(name = "SiteLocationEntity.findBySdocode", query = "SELECT loc FROM SiteLocationEntity loc where loc.siteCode=:siteCode"),
})
public class SiteLocationEntity {

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "ZONE")
	private String zone;

	@Column(name = "CIRCLE")
	private String circle;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "SUBDIVISION")
	private String subDivision;

	@Column(name = "SECTION")
	private String section;

	@Id
	@Column(name = "SITECODE")
	private Integer siteCode;

	@Column(name = "DBUSER")
	private String dbUser;

	@Column(name = "EMAILIDS")
	private String emailid;

	@Column(name = "PHONENOS")
	private String phone;
	
	@Column(name = "NEWSITECODE")
	private String newSitecode;


	public SiteLocationEntity() {

	}


	public SiteLocationEntity(String company, String zone, String circle,
			String division, String subDivision, String section,
			Integer siteCode, String dbUser, String emailid, String phone) {
		super();
		this.company = company;
		this.zone = zone;
		this.circle = circle;
		this.division = division;
		this.subDivision = subDivision;
		this.section = section;
		/*this.level = level;*/
		this.siteCode = siteCode;
		this.dbUser = dbUser;
		this.emailid = emailid;
		this.phone = phone;
	}



	public String getNewSitecode() {
		return newSitecode;
	}


	public void setNewSitecode(String newSitecode) {
		this.newSitecode = newSitecode;
	}


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Integer getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(Integer siteCode) {
		this.siteCode = siteCode;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	/*public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}*/

}
