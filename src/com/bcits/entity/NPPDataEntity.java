package com.bcits.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "NPP_DATA", schema = "METER_DATA", uniqueConstraints = { @UniqueConstraint(columnNames = { "monthyear","feeder_code" }) })

@NamedQueries({
	@NamedQuery(name="NPPDataEntity.getDataByMonthYear", query="select n from NPPDataEntity n where monthYear=:monthYear"),
	@NamedQuery(name="NPPDataEntity.getDataByTownMonthYear", query="select n from NPPDataEntity n where monthYear=:monthYear and n.keyNPPData.feedercode in "
			+ "(select f.fdrid from FeederEntity f where f.tpparentid in (select s.sstpid from SubstationDetailsEntity s where s.parent_town in (:towns)))")
	
})

public class NPPDataEntity {
	
	@EmbeddedId
	private KeyNPPData keyNPPData;
	
	
	//@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "feeder_type")
	private String feedertype;

	@Column(name = "start_billing_date")
	private Date start_billing_date;
	
	@Column(name = "end_billling_date")
	private Date end_billling_date;
	
	@Column(name = "no_of_power_faliure")
	private Long no_of_power_faliure;
	
	@Column(name = "duration_of_power_faliure")
	private Long duration_of_power_faliure;
	
	@Column(name = "min_voltage")
	private Double min_voltage;
	
	@Column(name = "max_current")
	private Double max_current;
	
	@Column(name = "input_energy")
	private Double input_energy;
	
	@Column(name = "export_energy")
	private Double export_energy=0.0;

	@Column(name = "ht_ind_con_count")
	private Integer ht_ind_con_count=0;
	
	@Column(name = "ht_com_con_count")
	private Integer ht_com_con_count=0;
	
	@Column(name = "lt_ind_con_count")
	private Integer lt_ind_con_count=0;
	
	@Column(name = "lt_com_con_count")
	private Integer lt_com_con_count=0;
	
	@Column(name = "lt_dom_con_count")
	private Integer lt_dom_con_count=0;
	
	@Column(name = "govt_con_count")
	private Integer govt_con_count=0;
	
	@Column(name = "agri_con_count")
	private Integer agri_con_count=0;
	
	@Column(name = "other_con_count")
	private Integer other_con_count=0;
	
	@Column(name = "ht_ind_energy_bill")
	private Double ht_ind_energy_bill=0.0;
	
	@Column(name = "ht_com_energy_bill")
	private Double ht_com_energy_bill=0.0;
	
	@Column(name = "lt_ind_energy_bill")
	private Double lt_ind_energy_bill=0.0;
	
	@Column(name = "lt_com_energy_bill")
	private Double lt_com_energy_bill=0.0;
	
	@Column(name = "lt_dom_energy_bill")
	private Double lt_dom_energy_bill=0.0;
	
	@Column(name = "govt_energy_bill")
	private Double govt_energy_bill=0.0;
	
	@Column(name = "agri_energy_bill")
	private Double agri_energy_bill=0.0;
	
	@Column(name = "other_energy_bill")
	private Double other_energy_bill=0.0;
	
	@Column(name = "ht_ind_amount_bill")
	private Double ht_ind_amount_bill=0.0;
	
	@Column(name = "ht_com_amount_bill")
	private Double ht_com_amount_bill=0.0;
	
	@Column(name = "lt_ind_amount_bill")
	private Double lt_ind_amount_bill=0.0;
	
	@Column(name = "lt_com_amount_bill")
	private Double lt_com_amount_bill=0.0;
	
	@Column(name = "lt_dom_amount_bill")
	private Double lt_dom_amount_bill=0.0;
	
	@Column(name = "govt_amount_bill")
	private Double govt_amount_bill=0.0;
	
	@Column(name = "agri_amount_bill")
	private Double agri_amount_bill=0.0;
	
	@Column(name = "other_amount_bill")
	private Double other_amount_bill=0.0;
	
	@Column(name = "ht_ind_amount_collected")
	private Double ht_ind_amount_collected=0.0;
	
	@Column(name = "ht_com_amount_collected")
	private Double ht_com_amount_collected=0.0;
	
	@Column(name = "lt_ind_amount_collected")
	private Long lt_ind_amount_collected=(long)0;
	
	@Column(name = "lt_com_amount_collected")
	private Long lt_com_amount_collected=(long)0;
	
	@Column(name = "lt_dom_amount_collected")
	private Long lt_dom_amount_collected=(long)0;
	
	@Column(name = "govt_amount_collected")
	private Long govt_amount_collected=(long)0;
	
	@Column(name = "agri_amount_collected")
	private Long agri_amount_collected=(long)0;
	
	@Column(name = "other_amount_collected")
	private Long other_amount_collected=(long)0;
	
	@Column(name = "feeder_name")
	private String feeder_name;
	
	@Column(name = "mtrno")
	private String mtrno;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public KeyNPPData getKeyNPPData() {
		return keyNPPData;
	}

	public void setKeyNPPData(KeyNPPData keyNPPData) {
		this.keyNPPData = keyNPPData;
	}
	

	public String getFeedertype() {
		return feedertype;
	}

	public void setFeedertype(String feedertype) {
		this.feedertype = feedertype;
	}

	public Date getStart_billing_date() {
		return start_billing_date;
	}

	public void setStart_billing_date(Date start_billing_date) {
		this.start_billing_date = start_billing_date;
	}

	public Date getEnd_billling_date() {
		return end_billling_date;
	}

	public void setEnd_billling_date(Date end_billling_date) {
		this.end_billling_date = end_billling_date;
	}

	public Long getNo_of_power_faliure() {
		return no_of_power_faliure;
	}

	public void setNo_of_power_faliure(Long no_of_power_faliure) {
		this.no_of_power_faliure = no_of_power_faliure;
	}

	public Long getDuration_of_power_faliure() {
		return duration_of_power_faliure;
	}

	public void setDuration_of_power_faliure(Long duration_of_power_faliure) {
		this.duration_of_power_faliure = duration_of_power_faliure;
	}

	

	public Double getMin_voltage() {
		return min_voltage;
	}

	public void setMin_voltage(Double min_voltage) {
		this.min_voltage = min_voltage;
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

	public Integer getHt_ind_con_count() {
		return ht_ind_con_count;
	}

	public void setHt_ind_con_count(Integer ht_ind_con_count) {
		this.ht_ind_con_count = ht_ind_con_count;
	}

	public Integer getHt_com_con_count() {
		return ht_com_con_count;
	}

	public void setHt_com_con_count(Integer ht_com_con_count) {
		this.ht_com_con_count = ht_com_con_count;
	}

	public Integer getLt_ind_con_count() {
		return lt_ind_con_count;
	}

	public void setLt_ind_con_count(Integer lt_ind_con_count) {
		this.lt_ind_con_count = lt_ind_con_count;
	}

	public Integer getLt_com_con_count() {
		return lt_com_con_count;
	}

	public void setLt_com_con_count(Integer lt_com_con_count) {
		this.lt_com_con_count = lt_com_con_count;
	}

	public Integer getLt_dom_con_count() {
		return lt_dom_con_count;
	}

	public void setLt_dom_con_count(Integer lt_dom_con_count) {
		this.lt_dom_con_count = lt_dom_con_count;
	}

	public Integer getGovt_con_count() {
		return govt_con_count;
	}

	public void setGovt_con_count(Integer govt_con_count) {
		this.govt_con_count = govt_con_count;
	}

	public Integer getAgri_con_count() {
		return agri_con_count;
	}

	public void setAgri_con_count(Integer agri_con_count) {
		this.agri_con_count = agri_con_count;
	}

	public Integer getOther_con_count() {
		return other_con_count;
	}

	public void setOther_con_count(Integer other_con_count) {
		this.other_con_count = other_con_count;
	}

	public Double getHt_ind_energy_bill() {
		return ht_ind_energy_bill;
	}

	public void setHt_ind_energy_bill(Double ht_ind_energy_bill) {
		this.ht_ind_energy_bill = ht_ind_energy_bill;
	}

	public Double getHt_com_energy_bill() {
		return ht_com_energy_bill;
	}

	public void setHt_com_energy_bill(Double ht_com_energy_bill) {
		this.ht_com_energy_bill = ht_com_energy_bill;
	}

	public Double getLt_ind_energy_bill() {
		return lt_ind_energy_bill;
	}

	public void setLt_ind_energy_bill(Double lt_ind_energy_bill) {
		this.lt_ind_energy_bill = lt_ind_energy_bill;
	}

	public Double getLt_com_energy_bill() {
		return lt_com_energy_bill;
	}

	public void setLt_com_energy_bill(Double lt_com_energy_bill) {
		this.lt_com_energy_bill = lt_com_energy_bill;
	}

	public Double getLt_dom_energy_bill() {
		return lt_dom_energy_bill;
	}

	public void setLt_dom_energy_bill(Double lt_dom_energy_bill) {
		this.lt_dom_energy_bill = lt_dom_energy_bill;
	}

	public Double getGovt_energy_bill() {
		return govt_energy_bill;
	}

	public void setGovt_energy_bill(Double govt_energy_bill) {
		this.govt_energy_bill = govt_energy_bill;
	}

	public Double getAgri_energy_bill() {
		return agri_energy_bill;
	}

	public void setAgri_energy_bill(Double agri_energy_bill) {
		this.agri_energy_bill = agri_energy_bill;
	}

	public Double getOther_energy_bill() {
		return other_energy_bill;
	}

	public void setOther_energy_bill(Double other_energy_bill) {
		this.other_energy_bill = other_energy_bill;
	}

	public Double getHt_ind_amount_bill() {
		return ht_ind_amount_bill;
	}

	public void setHt_ind_amount_bill(Double ht_ind_amount_bill) {
		this.ht_ind_amount_bill = ht_ind_amount_bill;
	}

	public Double getHt_com_amount_bill() {
		return ht_com_amount_bill;
	}

	public void setHt_com_amount_bill(Double ht_com_amount_bill) {
		this.ht_com_amount_bill = ht_com_amount_bill;
	}

	public Double getLt_ind_amount_bill() {
		return lt_ind_amount_bill;
	}

	public void setLt_ind_amount_bill(Double lt_ind_amount_bill) {
		this.lt_ind_amount_bill = lt_ind_amount_bill;
	}

	public Double getLt_com_amount_bill() {
		return lt_com_amount_bill;
	}

	public void setLt_com_amount_bill(Double lt_com_amount_bill) {
		this.lt_com_amount_bill = lt_com_amount_bill;
	}

	public Double getLt_dom_amount_bill() {
		return lt_dom_amount_bill;
	}

	public void setLt_dom_amount_bill(Double lt_dom_amount_bill) {
		this.lt_dom_amount_bill = lt_dom_amount_bill;
	}

	public Double getGovt_amount_bill() {
		return govt_amount_bill;
	}

	public void setGovt_amount_bill(Double govt_amount_bill) {
		this.govt_amount_bill = govt_amount_bill;
	}

	public Double getAgri_amount_bill() {
		return agri_amount_bill;
	}

	public void setAgri_amount_bill(Double agri_amount_bill) {
		this.agri_amount_bill = agri_amount_bill;
	}

	public Double getOther_amount_bill() {
		return other_amount_bill;
	}

	public void setOther_amount_bill(Double other_amount_bill) {
		this.other_amount_bill = other_amount_bill;
	}

	public Double getHt_ind_amount_collected() {
		return ht_ind_amount_collected;
	}

	public void setHt_ind_amount_collected(Double ht_ind_amount_collected) {
		this.ht_ind_amount_collected = ht_ind_amount_collected;
	}

	public Double getHt_com_amount_collected() {
		return ht_com_amount_collected;
	}

	public void setHt_com_amount_collected(Double ht_com_amount_collected) {
		this.ht_com_amount_collected = ht_com_amount_collected;
	}

	public Long getLt_ind_amount_collected() {
		return lt_ind_amount_collected;
	}

	public void setLt_ind_amount_collected(Long lt_ind_amount_collected) {
		this.lt_ind_amount_collected = lt_ind_amount_collected;
	}

	public Long getLt_com_amount_collected() {
		return lt_com_amount_collected;
	}

	public void setLt_com_amount_collected(Long lt_com_amount_collected) {
		this.lt_com_amount_collected = lt_com_amount_collected;
	}

	public Long getLt_dom_amount_collected() {
		return lt_dom_amount_collected;
	}

	public void setLt_dom_amount_collected(Long lt_dom_amount_collected) {
		this.lt_dom_amount_collected = lt_dom_amount_collected;
	}

	public Long getGovt_amount_collected() {
		return govt_amount_collected;
	}

	public void setGovt_amount_collected(Long govt_amount_collected) {
		this.govt_amount_collected = govt_amount_collected;
	}

	public Long getAgri_amount_collected() {
		return agri_amount_collected;
	}

	public void setAgri_amount_collected(Long agri_amount_collected) {
		this.agri_amount_collected = agri_amount_collected;
	}

	public Long getOther_amount_collected() {
		return other_amount_collected;
	}

	public void setOther_amount_collected(Long other_amount_collected) {
		this.other_amount_collected = other_amount_collected;
	}

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}

	public String getMtrno() {
		return mtrno;
	}

	public void setMtrno(String mtrno) {
		this.mtrno = mtrno;
	}

	
	@Embeddable 
	public static  class KeyNPPData implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Column(name = "monthyear")
		private Integer monthYear;
				
		@Column(name = "feeder_code")
		private String feedercode;
		
		
		public KeyNPPData(Integer monthYear, String feedercode) {
			super();
			this.monthYear = monthYear;
			this.feedercode = feedercode;
		}
		
		public KeyNPPData() {
			
		}

		public Integer getMonthYear() {
			return monthYear;
		}

		public void setMonthYear(Integer monthYear) {
			this.monthYear = monthYear;
		}

		public String getFeedercode() {
			return feedercode;
		}

		public void setFeedercode(String feedercode) {
			this.feedercode = feedercode;
		}
		
	}

}