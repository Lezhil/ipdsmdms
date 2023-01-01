package com.bcits.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AMIInstantaneousEntity.AMIKeyInstantaneous;

@Entity
@Table(name = "pfc_d2_rpt_intermediate" , schema="meter_data",uniqueConstraints={@UniqueConstraint(columnNames = { "month_year","town" })})
public class PfcD2IntermediateEntity {
	
	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private PfcD2IntermediateKey myKey; // month_year , towncode

	@Column(name ="rec_id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long recid;	
	
	
	@Column(name = "nc_req_opening_cnt")
	private Integer nc_req_opening_cnt;
	
	@Column(name = "nc_req_received")
	private Integer nc_req_received;
	
	@Column(name = "tot_nc_req")
	private Integer tot_nc_req;
	
	@Column(name = "nc_req_closed")
	private Integer nc_req_closed;
		
	@Column(name = "nc_req_pending")
	private Integer nc_req_pending;
	
	@Column(name = "closed_with_in_serc")
	private Integer closed_with_in_serc;
	
	@Column(name = "closed_beyond_serc")
	private Integer closed_beyond_serc;
	
	@Column(name = "percent_within_serc")
	private Double percent_within_serc;
	
	@Column(name = "released_by_it_system")
	private Integer released_by_it_system;
	
	@Column(name = "timestamp")
	private Timestamp timestamp;
	
	@Column(name = "readtime")
	private Timestamp readtime;


	

	public PfcD2IntermediateKey getMyKey() {
		return myKey;
	}

	public void setMyKey(PfcD2IntermediateKey myKey) {
		this.myKey = myKey;
	}
	
	
	@Embeddable
	public static  class PfcD2IntermediateKey implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "month_year")
		private String monthYear;
		
		@Column(name = "town")
		private String town;	//varchar

		public String getMonthYear() {
			return monthYear;
		}

		public void setMonthYear(String monthYear) {
			this.monthYear = monthYear;
		}

		public String getTown() {
			return town;
		}

		public void setTown(String town) {
			this.town = town;
		}
		  
		public PfcD2IntermediateKey(String monthYear, String town) {
			super();
			this.monthYear = monthYear;
			this.town = town;
		}

		public PfcD2IntermediateKey() {
			
		}

		
	}


	public Long getRecid() {
		return recid;
	}

	public void setRecid(Long recid) {
		this.recid = recid;
	}

	public Integer getNc_req_opening_cnt() {
		return nc_req_opening_cnt;
	}

	public void setNc_req_opening_cnt(Integer nc_req_opening_cnt) {
		this.nc_req_opening_cnt = nc_req_opening_cnt;
	}

	public Integer getNc_req_received() {
		return nc_req_received;
	}

	public void setNc_req_received(Integer nc_req_received) {
		this.nc_req_received = nc_req_received;
	}

	public Integer getTot_nc_req() {
		return tot_nc_req;
	}

	public void setTot_nc_req(Integer tot_nc_req) {
		this.tot_nc_req = tot_nc_req;
	}

	public Integer getNc_req_closed() {
		return nc_req_closed;
	}

	public void setNc_req_closed(Integer nc_req_closed) {
		this.nc_req_closed = nc_req_closed;
	}

	public Integer getNc_req_pending() {
		return nc_req_pending;
	}

	public void setNc_req_pending(Integer nc_req_pending) {
		this.nc_req_pending = nc_req_pending;
	}

	public Integer getClosed_with_in_serc() {
		return closed_with_in_serc;
	}

	public void setClosed_with_in_serc(Integer closed_with_in_serc) {
		this.closed_with_in_serc = closed_with_in_serc;
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

	public Integer getReleased_by_it_system() {
		return released_by_it_system;
	}

	public void setReleased_by_it_system(Integer released_by_it_system) {
		this.released_by_it_system = released_by_it_system;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getReadtime() {
		return readtime;
	}

	public void setReadtime(Timestamp readtime) {
		this.readtime = readtime;
	}


	



	

}
