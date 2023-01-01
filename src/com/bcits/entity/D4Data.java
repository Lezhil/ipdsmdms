package com.bcits.entity;

//default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
* D4Data entity. @author MyEclipse Persistence Tools
*/
@Entity
@Table(name = "D4_DATA" , schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="D4Data.getDetailsBasedOnId",query="SELECT d4 FROM D4Data d4 WHERE d4.cdfData.meterNo=:meterNo AND d4.cdfData.billmonth=:billMonth ORDER BY d4.dayProfileDate")
})
public class D4Data  {

	// Fields

	private int d4Id;
	private int cdfId;
	private int intervalPeriod;
	private Date dayProfileDate;
	private Double minKva;
	private Double maxKva;
	private Double sumKwh;
	private int kwhFlag;
	private Double sumPf;
	private int pf05;
	private int pf0507;
	private int pf0709;
	private int pf09;
	private int pfNoload;
	private int pfBlackout;
	private int ipGs20;
	private int ipGs2040;
	private int ipGs4060;
	private int ipGs60;
	private int ipOutGs20;
	private int ipOutGs2040;
	private int ipOutGs4060;
	private int ipOutGs60;
	private Double mf;
	private Double cd;
	private Double sumKva;
	
	private CDFData cdfData;

	// Constructors
	/** default constructor */
	public D4Data() {
	}

	
	// Property accessors
	
	@Id
/*	@SequenceGenerator(name = "d4Id", sequenceName = "d4_data_id")
	@GeneratedValue(generator = "d4Id")*/
	
	 @GeneratedValue(strategy = GenerationType.IDENTITY)

	
	@Column(name = "D4_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public int getD4Id() {
		return this.d4Id;
	}

	public void setD4Id(int d4Id) {
		this.d4Id = d4Id;
	}

	@Column(name = "CDF_ID", precision = 22, scale = 0)
	public int getCdfId() {
		return this.cdfId;
	}

	public void setCdfId(int cdfId) {
		this.cdfId = cdfId;
	}

	@Column(name = "INTERVAL_PERIOD", precision = 22, scale = 0)
	public int getIntervalPeriod() {
		return this.intervalPeriod;
	}

	public void setIntervalPeriod(int intervalPeriod) {
		this.intervalPeriod = intervalPeriod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DAY_PROFILE_DATE", length = 7)
	public Date getDayProfileDate() {
		return this.dayProfileDate;
	}

	public void setDayProfileDate(Date dayProfileDate) {
		this.dayProfileDate = dayProfileDate;
	}

	@Column(name = "MIN_KVA", precision = 20, scale = 3)
	public Double getMinKva() {
		return this.minKva;
	}

	public void setMinKva(Double minKva) {
		this.minKva = minKva;
	}

	@Column(name = "MAX_KVA", precision = 20, scale = 3)
	public Double getMaxKva() {
		return this.maxKva;
	}

	public void setMaxKva(Double maxKva) {
		this.maxKva = maxKva;
	}

	@Column(name = "SUM_KWH", precision = 20, scale = 3)
	public Double getSumKwh() {
		return this.sumKwh;
	}

	public void setSumKwh(Double sumKwh) {
		this.sumKwh = sumKwh;
	}

	@Column(name = "KWH_FLAG", precision = 22, scale = 0)
	public int getKwhFlag() {
		return this.kwhFlag;
	}

	public void setKwhFlag(int kwhFlag) {
		this.kwhFlag = kwhFlag;
	}

	@Column(name = "SUM_PF", precision = 20, scale = 3)
	public Double getSumPf() {
		return this.sumPf;
	}

	public void setSumPf(Double sumPf) {
		this.sumPf = sumPf;
	}

	@Column(name = "PF_05", precision = 22, scale = 0)
	public int getPf05() {
		return this.pf05;
	}

	public void setPf05(int pf05) {
		this.pf05 = pf05;
	}

	@Column(name = "PF_05_07", precision = 22, scale = 0)
	public int getPf0507() {
		return this.pf0507;
	}

	public void setPf0507(int pf0507) {
		this.pf0507 = pf0507;
	}

	@Column(name = "PF_07_09", precision = 22, scale = 0)
	public int getPf0709() {
		return this.pf0709;
	}

	public void setPf0709(int pf0709) {
		this.pf0709 = pf0709;
	}

	@Column(name = "PF_09", precision = 22, scale = 0)
	public int getPf09() {
		return this.pf09;
	}

	public void setPf09(int pf09) {
		this.pf09 = pf09;
	}

	@Column(name = "PF_NOLOAD", precision = 22, scale = 0)
	public int getPfNoload() {
		return this.pfNoload;
	}

	public void setPfNoload(int pfNoload) {
		this.pfNoload = pfNoload;
	}

	@Column(name = "PF_BLACKOUT", precision = 22, scale = 0)
	public int getPfBlackout() {
		return this.pfBlackout;
	}

	public void setPfBlackout(int pfBlackout) {
		this.pfBlackout = pfBlackout;
	}

	@Column(name = "IP_GS_20", precision = 22, scale = 0)
	public int getIpGs20() {
		return this.ipGs20;
	}

	public void setIpGs20(int ipGs20) {
		this.ipGs20 = ipGs20;
	}

	@Column(name = "IP_GS_20_40", precision = 22, scale = 0)
	public int getIpGs2040() {
		return this.ipGs2040;
	}

	public void setIpGs2040(int ipGs2040) {
		this.ipGs2040 = ipGs2040;
	}

	@Column(name = "IP_GS_40_60", precision = 22, scale = 0)
	public int getIpGs4060() {
		return this.ipGs4060;
	}

	public void setIpGs4060(int ipGs4060) {
		this.ipGs4060 = ipGs4060;
	}

	@Column(name = "IP_GS_60", precision = 22, scale = 0)
	public int getIpGs60() {
		return this.ipGs60;
	}

	public void setIpGs60(int ipGs60) {
		this.ipGs60 = ipGs60;
	}

	@Column(name = "IP_OUT_GS_20", precision = 22, scale = 0)
	public int getIpOutGs20() {
		return this.ipOutGs20;
	}

	public void setIpOutGs20(int ipOutGs20) {
		this.ipOutGs20 = ipOutGs20;
	}

	@Column(name = "IP_OUT_GS_20_40", precision = 22, scale = 0)
	public int getIpOutGs2040() {
		return this.ipOutGs2040;
	}

	public void setIpOutGs2040(int ipOutGs2040) {
		this.ipOutGs2040 = ipOutGs2040;
	}

	@Column(name = "IP_OUT_GS_40_60", precision = 22, scale = 0)
	public int getIpOutGs4060() {
		return this.ipOutGs4060;
	}

	public void setIpOutGs4060(int ipOutGs4060) {
		this.ipOutGs4060 = ipOutGs4060;
	}

	@Column(name = "IP_OUT_GS_60", precision = 22, scale = 0)
	public int getIpOutGs60() {
		return this.ipOutGs60;
	}

	public void setIpOutGs60(int ipOutGs60) {
		this.ipOutGs60 = ipOutGs60;
	}

	@Column(name = "MF", precision = 8, scale = 3)
	public Double getMf() {
		return this.mf;
	}

	public void setMf(Double mf) {
		this.mf = mf;
	}

	@Column(name = "CD", precision = 8, scale = 3)
	public Double getCd() {
		return this.cd;
	}

	public void setCd(Double cd) {
		this.cd = cd;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CDF_ID",insertable = false, updatable = false, nullable = false)
	public CDFData getCdfData() {
		return cdfData;
	}


	public void setCdfData(CDFData cdfData) {
		this.cdfData = cdfData;
	}


	@Column(name="SUM_KVA" ,precision=20,scale=3)
	public Double getSumKva()
	{
		return sumKva;
	}


	public void setSumKva(Double sumKva)
	{
		this.sumKva = sumKva;
	}

	
}
