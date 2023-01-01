package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "modem_master" , schema="mdm_test")
public class ModemMaster {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="ID")
	private int id;
	
	
	@Column(name = "MODEM_CODE")
	private String modem_code;
	
	@Column(name = "MODEM_SERIAL_NO")
	private Integer modem_serial_no;
	
	@Column(name = "MODEM_HOST_NAME")
	private String modem_host_name;
	
	@Column(name = "SIM_SERIAL_NO")
	private Integer sim_serial_no;
	
	@Column(name = "MOBILE_NO")
	private String mobile_no;
	
	@Column(name = "APN")
	private String apn;
	
	@Column(name = "SIM_SERVICE_PROVIDER")
	private String sim_service_provider;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModem_code() {
		return modem_code;
	}

	public void setModem_code(String modem_code) {
		this.modem_code = modem_code;
	}

	

	public Integer getModem_serial_no() {
		return modem_serial_no;
	}

	public void setModem_serial_no(Integer modem_serial_no) {
		this.modem_serial_no = modem_serial_no;
	}

	public String getModem_host_name() {
		return modem_host_name;
	}

	public void setModem_host_name(String modem_host_name) {
		this.modem_host_name = modem_host_name;
	}

	public Integer getSim_serial_no() {
		return sim_serial_no;
	}

	public void setSim_serial_no(Integer sim_serial_no) {
		this.sim_serial_no = sim_serial_no;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getApn() {
		return apn;
	}

	public void setApn(String apn) {
		this.apn = apn;
	}

	public String getSim_service_provider() {
		return sim_service_provider;
	}

	public void setSim_service_provider(String sim_service_provider) {
		this.sim_service_provider = sim_service_provider;
	}
	
	

}
