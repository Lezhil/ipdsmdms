package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.ModemLifeCycleEntity;
import com.bcits.mdas.service.ModemLifeCycleService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class ModemLifeCycleServiceImpl extends GenericServiceImpl<ModemLifeCycleEntity> implements ModemLifeCycleService {

	@Override
	public List<ModemLifeCycleEntity> getLifeCycleDataByImei(String imei) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("ModemLifeCycleEntity.getLifeCycleDataByImei", ModemLifeCycleEntity.class).setParameter("imei", imei).getResultList();
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
	}

}
