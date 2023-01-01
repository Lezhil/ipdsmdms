package com.bcits.service;

import java.util.List;

import com.bcits.entity.SdoJcc;

public interface SdoJccService extends GenericService<SdoJcc>
{

	public abstract List<SdoJcc> findAll();
	public  List getLevelDetails(String sdoCode,String level,String readingMonth);
	public  List getMRLevelDetails(String sdoCode,String level,String readingMonth,String mrName);
	public List getPendingSummaryDetails(String readingMonth,String condition);
	public List getNewConnectionDetails(String readingMonth);
	public List getHtPendingDetails(String readingMonth,String circle);
	public List<String> getAllCircle();
	public List<String> getAllDivisonBasedOnCircle(String circle);
	public List<?> getAllSubDivisonBasedOnDivision(String division);
	public String getsubDivisionName(String sdocode);
	
	
	public List<?> getAllDistDivision();
	public List<?> getDistALLSdoCodes();
	public List<?> getDistALLSdoNames();
	public List<?> getDistALLMNP();
	public SdoJcc getAllDetailsForAccno(String accn);
	public List<?> getDistALLMNPSDOJCC();
	public abstract List<?> getALLDivisionByCIR(String circle);
	public List<?> getSubDivisionList(String division);
	public abstract List<?> getHtManualDetails(String rdngMonth, String circle1);

	
	public List<?> getSdoNameForAnaysedReport(String circle);

	public abstract List<?> getDataFromsdoJcc(String sdocode);
	public abstract List<?> getAccnoNotAvailableData(String rdngMonth);

}