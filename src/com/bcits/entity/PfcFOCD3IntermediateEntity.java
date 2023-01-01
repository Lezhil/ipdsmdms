package com.bcits.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "pfc_focd3_rpt_intermediate", schema="meter_data",uniqueConstraints={@UniqueConstraint(columnNames = { "month_year","town_code" })})

public class PfcFOCD3IntermediateEntity {

	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private pfcfocd3IntermediateKey myKey; // month_year , towncode
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rec_id",insertable=false,updatable=false)
	private Long rec_id;

	@Column(name = "comp_opening_cnt")
	private Integer comp_opening_cnt;

	@Column(name = "comp_received")
	private Integer comp_received;

	@Column(name = "tot_complains")
	private Integer tot_complains;

	@Column(name = "comp_closed")
	private Integer compclosed;

	@Column(name = "comp_pending")
	private Integer comp_pending;

	@Column(name = "closed_with_in_serc")
	private Integer closed_with_inserc;

	@Column(name = "closed_beyond_serc")
	private Integer closed_beyond_serc;

	@Column(name = "percent_within_serc")
	private Double percent_within_serc;

	@Column(name = "timestamp")
	private Timestamp time_stamp;
	
	@Column(name = "flag")
	private String flag;
	
	@Column(name = "readtime")
	private Timestamp readtime;
	

	public Long getRec_id() {
		return rec_id;
	}

	public void setRec_id(Long rec_id) {
		this.rec_id = rec_id;
	}

	public Integer getComp_opening_cnt() {
		return comp_opening_cnt;
	}

	public void setComp_opening_cnt(Integer comp_opening_cnt) {
		this.comp_opening_cnt = comp_opening_cnt;
	}

	public Integer getComp_received() {
		return comp_received;
	}

	public void setComp_received(Integer comp_received) {
		this.comp_received = comp_received;
	}

	public Integer getTot_complains() {
		return tot_complains;
	}

	public void setTot_complains(Integer tot_complains) {
		this.tot_complains = tot_complains;
	}

	public Integer getCompclosed() {
		return compclosed;
	}

	public void setCompclosed(Integer compclosed) {
		this.compclosed = compclosed;
	}

	public Integer getComp_pending() {
		return comp_pending;
	}

	public void setComp_pending(Integer comp_pending) {
		this.comp_pending = comp_pending;
	}

	public Integer getClosed_with_inserc() {
		return closed_with_inserc;
	}

	public void setClosed_with_inserc(Integer closed_with_inserc) {
		this.closed_with_inserc = closed_with_inserc;
	}

	public Integer getClosed_beyond_serc() {
		return closed_beyond_serc;
	}

	public void setClosed_beyond_serc(Integer closed_beyond_serc) {
		this.closed_beyond_serc = closed_beyond_serc;
	}

	public Double getPercent_within_serc() {
		return percent_within_serc;
	}

	public void setPercent_within_serc(Double percent_within_serc) {
		this.percent_within_serc = percent_within_serc;
	}

	public Timestamp getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}


	public pfcfocd3IntermediateKey getMyKey() {
		return myKey;
	}

	public void setMyKey(pfcfocd3IntermediateKey myKey) {
		this.myKey = myKey;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Embeddable
	public static class pfcfocd3IntermediateKey  implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Column(name = "month_year")
		private String month_year;

		@Column(name = "town_code")
		private String town_code;
	
		public String getMonth_year() {
			return month_year;
		}

		public void setMonth_year(String month_year) {
			this.month_year = month_year;
		}

		public String getTown_code() {
			return town_code;
		}

		public void setTown_code(String town_code) {
			this.town_code = town_code;
		}

		public pfcfocd3IntermediateKey(String month_year, String town_code) {
			super();
			this.month_year = month_year;
			this.town_code = town_code;
		}

		public pfcfocd3IntermediateKey() {
			
		}
		
}

	public Timestamp getReadtime() {
		return readtime;
	}

	public void setReadtime(Timestamp readtime) {
		this.readtime = readtime;
	}





}
