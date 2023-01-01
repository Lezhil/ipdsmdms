package com.bcits.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.AlarmHistory;
import com.bcits.entity.Alarms;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.service.AlarmDefinitionService;
import com.bcits.service.AlarmHistoryService;
import com.bcits.service.AlarmService;
import com.bcits.service.ConsumerMasterService;
import com.bcits.utility.MDMLogger;
@Controller
public class AlarmController
{
	@Autowired
	private AlarmHistoryService alarmHstAervice;
	
	@Autowired
	private AlarmService alarmService;

	@Autowired
	private ConsumerMasterService consumermaseteService;
	@Autowired
	private AlarmDefinitionService alarmDefintionService;

	
	 @RequestMapping(value="/viewalarms",method={RequestMethod.POST,RequestMethod.GET})
		public String viewAlarms(HttpServletRequest request,ModelMap model)
		{
			MDMLogger.logger.info("In ::::::::::::::::::::::alarms view");
			return "viewalarms";
		}
	 @RequestMapping(value="/viewallalarms",method={RequestMethod.POST,RequestMethod.GET})
	 public @ResponseBody Object viewAllAlarms(HttpServletRequest request,HttpServletResponse response){
		 List<?> l=new ArrayList<>();
		 //alarmHstAervice.autoacknowledge();
		 
		 l=alarmHstAervice.viewAlarms();
		 //alarmHstAervice.acknowledgeAlarms(1);
		 
		return l;
		 
	 }
	 @RequestMapping(value="/acknowledgealarms",method={RequestMethod.POST,RequestMethod.GET})
	 public @ResponseBody Object acknowledgealarms(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 List<Alarms> list=new ArrayList<>();
		 AlarmHistory alarm_hst=new AlarmHistory();
		 String ack_by=session.getAttribute("username").toString();
		 String msg=request.getParameter("message");
		 String toDelete = request.getParameter("toDelete");
		// if(toDelete.length()!=null||toDelete!="")
			System.out.println(toDelete.toString() + "    " + toDelete.length());
			String[] toBeDelete = toDelete.split(",");
			//alarmHstAervice.autoacknowledge();

			int[] toBeDeleted = new int[toBeDelete.length];
			for (int i = 0; i < toBeDelete.length; i++) {
				toBeDeleted[i]=Integer.parseInt(toBeDelete[i]);
				//System.out.println(msg+ack_by+toBeDeleted[i]);
				list=alarmHstAervice.acknowledgeAlarms(toBeDeleted[i]);
				alarm_hst=alarmHstAervice.saveAlarmInHst(list, msg,ack_by);
				alarmHstAervice.save(alarm_hst);
				alarmService.deleteRecord(toBeDeleted[i]);
				//d5DataService.customSave(alarm_hst);
			}
		 return list;
	 }
	 @RequestMapping(value="/viewalarmshst",method={RequestMethod.POST,RequestMethod.GET})
		public String viewAlarmsHst(HttpServletRequest request,ModelMap model)
		{
		 List<?> circles=new ArrayList<>();
		 List<?> locationType=new ArrayList<>();
		 
		// zones=feederMasterService.getDistinctZone();
		 circles=consumermaseteService.getCircle();
		 locationType=alarmHstAervice.getLocationType();
			MDMLogger.logger.info("In ::::::::::::::::::::::view alarms history"+circles.size());
			model.put("circles", circles);
			model.put("locationtype", locationType);
			
			
			return "viewalarmshistory";
		}
	 @RequestMapping(value="/getalarmshistory",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object getAlarmHst(HttpServletRequest request,HttpServletResponse response)
		{
		 List<?> list=new ArrayList<>();
		 String zone=request.getParameter("zone");
		 String circle=request.getParameter("circle");
		/*
		 * String division=request.getParameter("division"); String
		 * sdocode=request.getParameter("subdiv");
		 */
		 String town=request.getParameter("town");
		 String fromDate = request.getParameter("fromdate");
		 String toDate=request.getParameter("todate");
		 String loctype=request.getParameter("loctype");
		 
		 
		 list=alarmHstAervice.getAlarmHistory(circle,zone,town,fromDate,toDate,loctype);
		 
			return list;
		 
		
		}
	 
	 @RequestMapping(value="/autoacknowledge",method={RequestMethod.POST,RequestMethod.GET})
	 public @ResponseBody Object autoAcknowledge(HttpServletRequest request,HttpServletResponse response)
		{
		 HttpSession session = request.getSession(false);
		 String ack_by=session.getAttribute("username").toString();
		 alarmHstAervice.autoacknowledge(ack_by);
			return response;
		 
		}
	 
	
	 
	 /**
	  * 
	  * ami location zones and circles
	  * 
	  * 
	  */

	 @RequestMapping(value="/showCircleAmi/{zone}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object showCircle(@PathVariable String zone,HttpServletRequest request,ModelMap model)
		{
			//System.out.println("==--showCircle--==List=="+zone);
			return alarmService.getAmiCircleByZone(zone);
		}
	 
	 @RequestMapping(value="/showDivisionAmi/{zone}/{circle}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object showDivision(@PathVariable String zone,@PathVariable String circle,HttpServletRequest request,ModelMap model)
		{
			//System.out.println("==--showDivision--==List=="+zone+"==>>"+circle);
			return alarmService.getAmiDivisionByCircle(zone,circle);
		}
	 @RequestMapping(value="/showSubdivByDivAmi/{zone}/{circle}/{division}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object showSubdivByDiv(@PathVariable String zone,@PathVariable String circle,@PathVariable String division,HttpServletRequest request,ModelMap model)
		{
			System.out.println("==--showSubdivByDiv--==List=="+zone+"==>>"+circle+"==>>"+division);
			return alarmService.getSubdivByDivisionByCircleAmi(zone, circle, division);
		}
	 @RequestMapping(value="/findmeterlocation",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody Object findmtrLocation(@RequestParam String mtrNum,@RequestParam String locationType){
			boolean b=alarmService.findMeterLocation(mtrNum,locationType);
			return b;
	 }
	 
	 @RequestMapping(value="/ViewAlarmsPDF",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody void ViewAlarmsPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
		{
		 alarmHstAervice.getViewAlarmDtlspdf(request, response);
		}
	 
	 @RequestMapping(value="/ViewHistoryAlarmsPDF",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody void ViewHistoryAlarmsPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
		{
		 String zone=request.getParameter("zne");
			String circle=request.getParameter("cir");
			String town=request.getParameter("townn");
			String fromdate=request.getParameter("fromdate");
			String todate=request.getParameter("todate");
			String loctype=request.getParameter("loctype1");
			String townname = request.getParameter("townname");
		
			String zone1="",crcl="",twn="",loctype1="";
			if(zone.equalsIgnoreCase("ALL")) 
			{
				zone1="%";
			}else {
				zone1=zone;
			}
			if(circle.equalsIgnoreCase("ALL")) 
			{
				crcl="%";
			}else {
				crcl=circle;
			}
			if(town.equalsIgnoreCase("ALL"))
			{
				twn="%";
			}else {
				twn=town;
			}
			if(loctype.equalsIgnoreCase("ALL"))
			{
				loctype1="%";
			}else {
				loctype1=loctype;
			}
			
		 
		 alarmHstAervice.getViewHistoryAlarmDtlspdf(zone1,crcl,twn,fromdate,todate,loctype1,request, response,townname);
		}
	 @RequestMapping(value="/removealarms",method={RequestMethod.POST,RequestMethod.GET})
		public String removeAlarms(HttpServletRequest request,ModelMap model)
		{
			MDMLogger.logger.info("In ::::::::::::::::::::::alarms view");
			return "alarmRemoval";
		}
	 @RequestMapping(value="/showAccnoDetails",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<String> showAccnoDetails(HttpServletRequest request,ModelMap model,HttpSession session)
		{
			String officeType = (String) session.getAttribute("officeType");
			String officeName = (String) session.getAttribute("officeName");
			
			return alarmService.getAccountDetails(officeType,officeName);
		}
	 @RequestMapping(value="/showMtrnoDetails",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<String> showMtrnoDetails(HttpServletRequest request,ModelMap model,HttpSession session)
		{
			String officeType = (String) session.getAttribute("officeType");
			String officeName = (String) session.getAttribute("officeName");
			
			return alarmService.getMtrnos(officeType,officeName);
		}
	 @RequestMapping(value="/showAlarmDetails",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> showAlarmDetails(HttpServletRequest request,ModelMap model)
		{
			String mtrno=request.getParameter("mtrno");
			String accno=request.getParameter("accno");
			String radioval=request.getParameter("radioval");
			List<?> AlarmDetails=alarmDefintionService.getAlarmDetails(accno,mtrno,radioval);
			return AlarmDetails;
		}
	 @RequestMapping(value="/showAlarmPriorityDetails",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> showAlarmPriorityDetails(HttpServletRequest request,ModelMap model)
		{
		
			String accno=request.getParameter("accnumber");
			List<?> AlarmPriorDetails=alarmDefintionService.getAlarmPriorityDet(accno);
			return AlarmPriorDetails;
		}
	 
	 @RequestMapping(value="/showAlarmPriorityDetailsbyAlarmName",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> showAlarmPriorityDetailsbyAlarmName(HttpServletRequest request,ModelMap model)
		{
		
			String alarmName=request.getParameter("alarmname");
			String locationId=request.getParameter("locationId");
			List<?> AlarmPriorDetails=alarmDefintionService.getAlarmPriorityDetbyAlarmName(alarmName,locationId);
			return AlarmPriorDetails;
		}
		@RequestMapping(value="/alarmRemoveDetails",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String alarmRemoveDetails(HttpServletRequest request,ModelMap model)
		{
		
			String mtrno=request.getParameter("mtrno");
			String accno=request.getParameter("accno");
			String radioval=request.getParameter("radioval");
			 int i=alarmDefintionService.getAlarmRemoveDet(mtrno,accno,radioval);
			  if(i>0) {
				  return "Deleted";
			  }else {
				  return "Failed";
			  }
		}
		 @RequestMapping(value="/getalarmname",method={RequestMethod.POST,RequestMethod.GET})
			public @ResponseBody List<String> getAlarmName(HttpServletRequest request,ModelMap model,HttpSession session)
			{
				String officeType = (String) session.getAttribute("officeType");
				String officeName = (String) session.getAttribute("officeName");
				
				return alarmService.getAlarmName(officeType,officeName);
			}
		 @RequestMapping(value="/showAlarmDetailsbyAlarmname",method={RequestMethod.POST,RequestMethod.GET})
			public @ResponseBody List<?> showAlarmDetailsbyAlarmname(HttpServletRequest request,ModelMap model,HttpSession session)
			{
				String alarmName=request.getParameter("alarmName");
				String officeType = (String) session.getAttribute("officeType");
				String officeName = (String) session.getAttribute("officeName");
				List<?> AlarmDetails=alarmDefintionService.showAlarmDetailsbyAlarmname(alarmName,officeType,officeName);
				return AlarmDetails;
			}
		 @RequestMapping(value="/alarmRemoveDetailsbyAlarmName",method={RequestMethod.POST,RequestMethod.GET})
			public @ResponseBody String alarmRemoveDetailsbyAlarmName(HttpServletRequest request,ModelMap model)
			{
			
				String alarmName=request.getParameter("alarmName");
				String checkboxes=request.getParameter("locationids[]");
				 int i=alarmDefintionService.getalarmRemoveDetailsbyAlarmName(alarmName,checkboxes);
				  if(i>0) {
					  return "Deleted";
				  }else {
					  return "Failed";
				  }
			}
		
}
