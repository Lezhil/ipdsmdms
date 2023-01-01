package com.bcits.service;

import java.util.List;

import com.bcits.entity.MeterLifeCycleEntity;

public interface MeterLifeCycleService extends GenericService<MeterLifeCycleEntity> 
{

	List<MeterLifeCycleEntity> searchmeter(String metrno);

	List<MeterLifeCycleEntity> searchByAccno(String mtrno, String accno);

	List<MeterLifeCycleEntity> searchDuplicatemeter(String metrno,String accno);

	List<MeterLifeCycleEntity> getData();

}
