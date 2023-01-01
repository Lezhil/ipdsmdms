package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "meterchange_transhistory" , schema="meter_data")
@NamedQueries({									
	@NamedQuery(name="METERCHANGE_TRANSHISTORY.getAllData",query="select model from MeterChangeTransHistory model order by entrydate DESC"),
})
public class MeterChangeTransHistory {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name ="ID")
		private int id;
		
		
		@Column(name = "oldmeterno")
		private String oldmeterno;
		
		@Column(name = "newmeterno")
		private String newmeterno;
		
		@Column(name = "lastinstataneouskwh")
		private Double lastinstataneouskwh;
		
		@Column(name = "lastinstataneousdate")
		private Timestamp lastinstataneousdate;
		
		@Column(name = "lastloadkwh")
		private Double lastloadkwh;
		
		@Column(name = "lastloaddate")
		private Timestamp lastloaddate;
		
		@Column(name = "initialreading")
		private Double initialreading;
		
		@Column(name = "mtrchangedate")
		private Timestamp mtrchangedate;
		
		@Column(name = "entrydate")
		private Timestamp entrydate;
		
		@Column(name = "entryby")
		private String entryby;
		
		@Column(name = "accno")
		private String accno;
		
		@Column(name = "consumername")
		private String consumername;
		
		@Column(name = "oldmf")
		private String oldmf;
		
		@Column(name = "newmf")
		private String newmf;

		@Column(name = "oldkvh")
		private String oldkvh;
		
		@Column(name = "newkvh")
		private String newkvh;
		
		@Column(name = "ipdsscheme")
		private String ipdsscheme;
		
		@Column(name = "reason")
		private String reason;
		
		@Column(name = "oldphase")
		private String oldphase;
		
		@Column(name = "newmtrmake")
		private String newmtrmake;
		
		@Column(name = "oldmtrmake")
		private String oldmtrmake;
		
		@Column(name = "newcapacity")
		private String newcapacity;
		
		@Column(name = "sync_genus")
		private String sync_genus;
		
		@Column(name = "sync_analogics")
		private String sync_analogics;
		
		
		
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getOldmeterno() {
			return oldmeterno;
		}

		public void setOldmeterno(String oldmeterno) {
			this.oldmeterno = oldmeterno;
		}

		public String getNewmeterno() {
			return newmeterno;
		}

		public void setNewmeterno(String newmeterno) {
			this.newmeterno = newmeterno;
		}

		public Double getLastinstaneouskwh() {
			return lastinstataneouskwh;
		}

		public void setLastinstaneouskwh(Double lastinstataneouskwh) {
			this.lastinstataneouskwh = lastinstataneouskwh;
		}

		public Timestamp getLastinstataneousdate() {
			return lastinstataneousdate;
		}

		public void setLastinstataneousdate(Timestamp lastinstataneousdate) {
			this.lastinstataneousdate = lastinstataneousdate;
		}

		public Double getLastloadkwh() {
			return lastloadkwh;
		}

		public void setLastloadkwh(Double lastloadkwh) {
			this.lastloadkwh = lastloadkwh;
		}

		public Timestamp getLastloaddate() {
			return lastloaddate;
		}

		public void setLastloaddate(Timestamp lastloaddate) {
			this.lastloaddate = lastloaddate;
		}

		public Double getInitialreading() {
			return initialreading;
		}

		public void setInitialreading(Double initialreading) {
			this.initialreading = initialreading;
		}

		

		public Timestamp getEntrydate() {
			return entrydate;
		}

		public void setEntrydate(Timestamp entrydate) {
			this.entrydate = entrydate;
		}

		

		public String getAccno() {
			return accno;
		}

		public void setAccno(String accno) {
			this.accno = accno;
		}

		public String getConsumername() {
			return consumername;
		}

		public void setConsumername(String consumername) {
			this.consumername = consumername;
		}

		public String getEntryby() {
			return entryby;
		}

		public void setEntryby(String entryby) {
			this.entryby = entryby;
		}

		public Double getLastinstataneouskwh() {
			return lastinstataneouskwh;
		}

		public void setLastinstataneouskwh(Double lastinstataneouskwh) {
			this.lastinstataneouskwh = lastinstataneouskwh;
		}

		public Timestamp getMtrchangedate() {
			return mtrchangedate;
		}

		public void setMtrchangedate(Timestamp mtrchangedate) {
			this.mtrchangedate = mtrchangedate;
		}

		public String getOldmf() {
			return oldmf;
		}

		public void setOldmf(String oldmf) {
			this.oldmf = oldmf;
		}

		public String getNewmf() {
			return newmf;
		}

		public void setNewmf(String newmf) {
			this.newmf = newmf;
		}

		public String getIpdsscheme() {
			return ipdsscheme;
		}

		public void setIpdsscheme(String ipdsscheme) {
			this.ipdsscheme = ipdsscheme;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getOldkvh() {
			return oldkvh;
		}

		public void setOldkvh(String oldkvh) {
			this.oldkvh = oldkvh;
		}

		public String getNewkvh() {
			return newkvh;
		}

		public void setNewkvh(String newkvh) {
			this.newkvh = newkvh;
		}

		public String getOldphase() {
			return oldphase;
		}

		public void setOldphase(String oldphase) {
			this.oldphase = oldphase;
		}

		public String getNewmtrmake() {
			return newmtrmake;
		}

		public void setNewmtrmake(String newmtrmake) {
			this.newmtrmake = newmtrmake;
		}

		public String getOldmtrmake() {
			return oldmtrmake;
		}

		public void setOldmtrmake(String oldmtrmake) {
			this.oldmtrmake = oldmtrmake;
		}

		public String getNewcapacity() {
			return newcapacity;
		}

		public void setNewcapacity(String newcapacity) {
			this.newcapacity = newcapacity;
		}

		public String getSync_genus() {
			return sync_genus;
		}

		public void setSync_genus(String sync_genus) {
			this.sync_genus = sync_genus;
		}

		public String getSync_analogics() {
			return sync_analogics;
		}

		public void setSync_analogics(String sync_analogics) {
			this.sync_analogics = sync_analogics;
		}

		
		 
		
		
		
	}	


