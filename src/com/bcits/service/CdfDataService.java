package com.bcits.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.entity.CDFData;

public interface CdfDataService extends GenericService<CDFData>
{

	public  List<CDFData> findAll(String meterNo, int billmonth);
	public  int getRecentCdfId(String meterNo, int billmonth);
	public  int findPrevDataD4(String meterNo);
	
	// for checking the present day files
	public List<CDFData> checkFileExistanceForDay(String meterNo,Date deviceReadingDate);
	public  int getRecentChangedCdfId(String meterNo, Date deviceReadingDate);
	
	public int getMaxCdfId();
	 String findData(String month, ModelMap model, HttpServletRequest request);
}