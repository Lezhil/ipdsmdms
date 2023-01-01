package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SMS_ALERT_CONFIG",schema="meter_data")
@NamedQueries({
	/*@NamedQuery(name="User.findAll" , query="SELECT s FROM User s WHERE s.username=:username  AND s.userPassword=:userPassword "),
	@NamedQuery(name="User.findUserID",query="SELECT u.id FROM User u WHERE u.username=:username")*/
	@NamedQuery(name="SmsToRecipient.findAllUsers", query="SELECT u FROM SmsToRecipient u"),
	@NamedQuery(name="SmsToRecipient.findsubdiv" , query="SELECT u FROM SmsToRecipient u WHERE u.subdivision=:subdivision"),
	@NamedQuery(name="SmsToRecipient.finddiv" , query="SELECT u FROM SmsToRecipient u WHERE u.division=:division")
})
public class SmsToRecipient
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="CIRCLE")
	private String circle;
	
	@Column(name="DIVISION")
	private String division;
	
	@Column(name="SUBDIVISION")
	private String subdivision;
	
	@Column(name="R_NAME")
	private String r_name;
	
	@Column(name="R_DESIGNATION")
	private String r_designation;
	
	@Column(name="R_MAIL")
	private String r_mail;
	
	@Column(name="R_MOBILE_NUM")
	private String r_mobile_num;
	
	@Column(name="MESSAGE_TYPE")
	private String message_type;
	
	@Column(name="METER_TYPE")
	private String meter_type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getR_designation() {
		return r_designation;
	}

	public void setR_designation(String r_designation) {
		this.r_designation = r_designation;
	}

	public String getR_mail() {
		return r_mail;
	}

	public void setR_mail(String r_mail) {
		this.r_mail = r_mail;
	}

	public String getR_mobile_num() {
		return r_mobile_num;
	}

	public void setR_mobile_num(String r_mobile_num) {
		this.r_mobile_num = r_mobile_num;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getMeter_type() {
		return meter_type;
	}

	public void setMeter_type(String meter_type) {
		this.meter_type = meter_type;
	}

	


}
