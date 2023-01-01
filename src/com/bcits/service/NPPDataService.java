package com.bcits.service;

import java.util.List;

import com.bcits.entity.NPPDataEntity;

public interface NPPDataService extends GenericService<NPPDataEntity> {

	List<NPPDataEntity> getDataByMonthYear(String my);

	List<Object[]> getRAPDRPTown();

	List<Object[]> getIPDSTown();

	List<?> getDistinctMonthYear();

	List<Object[]> getNPPReportRapdrpDetails(String town, String period);

	List<Object[]> getNPPReportipdsDetails(String town, String period);

	List<NPPDataEntity> getDataByTownMonthYear(String town, String my);

	

}
