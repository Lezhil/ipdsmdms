package com.bcits.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bcits.entity.MeterMaster;
import com.bcits.service.RdngMonthService;
import com.bcits.service.sealService;

@Controller
public class SealSearchController {
	
	@Autowired
	private sealService sealservice;
	
	@Autowired
	private RdngMonthService rdngMonthService;
	
	
	@RequestMapping(value = "/sealSearch", method = { RequestMethod.GET,RequestMethod.POST })
	public String seachSealMethod(@ModelAttribute("Newseal") MeterMaster Newseal,HttpServletRequest request,ModelMap model) 
	{
		model.put("readingMonth",rdngMonthService.findAll());
		return "sealSearch";
	}
	
	
	@RequestMapping(value = "/getFirstSealData", method = { RequestMethod.GET,RequestMethod.POST })
	public String getfirstSealData(@ModelAttribute("Newseal") MeterMaster Newseal,HttpServletRequest request,ModelMap model) 
	{
		
		model.put("readingMonth",rdngMonthService.findAll());
		
		String rdngMonth=request.getParameter("readingmonth");
		String sealNo=request.getParameter("sealNo");
		//System.out.println("getFirstSealData rdngMonth===>"+rdngMonth+"sealNo===>"+sealNo);
		
		List<?> firstSeal=sealservice.getFirstRecords(rdngMonth,sealNo);
		//System.out.println("firstSeal size======"+firstSeal.size());
		if(firstSeal.size() > 0)
		{
			model.put("firstSealShow", "firstSealShow");
			model.put("firstSealList", firstSeal);

		}
		else {
			model.put("firstSealError", "Seal Data Not Found");
		}
		return "sealSearch";
	}
	
	@RequestMapping(value = "/getSecondSealData", method = { RequestMethod.GET,RequestMethod.POST })
	public String getSecondSealDataMethod(@ModelAttribute("Newseal") MeterMaster Newseal,HttpServletRequest request,ModelMap model) 
	{
		
		model.put("readingMonth",rdngMonthService.findAll());
		
		String sealNo=request.getParameter("sealNoId");
		//System.out.println(" getSecondSealData sealNo===>"+sealNo);
		List<?> secondSeal=sealservice.getFirstRecords(sealNo);
		//System.out.println("secondSeal size======"+secondSeal.size());
		
		if(secondSeal.size() > 0)
		{
			model.put("seconddSealShow", "seconddSealShow");
			model.put("secondSealList", secondSeal);

		}
		else {
			model.put("secondSealError", "Seal Data Not Found");
		}
		return "sealSearch";
	}
	
}
