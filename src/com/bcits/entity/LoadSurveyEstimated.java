package com.bcits.entity;

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
@Table(name = "LOAD_SURVEY_ESTIMATED",  schema = "METER_DATA",uniqueConstraints = { @UniqueConstraint(columnNames = { "READ_TIME","METER_NUMBER" }) })

@NamedQueries({
	
	@NamedQuery(name="LoadSurveyEstimated.getdataForCheckCurrent", query="select l from LoadSurveyEstimated l where estimation_status is null AND (l.iR is NULL OR l.iY is NULL OR l.iB is NULL OR l.iR<0 OR l.iY<0 OR l.iB<0) AND l.kwh is not null"),
	@NamedQuery(name="LoadSurveyEstimated.getdataForCheckVoltage", query="select l from LoadSurveyEstimated l where estimation_status is null AND (l.vR is NULL OR l.vY is NULL OR l.vB is NULL OR l.vR<0 OR l.vY<0 OR l.vB<0 ) AND l.kwh is not null")
})

public class LoadSurveyEstimated {

	

	
	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private KeyLoad myKey; // READ_TIME, METER NUMBER
	   
	@Column(name = "id", insertable=false,updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "time_stamp")
	private Timestamp timeStamp;
	
	@Column(name = "modem_time")
	private Timestamp modemTime;	
	
	@Column(name = "imei")
	private String imei;	//varchar
	
	@Column(name = "i_r")
	private Double iR;	//numeric
	
	@Column(name = "i_y")
	private Double iY;//	numeric
	
	@Column(name = "i_b")
	private Double iB;	//numeric
	
	@Column(name = "v_r")
	private Double vR;//	numeric
	
	@Column(name = "v_y")
	private Double vY;	//numeric
	
	@Column(name = "v_b")
	private Double vB;	//numeric
	
	@Column(name = "kwh")
	private Double kwh;	//numeric
	
	@Column(name = "kvah")
	private Double kvah;	//numeric
	
	@Column(name = "kwh_exp")
	private Double kwhExp;//	numeric
	
	@Column(name = "kwh_imp")
	private Double kwhImp;	//numeric
	
	@Column(name = "structure_size")
	private Integer structureSize;//	numeric
 
	@Column(name = "trans_id")
	private String transId;	//varchar
	
	@Column(name = "frequency")
	private Double frequency;	//numeric
	
	@Column(name = "kvarh_lag")
	private Double kvarhLag;	//numeric
	
	@Column(name = "kvarh_lead")
	private Double kvarhLead;//	numeric
	
	@Column(name = "kvarh_q1")
	private Double kvarhQ1;	//numeric
	
	@Column(name = "kvarh_q2")
	private Double kvarhQ2;	//numeric
	
	@Column(name = "kvarh_q3")
	private Double kvarhQ3;	//numeric
	
	@Column(name = "kvarh_q4")
	private Double kvarhQ4;//	numeric
	
	@Column(name = "netkwh")
	private Double netKwh;//	numeric
	
	@Column(name = "load_id")
	private Long load_id;

	@Column(name="estimation_status")
	private String estimation_status;
	
	
	
	public String getEstimation_status() {
		return estimation_status;
	}

	public void setEstimation_status(String estimation_status) {
		this.estimation_status = estimation_status;
	}

	public Long getLoad_id() {
		return load_id;
	}

	public void setLoad_id(Long load_id) {
		this.load_id = load_id;
	}

	public KeyLoad getMyKey() {
		return myKey;
	}

	public void setMyKey(KeyLoad myKey) {
		this.myKey = myKey;
	}

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

	public Timestamp getModemTime() {
		return modemTime;
	}

	public void setModemTime(Timestamp modemTime) {
		this.modemTime = modemTime;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Double getiR() {
		return iR;
	}

	public void setiR(Double iR) {
		this.iR = iR;
	}

	public Double getiY() {
		return iY;
	}

	public void setiY(Double iY) {
		this.iY = iY;
	}

	public Double getiB() {
		return iB;
	}

	public void setiB(Double iB) {
		this.iB = iB;
	}

	public Double getvR() {
		return vR;
	}

	public void setvR(Double vR) {
		this.vR = vR;
	}

	public Double getvY() {
		return vY;
	}

	public void setvY(Double vY) {
		this.vY = vY;
	}

	public Double getvB() {
		return vB;
	}

	public void setvB(Double vB) {
		this.vB = vB;
	}

	public Double getKwh() {
		return kwh;
	}

	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}

	public Double getKvah() {
		return kvah;
	}

	public void setKvah(Double kvah) {
		this.kvah = kvah;
	}

	public Double getKwhExp() {
		return kwhExp;
	}

	public void setKwhExp(Double kwhExp) {
		this.kwhExp = kwhExp;
	}

	public Double getKwhImp() {
		return kwhImp;
	}

	public void setKwhImp(Double kwhImp) {
		this.kwhImp = kwhImp;
	}

	public Integer getStructureSize() {
		return structureSize;
	}

	public void setStructureSize(Integer structureSize) {
		this.structureSize = structureSize;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}

	public Double getKvarhLag() {
		return kvarhLag;
	}

	public void setKvarhLag(Double kvarhLag) {
		this.kvarhLag = kvarhLag;
	}

	public Double getKvarhLead() {
		return kvarhLead;
	}

	public void setKvarhLead(Double kvarhLead) {
		this.kvarhLead = kvarhLead;
	}

	public Double getKvarhQ1() {
		return kvarhQ1;
	}

	public void setKvarhQ1(Double kvarhQ1) {
		this.kvarhQ1 = kvarhQ1;
	}

	public Double getKvarhQ2() {
		return kvarhQ2;
	}

	public void setKvarhQ2(Double kvarhQ2) {
		this.kvarhQ2 = kvarhQ2;
	}

	public Double getKvarhQ3() {
		return kvarhQ3;
	}

	public void setKvarhQ3(Double kvarhQ3) {
		this.kvarhQ3 = kvarhQ3;
	}

	public Double getKvarhQ4() {
		return kvarhQ4;
	}

	public void setKvarhQ4(Double kvarhQ4) {
		this.kvarhQ4 = kvarhQ4;
	}

	public Double getNetKwh() {
		return netKwh;
	}

	public void setNetKwh(Double netKwh) {
		this.netKwh = netKwh;
	}
	

	@Embeddable
	public static  class KeyLoad implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "meter_number")
		private String meterNumber;	

		@Column(name = "read_time")
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

		public KeyLoad(String meterNumber, Timestamp readTime) {
			super();
			this.meterNumber = meterNumber;
			this.readTime = readTime;
		}

		public KeyLoad(){
			 
		}
	    
	}
	
	public LoadSurveyEstimated() {

	}

	

	

	
}
