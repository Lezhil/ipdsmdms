package com.bcits.service;

import java.util.Date;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.XmlImport;


public interface XmlImportService extends GenericService<XmlImport>
{
	public List<XmlImport> getDetailsBasedonMeterNo(String meterNo,String billMonth,ModelMap model);

	public List<XmlImport> exportDetailsBasedonMeterNo(String parameter);
	public String ChechForBillingParameters(String meterNo,String currentDate);
	public String ChechRdateData(String meterNo,Date rDate); 
}