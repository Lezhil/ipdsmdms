package com.bcits.mdas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@IdClass(SurveyOutputMobileID.class)
@Table(name = "survey_output", schema = "meter_data")
@NamedQueries({
	@NamedQuery(name="SurveyOutputMobileEntity.getMeterImageOnly", query="select s from SurveyOutputMobileEntity s where id=:id")
})
public class SurveyOutputMobileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "sdocode")
	private String sdocode;
	
	@Column(name = "kno")
	private String kno;
	
	@Id
	@Column(name = "accno")
	private String accno;
	
	@Column(name = "consumername")
	private String consumername;
	
	@Column(name = "address")
	private String 	address;

	@Column(name = "mobileno")
	private String 	mobileno;

	@Column(name = "meterno")
	private String 	meterno;

	@Column(name = "connectiontype")
	private String 	connectiontype;

	@Column(name = "poleno")
	private String 	poleno;

	@Column(name = "dtcno")
	private String 	dtcno;

	@Column(name = "latitude")
	private String 	latitude;

	@Column(name = "longitude")
	private String 	longitude;

	@Column(name = "premise")
	private String 	premise;

	@Column(name = "sticker_no")
	private String 	sticker_no;

	@Column(name = "land_mark")
	private String 	land_mark;

	@Column(name = "meter_image")
	private byte[] 	meter_image;

	@Column(name = "premise_image")
	private byte[] 	premise_image;

	@Column(name = "mrcode")
	private String 	mrcode;

	@Column(name = "appversion")
	private String 	appversion;

	@Column(name = "imei")
	private String 	imei;

	@Column(name = "survey_timings")
	private String 	survey_timings;

	@Column(name = "observation")
	private String 	observation;

	@Column(name = "old_metermake")
	private String 	old_metermake;

	@Column(name = "old_mf")
	private String 	old_mf;

	@Column(name = "old_ctrn")
	private String 	old_ctrn;

	@Column(name = "old_ctrd")
	private String 	old_ctrd;

	@Column(name = "currdngkwh")
	private String 	currdngkwh;

	@Column(name = "finalreading")
	private String 	finalreading;

	@Column(name = "newmeterno")
	private String 	newmeterno;

	@Column(name = "newmetermake")
	private String 	newmetermake;

	@Column(name = "newmetertype")
	private String 	newmetertype;

	@Column(name = "newmf")
	private String 	newmf;

	@Column(name = "newctratio")
	private String 	newctratio;

	@Column(name = "newinitialreading")
	private String 	newinitialreading;

	@Column(name = "newmeterimage")
	private byte[] 	newmeterimage;

	@Column(name = "mcrreportimage")
	private byte[] 	mcrreportimage;

	@Column(name = "newmeterkvah")
	private String 	newmeterkvah;

	@Column(name = "oldmeterkvah")
	private String 	oldmeterkvah;

	@Column(name = "newmeterkva")
	private String 	newmeterkva;

	@Column(name = "oldmeterkva")
	private String 	oldmeterkva;

	@Column(name = "oldmeterno_correction")
	private String 	oldmeterno_correction;

	@Column(name = "tenderno")
	private String 	tenderno;
	
	public SurveyOutputMobileEntity() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSdocode() {
		return sdocode;
	}

	public void setSdocode(String sdocode) {
		this.sdocode = sdocode;
	}

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getConsumername() {
		return consumername;
	}

	public void setConsumername(String consumername) {
		this.consumername = consumername;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getMeterno() {
		return meterno;
	}

	public void setMeterno(String meterno) {
		this.meterno = meterno;
	}

	public String getConnectiontype() {
		return connectiontype;
	}

	public void setConnectiontype(String connectiontype) {
		this.connectiontype = connectiontype;
	}

	public String getPoleno() {
		return poleno;
	}

	public void setPoleno(String poleno) {
		this.poleno = poleno;
	}

	public String getDtcno() {
		return dtcno;
	}

	public void setDtcno(String dtcno) {
		this.dtcno = dtcno;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPremise() {
		return premise;
	}

	public void setPremise(String premise) {
		this.premise = premise;
	}

	public String getSticker_no() {
		return sticker_no;
	}

	public void setSticker_no(String sticker_no) {
		this.sticker_no = sticker_no;
	}

	public String getLand_mark() {
		return land_mark;
	}

	public void setLand_mark(String land_mark) {
		this.land_mark = land_mark;
	}

	public byte[] getMeter_image() {
		return meter_image;
	}

	public void setMeter_image(byte[] meter_image) {
		this.meter_image = meter_image;
	}

	public byte[] getPremise_image() {
		return premise_image;
	}

	public void setPremise_image(byte[] premise_image) {
		this.premise_image = premise_image;
	}

	public String getMrcode() {
		return mrcode;
	}

	public void setMrcode(String mrcode) {
		this.mrcode = mrcode;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSurvey_timings() {
		return survey_timings;
	}

	public void setSurvey_timings(String survey_timings) {
		this.survey_timings = survey_timings;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getOld_metermake() {
		return old_metermake;
	}

	public void setOld_metermake(String old_metermake) {
		this.old_metermake = old_metermake;
	}

	public String getOld_mf() {
		return old_mf;
	}

	public void setOld_mf(String old_mf) {
		this.old_mf = old_mf;
	}

	public String getOld_ctrn() {
		return old_ctrn;
	}

	public void setOld_ctrn(String old_ctrn) {
		this.old_ctrn = old_ctrn;
	}

	public String getOld_ctrd() {
		return old_ctrd;
	}

	public void setOld_ctrd(String old_ctrd) {
		this.old_ctrd = old_ctrd;
	}

	public String getCurrdngkwh() {
		return currdngkwh;
	}

	public void setCurrdngkwh(String currdngkwh) {
		this.currdngkwh = currdngkwh;
	}

	public String getFinalreading() {
		return finalreading;
	}

	public void setFinalreading(String finalreading) {
		this.finalreading = finalreading;
	}

	public String getNewmeterno() {
		return newmeterno;
	}

	public void setNewmeterno(String newmeterno) {
		this.newmeterno = newmeterno;
	}

	public String getNewmetermake() {
		return newmetermake;
	}

	public void setNewmetermake(String newmetermake) {
		this.newmetermake = newmetermake;
	}

	public String getNewmetertype() {
		return newmetertype;
	}

	public void setNewmetertype(String newmetertype) {
		this.newmetertype = newmetertype;
	}

	public String getNewmf() {
		return newmf;
	}

	public void setNewmf(String newmf) {
		this.newmf = newmf;
	}

	public String getNewctratio() {
		return newctratio;
	}

	public void setNewctratio(String newctratio) {
		this.newctratio = newctratio;
	}

	public String getNewinitialreading() {
		return newinitialreading;
	}

	public void setNewinitialreading(String newinitialreading) {
		this.newinitialreading = newinitialreading;
	}

	public byte[] getNewmeterimage() {
		return newmeterimage;
	}

	public void setNewmeterimage(byte[] newmeterimage) {
		this.newmeterimage = newmeterimage;
	}

	public byte[] getMcrreportimage() {
		return mcrreportimage;
	}

	public void setMcrreportimage(byte[] mcrreportimage) {
		this.mcrreportimage = mcrreportimage;
	}

	public String getNewmeterkvah() {
		return newmeterkvah;
	}

	public void setNewmeterkvah(String newmeterkvah) {
		this.newmeterkvah = newmeterkvah;
	}

	public String getNewmeterkva() {
		return newmeterkva;
	}

	public void setNewmeterkva(String newmeterkva) {
		this.newmeterkva = newmeterkva;
	}

	public String getOldmeterkva() {
		return oldmeterkva;
	}

	public void setOldmeterkva(String oldmeterkva) {
		this.oldmeterkva = oldmeterkva;
	}

	public String getOldmeterno_correction() {
		return oldmeterno_correction;
	}

	public void setOldmeterno_correction(String oldmeterno_correction) {
		this.oldmeterno_correction = oldmeterno_correction;
	}
	

	public String getOldmeterkvah() {
		return oldmeterkvah;
	}

	public void setOldmeterkvah(String oldmeterkvah) {
		this.oldmeterkvah = oldmeterkvah;
	}

	public String getTenderno() {
		return tenderno;
	}

	public void setTenderno(String tenderno) {
		this.tenderno = tenderno;
	}

	public SurveyOutputMobileEntity( String sdocode, String kno, String accno, String consumername,
			String address, String mobileno, String meterno, String connectiontype, String poleno, String dtcno,
			String latitude, String longitude, String premise, String sticker_no, String land_mark, byte[] meter_image,
			byte[] premise_image, String mrcode, String appversion, String imei, String survey_timings,
			String observation, String old_metermake, String old_mf, String old_ctrn, String old_ctrd,
			String currdngkwh, String finalreading, String newmeterno, String newmetermake, String newmetertype,
			String newmf, String newctratio, String newinitialreading, byte[] newmeterimage, byte[] mcrreportimage,
			String newmeterkvah, String oldmeterkvah ,String newmeterkva, String oldmeterkva, String oldmeterno_correction,String tenderNo) {
		super();
		this.sdocode = sdocode;
		this.kno = kno;
		this.accno = accno;
		this.consumername = consumername;
		this.address = address;
		this.mobileno = mobileno;
		this.meterno = meterno;
		this.connectiontype = connectiontype;
		this.poleno = poleno;
		this.dtcno = dtcno;
		this.latitude = latitude;
		this.longitude = longitude;
		this.premise = premise;
		this.sticker_no = sticker_no;
		this.land_mark = land_mark;
		this.meter_image = meter_image;
		this.premise_image = premise_image;
		this.mrcode = mrcode;
		this.appversion = appversion;
		this.imei = imei;
		this.survey_timings = survey_timings;
		this.observation = observation;
		this.old_metermake = old_metermake;
		this.old_mf = old_mf;
		this.old_ctrn = old_ctrn;
		this.old_ctrd = old_ctrd;
		this.currdngkwh = currdngkwh;
		this.finalreading = finalreading;
		this.newmeterno = newmeterno;
		this.newmetermake = newmetermake;
		this.newmetertype = newmetertype;
		this.newmf = newmf;
		this.newctratio = newctratio;
		this.newinitialreading = newinitialreading;
		this.newmeterimage = newmeterimage;
		this.mcrreportimage = mcrreportimage;
		this.newmeterkvah = newmeterkvah;
		this.oldmeterkvah = oldmeterkvah;
		this.newmeterkva = newmeterkva;
		this.oldmeterkva = oldmeterkva;
		this.oldmeterno_correction = oldmeterno_correction;
		this.tenderno = tenderNo;
	}




}
class SurveyOutputMobileID implements Serializable
{
	private Integer id;
	private String accno;


}
