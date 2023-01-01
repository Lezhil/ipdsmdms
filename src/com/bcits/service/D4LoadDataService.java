package com.bcits.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.D4CdfData;

public interface D4LoadDataService extends GenericService<D4CdfData>
{

	List<D4CdfData> getloadSurveyDetailsBasedOnMeterNo(String meterNo,String loadSurveydate,ModelMap model);
	Object getIntervalD4LoadData(String billmonth, String meterno,ModelMap model);

	Object getIntervalD4LoadData1(String billmonth, String meterno,
			String billdate, ModelMap model);
	List<D4CdfData> getLoadSurveyData(String mtrNo, String billMonth);
	List<Object[]> getVeeEstimation(String mtrno,String billMonth);
	List<D4CdfData> getDuplicateData(String meterNumber, String dayProfileDate);
	List<?> getconsumptionanalysis(String division, String circle,
			String subdivision, String month);
}