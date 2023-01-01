package com.bcits.mdas.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//import sun.misc.BASE64Decoder;

import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.service.BusinessRoleService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.LoadSurveyMonthlyConsumptionService;

import com.bcits.mdas.utility.FilterUnit;
import com.bcits.service.MeterMasterService;
import com.bcits.service.PfcService;
import com.crystaldecisions.reports.queryengine.Session;

@Controller
public class EnergyAccountingController {
	@Autowired
	BusinessRoleService businessRoleService;

	@Autowired
	FeederMasterService feederMasterService;

	@Autowired
	private MeterMasterService meterMasterService;

	@Autowired
	private PfcService pfcservice;

	@Autowired
	private LoadSurveyMonthlyConsumptionService loadSurveymonthlyConService;

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@PersistenceContext(unitName = "POSTGREDataSource")
	private EntityManager postgresMdas;

	/*
	 * @RequestMapping(value="/energyAccountingDTLevel",method={RequestMethod.POST,
	 * RequestMethod.GET}) public String underDevelopment(ModelMap
	 * model,HttpServletRequest request) {
	 * 
	 * String qry
	 * ="select DISTINCT(subdivision),sitecode from meter_data.amilocation"; List<?>
	 * resultList = null;
	 * 
	 * try { resultList = (List<?>)
	 * businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(
	 * qry).getResultList(); } catch (Exception e) { e.printStackTrace(); }
	 * model.addAttribute("resultList", resultList);
	 * 
	 * return "energyAccountingDTLevel"; }
	 */

	// New method for Jasper Dt ea report
	@RequestMapping(value = "/energyAccountingDTLevel", method = { RequestMethod.POST, RequestMethod.GET })
	public String energyAccountingDTLevel(ModelMap model, HttpServletRequest request) {
		String qry;
		String qryRegion;
		Object res;
		List<?> circleList = new ArrayList<>();
		List<?> zoneList = new ArrayList<>();

		// circles=consumermasterService.getCircle();
		// model.put("circles", circles);

		qryRegion = "SELECT DISTINCT ZONE  FROM meter_data.amilocation ORDER BY ZONE ";
		qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation ORDER BY CIRCLE ";
		circleList = entityManager.createNativeQuery(qry).getResultList();
		zoneList = entityManager.createNativeQuery(qryRegion).getResultList();
		model.put("circles", circleList);
		model.put("zoneList", zoneList);

		return "eaDTWiseJasper";
	}

	// New method for Jasper Dt ea report
	@RequestMapping(value = "/energyAccountingMultiple", method = { RequestMethod.POST, RequestMethod.GET })
	public String energyAccountingMultiple(ModelMap model, HttpServletRequest request) {
		String qry;
		String qryRegion;
		Object res;
		List<?> circleList = new ArrayList<>();
		List<?> zoneList = new ArrayList<>();

		// circles=consumermasterService.getCircle();
		// model.put("circles", circles);

		qryRegion = "SELECT DISTINCT ZONE  FROM meter_data.amilocation ORDER BY ZONE ";
		qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation ORDER BY CIRCLE ";
		circleList = entityManager.createNativeQuery(qry).getResultList();
		zoneList = entityManager.createNativeQuery(qryRegion).getResultList();
		model.put("circles", circleList);
		model.put("zoneList", zoneList);

		return "eaMultipleMonthsFeederandDT";
	}

	@RequestMapping(value = "/dtlosses", method = { RequestMethod.POST, RequestMethod.GET })
	public String dtlosses(ModelMap model, HttpServletRequest request) {

		return "dtlosses";
	}

	@RequestMapping(value = "/fdrlosses", method = { RequestMethod.POST, RequestMethod.GET })
	public String fdrlosses(ModelMap model, HttpServletRequest request) {

		return "fdrlosses";
	}

//	@RequestMapping(value = "/getfdrlossdetails", method = { RequestMethod.GET, RequestMethod.POST })
//	public @ResponseBody HashMap<String, List<?>> getfdrlossdetails(HttpServletRequest request) {
//		String month = request.getParameter("fromdate");
//		String feedercode = request.getParameter("feederTpId");
//		String towncode = request.getParameter("town");
//		//String period = request.getParameter("periodMonth");
//		List<?> getfdrcount = loadSurveymonthlyConService.getfdrcount(month, towncode);
//		List<?> getfdrlossdetails = loadSurveymonthlyConService.getfdrlossdetails(month, towncode, feedercode);
//		HashMap<String, List<?>> hm = new HashMap<String, List<?>>();
//		hm.put("fdrcount", getfdrcount);
//		hm.put("getfdrlossdetails", getfdrlossdetails);
//
//		return hm;
//
//	}

	/*
	 * @RequestMapping(value="/getfdrlossdetailsInfo", method= {RequestMethod.POST,
	 * RequestMethod.GET}) public @ResponseBody HashMap<String, List<?>>
	 * getfdrlossdetailsInfo(HttpServletRequest request){
	 * 
	 * String month = request.getParameter("fromdate"); String feedercode =
	 * request.getParameter("feederTpId"); String towncode =
	 * request.getParameter("town"); String period =
	 * request.getParameter("periodMonth");
	 * 
	 * @SuppressWarnings("unused") List<?> getfdrlossdetailsInfo =
	 * loadSurveymonthlyConService.getfdrlossdetailsInfo(month, period, towncode,
	 * feedercode); HashMap<String, List<?>> hm = new HashMap<String, List<?>>();
	 * 
	 * hm.put("getfdrlossdetailsInfo", getfdrlossdetailsInfo); return hm;
	 * 
	 * }
	 */

	@RequestMapping(value = "/getfdrlossdetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getfdrlossdetails(HttpServletRequest request, HttpServletResponse response) {

		List<?> resultlist = new ArrayList<>();
		String month = request.getParameter("fromdate");
		String feedercode = request.getParameter("feederTpId");
		String towncode = request.getParameter("town");
		// String period = request.getParameter("periodMonth");

		resultlist = loadSurveymonthlyConService.getfdrlossdetails(month, towncode, feedercode);

		return resultlist;

	}

	@RequestMapping(value = "/getfdrlossdetailsInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getfdrlossdetailsInfo(HttpServletRequest request, HttpServletResponse response) {

		List<?> resultlist = new ArrayList<>();
		String month = request.getParameter("fromdate");
		String feedercode = request.getParameter("feederTpId");
		String towncode = request.getParameter("town");
		// String period = request.getParameter("periodMonth");

		resultlist = loadSurveymonthlyConService.getfdrlossdetailsInfo(month, towncode, feedercode);

		return resultlist;

	}
	
	@RequestMapping(value="/FeederDetailsExcelHtlosses",method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object FeederDetailsExcelHtlosses(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		
		
							try {
								
								String month = request.getParameter("fromdate");
								String feedercode = request.getParameter("feederTpId");
								String towncode = request.getParameter("town");
								
								
							
								if (month.equalsIgnoreCase("ALL")){
									month="%";
								}
						  		if (feedercode.equalsIgnoreCase("ALL")){
						  			feedercode="%";
								}
								if (towncode.equalsIgnoreCase("ALL")){
									towncode="%";
								}
								
								String fileName = "FeederDetailsExcelHtlosses";
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
								header.createCell(1).setCellValue("zone");
								header.createCell(2).setCellValue("circle");
								header.createCell(3).setCellValue("town_ipds");
								header.createCell(4).setCellValue("tp_town_code");
								header.createCell(5).setCellValue("Feeder Code");
								header.createCell(6).setCellValue("Feeder Name");
								header.createCell(7).setCellValue("Meter_number");
								header.createCell(8).setCellValue("Consumption (Kwh)");
								header.createCell(9).setCellValue("Import");      
								header.createCell(10).setCellValue("Export");
								header.createCell(11).setCellValue("Net");
							
								List<?> resultlist = null;
								resultlist = loadSurveymonthlyConService.getfdrlossdetailsInfo(month,towncode,feedercode);
								
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
									if (values[4] == null) {
										row.createCell(5).setCellValue(String.valueOf(""));
									} else {
										row.createCell(5).setCellValue(String.valueOf(values[4]));
									}
									
									if (values[5] == null) {
										row.createCell(6).setCellValue(String.valueOf(""));
									} else {
										row.createCell(6).setCellValue(String.valueOf(values[5]));
									}
									if (values[6] == null) {
										row.createCell(7).setCellValue(String.valueOf(""));
									} else {
										row.createCell(7).setCellValue(String.valueOf(values[6]));
									}
									if (values[7] == null) {
										row.createCell(8).setCellValue(String.valueOf(""));
									} else {
										row.createCell(8).setCellValue(String.valueOf(values[7]));
									}
									if (values[8] == null) {
										row.createCell(9).setCellValue(String.valueOf(""));
									} else {
										row.createCell(9).setCellValue(String.valueOf(values[8]));
									}
									if (values[9] == null) {
										row.createCell(10).setCellValue(String.valueOf(""));
									} else {
										row.createCell(10).setCellValue(String.valueOf(values[9]));
									}
									
									if (values[10] == null) {
										row.createCell(11).setCellValue(String.valueOf(""));
									} else {
										row.createCell(11).setCellValue(String.valueOf(values[10]));
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
								response.setHeader("Content-Disposition", "inline;filename=\"FeederDetailsExcelHtlosses.xlsx" + "\"");
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
	
	
	@RequestMapping(value="/dtDetailsExcelHtlosses",method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object dtDetailsExcelHtlosses(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		
		
							try {
								
								String month = request.getParameter("fromdate");
								String feedercode = request.getParameter("feederTpId");
								String towncode = request.getParameter("town");
								
								
							
								if (month.equalsIgnoreCase("ALL")){
									month="%";
								}
						  		if (feedercode.equalsIgnoreCase("ALL")){
						  			feedercode="%";
								}
								if (towncode.equalsIgnoreCase("ALL")){
									towncode="%";
								}
								
								String fileName = "dtDetailsExcelHtlosses";
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
								header.createCell(1).setCellValue("zone");
								header.createCell(2).setCellValue("circle");
								header.createCell(3).setCellValue("town_ipds");
								header.createCell(4).setCellValue("tp_town_code");
								header.createCell(5).setCellValue("dtCode");
								header.createCell(6).setCellValue("Dt_Name");
								header.createCell(7).setCellValue("Meter_number");
								header.createCell(8).setCellValue("Consumption");
								
						
							
								List<?> resultlist = null;
								resultlist = loadSurveymonthlyConService.getdtlossdetailsInfo(month,towncode,feedercode);
								
								int count=1;
								//int cellNO = 0;
								for (Iterator<?> iterator = resultlist.iterator(); iterator.hasNext();) {
									final Object[] values = (Object[]) iterator.next();
									XSSFRow row = sheet.createRow(count);
									//XSSFRow row = sheet.createRow(count);
									//cellNO++;
									//row.createCell(0).setCellValue(String.valueOf(cellNO + ""));
									if(values[1] == null) {
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
									if (values[4] == null) {
										row.createCell(4).setCellValue(String.valueOf(""));
									} else {
										row.createCell(4).setCellValue(String.valueOf(values[4]));
									}
									if (values[7] == null) {
										row.createCell(5).setCellValue(String.valueOf(""));
									} else {
										row.createCell(5).setCellValue(String.valueOf(values[7]));
									}
									if (values[8] == null) {
										row.createCell(6).setCellValue(String.valueOf(""));
									} else {
										row.createCell(6).setCellValue(String.valueOf(values[8]));
									}
									if (values[3] == null) {
										row.createCell(7).setCellValue(String.valueOf(""));
									} else {
										row.createCell(7).setCellValue(String.valueOf(values[3]));
									}
									if (values[10] == null) {
										row.createCell(8).setCellValue(String.valueOf(""));
									} else {
										row.createCell(8).setCellValue(String.valueOf(values[10]));
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
								response.setHeader("Content-Disposition", "inline;filename=\"dtDetailsExcelHtlosses.xlsx" + "\"");
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
	@RequestMapping(value = "/getdtlossdetailsInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getdtlossdetailsInfo(HttpServletRequest request, HttpServletResponse response) {
		List<?> resultlist = new ArrayList<>();

		String month = request.getParameter("fromdate");
		String feedercode = request.getParameter("feederTpId");
		String towncode = request.getParameter("town");
		// String period = request.getParameter("periodMonth");

		resultlist = loadSurveymonthlyConService.getdtlossdetailsInfo(month, towncode, feedercode);

		return resultlist;
	}

	@RequestMapping(value = "/getSubstationEA/{subdivision}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getSubstation(@PathVariable String subdivision, HttpServletRequest request,
			ModelMap model) {

		String qry = "select ss_name from meter_data.substation_details where office_id in (select sitecode from meter_data.amilocation where subdivision =  '"
				+ subdivision + "')  ";
		List<?> resultList = null;

		try {
			resultList = (List<?>) businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(qry)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;

	}

	@RequestMapping(value = "/getFeederdetails/{subdivision}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getFeederdetails(@PathVariable String subdivision, HttpServletRequest request,
			ModelMap model) {

		String qry = "select ss_name from meter_data.substation_details where office_id in (select sitecode from meter_data.amilocation where subdivision =  '"
				+ subdivision + "')  ";
		List<?> resultList = null;

		try {
			resultList = (List<?>) businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(qry)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;

	}

	@RequestMapping(value = "/getEneryAccoutingData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getEneryAccoutingData(@RequestParam String fromdate, @RequestParam String todate,
			@RequestParam String subdiv, @RequestParam String subStation, @RequestParam String feeder,
			@RequestParam String boundaryFeeder) {

		String sql = "SELECT A.*,round((1-(A.billing_efficiency/A.collection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
				+ "select mtr_sr_name,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,\n"
				+ "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n"
				+ "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n"
				+ "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" + "tech_loss,\n"
				+ "round(((tech_loss/unit_billed)*100),2) as tech_loss_perc,\n" + "time_stamp\n"
				+ "FROM meter_data.rpt_eadt_losses )A WHERE TO_CHAR(time_stamp, 'YYYY-MM-dd')='" + fromdate + "'";
		System.out.println(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql)
				.getResultList();
		return list;

	}

	@RequestMapping(value = "/feedrcommstatusrpt", method = { RequestMethod.POST, RequestMethod.GET })
	public String fdrcommstatusrpt(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Properties prop = new Properties();
		List<?> periodList = businessRoleService.getDistinctPeriod();

		/*
		 * model.put("discomName", prop.getProperty("discomName"));
		 * model.put("stateName", prop.getProperty("stateName"));
		 */

		model.put("periodList", periodList);
		String stateName = businessRoleService.getStateName();
		model.put("stateName", stateName);
		String discomName = businessRoleService.getDiscomName();
		model.put("discomName", discomName);
		return "fdrstatusreport";
	}

	@RequestMapping(value = "/showTownByScheme", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object showTownByScheme(@RequestParam String scheme, HttpServletRequest request,
			ModelMap model) {

		return businessRoleService.gettownByScheme(scheme);
	}

	@RequestMapping(value = "/getfdrcountData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getfdrcountData(@RequestParam String period, @RequestParam("town[]") String[] town,
			HttpServletRequest request, ModelMap model) {

		return businessRoleService.getfdrcountData(period, town);
	}

	/* Energey Accounting At DT Level Report */

	@RequestMapping(value = "/getEneryAccoutingDTLevelData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getEneryAccoutingDataDT(@RequestParam String fromdate, @RequestParam String todate,
			@RequestParam String subdiv, @RequestParam String subStation,
			@RequestParam("feederCode[]") String[] feederMultiple, @RequestParam String boundaryFeeder,
			@RequestParam String reportPeriod) {

		String FinalString = null;
		ArrayList<String> ae = new ArrayList<>();
		String testSmaple2 = null;
		int size = feederMultiple.length;
		String test = null;
		String[] splittest = null;
		String firsttest = null;
		String finalString = null;

		for (int i = 0; i <= size - 1; i++) {
			int x = size - 1;
			String[] splittest1 = feederMultiple[i].split("-");
			if (i == 0) {
				firsttest = "('";
				test = splittest1[0];
				finalString = firsttest + test + "')";
			} else if (i != x) {
				test = test + "','" + splittest1[0];
			} else {
				test = test + "','" + splittest1[0] + "')";
				finalString = firsttest + test;
			}

		}

		String sql = null;

		if (reportPeriod.equalsIgnoreCase("monthly")) {
			sql = "select DISTINCT * from\n" + "(select DISTINCT * from\n"
					+ " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
					+ "select tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,dt_id,\n"
					+ "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n"
					+ "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n"
					+ "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n"
					+ "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n"
					+ "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" + "tech_loss,\n"
					+ "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n"
					+ "time_stamp,mtr_sr_name,boundary_dt\n" + "FROM meter_data.rpt_eadt_losses )A WHERE month_year = '"
					+ fromdate + "'    AND boundary_dt = '" + boundaryFeeder
					+ "' AND dt_id IN ( SELECT dt_id FROM meter_data.dtdetails WHERE parentid in " + finalString
					+ "))X ,\n" + "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
					+ "WHERE Y.sitecode=X.office_id)AA,\n"
					+ "(select feedername, fdr_id,tp_fdr_id  from meter_data.feederdetails\n" + "WHERE fdr_id in "
					+ finalString + ")BB";

			/*
			 * "select DISTINCT * from\n" + "(select DISTINCT * from\n" +
			 * " (SELECT A.*,round((1-(A.billing_efficiency*A.collection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
			 * +
			 * "select tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,dt_id,\n"
			 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
			 * "round((amt_billed/amt_collected)*100,2) AS collection_efficiency,\n" +
			 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
			 * "tech_loss,\n" +
			 * "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" +
			 * "time_stamp,mtr_sr_name,boundary_dt\n" +
			 * "FROM meter_data.rpt_eadt_losses )A WHERE month_year = '"
			 * +fromdate+"' AND boundary_dt = '"
			 * +boundaryFeeder+"' AND dt_id IN ( SELECT dt_id FROM meter_data.dtdetails WHERE parentid in "
			 * +finalString+")))X ,\n" +
			 * "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
			 * + "WHERE Y.sitecode=X.office_id)AA,\n" +
			 * "(select feedername, fdr_id,tp_fdr_id  from meter_data.feederdetails\n" +
			 * "WHERE fdr_id in "+finalString+")BB";
			 */

			/*
			 * "SELECT A.*,round((1-(A.billing_efficiency/A.collection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
			 * +
			 * "select mtr_sr_name,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,\n"
			 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
			 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
			 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
			 * "tech_loss,\n" +
			 * "round(((tech_loss/unit_billed)*100),2) as tech_loss_perc,\n" +
			 * "time_stamp,office_id\n" +
			 * "FROM meter_data.rpt_eadt_losses )A WHERE TO_CHAR(time_stamp, 'YYYYMM')='"
			 * +fromdate+"' " +
			 * "and and   in (select sitecode from meter_data.amilocation where subdivision = '"
			 * +subdiv+"')   ";
			 */
		} else {
			sql = "select DISTINCT * from\n" + "(select DISTINCT * from\n"
					+ " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
					+ "select tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,dt_id,\n"
					+ "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n"
					+ "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n"
					+ "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n"
					+ "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n"
					+ "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" + "tech_loss,\n"
					+ "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n"
					+ "time_stamp,mtr_sr_name,boundary_dt\n"
					+ "FROM meter_data.rpt_eadt_losses )A WHERE month_year BETWEEN  '" + fromdate + "'   AND  '"
					+ todate + "' AND boundary_dt = '" + boundaryFeeder
					+ "' AND dt_id IN ( SELECT dt_id FROM meter_data.dtdetails WHERE parentid in " + finalString
					+ "))X ,\n" + "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
					+ "WHERE Y.sitecode=X.office_id)AA,\n"
					+ "(select feedername, fdr_id,tp_fdr_id  from meter_data.feederdetails\n" + "WHERE fdr_id in "
					+ finalString + ")BB";

			/*
			 * "SELECT A.*,round((1-(A.billing_efficiency/A.collection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
			 * +
			 * "select mtr_sr_name,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,\n"
			 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
			 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
			 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
			 * "tech_loss,\n" +
			 * "round(((tech_loss/unit_billed)*100),2) as tech_loss_perc,\n" +
			 * "time_stamp, office_id\n" +
			 * "FROM meter_data.rpt_eadt_losses )A WHERE TO_CHAR(time_stamp, 'YYYYMM') BETWEEN   '"
			 * +fromdate+"'   AND  '"+todate+"' " +
			 * "and office_id  in (select sitecode from meter_data.amilocation where subdivision = '"
			 * +subdiv+"')   ";
			 */
		}

		System.out.println("Energy Accounting DT Level ---> " + sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql)
				.getResultList();
		return list;

	}

	// Energy Accounting Feeder Level
	@RequestMapping(value = "/feederWiseEAreport", method = { RequestMethod.POST, RequestMethod.GET })
	public String feederWiseEAreport(ModelMap model, HttpServletRequest request) {

		/*
		 * String qry; Object res; List<?> circleList=new ArrayList<>(); //
		 * circles=consumermasterService.getCircle(); //model.put("circles", circles);
		 * 
		 * qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation "; circleList =
		 * entityManager.createNativeQuery(qry).getResultList(); model.put("circles",
		 * circleList);
		 * 
		 * 
		 * String qry ="select DISTINCT(subdivision) from meter_data.amilocation";
		 * List<?> resultList = null;
		 * 
		 * try { resultList = (List<?>)
		 * businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(
		 * qry).getResultList(); } catch (Exception e) { e.printStackTrace(); }
		 * model.addAttribute("resultList", resultList);
		 * 
		 * 
		 * return "energyAccountingFeederLevel";
		 */

		String qry;
		String qryRegion;
		Object res;
		List<?> circleList = new ArrayList<>();
		List<?> zoneList = new ArrayList<>();

		// circles=consumermasterService.getCircle();
		// model.put("circles", circles);

		qryRegion = "SELECT DISTINCT ZONE  FROM meter_data.amilocation ORDER BY ZONE";
		qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation  ORDER BY CIRCLE";
		circleList = entityManager.createNativeQuery(qry).getResultList();
		zoneList = entityManager.createNativeQuery(qryRegion).getResultList();
		model.put("circles", circleList);
		model.put("zoneList", zoneList);
		return "eaFeederWiseJasper";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getEneryAccoutingDataFeederWise", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getEneryAccoutingDataFeederWise(@RequestParam String fromdate,
			@RequestParam String todate, @RequestParam("town[]") String[] town, @RequestParam String reportPeriod,
			@RequestParam String subdivision, @RequestParam String circle, @RequestParam String division) {

		String whTownCode;
		String isAllSeelected = null;

		for (int i = 0; i <= town.length - 1; i++) {
			if (town[i].equalsIgnoreCase("All")) {
				isAllSeelected = "yes";
				break;
			} else {
				isAllSeelected = "no";
			}
		}
		if (isAllSeelected.equalsIgnoreCase("Yes")) {

			List<?> result = null;
			String towncodeqry;
			if (subdivision.equalsIgnoreCase("%")) {
				towncodeqry = " Select DISTINCT tp_towncode from meter_data.amilocation where  subdivision like '"
						+ subdivision + "'  ";
			} else {
				towncodeqry = " Select DISTINCT tp_towncode from meter_data.amilocation where  subdivision = '"
						+ subdivision + "' ";
			}
			whTownCode = "town_code in (" + towncodeqry + ")";

		} else {
			String FinalString = null;
			ArrayList<String> ae = new ArrayList<>();
			String testSmaple2 = null;
			int size = town.length;
			String test = null;
			String[] splittest = null;
			String firsttest = null;
			String finalString = null;

			for (int i = 0; i <= size - 1; i++) {
				int x = size - 1;
				String[] splittest1 = town[i].split("-");
				if (i == 0) {
					firsttest = "('";
					test = splittest1[0];
					finalString = firsttest + test + "')";
				} else if (i != x) {
					test = test + "','" + splittest1[0];
				} else {
					test = test + "','" + splittest1[0] + "')";
					finalString = firsttest + test;
				}

			}
			whTownCode = "town_code in " + finalString;
		}

		String sql = null;
		if (reportPeriod.equalsIgnoreCase("monthly")) {

			sql = "select distinct * from\n"
					+ " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
					+ "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,fdr_id,tp_fdr_id,fdr_name,boundary_feeder,\n"
					+ "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n"
					+ "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n"
					+ "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n"
					+ "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n"
					+ "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" + "tech_loss,\n"
					+ "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" + "time_stamp\n"
					+ "FROM meter_data.rpt_ea_feeder_losses WHERE month_year ='" + fromdate + "'  AND  " + whTownCode
					+ " )A )X ,\n" + "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
					+ "WHERE Y.sitecode=X.office_id";

			/*
			 * "SELECT A.*,round((1-(A.billing_efficiency/A.collection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
			 * +
			 * "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,\n"
			 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
			 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
			 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
			 * "tech_loss,\n" +
			 * "round(((tech_loss/unit_billed)*100),2) as tech_loss_perc,\n" +
			 * "time_stamp\n" +
			 * "FROM meter_data.rpt_ea_feeder_losses )A WHERE TO_CHAR(time_stamp, 'YYYYMM')= '"
			 * +fromdate+"'    ";
			 */

		} else {
			sql = "select distinct * from\n"
					+ " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
					+ "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,fdr_id,tp_fdr_id,fdr_name,boundary_feeder,\n"
					+ "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n"
					+ "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n"
					+ "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n"
					+ "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n"
					+ "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" + "tech_loss,\n"
					+ "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" + "time_stamp\n"
					+ "FROM meter_data.rpt_ea_feeder_losses WHERE month_year BETWEEN  '" + fromdate + "'  AND  '"
					+ todate + "' AND  " + whTownCode + " )A)X ,\n"
					+ "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
					+ "WHERE Y.sitecode=X.office_id";

			/*
			 * sql = "select * from\n" +
			 * " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
			 * +
			 * "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,fdr_id,tp_fdr_id,fdr_name,boundary_feeder,\n"
			 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
			 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
			 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
			 * "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n" +
			 * "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" +
			 * "tech_loss,\n" +
			 * "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" +
			 * "time_stamp\n" +
			 * "FROM meter_data.rpt_ea_feeder_losses )A WHERE month_year BETWEEN  '"
			 * +fromdate+"'  AND  '"
			 * +todate+"' AND  office_id in (select sitecode from meter_data.amilocation where subdivision = '"
			 * +subdiv+"'))X ,\n" +
			 * "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
			 * + "WHERE Y.sitecode=X.office_id";
			 */

		}
		System.out.println(sql);
		List<Object[]> list = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql)
				.getResultList();
		return list;

	}

	// Energy Accounting Subdivision Level
	@RequestMapping(value = "/eneryAccoutingDataSubdivisionWise", method = { RequestMethod.POST, RequestMethod.GET })
	public String eneryAccoutingDataSubdivisionWise(ModelMap model, HttpServletRequest request) {

		String qry = "select DISTINCT(subdivision) from meter_data.amilocation";
		List<?> resultList = null;

		try {
			resultList = (List<?>) businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(qry)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("resultList", resultList);

		return "subdivisionEAreport";
	}

	@RequestMapping(value = "/getEneryAccoutingDataSubdivisionWise", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getEneryAccoutingDataSubdivisionWise(@RequestParam String fromdate,
			@RequestParam String todate, @RequestParam String subdiv, @RequestParam String reportPeriod) {

		String sql;

		if (reportPeriod.equalsIgnoreCase("monthly")) {
			sql = "select * from\n"
					+ " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
					+ "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,\n"
					+ "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n"
					+ "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n"
					+ "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n"
					+ "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n"
					+ "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" + "tech_loss,\n"
					+ "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" + "time_stamp\n"
					+ "FROM meter_data.rpt_ea_feeder_losses )A WHERE month_year = '" + fromdate
					+ "'  AND  office_id in (select sitecode from meter_data.amilocation where subdivision = '" + subdiv
					+ "'))X ,\n" + "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
					+ "WHERE Y.sitecode=X.office_id";

			/*
			 * "SELECT A.*,round((1-(A.billing_efficiency/A.collection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
			 * +
			 * "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,'subdiv' as Subdivision \n"
			 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
			 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
			 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
			 * "tech_loss,\n" +
			 * "round(((tech_loss/unit_billed)*100),2) as tech_loss_perc,\n" +
			 * "time_stamp\n" +
			 * "FROM meter_data.rpt_ea_feeder_losses )A WHERE TO_CHAR(time_stamp, 'YYYYMM')='"
			 * +fromdate+"'    AND  office_id in (select sitecode from meter_data.amilocation where subdivision = '"
			 * +subdiv+"')";
			 */
		} else {

			sql = " select * from\n"
					+ " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
					+ "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,\n"
					+ "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n"
					+ "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n"
					+ "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n"
					+ "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n"
					+ "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" + "tech_loss,\n"
					+ "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" + "time_stamp\n"
					+ "FROM meter_data.rpt_ea_feeder_losses )A WHERE month_year BETWEEN  '" + fromdate + "'  AND  '"
					+ todate + "' AND  office_id in (select sitecode from meter_data.amilocation where subdivision = '"
					+ subdiv + "'))X ,\n" + "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
					+ "WHERE Y.sitecode=X.office_id";

			/*
			 * sql
			 * ="SELECT A.*,round((1-(A.billing_efficiency/A.collection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
			 * +
			 * "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,\n"
			 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
			 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
			 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
			 * "tech_loss,\n" +
			 * "round(((tech_loss/unit_billed)*100),2) as tech_loss_perc,\n" +
			 * "time_stamp\n" +
			 * "FROM meter_data.rpt_ea_feeder_losses )A WHERE TO_CHAR(time_stamp, 'YYYYMM') BETWEEN  '"
			 * +fromdate+"'  AND  '"
			 * +todate+"' AND  office_id in (select sitecode from meter_data.amilocation where subdivision = '"
			 * +subdiv+"') ";
			 */
		}

		List<Object[]> list = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql)
				.getResultList();
		return list;

	}

	@RequestMapping(value = "/PFCreportD1", method = { RequestMethod.POST, RequestMethod.GET })
	public String PFCreportD1(ModelMap model, HttpServletRequest request) {
		String States = pfcservice.getState();
		model.addAttribute("States", States);

		String Discom = pfcservice.getDiscom();
		model.addAttribute("Discom", Discom);

		List<?> periodList = businessRoleService.getDistinctPeriodD1();
		model.put("periodList", periodList);
		return "PFCreportD1";
	}

	/*
	 * @RequestMapping(value = "/getSubstation/{subdivision}", method = {
	 * RequestMethod.GET,RequestMethod.POST }) public @ResponseBody Object
	 * getEneryAccoutingDataSubdivisionWise(@PathVariable String subdivision) {
	 * 
	 * String sql ;
	 * 
	 * 
	 * sql
	 * ="select ss_name from  meter_data.substation_details where office_id in (select sitecode from meer_data.amilocation where subdivision = '"
	 * +subdivision+"') ";
	 * 
	 * 
	 * 
	 * List<Object[]> list =
	 * businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(
	 * sql).getResultList(); return list;
	 * 
	 * }
	 */

	// SubstationEnergyBalanceController
	@RequestMapping(value = "/substationEnergyBalance", method = { RequestMethod.POST, RequestMethod.GET })
	public String substationEnergyBalance(ModelMap model, HttpServletRequest request) {
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("results", "notDisplay");

		return "subEnegyBalanceRpt";
	}

	@RequestMapping(value = "/htloss", method = { RequestMethod.POST, RequestMethod.GET })
	public String htloss(ModelMap model, HttpServletRequest request) {

		return "htloss";
	}
	
	@RequestMapping(value = "/fdrEneryUpdateIdd", method = { RequestMethod.POST, RequestMethod.GET })
	public String fdrEneryUpdateId(ModelMap model, HttpServletRequest request) {

		return "fdrEneryUpdateIdd";
	}
	
	
	
	
	@RequestMapping(value = "/getfdrEnergyUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getfdrEnergyUpdate(HttpServletRequest request, HttpServletResponse response) {

		List<?> resultlist = new ArrayList<>();
		String circle = request.getParameter("circle");
		String month = request.getParameter("fromdate");
//		String feedercode = request.getParameter("feederTpId");
		String mtrno = request.getParameter("mtrno");
		String towncode = request.getParameter("town");
		// String period = request.getParameter("periodMonth");

		resultlist = loadSurveymonthlyConService.getfdrEnergyUpdate(circle,towncode,month,mtrno);

		return resultlist;

	}

	

	@RequestMapping(value = "/editimpexpNew", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> editSubStationDetailsNew(ModelMap model,
			HttpServletRequest request) {
		
		String id =request.getParameter("id");
		String mtrno =request.getParameter("mtrno");
		/* String month = request.getParameter("fromdate"); */
		
		String qry ="select adjacent_value_imp from meter_data.input_energy where id='"+id+"' ";
		System.out.println(qry);
		return postgresMdas.createNativeQuery(qry).getResultList();
	}
	
	@RequestMapping(value = "/editexp", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> editSubStationDetails(ModelMap model,
			HttpServletRequest request) {
		
		String id =request.getParameter("id");
		String mtrno =request.getParameter("mtrno");
		/* String month = request.getParameter("fromdate"); */
		
		String qry ="select adjacent_value_exp from meter_data.input_energy where id='"+id+"' ";
		System.out.println(qry);
		return postgresMdas.createNativeQuery(qry).getResultList();
	}
	
	@RequestMapping(value = "/modifyenergyDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object modifyenergyDetails(ModelMap model, HttpServletRequest request,
			HttpSession session) {
		

		
		String imp=request.getParameter("import");
		String month = request.getParameter("fromdate");
		String mtrno =request.getParameter("mtrno");
		String exp=request.getParameter("export");
		loadSurveymonthlyConService.getenergyDetails(imp,exp,mtrno,month);
		String msg = "SUBSTATION DETAILS UPDATED SUCCESFULLY";

		return "fdrEneryUpdateIdd";
	}
	
	
	@RequestMapping(value = "/getHtlosses", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getHtlosses(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		String region = request.getParameter("region");
		String circle = request.getParameter("circle");
		String fdrid = request.getParameter("feeder");
		// System.out.println(fdrid);
		List<?> gethtloss = loadSurveymonthlyConService.gethtloss(region, circle, fdrid);
		return gethtloss;

	}
	
	@RequestMapping(value = "/townenergyAccounting", method = { RequestMethod.POST, RequestMethod.GET })
	public String townenergyAccounting(ModelMap model, HttpServletRequest request) {

		return "townenergyAccounting";
	}
	
	@RequestMapping(value = "/getFeedertownEAReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?>  getFeedertownEAReport(ModelMap model, HttpServletRequest request, HttpServletResponse response){

		List<?> resultlist = new ArrayList<>();

		String monthyear = request.getParameter("monthyear");
		String feederTpId = request.getParameter("feederTpId");
		String periodMonth= request.getParameter("periodMonth");
		String circle = request.getParameter("circle");
		String town= request.getParameter("town");
		

		/*
		 * monthyear = request.getParameter("monthyear"); feederTpId =
		 * request.getParameter("feederTpId"); periodMonth =
		 * request.getParameter("town"); circle = request.getParameter("circle"); town =
		 * request.getParameter("town");
		 */
		

		 resultlist = loadSurveymonthlyConService.getFeedertownEAReport(monthyear,feederTpId, periodMonth,circle,town);

		

		return resultlist;

	}
	
	
	
	@RequestMapping(value = "/getFeederinputEAReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?>  getFeederinputEAReport(ModelMap model, HttpServletRequest request, HttpServletResponse response){

		List<?> resultlist = new ArrayList<>();

		String monthyear = request.getParameter("monthyear");
		String feederTpId = request.getParameter("feederTpId");
		String periodMonth= request.getParameter("periodMonth");
		String circle = request.getParameter("circle");
		String town= request.getParameter("town");
		

		/*
		 * monthyear = request.getParameter("monthyear"); feederTpId =
		 * request.getParameter("feederTpId"); periodMonth =
		 * request.getParameter("town"); circle = request.getParameter("circle"); town =
		 * request.getParameter("town");
		 */
		

		 resultlist = loadSurveymonthlyConService.getFeederinputEAReport(monthyear,feederTpId, periodMonth,circle,town);

		

		return resultlist;

	}

	@RequestMapping(value = "/dailysubenergy", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> dailysubenergy(HttpServletRequest request, ModelMap model) {

		String substation = request.getParameter("substation");
		String fromdate = request.getParameter("fromdate");
		String todate = request.getParameter("todate");
		List<?> dailysubenergy = loadSurveymonthlyConService.dailysubenergy(substation, fromdate, todate);
		return dailysubenergy;

	}

	@RequestMapping(value = "/dailysubenergysample", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> dailysubenergysample(HttpServletRequest request, ModelMap model) {

		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String MonthYearId = request.getParameter("MonthYearId");
		List<?> dailysubenergysample = loadSurveymonthlyConService.dailysubenergysample(zone, circle, town,
				MonthYearId);
		return dailysubenergysample;

	}

	@RequestMapping(value = "/dailyfeedersubenergy", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> dailyfeedersubenergy(HttpServletRequest request, ModelMap model) {

		String substation = request.getParameter("substation");
		String fromdate = request.getParameter("fromdate");
		String todate = request.getParameter("todate");
		List<?> dailyfeedersubenergy = loadSurveymonthlyConService.dailyfeedersubenergy(substation, fromdate, todate);
		return dailyfeedersubenergy;

	}

	@RequestMapping(value = "/monthlysubenergy", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> monthlysubenergy(HttpServletRequest request, ModelMap model) {

		String substation = request.getParameter("substation");
		String fromdate = request.getParameter("fromdate");
		String todate = request.getParameter("todate");
		List<?> monthlysubenergy = loadSurveymonthlyConService.monthlysubenergy(substation, fromdate, todate);
		return monthlysubenergy;

	}

	@RequestMapping(value = "/mntlyfeedersubenergy", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> mntlyfeedersubenergy(HttpServletRequest request, ModelMap model) {
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String sdocode = request.getParameter("sdocode");
		String substation = request.getParameter("substation");
		String fromdate = request.getParameter("fromdate");
		String todate = request.getParameter("todate");
		List<?> mntlyfeedersubenergy = loadSurveymonthlyConService.mntlyfeedersubenergy(substation, fromdate, todate);
		return mntlyfeedersubenergy;

	}

	@RequestMapping(value = "/viewconsumption", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> viewconsumption(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {

		String mtrno = request.getParameter("mtrno");
		String billmonth = request.getParameter("billmth");
		List<?> viewconsumption = loadSurveymonthlyConService.viewconsumption(mtrno, billmonth);
		return viewconsumption;
	}

	@RequestMapping(value = "/lossAnalysis", method = { RequestMethod.POST, RequestMethod.GET })
	public String lossAnalysis(ModelMap model, HttpServletRequest request) {
		String qry;
		String qryRegion;
		Object res;
		List<?> circleList = new ArrayList<>();
		List<?> zoneList = new ArrayList<>();

		// circles=consumermasterService.getCircle();
		// model.put("circles", circles);

		qryRegion = "SELECT DISTINCT ZONE  FROM meter_data.amilocation ORDER BY ZONE ";
		qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation ORDER BY CIRCLE ";
		circleList = entityManager.createNativeQuery(qry).getResultList();
		zoneList = entityManager.createNativeQuery(qryRegion).getResultList();
		model.put("circles", circleList);
		model.put("zoneList", zoneList);
		return "lossAnalysis";
	}

	// Feeder And DT Monthly Consumption
	@RequestMapping(value = "/fdrandDTmntlyConsumption", method = { RequestMethod.GET, RequestMethod.POST })
	public String fdrandDTmntlyConsumption(ModelMap model, HttpServletRequest request, HttpSession session) {
		List<?> circleList = new ArrayList<>();
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
//		try {
//			circleList = meterMasterService.getAllCirclesInAmiLocation();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("circleList" , circleList);
		return "fdrandDTmntlyConsumption";

	}

	@RequestMapping(value = "/getlocationwisedata", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getlocationwisedata(HttpServletRequest request, ModelMap model) {
		String LocIdentity = request.getParameter("LocIdentity");
		String LocationType = request.getParameter("LocationType");
		String meterno = request.getParameter("meterno");
		List<?> locationwisedata = loadSurveymonthlyConService.getlocationwisedata(LocIdentity, meterno, LocationType);
		return locationwisedata;

	}

	@RequestMapping(value = "/getdtLocationdata", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getdtLocationdata(HttpServletRequest request, ModelMap model) {
		String LocIdentity = request.getParameter("LocIdentity");
		String meterno = request.getParameter("meterno");
		List<?> dtLocationdata = loadSurveymonthlyConService.getdtLocationdata(LocIdentity, meterno);
		return dtLocationdata;
	}

	@RequestMapping(value = "/getAreawisefeederdata", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getAreawisefeederdata(HttpServletRequest request, ModelMap model) {
		String circle = request.getParameter("circle");
		/*
		 * String division=request.getParameter("division"); String
		 * subdiv=request.getParameter("subdiv");
		 */
		String townCode = request.getParameter("townCode");

		String monthyr = request.getParameter("monthyr");
		String Consumavail = request.getParameter("Consumavail");

		String locationtype = request.getParameter("locationtype");
		List<?> fdrareadata = loadSurveymonthlyConService.getAreawisefeederdata(circle, townCode, monthyr, Consumavail,
				locationtype);
		return fdrareadata;
	}



	@RequestMapping(value = "/getMonthlyConsumptionReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getMonthlyConsumptionReport(HttpServletRequest request, ModelMap model) {
		String circle = request.getParameter("circle");
		/*
		 * String division=request.getParameter("division"); String
		 * subdiv=request.getParameter("subdiv");
		 */
		String townCode = request.getParameter("townCode");

		String monthyr = request.getParameter("monthyr");
		String locationtype = request.getParameter("locationtype");
		List<?> fdrareadata = loadSurveymonthlyConService.getMonthlyConsumptionReport(circle, townCode, monthyr,
				locationtype);
		return fdrareadata;
	}

	@RequestMapping(value = "/getAreawisedtdata", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getAreawisedtdata(HttpServletRequest request, ModelMap model) {
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("subdiv");
		String monthyr = request.getParameter("monthyr");
		String Consumavail = request.getParameter("Consumavail");
		List<?> dtareadata = loadSurveymonthlyConService.getAreawisedtdata(circle, division, subdiv, monthyr,
				Consumavail);
		return dtareadata;
	}

	@RequestMapping(value = "/togetConsumption", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String togetConsumption(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		// String month=request.getParameter("month");
		String Consumption = loadSurveymonthlyConService.togetConsumption(id) + "";
		return Consumption;
	}
	
	@RequestMapping(value = "/togetConsumpt", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String togetConsumpt(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		// String month=request.getParameter("month");
		String Consumption = loadSurveymonthlyConService.togetConsumpt(id) + "";
		return Consumption;
	}

	
//	@RequestMapping(value="/togetConsumpt",method={RequestMethod.POST,RequestMethod.GET})
//	public @ResponseBody String togetConsumpt(HttpServletRequest request,ModelMap model)
//	{
//		String id=request.getParameter("id");
//		//String month=request.getParameter("month");
//		String Consumption=loadSurveymonthlyConService.togetConsumpt(id)+"";
//		return Consumption;
//	}
//	

	@RequestMapping(value = "/updateConsumption", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object updateConsumption(HttpServletRequest request, ModelMap model) {

		String consumption = request.getParameter("consumption");
		String consumpt = request.getParameter("consumpt");
		String oldconsumption = request.getParameter("oldconsumption");
		String oldconsumpt = request.getParameter("oldconsumpt");
		String id = request.getParameter("id");
		String remarks = request.getParameter("remarks");
		String loccode = request.getParameter("loccode");
		String mtrno = request.getParameter("mtrno");
		String month = request.getParameter("month");
		System.out.println("consumption--" + consumption);

		int UpdateConsumption = loadSurveymonthlyConService.updateConsumption(consumption, consumpt, oldconsumption,
				oldconsumpt, remarks, id, mtrno, loccode, month);
		if (UpdateConsumption > 0) {
			return "exist";
		} else {
			return "notexist";
		}
	}
	
	
	
	
	@RequestMapping(value = "/updateEnergy", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object updateEnergy(HttpServletRequest request, ModelMap model) {
		
		
		String consumption = request.getParameter("consumption");
		String oldconsumption = request.getParameter("oldconsumption");
	
		String id = request.getParameter("id");
		String mtrno = request.getParameter("mtrno");
		String month = request.getParameter("month");
		System.out.println("consumption--" + consumption);
		
		
		int UpdateConsumption = loadSurveymonthlyConService.updateEnergy(oldconsumption,consumption,id, mtrno, month);
				 
		if (UpdateConsumption > 0) {
			return "exist";
		} else {
			return "notexist";
		}
				
		
	}
	
	
	@RequestMapping(value = "/updateEnergyExp", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object updateEnergyExp(HttpServletRequest request, ModelMap model) {
		
		String oldconsumpt = request.getParameter("oldconsumpt");
		String consumpt = request.getParameter("consumpt");

		String id = request.getParameter("id");
		String mtrno = request.getParameter("mtrno");
		String month = request.getParameter("month");
		System.out.println("consumpt--" + consumpt);
		
		
		int UpdateConsumption = loadSurveymonthlyConService.updateEnergyExp (oldconsumpt,consumpt,
				 id, mtrno, month);
		if (UpdateConsumption > 0) {
			return "exist";
		} else {
			return "notexist";
		}
				
		
	}
	
	

	@RequestMapping(value = "/NPPReportFeeder", method = { RequestMethod.POST, RequestMethod.GET })
	public String NPPReportFeeder(ModelMap model, HttpServletRequest request) {
		return "NPPReportFeeder";
	}

	@Transactional
	@RequestMapping(value = "/getAtcLosses", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getAtcLosses(@RequestParam String billmonth, @RequestParam String period,
			@RequestParam String townCode, ModelMap model, HttpServletRequest request, HttpSession session) {

		System.out.println("ATC losses....");
		List<?> AtcLosses = feederMasterService.getAtcLosses(billmonth, period, townCode, session);
		System.out.println("ATC losses...." + AtcLosses);

		return AtcLosses;
	}

	@RequestMapping(value = "/getTdLosses", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getTdLosses(@RequestParam String billmonth, @RequestParam String period,
			@RequestParam String townCode, ModelMap model, HttpServletRequest request) {
		System.out.println("TD losses.....");
		List<?> TdLosses = feederMasterService.getTdLosses(billmonth, period, townCode);
		System.out.println("TD losses...." + TdLosses);
		return TdLosses;
	}

	@RequestMapping(value = "/getBillingEfficiencyLosses", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getBillingEfficiencyLosses(@RequestParam String billmonth, @RequestParam String period,
			@RequestParam String townCode, ModelMap model, HttpServletRequest request) {
		System.out.println("BE Losses.....");
		List<?> BillingEffLosses = feederMasterService.getBillingEffLosses(billmonth, period, townCode);
		System.out.println("BE Losses...." + BillingEffLosses);
		return BillingEffLosses;
	}

	@RequestMapping(value = "/getCollectionEfficiencyLosses", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getCollectionEfficiencyLosses(@RequestParam String billmonth,
			@RequestParam String period, @RequestParam String townCode, ModelMap model, HttpServletRequest request) {
		System.out.println("CE Losses.....");
		List<?> CollectionEffLosses = feederMasterService.getCollectionEffLosses(billmonth, period, townCode);
		System.out.println("CE Losses...." + CollectionEffLosses);
		return CollectionEffLosses;
	}

	@RequestMapping(value = "/getdivision/{circle}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getdivision(@PathVariable String circle, ModelMap model, HttpServletRequest request) {

		System.out.println("division list.....");
		List<?> divisionList = feederMasterService.getDivision(circle);
		System.out.println("division list...." + divisionList);
		return divisionList;
	}

	@RequestMapping(value = "/energyAccountingTownLevel", method = { RequestMethod.POST, RequestMethod.GET })
	public String energyAccountingTownnLevel(ModelMap model, HttpServletRequest request) {

		String qry;
		String qryRegion;
		Object res;
		List<?> circleList = new ArrayList<>();
		List<?> zoneList = new ArrayList<>();

		// circles=consumermasterService.getCircle();
		// model.put("circles", circles);

		qryRegion = "SELECT DISTINCT ZONE  FROM meter_data.amilocation ORDER BY ZONE ";
		qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation ORDER BY CIRCLE ";
		circleList = entityManager.createNativeQuery(qry).getResultList();
		zoneList = entityManager.createNativeQuery(qryRegion).getResultList();
		model.put("circles", circleList);
		model.put("zoneList", zoneList);

		return "eaTownWiseJasper";
	}

	@RequestMapping(value = "/lossCalculator", method = { RequestMethod.GET, RequestMethod.POST })
	public String lossCalculator(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		return "lossCalculator";
	}

	@RequestMapping(value = "/getCalculatedValue", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getCalculatedValue(ModelMap model, HttpServletRequest request, HttpSession session,
			double inputEnergy, double billedEnergy, double amountBilled, double amountCollected) {

		double[] arrayobj = new double[3];
		List<double[]> list = Arrays.asList(arrayobj);

		DecimalFormat formatter = new DecimalFormat("#.###");

		double billingEfficiency = (billedEnergy / inputEnergy) * 100;
		arrayobj[0] = Double.parseDouble(formatter.format(billingEfficiency));
		double collectionEfficiency = (amountCollected / amountBilled) * 100;
		arrayobj[1] = Double.parseDouble(formatter.format(collectionEfficiency));
		double be = (billedEnergy / inputEnergy);
		double ce = (amountCollected / amountBilled);
		double atAndCLoss = (1 - (be * ce)) * 100;
		arrayobj[2] = Double.parseDouble(formatter.format(atAndCLoss));

		return list;
	}

	@RequestMapping(value = "/lossCalculatorPdf", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void lossCalculatorPdf(HttpServletRequest request, HttpServletResponse response,
			ModelMap model, double inputEnergy, double billedEnergy, double amountBilled, double amountCollected,
			String anyTextId) {

		pfcservice.getLossCalculator(request, response, inputEnergy, billedEnergy, amountBilled, amountCollected,
				anyTextId);
	}

	// TODO: to be depricated if requirements gets freezed.
	/*
	 * @RequestMapping(value = "/getEneryAccoutingDataTownWise", method = {
	 * RequestMethod.GET,RequestMethod.POST }) public @ResponseBody Object
	 * getEneryAccoutingDataTownWise(@RequestParam String fromdate,
	 * 
	 * @RequestParam String todate,
	 * 
	 * @RequestParam ("town[]") String[] town,
	 * 
	 * @RequestParam String reportPeriod,
	 * 
	 * @RequestParam String subdivision ) { String sql ;
	 * 
	 * 
	 * String finalString ; if(town.equalsIgnoreCase("%")){ town = "%"; }
	 * 
	 * String whTownCode ; String isAllSeelected = null;
	 * 
	 * for(int i = 0 ; i <= town.length-1; i++ ){ if
	 * (town[i].equalsIgnoreCase("All")){ isAllSeelected = "yes"; break; }else{
	 * isAllSeelected = "no"; } } if(isAllSeelected.equalsIgnoreCase("Yes")){
	 * 
	 * List<?> result = null; String towncodeqry ;
	 * if(subdivision.equalsIgnoreCase("%")){ towncodeqry =
	 * " Select DISTINCT tp_towncode from meter_data.amilocation where  subdivision like '"
	 * +subdivision+"' "; }else { towncodeqry =
	 * " Select DISTINCT tp_towncode from meter_data.amilocation where  subdivision = '"
	 * +subdivision+"' "; }
	 * 
	 * result =
	 * businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(
	 * towncodeqry).getResultList();
	 * 
	 * whTownCode = "town_code in ("+towncodeqry+")";
	 * 
	 * }else{ String FinalString = null; ArrayList<String> ae = new ArrayList<>();
	 * String testSmaple2 = null; int size = town.length; String test = null;
	 * String[] splittest = null ; String firsttest =null; String finalString =
	 * null;
	 * 
	 * for(int i = 0 ; i <= size-1; i++){ int x = size-1; String[] splittest1 =
	 * town[i].split("-"); if(i== 0){ firsttest = "('"; test = splittest1[0];
	 * finalString = firsttest+test+"')"; }else if (i != x){ test =
	 * test+"','"+splittest1[0]; }else { test = test+"','"+splittest1[0]+"')";
	 * finalString = firsttest+test; }
	 * 
	 * } whTownCode = "town_code in " +finalString; }
	 * 
	 * 
	 * if(reportPeriod.equalsIgnoreCase("monthly")){ sql =
	 * "select distinct * from\n" +
	 * " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
	 * +
	 * "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,town_code,\n"
	 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
	 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
	 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
	 * "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n" +
	 * "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" +
	 * "tech_loss,\n" +
	 * "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" +
	 * "time_stamp\n" +
	 * "FROM meter_data.rpt_ea_feeder_losses )A WHERE month_year = '"
	 * +fromdate+"' AND  "+whTownCode+"  )X ,\n" +
	 * "(select tp_towncode,town_ipds,circle,division,subdivision from meter_data.amilocation)Y\n"
	 * + "WHERE Y.tp_towncode=X.town_code";
	 * 
	 * sql =
	 * " SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
	 * +
	 * "select month_year, town_code,town_ipds,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,\n"
	 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
	 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
	 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
	 * "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n" +
	 * "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" +
	 * "tech_loss,\n" +
	 * "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc from (\n" +
	 * "Select town_code,month_year,town_ipds,\n" +
	 * "SUM (tot_consumers)as tot_consumers,\n" +
	 * "SUM (unit_supply) as unit_supply,\n" + "SUM (unit_billed) as unit_billed,\n"
	 * + "SUM (amt_billed) as amt_billed,\n" +
	 * "SUM (amt_collected) as amt_collected,\n" + "SUM (tech_loss)as tech_loss\n" +
	 * "from meter_data.rpt_ea_feeder_losses fl \n" +
	 * "LEFT JOIN meter_data.amilocation al on fl.town_code = al.tp_towncode WHERE month_year = '"
	 * +fromdate+"' AND  "+whTownCode+"  \n" +
	 * "GROUP BY town_code,month_year,town_ipds) B)A;";
	 * 
	 * 
	 * } else {
	 * 
	 * sql =
	 * " SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
	 * +
	 * "select month_year, town_code,town_ipds,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,\n"
	 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
	 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
	 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
	 * "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n" +
	 * "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" +
	 * "tech_loss,\n" +
	 * "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc from (\n" +
	 * "Select town_code,month_year,town_ipds,\n" +
	 * "SUM (tot_consumers)as tot_consumers,\n" +
	 * "SUM (unit_supply) as unit_supply,\n" + "SUM (unit_billed) as unit_billed,\n"
	 * + "SUM (amt_billed) as amt_billed,\n" +
	 * "SUM (amt_collected) as amt_collected,\n" + "SUM (tech_loss)as tech_loss\n" +
	 * "from meter_data.rpt_ea_feeder_losses fl \n" +
	 * "LEFT JOIN meter_data.amilocation al on fl.town_code = al.tp_towncode WHERE month_year BETWEEN  '"
	 * +fromdate+"'  AND  '"+todate+"' AND  "+whTownCode+"  \n" +
	 * "GROUP BY town_code,month_year,town_ipds) B)A;";
	 * 
	 * 
	 * sql = " select distinct * from\n" +
	 * " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
	 * +
	 * "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,town_code,\n"
	 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
	 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
	 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
	 * "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n" +
	 * "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" +
	 * "tech_loss,\n" +
	 * "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" +
	 * "time_stamp\n" +
	 * "FROM meter_data.rpt_ea_feeder_losses )A WHERE month_year BETWEEN  '"
	 * +fromdate+"'  AND  '"+todate+"' AND  "+whTownCode+" )X ,\n" +
	 * "(select tp_towncode,town_ipds,circle,division,subdivision from meter_data.amilocation)Y\n"
	 * + "WHERE Y.tp_towncode=X.town_code" ;
	 * 
	 * 
	 * }
	 * 
	 * List<Object[]> list =
	 * businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(
	 * sql).getResultList(); return list;
	 * 
	 * }
	 */

	@RequestMapping(value = "/getEneryAccoutingDataTownWise", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getEneryAccoutingDataTownWise(@RequestParam String fromdate, @RequestParam String town,
			@RequestParam String subdivision) {
		String sql;

		sql = " SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
				+ "select month_year, town_code,town_ipds,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,\n"
				+ "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n"
				+ "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n"
				+ "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n"
				+ "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n"
				+ "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" + "tech_loss,\n"
				+ "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc from (\n"
				+ "Select town_code,month_year,town_ipds,\n" + "SUM (tot_consumers)as tot_consumers,\n"
				+ "SUM (unit_supply) as unit_supply,\n" + "SUM (unit_billed) as unit_billed,\n"
				+ "SUM (amt_billed) as amt_billed,\n" + "SUM (amt_collected) as amt_collected,\n"
				+ "SUM (tech_loss)as tech_loss\n" + "from meter_data.rpt_ea_feeder_losses fl \n"
				+ "LEFT JOIN meter_data.amilocation al on fl.town_code = al.tp_towncode WHERE month_year = '" + fromdate
				+ "' AND  town_code = '" + town + "'  \n" + "GROUP BY town_code,month_year,town_ipds) B)A;";

		/*
		 * sql =
		 * " SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
		 * +
		 * "select month_year, town_code,town_ipds,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,\n"
		 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
		 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
		 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
		 * "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n" +
		 * "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" +
		 * "tech_loss,\n" +
		 * "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc from (\n" +
		 * "Select town_code,month_year,town_ipds,\n" +
		 * "SUM (tot_consumers)as tot_consumers,\n" +
		 * "SUM (unit_supply) as unit_supply,\n" + "SUM (unit_billed) as unit_billed,\n"
		 * + "SUM (amt_billed) as amt_billed,\n" +
		 * "SUM (amt_collected) as amt_collected,\n" + "SUM (tech_loss)as tech_loss\n" +
		 * "from meter_data.rpt_ea_feeder_losses fl \n" +
		 * "LEFT JOIN meter_data.amilocation al on fl.town_code = al.tp_towncode WHERE month_year BETWEEN  '"
		 * +fromdate+"'  AND  '"+todate+"' AND  town_code = '"+town+"' \n" +
		 * "GROUP BY town_code,month_year,town_ipds) B)A;";
		 * 
		 * }
		 */
		@SuppressWarnings("unchecked")
		List<Object[]> list = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql)
				.getResultList();
		return list;

	}

	@RequestMapping(value = "/getEneryAccoutingDataTownWiseFeeder", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getEneryAccoutingDataTownWiseFeeder(@RequestParam String fromdate,
			@RequestParam String town

	) {

		String sql = null;

		sql = "select distinct * from\n"
				+ " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
				+ "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,fdr_id,tp_fdr_id,fdr_name,boundary_feeder,\n"
				+ "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n"
				+ "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n"
				+ "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n"
				+ "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n"
				+ "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" + "tech_loss,\n"
				+ "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" + "time_stamp\n"
				+ "FROM meter_data.rpt_ea_feeder_losses WHERE month_year ='" + fromdate + "'  AND  town_code = '" + town
				+ "' )A )X ,\n" + "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
				+ "WHERE Y.sitecode=X.office_id";

		/*
		 * "SELECT A.*,round((1-(A.billing_efficiency/A.collection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
		 * +
		 * "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,\n"
		 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
		 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
		 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
		 * "tech_loss,\n" +
		 * "round(((tech_loss/unit_billed)*100),2) as tech_loss_perc,\n" +
		 * "time_stamp\n" +
		 * "FROM meter_data.rpt_ea_feeder_losses )A WHERE TO_CHAR(time_stamp, 'YYYYMM')= '"
		 * +fromdate+"'    ";
		 */

		/*
		 * sql = "select distinct * from\n" +
		 * " (SELECT A.*,round((1-(A.Actualbilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM(\n"
		 * +
		 * "select meter_sr_number,tot_consumers,unit_supply, unit_billed, amt_billed,amt_collected,office_id,month_year,fdr_id,tp_fdr_id,fdr_name,boundary_feeder,\n"
		 * + "round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
		 * "round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
		 * "round(((unit_supply-unit_billed)/unit_supply)*100,2) as t_d_loss,\n" +
		 * "round((unit_billed/unit_supply),2) AS Actualbilling_efficiency,\n" +
		 * "round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" +
		 * "tech_loss,\n" +
		 * "round(((tech_loss/unit_supply)*100),2) as tech_loss_perc,\n" +
		 * "time_stamp\n" +
		 * "FROM meter_data.rpt_ea_feeder_losses WHERE month_year BETWEEN  '"
		 * +fromdate+"'  AND  '"+todate+"' AND  towncode = '"+town+"'  )A)X ,\n" +
		 * "(select tp_subdivcode,sitecode,subdivision from meter_data.amilocation)Y\n"
		 * + "WHERE Y.sitecode=X.office_id";
		 * 
		 */
		@SuppressWarnings("unchecked")
		List<Object[]> list = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql)
				.getResultList();
		return list;

	}

	// Below schedular inserts process data to RPT_FEEDER_LOSSES table monthly once
	public void dataProcesstoRPTfeederLoss() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();

		String billMonth = dateFormat.format(date);

		String qry = "INSERT INTO \"rpt_ea_feeder_losses\" (\n" + "month_year,\n" + "fdr_name,\n" + "fdr_id,\n"
				+ "tp_fdr_id,\n" + "town_code,\n" + "office_id,\n" + "meter_sr_number,\n" + "boundary_feeder,\n"
				+ "tot_consumers,\n" + "unit_supply,\n" + "unit_billed,\n" + "amt_billed,\n" + "amt_collected,\n"
				+ "time_stamp)\n" + "(\n" + "Select CAST ('" + billMonth + "' as NUMERIC) report_month,\n"
				+ "feedername, feeder_code, tp_feeder_code,towncode,CAST (officeid as NUMERIC) ,meterno,boundry_feeder,\n"
				+ "SUM(total_lt_consumer_count + total_ht_consumer_cocount )as total_consumer_count,\n"
				+ "total_energy_supplied,\n"
				+ "SUM(total_ht_unit_billed + total_lt_unit_billed )as total_units_billed,\n"
				+ "SUM(total_ht_amount_billed + total_lt_amount_billed )as total_amount_billed,\n"
				+ "SUM (total_ht_amount_collected + total_lt_amount_collected) as total_amount_collected,\n" + "now()\n"
				+ "from (Select feedername,feeder_code,feeder_type,tp_feeder_code,towncode,dc.officeid,fd.meterno,fd.boundry_feeder,\n"
				+ "SUM(CASE WHEN (monthyear>=\n" + "cast(to_char((TO_DATE(to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n"
				+ "- INTERVAL '11 MONTH'),'YYYYMM') AS numeric)) AND  (monthyear<=CAST( to_char(to_date('201909', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
				+ " COALESCE(ht_ind_energy_bill ,0)+\n"
				+ " COALESCE(ht_com_energy_bill ,0))    END)  AS total_ht_unit_billed,\n" + " \n"
				+ " SUM(CASE WHEN (monthyear>=\n" + "cast(to_char((TO_DATE(to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n"
				+ "- INTERVAL '11 MONTH'),'YYYYMM') AS numeric)) AND  (monthyear<=CAST( to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
				+ " COALESCE(lt_ind_energy_bill ,0)+\n" + " COALESCE(lt_com_energy_bill ,0)+\n"
				+ " COALESCE(lt_dom_energy_bill ,0)+\n" + " COALESCE(govt_energy_bill ,0)+\n"
				+ " COALESCE(agri_energy_bill ,0)+\n"
				+ " COALESCE(other_energy_bill ,0))   END)  AS total_lt_unit_billed,\n" + "  \n"
				+ " SUM(CASE WHEN (monthyear>=\n" + "cast(to_char((TO_DATE(to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM'),'YYYYMM')\n"
				+ "- INTERVAL '11 MONTH'),'YYYYMM') AS numeric)) AND  (monthyear<=CAST( to_char(to_date('201909', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) THEN  (\n"
				+ "COALESCE(ht_ind_amount_bill	,0)+\n"
				+ " COALESCE(ht_com_amount_bill	,0))   END)  AS total_ht_amount_billed ,\n" + " \n"
				+ " SUM(CASE WHEN (monthyear>=\n" + "cast(to_char((TO_DATE(to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n"
				+ "- INTERVAL '11 MONTH'),'YYYYMM') AS numeric)) AND  (monthyear<=CAST( to_char(to_date('201909', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
				+ "COALESCE(lt_ind_amount_bill ,0)+\n" + " COALESCE(lt_com_amount_bill ,0)+\n"
				+ " COALESCE(lt_dom_amount_bill ,0)+\n" + " COALESCE(govt_amount_bill ,0)+\n"
				+ " COALESCE(agri_amount_bill ,0)+\n"
				+ " COALESCE(other_amount_bill ,0))   END)  AS total_lt_amount_billed,\n" + " \n"
				+ " SUM(CASE WHEN (monthyear>=\n" + "cast(to_char((TO_DATE(to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM'),'YYYYMM')\n"
				+ "- INTERVAL '11 MONTH'),'YYYYMM') AS numeric)) AND  (monthyear<=CAST( to_char(to_date('201909', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
				+ "COALESCE(ht_ind_amount_collected	,0)+\n"
				+ " COALESCE(ht_com_amount_collected	,0))   END)  AS total_ht_amount_collected,\n" + " \n"
				+ " SUM(CASE WHEN (monthyear>=\n" + "cast(to_char((TO_DATE(to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '1 MONTH','YYYYMM'),'YYYYMM')\n"
				+ "- INTERVAL '11 MONTH'),'YYYYMM') AS numeric)) AND  (monthyear<=CAST( to_char(to_date('201909', 'YYYYMM') - INTERVAL '1 MONTH','YYYYMM') AS numeric)) THEN  (\n"
				+ "COALESCE(lt_ind_amount_collected ,0)+\n" + " COALESCE(lt_com_amount_collected ,0)+\n"
				+ " COALESCE(lt_dom_amount_collected ,0)+\n" + " COALESCE(govt_amount_collected ,0)+\n"
				+ " COALESCE(agri_amount_collected ,0)+\n"
				+ " COALESCE(other_amount_collected ,0))   END)  AS total_lt_amount_collected,\n" + " \n"
				+ "SUM(CASE WHEN (monthyear>=\n" + "cast(to_char((TO_DATE(to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '14 MONTH','YYYYMM'),'YYYYMM')\n"
				+ "- INTERVAL '11 MONTH'),'YYYYMM') AS numeric)) AND  (monthyear<=CAST( to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL'2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
				+ "COALESCE(lt_ind_con_count ,0)+\n" + "COALESCE(lt_com_con_count ,0)+\n"
				+ "COALESCE(lt_dom_con_count ,0)+\n" + "COALESCE(govt_con_count ,0)+\n"
				+ "COALESCE(agri_con_count ,0)+\n"
				+ "COALESCE(other_con_count ,0))   END)  AS total_lt_consumer_count,\n" + "  \n"
				+ "SUM(CASE WHEN (monthyear>=\n" + "cast(to_char((TO_DATE(to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '14 MONTH','YYYYMM'),'YYYYMM')\n"
				+ "- INTERVAL '3 MONTH'),'YYYYMM') AS numeric)) AND  (monthyear<=CAST( to_char(to_date('" + billMonth
				+ "', 'YYYYMM') - INTERVAL '2 MONTH','YYYYMM') AS numeric)) THEN  (\n"
				+ "COALESCE(ht_ind_con_count	,0)+\n"
				+ "COALESCE(ht_com_con_count	,0))   END)  AS total_ht_consumer_cocount\n" + "from npp_data dc\n"
				+ " LEFT JOIN feederdetails fd on fd.fdr_id =  dc.feeder_code WHERE  monthyear >=cast(to_char(to_date('"
				+ billMonth
				+ "', 'YYYYMM') - INTERVAL '15 MONTH','YYYYMM') AS numeric) GROUP BY feedername,feeder_code,tp_feeder_code,towncode,dc.officeid,feeder_type,fd.meterno,fd.boundry_feeder )Z\n"
				+ " LEFT JOIN(\n" + "Select mtrno,SUM(energy_supplied)as total_energy_supplied from (\n"
				+ "Select kwh_imp , kwh_exp,billmonth,mtrno,boundry_feeder,\n"
				+ "(CASE  WHEN (boundry_feeder ='t')  THEN	(kwh_imp-kwh_exp)  else kwh_imp end) as energy_supplied  from (\n"
				+ " Select kwh_imp , kwh_exp, mtrno,billmonth,fd.boundry_feeder  from  \"monthly_consumption_testing(Amit)\" mc  \n"
				+ " left join feederdetails fd on fd.meterno = mc.mtrno  where  mc.billmonth >=cast(to_char(to_date('"
				+ billMonth
				+ "', 'YYYYMM') - INTERVAL '14 MONTH','YYYYMM') AS numeric) AND mc.billmonth <cast(to_char(to_date('201909', 'YYYYMM') - INTERVAL '3 MONTH','YYYYMM') AS numeric)) a)b Group by mtrno) xv on Z.meterno = xv.mtrno GROUP BY feeder_code, feeder_type,feedername,\n"
				+ "tp_feeder_code,towncode,officeid,meterno,boundry_feeder,total_energy_supplied \n" + ")";

		int result = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();

	}
	/*
	 * by kesav this one method
	 * 
	 * @RequestMapping(value="/energyAccountingDTLevel",method={RequestMethod.POST,
	 * RequestMethod.GET}) public String energyAccountingDTLevel(ModelMap
	 * model,HttpServletRequest request) {
	 * 
	 * String qry
	 * ="select DISTINCT(subdivision),sitecode from meter_data.amilocation"; List<?>
	 * resultList = null;
	 * 
	 * try { resultList = (List<?>)
	 * businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(
	 * qry).getResultList(); } catch (Exception e) { e.printStackTrace(); }
	 * model.addAttribute("resultList", resultList);
	 * 
	 * return "energyAccountingDTLevel"; }
	 */

	@RequestMapping(value = "/getTownSLDimagePath", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getTownSLDimagePath(@RequestParam String town) {
		String sql;

		sql = "SELECT server_filepath FROM meter_data.town_master where towncode  = '" + town + "'";
		String result = null;
		try {
			result = businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getSingleResult()
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result = "noImage";
		}

		return result;

	}

	/*
	 * @RequestMapping(value = "/getTownWiseEAjasperReport", method = {
	 * RequestMethod.GET,RequestMethod.POST }) public @ResponseBody String
	 * getTownWiseEAjasperReport(
	 * 
	 * @RequestParam("monthyear") String monthyear,
	 * 
	 * @RequestParam("town") String town,
	 * 
	 * @RequestParam("reportName") String reportName,
	 * 
	 * @RequestParam("feederTpId") String feederTpId,
	 * 
	 * @RequestParam("periodMonth") String periodMonth ) throws JSONException,
	 * ClientProtocolException, IOException {
	 * 
	 * String url =FilterUnit.jasperUrl+"getJasperReportTNEBEA"; JSONObject obj =
	 * new JSONObject(); obj.put("reportName", reportName); obj.put("monthyear",
	 * monthyear); obj.put("town", town); obj.put("feederTpId", feederTpId);
	 * obj.put("periodMonth", periodMonth);
	 * 
	 * 
	 * 
	 * HttpClient httpClient = HttpClientBuilder.create().build(); HttpPost
	 * httpRequest = new HttpPost(url); httpRequest.setHeader("Content-Type",
	 * "application/json"); StringEntity body = new StringEntity(obj.toString());
	 * httpRequest.setEntity(body);
	 * 
	 * HttpResponse response = httpClient.execute(httpRequest); String res = new
	 * BasicResponseHandler().handleResponse(response);
	 * 
	 * return res;
	 * 
	 * }
	 */

	@RequestMapping(value = "/getTownWiseEAjasperReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getTownWiseEAjasperReport(@RequestParam("monthyear") String monthyear,
			@RequestParam("town") String town, @RequestParam("reportName") String reportName,
			@RequestParam("periodMonth") String periodMonth)
			throws JSONException, ClientProtocolException, IOException {

		String url = FilterUnit.jasperUrl + "getJasperReportTNEBEA";
		JSONObject obj = new JSONObject();
		obj.put("reportName", reportName);
		obj.put("monthyear", monthyear);
		obj.put("town", town);
		obj.put("periodMonth", periodMonth);

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpRequest = new HttpPost(url);
		httpRequest.setHeader("Content-Type", "application/json");
		StringEntity body = new StringEntity(obj.toString());
		httpRequest.setEntity(body);

		HttpResponse response = httpClient.execute(httpRequest);
		String res = new BasicResponseHandler().handleResponse(response);

		return res;

	}

	@RequestMapping(value = "/getFeederWiseEAjasperReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getTownWiseEAjasperReport(@RequestParam("monthyear") String monthyear,@RequestParam("circle") String circle,@RequestParam("town") String town,
			 @RequestParam("reportName") String reportName,
			@RequestParam("feederTpId") String feederTpId, @RequestParam("periodMonth") String periodMonth)
			throws JSONException, ClientProtocolException, IOException {

		String url = FilterUnit.jasperUrl + "getDTJasperReportTNEBEA";
		JSONObject obj = new JSONObject();
		obj.put("reportName", reportName);
		obj.put("monthyear", monthyear);
		obj.put("circle", circle);
		obj.put("town", town);
		obj.put("feederTpId", feederTpId);
		obj.put("periodMonth", periodMonth);
		

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpRequest = new HttpPost(url);
		httpRequest.setHeader("Content-Type", "application/json");
		StringEntity body = new StringEntity(obj.toString());
		httpRequest.setEntity(body);

		HttpResponse response = httpClient.execute(httpRequest);
		String res = new BasicResponseHandler().handleResponse(response);

		return res;

	}
	
	
	
	

	// ?monthyear='+monthyear+'&reportName='+reportName+'&periodMonth='+periodMonth+'&town='+town+'&exporttype='+exporttype+'
	@RequestMapping(value = "/eaTownWiseReportDownload", method = { RequestMethod.GET, RequestMethod.POST })
	public void eaTownWiseReportDownload(@RequestParam("monthyear") String monthyear, @RequestParam("town") String town,
			@RequestParam("reportName") String reportName, @RequestParam("periodMonth") String periodMonth,
			@RequestParam("exporttype") String exporttype) throws JSONException, ClientProtocolException, IOException {
		// String urldemo = https://www.google.co.in/?gfe_rd=cr&ei=ptYq" +
		// "WK26I4fT8gfth6CACg#q=geeks+for+geeks+java");

		URL url1 = new URL("http://localhost:7272/JasperFilter/downloadjasperreportEATownWiseREST");

		// String url =FilterUnit.jasperUrl+"downloadjasperreportEATownWiseREST";
		String url = "http://localhost:7272/JasperFilter/downloadjasperreportEATownWiseREST";
		JSONObject obj = new JSONObject();
		obj.put("reportName", reportName);
		obj.put("monthyear", monthyear);
		obj.put("town", town);
		obj.put("periodMonth", periodMonth);
		obj.put("exporttype", exporttype);

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpRequest = new HttpPost(url);
		httpRequest.setHeader("Content-Type", "application/json");
		StringEntity body = new StringEntity(obj.toString());
		httpRequest.setEntity(body);

		HttpResponse response = httpClient.execute(httpRequest);
		

	}
	
	

	@RequestMapping(value = "/getDTWiseEAjasperReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getDTWiseEAjasperReport(@RequestParam("monthyear") String monthyear,
			@RequestParam("town") String town, @RequestParam("reportName") String reportName,
			@RequestParam("feederTpId") String feederTpId, @RequestParam("periodMonth") String periodMonth)
			throws JSONException, ClientProtocolException, IOException {

		String url = FilterUnit.jasperUrl + "getDTJasperReportTNEBEA";
		JSONObject obj = new JSONObject();
		obj.put("reportName", reportName);
		obj.put("monthyear", monthyear);
		obj.put("town", town);
		obj.put("feederTpId", feederTpId);
		obj.put("periodMonth", periodMonth);

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpRequest = new HttpPost(url);
		httpRequest.setHeader("Content-Type", "application/json");
		StringEntity body = new StringEntity(obj.toString());
		httpRequest.setEntity(body);

		HttpResponse response = httpClient.execute(httpRequest);
		String res = new BasicResponseHandler().handleResponse(response);

		return res;

	}

	@RequestMapping(value = "/getdtloss", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getdtloss(@RequestParam("monthyear") String monthyear,
			@RequestParam("town") String town, @RequestParam("feederTpId") String feederTpId,
			@RequestParam("periodMonth") String periodMonth, @RequestParam("zone") String zone,
			@RequestParam("circle") String circle) throws JSONException, ClientProtocolException, IOException {

		List<?> gethtloss = loadSurveymonthlyConService.getdtloss(zone, circle, town, monthyear, feederTpId,
				periodMonth);

		return gethtloss;

	}

	@RequestMapping(value = "/getdtlossPDF", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void getdtlossPDF(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		System.out.println("hi inside pdf controller..........");
		String town = request.getParameter("town");
		String monthyear = request.getParameter("monthyear");
		String periodMonth = request.getParameter("periodMonth");
		String gfeederTpId = request.getParameter("feederTpId");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");

		String zonename = "", cirname = "", townname = "", feederTpId = "";
		if (gfeederTpId.equalsIgnoreCase("ALL")) {
			feederTpId = "%";
		} else {
			feederTpId = gfeederTpId;
		}
		if (zone.equalsIgnoreCase("ALL")) {
			zonename = "%";
		} else {
			zonename = zone;
		}
		if (circle.equalsIgnoreCase("ALL")) {
			cirname = "%";
		} else {
			cirname = circle;
		}
		if (town.equalsIgnoreCase("ALL")) {
			townname = "%";
		} else {
			townname = town;
		}

		pfcservice.getdtlossPDF(townname, monthyear, periodMonth, feederTpId, zonename, cirname, request, response);

	}

	@RequestMapping(value = "/PFCreportD6PDF", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void PFCreportD6PDF(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String scheme = request.getParameter("scheme");
		String town = request.getParameter("town");
		String period = request.getParameter("period");
		String state = request.getParameter("state");
		String discom = request.getParameter("discom");
		String month = request.getParameter("month");
		String ieperiod = request.getParameter("ieperiod");
		String townname = request.getParameter("townname");
		String[] arrSplit = town.split(",");

		String twn = "";
		if (town.equalsIgnoreCase("All")) {
			twn = "%";
		} else {
			twn = town;
		}

		String firsttest = null;
		String test = null;
		int size = town.length();
		String finalString = null;
		for (int i = 0; i < arrSplit.length; i++) {
			int x = (arrSplit.length) - 1;
			String[] splittest1 = arrSplit[i].split("-");
			if (i == 0) {
				firsttest = "('";
				test = splittest1[0];
				finalString = firsttest + test + "')";

			} else if (i != x) {
				test = test + "','" + splittest1[0];
			} else {
				test = test + "','" + splittest1[0] + "')";
				finalString = firsttest + test;
			}
		}

		pfcservice.getPfcreportD6Pdf(request, response, scheme, finalString, period, state, discom, month, ieperiod,
				townname, twn);

	}

	@RequestMapping(value = "/LocwiseFdrDtMonthlyConsum", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object LocwiseFdrDtMonthlyConsum(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		try {

			String loctype = request.getParameter("LocationType");
			String locid = request.getParameter("LocIdentity");
			String meterno = request.getParameter("meterno");

			String fileName = "FdrandDtMonthlyConsum";
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

			header.createCell(0).setCellValue("Month_Year");
			header.createCell(1).setCellValue("Subdivision_Name");
			header.createCell(2).setCellValue("Substation_Code");
			header.createCell(3).setCellValue("Feeder_Id");
			header.createCell(4).setCellValue("Feeder_Name");
			header.createCell(5).setCellValue("MeterNo");
			header.createCell(6).setCellValue("Meter_Make");
			header.createCell(7).setCellValue("MF");
			header.createCell(8).setCellValue("MonthlyConsumption(KWH)WithMF");

			List<?> locationwisedata = null;
			locationwisedata = loadSurveymonthlyConService.getlocationwisedata(locid, meterno, loctype);

			int count = 1;
			// int cellNO=0;
			for (Iterator<?> iterator = locationwisedata.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();

				XSSFRow row = sheet.createRow(count);
				// cellNO++;
				// row.createCell(0).setCellValue(String.valueOf(cellNO+""));
				if (values[0] == null) {
					row.createCell(0).setCellValue(String.valueOf(""));
				} else {
					row.createCell(0).setCellValue(String.valueOf(values[0]));
				}

				if (values[1] == null) {
					row.createCell(1).setCellValue(String.valueOf(""));
				} else {
					row.createCell(1).setCellValue(String.valueOf(values[1]));
				}

				if (values[2] == null) {
					row.createCell(2).setCellValue(String.valueOf(""));
				} else {
					row.createCell(2).setCellValue(String.valueOf(values[2]));
				}

				if (values[3] == null) {
					row.createCell(3).setCellValue(String.valueOf(""));
				} else {
					row.createCell(3).setCellValue(String.valueOf(values[3]));
				}

				if (values[4] == null) {
					row.createCell(4).setCellValue(String.valueOf(""));
				} else {
					row.createCell(4).setCellValue(String.valueOf(values[4]));
				}
				if (values[5] == null) {
					row.createCell(5).setCellValue(String.valueOf(""));
				} else {
					row.createCell(5).setCellValue(String.valueOf(values[5]));
				}
				if (values[6] == null) {
					row.createCell(6).setCellValue(String.valueOf(""));
				} else {
					row.createCell(6).setCellValue(String.valueOf(values[6]));
				}
				if (values[7] == null) {
					row.createCell(7).setCellValue(String.valueOf(""));
				} else {
					row.createCell(7).setCellValue(String.valueOf(values[7]));
				}
				if (values[8] == null) {
					row.createCell(8).setCellValue(String.valueOf(""));
				} else {
					row.createCell(8).setCellValue(String.valueOf(values[8]));
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
			response.setHeader("Content-Disposition",
					"inline;filename=\"FeederandDtMonthlyConsumptionLocationWise.xlsx" + "\"");
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

	@RequestMapping(value = "/AreawiseFdrDtMonthlyConsum", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object AreawiseFdrDtMonthlyConsum(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		try {

			String circle = request.getParameter("cir");
			String monthyear = request.getParameter("monthyr");
			String loctype = request.getParameter("locationtype");
			String consumavail = request.getParameter("Consumavail");
			String towncode = request.getParameter("townn");
			String zone1 = "", crcl = "", twn = "", townname1 = "";

			if (circle.equalsIgnoreCase("ALL")) {
				crcl = "%";
			} else {
				crcl = circle;
			}
			if (towncode.equalsIgnoreCase("ALL")) {
				twn = "%";
			} else {
				twn = towncode;
			}

			String fileName = "FdrandDtMonthlyConsum";
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

			if (loctype.equalsIgnoreCase("Feeder")) {

				header.createCell(0).setCellValue("Month_Year");
				header.createCell(1).setCellValue("Subdivision_Name");
				header.createCell(2).setCellValue("Substation_Code");
				header.createCell(3).setCellValue("Feeder_Id");
				header.createCell(4).setCellValue("Feeder_Name");
				header.createCell(5).setCellValue("MeterNo");
				header.createCell(6).setCellValue("Meter_Make");
				header.createCell(7).setCellValue("MF");
				header.createCell(8).setCellValue("MonthlyConsumption(KWH)WithMF");

			} else if (loctype.equalsIgnoreCase("DT")) {

				header.createCell(0).setCellValue("Month_Year");
				header.createCell(1).setCellValue("Subdivision_Name");
				header.createCell(2).setCellValue("Feeder_Code");
				header.createCell(3).setCellValue("DT_Id");
				header.createCell(4).setCellValue("DT_Name");
				header.createCell(5).setCellValue("MeterNo");
				header.createCell(6).setCellValue("Meter_Make");
				header.createCell(7).setCellValue("MF");
				header.createCell(8).setCellValue("MonthlyConsumption(KWH)WithMF");

			}
			List<?> fdrareadata = null;
			fdrareadata = loadSurveymonthlyConService.getAreawisefeederdata(crcl, twn, monthyear, consumavail, loctype);

			int count = 1;
			// int cellNO=0;
			for (Iterator<?> iterator = fdrareadata.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();

				XSSFRow row = sheet.createRow(count);
				// cellNO++;
				// row.createCell(0).setCellValue(String.valueOf(cellNO+""));
				if (values[0] == null) {
					row.createCell(0).setCellValue(String.valueOf(""));
				} else {
					row.createCell(0).setCellValue(String.valueOf(values[0]));
				}

				if (values[1] == null) {
					row.createCell(1).setCellValue(String.valueOf(""));
				} else {
					row.createCell(1).setCellValue(String.valueOf(values[1]));
				}

				if (values[2] == null) {
					row.createCell(2).setCellValue(String.valueOf(""));
				} else {
					row.createCell(2).setCellValue(String.valueOf(values[2]));
				}

				if (values[3] == null) {
					row.createCell(3).setCellValue(String.valueOf(""));
				} else {
					row.createCell(3).setCellValue(String.valueOf(values[3]));
				}

				if (values[4] == null) {
					row.createCell(4).setCellValue(String.valueOf(""));
				} else {
					row.createCell(4).setCellValue(String.valueOf(values[4]));
				}
				if (values[5] == null) {
					row.createCell(5).setCellValue(String.valueOf(""));
				} else {
					row.createCell(5).setCellValue(String.valueOf(values[5]));
				}
				if (values[6] == null) {
					row.createCell(6).setCellValue(String.valueOf(""));
				} else {
					row.createCell(6).setCellValue(String.valueOf(values[6]));
				}
				if (values[7] == null) {
					row.createCell(7).setCellValue(String.valueOf(""));
				} else {
					row.createCell(7).setCellValue(String.valueOf(values[7]));
				}
				if (values[8] == null) {
					row.createCell(8).setCellValue(String.valueOf(""));
				} else {
					row.createCell(8).setCellValue(String.valueOf(values[8]));
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
			response.setHeader("Content-Disposition",
					"inline;filename=\"FeederandDtMonthlyConsumptionAreaWise.xlsx" + "\"");
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

	// Below methods to display multiple month at&c loss

	@RequestMapping(value = "/getMultipleMonthatandcLossData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getMultipleMonthatandcLossData(HttpServletRequest request, ModelMap model) {

		String FromMonth = request.getParameter("FromMonth");
		String ToMonth = request.getParameter("ToMonth");
		String feederTpId = request.getParameter("feederTpId");
		String dtIdName = request.getParameter("dtIdName");
		String locationType = request.getParameter("locationType");
		String periodMonth = request.getParameter("periodMonth");

		List<?> multipleatcList = null;

		if (locationType.equalsIgnoreCase("feederLoc")) {

			multipleatcList = feederMasterService.getmultipleMonthatclossFeederdata(feederTpId, FromMonth, ToMonth,
					periodMonth);
		} else {
			multipleatcList = feederMasterService.getmultipleMonthatclossDtdata(dtIdName, FromMonth, ToMonth,
					periodMonth);
		}

		System.out.println("dt list is----" + multipleatcList);

		return multipleatcList;
	}

	/*
	 * @RequestMapping(value = "/downLoadEaReportsTownWise", method = {
	 * RequestMethod.GET, RequestMethod.POST },produces="application/text")
	 * public @ResponseBody String downLoadEaReportsTownWise(HttpServletRequest
	 * request, HttpServletResponse response) throws JSONException {
	 * 
	 * 
	 * 
	 * String monthyear=request.getParameter("monthyear"); String
	 * reportName=request.getParameter("reportName"); String
	 * town=request.getParameter("town"); String
	 * periodMonth=request.getParameter("periodMonth"); String
	 * exporttype=request.getParameter("exporttype");
	 * 
	 * 
	 * 
	 * String pathname = "/Application/bcits/jasper_reports/"; JSONObject obj = new
	 * JSONObject(); try {
	 * 
	 * String url =FilterUnit.jasperUrl+"downLoadEAReportsTownUpdated?monthyear="+
	 * monthyear + "&town=" + town + "&periodMonth=" + periodMonth + "&exporttype="
	 * + exporttype + "&reportName=" +reportName ;
	 * 
	 * byte[] serverResponse; try {
	 * 
	 * RestTemplate template = new RestTemplate(); UriComponentsBuilder builder =
	 * UriComponentsBuilder.fromHttpUrl(url); serverResponse =
	 * template.getForObject(builder.build().encode().toUri(), byte[].class); if
	 * ("null".equalsIgnoreCase(serverResponse + "") || serverResponse == null ||
	 * "".equalsIgnoreCase(serverResponse + "")) { obj.put("FAILED",
	 * "BillPrint Downoad Failed From Jasper Server"); return obj.toString(); } }
	 * catch (Exception e) { e.printStackTrace(); obj.put("FAILED", "MSG :" +
	 * e.getMessage()); return obj.toString(); } if
	 * ("EoR".equalsIgnoreCase(serverResponse + "")) {
	 * 
	 * obj.put("FAILED", "Download is Failed Due to Network Issue"); return
	 * obj.toString(); } String fileName =
	 * reportName.substring(reportName.lastIndexOf("/")+1)+"_"+town + "_" +
	 * monthyear + "." + exporttype.toLowerCase();
	 * 
	 * // File downloadFile = new File(pathname + fileName); //FileInputStream
	 * inputStream = new FileInputStream(downloadFile); ByteArrayOutputStream baos =
	 * new ByteArrayOutputStream(); response.setHeader("Content-Disposition",
	 * "inline;filename=\"" + fileName + "\"");
	 * response.setContentType("application/pdf"); ServletOutputStream outstream =
	 * response.getOutputStream(); outstream.write(serverResponse);
	 * baos.writeTo(outstream); outstream.flush(); outstream.close();
	 * 
	 * }
	 * 
	 * catch (IOException e) { e.printStackTrace(); obj.put("FAILED",
	 * "File Not Found"); return obj.toString(); } catch (Exception e) {
	 * e.printStackTrace(); obj.put("FAILED", "File Not Found"); return
	 * obj.toString(); } return null;
	 * 
	 * 
	 * }
	 */

	@RequestMapping(value = "/downLoadEaReportsTownWise", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/text")
	public @ResponseBody String downLoadEaReportsTownWise(HttpServletRequest request, HttpServletResponse response)
			throws JSONException {

		String monthyear = request.getParameter("monthyear");
		String reportName = request.getParameter("reportName");
		String town = request.getParameter("town");
		String circle = request.getParameter("circle");
		String periodMonth = request.getParameter("periodMonth");
		String exporttype = request.getParameter("exporttype");
		String feederTpId = request.getParameter("feederTpId");
		String pathname = "/Application/bcits/jasper_reports/";
		
		JSONObject obj = new JSONObject();
		try {
			String url = FilterUnit.jasperUrl + "downLoadEAReportsTownUpdated?monthyear=" + monthyear + "&town=" + town+"&circle=" + circle
					+ "&periodMonth=" + periodMonth + "&feederTpId=" + feederTpId + "&exporttype=" + exporttype
					+ "&reportName=" + reportName;
			byte[] serverResponse;
			try {
				RestTemplate template = new RestTemplate();
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
				serverResponse = template.getForObject(builder.build().encode().toUri(), byte[].class);
				if ("null".equalsIgnoreCase(serverResponse + "") || serverResponse == null
						|| "".equalsIgnoreCase(serverResponse + "")) {

					obj.put("FAILED", "BillPrint Downoad Failed From Jasper Server");
					return obj.toString();
				}
			}

			catch (Exception e) {
				e.printStackTrace();
				obj.put("FAILED", "MSG :" + e.getMessage());
				return obj.toString();
			}

			if ("EoR".equalsIgnoreCase(serverResponse + "")) {

				obj.put("FAILED", "Download is Failed Due to Network Issue");
				return obj.toString();
			}

			String fileName = reportName.substring(reportName.lastIndexOf("/") + 1) + "_" + town + "_" + monthyear + "."
					+ exporttype.toLowerCase();

			@SuppressWarnings("unused")
			File newTextFile = new File(pathname + fileName);
			/*
			 * File downloadFile = new File(pathname + fileName); FileInputStream
			 * inputStream = new FileInputStream(downloadFile);
			 */

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			outstream.write(serverResponse);
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();

		}

		catch (IOException e) {
			e.printStackTrace();
			obj.put("FAILED", "File Not Found");
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("FAILED", "File Not Found");
			return obj.toString();
		}
		return null;
	}
	
	
	

}
