package com.bcits.mdas.service;


import java.util.List;

import com.bcits.mdas.entity.PfcD7ReportEntity;
import com.bcits.service.GenericService;

public interface PfcD7ReportService extends  GenericService<PfcD7ReportEntity>
{

	int freezeData(String billmonth);

	List<?> checkFreezDataD7(String billmonth);

}
