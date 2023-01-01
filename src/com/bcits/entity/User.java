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
@Table(name="USERS",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="User.findAll" , query="SELECT s FROM User s WHERE s.username=:username  AND s.userPassword=:userPassword "),
	@NamedQuery(name="User.findAllUser", query="SELECT u FROM User u"),
	@NamedQuery(name="User.findDuplicate" , query="SELECT u.username FROM User u WHERE u.username=:username"),
	@NamedQuery(name="User.findUserID",query="SELECT u.id FROM User u WHERE u.username=:username"),
	@NamedQuery(name="User.getById",query="SELECT u.id FROM User u WHERE u.id=:id"),
	@NamedQuery(name="User.getByEmailId",query="SELECT u FROM User u WHERE u.emailId=:emailId")
})
public class User
{

	 @Id
	/* @SequenceGenerator(name = "userSeq", sequenceName = "USERS_ID")
	 @GeneratedValue(generator = "userSeq")	*/
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="ID")
	private int id;
	
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="USERPASSWORD")
	private String userPassword;
	
	/*@Column(name="USEREMAILID")
	private String userMailId;
	
	@Column(name="USERMOBILENO")
	private String userMobileNo;*/
	
	@Column(name="office_type")
	private String officeType;
	
	//private String officeName;
	
	@Column(name="office")
	private String office;
	
	
	@Column(name="discom")
	private String discom;
	
	
	@Column(name="mobile_no")
	private String mobileNo;
	
	@Column(name="email_id")
	private String emailId;
	
	@Column(name="edit_access")
	private String editAccess;
		
	public String getEditAccess() {
		return editAccess;
	}

	public void setEditAccess(String editAccess) {
		this.editAccess = editAccess;
	}

	public String getDiscom() {
		return discom;
	}

	public void setDiscom(String discom) {
		this.discom = discom;
	}


	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name="USERTYPE")
	private String userType;
	
	/*@Column(name="USERSTATUS")
	private String userStatus;
	
	@Column(name="DEPARTMENT")
	private String userDepartment;
	
	@Column(name="USERLEVEL")
	private String userLevel;*/
	
	@Column(name="DESIGNATION")
	private String designation;
	@Column(name="name")
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User()
	{
		
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

	/*public String getUserMailId()
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
	}*/

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

//	public String getUserStatus()
//	{
//		return userStatus;
//	}
//
//	public void setUserStatus(String userStatus)
//	{
//		this.userStatus = userStatus;
//	}
//
//	public String getUserDepartment()
//	{
//		return userDepartment;
//	}
//
//	public void setUserDepartment(String userDepartment)
//	{
//		this.userDepartment = userDepartment;
//	}

	/*public String getUserLevel()
	{
		return userLevel;
	}

	public void setUserLevel(String userLevel)
	{
		this.userLevel = userLevel;
	}*/

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	
}
