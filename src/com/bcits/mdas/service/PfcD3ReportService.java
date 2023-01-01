package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.PfcD3ReportEntity;
import com.bcits.service.GenericService;

public interface PfcD3ReportService extends  GenericService<PfcD3ReportEntity> {

	
	List<?> checkFreezDataD3(String billmonth);

	int freezeDataD3(String billmonth);

}
