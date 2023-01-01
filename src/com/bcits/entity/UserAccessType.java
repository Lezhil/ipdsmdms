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
@Table(name="USER_ACCESS_TYPE",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="UserAccessType.findAll",query="SELECT u FROM UserAccessType u ORDER BY u.userType "),
	@NamedQuery(name="UserAccessType.getSideMenu",query="SELECT u.accessTypeId FROM UserAccessType u WHERE UPPER(u.userType)=UPPER(:userType)"),
	@NamedQuery(name="UserAccessType.checkUserType",query="SELECT COUNT(*) FROM UserAccessType u WHERE UPPER(u.userType)=UPPER(:userType)")
})
public class UserAccessType {

	@Id
/*	@SequenceGenerator(name="uAccessTypeId",sequenceName="userAccessTypeId")
	@GeneratedValue(generator="uAccessTypeId")*/
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	
	@Column(name="USERTYPE")
	private String userType;
	
	@Column(name="ACCESSTYPEID")
	private String accessTypeId;
	
	
	
	
	public UserAccessType()
	{
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAccessTypeId() {
		return accessTypeId;
	}

	public void setAccessTypeId(String accessTypeId) {
		this.accessTypeId = accessTypeId;
	}
	
	
}
