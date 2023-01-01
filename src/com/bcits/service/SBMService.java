package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.entity.MobileBillingDataEntity;
/*Author: Ved Prakash Mishra*/
public interface SBMService extends GenericService<MobileBillingDataEntity>
{
	public void getSbmData(ModelMap model,Integer readingMonth);
	public int  getMaxRdgMonthYear(HttpServletRequest request);
	public List<MobileBillingDataEntity> getMrnameData();

	public List<MobileBillingDataEntity> getMobileBillingData(String mrname, HttpServletRequest request);

	public void countBillmonth(ModelMap model,Integer billmonth);

}
