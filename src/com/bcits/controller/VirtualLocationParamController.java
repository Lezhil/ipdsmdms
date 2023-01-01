package com.bcits.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.service.VirtualLocParam;

@Controller
public class VirtualLocationParamController {

	@Autowired
	private VirtualLocParam virtualLocParams;
	
	@RequestMapping(value="/vlParameters",method= {RequestMethod.POST,RequestMethod.GET})
    public String virtualParameters(ModelMap model) {
		String cuurentDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//model.put("currentDate", cuurentDate);
		List<?> locationList=virtualLocParams.getvirtualLocationNames();
		model.addAttribute("locList", locationList);
		return "virtualparameters";
	}
	
	@RequestMapping(value="/getvlParameters",method= {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getvlParameters(HttpServletRequest request) throws ParseException {

		HttpSession session=request.getSession(false);
		String projectName=(String) session.getAttribute("projectName");
		String circle=request.getParameter("circle");
		String division=request.getParameter("division");
		String subdiv=request.getParameter("subdiv");
		String vlType=request.getParameter("vlType");
		String vlName=request.getParameter("vlName");
		String fromdate=request.getParameter("fromDate");
		String todate=request.getParameter("toDate");
		
		//System.out.println(circle+division+subdiv+vlName+vlType+fromdate+todate);
		List<?> parametersList =new ArrayList();
		parametersList=virtualLocParams.getVirtualLocationParameters(circle,division,subdiv,vlType,vlName,fromdate,todate,projectName);
		//System.out.println(parametersList.size());
		virtualLocParams.savelocParams(parametersList);
		return parametersList;
	}
	
}
