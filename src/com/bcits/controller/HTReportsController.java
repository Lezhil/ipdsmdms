package com.bcits.controller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.service.BillingData360Service;
import com.bcits.service.MasterService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.RdngMonthService;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
public class HTReportsController {
	@Autowired
	private MasterService masterService;
	@Autowired 
	private MeterMasterService meterMasterService;
	
	@Autowired
	private RdngMonthService rdngMonthService;
	
	@Autowired
	private BillingData360Service billingData360Service;
	
	@RequestMapping(value="/htReadingReport",method={RequestMethod.GET,RequestMethod.POST})
	public String htmethod(ModelMap model)
	{
		model.put("readingMonth",rdngMonthService.findAll());
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		
		return "htReport";
	}

	@RequestMapping(value="/htReadingDetails",method={RequestMethod.GET,RequestMethod.POST})
	public String htReadingDatamethod(HttpServletRequest request,ModelMap model)
	{
		model.put("readingMonth",rdngMonthService.findAll());
		List<?> circle=masterService.getALLCircle();
		model.addAttribute("circle",circle);
		String circle1=request.getParameter("circle");
		String rdngMonth=request.getParameter("reportsMonth");
		
		System.out.println("In HT Manual rdngMonth-----"+rdngMonth+"circle----"+circle1);
		
		List<?> htlList=meterMasterService.gethtReadingDetails(rdngMonth,circle1);
		if(htlList.size()>0)
		{
		model.addAttribute("showhtReading","showhtReading");
		model.put("htlList",htlList);
	
		}
		else{
			model.addAttribute("htReadingerror","HT Reading Data Not Found...");
		}

		
		return "htReport";
	}
	
	

	@RequestMapping(value="/htReadingExportToExcel",method={RequestMethod.GET,RequestMethod.POST})
	public String htExportExcel(HttpServletRequest request,HttpSession session,ModelMap model,HttpServletResponse response) throws ParseException, IOException
	{ 
		System.out.println("===================Inside HT Reading Export==============");
		
		String rdngMonth=request.getParameter("rdngMonth");
		String circle=request.getParameter("circle");
		if(circle.equalsIgnoreCase("ALL"))
		{
			circle="%";
		}
		System.out.println("In Export To Excel rdngMonth-----"+rdngMonth+"circle----"+circle);
	    String fileName = "HtReading.xlsx";
	List<?> htlExcelList=meterMasterService.gethtReadingDetailsForExcel(rdngMonth,circle);
	
	System.out.println("htlExcelList size =========>>>"+htlExcelList.size());
   
	//List<?> queryTemplateEntities = prepaidMeterService.FindAll();

           
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
	header.createCell(1).setCellValue("DIVISION");
	header.createCell(2).setCellValue("SUBDIV ");
    header.createCell(3).setCellValue("ACCNO");
    header.createCell(4).setCellValue("CONSUMERNAME");
    header.createCell(5).setCellValue("ADDRESS ");
    header.createCell(6).setCellValue("CATEGORY");
    header.createCell(7).setCellValue("METRNO");
    header.createCell(8).setCellValue("RDATE");
    header.createCell(9).setCellValue("XCURRDNGKWH");
    
    header.createCell(10).setCellValue("XCURRRDNGKVAH");
	header.createCell(11).setCellValue("XCURRDNGKVA");
	header.createCell(12).setCellValue("KW");
    header.createCell(13).setCellValue("KWHE");
    header.createCell(14).setCellValue("KVHE");
    header.createCell(15).setCellValue("KVAE ");
    header.createCell(16).setCellValue("T1KWH");
    header.createCell(17).setCellValue("T2KWH");
    header.createCell(18).setCellValue("T3KWH");
    header.createCell(19).setCellValue("T4KWH");
    
    header.createCell(20).setCellValue("T5KWH");
  	header.createCell(21).setCellValue("T6KWH");
  	header.createCell(22).setCellValue("T7KWH");
      header.createCell(23).setCellValue("T8KWH");
      header.createCell(24).setCellValue("T1KVAH");
      header.createCell(25).setCellValue("T2KVAH ");
      header.createCell(26).setCellValue("T3KVAH");
      header.createCell(27).setCellValue("T4KVAH");
      header.createCell(28).setCellValue("T5KVAH");
      header.createCell(29).setCellValue("T6KVAH");
      
      
      header.createCell(30).setCellValue("T7KVAH");
    	header.createCell(31).setCellValue("T8KVAH");
    	header.createCell(32).setCellValue("T1KVAV");
        header.createCell(33).setCellValue("T2KVAV");
        header.createCell(34).setCellValue("T3KVAV");
        header.createCell(35).setCellValue("T4KVAV ");
        header.createCell(36).setCellValue("T5KVAV");
        header.createCell(37).setCellValue("T6KVAV");
        header.createCell(38).setCellValue("T7KVAV");
        header.createCell(39).setCellValue("T8KVAV");
        
        header.createCell(40).setCellValue("GROUP_VALUE");
    	header.createCell(41).setCellValue("BILLING_CATEGORY");
    	header.createCell(42).setCellValue("READINGREMARK");
        header.createCell(43).setCellValue("REMARK");
       
   
   
   for(int j = 0; j<=43; j++){
		header.getCell(j).setCellStyle(style);
        sheet.setColumnWidth(j, 8000); 
        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q200"));
    }
        
       
    int count = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm ");
    SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    for(Iterator<?> iterator=htlExcelList.iterator();iterator.hasNext();){
	       final Object[] values=(Object[]) iterator.next();
		
		XSSFRow row = sheet.createRow(count);
		
		row.createCell(0).setCellValue((String)values[0]);
		row.createCell(1).setCellValue((String)values[1]);
		row.createCell(2).setCellValue((String)values[2]);
		row.createCell(3).setCellValue((String)values[3]);
		row.createCell(4).setCellValue((String)values[4]);
		row.createCell(5).setCellValue((String)values[5]);
		row.createCell(6).setCellValue((String)values[6]);
		row.createCell(7).setCellValue((String)values[7]);
		
		if(values[8]!=null)
		{
		String rdate1=String.valueOf(values[8]);
		Date d1=sdf1.parse(rdate1);
		String s1=sdf1.format(d1);
		row.createCell(8).setCellValue(s1);
		}
		else
		{
			row.createCell(8).setCellValue("");
		}
		
		if(values[9]!=null)
		{
		row.createCell(9).setCellValue(String.valueOf(values[9]));
		}
		else
		{
			row.createCell(9).setCellValue("");
		}
		
		if(values[10]!=null)
		{
		row.createCell(10).setCellValue(String.valueOf(values[10]));
		}
		else
		{
			row.createCell(10).setCellValue("");
		}
		
		if(values[11]!=null)
		{
		row.createCell(11).setCellValue(String.valueOf(values[11]));
		}
		else
		{
			row.createCell(11).setCellValue("");
		}
		
		if(values[12]!=null)
		{
		row.createCell(12).setCellValue(String.valueOf(values[12]));
		}
		else
		{
			row.createCell(12).setCellValue("");
		}
		
		if(values[13]!=null)
		{
		row.createCell(13).setCellValue(String.valueOf(values[13]));
		}
		else
		{
			row.createCell(13).setCellValue("");
		}
		
		if(values[14]!=null)
		{
		row.createCell(14).setCellValue(String.valueOf(values[14]));
		}
		else
		{
			row.createCell(14).setCellValue("");
		}
		
		if(values[15]!=null)
		{
		row.createCell(15).setCellValue(String.valueOf(values[15]));
		}
		else
		{
			row.createCell(15).setCellValue("");
		}
		
		if(values[16]!=null)
		{
		row.createCell(16).setCellValue(String.valueOf(values[16]));
		}
		else
		{
			row.createCell(16).setCellValue("");
		}
		
		if(values[17]!=null)
		{
		row.createCell(17).setCellValue(String.valueOf(values[17]));
		}
		else
		{
			row.createCell(17).setCellValue("");
		}
		
		if(values[18]!=null)
		{
		row.createCell(18).setCellValue(String.valueOf(values[18]));
		}
		else
		{
			row.createCell(18).setCellValue("");
		}
		
		if(values[19]!=null)
		{
		row.createCell(19).setCellValue(String.valueOf(values[19]));
		}
		else
		{
			row.createCell(19).setCellValue("");
		}
		
		if(values[20]!=null)
		{
		row.createCell(20).setCellValue(String.valueOf(values[20]));
		}
		else
		{
			row.createCell(20).setCellValue("");
		}
		
		if(values[21]!=null)
		{
		row.createCell(21).setCellValue(String.valueOf(values[21]));
		}
		else
		{
			row.createCell(21).setCellValue("");
		}
		
		if(values[22]!=null)
		{
		row.createCell(22).setCellValue(String.valueOf(values[22]));
		}
		else
		{
			row.createCell(22).setCellValue("");
		}
		
		if(values[23]!=null)
		{
		row.createCell(23).setCellValue(String.valueOf(values[23]));
		}
		else
		{
			row.createCell(23).setCellValue("");
		}
		
		if(values[24]!=null)
		{
		row.createCell(24).setCellValue(String.valueOf(values[24]));
		}
		else
		{
			row.createCell(24).setCellValue("");
		}
		
		if(values[25]!=null)
		{
		row.createCell(25).setCellValue(String.valueOf(values[25]));
		}
		else
		{
			row.createCell(25).setCellValue("");
		}
		
		if(values[26]!=null)
		{
		row.createCell(26).setCellValue(String.valueOf(values[26]));
		}
		else
		{
			row.createCell(26).setCellValue("");
		}
		
		if(values[27]!=null)
		{
		row.createCell(27).setCellValue(String.valueOf(values[27]));
		}
		else
		{
			row.createCell(27).setCellValue("");
		}
		
		if(values[28]!=null)
		{
		row.createCell(28).setCellValue(String.valueOf(values[28]));
		}
		else
		{
			row.createCell(28).setCellValue("");
		}
		
		if(values[29]!=null)
		{
		row.createCell(29).setCellValue(String.valueOf(values[29]));
		}
		else
		{
			row.createCell(29).setCellValue("");
		}
		
		if(values[30]!=null)
		{
		row.createCell(30).setCellValue(String.valueOf(values[30]));
		}
		else
		{
			row.createCell(30).setCellValue("");
		}
		
		if(values[31]!=null)
		{
		row.createCell(31).setCellValue(String.valueOf(values[31]));
		}
		else
		{
			row.createCell(31).setCellValue("");
		}
		
		if(values[32]!=null)
		{
		row.createCell(32).setCellValue(String.valueOf(values[32]));
		}
		else
		{
			row.createCell(32).setCellValue("");
		}
		
		if(values[33]!=null)
		{
		row.createCell(33).setCellValue(String.valueOf(values[33]));
		}
		else
		{
			row.createCell(33).setCellValue("");
		}
		
		if(values[34]!=null)
		{
		row.createCell(34).setCellValue(String.valueOf(values[34]));
		}
		else
		{
			row.createCell(34).setCellValue("");
		}
		
		if(values[35]!=null)
		{
		row.createCell(35).setCellValue(String.valueOf(values[35]));
		}
		else
		{
			row.createCell(35).setCellValue("");
		}
		
		if(values[36]!=null)
		{
		row.createCell(36).setCellValue(String.valueOf(values[36]));
		}
		else
		{
			row.createCell(36).setCellValue("");
		}
		
		if(values[37]!=null)
		{
		row.createCell(37).setCellValue(String.valueOf(values[37]));
		}
		else
		{
			row.createCell(37).setCellValue("");
		}
		
		if(values[38]!=null)
		{
		row.createCell(38).setCellValue(String.valueOf(values[38]));
		}
		else
		{
			row.createCell(38).setCellValue("");
		}
		
		if(values[39]!=null)
		{
		row.createCell(39).setCellValue(String.valueOf(values[39]));
		}
		else
		{
			row.createCell(39).setCellValue("");
		}
		
		if(values[40]!=null)
		{
		row.createCell(40).setCellValue(String.valueOf(values[40]));
		}
		else
		{
			row.createCell(40).setCellValue("");
		}
		
		
		if(values[41]!=null)
		{
		row.createCell(41).setCellValue(String.valueOf(values[41]));
		}
		else
		{
			row.createCell(41).setCellValue("");
		}
		
		if(values[42]!=null)
		{
		row.createCell(42).setCellValue(String.valueOf(values[42]));
		}
		else
		{
			row.createCell(42).setCellValue("");
		}
		
		if(values[43]!=null)
		{
		row.createCell(43).setCellValue(String.valueOf(values[43]));
		}
		else
		{
			row.createCell(43).setCellValue("");
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
	response.setHeader("Content-Disposition", "inline;filename=\"HTReadingDetails_"+circle);
	FileInputStream input = new FileInputStream(fileName);
	IOUtils.copy(input, servletOutputStream);
	//servletOutputStream.w
	servletOutputStream.flush();
	servletOutputStream.close();
		
		
	return null;
		
	}
	
	//circle wise report
	@RequestMapping(value="/circleWiseReport",method={RequestMethod.GET,RequestMethod.POST})
	public String circleWiseReport(ModelMap model)
	{
		int rdngmonth= rdngMonthService.findAll();
		
		return "CircleWiseReport";
	}
	
	@RequestMapping(value="/btopFirstTableData",method={RequestMethod.GET,RequestMethod.POST})
	public String circleWiseBtopReport(ModelMap model)
	{
		int rdngmonth= rdngMonthService.findAll();
		
		List result= meterMasterService.getCirWiseReport(rdngmonth);
	
		if(result.size() > 0)
		{
			model.addAttribute("Circledata", result);
			model.addAttribute("btopShow", "btopShow");
		}
		else
		{
			model.addAttribute("btopError", "BTOP MRWise Data Not Found...");
		}
		return "CircleWiseReport";
	}
	

@RequestMapping(value = "/getCircleWiseMeters", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody  List getNumberOfInstaltionDetails(HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		String circle=request.getParameter("circle");
		String mmrname=request.getParameter("mmrname");
		System.out.println("getCircleWiseMeters  :::::::::Controller cir"+circle+"-- mrname"+mmrname);
		List resultList=null;
		int rdngmonth= rdngMonthService.findAll();
		resultList=meterMasterService.getCircleWiseMeters(circle.trim(), mmrname.trim(),rdngmonth);
		System.out.println(resultList.size());
		
		return resultList;

	}

	
	
	@RequestMapping(value = "/btopSecondTable", method = {RequestMethod.GET,RequestMethod.POST})
	public String secondBtopMethod(HttpServletResponse response,HttpServletRequest request,ModelMap model) 
	{
		String circle=request.getParameter("circle");
		String mmrname=request.getParameter("mmrname");
		System.out.println("getCircleWiseMeters  :::::::::Controller cir"+circle+"-- mrname"+mmrname);
		List secondBtopList=null;
		int rdngmonth= rdngMonthService.findAll();
		secondBtopList=meterMasterService.getCircleWiseSecondBtopData(circle.trim(),rdngmonth);
		
		List result= meterMasterService.getCirWiseReport(rdngmonth);
		
			model.addAttribute("Circledata", result);
			model.addAttribute("btopShow", "btopShow");
			
		if(secondBtopList.size() > 0)
		{
			model.put("secondBtopList", secondBtopList);
			//model.put("secondBtopShow", "secondBtopShow");
			model.addAttribute("second", "second");
			model.addAttribute("BTOPcircle", circle);
		}
		else
		{
			model.addAttribute("secondBtopError", "BTOP MRWise Data Not Found...");
		}
		
		return "CircleWiseReport";

	}
	
	
	
	
	//360
	//circle wise report
		@RequestMapping(value="/Billingdata360",method={RequestMethod.GET,RequestMethod.POST})
		public String Billingdata360(ModelMap model)
		{
			int rdngmonth= rdngMonthService.findAll();
			
			return "Billingdata360";
		}
		
		
		@RequestMapping(value="/Billingdata360TableData/{meterno}",method={RequestMethod.GET,RequestMethod.POST})
		public String BillingdataXMLImport(@PathVariable String meterno,ModelMap model,HttpServletRequest request,HttpServletResponse response)
		{
			
			System.out.println("inside billdata 360");
			
			int rdngmonth= rdngMonthService.findAll();
			//System.out.println("month-->"+month);
			System.out.println("metrno-->"+meterno);
			
			return "Billingdata360";
		}
		
		/*@RequestMapping(value="/Billingdata360TableData",method={RequestMethod.GET,RequestMethod.POST})
		public String BillingdataXMLImport(ModelMap model,HttpServletRequest request,HttpServletResponse response)
		{
			
			System.out.println("inside billdata 360");
			
			int rdngmonth= rdngMonthService.findAll();
			
			
			return "Billingdata360";
		}
	*/
		

		
		//ASCII REPORT
		
		@RequestMapping(value="/asciiReportHT",method={RequestMethod.GET,RequestMethod.POST})
		public String asciiReportHT(ModelMap model)
		{
			model.put("readingMonth",rdngMonthService.findAll());
			List<?> circle=masterService.getALLCircle();
			model.addAttribute("circle",circle);
			
			int month=rdngMonthService.findAll();
			String rdngMonth=Integer.toString(month);
			/*List<?> htlList=meterMasterService.htSummary(rdngMonth);
			if(htlList.size()>0)
			{
			model.addAttribute("showhtReading","showhtReading");
			model.put("htlList",htlList);
			}*/
			
			return "asciiReport";
		}
		
		@RequestMapping(value="/generateAsciiReport/{param}",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody String generateAsciiReport(@PathVariable int param, HttpServletRequest request,ModelMap model,HttpServletResponse response)
		{
			System.out.println("inside generate ascii method="+param);
			int acclength=param;
			model.put("readingMonth",rdngMonthService.findAll());
			List<?> circle=masterService.getALLCircle();
			model.addAttribute("circle",circle);
			String circle1=request.getParameter("circle");
			String rdngMonth=request.getParameter("reportsMonth");
			
			System.out.println("In HT ASCII Reports rdngMonth-----"+rdngMonth+"circle----"+circle1);
			
			String value=meterMasterService.gethtReadingDataAscii(rdngMonth,circle1,acclength,response);
			if(value.equals("nodata"))
			{
				System.out.println("inside nodata");
				model.addAttribute("htReadingerror","HT Reading Data Not Found...");
			}

			List<?> htlList=meterMasterService.htSummary(rdngMonth);
			if(htlList.size()>0)
			{
			model.addAttribute("showhtReading","showhtReading");
			model.put("htlList",htlList);
			}
			
			return "asciiReport";
		}
	
}

	

