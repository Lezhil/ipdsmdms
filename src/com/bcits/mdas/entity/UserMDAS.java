package com.bcits.mdas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="USERS",schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="UserMDAS.findAll" , query="SELECT s FROM User s "),
	/*@NamedQuery(name="User.findAll" , query="SELECT s FROM User s WHERE UPPER(s.userMailId) LIKE UPPER(:userMailId) AND UPPER(s.userPassword) LIKE UPPER(:userPassword)"),
	@NamedQuery(name="User.screenUnlock",query="SELECT u FROM User u WHERE u.username= :username and u.userPassword=:password"),
	@NamedQuery(name = "User.findAllUser", query = "SELECT a FROM User a"), 
	@NamedQuery(name="User.findUser" , query="SELECT s FROM User s WHERE s.username LIKE :userName AND s.userPassword LIKE :passWord"),
	@NamedQuery(name="User.checkEmailExist" , query="SELECT COUNT(*) FROM User s WHERE s.userMailId LIKE :emailId"),
	@NamedQuery(name="User.checkEmailExistWhileEdit" , query="SELECT COUNT(*) FROM User s WHERE s.userMailId LIKE :emailId AND s.id<>:id"),
	
	@NamedQuery(name="User.findAllSdo" , query="SELECT s FROM User s WHERE s.userMailId LIKE :userMailId AND UPPER(s.userPassword) LIKE UPPER(:userPassword)"),
	*/
})
public class UserMDAS
{

	@Id
	 /*@SequenceGenerator(name = "userSeq", sequenceName = "users_id_seq" )
	 @GeneratedValue(strategy=GenerationType.AUTO,generator = "userSeq")	*/
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="USERPASSWORD")
	private String userPassword;
	
	@Column(name="USEREMAILID")
	private String userMailId;
	
	@Column(name="USERMOBILENO")
	private String userMobileNo;
	
	@Column(name="USERTYPE")
	private String userType;
	
	@Column(name="datestamp")
	private Date userCreatedDate;
	
	@Column(name="USERSTATUS")
	private String userStatus;
	
	@Column(name="USERLEVEL")
	private String userLevel;
	
	@Column(name="address")
	private String address;
	
	@Column(name="zone")
	private String zone;
	@Column(name="designation")
	private String designation;
	


	@Column(name="profile_photo")
	private byte[] image;
	
	public UserMDAS()
	{
		
	}
   
	public String getZone() {
		return zone;
	}


	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public Date getUserCreatedDate() {
		return userCreatedDate;
	}


	public void setUserCreatedDate(Date userCreatedDate) {
		this.userCreatedDate = userCreatedDate;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUserPassword()
	{
		return userPassword;
	}

	public void setUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
	}

	public String getUserMailId()
	{
		return userMailId;
	}

	public void setUserMailId(String userMailId)
	{
		this.userMailId = userMailId;
	}

	public String getUserMobileNo()
	{
		return userMobileNo;
	}

	public void setUserMobileNo(String userMobileNo)
	{
		this.userMobileNo = userMobileNo;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public String getUserStatus()
	{
		return userStatus;
	}

	public void setUserStatus(String userStatus)
	{
		this.userStatus = userStatus;
	}
	

	public String getUserLevel()
	{
		return userLevel;
	}

	public void setUserLevel(String userLevel)
	{
		this.userLevel = userLevel;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
}
