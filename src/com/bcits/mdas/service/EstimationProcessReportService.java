/**
 * 
 */
package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.EstimationProcessRptEntity;
import com.bcits.service.GenericService;

/**
 * @author Tarik
 *
 */
public interface EstimationProcessReportService extends GenericService<EstimationProcessRptEntity>{

//	EstimationProcessRptEntity getAssignRuleId(String ruleType, String meter_number, String monthyear);
//
//	List<EstimationProcessRptEntity> getValidationReportData(String zone, String circle, String division,
//			String subdivision, String ruleid, String monthyear);
	List<Object[]> getEstimatedIpValue(String meterNumber,String fromDate,String toDate, HttpServletRequest request);

	

	List<Object[]> saveEstAvgData(List<Object[]> list, String fromDate,
			String toDate, HttpServletRequest request,String kwhArr[],String kvahArr[],String kwArr[],String kvaArr[]);



	List<Object[]> getEstimatedlastYearValue(String meterNumber,String fromDate, String toDate, HttpServletRequest request);






//	List<?> saveAmrLoad(List<Object[]> list, String fromDate,String toDate, HttpServletRequest request);
	//void saveEstimatedAvgIpData(List<Object[]> list, String fromDate,	String toDate, HttpServletRequest request);



	//AmrLoadEntity saveEstimatedAvgIpDataLoad(Object[] item, String fromDate,String toDate, HttpServletRequest request);



	//void saveEstimatedAvgIpDataProcess(Object[] item, String fromDate,	String toDate, Integer id, HttpServletRequest request);

}