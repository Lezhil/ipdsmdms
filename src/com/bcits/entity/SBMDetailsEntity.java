package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SBMDETAILS",schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="SBMDetailsEntity.GetAllData",query="SELECT m FROM SBMDetailsEntity m ORDER BY m.datestamp"),
	@NamedQuery(name="SBMDetailsEntity.Getsbmnumber",query="SELECT M.sbmnumber FROM SBMDetailsEntity M WHERE M.sbmnumber=:sbmnumber "),
	@NamedQuery(name="SBMDetailsEntity.GetSbmData",query="SELECT M FROM SBMDetailsEntity M")
	/*@NamedQuery(name="SBMDetailsEntity.GetSbmData",query="SELECT M FROM SBMDetailsEntity M WHERE M.sbmnumber NOT IN (SELECT MM.device FROM MeterReaderDetailsEntity MM)")*/
})
/*Author: Ved Prakash Mishra*/
public class SBMDetailsEntity 
{
	@Id
	@Column(name="SBMNUMBER", length = 15)
	private String sbmnumber;
	
	@Column(name="SBMTYPE")
	private String type;
	
	@Column(name="MANUFACTURE")
    private String manufacture;
	
	@Column(name="USER_ID")
    private String userid ;
	
	@Column(name="DATE_STAMP")
    private Date datestamp;
	
	/*@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="SBMNUMBER",referencedColumnName="SBMNO")
	private MeterReaderDetailsEntity meterReaderDetailsEntity; 

	
	public MeterReaderDetailsEntity getMeterReaderDetailsEntity() {
		return meterReaderDetailsEntity;
	}

	public void setMeterReaderDetailsEntity(
			MeterReaderDetailsEntity meterReaderDetailsEntity) {
		this.meterReaderDetailsEntity = meterReaderDetailsEntity;
	}
*/
	public String getSbmnumber() {
		return sbmnumber;
	}

	public void setSbmnumber(String sbmnumber) {
		this.sbmnumber = sbmnumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacture() {
		return manufacture;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getDatestamp() {
		return datestamp;
	}

	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}
	
	
 
}
