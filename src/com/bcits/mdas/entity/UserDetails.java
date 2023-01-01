package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="userdetails",schema="vcloudengine")
@NamedQueries
({
	@NamedQuery(name="UserDetails.findAll" , query="SELECT u FROM UserDetails u WHERE (u.userName)=:userName  AND (u.passWord)=:password ")
})
public class UserDetails 
{

	@Id
	@Column(name="username")
	private String userName;
	
	@Column(name="password")
	private String passWord;
	
	@Column(name="fullname")
	private String fullName;
	
	@Column(name="emailid")
	private String emailId;
	
	@Column(name="phoneno")
	private String phoneNo;
	
	@Column(name="consumernos")
	private String consumerNos;
	
	@Column(name="registrationdate")
	private Timestamp registrationDate;

	public UserDetails() 
	{
		
	}

	public UserDetails(String userName, String passWord, String fullName,
			String emailId, String phoneNo, String consumerNos,
			Timestamp registrationDate) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.fullName = fullName;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.consumerNos = consumerNos;
		this.registrationDate = registrationDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getConsumerNos() {
		return consumerNos;
	}

	public void setConsumerNos(String consumerNos) {
		this.consumerNos = consumerNos;
	}

	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public String toString() {
		return "UserDetails [userName=" + userName + ", passWord=" + passWord
				+ ", fullName=" + fullName + ", emailId=" + emailId
				+ ", phoneNo=" + phoneNo + ", consumerNos=" + consumerNos
				+ ", registrationDate=" + registrationDate + "]";
	}

	
	
}
