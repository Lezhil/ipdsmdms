package com.bcits.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Xmlimport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "XMLIMPORT", schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="XmlImport.getDetailsBasedonMeterNo",query="SELECT x FROM XmlImport x WHERE x.meterno=:meterNo AND x.month=:billMonth"),
	@NamedQuery(name="XmlImport.ChechForBillingParameters",query="SELECT x FROM XmlImport x WHERE x.meterno=:meterNo AND x.datestamp=:datestamp"),
	@NamedQuery(name="XmlImport.ChechRdateData",query="SELECT x FROM XmlImport x WHERE x.meterno=:meterNo AND x.rdate=:rdate"),
	@NamedQuery(name="XmlImport.getThreeMonthsReadings",query="SELECT x FROM XmlImport x WHERE x.meterno=:meterNo AND x.month BETWEEN  :beforePreviousMonth AND :billMonth"),
	@NamedQuery(name="XmlImport.exportDetailsBasedonMeterNo",query="SELECT x FROM XmlImport x WHERE x.meterno=:meterNo AND x.month<=:billMonth ORDER BY x.id DESC")
})
public class XmlImport {

	
	private Long id;
	private String meterno;
	private Date rdate;
	private Double kwh;
	private String kwhunit;
	private Double kvh;
	private String kvhunit;
	private Double kva;
	private String kvaunit;
	private String kwharb;
	private Double pf;
	private String kvaarb;
	private String kvharb;
	private String pfarb;
	private Integer month;
	private String datestamp;
	private String g2value;
	private String g3value;
	private Long loadSurveyCount;	
	private String kvaOccDate;

	public XmlImport() {
		
	}

	// Property accessors

	@Id
	@SequenceGenerator(name = "xmlImportId", sequenceName = "XMLIMPORT_ID")
	@GeneratedValue(generator = "xmlImportId")
	@Column(name ="ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name = "METERNO", length = 18)
	public String getMeterno() {
		return this.meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	@Column(name = "RDATE")
	public Date getRdate() {
		return this.rdate;
	}

	public void setRdate(Date rdate) {
		this.rdate = rdate;
	}

	@Column(name = "KWH", precision = 20, scale = 3)
	public Double getKwh() {
		return this.kwh;
	}

	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}

	@Column(name = "KWHUNIT", length = 10)
	public String getKwhunit() {
		return this.kwhunit;
	}

	public void setKwhunit(String kwhunit) {
		this.kwhunit = kwhunit;
	}

	@Column(name = "KVH", precision = 20, scale = 3)
	public Double getKvh() {
		return this.kvh;
	}

	public void setKvh(Double kvh) {
		this.kvh = kvh;
	}

	@Column(name = "KVHUNIT", length = 10)
	public String getKvhunit() {
		return this.kvhunit;
	}

	public void setKvhunit(String kvhunit) {
		this.kvhunit = kvhunit;
	}

	@Column(name = "KVA", precision = 20, scale = 3)
	public Double getKva() {
		return this.kva;
	}

	public void setKva(Double kva) {
		this.kva = kva;
	}

	@Column(name = "KVAUNIT", length = 10)
	public String getKvaunit() {
		return this.kvaunit;
	}

	public void setKvaunit(String kvaunit) {
		this.kvaunit = kvaunit;
	}

	@Column(name = "KWHARB", length = 50)
	public String getKwharb() {
		return this.kwharb;
	}

	public void setKwharb(String kwharb) {
		this.kwharb = kwharb;
	}

	@Column(name = "PF", precision = 20, scale = 3)
	public Double getPf() {
		return this.pf;
	}

	public void setPf(Double pf) {
		this.pf = pf;
	}

	@Column(name = "KVAARB", length = 50)
	public String getKvaarb() {
		return this.kvaarb;
	}

	public void setKvaarb(String kvaarb) {
		this.kvaarb = kvaarb;
	}

	@Column(name = "KVHARB", length = 50)
	public String getKvharb() {
		return this.kvharb;
	}

	public void setKvharb(String kvharb) {
		this.kvharb = kvharb;
	}

	@Column(name = "PFARB", length = 50)
	public String getPfarb() {
		return this.pfarb;
	}

	public void setPfarb(String pfarb) {
		this.pfarb = pfarb;
	}

	@Column(name = "MONTH", precision = 6, scale = 0)
	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	@Column(name = "DATESTAMP", length = 12)
	public String getDatestamp() {
		return this.datestamp;
	}

	public void setDatestamp(String datestamp) {
		this.datestamp = datestamp;
	}

	
	@Column(name = "G2VALUE", length = 50)
	public String getG2value() {
		return this.g2value;
	}

	public void setG2value(String g2value) {
		this.g2value = g2value;
	}

	
	@Column(name = "G3VALUE", length = 50)
	public String getG3value() {
		return this.g3value;
	}

	public void setG3value(String g3value) {
		this.g3value = g3value;
	}

	@Column(name = "LOAD_SURVEY_COUNT", precision = 10, scale = 0)
	public Long getLoadSurveyCount() {
		return this.loadSurveyCount;
	}

	public void setLoadSurveyCount(Long loadSurveyCount) {
		this.loadSurveyCount = loadSurveyCount;
	}

	@Column(name="KVAOCCDATE")
	public String getKvaOccDate()
	{
		return kvaOccDate;
	}

	public void setKvaOccDate(String kvaOccDate)
	{
		this.kvaOccDate = kvaOccDate;
	}
	
	
}