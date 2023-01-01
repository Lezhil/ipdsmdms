package com.bcits.serviceImpl;

import org.springframework.stereotype.Repository;

import com.bcits.entity.Meterdata;
import com.bcits.service.MeterdataService;
@Repository
public class MeterdataServiceImpl extends GenericServiceImpl<Meterdata> implements MeterdataService  {
	
	

	@Override
	public long checkMeterExists(String metrno) {
		// TODO Auto-generated method stub
		return (long)postgresMdas.createNamedQuery("Meterdata.checkMeterExists").setParameter("metrno", metrno).getSingleResult();
	}

	
}
