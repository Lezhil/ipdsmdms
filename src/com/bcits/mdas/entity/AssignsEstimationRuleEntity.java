package com.bcits.mdas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Tarik
 *
 */
@Entity
@Table(name="ML_TO_ESTIMATION_RULE_MAP", schema="meter_data",uniqueConstraints = { @UniqueConstraint(columnNames = { "e_rule_id", "location_type", "location_code"}) })
@NamedQueries({
	
	@NamedQuery(name="AssignsEstimationRuleEntity.getAssignRuleId", query="select a from AssignsEstimationRuleEntity a where a.myKey.e_rule_id=:e_rule_id and a.myKey.location_type=:location_type and a.myKey.location_code=:location_code"),
	
})
public class AssignsEstimationRuleEntity {

	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private EstKeyValidation myKey; // RULUID, LOCATIONTYPE  LOCATIONCODE
	
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "entry_date")
	private Timestamp entry_date;
	
	@Column(name = "entry_by")
	private String entry_by;
	
	@Column(name = "update_date")
	private Timestamp update_date;
	
	@Column(name = "update_by")
	private String update_by;
	
	@Column(name = "status")
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Timestamp getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Timestamp entry_date) {
		this.entry_date = entry_date;
	}

	public String getEntry_by() {
		return entry_by;
	}

	public void setEntry_by(String entry_by) {
		this.entry_by = entry_by;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	
	
	public EstKeyValidation getMyKey() {
		return myKey;
	}

	public void setMyKey(EstKeyValidation myKey) {
		this.myKey = myKey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	@Embeddable
	public static  class EstKeyValidation implements Serializable {

		private static final long serialVersionUID = 2L;

		@Column(name = "e_rule_id")
		private String e_rule_id;
		
		@Column(name = "location_type")
		private String location_type;
		
		@Column(name = "location_code")
		private String location_code;

		
		public  EstKeyValidation (String e_rule_id, String location_type, String location_code) {
			super();
			this.e_rule_id = e_rule_id;
			this.location_type = location_type;
			this.location_code = location_code;
		}

		public EstKeyValidation() {
			super();
		}

		public String getE_rule_id() {
			return e_rule_id;
		}

		public void setE_rule_id(String e_rule_id) {
			this.e_rule_id = e_rule_id;
		}

		public String getLocation_type() {
			return location_type;
		}

		public void setLocation_type(String location_type) {
			this.location_type = location_type;
		}

		public String getLocation_code() {
			return location_code;
		}

		public void setLocation_code(String location_code) {
			this.location_code = location_code;
		}

			     
		
	}
}
