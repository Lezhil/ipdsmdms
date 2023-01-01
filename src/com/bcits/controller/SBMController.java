package com.bcits.controller;

import java.io.IOException;
import java.util.Date;
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

import com.bcits.entity.MeterReaderDetailsEntity;
import com.bcits.entity.MobileBillingDataEntity;
import com.bcits.entity.SBMDetailsEntity;
import com.bcits.service.MRDetailsService;
import com.bcits.service.MasterService;
import com.bcits.service.SBMDetailsService;
import com.bcits.service.SBMService;
import com.bcits.utility.MDMLogger;

/*Author: Ved Prakash Mishra*/ 

@Controller
public class SBMController 
{
	@Autowired
	private SBMService sbmService;
	@Autowired
	private SBMDetailsService sbmDetailsService;
	
	@Autowired
	private MRDetailsService mrDetailsService;
	
	@Autowired
	private MasterService masterService;
	
	
	
	
	@RequestMapping(value="/mobileBillingData", method={RequestMethod.GET,RequestMethod.POST})
     public String mobileBillingData(@ModelAttribute("transferData") MobileBillingDataEntity mobileBillingDataEntity,HttpServletRequest request,ModelMap model)
     {
		MDMLogger.logger.info("In :::::::::Mobile Billing Data  ");
		mobileBillingDataEntity.setBillmonth(sbmService.getMaxRdgMonthYear(request));
		
		return "mobileBillingData";
     }
	
	
	
	
	@RequestMapping(value="/addDataToSbm", method={RequestMethod.GET,RequestMethod.POST})
    public String addDataToSbm(@ModelAttribute("transferData") MobileBillingDataEntity mobileBillingDataEntity,HttpServletRequest request,ModelMap model)
    {
		MDMLogger.logger.info("In :::::Add Meter,Maste Data To SBMDownload Method   ");
		sbmService.countBillmonth(model,mobileBillingDataEntity.getBillmonth());
		return "mobileBillingData";
    }
	
	
	
	
	@RequestMapping(value="/sbmDetails", method={RequestMethod.GET,RequestMethod.POST})
    public String sbmDetails(@ModelAttribute("sbmDetailsEntity")SBMDetailsEntity sbmDetailsEntity,HttpServletRequest request,ModelMap model)
    {
		MDMLogger.logger.info("In :::::: SBM Details  Controller ");
		model.put("sbmdata",sbmDetailsService.getAllDetails());
		return "sbmDetails";
    }
	
	
	
	@RequestMapping(value="/addSbmDetails", method={RequestMethod.GET,RequestMethod.POST})
    public String addSbmDetails(@ModelAttribute("sbmDetailsEntity")SBMDetailsEntity sbmDetailsEntity,HttpServletRequest request,ModelMap model)
    {
		MDMLogger.logger.info("In :::::::::::In ADD SBM Details  Controller Method ");
		String username=(String)request.getSession().getAttribute("userMailId");
		sbmDetailsEntity.setDatestamp(new Date());
		sbmDetailsEntity.setUserid(username);
		Boolean sbmno=sbmDetailsService.findSbmNum(model,sbmDetailsEntity.getSbmnumber());
	    if(sbmno==true)
	    	 model.put("msg","Given IMEI number already exist ");
	    	
	     else
	    	{
	    	    sbmDetailsService.save(sbmDetailsEntity);
	    	    model.put("msg","IMEI Details Added Succsessfully");
	    	}
			
		
		
		
		model.put("sbmdata",sbmDetailsService.getAllDetails());
		model.put("sbmDetailsEntity",new SBMDetailsEntity());
		return "sbmDetails";
    }
	
	
	
	
	@RequestMapping(value="/meterReaderDetails", method={RequestMethod.GET,RequestMethod.POST})
    public String meterReaderDetails(@ModelAttribute("meterReaderDetailsEntity")MeterReaderDetailsEntity meterReaderDetailsEntity,HttpServletRequest request,ModelMap model)
    {
		MDMLogger.logger.info("In ::::::::::::::::::::::::MR Details Controller    ");
		//model.put("mrnamedata",sbmService.getMrnameData());
		
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		model.put("mrnamedata",masterService.getDistinctMrname());
		model.put("sbmno",sbmDetailsService.getSbmNumber());
		model.put("mrdetails",mrDetailsService.getAllMrDetails());
	    return "meterReaderDetails";
    }
	
	
	
	@RequestMapping(value="/addMeterReaderDetails", method={RequestMethod.GET,RequestMethod.POST})
    public String addMeterReaderDetails(@ModelAttribute("meterReaderDetailsEntity")MeterReaderDetailsEntity meterReaderDetailsEntity,HttpServletRequest request,ModelMap model)
    {
		MDMLogger.logger.info("In ::::::::::::::::::::::::In Add MR Details ");
		String username=(String)request.getSession().getAttribute("userMailId");
		meterReaderDetailsEntity.setUserid(username);
		meterReaderDetailsEntity.setDatestamp(new Date());
		Boolean sbmno=mrDetailsService.findDevice1(model,meterReaderDetailsEntity.getDevice());
	    Boolean mrname=mrDetailsService.findDupliMRname(meterReaderDetailsEntity.getMrname());
		System.out.println("SSSSSSSSSSS"+mrname+"DDDDDDDDDDDDDD"+sbmno);
	    if(sbmno==true)
	     {
	        model.put("msg","Given SBM number already allocated ");
	     }  
		else
		{
			if(mrname==true)
	        {
	        	model.put("msg","Given Mr name number already allocated ");
	        }
	    	
	       else
	    	{
	    		mrDetailsService.save(meterReaderDetailsEntity);
	    		model.put("msg","MR Details Added Sucessfully");
	    	}
	     }
			
	    List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		
		model.put("mrnamedata",sbmService.getMrnameData());
		model.put("sbmno",sbmDetailsService.getSbmNumber());
		model.put("mrdetails",mrDetailsService.getAllMrDetails());
		model.put("meterReaderDetailsEntity",new MeterReaderDetailsEntity());
		return "meterReaderDetails";
    }
	
	
	
	
	@RequestMapping(value="/detletMrDetails",method={RequestMethod.POST,RequestMethod.GET})
   	public  String deleteUserDetails(@ModelAttribute("meterReaderDetailsEntity")MeterReaderDetailsEntity meterReaderDetailsEntity,@RequestParam("delMrId")String operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
   	{
   		MDMLogger.logger.info("In ::::::::::::::::::::::::In Delete MR Details   ");
   		mrDetailsService.delete(operation);
   		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
   		model.put("mrnamedata",sbmService.getMrnameData());
		model.put("sbmno",sbmDetailsService.getSbmNumber());
   		model.put("mrdetails",mrDetailsService.getAllMrDetails());
   		model.put("msg","MR Details deleted succsessfully");
		model.put("meterReaderDetailsEntity", new MeterReaderDetailsEntity());
        return "meterReaderDetails";
   	}
	
	/* @RequestMapping(value="/checkMrnameDulicate/{operation}",method={RequestMethod.POST,RequestMethod.GET})
	  	public @ResponseBody Object checkMrnameDulicate(@PathVariable("operation") String operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	  	 {
		       MDMLogger.logger.info("In ::::::::::::::::::::::::In Check Mrname   ");
	           return mrDetailsService.findDupliMRname(operation);
	  	 }*/
	
	    @RequestMapping(value="/checkDeviceDulicate/{operation}",method={RequestMethod.POST,RequestMethod.GET})
	  	public @ResponseBody Object checkDeviceDulicate(@PathVariable("operation") String operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	  	 {
		      
		      MDMLogger.logger.info("In ::::::::::::::::::::::::In Check Device  "+operation);
		      return mrDetailsService.findDevice2(model,operation);
		     
	  	 }
	
	/*
	@RequestMapping(value="/checkMrnameDulicate/{operation}",method={RequestMethod.POST,RequestMethod.GET})
   	public @ResponseBody Object retriveMrName(@PathVariable String operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
   	{
   		MDMLogger.logger.info("In ::::::::::::::::::::::::In Check Mrname   ");
   	    System.out.println("MMMMMMMMMMMMMMMMM"+mrDetailsService.findDupliMRname(operation));
   		return mrDetailsService.findDupliMRname(operation);
   	   
   	
   }
	

	@RequestMapping(value="/checkDeviceDulicate/{operation}",method={RequestMethod.POST,RequestMethod.GET})
   	public @ResponseBody Object checkDeviceDulicate(@PathVariable String operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
   	{
   		MDMLogger.logger.info("In ::::::::::::::::::::::::In Check Device  "+operation);
   	    System.out.println("DDDDDDDDDDDDDDDDDDDD"+mrDetailsService.findDevice1(model,operation));
   		return mrDetailsService.findDevice1(model,operation);
   	    
   	
   }	
	*/

	@RequestMapping(value="/detletSbmDetails",method={RequestMethod.POST,RequestMethod.GET})
   	public  String detletSbmDetails(@ModelAttribute("sbmDetailsEntity")SBMDetailsEntity sbmDetailsEntity,@RequestParam("delSbmId")String operation,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
   	{
		
   		MDMLogger.logger.info("In ::::::::::::::::::::::::In Delete SBM Details   ");
   		sbmDetailsService.delete(operation);
   		model.put("sbmdata",sbmDetailsService.getAllDetails());
		model.put("sbmDetailsEntity",new SBMDetailsEntity());
		model.put("msg","IMEI Details Deleted Succsessfully");
		return "sbmDetails";
   	}
	
	
	
}
