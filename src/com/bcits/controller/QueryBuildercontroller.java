package com.bcits.controller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.SqlEditorExecutedQry;
import com.bcits.mdas.service.QueryBuilderService;
import com.bcits.service.SqlEditorSaveService;

@Controller
public class QueryBuildercontroller {
	
	@Autowired
	private  QueryBuilderService  querybuilderservice;
	
	@Autowired
	private SqlEditorSaveService sqlEditorSaveService;
	
	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;
	
	@RequestMapping(value="/querybuilder",method={RequestMethod.GET,RequestMethod.POST})
	public String querybuilder(HttpServletRequest request,ModelMap model,HttpSession session)
	{
		/*try 
		{
			String qry=request.getParameter("sqlqry");
		    System.out.println(qry);
		    if(qry !=null)
		    {
		    	List<?> data= querybuilderservice.getDataList(request);
		    	List<?>	colunmName=(List<?>) data.get(0);
		    	List<?>	AttributeValue=(List<?>) data.get(1);
		    	if(AttributeValue.size()>0)
		    	{
		    		model.put("colunmName", colunmName);
		    		model.put("dataList", AttributeValue);
		    	}
		    	else
		    	{
		    		model.put("result","NO result For this Query");
		    	}
		    }
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}*/
		
		
		// fetching all the saved query
		String executed_by=(String) session.getAttribute("username");
				
		List<SqlEditorExecutedQry> list=sqlEditorSaveService.findAllSavedQuery(executed_by);
		model.addAttribute("list", list);
		
		
		
		model.put("schemalist", querybuilderservice.getSchemaList());	
		return "querybuilder";
	}
	@RequestMapping(value="/getTableList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getTableList(HttpServletRequest request,ModelMap model)
	{
		return querybuilderservice.getTableList(request);
	}
	@RequestMapping(value="/getColumnList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getColumnList(@RequestParam String tableName,@RequestParam String schemaName,HttpServletRequest request,ModelMap model)
	{
		return querybuilderservice.getColumnList(tableName,schemaName);
	}
	
	@RequestMapping(value="/getQueryBuilderData",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getquerybuilderData(HttpServletRequest request)
	{
		List<?> resultlist=new ArrayList<>();
		try 
		{
			String qry=request.getParameter("sqlqry");
		    System.out.println(qry);
		    if(qry !=null)
		    {
		    	resultlist= querybuilderservice.getDataList(request);
		    }
		} catch (Exception e)
		{
			List<Object> l1=new ArrayList<>();
			l1.add("ERROR");
			return l1;
		}
		return resultlist;
	}
	
	
	
	// saving query data **********
	@RequestMapping(value="/saveQuery",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String saveQueryData(HttpServletRequest request)
	{
	
		HttpSession session = request.getSession(false);
		String reportname=request.getParameter("reportname");
		String qry=request.getParameter("qry");
		String schemaName=request.getParameter("schemaName");
		
		SqlEditorExecutedQry sqlsave=new SqlEditorExecutedQry();
		System.out.println("report name==== "+reportname);
		
		System.out.println("report name==== "+qry);
		
		String username=(String) session.getAttribute("username");
		Timestamp timestamp=new Timestamp(System.currentTimeMillis());
		
		System.out.println(username+ "  --  "+timestamp);
		
		
		
		
		sqlsave.setReport_name(reportname);
		sqlsave.setSchema_name(schemaName);
		sqlsave.setSql_qry(qry);
		sqlsave.setExecuted_by(username);
		sqlsave.setExecuted_date(timestamp);
		try {
			String rep=sqlEditorSaveService.findByReportName(reportname);
			if(rep !=null) {
				return "Report Name Already Exit";
			}
			else{
	     	sqlEditorSaveService.customSave(sqlsave);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "Problem While Saving";
		}
		
		
		
		
		return "Saved Successfully !";
		
		
	}
	
	@RequestMapping(value="/getQueryBuilderbyRpt_name",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getQueryBuilderbyRpt_name(HttpServletRequest request,HttpSession session,ModelMap model)
	{
		
		// fetching all the saved query
		
		//String username=(String) session.getAttribute("username");

		
		String rep=sqlEditorSaveService.findSavedQuerybyRpt_name(request.getParameter("report_name"));
		

		return rep;

		}
	
	@RequestMapping(value = "/dtdashboardReports", method = { RequestMethod.GET, RequestMethod.POST })
	public String dtdashboardReports(HttpServletRequest request, ModelMap model) {

		String qryRegion = "SELECT DISTINCT report_name  FROM meter_data.sql_editor_query ORDER BY report_name ";
		List<?> report_name = entityManager.createNativeQuery(qryRegion).getResultList();

		model.put("report_name", report_name);

		return "dtdashboardReports";

	}
	
	@RequestMapping(value = "/dtdashboardDetailedReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String dtdashboardDetailedReport(HttpServletRequest request, ModelMap model) {

		return "dtdashboardReportsForCirclewise";

	}
	
	
	
	@RequestMapping(value = "/dtpowerfailureReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String dtpowerfailureReport(HttpServletRequest request, ModelMap model) {

		return "dtpowerfailureForCirclewise";

	}
	
	
	@RequestMapping(value="/getdtpowerfailureReport",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getdtpowerfailureReport(HttpServletRequest request,HttpServletResponse response)
	{
		List<?> resultlist=new ArrayList<>();
		
		String region = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String reportId = request.getParameter("report");
		String reportIdPeriod = request.getParameter("reportIdPeriod");
		 
		
		resultlist =  querybuilderservice.getDtpowerfailureReports(region,circle,reportId,reportIdPeriod);
		
		return resultlist;
	}
	
	@RequestMapping(value = "/dtcommReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String dtcommReport(HttpServletRequest request, ModelMap model) {

		return "dtcommForCirclewise";

	}
	
	
	
	@RequestMapping(value="/getdtcommReport",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> dtcommReport(HttpServletRequest request,HttpServletResponse response){
			List<?> resultlist=new ArrayList<>();
		
			String zone=request.getParameter("zone");
			String circle = request.getParameter("circle");
			String subdiv = request.getParameter("subdiv");
			String division = request.getParameter("division");
			String town = request.getParameter("town");
		
		resultlist =  querybuilderservice.getdtcommReport(zone,circle,town);
		
		return resultlist;
	}
	
	
	@RequestMapping(value = "/showloadphaseReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String showloadphaseReport(HttpServletRequest request, ModelMap model) {

		return "showloadphaseID";

	}
	
	
	

	@RequestMapping(value="/getdtloadphasevr",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getdtloadphasevr(HttpServletRequest request,HttpServletResponse response){
			List<?> resultlist=new ArrayList<>();
		
			String zone=request.getParameter("zone");
			String circle = request.getParameter("circle");
			String rdngmnth = request.getParameter("rdngmnth");
		
			resultlist =  querybuilderservice.getdtloadphasevr(zone,circle,rdngmnth);
		return resultlist;
	}
	
	
	@RequestMapping(value="/getdtloadphasevy",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getdtloadphasevy(HttpServletRequest request,HttpServletResponse response){
			List<?> resultlist=new ArrayList<>();
		
			String zone=request.getParameter("zone");
			String circle = request.getParameter("circle");
			String rdngmnth = request.getParameter("rdngmnth");
		
			resultlist =  querybuilderservice.getdtloadphasevy(zone,circle,rdngmnth);
		
		return resultlist;
	}
	
	
	@RequestMapping(value="/getdtloadphasevb",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getdtloadphasevb(HttpServletRequest request,HttpServletResponse response){
			List<?> resultlist=new ArrayList<>();
		
			String zone=request.getParameter("zone");
			String circle = request.getParameter("circle");
			String rdngmnth = request.getParameter("rdngmnth");
			resultlist =  querybuilderservice.getdtloadphasevb(zone,circle,rdngmnth);
			
			return resultlist;
	}
	
	@RequestMapping(value = "/dataAvailabilityReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String dataAvailabilityReport(HttpServletRequest request, ModelMap model) {

		return "dataAvailability";

	}
	@RequestMapping(value = "/getDTDataAvailability", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDTHealthData(HttpServletRequest request) {
		List<?> list = new ArrayList<>();
		
		String region = request.getParameter("zone");
		String subdiv = request.getParameter("subdiv");
		String circle = request.getParameter("circle");
		String fromDate = request.getParameter("fromDate");
	
		String toDate = request.getParameter("toDate");
		
		String division = request.getParameter("division");
		String town = request.getParameter("town");
	//	System.out.println(town);
		String towncode=request.getParameter("town");
		list = querybuilderservice.getDTDataAvailability(region,subdiv, circle,fromDate,toDate,  division,town,towncode);
		return list;
	}
	
	@RequestMapping(value = "/getDTDataAvailabilitybill", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDTDataAvailabilitybill(HttpServletRequest request) {
		List<?> list = new ArrayList<>();
		
		String region = request.getParameter("zone");
		String subdiv = request.getParameter("subdiv");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String fromDate = request.getParameter("fromDate");
	
		String toDate = request.getParameter("toDate");
		
		String division = request.getParameter("division");
		
	//	System.out.println(town);
		String towncode=request.getParameter("town");
		list = querybuilderservice.getDTDataAvailabilitybill(region,subdiv, circle,fromDate,toDate,  division,town,towncode);
		return list;
	}
	@RequestMapping(value = "/getDTDataAvailabilityconsumption", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDTDataAvailabilityconsumption(HttpServletRequest request) {
		List<?> list = new ArrayList<>();
		
		String region = request.getParameter("zone");
		String subdiv = request.getParameter("subdiv");
		String circle = request.getParameter("circle");
		String fromDate = request.getParameter("fromDate");
	
		String toDate = request.getParameter("toDate");
		
		String division = request.getParameter("division");
		String town = request.getParameter("town");
	//	System.out.println(town);
		String towncode=request.getParameter("town");
		list = querybuilderservice.getDTDataAvailabilityconsumption(region,circle,fromDate,toDate);
		return list;
	}
	
	@RequestMapping(value = "/loadataAvailabilityReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String loadataAvailabilityReport(HttpServletRequest request, ModelMap model) {

		return "loadataAvailability";

	}
	
	
	@RequestMapping(value = "/getDTDataAvailabilityload", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDTHealthDataload(HttpServletRequest request) {
		List<?> list = new ArrayList<>();
		
		String region = request.getParameter("zone");
		String subdiv = request.getParameter("subdiv");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String fromDate = request.getParameter("fromDate");
	
		String toDate = request.getParameter("toDate");
		
		String division = request.getParameter("division");
		
	//	System.out.println(town);
		String towncode=request.getParameter("town");
		list = querybuilderservice.getDTDataAvailabilityload(region,subdiv, circle,fromDate, toDate,division,town,towncode);
		return list;
	}
	
	@RequestMapping(value = "/instdataAvailabilityReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String instdataAvailabilityReport(HttpServletRequest request, ModelMap model) {

		return "instdataAvailability";

	}
	
	
	@RequestMapping(value = "/getDTDataAvailabilityinst", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDTDataAvailabilityinst(HttpServletRequest request) {
		List<?> list = new ArrayList<>();
		
		String region = request.getParameter("zone");
		String subdiv = request.getParameter("subdiv");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String fromDate = request.getParameter("fromDate");
	
		String toDate = request.getParameter("toDate");
		
		String division = request.getParameter("division");
		
	//	System.out.println(town);
		String towncode=request.getParameter("town");
		list = querybuilderservice.getDTDataAvailabilityinst(region,subdiv, circle,fromDate,toDate,  division,town,towncode);
		return list;
	}
	
	
	@RequestMapping(value="/DailyToExcelRep",method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object DailyToExcelRep(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		
		
							try {
								String region = request.getParameter("region");
								String circle = request.getParameter("circle");
								String fromDate = request.getParameter("fromDate");
								String toDate = request.getParameter("toDate");
								
								
								if (region.equalsIgnoreCase("ALL")){
									region="%";
								}
								
								if (circle.equalsIgnoreCase("ALL")){
									circle="%";
								}
								if (fromDate.equalsIgnoreCase("ALL")){
									fromDate="%";
								} 
								if (toDate.equalsIgnoreCase("ALL")){
									toDate="%";
								}
								String fileName = "DailyToExcelRep";
								XSSFWorkbook wb = new XSSFWorkbook();
								XSSFSheet sheet = wb.createSheet(fileName);
								XSSFRow header = sheet.createRow(0);
								
								CellStyle lockedCellStyle = wb.createCellStyle();
								lockedCellStyle.setLocked(true);
								CellStyle unlockedCellStyle = wb.createCellStyle();
								unlockedCellStyle.setLocked(false);
								
								
								XSSFCellStyle style = wb.createCellStyle();
								style.setWrapText(true);
								sheet.setColumnWidth(0, 1000);
								XSSFFont font = wb.createFont();
								font.setFontName(HSSFFont.FONT_ARIAL);
								font.setFontHeightInPoints((short) 10);
								font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
								style.setFont(font);
								
								
								header.createCell(0).setCellValue("SI.No"); 
								header.createCell(1).setCellValue("Meter Make");
								header.createCell(2).setCellValue("Total Mapped Meters");
								header.createCell(3).setCellValue("Constantly Communicated Meters");
								header.createCell(4).setCellValue("Communication Percentage");
							
							
								List<?> resultlist = null;
								resultlist = querybuilderservice.getDTDataAvailabilityconsumption(region,circle,fromDate,toDate);
								
								int count=1;
								//int cellNO = 0;
								for (Iterator<?> iterator = resultlist.iterator(); iterator.hasNext();) {
									final Object[] values = (Object[]) iterator.next();
									XSSFRow row = sheet.createRow(count);
									//XSSFRow row = sheet.createRow(count);
									//cellNO++;
									//row.createCell(0).setCellValue(String.valueOf(cellNO + ""));
									if(values[0] == null) {
									row.createCell(0).setCellValue(String.valueOf(""));
									}else {
									row.createCell(0).setCellValue(String.valueOf(count));
									}
									if (values[0] == null) {
										row.createCell(1).setCellValue(String.valueOf(""));
									} else {
										row.createCell(1).setCellValue(String.valueOf(values[0]));
									}
									if (values[1] == null) {
										row.createCell(2).setCellValue(String.valueOf(""));
									} else {
										row.createCell(2).setCellValue(String.valueOf(values[1]));
									}
									if (values[2] == null) {
										row.createCell(3).setCellValue(String.valueOf(""));
									} else {
										row.createCell(3).setCellValue(String.valueOf(values[2]));
									}
									if (values[3] == null) {
										row.createCell(4).setCellValue(String.valueOf(""));
									} else {
										row.createCell(4).setCellValue(String.valueOf(values[3]));
									}
											
									count++;
								}
								
								FileOutputStream fileOut = new FileOutputStream(fileName);
								wb.write(fileOut);
								fileOut.flush();
								fileOut.close();

								ServletOutputStream servletOutputStream;

								servletOutputStream = response.getOutputStream();
								response.setContentType("application/vnd.ms-excel");
								response.setHeader("Content-Disposition", "inline;filename=\"DailyToExcelRep.xlsx" + "\"");
								FileInputStream input = new FileInputStream(fileName);
								IOUtils.copy(input, servletOutputStream);
								servletOutputStream.flush();
								servletOutputStream.close();

								return null;
								
							}catch(Exception e) {
								e.printStackTrace();
							}
							return null;
		
	}
	
	@RequestMapping(value="/totalinstanceDetails",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> totalinstanceDetails(HttpServletRequest request,HttpServletResponse response)
	{
		List<?> resultlist=new ArrayList<>();
		
		String dttpid = request.getParameter("dttpid");

		String region = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String reportId = request.getParameter("report");
		String reportIdPeriod = request.getParameter("reportIdPeriod"); 
		String meterno = request.getParameter("meterno");
		
		resultlist =  querybuilderservice.getTotalinstanceDetails(dttpid,region, circle, reportId, reportIdPeriod,meterno);
		
		return resultlist;
	}

	@RequestMapping(value="/getdtanditsInstances",method={RequestMethod.GET,RequestMethod.POST})
public @ResponseBody List<?> getdtanditsInstances(HttpServletRequest request,HttpServletResponse response)
{
	List<?> resultlist=new ArrayList<>();
	
	String region = request.getParameter("zone");
	String circle = request.getParameter("circle");
	String reportId = request.getParameter("report");
	String reportIdPeriod = request.getParameter("reportIdPeriod");
	 
	
	resultlist =  querybuilderservice.getDtdashboardReports(region,circle,reportId,reportIdPeriod);
	
	return resultlist;
}

	@RequestMapping(value="/getQueryBuilderDataw",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getquerybuilderDataw(HttpServletRequest request)
	{
		List<?> resultlist=new ArrayList<>();
		try 
		{
			String qry=request.getParameter("sqlqry");
		    System.out.println(qry);
		    if(qry !=null)
		    {
		    	resultlist= querybuilderservice.getCustomList(request);
		    }
		} catch (Exception e)
		{
			List<Object> l1=new ArrayList<>();
			l1.add("ERROR");
			return l1;
		}
		return resultlist;
	}

	
	
}

