package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.AllowedDeviceVersionsEntity;
import com.bcits.service.GenericService;

public interface AllowedDeviceVersionService extends GenericService<AllowedDeviceVersionsEntity>
{
	public List<AllowedDeviceVersionsEntity> findAll();

	public Integer updateAllowedFlagStatus(Integer id, String status);

	public List<AllowedDeviceVersionsEntity> findAllVersionsToAllow();
	
	public List<AllowedDeviceVersionsEntity> findAllField(String remarks);

}
