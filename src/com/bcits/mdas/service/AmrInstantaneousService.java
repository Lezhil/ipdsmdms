package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.AMIInstantaneousEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.service.GenericService;

public interface AmrInstantaneousService extends GenericService<AmrInstantaneousEntity> {
	List<?> getModemsStats(ModelMap model, HttpServletRequest request);

	List<Object[]> getTotalModems(ModelMap model, HttpServletRequest request);

	List<Object[]> getWorkingsModems(ModelMap model, HttpServletRequest request);

	List<Object[]> getNotWorkingsModems(ModelMap model,	HttpServletRequest request);

	List<AmrInstantaneousEntity> getInstansData(String mtrNo);

	AmrInstantaneousEntity getD1DataForXml(String meterNumber, String fileDate);

	ModelMap getFdrInstantaneousData(String imei,String mtrNo);

	ModelMap getfdrInsGraphData(String imei);


	List<AMIInstantaneousEntity> meterByDate(String meterNo,String selectedDateName, ModelMap model);
	
	List<AmrInstantaneousEntity> getMeterData(String mtrNo);

	List<AmrInstantaneousEntity> getCompleteInstansData(String mtrNo,
			String radioValue);
	List<AmrInstantaneousEntity> getCompleteInstansDataNew(String mtrNo, String frmDate, String tDate, String radioValue);

	List<AmrInstantaneousEntity> getAllInstansData(String mtrNo,
			String frmDate, String tDate, String radioValue);
	
}
