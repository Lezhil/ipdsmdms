/**
 * 
 */
package com.bcits.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author User
 *
 */
@Entity
@Table(name = "town_master", schema = "meter_data")
@NamedQueries({									
	@NamedQuery(name="TownEntity.getTownEntity",query="select model from TownEntity model where model.towncode=:towncode"),
})
public class TownEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "towncode")
	private String towncode;

	@Column(name = "town_name")
	private String town_name;
	
	@Column(name = "sld_file")
	private byte[] sld_file;

	@Column(name = "technical_loss")
	private String technical_loss;

	@Column(name = "createdby")
	private String createdby;
	
	@Column(name = "createddate")
	private Timestamp createddate;
	
	@Column(name = "updatedby")
	private String updatedby;

	@Column(name = "updateddate")
	private Timestamp updateddate;
	
	@Column(name = "golivedate")
	private Date golivedate;
	
	@Column(name = "filename")
	private String filename;
	
	@Column(name = "server_filepath")
	private String server_filepath;
	
	@Column(name = "baseline_loss")
	private String baseline_loss;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTowncode() {
		return towncode;
	}

	public void setTowncode(String towncode) {
		this.towncode = towncode;
	}

	public String getTown_name() {
		return town_name;
	}

	public void setTown_name(String town_name) {
		this.town_name = town_name;
	}

	public byte[] getSld_file() {
		return sld_file;
	}

	public void setSld_file(byte[] sld_file) {
		this.sld_file = sld_file;
	}

	public String getTechnical_loss() {
		return technical_loss;
	}

	public void setTechnical_loss(String technical_loss) {
		this.technical_loss = technical_loss;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Timestamp getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}

	public Date getGolivedate() {
		return golivedate;
	}

	public void setGolivedate(Date golivedate) {
		this.golivedate = golivedate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getServer_filepath() {
		return server_filepath;
	}

	public void setServer_filepath(String server_filepath) {
		this.server_filepath = server_filepath;
	}

	public String getBaseline_loss() {
		return baseline_loss;
	}

	public void setBaseline_loss(String baseline_loss) {
		this.baseline_loss = baseline_loss;
	}
	
	
	
	

}
