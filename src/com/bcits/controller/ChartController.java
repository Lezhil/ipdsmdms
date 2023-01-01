package com.bcits.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.service.D4LoadDataService;
import com.bcits.utility.MDMLogger;

@Controller
public class ChartController 
{
	@Autowired
	private D4LoadDataService d4LoadDataService;
	
	@RequestMapping(value="/showBarGraph", method={RequestMethod.GET,RequestMethod.POST})
    public String showBarGraph(HttpServletRequest request,ModelMap model)
    {
		MDMLogger.logger.info("In :::::::::showBarGraph");
		return "barChart";
    }
	
	@RequestMapping(value="/getIntervalD4LoadData1/{meterno}/{billmonth}/{billdate}",method={RequestMethod.POST,RequestMethod.GET})
   	public @ResponseBody Object getIntervalD4LoadData(@PathVariable String billdate,@PathVariable String meterno,@PathVariable String billmonth, HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
   	{
		return d4LoadDataService.getIntervalD4LoadData1(billmonth,meterno,billdate,model);
    }
	
	@RequestMapping(value="/getIntervalD4LoadData", method={RequestMethod.GET,RequestMethod.POST})
    public String getIntervalD4LoadData(HttpServletRequest request,ModelMap model)
    {
		MDMLogger.logger.info("In :::::::::showBarGraph");
		d4LoadDataService.getIntervalD4LoadData(request.getParameter("selectedDateName"),request.getParameter("meterNo"),model);
		model.put("meterNumber", request.getParameter("meterNo"));
		model.put("selectedData", request.getParameter("selectedDateName"));
		return "barChart";
    }
}
