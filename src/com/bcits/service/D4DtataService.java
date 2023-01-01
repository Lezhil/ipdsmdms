package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.entity.D4Data;

public interface D4DtataService extends GenericService<D4Data>{
	
	public List<D4Data> getDetailsBasedOnMeterNo(String meterNo,String billMonth,String previousMonthForD4,ModelMap model);

	public List<Object[]> getLoadSurveyDatewise(String meterNo,String selectedDateName, ModelMap model);

	//public void downloadLoadSurveyDataPdf(String meterno, String month,ModelMap model, HttpServletResponse response);

	public void downloadLoadSurveyIpPdf(String meterno, String month,ModelMap model, HttpServletResponse response);

}