package com.bcits.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.service.BusinessRoleService;
import com.bcits.service.NPPDataService;
import com.bcits.service.PfcService;

@Controller
public class PfcReportController {
	@Autowired
	private PfcService pfcservice;
	@Autowired
	BusinessRoleService businessRoleService;
	@Autowired
	private NPPDataService nppDataService;

	@RequestMapping(value="/pfcd7report", method={RequestMethod.POST,RequestMethod.GET})
	public String pfcD7Report(ModelMap model){
		
		List<?> periodList=businessRoleService.getDistinctPeriodD7();
		model.put("periodList", periodList);
		String stateName=businessRoleService.getStateName();
		model.put("stateName", stateName);
		String discomName=businessRoleService.getDiscomName();
		model.put("discomName", discomName);
		return "pfcd7report";
	}
	@RequestMapping(value="/getpfcd7reportdata",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getpfcd7data(@RequestParam String rdngmnth,@RequestParam("town[]") String[] multipletown){
		////System.out.println("in con======="+town);
		
		List<?> list=new ArrayList<>();
		list=pfcservice.getpfcd7Report(rdngmnth,multipletown);
		return list;
		
	}
	
	@RequestMapping(value = "/pfcReportD2", method = { RequestMethod.POST,RequestMethod.GET })
	public String pfcReportD2(ModelMap model, HttpServletRequest requst) {
		
		String States = pfcservice.getState();
		model.addAttribute("States", States);
		
		String Discom = pfcservice.getDiscom();
		model.addAttribute("Discom", Discom);
		
		List<?> Period = pfcservice.getPeriodD2();
		model.addAttribute("Period", Period);
	return "pfcReportD2";
	}
	@RequestMapping(value = "/gettowns/{GovtSchemeId}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List gettowns(@PathVariable String GovtSchemeId,HttpServletRequest request, ModelMap model) {
		
		//System.out.println("GovtSchemeId--" + GovtSchemeId);
		List<?> townsList = pfcservice.gettowns(GovtSchemeId);
		return townsList;
	}
	
	@RequestMapping(value = "/getpfcConnectionData", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getpfcConnectionData(@RequestParam String PeriodId,@RequestParam("TownId[]") String[] TownIdMultiple,HttpServletRequest request, ModelMap model) {
		// @RequestParam("feederCode[]") String[] feederMultiple
		
		List<?> ConnectionData = pfcservice.getpfcConnectionData(PeriodId,TownIdMultiple);
		return ConnectionData;
	}
	
	@RequestMapping(value = "/getPFCreportD1data", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Object getEneryAccoutingDataSubdivisionWise(@RequestParam  String period ,@RequestParam("town[]") String[] multipleTown, HttpServletRequest request)	{

		List<?> ConnectionData = pfcservice.getpfcD1ReportData(period,multipleTown);
		return ConnectionData;
		

	}

	

	@RequestMapping(value="/pfcReportD5",method={RequestMethod.GET,RequestMethod.POST})
	public String pfcReportD5(HttpServletRequest request,ModelMap model) 
	{
		List<?> list=pfcservice.getPeriodD5();
		model.put("periodList",list);
		
		String li=pfcservice.getState();
		model.put("stateName",li);
		
		String ls=pfcservice.getDiscom();
		model.put("discomName",ls);
		
		return "pfcReportD5";
	}
	
	@RequestMapping(value="/pfcd5tableDetails",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> tableDetails(@RequestParam String period,@RequestParam ("town[]") String[] town,HttpServletRequest request,ModelMap model)
	{
		
		List<?> tableList=null;
		try {
			tableList=pfcservice.getTablepfcD5Details(period,town);
			return tableList;
		} catch (Exception e) {
			e.printStackTrace();
        }
		
		////System.out.println("tableList...."+tableList);
		return tableList;
	}
	
	
	@RequestMapping(value="/towndetails/{scheme}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> towndetails(@PathVariable String scheme,HttpServletRequest request,ModelMap model)
	{
//		System.err.println("town..details....");
		List<?> list=null;
		list=pfcservice.getTown(scheme);
		return list;
	}
	
	@RequestMapping(value="/pfcReportD3", method={RequestMethod.POST,RequestMethod.GET})
	public String pfcReportD3(HttpServletRequest request,ModelMap model)
	{
		List<?> list=pfcservice.getPeriodD3();
		model.put("periodlist",list);

		String li=pfcservice.getState();
		model.put("stateName",li);
		
		String ls=pfcservice.getDiscom();
		model.put("discomName",ls);
		
		return "pfcReportD3";
	}
	
	@RequestMapping(value="/ComplaintDetails",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> ComplaintDetails(@RequestParam String period,@RequestParam ("town[]") String[] town,HttpServletRequest request,ModelMap model)
	{
		////System.out.println("Complaint..details..");
		List<?> tableList=null;
		try {
			tableList=pfcservice.getComplaintDetails(period,town);
			return tableList ;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tableList ;
	}
	
	
	/*@RequestMapping(value = "/PFCreportD4", method = { RequestMethod.POST, RequestMethod.GET })
	public String PFCreportD4(@RequestParam String period,@RequestParam ("town[]") String[] town,ModelMap model, HttpServletRequest request) {
		try {
			String li = pfcservice.getState();
			String ls = pfcservice.getDiscom();
			List<?> periodList = pfcservice.getDistinctPeriod();

			model.put("periodList", periodList);
			model.put("stateName", li);
			model.put("discomName", ls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PFCreportD4";
	}
*/
	
	@RequestMapping(value="/NPPReport", method={RequestMethod.POST,RequestMethod.GET})
	public String NPPReport(HttpServletRequest request,ModelMap model)
	{
		List<?> list=pfcservice.getPeriodD3();
		model.put("periodlist",list); 
		
		String li=pfcservice.getState();
		model.put("stateName",li);
		
		String ls=pfcservice.getDiscom();
		model.put("discomName",ls);
		
		return "NPPReport";
	}
	
	@RequestMapping(value="/NPPReportDetails",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> NPPReportDetails(@RequestParam String period,@RequestParam  String town,@RequestParam String scheme,HttpServletRequest request,ModelMap model)
	{
		////System.out.println("NPP..detais..");
		List<?> nppList=null;
		try {
			nppList=pfcservice.getNppReportDetails(period,town,scheme);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return nppList ;
	}	
	
	@RequestMapping(value="/PFCreportD1PDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void PFCreportD1PDF(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String scheme=request.getParameter("scheme");
		String town=request.getParameter("town");
		String period=request.getParameter("period");
		String state=request.getParameter("state");
		String discom=request.getParameter("discom");
		String month=request.getParameter("month");
		String ieperiod=request.getParameter("ieperiod");
		String townname=request.getParameter("townname");
		String[] arrSplit = town.split(",");
		
		String twn="";
		if(town.equalsIgnoreCase("All"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
    	String firsttest =null;
    	String test = null;
    	int size = town.length();
    	String finalString = null;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    }
		
		pfcservice.getPfcreportD1Pdf(request, response, scheme, finalString, period, state, discom, month, ieperiod,townname,twn);
		
	}
	
	@RequestMapping(value="/PFCreportD2PDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void PFCreportD2PDF(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String scheme=request.getParameter("scheme");
		String town=request.getParameter("town");
		String period=request.getParameter("period");
		String state=request.getParameter("state");
		String discom=request.getParameter("discom");
		String month=request.getParameter("month");
		String ieperiod=request.getParameter("ieperiod");
		String townname=request.getParameter("townname");
		String[] arrSplit = town.split(",");
		
		String twn="";
		if(town.equalsIgnoreCase("All"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
    	String firsttest =null;
    	String test = null;
    	int size = town.length();
    	String finalString = null;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    }
		
		pfcservice.getPfcreportD2Pdf(request, response, scheme, finalString, period, state, discom, month, ieperiod,townname,twn);
		
	}
	
	@RequestMapping(value="/PFCreportD3PDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void PFCreportD3PDF(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String scheme=request.getParameter("scheme");
		String town=request.getParameter("town");
		String period=request.getParameter("period");
		String state=request.getParameter("state");
		String discom=request.getParameter("discom");
		String month=request.getParameter("month");
		String ieperiod=request.getParameter("ieperiod");
		String townname=request.getParameter("townname");
		String[] arrSplit = town.split(",");
		
		String twn="";
		if(town.equalsIgnoreCase("All"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
    	String firsttest =null;
    	String test = null;
    	int size = town.length();
    	String finalString = null;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    }
		
		pfcservice.getPfcreportD3Pdf(request, response, scheme, finalString, period, state, discom, month, ieperiod,townname,twn);
		
	}
	
	@RequestMapping(value="/PFCreportD5PDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void PFCreportD5PDF(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String scheme=request.getParameter("scheme");
		String town=request.getParameter("town");
		String period=request.getParameter("period");
		String state=request.getParameter("state");
		String discom=request.getParameter("discom");
		String month=request.getParameter("month");
		String ieperiod=request.getParameter("ieperiod");
		String townname=request.getParameter("townname");
		String[] arrSplit = town.split(",");
		
		String twn="";
		if(town.equalsIgnoreCase("All"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
    	String firsttest =null;
    	String test = null;
    	int size = town.length();
    	String finalString = null;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    }
		
		pfcservice.getPfcreportD5Pdf(request, response, scheme, finalString, period, state, discom, month, ieperiod,townname,twn);
		
	}
	
	@RequestMapping(value="/PFCreportD7PDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void PFCreportD7PDF(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String scheme=request.getParameter("scheme");
		String town=request.getParameter("town");
		String period=request.getParameter("period");
		String state=request.getParameter("state");
		String discom=request.getParameter("discom");
		String month=request.getParameter("month");
		String ieperiod=request.getParameter("ieperiod");
		String townname=request.getParameter("townname");
		String[] arrSplit = town.split(",");
		
		String twn="";
		if(town.equalsIgnoreCase("All"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
    	String firsttest =null;
    	String test = null;
    	int size = town.length();
    	String finalString = null;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    }
		
		pfcservice.getPfcreportD7Pdf(request, response, scheme, finalString, period, state, discom, month, ieperiod,townname,twn);
		
	}
	
	/*@RequestMapping(value="/NPPReportConsumerPDF/{scheme}/{town}/{period}/{state}/{discom}/{month}/{ieperiod}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void NPPReportConsumerPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model,@PathVariable String scheme,@PathVariable String town,@PathVariable String period
			,@PathVariable String state,@PathVariable String discom,@PathVariable String month,@PathVariable String ieperiod)
	{
	            pfcservice.getNPPReportConsumerPdf(request, response, scheme, town, period, state, discom, month, ieperiod);
	}*/
	
	@RequestMapping(value="/NPPReportConsumerPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void NPPReportConsumerPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String scheme=request.getParameter("scheme");
		String town=request.getParameter("town");
		String period=request.getParameter("period");
		String state=request.getParameter("state");
		String discom=request.getParameter("discom");
		String month=request.getParameter("month");
		String ieperiod=request.getParameter("ieperiod");
		String townname=request.getParameter("townname");
		String[] arrSplit = town.split(",");
		
		String twn="";
		if(town.equalsIgnoreCase("All"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
    	String firsttest =null;
    	String test = null;
    	int size = town.length();
    	String finalString = null;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    }
		
		pfcservice.getNPPReportConsumerPdf(request, response, scheme, finalString, period, state, discom, month, ieperiod,townname,twn);
		
	}
	
	@RequestMapping(value="/exportToExcelPFCReportD1Data",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelPFCReportD1Data(@RequestParam String endMonth,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		//System.out.println("calling download excel consumer Data");
		try {
			String scheme=request.getParameter("scheme");
			String town= request.getParameter("town");
			String period=request.getParameter("period");
			String ieperiod=request.getParameter("ieperiod");
			String repmonth=endMonth;
			
			String twn="";
			if(town.equalsIgnoreCase("All"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
            String[] arrSplit = town.split(",");
            
            String firsttest =null;
        	String test = null;
        	int size = town.length();
        	String finalString = null;
        	for (int i=0; i < arrSplit.length; i++) {

	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}

        	}
		    
		    String fileName = "Pfcd1";
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header1  = sheet.createRow(0);
            XSSFRow header2  = sheet.createRow(1);
            XSSFRow header3  = sheet.createRow(2);
            XSSFRow header4  = sheet.createRow(3);   
            XSSFRow header5  = sheet.createRow(4);   
            XSSFRow header6  = sheet.createRow(5);   
            XSSFRow header7  = sheet.createRow(6);   
            
            header1.createCell(0).setCellValue("Town wise AT&C Loss report");
            header2.createCell(0).setCellValue("Level of Monitoring:PFC/MoP");
            header3.createCell(0).setCellValue("Format: D1");
            
            header4.createCell(0).setCellValue("Name of State:");
            header5.createCell(0).setCellValue("Name of Discom:");
            header6.createCell(0).setCellValue("Report Month:");
            header7.createCell(0).setCellValue("Input Energy Period:");
            
            header4.createCell(1).setCellValue("Tamil Nadu");
            header5.createCell(1).setCellValue("TNEB");
            header6.createCell(1).setCellValue(repmonth);
            header7.createCell(1).setCellValue(request.getParameter("ieperiod"));
            
            sheet.addMergedRegion(new CellRangeAddress(8, 9, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(8, 9, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(8, 8, 2, 4));
            
            XSSFRow header8  = sheet.createRow(8); 
            XSSFRow header9 = sheet.createRow(9);
            XSSFRow header10 = sheet.createRow(8);
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
				header8.createCell(0).setCellValue("S.NO");
				header8.createCell(1).setCellValue("Town Name");
				header8.createCell(2).setCellValue("Cumulative Billing Efficiency, Collection Efficiency & AT&C Losses in %");
				header9.createCell(2).setCellValue("Billing Efficiency(%)");
				header9.createCell(3).setCellValue("Collection Efficiency(%)");
				header9.createCell(4).setCellValue("AT&C Loss(%)");
				header8.createCell(5).setCellValue("Baseline Loss");
				
				
				List<?> pfcd1Data=null;
				pfcd1Data = pfcservice.getpfcD1Data(period,finalString,twn);
				
				int count =10;
				int cellNO=0;
	            for(Iterator<?> iterator=pfcd1Data.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		cellNO++;
	      		row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		if(values[1]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(1).setCellValue(String.valueOf(values[1]));
	      		}
	      		if(values[3]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[3]));
	      		}
	      		if(values[4]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      			sheet.setColumnWidth(2, 40);
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[4]));
	      		}
	      		if(values[2]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[2]));
	      		}
	      		
	      		if(values[5]==null)
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(values[5]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"PFCReportD1Report.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/exportToExcelPFCReportD2Data",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelPFCReportD2Data(@RequestParam String endMonth,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {

			
			String scheme=request.getParameter("scheme");
			String town= request.getParameter("town");
			String period=request.getParameter("period");
			String ieperiod=request.getParameter("ieperiod");
			String repmonth=endMonth;
			
			String twn="";
			if(town.equalsIgnoreCase("All"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
            String[] arrSplit = town.split(",");
            
            String firsttest =null;
        	String test = null;
        	int size = town.length();
        	String finalString = null;
        	for (int i=0; i < arrSplit.length; i++) {

	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    
        	}
		    
		    String fileName = "Pfcd2";	
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header1  = sheet.createRow(0);
            XSSFRow header2  = sheet.createRow(1);
            XSSFRow header3  = sheet.createRow(2);
            XSSFRow header4  = sheet.createRow(3);   
            XSSFRow header5  = sheet.createRow(4);   
            XSSFRow header6  = sheet.createRow(5);   
            XSSFRow header7  = sheet.createRow(6);   
            
            header1.createCell(0).setCellValue("New Service Connection Report");
            header2.createCell(0).setCellValue("Level of Monitoring:PFC/MoP ");
            header3.createCell(0).setCellValue("Format:D2");
            
            header4.createCell(0).setCellValue("Name of State:");
            header5.createCell(0).setCellValue("Name of Discom:");
            header6.createCell(0).setCellValue("Report Month:");
            header7.createCell(0).setCellValue("Input Energy Period:");
            
            header4.createCell(1).setCellValue("Tamil Nadu");
            header5.createCell(1).setCellValue("TNEB");
            header6.createCell(1).setCellValue(repmonth);
            header7.createCell(1).setCellValue(request.getParameter("ieperiod"));
            
            XSSFRow header  = sheet.createRow(8);   
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
             
                header.createCell(0).setCellValue("S.No");
				header.createCell(1).setCellValue("Name of Town");
				header.createCell(2).setCellValue("New connections pending from Previous Period");
				header.createCell(3).setCellValue("New Connections Applied in Current Period");
				header.createCell(4).setCellValue("Total New Connections Pending For Release");
				header.createCell(5).setCellValue("Total Connections Released in Current Period");
				header.createCell(6).setCellValue("Connections Yet to be Released");
				header.createCell(7).setCellValue("Connections Released within SERC Time Limit");
				header.createCell(8).setCellValue("Connections Released beyond SERC Time Limit");
				header.createCell(9).setCellValue(" % of Connections Released within SERC Time Limit");
				header.createCell(10).setCellValue("Connections Released witby  it system");
				List<?> pfcd2Data=null;
				pfcd2Data = pfcservice.getpfcD2Data(period,finalString,twn);
				
				int count =9;
				int cellNO=0;
	            for(Iterator<?> iterator=pfcd2Data.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		cellNO++;
	      		row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		if(values[0]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(1).setCellValue(String.valueOf(values[0]));
	      		}
	      		if(values[1]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[1]));
	      		}
	      		if(values[2]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      			sheet.setColumnWidth(2, 40);
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[2]));
	      		}
	      		if(values[3]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[3]));
	      		}
	      		if(values[4]==null)
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(values[4]));
	      		}
	      		if(values[5]==null)
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(values[5]));
	      		}
	      		if(values[6]==null)
	      		{
	      			row.createCell(7).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(7).setCellValue(String.valueOf(values[6]));
	      		}
	      		
	      		if(values[7]==null)
	      		{
	      			row.createCell(8).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(8).setCellValue(String.valueOf(values[7]));
	      		}
	      		
	      		if(values[8]==null)
	      		{
	      			row.createCell(9).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(9).setCellValue(String.valueOf(values[8]));
	      		}
	      		
	      		if(values[9]==null)
	      		{
	      			row.createCell(10).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(10).setCellValue(String.valueOf(values[9]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"PFCReportD2Report.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/exportToExcelPFCReportD3Data",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelPFCReportD3Data(@RequestParam String endMonth,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {

			String scheme=request.getParameter("scheme");
			String town= request.getParameter("town");
			String period=request.getParameter("period");
			String ieperiod=request.getParameter("ieperiod");
			String repmonth=endMonth;
			
			String twn="";
			if(town.equalsIgnoreCase("All"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
            String[] arrSplit = town.split(",");
            
            String firsttest =null;
        	String test = null;
        	int size = town.length();
        	String finalString = null;
        	for (int i=0; i < arrSplit.length; i++) {

	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    
        	}
		    
		    String fileName = "Pfcd3";	
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header1  = sheet.createRow(0);
            XSSFRow header2  = sheet.createRow(1);
            XSSFRow header3  = sheet.createRow(2);
            XSSFRow header4  = sheet.createRow(3);   
            XSSFRow header5  = sheet.createRow(4);   
            XSSFRow header6  = sheet.createRow(5);   
            XSSFRow header7  = sheet.createRow(6);   
            
            header1.createCell(0).setCellValue("Consumer Complaints redressal report");
            header2.createCell(0).setCellValue("Level of Monitoring:PFC/MOP");
            header3.createCell(0).setCellValue("Format:D3");
            
            header4.createCell(0).setCellValue("Name of State:");
            header5.createCell(0).setCellValue("Name of Discom:");
            header6.createCell(0).setCellValue("Report Month:");
            header7.createCell(0).setCellValue("Input Energy Period:");
            
            header4.createCell(1).setCellValue("Tamil Nadu");
            header5.createCell(1).setCellValue("TNEB");
            header6.createCell(1).setCellValue(repmonth);
            header7.createCell(1).setCellValue(request.getParameter("ieperiod"));
            
            XSSFRow header  = sheet.createRow(8);   
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
                header.createCell(0).setCellValue("S.No");
				header.createCell(1).setCellValue("Name of Town");
				header.createCell(2).setCellValue("Complaints pending from Previous Period");
				header.createCell(3).setCellValue("Complaints registered in Current Period");
				header.createCell(4).setCellValue("Total Pending Complaints");
				header.createCell(5).setCellValue("Complaints Closed");
				header.createCell(6).setCellValue("Complaints Pending Period(Average)HH:MM");
				
				List<?> pfcd3Data=null;
				pfcd3Data = pfcservice.getpfcD3Data(period,finalString,twn);
				
				int count =9;
				int cellNO=0;
	            for(Iterator<?> iterator=pfcd3Data.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		cellNO++;
	      		row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		
	      		if(values[0]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(1).setCellValue(String.valueOf(values[0]));
	      		}
	      		if(values[1]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[1]));
	      		}
	      		if(values[2]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      			sheet.setColumnWidth(2, 40);
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[2]));
	      		}
	      		if(values[3]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[3]));
	      		}
	      		if(values[4]==null)
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(values[4]));
	      		}
	      		if(values[5]==null)
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(values[5]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"PFCReportD3Report.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/exportToExcelPFCReportD4Data",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelPFCReportD4Data(@RequestParam String endMonth,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {


			String scheme=request.getParameter("scheme");
			String town= request.getParameter("town");
			String period=request.getParameter("period");
			String ieperiod=request.getParameter("ieperiod");
			String repmonth=endMonth;
			
			String twn="";
			if(town.equalsIgnoreCase("All"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
            String[] arrSplit = town.split(",");
            
            String firsttest =null;
        	String test = null;
        	int size = town.length();
        	String finalString = null;
        	for (int i=0; i < arrSplit.length; i++) {

	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    
        	}
		    
		    String fileName = "Pfcd4";	
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header1  = sheet.createRow(0);
            XSSFRow header2  = sheet.createRow(1);
            XSSFRow header3  = sheet.createRow(2);
            XSSFRow header4  = sheet.createRow(3);   
            XSSFRow header5  = sheet.createRow(4);   
            XSSFRow header6  = sheet.createRow(5);   
            XSSFRow header7  = sheet.createRow(6);   
            
            header1.createCell(0).setCellValue("Feeder wise AT & C loss report ( 10% worst feeder)");
            header2.createCell(0).setCellValue("Level of Monitoring:PFC/MOP");
            header3.createCell(0).setCellValue("Format:D4");
            
            header4.createCell(0).setCellValue("Name of State:");
            header5.createCell(0).setCellValue("Name of Discom:");
            header6.createCell(0).setCellValue("Report Month:");
            header7.createCell(0).setCellValue("Input Energy Period:");
            
            header4.createCell(1).setCellValue("Tamil Nadu");
            header5.createCell(1).setCellValue("TNEB");
            header6.createCell(1).setCellValue(repmonth);
            header7.createCell(1).setCellValue(request.getParameter("ieperiod"));
            
            sheet.addMergedRegion(new CellRangeAddress(8, 9, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(8, 9, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(8, 9, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(8, 9, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(8, 8, 4, 6));
            
            XSSFRow header8  = sheet.createRow(8);
            XSSFRow header9  = sheet.createRow(9);
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
                header8.createCell(0).setCellValue("S.No"); 
				header8.createCell(1).setCellValue("Town Code");
				header8.createCell(2).setCellValue("Total Feeder");
				header8.createCell(3).setCellValue("Name of Feeder");
				header8.createCell(4).setCellValue("Cumulative Billing Efficiency, Collection Efficiency & AT&C Losses in %");
				header9.createCell(4).setCellValue("Billing Efficiency(%)");
				header9.createCell(5).setCellValue("Collection Efficiency(%)");
				header9.createCell(6).setCellValue("AT&C Loss(%)");
				
				List<?> pfcd4Data=null;
				pfcd4Data = pfcservice.getpfcD4Data(period,finalString,twn);
			
				int count =10;
				int cellNO=0;
	            for(Iterator<?> iterator=pfcd4Data.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		cellNO++;
	      		row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		
	      		if(values[0]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(1).setCellValue(String.valueOf(values[0]));
	      		}
	      		if(values[1]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[1]));
	      		}
	      		if(values[2]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[2]));
	      		}
	      		if(values[3]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[3]));
	      		}
	      		if(values[4]==null)
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(values[4]));
	      		}
	      		if(values[5]==null)
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(values[5]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"PFCReportD4Report.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/exportToExcelPFCReportD5Data",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelPFCReportD5Data(@RequestParam String endMonth,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {

			String scheme=request.getParameter("scheme");
			String town= request.getParameter("town");
			String period=request.getParameter("period");
			String ieperiod=request.getParameter("ieperiod");
			String repmonth=endMonth;
			
			String twn="";
			if(town.equalsIgnoreCase("All"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
            String[] arrSplit = town.split(",");
            
            String firsttest =null;
        	String test = null;
        	int size = town.length();
        	String finalString = null;
        	for (int i=0; i < arrSplit.length; i++) {

	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    
        	}
		    
		    String fileName = "Pfcd5";	
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header1  = sheet.createRow(0);
            XSSFRow header2  = sheet.createRow(1);
            XSSFRow header3  = sheet.createRow(2);
            XSSFRow header4  = sheet.createRow(3);   
            XSSFRow header5  = sheet.createRow(4);   
            XSSFRow header6  = sheet.createRow(5);   
            XSSFRow header7  = sheet.createRow(6);   
            
            header1.createCell(0).setCellValue("SAIDI-SAIF I Report ");
            header2.createCell(0).setCellValue("Level of Monitoring:PFC/MOP");
            header3.createCell(0).setCellValue("Format:D5");
            
            header4.createCell(0).setCellValue("Name of State:");
            header5.createCell(0).setCellValue("Name of Discom:");
            header6.createCell(0).setCellValue("Report Month:");
            header7.createCell(0).setCellValue("Input Energy Period:");
            
            header4.createCell(1).setCellValue("Tamil Nadu");
            header5.createCell(1).setCellValue("TNEB");
            header6.createCell(1).setCellValue(repmonth);
            header7.createCell(1).setCellValue(request.getParameter("ieperiod"));
            
            XSSFRow header  = sheet.createRow(8);   
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
                header.createCell(0).setCellValue("S.No");
				header.createCell(1).setCellValue("Name of Town");
				header.createCell(2).setCellValue("Name of Feeder");
				header.createCell(3).setCellValue("Number of Consumers");
				header.createCell(4).setCellValue("Number of outages(Nos.)");
				header.createCell(5).setCellValue("Duration of Outaged(Sec)");
				
				List<?> pfcd5Data=null;
				pfcd5Data = pfcservice.getpfcD5Data(period,finalString,twn);
				
				int count =9;
				int cellNO=0;
	            for(Iterator<?> iterator=pfcd5Data.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		cellNO++;
	      		row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		
	      		if(values[0]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(1).setCellValue(String.valueOf(values[0]));
	      		}
	      		if(values[1]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[1]));
	      		}
	      		if(values[2]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      			sheet.setColumnWidth(2, 40);
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[2]));
	      		}
	      		if(values[3]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[3]));
	      		}
	      		if(values[4]==null)
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(values[4]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"PFCReportD5Report.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/exportToExcelPFCReportD6Data",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelPFCReportD6Data(@RequestParam String endMonth,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {


			String scheme=request.getParameter("scheme");
			String town= request.getParameter("town");
			String period=request.getParameter("period");
			String ieperiod=request.getParameter("ieperiod");
			String repmonth=endMonth;
			
			String twn="";
			if(town.equalsIgnoreCase("All"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
            String[] arrSplit = town.split(",");
            
            String firsttest =null;
        	String test = null;
        	int size = town.length();
        	String finalString = null;
        	for (int i=0; i < arrSplit.length; i++) {

	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    
        	}
		    
		    String fileName = "Pfcd6";	
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header1  = sheet.createRow(0);
            XSSFRow header2  = sheet.createRow(1);
            XSSFRow header3  = sheet.createRow(2);
            XSSFRow header4  = sheet.createRow(3);   
            XSSFRow header5  = sheet.createRow(4);   
            XSSFRow header6  = sheet.createRow(5);   
            XSSFRow header7  = sheet.createRow(6);   
            
            header1.createCell(0).setCellValue("Feeder Meter Communication Status Report");
            header2.createCell(0).setCellValue("Level of Monitoring:PFC/MoP");
            header3.createCell(0).setCellValue("Format:D6");
            
            header4.createCell(0).setCellValue("Name of State:");
            header5.createCell(0).setCellValue("Name of Discom:");
            header6.createCell(0).setCellValue("Report Month:");
            header7.createCell(0).setCellValue("Input Energy Period:");
            
            header4.createCell(1).setCellValue("Tamil Nadu");
            header5.createCell(1).setCellValue("TNEB");
            header6.createCell(1).setCellValue(repmonth);
            header7.createCell(1).setCellValue(request.getParameter("ieperiod"));
            
            XSSFRow header  = sheet.createRow(8);   
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
                header.createCell(0).setCellValue("S.No");
				header.createCell(1).setCellValue("Name of Town");
				header.createCell(2).setCellValue("Feeder Name");
				header.createCell(3).setCellValue("MeterNo");
				header.createCell(4).setCellValue("Feeder Meters Communicating With data Center");
				
				List<?> pfcd6Data=null;
				pfcd6Data = pfcservice.getpfcD6Data(period,finalString,twn);
				
				int count =9;
				int cellNO=0;
	            for(Iterator<?> iterator=pfcd6Data.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		cellNO++;
	      		row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		
	      		if(values[0]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(1).setCellValue(String.valueOf(values[0]));
	      		}
	      		if(values[1]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[1]));
	      		}
	      		if(values[2]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      			sheet.setColumnWidth(2, 40);
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[2]));
	      		}
	      		if(values[3]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[3]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"PFCReportD6Report.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/exportToExcelPFCReportD7Data",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelPFCReportD7Data(@RequestParam String endMonth,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {

			String scheme=request.getParameter("scheme");
			String town= request.getParameter("town");
			String period=request.getParameter("period");
			String ieperiod=request.getParameter("ieperiod");
			String repmonth=endMonth;
			
			String twn="";
			if(town.equalsIgnoreCase("All"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
            String[] arrSplit = town.split(",");
            
            String firsttest =null;
        	String test = null;
        	int size = town.length();
        	String finalString = null;
        	for (int i=0; i < arrSplit.length; i++) {

	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    
        	}
		    
		    String fileName = "Pfcd7";	
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header1  = sheet.createRow(0);
            XSSFRow header2  = sheet.createRow(1);
            XSSFRow header3  = sheet.createRow(2);
            XSSFRow header4  = sheet.createRow(3);   
            XSSFRow header5  = sheet.createRow(4);   
            XSSFRow header6  = sheet.createRow(5);   
            XSSFRow header7  = sheet.createRow(6);   
            
            header1.createCell(0).setCellValue("E-Payment report");
            header2.createCell(0).setCellValue("Level of Monitoring:PFC/MoP");
            header3.createCell(0).setCellValue("Format:D7");
            
            header4.createCell(0).setCellValue("Name of State:");
            header5.createCell(0).setCellValue("Name of Discom:");
            header6.createCell(0).setCellValue("Report Month:");
            header7.createCell(0).setCellValue("Input Energy Period:");
            
            header4.createCell(1).setCellValue("Tamil Nadu");
            header5.createCell(1).setCellValue("TNEB");
            header6.createCell(1).setCellValue(repmonth);
            header7.createCell(1).setCellValue(request.getParameter("ieperiod"));
            
            XSSFRow header  = sheet.createRow(8);   
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
                header.createCell(0).setCellValue("S.No");
				header.createCell(1).setCellValue("Name of The Town");
				header.createCell(2).setCellValue("Total Consumers(Nos.)");
				header.createCell(3).setCellValue("Total Consumers Using E-Payment(Nos)");
				header.createCell(4).setCellValue("Total Collection(Rs.in Lac)");
				header.createCell(5).setCellValue("Collection through E-Payment(Rs.in Lac)");
				
				List<?> pfcd7Data=null;
				pfcd7Data = pfcservice.getpfcD7Data(period,finalString,twn);
				
				int count =9;
				int cellNO=0;
	            for(Iterator<?> iterator=pfcd7Data.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		cellNO++;
	      		row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		
	      		if(values[0]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(1).setCellValue(String.valueOf(values[0]));
	      		}
	      		if(values[1]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[1]));
	      		}
	      		if(values[2]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      			sheet.setColumnWidth(2, 40);
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[2]));
	      		}
	      		if(values[3]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[3]));
	      		}
	      		if(values[4]==null)
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(values[4]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"PFCReportD7Report.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/exportToExcelNPPRepConsumerData",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelNPPRepConsumerData(@RequestParam String endMonth,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {


			String scheme=request.getParameter("scheme");
			String town= request.getParameter("town");
			String period=request.getParameter("period");
			String ieperiod=request.getParameter("ieperiod");
			String repmonth=endMonth;
			
			String twn="";
			if(town.equalsIgnoreCase("All"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
            String[] arrSplit = town.split(",");
            
            String firsttest =null;
        	String test = null;
        	int size = town.length();
        	String finalString = null;
        	for (int i=0; i < arrSplit.length; i++) {

	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    
        	}
		    
		    String fileName = "nppconsumer";	
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header1  = sheet.createRow(0);
            XSSFRow header2  = sheet.createRow(1);
            XSSFRow header3  = sheet.createRow(2);
            XSSFRow header4  = sheet.createRow(3);   
            XSSFRow header5  = sheet.createRow(4);   
            XSSFRow header6  = sheet.createRow(5);   
            XSSFRow header7  = sheet.createRow(6);   
            
            header1.createCell(0).setCellValue("NPP Report");
            header2.createCell(0).setCellValue("Level of Monitoring:NPP");
            header3.createCell(0).setCellValue("Format:Consumer");
            
            header4.createCell(0).setCellValue("Name of State:");
            header5.createCell(0).setCellValue("Name of Discom:");
            header6.createCell(0).setCellValue("Report Month:");
            header7.createCell(0).setCellValue("Input Energy Period:");
            
            header4.createCell(1).setCellValue("Tamil Nadu");
            header5.createCell(1).setCellValue("TNEB");
            header6.createCell(1).setCellValue(repmonth);
            header7.createCell(1).setCellValue(request.getParameter("ieperiod"));
            
            sheet.addMergedRegion(new CellRangeAddress(8, 9, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(8, 9, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(8, 9, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(8, 8, 3, 6));
            sheet.addMergedRegion(new CellRangeAddress(8, 8, 7, 10));
            
            XSSFRow header8  = sheet.createRow(8); 
            XSSFRow header9  = sheet.createRow(9);
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
                header8.createCell(0).setCellValue("S.No");
				header8.createCell(1).setCellValue("Town Code");
				header8.createCell(2).setCellValue("DISCOM Code(NPP)");
				header8.createCell(3).setCellValue("New Connection");
				header9.createCell(3).setCellValue("New Connection Pending From Previous Month");
				header9.createCell(4).setCellValue("New Connection applied in Current Month");
				header9.createCell(5).setCellValue("Total Connection released in Current Month");
				header9.createCell(6).setCellValue("Connection released within SERC limit");
				header8.createCell(7).setCellValue("Complaints");
				header9.createCell(7).setCellValue("Complaints Pending From Previous Month");
				header9.createCell(8).setCellValue("New Complaints received in Current Month");
				header9.createCell(9).setCellValue("Total Complaints Closed in Current Month");
				header9.createCell(10).setCellValue("Complaints Closed within SERC limit");
				
				List<?> NPPConsumData=null;
				NPPConsumData = pfcservice.getnppconsumData(period,finalString,twn);
				
				int count =10;
				int cellNO=0;
	            for(Iterator<?> iterator=NPPConsumData.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		cellNO++;
	      		row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		
	      		if(values[0]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(1).setCellValue(String.valueOf(values[0]));
	      		}
	      		if(values[1]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[1]));
	      		}
	      		if(values[2]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      			sheet.setColumnWidth(2, 40);
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[2]));
	      		}
	      		if(values[3]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[3]));
	      		}
	      		if(values[4]==null)
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(values[4]));
	      		}
	      		if(values[5]==null)
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(values[5]));
	      		}
	      		if(values[6]==null)
	      		{
	      			row.createCell(7).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(7).setCellValue(String.valueOf(values[6]));
	      		}
	      		if(values[7]==null)
	      		{
	      			row.createCell(8).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(8).setCellValue(String.valueOf(values[7]));
	      		}
	      		if(values[8]==null)
	      		{
	      			row.createCell(9).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(9).setCellValue(String.valueOf(values[8]));
	      		}
	      		if(values[9]==null)
	      		{
	      			row.createCell(10).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(10).setCellValue(String.valueOf(values[9]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"NPPReportConsumer.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value="/exportToExcelNPPRepFeederData",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelNPPRepFeederData(@RequestParam String endMonth,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		try {



			String scheme=request.getParameter("scheme");
			String town= request.getParameter("town");
			String period=request.getParameter("period");
			String ieperiod=request.getParameter("ieperiod");
			String repmonth=endMonth;
			
			String twn="";
			if(town.equalsIgnoreCase("All"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
            String[] arrSplit = town.split(",");
            
            String firsttest =null;
        	String test = null;
        	int size = town.length();
        	String finalString = null;
        	for (int i=0; i < arrSplit.length; i++) {

	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    
        	}
		    
		    String fileName = "nppfeeder";	
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(fileName);
            XSSFRow header1  = sheet.createRow(0);
            XSSFRow header2  = sheet.createRow(1);
            XSSFRow header3  = sheet.createRow(2);
            XSSFRow header4  = sheet.createRow(3);   
            XSSFRow header5  = sheet.createRow(4);   
            XSSFRow header6  = sheet.createRow(5);   
            XSSFRow header7  = sheet.createRow(6);  
            
            header1.createCell(0).setCellValue("NPP REPORT");
            header2.createCell(0).setCellValue("Level of Monitoring : NPP");
            header3.createCell(0).setCellValue("Format : Feeder");
            
            header4.createCell(0).setCellValue("Name of State:");
            header5.createCell(0).setCellValue("Name of Discom:");
            header6.createCell(0).setCellValue("Report Month:");
            header7.createCell(0).setCellValue("Input Energy Period:");
            
            header4.createCell(1).setCellValue("Tamil Nadu");
            header5.createCell(1).setCellValue("TNEB");
            header6.createCell(1).setCellValue(repmonth);
            header7.createCell(1).setCellValue(request.getParameter("ieperiod"));
            
            XSSFRow header  = sheet.createRow(8);   
            
             CellStyle lockedCellStyle = wb.createCellStyle();
             lockedCellStyle.setLocked(true);
             CellStyle unlockedCellStyle = wb.createCellStyle();
             unlockedCellStyle.setLocked(false);
            
             XSSFCellStyle style = wb.createCellStyle();
             style.setWrapText(true);
             sheet.setColumnWidth(0, 1000);
             XSSFFont font = wb.createFont();
             font.setFontName(HSSFFont.FONT_ARIAL);
             font.setFontHeightInPoints((short)10);
             font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
             style.setFont(font);
 
				header.createCell(0).setCellValue("Feeder Code");
				header.createCell(1).setCellValue("Feeder Type(U/R/M)");
				header.createCell(2).setCellValue("Start Blling Period");
				header.createCell(3).setCellValue("End Billing Period");
				header.createCell(4).setCellValue("No of Power Failure");
				header.createCell(5).setCellValue("Duration of Power Failure(Second)");
				header.createCell(6).setCellValue("Minimun Voltage(V)");
				header.createCell(7).setCellValue("Maximum Current(A)");
				header.createCell(8).setCellValue("Input Energy(Kwh)");
				header.createCell(9).setCellValue("Export Energy(Kwh)");
				header.createCell(10).setCellValue("HT Industrial Consumer Count");
				header.createCell(11).setCellValue("HT Commercial Consumer Count");
				header.createCell(12).setCellValue("LT Industrial Consumer Count");
				header.createCell(13).setCellValue("LT Commercial Consumer Count");
				header.createCell(14).setCellValue("LT Domestic Consumer Count");
				header.createCell(15).setCellValue("Govt Consumer Count");
				header.createCell(16).setCellValue("Agri Consumer Count");
				header.createCell(17).setCellValue("Others Consumer Count");
				header.createCell(18).setCellValue("HT Industrial Energy Billed(Kwh)");
				header.createCell(19).setCellValue("HT Commercial");
				header.createCell(20).setCellValue("LT Industrial Energy Billed(Kwh)");
				header.createCell(21).setCellValue("LT Commercial Energy Billed(Kwh)");
				header.createCell(22).setCellValue("LT Domestic Energy Billed(Kwh)");
				header.createCell(23).setCellValue("Govt Energy Billed(Kwh)");
				header.createCell(24).setCellValue("Agri Energy Billed(Kwh)");
				header.createCell(25).setCellValue("Others Energy Billed(Kwh)");
				header.createCell(26).setCellValue("HT Industrial Amount Billed");
				header.createCell(27).setCellValue("HT Commercial");
				header.createCell(28).setCellValue("LT Industrial Amount Billed");
				header.createCell(29).setCellValue("LT Commercial Amount Billed");
				header.createCell(30).setCellValue("LT Domestic Amount Billed");
				header.createCell(31).setCellValue("Govt Amount Billed");
				header.createCell(32).setCellValue("Agri Amount Billed");
				header.createCell(33).setCellValue("Others Amount Billed");
				header.createCell(34).setCellValue("HT Industrial Amount Collected");
				header.createCell(35).setCellValue("HT Commercial Amount Collected");
				header.createCell(36).setCellValue("LT Industrial Amount Collected");
				header.createCell(37).setCellValue("LT Commercial Amount Collected");
				header.createCell(38).setCellValue("LT Domestic Amount Collected)");
				header.createCell(39).setCellValue("Govt Amount Collected");
				header.createCell(40).setCellValue("Agri Amount Collected");
				header.createCell(41).setCellValue("Others Amount Collected");

				
				List<?> NPPFeederData=null;
				//NPPFeederData = pfcservice.getnppfeederData(period,finalString,twn);
				NPPFeederData = (List<Object[]>) nppDataService.getNPPReportRapdrpDetails(town, period);
				
				int count =9;
				//int cellNO=0;
	            for(Iterator<?> iterator=NPPFeederData.iterator();iterator.hasNext();){
	      	    final Object[] values=(Object[]) iterator.next();
	      		
	      		XSSFRow row = sheet.createRow(count);
	      		//cellNO++;
	      		//row.createCell(0).setCellValue(String.valueOf(cellNO+""));
	      		
	      		if(values[0]==null)
	      		{
	      			row.createCell(0).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(0).setCellValue(String.valueOf(values[0]));
	      		}
	      		if(values[44]==null)
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(1).setCellValue(String.valueOf(values[44]));
	      		}
	      		if(values[42]==null)
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(""));
	      			sheet.setColumnWidth(2, 40);
	      		}else
	      		{
	      			row.createCell(2).setCellValue(String.valueOf(values[42]));
	      		}
	      		if(values[43]==null)
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(3).setCellValue(String.valueOf(values[43]));
	      		}
	      		if(values[2]==null)
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(4).setCellValue(String.valueOf(values[2]));
	      		}
	      		if(values[4]==null)
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(5).setCellValue(String.valueOf(values[4]));
	      		}
	      		if(values[40]==null)
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(values[40]));
	      		}
	      		if(values[41]==null)
	      		{
	      			row.createCell(7).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(7).setCellValue(String.valueOf(values[41]));
	      		}
	      		if(values[38]==null)
	      		{
	      			row.createCell(8).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(8).setCellValue(String.valueOf(values[38]));
	      		}
	      		if(values[39]==null)
	      		{
	      			row.createCell(9).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(9).setCellValue(String.valueOf(values[39]));
	      		}
	      		if(values[6]==null)
	      		{
	      			row.createCell(10).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(10).setCellValue(String.valueOf(values[6]));
	      		}	      		
	      		if(values[7]==null)
	      		{
	      			row.createCell(11).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(11).setCellValue(String.valueOf(values[7]));
	      		}
	      		if(values[8]==null)
	      		{
	      			row.createCell(12).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(12).setCellValue(String.valueOf(values[8]));
	      		}
	      		if(values[9]==null)
	      		{
	      			row.createCell(13).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(13).setCellValue(String.valueOf(values[9]));
	      		}
	      		if(values[10]==null)
	      		{
	      			row.createCell(14).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(14).setCellValue(String.valueOf(values[10]));
	      		}
	      		if(values[11]==null)
	      		{
	      			row.createCell(15).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(15).setCellValue(String.valueOf(values[11]));
	      		}
	      		if(values[12]==null)
	      		{
	      			row.createCell(16).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(16).setCellValue(String.valueOf(values[12]));
	      		}
	      		if(values[13]==null)
	      		{
	      			row.createCell(17).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(17).setCellValue(String.valueOf(values[13]));
	      		}
	      		if(values[14]==null)
	      		{
	      			row.createCell(18).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(18).setCellValue(String.valueOf(values[14]));
	      		}
	      		if(values[15]==null)
	      		{
	      			row.createCell(19).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(19).setCellValue(String.valueOf(values[15]));
	      		}
	      		if(values[16]==null)
	      		{
	      			row.createCell(20).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(20).setCellValue(String.valueOf(values[16]));
	      		}
	      		if(values[17]==null)
	      		{
	      			row.createCell(21).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(21).setCellValue(String.valueOf(values[17]));
	      		}
	      		if(values[18]==null)
	      		{
	      			row.createCell(22).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(22).setCellValue(String.valueOf(values[18]));
	      		}
	      		if(values[19]==null)
	      		{
	      			row.createCell(23).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(23).setCellValue(String.valueOf(values[19]));
	      		}
	      		if(values[20]==null)
	      		{
	      			row.createCell(24).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(24).setCellValue(String.valueOf(values[20]));
	      		}
	      		if(values[21]==null)
	      		{
	      			row.createCell(25).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(25).setCellValue(String.valueOf(values[21]));
	      		}
	      		if(values[22]==null)
	      		{
	      			row.createCell(26).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(26).setCellValue(String.valueOf(values[22]));
	      		}
	      		if(values[23]==null)
	      		{
	      			row.createCell(27).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(27).setCellValue(String.valueOf(values[23]));
	      		}
	      		if(values[24]==null)
	      		{
	      			row.createCell(28).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(28).setCellValue(String.valueOf(values[24]));
	      		}
	      		if(values[25]==null)
	      		{
	      			row.createCell(29).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(29).setCellValue(String.valueOf(values[25]));
	      		}
	      		if(values[26]==null)
	      		{
	      			row.createCell(30).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(30).setCellValue(String.valueOf(values[26]));
	      		}
	      		if(values[27]==null)
	      		{
	      			row.createCell(31).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(31).setCellValue(String.valueOf(values[27]));
	      		}
	      		if(values[28]==null)
	      		{
	      			row.createCell(32).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(32).setCellValue(String.valueOf(values[28]));
	      		}
	      		if(values[29]==null)
	      		{
	      			row.createCell(33).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(33).setCellValue(String.valueOf(values[29]));
	      		}
	      		if(values[30]==null)
	      		{
	      			row.createCell(34).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(34).setCellValue(String.valueOf(values[30]));
	      		}
	      		if(values[31]==null)
	      		{
	      			row.createCell(35).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(35).setCellValue(String.valueOf(values[31]));
	      		}
	      		if(values[32]==null)
	      		{
	      			row.createCell(36).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(36).setCellValue(String.valueOf(values[32]));
	      		}
	      		if(values[33]==null)
	      		{
	      			row.createCell(37).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(37).setCellValue(String.valueOf(values[33]));
	      		}
	      		if(values[34]==null)
	      		{
	      			row.createCell(38).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(38).setCellValue(String.valueOf(values[34]));
	      		}
	      		if(values[35]==null)
	      		{
	      			row.createCell(39).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(39).setCellValue(String.valueOf(values[35]));
	      		}
	      		if(values[36]==null)
	      		{
	      			row.createCell(40).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(40).setCellValue(String.valueOf(values[36]));
	      		}
	      		if(values[37]==null)
	      		{
	      			row.createCell(41).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      			row.createCell(41).setCellValue(String.valueOf(values[37]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"NPPReportFeeder.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
