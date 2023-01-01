package com.bcits.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.service.ReliabilityIndicesService;

	@Controller
	public class ReliabilityIndicesController {
		@Autowired 
		private FeederDetailsService feederdetailsservice;
		
		@Autowired
		private ReliabilityIndicesService reliabilityIndicesService;
		
		@Autowired
		private FeederMasterService feederService;
		
		@RequestMapping(value = "/reliabilityIndicesFeeder", method = {RequestMethod.POST, RequestMethod.GET })
		public String reliabilityIndicesFeeder(HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap model){
	
			List<?> circleList=feederdetailsservice.getcircle();
			model.addAttribute("circleList" , circleList);
			return "reliabilityIndicesFeeder";
	}
		
		/*@RequestMapping(value = "/reliabilityIndicesDT", method = {RequestMethod.POST, RequestMethod.GET })
		public String reliabilityIndicesDT(HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap model){
	
			List<?> circleList=feederdetailsservice.getcircle();
			model.addAttribute("circleList" , circleList);
			return "reliabilityIndicesDT";
	}
	*/	
		@RequestMapping(value = "/reliabilityIndicesDT", method = {RequestMethod.POST, RequestMethod.GET })
		public String reliabilityIndicesDT(HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap model){
	
			
			List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
			model.addAttribute("zoneList", zoneList);
			/*List<?> circleList=feederdetailsservice.getcircle();
			model.addAttribute("circleList" , circleList);*/
			return "saifiSaidiDT";
	}

		@RequestMapping(value = "/getSubstation/{subDivision}", method = { RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody Object getsubdivdetails(@PathVariable("subDivision") String subDivision,HttpServletRequest request) 
		{
		    return feederdetailsservice.getSSnameBySubdiv(subDivision);
		}
		
	/*	@RequestMapping(value = "/getSubstation", method = {RequestMethod.POST, RequestMethod.GET })
		public String getSubstation(HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap model){
	
			List<?> circleList=feederdetailsservice.getcircle();
			model.addAttribute("circleList" , circleList);
			return "reliabilityIndices";
	}	*/
	
	//	Below is to fetch data for SAIFI SAIDI CAIFI AND MAIFI for Feeder  Meters.
		@RequestMapping(value = "/getMultipleFeederWiseData", method = {RequestMethod.POST, RequestMethod.GET})
		public @ResponseBody Object getMultipleFeederWiseData(@RequestParam("feederCode[]") String[] feederMultiple,HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap model){
			
		String fromDate=request.getParameter("fromdate");
		String toDate=request.getParameter("todate");
		String subDiv=request.getParameter("subdiv");
		//String feederNo=request.getParameter("feeder");
		String subStation=request.getParameter("subStation");
		String reportType=request.getParameter("reportPeriod");
		
	/*String[] receivedData ="{F0066-FEEDERTEST, F0069-FEEDER MAIN, F0070-FEEDERTEST]" ;
		for(String test: receivedData){
			
		}
		*/
			List<Object[]> list = null ;
			try {
				list = (List<Object[]>) reliabilityIndicesService.getReliabalityMultipleFeederData(fromDate, feederMultiple,subStation);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
			
		}
		
		/* +"('test','test1','test2')"+*/

		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/getFeederWiseData", method = {RequestMethod.POST, RequestMethod.GET})
		public @ResponseBody Object getFeederWiseData(HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap model){
			
		String fromDate=request.getParameter("fromdate");
		String toDate=request.getParameter("todate");
		String subDiv=request.getParameter("subdiv");
		String town=request.getParameter("town");
		String feederNo=request.getParameter("feederCode");
		String subStation=request.getParameter("subStation");
		String reportType=request.getParameter("reportPeriod");
		
		List<Object[]> list = null ;
			try {
				list = (List<Object[]>) reliabilityIndicesService.getReliabalitySingleFeederData(fromDate, toDate, feederNo,subStation );
			} catch (Exception e) {
				e.printStackTrace();
			}
		return list;
			
		}
		
		//Below is to fetch data for SAIFI SAIDI CAIFI AND MAIFI for DT  Meters.

		@RequestMapping(value = "/getMultipleDTwiseData", method = {RequestMethod.POST, RequestMethod.GET})
		public @ResponseBody Object getMultipleDTWiseData(@RequestParam("feederCode[]") String[] DTNo,HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap model){
			
		String fromDate=request.getParameter("fromdate");
		String toDate=request.getParameter("todate");
		String subDiv=request.getParameter("subdiv");
		//String feederNo=request.getParameter("feeder");
		String subStation=request.getParameter("subStation");
		String reportType=request.getParameter("reportPeriod");
		
		String FinalValue ;
			
			List<Object[]> list = null ;
			try {
				list = (List<Object[]>) reliabilityIndicesService.getReliabalityMultipleDTData(fromDate, toDate, DTNo, subStation);
			} catch (Exception e) {
				e.printStackTrace();
			}
	return list;
			
		}

		@RequestMapping(value = "/getDTWiseData", method = {RequestMethod.POST, RequestMethod.GET})
		public @ResponseBody Object getDTWiseData(HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap model){
			
		String fromDate=request.getParameter("fromdate");
		String toDate=request.getParameter("todate");
		String subDiv=request.getParameter("subdiv");
		String DTNo=request.getParameter("feederCode");
		String subStation=request.getParameter("subStation");
		String reportType=request.getParameter("reportPeriod");
		
			List<Object[]> list = null ;
			try {
				list = (List<Object[]>) reliabilityIndicesService.getReliabalitySingleDTData(fromDate, toDate, DTNo,subStation);
			} catch (Exception e) {
				e.printStackTrace();
			}
	return list;
			
		}
		
		@RequestMapping(value="/getSaidiSaifiReportDT",method= {RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object getSaidiSaifiReport(HttpServletRequest request) 
		{
	   	String townfeeder=request.getParameter("townfeeder");
	   	String reportRange=request.getParameter("reportRange");
	   	String zone=request.getParameter("zone");
	   	String circle =request.getParameter("circle ");
	   	String town=request.getParameter("town");
	   	String monthyr=request.getParameter("monthyr");
	   	String dt=request.getParameter("dt");
	   	String yearid=request.getParameter("yearid");
	    System.setProperty("server.connection-timeout","300000"); 
	    
	   	String current_monthyear=new SimpleDateFormat("yyyyMM").format(new Date());
	   
	   	
	  
		List<?> res = reliabilityIndicesService.getSaifiSaidiDataDT(townfeeder,town, monthyr, dt);
	   	
	   	
	   	return res;
	   	
	   	
		}
	//pdf	
		@RequestMapping(value="/ReliabilityIndicesDTSummary",method= {RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody void getSaidiSaifiReportPDF(HttpServletRequest request,HttpServletResponse response) 
		{
	   	String townfeeder=request.getParameter("townfeeder");
	   	String reportRange=request.getParameter("reportRange");
	   	String zone=request.getParameter("zne");
	   	String circle =request.getParameter("cir");
	   	String town=request.getParameter("twn");
	   	String monthyr=request.getParameter("monthyr");
	   	String dt=request.getParameter("feeder");
	   	String yearid=request.getParameter("yearid");
	   	String townname=request.getParameter("townname1");
	   	String feedername=request.getParameter("feedername");
        String zne="",cir="",twn="",townname1="";
		
		
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="ALL";
		}else {
			zne=zone;
		}
		//System.err.println("hii");
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="ALL";
		}else {
			cir=circle;
		}
		//System.err.println("hiiii");
		if(town.equalsIgnoreCase("ALL"))
		{
			twn="ALL";
		}else {
			twn=town;
		}
		if(townname.equalsIgnoreCase("ALL"))
		{
			townname1="%";
		}else {
			townname1=townname;
		}

	   	String current_monthyear=new SimpleDateFormat("yyyyMM").format(new Date());
	   
	   	
	  
	  reliabilityIndicesService.getSaidiSaifiReportPDF(townfeeder,  twn,zne,cir, monthyr, dt,townname1,feedername,request,response);
	   	
	   	
	  
	   	
	   	
		}
		
		
		
		
		
}