
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
@Table(name="JVVNL_USERS",schema="mdm_test")
@NamedQueries({
	@NamedQuery(name="JvvnlUsersEntity.findAllJvvnlUsers" , query="SELECT s FROM JvvnlUsersEntity s WHERE s.user_login_name=:username  AND s.password=:userPassword "),
	@NamedQuery(name="JvvnlUsersEntity.findAllOfficeTypes" , query="SELECT DISTINCT office_type  from JvvnlUsersEntity where office_type not in('null','ht','HT','NULL')"),
	@NamedQuery(name="JvvnlUsersEntity.getDesignations" , query="SELECT DISTINCT(designation) from JvvnlUsersEntity WHERE designation is not NULL ORDER BY designation ASC"),
	@NamedQuery(name="JvvnlUsersEntity.findUsersData" , query="SELECT s FROM JvvnlUsersEntity s WHERE s.office_code=:office_code"),
	@NamedQuery(name="JvvnlUsersEntity.findAllUsers" , query="SELECT s FROM JvvnlUsersEntity s WHERE s.office_code is not NULL ORDER BY s.userId DESC"),
	@NamedQuery(name="JvvnlUserEntity.delUser",query="DELETE  FROM JvvnlUsersEntity s WHERE s.userId=:userId"),
	@NamedQuery(name="JvvnlUserEntity.findDupUserName", query="SELECT d FROM JvvnlUsersEntity d WHERE UPPER(d.user_login_name)=:user_login_name"),
})
public class JvvnlUsersEntity {
	
	 @Id
/*	 @SequenceGenerator(name = "jvvnl_users_seq", sequenceName = "JVVNL_USERS_SEQ")
	 @GeneratedValue(generator = "jvvnl_users_seq")	*/
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="ID")
	private int userId;
	
	@Column(name="USER_NAME")
	private String user_name;
	
	@Column(name="USER_LOGIN_NAME")
	private String user_login_name;
	
	@Column(name="DESIGNATION")
	private String designation;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="OFFICE_TYPE")
	private String office_type;
	
	@Column(name="OFFICE_CODE")
	private String office_code;
	
	@Column(name="SDOCODE")
	private String sdocode;
	
	@Column(name="CIRCLE")
	private String circle;
	
	@Column(name="DIVISION")
	private String division;
	
	@Column(name="SDONAME")
	private String sdoname;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_login_name() {
		return user_login_name;
	}

	public void setUser_login_name(String user_login_name) {
		this.user_login_name = user_login_name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOffice_type() {
		return office_type;
	}

	public void setOffice_type(String office_type) {
		this.office_type = office_type;
	}

	public String getOffice_code() {
		return office_code;
	}

	public void setOffice_code(String office_code) {
		this.office_code = office_code;
	}

	public String getSdocode() {
		return sdocode;
	}

	public void setSdocode(String sdocode) {
		this.sdocode = sdocode;
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

	public String getSdoname() {
		return sdoname;
	}

	public void setSdoname(String sdoname) {
		this.sdoname = sdoname;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
	
	

}
