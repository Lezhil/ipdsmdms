package com.bcits.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.service.ServiceexceptionsService;
@Controller
public class ServiceexceptionsController 
{
	@Autowired
	private ServiceexceptionsService serviceexceptionservice;

	@RequestMapping(value = "/serviceExceptionsreport", method = {RequestMethod.POST, RequestMethod.GET })
	public String ServiceExceptionsReport(ModelMap model,HttpServletRequest request) {
		
		/*List<?> getExceptionreport=serviceexceptionservice.getExceptionreport(request);
		model.put("reports", getExceptionreport);*/
		List<?> getExceptionreports=serviceexceptionservice.getExceptionreports();
		model.put("service", getExceptionreports);
		return "serviceExceptionRep";
	}	
	
	@RequestMapping(value = "/getReport", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getexceptionReport(HttpServletRequest request) 
	{
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		String service=request.getParameter("service");
		
		return serviceexceptionservice.getExceptionreport(fdate,tdate,service);	
}
}
