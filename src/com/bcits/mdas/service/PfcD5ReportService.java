package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.Pfcd5ReportEntity;
import com.bcits.service.GenericService;

public interface PfcD5ReportService extends GenericService<Pfcd5ReportEntity>{

	List<?> checkFreezDataD5(String billmonth);
	int freezeDataD5(String billmonth);

	

}
