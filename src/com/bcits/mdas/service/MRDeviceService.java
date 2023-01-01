package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.MRDeviceEntity;
import com.bcits.service.GenericService;

public interface MRDeviceService extends GenericService<MRDeviceEntity> {


	public List<MRDeviceEntity> findAllMRDevices();

	public List<String> getMatchedDeviceIds(Integer sdoCode);

	public List<String> getMatchedDeviceIdsForAllocation(Integer sdoCode);
	
	public String findByDevice(String deviceid ) ;
}
