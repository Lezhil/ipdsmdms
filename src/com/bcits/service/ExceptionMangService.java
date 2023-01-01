package com.bcits.service;

import java.util.List;

import com.bcits.entity.ExceptionManagementEntity;

public interface ExceptionMangService extends GenericService<ExceptionManagementEntity>{
	
	public List findALL();
	public int UpdateFlag(int id);

}
