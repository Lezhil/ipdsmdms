package com.bcits.service;

import javax.servlet.http.HttpServletRequest;

import com.bcits.entity.ChangesEntity;
import com.bcits.entity.MeterMaster;

public interface ChangesService extends GenericService<ChangesEntity>{
	
	void InsertTransaction(MeterMaster newConnectionMeterMaster,String oldNew,HttpServletRequest request);
	
	public  ChangesEntity  InsertTransaction1(MeterMaster newConnectionMeterMaster,String oldNew,HttpServletRequest request);

}