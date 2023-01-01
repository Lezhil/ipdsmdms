package com.bcits.mdas.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="non_communication_reason", schema="meter_data")
public class NonCommunicationReason {

	@EmbeddedId
	private KeyNonCommunication keyNonCommunication;
	
	//@Id
	@Column(name="id", insertable=false, updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="office_id")
	private String office_id;
	
	@Column(name="loc_type")
	private String loc_type;
	
	@Column(name="loc_idenity")
	private String loc_idenity;
	
	@Column(name="loc_name")
	private String loc_name;
	
	@Column(name="hes_type")
	private String hes_type;
	
	
	
	@Column(name="modem_sl_no")
	private String modem_sl_no;
	
	@Column(name="reason_type")
	private String reason_type;
	
	@Column(name="reason_desp")
	private String reason_desp;
	
	@Column(name="time_stamp")
	private Date time_stamp;
	
	public KeyNonCommunication getKeyNonCommunication() {
		return keyNonCommunication;
	}

	public void setKeyNonCommunication(KeyNonCommunication keyNonCommunication) {
		this.keyNonCommunication = keyNonCommunication;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getLoc_type() {
		return loc_type;
	}

	public void setLoc_type(String loc_type) {
		this.loc_type = loc_type;
	}

	public String getLoc_idenity() {
		return loc_idenity;
	}

	public void setLoc_idenity(String loc_idenity) {
		this.loc_idenity = loc_idenity;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getHes_type() {
		return hes_type;
	}

	public void setHes_type(String hes_type) {
		this.hes_type = hes_type;
	}

	public String getModem_sl_no() {
		return modem_sl_no;
	}

	public void setModem_sl_no(String modem_sl_no) {
		this.modem_sl_no = modem_sl_no;
	}

	public String getReason_type() {
		return reason_type;
	}

	public void setReason_type(String reason_type) {
		this.reason_type = reason_type;
	}

	public String getReason_desp() {
		return reason_desp;
	}

	public void setReason_desp(String reason_desp) {
		this.reason_desp = reason_desp;
	}

	public Date getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}

	@Embeddable
	public static  class KeyNonCommunication implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name="mtrno")
		private String mtrno;
		
		@Column(name = "date")
		private Date date;
		 
		public KeyNonCommunication(String mtrno, Date date) {
			super();
			this.mtrno = mtrno;
			this.date = date;
		}

		public KeyNonCommunication(){
			 
		}

		

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getMtrno() {
			return mtrno;
		}

		public void setMtrno(String mtrno) {
			this.mtrno = mtrno;
		}
	    
	}
	
	
}
