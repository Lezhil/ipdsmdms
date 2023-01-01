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





@Entity
@Table(name = "daily_load",  schema = "METER_DATA",uniqueConstraints = { @UniqueConstraint(columnNames = { "rtc_date_time","mtrno" }) })

public class AmrDailyLoadEntity implements Comparable<AmrDailyLoadEntity> {
	
	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private DailyKeyLoad myKey; // READ_TIME, METER NUMBER
	   
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "time_stamp")
	private Timestamp timeStamp;
	
	@Column(name = "cum_active_import_energy")
	private Double cum_active_import_energy;	
	
	@Column(name = "cum_active_export_energy")
	private Double cum_active_export_energy;
	
	@Column(name = "cum_apparent_import_energy")
	private Double cum_apparent_import_energy;
	
	@Column(name = "cum_apparent_export_energy")
	private Double cum_apparent_export_energy;
	
	@Column(name = "cum_reactive_energy5")
	private Double cum_reactive_energy5;
	
	@Column(name = "cum_reactive_energy6")
	private Double cum_reactive_energy6;
	
	@Column(name = "cum_reactive_energy7")
	private Double cum_reactive_energy7;
	
	@Column(name = "cum_reactive_energy8")
	private Double cum_reactive_energy8;
	
	@Column(name = "flag")
	private String flag;
	
	@Column(name = "kvrah_high")
	private Double kvrah_high;
	
	@Column(name = "kvarh_low")
	private Double kvarh_low;
	

	public Double getCum_active_import_energy() {
		return cum_active_import_energy;
	}

	public void setCum_active_import_energy(Double cum_active_import_energy) {
		this.cum_active_import_energy = cum_active_import_energy;
	}

	public Double getCum_active_export_energy() {
		return cum_active_export_energy;
	}

	public void setCum_active_export_energy(Double cum_active_export_energy) {
		this.cum_active_export_energy = cum_active_export_energy;
	}

	public Double getCum_apparent_import_energy() {
		return cum_apparent_import_energy;
	}

	public void setCum_apparent_import_energy(Double cum_apparent_import_energy) {
		this.cum_apparent_import_energy = cum_apparent_import_energy;
	}

	public Double getCum_apparent_export_energy() {
		return cum_apparent_export_energy;
	}

	public void setCum_apparent_export_energy(Double cum_apparent_export_energy) {
		this.cum_apparent_export_energy = cum_apparent_export_energy;
	}

	public Double getCum_reactive_energy5() {
		return cum_reactive_energy5;
	}

	public void setCum_reactive_energy5(Double cum_reactive_energy5) {
		this.cum_reactive_energy5 = cum_reactive_energy5;
	}

	public Double getCum_reactive_energy6() {
		return cum_reactive_energy6;
	}

	public void setCum_reactive_energy6(Double cum_reactive_energy6) {
		this.cum_reactive_energy6 = cum_reactive_energy6;
	}

	public Double getCum_reactive_energy7() {
		return cum_reactive_energy7;
	}

	public void setCum_reactive_energy7(Double cum_reactive_energy7) {
		this.cum_reactive_energy7 = cum_reactive_energy7;
	}

	public Double getCum_reactive_energy8() {
		return cum_reactive_energy8;
	}

	public void setCum_reactive_energy8(Double cum_reactive_energy8) {
		this.cum_reactive_energy8 = cum_reactive_energy8;
	}

	public DailyKeyLoad getMyKey() {
		return myKey;
	}

	public void setMyKey(DailyKeyLoad myKey) {
		this.myKey = myKey;
	}

	
	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	

	

	

	@Embeddable
	public static  class DailyKeyLoad implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "mtrno")
		private String meterNumber;	

		@Column(name = "rtc_date_time")
		private Timestamp readTime;	//varchar


		public Timestamp getReadTime() {
			return readTime;
		}

		public void setReadTime(Timestamp readTime) {
			
				/*Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(readTime.getTime()));
				cal.add(Calendar.MINUTE, -30);//Reduce 30 minutes to the load survey date to make compatible with XML time
				this.readTime = new Timestamp(cal.getTime().getTime());
				*/
			this.readTime = readTime;
			
		}

		public String getMeterNumber() {
			return meterNumber;
		}

		public void setMeterNumber(String meterNumber) {
			this.meterNumber = meterNumber;
		}

		public DailyKeyLoad(String meterNumber, Timestamp readTime) {
			super();
			this.meterNumber = meterNumber;
			this.readTime = readTime;
		}

		public DailyKeyLoad(){
			 
		}
	    
	}
	
	public AmrDailyLoadEntity() {

	}

	@Override
	public int compareTo(AmrDailyLoadEntity amrLoadEntity) {
		
		Timestamp t1=this.getMyKey().getReadTime();
		Timestamp t2=amrLoadEntity.getMyKey().getReadTime();
		if(t1.after(t2))
		{
			return 1;
		}
		else if(t1.before(t2))
		{
			return -1;
		}else 
		{
			return 0;
		}
		// TODO Auto-generated method stub
		
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getKvrah_high() {
		return kvrah_high;
	}

	public void setKvrah_high(Double kvrah_high) {
		this.kvrah_high = kvrah_high;
	}

	public Double getKvarh_low() {
		return kvarh_low;
	}

	public void setKvarh_low(Double kvarh_low) {
		this.kvarh_low = kvarh_low;
	}
	 

	
}
