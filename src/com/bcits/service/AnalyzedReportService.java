package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.entity.Master;

public interface AnalyzedReportService extends GenericService<Master> 
{

	List<?> getCmriListData(String circle, String rdngMonth, String sdoname);
	
	List<?> getMISXMLReport(String circle, String rdngMonth, String sdoname);

	List<?> getCNPListData(String circle, String rdngMonth, String sdoname);

	List<?> getDateWiseListData(String circle, String rdngMonth,String sdoname);

	List<?> getDefectiveListData(String circle, String rdngMonth, String sdoname);

	List<?> getEnergyWiseListData(String circle, String rdngMonth, String sdoname);

	List<?> getEventWiseListData(String circle, String rdngMonth, String sdoname);

	List<?> getFaultyListData(String circle, String rdngMonth,String sdoname);

	List<?> getIndexUsageListData(String circle, String rdngMonth,String sdoname);

	List<?> getLoadUtilizationData(String circle, String rdngMonth, String sdoname);

	List<?> getManualReportData(String circle, String rdngMonth,String sdoname);

	List<?> getOtherMakeReportData(String circle, String rdngMonth,String sdoname);

	List<?> getPowerFactorData(String circle, String rdngMonth, String sdoname);

	List<?> getMeterChangeData(String circle, String rdngMonth,String sdoname);

	List<?> getStaticClassData(String circle, String rdngMonth,String sdoname);

	List<?> getTamperReportData(String circle, String rdngMonth,String sdoname, String division, String TownCode);

	List<?> getTransactionReportData(String circle, String rdngMonth, String sdoname);

	List<?> getWiringReportData(String circle, String rdngMonth,String sdoname);

	List<?> ExcelForLoadSurvey(String billMonth, String meterno);

	byte[] findOnlyImage4(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, String rdngMonth, String accNo);

	byte[] findOnlyImageDefective(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, String rdngMonth, String accNo);

	List<?> getconsumerConsumptionData(String circle, String rdngMonth,
			String category, String part);
	List<?> getUsageIndexingReport(HttpServletRequest req);

	//avg load data
	public List findALLDateWiseAvgLOad(String selectedDateName,String circle,String division,String subdivision);
	
	//missing mtrs in bill data
	public List findAllMissingMtrsInBillData(String selectedDateName,String circle,String division,String subdivision);

	List<?> getTamperHistoryReport(String circle, String rdngMonth,
			String sdoname);

	List<?> getTamperSummaryData(String circle, String division,String rdngmnth, String subdiv, String TownCode );

}
