package com.bcits.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bcits.entity.MeterMaster;
import com.bcits.service.MasterService;
import com.bcits.service.RdngMonthService;


@Controller
public class MRWiseTotalController {
	
	@Autowired 
	private MasterService masterService;
	
	@Autowired
	private RdngMonthService rdngMonthService;
	
	@RequestMapping(value="/mrTotal",method={RequestMethod.GET,RequestMethod.POST})
	public String totalmrWiseMethod(@ModelAttribute("Newseal") MeterMaster Newseal,ModelMap model)
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		model.put("readingDate",sdf.format(date));
		
		//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
		
		model.put("readingMonth",rdngMonthService.findAll());
		
		model.put("category",masterService.getTadescforMrWise());
		model.put("circle",masterService.getCircleForMrWiseTotal());
		
		return "mrTotal";
		
	}
	
	@RequestMapping(value="/getAllMrWiseTotalData",method={RequestMethod.GET,RequestMethod.POST})
	public String mrwiseTotalRecords(@ModelAttribute("Newseal") MeterMaster Newseal,HttpServletRequest reqest,ModelMap model)
	{
		System.out.println("Inside 1st table form submit------------------");
		
		//Date date = new Date();
		//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
		model.put("readingMonth",rdngMonthService.findAll());
		
		model.put("category",masterService.getTadescforMrWise());
		model.put("circle",masterService.getCircleForMrWiseTotal());
		
		
		String billMonth=reqest.getParameter("readingmonth");
		String tadesc=reqest.getParameter("tadesc");
		List<?> mrTotalList=masterService.getMrWiseRecordonRdngMonthTadsc(billMonth,tadesc);
		if(mrTotalList.size() > 0)
		{
			model.put("mrTotalShow","mrTotalShow");
			model.put("mrTotalList", mrTotalList);
		}
		else
		{
			model.put("mrTotalError", "MR Wise Total Records Not Found...");
		}
		
		return "mrTotal";
	}
	
	

	@RequestMapping(value="/getMrWiseTotalDataOnCircle",method={RequestMethod.GET,RequestMethod.POST})
	public String mrWiseRecordsMethod(@ModelAttribute("Newseal") MeterMaster Newseal,HttpServletRequest reqest,ModelMap model)
	{
		System.out.println("Inside 2nd table form submit------------------");
		
		/*Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
		model.put("readingMonth",sdf1.format(date));*/
		
		model.put("readingMonth",rdngMonthService.findAll());
		model.put("category",masterService.getTadescforMrWise());
		model.put("circle",masterService.getCircleForMrWiseTotal());
		
		String billMonth=reqest.getParameter("rdMonth");
		String circle=reqest.getParameter("circleId");
		List<?> mrWiseList=masterService.getMrwiseDataOnRdngMonthCircle(billMonth,circle);
		if(mrWiseList.size()>0)
		{
			model.put("mrWiseListShow", "mrWiseListShow");
			model.put("mrWiseList", mrWiseList);
		}
		else
		{
			model.put("mrWiseListError", "MR Wise Total Records Not Found...");
		}
		
		return "mrTotal";
	}
}
