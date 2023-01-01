package com.bcits.mdas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="SAIDI_SAIFI_RPT", schema="METER_DATA")
public class SaidiSaifiEntity {

	@EmbeddedId
	private KeySaidi keySaidi;
	
	@Column(name = "id", insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="dt_name")
	private String dt_name;
	
	@Column(name="parent_feeder_id")
	private String parent_feeder_id;
	
	@Column(name="parent_feeder_code")
	private String parent_feeder_code;
	
	@Column(name="town_name")
	private String town_name;
	
	@Column(name="town_code")
	private String town_code;
	
	@Column(name="location_type")
	private String location_type;
	
	@Column(name="interruption_count")
	private Integer interruption_count;
	
	@Column(name="interruption_duration")
	private Integer interruption_duration;
	
	@Column(name="consumer_count")
	private Integer consumer_count;
	
	@Column(name="freeze_status")
	private Integer freeze_status;
	
	@Column(name="min_interruption_time")
	private Integer min_interruption_time;
	
	@Embeddable
	public static  class KeySaidi implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Column(name="monthyear")
		private Integer monthyear;
		
		@Column(name="dt_id")
		private String dt_id;

		
		
		public KeySaidi() {
			super();
		}

		public KeySaidi(Integer monthyear, String dt_id) {
			super();
			this.monthyear = monthyear;
			this.dt_id = dt_id;
		}

		public Integer getMonthyear() {
			return monthyear;
		}

		public void setMonthyear(Integer monthyear) {
			this.monthyear = monthyear;
		}

		public String getDt_id() {
			return dt_id;
		}

		public void setDt_id(String dt_id) {
			this.dt_id = dt_id;
		}
		
		
	}

	public KeySaidi getKeySaidi() {
		return keySaidi;
	}

	public void setKeySaidi(KeySaidi keySaidi) {
		this.keySaidi = keySaidi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDt_name() {
		return dt_name;
	}

	public void setDt_name(String dt_name) {
		this.dt_name = dt_name;
	}

	public String getParent_feeder_id() {
		return parent_feeder_id;
	}

	public void setParent_feeder_id(String parent_feeder_id) {
		this.parent_feeder_id = parent_feeder_id;
	}

	public String getParent_feeder_code() {
		return parent_feeder_code;
	}

	public void setParent_feeder_code(String parent_feeder_code) {
		this.parent_feeder_code = parent_feeder_code;
	}

	public String getTown_name() {
		return town_name;
	}

	public void setTown_name(String town_name) {
		this.town_name = town_name;
	}

	public String getTown_code() {
		return town_code;
	}

	public void setTown_code(String town_code) {
		this.town_code = town_code;
	}

	public String getLocation_type() {
		return location_type;
	}

	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}

	public Integer getInterruption_count() {
		return interruption_count;
	}

	public void setInterruption_count(Integer interruption_count) {
		this.interruption_count = interruption_count;
	}

	public Integer getInterruption_duration() {
		return interruption_duration;
	}

	public void setInterruption_duration(Integer interruption_duration) {
		this.interruption_duration = interruption_duration;
	}

	public Integer getConsumer_count() {
		return consumer_count;
	}

	public void setConsumer_count(Integer consumer_count) {
		this.consumer_count = consumer_count;
	}

	public Integer getFreeze_status() {
		return freeze_status;
	}

	public void setFreeze_status(Integer freeze_status) {
		this.freeze_status = freeze_status;
	}

	public Integer getMin_interruption_time() {
		return min_interruption_time;
	}

	public void setMin_interruption_time(Integer min_interruption_time) {
		this.min_interruption_time = min_interruption_time;
	}
}
