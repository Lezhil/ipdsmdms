package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ReportService {


	public List<?> pfanalysisreport(String region,String circle,String subdiv, String rdngmnth, String metertype);

	public List<?> getconsumerData(String zone,String circle,String division,String subdivision,String kno,String acno,String mtrno,String consumerCatgry);

	public List<?> getdtdata(String zone,String circle,String division,String subdivision,String crossdt, String dtcodeId, String dtmtrnoId);

	public List<?> getfeederdata(String zone,String circle,String division,String subdivision,String fedrcodeId, String crossfeeder,String fedrmtrnoId);

	List<?> getNetworkGraphicalData(String rdngMonth, String officeId, String locType);
	List<?> getfrequencyobliviondata(String zone, String officeId, String locType,String circle,String date);

	public List<?> voltageVariationAnalysisRep(String town, String rdngmnth, String metertype, String zone, String circle);

	List<Object[]> getFeederHealthRptData(String zone,String circle,String rdngmonth, String officeCode, String town,String crossfdr);

	public List getMultipleMeterDTMonthWiseResult(String zone, String circle,
			String division, String sdocode, String townCode, String dtTpCode,String billmonth);

	public List getMultipleMeterDTDateWiseResult(String fromDate,
			String toDate, String dtTpCode);

	public List getMultipleMeterDTMeterWiseResult(String billDate,
			String dtTpCode);

	public Object getDtHealthRptPdf(HttpServletRequest request, HttpServletResponse response, String month, String officeCode, String zone,String circle, String loctype, String town);

	public Object getFeederHealthRptPdf(HttpServletRequest request, HttpServletResponse response, String month, String officeCode, String zone,String circle, String loctype, String town,String townname);

	public void getVolVarReppdf(HttpServletRequest request, HttpServletResponse response, String zone, String circle, String town,
			 String loctype, String rdngmonth,String townname1);

	public void getPowerFactorReppdf(HttpServletRequest request, HttpServletResponse response, String zone, String circle, String town,
			 String loctype, String rdngmonth,String townname1);

	List<?> getDtHealthRptData(String zone, String circle,String rdngMonth, String officeId, String town);

	
	//String getOfficeCode(String subDiv);
	
	public List<?> getSaidiDataFromTableUpdate(String townfeeder, String town, String monthyr,String feeder);
	public void getSaidiSaifiReportpdf(String townfeeder, String town,String zne, String crcl, String frommonth,String feeder,String townnname1,String feedername, HttpServletRequest request,HttpServletResponse response,String tomonth);

	public List<?> getSaidiData(String town_code, String loc_type, String month_year);

	
	
	

	
}
