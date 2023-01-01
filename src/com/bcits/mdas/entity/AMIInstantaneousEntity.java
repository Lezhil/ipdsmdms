package com.bcits.mdas.entity;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "amiinstantaneous",  schema = "METER_DATA",uniqueConstraints = { @UniqueConstraint(columnNames = { "METER_NUMBER","rdate" }) })

@NamedQueries
({
	//@NamedQuery(name = "AmrInstantaneousEntity.getD1", query = "SELECT ai FROM AmrInstantaneousEntity ai where ai.myKey.meterNumber = :meterNumber and to_char(ai.myKey.date, 'YYYY-MM-DD' ) =:fileDate ORDER BY ai.myKey.date desc "),

	@NamedQuery(name="AMIInstantaneousEntity.getDataByreadDate",query="SELECT a FROM AMIInstantaneousEntity a WHERE a.myKey.meterNumber = :meterNo and to_char(a.readTime,'YYYY-MM-DD')=:readdate")
	/*@NamedQuery(name = "AmrInstantaneousEntity.getModemsStats", query="SELECT count(ai) AS TOTAL,"
			+"COUNT(CASE WHEN TO_CHAR(CURRENT_DATE,'YYYY-MM-DD') =TO_CHAR(ai.timeStamp,'YYYY-MM-DD') THEN 1 END) AS WORKING,"
			+"COUNT(CASE WHEN age(CURRENT_TIMESTAMP, ai.timeStamp) >  '24 hours' THEN 't' END) AS NOT_WORKING"
			+" FROM AmrInstantaneousEntity ai"),*/
  //AND a.readTime=(SELECT  max(b.read_time) FROM AmrInstantaneousEntity b WHERE b.myKey.meterNumber = :meterNumber)
})
public class AMIInstantaneousEntity {
	
	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private AMIKeyInstantaneous myKey; // DATE, METER NUMBER
	
	@Column(name = "time_stamp")
	private Timestamp timeStamp;
	
	@Column(name = "modem_time")
	private Timestamp modemTime;	
	
	@Column(name = "read_time")
	private Timestamp readTime;	
	
	@Column(name = "imei")
	private String imei;	//varchar
	
	@Column(name = "kwh")
	private Double kwh;	//numeric
	
	@Column(name = "kvah")
	private Double kvah;	//numeric
	
	@Column(name = "kva")
	private Double kva;//	numeric
	
	@Column(name = "i_r_angle")
	private Double iRAngle;//	numeric
	
	@Column(name = "i_y_angle")
	private Double iYAngle;	//numeric
	
	@Column(name = "i_b_angle")
	private Double iBAngle;//	numeric
	
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
	
	@Column(name = "pf_r")
	private Double pfR;	//numeric
	
	@Column(name = "pf_y")
	private Double pfY;	//numeric
	
	@Column(name = "pf_b")
	private Double pfB;//	numeric
	
	@Column(name = "pf_threephase")
	private Double pfThreephase;	//numeric
	
	@Column(name = "frequency")
	private Double frequency;	//numeric
	
	@Column(name = "kwh_exp")
	private Double kwhExp;//	numeric
	
	@Column(name = "kwh_imp")
	private Double kwhImp;	//numeric
	
	@Column(name = "kvah_exp")
	private Double kvahExp;	//numeric
	
	@Column(name = "kvah_imp")
	private Double kvahImp;	//numeric
	
	@Column(name = "power_kw")
	private Double powerKw;	//numeric
	
	@Column(name = "kvar")
	private Double kvar;	//numeric
	
	@Column(name = "kvarh_lag")
	private Double kvarhLag;	//numeric
	
	@Column(name = "kvarh_lead")
	private Double kvarhLead;	//numeric
	
	@Column(name = "power_off_count")
	private Integer powerOffCount;	//numeric
	
	@Column(name = "power_off_duration")
	private Integer powerOffDuration;	//numeric
	
	@Column(name = "md_reset_count")
	private Integer mdResetCount;	//numeric
	
	@Column(name = "programming_count")
	private Integer programmingCount;	//numeric
	
	@Column(name = "tamper_count")
	private Integer tamperCount;	//numeric
	
	@Column(name = "md_reset_date")
	private String mdResetDate;	//varchar
	
	@Column(name = "md_kw")
	private Double mdKw;	//numeric
	
	@Column(name = "date_md_kw")
	private String dateMdKw;	//varchar
	
	@Column(name = "md_kva")
	private Double mdKva;	//numeric
	
	@Column(name = "date_md_kva")
	private String dateMdKva;//	varchar
	
	@Column(name = "trans_id")
	private String transId;	//varchar
	
	@Column(name = "signed_active_power")
	private Double signedActivePower;
	@Column(name = "signed_reactive_power")
	private Double signedReactivePower;
	@Column(name = "billing_count")
	private Integer billingCount;
	@Column(name = "billing_date")
	private Timestamp billingDate;
	@Column(name = "reactive_imp_active_exp")
	private Double reactiveImpActiveExp;
	@Column(name = "reactive_exp_active_exp")
	private Double reactiveExpActiveExp;
	@Column(name="iflag")
	private String flag;
	
	@Column(name = "power_voltage")
	private String powerVoltage;	//varchar
	

	@Column(name = "phase_current")
	private String phaseCurrent ;	//varchar
	

	@Column(name = "neutral_current")
	private String neutralCurrent ;	//varchar
	

	@Column(name = "total_power_on_duration")
	private Integer totalPowerOnDuration ;	//varchar
	
	@Column(name = "phase_sequence")
	private String phase_sequence ;
	
	@Column(name="READ_FROM")
	private String read_from;
	
	@Column(name = "v_ry_angle")
	private Integer v_ry_angle;
	
	@Column(name = "v_rb_angle")
	private Integer v_rb_angle;
	
	@Column(name = "v_yb_angle")
	private Integer v_yb_angle;
	
	

	public String getPhase_sequence() {
		return phase_sequence;
	}

	public void setPhase_sequence(String phase_sequence) {
		this.phase_sequence = phase_sequence;
	}

	public String getRead_from() {
		return read_from;
	}

	public void setRead_from(String read_from) {
		this.read_from = read_from;
	}

	public String getPowerVoltage() {
		return powerVoltage;
	}

	public void setPowerVoltage(String powerVoltage) {
		this.powerVoltage = powerVoltage;
	}

	public String getPhaseCurrent() {
		return phaseCurrent;
	}

	public void setPhaseCurrent(String phaseCurrent) {
		this.phaseCurrent = phaseCurrent;
	}

	public String getNeutralCurrent() {
		return neutralCurrent;
	}

	public void setNeutralCurrent(String neutralCurrent) {
		this.neutralCurrent = neutralCurrent;
	}

	public Integer getTotalPowerOnDuration() {
		return totalPowerOnDuration;
	}

	public void setTotalPowerOnDuration(Integer totalPowerOnDuration) {
		this.totalPowerOnDuration = totalPowerOnDuration;
	}

	public Double getSignedActivePower(){
		return signedActivePower;
	}
	
	public void setSignedActivePower(Double signedActivePower){
		this.signedActivePower=signedActivePower;
	}
	
	public AMIKeyInstantaneous getMyKey() {
		return myKey;
	}


	public void setMyKey(AMIKeyInstantaneous myKey) {
		this.myKey = myKey;
	}


	public AMIInstantaneousEntity() {
		 
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

	public Timestamp getReadTime() {
		return readTime;
	}

	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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

	public Double getKva() {
		return kva;
	}

	public void setKva(Double kva) {
		this.kva = kva;
	}

	public Double getiRAngle() {
		return iRAngle;
	}

	public void setiRAngle(Double iRAngle) {
		this.iRAngle = iRAngle;
	}

	public Double getiYAngle() {
		return iYAngle;
	}

	public void setiYAngle(Double iYAngle) {
		this.iYAngle = iYAngle;
	}

	public Double getiBAngle() {
		return iBAngle;
	}

	public void setiBAngle(Double iBAngle) {
		this.iBAngle = iBAngle;
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

	public Double getPfR() {
		return pfR;
	}

	public void setPfR(Double pfR) {
		this.pfR = pfR;
	}

	public Double getPfY() {
		return pfY;
	}

	public void setPfY(Double pfY) {
		this.pfY = pfY;
	}

	public Double getPfB() {
		return pfB;
	}

	public void setPfB(Double pfB) {
		this.pfB = pfB;
	}

	public Double getPfThreephase() {
		return pfThreephase;
	}

	public void setPfThreephase(Double pfThreephase) {
		this.pfThreephase = pfThreephase;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
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

	public Double getKvahExp() {
		return kvahExp;
	}

	public void setKvahExp(Double kvahExp) {
		this.kvahExp = kvahExp;
	}

	public Double getKvahImp() {
		return kvahImp;
	}

	public void setKvahImp(Double kvahImp) {
		this.kvahImp = kvahImp;
	}

	public Double getPowerKw() {
		return powerKw;
	}

	public void setPowerKw(Double powerKw) {
		this.powerKw = powerKw;
	}

	public Double getKvar() {
		return kvar;
	}

	public void setKvar(Double kvar) {
		this.kvar = kvar;
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

	public Integer getPowerOffCount() {
		return powerOffCount;
	}

	public void setPowerOffCount(Integer powerOffCount) {
		this.powerOffCount = powerOffCount;
	}

	public Integer getPowerOffDuration() {
		return powerOffDuration;
	}

	public void setPowerOffDuration(Integer powerOffDuration) {
		this.powerOffDuration = powerOffDuration;
	}

	public Integer getMdResetCount() {
		return mdResetCount;
	}

	public void setMdResetCount(Integer mdResetCount) {
		this.mdResetCount = mdResetCount;
	}

	public Integer getProgrammingCount() {
		return programmingCount;
	}

	public void setProgrammingCount(Integer programmingCount) {
		this.programmingCount = programmingCount;
	}

	public Integer getTamperCount() {
		return tamperCount;
	}

	public void setTamperCount(Integer tamperCount) {
		this.tamperCount = tamperCount;
	}

	public String getMdResetDate() {
		return mdResetDate;
	}

	public void setMdResetDate(String mdResetDate) {
		this.mdResetDate = mdResetDate;
	}

	public Double getMdKw() {
		return mdKw;
	}

	public void setMdKw(Double mdKw) {
		this.mdKw = mdKw;
	}

	public String getDateMdKw() {
		return dateMdKw;
	}

	public void setDateMdKw(String dateMdKw) {
		this.dateMdKw = dateMdKw;
	}

	public Double getMdKva() {
		return mdKva;
	}

	public void setMdKva(Double mdKva) {
		this.mdKva = mdKva;
	}

	public String getDateMdKva() {
		return dateMdKva;
	}

	public void setDateMdKva(String dateMdKva) {
		this.dateMdKva = dateMdKva;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}
	
	public Double getSignedReactivePower() {
		return signedReactivePower;
	}

	public void setSignedReactivePower(Double signedReactivePower) {
		this.signedReactivePower = signedReactivePower;
	}

	public Integer getBillingCount() {
		return billingCount;
	}

	public void setBillingCount(Integer billingCount) {
		this.billingCount = billingCount;
	}

	public Timestamp getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Timestamp billingDate) {
		this.billingDate = billingDate;
	}

	public Double getReactiveImpActiveExp() {
		return reactiveImpActiveExp;
	}

	public void setReactiveImpActiveExp(Double reactiveImpActiveExp) {
		this.reactiveImpActiveExp = reactiveImpActiveExp;
	}

	public Double getReactiveExpActiveExp() {
		return reactiveExpActiveExp;
	}

	public void setReactiveExpActiveExp(Double reactiveExpActiveExp) {
		this.reactiveExpActiveExp = reactiveExpActiveExp;
	}

	@Embeddable
	public static  class AMIKeyInstantaneous implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "meter_number")
		private String meterNumber;	

		@Column(name = "rdate")
		private Timestamp rdate;	//varchar


		
		public AMIKeyInstantaneous(String meterNumber, Timestamp rdate) {
			super();
			this.meterNumber = meterNumber;
			this.rdate = rdate;
		}



		public String getMeterNumber() {
			return meterNumber;
		}



		public void setMeterNumber(String meterNumber) {
			this.meterNumber = meterNumber;
		}



		public Timestamp getRdate() {
			return rdate;
		}



		public void setRdate(Timestamp rdate) {
			this.rdate = rdate;
		}



		public AMIKeyInstantaneous(){
			 
		}
	    
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getV_ry_angle() {
		return v_ry_angle;
	}

	public void setV_ry_angle(Integer v_ry_angle) {
		this.v_ry_angle = v_ry_angle;
	}

	public Integer getV_rb_angle() {
		return v_rb_angle;
	}

	public void setV_rb_angle(Integer v_rb_angle) {
		this.v_rb_angle = v_rb_angle;
	}

	public Integer getV_yb_angle() {
		return v_yb_angle;
	}

	public void setV_yb_angle(Integer v_yb_angle) {
		this.v_yb_angle = v_yb_angle;
	}
	
	
}
