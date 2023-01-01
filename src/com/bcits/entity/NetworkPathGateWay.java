package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="networkgateway",schema="meter_data")
@NamedQueries({	
	
	@NamedQuery(name="NetworkPathGateWay.findUser",query="SELECT s FROM NetworkPathGateWay s ")
	
	})
public class NetworkPathGateWay {

	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="folder_path")
	private String folder_path;
	
	@Column(name="folder_name")
	private String folder_name;

	@Column(name="final_path")
	private String final_path;
	

	@Column(name="loggeduser")
	private String loggedUser;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFolder_path() {
		return folder_path;
	}

	public void setFolder_path(String folder_path) {
		this.folder_path = folder_path;
	}

	public String getFolder_name() {
		return folder_name;
	}

	public void setFolder_name(String folder_name) {
		this.folder_name = folder_name;
	}

	public String getFinal_path() {
		return final_path;
	}

	public void setFinal_path(String final_path) {
		this.final_path = final_path;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name="timestamp")
	private Date timestamp;
	
	 
	
}
