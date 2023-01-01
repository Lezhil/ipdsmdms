package com.bcits.service;

import java.util.List;

import com.bcits.entity.UserAccessType;

public interface UserAccessTypeService extends GenericService<UserAccessType>{

	public String getSideMenu(String userType);
	public long checkUserType(String userType);
	public List<UserAccessType> findAll();
	public boolean isUserTypeAssigned(String userType);
	public int deleteUserType(String deleteId);
	public String getEditRights(String userType);
	public String getViewRights(String userType);
}