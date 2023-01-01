package com.bcits.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.LoadAvailabilityReportEntity;


public interface LoadAvailabilityRptService extends GenericService<LoadAvailabilityReportEntity> {
	List<LoadAvailabilityReportEntity> getDistinctZone();

	List<?>getCircleByZone(String zone, ModelMap model);

	List<?>getDivisionByCircle(String zone, String circle, ModelMap model);

	List<?>getSubdivByDivisionByCircle(String zone, String circle, String division, ModelMap model);

	List<?>getloadavailabilityreport(String zone,String circle,String division,String subdiv, String fDate, String tDate);

	List<?>getLoadSummaryReport(String fromDate, String toDate);

	List<?> executeSelectQueryRrnList(String qry);

	
	

}
