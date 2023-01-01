package com.bcits.controller;

import java.io.IOException;
import java.util.List;

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

import com.bcits.service.ConsumerMasterService;

@Controller
public class OldDismantleMetersController {
	
	@Autowired
	private ConsumerMasterService cmsservice;
	
	@RequestMapping(value="/oldDismantleMeters", method={RequestMethod.POST,RequestMethod.GET})
	public String oldDismantleMeters(HttpServletRequest requst,ModelMap model)
	{
		//System.out.println("circle-details...");
		List<?> list=cmsservice.getCircle();
		model.put("circleName",list);
		
		return "oldDismantleMeters";
	}
	
	@RequestMapping(value="/divisionbycircle/{circle}", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> divisionbycircle(@PathVariable String circle,HttpServletRequest requst,ModelMap model)
	{
		//System.out.println("division-list....");
		List<?> divisionlist=cmsservice.getDivisionByCircle(circle);
		return divisionlist;
	}

	@RequestMapping(value="/subdivbydivision/{division}", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> subdivbydivision(@PathVariable String division,HttpServletRequest requst,ModelMap model)
	{
		//System.out.println("division-list....");
		List<?> subdivisionlist=cmsservice.getSubdivByDiv(division);
		return subdivisionlist;
	}
	
	//check Meterno Exist or not
		@RequestMapping(value="/getOldDismantleMeters/{circle}/{division}/{subdivision}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List retriveMrName(@PathVariable String circle,@PathVariable String division,@PathVariable String subdivision,HttpServletResponse response,HttpServletRequest request,ModelMap model)
		{
			
			
			return cmsservice.getAllOldDismantle(circle, division, subdivision);
		}
}
