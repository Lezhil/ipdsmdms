package com.bcits.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.service.BatchStatusService;

@Controller
public class BatchStatusContoller {
	
	@Autowired 
	private BatchStatusService batchStatusService;
	
	@RequestMapping(value="/batchStatus")
	public String batchMethod(HttpServletRequest request,ModelMap model)
	{
		model.put("circle", batchStatusService.getCircle());
		return "batchStatus";
	}
	
	
	@RequestMapping(value="/getSdocodeBacedoncircle",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody public List<?> sdocodeMethod(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circle");
		return batchStatusService.getSdocodeOnCircle(circle);
		
	}
	

	
	@RequestMapping(value="/getDateOnSdocodeAndcircle",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody public List<?> readingDateMethod(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circle");
		String sdocode=request.getParameter("sdocode");
		return batchStatusService.getReadingDateOnSdocodeAndcircle(circle,sdocode);
		
	}
	
	
	@RequestMapping(value="/searchMMUpdatedRecords",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody public List<?> searchMMUpdatedMethod(HttpServletRequest request,ModelMap model)
	{
		List<?> batchlist=new ArrayList<>();
		String circle=request.getParameter("circle");
		String sdocode=request.getParameter("sdocode");
		//String batchDate=request.getParameter("batchdate");
		System.out.println("circle===="+circle+"sdocode===="+sdocode);
		
		batchlist=batchStatusService.getAllbatchStatusRecords(circle,sdocode);
		//System.err.println("List size====="+batchlist.size());
		model.put("circle", batchStatusService.getCircle());
		//model.addAttribute("batchlist",batchlist);
		
		return batchlist;
		
	}
	
	
	@RequestMapping(value="/searchNotMMUpdatedRecords",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody public List<?>  notupdatedMM(HttpServletRequest request,ModelMap model)
	{
		List<?> batchlist=new ArrayList<>();
		String circle=request.getParameter("circle");
		String sdocode=request.getParameter("sdocode");
		//String batchDate=request.getParameter("batchdate");
	//	System.err.println("circle===="+circle+"sdocode===="+sdocode);
		
		batchlist=batchStatusService.getmmNotUpdatedStatusRecords(circle,sdocode);
		//System.err.println("List size====="+batchlist.size());
		//model.put("circle", batchStatusService.getCircle());
		//model.addAttribute("batchlist",batchlist);
		return batchlist;
		
	}

	
	
	
	@RequestMapping(value="/searchXmlUpdatedRecords",method={RequestMethod.GET,RequestMethod.POST})
	public  @ResponseBody  List<?> xmlUpdatedMethod(HttpServletRequest request,ModelMap model)
	{
		List<?> batchlist=new ArrayList<>();
		String circle=request.getParameter("circle");
		String sdocode=request.getParameter("sdocode");
		//String batchDate=request.getParameter("batchdate");
		//System.err.println("circle===="+circle+"sdocode===="+sdocode);
		
		batchlist=batchStatusService.getxmlUpdatedStatusRecords(circle,sdocode);
		//System.err.println("List size====="+batchlist.size());
		
		return batchlist;
		
	}
	
	
	
	
	@RequestMapping(value="/searchXmlNotUpdatedRecords",method={RequestMethod.GET,RequestMethod.POST})
	public  @ResponseBody  List<?> xmlNotUpdatedMethod(HttpServletRequest request,ModelMap model)
	{
		List<?> batchlist=new ArrayList<>();
		String circle=request.getParameter("circle");
		String sdocode=request.getParameter("sdocode");
		//String batchDate=request.getParameter("batchdate");
		//System.err.println("circle===="+circle+"sdocode===="+sdocode);
		
		batchlist=batchStatusService.getxmlNotUpdatedStatusRecords(circle,sdocode);
		//System.err.println("List size====="+batchlist.size());
		
		return batchlist;
		
	}
	
	
	

	@RequestMapping(value="/searchXmlAndMMUpdatedRecords",method={RequestMethod.GET,RequestMethod.POST})
	public  @ResponseBody  List<?> xmlAndMMUpdatedMethod(HttpServletRequest request,ModelMap model)
	{
		List<?> batchlist=new ArrayList<>();
		String circle=request.getParameter("circle");
		String sdocode=request.getParameter("sdocode");
		//String batchDate=request.getParameter("batchdate");
		//System.err.println("circle===="+circle+"sdocode===="+sdocode);
		
		batchlist=batchStatusService.getxmlAndMMUpdatedStatusRecords(circle,sdocode);
		//System.err.println("List size====="+batchlist.size());
		
		return batchlist;
		
	}
	
	@RequestMapping(value="/searchAllRecordsForNoCircle",method={RequestMethod.GET,RequestMethod.POST})
	public  @ResponseBody  List<?> recordsForNoCircle(HttpServletRequest request,ModelMap model)
	{
		List<?> batchlist=new ArrayList<>();
		
		batchlist=batchStatusService.getAllRecordsWithNoCircles();
		//System.err.println("List size====="+batchlist.size());
		
		return batchlist;
		
	}
	

}
