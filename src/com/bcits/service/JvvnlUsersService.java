package com.bcits.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.JvvnlUsersEntity;

public interface JvvnlUsersService extends GenericService<JvvnlUsersEntity>{
	
	public List<?> findAllUsers(ModelMap model);

	public List<?> findAllOfficeTypes();
	
	public List<?> getDesignations();
	
	public List<?> getAllOfficeCodes(String Office_Type);
	
	public void findUsersData(JvvnlUsersEntity jvvnlusers,ModelMap model);
	
	int delUsers(int userId);
	
	List<JvvnlUsersEntity> findUserName(String userName);

}
