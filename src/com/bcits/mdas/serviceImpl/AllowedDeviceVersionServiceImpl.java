package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.mdas.entity.AllowedDeviceVersionsEntity;
import com.bcits.mdas.service.AllowedDeviceVersionService;
import com.bcits.serviceImpl.GenericServiceImpl;
@Repository
public class AllowedDeviceVersionServiceImpl extends GenericServiceImpl<AllowedDeviceVersionsEntity> implements AllowedDeviceVersionService
{

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<AllowedDeviceVersionsEntity> findAll() 
	{
		return postgresMdas.createNamedQuery("AllowedDeviceVersionsEntity.findAll").getResultList();	
	}

	@Override
	public Integer updateAllowedFlagStatus(Integer id, String status) {
		return postgresMdas
				.createNamedQuery(
						"AllowedDeviceVersionsEntity.updateAllowedStatus")
				.setParameter("id", id).setParameter("status", status)
				.executeUpdate();
	}

	@Override
	public List<AllowedDeviceVersionsEntity> findAllVersionsToAllow() {
		return postgresMdas.createNamedQuery(
				"AllowedDeviceVersionsEntity.findAllVersionsToAllow",
				AllowedDeviceVersionsEntity.class).getResultList();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<AllowedDeviceVersionsEntity> findAllField(String remarks) 
	{
		return postgresMdas.createNamedQuery("AllowedDeviceVersionsEntity.findAllField").setParameter("remarks", remarks).getResultList();	
	}
}
