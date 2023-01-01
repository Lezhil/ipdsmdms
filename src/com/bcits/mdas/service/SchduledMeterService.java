package com.bcits.mdas.service;

import com.bcits.mdas.entity.SchduledMeterTrackEntity;
import com.bcits.service.GenericService;

public interface SchduledMeterService extends GenericService<SchduledMeterTrackEntity> {
	public String meterSamples(int count,long startId);
	public int highcount() ;

}
