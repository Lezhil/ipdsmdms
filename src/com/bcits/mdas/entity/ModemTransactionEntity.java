package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;


@Entity
@Table(name = "modem_transactions", schema = "METER_DATA")
@NamedQueries({
	/* @NamedQuery(name="ModemTransactionEntity.addCommands",query="insert into modem_transactions"
	 		+ "(time_stamp,is_single_modem,modem_number,location_breadcrumbs,command,purpose,media,user_id)values(:)"),
*/
	 })

public class ModemTransactionEntity {

	@Id
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "time_stamp")
	private Timestamp timeStamp;
	
	@Column(name = "is_single_modem")
	private int is_single_modem;
	
	@Column(name = "modem_number")
	private String modem_number;
	
	
	public void setModem_number(String modem_number) {
		this.modem_number = modem_number;
	}

	@Column(name = "location_breadcrumbs")
	private String location_breadcrumbs;
	
	@Column(name = "command")
	private String command;

	
	@Column(name = "purpose")
	private String purpose;
	
	
	@Column(name = "media")
	private String media;
	
	@Column(name = "user_id")
	private String user_id;
	
	@Column(name = "phone_no")
	private String phone_no;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getIs_single_modem() {
		return is_single_modem;
	}

	public void setIs_single_modem(int is_single_modem) {
		this.is_single_modem = is_single_modem;
	}


	public String getLocation_breadcrumbs() {
		return location_breadcrumbs;
	}

	public void setLocation_breadcrumbs(String location_breadcrumbs) {
		this.location_breadcrumbs = location_breadcrumbs;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getModem_number() {
		return modem_number;
	}

	
	
}
