package com.bcits.service;

import com.bcits.entity.Meterdata;

public interface MeterdataService extends GenericService<Meterdata> {

	long checkMeterExists(String metrno);

}
