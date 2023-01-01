package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bcits.mdas.entity.FeederMRDeviceAllocationEntity;
import com.bcits.service.GenericService;

public interface FeederMRDeviceAllocationService extends GenericService<FeederMRDeviceAllocationEntity> 
{
	public List<FeederMRDeviceAllocationEntity> findAllMRDeviceAllocations();
	public List<FeederMRDeviceAllocationEntity> findSdoCodeByMrcode(String mrCode);
  
	boolean checkAllocated(HttpServletRequest request,String mrcode,String deviceid);
	public List<Object> findBySdoCode(String sdoCode);
	public List<FeederMRDeviceAllocationEntity> findSdoCodeByMrcode1(String mrcode, String sdocode, String imei);
	public int deleteDevice(String deviceidPk);
	public List<FeederMRDeviceAllocationEntity> findByDeviceId(String deviceid);
}
