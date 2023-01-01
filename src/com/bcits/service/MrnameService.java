package com.bcits.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.Mrname;

public interface MrnameService extends GenericService<Mrname>
{
	List<Mrname> findAll();
	public String findDuplicateMrname(String mrname,ModelMap model);
	int updateMRName(String oldMrName, String newMrName,String circle,String sdocode);
	int updateMRNameSDO(String oldMrName,String newMrName,String sdoCode,String tadesc,String circle);
	List<MeterMaster> AccountNos();
	int updateAccountNO(String oldAccNo,String newAccNo);
	
	int updateMrnameTable(String oldMrName, String newMrName);
	
}