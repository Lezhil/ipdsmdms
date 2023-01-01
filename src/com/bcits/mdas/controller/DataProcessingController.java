package com.bcits.mdas.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.SBMDetailsEntity;
import com.bcits.entity.TodDefinitionEntity;
import com.bcits.service.DtDetailsService;
import com.bcits.service.Tod_definitionService;
import com.bcits.utility.MDMLogger;
@Controller
public class DataProcessingController {
	
	@Autowired
	private Tod_definitionService toddefinitionService;
	
	String msg="";
	
	@RequestMapping(value="/todview")
	public String todView(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		List result=toddefinitionService.getAllTODSlots();
		String todname=toddefinitionService.getTODName();
		model.put("todName", todname);
		model.put("tods",result);
		model.put("result", msg);
		msg="";
		return "todDefinition";	
	}
	
	@RequestMapping(value="/addTod",method={RequestMethod.GET,RequestMethod.POST})
	public String addTod(HttpServletRequest request, HttpServletResponse response,ModelMap model)
	{
		//System.out.println("todNo:"+request.getParameter("todNoId"));
		//System.out.println("startTime:"+request.getParameter("datetimepickerStartId"));
		//System.out.println("end Time:"+request.getParameter("datetimepickerEndId"));
		
		String todNo=request.getParameter("todNoId");
		String startTime=request.getParameter("datetimepickerStartId");
		String endTime=request.getParameter("datetimepickerEndId");
		TodDefinitionEntity tod=new TodDefinitionEntity();
		tod.setStart_time(startTime);
		tod.setEnd_time(endTime);
		tod.setTodno(todNo);
		tod.setEntryby(request.getSession().getAttribute("username")+"");
		tod.setEntrydate(new Timestamp(System.currentTimeMillis()));
		System.out.println(tod.toString());
		toddefinitionService.save(tod);
		msg="Data Saved Successfully";
		return "redirect:/todview";
	}
	
	@RequestMapping(value="/getPreviuosEndTime",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List getPreviuosEndTime(HttpServletRequest request,HttpServletResponse response){
		List result=toddefinitionService.getPreviuosEndTime();
		return result;
	}
	@RequestMapping(value="/checkTodNameExist",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List checkTodNameExist(HttpServletRequest request,HttpServletResponse response){
		List result=toddefinitionService.checkTodNameExist(request.getParameter("todNo"));
		return result;
	}
	
	@RequestMapping(value="/detletTodDefination",method={RequestMethod.POST,RequestMethod.GET})
   	public  String detletSbmDetails(HttpServletResponse response,HttpServletRequest request,ModelMap model) 
   	{
		String id=request.getParameter("toddelId");
		int count=0;
		try {
			count=toddefinitionService.deleteTod(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count>0){
		msg="Successfully Deleted";
		}
		return "redirect:/todview";
   	}
	
	//get Tod definition data
		@RequestMapping(value="/getTodDefinitionData/{operation}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object getTodDefinitionData(@PathVariable Long operation,HttpServletResponse response,HttpServletRequest request,ModelMap model)
		{
			MDMLogger.logger.info("In ::::::::::::::::::::::::In Edit MeterDetails   ");
			TodDefinitionEntity result=new TodDefinitionEntity();
			try {
				result = toddefinitionService.find(operation);
				
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
			return result;
		}
	
		
		@RequestMapping(value="/modifyTodDefinition",method={RequestMethod.GET,RequestMethod.POST})
		public String modifyTodDefinition(HttpServletRequest request, HttpServletResponse response,ModelMap model)
		{
			//System.out.println("startTime:"+request.getParameter("datetimepickerStartId"));
			//System.out.println("end Time:"+request.getParameter("datetimepickerEndId"));
			
			String todNo=request.getParameter("todNoId");
			String startTime=request.getParameter("datetimepickerStartId");
			String endTime=request.getParameter("datetimepickerEndId");
			
			Long id=Long.parseLong(request.getParameter("id"));
			TodDefinitionEntity tod=toddefinitionService.find(id);
			tod.setStart_time(startTime);
			tod.setEnd_time(endTime);
			tod.setTodno(todNo);
			tod.setUpdate_by(request.getSession().getAttribute("username")+"");
			tod.setUpdate_time(new Timestamp(System.currentTimeMillis()));
			toddefinitionService.update(tod);
			msg="Data Modified Successfully";
			return "redirect:/todview";
		}
		
		
		@RequestMapping(value="/getTODName",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody String getTODName(HttpServletResponse response,HttpServletRequest request,ModelMap model){
		
		return toddefinitionService.getTODName();
		}
		
	
}
