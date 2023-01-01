package com.bcits.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bcits.service.MeterMasterService;
import com.bcits.utility.MDMLogger;


@Controller
public class NewReportsController
{
	@Autowired
	private MeterMasterService meterMasterService;
	
	@RequestMapping(value="/billingParameter",method=RequestMethod.GET)
	public String fulDegree(HttpServletRequest request)
	{
		System.out.println("Inside billing parameter controller");
		return "billingParameter";
	}
	
	
	@RequestMapping(value="/getBillingParameters",method={RequestMethod.GET,RequestMethod.POST})
	public String getBillingParameters(ModelMap model,HttpServletRequest request,@RequestParam String meterNo,@RequestParam String selectedDateName)
	{
		MDMLogger.logger.info("In BillingData ");
		//List<XmlImport> list=xmlImportService.getDetailsBasedonMeterNo(meterNo,selectedDateName,model);
		System.out.println("metrno---"+meterNo);
		List billingParameters=meterMasterService.getBillingDataMM(selectedDateName, meterNo, model,request);
		model.put("meterno",meterNo);
		model.put("month",selectedDateName);
		model.put("billingParameters", billingParameters);
		return "billingParameter";
	}
	
		
}
