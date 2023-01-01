package com.bcits.controller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.CDFData;
import com.bcits.entity.Master;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.service.AnalyzedReportService;
import com.bcits.service.CdfDataService;
import com.bcits.service.MasterService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.RdngMonthService;
import com.bcits.service.SdoJccService;
import com.ibm.icu.util.Calendar;

@Controller
public class AnalyzedReportController {

	
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private SdoJccService sdoJccService;
	
	@Autowired
	private AnalyzedReportService analyzedReportService;
	
	@Autowired
	private RdngMonthService rdngMonthService;
	
	@Autowired
	private CdfDataService cdfDataService;
	
	@Autowired
	private MeterMasterService 	meterMasterService;
	
	@Autowired
	private FeederMasterService feederService;
	
	
	@Autowired
	private FeederDetailsService feederdetailsservice;
	
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager entityManager;
	
	@RequestMapping(value="/analyzedReports",method={RequestMethod.GET,RequestMethod.POST})
	public String analysedMethod(HttpServletRequest request,ModelMap model)
	{
		System.out.println("--------Inside analyzed method--------");
		
		model.put("readingMonth",new SimpleDateFormat("yyyyMM").format(new Date()));
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		//model.addAttribute("MNP",masterService.getAllMNP());
		return "analyzedReport";
		
		
	}

	
	@RequestMapping(value="/analysedReportData",method={RequestMethod.GET,RequestMethod.POST})
	public String analysedRecords(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		//String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		
		
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		/*System.out.println("-----In CMRI List Report----"+circle+" "+rdngMonth+" "+mnp+" "+reportName);*/
		System.out.println("-----In CMRI List Report----"+circle+" "+rdngMonth+"  "+reportName);
		model.addAttribute("sdoNAME", sdoname);
		//model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		
		List<?> cmriList=null;
		if(reportName.equalsIgnoreCase("CMRILIST"))
		{
			 cmriList=analyzedReportService.getCmriListData(circle,rdngMonth,sdoname);
		}
		if(cmriList.size() > 0)
		{
			model.addAttribute("cmriList",cmriList);
			model.addAttribute("cmriDataShow","cmriDataShow");
			model.addAttribute("sdoNAME", sdoname);
			//model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("cmrierror","CMRI List Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
	
	@RequestMapping(value="/analysedReportgetSdoName",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List analysedReportgetSdoName(HttpServletRequest request,ModelMap model)
	{
		//System.out.println("come inside analysed reports");
		String circle=request.getParameter("circle");
		List result=sdoJccService.getSdoNameForAnaysedReport(circle);
		return result;
		
	
	}
	
	@RequestMapping(value="/analysedReportgetMnp",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List getMnp(HttpServletRequest request,ModelMap model)
	{
		//System.out.println("come inside analysed reports");
		String circle=request.getParameter("circle");
		String sdoName=request.getParameter("sdoId");
		List result=masterService.getAllMNPOnSdoNames(sdoName,circle);
		return result;
	
	}
	

	@RequestMapping(value="/analysedCNPReport",method={RequestMethod.GET,RequestMethod.POST})
	public String analysedCnpRecords(HttpServletRequest request,ModelMap model)
	{	   
		System.out.println("In CNP report---------------");
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In CNP Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> cnpList=null;
		if(reportName.equalsIgnoreCase("CNP"))
		{
			cnpList=analyzedReportService.getCNPListData(circle,rdngMonth,sdoname);
		}
		if(cnpList.size() > 0)
		{
			model.addAttribute("cnpList",cnpList);
			model.addAttribute("cnpListDataShow","cnpListDataShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("cnperror","CNP Report Data Not Found...");
		}
		return "analyzedReport";
	}

	@RequestMapping(value="/MISXML",method={RequestMethod.GET,RequestMethod.POST})
	public String XmlReading(HttpServletRequest request,ModelMap model)
	{
		System.out.println("--------Inside XmlReading method--------");
		
		int rdngMonth1=rdngMonthService.findAll();
		model.put("readingMonthMip",rdngMonth1);
		
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		return "XMLReading";
	}

	
	/*----------------------- DateWise Report----------------------------------*/
	

	@RequestMapping(value="/analysedDateWiseReport",method={RequestMethod.GET,RequestMethod.POST})
	public String analysedDatewiseRecords(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		/*System.out.println("---In Datewise Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);*/
		System.out.println("---In Datewise Report------"+circle+" "+rdngMonth+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> dateWiseList=null;
		if(reportName.equalsIgnoreCase("DATEWISEREPORT"))
		{
			dateWiseList=analyzedReportService.getDateWiseListData(circle,rdngMonth,sdoname);
		}
		if(dateWiseList.size() > 0)
		{
			model.addAttribute("dateWiseList",dateWiseList);
			model.addAttribute("dateWiseDataShow","dateWiseDataShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("dateWiseError","DateWise Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
	
	
/*----------------------- Defective Report----------------------------------*/
	
	@RequestMapping(value="/analysedDefectiveReport",method={RequestMethod.GET,RequestMethod.POST})
	public String defectiveReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In Defective Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> defectiveList=null;
		if(reportName.equalsIgnoreCase("DEFECTIVE"))
		{
			defectiveList=analyzedReportService.getDefectiveListData(circle,rdngMonth,sdoname);
		}
		if(defectiveList.size() > 0)
		{
			model.addAttribute("defectiveList",defectiveList);
			model.addAttribute("defectiveReportShow","defectiveReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("defectiveReportError","Defective Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
/*----------------------- EnergyWise Report----------------------------------*/
	
	@RequestMapping(value="/analysedEnergyWiseReport",method={RequestMethod.GET,RequestMethod.POST})
	public String energyReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In EnergyWise Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		
		List<?> energyList=null;
		if(reportName.equalsIgnoreCase("ENERGYWISEREPORT"))
		{
			energyList=analyzedReportService.getEnergyWiseListData(circle,rdngMonth,sdoname);
		}
		if(energyList.size() > 0)
		{
			model.addAttribute("energyListList",energyList);
			model.addAttribute("energyReportShow","energyReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("energyReportError","EnergyWise Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
/*----------------------- EventWise Report----------------------------------*/
	
	@RequestMapping(value="/analysedEventWiseReport",method={RequestMethod.GET,RequestMethod.POST})
	public String eventReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In EventWise Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> eventList=null;
		if(reportName.equalsIgnoreCase("EVENTWISEREPORT"))
		{
			eventList=analyzedReportService.getEventWiseListData(circle,rdngMonth,sdoname);
		}
		if(eventList.size() > 0)
		{
			model.addAttribute("eventList",eventList);
			model.addAttribute("eventReportShow","eventReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("eventReportError","EventWise Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
/*----------------------- Faulty Report----------------------------------*/
	
	@RequestMapping(value="/analysedFaultyReport",method={RequestMethod.GET,RequestMethod.POST})
	public String faultyReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In FAULTY Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> faultyList=null;
		if(reportName.equalsIgnoreCase("FAULTY"))
		{
			faultyList=analyzedReportService.getFaultyListData(circle,rdngMonth,sdoname);
		}
		if(faultyList.size() > 0)
		{
			model.addAttribute("faultyList",faultyList);
			model.addAttribute("faultyReportShow","faultyReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("faultyReportError","Faulty Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
/*----------------------- Index Usage Report----------------------------------*/
	
	@RequestMapping(value="/analysedIndexUsageReport",method={RequestMethod.GET,RequestMethod.POST})
	public String indexUsageReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In USAGEINDEXREPORT Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> indexList=null;
		if(reportName.equalsIgnoreCase("USAGEINDEXREPORT"))
		{
			indexList=analyzedReportService.getIndexUsageListData(circle,rdngMonth,sdoname);
		}
		if(indexList.size() > 0)
		{
			model.addAttribute("indexList",indexList);
			model.addAttribute("indexUsageReportShow","indexUsageReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("indexUsageReportError","Index Usage Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
/*----------------------- LOAD UTILIZATION Usage Report----------------------------------*/
	
	@RequestMapping(value="/analysedLoadUtilizationReport",method={RequestMethod.GET,RequestMethod.POST})
	public String loadUtilization(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In LOADUTILIZATIONREPORT Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> loadList=null;
		if(reportName.equalsIgnoreCase("LOADUTILIZATIONREPORT"))
		{
			loadList=analyzedReportService.getLoadUtilizationData(circle,rdngMonth,sdoname);
		}
		if(loadList.size() > 0)
		{
			model.addAttribute("loadList",loadList);
			model.addAttribute("loadUtilizeReportShow","loadUtilizeReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("loadUtilizeReportError","Load Utilization Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
	
/*----------------------- Manual Report----------------------------------*/
	
	@RequestMapping(value="/analysedManualReport",method={RequestMethod.GET,RequestMethod.POST})
	public String manualReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In MANUAL Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		
		List<?> manualList=null;
		if(reportName.equalsIgnoreCase("MANUAL"))
		{
			manualList=analyzedReportService.getManualReportData(circle,rdngMonth,sdoname);
		}
		if(manualList.size() > 0)
		{
			model.addAttribute("manualList",manualList);
			model.addAttribute("manualReportShow","manualReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("manualReportError","Manual Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
/*----------------------- OtherMake Report----------------------------------*/
	
	@RequestMapping(value="/analysedOtherMakeReport",method={RequestMethod.GET,RequestMethod.POST})
	public String otherMakeReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In OTHERMAKE Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> otherList=null;
		if(reportName.equalsIgnoreCase("OTHERMAKE"))
		{
			otherList=analyzedReportService.getOtherMakeReportData(circle,rdngMonth,sdoname);
		}
		if(otherList.size() > 0)
		{
			model.addAttribute("otherList",otherList);
			model.addAttribute("otherMakeReportShow","otherMakeReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("otherMakeReportError","OtherMake Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
/*----------------------- Power Factor Report----------------------------------*/
	
	@RequestMapping(value="/analysedPowerFactorReport",method={RequestMethod.GET,RequestMethod.POST})
	public String powerFactor(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In PowerFactor Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> pfList=null;
		if(reportName.equalsIgnoreCase("POWERFACTORREPORT"))
		{
			pfList=analyzedReportService.getPowerFactorData(circle,rdngMonth,sdoname);
			System.out.println(pfList.size());
		}
		if(pfList.size() > 0)
		{
			
			model.addAttribute("pfList",pfList);
			model.addAttribute("pfReportShow","pfReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("pfReportError","OtherMake Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
/*----------------------- Meter Change Report----------------------------------*/
	
	@RequestMapping(value="/analysedMeterChangeReport",method={RequestMethod.GET,RequestMethod.POST})
	public String meterChange(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In Meterchange Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> meterList=null;
		if(reportName.equalsIgnoreCase("METERCHANGE"))
		{
			meterList=analyzedReportService.getMeterChangeData(circle,rdngMonth,sdoname);
		}
		if(meterList.size() > 0)
		{
			model.addAttribute("meterList",meterList);
			model.addAttribute("meterChangeReportShow","meterChangeReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("meterChangeReportError","MeterChange Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
	
/*----------------------- Static Class Report----------------------------------*/
	
	@RequestMapping(value="/analysedStaticClassReport",method={RequestMethod.GET,RequestMethod.POST})
	public String staticClass(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In STATICCLASS Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> staticList=null;
		if(reportName.equalsIgnoreCase("STATICMETERCLASSREPORT"))
		{
			staticList=analyzedReportService.getStaticClassData(circle,rdngMonth,sdoname);
		}
		if(staticList.size() > 0)
		{
			model.addAttribute("staticList",staticList);
			model.addAttribute("staticClassReportShow","staticClassReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("staticReportError","Static Class Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
	
/*----------------------- Tamper Event Report----------------------------------*/
	@RequestMapping(value="/analysedTamperReport",method={RequestMethod.GET,RequestMethod.POST})
	public String tamperReportss(HttpServletRequest request,ModelMap model){
		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		return "tamperReport";
		
	}
	@RequestMapping(value="/analysedDataTamperReport",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object tamperReport(HttpServletRequest request,ModelMap model)
	{
		//String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("subdiv");
		String townCode = request.getParameter("townCode");
		String rdngmnth = request.getParameter("rdngmnth");
		
		List<?> tamperList=null;
		
			tamperList=analyzedReportService.getTamperReportData(circle,rdngmnth,subdiv,division,townCode);
		
		return tamperList;
	}
	/**
	 * Tamper History Report
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/analysedTamperHistoryReport",method={RequestMethod.GET,RequestMethod.POST})
	public String tamperHistoryReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In TAMPEREVENTREPORT Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> tamperList=null;
		if(reportName.equalsIgnoreCase("TAMPEREVENTSUMMARY"))
		{
			tamperList=analyzedReportService.getTamperHistoryReport(circle,rdngMonth,sdoname);
		}
		if(tamperList.size() > 0)
		{
			model.addAttribute("tamperHistoryList",tamperList);
			model.addAttribute("tamperHistoryReportShow","tamperReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("tamperHistoryReportError","Tamper Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
	/*----------------------- Tamper Event Summary Report----------------------------------*/	
	@RequestMapping(value="/TamperEventSummaryReport",method={RequestMethod.GET,RequestMethod.POST})
	public String TamperEventSummaryReport(HttpServletRequest request,ModelMap model){
		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.addAttribute("zoneList",zoneList);
		return "TamperEventSummaryReport";
		
	}
	
	
	
	@RequestMapping(value="/TamperSummaryReport",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object TamperSummary(HttpServletRequest request,ModelMap model)
	{
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("sdocode");
		String townCode = request.getParameter("townCode");
		String rdngmnth = request.getParameter("rdngmnth");
		if(subdiv.equalsIgnoreCase("All")){
			subdiv="%";
		}
		List<?> tamperList=null;
			tamperList=analyzedReportService.getTamperSummaryData(circle,division,rdngmnth,subdiv,townCode);
		return tamperList;
	}
	
	
	
/*----------------------- Transaction Report----------------------------------*/
	
	@RequestMapping(value="/analysedTransactionReport",method={RequestMethod.GET,RequestMethod.POST})
	public String transactionReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In TRANSACTIONREPORT Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> transList=null;
		if(reportName.equalsIgnoreCase("TRANSACTIONREPORT"))
		{
			transList=analyzedReportService.getTransactionReportData(circle,rdngMonth,sdoname);
		}
		if(transList.size() > 0)
		{
			model.addAttribute("transList",transList);
			model.addAttribute("transactionReportShow","transactionReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{ 
			model.addAttribute("transactionReportError","Tansaction Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	
	
/*----------------------- Wiring Verification Report----------------------------------*/
	
	@RequestMapping(value="/analysedWiringReport",method={RequestMethod.GET,RequestMethod.POST})
	public String wiringReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String mnp=request.getParameter("mnp");
		String sdoname=request.getParameter("sdoId");
		String reportName=request.getParameter("reportName");
		model.put("readingMonth",rdngMonthService.findAll());
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		model.addAttribute("MNP",masterService.getAllMNP());
		System.out.println("---In WIRING VERIFICATIONREPORT Report------"+circle+" "+rdngMonth+" "+mnp+" "+reportName+"sdoname=="+sdoname);
		
		model.addAttribute("sdoNAME", sdoname);
		model.addAttribute("mnpVal", mnp);
		model.addAttribute("report_name", reportName);
		
		List<?> wireList=null;
		if(reportName.equalsIgnoreCase("WIRINGVERIFICATIONREPORT"))
		{
			wireList=analyzedReportService.getWiringReportData(circle,rdngMonth,sdoname);
		}
		if(wireList.size() > 0)
		{
			model.addAttribute("wireList",wireList);
			model.addAttribute("wiringReportShow","wiringReportShow");
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("mnpVal", mnp);
			model.addAttribute("report_name", reportName);
			model.addAttribute("rdngmonth_1", rdngMonth);
		}else{
			model.addAttribute("wiringReportError","Wiring Verification Report Data Not Found...");
		}
		return "analyzedReport";
	}
	
	

	@RequestMapping(value="/XmlReadingReport",method={RequestMethod.GET,RequestMethod.POST})
	public  String XmlReadingReport(HttpServletRequest request,ModelMap model)
	{
		String circle=request.getParameter("circleId");
		String rdngMonth=request.getParameter("reportFromDate");
		String sdoname=request.getParameter("sdoId");
		
		/*int rdngMonth1=rdngMonthService.findAll();
		model.put("readingMonthMip",rdngMonth1);*/
		
		model.put("readingMonthMip",rdngMonth);
		
		
		
		System.out.println("circle-->"+circle+" rdngMonth=="+rdngMonth+" sdoname=="+sdoname);
		
		
		
		model.addAttribute("circle",masterService.getAllCircleforMisReport());
		List<?> cmriList=null;
		
		cmriList=analyzedReportService.getMISXMLReport(circle,rdngMonth,sdoname);
		
		if(cmriList.size() > 0)
		{
			model.addAttribute("cmriList",cmriList);
			model.addAttribute("cmriData","cmriData");
			model.addAttribute("sdoName", sdoname);
			
			model.put("rdng_Month",rdngMonth);
			model.addAttribute("result","dataFound");
			
			
		}else{
			model.addAttribute("cmrierror","MIP,SIP,NDS Reading Data Not Found...");
			
			
		}
		return "XMLReading";
	}
	
	@RequestMapping(value="/downloadExcelMIP",method={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody String downloadCMRI(HttpServletRequest request,HttpServletResponse response)
    {
    	System.out.println("calling download excel ");
		
		try{
			 String fileName = "Report";
            XSSFWorkbook wb = new XSSFWorkbook();
             XSSFSheet sheet = wb.createSheet(fileName) ;
             XSSFRow header  = sheet.createRow(0);   
             
            
          
             /* password required for locks to become effective */
          /*  sheet.protectSheet("bsmartjvvnl");*/
         
             /* cell style for locking */
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
              /*cell style for editable cells */
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
             
             header.createCell(0).setCellValue("CIRCLE");
             header.createCell(1).setCellValue("SDOCODE");
             header.createCell(2).setCellValue("SDONAME");
             header.createCell(3).setCellValue("Category");
             header.createCell(4).setCellValue("ACCNO");
             header.createCell(5).setCellValue("METRNO");
             header.createCell(6).setCellValue("remark");
             header.createCell(7).setCellValue("CMRI_KWH");
             header.createCell(8).setCellValue("CMRI_KVA");
             header.createCell(9).setCellValue("CMRI_KVAH");
             header.createCell(10).setCellValue("CMRI_PF");
             header.createCell(11).setCellValue("Manual_KWH");
             header.createCell(12).setCellValue("Manual_KVAH");
             header.createCell(13).setCellValue("Manual_KVA");
             header.createCell(14).setCellValue("Manual_PF");
             header.createCell(15).setCellValue("Name and Address");
           
            
             List<?> cmriList=null;
     		
     		cmriList=analyzedReportService.getMISXMLReport("TONK","201806","%");
           
             
             int count = 1;
             for(Iterator<?> iterator=cmriList.iterator();iterator.hasNext();){
      	       final Object[] values=(Object[]) iterator.next();
      		
      		XSSFRow row = sheet.createRow(count);
      		
      		if(values[0]==null)
      		{
      			row.createCell(0).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(0).setCellValue(String.valueOf(values[0]));
      		}
      		if(values[1]==null)
      		{
      			row.createCell(1).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      			row.createCell(1).setCellValue(String.valueOf(values[1]));
      		}
      		if(values[2]==null)
      		{
      			row.createCell(2).setCellValue(String.valueOf(""));
      			sheet.setColumnWidth(2, 40);
      		}
      		else
      		{
      			row.createCell(2).setCellValue(String.valueOf(values[2]));
      		}
      		if(values[3]==null)
      		{
      			row.createCell(3).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      			row.createCell(3).setCellValue(String.valueOf(values[3]));
      		}
      		if(values[4]==null)
      		{
      			row.createCell(4).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      			row.createCell(4).setCellValue(String.valueOf(values[4]));
      		}
      		
      		if(values[5]==null)
      		{
      			row.createCell(5).setCellValue(String.valueOf(""));
      		}
      		
      		else
      		{
      		row.createCell(5).setCellValue(String.valueOf(values[5]));
      		}
      		
      		if(values[6]==null)
      		{
      			row.createCell(6).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(6).setCellValue(String.valueOf(values[6]));
      		}
      		
      		
      		if(values[7]!=null)
      		{
      			row.createCell(7).setCellValue(String.valueOf(values[7]));
      			row.createCell(8).setCellValue(String.valueOf(values[8]));
      			row.createCell(9).setCellValue(String.valueOf(values[9]));
      			
      		}
      		
      		
      		
      		if(values[8]==null)
      		{
      			row.createCell(8).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(8).setCellValue(String.valueOf(values[8]));
      		}
      		if(values[9]==null)
      		{
      			row.createCell(9).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(9).setCellValue(String.valueOf(values[9]));
      		}
      		if(values[10]==null)
      		{
      			row.createCell(10).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(10).setCellValue(String.valueOf(values[10]));
      		}
      		if(values[11]==null)
      		{
      			row.createCell(11).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(11).setCellValue(String.valueOf(values[11]));
      		}
      		if(values[12]==null)
      		{
      			row.createCell(12).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(12).setCellValue(String.valueOf(values[12]));
      		}
      		if(values[13]==null)
      		{
      			row.createCell(13).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(13).setCellValue(String.valueOf(values[13]));
      		}
      		if(values[14]==null)
      		{
      			row.createCell(14).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(14).setCellValue(String.valueOf(values[14]));
      		}
      		if(values[15]==null)
      		{
      			row.createCell(15).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(15).setCellValue(String.valueOf(values[15]));
      		}
      		if(values[16]==null)
      		{
      			row.createCell(16).setCellValue(String.valueOf(""));
      		}
      		else
      		{
      		row.createCell(16).setCellValue(String.valueOf(values[16]));
      		}
      		
      		count ++;
      	}
      	
      	FileOutputStream fileOut = new FileOutputStream(fileName);    	
      	wb.write(fileOut);
      	fileOut.flush();
      	fileOut.close();
          
          ServletOutputStream servletOutputStream;

      	servletOutputStream = response.getOutputStream();
      	response.setContentType("application/vnd.ms-excel");
      	response.setHeader("Content-Disposition", "inline;filename=\"MIP_SIPandNDS.xlsx"+"\"");
      	FileInputStream input = new FileInputStream(fileName);
      	IOUtils.copy(input, servletOutputStream);
      	//servletOutputStream.w
      	servletOutputStream.flush();
      	servletOutputStream.close();
      		
      		
      	return null;
      		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
    	
    }
	
	
	@RequestMapping(value="/downloadLOadSurveyExcel",method={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody String downloadLOadSurveyExcel(HttpServletRequest request,HttpServletResponse response)
    {
    	System.out.println("calling download excel for load survey ");
    	
    	System.out.println();
    	
    	System.out.println(request.getParameter("metrno"));
    	System.out.println(request.getParameter("Billmonth"));
    	
		String billmonth=request.getParameter("Billmonth");
		String metrno=request.getParameter("metrno");
		
		try{
           
             
			 String fileName = "Report";
            XSSFWorkbook wb = new XSSFWorkbook();
             XSSFSheet sheet = wb.createSheet(fileName) ;
             XSSFRow header  = sheet.createRow(0);   
             
            
          
             /* password required for locks to become effective */
          /*  sheet.protectSheet("bsmartjvvnl");*/
         
             /* cell style for locking */
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
              /*cell style for editable cells */
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
             
             header.createCell(0).setCellValue("METERNO");
             header.createCell(1).setCellValue("DAY-PROFILE-DATE");
             header.createCell(2).setCellValue("IP-INTERVAL");
             header.createCell(3).setCellValue("KVA");
             header.createCell(4).setCellValue("KWH");
             header.createCell(5).setCellValue("PF");
            
            
             List<?> cmriList=null;
             
             List<CDFData> data=null;
             
             
             int cdfid=0;
             int month=Integer.parseInt(billmonth);
             
             data = cdfDataService.findAll(metrno, month);
             System.out.println("before cmrilist=="+cmriList);
             if(data.size()>0){
            	 
            	 cdfid=data.get(0).getId();
            	 System.out.println("cdfid in excel-->"+cdfid);
            	 
     		 cmriList=analyzedReportService.ExcelForLoadSurvey(metrno,billmonth);
     		 
             
             }
             System.out.println("after cmrilist=="+cmriList);
             
             
//             System.out.println("before cmrilist=="+cmriList);
             
//             cmriList=analyzedReportService.ExcelForLoadSurvey(metrno,billmonth);
             
//             System.out.println("after cmrilist=="+cmriList);
             
             if(cmriList!=null)
             {
            	 int count = 1;
                 for(Iterator<?> iterator=cmriList.iterator();iterator.hasNext();){
          	       final Object[] values=(Object[]) iterator.next();
          		
          		XSSFRow row = sheet.createRow(count);
          		
          		if(values[0]==null)
          		{
          			row.createCell(0).setCellValue(String.valueOf(""));
          		}
          		else
          		{
          		row.createCell(0).setCellValue(String.valueOf(values[0]));
          		}
          		if(values[1]==null)
          		{
          			row.createCell(1).setCellValue(String.valueOf(""));
          		}
          		else
          		{
          			row.createCell(1).setCellValue(String.valueOf(values[1]));
          		}
          		if(values[2]==null)
          		{
          			row.createCell(2).setCellValue(String.valueOf(""));
          			sheet.setColumnWidth(2, 40);
          		}
          		else
          		{
          			row.createCell(2).setCellValue(String.valueOf(values[2]));
          		}
          		if(values[3]==null)
          		{
          			row.createCell(3).setCellValue(String.valueOf(""));
          		}
          		else
          		{
          			row.createCell(3).setCellValue(String.valueOf(values[3]));
          		}
          		if(values[4]==null)
          		{
          			row.createCell(4).setCellValue(String.valueOf(""));
          		}
          		else
          		{
          			row.createCell(4).setCellValue(String.valueOf(values[4]));
          		}
          		
          		if(values[5]==null)
          		{
          			row.createCell(5).setCellValue(String.valueOf(""));
          		}
          		
          		else
          		{
          		row.createCell(5).setCellValue(String.valueOf(values[5]));
          		}
          		
          		
          		
          		count ++;
          	}
             }
      	
      	FileOutputStream fileOut = new FileOutputStream(fileName);    	
      	wb.write(fileOut);
      	fileOut.flush();
      	fileOut.close();
          
          ServletOutputStream servletOutputStream;

      	servletOutputStream = response.getOutputStream();
      	response.setContentType("application/vnd.ms-excel");
      	response.setHeader("Content-Disposition", "inline;filename=\""+metrno+"LoadSurvey.xlsx"+"\"");
      	FileInputStream input = new FileInputStream(fileName);
      	IOUtils.copy(input, servletOutputStream);
      	//servletOutputStream.w
      	servletOutputStream.flush();
      	servletOutputStream.close();
      		
      		
      	return null;
      		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
    	
    }
   
	// Addedd by Vijayalaxmi
	
	
	//@RequestMapping(value="/mipSipRptExportToExcel/{rdngMonth}/{circle}/{sdoname}",method={RequestMethod.GET,RequestMethod.POST})
	@RequestMapping(value="/mipSipRptExportToExcel",method={RequestMethod.GET,RequestMethod.POST})
	public String mipSipExportMethod(HttpServletResponse response,HttpServletRequest req) throws ParseException, IOException
	{
	
	System.out.println("===================In MIP,SIP Report Export Method==============");
	String rdngMonth=req.getParameter("rdngMonth");
	String circle=req.getParameter("circle");
	String sdoname=req.getParameter("sdoname");
	System.out.println("In Export To Excel rdngMonth-----"+rdngMonth+"circle----"+circle+"sdoname====>"+sdoname);
	String fileName = "HtReading.xlsx";
	List<?> mipList=analyzedReportService.getMISXMLReport(circle,rdngMonth,sdoname);
	System.out.println("MIPList size =========>>>"+mipList.size());
      
	String sheetName = "Templates";//name of sheet

	XSSFWorkbook wb = new XSSFWorkbook();
	XSSFSheet sheet = wb.createSheet(sheetName) ;
	
	XSSFRow header = sheet.createRow(0);    	
	
	XSSFCellStyle style = wb.createCellStyle();
	style.setWrapText(true);
	XSSFFont font = wb.createFont();
	font.setFontName(HSSFFont.FONT_ARIAL);
	font.setFontHeightInPoints((short)10);
	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	style.setFont(font);
	
	
	header.createCell(0).setCellValue("CIRCLE");
	header.createCell(1).setCellValue("SDOCODE");
	header.createCell(2).setCellValue("SDONAME");
	header.createCell(3).setCellValue("CATEGORY");
    header.createCell(4).setCellValue("ACCNO");
    header.createCell(5).setCellValue("METRNO");
    header.createCell(6).setCellValue("REMARK");
    header.createCell(7).setCellValue("KWH");
    header.createCell(8).setCellValue("KVAH");
    header.createCell(9).setCellValue("KVA");
    header.createCell(10).setCellValue("PF");
    header.createCell(11).setCellValue("NAME AND ADDRESS");
  
    for(int j = 0; j<=11; j++){
		header.getCell(j).setCellStyle(style);
        sheet.setColumnWidth(j, 8000); 
        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q200"));
    }
    
    int count = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm ");
    for(Iterator<?> iterator=mipList.iterator();iterator.hasNext();){
	       final Object[] values=(Object[]) iterator.next();
		
		XSSFRow row = sheet.createRow(count);
		
		row.createCell(0).setCellValue((String)values[0]);
		row.createCell(1).setCellValue(String.valueOf(values[1]));
		row.createCell(2).setCellValue((String)values[2]);
		row.createCell(3).setCellValue((String)values[3]);
		row.createCell(4).setCellValue((String)values[4]);
		row.createCell(5).setCellValue((String)values[5]);
		//row.createCell(6).setCellValue((String)values[6]);
		
		

		if(values[7]!=null )
		{
			row.createCell(6).setCellValue("OK");
		}
		else
		{
			row.createCell(6).setCellValue((String)values[6]);
		}
		
	
		if( (values[7]!="" && values[7]!=null) )
		{
			
			row.createCell(7).setCellValue(String.valueOf(values[7]));
			
			if(values[8]==null)
			{
				row.createCell(8).setCellValue("");
			}
			else
			{
			row.createCell(8).setCellValue(String.valueOf(values[8]));
			}
			if(values[9]==null)
			{
				row.createCell(9).setCellValue("");
			}
			else
			{
			row.createCell(9).setCellValue(String.valueOf(values[9]));
			}
			if(values[10]==null)
			{
				row.createCell(10).setCellValue("");
			}
			else
			{
			row.createCell(10).setCellValue(String.valueOf(values[10]));
			}
			
		}else
		{
			if(values[16] !=null)
			{
				if( (values[16].toString().equalsIgnoreCase("99999999")) || (values[16].toString().equalsIgnoreCase("9999999")) || (values[16].toString().equalsIgnoreCase("999999"))   )
				{
					row.createCell(7).setCellValue("");
				}
				else
				{
					row.createCell(7).setCellValue(String.valueOf(values[16]));
				}
			}
			
			if(values[17] !=null)
			{
				if(  (values[17].toString().equalsIgnoreCase("99999999")) || (values[17].toString().equalsIgnoreCase("9999999")) || (values[17].toString().equalsIgnoreCase("999999")) )
				{
					row.createCell(8).setCellValue("");
				}
				else
				{
					row.createCell(8).setCellValue(String.valueOf(values[17]));
				}
			}
			
			
			if(values[18] !=null)
			{
				if(  (values[18].toString().equalsIgnoreCase("99999999")) || (values[18].toString().equalsIgnoreCase("9999999")) || (values[18].toString().equalsIgnoreCase("999999")) )
				{
					row.createCell(9).setCellValue("");
				}
				else
				{
					row.createCell(9).setCellValue(String.valueOf(values[18]));
				}
			}

			
			if(values[19] !=null)
			{
				if(  (values[19].toString().equalsIgnoreCase("99999999")) || (values[19].toString().equalsIgnoreCase("9999999")) || (values[19].toString().equalsIgnoreCase("999999")) )
				{
					row.createCell(10).setCellValue("");
				}
				else
				{
					row.createCell(10).setCellValue(String.valueOf(values[19]));
				}
			}
		}
		
		row.createCell(11).setCellValue((String)values[15]);
		
		count ++;
	}
	
	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	wb.write(fileOut);
	fileOut.flush();
	fileOut.close();
    
    ServletOutputStream servletOutputStream;

	servletOutputStream = response.getOutputStream();
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "inline;filename=\"MipSipNdsReport.xlsx"+"\"");
	FileInputStream input = new FileInputStream(fileName);
	IOUtils.copy(input, servletOutputStream);
	//servletOutputStream.w
	servletOutputStream.flush();
	servletOutputStream.close();
		
		return null;
	}
	
// Vijayalaxmi
	
	/*---------------------  Manual Report For Getting Image-------------------------*/
	
	 @RequestMapping(value="/imageDisplay/getImageForManualReading/{rdngMonth}/{accNo}",method=RequestMethod.GET)
	   	public void getImageDTCSLD(ModelMap model, HttpServletRequest request,HttpServletResponse response,@PathVariable String rdngMonth,@PathVariable String accNo) throws IOException
	   	{
		 System.out.println("In Get Image Method==   rdngMonth=="+rdngMonth +"accNo== "+accNo);
		 analyzedReportService.findOnlyImage4(model,request,response,rdngMonth,accNo);
	   	}

	 
	 /*---------------------  Defective Report For Getting Image-------------------------*/
		
	 @RequestMapping(value="/imageDisplay/getImageForDeffective/{rdngMonth}/{accNo}",method=RequestMethod.GET)
	   	public void getImageDefective(ModelMap model, HttpServletRequest request,HttpServletResponse response,@PathVariable String rdngMonth,@PathVariable String accNo) throws IOException
	   	{
		 System.out.println("In Get Image Method==   rdngMonth=="+rdngMonth +"accNo== "+accNo);
		 analyzedReportService.findOnlyImageDefective(model,request,response,rdngMonth,accNo);
	   	}

	 @RequestMapping(value="/consumerConsumption",method={RequestMethod.GET,RequestMethod.POST})
		public  String consumerConsumption(HttpServletRequest request,ModelMap model)
		{
		 
		 model.addAttribute("parts",masterService.getAllParts());
		 model.put("categories",masterService.getAllCategory()); 
		 	model.put("readingMonth",rdngMonthService.findAll());
			model.addAttribute("circle",masterService.getAllCircleforMisReport());
			return  "consumerConsumption";
		}
	 
	 @RequestMapping(value=" /consumerConsumptionanalysis",method={RequestMethod.GET,RequestMethod.POST})
		public  String  consumerConsumptiondashboard(HttpServletRequest request,ModelMap model)
		{
		 
		 List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
			model.addAttribute("zoneList", zoneList);
			model.addAttribute("results", "notDisplay");
		 
		 
			return  "consumerConsumptionAnalysis";
		}
	
	 @RequestMapping(value="/getConsumerConsumption",method={RequestMethod.GET,RequestMethod.POST})
		public  String getConsumerConsumption(HttpServletRequest request,ModelMap model)
		{
		 
		 	String circle=request.getParameter("circleId");
			String rdngMonth=request.getParameter("reportFromDate");
			String part=request.getParameter("partId");
			String category=request.getParameter("categoreyID");
			model.addAttribute("parts",masterService.getAllParts());
			 model.put("categories",masterService.getAllCategory()); 
		 	model.put("readingMonth",rdngMonthService.findAll());
			model.addAttribute("circle",masterService.getAllCircleforMisReport());
			 
			List<?> consumerList=null;
			consumerList=analyzedReportService.getconsumerConsumptionData(circle, rdngMonth, category, part);
			model.addAttribute("consumerList",consumerList);
		 
			return  "consumerConsumption";
		}
	 
	 @RequestMapping(value="/consumptionCurveMDM",method={RequestMethod.GET,RequestMethod.POST})
		public  String consumptionCurve(HttpServletRequest request,ModelMap model)
		{
		 List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		 return  "consumptionCurveMDM";
		}
	 @RequestMapping(value="/consumptionCurveMDAS",method={RequestMethod.GET,RequestMethod.POST})
		public  String consumptionCurveMDAS(HttpServletRequest request,ModelMap model)
		{
		 String qry = "select DISTINCT metrno from meter_data.metermaster";
		 List<?> metrList = null;
		 try {
			metrList =  entityManager.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
		e.printStackTrace();
		
		}
		 
		 model.addAttribute("meterNoList" , metrList);
		 return  "consumptionCurveMDAS";
		}
	 @RequestMapping(value="/getConsumptionCurveMDM",method={RequestMethod.GET,RequestMethod.POST})
		public  String getConsumptionCurveMDM(HttpServletRequest request,ModelMap model)
		{
		 	String billMonth=request.getParameter("reportFromDate");
		 	String meterNo=request.getParameter("meterNo");
		 	model.addAttribute("d4LoadDataMDM",masterService.getd4LoadDataMDM(billMonth,meterNo));
		 	model.addAttribute("meterNo",meterNo);
			model.addAttribute("formatDate",billMonth);
		 	return  "consumptionCurveMDM";
		}
	 @RequestMapping(value="/getConsumptionCurveMDAS/{kno}/{date}",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody  Object getConsumptionCurveMDAS(HttpServletRequest request,ModelMap model,@PathVariable("kno") String mtrno,@PathVariable("date") String date,HttpSession session)
		{
		 String officeType = (String) session.getAttribute("officeType");
		 String officeCode = (String) session.getAttribute("officeCode");
			System.out.println("This is the session value for Offive type #######" +officeType );
		// System.out.println("meter"+mtrno);
		 	//String date=request.getParameter("reportFromDate");
			//String mtrno=request.getParameter("meterNo");
			String qry = "select DISTINCT metrno from meter_data.metermaster";
			List<?> metrList = null;
			try {
				metrList = entityManager.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();

		}

		model.addAttribute("meterNoList" , metrList);
			model.addAttribute("d4LoadDataMDAS",masterService.getd4LoadDataMDAS(date, mtrno));
			List< ?> li=  masterService.getd4LoadDataMDAS(date, mtrno);
			
		        System.out.println("date"+date+"mtrno"+mtrno);
			model.addAttribute("meterNo",mtrno);
			model.addAttribute("formatDate",date);
			
		 	//return  "consumptionCurveMDAS";
			return li;
		}
	 @RequestMapping(value ="/getAjaxConsumptionCurveMDAS/{meterNo}/{profilDate}",  method ={RequestMethod.POST,RequestMethod.GET })
		public @ResponseBody List<Map<String, String>> getAjaxConsumptionCurveMDAS(@PathVariable String meterNo, @PathVariable String profilDate,ModelMap model, HttpServletRequest request){
		 List<Map<String, String>> finalresult=new ArrayList<>();
		
			Map<String, String> map=null;
			List<Object[]> resultList=null;
			resultList = (List<Object[]>) masterService.getd4LoadDataCurveMDAS(profilDate, meterNo);
			if(!(resultList.isEmpty())){
			for (Object[] objects : resultList) {
				map=new HashMap<>();
				//get
				map.put("IpInterval", objects[0]+"");
				map.put("kwhValue", objects[1]+"");
				map.put("pfValue", 1+"");
				finalresult.add(map);
			}
		 
	 }
	return finalresult;}
	 
	 
	 @RequestMapping(value ="/getAjaxConsumptionCurveMDM/{meterNo}/{billMonth}",  method ={RequestMethod.POST,RequestMethod.GET })
		public @ResponseBody List<Map<String, String>> getAjaxConsumptionCurveMDM(@PathVariable String meterNo, @PathVariable String billMonth,ModelMap model, HttpServletRequest request){
		 List<Map<String, String>> finalresult=new ArrayList<>();
		
			Map<String, String> map=null;
			List<Object[]> resultList=null;
			resultList = (List<Object[]>) masterService.getd4LoadDataCurveMDAM(billMonth, meterNo);
			if(!(resultList.isEmpty())){
				String yearmon=null;
			       String s1 = null;
			       List<Integer> l=new ArrayList<>();
			       int i=1;
			for (Object[] objects : resultList) {
				 s1=objects[1].toString();
				 String[] si=s1.split("-");
				 l.add(Integer.parseInt(si[2]));
				 if(Integer.parseInt(si[2])!=i){
					 for(int j=i;j<Integer.parseInt(si[2]);j++){
		 				 map=new HashMap<>();
							
							if(i<=9){
								map.put("IpInterval",si[0]+"-"+si[1]+"-0"+i+"");
								map.put("kwhValue", "0"+"");
								
							}
							else{
								map.put("IpInterval",si[0]+"-"+si[1]+"-"+i+"");
								map.put("kwhValue", "0"+"");
								
							}
							
							finalresult.add(map);
							i++; 
					 }
					 
				 }
				 if(Integer.parseInt(si[2])==i){
					 map=new HashMap<>();
						
						
						map.put("IpInterval", objects[1]+"");
						map.put("kwhValue", objects[0]+"");
						finalresult.add(map);
						i++;
				 }
			}
	 }
	return finalresult;
	}
	 
	 
	 /*----------------------- Daily Average Report----------------------------------*/
		
		@RequestMapping(value="/analysedDailyAvg",method={RequestMethod.GET,RequestMethod.POST})
		public String rtcReport(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
		{System.out.println("===inside rtcReport===");
		
		List<Master> circlelist=meterMasterService.getCirclesByZone("All");
		model.put("circleList", circlelist);
		
			return "dailyAvgLoadReport";
		}
		
		
		
		@RequestMapping(value="/dailyAvgLoadList",method={RequestMethod.GET,RequestMethod.POST})
		public String dailyAvgLoadList(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
		{
			System.out.println("Inside Daily Avge List");
			String selectedDateName=request.getParameter("selectedDateName");
			String circle=request.getParameter("circle");
			String div=request.getParameter("division");
			String subdivision=request.getParameter("sdoCode");
			String division= div.trim();
			
			List<Master> circlelist=meterMasterService.getCirclesByZone("All");
			List result=analyzedReportService.findALLDateWiseAvgLOad(selectedDateName, circle, division, subdivision);
			model.put("circleList", circlelist);
			
			model.put("DatewiseLoad", result);
			model.put("month",selectedDateName);
			return "dailyAvgLoadReport";
		}
	 //SAIDI report
		@RequestMapping(value = "/saidiReport", method = RequestMethod.GET)
		public String saidiReport(ModelMap model, HttpServletRequest request) {
			
			int readingMonth=rdngMonthService.findAll();
			
			model.put("readingMonth", readingMonth);
			String sql="SELECT \"count\"(*), SUM (diff),(SELECT \"count\"(*) from meter_data.master_main WHERE accno is not NULL) as customer_count FROM\n" +
					"(\n" +
					"SELECT meter_number, \"min\"(power_off_duration), \"max\"(power_off_duration), \n" +
					"(\"max\"(power_off_duration)- \"min\"(power_off_duration)) as diff\n" +
					"FROM meter_data.amiinstantaneous WHERE to_char(read_time, 'YYYYMM')='"+readingMonth+"' GROUP BY meter_number \n" +
					"ORDER BY (\"max\"(power_off_duration)- \"min\"(power_off_duration)) DESC\n" +
					")A ";
			System.out.println("saidi-->"+sql);
			
			List<?> list = entityManager.createNativeQuery(sql).getResultList();
			
			Object[] obj=(Object[]) list.get(0);
			
			String duration=String.valueOf(obj[1]);
			String customers=String.valueOf(obj[2]);
			
			double saidi=Double.parseDouble(duration)/Double.parseDouble(customers);
			
			Double du=Double.parseDouble(duration);
			
			duration=getDurationString(du.intValue());
			
			String saidiS=getDurationString((int)saidi);
			
			model.addAttribute("duration",duration);
			model.addAttribute("customers", customers);
			model.addAttribute("saidi", saidiS);
			model.addAttribute("DataAvailable","DataAvailable");
			return "saidiReport";
		}
		
		
		@RequestMapping(value = "/saidiReportMonthwise", method = {RequestMethod.GET,RequestMethod.POST})
		public String saidiReportMonthwise(ModelMap model, HttpServletRequest request) {
			
			System.out.println("inside saidi report month wise");
			String readingMonth=request.getParameter("reportFromDate");
			
			model.put("readingMonth", readingMonth);
			String sql="SELECT B.* FROM\n" +
					"(SELECT \"count\"(*), SUM (diff),(SELECT \"count\"(*) from meter_data.master_main WHERE accno IS NOT NULL) as customer_count FROM\n" +
					"(\n" +
					"SELECT meter_number, \"min\"(power_off_duration), \"max\"(power_off_duration), \n" +
					"(\"max\"(power_off_duration)- \"min\"(power_off_duration)) as diff\n" +
					"FROM meter_data.amiinstantaneous WHERE to_char(read_time, 'YYYYMM')='"+readingMonth+"' GROUP BY meter_number \n" +
					"ORDER BY (\"max\"(power_off_duration)- \"min\"(power_off_duration)) DESC\n" +
					")A)B  WHERE SUM IS NOT NULL";
			System.out.println("saidi in month wise-->"+sql);
			
			List<?> list = entityManager.createNativeQuery(sql).getResultList();
			System.out.println("list-->"+list);
			
			if(list!=null && list.size()>0)
			{	
				Object[] obj=(Object[]) list.get(0);
				
				String duration=String.valueOf(obj[1]);
				String customers=String.valueOf(obj[2]);
				
				double saidi=Double.parseDouble(duration)/Double.parseDouble(customers);
				
				Double du=Double.parseDouble(duration);
				
				duration=getDurationString(du.intValue());
				
				String saidiS=getDurationString((int)saidi);
				
				model.addAttribute("duration",duration);
				model.addAttribute("customers", customers);
				model.addAttribute("saidi", saidiS);
				model.addAttribute("DataAvailable","DataAvailable");
				
			}
			else
			{
				model.addAttribute("DataNotAvailable","Data Not Available");
			}
			
			return "saidiReport";
		}
		
		
		
		private String getDurationString(int seconds) {

		    int hours = seconds / 3600;
		    int minutes = (seconds % 3600) / 60;
		    seconds = seconds % 60;
 
		    
		    return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
		}
		private String twoDigitString(int number) {

		    if (number == 0) {
		        return "00";
		    }
		    if (number / 10 == 0) {
		        return "0" + number;
		    }
		    return String.valueOf(number);
		}
		
		/*Missing billing data day wise meters*/
		@RequestMapping(value="/dateWiseMissingMetrsList",method={RequestMethod.GET,RequestMethod.POST})
		public String dateWiseMissingMetrsList(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
		{
		System.out.println("===inside dateWiseMissingBillData===");
		List<Master> circlelist=meterMasterService.getCirclesByZone("All");
		model.put("circleList", circlelist);
		
			return "missingMtrsInBill";
		}
		
		@RequestMapping(value="/missingMetrsListResult",method={RequestMethod.GET,RequestMethod.POST})
		public String missingMetrsListResult(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
		{
			System.out.println("Inside missingMetrsListResult  List");
			String selectedDateName=request.getParameter("selectedDateName");
			String circle=request.getParameter("circle");
			String div=request.getParameter("division");
			String subdivision=request.getParameter("sdoCode");
			String division= div.trim();
			
			List<Master> circlelist=meterMasterService.getCirclesByZone("All");
			List result=analyzedReportService.findAllMissingMtrsInBillData(selectedDateName, circle, division, subdivision);
			model.put("circleList", circlelist);
			model.put("DatewiseLoad", result);
			model.put("month",selectedDateName);
			return "missingMtrsInBill";
		}
		
		//sateesh
		@RequestMapping(value="/usageIndexingReport",method={RequestMethod.GET,RequestMethod.POST})
		public String usageIndexingReport(HttpServletRequest request,ModelMap model)
		{
		  try 
		  {
			    String rdngMonth=request.getParameter("reportFromDate");
				String mnp=request.getParameter("mnp");
				String sdoname=request.getParameter("sdoId");
				String reportname=request.getParameter("reportName");
				model.put("readingMonth",rdngMonthService.findAll());
				model.addAttribute("circle",masterService.getAllCircleforMisReport());
				model.addAttribute("sdoNAME", sdoname);
				model.addAttribute("mnpVal", mnp);
				model.addAttribute("report_name", reportname);
				model.addAttribute("rdngmonth_1", rdngMonth);
				
				SimpleDateFormat date=new SimpleDateFormat("yyyyMM");
				SimpleDateFormat sdf = new SimpleDateFormat("mm");

				Calendar calendar = Calendar.getInstance();
				String currdate=date.format(new Date());
				if(currdate.equalsIgnoreCase(rdngMonth))
				{
				calendar.setTime(new Date());
		        }
				else
				{
				  calendar.setTime(date.parse(rdngMonth));
				}
				
				int numDays=0;
				if(currdate.equalsIgnoreCase(rdngMonth))
				{
					numDays=calendar.get(Calendar.DAY_OF_MONTH);
				}
				else
				{
					numDays = calendar.getActualMaximum(Calendar.DATE); 
				}
				long totalHRs=(24*numDays)*60;
				
				List<?> meterData=analyzedReportService.getUsageIndexingReport(request);
				if(meterData.size()>0)
				{
					List<Map<String, Object>> list=new ArrayList<>();
					Map<String, Object> meter=null;
					for(Iterator<?> iterator=meterData.iterator();iterator.hasNext();)
					{
			      	       final Object[] values=(Object[]) iterator.next();
			      	        meter=new HashMap<>();
			      	        String offTime=values[7]+"";
			      	        long totaloffTime=Long.parseLong(offTime);
			      	        
			      	        double totalOnHrs=(double)(totalHRs-totaloffTime)/60;
			      	        int totalOnHrs2=(int)totalOnHrs;
			      	        double totalOnHrsDecimal=totalOnHrs-totalOnHrs2;
			      	        double totaloffTime2=0;
			      	        double totaloffDecimal=0;
			      	          int totaloff=0;
			      	        if(totaloffTime>59)
			      	        {
			      	        	totaloffTime2=(double)totaloffTime/60;
			      	        	totaloff=(int)totaloffTime2;
			      	        	totaloffDecimal=totaloffTime2-totaloff;
			      	        }
			      	        
				      		 meter.put("circle", values[0]+"");
				      		 meter.put("division", values[1]+"");
				      		 meter.put("subdivision", values[2]+"");
				      		 meter.put("name", values[3]+"");
				      		 meter.put("address", values[4]+"");
				      		 meter.put("accno", values[5]+"");
				      		 meter.put("meterno", values[6]+"");
				      		 meter.put("totalhrs", totalHRs/60);
				      		 meter.put("rdngMonth",rdngMonth );
				      		 meter.put("powerOn", totalOnHrs2+"."+Math.round(totalOnHrsDecimal*60));
				      		 if(totaloffTime>59)
				      		 {
				      			meter.put("powerOff", totaloff+"."+Math.round(totaloffDecimal*60));
				      		 }
				      		 else
				      		 {
				      			meter.put("powerOff", 0+"."+totaloffTime);
				      			 
				      		 }
				      		 list.add(meter);
			      	}
					model.addAttribute("usageIndexedData1", list);
					model.addAttribute("usageIndex", "usageIndex");
				}
				else
				{
					model.addAttribute("result", "Data is not Available");	
				}
				
				
	      } catch (Exception e) 
	      {
			e.printStackTrace();
		  }
			
		 return "analyzedReport";
		}
	 
		
		/*----------------------- EnergyWise Report----------------------------------*/
		
		@RequestMapping(value="/analysedEnergyWiseReportAMI",method={RequestMethod.GET,RequestMethod.POST})
		public String analysedEnergyWiseReportAMI(HttpServletRequest request,ModelMap model)
		{
			String circle=request.getParameter("circleId");
			String rdngMonth=request.getParameter("reportFromDate");
			String sdoname=request.getParameter("sdoId");
			String reportName=request.getParameter("reportName");
			model.put("readingMonth",rdngMonthService.findAll());
			model.addAttribute("circle",masterService.getAllCircleforMisReport());
			
			model.addAttribute("sdoNAME", sdoname);
			model.addAttribute("report_name", reportName);
			
			
			List<?> energyList=null;
			if(reportName.equalsIgnoreCase("ENERGYWISEREPORT"))
			{
				energyList=analyzedReportService.getEnergyWiseListData(circle,rdngMonth,sdoname);
			}
			if(energyList.size() > 0)
			{
				model.addAttribute("energyListList",energyList);
				model.addAttribute("energyReportShow","energyReportShow");
				
				model.addAttribute("sdoNAME", sdoname);
				model.addAttribute("report_name", reportName);
				model.addAttribute("rdngmonth_1", rdngMonth);
			}else{
				model.addAttribute("energyReportError","EnergyWise Report Data Not Found...");
			}
			return "analyzedReport";
		}
		
		
}

		
		

