
package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "pfc_d2_rpt" , schema="meter_data")

	public class PfcD2reportEntity {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)

		@Column(name ="rec_id")
		private int id;
		
		@Column(name = "month_year")
		private String monthYear;
		
		@Column(name = "town")
		private String town;
		
		@Column(name = "nc_req_opening_cnt")
		private Double ncReqOpeningCnt;
		
		@Column(name = "nc_req_received")
		private Double ncReqReceived;
		
		@Column(name = "tot_nc_req")
		private Double totNcReq;
		
		@Column(name = "nc_req_closed")
		private Double ncReqClosed;
		
		@Column(name = "nc_req_pending")
		private Double ncReqPending;
		
		@Column(name = "closed_with_in_serc")
		private Double closedWithInSerc;
		
		@Column(name = "closed_beyond_serc")
		private Double closedBeyondSerc;
		
		@Column(name = "percent_within_serc")
		private Double percentWithinSerc;
		
		@Column(name = "released_by_it_system")
		private Double releasedByItSystem;
			
		@Column(name = "timestamp")
		private Timestamp timestamp;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

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

		public Double getNcReqOpeningCnt() {
			return ncReqOpeningCnt;
		}

		public void setNcReqOpeningCnt(Double ncReqOpeningCnt) {
			this.ncReqOpeningCnt = ncReqOpeningCnt;
		}

		public Double getNcReqReceived() {
			return ncReqReceived;
		}

		public void setNcReqReceived(Double ncReqReceived) {
			this.ncReqReceived = ncReqReceived;
		}

		public Double getTotNcReq() {
			return totNcReq;
		}

		public void setTotNcReq(Double totNcReq) {
			this.totNcReq = totNcReq;
		}

		public Double getNcReqClosed() {
			return ncReqClosed;
		}

		public void setNcReqClosed(Double ncReqClosed) {
			this.ncReqClosed = ncReqClosed;
		}

		public Double getNcReqPending() {
			return ncReqPending;
		}

		public void setNcReqPending(Double ncReqPending) {
			this.ncReqPending = ncReqPending;
		}

		public Double getClosedWithInSerc() {
			return closedWithInSerc;
		}

		public void setClosedWithInSerc(Double closedWithInSerc) {
			this.closedWithInSerc = closedWithInSerc;
		}

		public Double getClosedBeyondSerc() {
			return closedBeyondSerc;
		}

		public void setClosedBeyondSerc(Double closedBeyondSerc) {
			this.closedBeyondSerc = closedBeyondSerc;
		}

		public Double getPercentWithinSerc() {
			return percentWithinSerc;
		}

		public void setPercentWithinSerc(Double percentWithinSerc) {
			this.percentWithinSerc = percentWithinSerc;
		}

		public Double getReleasedByItSystem() {
			return releasedByItSystem;
		}

		public void setReleasedByItSystem(Double releasedByItSystem) {
			this.releasedByItSystem = releasedByItSystem;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
		}

		
}


