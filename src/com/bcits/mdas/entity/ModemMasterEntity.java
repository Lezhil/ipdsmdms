package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "modem_master", schema = "METER_DATA")
@NamedQueries({
	 @NamedQuery(name="ModemMasterEntity.findAll",query="SELECT fm FROM ModemMasterEntity fm"),
	 @NamedQuery(name="ModemMasterEntity.getEntityByImei",query="SELECT fm FROM ModemMasterEntity fm where fm.modem_imei = :modem_imei"),

	 })
public class ModemMasterEntity {

	@Id
	@Column(name = "id", insertable= false, updatable= false )
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(name = "modem_serial_no")
	private String modem_serial_no;

	@Column(name = "modem_imei")
	private String modem_imei;

	@Column(name = "sim_imsi")
	private String sim_imsi;

	@Column(name = "sim_ccid")
	private String sim_ccid;

	@Column(name = "attached_mtrno")
	private String attached_mtrno;
	
	@Column(name = "phone_no")
	private String phone_no;
	
	public String getInstalled() {
		return installed;
	}

	public void setInstalled(String installed) {
		this.installed = installed;
	}

	@Column(name = "working_status")
	private String working_status;
	
	@Column(name = "installed")
	private String installed;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModem_serial_no() {
		return modem_serial_no;
	}

	public void setModem_serial_no(String modem_serial_no) {
		this.modem_serial_no = modem_serial_no;
	}

	public String getModem_imei() {
		return modem_imei;
	}

	public void setModem_imei(String modem_imei) {
		this.modem_imei = modem_imei;
	}

	public String getSim_imsi() {
		return sim_imsi;
	}

	public void setSim_imsi(String sim_imsi) {
		this.sim_imsi = sim_imsi;
	}

	public String getSim_ccid() {
		return sim_ccid;
	}

	public void setSim_ccid(String sim_ccid) {
		this.sim_ccid = sim_ccid;
	}

	public String getAttached_mtrno() {
		return attached_mtrno;
	}

	public void setAttached_mtrno(String attached_mtrno) {
		this.attached_mtrno = attached_mtrno;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getWorking_status() {
		return working_status;
	}

	public void setWorking_status(String working_status) {
		this.working_status = working_status;
	}
	

	

	



}
