package com.bcits.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.SdoJcc;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.service.AssessmentsService;
import com.bcits.service.MasterService;
import com.bcits.service.MeterLifeCycleService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.MrnameService;
import com.bcits.service.RdngMonthService;
import com.bcits.service.ReadingRemarkService;
import com.bcits.service.SdoJccService;
import com.bcits.utility.MDMLogger;

@Controller
public class Reports
{
	@Autowired
	private SdoJccService sdoJccService;
	
	@Autowired
	private MrnameService mrnameService;
	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private ReadingRemarkService readingRemarkService;
	
	@Autowired
	private RdngMonthService rdngMonthService;
	
	@Autowired
	private AssessmentsService assessmentsService;
	
	@Autowired
	private MeterLifeCycleService meterLifeCycleService;
	
	@Autowired
	private AmrInstantaneousService amrinstantaneousservice;
	
	@Autowired
	private MasterMainService mainService;
	
	
	@RequestMapping(value="/otherReports",method={RequestMethod.GET,RequestMethod.POST})
	public String otherReports(HttpServletRequest request,ModelMap model)
	{
		List<SdoJcc> list = sdoJccService.findAll();
		model.put("mrnamrList", masterService.findMr());
		model.addAttribute("sdoList",list);
		model.addAttribute("pendingerror","notDisplay");
		
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		return "Reports";
	}
	
	@RequestMapping(value="/generateOtherReports",method={RequestMethod.GET,RequestMethod.POST})
	public String generateOtherReports(HttpServletRequest request,ModelMap model)
	{
		System.out.println("------generateOtherReports-----------");
		List<SdoJcc> list = sdoJccService.findAll();
		model.addAttribute("sdoList",list);
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		model.put("mrnamrList", masterService.findMr());
		String sdoCode = request.getParameter("sdoValue");
		String level = request.getParameter("levelValue");
		String billmonth = request.getParameter("reportsMonth");
		//String[] sdo = sdoCode.split(",");
		String[] levl = level.split(",");
		String notFoundMsg = "";
		
		for(int i=0;i < levl.length;i++)
		{
			if(levl[i].equalsIgnoreCase("1"))
			{
				List pendingList = sdoJccService.getLevelDetails(sdoCode, levl[i], billmonth);
				if(pendingList.size() > 0)
				{
					model.addAttribute("pendingList",pendingList);
					model.addAttribute("pending","pending");
					model.addAttribute("title","pendingTitle");
					
				}else{
					notFoundMsg = notFoundMsg + "Pending";
				}
				
			}
			else if(levl[i].equalsIgnoreCase("2"))
			{
				List cmriList = sdoJccService.getLevelDetails(sdoCode, levl[i], billmonth);
				if(cmriList.size() > 0)
				{
					model.addAttribute("cmriList",cmriList);
					model.addAttribute("CMRI","CMRI");
					
				}else{
					notFoundMsg = notFoundMsg + "  CMRI ";
				}
				
				
			}
			else if(levl[i].equalsIgnoreCase("3"))
			{
				
				List manualList = sdoJccService.getLevelDetails(sdoCode, levl[i], billmonth);
				if(manualList.size() > 0)
				{
					model.addAttribute("manualList",manualList);
					model.addAttribute("Manual","Manual");
					
				}else{
					notFoundMsg = notFoundMsg + " Manual";
				}
				
			}
			else if(levl[i].equalsIgnoreCase("4"))
			{
				List<Object[]> conCompleteList = sdoJccService.getLevelDetails(sdoCode, levl[i], billmonth);
				if(conCompleteList.size() > 0)
				{
					model.addAttribute("conCompleteList",conCompleteList);
					model.addAttribute("conCompleteListArr",conCompleteList.get(0).length-1);
					model.addAttribute("Consumer_Complete_List","Consumer Complete List");
					
				}else{
					notFoundMsg = notFoundMsg + "  Consumer Complete List ";
				}
				
				
			}
			else if(levl[i].equalsIgnoreCase("5"))
			{
				List<Object[]> mtrChangeList = sdoJccService.getLevelDetails(sdoCode, levl[i], billmonth);
				if(mtrChangeList.size() > 0)
				{
					model.addAttribute("mtrChangeList",mtrChangeList);
					model.addAttribute("mtrChangeListArr",mtrChangeList.get(0).length-1);
					model.addAttribute("Meter_Change_List","Meter Change List");
					
				}else{
					notFoundMsg = notFoundMsg + "  Meter Change List ";
				}
				
				
			}
		}
		if(!notFoundMsg.equalsIgnoreCase(""))
		{
			notFoundMsg = notFoundMsg + " Details Not Found..";
			model.addAttribute("pendingerror",notFoundMsg);
		}else{
			model.addAttribute("pendingerror","notDisplay");
		}
		
		
		System.out.println("----sdoCode : "+sdoCode + " level: "+level);
		return "Reports";
	}


@RequestMapping(value="/generateOtherReportsWithMR",method={RequestMethod.GET,RequestMethod.POST})
public String generateOtherReportsWithMR(HttpServletRequest request,ModelMap model)
{
	System.out.println("------generateOtherReportsWithMR-----------");
	List<SdoJcc> list = sdoJccService.findAll();
	model.addAttribute("sdoList",list);
	model.put("mrnamrList", masterService.findMr());
	List<?> circle=masterService.getALLCircle();
	model.addAttribute("circle",circle);
	
	String sdoCode = request.getParameter("SecondSdocodeId");
	String level = request.getParameter("levelValue");
	String billmonth = request.getParameter("reportsMonth");
	String mrName = request.getParameter("mrName");
	System.out.println("----generateOtherReportsWithMR------sdoCode : "+sdoCode + " level: "+level+"  mrName: "+mrName);
	//String[] sdo = sdoCode.split(",");
	String[] levl = level.split(",");
	String notFoundMsg = "";
	
	for(int i=0;i < levl.length;i++)
	{
		if(levl[i].equalsIgnoreCase("1"))
		{
			List pendingList = sdoJccService.getMRLevelDetails(sdoCode, levl[i], billmonth, mrName);
			if(pendingList.size() > 0)
			{
				model.addAttribute("pendingList",pendingList);
				model.addAttribute("pending","pending");
				model.addAttribute("title","pendingTitle");
				
			}else{
				notFoundMsg = notFoundMsg + "Pending";
			}
			
		}
		else if(levl[i].equalsIgnoreCase("2"))
		{
			List cmriList = sdoJccService.getMRLevelDetails(sdoCode, levl[i], billmonth, mrName);
			if(cmriList.size() > 0)
			{
				model.addAttribute("cmriList",cmriList);
				model.addAttribute("CMRI","CMRI");
				
			}else{
				notFoundMsg = notFoundMsg + "  CMRI ";
			}
			
			
		}
		else  if(levl[i].equalsIgnoreCase("3"))
		{
			
			List manualList = sdoJccService.getMRLevelDetails(sdoCode, levl[i], billmonth, mrName);
			if(manualList.size() > 0)
			{
				model.addAttribute("manualList",manualList);
				model.addAttribute("Manual","Manual");
				
			}else{
				notFoundMsg = notFoundMsg + " Manual";
			}
			
		}
		else if(levl[i].equalsIgnoreCase("4"))
		{
			List<Object[]> conCompleteList = sdoJccService.getMRLevelDetails(sdoCode, levl[i], billmonth, mrName);
			if(conCompleteList.size() > 0)
			{
				model.addAttribute("conCompleteList",conCompleteList);
				model.addAttribute("conCompleteListArr",conCompleteList.get(0).length-1);
				model.addAttribute("Consumer_Complete_List","Consumer Complete List");
				
			}else{
				notFoundMsg = notFoundMsg + "  Consumer Complete List ";
			}
			
			
		}
		else if(levl[i].equalsIgnoreCase("5"))
		{
			List<Object[]> mtrChangeList = sdoJccService.getMRLevelDetails(sdoCode, levl[i], billmonth, mrName);
			if(mtrChangeList.size() > 0)
			{
				model.addAttribute("mtrChangeList",mtrChangeList);
				model.addAttribute("mtrChangeListArr",mtrChangeList.get(0).length-1);
				model.addAttribute("Meter_Change_List","Meter Change List");
				
			}else{
				notFoundMsg = notFoundMsg + "  Meter Change List ";
			}
			
			
		}
	}
	if(!notFoundMsg.equalsIgnoreCase(""))
	{
		notFoundMsg = notFoundMsg + " Details Not Found..";
		model.addAttribute("pendingerror",notFoundMsg);
	}else{
		model.addAttribute("pendingerror","notDisplay");
	}
	
	return "Reports";
}

@RequestMapping(value="/pendingSummary",method={RequestMethod.GET,RequestMethod.POST})
public String pendingSummary(HttpServletRequest request,ModelMap model)
{
	System.out.println("---------------pendingSummary------------");
	String billmonth = request.getParameter("reportsMonth");
	List list = sdoJccService.getPendingSummaryDetails(billmonth,"normal");
	List<SdoJcc> list1 = sdoJccService.findAll();
	model.put("mrnamrList", masterService.findMr());
	List<?> circle=masterService.getALLCircle();
	model.addAttribute("circle",circle);
	
	model.addAttribute("sdoList",list1);
	model.addAttribute("pendingerror","notDisplay");
	model.addAttribute("billmonth",billmonth);
			if(list.size() > 0)
			{
				model.addAttribute("pendingSummaryList",list);
				model.addAttribute("pendingerror","notDisplay");
				model.addAttribute("pendingSummary","pendingSummary");
				model.put("Summarytitle", "Pending Summary Details of "+billmonth);
				
			}else{
				model.addAttribute("pendingerror","Pending Summary Not Found...");
			}
	
	return "Reports";
}

@RequestMapping(value="/newReportConnectionDetails",method={RequestMethod.GET,RequestMethod.POST})
public String newReportConnectionDetails(HttpServletRequest request,ModelMap model)
{
	System.out.println("---------------newReportConnectionDetails------------");
	String billmonth = request.getParameter("reportsMonth");
	List list = sdoJccService.getNewConnectionDetails(billmonth);
	List<SdoJcc> list1 = sdoJccService.findAll();
	model.put("mrnamrList", masterService.findMr());
	List<?> circle=masterService.getALLCircle();
	model.addAttribute("circle",circle);
	model.addAttribute("sdoList",list1);
	model.addAttribute("pendingerror","notDisplay");
	model.addAttribute("billmonth",billmonth);
	if(list.size() > 0)
	{
		model.addAttribute("pendingList",list);
		model.addAttribute("pendingerror","notDisplay");
		model.addAttribute("pending","pending");
		model.addAttribute("title","newConnTitle");
		
	}else{
		model.addAttribute("pendingerror","New Connection Data Not Found...");
	}
	return "Reports";
}
@RequestMapping(value="/newReportHTPending",method={RequestMethod.GET,RequestMethod.POST})
public String newReportHTPending(HttpServletRequest request,ModelMap model)
{
	System.out.println("---------------newReportHTPending------------");
	String billmonth = request.getParameter("reportsMonth");
	String circle=request.getParameter("circle");
	List list = sdoJccService.getHtPendingDetails(billmonth,circle);
	List<SdoJcc> list1 = sdoJccService.findAll();
	model.put("mrnamrList", masterService.findMr());
	
	List<?> circle1=masterService.getALLCircle();
	model.addAttribute("circle",circle1);
	
	List<?> rremark=readingRemarkService.selectReadingRemark();
	model.addAttribute("rremark",rremark);
	
	
	model.addAttribute("sdoList",list1);
	model.addAttribute("pendingerror","notDisplay");
	model.addAttribute("billmonth",billmonth);
	if(list.size() > 0)
	{
		model.addAttribute("HTPendingList",list);
		model.addAttribute("pendingerror","notDisplay");
		model.addAttribute("HTPending","HTPending");
		
	}else{
		model.addAttribute("pendingerror","HT Pending Data Not Found...");
	}
	return "Reports";
}

@RequestMapping(value="/newHtpendingSummary",method={RequestMethod.GET,RequestMethod.POST})
public String newHtpendingSummary(HttpServletRequest request,ModelMap model)
{
	System.out.println("---------------newHtpendingSummary------------");
	String billmonth = request.getParameter("reportsMonth");
	List list = sdoJccService.getPendingSummaryDetails(billmonth,"ht");
	List<SdoJcc> list1 = sdoJccService.findAll();
	model.put("mrnamrList", masterService.findMr());
	List<?> circle1=masterService.getALLCircle();
	model.addAttribute("circle",circle1);
	model.addAttribute("sdoList",list1);
	model.addAttribute("pendingerror","notDisplay");
	model.addAttribute("billmonth",billmonth);
			if(list.size() > 0)
			{
				model.addAttribute("pendingSummaryList",list);
				model.addAttribute("pendingerror","notDisplay");
				model.addAttribute("pendingSummary","pendingSummary");
				model.put("Summarytitle", "HT Pending Summary Details of "+billmonth);
				
			}else{
				model.addAttribute("pendingerror","HT Pending Summary Not Found...");
			}
	
	return "Reports";
}


@RequestMapping(value="/mrWiseReport",method={RequestMethod.GET,RequestMethod.POST})
public String mrWiseReport(@ModelAttribute("meterMasterEntity") MeterMaster meterMaster ,BindingResult result,HttpServletRequest request,ModelMap model)
{
	model.put("rdngmonth",meterMasterService.getMaxRdgMonthYear(request));
	List<?> circle=masterService.getALLCircle();
	model.addAttribute("circle",circle);
	return "mrWiseReport";
	 
}
@RequestMapping(value="/meterReaderWiseSearchReport",method={RequestMethod.GET,RequestMethod.POST})
public String meterReaderWiseSearchReport(@ModelAttribute("meterMasterEntity") MeterMaster meterMaster ,BindingResult result,HttpServletRequest request,ModelMap model)
{

	String circle=request.getParameter("circle");
	System.out.println("circle Name in MR Wise Report ======"+circle);

	model.put("rdngmonth",meterMasterService.getMaxRdgMonthYear(request));
	model.put("result",meterMasterService.getMrWiseReprot(meterMaster, request, model));
	
	model.put("result2",meterMasterService.getMrWiseReprotForReportReading(meterMaster, request, model,circle));
	
	List<?> list =meterMasterService.getMrWiseReprotForReportReading(meterMaster, request, model,circle);
	
	ArrayList<Map<String, Object>> D1result1=new ArrayList<>();
	
	List<Map<String, Object>> resultList=new ArrayList<>();
	List<Map<String, Object>> resultList2=new ArrayList<>();
	Map<String, Object> map=null;
	
	for (Iterator iterator = list.iterator(); iterator.hasNext();)
	{
		final Object[] objects = (Object[]) iterator.next();
		
			String mrname=String.valueOf(objects[0]); 
			String sdoname=String.valueOf(objects[2]); 
			
		    List<?>  result1List=meterMasterService.getMrWiseReprotOnebySdoname(meterMaster, request, model,circle,mrname,sdoname);
			for (Iterator<?> iterator1 = result1List.iterator(); iterator1.hasNext();)
			{
				Map<String, Object> map1=new HashMap<String, Object>();
				final Object[] objects1 = (Object[]) iterator1.next();
				
					String mrname1=(String)(objects1[0]); 
					String reading=(String)(objects1[1]); 
					String count=String.valueOf(objects1[2]); 
					String sdoname1=(String)(objects1[3]); 
					map1.put("mrname", mrname1);
					map1.put("date", reading);
					map1.put("count", count);
					map1.put("sdoname", sdoname1);
					/*System.err.println("mrname-----"+mrname1);
					System.err.println("reading-----"+reading);
					System.err.println("count-----"+count);
					System.err.println("sdoname-----"+sdoname1);*/
					resultList2.add(map1);
			}
	}
	model.put("result1", resultList2);
	model.put("grandTotal",meterMasterService.getGrandTotalForReadingReport(meterMaster, request, model,circle));
	//model.put("result1",meterMasterService.getMrWiseReprotOne(meterMaster, request, model,circle));
	model.put("days", meterMasterService.getDays(meterMaster, request, model,circle));
	//model.put("grandTotal",meterMasterService.getGrandTotalForReadingReport(meterMaster, request, model,circle));
	List<?> circles=masterService.getALLCircle();
	model.addAttribute("circle",circles);
	return "mrWiseReport";
	 
}



@RequestMapping(value = "/getDaywiseMrDetails/{mrname}/{sdoname}/{readingdate}/{circle}/{rdng}", method = {RequestMethod.GET,RequestMethod.POST})
public @ResponseBody  List getdaywise(@PathVariable String mrname,@PathVariable String sdoname,@PathVariable String readingdate,
		@PathVariable String circle,@PathVariable String rdng,
		HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
{
	System.out.println("-------------In DayWise Report-------------------");
	System.out.println("mrname===>"+mrname+"sdoname===>"+sdoname+"readingdate===>"+readingdate);
	System.out.println("circle===>"+circle+"readingMonth===>"+rdng);
	List dayList=null;
	return dayList=meterMasterService.getdayWiseMrDetailsForUserRpt(mrname, sdoname,readingdate,circle,rdng,request,model); 

}



 
@RequestMapping(value = "/getNumberOfInstaltionDetails/{operation}/{sdoName}/{operation1}", method = {RequestMethod.GET,RequestMethod.POST})
public @ResponseBody  List getNumberOfInstaltionDetails(@PathVariable String operation,@PathVariable String operation1,@PathVariable String sdoName,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
{
	
	System.out.println("In add sub div manually  :::::::::Controller "+operation+" "+operation1+" "+sdoName);
	List resultList=null;
	return resultList=meterMasterService.getMrWiseBilledDetails(operation, operation1,sdoName, request, model); 

}

@RequestMapping(value = "/getNumberOfPendingDetails/{operation}/{sdoname}/{operation1}", method = {RequestMethod.GET,RequestMethod.POST})
public @ResponseBody  List getNumberOfPendingDetails(@PathVariable String operation,@PathVariable String sdoname,@PathVariable String operation1,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
{
	System.out.println("operation operation1 sdoname  "+operation+" "+operation1+" "+sdoname);
	List resultList=null;
	return resultList=meterMasterService.getMrWisePendingDetails(operation,sdoname, operation1, request, model);
}

@RequestMapping(value = "/otherExternalReports", method = {RequestMethod.GET,RequestMethod.POST})
public String otherExternalReports(HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
{
	
	return "otherExternalReport";
}

@RequestMapping(value = "/simpleExternaleReport", method = {RequestMethod.GET,RequestMethod.POST})
public String simpleExternaleReport(HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
{
	String condition = request.getParameter("reportName");
	String month = request.getParameter("reportsMonth");
	if(condition.equalsIgnoreCase("1"))
	{
		model.put("defectiveList", meterMasterService.getOtherExternalReportData(condition, month));
	}
	else if(condition.equalsIgnoreCase("2"))
	{
		model.put("cdList", meterMasterService.getOtherExternalReportData(condition, month));
	}
	else if(condition.equalsIgnoreCase("3"))
	{
		model.put("consumptioList", meterMasterService.getOtherExternalReportData(condition, month));
	}
	else if(condition.equalsIgnoreCase("4"))
	{
		model.put("consumptionfalldownlessList", meterMasterService.getOtherExternalReportData(condition, month));
	}
	else if(condition.equalsIgnoreCase("5"))
	{
		model.put("consumptionfalldowaboveList", meterMasterService.getOtherExternalReportData(condition, month));
	}
	
	return "otherExternalReport";
}


	@RequestMapping(value="/showMrDaywise", method = {RequestMethod.GET,RequestMethod.POST})
	public String showMrDaywise(HttpServletRequest request, ModelMap model) 
	{
		String cuurentDate=new SimpleDateFormat("yyyyMM").format(new Date());
		model.put("cuurentDate", cuurentDate);
		model.put("results", "notDisplay");
		return "mrDaywiseReport";
	}
	
	@RequestMapping(value="/showMrDaywiseReport", method = {RequestMethod.GET,RequestMethod.POST})
	public String showMrDaywiseReport(HttpServletRequest request, ModelMap model) 
	{
		model.put("cuurentDate", request.getParameter("fromDate"));
		model.put("mrDaywiseList",meterMasterService.showMrDaywiseReport(request.getParameter("fromDate"),model,request));
		return "mrDaywiseReport";
	}
	
	@RequestMapping(value = "/showAllPendingList/{month}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody  Object showAllPendingList(@PathVariable String month,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		System.out.println("In add sub div manually  :::::::::Controller "+month+" "+month);
		return meterMasterService.getAllPendingList(month, request, model);
	}

	@RequestMapping(value = "/showPendingList/{mrname}/{month}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody  Object showPendingList(@PathVariable String mrname,@PathVariable String month,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		MDMLogger.logger.info("================>sql"+mrname+"==="+month);
		return meterMasterService.getPendingList(mrname, month,request, model);
	}
	
	
	@RequestMapping(value="/dataExport",method={RequestMethod.GET,RequestMethod.POST})
	public String dataExport(HttpServletRequest request,ModelMap model)
	{
		model.put("sdoCodesData", masterService.findSdoCode());
		
		
		/*List<SdoJcc> list = sdoJccService.findAll();
		List<Mrname> mrnameList = mrnameService.findAll();
		model.addAttribute("mrnamrList",mrnameList);
		model.addAttribute("sdoList",list);
		model.addAttribute("pendingerror","notDisplay");*/
		
		return "dataExport";
	}
	
	@RequestMapping(value = "/dataExportView", method = {RequestMethod.GET,RequestMethod.POST})
	public String dataExportView(HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		System.err.println("inside");
		String MrNameEx=request.getParameter("mrname1");
		String sdoCodeEx=request.getParameter("sdocode");
		String tadescEx=request.getParameter("tadesc");
		model.put("sdoCodesData", masterService.findSdoCode());
		System.out.println("MrNameEx=="+MrNameEx);
		System.out.println("sdoCodeEx=="+sdoCodeEx);
		System.out.println("tadescEx=="+tadescEx);
		
		
		
		List<?> data= meterMasterService.getExportData(MrNameEx, sdoCodeEx, tadescEx);
		
		if(data==null)
		{
			System.out.println("data="+data);
			return "redirect:dataExport";
		}
		else{
			model.put("DataExport",data);
			
		}
		
		return "dataExport";
	}
	
	@RequestMapping(value="/rnaReport",method={RequestMethod.GET,RequestMethod.POST})
	public String rnaReport(HttpServletRequest request,ModelMap model)
	{
		List<?> circles=masterService.getALLCircle();
		model.addAttribute("circle",circles);
		
	
		return "rnaReport";
	}
	
	@RequestMapping(value="/generaternaReport",method={RequestMethod.GET,RequestMethod.POST})
	public String generaternaReport(HttpServletRequest request,ModelMap model,HttpServletResponse response)
	{
		String circle=request.getParameter("circle");
		String billmonth=request.getParameter("month");
		String sdocode=request.getParameter("sdocode");
		String sdoname=request.getParameter("sdoname");
		String mrname=request.getParameter("mrname");
		System.out.println("mrname ====> "+mrname);
		List<Object[]> dashDetails=meterMasterService.getRNAdata(billmonth,circle);
		System.out.println(dashDetails);
		model.put("dashDetails",dashDetails);
		List<?> circles=masterService.getALLCircle();
		model.addAttribute("circle",circles);
		
		if(mrname!=null&&!mrname.isEmpty()&&mrname.trim()!="")
		{
			MDMLogger.logger.info("COMING INSIDE /if");
			model.put("key", "Yes");
			model.put("sectionDash",meterMasterService.getrnaReportBasedonMr(circle,billmonth,sdocode,sdoname,mrname,model,request,response));
			model.addAttribute("mrname",mrname);
		}
		else
		{
			MDMLogger.logger.info("COMING INSIDE /Else");
			model.put("key1", "No");
		}
		return "rnaReport";
	}
	
	
	// Added by Vijayalaxmi
	
	@RequestMapping(value="/updateHtManual",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody public String updatemanualMethod(HttpServletRequest request,ModelMap model)
	{
		String billMonth=request.getParameter("billMonth");
		String rdngRemark=request.getParameter("newrdngRemark");
		String accNo=request.getParameter("accNo");
		double currdngkwh=0.0;
		double currdngkvah=0.0;
		double currdngkva=0.0;
		
		String currdngkwh1=request.getParameter("currdngkwh");
		String currdngkvah1=request.getParameter("currdngkvah");
		String currdngkva1=request.getParameter("currdngkva");
		
	  if(currdngkwh1.isEmpty())
	    {
		  currdngkwh=0.0;
	    }
	  else
	  {
		   currdngkwh=Double.parseDouble(currdngkwh1);
	  }
	  
	  if(currdngkvah1.isEmpty())
	    {
		  currdngkvah=0.0;
	    }
	  else
	  {
		  currdngkvah=Double.parseDouble(currdngkvah1);
	  }
	  
	  if(currdngkva1.isEmpty())
	    {
		  currdngkva=0.0;
	    }
	  else
	  {
		  currdngkva=Double.parseDouble(currdngkva1);
	  }
		 
		 
		
		System.out.println("billMonth-----"+billMonth+"rdngRemark----"+rdngRemark+"accNo------"+accNo);
		System.out.println("currdngkwh-----"+currdngkwh+"currdngkvah----"+currdngkvah+"currdngkva------"+currdngkva);
		
		meterMasterService.updateHtMualAndRRemark(billMonth,rdngRemark,accNo,currdngkwh,currdngkvah,currdngkva);
		return "Updated Successfully";
	}

	@RequestMapping(value="/htAbtManualReports",method={RequestMethod.GET,RequestMethod.POST})
	public String htabtMethod(HttpServletRequest request,ModelMap model)
	{
		model.put("readingMonth",rdngMonthService.findAll());
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		return "htAbtManualReport";
	}
	
	
	@RequestMapping(value="/getHtAbtManualData",method={RequestMethod.GET,RequestMethod.POST})
	public String getHtManual(HttpServletRequest request,ModelMap model)
	{
		model.put("readingMonth",rdngMonthService.findAll());
		
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		
		String rdngMonth=request.getParameter("reportsMonth");
		String circle1=request.getParameter("circle");
		System.out.println("In HT Manual rdngMonth-----"+rdngMonth+"circle----"+circle1);
		
		List<?> manualList=sdoJccService.getHtManualDetails(rdngMonth,circle1);
		if(manualList.size()>0)
		{
		model.put("manualList",manualList);
		}
		else{
			model.addAttribute("manualerror","HT Manual Data Not Found...");
		}

		return "htAbtManualReport";
	}
	
	
	@RequestMapping(value="/accNoNotAvailableReport",method={RequestMethod.GET,RequestMethod.POST})
	public String accNoNotAvail(HttpServletRequest request,ModelMap model)
	{
		model.put("readingMonth",rdngMonthService.findAll());
		
		return "accnoNotAvailable";
	}
	
	@RequestMapping(value="/getaccountdetailsUsersRpt",method={RequestMethod.GET,RequestMethod.POST})
	public String getaccountData(HttpServletRequest request,ModelMap model)
	{
		model.put("readingMonth",rdngMonthService.findAll());
		String rdngMonth=request.getParameter("reportsMonth");
		List<?> accntList=sdoJccService.getAccnoNotAvailableData(rdngMonth);
		if(accntList.size() > 0)
		{
			model.put("accntList",accntList);
			model.put("accntListShow","accntListShow");
		}
		else {
			model.put("accntListError","Account Details Not Found...");
		}
		return "accnoNotAvailable";
	}
	
	@RequestMapping(value="/serviceOrderStatus",method={RequestMethod.GET,RequestMethod.POST})
	public String serviceOrderStatus(HttpServletRequest request,ModelMap model)
	{
		List<Object[]> l=meterMasterService.getServiceOrder();
		model.addAttribute("report", l);
		return "serviceOrderStatus";
	}
	
	@RequestMapping(value="/meterLifeCycleData",method={RequestMethod.POST,RequestMethod.GET})
	public  String meterLifeCycleData(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		return "MeterLifeCycle";
	}
	
	@RequestMapping(value="/meterfind",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object meterfind(@RequestParam String mtrno){
		boolean b=mainService.getmetrno(mtrno);
		return b;
	}
	
	
	@RequestMapping(value="/getMeterLifeData",method={RequestMethod.POST,RequestMethod.GET})
	public  @ResponseBody List<?> getMeterLifeData(HttpServletResponse response,HttpServletRequest request,ModelMap model,@RequestParam String mtrno,HttpSession session)
	{
		MasterMainEntity mainEntity=mainService.getEntityByMtrNO(mtrno);
		List<?> meterData=null;
		try {
			String Fdrcategory=mainEntity.getFdrcategory();
			 meterData=assessmentsService.getMeterLifeCycleData(mtrno,"mtrno",session,Fdrcategory);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return meterData;
	}
	//kno
	@RequestMapping(value="/getMeterLifeDataKno",method={RequestMethod.POST,RequestMethod.GET})
	public  @ResponseBody List<?> getMeterLifeDataKno(HttpServletResponse response,HttpServletRequest request,ModelMap model,@RequestParam String mtrno,HttpSession session)
	{
		System.out.println("inside--kno");
		MasterMainEntity mainEntity=mainService.getEntityByMtrNO(mtrno);
		String Fdrcategory=mainEntity.getFdrcategory();
 		List<?> meterData=assessmentsService.getMeterLifeCycleData(mtrno,"kno",session,Fdrcategory);
 		System.err.println(meterData.size());
		return meterData;
	}
	
	@RequestMapping(value="/AssetManagement",method={RequestMethod.POST,RequestMethod.GET})
	public String AssestManagement(HttpServletRequest request,ModelMap model)
	{
		model.put("result", "notDisplay");
		model.put("meterdata", meterLifeCycleService.getData());
		return "assetManagement";
	}
	@RequestMapping(value="/getMeterData",method={RequestMethod.POST,RequestMethod.GET})
	public  @ResponseBody List<MeterMaster> getMeterData(HttpServletResponse response,HttpServletRequest request,ModelMap model,@RequestParam String mtrno)
	{
 		List<MeterMaster> meterData=meterMasterService.getMeterDataByMeterNo(mtrno);;
		return meterData;
	}
	
	@RequestMapping(value="/showMdiExceedRpt",method={RequestMethod.POST,RequestMethod.GET})
	public String showMdiExceedRpt(HttpServletRequest request,ModelMap model)
	{
		try 
		{
			masterService.getAllSubDiv(model,request);
			model.put("subdivisionVal",meterMasterService.getDistinctSubdivision("%","%",request));
			String cuurentDate=new SimpleDateFormat("yyyyMM").format(new Date());
			model.put("cuurentDate", cuurentDate);
			model.put("results", "notDisplay");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "mdiExceedReport";
	}
	
	@RequestMapping(value="/generateMdiExceedRpt",method=RequestMethod.POST)
	public  Object generateSealCardRpt( HttpServletRequest request,ModelMap model,HttpServletResponse response)
	{
		String month=request.getParameter("fromDate");
		String subdiv=request.getParameter("subdivision");
		String categoryVal=request.getParameter("category");
		meterMasterService.generateMdiExceedRpt(month,subdiv,categoryVal,model,response);
		return null;
	}
	
	@RequestMapping(value="/meterAssetManagement",method={RequestMethod.POST,RequestMethod.GET})
	public  String meterAssetManagement(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		return "assetManage";
	}
	
	@RequestMapping(value="/mftrd",method={RequestMethod.POST,RequestMethod.GET})
	public String mftrd(HttpServletResponse response,HttpServletRequest request,ModelMap model) 
	{
		return "360degreeMDM";
	}
	
	@RequestMapping(value="/getMeterDetailsAMI/{mtrNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMeterDetails(@PathVariable String mtrNo,HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		System.out.println("inside getMeterDetails Data");
		List<AmrInstantaneousEntity> meterdata=amrinstantaneousservice.getMeterData(mtrNo);
		return meterdata;
	}
	
}
