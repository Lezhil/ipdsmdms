package com.bcits.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "LOAD_AVAILABILITY", schema = "METER_DATA",uniqueConstraints= { @UniqueConstraint(columnNames = { "mtrno","date" }) })

public class LoadAvailabilityReportEntity {
	
		@EmbeddedId
		private KeyLoadAvl keyLoadAvl;
		
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


		@Column(name="fdrname")
		private String fdrname;

		@Column(name="load_count")
		private Integer load_count;


		@Column(name="load_interval")
		private Integer load_interval;



		@Embeddable
		public static  class KeyLoadAvl implements Serializable {

			private static final long serialVersionUID = 1L;

			@Column(name = "mtrno")
			private String mtrno;
			
			@Column(name = "date")
			private Date ldate;
			 
			public KeyLoadAvl(String mtrno, Date date) {
				super();
				this.mtrno = mtrno;
				this.ldate = date;
			}
			public KeyLoadAvl() {
				super();
			}


			public String getMtrno() {
				return mtrno;
			}

			public void setMtrno(String mtrno) {
				this.mtrno = mtrno;
			}

			public Date getLdate() {
				return ldate;
			}

			public void setLdate(Date ldate) {
				this.ldate = ldate;
			}
		    
		}



		public KeyLoadAvl getKeyLoadAvl() {
			return keyLoadAvl;
		}



		public void setKeyLoadAvl(KeyLoadAvl keyLoadAvl) {
			this.keyLoadAvl = keyLoadAvl;
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



		public String getFdrname() {
			return fdrname;
		}



		public void setFdrname(String fdrname) {
			this.fdrname = fdrname;
		}



		public Integer getLoad_count() {
			return load_count;
		}



		public void setLoad_count(Integer load_count) {
			this.load_count = load_count;
		}



		public Integer getLoad_interval() {
			return load_interval;
		}



		public void setLoad_interval(Integer load_interval) {
			this.load_interval = load_interval;
		}
		
	}



