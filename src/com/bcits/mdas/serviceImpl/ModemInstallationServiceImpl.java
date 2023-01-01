package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.ModemInstallationEntity;
import com.bcits.mdas.entity.ModemMasterEntity;
import com.bcits.mdas.service.ModemInstallationService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class ModemInstallationServiceImpl extends GenericServiceImpl<ModemInstallationEntity> implements ModemInstallationService
{

	@Override
	public List<ModemMasterEntity> findAll() {
		List<ModemMasterEntity> list=null;
		return list;
	}
}
