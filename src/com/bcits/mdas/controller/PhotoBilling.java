package com.bcits.mdas.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.service.FeederOutputService;
import com.bcits.mdas.service.SubstationOutputService;
import com.bcits.mdas.utility.BCITSLogger;

@Controller
public class PhotoBilling 
{
	@Autowired
	private FeederOutputService feederdata;
	@Autowired
	private SubstationOutputService substation;
	
	
	
	@RequestMapping(value = "/FeederPhoto", method={RequestMethod.POST,RequestMethod.GET})
	public  String getDistinctCircle(ModelMap model)
	{
		System.out.println("FeederPhoto =====");
		model.put("circles", feederdata.getCircle());
		return "FeederPhoto";
	}
	
	@RequestMapping(value = "/SurveyAbstract", method={RequestMethod.POST,RequestMethod.GET})
	public  String SurveyAbstract(ModelMap model,HttpServletRequest request)
	{
		String date=request.getParameter("datedata");
		System.out.println("date ="+date);
		if(date != null && !date.toString().isEmpty())
		{
			model.put("fromdata", date);
			model.put("data", feederdata.getSurveyData(date, model));
		}
		
		return "SurveyAbstract";
	}
	
	@RequestMapping(value = "/surveycount", method={RequestMethod.POST,RequestMethod.GET})
	public  String SurveyCount(ModelMap model,HttpServletRequest request)
	{
			model.put("data", feederdata.getSurveyAllData(model));
		return "surveycount";
	}
	
	
	
	@RequestMapping(value = "/getDistrict", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getDistrictByCircle(HttpServletRequest request, ModelMap model)
	{
	    System.out.println("circle="+request.getParameter("circle"));
		return feederdata.getDistrictByCircle(request.getParameter("circle"));
	}
	

	@RequestMapping(value = "/getStation", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getSubStation(HttpServletRequest request, ModelMap model)
	{
		 System.out.println("district="+request.getParameter("district"));
		return feederdata.getSubStationByDistrict(request.getParameter("circle"), request.getParameter("district"));
	}
	
	@RequestMapping(value = "/getFeeder", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getFeeder(HttpServletRequest request, ModelMap model)
	{
		 System.out.println("station="+request.getParameter("station"));
		 return feederdata.getFeederByStation(request.getParameter("circle"), request.getParameter("district"), request.getParameter("station"));
	}
	
	@RequestMapping(value = "/getFeederAllData", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getFeederAllData(HttpServletRequest request, ModelMap model)
	{
		 System.out.println("FeederID="+request.getParameter("fid"));
		 return feederdata.getFeederAllData(request.getParameter("fid"));
	}
	
	 @RequestMapping(value="/getFeederImages/{feederId}",method={RequestMethod.POST,RequestMethod.GET})
		public void getUseImage(ModelMap model, HttpServletRequest request,HttpServletResponse response,@PathVariable String feederId) throws IOException
		{
		 System.out.println("image type="+feederId);
		 String[] idlist=feederId.split("_");
			int id=Integer.parseInt(idlist[0]);
			 String imageTyle=idlist[1]; 
		 
		 
		// feederdata.getImage(request,response,id,imageTyle);
			 feederdata.getConsumerImage(request,response,id,imageTyle);
		     
		} 
	 
	 @RequestMapping(value="/SubstationPhoto",method={RequestMethod.POST,RequestMethod.GET})
		public String SubstationPhoto(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException
		{
		 model.put("circles", feederdata.getCircle());
		 return "SubstationPhoto";
		} 
	 
	 @RequestMapping(value="/getSubStation",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> getSubStation(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException
		{
		  
		 return substation.getSubStationBYdistrict(request.getParameter("district"));
		} 
	 
	 
	 
	 
	 @RequestMapping(value="/getSubStationImages/{subId}",method={RequestMethod.POST,RequestMethod.GET})
		public void getSubstationImage(ModelMap model, HttpServletRequest request,HttpServletResponse response,@PathVariable String subId) throws IOException
		{
		 System.out.println("image type="+subId);
		 String[] idlist=subId.split("_");
			int id=Integer.parseInt(idlist[0]);
			 String imageTyle=idlist[1]; 
			 substation.getImage(request,response,id,imageTyle);
		     
		} 
	 
	 @RequestMapping(value="/getdetailsdata1",method={RequestMethod.POST,RequestMethod.GET})
		public String getdetailsdata1(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException
		{
		   String date=request.getParameter("fromData");
		    model.put("fromdata", date);
		    model.put("data", feederdata.getSurveyData(date, model));
		    model.put("result", feederdata.getFeederAllDAta(date, model));
		 
		 return "SurveyAbstract";
		} 
	 @RequestMapping(value="/getdetailsdata2",method={RequestMethod.POST,RequestMethod.GET})
		public String getdetailsdata2(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException
		{
		    String date=request.getParameter("fromData");
		    String district=request.getParameter("district");
		    model.put("fromdata", date);
		    model.put("data", feederdata.getSurveyData(date, model));
		    model.put("result",substation.getSubstationAllDAta(date,district,model) );
		     return "SurveyAbstract";
		} 
	
	
	 @RequestMapping(value="/getCircleData",method={RequestMethod.POST,RequestMethod.GET})
		public String getCircleData(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException
		{
		    
		    String state=request.getParameter("state");
		    model.put("data", feederdata.getSurveyAllData(model));
		    model.put("StateData", feederdata.getSurveyDataByZone(model, state));
		    return "surveycount";
		} 
	 @RequestMapping(value="/getDistrictData",method={RequestMethod.POST,RequestMethod.GET})
		public String getDistrictData(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException
		{
		    String zone=request.getParameter("zone");
		    String state=request.getParameter("state");
		    model.put("data", feederdata.getSurveyAllData(model));
		    model.put("StateData", feederdata.getSurveyDataByZone(model, state));
		    model.put("zonedata", feederdata.getSurveyDataByCircle(model, zone,state));
		    return "surveycount";
		} 
	
	 @RequestMapping(value="/getFeederData",method={RequestMethod.POST,RequestMethod.GET})
		public String getFeederData(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException
		{
		   
		     String state=request.getParameter("state");
		     String zone=request.getParameter("zone");
		     String circle=request.getParameter("circle");
		        model.put("data", feederdata.getSurveyAllData(model));
			    model.put("StateData", feederdata.getSurveyDataByZone(model, state));
			    model.put("zonedata", feederdata.getSurveyDataByCircle(model, zone,state));
		        model.put("zone", zone);
		        model.put("state", state);
		        model.put("circle", circle);
		    return "surveycount";
		} 
	 
	 @RequestMapping(value="/getDivisionData",method={RequestMethod.POST,RequestMethod.GET})
		public String getDivisionData(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException
		{
		   System.out.println("DIVISION CALLED");
		        String state=request.getParameter("state");
		        String zone=request.getParameter("zone");
		        String circle=request.getParameter("circle");
		        model.put("data", feederdata.getSurveyAllData(model));
			    model.put("StateData", feederdata.getSurveyDataByZone(model, state));
			    model.put("zonedata", feederdata.getSurveyDataByCircle(model, zone,state));
		        model.put("circleData", feederdata.getFeederData(model,state,zone,circle));
		        model.put("zone", zone);
		        model.put("state", state);
		        model.put("circle", circle);
		    return "surveycount";
		} 
	 
	 @RequestMapping(value="/getSubDivisionData",method={RequestMethod.POST,RequestMethod.GET})
		public String getSubDivisionData(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException
		{
		        String state=request.getParameter("state");
		        String zone=request.getParameter("zone");
		        String circle=request.getParameter("circle");
		        String division=request.getParameter("division");
		        model.put("data", feederdata.getSurveyAllData(model));
			    model.put("StateData", feederdata.getSurveyDataByZone(model, state));
			    model.put("zonedata", feederdata.getSurveyDataByCircle(model, zone,state));
		        model.put("circleData", feederdata.getFeederData(model,state,zone,circle));
		        model.put("divisionData", feederdata.getSubDivisionData(model,state,zone,circle,division));
		        model.put("zone", zone);
		        model.put("state", state);
		        model.put("circle", circle);
		    return "surveycount";
		} 
	
	 @RequestMapping(value="/getViewOnImageMtrData/{mtrNumber}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object getViewOnImageMtrData(@PathVariable String mtrNumber,HttpServletRequest request,ModelMap model)
		{
			BCITSLogger.logger.info("In getViewOnImageMtrData ==>"+mtrNumber);
			//String date=hhbm_DownloadService.getDate4(new Date());
			String date="22-08-2017";
			return feederdata.getViewOnImageMtrData(request,mtrNumber,model);
			
		}
	 @RequestMapping(value="/imageDisplay/getImage/{mtrNumber}",method=RequestMethod.GET)
		public void getViewOnMapMtrImage(ModelMap model, HttpServletRequest request,HttpServletResponse response,@PathVariable String mtrNumber) throws IOException
		{
			BCITSLogger.logger.info("In getViewOnMapMtrImage===>"+mtrNumber);
			feederdata.findOnlyImage(model,request,response,mtrNumber);
	    	
		}
	 
}
