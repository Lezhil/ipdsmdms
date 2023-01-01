package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.ModemLifeCycleEntity;
import com.bcits.service.GenericService;

public interface ModemLifeCycleService extends GenericService<ModemLifeCycleEntity> {

	List<ModemLifeCycleEntity> getLifeCycleDataByImei(String imei);

}
