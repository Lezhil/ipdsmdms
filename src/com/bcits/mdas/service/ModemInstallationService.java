package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.ModemInstallationEntity;
import com.bcits.mdas.entity.ModemMasterEntity;
import com.bcits.service.GenericService;

public interface ModemInstallationService extends GenericService<ModemInstallationEntity> 
{
	List<ModemMasterEntity> findAll();
	
}
