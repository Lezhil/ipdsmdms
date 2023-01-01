package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.mdas.entity.OnAirVersionUpdationEntity;
import com.bcits.mdas.service.OnAirVersionUpdationService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class OnAirVersionUpdationServiceImpl extends GenericServiceImpl<OnAirVersionUpdationEntity>	implements OnAirVersionUpdationService {

	@Override
	public List<OnAirVersionUpdationEntity> findAll() {
		return postgresMdas.createNamedQuery("OnAirVersionUpdationEntity.findAll",	OnAirVersionUpdationEntity.class).getResultList();
	}

	@Override
	public long checkVersionExist(String version) {
		return (Long)postgresMdas.createNamedQuery("OnAirVersionUpdationEntity.checkVersionExist").setParameter("version", version).getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OnAirVersionUpdationEntity> getlatestVersion() {
		return postgresMdas.createNamedQuery(
				"OnAirVersionUpdationEntity.latestVersion",
				OnAirVersionUpdationEntity.class).getResultList();
	}
	

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OnAirVersionUpdationEntity> getlatestVersion(String appname) {
		return postgresMdas.createNamedQuery(
				"OnAirVersionUpdationEntity.latestVersionfield",
				OnAirVersionUpdationEntity.class).setParameter("remarks", appname)
				.getResultList();
	}
}
