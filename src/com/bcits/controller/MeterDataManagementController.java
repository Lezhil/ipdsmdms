package com.bcits.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * 
 * @author Amith Nishti
 * 
 *
 */
@Controller
public class MeterDataManagementController {

	@RequestMapping(value="/meterValidationUi",method={RequestMethod.GET,RequestMethod.POST})
	public String analysedRecords(HttpServletRequest request,ModelMap model)
	{
	
		
		
		
		return "validationService";
	}
	
}
