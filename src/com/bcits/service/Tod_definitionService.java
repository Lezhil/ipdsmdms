package com.bcits.service;

import java.util.List;

import com.bcits.entity.TodDefinitionEntity;

public interface Tod_definitionService extends GenericService<TodDefinitionEntity>{
	
	public List getPreviuosEndTime();
	public List checkTodNameExist(String tod_name);
	public List<TodDefinitionEntity> getAllTODSlots();
	public int deleteTod(long id);
	public String getTODName();
}
