package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.PfcD2reportEntity;
import com.bcits.service.GenericService;

public interface PfcD2ReportService extends  GenericService<PfcD2reportEntity> {

	List<Object[]> getPFCD2ReportData(String billmonthNew);

	List<?> checkFreezDataD2(String billmonth);

	int freezeDataD2(String billmonth);

}
