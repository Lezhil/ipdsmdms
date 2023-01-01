package com.bcits.mdas.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.AssignValidationRuleEntity;
import com.bcits.service.GenericService;

public interface AmrLoadService extends GenericService<AmrLoadEntity>
{
	List<AmrLoadEntity> getRecords(String meterno, String fileDate) throws ParseException;

	List<AmrLoadEntity> getLoadSurveyData(String mtrNo, String frmDate, String tDate, String radioValue);
	
	List<AmrLoadEntity> getLoadSurveyDataInfo(String mtrNo);
	

	AmrLoadEntity findEntityById(String colid);

	
	AmrLoadEntity findEntityByUniId(String mtrNo, Timestamp readTime);

	List<?> getNamePlateDetailsBymeterNo(String mtrNo);

	List<?> getNamePlateDetailsByKno(String mtrNo);

	List<?> getDailyLoadSurveyData(String mtrNo, String frmDate, String tDate,
			String radioValue);
	
	List<?> getDailyLoadSurveyDataInfo(String mtrNo);

	//List<?> getDailyMinMaxByMtrNo(String mtrNo, String frmDate, String tDate);
	//List<?> getDailyMinMaxByKnoNo(String kNo, String frmDate, String tDate);

	List<?> getTransactionData(String mtrno, String frmDate, String tDate);

	List<List<?>> getDailyMinMaxByMtrNo(String metrNo, String fromDate, String toDate);

	//List<?> getMaximumParameters(String metrNo, String fromDate, String toDate);

	List<List<?>> getdailyParametersByKno(String metrNo, String fromDate,
			String toDate);

	List<List<?>> getDailyMinMaxByMtrNoMf(String meterNum, String frmDate,
			String tDate);

	List<List<?>> getdailyParametersByKnoandMf(String meterNum, String frmDate,
			String tDate);

	Integer findEntityByLoadId(String mtrno, Timestamp readTime);

	List<?> getmMtrnoByTcode(String zone, String circle, String tname);
	
	List<?> getmMtrnoByInfo(String zone);

	void getEnergyHistoryPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno,
			String fdate, String tdate, ArrayList<String> strlist);

	void getLoadSurveyPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno, String fdate,
			String tdate);

	void getEventDetailsPDF(HttpServletResponse response, String zne, String cir, String twn, String mtrno,
			String fdate, String tdate);

	void getDailyParamPDF(HttpServletResponse response, String zne, String cir, String twn, String mtrno, String fdate,
			String tdate);
	
	List<AmrLoadEntity> getLoadSurveyDataExcel(String mtrno, String fdate, String tdate);
	

	void getInstantaneous2PDF(HttpServletResponse response, String zne, String cir, String twn, String mtrno,
			String fdate, String tdate);
	
	void getDailyMinPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno, String fdate,
			String tdate,String minmf);

	void getDailyMaxPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno,
			String fdate, String tdate,String maxmf);

	void getDailyAvgPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno,
			String fdate, String tdate, String avgmf);

	List<?> getmMtrnoByZonCirTwn(String zone, String circle, String tname);

	
	
}
