package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.RptFeederLossesEntity;
import com.bcits.service.GenericService;

public interface RptFeederLossesService extends GenericService<RptFeederLossesEntity> {

	List<Object[]> getrptFeederLosses(String billmonthNew);

}
