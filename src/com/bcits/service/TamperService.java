package com.bcits.service;

import java.util.List;

import com.bcits.entity.TamperEntity;

public interface TamperService extends GenericService<TamperEntity> {

	List<?> getTamperType();

	List<?> getTamperDATA(int rdngmonth);

	List<?> getTmpViewData(String circle, int rdngmonth);

	Long checkMeterNo(String rdngMonth, String meterNo);

}
