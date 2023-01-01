package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterMaster;

public interface BillingData360Service extends GenericService<MeterMaster>{

public	List<MeterMaster> getBillingData360(String billMonth, String meterno,
			HttpServletRequest request, ModelMap model);
	
	

}
