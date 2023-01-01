package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.MeterLifeCycleEntity;
import com.bcits.service.MeterLifeCycleService;

@Repository
public class MeterLifeCycleServiceImpl extends GenericServiceImpl<MeterLifeCycleEntity> implements MeterLifeCycleService {

	@Override
	public List<MeterLifeCycleEntity> searchmeter(String metrno) 
	{
		return postgresMdas.createNamedQuery("MeterLifeCycleEntity.searchmeterData").setParameter("meterno", metrno).getResultList();
	}
	
	@Override
	public List<MeterLifeCycleEntity> searchByAccno(String metrno,String accno) 
	{
		return postgresMdas.createNamedQuery("MeterLifeCycleEntity.searchData").setParameter("meterno", metrno).setParameter("accno", accno).getResultList();
	}

	@Override
	public List<MeterLifeCycleEntity> searchDuplicatemeter(String metrno,String accno) 
	{
		return postgresMdas.createNamedQuery("MeterLifeCycleEntity.searchDuplicateData").setParameter("meterno", metrno).getResultList();
	}

	@Override
	public List<MeterLifeCycleEntity> getData() {
		return postgresMdas.createNamedQuery("MeterLifeCycleEntity.getAllData").getResultList();
	}

}
