package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.entity.Master;

public interface PfcService extends GenericService<Master>{

	public List<?> getpfcd7Report(String rdngmnth, String[] town);

	public List<?> gettowns(String GovtSchemeId);

	public List<?> getpfcConnectionData(String PeriodId,String[] TownId);

	public List<?> getPeriodD5();

	public String getState();

	public String getDiscom();
	
	public List<?> getTown(String scheme);
	
	public List<?> getPeriodD2();
	
	public List<?> getTablepfcD5Details(String period,String TownId[]);
	
	public List<?> getPeriodD3();
	
	public List<?> getComplaintDetails(String period,String[] TownId);
	
    public List<?> getNppReportDetails(String period,String town,String scheme);

	public List<?> getDistinctPeriod();

	public List<?> getPfcD4RepData(String town[], String period);

	List<?> getpfcD1ReportData(String PeriodId, String[] TownId);
	public String getString(String[] townCode);
	
	void getPfcreportD1Pdf(HttpServletRequest request,HttpServletResponse response,String scheme,String town,String period,String state,String discom,String month,String ieperiod,String townname,String twn);

	void getPfcreportD2Pdf(HttpServletRequest request,HttpServletResponse response,String scheme,String town,String period,String state,String discom,String month,String ieperiod,String townname,String twn);

	void getPfcreportD3Pdf(HttpServletRequest request,HttpServletResponse response,String scheme,String town,String period,String state,String discom,String month,String ieperiod,String townname,String twn);

	void getPfcreportD4Pdf(HttpServletRequest request,HttpServletResponse response,String scheme,String town,String period,String state,String discom,String month,String ieperiod,String townname,String twn);

	void getPfcreportD5Pdf(HttpServletRequest request,HttpServletResponse response,String scheme,String town,String period,String state,String discom,String month,String ieperiod,String townname,String twn);

	void getPfcreportD6Pdf(HttpServletRequest request,HttpServletResponse response,String scheme,String town,String period,String state,String discom,String month,String ieperiod,String townname,String twn);

	void getPfcreportD7Pdf(HttpServletRequest request,HttpServletResponse response,String scheme,String town,String period,String state,String discom,String month,String ieperiod,String townname,String twn);

	void getLossCalculator (HttpServletRequest request,HttpServletResponse response,double inputEnergy,double billedEnergy,double amountBilled,double amountCollected,String anyTextId);
	
	public void getNPPReportConsumerPdf(HttpServletRequest request, HttpServletResponse response, String scheme,
			String town, String period, String state, String discom, String month, String ieperiod,String townname,String twn);
	
	void getNPPReportFeederPdf(HttpServletRequest request, HttpServletResponse response, String scheme, String town,
			String period, String state, String discom, String month, String ieperiod,String townname,String twn);

	public List<?> getpfcD1Data(String period, String finalString, String twn);

	public List<?> getpfcD2Data(String period, String finalString, String twn);

	public List<?> getpfcD3Data(String period, String finalString, String twn);

	public List<?> getpfcD4Data(String period, String finalString, String twn);

	public List<?> getpfcD5Data(String period, String finalString, String twn);

	public List<?> getpfcD6Data(String period, String finalString, String twn);

	public List<?> getpfcD7Data(String period, String finalString, String twn);

	public List<?> getnppconsumData(String period, String finalString, String twn);

	public List<?> getnppfeederData(String period, String finalString, String twn);
	
	void getdtlossPDF(String town, String monthyear, String periodMonth, String feederTpId, String zone, String circle,HttpServletRequest request,HttpServletResponse response);


}
