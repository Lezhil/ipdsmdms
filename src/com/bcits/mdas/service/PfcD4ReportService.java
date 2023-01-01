package com.bcits.mdas.service;


import java.util.List;

import com.bcits.mdas.entity.PfcD4reportEntity;
import com.bcits.service.GenericService;

public interface PfcD4ReportService extends GenericService<PfcD4reportEntity>{

	List<?> checkFreezDataD4(String billmonth);

	int freezeDataD4(String billmonth);
	
}
