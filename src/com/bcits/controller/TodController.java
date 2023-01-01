package com.bcits.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.service.TodService;
@Controller
public class TodController {
	
	@Autowired
	private TodService todService;

	@RequestMapping(value = "/toddaywisereport", method = {RequestMethod.POST, RequestMethod.GET })
	public String ServiceExceptionsReport(HttpServletRequest request) {
		
		return "tod";
	}	
	
	@RequestMapping(value = "/gettodReport", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object gettodreport(HttpServletRequest request) 
	{
		
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		String mtrno=request.getParameter("mtrno");
		
		return todService.gettodreport(fdate,tdate,mtrno);	
}
}




