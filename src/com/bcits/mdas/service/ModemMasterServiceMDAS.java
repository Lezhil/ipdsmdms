package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.ModemMasterEntity;
import com.bcits.service.GenericService;

public interface ModemMasterServiceMDAS extends GenericService<ModemMasterEntity> 
{
	List<ModemMasterEntity> findAll();

	ModemMasterEntity getEntityByImei(String imei);

	List<?> findNotInstalledIMEI();
	
	Object getCountForMappedMeters();

	
}
