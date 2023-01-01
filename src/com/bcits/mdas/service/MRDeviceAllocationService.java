package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.MRDeviceAllocationEntity;
import com.bcits.service.GenericService;

public interface MRDeviceAllocationService extends
		GenericService<MRDeviceAllocationEntity> {

	public List<MRDeviceAllocationEntity> findAllMRDeviceAllocations();
	public boolean[] isUserExist(String sdoCode,String bmdReading, String imeiNumber );
	public boolean[] isUserExistOtherApps(String sdoCode,String bmdReading, String imeiNumber );
	String[] getLoginCredentials(String imeiNumber);
	String[] getMRDetails(String imeiNumber);

}
