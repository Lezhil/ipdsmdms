package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.NamePlate;
import com.bcits.service.GenericService;

public interface NamePlateService extends GenericService<NamePlate> {
	public List<Object[]> nodeIdList();

	public NamePlate getNamePlateDataByMeterNo(String meterid);
}
