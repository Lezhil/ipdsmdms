package com.bcits.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.CmriNumber;

public interface cmriDeviceService extends GenericService<CmriNumber>{

 //void newCmriNumber(ModelMap model);

	 List<CmriNumber> findAllCmriNumber();

	 public  String findDuplicate(String cmrinumber,ModelMap model);
	 public  String checkCmriAvail(String cmrinumber,ModelMap model);

	 public int  updateCrmri(String cmrinumber, String cmriid,ModelMap model);
	
	
}