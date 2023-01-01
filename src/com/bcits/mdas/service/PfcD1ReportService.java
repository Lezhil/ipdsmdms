package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.PfcD7ReportEntity;
import com.bcits.mdas.entity.pfcd1rptentity;
import com.bcits.service.GenericService;

public interface PfcD1ReportService extends  GenericService<pfcd1rptentity>
{

	List<?> checkFreezDataD1(String billmonth);

	int freezeDataD1(String billmonth);

	List<Object[]> getPFCD1ReportData(String billmonth);

	

}
