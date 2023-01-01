package com.bcits.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.Master;
import com.bcits.entity.MeterLifeCycleEntity;
import com.bcits.entity.MeterMaster;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.service.MeterMasterService;
import com.bcits.utility.MDMLogger;

@Controller
public class NewMeterInstallController {
	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@RequestMapping(value="/newConnectionAMIMtr",method={RequestMethod.GET,RequestMethod.POST})
	public String newConnectionAMR(@ModelAttribute("newConnectionMeterMaster")MeterMaster newConnectionMeterMaster, HttpServletRequest request,HttpServletResponse resp,ModelMap model)
	{
		System.out.println("come inside new Consumer Connection");
		newConnectionMeterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		int RdngMonth=meterMasterService.getMaxRdgMonthYear(request);
		model.put("zones", meterMasterService.getAllZonesInAmiLocation());
		model.put("circle", meterMasterService.getAllCirclesInAmiLocation());
		model.put("division", meterMasterService.getAllDivisionsInAmiLocation());
		model.put("sdoname", meterMasterService.getAllSubDivisionsInAmiLocation());
		return "NewMeterConnection";
	}

	@RequestMapping(value="/getLocationDetailsBySdocode",method= {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object List(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("inside location controller--");
		List locationDetails=null;
		String sdocode=request.getParameter("sdocode");
		String meterNo=request.getParameter("meterNo");
		System.out.println("sdocode-->"+sdocode);
		locationDetails=meterMasterService.getLocationBySdocode(sdocode,meterNo);
		System.out.println("location details-->"+locationDetails);
		return locationDetails;
	}
	
	@RequestMapping(value="/addNewMtrConnectionAMI",method={RequestMethod.GET,RequestMethod.POST})
	public String addNewConnection(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model)
	{
		MDMLogger.logger.info("In Add new connection Data ");
		meterMasterService.addNewConnectionData(newConnectionMeterMaster, request, model);
		
			/*meterLifeCycleService.customsaveBySchema(lifeCycleEntity, "entityManager");*/
			//MasterMainEntity mastermain=new MasterMainEntity();
			System.out.println("newConnectionMeterMaster.getAccno()------->"+newConnectionMeterMaster.getAccno());
			
		
		return "NewMeterConnection";
	}
	
	
	
	
}
