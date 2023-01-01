/**
 * 
 */
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

/**
 * @author Tarik
 *
 */


@Entity
@Table(name = "LOAD_SURVEY_RAW",  schema = "METER_DATA",uniqueConstraints = { @UniqueConstraint(columnNames = { "READ_TIME","METER_NUMBER" }) })
public class AmrLoadRawEntity implements Comparable<AmrLoadRawEntity> {

	
	@EmbeddedId  // FOR MAKING UNIQUE KEY
	private KeyRawLoad myKey; // READ_TIME, METER NUMBER
	   
	/*@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;	*/
	
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
	
	@Column(name = "block_energy_kwh_exp")
	private Double blockEnergyKwhExp;//	numeric
	
	@Column(name = "block_energy_kvah_exp")
	private Double blockEnergyKvahExp;//	numeric
		
	@Column(name = "kvah_imp")
	private Double kvahImp;//	numeric
	
	@Column(name = "kvah_exp")
	private Double kvahExp;//	numeric
	
	
	@Column(name = "flag")
	private String flag;//	String
	
	//new
	@Column(name = "average_voltage")
	private String averageVoltage;//	String
	
	@Column(name = "average_current")
	private String averageCurrent;//	String
	
	@Column(name = "neutral_current")
	private String neutralCurrent;//	String
	@Column(name="sample_or_server_time")
	private Timestamp sampleTime;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name = "kw")
	private Double kw;
	@Column(name = "kva")
	private Double kva;
	@Column(name = "kvar_lag")
	private Double kvarLag;
	@Column(name = "kvar_lead")
	private Double kvarLead;
	@Column(name = "power_factor")
	private Double powerFactor;
	@Column(name = "kw_exp")
	private Double kwExp;
	
	@Column(name = "estflag")
	private Integer estimationflag=0;
	
	public Integer getEstimationflag() {
		return estimationflag;
	}

	public void setEstimationflag(Integer estimationflag) {
		this.estimationflag = estimationflag;
	}
	@Column(name="read_from")
	private String read_from;
	
	
	public String getRead_from() {
		return read_from;
	}

	public void setRead_from(String read_from) {
		this.read_from = read_from;
	}

	public String getAverageVoltage() {
		return averageVoltage;
	}

	public void setAverageVoltage(String averageVoltage) {
		this.averageVoltage = averageVoltage;
	}

	public String getAverageCurrent() {
		return averageCurrent;
	}

	public void setAverageCurrent(String averageCurrent) {
		this.averageCurrent = averageCurrent;
	}

	public String getNeutralCurrent() {
		return neutralCurrent;
	}

	public void setNeutralCurrent(String neutralCurrent) {
		this.neutralCurrent = neutralCurrent;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Double getKvahImp() {
		return kvahImp;
	}

	public void setKvahImp(Double kvahImp) {
		this.kvahImp = kvahImp;
	}

	public Double getKvahExp() {
		return kvahExp;
	}

	public void setKvahExp(Double kvahExp) {
		this.kvahExp = kvahExp;
	}

	public Double getBlockEnergyKwhExp() {
		return blockEnergyKwhExp;
	}

	public void setBlockEnergyKwhExp(Double blockEnergyKwhExp) {
		this.blockEnergyKwhExp = blockEnergyKwhExp;
	}

	public Double getBlockEnergyKvahExp() {
		return blockEnergyKvahExp;
	}

	public void setBlockEnergyKvahExp(Double blockEnergyKvahExp) {
		this.blockEnergyKvahExp = blockEnergyKvahExp;
	}

	public KeyRawLoad getMyKey() {
		return myKey;
	}

	public void setMyKey(KeyRawLoad myKey) {
		this.myKey = myKey;
	}

/*	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}*/

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
	

	public Timestamp getSampleTime() {
		return sampleTime;
	}

	public void setSampleTime(Timestamp sampleTime) {
		this.sampleTime = sampleTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	


	public Double getKw() {
		return kw;
	}

	public void setKw(Double kw) {
		this.kw = kw;
	}

	public Double getKva() {
		return kva;
	}

	public void setKva(Double kva) {
		this.kva = kva;
	}

	public Double getKvarLag() {
		return kvarLag;
	}

	public void setKvarLag(Double kvarLag) {
		this.kvarLag = kvarLag;
	}

	public Double getKvarLead() {
		return kvarLead;
	}

	public void setKvarLead(Double kvarLead) {
		this.kvarLead = kvarLead;
	}

	public Double getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(Double powerFactor) {
		this.powerFactor = powerFactor;
	}

	public Double getKwExp() {
		return kwExp;
	}

	public void setKwExp(Double kwExp) {
		this.kwExp = kwExp;
	}



	@Embeddable
	public static  class KeyRawLoad implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "meter_number")
		private String meterNumber;	

		@Column(name = "read_time")
		private Timestamp readTime;	//varchar


		public Timestamp getReadTime() {
			return readTime;
		}

		public void setReadTime(Timestamp readTime) {
			this.readTime = readTime;
			
		}

		public String getMeterNumber() {
			return meterNumber;
		}

		public void setMeterNumber(String meterNumber) {
			this.meterNumber = meterNumber;
		}

		public KeyRawLoad(String meterNumber, Timestamp readTime) {
			super();
			this.meterNumber = meterNumber;
			this.readTime = readTime;
		}

		public KeyRawLoad(){
			 
		}
	    
	}
	
	public AmrLoadRawEntity() {

	}

	@Override
	public int compareTo(AmrLoadRawEntity amrLoadRawEntity) {
		
		Timestamp t1=this.getMyKey().getReadTime();
		Timestamp t2=amrLoadRawEntity.getMyKey().getReadTime();
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
	  @Override
	    public String toString() {
	       return "IMEI is: "+this.imei+" TimeStamp is: "+this.getTimeStamp()+" read Time: "+this.myKey.readTime.toString()+"  meterNO: "+this.myKey.meterNumber;
	    }

	


}
