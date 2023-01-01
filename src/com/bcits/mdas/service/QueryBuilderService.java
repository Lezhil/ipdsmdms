package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bcits.service.GenericService;

public interface QueryBuilderService extends GenericService<Object> {
	List<?> getSchemaList();

	List<?> getTableList(HttpServletRequest request);

	List<?> getColumnList(String tableName, String schemaName);

	List<?> getDataList(HttpServletRequest req);

	List<?> getCustomList(HttpServletRequest req);
	List<?> getDTDataAvailability(String zone,String subdiv,String toDate, String fromDate, String circle, String division, String town,String towncode);
	List<?> getDTDataAvailabilityload(String zone,String subdiv, String fromDate,String toDate, String circle, String division, String town,String towncode);
	List<?> getDTDataAvailabilitybill(String zone,String subdiv,String toDate, String fromDate, String circle, String division, String town,String towncode);
	List<?> getDTDataAvailabilityinst(String zone,String subdiv,String toDate, String fromDate, String circle, String division, String town,String towncode);
	List<?> getDTDataAvailabilityconsumption(String zone,String toDate, String fromDate, String circle);

	List<?> getDtdashboardReports(String region, String circle, String reportId, String reportIdPeriod);

	List<?> getTotalinstanceDetails(String dttpid, String region, String circle, String reportId, String reportIdPeriod,
			String meterno);

	List<?> getDtpowerfailureReports(String region, String circle, String reportId, String reportIdPeriod);

	List<?> getdtcommReport(String zone, String circle, String town_code);
	
	List<?> getdtloadphasevr(String zone, String circle,String rdngmnth);
	List<?> getdtloadphasevy(String zone, String circle,String rdngmnth);
	List<?> getdtloadphasevb(String zone, String circle,String rdngmnth);
	
	
	List<?> getdtcommReportmonth(String zone, String circle, String town_code,String month);
	
	
	

	
//	  List<?> getdtcommReportmonth(String zone, String circle,String
//	 town_code,String month);
	 

}
