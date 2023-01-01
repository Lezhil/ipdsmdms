package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.OnAirVersionUpdationEntity;
import com.bcits.service.GenericService;

public interface OnAirVersionUpdationService extends
		GenericService<OnAirVersionUpdationEntity> {

	public List<OnAirVersionUpdationEntity> findAll();
	
	long checkVersionExist(String version);
	public List<OnAirVersionUpdationEntity> getlatestVersion() ;
	public List<OnAirVersionUpdationEntity> getlatestVersion(String appname) ;

}
