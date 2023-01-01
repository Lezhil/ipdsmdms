package com.bcits.mdas.entity;

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
@Table(name = "sim_details", schema = "meter_data")
@NamedQueries({
	@NamedQuery(name = "SimDetailsEntity.getsimdetails", query = "select d from SimDetailsEntity d"),
})


public class SimDetailsEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="sim_sno")
	private String simsrno;
	
	@Column(name="nwk_ser_provider")
	private String nsprovider;
	
	@Column(name="mob_dir_number")
	private String mdnumber;
	
	@Column(name="sim_status")
	private String simstatus;
	
	@Column(name="sim_static_ip")
	private String simstaticip;
	
	@Column(name="barcode")
	private String barcode;
	
	@Column(name="entry_by")
	private String entryby;
	
	@Column(name="entry_date")
	private Timestamp entrydate;
	
	@Column(name="update_by")
	private String updateby;
	
	@Column(name="update_date")
	private Timestamp updatedate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSimsrno() {
		return simsrno;
	}

	public void setSimsrno(String simsrno) {
		this.simsrno = simsrno;
	}

	public String getNsprovider() {
		return nsprovider;
	}

	public void setNsprovider(String nsprovider) {
		this.nsprovider = nsprovider;
	}

	public String getMdnumber() {
		return mdnumber;
	}

	public void setMdnumber(String mdnumber) {
		this.mdnumber = mdnumber;
	}

	public String getSimstatus() {
		return simstatus;
	}

	public void setSimstatus(String simstatus) {
		this.simstatus = simstatus;
	}

	public String getSimstaticip() {
		return simstaticip;
	}

	public void setSimstaticip(String simstaticip) {
		this.simstaticip = simstaticip;
	}

	public String getEntryby() {
		return entryby;
	}

	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public Timestamp getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
}
