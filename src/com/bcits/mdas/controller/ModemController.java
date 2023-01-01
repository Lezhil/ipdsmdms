package com.bcits.mdas.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.Last30DaysEntity;
import com.bcits.entity.MeterChangeTransHistory;
import com.bcits.entity.MeterMaster;
import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.ChangeModemDetailsEntity;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.MeterChange;
import com.bcits.mdas.entity.ModemLifeCycleEntity;
import com.bcits.mdas.entity.ModemMasterEntity;
import com.bcits.mdas.mqtt.Subscriber;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.ChangeModemService;
import com.bcits.mdas.service.DashboardService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterChangeService;
import com.bcits.mdas.service.ModemCommunicationService;
import com.bcits.mdas.service.ModemDiagnosisService;
import com.bcits.mdas.service.ModemLifeCycleService;
import com.bcits.mdas.service.ModemMasterServiceMDAS;
import com.bcits.mdas.service.SearchNodesService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.mdas.utility.SendDocketInfoSMS;
import com.bcits.mdas.utility.SendModemAlertViaMail;

import com.bcits.service.MasterService;
import com.bcits.service.MeterChangeTransHistoryService;
import com.bcits.service.MeterMasterService;
import com.bcits.utility.SmsCredentialsDetailsBean;
import com.google.gson.JsonParseException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class ModemController {
	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired(required = false)
	private AmrInstantaneousService amrInstantaneousService;

	@Autowired(required = false)
	private AmrBillsService amrBillsService;

	@Autowired(required = false)
	private AmrEventsService amrEventsService;

	@Autowired
	private FeederMasterService feederMasterService;

	@Autowired
	private FeederDetailsService feederdetailsservice;

	@Autowired
	private ModemCommunicationService modemCommService;

	@Autowired
	private FeederMasterService feederService;

	@Autowired
	private ModemDiagnosisService modemDiagnosisService;

	@Autowired
	private MasterMainService MasterMainService;

	@Autowired(required = false)
	private ChangeModemService changeModemService;

	@Autowired(required = false)
	private ModemMasterServiceMDAS modemMasterService;

	@Autowired
	private ModemLifeCycleService modemLifeCycleService;

	@Autowired
	private MeterChangeService meterChangeService;

	@Autowired
	private MasterService masterService;

	@Autowired
	private MeterMasterService meterMasterService;
	@Autowired
	SearchNodesService sns;

	@Autowired
	private MeterChangeTransHistoryService meterChangeTransHistoryService;
	



	@Autowired
	DashboardService ds;

	private static int dhOff = 0;
	private static int dhOn = 0;
	private static int dhp1 = 0;
	private static int dhp3 = 0;

	private static int uhOff = 0;
	private static int uhOn = 0;
	private static int uhp1 = 0;
	private static int uhp3 = 0;

	private static int sallOff = 0;
	private static int sallOn = 0;
	private static int sallp1 = 0;
	private static int sallp3 = 0;
	private static int last15Communicated = 0;

	int createModemFlag = 0;

	@RequestMapping(value = "/modemManagmentMDAS", method = RequestMethod.GET)
	public String modemManagment(ModelMap model, HttpServletRequest request)// get count of
																			// zone,circle,division,sub-division
	{
		BCITSLogger.logger
				.info("------------ In modemManagment  -------------==>" + request.getParameter("viewModems"));
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String btnSLD = request.getParameter("viewModem");
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode"),
				subStation = request.getParameter("subStation");
		;
		model.put("zoneList", zoneList);
		model.put("circleList", feederService.getCircleByZone(zone, model));
		model.put("divisionList", feederService.getDivisionByCircle(zone, circle, model));
		model.put("subdivList", feederService.getSubdivByDivisionByCircle(zone, circle, division, model));
		model.put("subStationList", feederService.getSStationBySubByDiv(zone, circle, division, subdiv, model));

		model.addAttribute("zone", zone);
		model.addAttribute("circle", circle);
		model.addAttribute("division", division);
		model.addAttribute("subdiv", subdiv);
		model.addAttribute("subStation", subStation);
		model.addAttribute("results", "notDisplay");
		return "modemsManagement";
	}

	@RequestMapping(value = "/modemMngtdetailsMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String modemMngtdetails(ModelMap model, HttpServletRequest request)// get count of
																				// zone,circle,division,sub-division
	{
		BCITSLogger.logger
				.info("------------ In modemMngtdetails  -------------==>" + request.getParameter("subStation"));
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String btnSLD = request.getParameter("viewModem");
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode"),
				subStation = request.getParameter("subStation");
		;
		model.put("zoneList", zoneList);
		model.put("circleList", feederService.getCircleByZone(zone, model));
		model.put("divisionList", feederService.getDivisionByCircle(zone, circle, model));
		model.put("subdivList", feederService.getSubdivByDivisionByCircle(zone, circle, division, model));
		model.put("subStationList", feederService.getSStationBySubByDiv(zone, circle, division, subdiv, model));

		model.addAttribute("zone", zone);
		model.addAttribute("circle", circle);
		model.addAttribute("division", division);
		model.addAttribute("subdiv", subdiv);
		model.addAttribute("subStation", subStation);

		List<?> mmd = modemCommService.getModelMngtDetailsBySS(zone, circle, division, subdiv, subStation, model,
				request);
		for (Iterator<?> iterator = mmd.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			model.addAttribute("totalModems", values[0]);
			model.addAttribute("activeModems", values[1]);
			model.addAttribute("inActiveModems", values[2]);

		}

		// model.addAttribute("results", "notDisplay");
		return "modemsManagementMDAS";
	}

	// **USED IN MOBILE APPLICATION. DONT CHANGE WITHOUT MOB DEVELOPER PERMISSION
	@RequestMapping(value = "/showCircleMDAS/{zone}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object showCircle(@PathVariable String zone, HttpServletRequest request, ModelMap model) {
		// System.out.println("==--showCircle--==List=="+zone);
		return feederService.getCircleByZone(zone, model);
	}

	@RequestMapping(value = "/showDistinctCircles", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object showDistinctCircle(HttpServletRequest request, ModelMap model) {
		// System.out.println("==--showCircle--==List=="+zone);
		return feederService.getDistinctCircle(request, model);
	}

	// **USED IN MOBILE APPLICATION. DONT CHANGE WITHOUT MOB DEVELOPER PERMISSION
	@RequestMapping(value = "/showDivisionMDAS/{zone}/{circle}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object showDivision(@PathVariable String zone, @PathVariable String circle,
			HttpServletRequest request, ModelMap model) {
		// System.out.println("==--showDivision--==List=="+zone+"==>>"+circle);
		return feederService.getDivisionByCircle(zone, circle, model);
	}

	// to get the divisions
	@RequestMapping(value = "/showDivisions/{circle}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object showDivisions(@PathVariable String circle, HttpServletRequest request, ModelMap model) {
		// System.out.println("==--showDivision--==List=="+zone+"==>>"+circle);
		return feederService.getDivisionUnderCircle(circle, model);
	}

	@RequestMapping(value = "/showDistrictByCircleMDAS/{zone}/{circle}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object showDistrictByCircle(@PathVariable String zone, @PathVariable String circle,
			HttpServletRequest request, ModelMap model) {
		// System.out.println("==--showDivision--==List=="+zone+"==>>"+circle);
		return feederService.showDistrictByCircle(zone, circle);
	}

	// **USED IN MOBILE APPLICATION. DONT CHANGE WITHOUT MOB DEVELOPER PERMISSION
	@RequestMapping(value = "/showSubdivByDivMDAS/{zone}/{circle}/{division}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object showSubdivByDiv(@PathVariable String zone, @PathVariable String circle,
			@PathVariable String division, HttpServletRequest request, ModelMap model) {
		// System.out.println("==--showSubdivByDiv--==List=="+zone+"==>>"+circle+"==>>"+division);
		return feederService.getSubdivByDivisionByCircle(zone, circle, division, model);
	}

	// to get distinct subdivisions
	@RequestMapping(value = "/showSubdivByDiv/{circle}/{division}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object showSubdivUnderDiv(@PathVariable String circle, @PathVariable String division,
			HttpServletRequest request, ModelMap model) {
		// System.out.println("==--showSubdivByDiv--==List=="+zone+"==>>"+circle+"==>>"+division);
		return feederService.getSubdivUnderDivision(circle, division, model);
	}

	@RequestMapping(value = "/getDistinctSubstationsMDAS/{zone}/{circle}/{division}/{subdiv}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDistinctSubstations(@PathVariable String zone, @PathVariable String circle,
			@PathVariable String division, @PathVariable String subdiv, HttpServletRequest request, ModelMap model) {
		// System.out.println("==--showSubdivByDiv--==List=="+zone+"==>>"+circle+"==>>"+division);
		return feederService.getDistinctSubstations(zone, circle, division, subdiv);
	}

	/******************* NEW IMPLEMENTATION **********************/
	@RequestMapping(value = "/getDistinctFeedersMDAS/{zone}/{circle}/{division}/{subdiv}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDistinctFeeders(@PathVariable String zone, @PathVariable String circle,
			@PathVariable String division, @PathVariable String subdiv, HttpServletRequest request, ModelMap model) {
		String substn = request.getParameter("substn");
		System.out.println("sub-station : " + substn);
		return feederService.getDistinctFeeders(zone, circle, division, subdiv, substn);
	}

	/*********************************************************/

	@RequestMapping(value = "/showSStaionBySubdivByDivMDAS/{zone}/{circle}/{division}/{subdiv}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object showFeederBySubdivByDiv(@PathVariable String zone, @PathVariable String circle,
			@PathVariable String division, @PathVariable String subdiv, HttpServletRequest request, ModelMap model) {
		// System.out.println("==--showSStaionBySubdivByDiv--==List=="+zone+"==>>"+circle+"==>"+division+"==>"+subdiv);
		return feederService.getSStationBySubByDiv(zone, circle, division, subdiv, model);
	}

	@RequestMapping(value = "/manageMeters", method = { RequestMethod.GET, RequestMethod.POST })
	public String manageMeters(ModelMap model, HttpServletRequest request)// get active and inactive count
	{

		List<FeederMasterEntity> ZoneList = feederService.getDistinctZone();
		model.put("ZoneList", ZoneList);
		model.put("results", "notDisplay");

		return "mtrDetailsMDAS";

	}

	@RequestMapping(value = "/totalMeterDetailsMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String totalMeterDetails(ModelMap model, HttpServletRequest request,HttpSession session)// get active and inactive count
	{
		String officeType = (String) session.getAttribute("officeType");
		String officeName = (String) session.getAttribute("officeName");
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);
		model.put("results", "notDisplay");
		String type = request.getParameter("type");
		String circle = request.getParameter("circle");
		String region = request.getParameter("region");
		System.err.println(officeType+" nmae---"+officeName);
		String qrylevel = " ";
		String cis = "";
		String subdivision = request.getParameter("subdivision");
		
		 if("region".equalsIgnoreCase(officeType)) {
			 qrylevel=" and zone like '"+officeName+"' ";
		}
		 else if ("region".equalsIgnoreCase(officeType)) {
			 qrylevel=" and circle like like '"+officeName+"' ";
		 }
		if(circle == null)
		{
			cis = "%";
		}
		else
		{
			cis = circle;
		}
		

		

		if (subdivision != null && !"".equals(subdivision)) {
			qrylevel += " AND subdivision='" + subdivision + "'";
			;
		}
		model.put("type", type);
		model.put("hideFlag", "false");
//		System.out.println("qryLevel: "+qrylevel);
		if (type != null || "".equalsIgnoreCase(type)) {
			// System.out.println("----------->>>>> "+type);
			String qry = "";

			if ("active".equals(type)) {
				qry = "SELECT modem_sl_no, mtrno, fdrname, substation, subdivision, division, district, circle, last_communicated_date,customer_name,customer_mobile,customer_address,accno,consumerstatus,tariffcode,kworhp,sanload,contractdemand,mrname,kno,location_id "
						+ "FROM meter_data.master_main  WHERE circle like '"+cis+"' AND mtrno IN ( SELECT DISTINCT meter_number FROM meter_data.modem_communication WHERE DATE = CURRENT_DATE)"
						+ qrylevel;
			} else if ("notcom".equals(type)) {

				qry = "SELECT modem_sl_no, mtrno, fdrname, substation, subdivision, division, district, circle, last_communicated_date,customer_name,customer_mobile,customer_address,accno,consumerstatus,tariffcode,kworhp,sanload,contractdemand,mrname,kno,location_id "
						+ "FROM meter_data.master_main WHERE circle like '"+cis+"' AND mtrno NOT IN ( SELECT DISTINCT meter_number FROM meter_data.modem_communication WHERE DATE = CURRENT_DATE)"
						+ qrylevel;

			}
			
			//System.err.println(qry);

			if (!"".equals(qry)) {
				
				List<?> mlist = entityManager.createNativeQuery(qry).getResultList();
				model.put("mDetailList", mlist);
			}

			model.put("hideFlag", "true");

		}
		return "mtrDetails2";

	}

    
	@RequestMapping(value = "/getAllMetersBasedOnMDAS/{zone}/{circle}/{division}/{subdiv}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getAllMetersBasedOn(@PathVariable String zone, @PathVariable String circle,
			@PathVariable String division, @PathVariable String subdiv, HttpServletRequest request, ModelMap model) {
		//System.out.println(
				//"==--getFeedersBasedOn--==List==" + zone + "==>>" + circle + "==>>" + division + "==>>" + subdiv);
		return modemCommService.getAllMetersBasedOn(zone, circle, division, subdiv);
	}

	@RequestMapping(value = "/getAllMetersBasedOnMDM", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getAllMetersBasedOnMDM(HttpServletRequest request, ModelMap model) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("subdiv");
		String towncode = request.getParameter("towncode");
		String location = request.getParameter("location");
		
		//System.out.println(
				//"==--getAllMetersBasedOnMDM--==List==" + zone + "==>>" + circle + "==>>" + division + "==>>" + subdiv);
		// return modemCommService.getAllMetersBasedOn(zone,circle,division,subdiv);
		return modemCommService.getAllMetersBasedOnZoneCircle(zone, circle, towncode,location);
	}

	@RequestMapping(value = "/getInactiveModemsSubLevelMDAS/{levelName}/{columnName}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<?> getInactiveModemDetailszone(@PathVariable String levelName,
			@PathVariable String columnName, ModelMap model, HttpServletRequest request)// get active and inactive count
	{
		System.out.println(columnName + "##################################333333333333333 " + levelName);
		levelName = levelName.replace("@@@", "/");
		System.out.println(levelName + " SSSSSSSSSSSSSSSSS");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> activityMap = null;
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

		String where = "and " + columnName + "='" + levelName + "' "
				+ queryLocationAndCountWhere.replace("WHERE", "AND");// FOR SUBLOCATION

		if (levelName.equals("HEADER")) {// FOR MAIN HEADER
			where = queryLocationAndCountWhere.replace("WHERE", "AND");
		}

		System.out.println("00000000000000000000000000000 " + where);

		String inactive = "select rt.modem_sl_no,rt.mtrno,rt.fdrname,rt.substation,rt.subdivision,rt.division,rt.district,rt.circle,lt.last_communication from"
				+ " (SELECT modem_sl_no,mtrno,fdrname,substation,subdivision,division,district,circle"
				+ " from meter_data.master_main where  modem_sl_no not in (select inactive from(SELECT (CASE WHEN sd.times < 24 THEN sd.imei END)as inactive FROM ("
				+ " SELECT lstCom.imei, lstCom.lastComm, EXTRACT ( epoch FROM age( to_timestamp( '" + timeStamp
				+ "', 'YYYY-MM-DD HH24:MI:SS.MS' ), "
				+ " lstCom.lastComm ) / 3600 ) AS times FROM ( SELECT imei, MAX (last_communication) AS lastComm FROM meter_data.modem_communication "
				+ " where imei is not null GROUP BY imei ) lstCom) sd )dd where dd.inactive is not null) " + where
				+ " ) rt LEFT JOIN (SELECT imei, MAX (last_communication)"
				+ " AS last_communication FROM meter_data.modem_communication where imei is not null GROUP BY imei ) lt ON rt.modem_sl_no=lt.imei	";

		String inactiveNew = "SELECT m.modem_sl_no,m.mtrno,m.fdrname,m.substation,m.subdivision,m.division,m.district,m.circle,b.last_communication  FROM meter_data.master_main m LEFT JOIN\n"
				+ "(\n"
				+ "SELECT DISTINCT meter_number, max(last_communication) as last_communication from meter_data.modem_communication GROUP BY meter_number\n"
				+ ")b ON m.mtrno=b.meter_number WHERE b.meter_number is NULL " + where;

//		System.err.println("GET INACTIVE TOTAL SUBLEVEL : " + inactiveNew);
		Query queryUpload = entityManager.createNativeQuery(inactiveNew);
		List<Object> li = queryUpload.getResultList();

		for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			activityMap = new HashMap<>();
			activityMap.put("modem_sl_no", (values[0] == null) ? "--" : values[0]);
			activityMap.put("mtrno", (values[1] == null) ? "--" : values[1]);
			activityMap.put("fdrname", (values[2] == null) ? "--" : values[2]);
			activityMap.put("substation", (values[3] == null) ? "--" : values[3]);
			activityMap.put("subdivision", (values[4] == null) ? "--" : values[4]);
			activityMap.put("division", (values[5] == null) ? "--" : values[5]);
			activityMap.put("district", (values[6] == null) ? "--" : values[6]);
			activityMap.put("circle", (values[7] == null) ? "--" : values[7]);
			activityMap.put("lastComm", (values[8] == null) ? "--" : values[8]);
			result.add(activityMap);
		}
		return result;
	}

	@RequestMapping(value = "/getLastDaysActiveStatusMDAS", method = { RequestMethod.GET })
	public @ResponseBody List<Object[]> getLastDaysActiveStatus(ModelMap model, HttpServletRequest request,HttpSession session)
			throws JSONException// get active and inactive count
	{
		// System.out.println("##################################9999999999999999 "+
		// queryLocationAndCountWhere);
		String subLocationWhere = queryLocationAndCountWhere2.replace("WHERE", " AND ");
		System.out.println(subLocationWhere);
	//	queryLocationAndCountWhere = "WHERE  location_name='" + value + "'";
		
		String officeType = (String) session.getAttribute("officeType");
		String officeName = (String) session.getAttribute("officeName");
		String type=request.getParameter("type");
		String value=request.getParameter("value");
		System.out.println(officeType+"----"+type);
		System.out.println(officeName+"----"+value);
		
		
		
//		System.out.println("queryLocationAndCountWhere="+queryLocationAndCountWhere);
//		System.out.println("subLocationWhere="+subLocationWhere);
		
		String active = "";

		if ("corporate".equalsIgnoreCase(type)) {
			active = "select to_char((CURRENT_DATE + i), 'Mon-DD' ) as date, COALESCE(modem.communicating,0) as active,COALESCE(modem.non_communicating,0) as inactive,COALESCE(modem.total,0) as total from generate_series(-30,-1 ) i\r\n" + 
					"LEFT JOIN (select communicating,non_communicating,total,date from meter_data.last_30_days_data where date(date)>= CURRENT_DATE-30 and date(date)<=CURRENT_DATE-1 and flag = '1' ) modem \r\n" + 
					"on modem.date = CURRENT_DATE + i order by CURRENT_DATE+i ";
		} else if ("circle".equalsIgnoreCase(type)) {

			active = "select to_char((CURRENT_DATE + i), 'Mon-DD' ) as date, COALESCE(modem.communicating,0) as active,COALESCE(modem.non_communicating,0) as inactive,COALESCE(modem.total,0) as total from generate_series(-30,-1 ) i\r\n" + 
					"LEFT JOIN (select distinct on (date)date as date ,communicating,non_communicating,total from meter_data.last_30_days_data where flag='0' and location_name ='"+value+"' and  date(date)>= CURRENT_DATE-30 and date(date)<=CURRENT_DATE-1) modem \r\n" + 
					"on modem.date = CURRENT_DATE + i order by CURRENT_DATE+i ";
		}else if ("region".equalsIgnoreCase(type)) {
			active = "select to_char((CURRENT_DATE + i), 'Mon-DD' ) as date, COALESCE(modem.communicating,0) as active,COALESCE(modem.non_communicating,0) as inactive,COALESCE(modem.total,0) as total from generate_series(-30,-1 ) i \r\n" + 
					"					LEFT JOIN (select distinct on (date)date as date ,communicating,non_communicating,total from meter_data.last_30_days_data where flag='2' and location_name ='"+value+"' and  date(date)>= CURRENT_DATE-30 and date(date)<=CURRENT_DATE-1) modem  \r\n" + 
					"					on modem.date = CURRENT_DATE + i order by CURRENT_DATE+i ";
		}
//		String active = "select to_char((CURRENT_DATE + i), 'Mon-DD' ) as date, COALESCE(modem.communicating,0) as active,COALESCE(modem.non_communicating,0) as inactive,COALESCE(modem.total,0) as total from generate_series(-30,-1 ) i\r\n" + 
//				"LEFT JOIN (select communicating,non_communicating,total,date from meter_data.last_30_days_data) modem \r\n" + 
//				"on modem.date = CURRENT_DATE + i";
		System.out.println("active <==============> "+active);
		// System.err.println("GET LAST DAYS ACTIVE COUNTS : "+active);
		Query queryUpload = entityManager.createNativeQuery(active);
		List<Object[]> li = queryUpload.getResultList();

		return li;
	}
	
	/*
	 * @RequestMapping(value = "/getMappedMetersDetails", method = {
	 * RequestMethod.GET }) public @ResponseBody List<?>
	 * getMappedMetersDetails(ModelMap model, HttpServletRequest request) throws
	 * JSONException {
	 * 
	 * // Query to display number of mapped meters String query =
	 * "select to_char((CURRENT_DATE + i), 'Mon-DD' ) as date,\n" +
	 * "COALESCE(mapp1.total,0) as mapped from generate_series(-30,-1 ) i \n" +
	 * "LEFT JOIN (SELECT count(*) as total,date(updateddate) as time_stamp\n" +
	 * "FROM meter_data.initial_meter_info a WHERE sync_status=1 and data_type='MasterInfo'\n"
	 * +regionSubLevel+ "and updateddate is not null\n" +
	 * "GROUP BY date(updateddate))\n" +
	 * "mapp1 on mapp1.time_stamp=CURRENT_DATE + i";
	 * 
	 * Query queryUpload = entityManager.createNativeQuery(query); List<Object[]>
	 * list = queryUpload.getResultList();
	 * 
	 * return list; }
	 */

	@RequestMapping(value = "/getNotUploadedModemsSubLevelMDAS/{levelName}/{columnName}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<?> getNotUploadedModemsSubLevel(@PathVariable String levelName,
			@PathVariable String columnName, ModelMap model, HttpServletRequest request)// get active and inactive count
	{
//		System.out.println(columnName + "##################################NOTUPLOADED " + levelName);
		levelName = levelName.replace("@@@", "/");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> activityMap = null;
		String where = "and mm." + columnName + "='" + levelName + "' "
				+ queryLocationAndCountWhere.replace("WHERE", "AND");// FOR SUBLOCATION

		if (levelName.equals("HEADER")) {// FOR MAIN HEADER
			where = queryLocationAndCountWhere.replace("WHERE", "AND");
		}

		System.out.println("00000000000000000000000000000 " + where);
		String notUploaded = "select fq.meter_number,mm.fdrname,mm.substation,fq.fail_reason from (SELECT DISTINCT meter_number, fail_reason FROM meter_data.xml_upload_status WHERE file_date=CURRENT_DATE-1 and upload_status = 0)fq,meter_data.master_main mm where fq.meter_number=mm.mtrno "
				+ where;
//		System.err.println("GET INACTIVE TOTAL SUBLEVEL : " + notUploaded);
		Query queryUpload = entityManager.createNativeQuery(notUploaded);
		List<Object> li = queryUpload.getResultList();

		for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			activityMap = new HashMap<>();
			activityMap.put("meter_number", (values[0] == null) ? "--" : values[0]);
			activityMap.put("fdrname", (values[1] == null) ? "--" : values[1]);
			activityMap.put("substation", (values[2] == null) ? "--" : values[2]);
			activityMap.put("fail_reason", (values[3] == null) ? "--" : values[3]);
			result.add(activityMap);
		}
		return result;
	}

	@RequestMapping(value = "/modemDetailsMDAS", method = RequestMethod.GET)
	public String modemDetails(ModelMap model, HttpServletRequest request) {
		String METERNO = request.getParameter("METERNO");
		String MODEM = request.getParameter("MODEM");
		model.addAttribute("METERNO", METERNO);
		model.addAttribute("MODEM", MODEM);

		ModelMap modem_Info = modemCommService.modemInformation(MODEM);

		model.putAll(modem_Info);

		return "modemInfoMDAS";
	}

	@RequestMapping(value = "/subStationDetailsMDAS", method = RequestMethod.GET)
	public String subStationDetails(ModelMap model, HttpServletRequest request) {

		System.out.println("===========subStationDetails==============");

		feederMasterService.getFeederInfo(model, request);
		// modemCommService.getActInactSubStationWise( model, request);

		return "subStationDetailsMDAS";
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/metertDetailsMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String metertDetails(HttpServletResponse response, HttpServletRequest request, ModelMap model)
			throws JsonParseException, JsonMappingException, IOException {

		System.out.println("enter to /metertDetails");
		String FEEDERNAME = request.getParameter("FEEDERNAME");
		String FEEDERCODE = request.getParameter("FEEDERCODE");
		String METERNO = request.getParameter("METERNO");
		String imei = request.getParameter("imei");

		model.addAttribute("FEEDERNAME", FEEDERNAME);
		model.addAttribute("FEEDERCODE", FEEDERCODE);
		model.addAttribute("METERNO", METERNO);
		model.addAttribute("imei", imei);

		ModelMap FM_Info = feederMasterService.getFdrMeterInfo(FEEDERNAME, FEEDERCODE);
		model.putAll(FM_Info);

		ModelMap fdrTimeStmp = modemCommService.getFdrTimeStamps(METERNO, imei);
		model.putAll(fdrTimeStmp);

		ModelMap fdrInsData = amrInstantaneousService.getFdrInstantaneousData(imei, METERNO);
		model.putAll(fdrInsData);

		ModelMap fdrInsGraphData = amrInstantaneousService.getfdrInsGraphData(imei);
		model.putAll(fdrInsGraphData);

		String query = "SELECT COUNT(CASE WHEN a.upload_status = 0 then 1 ELSE NULL END) as pending ,COUNT(CASE WHEN a.upload_status = 1 then 1 ELSE NULL END) as uploaded FROM meter_data.xml_upload_status AS A WHERE	A .meter_number = '"
				+ METERNO + "'";
		System.out.println(query);
		Query q = entityManager.createNativeQuery(query);
		List<Object> list = q.getResultList();

		Object[] obj = (Object[]) list.get(0);
		String Pending = obj[0].toString();
		String upLoaded = obj[1].toString();
		System.out.println("Pending======" + obj[0].toString());
		System.out.println("upLoaded======" + obj[1].toString());
		model.put("Pending", Pending);
		model.put("upLoaded", upLoaded);

		return "meterInfoMDAS";
	}

	@RequestMapping(value = "/modemDetailsInactiveMDAS", method = RequestMethod.GET)
	public String modemDetailsInactive(@RequestParam String modem_sl_no, @RequestParam String mtrno,
			@RequestParam String substation, ModelMap model, HttpServletRequest request) {

		System.out.println("modem_sl_no==========" + modem_sl_no);
		model.put("METERNO", mtrno);
		model.put("MODEM", modem_sl_no);
		model.put("substation", substation);
		ModelMap modem_Info = modemCommService.modemInformation(modem_sl_no);

		try {
			MasterMainEntity main = MasterMainService.getFeedersBasedOnImei(modem_sl_no).get(0);
			model.put("accno", main.getAccno());
			model.put("cname", main.getCustomer_name());

		} catch (Exception e) {
			// TODO: handle exception
		}

		model.putAll(modem_Info);

		return "modemInfoMDAS";

	}

	private String zone = "", circle = "", division = "", subDivision = "", subStation = "";
	private String selectedLevel = "", selectedLevelName = "";
	String queryLocationAndCountWhere = "";
	String queryLocationAndCountWhere2 = "";
	@RequestMapping(value = "/dashBoard2MDAS", method = RequestMethod.GET)
	public String openDadhBoard(@RequestParam String type, @RequestParam String value, ModelMap model,
			HttpServletRequest request) {
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		selectedLevelName = value.trim();
		// System.out.println("-----------------------selected sub
		// level--"+value+"---------------------------");
		String level = "", sublevel = "";
		String columnNameSubLevel = "";
		String urlLavel = "";
		switch (type) {
		/*
		 * case "Corporate": selectedLevel=""; queryLocationAndCountWhere="";//NO
		 * CONDITIONS FOR CORPORATE LEVEL columnNameSubLevel="zone"; level="Corporate";
		 * sublevel="All Zones"; break;
		 * 
		 * case "All Zones": selectedLevel="zone"; zone=value;
		 * queryLocationAndCountWhere="WHERE zone='"+value+"'";
		 * columnNameSubLevel="circle"; level="Zone"; sublevel="All Circles";
		 * urlLavel="&zone="+zone; break;
		 * 
		 * case "All Circles": selectedLevel="circle"; circle=value;
		 * queryLocationAndCountWhere="WHERE zone='"+zone+"'and circle='"+value+"'";
		 * columnNameSubLevel="division"; level="Circle"; sublevel="All Divisions";
		 * urlLavel="&zone="+zone+"&circle="+circle; break;
		 */

//		case "Corporate":
//			selectedLevel="division";
//			division=value;
//			queryLocationAndCountWhere="";
//			columnNameSubLevel="subdivision";
//			level="Corporate";
//			sublevel="All Sub Divisions";
//			urlLavel="";
//			break;
		case "corporate":
			selectedLevel = "zone";
			zone = value;
			queryLocationAndCountWhere = "";
			
			columnNameSubLevel = "circle";
			level = "corporate";
			sublevel = "circle";
			urlLavel = "";
			break;
			///sowjanya implementation for region level login
		case "region":
			selectedLevel = "zone";
			zone = value;
			queryLocationAndCountWhere = "WHERE zone like '"+value+"'";
			columnNameSubLevel = "circle";
			level = "region";
			sublevel = "circle";
			urlLavel="&zone=" + zone;
			//regionLevel="where zone like '"+value+"'";
			 //regionSubLevel = "and zone like '"+value+"'";
			break;

		case "circle":
			selectedLevel = "circle";
			circle = value;
			queryLocationAndCountWhere = "WHERE  circle='" + value + "'";
			queryLocationAndCountWhere2 = "WHERE  location_name='" + value + "'";
			
			columnNameSubLevel = "division";
			level = "Circle";
			sublevel = "All Divisions";
			urlLavel = "&zone=" + zone + "&circle=" + circle;
			break;
			
		

		case "All Sub Divisions":
			selectedLevel = "subdivision";
			subDivision = value;
			queryLocationAndCountWhere = "WHERE subdivision='" + value + "'";
			columnNameSubLevel = "substation";
			level = "Sub Division";
			sublevel = "";
			urlLavel = "&subdivision=" + subDivision;
			/*
			 * String getFeeders="SELECT mm.fdrname, mm.fdrcode, mm.mtrno, mm.modem_sl_no,"
			 * +
			 * " (case when sb.lastComm is null then 'Not Communicated Yet' else (case when EXTRACT (epoch	FROM age(to_timestamp('"
			 * +timeStamp+"','YYYY-MM-DD HH24:MI:SS.MS'),sb.lastComm ) / 3600)<24 then 'Active' else 'Inactive' end) end) as lastComms  "
			 * + "FROM  (SELECT fdrname, fdrcode, mtrno,modem_sl_no FROM  " +
			 * "meter_data.master_main "+queryLocationAndCountWhere+" )mm "
			 * +" LEFT JOIN( SELECT mc.imei AS mdlNo, MAX(mc.last_communication) AS lastComm FROM meter_data.modem_communication mc GROUP BY mc.imei )sb on mm.modem_sl_no=sb.mdlNo"
			 * ;
			 */
			String getFeeders = "SELECT mm.accno, mm.customer_name, mm.mtrno,"
					+ "( CASE WHEN sb.lastComm IS NULL THEN 'Not Communicated Yet' ELSE 'Active' END) AS lastComms "
					+ "FROM ( SELECT * FROM meter_data.master_main " + queryLocationAndCountWhere + " ) mm LEFT JOIN "
					+ "( SELECT mc.meter_number AS mdlNo, MAX (mc.last_communication) AS lastComm FROM meter_data.modem_communication mc "
					+ "GROUP BY mc.meter_number ) sb ON mm.mtrno = sb.mdlNo";

			// System.out.println(""+);
			 System.err.println("GET ALL FEEDERS : "+getFeeders);
			Query q7 = entityManager.createNativeQuery(getFeeders);
			List<Object[]> listFeeders = q7.getResultList();
			model.addAttribute("feederInfoList", listFeeders);
			break;

		/*
		 * case "All Sub Stations": selectedLevel="substation"; subStation=value;
		 * queryLocationAndCountWhere="WHERE zone='"+zone+"' and circle='"
		 * +circle+"' and division='"+division+"' and subdivision='"
		 * +subDivision+"' and substation='"+value+"'"; columnNameSubLevel="fdrname";
		 * level="Sub Station"; sublevel="All Feeders";
		 * urlLavel="&zone="+zone+"&circle="+circle+"&division="+division+
		 * "&subdivision="+subDivision+"&substation="+subStation;
		 * 
		 * 
		 * //GETTTIN ALL FEEDERS UNDER
		 * SUBSTATION=================================START=============================
		 * ====================== String
		 * getFeeders="SELECT mm.fdrname, mm.fdrcode, mm.mtrno, mm.modem_sl_no," +
		 * " (case when sb.lastComm is null then 'Not Communicated Yet' else (case when EXTRACT (epoch	FROM age(to_timestamp('"
		 * +timeStamp+"','YYYY-MM-DD HH24:MI:SS.MS'),sb.lastComm ) / 3600)<24 then 'Active' else 'Inactive' end) end) as lastComms  "
		 * + "FROM  (SELECT fdrname, fdrcode, mtrno,modem_sl_no FROM  " +
		 * "meter_data.master_main "+queryLocationAndCountWhere+" )mm "
		 * +" LEFT JOIN( SELECT mc.imei AS mdlNo, MAX(mc.last_communication) AS lastComm FROM meter_data.modem_communication mc GROUP BY mc.imei )sb on mm.modem_sl_no=sb.mdlNo"
		 * ;
		 * 
		 * //System.out.println(""+);
		 * System.err.println("GET ALL FEEDERS : "+getFeeders); Query q7=
		 * entityManager.createNativeQuery(getFeeders); List<Object[]>
		 * listFeeders=q7.getResultList(); model.addAttribute("feederInfoList",
		 * listFeeders); //GETTTIN ALL FEEDERS UNDER
		 * SUBSTATION=================================END===============================
		 * ======================
		 */
		}

		// GETTTIN TOTAL METER COUNT AND LOCATION
		// DETAILS====================START====================================================
		String queryLocationAndCount = "SELECT  COUNT (*) AS mtrCOUNT,COUNT (DISTINCT zone) AS ZONECOUNT, COUNT (DISTINCT circle) AS CIRCLECOUNT, COUNT (DISTINCT division) AS DIVISIONCOUNT, "
				+ "COUNT (DISTINCT subdivision) AS SUBDIVISIONCOUNT, COUNT (DISTINCT substation) AS SUBSTATIONCOUNT FROM meter_data.master_main  ";
		queryLocationAndCount = queryLocationAndCount + queryLocationAndCountWhere;// APPENDING WHERE CONDITIONS
																					// ACCORDING TO THE LEVEL
		 System.err.println("GET LOCATION LEVEL COUNT : "+queryLocationAndCount);
		Query q = entityManager.createNativeQuery(queryLocationAndCount);
		List<Object> list = q.getResultList();

		// System.out.println("queryLocationAndCount======"+queryLocationAndCount);
		int totalMeters = 0;

		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			totalMeters = Integer.parseInt(values[0].toString());
			model.addAttribute("totalMeters", values[0]);
			model.addAttribute("ZONECOUNT", values[1]);
			model.addAttribute("CIRCLECOUNT", values[2]);
			model.addAttribute("DIVISIONCOUNT", values[3]);
			model.addAttribute("SUBDIVISIONCOUNT", values[4]);
			model.addAttribute("SUBSTATIONCOUNT", values[5]);

		}
		// GETTTIN TOTAL METER COUNT AND LOCATION
		// DETAILS====================END=====================================================
		// GETTTIN TOTAL SUB LOCATION
		// DETAILS================================START===================================================
		String subLocationWhere = queryLocationAndCountWhere.replace("WHERE", " AND ");
//		String querySubLocations = "SELECT D." + columnNameSubLevel
//				+ ",COALESCE (D.total,0)as total,COALESCE (D.active, 0) as active, COALESCE (C.uploaded, 0) AS uploaded,"
//				+ "COALESCE (D.lastactive,0) as lastactive FROM " + "( SELECT A." + columnNameSubLevel
//				+ ",A.total, B.active,B.lastactive FROM ( SELECT " + columnNameSubLevel
//				+ ",COUNT (*) as total FROM meter_data.master_main " + queryLocationAndCountWhere  +" GROUP BY "
//				+ columnNameSubLevel + ") AS A " + "LEFT JOIN ( " + " SELECT X." + columnNameSubLevel
//				+ ",X.active,Y.lastactive FROM( " + "SELECT " + columnNameSubLevel
//				+ ", count(*) AS active FROM meter_data.master_main a,"
//				+ "(SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number"
//				+ ")b WHERE a.mtrno=b.meter_number  " + subLocationWhere + " GROUP BY	" + columnNameSubLevel + ""
//				+ ") X LEFT JOIN " + "( SELECT mm." + columnNameSubLevel
//				+ ", COUNT (DISTINCT mc.meter_number) as lastactive FROM meter_data.modem_communication mc, meter_data.master_main mm "
//				+ "WHERE mc.meter_number = mm.mtrno  AND mc. DATE = (CURRENT_DATE - 1) " + subLocationWhere
//				+ " GROUP BY mm." + columnNameSubLevel + " )Y ON X." + columnNameSubLevel + "=Y." + columnNameSubLevel
//				+ "  "
//
//				+ ") B ON A." + columnNameSubLevel + "=B." + columnNameSubLevel + " ) D " + "LEFT JOIN ( SELECT mm. "
//				+ columnNameSubLevel + ", COUNT ( CASE WHEN mstat.create_status = 1 THEN 1 END) AS uploaded "
//				+ "FROM meter_data.xml_upload_status mstat, meter_data.master_main mm WHERE mm.mtrno = mstat.meter_number "
//				+ subLocationWhere + " AND mstat.file_date = (CURRENT_DATE - 1) " + "GROUP BY mm. " + columnNameSubLevel
//				+ " ) C ON D." + columnNameSubLevel + "=C." + columnNameSubLevel + ";";
		
		String querySubLocations="";
		if(type.equalsIgnoreCase("corporate"))
		{
			 querySubLocations="select distinct * from meter_data.circle_status";
		}
		
		else {
			querySubLocations = "SELECT D." + columnNameSubLevel
				+ ",COALESCE (D.total,0)as total,COALESCE (D.active, 0) as active, COALESCE (C.uploaded, 0) AS uploaded,"
				+ "COALESCE (D.lastactive,0) as lastactive FROM " + "( SELECT A." + columnNameSubLevel
				+ ",A.total, B.active,B.lastactive FROM ( SELECT " + columnNameSubLevel
				+ ",COUNT (*) as total FROM meter_data.master_main " + queryLocationAndCountWhere  +" GROUP BY "
				+ columnNameSubLevel + ") AS A " + "LEFT JOIN ( " + " SELECT X." + columnNameSubLevel
				+ ",X.active,Y.lastactive FROM( " + "SELECT " + columnNameSubLevel
				+ ", count(*) AS active FROM meter_data.master_main a,"
				+ "(SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number"
				+ ")b WHERE a.mtrno=b.meter_number  " + subLocationWhere + " GROUP BY	" + columnNameSubLevel + ""
				+ ") X LEFT JOIN " + "( SELECT mm." + columnNameSubLevel
				+ ", COUNT (DISTINCT mc.meter_number) as lastactive FROM meter_data.modem_communication mc, meter_data.master_main mm "
				+ "WHERE mc.meter_number = mm.mtrno  AND mc. DATE = (CURRENT_DATE) " + subLocationWhere
				+ " GROUP BY mm." + columnNameSubLevel + " )Y ON X." + columnNameSubLevel + "=Y." + columnNameSubLevel
				+ "  "

				+ ") B ON A." + columnNameSubLevel + "=B." + columnNameSubLevel + " ) D " + "LEFT JOIN ( SELECT mm. "
				+ columnNameSubLevel + ", COUNT ( CASE WHEN mstat.create_status = 1 THEN 1 END) AS uploaded "
				+ "FROM meter_data.xml_upload_status mstat, meter_data.master_main mm WHERE mm.mtrno = mstat.meter_number "
				+ subLocationWhere + " AND mstat.file_date = (CURRENT_DATE) " + "GROUP BY mm. " + columnNameSubLevel
				+ " ) C ON D." + columnNameSubLevel + "=C." + columnNameSubLevel + ";"; 
		}
		
		

		System.out.println("################" +querySubLocations);
		Query q2 = entityManager.createNativeQuery(querySubLocations);
		List<Object[]> listSub = q2.getResultList();
		List<Object[]> listSubLocation = new ArrayList<>();
		for (int i = 0; i < listSub.size(); i++) {
			Object[] columns = listSub.get(i);
			String location = (null != columns[0]) ? columns[0].toString() : "-";
			String total = (null != columns[1]) ? columns[1].toString() : "0";
			String active = (null != columns[2]) ? columns[2].toString() : "0";
			String uploaded = (null != columns[3]) ? columns[3].toString() : "0";
			String lastActive = (null != columns[4]) ? columns[4].toString() : "0";

			int inactive = Integer.parseInt(total) - Integer.parseInt(active);
			int notUploaded = Integer.parseInt(lastActive) - Integer.parseInt(uploaded);
			int activePercentage = Integer.parseInt(
					new DecimalFormat("#").format((Double.parseDouble(lastActive) / Double.parseDouble(total) * 100)));
			int uploadPercentage = Integer.parseInt(
					new DecimalFormat("#").format((Double.parseDouble(uploaded) / Double.parseDouble(total) * 100)));

			Object[] newColumns = new Object[9];
			newColumns[0] = location;
			newColumns[1] = total;
			newColumns[2] = active;
			newColumns[3] = inactive;
			newColumns[4] = uploaded;
			newColumns[5] = notUploaded;
			newColumns[6] = activePercentage;
			newColumns[7] = uploadPercentage;
			newColumns[8] = lastActive;
			listSubLocation.add(newColumns);
		}
		model.addAttribute("level", level);
		model.addAttribute("sub_level", sublevel);
		model.addAttribute("value", value);
		model.addAttribute("sublevel", listSubLocation);
		model.addAttribute("columnName", columnNameSubLevel);
		model.addAttribute("urlLavel", urlLavel);

		try {
			/*
			 * String
			 * qryNew="SELECT( SELECT count(*) FROM meter_data.master_main WHERE mtrno in ( SELECT DISTINCT meter_number from meter_data.modem_communication) "
			 * +subLocationWhere+" ) as comm, ( SELECT count(*) FROM meter_data.master_main WHERE mtrno NOT in ( SELECT DISTINCT meter_number from meter_data.modem_communication ) "
			 * +subLocationWhere+" ) notcomm, ( SELECT count(*) FROM meter_data.master_main WHERE mtr_change_date=to_char((CURRENT_DATE-1), 'YYYY-MM-DD') "
			 * +subLocationWhere+" )as mtrchng; ";
			 */
			/*
			 * String
			 * qryNew="SELECT( SELECT count(*) FROM meter_data.master_main WHERE modem_sl_no in ( SELECT DISTINCT imei from meter_data.modem_communication) "
			 * +subLocationWhere+" ) as comm, ( SELECT count(*) FROM meter_data.master_main WHERE modem_sl_no NOT in ( SELECT DISTINCT imei from meter_data.modem_communication ) "
			 * +subLocationWhere+" ) notcomm, ( SELECT count(*) FROM meter_data.master_main WHERE mtr_change_date=to_char((CURRENT_DATE-1), 'YYYY-MM-DD') "
			 * +subLocationWhere+" )as mtrchng; ";
			 */
			String qryNew="";
			
			if(type.equalsIgnoreCase("corporate")) {
				
//				qryNew="select distinct * from meter_data.comm_active";
				
				qryNew="SELECT (SELECT count(*) AS count\r\n" + 
						"           FROM meter_data.master_main a LEFT JOIN\r\n" + 
						"            (SELECT DISTINCT modem_communication.meter_number\r\n" + 
						"                   FROM meter_data.modem_communication\r\n" + 
						"                  GROUP BY modem_communication.meter_number)b\r\n" + 
						"          ON ((b.meter_number) = (a.mtrno))) AS comm,\r\n" + 
						"    ( SELECT count(*) AS count\r\n" + 
						"           FROM (meter_data.master_main a\r\n" + 
						"             LEFT JOIN ( SELECT DISTINCT modem_communication.meter_number\r\n" + 
						"                   FROM meter_data.modem_communication\r\n" + 
						"                  GROUP BY modem_communication.meter_number) b ON (((a.mtrno) = (b.meter_number))))\r\n" + 
						"          WHERE (b.meter_number IS NULL)) AS notcomm,\r\n" + 
						"    ( SELECT count(*) AS count\r\n" + 
						"           FROM meter_data.master_main\r\n" + 
						"          WHERE ((master_main.mtr_change_date) = to_char(((CURRENT_DATE - 1)), 'YYYY-MM-DD'))) AS mtrchng,\r\n" + 
						"    (SELECT count(*) AS count\r\n" + 
						"           FROM meter_data.master_main a  JOIN\r\n" + 
						"            (SELECT DISTINCT modem_communication.meter_number\r\n" + 
						"                   FROM meter_data.modem_communication\r\n" + 
						"                  WHERE (modem_communication.date = CURRENT_DATE)\r\n" + 
						"                  GROUP BY modem_communication.meter_number) b\r\n" + 
						"          ON((a.mtrno) = (b.meter_number))) AS active ";
				
			}
			else {
			
			 qryNew =
			  "SELECT( SELECT count(*) FROM meter_data.master_main a, ( SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number)b "
			  + "WHERE a.mtrno=b.meter_number  " +
			  subLocationWhere+" ) AS comm, ( SELECT count(*) FROM meter_data.master_main a LEFT JOIN "
			  +
			  "( SELECT DISTINCT meter_number from meter_data.modem_communication GROUP BY meter_number )b ON a.mtrno=b.meter_number  "
			  + "WHERE b.meter_number is NULL " + subLocationWhere +
			  " ) notcomm, ( SELECT COUNT (*) FROM meter_data.master_main WHERE " +
			  "mtr_change_date = to_char( (CURRENT_DATE - 1), 'YYYY-MM-DD' ) " +
			  subLocationWhere +
			  " ) AS mtrchng, ( SELECT count(*) FROM meter_data.master_main a, " +
			  "( SELECT DISTINCT meter_number from meter_data.modem_communication WHERE date=CURRENT_DATE GROUP BY meter_number )b WHERE a.mtrno=b.meter_number"
			 + "  " + subLocationWhere +") AS active; ";
			 
			}
			
		
			
			System.out.println("New Dashboard qry: "+qryNew);
			List<?> listNew = entityManager.createNativeQuery(qryNew).getResultList();

			Object[] objNew = (Object[]) listNew.get(0);
			double mtrToComm = new BigInteger(objNew[0].toString()).doubleValue();
			double mtrNvrComm = new BigInteger(objNew[1].toString()).doubleValue();
			double tol = mtrToComm + mtrNvrComm;

			int activeCount = new BigInteger(objNew[3].toString()).intValue();
			int inactiveCount = (int) (tol - activeCount);
			model.addAttribute("mtrToComm", objNew[0]);
			model.addAttribute("mtrNvrComm", objNew[1]);
			model.addAttribute("mtrChnaged", objNew[2]);
			model.addAttribute("activeCount", activeCount);
			BigInteger inact = (BigInteger) entityManager.createNativeQuery("select count(*) from(\r\n"
					+ "SELECT modem_sl_no, mtrno, fdrname, substation, subdivision, division, district, circle, last_communicated_date,customer_name,customer_mobile,customer_address,accno,consumerstatus,tariffcode,kworhp,sanload,contractdemand,mrname,kno FROM meter_data.master_main WHERE mtrno NOT IN ( SELECT DISTINCT meter_number FROM meter_data.modem_communication WHERE DATE = CURRENT_DATE) )a")
					.getSingleResult();
			BigInteger installed = (BigInteger) entityManager
					.createNativeQuery("select count(*) from meter_data.meter_inventory where meter_status='INSTALLED'")
					.getSingleResult();
			
			//Commented bcz DashBoard Slowness Issue
			
//			BigInteger unmappedMeter = (BigInteger) entityManager.createNativeQuery(
//					"select count(*) from (select A.meter_number,case when np.manufacturer_name is null then '' else np.manufacturer_name end ,A.first_comm,A.LAST_comm from (SELECT meter_number,min(last_communication) AS first_comm ,max(last_communication) as LAST_comm  FROM meter_data.modem_communication WHERE DATE(last_communication)>=CURRENT_DATE-30 and meter_number NOT IN (SELECT mtrno FROM meter_data.master_main) GROUP BY meter_number) A LEFT JOIN meter_data.name_plate np on np.meter_serial_number=a.meter_number) a")
//					.getSingleResult(); //DashBoard Issue
		//	model.addAttribute("installed", installed); //DashBoard Issue
		//	model.addAttribute("unmappedMeter", unmappedMeter); //DashBoard Issue

			
			// model.addAttribute("activeper",Math.round((activeCount/mtrToComm)*100));
			model.addAttribute("activeper", Math.round((activeCount / installed.doubleValue()) * 100));
			model.addAttribute("inActiveCount", inactiveCount);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		try {
			
			String queryHESAndCount = "";
			
			if(type.equalsIgnoreCase("circle"))
			{

			// System.out.println("-------------Tarik Implementation-------------");
			 queryHESAndCount = "SELECT * FROM\n" +
					 " (SELECT hes_type, \"count\"(m.*),\"count\"(case WHEN mc.lcom is NOT null THEN 1 END) as active,\n" +
					 "count(case WHEN mc.lcom is null THEN 1 END) as inactive,\n" +
					 "count(case WHEN date(mc.lcom)=CURRENT_DATE THEN 1 END) as comm_today,\n" +
					 "count(case WHEN date(mc.lcom)<CURRENT_DATE THEN 1 END) as not_com_today,circle\n" +
					 "FROM meter_data.master_main  m LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number  GROUP BY hes_type,circle)ag WHERE ag.circle = '"+value+"'";
			}
			else
			{
				queryHESAndCount = "SELECT hes_type, \"count\"(m.*),\"count\"(case WHEN mc.lcom is NOT null THEN 1 END) as active,\r\n"
						+ "\"count\"(case WHEN mc.lcom is null THEN 1 END) as inactive,\r\n"
						+ "\"count\"(case WHEN date(mc.lcom)=CURRENT_DATE THEN 1 END) as comm_today,\r\n"
						+ "\"count\"(case WHEN date(mc.lcom)<CURRENT_DATE THEN 1 END) as not_com_today\r\n"
						+ "FROM meter_data.master_main m LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number "+queryLocationAndCountWhere+" GROUP BY hes_type";
			}
			
			Query qry = entityManager.createNativeQuery(queryHESAndCount);
			List<Object> listhes = qry.getResultList();
			List<Object[]> listHESLocation = new ArrayList<>();
			for (int i = 0; i < listhes.size(); i++) {
				Object[] columns = (Object[]) listhes.get(i);
				String hes_type = (null != columns[0]) ? columns[0].toString() : "Others";
				String total = (null != columns[1]) ? columns[1].toString() : "0";
				String active = (null != columns[2]) ? columns[2].toString() : "0";
				String inactive = (null != columns[3]) ? columns[3].toString() : "0";
				String com_today = (null != columns[4]) ? columns[4].toString() : "0";
				String notcom_today = (null != columns[5]) ? columns[5].toString() : "0";
			 System.out.println("Too check--"+hes_type+" "+total+" "+active+" "+com_today+" "+inactive);
				// "+notcom_today);
				int activeHESPercentage = Integer.parseInt(
						new DecimalFormat("#").format((Double.parseDouble(com_today) / Double.parseDouble(total) * 100)));
				
				
				Object[] newColumns = new Object[9];
				newColumns[0] = hes_type;
				newColumns[1] = total;
				newColumns[2] = active;
				newColumns[3] = inactive;
				newColumns[4] = com_today;
				newColumns[5] = notcom_today;
				newColumns[6] = activeHESPercentage;
				if(type.equalsIgnoreCase("circle"))
				{
				String circle = (null != columns[6]) ? columns[6].toString() : "0";
				newColumns[7] = circle;
				listHESLocation.add(newColumns);
				}
				else
				{
					newColumns[7] = "0";
				listHESLocation.add(newColumns);
				}
				
			}
			
			String sqlFderCatSummary="";
			if(type.equalsIgnoreCase("corporate")) {
			 sqlFderCatSummary="select distinct * from meter_data.fdrlist";
				
				}
			else {

		sqlFderCatSummary ="SELECT a.*, \n" +
					"round((a.active*100/a.total)) as act_per,\n" +
					"round((a.inactive*100.0/a.total)) as inact_per,\n" +
					"round((a.comm_today*100.0/a.total)) as com_per,\n" +
					"round((a.not_com_today*100.0/a.total),1) as notcom_per\n" +
					"FROM\n" +
					"(\n" +
					"SELECT fdrcategory, \"count\"(m.*) as total,\"count\"(case WHEN mc.lcom is NOT null THEN 1 END) as active,\n" +
					"\"count\"(case WHEN mc.lcom is null THEN 1 END) as inactive,\n" +
					"\"count\"(case WHEN date(mc.lcom)=CURRENT_DATE THEN 1 END) as comm_today,\n" +
					"\"count\"(case WHEN date(mc.lcom)<CURRENT_DATE THEN 1 END) as not_com_today\n" +
					"FROM meter_data.master_main m LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number   GROUP BY fdrcategory ORDER BY length(fdrcategory) DESC\n" +
					")a";
					
			}
		
			
		System.out.println(sqlFderCatSummary);

			List<?> fdrcatSummaryList = entityManager.createNativeQuery(sqlFderCatSummary).getResultList();
			model.addAttribute("fdrcatSummaryList", fdrcatSummaryList);
			

			//System.out.println("queryLocationAndCount======"+queryHESAndCount);
			model.addAttribute("HESDETAILS", listHESLocation);

			String notComQry = "SELECT reason_type, count(CASE WHEN loc_type='HT' THEN 1 END) as ht,\n"
					+ "count(CASE WHEN loc_type='LT' THEN 1 END) as lt,\n"
					+ "count(CASE WHEN loc_type='DT' THEN 1 END) as dt,\n"
					+ "count(CASE WHEN loc_type='BORDER METER' THEN 1 END) as bm,\n"
					+ "count(CASE WHEN loc_type='FEEDER METER' THEN 1 END) as fm\n"
					+ "FROM meter_data.non_communication_reason WHERE date=CURRENT_DATE-1 GROUP BY reason_type;";

			List<?> notComQryList = entityManager.createNativeQuery(notComQry).getResultList();
			model.addAttribute("notComQryList", notComQryList);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		try {
			String dashboadTotalPer ="select a.*, round((a.comm_today*100.0/a.total)) as com_per\r\n" + 
					"from\r\n" + 
					"(select \"count\"(m.mtrno) as total,\"count\"(case WHEN date(mc.lcom)=CURRENT_DATE THEN 1 END) as comm_today FROM meter_data.master_main m left join (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number)mc ON m.mtrno=mc.meter_number "+queryLocationAndCountWhere+")a";
		
			Query query = entityManager.createNativeQuery(dashboadTotalPer);
			List<?> lists = query.getResultList();
			//System.err.println(dashboadTotalPer);//queryLocationAndCountWhere
			for(Iterator<?> iterator = lists.iterator(); iterator.hasNext(); ) {
				Object[] values = (Object[]) iterator.next();
				
				model.addAttribute("total",  values[0]);
				model.addAttribute("com_today",  values[1]);
				model.addAttribute("com_per",  values[2]);
			
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
//		List<Object[]> unmappedCount = ds.unmappedMeters(); //DashBoard Issue
//		model.put("unmappedCount", unmappedCount.size()); //DashBoard Issue
			
		//Commented bcz DashBoard Slowness Issue
//		Object totalMappedMeters = modemMasterService.getCountForMappedMeters(); //DashBoard Issue
//		model.addAttribute("totalMappedMeters", totalMappedMeters.toString()); //DashBoard Issue
			
		return "dashBoard2MDAS";
	}
	
	
		

	@RequestMapping(value = "/longitudeLatitudeMDAS/{subDiv}/{subStation}", method = RequestMethod.GET)
	public @ResponseBody Object longitudeLatitude(@PathVariable String subDiv, @PathVariable String subStation,
			ModelMap model, HttpServletRequest request) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		if (subStation.contains("@") == true) {
			subStation = subStation.replaceAll("[@]", ".");
		}
		if (subStation.contains("_") == true) {
			subStation = subStation.replaceAll("[_]", "/");
		}
		if (subDiv.equalsIgnoreCase("All")) {
			subDiv = "%";
		} else {
			subDiv = subDiv;
		}
		if (subStation.equalsIgnoreCase("All")) {
			subStation = "%";
		} else {
			subStation = subStation;
		}
		String query = "SELECT\r\n" + "	mm. ZONE,\r\n" + "mm.modem_sl_no,\r\n" + "mm.longitude,\r\n"
				+ "mm.latitude,\r\n" + "mm.mtrno," + "mm.substation," + " (\r\n" + "		CASE\r\n"
				+ "		WHEN sd.times < 24 THEN\r\n" + "			'Active'\r\n" + "ELSE\r\n" + "'Inactive'\r\n"
				+ "		END\r\n" + "	) AS Status\r\n" + "FROM\r\n" + "	(\r\n" + "		SELECT\r\n"
				+ "			lstCom.imei,\r\n" + "			lstCom.lastComm,\r\n" + "			EXTRACT (\r\n"
				+ "				epoch\r\n" + "				FROM\r\n" + "					age(\r\n"
				+ "						to_timestamp(\r\n" + "							'" + timeStamp + "',\r\n"
				+ "							'YYYY-MM-DD HH24:MI:SS.MS'\r\n" + "						),\r\n"
				+ "						lstCom.lastComm\r\n" + "					) / 3600\r\n"
				+ "			) AS times\r\n" + "		FROM\r\n" + "			(\r\n" + "				SELECT\r\n"
				+ "					imei,\r\n" + "					MAX (last_communication) AS lastComm\r\n"
				+ "				FROM\r\n" + "					meter_data.modem_communication\r\n"
				+ "				GROUP BY\r\n" + "					imei\r\n" + "			) lstCom\r\n"
				+ "	) sd,\r\n" + "	meter_data.master_main AS mm\r\n" + "WHERE\r\n"
				+ "	mm.modem_sl_no = sd.imei and mm.subdivision like '" + subDiv + "' and mm.substation like '"
				+ subStation + "'";

		Query q = entityManager.createNativeQuery(query);
		// System.out.println(" lat long query====>"+q);
		List<?> list = q.getResultList();
		return list;
	}

	@RequestMapping(value = "/getSubstnLatLongBySubDivMDAS/{subDiv}", method = RequestMethod.GET)
	public @ResponseBody Object getSubstnLatLongBySubDiv(@PathVariable String subDiv, ModelMap model,
			HttpServletRequest request) {
		String query = "SELECT A.substation_name,A.substation_district,A.latitude,A.longitude,B.No_of_Feeder FROM "
				+ "(SELECT substation_name,substation_district,latitude,longitude FROM vcloudengine.substation_output  "
				+ "WHERE subdivision LIKE '" + subDiv + "')A " + "LEFT JOIN "
				+ "(SELECT substation,count(DISTINCT(feeder_name)) as No_of_Feeder FROM "
				+ "vcloudengine.feeder_output WHERE subdivision= "
				+ "(SELECT DISTINCT subdivision FROM vcloudengine.substation_output WHERE subdivision LIKE '" + subDiv
				+ "') " + "group by substation)B on A.substation_name=B.substation";
		List<?> list = entityManager.createNativeQuery(query).getResultList();
		// System.out.println(list.size()+" lat long query====>"+query);
		return list;
	}

	/*
	 * @RequestMapping(value="/showListFdrConnToSub/{subStation}",method=
	 * RequestMethod.GET) public @ResponseBody Object
	 * showListFdrConnToSub(@PathVariable String subStation,ModelMap model,
	 * HttpServletRequest request) { if(subStation.contains("@")==true){
	 * subStation=subStation.replaceAll("[@]","."); }
	 * if(subStation.contains("_")==true){
	 * subStation=subStation.replaceAll("[_]","/"); } String
	 * query="SELECT count(*) totFeeder,\n" +
	 * "count(case when installation='1' then 1 end) as Installed,\n" +
	 * "count(case when installation='0' then 1 end) as Pending\n" +
	 * "FROM vcloudengine.feeder_output WHERE substation like '"+subStation+"'";
	 * List<?> list= entityManager.createNativeQuery(query).getResultList();
	 * System.out.println(" showListFdrConnToSub query====>"+query); return list; }
	 */

	@RequestMapping(value = "/showListFdrConnToSubMDAS/{subStation}", method = RequestMethod.GET)
	public @ResponseBody Object showListFdrConnToSub(@PathVariable String subStation, ModelMap model,
			HttpServletRequest request) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		if (subStation.contains("@") == true) {
			subStation = subStation.replaceAll("[@]", ".");
		}
		if (subStation.contains("_") == true) {
			subStation = subStation.replaceAll("[_]", "/");
		}
		/*
		 * String
		 * query="SELECT feeder_name,district,meter_number,imei FROM vcloudengine.feeder_output \n"
		 * + "WHERE trim(substation)  like '"+subStation+"' and installation='1'";
		 */

		String query = "SELECT fdrname,district,mtrno,imei_no,\n"
				+ "(CASE	WHEN sd.times < 24 THEN 'Active' ELSE 'Inactive' END) AS Status \n"
				+ "FROM (SELECT fdrname,district,mtrno,modem_sl_no as imei_no FROM meter_data.master_main \n"
				+ "WHERE trim(substation)  like '" + subStation + "')A\n"
				+ "LEFT JOIN (SELECT lstCom.imei,lstCom.lastComm,\n" + "EXTRACT (epoch	FROM age(to_timestamp('"
				+ timeStamp + "','YYYY-MM-DD HH24:MI:SS.MS'),\n"
				+ "lstCom.lastComm) / 3600) AS times FROM (SELECT imei,MAX (last_communication) AS lastComm \n"
				+ "FROM meter_data.modem_communication GROUP BY imei) lstCom) sd on sd.imei=A.imei_no";
		List<?> list = entityManager.createNativeQuery(query).getResultList();
		// System.out.println(" showListFdrConnToSub query====>"+query);
		return list;
	}

	@RequestMapping(value = "/getFeederBySubStnMDAS/{subStation}", method = RequestMethod.GET)
	public @ResponseBody Object getFeederBySubStn(@PathVariable String subStation, ModelMap model,
			HttpServletRequest request) {
		String query = "SELECT feeder_name,meter_number,dlms,latitude,longitude FROM vcloudengine.feeder_output \n"
				+ "WHERE substation like '" + subStation + "'";
		List<?> list = entityManager.createNativeQuery(query).getResultList();
		// System.out.println("getFeederBySubStn query====>"+query);
		return list;
	}

	@RequestMapping(value = "/getViewOnMapMtrDataMDAS/{mtrNum}", method = RequestMethod.GET)
	public @ResponseBody Object getViewOnMapMtrData(@PathVariable String mtrNum, ModelMap model,
			HttpServletRequest request) {
		String query = "SELECT customer_name, mtrno,dlms,latitude,longitude FROM meter_data.master_main ";
		// System.out.println("getViewOnMapMtrData query====>"+query);
		List<?> list = entityManager.createNativeQuery(query).getResultList();
		// System.out.println("getViewOnMapMtrData query====>"+query);
		return list;
	}

	@RequestMapping(value = "/getViewOnMapMtrDataNew/{mtrNum}", method = RequestMethod.GET)
	public @ResponseBody Object getViewOnMapMtrDataNew(@PathVariable String mtrNum, ModelMap model,
			HttpServletRequest request) {
		String query = "SELECT customer_name, mtrno,dlms,latitude,longitude FROM meter_data.master_main \n"
				+ "WHERE mtrno like '" + mtrNum + "'";
		List<?> list = entityManager.createNativeQuery(query).getResultList();
		// System.out.println("getViewOnMapMtrData query====>"+query);
		return list;
	}

	@RequestMapping(value = "/getAllViewOnMapMtrDataMDAS/{mtrNum}", method = RequestMethod.GET)
	public @ResponseBody Object getAllViewOnMapMtrData(@PathVariable String mtrNum, ModelMap model,
			HttpServletRequest request) {
		String query = "SELECT customer_name, mtrno,dlms,latitude,longitude FROM meter_data.master_main where subdivision = 'AEN_F-III_SANGANER' ";
		// System.out.println("getViewOnMapMtrData query====>"+query);
		List<?> list = entityManager.createNativeQuery(query).getResultList();
		// System.out.println("getViewOnMapMtrData query====>"+query);
		return list;
	}

	@RequestMapping(value = "/getFeedersByInstalledMDAS/{subStation}", method = { RequestMethod.GET })
	public @ResponseBody String getFeedersByInstalled(@PathVariable String subStation, ModelMap model,
			HttpServletRequest request) throws JSONException// get active and inactive count
	{
		// System.out.println("==== getFeedersByInstalled ====>"+subStation);

		String query = "SELECT count(*) totFeeder,\n" + "count(case when installation='1' then 1 end) as Installed,\n"
				+ "count(case when installation='0' then 1 end) as Pending\n"
				+ "FROM vcloudengine.feeder_output WHERE substation like '" + subStation + "'";

		// System.err.println("***getFeedersByInstalled===> " + query);
		List<Object[]> li = entityManager.createNativeQuery(query).getResultList();

		int totalFeeder = 0, installed = 0, pending = 0;
		for (Object[] columns : li) {
			String category = columns[0].toString().trim();
			totalFeeder = Integer.parseInt(columns[0].toString());
			;
			pending = Integer.parseInt(columns[1].toString());
			installed = Integer.parseInt(columns[2].toString());
		}
		JSONArray array = new JSONArray();

		JSONObject obj = new JSONObject();
		obj.put("name", "Total Feeder");
		obj.put("y", totalFeeder);
		array.put(obj);

		obj = new JSONObject();
		obj.put("name", "Installed");
		obj.put("y", 5);
		array.put(obj);

		obj = new JSONObject();
		obj.put("name", "Pending");
		obj.put("y", 3);
		array.put(obj);

		JSONObject object = new JSONObject();
		object.put("graphData", array);
		return object.toString();
	}
	

	@RequestMapping(value = "/mtrNoDetailsMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String mtrNoDetails(@RequestParam String mtrno, ModelMap model, HttpServletRequest request,
			HttpSession session) {
		String officeType = (String) session.getAttribute("officeType");
		System.out.println("officeType---"+officeType);
		//String cuurentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//model.put("currentDate", cuurentDate);
		// System.out.println("mtrNoDetails=========="+mtrno+" "+cuurentDate);
		model.addAttribute("mtrno", mtrno);
		
		 mtrno=request.getParameter("mtrno");
		String zone=request.getParameter("zone");
		String circle=request.getParameter("circle");
		String town=request.getParameter("town");
		
		
		System.out.println("zone ----"+zone);
		System.out.println("circle ----"+circle);
		System.out.println("town ----"+town);
		System.out.println("mtrno----"+mtrno);
	
		model.put("mtrno", mtrno);
		model.put("zone", zone);
		model.put("circle", circle);
		model.put("town",town);
		
 
		// List<FeederMasterEntity> feederData=feederService.getFeederData(mtrno);

		if (mtrno.length() > 1) {
			List<MasterMainEntity> feederData = MasterMainService.getFeederData(mtrno);
			model.put("mtrFdrList", feederData);
			model.addAttribute("phase", feederData.get(0).getPhase());
			model.put("session", officeType);
		}
		
		
		
		return "360degreeviewMDASNew";
	}
	
	
	@RequestMapping(value = "/viewFeederMeterInfoMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewFeederMeterInfo(@RequestParam String mtrno, ModelMap model, HttpServletRequest request)
			throws JSONException {

		System.out.println("GET SINGLE METER INFO ==============================================");

		
	
		String queryMeter="";
		String tailPart = " AND i.rdate=(SELECT MAX(rdate) FROM meter_data.amiinstantaneous where meter_number='"
				+ mtrno + "')"; // FOR AVOIDING LEFT JOIN NULL DATA
		
		
//		 queryMeter = "SELECT AA.zone,AA.circle,AA.division,AA.subdivision,AA.substation,AA.addrsub,AA.fdrname,AA.fdrcode,AA.modem_sl_no,AA.simno,AA.mtrmake,AA.dlms,"
//				+ " AA.mtr_firmware,AA.year_of_man,AA.ct_ratio,AA.pt_ratio,AA.mf,AA.comm,AA.longitude,AA.latitude, "
//				+ " round(i.kwh_imp,2) as kwh,round(i.kva,2) as kva,round(i.kvah,2) as kvah,round(i.frequency,2) as frequency,round(i.pf_threephase,2) as pf_threephase,"
//				+ " round(i.i_r,2) as i_r,round(i.i_y,2) as i_y,round(i.i_b,2) as i_b,round(i.v_r,2) as v_r,round(i.v_y,2) as v_y,round(i.v_b,2) as v_b,"
//				+ " round(i.pf_r,2) as pf_r,round(i.pf_y,2) as pf_y,round(i.pf_b,2) as pf_b, "
//				+ "i.power_off_count, i.power_off_duration , i.md_reset_count, i.programming_count, i.tamper_count, "
//				+ "AA.customer_name,AA.customer_mobile,AA.customer_address,AA.accno,AA.consumerstatus,AA.tariffcode,AA.kworhp,AA.sanload,AA.contractdemand,AA.mrname,AA.kno,AA.discom, "
//				+ " (i.power_voltage ) as voltage, "
//				+ "	(i.phase_current ) as pcurrent, "
//				+ "	(i.neutral_current ) as ncurrent,AA.fdrcategory,  "
//				+ " (SELECT DISTINCT town_ipds FROM meter_data.amilocation WHERE tp_towncode=(SELECT split_part(AA.fdrname,'-',1))) as town "
//				+ "FROM meter_data.master_main  AA"
//				+ " LEFT JOIN meter_data.amiinstantaneous i ON AA.mtrno = i.meter_number where AA.mtrno='" + mtrno
//				+ "'  ";
		
		 queryMeter="SELECT distinct AA.zone,AA.circle,AA.division,AA.subdivision,AA.substation,AA.addrsub,AA.fdrname,"
		 		+ "AA.fdrcode,AA.modem_sl_no,AA.simno,AA.mtrmake,AA.dlms, AA.mtr_firmware,AA.year_of_man,AA.ct_ratio,AA.pt_ratio,"
		 		+ "AA.mf,AA.comm,AA.longitude,AA.latitude,  round(i.kwh_imp,2) as kwh,round(i.kva,2) as kva,round(i.kvah,2) as kvah,round(i.frequency,2) as frequency,"
		 		+ "round(i.pf_threephase,2) as pf_threephase, round(i.i_r,2) as i_r,round(i.i_y,2) as i_y,round(i.i_b,2) as i_b,round(i.v_r,2) as v_r,"
		 		+ "round(i.v_y,2) as v_y,(CASE WHEN (AA.fdrcategory)='FEEDER METER' THEN (round(i.v_b * sqrt(3))) ELSE (round(i.v_b,2)) END) as v_b ,"
		 		+ " round(i.pf_r,2) as pf_r,round(i.pf_y,2) as pf_y,round(i.pf_b,2) as pf_b, i.power_off_count, i.power_off_duration , i.md_reset_count, i.programming_count,"
		 		+ " i.tamper_count, AA.customer_name,AA.customer_mobile,AA.customer_address,AA.accno,AA.consumerstatus,AA.tariffcode,AA.kworhp,AA.sanload,AA.contractdemand,AA.mrname,AA.kno,AA.discom,  "
		 		+ "(i.power_voltage ) as voltage, 	(i.phase_current ) as pcurrent, (i.neutral_current) as ncurrent,AA.fdrcategory,   "
		 		+ "(SELECT DISTINCT town_ipds FROM meter_data.amilocation WHERE tp_towncode=(SELECT split_part(AA.fdrname,'-',1))) as town "
		 		+ "FROM meter_data.master_main  AA LEFT JOIN meter_data.amiinstantaneous i ON AA.mtrno = i.meter_number where AA.mtrno='"+mtrno+"' ";
		
		
		
		
		
		 System.out.println(queryMeter);

		List dataList = entityManager.createNativeQuery(queryMeter + tailPart).getResultList();

		if (dataList.size() <= 0) {
			dataList = entityManager.createNativeQuery(queryMeter).getResultList();
		}

		String zone = "", circle = "", division = "", subdivision = "", substation = "", addrsub = "", fdrname = "",
				fdrcode = "", modem_sl_no = "", simno = "", mtrmake = "", dlms = "", mtr_firmware = "",
				year_of_man = "", ct_ratio = "", pt_ratio = "", mf, comm = "", longitude = "", latitude = "";
		String kwh = "", kva = "", kvah = "", frequency = "", pf_threephase = "", i_r = "", i_y = "", i_b = "",
				v_r = "", v_y = "", v_b = "", pf_r = "", pf_y = "", pf_b = "", power_off_count = "",
				power_off_duration = "", md_reset_count = "", programming_count = "", tamper_count = "";
		String customer_name = "";
		String customer_mobile = "";
		String customer_address = "";
		String accno = "";
		String consumerstatus = "";
		String tariffcode = "";
		String kworhp = "";
		String sanload = "";
		String contractdemand = "";
		String mrname = "";
		String kno = "";
		String fdrcat = "";
		String town = "";
		if (dataList.size() > 0) {
			Object[] objMaster = (Object[]) dataList.get(0);
			zone = (objMaster[0] == null) ? " " : objMaster[0].toString();
			circle = (objMaster[1] == null) ? " " : objMaster[1].toString();
			division = (objMaster[2] == null) ? " " : objMaster[2].toString();
			subdivision = (objMaster[3] == null) ? " " : objMaster[3].toString();
			substation = (objMaster[4] == null) ? " " : objMaster[4].toString();
			addrsub = (objMaster[5] == null) ? " " : objMaster[5].toString();
			fdrname = (objMaster[6] == null) ? " " : objMaster[6].toString();
			fdrcode = (objMaster[7] == null) ? " " : objMaster[7].toString();
			modem_sl_no = (objMaster[8] == null) ? " " : objMaster[8].toString();
			simno = (objMaster[9] == null) ? " " : objMaster[9].toString();
			mtrmake = (objMaster[10] == null) ? " " : objMaster[10].toString();
			dlms = (objMaster[11] == null) ? " " : objMaster[11].toString();
			mtr_firmware = (objMaster[12] == null) ? " " : objMaster[12].toString();
			year_of_man = (objMaster[13] == null) ? "NA" : objMaster[13].toString();
			ct_ratio = (objMaster[14] == null) ? "NA" : objMaster[14].toString();
			pt_ratio = (objMaster[15] == null) ? "NA" : objMaster[15].toString();
			mf = (objMaster[16] == null) ? " " : objMaster[16].toString();
			comm = (objMaster[17] == null) ? " " : objMaster[17].toString();
			longitude = (objMaster[18] == null) ? " " : objMaster[18].toString();
			latitude = (objMaster[19] == null) ? " " : objMaster[19].toString();

			kwh = (objMaster[20] == null) ? "" : objMaster[20].toString();
			kva = (objMaster[21] == null) ? "" : objMaster[21].toString();
			kvah = (objMaster[22] == null) ? "" : objMaster[22].toString();
			frequency = (objMaster[23] == null) ? " " : objMaster[23].toString();
			pf_threephase = (objMaster[24] == null) ? " " : objMaster[24].toString();
			i_r = (objMaster[25] == null) ? " " : objMaster[25].toString();
			i_y = (objMaster[26] == null) ? " " : objMaster[26].toString();
			i_b = (objMaster[27] == null) ? " " : objMaster[27].toString();
			v_r = (objMaster[28] == null) ? " " : objMaster[28].toString();
			v_y = (objMaster[29] == null) ? " " : objMaster[29].toString();
			v_b = (objMaster[30] == null) ? " " : objMaster[30].toString();
			pf_r = (objMaster[31] == null) ? " " : objMaster[31].toString();
			pf_y = (objMaster[32] == null) ? " " : objMaster[32].toString();
			pf_b = (objMaster[33] == null) ? " " : objMaster[33].toString();
			try {
				DecimalFormat decmForm = new DecimalFormat("0");
				power_off_count = (objMaster[34] == null) ? "0" : decmForm.format(objMaster[34]);
				power_off_duration = (objMaster[35] == null) ? "0" : decmForm.format(objMaster[35]);
				md_reset_count = (objMaster[36] == null) ? "0" : decmForm.format(objMaster[36]);
				programming_count = (objMaster[37] == null) ? "0" : decmForm.format(objMaster[37]);
				tamper_count = (objMaster[38] == null) ? "0" : decmForm.format(objMaster[38]);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			customer_name = (objMaster[39] == null ? "" : String.valueOf(objMaster[39]));
			customer_mobile = (objMaster[40] == null ? "" : String.valueOf(objMaster[40]));
			customer_address = (objMaster[41] == null ? "" : String.valueOf(objMaster[41]));
			accno = (objMaster[42] == null ? "" : String.valueOf(objMaster[42]));
			consumerstatus = (objMaster[43] == null ? "" : String.valueOf(objMaster[43]));
			tariffcode = (objMaster[44] == null ? "" : String.valueOf(objMaster[44]));
			kworhp = (objMaster[45] == null ? "" : String.valueOf(objMaster[45]));
			sanload = (objMaster[46] == null ? "" : String.valueOf(objMaster[46]));
			contractdemand = (objMaster[47] == null ? "" : String.valueOf(objMaster[47]));
			mrname = (objMaster[48] == null ? "" : String.valueOf(objMaster[48]));
			kno = (objMaster[49] == null ? "" : String.valueOf(objMaster[49]));
			fdrcat = String.valueOf(objMaster[54]);
			town = String.valueOf(objMaster[55]);
			try {
				int hours = Integer.parseInt(power_off_duration) / 60; // since both are ints, you get an int
				int minutes = Integer.parseInt(power_off_duration) % 60;
				System.out.printf("%d:%02d", hours, minutes);
				power_off_duration = String.format("%d:%02d", hours, minutes);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// System.out.println(latitude +" "+longitude);
		}
		model.addAttribute("zone", zone);
		model.addAttribute("circle", circle);
		model.addAttribute("division", division);
		model.addAttribute("subdivision", subdivision);
		model.addAttribute("substation", substation);

		model.addAttribute("FEEDERNAME", fdrname);
		model.addAttribute("FEEDERCODE", fdrcode);
		model.addAttribute("METERNO", mtrno);
		model.addAttribute("imei", modem_sl_no);
		model.addAttribute("fdrcat", fdrcat);
		model.addAttribute("town", town);
		model.addAttribute("kwh", (kwh.isEmpty() || kwh == null ? "" : Double.parseDouble(kwh)));
		model.addAttribute("kva", (kva.isEmpty() || kva == null ? "" : Double.parseDouble(kva)));
		model.addAttribute("kvah", (kvah.isEmpty() || kvah == null ? "" : Double.parseDouble(kvah)));
		model.addAttribute("frequency", frequency);
		model.addAttribute("pf_threephase", pf_threephase);
		model.addAttribute("i_r", i_r);
		model.addAttribute("i_y", i_y);
		model.addAttribute("i_b", i_b);
		model.addAttribute("v_r", v_r);
		model.addAttribute("v_y", v_y);
		model.addAttribute("v_b", v_b);
		model.addAttribute("pf_r", pf_r);
		model.addAttribute("pf_y", pf_y);
		model.addAttribute("pf_b", pf_b);
		model.addAttribute("power_off_count", power_off_count);
		model.addAttribute("power_off_duration", power_off_duration);
		model.addAttribute("md_reset_count", md_reset_count);
		model.addAttribute("programming_count", programming_count);
		model.addAttribute("tamper_count", tamper_count);

		model.addAttribute("customer_name", customer_name);
		model.addAttribute("customer_mobile", customer_mobile);
		model.addAttribute("customer_address", customer_address);
		model.addAttribute("accno", accno);
		model.addAttribute("consumerstatus", consumerstatus);
		model.addAttribute("tariffcode", tariffcode);
		model.addAttribute("kworhp", kworhp);
		model.addAttribute("sanload", sanload);
		model.addAttribute("contractdemand", contractdemand);
		model.addAttribute("mrname", mrname);
		model.addAttribute("kno", kno);

		model.addAttribute("mtrno", mtrno);
		model.addAttribute("mtrmake", mtrmake);
		model.addAttribute("dlms", dlms);
		model.addAttribute("comm", (comm.isEmpty()) ? "NA" : comm);
		model.addAttribute("year_of_man", (year_of_man.isEmpty()) ? "NA" : year_of_man);
		model.addAttribute("ct_ratio", (ct_ratio.isEmpty()) ? "NA" : ct_ratio);
		model.addAttribute("pt_ratio", (pt_ratio.isEmpty()) ? "NA" : pt_ratio);

		ModelMap fdrTimeStmp = modemCommService.getFdrTimeStamps(mtrno, modem_sl_no);
		model.putAll(fdrTimeStmp);

		/*
		 * ModelMap
		 * fdrInsData=amrInstantaneousService.getFdrInstantaneousData(modem_sl_no,mtrno)
		 * ; model.putAll(fdrInsData);
		 */

		/*
		 * ModelMap
		 * fdrInsGraphData=amrInstantaneousService.getfdrInsGraphData(modem_sl_no);
		 * model.putAll(fdrInsGraphData);
		 */

		/*
		 * String
		 * query="SELECT COUNT(CASE WHEN a.upload_status = 0 then 1 ELSE NULL END) as pending ,COUNT(CASE WHEN a.upload_status = 1 then 1 ELSE NULL END) as uploaded FROM meter_data.xml_upload_status AS A WHERE	A .meter_number = '"
		 * +mtrno+"'"; System.out.println(query); Query q=
		 * entityManager.createNativeQuery(query); List<Object> list=q.getResultList();
		 * 
		 * Object[] obj=(Object[]) list.get(0); String Pending=obj[0].toString(); String
		 * upLoaded=obj[1].toString();
		 * System.out.println("Pending======"+obj[0].toString());
		 * System.out.println("upLoaded======"+obj[1].toString()); model.put("Pending",
		 * Pending); model.put("upLoaded", upLoaded); model.put("fromdate", "Last "+
		 * (Integer.parseInt(Pending)+Integer.parseInt(upLoaded))+" Days");
		 */

		return "meterInfoMDAS";
	}

	@RequestMapping(value = "/getLastDaysActiveXmlSingleMtrMDAS/{mtrNo}", method = { RequestMethod.GET })
	public @ResponseBody String getLastDaysActiveXmlSingleMtr(@PathVariable String mtrNo, HttpServletRequest request)
			throws JSONException// get active and inactive count
	{

		String active = "select (CURRENT_DATE + i) as date,COALESCE(modem1.total,0) as active,COALESCE(modem.total,0) as uploaded , COALESCE(diag.total,0) as issues"
				+ " from generate_series(-29,0 ) i LEFT JOIN (SELECT count(*) as total,file_date FROM meter_data.xml_upload_status where meter_number ='"
				+ mtrNo + "' " + " AND upload_status=1 GROUP BY file_date) modem on modem.file_date=CURRENT_DATE + i   "
				+ " LEFT JOIN (SELECT count(*) as total,date FROM meter_data.modem_communication  where meter_number = '"+mtrNo+"' GROUP BY date) modem1 on modem1.date=CURRENT_DATE + i "
				+ " LEFT JOIN (SELECT count(*) as total,date FROM meter_data.modem_diagnosis  where meter_number='"
				+ mtrNo + "' GROUP BY date) diag on diag.date=CURRENT_DATE + i";

		String newActive = "SELECT\n" + "	( CURRENT_DATE + i ) AS DATE,\n"
				+ "	COALESCE ( modem1.total, 0 ) AS active,\n" + "	COALESCE ( modem.total, 0 ) AS uploaded\n"
				+ "FROM\n" + "	generate_series ( - 29, 0 ) i\n"
				+ "	LEFT JOIN ( SELECT COUNT ( * ) AS total, file_date FROM meter_data.xml_upload_status WHERE meter_number = '"
				+ mtrNo + "' AND upload_status = 1 GROUP BY file_date ) modem ON modem.file_date = CURRENT_DATE + i\n"
				+ "	LEFT JOIN (\n" + "	SELECT COUNT\n" + "		( * ) AS total,\n" + "	DATE \n" + "	FROM\n"
				+ "		meter_data.modem_communication \n" + "	WHERE\n"
				+ "		meter_number = '"+mtrNo+"' 	GROUP BY	DATE  	) modem1 ON modem1.DATE = CURRENT_DATE + i\n"
				+ "	";

		 System.err.println("GET LAST DAYS ACTIVE COUNTS : " + newActive);
		Query queryUpload = entityManager.createNativeQuery(newActive);
		@SuppressWarnings("unchecked")
		List<Object[]> li = queryUpload.getResultList();

		JSONArray array = new JSONArray();
		for (Object[] objects : li) {
			JSONObject js = new JSONObject();
			js.put("date", objects[0].toString());
			js.put("active", objects[1]);
			js.put("upload", objects[2]);
			// js.put("issues", objects[3]);
			array.put(js);
		}

		return array.toString();
	}

	@RequestMapping(value = "/fullViewMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String fullView(ModelMap model, HttpServletRequest request, HttpSession session) {
		String radioVal = request.getParameter("radioVal");
		String metrNo = request.getParameter("meterNum");
		//String fromDate = request.getParameter("frmDate");
		//String toDate = request.getParameter("tDate");
		//System.out.println("mtrno---"+metrNo);
		//System.out.println("fromDate---"+fromDate);
		//System.out.println("toDate---"+toDate);
		// System.out.println(radioVal);

		model.addAttribute("meterNum",request.getParameter("meterNum"));
		model.addAttribute("fromDate", request.getParameter("fromDate"));
		model.addAttribute("toDate", request.getParameter("toDate"));
		model.addAttribute("radioVal", request.getParameter("radioVal"));

		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String presentDate = dateFormat.format(Calendar.getInstance().getTime());
		String yesterDay = dateFormat.format(c.getTime());
		// String yesterDay = dateFormat.format(c.getTime());

		String[] str = presentDate.split("-");

		// System.out.println("presentDate==>"+presentDate);
		// System.out.println("yesterDay==>"+str[0]+"-"+str[1]+"-"+"01");

		model.addAttribute("firstDate", presentDate);
		model.addAttribute("presentDate", str[0] + "-" + str[1] + "-" + "01");

		/*
		 * if(request.getParameter("meterNum").length()<10) {
		 */
		// List<MasterMainEntity>
		// feederData=MasterMainService.getFeederData(request.getParameter("meterNum"));
		// List<?>
		// meterData=MasterMainService.getMeterData(request.getParameter("meterNum"));
		List<MasterMainEntity> masterList = null;
		// List<MeterMaster> meterMasterList=null;
		String mtrno;
		/* if ("meterno".equals(radioVal)) { */
			masterList = MasterMainService.getFeederData(request.getParameter("meterNum"));

			/*} 
			 * else { masterList =
			 * MasterMainService.getMeterDataByKno(request.getParameter("meterNum")); }
			 */

		// System.out.println("meterData--"+masterList.size());
		model.put("mtrFdrList", masterList);
		model.addAttribute("mtrno", metrNo);
		if (masterList.size() > 0) {
			MasterMainEntity m = masterList.get(0);
			mtrno = m.getMtrno();
			model.addAttribute("accno", m.getAccno());
			model.addAttribute("phase", m.getPhase());
			try {
				// meterMasterList=meterMasterService.getMeterDataByMeterNo(mtrno);
			} catch (Exception e) {

			}

			// model.addAttribute("meterMaster",meterMasterList.get(0));
			// model.addAttribute("phase",meterMasterList.get(0).getPhase());
		}
		List<?> comm = null;
		try {
			if ("meterno".equals(radioVal)) {

				String sql = "SELECT meter_number, \"max\"(last_communication),\"count\"(*) FROM meter_data.modem_communication WHERE meter_number='"
						+ request.getParameter("meterNum") + "' GROUP BY meter_number";
				comm = entityManager.createNativeQuery(sql).getResultList();
				model.put("MtrDegreeId", "of the Meter No :");
			} else {
				String sql1 = "SELECT meter_number, \"max\"(last_communication),\"count\"(*) FROM meter_data.modem_communication WHERE meter_number IN (SELECT distinct mtrno from meter_data.master_main where mtrno='"
						+ request.getParameter("meterNum") + "')\n" + " GROUP BY meter_number";
				comm = entityManager.createNativeQuery(sql1).getResultList();
				model.put("MtrDegreeId", "of the KNo :");

			}
			List<Map<String, String>> comList = new ArrayList<>();
			for (Iterator iterator = comm.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				Map<String, String> map = new HashMap<>();
				map.put("mtrno", String.valueOf(object[0]));
				map.put("lastcomm", new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format((Date) object[1]));
				map.put("count", String.valueOf(object[2]));
				comList.add(map);
			}
			model.addAttribute("comList", comList);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// }
		/*
		 * else
		 * 
		 * { List<MasterMainEntity>
		 * feederData=MasterMainService.getFeederDataAccno(request.getParameter(
		 * "meterNum")); model.put("mtrFdrList", feederData); }
		 */
		return "360degreeviewMDASNew";

	}

	@RequestMapping(value = "/getdistinctmtrnos", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getMtrNos(HttpServletRequest request) {
		List<MasterMainEntity> metersList = new ArrayList<MasterMainEntity>();
		metersList = MasterMainService.getDistinctMeterNos();
		return metersList;
	}

	@RequestMapping(value = "/getdistinctKnos", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getKNos(HttpServletRequest request) {
		List<MasterMainEntity> metersList = new ArrayList<MasterMainEntity>();
		metersList = MasterMainService.getDistinctKNos();
		return metersList;
	}
	/*
	 * @RequestMapping(value="/feedersOnMap", method =
	 * {RequestMethod.GET,RequestMethod.POST}) public String feedersOnMap(ModelMap
	 * model, HttpServletRequest request) {
	 * System.out.println("===========feedersOnMap info==============");
	 * List<FeederMasterEntity> subdivList=feederService.getDistinctSubDivision();
	 * String
	 * subdiv=request.getParameter("sdoCode"),subStation=request.getParameter(
	 * "subStation");; model.put("subdivList", subdivList);
	 * model.put("subStationList", feederService.getSStationBySub(subdiv,model));
	 * 
	 * model.addAttribute("subdiv",subdiv);
	 * model.addAttribute("subStation",subStation); model.addAttribute("results",
	 * "notDisplay"); return "feedersOnMap"; }
	 * 
	 * @RequestMapping(value="/fdrOnMapdetails", method =
	 * {RequestMethod.GET,RequestMethod.POST}) public String
	 * fdrOnMapdetails(ModelMap model, HttpServletRequest request) {
	 * System.out.println("===========fdrOnMapdetails info=============="); String
	 * zone="All",circle="All",division="All"; List<FeederMasterEntity>
	 * subdivList=feederService.getDistinctSubDivision(); String
	 * subdiv=request.getParameter("sdoCode"),subStation=request.getParameter(
	 * "subStation");; model.put("subdivList", subdivList);
	 * model.put("subStationList", feederService.getSStationBySub(subdiv,model));
	 * 
	 * model.addAttribute("subdiv",subdiv);
	 * model.addAttribute("subStation",subStation);
	 * modemCommService.getModelMngtDetailsBySS(zone,circle,division,subdiv,
	 * subStation,model,request); return "feedersOnMap"; }
	 */

	@RequestMapping(value = "/feedersOnMapMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String feedersOnMap(ModelMap model, HttpServletRequest request) {
		// System.out.println("===========feedersOnMap info==============");
		/*
		 * List<FeederMasterEntity> subdivList=feederService.getDistinctSubDivision();
		 * String
		 * subdiv=request.getParameter("sdoCode"),subStation=request.getParameter(
		 * "subStation");; model.put("subdivList", subdivList);
		 * model.put("subStationList", feederService.getSStationBySub(subdiv,model));
		 * 
		 * model.addAttribute("subdiv",subdiv);
		 * model.addAttribute("subStation",subStation); model.addAttribute("results",
		 * "notDisplay");
		 */
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String btnSLD = request.getParameter("viewModem");
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode"),
				subStation = request.getParameter("subStation");
		;
		model.put("zoneList", zoneList);
		model.put("circleList", feederService.getCircleByZone(zone, model));
		model.put("divisionList", feederService.getDivisionByCircle(zone, circle, model));
		model.put("subdivList", feederService.getSubdivByDivisionByCircle(zone, circle, division, model));

		model.addAttribute("zone", zone);
		model.addAttribute("circle", circle);
		model.addAttribute("division", division);
		model.addAttribute("subdiv", subdiv);
		model.addAttribute("results", "notDisplay");
		return "feedersOnMapMDAS";
	}

	@RequestMapping(value = "/fdrOnMapdetailsMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String fdrOnMapdetails(ModelMap model, HttpServletRequest request) {
		// System.out.println("===========fdrOnMapdetails info==============");
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode"),
				subStation = request.getParameter("subStation");
		;
		/*
		 * model.put("subdivList", subdivList); model.put("subStationList",
		 * feederService.getSStationBySub(subdiv,model));
		 * 
		 * model.addAttribute("subdiv",subdiv);
		 * model.addAttribute("subStation",subStation);
		 */
		// System.out.println("In
		// fdrOnMapdetails==>"+zone+"==>"+circle+"==>"+division+"==>"+subdiv);
		model.put("zoneList", zoneList);
		model.put("circleList", feederService.getCircleByZone(zone, model));
		model.put("divisionList", feederService.getDivisionByCircle(zone, circle, model));
		model.put("subdivList", feederService.getSubdivByDivisionByCircle(zone, circle, division, model));

		model.addAttribute("zone", zone);
		model.addAttribute("circle", circle);
		model.addAttribute("division", division);
		model.addAttribute("subdiv", subdiv);
		model.addAttribute("results", "notDisplay");
		modemCommService.getSubstationBySubDiv(zone, circle, division, subdiv, model, request);
		return "feedersOnMapMDAS";
	}

	@RequestMapping(value = "/showSStaionBySubdivByDivMDAS/{subdiv}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object showFeederBySubdivByDiv(@PathVariable String subdiv, HttpServletRequest request,
			ModelMap model) {
		// System.out.println("==--showSStaionBySubdivByDiv--==List=="+zone+"==>>"+circle+"==>"+division+"==>"+subdiv);
		return feederService.getSStationBySub(subdiv, model);
	}

	// power scenario graph

	@RequestMapping(value = "/powSupplyuStatsMDAS", method = RequestMethod.GET)
	public String powSupplyuStats(ModelMap model, HttpServletRequest request) {
		// System.out.println("power");
		// System.out.println("come to /powSupplyuStats");
		model.put("subStations", feederMasterService.getDistinctSubStation(queryLocationAndCountWhere));

		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);

		// List<?> substa=feederMasterService.getDistinctSubStation();
		// System.out.println("substa=="+substa);

		return "graphMDAS";

	}

	/*
	 * @RequestMapping(value="/getFeederEvent",method=RequestMethod.GET)
	 * public @ResponseBody List getFeederEvent(ModelMap model, HttpServletRequest
	 * request) {
	 * 
	 * System.out.println("come to getFeederEvent controller");
	 * System.out.println(request.getParameter("subStation")+"==="+request.
	 * getParameter("date"));
	 * //feederMasterService.findFeeders(request.getParameter("subStation"),request.
	 * getParameter("date")); String substation= request.getParameter("subStation");
	 * 
	 * List feeders= feederMasterService.getFeederBySubstn(substation);
	 * System.out.println("end of getFeederEvent controller"+feeders.toString());
	 * return feeders;
	 * 
	 * }
	 */

	@RequestMapping(value = "/getTimeAndPhasesMDAS", method = RequestMethod.GET)
	public @ResponseBody String getTimeAndPhases(ModelMap model, HttpServletRequest request) throws ParseException {

		// System.out.println("come to getTimeAndPhases controller");
		// System.out.println(request.getParameter("fdName") + "===" +
		// request.getParameter("date"));
		// feederMasterService.findFeeders(request.getParameter("subStation"),request.getParameter("date"));
		String feederName = request.getParameter("fdName");

		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("subdiv");

		String date = request.getParameter("date");
		System.out.println("feederName==" + feederName + "date==" + date);

		JSONArray PhasesData = feederMasterService.getTimeAndPhases(feederName, date, zone, circle, division, subdiv);
		// System.out.println("end of getTimeAndPhases controller" +
		// PhasesData.toString());

		return PhasesData.toString();

	}

	@RequestMapping(value = "/powerStatusReportMDAS", method = RequestMethod.GET)
	public String powerStatusReport(ModelMap model, HttpServletRequest request) {
		// System.out.println("power");
		// System.out.println("come to /powSupplyuStats");
		// model.put("subStations",
		// feederMasterService.getDistinctSubStation(queryLocationAndCountWhere));

		// List<FeederMasterEntity> zoneList=feederService.getDistinctZone();
		// model.addAttribute("zoneList",zoneList);

		// List<?> substa=feederMasterService.getDistinctSubStation();
		// System.out.println("substa=="+substa);

		return "powerStatusReportMDAS";

	}

	@RequestMapping(value = "/getPowerStatusReportDataMDAS", method = RequestMethod.GET)
	public @ResponseBody Object getPowerStatusReportData(ModelMap model, HttpServletRequest request)
			throws ParseException {

		List<?> list = new ArrayList<>();
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("subdiv");
		String from = request.getParameter("from");
		String to = request.getParameter("to");

		list = feederMasterService.getPowerStatusReportData(zone, circle, division, subdiv, from, to);
		// System.out.println("end of getTimeAndPhases controller" +
		// PhasesData.toString());

		return list;

	}

	// graph end
	// CALENDAR

	@RequestMapping(value = "/calenderEventsMDAS", method = { RequestMethod.POST, RequestMethod.GET })
	public String calenderEventsByDv(HttpServletRequest request, ModelMap model) {
		return "calenderMDAS";
	}

	@RequestMapping(value = "/getStatsCalenderMDAS/{month}/{year}")
	public @ResponseBody String getStatsCalender(@PathVariable String month, @PathVariable String year,
			HttpServletRequest request, ModelMap model) throws java.text.ParseException {
		JSONArray jarray = new JSONArray();
		String str = year + "-" + month + "-1";
		String my = year + month;
		Date cdate = new SimpleDateFormat("yyyy-MM-dd").parse(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(cdate);
		HttpSession session = request.getSession();
		String officeTypeUser= ""+session.getAttribute("officeType");
		System.out.println(officeTypeUser);
	    String  circle =  ""+session.getAttribute("officeName");
	    String  zone =  ""+session.getAttribute("newRegionName");
		String subQuery="";
		System.out.println(zone);
		System.out.println(circle);
		/*
		 * System.out.println("#########" +officeTypeUser); System.out.println(circle);
		 * System.out.println(zone);
		 */
		// uploaded count
		/*
		 * String
		 * queryGetMainUploadcount="select cast(CALDATE as date),COALESCE(upld.uploaded,0) from generate_series('"
		 * +year+"-"+month+"-1',date '"+year+"-"+
		 * month+"-1' + interval '30 day', cast('1 day' as interval)) CALDATE LEFT JOIN (SELECT file_date,count(CASE WHEN mstat.upload_status = 1 THEN 1 END ) as uploaded FROM meter_data.xml_upload_status mstat WHERE to_char(file_date,'YYYYMM') ='"
		 * +year+
		 * month+"' GROUP BY file_date ORDER BY file_date)upld ON cast(CALDATE as date)=upld.file_date "
		 * ;
		 */

		/*
		 * String
		 * queryGetMainUploadcount="SELECT to_char((mstat.file_date-INTERVAL '1 day'), 'YYYY-MM-DD') as fileDate, "
		 * +
		 * "COUNT( CASE WHEN mstat.create_status = 1 THEN 1 END) AS uploaded FROM meter_data.xml_upload_status mstat "
		 * + "WHERE to_char((mstat.file_date-INTERVAL '1 day'), 'YYYYMM') = '"+my+"' " +
		 * "GROUP BY to_char((mstat.file_date-INTERVAL '1 day'), 'YYYY-MM-DD') " +
		 * "ORDER BY to_char((mstat.file_date-INTERVAL '1 day'), 'YYYY-MM-DD')";
		 */
		/*
		 * String
		 * queryGetMainUploadcount="SELECT file_date, count(*) FROM meter_data.xml_upload_status WHERE create_status=1 AND file_date<(CURRENT_DATE) AND to_char(CURRENT_DATE, 'YYYYMM')=to_char(file_date, 'YYYYMM') GROUP BY file_date ORDER BY file_date asc"
		 * ;
		 * 
		 * System.out.println("queryGetMainUploadcount----------->"+
		 * queryGetMainUploadcount); System.err.println(queryGetMainUploadcount); Query
		 * queryUpload= entityManager.createNativeQuery((queryGetMainUploadcount));
		 * List<Object> li=queryUpload.getResultList(); int i=0; for (Iterator<?>
		 * iterator = li.iterator(); iterator.hasNext();) { try { final Object[] values
		 * = (Object[]) iterator.next(); if(!values[1].toString().equals("0")){
		 * jarray.put(i); JSONObject jsonObject = new JSONObject();
		 * jsonObject.put("allDay","");
		 * jsonObject.put("title","XML Created : "+values[1].toString());
		 * jsonObject.put("id","uploded_"+i); jsonObject.put("end",
		 * values[0].toString()+" 04:00"); //hard coded end time to date for ordering
		 * the events jsonObject.put("start", values[0].toString()+" 03:00"); //hard
		 * coded start time to date for ordering the events jsonObject.put("color",
		 * "#6B8E23");
		 * 
		 * i++; jarray.put(jsonObject); } } catch (JSONException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 * 
		 * 
		 * //failed upload count String
		 * queryGetMainFailedCount="select cast(CALDATE as date),COALESCE(fupld.fupload,0) from "
		 * + "generate_series('"+year+"-"+month+"-1',date '"+year+"-"+
		 * month+"-1' + interval '30 day', " +
		 * "cast('1 day' as interval)) CALDATE LEFT JOIN (SELECT file_date," +
		 * "count(CASE WHEN mstat.upload_status = 0 THEN 1 END ) as fupload " +
		 * "FROM meter_data.xml_upload_status mstat WHERE " +
		 * "to_char((mstat.time_stamp-INTERVAL '1 day'), 'YYYYMM') = '"+my+"' " +
		 * "GROUP BY file_date ORDER BY file_date)fupld ON cast(CALDATE as date)=fupld.file_date"
		 * ;
		 * 
		 * 
		 * String
		 * failedUpload="select cast(CALDATE as date),COALESCE(fupld.fupload,0),(( SELECT count(DISTINCT mc.meter_number) "
		 * +
		 * "FROM meter_data.modem_communication mc, meter_data.master_main mm WHERE mc.meter_number = mm.mtrno AND mc. DATE ="
		 * + "CAST (CALDATE AS DATE))- COALESCE(fupld.supload,0)) as notuploaded from "
		 * + "generate_series('"+year+"-"+month+"-1',date '"+year+"-"+
		 * month+"-1' + interval '30 day', cast('1 day' as interval)) " +
		 * "CALDATE LEFT JOIN ( SELECT file_date, count(CASE WHEN mstat.upload_status = 0 THEN 1 END ) as fupload, "
		 * +
		 * "count(CASE WHEN mstat.upload_status = 1 THEN 1 END ) as supload FROM meter_data.xml_upload_status mstat "
		 * + "WHERE to_char((mstat.time_stamp-INTERVAL '1 day'), 'YYYYMM') = '"+my+"' "
		 * +
		 * "GROUP BY file_date ORDER BY file_date )fupld ON cast(CALDATE as date)=fupld.file_date"
		 * ;
		 * 
		 * String
		 * failedUpload="SELECT CAST(CALDATE AS DATE), COALESCE (fupld.fupload, 0), ( ( SELECT COUNT (DISTINCT mc.meter_number) FROM meter_data.modem_communication mc, "
		 * +
		 * "meter_data.master_main mm WHERE mc.meter_number = mm.mtrno AND mc.imei=mm.modem_sl_no AND mc. DATE = CAST (CALDATE AS DATE)) - "
		 * + "COALESCE (fupld.supload, 0) ) AS notuploaded FROM " +
		 * "generate_series('"+year+"-"+month+"-1',date '"+year+"-"+
		 * month+"-1' + interval '30 day', cast('1 day' as interval)) " +
		 * "CALDATE LEFT JOIN ( SELECT file_date, COUNT ( CASE WHEN mstat.create_status = 0 THEN 1 END ) AS fupload, "
		 * +
		 * "COUNT ( CASE WHEN mstat.create_status = 1 THEN 1 END ) AS supload FROM meter_data.xml_upload_status mstat "
		 * +
		 * "WHERE to_char( ( mstat.time_stamp - INTERVAL '1 day' ), 'YYYYMM' ) = '201809' GROUP BY file_date ORDER BY file_date ) "
		 * + "fupld ON CAST (CALDATE AS DATE) = fupld.file_date";
		 * 
		 * System.err.println(failedUpload); Query queryfailUpload=
		 * entityManager.createNativeQuery((failedUpload)); List<Object>
		 * failli=queryfailUpload.getResultList(); SimpleDateFormat formatter1 = new
		 * SimpleDateFormat("yyyy-MM-dd"); Date date1 = new Date(); int j=i; for
		 * (Iterator<?> iterator = failli.iterator(); iterator.hasNext();) { try { final
		 * Object[] values = (Object[]) iterator.next(); Date ccdate=new
		 * SimpleDateFormat("yyyy-MM-dd").parse(values[0].toString()); Calendar cal1 =
		 * Calendar.getInstance(); cal1.setTime(ccdate);
		 * if(!values[1].toString().equals("0")&&(formatter1.parse(values[0].toString())
		 * .before(date1))){ jarray.put(j); JSONObject jsonObject = new JSONObject();
		 * jsonObject.put("allDay","");
		 * jsonObject.put("title","XML Creation Failed: "+values[2].toString());
		 * jsonObject.put("id", "ufailed_"+j); jsonObject.put("end",
		 * values[0].toString()+" 05:00"); jsonObject.put("start",
		 * values[0].toString()+" 04:00"); jsonObject.put("color", "#DC143C");
		 * 
		 * j++; jarray.put(jsonObject); }
		 * 
		 * } catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (ParseException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } }
		 */

		// String queryGetMainactiveCount="SELECT CALDATE, ACTIVECOUNT FROM (select
		// cast(CALDATE as date),(SELECT COUNT(DISTINCT modem_sl_no) FROM
		// meter_data.master_main ) AS MASTERCOUNT, COALESCE(test.ct,0) AS ACTIVECOUNT
		// from generate_series('"+year+"-"+month+"-1', date '"+year+"-"+month+"-1' +
		// interval '30 day', cast('1 day' as interval))CALDATE LEFT JOIN (SELECT DATE,
		// COUNT (DISTINCT imei) as ct FROM meter_data.modem_communication WHERE
		// to_char(date,'YYYYMM') ='"+year+month+"' GROUP BY DATE) test ON cast(CALDATE
		// as date)=test.date )A ";

		/*
		 * String
		 * q="SELECT CALDATE, ACTIVECOUNT  FROM (select cast(CALDATE as date),(SELECT COUNT(DISTINCT  modem_sl_no) FROM meter_data.master_main ) AS MASTERCOUNT, COALESCE(test.ct,0) AS ACTIVECOUNT from generate_series('"
		 * +year+"-"+month+"-1',  date '"+year+"-"+
		 * month+"-1' + interval '30 day', cast('1 day' as interval))CALDATE LEFT JOIN (SELECT     DATE,     COUNT (DISTINCT imei) as ct FROM     meter_data.modem_communication WHERE to_char(date,'YYYYMM') ='"
		 * +year+
		 * month+"' and imei in (SELECT modem_sl_no FROM meter_data.master_main) GROUP BY    DATE) test ON cast(CALDATE as date)=test.date )A "
		 * ;
		 */
		/*
		 * String
		 * q="SELECT CALDATE, ACTIVECOUNT  FROM (select cast(CALDATE as date),(SELECT COUNT(DISTINCT  mtrno) FROM meter_data.master_main ) AS MASTERCOUNT, COALESCE(test.ct,0) AS ACTIVECOUNT from generate_series('"
		 * +year+"-"+month+"-1',  date '"+year+"-"+
		 * month+"-1' + interval '30 day', cast('1 day' as interval))CALDATE LEFT JOIN (SELECT     DATE,     COUNT (DISTINCT meter_number) as ct FROM     meter_data.modem_communication WHERE to_char(date,'YYYYMM') ='"
		 * +year+
		 * month+"' and meter_number in (SELECT mtrno FROM meter_data.master_main) GROUP BY    DATE) test ON cast(CALDATE as date)=test.date )A "
		 * ;
		 */
		
		String totalCount = "SELECT CALDATE,MASTERCOUNT, ACTIVECOUNT FROM( SELECT CAST (CALDATE AS DATE), ( SELECT COUNT (DISTINCT mtrno) FROM meter_data.master_main) AS MASTERCOUNT, COALESCE (test.ct, 0) AS ACTIVECOUNT FROM "
				+ "generate_series('" + year + "-" + month + "-1',date '" + year + "-" + month
				+ "-1' + interval '30 day', cast('1 day' as interval)) CALDATE "
				+ "LEFT JOIN ( SELECT b.date, \"count\"(*) as ct FROM meter_data.master_main a, ( SELECT DISTINCT meter_number,date "
				+ "from meter_data.modem_communication WHERE to_char(DATE, 'YYYYMM') = '" + year + month
				+ "' GROUP BY meter_number,date )b "
				+ "WHERE a.mtrno=b.meter_number  GROUP BY b.date ) test ON CAST (CALDATE AS DATE) = test. DATE ) A";
		if (officeTypeUser.equalsIgnoreCase("circle"))
		{ 
			
			totalCount="SELECT CALDATE,MASTERCOUNT, ACTIVECOUNT FROM( SELECT CAST (CALDATE AS DATE), ( SELECT COUNT (DISTINCT mtrno) FROM meter_data.master_main where circle like  '"+circle+"') AS MASTERCOUNT, COALESCE (test.ct, 0) AS ACTIVECOUNT  FROM generate_series('"+year+"-"+month+"-1',date '"+year+"-"+month+"-1' + interval '30 day', cast('1 day' as interval)) CALDATE LEFT JOIN ( SELECT b.date, \"count\"(*) as ct FROM meter_data.master_main a ,( SELECT DISTINCT meter_number,date from meter_data.modem_communication WHERE to_char(DATE, 'YYYYMM') = '"+year+month+"' GROUP BY meter_number,date)b WHERE a.mtrno=b.meter_number AND CIRCLE LIKE '"+circle+"' GROUP BY b.date ) test ON CAST (CALDATE AS DATE) = test. DATE  ) A ";
		}
		
		if (officeTypeUser.equalsIgnoreCase("region"))
		{
			
			totalCount="SELECT CALDATE,MASTERCOUNT, ACTIVECOUNT FROM( SELECT CAST (CALDATE AS DATE), ( SELECT COUNT (DISTINCT mtrno) FROM meter_data.master_main where zone like  '"+zone+"') AS MASTERCOUNT, COALESCE (test.ct, 0) AS ACTIVECOUNT FROM generate_series('"+year+"-"+month+"-1',date '"+year+"-"+month+"-1' + interval '30 day', cast('1 day' as interval)) CALDATE LEFT JOIN ( SELECT b.date, \"count\"(*) as ct FROM meter_data.master_main a, ( SELECT DISTINCT meter_number,date from meter_data.modem_communication WHERE to_char(DATE, 'YYYYMM') = '"+year+month+"' GROUP BY meter_number,date )b WHERE a.mtrno=b.meter_number and zone like '"+zone+"'  GROUP BY b.date ) test ON CAST (CALDATE AS DATE) = test. DATE ) A";
		System.out.println(totalCount);
		}
		
		
		//System.out.println(totalCount);
		Query querytotal = entityManager.createNativeQuery((totalCount));
		List<Object> acttotal = querytotal.getResultList();
		int t = 1;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
		for (Iterator<?> iterator = acttotal.iterator(); iterator.hasNext();) {
			try {
				final Object[] values = (Object[]) iterator.next();
				Date ccdate = new SimpleDateFormat("yyyy-MM-dd").parse(values[0].toString());
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(ccdate);
				if (!values[1].toString().equals("0") && (formatter.parse(values[0].toString()).before(date))
						&& cal.get(Calendar.MONTH) == cal1.get(Calendar.MONTH)) {
					jarray.put(t);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("allDay", "");
					jsonObject.put("title", "Total Meters : " + values[1].toString());
					jsonObject.put("id", "total_" + t);
					jsonObject.put("end", values[0].toString() + " 02:00");
					jsonObject.put("start", values[0].toString() + " 01:00");
					jsonObject.put("color", "#328B89");

					t++;
					jarray.put(jsonObject);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
		String q = "SELECT CALDATE, ACTIVECOUNT FROM( SELECT CAST (CALDATE AS DATE), ( SELECT COUNT (DISTINCT mtrno) FROM meter_data.master_main) AS MASTERCOUNT, COALESCE (test.ct, 0) AS ACTIVECOUNT FROM generate_series('"+year+"-"+month+"-1',date '"+year+"-"+month+"-1' + interval '30 day', cast('1 day' as interval)) CALDATE LEFT JOIN ( SELECT b.date, \"count\"(*) as ct FROM meter_data.master_main a, ( SELECT DISTINCT meter_number,date from meter_data.modem_communication WHERE to_char(DATE, 'YYYYMM') = '"+year+month+"' GROUP BY meter_number,date )b WHERE a.mtrno=b.meter_number GROUP BY b.date ) test ON CAST (CALDATE AS DATE) = test. DATE ) A";
		if (officeTypeUser.equalsIgnoreCase("circle"))
		{
			q="SELECT CALDATE, ACTIVECOUNT FROM( SELECT CAST (CALDATE AS DATE), ( SELECT COUNT (DISTINCT mtrno) FROM meter_data.master_main  where circle like  '"+circle+"') AS MASTERCOUNT, COALESCE (test.ct, 0) AS ACTIVECOUNT FROM generate_series('"+year+"-"+month+"-1',date '"+year+"-"+month+"-1' + interval '30 day', cast('1 day' as interval)) CALDATE LEFT JOIN ( SELECT b.date, \"count\"(*) as ct FROM meter_data.master_main a, ( SELECT DISTINCT meter_number,date from meter_data.modem_communication WHERE to_char(DATE, 'YYYYMM') = '"+year+month+"' GROUP BY meter_number,date )b WHERE a.mtrno=b.meter_number   and circle like '"+circle+"' GROUP BY b.date ) test ON CAST (CALDATE AS DATE) = test. DATE ) A";
		}
		if (officeTypeUser.equalsIgnoreCase("region"))
		{
			q="SELECT CALDATE, ACTIVECOUNT FROM( SELECT CAST (CALDATE AS DATE), ( SELECT COUNT (DISTINCT mtrno) FROM meter_data.master_main  where zone like  '"+zone+"') AS MASTERCOUNT, COALESCE (test.ct, 0) AS ACTIVECOUNT FROM generate_series('"+year+"-"+month+"-1',date '"+year+"-"+month+"-1' + interval '30 day', cast('1 day' as interval)) CALDATE LEFT JOIN ( SELECT b.date, \"count\"(*) as ct FROM meter_data.master_main a, ( SELECT DISTINCT meter_number,date from meter_data.modem_communication WHERE to_char(DATE, 'YYYYMM') = '"+year+month+"' GROUP BY meter_number,date )b WHERE a.mtrno=b.meter_number   and zone like '"+zone+"' GROUP BY b.date ) test ON CAST (CALDATE AS DATE) = test. DATE ) A";
		}
			
		System.err.println("2nd one"+q);
		// System.out.println("queryGetMainactiveCount--->"+q);
		Query queryactive = entityManager.createNativeQuery((q));
		List<Object> actli = queryactive.getResultList();
		// System.out.println("Active Object Count------>"+actli.size());
		int k = 1;
		for (Iterator<?> iterator = actli.iterator(); iterator.hasNext();) {
			try {
				final Object[] values = (Object[]) iterator.next();
				if (!values[1].toString().equals("0")) {
					jarray.put(k);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("allDay", "");
					jsonObject.put("title", "Meters Communicating : " + values[1].toString());
					jsonObject.put("id", "active_" + k);
					jsonObject.put("end", values[0].toString() + " 02:00");
					jsonObject.put("start", values[0].toString() + " 01:00");
					jsonObject.put("color", "#228B22");

					k++;
					jarray.put(jsonObject);

					// System.out.println("Meters Communicating :------------->
					// "+values[1].toString());
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		String queryGetMaininactiveCount = "SELECT CALDATE,(CASE WHEN MASTERCOUNT-ACTIVECOUNT<0 THEN 0 ELSE MASTERCOUNT-ACTIVECOUNT END )AS INACTIVE  FROM (select cast(CALDATE as date), (SELECT COUNT(DISTINCT  modem_sl_no) FROM meter_data.master_main ) AS MASTERCOUNT, COALESCE(test.ct,0) AS ACTIVECOUNT from generate_series('"
				+ year + "-" + month + "-1',   date '" + year + "-" + month
				+ "-1' + interval '30 day', cast('1 day' as interval))CALDATE LEFT JOIN (SELECT     DATE,     COUNT (DISTINCT imei) as ct FROM     meter_data.modem_communication WHERE to_char(date,'YYYYMM') ='"
				+ year + month + "' GROUP BY    DATE) test ON cast(CALDATE as date)=test.date )A";

		String qNew = "SELECT CALDATE,(CASE WHEN MASTERCOUNT-ACTIVECOUNT<0 THEN 0 ELSE MASTERCOUNT-ACTIVECOUNT END)AS INACTIVE, (CASE WHEN ACTIVECOUNT=0 then 0 ELSE  noactdate-ACTIVECOUNT END) as dayinactive  FROM( select cast(CALDATE as date), (SELECT COUNT(DISTINCT mtrno) FROM meter_data.master_main ) AS MASTERCOUNT, (SELECT COUNT(DISTINCT mtrno) FROM meter_data.master_main WHERE mtrno IN ( SELECT DISTINCT meter_number FROM meter_data.modem_communication WHERE last_communication< CAST (CALDATE AS DATE) ) ) AS noactdate, COALESCE(test.ct,0) AS ACTIVECOUNT from generate_series('"+year+"-"+month+"-1',date '"+year+"-"+month+"-1' + interval '30 day', cast('1 day' as interval))CALDATE LEFT JOIN (SELECT DATE, COUNT (DISTINCT meter_number) as ct FROM meter_data.modem_communication WHERE to_char(date,'YYYYMM') ='"+year+month+"'  AND meter_number in ( SELECT mtrno FROM meter_data.master_main )GROUP BY DATE  ) test ON cast(CALDATE as date)=test.date )A ";
		if (officeTypeUser.equalsIgnoreCase("region"))
		{
			qNew="SELECT CALDATE,(CASE WHEN MASTERCOUNT-ACTIVECOUNT<0 THEN 0 ELSE MASTERCOUNT-ACTIVECOUNT END)AS INACTIVE, (CASE WHEN ACTIVECOUNT=0 then 0 ELSE  noactdate-ACTIVECOUNT END) as dayinactive  FROM( select cast(CALDATE as date), (SELECT COUNT(DISTINCT mtrno) FROM meter_data.master_main where zone like  '"+zone+"') AS MASTERCOUNT, (SELECT COUNT(DISTINCT mtrno) FROM meter_data.master_main WHERE mtrno IN ( SELECT DISTINCT meter_number FROM meter_data.modem_communication WHERE last_communication< CAST (CALDATE AS DATE) ) ) AS noactdate, COALESCE(test.ct,0) AS ACTIVECOUNT from generate_series('"+year+"-"+month+"-1',date '"+year+"-"+month+"-1' + interval '30 day', cast('1 day' as interval))CALDATE LEFT JOIN (SELECT DATE, COUNT (DISTINCT meter_number) as ct FROM meter_data.modem_communication WHERE to_char(date,'YYYYMM') ='"+year+month+"' AND meter_number in ( SELECT mtrno FROM meter_data.master_main )GROUP BY DATE  ) test ON cast(CALDATE as date)=test.date )A ";	
		}
		if (officeTypeUser.equalsIgnoreCase("circle"))
		{
			qNew="SELECT CALDATE,(CASE WHEN MASTERCOUNT-ACTIVECOUNT<0 THEN 0 ELSE MASTERCOUNT-ACTIVECOUNT END)AS INACTIVE, (CASE WHEN ACTIVECOUNT=0 then 0 ELSE  noactdate-ACTIVECOUNT END) as dayinactive  FROM( select cast(CALDATE as date), (SELECT COUNT(DISTINCT mtrno) FROM meter_data.master_main where circle like  '"+circle+"') AS MASTERCOUNT, (SELECT COUNT(DISTINCT mtrno) FROM meter_data.master_main WHERE mtrno IN ( SELECT DISTINCT meter_number FROM meter_data.modem_communication WHERE last_communication< CAST (CALDATE AS DATE) ) ) AS noactdate, COALESCE(test.ct,0) AS ACTIVECOUNT from generate_series('"+year+"-"+month+"-1',date '"+year+"-"+month+"-1' + interval '30 day', cast('1 day' as interval))CALDATE LEFT JOIN (SELECT DATE, COUNT (DISTINCT meter_number) as ct FROM meter_data.modem_communication WHERE to_char(date,'YYYYMM') ='"+year+month+"' AND meter_number in ( SELECT mtrno FROM meter_data.master_main )GROUP BY DATE  ) test ON cast(CALDATE as date)=test.date )A ";	
		}
		
		
		System.out.println("queryGetMaininactiveCount------>"+queryGetMaininactiveCount);
		 System.err.println(qNew);
		Query queryinactive = entityManager.createNativeQuery((qNew));
		List<Object> inactli = queryinactive.getResultList();
		int l = k;
//	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//	    Date date = new Date();

		for (Iterator<?> iterator = inactli.iterator(); iterator.hasNext();) {
			try {
				final Object[] values = (Object[]) iterator.next();
				try {
					Date ccdate = new SimpleDateFormat("yyyy-MM-dd").parse(values[0].toString());
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(ccdate);
					// System.out.println("Date------>"+values[0].toString());
					if ((!values[1].toString().equals("0")) && (formatter.parse(values[0].toString()).before(date))
							&& cal.get(Calendar.MONTH) == cal1.get(Calendar.MONTH)) {
						jarray.put(l);
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("allDay", "");
						jsonObject.put("title", "Meters Not Communicating: " + values[1].toString());
						jsonObject.put("id", "inactive_" + l);
						jsonObject.put("end", values[0].toString() + " 03:00");
						jsonObject.put("start", values[0].toString() + " 02:00");
						jsonObject.put("color", "#FF0000");

						k++;
						jarray.put(jsonObject);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

		return jarray.toString();
	}

	// calendarEventClick
	@RequestMapping(value = "/calendarEventClick", method = { RequestMethod.POST, RequestMethod.GET })
	public String calendarEventClick(@RequestParam String eventType, @RequestParam String selectedDate,
			HttpServletRequest request, ModelMap model) {
		List<?> li = null;
		String query = "";

		switch (eventType) {
		case "uploded":
			query = "SELECT A.modem_sl_no,A.mtrno,A.fdrcode,A.substation,B.time_stamp FROM (SELECT modem_sl_no,mtrno,fdrcode,substation  FROM meter_data.master_main where mtrno in (SELECT     DISTINCT meter_number FROM     meter_data.xml_upload_status WHERE to_char(file_date,'DD-MM-YYYY') ='"
					+ selectedDate
					+ "' and upload_status=1 ))A LEFT join (SELECT DISTINCT meter_number,time_stamp FROM     meter_data.xml_upload_status WHERE to_char(file_date,'DD-MM-YYYY') ='"
					+ selectedDate + "' and upload_status=1 )B ON A.mtrno =B.meter_number";
			Query queryUpload = entityManager.createNativeQuery((query));
			model.put("selectedDate", selectedDate);
			model.put("type", "Meters Successfully Uploaded Data on " + selectedDate);
			model.put("mDetailList", queryUpload.getResultList());
			break;
		case "ufailed":
			String[] s = selectedDate.split("-");
			query = "SELECT A.modem_sl_no,A.mtrno,A.fdrcode,A.substation,B.fail_reason  FROM (SELECT modem_sl_no,mtrno,fdrcode,substation  FROM meter_data.master_main where mtrno in (SELECT     DISTINCT meter_number FROM     meter_data.xml_upload_status WHERE file_date = to_date('"
					+ s[2] + "-" + s[1] + "-" + s[0]
					+ "', 'YYYY-MM-DD') and upload_status=0 ))A LEFT join (SELECT         DISTINCT meter_number, fail_reason FROM     meter_data.xml_upload_status WHERE file_date = to_date('"
					+ s[2] + "-" + s[1] + "-" + s[0]
					+ "', 'YYYY-MM-DD') and upload_status=0 )B ON A.mtrno =B.meter_number";
			Query queryFUpload = entityManager.createNativeQuery((query));
			model.put("selectedDate", selectedDate);
			model.put("type", "Meters Failed to  Upload Data on " + selectedDate);
			model.put("mDetailList", queryFUpload.getResultList());
			break;
		case "active":
			model.put("selectedDate", selectedDate);
			model.put("type", "Communicating Meters on " + selectedDate);
			model.put("mDetailList", modemCommService.getTotalMeterDetailsCalender(selectedDate));
			break;
		case "inactive":
			model.put("selectedDate", selectedDate);
			model.put("type", "Meters Not Communicating on " + selectedDate);
			model.put("mDetailList", modemCommService.getTotalInactiveMeterDetailsCalender(selectedDate));
			break;
		case "total":
			model.put("selectedDate", selectedDate);
			model.put("type", "Total Meters " + selectedDate);
			model.put("mDetailList", modemCommService.getTotalMetersDetailsCalender(selectedDate));
			break;
		}

		return "mtrDetailsCaledarMDAS";
	}

	@RequestMapping(value = "/getDiagnosisAlertsMDAS", method = { RequestMethod.GET })
	public @ResponseBody Object getDiagnosisAlerts(ModelMap model, HttpServletRequest request) throws JSONException// get
																													// active
																													// and
																													// inactive
																													// count
	{
		// System.out.println("################################## getDiagnosisAlerts ");

		/*
		 * String
		 * active="SELECT A.meter_number,A.imei,A.date, A.tracked_time,A.diag_type,A.status, B.zone,B.circle,"
		 * +" B.division,B.district,B.subdivision,B.substation,B.fdrname,B.fdrcode,B.mtrmake, B.longitude, B.latitude "
		 * +" FROM meter_data.modem_diagnosis A LEFT JOIN meter_data.master_main B ON A .imei = B.modem_sl_no where A.date >=  CURRENT_DATE-1 "
		 * +(queryLocationAndCountWhere.replace("WHERE",
		 * " AND "))+" ORDER BY A.tracked_time DESC";
		 */
		String active = "SELECT d.*, m.accno,m.customer_name,m.subdivision,m.division,m.circle from meter_data.master_main m,\n"
				+ "(\n" + "SELECT p.meter_number,p.event_code,em.event,p.event_time FROM meter_data.event_master em,\n"
				+ "(\n" + "SELECT e.meter_number,e.event_code,e.event_time  FROM meter_data.events e,\n" + "(\n"
				+ "SELECT meter_number, \"max\"(id) as id FROM meter_data.events WHERE date(event_time)=CURRENT_DATE GROUP BY meter_number\n"
				+ ")a WHERE e.id=a.id AND e.meter_number =a.meter_number\n"
				+ ") p WHERE em.event_code=CAST(p.event_code as INTEGER)\n" + ")d WHERE m.mtrno=d.meter_number";

		// System.err.println("GET LAST DAYS ACTIVE COUNTS : "+active);
		List<Object[]> li = entityManager.createNativeQuery(active).getResultList();
		/*
		 * JSONArray array= new JSONArray(); SimpleDateFormat format= new
		 * SimpleDateFormat("dd-MM-YYYY HH:mm:ss"); for(Object[] columns: li){
		 * JSONObject obj= new JSONObject(); obj.put("meter_number", (columns[0]!=null)?
		 * columns[0].toString():""); obj.put("imei", (columns[1]!=null)?
		 * columns[1].toString():""); obj.put("date", (columns[2]!=null)?
		 * columns[2].toString():""); obj.put("tracked_time", (columns[3]!=null)?
		 * format.format(columns[3]):""); obj.put("diag_type", (columns[4]!=null)?
		 * columns[4].toString():""); obj.put("status", (columns[5]!=null)?
		 * columns[5].toString():""); obj.put("zone", (columns[6]!=null)?
		 * columns[6].toString():""); obj.put("circle", (columns[7]!=null)?
		 * columns[7].toString():""); obj.put("division", (columns[8]!=null)?
		 * columns[8].toString():""); obj.put("district", (columns[9]!=null)?
		 * columns[9].toString():""); obj.put("subdivision", (columns[10]!=null)?
		 * columns[10].toString():""); obj.put("substation", (columns[11]!=null)?
		 * columns[11].toString():""); obj.put("fdrname", (columns[12]!=null)?
		 * columns[12].toString():""); obj.put("fdrcode", (columns[13]!=null)?
		 * columns[13].toString():""); obj.put("mtrmake", (columns[14]!=null)?
		 * columns[14].toString():""); obj.put("longitude", (columns[15]!=null)?
		 * columns[15].toString():""); obj.put("latitude", (columns[16]!=null)?
		 * columns[16].toString():""); array.put(obj); }
		 */

		// return array.toString();
		return li;
	}

	@RequestMapping(value = "/getModemIssuesMDAS/{mtrNo}/{date}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getModemIssues(@PathVariable String mtrNo, @PathVariable String date, ModelMap model,
			HttpServletRequest request) throws java.text.ParseException// get active and inactive count
	{
		// System.out.println(mtrNo+" MTR
		// NO##################################333333333333333 DATE "+date);

		List<Map<String, Object>> resultDiag = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapDiag = null;
		String meterDiag = "SELECT date,tracked_time,diag_type,status FROM meter_data.modem_diagnosis where meter_number='"
				+ mtrNo + "' AND date = '" + date + "' ";
		// System.err.println("GET METER ISSUES : "+meterDiag);
		Query queryDiag = entityManager.createNativeQuery(meterDiag);
		List<Object> listDiag = queryDiag.getResultList();

		for (Iterator<?> iterator = listDiag.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			mapDiag = new HashMap<>();
			mapDiag.put("date",
					(values[0] == null) ? "--"
							: new SimpleDateFormat("MMM dd")
									.format(new SimpleDateFormat("yyyy-MM-dd").parse(values[0].toString())));
			mapDiag.put("tracked_time", (values[1] == null) ? "--" : values[1]);

			String diagType = (values[2] == null) ? "--" : values[2].toString().trim();
			String status = (values[3] == null) ? "--" : values[3].toString().trim();

			if (diagType.equalsIgnoreCase("POWERSTS")) {
				switch (status) {
				case "FAIL":
					status = "Power Outage";
					break;
				case "SUCC":
					status = "Power Restore";
					break;
				}
			} else if (diagType.equalsIgnoreCase("METERSTS")) {
				switch (status) {
				case "NLEN":
					status = " Modem not able to communicate with meter. Problem with meter cable connection or meter.";
					break;
				case "CRCF":
					status = "Error in dlms CRC(cyclic redundancy check) format.";
					break;
				case "AARJ":
					status = "Meter is rejecting. Need to give correct password before communication with meter.";
					break;
				case "FRER":
					status = "Framing error in dlms format.";
					break;
				case "FAIL":
					status = "Default error.";
					break;
				}
			}

			mapDiag.put("diag_type", diagType);
			mapDiag.put("status", status);

			resultDiag.add(mapDiag);
		}
		return resultDiag;
	}

	@RequestMapping(value = "/installationDetailsMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String installationDetails(@RequestParam String date, ModelMap model, HttpServletRequest request)
			throws java.text.ParseException// get active and inactive count
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> activityMap = null;
		String dateHead;
		if (date != null && date.equals("nodate")) {
			date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
			dateHead = "Today";
		} else {
			date = request.getParameter("date");
			dateHead = new SimpleDateFormat("MMM dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		}

		String meterIssues = "SELECT meter_number,imei,last_communication,mast.customer_name,mast.accno FROM meter_data.modem_communication dig"
				+ " LEFT JOIN   meter_data.master_main mast on mast.mtrno = dig.meter_number " + " where dig.date='"
				+ date + "' order by  dig.last_communication desc";
		// System.err.println("GET INSTALLATION : "+meterIssues);
		Query queryUpload = entityManager.createNativeQuery(meterIssues);
		List<Object> li = queryUpload.getResultList();

		int count = 1;
		for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			activityMap = new HashMap<>();
			activityMap.put("meter_number", (values[0] == null) ? "--" : values[0]);
			activityMap.put("imei", (values[1] == null) ? "--" : values[1]);
			activityMap.put("last_communication", (values[2] == null) ? "--" : values[2]);
			activityMap.put("fdrname", (values[3] == null) ? "--" : values[3]);
			activityMap.put("substation", (values[4] == null) ? "--" : values[4]);
			activityMap.put("rowNo", count);
			result.add(activityMap);
			count++;
		}

		model.put("results", "notDisplay");
		model.put("mDetailList", result);
		/*
		 * List<Map<String, Object>> resultDiag = new ArrayList<Map<String, Object>>();
		 * Map<String, Object> mapDiag = null; String
		 * meterDiag="SELECT dig.meter_number,dig.tracked_time,dig.diag_type,dig.status,dig.imei ,mast.customer_name,mast.accno FROM "
		 * +" meter_data.modem_diagnosis dig LEFT JOIN   meter_data.master_main mast on mast.mtrno = dig.meter_number "
		 * +" where dig.date='"+date+"' order by dig.imei, dig.tracked_time desc";
		 * System.err.println("GET METER ISSUES : "+meterDiag); Query queryDiag=
		 * entityManager.createNativeQuery(meterDiag); List<Object>
		 * listDiag=queryDiag.getResultList(); int count2=1; for (Iterator<?> iterator =
		 * listDiag.iterator(); iterator.hasNext();){ final Object[] values = (Object[])
		 * iterator.next(); mapDiag=new HashMap<>(); mapDiag.put("meter_number",
		 * (values[0]==null)?"--":values[0]); mapDiag.put("tracked_time",
		 * (values[1]==null)?"--":values[1]); mapDiag.put("imei",
		 * (values[4]==null)?"--":values[4]); mapDiag.put("fdrname",
		 * (values[5]==null)?"NA":values[5]); mapDiag.put("substation",
		 * (values[6]==null)?"NA":values[6]); String
		 * diagType=(values[2]==null)?"--":values[2].toString().trim(); String status =
		 * (values[3]==null)?"--":values[3].toString().trim();
		 * 
		 * if(diagType.equalsIgnoreCase("POWERSTS")){ switch (status) { case "FAIL":
		 * status="Power Outage"; break; case "SUCC": status="Power Restore"; break; }
		 * }else if(diagType.equalsIgnoreCase("METERSTS")) { switch (status) { case
		 * "NLEN":
		 * status=" Modem not able to communicate with meter. Problem with meter cable connection or meter."
		 * ; break; case "CRCF":
		 * status="Error in dlms CRC(cyclic redundancy check) format."; break; case
		 * "AARJ":
		 * status="Meter is rejecting. Need to give correct password before communication with meter."
		 * ; break; case "FRER": status="Framing error in dlms format."; break; case
		 * "FAIL": status="Default error."; break; } }
		 * 
		 * mapDiag.put("diag_type",diagType); mapDiag.put("status", status);
		 * mapDiag.put("count", count2); resultDiag.add(mapDiag); count2++; }
		 * 
		 * 
		 * 
		 * 
		 * model.put("date", date); model.put("dateHead", dateHead);
		 * model.put("diagInfo", resultDiag);
		 */

		/*
		 * String
		 * instprogqry="SELECT COALESCE(count(CASE WHEN a.mtrno is not null then 1 END),0) as total,\n"
		 * +
		 * "COALESCE (count(CASE WHEN a.mtrno is not null AND a.meter_number is NOT null then 1 END),0) as installedmaster,\n"
		 * +
		 * "COALESCE (count(CASE WHEN a.mtrno is not null AND a.meter_number is null then 1 END),0) as pendingmaster,\n"
		 * +
		 * "COALESCE (count(CASE WHEN a.meter_number is NOT null then 1 END),0) as totalinstalled,\n"
		 * + "(SELECT count(*) FROM (\n" +
		 * "SELECT mc.meter_number, mc.imei, mc.last_communication FROM meter_data.modem_communication mc, ( SELECT imei, max(last_communication) as lastcom\n"
		 * +
		 * "FROM meter_data.modem_communication GROUP BY imei )A WHERE mc.imei=A.imei AND A.lastcom=mc.last_communication )X \n"
		 * + "LEFT JOIN meter_data.master_main m ON m.mtrno=X.meter_number\n" +
		 * "AND m.modem_sl_no=X.imei WHERE m.modem_sl_no is NULL) as notinmaster,\n" +
		 * "COALESCE (count(CASE WHEN to_char(a.last_communication, 'YYYY-MM-DD')=to_char(CURRENT_DATE, 'YYYY-MM-DD') then 1 END),0) as activetoday\n"
		 * +
		 * "FROM ( SELECT m.mtrno,m.modem_sl_no,X.meter_number,X.imei,X.last_communication FROM meter_data.master_main m FULL OUTER JOIN\n"
		 * +
		 * "( SELECT mc.meter_number, mc.imei, mc.last_communication FROM meter_data.modem_communication mc, ( SELECT imei, max(last_communication) as lastcom\n"
		 * +
		 * "FROM meter_data.modem_communication GROUP BY imei )A WHERE mc.imei=A.imei AND A.lastcom=mc.last_communication )X ON m.mtrno=X.meter_number\n"
		 * + "AND m.modem_sl_no=X.imei )a";
		 */

		/*
		 * String installqry="SELECT \"count\"(m.*),\n" +
		 * "\"count\"(CASE WHEN m.mtrno is not null AND mc.cdate =CURRENT_DATE then 1 END) as comm,\n"
		 * +
		 * "\"count\"(CASE WHEN m.mtrno is not null AND mc.ccount =1 then 1 END) as install_today\n"
		 * + " FROM meter_data.master_main m LEFT JOIN\n" + "(\n" +
		 * "SELECT meter_number, \"max\"(date) as cdate, \"count\"(*) as ccount FROM meter_data.modem_communication  GROUP BY meter_number \n"
		 * + ")mc ON m.mtrno=mc.meter_number ;";
		 */

		String installqry = "select A.* ,B.* From (SELECT \"count\"(m.*) as total_consumer,\n"
				+ "\"count\"(CASE WHEN m.mtrno is not null AND mc.cdate =CURRENT_DATE then 1 END) as comm,\n"
				+ "\"count\"(CASE WHEN m.mtrno is not null AND mc.ccount =1 then 1 END) as install_today\n"
				+ " FROM meter_data.master_main m LEFT JOIN\n" + "(\n"
				+ "SELECT meter_number, \"max\"(date) as cdate, \"count\"(*) as ccount FROM \n"
				+ "meter_data.modem_communication m  GROUP BY meter_number \n"
				+ ")mc ON m.mtrno=mc.meter_number) A,(select count(*) as total_installed from meter_data.survey_output)B";
//System.out.println("install qry--"+installqry);
		List<?> insprog = entityManager.createNativeQuery(installqry).getResultList();

		Object[] values = (Object[]) insprog.get(0);

		model.put("totalmaster", values[0]);
		model.put("commtoday", values[1]);
		model.put("installedtoday", values[2]);
		model.put("total_installed", values[3]);

		return "installationsMDAS";
	}

	@RequestMapping(value = "/communicationDetailsMDAS/{datecom1}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> communicationDetails(@PathVariable String datecom1, ModelMap model,
			HttpServletRequest request) throws java.text.ParseException, JSONException// get active and inactive count
	{
		// System.out.println("datecom========================"+datecom1);
		/*
		 * String
		 * commu="select a.circle,a.division,a.subdivision,a.substation,a.feeder_name,a.meter_no,a.imei_no,a.timestaken as instDate,CASE WHEN b.imei is null THEN 'NO' else 'yes' end as commun from (SELECT  circle,division,subdivision,substation,feeder_name, meter_no,timestaken,imei_no  from vcloudengine.modem_installation where to_char(to_date(timestaken,'dd-MM-yyyy'),'dd-MM-yyyy')= '"
		 * +datecom1+"')a left JOIN  (SELECT DISTINCT imei FROM meter_data.modem_communication  ) b on a.imei_no=b.imei"
		 * ;
		 */
		String commu = "SELECT \n" + "	A .circle,\n" + "	A .division,\n" + "	A .subdivision,\n"
				+ "	A .substation,\n" + "	A .fdrname,\n" + "	A .mtrno,\n" + "	A .modem_sl_no, \n"
				+ "	mc.last_communication\n"
				+ "FROM meter_data.master_main A, meter_data.modem_communication mc WHERE A.mtrno=mc.meter_number\n"
				+ "AND mc.\"date\"='" + datecom1 + "'";

		// System.out.println("commu sql==============="+commu);
		List<?> lis = entityManager.createNativeQuery(commu).getResultList();
		List<Map<String, Object>> list = new ArrayList<>();

		for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();) {
			Object[] values = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<>();
			data.put("circle", values[0]);
			data.put("division", values[1]);
			data.put("subDivision", values[2]);
			data.put("subStation", values[3]);
			data.put("feederNmae", values[4]);
			data.put("meterNo", values[5]);
			data.put("imei", values[6]);
			data.put("instDate", values[7]);
			data.put("commun", "YES");
			list.add(data);
		}

		return list;

	}

	@RequestMapping(value = "/meterDiagnosis", method = RequestMethod.GET)
	public String modemDiagnosis(ModelMap model, HttpServletRequest request) {
		// BCITSLogger.logger.info("------------ In modemDiagnosis -------------==>");
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode");
		model.put("zoneList", zoneList);
		model.put("circleList", feederService.getCircleByZone(zone, model));
		model.put("divisionList", feederService.getDivisionByCircle(zone, circle, model));
		model.put("subdivList", feederService.getSubdivByDivisionByCircle(zone, circle, division, model));

		model.addAttribute("zone", zone);
		model.addAttribute("circle", circle);
		model.addAttribute("division", division);
		model.addAttribute("subdiv", subdiv);
		model.addAttribute("results", "notDisplay");
		return "modemDiagnosisMDAS";
	}

	@RequestMapping(value = "/modemDiagnosisStatsMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String modemDiagnosisStats(ModelMap model, HttpServletRequest request) {
		// System.out.println("===========modemDiagnosisStats info==============");
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode");
		System.out.println("In modemDiagnosisStats==>" + zone + "==>" + circle + "==>" + division + "==>" + subdiv);
		model.put("zoneList", zoneList);
		model.put("circleList", feederService.getCircleByZone(zone, model));
		model.put("divisionList", feederService.getDivisionByCircle(zone, circle, model));
		model.put("subdivList", feederService.getSubdivByDivisionByCircle(zone, circle, division, model));

		model.addAttribute("zone", zone);
		model.addAttribute("circle", circle);
		model.addAttribute("division", division);
		model.addAttribute("subdiv", subdiv);
		model.addAttribute("results", "notDisplay");
		modemDiagnosisService.getModemDiagnosisStats(zone, circle, division, subdiv, model, request);
		return "modemDiagnosisMDAS";
	}

	@RequestMapping(value = "/modemDiagnosisCatMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String modemDiagnosisCat(ModelMap model, HttpServletRequest request) {
		// System.out.println("===========modemDiagnosisCat info==============");
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode"),
				category = request.getParameter("category");
		// System.out.println("In
		// modemDiagnosisCat==>"+zone+"==>"+circle+"==>"+division+"==>"+subdiv+"==>"+category);
		model.put("zoneList", zoneList);
		model.put("circleList", feederService.getCircleByZone(zone, model));
		model.put("divisionList", feederService.getDivisionByCircle(zone, circle, model));
		model.put("subdivList", feederService.getSubdivByDivisionByCircle(zone, circle, division, model));

		model.addAttribute("zone", zone);
		model.addAttribute("circle", circle);
		model.addAttribute("division", division);
		model.addAttribute("subdiv", subdiv);
		model.addAttribute("results", "notDisplay");
		modemDiagnosisService.getModemDiagnosisStats(zone, circle, division, subdiv, model, request);
		modemDiagnosisService.getModemDiagnosisCat(zone, circle, division, subdiv, category, model, request);
		// modemDiagnosisService.getModemDiagnosisCat(category,model,request);
		return "modemDiagnosisMDAS";
	}
	// deepa modem

	@RequestMapping(value = "/modemdiagnosis", method = { RequestMethod.GET, RequestMethod.POST })
	public String modemdiagnosis(ModelMap model, HttpServletRequest request) {
		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		return "modemdiagnosis";
	}

	@RequestMapping(value = "/modemcount", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> modemcount(HttpServletRequest request, ModelMap model) {

		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("subdiv");
		List<?> modemcount = modemDiagnosisService.getmodemcount(zone, circle, division, subdiv, model);
		return modemcount;
	}

	@RequestMapping(value = "/getValDiagStat", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getValDiagStat(HttpServletRequest request, ModelMap model) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("sdocode");
		String category = request.getParameter("category");
		List<?> DiagStatus = modemDiagnosisService.getValDiagStat(zone, circle, division, subdiv, category, model);
		return DiagStatus;
	}

	@RequestMapping(value = "/mapSubstationMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String mapSubstation(ModelMap model, HttpServletRequest request) throws JSONException {
		// System.out.println("===========mapSubstation info==============");
		String getSubStationPoints = "SELECT A.substation,B.substation_address,B.latitude,B.longitude,A.totFeeder FROM"
				+ " (SELECT DISTINCT substation,count(fdrname) as totFeeder FROM meter_data.master_main GROUP BY substation)A"
				+ " LEFT JOIN (SELECT substation_name,substation_address,latitude,longitude  FROM  "
				+ " vcloudengine.substation_output)B on A.substation=B.substation_name";

		// System.err.println("GET SUBSTATION POINTS : "+getSubStationPoints);
		Query queryDiag = entityManager.createNativeQuery(getSubStationPoints);
		List<Object[]> listDiag = queryDiag.getResultList();

		JSONArray array = new JSONArray();

		for (Object object[] : listDiag) {
			JSONObject obj = new JSONObject();
			obj.put("substation_name", object[0]);
			obj.put("substation_address", object[1]);
			obj.put("latitude", object[2]);
			obj.put("longitude", object[3]);
			obj.put("feederCount", object[4]);
			array.put(obj);
		}

		model.addAttribute("results", "notDisplay");
		model.addAttribute("substationPoints", array);
		return "mapSubstationMDAS";
	}

	@RequestMapping(value = "/meterExtremeViewMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String meterExtremeView(/* @RequestParam String mtrno, */ModelMap model, HttpServletRequest request) {
		return "360ExtremeViewMDAS";
	}

	@RequestMapping(value = "/meterChangeInfoMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String meterChangeInfo(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{

		/*
		 * String
		 * meterChangeQry="SELECT CC.fdrname,CC.mtrno,CC.circle,CC.division,CC.subdivision,CC.substation, DD.*,case when CC.mtrno=DD.meter_number then 'SAME' else 'METER CHANGED' END as status FROM (SELECT * from meter_data.master_main)CC JOIN(SELECT AA.imei,AA.maxDate,BB.meter_number  from (select imei,max(date) as maxDate from meter_data.modem_communication GROUP BY imei) AA LEFT JOIN meter_data.modem_communication BB ON AA.maxDate= BB.date AND AA.imei=BB.imei)DD ON CC.modem_sl_no=DD.imei AND CC.mtrno != DD.meter_number"
		 * ;
		 */
		String meterChangeQry = "SELECT CC.customer_name,CC.mtrno,CC.circle,CC.division,CC.subdivision,CC.accno, DD.*,case when CC.mtrno=DD.meter_number then 'SAME' else 'METER CHANGED' END as status,CC.mtrmake,CC.dlms FROM (SELECT * from meter_data.master_main)CC JOIN(SELECT AA.imei,AA.maxDate,BB.meter_number  from (select imei,max(date) as maxDate from meter_data.modem_communication GROUP BY imei) AA LEFT JOIN meter_data.modem_communication BB ON AA.maxDate= BB.date AND AA.imei=BB.imei)DD ON CC.modem_sl_no=DD.imei AND CC.mtrno != DD.meter_number ORDER BY CC.circle";

		Query queryUpload = entityManager.createNativeQuery(meterChangeQry);

		List<?> lis = queryUpload.getResultList();
		List<Map<String, Object>> list = new ArrayList<>();
		int i = 1;
		for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();) {
			Object[] values = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<>();
			data.put("rowNo", i);
			data.put("fdName", (values[0] == null) ? "--" : values[0]);
			data.put("OldmtrNo", (values[1] == null) ? "--" : values[1]);
			data.put("circle", (values[2] == null) ? "--" : values[2]);
			data.put("division", (values[3] == null) ? "--" : values[3]);
			data.put("subDivision", (values[4] == null) ? "--" : values[4]);
			data.put("subStation", (values[5] == null) ? "--" : values[5]);
			data.put("IMEI", (values[6] == null) ? "--" : values[6]);
			data.put("maxDate", (values[7] == null) ? "--" : values[7]);
			data.put("newMtrNo", (values[8] == null) ? "--" : values[8]);
			data.put("status", (values[9] == null) ? "--" : values[9]);
			data.put("mtrMake", (values[10] == null) ? "--" : values[10]);
			data.put("dlms", (values[11] == null) ? "--" : values[11]);

			list.add(data);
			i++;
		}
		model.put("mtrChangeInfo", list);

		return "meterChangeInfoMDAS";
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/updateMasterTableMDAS/{OldmtrNo}/{changeDate}/{newMtrNo}/{imei}/{fdrNmae}", method = RequestMethod.GET)
	public @ResponseBody String scanModem(@PathVariable String OldmtrNo, @PathVariable String changeDate,
			@PathVariable String newMtrNo, @PathVariable String imei, @PathVariable String fdrNmae, ModelMap model,
			HttpServletRequest request) {

		// System.out.println( "OldmtrNo==================="+OldmtrNo);
		// System.out.println( "changeDate==================="+changeDate);
		// System.out.println( "newMtrNo==================="+newMtrNo);
		String trimedOldmtrNo = OldmtrNo.trim();
		String trimedchangeDate = changeDate.trim();
		String trimednewMtrNo = newMtrNo.trim();
		String trimedimei = imei.trim();

		///////////////////////
		if (fdrNmae.contains("@") == true) {
			fdrNmae = fdrNmae.replaceAll("[@]", ".");
		}
		if (fdrNmae.contains("_") == true) {
			fdrNmae = fdrNmae.replaceAll("[_]", "/");
		}
		// System.out.println( "FEEDER NAME WITH DOT==================="+fdrNmae);
		///////////////////
		String trimedfdrNmae = fdrNmae.trim();
		int value = 0;

		try {

			String query = "update meter_data.master_main set mtr_change_date = '" + trimedchangeDate + "',old_mtr_no='"
					+ trimedOldmtrNo + "',mtrno='" + trimednewMtrNo + "' where trim(modem_sl_no) = '" + trimedimei
					+ "' and trim(fdrname)='" + trimedfdrNmae + "'";
			// System.out.println(" QUERY==="+query);
			value = entityManager.createNativeQuery(query).executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		}
		if (value == 0) {
			return "FAIL";
		}

		return "SUCCESS";

	}

	// Total Modem Communication
	@RequestMapping(value = "/totalModemCommInfoMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String totalModemComm(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{

		return "totalModemCommMDAS";
	}

	@RequestMapping(value = "/totalModemCommMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String totalModemCommRec(@RequestParam String date, ModelMap model, HttpServletRequest request)
			throws Exception {
		System.out.println("datecom1========" + date);
		String todate = request.getParameter("todate");
		// System.out.println("todate :: "+todate);
		/*
		 * Calendar cal = Calendar.getInstance(); cal.add(Calendar.DATE, 1);
		 * SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		 * System.out.println(cal.getTime()); // Output "Wed Sep 26 14:23:28 EST 2012"
		 * 
		 * String formatted = format1.format(cal.getTime());
		 * System.out.println(formatted); // Output "2012-09-26"
		 */

		// String dateyyMMDD=getFormatedDate1(date);
		// String totalModemComm_query="select distinct imei,meter_number ,max(date) as
		// date from meter_data.modem_communication where
		// to_char(date,'dd-MM-yyyy')<='"+dateyyMMDD+"'group by imei,meter_number order
		// by date,imei,meter_number ";
		/*
		 * String totalModemComm_query =
		 * "select distinct imei,meter_number ,max(date) as date  from meter_data.modem_communication  where date<='"
		 * + date + "'group by imei,meter_number order by  date,imei,meter_number ";
		 */

		/*
		 * String totalModemComm_query =
		 * "SELECT DISTINCT imei, meter_number, MAX(DATE) AS DATE FROM meter_data.modem_communication WHERE "
		 * + "DATE >= '"+date+"' AND DATE<='"
		 * +todate+"' GROUP BY imei, meter_number ORDER BY DATE, imei, meter_number";
		 */

		String totalModemComm_query = "SELECT a.imei,a.meter_number, a.DATE, m.zone, m.circle, m.division,m.subdivision "
				+ "FROM( SELECT DISTINCT imei, meter_number, MAX(DATE) AS DATE FROM meter_data.modem_communication "
				+ "WHERE DATE >= '" + date + "' AND DATE<='" + todate
				+ "' GROUP BY imei, meter_number ORDER BY DATE, imei, meter_number)a "
				+ "LEFT JOIN meter_data.master_main m ON a.imei=m.modem_sl_no AND a.meter_number=m.mtrno";

		// System.out.println("=======totalModemComm query==========" +
		// totalModemComm_query);
		Query queryUpload = entityManager.createNativeQuery(totalModemComm_query);

		List<?> lis = queryUpload.getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> activityMap = null;
		int i = 1;
		for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			activityMap = new HashMap<>();
			activityMap.put("imei", (values[0] == null) ? "--" : values[0]);
			activityMap.put("mtrNo", (values[1] == null) ? "--" : values[1]);
			activityMap.put("date", (values[2] == null) ? "--" : values[2]);
			activityMap.put("zone", (values[3] == null) ? "--" : values[3]);
			activityMap.put("circle", (values[4] == null) ? "--" : values[4]);
			activityMap.put("division", (values[5] == null) ? "--" : values[5]);
			activityMap.put("subdivision", (values[6] == null) ? "--" : values[6]);
			activityMap.put("rowNo", i);
			result.add(activityMap);
			i++;
		}
		model.put("totalModemInfo", result);
		model.put("date", date);
		model.put("todate", todate);
		return "totalModemCommMDAS";

	}

	@RequestMapping(value = "/getUpdateMasterFdrtableMDAS/{imeiVal}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> getUpdateMasterFdrtable(@PathVariable String imeiVal, ModelMap model,
			HttpServletRequest request) throws java.text.ParseException, JSONException// get active and inactive count
	{
		// System.out.println("imeiVal========================"+imeiVal);
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> data;

		String checkInmaster = "select mtrno from meter_data.master_main WHERE modem_sl_no='" + imeiVal + "'";
		Query query1 = entityManager.createNativeQuery(checkInmaster);
		List<?> lis12 = query1.getResultList();

		if (lis12 == null || lis12.isEmpty()) {
			data = new HashMap<>();
			data.put("NoValue", "true");
			list.add(data);
			return list;
		} else {
			String updateMasterFdrQuery = "select zone,circle,division,subdivision,substation,modem_sl_no,fdrname from meter_data.master_main  WHERE modem_sl_no='"
					+ imeiVal + "'";
			System.out.println("updateMasterFdrQuery sql===============" + updateMasterFdrQuery);

			Query query = entityManager.createNativeQuery(updateMasterFdrQuery);
			List<?> lis = query.getResultList();

			for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();) {
				Object[] values = (Object[]) iterator.next();
				data = new HashMap<>();
				data.put("zone", values[0]);
				data.put("circle", values[1]);
				data.put("division", values[2]);
				data.put("subdivision", values[3]);
				data.put("substation", values[4]);
				data.put("modem_sl_no", values[5]);
				data.put("fdrname", values[6]);
				/*
				 * data.put("TemporrayOFF", "TemporrayOFF"); data.put("Feeder Mereged",
				 * "Feeder Mereged"); data.put("Feeder Shifted","Feeder Shifted");
				 * data.put("Feeder Not in USe","Feeder Not in USe");
				 * data.put("Feeder Meter Burnt", "Feeder Meter Burnt"); data.put("WORKING",
				 * "WORKING");
				 */
				data.put("NoValue", "false");

				list.add(data);
			}

			return list;
		}

	}

	/*
	 * @Transactional(propagation=Propagation.REQUIRED)
	 * 
	 * @RequestMapping(value="/updateMasterFdrStatusCol/{selectedStatus}/{imeiVal}",
	 * method=RequestMethod.GET) public @ResponseBody String
	 * updateMasterFdrStatusCol(@PathVariable String selectedStatus,@PathVariable
	 * String imeiVal,ModelMap model, HttpServletRequest request) {
	 * System.out.println("selectedStatus==="+selectedStatus);
	 * System.out.println("imeiVal==="+imeiVal); int value=0;
	 * 
	 * try {
	 * 
	 * String query = "update meter_data.master_main set feeder_status = '"
	 * +selectedStatus+"' where modem_sl_no='"+imeiVal+"'";
	 * System.out.println(" QUERY==="+query); value =
	 * entityManager.createNativeQuery(query).executeUpdate();
	 * System.out.println("value======="+value); } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } if(value==1) { return "SUCCESS";
	 * 
	 * }
	 * 
	 * 
	 * return "FAIL"; }
	 */

	private static Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	@RequestMapping(value = "/getDataBefore3OCLKMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getDataBefore3OCLK(ModelMap model, HttpServletRequest request) throws Exception// get
																												// active
																												// and
																												// inactive
																												// count
	{
		String mtr = null;
		Map allStatus = new HashMap<String, String>();

		String allMtrsInMaster = "select DISTINCT mtrno FROM meter_data.master_main";

		// System.out.println("allMtrInMaster sql==============="+allMtrsInMaster);
		Query query1 = entityManager.createNativeQuery(allMtrsInMaster);
		List<?> lis = query1.getResultList();
		int i = 1;
		System.out.println("out side first for ");
		System.out.println("master main length====== " + lis.size());
		/////////////////////////////// Load Survey///////////////////////////////////
		for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();) {

			String dynamicMtr = (String) iterator.next();

			System.out.println("meter_no " + i + "===>" + dynamicMtr);

			i++;

			// To check Load survey
			String checkInLoadSurvey = "SELECT count(meter_number)from meter_data.load_survey  where meter_number='"
					+ dynamicMtr + "' AND CAST(read_time as TEXT)like '2017-10-29%' GROUP BY meter_number";

			// System.out.println("checkInLoadSurvey==============="+checkInLoadSurvey);

			Query query2 = entityManager.createNativeQuery(checkInLoadSurvey);
			List<?> lis2 = query2.getResultList();

			// System.out.println("inside side first FOR");
			// System.out.println("load survey size===="+lis2.size());
			if (lis2.size() == 0) {
				System.out.println("No load survey data");

			} else {
				Object count = null;
				for (Iterator<?> iterator2 = lis2.iterator(); iterator2.hasNext();)

				{
					System.out.println("Load survey count ");
					final Object[] values = (Object[]) iterator.next();

					String meter_number = (String) values[0];
					String imei_no = (String) values[1];

					System.out.println("count===" + count);
					if (Integer.valueOf(count.toString()) >= 48) {
						// System.out.println("Load survey count equal or greater than 48");
						allStatus.put("LOAD", "TRUE");
					} else {
						// 3GC;PULL;INST;868657029196084;17:12:21:14:00:00:;17:12:21:15:22:41:
						String msg = "3GC;PULL;INST;" + imei_no + ";" + yesterdayInst();
						// System.out.println("Load survey count is less than 48");
						String response = Subscriber.sendMqttMessage("ir/" + imei_no, msg);
						if (response == "SUCCESS") {
							System.out.println("Load survey mqtt request sent successfully for the meter== "
									+ meter_number + " with the imei no== " + imei_no);

						} else {
							System.out.println("Load survey mqtt request sent failed for the meter== " + meter_number
									+ " with the imei no== " + imei_no);
						}

						allStatus.put("LOAD", "FALSE");
					}

				}

			}
			//////////////// To check Bill/////////////////////////////////////

			List<AmrBillsEntity> amrBill = amrBillsService.getRecords(dynamicMtr, yesterday().toString());
			if (amrBill.size() == 0) {
				System.out.println("Billing data not available...So we are sending a mqtt request msg");
				String msg = ((AmrBillsEntity) amrBill).getImei().toString() + "|3GC HPL|LATESTLOAD|"
						+ ((AmrBillsEntity) amrBill).getMeterId().toString() + "|";

				String response = Subscriber.sendMqttMessage("ir/" + ((AmrBillsEntity) amrBill).getImei().toString(),
						msg);
				if (response == "SUCCESS") {
					System.out.println("Billing  mqtt request sent successfully for the meter== "
							+ ((AmrBillsEntity) amrBill).getMeterId().toString() + " with the imei no== "
							+ ((AmrBillsEntity) amrBill).getImei().toString());

				} else {
					System.out.println("Billing mqtt request sent failed for the meter== "
							+ ((AmrBillsEntity) amrBill).getMeterId().toString() + " with the imei no== "
							+ ((AmrBillsEntity) amrBill).getImei().toString());
				}
			} else {
				System.out.println("Got Billing data successfully");
			}

			//////////////// To check instantaneous/////////////////////////////////////

			List<AmrInstantaneousEntity> amrInst = (List<AmrInstantaneousEntity>) amrInstantaneousService
					.getD1DataForXml(dynamicMtr, yesterday().toString());
			if (amrInst.size() == 0) {
				System.out.println("Instantaneous data not available...So we are sending a mqtt request msg");
				// Ir/868657029196084,
				// 3GC;PULL;INST;1234567890123;17:12:23:14:00:00:;17:12:23:15:22:41:;

				String msg = "3GC;PULL;INST;1234567890123;" + yesterdayInst();
				String response = Subscriber
						.sendMqttMessage("ir/" + ((AmrInstantaneousEntity) amrInst).getImei().toString(), msg);
				if (response == "SUCCESS") {
					System.out.println("Load survey mqtt request sent successfully for the meter== "
							+ ((AmrBillsEntity) amrBill).getMeterId().toString() + " with the imei no== "
							+ ((AmrBillsEntity) amrBill).getImei().toString());

				} else {
					System.out.println("Load survey mqtt request sent failed for the meter== "
							+ ((AmrBillsEntity) amrBill).getMeterId().toString() + " with the imei no== "
							+ ((AmrBillsEntity) amrBill).getImei().toString());
				}
			} else {
				System.out.println("Got Billing data successfully");
			}

		}

		return "SUCCESS";
	}

	private static String yesterdayInst() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		// 17:12:23:14:00:00:;17:12:23:15:22:41:
		String month = (((cal.get(Calendar.MONDAY) + 1) < 10) ? "0" : "") + (cal.get(Calendar.MONDAY) + 1);
		String formatedDate1 = String.valueOf(cal.get(Calendar.YEAR)).substring(2) + ":" + month + ":"
				+ cal.get(Calendar.DATE) + ":00:00:00:;";
		String formatedDate2 = String.valueOf(cal.get(Calendar.YEAR)).substring(2) + ":" + month + ":"
				+ cal.get(Calendar.DATE) + ":18:23:00:";
		// System.out.println("formatedDate : " + formatedDate1+formatedDate2);
		return formatedDate1 + formatedDate2;
	}

	@ResponseBody
	@RequestMapping(value = "/testMsgMQTTMDAS", method = RequestMethod.GET)
	public String sendMsgMQTT() {
		String response = "";
		// 3GC;PULL;INST;868657029196084;17:12:21:14:00:00:;17:12:21:15:22:41:
		String msg = "3GC;PULL;INST;868657029196084;18:02:21:14:00:00:;18:02:21:15:22:41:;";
		// response= Subscriber.sendMqttMessage("ir/868657029196084", "3gc SETT;51:01");
		// response= Subscriber.sendMqttMessage("ir/868657029196084", "3gc SETT;51:01");
		/* System.out.println("response==========="+response); */
		// BSNL=8762795468
		// Idea=9071764934

		// to change IP(Give IP of mqtt server)
		// SMSAlertsToNigamConsumers("3gc
		// SETT;04:164.100.59.158;","9071764934","BCITS");
		// to change port(Give port of mqtt server)
		// SMSAlertsToNigamConsumers("3gc SETT;05:1883;","9071764934","BCITS");

		SMSAlertsToNigamConsumers("3gc SETT;51:01;", "9071764934", "BCITS");
		SMSAlertsToNigamConsumers(msg, "9071764934", "BCITS");
		// System.out.println("yesterdayInst()=================="+yesterdayInst());
		return response;
	}

	// delet once tested.......
	public void SMSAlertsToNigamConsumers(String mailMessage, String mobNo, String userName) {

		// System.out.println("======= SMSAlertsToNigamConsumers ========"+mailMessage);
		BCITSLogger.logger.info("******* SMSAlertsToNigamConsumers *******");
		SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		smsCredentialsDetailsBean.setNumber(mobNo);
		smsCredentialsDetailsBean.setUserName(userName);
		/*
		 * String mailMessage="Dear Customer,JVVNL announces award u p to Rs. 5000* on
		 * online electricity bill payments. " +
		 * " Avail your chance to win lottery by paying your energy bills online. *T&C apply."
		 * + "   " +"      Warm Regards, " +" JVVNL-Administration services";
		 */
		// String mailMessage="711151 : 3gc SETT; 04:"+chIp+"";
		// String mailMessage="3gc SETT; 04:"+chIp+"";
		smsCredentialsDetailsBean.setMessage(mailMessage);
		new Thread(new SendDocketInfoSMS(smsCredentialsDetailsBean)).start();
	}

///////////////Not communicated modems////////
	@RequestMapping(value = "/totalModemNotCommDetailsMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String totalModemNotCommDetails(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{
		/*
		 * 
		 * 
		 * 
		 * //String
		 * totalModemNotCommDetails="SELECT MM.modem_sl_no,DATE_PART('days', DATE_TRUNC('day', NOW()) - '10-01-2017') - count(MC.last_communication) as Not_Communicated from meter_data.modem_communication MC,(select modem_sl_no from meter_data.master_main where feeder_status='Feeder Live' ) MM WHERE MC.imei=MM.modem_sl_no  and to_char(MC.last_communication, 'YYYY-MM-DD HH24:MI:SS')>='10-01-2017%' group by MM.modem_sl_no"
		 * ;
		 * 
		 * String
		 * sql="SELECT A.imei, DATE_PART( 'days', DATE_TRUNC('day', NOW()) - (SELECT MIN(last_communication) FROM meter_data.modem_communication WHERE imei=A .imei)) - B.count AS Not_Communicated FROM( SELECT m.imei as imei, m.last_communication as last_com, rank() OVER (PARTITION BY m.imei ORDER BY m.last_communication ASC ) as ranking FROM meter_data.modem_communication m )A, ( SELECT imei as imei1, \"count\"(last_communication) as count FROM meter_data.modem_communication MC GROUP BY imei ) B WHERE A.ranking=1 AND to_char( A.last_com, 'YYYY-MM-DD HH24:MI:SS' ) >= '10-01-2017%' AND a.imei=B.imei1"
		 * ; String
		 * sql="SELECT * FROM( SELECT A .imei as imei, DATE_PART( 'days', DATE_TRUNC('day', NOW()) - to_date(to_char((SELECT MIN(last_communication) FROM meter_data.modem_communication WHERE imei=A .imei), 'MM-DD-YYYY'), 'MM-DD-YYYY')) - B. COUNT AS Not_Communicated FROM ( SELECT M .imei AS imei, M .last_communication AS last_com, RANK () OVER ( PARTITION BY M .imei ORDER BY M .last_communication ASC ) AS ranking FROM meter_data.modem_communication M ) A, ( SELECT imei AS imei1, \"count\" (last_communication) AS COUNT FROM meter_data.modem_communication MC GROUP BY imei ) B WHERE A .ranking = 1 AND to_char( A .last_com, 'YYYY-MM-DD HH24:MI:SS' ) >= '10-01-2017%' AND A .imei = B.imei1 )D WHERE d.Not_Communicated>0"
		 * ; String
		 * sql="SELECT d.imei, d.Not_Communicated,d.mtr_no,mm.ZONE, mm.circle, mm.division, mm.subdivision, mm.substation FROM( SELECT A .imei AS imei, DATE_PART( 'days', DATE_TRUNC('day', NOW()) - to_date( to_char( ( SELECT MIN (last_communication) FROM meter_data.modem_communication WHERE imei = A .imei), 'MM-DD-YYYY' ), 'MM-DD-YYYY' ) ) - B. COUNT AS Not_Communicated,A.mtr_no FROM ( SELECT M .imei AS imei, M .last_communication AS last_com, m.meter_number as mtr_no, RANK () OVER ( PARTITION BY M .imei ORDER BY M .last_communication ASC ) AS ranking FROM meter_data.modem_communication M ) A, ( SELECT imei AS imei1, \"count\" (last_communication) AS COUNT FROM meter_data.modem_communication MC GROUP BY imei ) B WHERE A .ranking = 1 AND A .imei = B.imei1 ) D LEFT JOIN meter_data.master_main mm ON mm.modem_sl_no=d.imei AND mm.mtrno=d.mtr_no WHERE d.Not_Communicated > 0 "
		 * ; System.out.println("=======totalModemNotCommDetails=========="+sql); Query
		 * queryUpload= entityManager.createNativeQuery(sql);
		 * 
		 * List<?> lis = queryUpload.getResultList(); List<Map<String, Object>> list=new
		 * ArrayList<>(); int i=1; for (Iterator<?> iterator = lis.iterator();
		 * iterator.hasNext();) { Object[] values = (Object[]) iterator.next();
		 * Map<String, Object> data=new HashMap<>(); data.put("rowNo", i);
		 * data.put("imei",(values[0]==null)?"--":values[0]);
		 * data.put("count",(values[1]==null)?"--":(int)Double.parseDouble(values[1].
		 * toString()));
		 * 
		 * data.put("zone",values[3]==null?"":values[3]);
		 * data.put("circle",values[4]==null?"":values[4]);
		 * data.put("division",values[5]==null?"":values[5]);
		 * data.put("subdivision",values[6]==null?"":values[6]);
		 * data.put("substation",values[7]==null?"":values[7]); list.add(data); i++; }
		 * model.put("totalModemNotComm", list);
		 */

		String sql = "SELECT sad.zone as zone, Count(CASE WHEN sad.notcom=1 THEN 1 END) as slab1, Count(CASE WHEN sad.notcom=2 THEN 1 END) as slab2, "
				+ "Count(CASE WHEN sad.notcom>2 AND sad.notcom<=7 THEN 1 END) as slab3, Count(CASE WHEN sad.notcom>7 AND sad.notcom<=15 THEN 1 END) as slab4, "
				+ "Count(CASE WHEN sad.notcom>15 AND sad.notcom<=30 THEN 1 END) as slab5, Count(CASE WHEN sad.notcom>30 THEN 1 END) as slab6 "
				+ "FROM( SELECT d.imei as imei, d.Not_Communicated as notcom, d.mtr_no, mm. ZONE as zone, mm.circle, mm.division,"
				+ "mm.subdivision, mm.substation FROM ( SELECT A .imei AS imei, DATE_PART( 'days', DATE_TRUNC('day', NOW()) - "
				+ "to_date( to_char( ( SELECT MAX (last_communication) FROM meter_data.modem_communication WHERE imei = A .imei), "
				+ "'MM-DD-YYYY' ), 'MM-DD-YYYY' ) )   AS Not_Communicated, A .mtr_no FROM ( SELECT M .imei AS imei, "
				+ "M .last_communication AS last_com, M .meter_number AS mtr_no, RANK () OVER ( PARTITION BY M .imei ORDER BY "
				+ "M .last_communication DESC ) AS ranking FROM meter_data.modem_communication M ) A, ( SELECT imei AS imei1, "
				+ "COUNT (last_communication) AS COUNT FROM meter_data.modem_communication MC GROUP BY imei ) B WHERE "
				+ "A .ranking = 1 AND A .imei = B.imei1 ) D LEFT JOIN meter_data.master_main mm ON mm.modem_sl_no = d.imei "
				+ "AND mm.mtrno = d.mtr_no WHERE d.Not_Communicated > 0 ) sad GROUP BY sad.zone";

		// System.out.println("=======totalModemNotCommDetails=========="+sql);
		List<?> lis = entityManager.createNativeQuery(sql).getResultList();

		List<Map<String, Object>> list = new ArrayList<>();
		for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();) {
			Object[] values = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<>();
			if (values[0] != null) {
				data.put("zone", (values[0] == null) ? "--" : values[0]);
				data.put("slab1", (values[1] == null) ? "0" : (int) Double.parseDouble(values[1].toString()));
				data.put("slab2", (values[2] == null) ? "0" : (int) Double.parseDouble(values[2].toString()));
				data.put("slab3", (values[3] == null) ? "0" : (int) Double.parseDouble(values[3].toString()));
				data.put("slab4", (values[4] == null) ? "0" : (int) Double.parseDouble(values[4].toString()));
				data.put("slab5", (values[5] == null) ? "0" : (int) Double.parseDouble(values[5].toString()));
				data.put("slab6", (values[6] == null) ? "0" : (int) Double.parseDouble(values[6].toString()));
				list.add(data);
			}
		}
		model.put("discomWiseList", list);

		return "totalModemNotCommMDAS";
	}

	@RequestMapping(value = "/getNotCommunicatedDataForFilterMDAS/{zone}/{slab}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object getNotCommunicatedDataForFilter(@PathVariable String zone, @PathVariable String slab,
			HttpServletRequest request, ModelMap model) {
		String sql = "";
		sql = "SELECT d.imei, d.Not_Communicated,d.mtr_no,mm.ZONE, mm.circle, mm.division, mm.subdivision, mm.substation "
				+ "FROM( SELECT A .imei AS imei, DATE_PART( 'days', DATE_TRUNC('day', NOW()) - to_date( to_char( ( SELECT MAX "
				+ "(last_communication) FROM meter_data.modem_communication WHERE imei = A .imei), 'MM-DD-YYYY' ), 'MM-DD-YYYY' ) ) "
				+ "- 1 AS Not_Communicated,A.mtr_no FROM ( SELECT M .imei AS imei, M .last_communication AS last_com, "
				+ "m.meter_number as mtr_no, RANK () OVER ( PARTITION BY M .imei ORDER BY M .last_communication DESC ) "
				+ "AS ranking FROM meter_data.modem_communication M ) A, ( SELECT imei AS imei1, \"count\" (last_communication) "
				+ "AS COUNT FROM meter_data.modem_communication MC GROUP BY imei ) B WHERE A .ranking = 1 AND A .imei = B.imei1 ) "
				+ "D LEFT JOIN meter_data.master_main mm ON mm.modem_sl_no=d.imei AND mm.mtrno=d.mtr_no ";

		String addZone = "";
		if ("--".equals(zone)) {
			addZone = "WHERE mm.zone is NULL ";
		} else {
			addZone = "WHERE mm.zone='" + zone + "'";
		}

		String add = "AND d.Not_Communicated ";

		switch (slab) {
		case "1":
			add += "=1";
			break;

		case "2":
			add += "=2";
			break;

		case "3":
			add += ">2 AND d.Not_Communicated <=7";
			break;

		case "4":
			add += ">7 AND d.Not_Communicated <=15";
			break;

		case "5":
			add += ">15 AND d.Not_Communicated <=30";
			break;

		case "6":
			add += ">30";
			break;
		}

		sql += addZone + add;

		/*
		 * if("ALL".equalsIgnoreCase(zone)) { } else { int temp=Integer.parseInt(zone);
		 * 
		 * if(temp<=30) {
		 * sql="SELECT d.imei, d.Not_Communicated,d.mtr_no,mm.ZONE, mm.circle, mm.division, mm.subdivision, mm.substation FROM( SELECT A .imei AS imei, DATE_PART( 'days', DATE_TRUNC('day', NOW()) - to_date( to_char( ( SELECT MIN (last_communication) FROM meter_data.modem_communication WHERE imei = A .imei), 'MM-DD-YYYY' ), 'MM-DD-YYYY' ) ) - B. COUNT AS Not_Communicated,A.mtr_no FROM ( SELECT M .imei AS imei, M .last_communication AS last_com, m.meter_number as mtr_no, RANK () OVER ( PARTITION BY M .imei ORDER BY M .last_communication ASC ) AS ranking FROM meter_data.modem_communication M ) A, ( SELECT imei AS imei1, \"count\" (last_communication) AS COUNT FROM meter_data.modem_communication MC GROUP BY imei ) B WHERE A .ranking = 1 AND A .imei = B.imei1 ) D LEFT JOIN meter_data.master_main mm ON mm.modem_sl_no=d.imei AND mm.mtrno=d.mtr_no WHERE d.Not_Communicated > 0 AND d.Not_Communicated <="
		 * +temp; } else {
		 * sql="SELECT d.imei, d.Not_Communicated,d.mtr_no,mm.ZONE, mm.circle, mm.division, mm.subdivision, mm.substation FROM( SELECT A .imei AS imei, DATE_PART( 'days', DATE_TRUNC('day', NOW()) - to_date( to_char( ( SELECT MIN (last_communication) FROM meter_data.modem_communication WHERE imei = A .imei), 'MM-DD-YYYY' ), 'MM-DD-YYYY' ) ) - B. COUNT AS Not_Communicated,A.mtr_no FROM ( SELECT M .imei AS imei, M .last_communication AS last_com, m.meter_number as mtr_no, RANK () OVER ( PARTITION BY M .imei ORDER BY M .last_communication ASC ) AS ranking FROM meter_data.modem_communication M ) A, ( SELECT imei AS imei1, \"count\" (last_communication) AS COUNT FROM meter_data.modem_communication MC GROUP BY imei ) B WHERE A .ranking = 1 AND A .imei = B.imei1 ) D LEFT JOIN meter_data.master_main mm ON mm.modem_sl_no=d.imei AND mm.mtrno=d.mtr_no WHERE d.Not_Communicated >="
		 * +temp; }
		 * 
		 * }
		 */

		// System.out.println("=======totalModemNotCommDetails=========="+sql);
		Query queryUpload = entityManager.createNativeQuery(sql);

		List<?> lis = queryUpload.getResultList();
		List<Map<String, Object>> list = new ArrayList<>();
		int i = 1;
		for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();) {
			Object[] values = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<>();
			data.put("rowNo", i);
			data.put("imei", (values[0] == null) ? "--" : values[0]);
			data.put("count", (values[1] == null) ? "--" : (int) Double.parseDouble(values[1].toString()));
			data.put("meterNo", values[2] == null ? "" : values[2]);
			data.put("zone", values[3] == null ? "" : values[3]);
			data.put("circle", values[4] == null ? "" : values[4]);
			data.put("division", values[5] == null ? "" : values[5]);
			data.put("subdivision", values[6] == null ? "" : values[6]);
			data.put("substation", values[7] == null ? "" : values[7]);
			list.add(data);
			i++;
		}

		return list;

	}

	@RequestMapping(value = "/notCommDaysMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String notCommDays(ModelMap model, HttpServletRequest request) {
		String imei = request.getParameter("imei");

		String totalModemNotCommDetails = "select distinct to_char(cast(i as date),'yyyy-MM-dd') i  from generate_series((SELECT MAX( last_communication) FROM meter_data.modem_communication WHERE imei = '"
				+ imei + "'),CURRENT_DATE - 1, '1 day') i,meter_data.modem_communication MM where imei= '" + imei
				+ "' and i not in (SELECT last_communication FROM meter_data.modem_communication where imei= '" + imei
				+ "') order by i";

		// System.out.println("=======totalModemNotCommDetails=========="+totalModemNotCommDetails);
		Query queryUpload = entityManager.createNativeQuery(totalModemNotCommDetails);

		List<?> lis = queryUpload.getResultList();
		List<Map<String, Object>> list = new ArrayList<>();
		int i = 1;

		for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();) {
			String values = (String) iterator.next();
			Map<String, Object> data = new HashMap<>();
			data.put("rowNo", i);
			data.put("days", (values == null) ? "--" : values);

			// System.out.println(i+"==values==="+values);
			list.add(data);
			i++;
		}
		// System.out.println("lenght of not comm date======
		// "+imei+"==========size==="+list.size());
		model.put("notCommDaysList", list);
		model.put("imei", imei);
		return "notCommModemMonthWiseMDAS";
	}

	@RequestMapping(value = "/fourMonthsModemChartMDAS/{imei}", method = { RequestMethod.GET })
	public @ResponseBody String fourMonthsModemChart(@PathVariable String imei, ModelMap model,
			HttpServletRequest request) throws JSONException, java.text.ParseException// get active and inactive count
	{
		// String active="SELECT date_trunc,CASE WHEN b.last_communication IS NULL THEN
		// 'N' ELSE 'Y' END FROM (SELECT cast(date_trunc('day', dd)as date) date_trunc
		// FROM generate_series ( date_trunc('month', CURRENT_DATE) - interval '5
		// month', (CURRENT_DATE-1),'1 day' ) dd)a LEFT JOIN (SELECT
		// imei,cast(last_communication AS date) FROM meter_data.modem_communication
		// WHERE imei='"+imei+"')b ON b.last_communication =a.date_trunc ORDER BY
		// date_trunc";
		String active = "SELECT DISTINCT date_trunc,CASE WHEN b.last_communication IS NULL THEN 'N' ELSE 'Y' END FROM (SELECT cast(date_trunc('day', dd)as date) date_trunc FROM generate_series( to_date(to_char((SELECT MAX(last_communication) FROM meter_data.modem_communication WHERE imei='"
				+ imei
				+ "'), 'MM-DD-YYYY'), 'MM-DD-YYYY'), CURRENT_DATE - 1, '1 day') dd)a LEFT  JOIN (SELECT  imei,cast(last_communication AS date) FROM meter_data.modem_communication WHERE imei='"
				+ imei + "')b  ON b.last_communication =a.date_trunc ORDER BY date_trunc";
		Date date = new Date();
		String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

		// System.out.println("modifiedDate======"+modifiedDate);
		// System.err.println("fourMonthsModemChart: " + active);
		Query queryUpload = entityManager.createNativeQuery(active);
		List<Object[]> li = queryUpload.getResultList();

		int month = 0;
		JSONArray outerJSONArray = new JSONArray();
		JSONArray firstmonth = new JSONArray();
		int i = 1;
		JSONArray monthJSArray = null;
		JSONObject monthJSObj = null;
		for (Object[] objects : li) {
			JSONObject js = new JSONObject();
			js.put("date", objects[0].toString());
			js.put("yesNO", objects[1]);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = df.parse(objects[0].toString());

			LocalDate localDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			// Month month2 = localDate.getMonth();
			int month1 = localDate.getMonthValue();
			// System.out.println("month local==="+month+"==month from
			// obj==="+month1+"==date=="+objects[0].toString()+"==yesNO=="+objects[1]);
			DateFormat df1 = new SimpleDateFormat("MMM");
			Date startDate1 = df.parse(objects[0].toString());
			LocalDate localDate1 = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Month monthText = localDate1.getMonth();
			if (month != month1) {
				month = month1;
				monthJSArray = new JSONArray();
				monthJSObj = new JSONObject();// dec
				// System.out.println("monthJSArray monthJSArray created");
				if (monthJSObj != null) {
					// System.out.println("adding to outer array==="+outerJSONArray);
					// System.out.println("monthJSObj===="+monthJSObj);
					outerJSONArray.put(monthJSObj);

				}
			}

			monthJSArray.put(js);
			monthJSObj.put(monthText + "", monthJSArray);
		}
		// System.out.println("array========"+outerJSONArray);
		return outerJSONArray.toString();

	}

	@RequestMapping(value = "/updateMasterFdrStatusMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateMasterFdr(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);

		return "updateMasterFdrStatusMDAS";
	}

	@RequestMapping(value = "/updateMasterFdrStatusSubmitMDAS", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String updateMasterFdrStatus(HttpServletRequest request, ModelMap model) {
		String imei = request.getParameter("imei");
		String mtrno = request.getParameter("mtrno");
		String type = request.getParameter("type");
		MasterMainEntity mainEntity = MasterMainService.getEntityByImeiNoAndMtrNO(imei, mtrno);
		mainEntity.setFeeder_status(type);

		try {
			MasterMainService.customupdateBySchema(mainEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
			return "FALIURE";
		}
		return "SUCCESS";

	}

	@RequestMapping(value = "/updateMasterFdrTypeMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateMasterFdrType(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);

		return "updateMasterFdrTypeMDAS";

	}

	@RequestMapping(value = "/getFeedersBasedOnMDAS/{zone}/{circle}/{division}/{subdiv}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object getFeedersBasedOn(@PathVariable String zone, @PathVariable String circle,
			@PathVariable String division, @PathVariable String subdiv, HttpServletRequest request, ModelMap model) {
		System.out.println(
				"==--getFeedersBasedOn--==List==" + zone + "==>>" + circle + "==>>" + division + "==>>" + subdiv);
		return MasterMainService.getFeedersBasedOn(zone, circle, division, subdiv);
	}

	@RequestMapping(value = "/getAllMeterData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getAllMeterData(HttpServletRequest request, ModelMap model) {
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("subdiv");
		System.out.println(
				"==--getFeedersBasedOn--==List==" + zone + "==>>" + circle + "==>>" + division + "==>>" + subdiv);
		return masterService.getMeterBasedOn(circle, division, subdiv);
	}

	@RequestMapping(value = "/getSubStationsBasedOnMDAS/{zone}/{circle}/{division}/{subdiv}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getSubStationsBasedOn(@PathVariable String zone, @PathVariable String circle,
			@PathVariable String division, @PathVariable String subdiv, HttpServletRequest request, ModelMap model) {
		System.out.println(
				"==--getFeedersBasedOn--==List==" + zone + "==>>" + circle + "==>>" + division + "==>>" + subdiv);
		return MasterMainService.getSubStationsBasedOn(zone, circle, division, subdiv);
	}

	@RequestMapping(value = "/getFeedersBasedOnMDAS/{imei}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getFeedersBasedOnImei(@PathVariable String imei, HttpServletRequest request,
			ModelMap model) {
		System.out.println("==--getFeedersBasedOnImei--==List==" + imei);
		return MasterMainService.getFeedersBasedOnImei(imei);
	}

	@RequestMapping(value = "/getFeedersBasedOnMeterNoMDAS/{mterno}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object getFeedersBasedOnMeterNo(@PathVariable String mterno, HttpServletRequest request,
			ModelMap model) {
		System.out.println("==--getFeedersBasedOnMeterNo--==List==" + mterno);
		return MasterMainService.getFeedersBasedOnMeterNo(mterno);
	}

	@RequestMapping(value = "/getFeedersBasedOnaccno/{accno}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getFeedersBasedOnaccno(@PathVariable String accno, HttpServletRequest request,
			ModelMap model) {
		System.out.println("==--getFeedersBasedOnMeterNo--==accnooooo==" + accno);
		return MasterMainService.getFeedersBasedOnAccno(accno);
	}

	@RequestMapping(value = "/updateMasterFdrTypeSubmitMDAS", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String updateMasterFdrType(HttpServletRequest request, ModelMap model) {
		String imei = request.getParameter("imei");
		String mtrno = request.getParameter("mtrno");
		String type = request.getParameter("type");
		MasterMainEntity mainEntity = MasterMainService.getEntityByImeiNoAndMtrNO(imei, mtrno);
		mainEntity.setFeeder_type(type);

		try {
			MasterMainService.customupdateBySchema(mainEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
			return "FALIURE";
		}
		return "SUCCESS";

	}

	@RequestMapping(value = "/getLast15MintsDataMDAS", method = { RequestMethod.GET })
	public @ResponseBody Object getLast15MintsData(ModelMap model, HttpServletRequest request) {
		// System.out.println("################################## getDiagnosisAlerts ");

		String active = "SELECT "
				+ "(select count(*) from meter_data.instantaneous WHERE time_stamp >=localtimestamp -(15 ||' minutes')\\:\\:interval) as instCount, "
				+ "(select count(*) from meter_data.events WHERE time_stamp >=localtimestamp -(15 ||' minutes')\\:\\:interval) as eventCount, "
				+ "(select count(*) from meter_data.load_survey WHERE time_stamp >=localtimestamp -(15 ||' minutes')\\:\\:interval) as loadCount, "
				+ "(select count(*) from meter_data.bill_history WHERE time_stamp >=localtimestamp -(15 ||' minutes')\\:\\:interval) as billCount";

		// System.err.println("getLast15MintsData : "+active);
		List<Object[]> li = entityManager.createNativeQuery(active).getResultList();
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] columns : li) {
			Map<String, Object> obj = new HashMap<>();
			obj.put("instCount", (columns[0] == null ? 0 : columns[0]));
			obj.put("eventCount", (columns[1] == null ? 0 : columns[1]));
			obj.put("loadCount", (columns[2] == null ? 0 : columns[2]));
			obj.put("billCount", (columns[3] == null ? 0 : columns[3]));

			list.add(obj);
		}

		return list;
	}

	@RequestMapping(value = "/reasonforUnavailabilityofDataMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String reasonforUnavailabilityofData(ModelMap model, HttpServletRequest request)
			throws java.text.ParseException//
	{
		model.put("circleList", feederService.getDistinctCircle(request, model));
		return "unavailabilityReasonsMDAS";
	}

	@RequestMapping(value = "/updateMasterMeterReasonSubmitMDAS", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String updateMasterMeterReasonSubmit(HttpServletRequest request, ModelMap model) {
		String imei = request.getParameter("imei");
		String mtrno = request.getParameter("mtrno");
		String reason = request.getParameter("reason");
		MasterMainEntity mainEntity = MasterMainService.getEntityByImeiNoAndMtrNO(imei, mtrno);
		mainEntity.setNon_availabilityOf_data(reason);

		try {
			MasterMainService.customupdateBySchema(mainEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
			return "FALIURE";
		}
		return "SUCCESS";

	}

	@RequestMapping(value = "/updateMeterTypeMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateMeterType(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);
		return "updateMeterTypeMDAS";
	}

	@RequestMapping(value = "/updateMeterTypeSubmitMDAS", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String updateMeterTypeSubmit(HttpServletRequest request, ModelMap model) {
		String imei = request.getParameter("imei");
		String mtrno = request.getParameter("mtrno");

		String dlms = request.getParameter("dlms");
		String mtrmake = request.getParameter("mtrmake");
		String modelNo = request.getParameter("model");
		String tenderNo = request.getParameter("tenderNo");
		MasterMainEntity mainEntity = MasterMainService.getEntityByImeiNoAndMtrNO(imei, mtrno);

		mainEntity.setDlms(dlms);
		mainEntity.setMtrmake(mtrmake);
		mainEntity.setModel_no(modelNo);
		mainEntity.setTender_no(tenderNo);

		try {
			MasterMainService.customupdateBySchema(mainEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
			return "FALIURE";
		}
		return "SUCCESS";

	}

	@RequestMapping(value = "/dashBoard3MDAS", method = RequestMethod.GET)
	public String dashBoardNew(ModelMap model, HttpServletRequest request) {

		List<?> data = entityManager.createNativeQuery("SELECT * FROM meter_data.\"Dashboard3\" WHERE zone != 'TEST'")
				.getResultList();
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			String zone = object[0].toString();
			if ("DHBVN".equalsIgnoreCase(zone)) {

				int act33 = Integer.parseInt(String.valueOf(object[1]));
				int act11 = Integer.parseInt(String.valueOf(object[2]));
				int live11 = Integer.parseInt(String.valueOf(object[3]));
				int live33 = Integer.parseInt(String.valueOf(object[4]));
				int shut11 = Integer.parseInt(String.valueOf(object[5]));
				int shut33 = Integer.parseInt(String.valueOf(object[6]));

				model.addAttribute("dactTotal", (act33 + act11));
				model.addAttribute("dact11", (act11));
				model.addAttribute("dact33", (act33));
				model.addAttribute("dliveTotal", (live11 + live33));
				model.addAttribute("dlive11", (live11));
				model.addAttribute("dlive33", (live33));
				model.addAttribute("dshutTotal", (shut11 + shut33));
				model.addAttribute("dshut11", (shut11));
				model.addAttribute("dshut33", (shut33));
			} else {
				int act33 = Integer.parseInt(String.valueOf(object[1]));
				int act11 = Integer.parseInt(String.valueOf(object[2]));
				int live11 = Integer.parseInt(String.valueOf(object[3]));
				int live33 = Integer.parseInt(String.valueOf(object[4]));
				int shut11 = Integer.parseInt(String.valueOf(object[5]));
				int shut33 = Integer.parseInt(String.valueOf(object[6]));

				model.addAttribute("uactTotal", (act33 + act11));
				model.addAttribute("uact11", (act11));
				model.addAttribute("uact33", (act33));
				model.addAttribute("uliveTotal", (live11 + live33));
				model.addAttribute("ulive11", (live11));
				model.addAttribute("ulive33", (live33));
				model.addAttribute("ushutTotal", (shut11 + shut33));
				model.addAttribute("ushut11", (shut11));
				model.addAttribute("ushut33", (shut33));
			}

		}
		model.addAttribute("data", data);

		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

		Integer myn = Integer.parseInt(new SimpleDateFormat("YYYYMM").format(new Date()));
		Integer mynp = Integer.parseInt(new SimpleDateFormat("YYYYMM").format(new Date())) - 1;
		// String queryGetActiveStatus="SELECT DISTINCT imei, count(*) as count FROM
		// meter_data.modem_communication WHERE to_char(date, 'YYYYMM')='201801'
		// "+subLocationWhere+" GROUP BY imei HAVING count(*)>=27";

		String queryLocationAndCount = "SELECT  COUNT (DISTINCT mtrno) AS mtrCOUNT,COUNT (DISTINCT zone) AS ZONECOUNT, COUNT (DISTINCT circle) AS CIRCLECOUNT, COUNT (DISTINCT division) AS DIVISIONCOUNT, COUNT (DISTINCT subdivision) AS SUBDIVISIONCOUNT, COUNT (DISTINCT substation) AS SUBSTATIONCOUNT, "
				+ "COUNT(case when modem_sl_no is not null and modem_sl_no !='' then 1 end)  as throughModem, COUNT( CASE WHEN modem_sl_no IS NOT NULL AND modem_sl_no != '' AND dlms_old='DLMS' AND feeder_status='Feeder Live'THEN 1 END) AS dlmsLive FROM meter_data.master_main ";
		Query q = entityManager.createNativeQuery(queryLocationAndCount);
		List<Object> list = q.getResultList();

		System.out.println("queryLocationAndCount======" + queryLocationAndCount);
		int totalMeters = 0;
		int throughModem = 0;
		int dlmsLive = 0;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			totalMeters = Integer.parseInt(values[0].toString());
			throughModem = Integer.parseInt(values[6].toString());
			dlmsLive = Integer.parseInt(values[7].toString());
			int manual = totalMeters - throughModem;
			System.out.println(totalMeters + "  " + throughModem + " " + manual);
		}

		String queryGetActiveStatus = " SELECT( SELECT count(DISTINCT imei) AS imei FROM meter_data.modem_communication mc, meter_data.master_main m WHERE mc.imei=m.modem_sl_no AND to_char(mc.DATE, 'YYYYMM') = '"
				+ mynp
				+ "'), (SELECT COUNT (DISTINCT meter_number) FROM meter_data.xml_upload_status WHERE to_char((time_stamp-INTERVAL '1 day'), 'YYYYMM')='"
				+ mynp + "' AND fail_reason IS NULL)";
		System.err.println("GET ACTIVE COUNT : " + queryGetActiveStatus);
		Query q3 = entityManager.createNativeQuery(queryGetActiveStatus);
		List<Object[]> listActive = q3.getResultList();

		try {// TODO
			if (listActive == null || listActive.isEmpty()) {// TODO
				model.addAttribute("activeCount", "0");// TODO
				model.addAttribute("activeCompatiable", "0");// TODO
				model.addAttribute("uploadper", "0");// TODO
				model.addAttribute("activeper", "0");// TODO

			} else {

				for (Iterator<?> iterator = listActive.iterator(); iterator.hasNext();) {
					Object[] values = (Object[]) iterator.next();

					int total = new BigInteger(values[1].toString()).intValue();
					int activeCount = new BigInteger(values[0].toString()).intValue();
					int exportedCount = new BigInteger(values[1].toString()).intValue();
					model.addAttribute("activeCount", activeCount);
					model.addAttribute("uploadedCount", exportedCount);
					model.addAttribute("activeCompatiable", dlmsLive);// TODO
					model.addAttribute("activeper",
							Math.round((Double.parseDouble(values[0].toString())) / dlmsLive * 100));
					model.addAttribute("uploadper",
							Math.round((Double.parseDouble(values[1].toString())) / dlmsLive * 100));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * String
		 * queryGetMainUploadStatus="SELECT count(*) FROM meter_data.xml_upload_status WHERE to_char(file_date, 'YYYYMM')='201801' AND fail_reason IS NULL"
		 * ; System.err.println("GET UPLOAD COUNT : "+queryGetMainUploadStatus+(
		 * queryLocationAndCountWhere).replace("WHERE", " AND ")); Query queryUpload=
		 * entityManager.createNativeQuery((queryGetMainUploadStatus+(
		 * queryLocationAndCountWhere).replace("WHERE", " AND "))); List<?>
		 * li=queryUpload.getResultList();
		 * 
		 * try { if (li==null || li.isEmpty()) { model.addAttribute("uploadedCount",
		 * "0"); model.addAttribute("uploadper", "0");
		 * 
		 * } else { for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) {
		 * BigInteger values = (BigInteger) iterator.next();
		 * model.addAttribute("uploadedCount", values);
		 * model.addAttribute("uploadper",Math.round((Double.parseDouble(values+"")/
		 * throughModem)*100));
		 * 
		 * } } } catch (Exception e) { e.printStackTrace(); }
		 */

		String consp = "SELECT zone,sum(ls.kwh) as Consumption,sum(ls.kwh*(mf\\:\\:int)) as Consumption_with_MF from meter_data.load_survey ls, meter_data.master_main mm where mm.modem_sl_no=ls.imei AND to_char(ls.read_time,'yyyymm')='"
				+ myn + "' GROUP BY zone";
		List<?> conspList = entityManager.createNativeQuery(consp).getResultList();

		double consmp = 0;
		double consptTotal = 0;
		double consptHar = 0;
		double consptHim = 0;
		DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator iterator = conspList.iterator(); iterator.hasNext();) {
			Object[] vaallue = (Object[]) iterator.next();

			consmp += Double.parseDouble(vaallue[1].toString());
			consptTotal += Double.parseDouble(vaallue[2].toString());
			if (vaallue[0].toString().equalsIgnoreCase("DHBVN") || vaallue[0].toString().equalsIgnoreCase("UHBVN")) {
				consptHar += Double.parseDouble(vaallue[1].toString());
			} else {
				consptHim += Double.parseDouble(vaallue[1].toString());
			}
			model.addAttribute("consmp", df.format(consmp / 1000));
			model.addAttribute("consTotal", df.format(consptTotal / 1000));
			model.addAttribute("consHar", df.format(consptHar / 1000));
			model.addAttribute("consHim", df.format(consptHim / 1000));
		}

		model.addAttribute("dhOff", dhOff);
		model.addAttribute("dhOn", dhOn);
		model.addAttribute("dhp1", dhp1);
		model.addAttribute("dhp3", dhp3);

		model.addAttribute("uhOff", uhOff);
		model.addAttribute("uhOn", uhOn);
		model.addAttribute("uhp1", uhp1);
		model.addAttribute("uhp3", uhp3);

		model.addAttribute("allOff", sallOff);
		model.addAttribute("allOn", sallOn);
		model.addAttribute("allp1", sallp1);
		model.addAttribute("allp3", sallp3);
		model.addAttribute("last15communicated", last15Communicated);

		/*
		 * String powerStatusqry="SELECT m.zone, count(A.mtrno) as total, " +
		 * "count(DISTINCT(CASE WHEN A.i_r=0 AND A.i_y=0 AND A.i_b=0 THEN A.mtrno END)) as powerOff, "
		 * +
		 * "count(DISTINCT(CASE WHEN A.i_r!=0 AND A.i_y!=0 AND A.i_b!=0 THEN A.mtrno END)) as threePhase "
		 * +
		 * "FROM ( SELECT meter_number as mtrno,i_r,i_y, i_b FROM meter_data.events WHERE id in "
		 * +
		 * "( SELECT max(id) as maxid FROM meter_data.events GROUP BY meter_number) )A, "
		 * +
		 * "meter_data.master_main m WHERE A.mtrno=m.mtrno AND m.zone!='TEST' GROUP BY m.zone"
		 * ;
		 * 
		 * List<?> li=entityManager.createNativeQuery(powerStatusqry).getResultList();
		 * System.out.println("powerStatusqry==== "+powerStatusqry); try { if (li==null
		 * || li.isEmpty()) {
		 * 
		 * 
		 * } else { int allTot=0; int alloff=0; int allOn=0; int allp1=0; int allp3=0;
		 * for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) { Object[]
		 * values = (Object[]) iterator.next(); String zone=String.valueOf(values[0]);
		 * int total=new BigInteger(values[1].toString()).intValue(); int poweroff=new
		 * BigInteger(values[2].toString()).intValue(); int powerOn=total-poweroff; int
		 * phase3=new BigInteger(values[3].toString()).intValue(); int
		 * phase1=powerOn-phase3; allTot+=total; alloff+=poweroff; allOn+=powerOn;
		 * allp1+=phase1; allp3+=phase3;
		 * 
		 * if("DHBVN".equalsIgnoreCase(zone)) { model.addAttribute("dhOff",poweroff);
		 * model.addAttribute("dhOn",powerOn); model.addAttribute("dhp1",phase1);
		 * model.addAttribute("dhp3",phase3); } else if("UHBVN".equalsIgnoreCase(zone))
		 * { model.addAttribute("uhOff",poweroff); model.addAttribute("uhOn",powerOn);
		 * model.addAttribute("uhp1",phase1); model.addAttribute("uhp3",phase3); }
		 * 
		 * 
		 * } model.addAttribute("allOff",alloff); model.addAttribute("allOn",allOn);
		 * model.addAttribute("allp1",allp1); model.addAttribute("allp3",allp3); } }
		 * catch (Exception e) { e.printStackTrace(); }
		 */

		return "dashBoardNewMDAS";

	}

	@RequestMapping(value = "/getRealTimeDataDashBoardMDAS", method = { RequestMethod.GET })
	public @ResponseBody Object getRealTimeDataDashBoard(HttpServletRequest request) {
		String powerStatusqry = "SELECT m.zone, count(A.mtrno) as total, "
				+ "count(DISTINCT(CASE WHEN A.i_r=0 AND A.i_y=0 AND A.i_b=0 THEN A.mtrno END)) as powerOff, "
				+ "count(DISTINCT(CASE WHEN A.i_r!=0 AND A.i_y!=0 AND A.i_b!=0 THEN A.mtrno END)) as threePhase "
				+ "FROM ( SELECT meter_number as mtrno,i_r,i_y, i_b FROM meter_data.events WHERE id in "
				+ "( SELECT max(id) as maxid FROM meter_data.events GROUP BY meter_number) )A, "
				+ "meter_data.master_main m WHERE A.mtrno=m.mtrno AND m.zone!='TEST' GROUP BY m.zone";

		String l15com = "SELECT COUNT(*) FROM meter_data.modem_communication WHERE last_communication >= LOCALTIMESTAMP - (15 || ' minutes') \\:\\: INTERVAL";
		Integer l15comCount = Integer.parseInt(entityManager.createNativeQuery(l15com).getSingleResult() + "");
		List<?> li = entityManager.createNativeQuery(powerStatusqry).getResultList();
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		try {
			if (li == null || li.isEmpty()) {

			} else {
				int allTot = 0;
				int alloff = 0;
				int allOn = 0;
				int allp1 = 0;
				int allp3 = 0;
				for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) {
					Object[] values = (Object[]) iterator.next();
					String zone = String.valueOf(values[0]);
					int total = new BigInteger(values[1].toString()).intValue();
					int poweroff = new BigInteger(values[2].toString()).intValue();
					int powerOn = total - poweroff;
					int phase3 = new BigInteger(values[3].toString()).intValue();
					int phase1 = powerOn - phase3;
					allTot += total;
					alloff += poweroff;
					allOn += powerOn;
					allp1 += phase1;
					allp3 += phase3;

					if ("DHBVN".equalsIgnoreCase(zone)) {
						dhOff = poweroff;
						dhOn = powerOn;
						dhp1 = phase1;
						dhp3 = phase3;

						map.put("dhOff", poweroff);
						map.put("dhOn", powerOn);
						map.put("dhp1", phase1);
						map.put("dhp3", phase3);
					} else if ("UHBVN".equalsIgnoreCase(zone)) {
						uhOff = poweroff;
						uhOn = powerOn;
						uhp1 = phase1;
						uhp3 = phase3;
						map.put("uhOff", poweroff);
						map.put("uhOn", powerOn);
						map.put("uhp1", phase1);
						map.put("uhp3", phase3);
					}

				}
				sallOff = alloff;
				sallOn = allOn;
				sallp1 = allp1;
				sallp3 = allp3;
				last15Communicated = l15comCount;
				map.put("allOff", alloff);
				map.put("allOn", allOn);
				map.put("allp1", allp1);
				map.put("allp3", allp3);
				map.put("last15communicated", l15comCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		list.add(map);
		return list;

	}

	@RequestMapping(value = "/changeModemMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String changeModem(@ModelAttribute("changeModemDetails") ChangeModemDetailsEntity changeModemDetails,
			ModelMap model, HttpServletRequest request, BindingResult bindingResult) {
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);

		return "changeModemMDAS";
	}

	@RequestMapping(value = "/checkUniqueIMEINoMDAS/{imei}", method = { RequestMethod.GET })
	public @ResponseBody String checkUniqueIMEINo(@PathVariable("imei") String imei, HttpServletRequest request) {
		System.out.println("IMEI : " + imei);

		List<MasterMainEntity> list = MasterMainService.getFeedersBasedOnUniqueImei(imei);
		System.out.println("SIZE : " + list.size());
		if (list.size() == 0) {
			return "NOTEXIST";
		} else {
			return "EXIST";
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/changeModemImeiNoMDAS", method = { RequestMethod.POST })
	public @ResponseBody String changeModemImeiNo(
			@ModelAttribute("changeModemDetails") ChangeModemDetailsEntity changeModemDetails,
			HttpServletRequest request, ModelMap model) {
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		String zone = request.getParameter("zoneN");
		String circle = request.getParameter("circleN");
		String division = request.getParameter("divisionN");
		String subdivision = request.getParameter("subdivisionN");
		changeModemDetails.setZone(zone);
		changeModemDetails.setCircle(circle);
		changeModemDetails.setDivision(division);
		changeModemDetails.setSubdivision(subdivision);
		changeModemDetails.setCreated_by(username);
		changeModemDetails.setCreated_date(new Date());

		System.out.println(changeModemDetails);
		try {
			changeModemService.customsaveBySchema(changeModemDetails, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILURE";
		}
		MasterMainEntity mainEntity = MasterMainService.getEntityByImeiNoAndMtrNO(changeModemDetails.getOld_imei_no(),
				changeModemDetails.getMtr_no());

		mainEntity.setModem_sl_no(changeModemDetails.getNew_imei_no());

		try {
			MasterMainService.customupdateBySchema(mainEntity, "postgresMdas");
		} catch (Exception e) {
			e.printStackTrace();
			return "FALIURE";
		}

		try {
			ModemLifeCycleEntity cycleEntity = new ModemLifeCycleEntity();
			cycleEntity.setImei(changeModemDetails.getNew_imei_no());
			cycleEntity.setLocation(zone + ">" + circle + ">" + division + ">" + subdivision);
			cycleEntity.setEvents("Modem No : " + changeModemDetails.getOld_imei_no()
					+ " replaced With this modem, in Meter no : " + changeModemDetails.getMtr_no());
			cycleEntity.setEdate(new Date());
			modemLifeCycleService.customsaveBySchema(cycleEntity, "postgresMdas");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "SUCCESS";

	}

	/*
	 * @RequestMapping(value="/cdfImportManagerMDAS",method={RequestMethod.GET,
	 * RequestMethod.POST}) public String cdfImportManager(HttpServletRequest
	 * request,Model model) { model.addAttribute("result","notDisplay"); return
	 * "cdfimportmanagerMDAS"; }
	 */

	@RequestMapping(value = "/modeminventoryMDAS", method = { RequestMethod.GET })
	public String getmodeminventory(Model model) {
		System.out.println("-------------------inside ModemController.getmodeminventory()-------------------");
		Object[] modem = MasterMainService.getAllModemDetails();
//		System.err.println("modem------>" + modem);
		model.addAttribute("total", modem[0]);
		model.addAttribute("installed", modem[1]);
		model.addAttribute("instock", modem[2]);
		model.addAttribute("notWorking", modem[3]);
		return "inventoryMDAS";
	}

	@RequestMapping(value = "/getModemMasterDataMDAS/{val}", method = { RequestMethod.GET })
	public @ResponseBody Object getModemMasterData(@PathVariable("val") int val, HttpServletRequest request) {
		List<?> li = null;
		String qry = "SELECT * FROM meter_data.modem_master ";

		if (val == 0) {
			qry += "";
		} else if (val == 1) {
			qry += "WHERE installed = '1' ";
		} else if (val == 2) {
			qry += "WHERE installed = '0' ";
		} else if (val == 3) {
			qry += "WHERE working_status = '0' ";
		}
		qry += " ORDER BY modem_serial_no";
		System.out.println(qry);
		try {
			li = entityManager.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	}

	@RequestMapping(value = "/modemMasterMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String modemMaster(@ModelAttribute("modemMaster") ModemMasterEntity masterEntity, HttpServletRequest request,
			Model model) {
		/* model.addAttribute("result","notDisplay"); */

		if (createModemFlag == 1) {
			model.addAttribute("msg", "Modem Master Added Successfully.");
			createModemFlag = 0;
		} else if (createModemFlag == 2) {
			model.addAttribute("msg", "Modem Master Updated Successfully.");
			createModemFlag = 0;
		} else if (createModemFlag == 3) {
			model.addAttribute("msg", "OOPs!! Something went wrong. Please try again later.");
			createModemFlag = 0;
		}

		return "modemMasterMDAS";
	}

	@RequestMapping(value = "/addNewModemMasterMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String addNewModemMaster(@ModelAttribute("modemMaster") ModemMasterEntity masterEntity,
			BindingResult bingingResult, ModelMap model, HttpServletRequest request) {

		try {
			/*
			 * ObjectMapper objectMapper = new ObjectMapper(); String obj =
			 * objectMapper.writeValueAsString(masterEntity); System.out.println(obj);
			 */
			modemMasterService.customsaveBySchema(masterEntity, "postgresMdas");
			createModemFlag = 1;
		} catch (Exception e) {
			e.printStackTrace();
			createModemFlag = 3;
		}

		try {
			ModemLifeCycleEntity cycleEntity = new ModemLifeCycleEntity();
			cycleEntity.setImei(masterEntity.getModem_imei());
			cycleEntity.setLocation("");
			cycleEntity.setEvents("Added In Inventory.");
			cycleEntity.setEdate(new Date());
			modemLifeCycleService.customsaveBySchema(cycleEntity, "postgresMdas");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/modemMasterMDAS";

	}

	@RequestMapping(value = "/updateNewModemMasterMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateNewModemMaster(@ModelAttribute("modemMaster") ModemMasterEntity masterEntity,
			BindingResult bingingResult, ModelMap model, HttpServletRequest request) {

		try {
			modemMasterService.customupdateBySchema(masterEntity, "postgresMdas");
			createModemFlag = 2;
		} catch (Exception e) {
			e.printStackTrace();
			createModemFlag = 3;
		}

		return "redirect:/modemMasterMDAS";

	}

	@RequestMapping(value = "/checkImeiNoInModemMasterMDAS/{imei}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object checkImeiNoInModemMaster(@PathVariable("imei") String imei,
			HttpServletRequest request) {
		return modemMasterService.getEntityByImei(imei);

	}

	@RequestMapping(value = "/modemLifeCycleMDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public String modemLifeCycle(@ModelAttribute("modemMaster") ModemMasterEntity masterEntity,
			HttpServletRequest request, Model model) {
		/* model.addAttribute("result","notDisplay"); */

		/*
		 * if(createModemFlag==1) {
		 * model.addAttribute("msg","Modem Master Added Successfully.");
		 * createModemFlag=0; } else if(createModemFlag==2) {
		 * model.addAttribute("msg","Modem Master Updated Successfully.");
		 * createModemFlag=0; } else if(createModemFlag==3) { model.addAttribute(
		 * "msg","OOPs!! Something went wrong. Please try again later.");
		 * createModemFlag=0; }
		 */

		return "modemLifeCycleMDAS";
	}

	@RequestMapping(value = "/getLifeCycleDataByImeiMDAS/{imei}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getLifeCycleDataByImei(@PathVariable("imei") String imei, HttpServletRequest request) {
		return modemLifeCycleService.getLifeCycleDataByImei(imei);
	}

	// THE METER CHANGE MODULE
	@RequestMapping(value = "/meterChangeScreen", method = { RequestMethod.GET, RequestMethod.POST })
	public String meterChangeScreen(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "MeterChange";
	}

	@RequestMapping(value = "/changingMeter", method = { RequestMethod.GET, RequestMethod.POST })
	public String changingMeter(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String oldMeterno = request.getParameter("oldMeterno");
		String newMeterNo = request.getParameter("newMeterNo");
		System.err.println("====>" + newMeterNo);
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 0);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int existingFlag = MasterMainService.checkMeterExist(newMeterNo);
		if (existingFlag == 0) {
			// NEED TO UPDATE IN MASTER MAIN
			List<MasterMainEntity> mainEntity = MasterMainService.getOldMeterData(oldMeterno);

			MasterMainEntity masterMain = mainEntity.get(0);
			masterMain.setMtrno(newMeterNo);
			masterMain.setOld_mtr_no(oldMeterno);
			masterMain.setMtr_change_date(dateFormat.format(c.getTime()));
			MasterMainEntity masterMainUpdated = MasterMainService.customupdatemdas(masterMain);

			// NEED TO INSERT IN METER CHANGE
			MeterChange mtrChange = new MeterChange();
			mtrChange.setZone(masterMainUpdated.getZone());
			mtrChange.setCircle(masterMainUpdated.getCircle());
			mtrChange.setDivision(masterMainUpdated.getDivision());
			mtrChange.setSubdivision(masterMainUpdated.getSubdivision());
			mtrChange.setSubstation(masterMain.getSubstation());
			mtrChange.setConsumer_name(masterMain.getCustomer_name());
			mtrChange.setNew_mtr_no(masterMainUpdated.getMtrno());
			mtrChange.setold_mtr_no(masterMainUpdated.getOld_mtr_no());
			String username = session.getAttribute("username").toString();
			mtrChange.setCreated_by(username);
			mtrChange.setCreated_date(dt);
			meterChangeService.customupdatemdas(mtrChange);
			model.addAttribute("results", "METER DETAILS UPDATED SUCCESSFULLY..");
		} else {
			model.addAttribute("results", "METER ALREADY EXISTS FOR OTHER CONSUMER!!!!");
		}
		// NEED TO GET THE UPDATE DATA BACK

		return "MeterChange";
	}

	// FOR DOWNLOADING THE PDFS
	@RequestMapping(value = "/downloadPdfs/{radioValue}", method = { RequestMethod.GET, RequestMethod.POST })
	public String exportingDataPDF(@PathVariable String radioValue, ModelMap model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, @RequestParam String key, @RequestParam String meterNo) {

		if ("instant".equalsIgnoreCase(key)) {
			List<AmrInstantaneousEntity> instansData = amrInstantaneousService.getCompleteInstansData(meterNo,
					radioValue);
			List<MasterMainEntity> mainEntity = MasterMainService.getOldMeterData(meterNo);
			MasterMainEntity mainEntity1 = null;
			try {

				mainEntity1 = mainEntity.get(0);

			} catch (Exception e) {
				e.printStackTrace();
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");

			try {

				Rectangle pageSize = new Rectangle(720, 1050);
				Document document = new Document(pageSize);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);

				document.open();

				// InputStream inputStream =
				// this.getClass().getClassLoader().getResourceAsStream("files/bcitslogo1.png");
				// byte[] bytes = IOUtils.toByteArray(inputStream);
				// Image img1 = Image.getInstance(bytes);

				/*
				 * PdfContentByte content1 = pdfWriter.getDirectContent(); Rectangle pageRect =
				 * document.getPageSize(); pageRect.setLeft(pageRect.getLeft() + 10);
				 * pageRect.setRight(pageRect.getRight() - 10);
				 * pageRect.setTop(pageRect.getTop() - 10);
				 * pageRect.setBottom(pageRect.getBottom() +10);
				 * 
				 * content.setColorStroke( BaseColor.GRAY);
				 * content.rectangle(pageRect.getLeft(), pageRect.getBottom(),
				 * pageRect.getWidth(), pageRect.getHeight()); content.setLineWidth(3);
				 * content.stroke(); content.fillStroke();
				 */

				Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
				Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
				PdfPTable pdf1 = new PdfPTable(1);
				pdf1.setWidthPercentage(100); // percentage
				pdf1.getDefaultCell().setPadding(3);
				pdf1.getDefaultCell().setBorderWidth(0);
				pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

				PdfPTable pdf2 = new PdfPTable(1);
				pdf2.setWidthPercentage(100); // percentage
				pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				Chunk glue = new Chunk(new VerticalPositionMark());
				PdfPCell cell1 = new PdfPCell();
				Paragraph pstart = new Paragraph();
				pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.addElement(pstart);
				pdf2.addCell(cell1);
				pstart.add(new Chunk(glue));
				// pstart.add(new Phrase("Reading Month : "+new
				// SimpleDateFormat("MMM-YYYY").format(new
				// SimpleDateFormat("yyyyMM").parse(month)),new Font(Font.FontFamily.HELVETICA
				// ,13, Font.BOLD)));
				document.add(pdf2);
				PdfPCell cell2 = new PdfPCell();
				Paragraph p1 = new Paragraph();
				p1.add(new Phrase("INSTANTANEOUS PARAMETERS :  " + mainEntity1.getMtrno(),
						new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
				p1.setAlignment(Element.ALIGN_CENTER);
				cell2.addElement(p1);
				cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
				pdf1.addCell(cell2);
				document.add(pdf1);

				PdfPTable table = new PdfPTable(2);
				table.setTotalWidth(100);
				table.setLockedWidth(false);
				float headerwidths1[] = { 50f, 50f };
				float headerwidths2[] = { 25f, 25f, 25f, 25f };
				float headerwidths3[] = { 50f, 25f, 25f };
				float headerwidths4[] = { 100f };

				DecimalFormat decFormat = new DecimalFormat("##.##");

				PdfPTable header = new PdfPTable(4);
				header.setWidthPercentage(100);
				header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
				header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
				PdfPCell headerCell = null;

				headerCell = new PdfPCell(
						new Phrase("Account No :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(mainEntity1.getAccno(), PdfPCell.ALIGN_LEFT));

				headerCell = new PdfPCell(
						new Phrase("Consumer Name :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(mainEntity1.getCustomer_name(), PdfPCell.ALIGN_LEFT));

				headerCell = new PdfPCell(new Phrase("Circle :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(mainEntity1.getCircle(), PdfPCell.ALIGN_LEFT));

				headerCell = new PdfPCell(new Phrase("Division :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				// header.addCell(getCell("Division :", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(mainEntity1.getDivision(), PdfPCell.ALIGN_LEFT));

				headerCell = new PdfPCell(new Phrase("Address :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				// header.addCell(getCell("Address :", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(mainEntity1.getCustomer_address(), PdfPCell.ALIGN_LEFT));

				headerCell = new PdfPCell(
						new Phrase("Sub-Division :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				// header.addCell(getCell("Sub-Division :", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(mainEntity1.getSubdivision(), PdfPCell.ALIGN_LEFT));

				headerCell = new PdfPCell(new Phrase("Meter No :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				// header.addCell(getCell("Meter No :", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(mainEntity1.getMtrno(), PdfPCell.ALIGN_LEFT));

				/*
				 * headerCell = new PdfPCell(new Phrase("CD :",new
				 * Font(Font.FontFamily.HELVETICA ,13, Font.BOLD)));
				 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
				 * header.addCell(headerCell); //header.addCell(getCell("CD :",
				 * PdfPCell.ALIGN_RIGHT)); header.addCell(getCell(mainEntity1.getCt_ratio(),
				 * PdfPCell.ALIGN_LEFT));
				 */

				headerCell = new PdfPCell(new Phrase("MF :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				// header.addCell(getCell("MF :", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(mainEntity1.getMf(), PdfPCell.ALIGN_LEFT));

				header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
				header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
				header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
				document.add(header);

				PdfPTable header2 = new PdfPTable(4);
				header2.setWidthPercentage(100);
				header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
				header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
				header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
				header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
				header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
				header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
				header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
				header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
				document.add(header2);

				PdfPTable datatable10 = new PdfPTable(2);
				datatable10.setWidths(headerwidths1);
				datatable10.setWidthPercentage(100); // percentage
				datatable10.getDefaultCell().setPadding(3);
				datatable10.getDefaultCell().setBorderWidth(1);
				datatable10.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				document.add(datatable10);

				for (Object obj1 : instansData) {
					Object[] obj = (Object[]) obj1;
					System.err.println("TAMPER VAL====>" + obj[36].toString());
					Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[2].toString());
					Date date2 = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss a").parse(obj[37].toString());
					// System.out.println("=======>"+"\t"+sdf.format(date1));
					// AmrInstantaneousEntity obj=instansData.get(i);

					PdfPTable datatable53 = new PdfPTable(2);
					datatable53.setTotalWidth(300);
					datatable53.setLockedWidth(false);
					datatable53.getAbsoluteWidths();
					datatable53.setWidths(headerwidths1);
					datatable53.setWidthPercentage(100); // percentage
					datatable53.getDefaultCell().setPadding(3);
					datatable53.getDefaultCell().setBorderWidth(1);
					datatable53.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable53.addCell("Date and time");
					datatable53.addCell(sdf.format(date1));
					document.add(datatable53);

					PdfPTable datatable54 = new PdfPTable(2);
					datatable54.setTotalWidth(300);
					datatable54.setLockedWidth(false);
					datatable54.getAbsoluteWidths();
					datatable54.setWidths(headerwidths1);
					datatable54.setWidthPercentage(100); // percentage
					datatable54.getDefaultCell().setPadding(3);
					datatable54.getDefaultCell().setBorderWidth(1);
					datatable54.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable54.addCell("Meter No");
					datatable54.addCell(mainEntity1.getMtrno());
					document.add(datatable54);

					/*
					 * PdfPTable datatable55 = new PdfPTable(2); datatable55.setTotalWidth(300);
					 * datatable55.setLockedWidth(false); datatable55.getAbsoluteWidths();
					 * datatable55.setWidths(headerwidths1); datatable55.setWidthPercentage(100); //
					 * percentage datatable55.getDefaultCell().setPadding(3);
					 * datatable55.getDefaultCell().setBorderWidth(1);
					 * datatable55.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					 * datatable55.addCell("IMEI NO"); datatable55.addCell(obj[6].toString());
					 * document.add(datatable55);
					 */

					PdfPTable datatable56 = new PdfPTable(2);
					datatable56.setTotalWidth(300);
					datatable56.setLockedWidth(false);
					datatable56.getAbsoluteWidths();
					datatable56.setWidths(headerwidths1);
					datatable56.setWidthPercentage(100); // percentage
					datatable56.getDefaultCell().setPadding(3);
					datatable56.getDefaultCell().setBorderWidth(1);
					datatable56.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable56.addCell("KWH");
					datatable56.addCell(decFormat.format(Double.parseDouble(obj[7].toString())));
					document.add(datatable56);

					PdfPTable datatable57 = new PdfPTable(2);
					datatable57.setTotalWidth(300);
					datatable57.setLockedWidth(false);
					datatable57.getAbsoluteWidths();
					datatable57.setWidths(headerwidths1);
					datatable57.setWidthPercentage(100); // percentage
					datatable57.getDefaultCell().setPadding(3);
					datatable57.getDefaultCell().setBorderWidth(1);
					datatable57.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable57.addCell("KVAH");
					datatable57.addCell(decFormat.format(Double.parseDouble(obj[8].toString())));
					document.add(datatable57);

					PdfPTable datatable58 = new PdfPTable(2);
					datatable58.setTotalWidth(300);
					datatable58.setLockedWidth(false);
					datatable58.getAbsoluteWidths();
					datatable58.setWidths(headerwidths1);
					datatable58.setWidthPercentage(100); // percentage
					datatable58.getDefaultCell().setPadding(3);
					datatable58.getDefaultCell().setBorderWidth(1);
					datatable58.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable58.addCell("KVA");
					datatable58.addCell(decFormat.format(Double.parseDouble(obj[9].toString())));
					document.add(datatable58);

					PdfPTable datatable62 = new PdfPTable(2);
					datatable62.setTotalWidth(300);
					datatable62.setLockedWidth(false);
					datatable62.getAbsoluteWidths();
					datatable62.setWidths(headerwidths1);
					datatable62.setWidthPercentage(100); // percentage
					datatable62.getDefaultCell().setPadding(3);
					datatable62.getDefaultCell().setBorderWidth(1);
					datatable62.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable62.addCell("Crrent R-Phase");
					datatable62.addCell(decFormat.format(Double.parseDouble(obj[13].toString())));
					document.add(datatable62);

					PdfPTable datatable63 = new PdfPTable(2);
					datatable63.setTotalWidth(300);
					datatable63.setLockedWidth(false);
					datatable63.getAbsoluteWidths();
					datatable63.setWidths(headerwidths1);
					datatable63.setWidthPercentage(100); // percentage
					datatable63.getDefaultCell().setPadding(3);
					datatable63.getDefaultCell().setBorderWidth(1);
					datatable63.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable63.addCell("Current Y-Phase");
					datatable63.addCell(decFormat.format(Double.parseDouble(obj[14].toString())));
					document.add(datatable63);

					PdfPTable datatable64 = new PdfPTable(2);
					datatable64.setTotalWidth(300);
					datatable64.setLockedWidth(false);
					datatable64.getAbsoluteWidths();
					datatable64.setWidths(headerwidths1);
					datatable64.setWidthPercentage(100); // percentage
					datatable64.getDefaultCell().setPadding(3);
					datatable64.getDefaultCell().setBorderWidth(1);
					datatable64.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable64.addCell("Current B-Phase");
					datatable64.addCell(decFormat.format(Double.parseDouble(obj[15].toString())));
					document.add(datatable64);

					PdfPTable datatable65 = new PdfPTable(2);
					datatable65.setTotalWidth(300);
					datatable65.setLockedWidth(false);
					datatable65.getAbsoluteWidths();
					datatable65.setWidths(headerwidths1);
					datatable65.setWidthPercentage(100); // percentage
					datatable65.getDefaultCell().setPadding(3);
					datatable65.getDefaultCell().setBorderWidth(1);
					datatable65.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable65.addCell("Voltage R-Phase");
					datatable65.addCell(decFormat.format(Double.parseDouble(obj[16].toString())));
					document.add(datatable65);

					PdfPTable datatable66 = new PdfPTable(2);
					datatable66.setTotalWidth(300);
					datatable66.setLockedWidth(false);
					datatable66.getAbsoluteWidths();
					datatable66.setWidths(headerwidths1);
					datatable66.setWidthPercentage(100); // percentage
					datatable66.getDefaultCell().setPadding(3);
					datatable66.getDefaultCell().setBorderWidth(1);
					datatable66.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable66.addCell("Voltage Y-Phase");
					datatable66.addCell(decFormat.format(Double.parseDouble(obj[17].toString())));
					document.add(datatable66);

					PdfPTable datatable67 = new PdfPTable(2);
					datatable67.setTotalWidth(300);
					datatable67.setLockedWidth(false);
					datatable67.getAbsoluteWidths();
					datatable67.setWidths(headerwidths1);
					datatable67.setWidthPercentage(100); // percentage
					datatable67.getDefaultCell().setPadding(3);
					datatable67.getDefaultCell().setBorderWidth(1);
					datatable67.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable67.addCell("Voltage B-Phase");
					datatable67.addCell(decFormat.format(Double.parseDouble(obj[18].toString())));
					document.add(datatable67);

					PdfPTable datatable68 = new PdfPTable(2);
					datatable68.setTotalWidth(300);
					datatable68.setLockedWidth(false);
					datatable68.getAbsoluteWidths();
					datatable68.setWidths(headerwidths1);
					datatable68.setWidthPercentage(100); // percentage
					datatable68.getDefaultCell().setPadding(3);
					datatable68.getDefaultCell().setBorderWidth(1);
					datatable68.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable68.addCell("Power factor R-Phase");
					datatable68.addCell(decFormat.format(Double.parseDouble(obj[19].toString())));
					document.add(datatable68);

					PdfPTable datatable69 = new PdfPTable(2);
					datatable69.setTotalWidth(300);
					datatable69.setLockedWidth(false);
					datatable69.getAbsoluteWidths();
					datatable69.setWidths(headerwidths1);
					datatable69.setWidthPercentage(100); // percentage
					datatable69.getDefaultCell().setPadding(3);
					datatable69.getDefaultCell().setBorderWidth(1);
					datatable69.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable69.addCell("Power factor Y-Phase");
					datatable69.addCell(decFormat.format(Double.parseDouble(obj[20].toString())));
					document.add(datatable69);

					PdfPTable datatable70 = new PdfPTable(2);
					datatable70.setTotalWidth(300);
					datatable70.setLockedWidth(false);
					datatable70.getAbsoluteWidths();
					datatable70.setWidths(headerwidths1);
					datatable70.setWidthPercentage(100); // percentage
					datatable70.getDefaultCell().setPadding(3);
					datatable70.getDefaultCell().setBorderWidth(1);
					datatable70.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable70.addCell("Power factor B-Phase");
					datatable70.addCell(decFormat.format(Double.parseDouble(obj[21].toString())));
					document.add(datatable70);

					PdfPTable datatable71 = new PdfPTable(2);
					datatable71.setTotalWidth(300);
					datatable71.setLockedWidth(false);
					datatable71.getAbsoluteWidths();
					datatable71.setWidths(headerwidths1);
					datatable71.setWidthPercentage(100); // percentage
					datatable71.getDefaultCell().setPadding(3);
					datatable71.getDefaultCell().setBorderWidth(1);
					datatable71.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable71.addCell("Power factor three phase");
					datatable71.addCell(decFormat.format(Double.parseDouble(obj[22].toString())));
					document.add(datatable71);

					PdfPTable datatablefreq = new PdfPTable(2);
					datatablefreq.setTotalWidth(300);
					datatablefreq.setLockedWidth(false);
					datatablefreq.getAbsoluteWidths();
					datatablefreq.setWidths(headerwidths1);
					datatablefreq.setWidthPercentage(100); // percentage
					datatablefreq.getDefaultCell().setPadding(3);
					datatablefreq.getDefaultCell().setBorderWidth(1);
					datatablefreq.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatablefreq.addCell("Frequency");
					datatablefreq.addCell(decFormat.format(Double.parseDouble(obj[23].toString())));
					document.add(datatablefreq);

					PdfPTable datatable72 = new PdfPTable(2);
					datatable72.setTotalWidth(300);
					datatable72.setLockedWidth(false);
					datatable72.getAbsoluteWidths();
					datatable72.setWidths(headerwidths1);
					datatable72.setWidthPercentage(100); // percentage
					datatable72.getDefaultCell().setPadding(3);
					datatable72.getDefaultCell().setBorderWidth(1);
					datatable72.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable72.addCell("Power_Kw");
					datatable72.addCell(decFormat.format(Double.parseDouble(obj[28].toString())));
					document.add(datatable72);

					PdfPTable datatable73 = new PdfPTable(2);
					datatable73.setTotalWidth(300);
					datatable73.setLockedWidth(false);
					datatable73.getAbsoluteWidths();
					datatable73.setWidths(headerwidths1);
					datatable73.setWidthPercentage(100); // percentage
					datatable73.getDefaultCell().setPadding(3);
					datatable73.getDefaultCell().setBorderWidth(1);
					datatable73.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable73.addCell("Kvar");
					datatable73.addCell(decFormat.format(Double.parseDouble(obj[29].toString())));
					document.add(datatable73);

					PdfPTable datatable74 = new PdfPTable(2);
					datatable74.setTotalWidth(300);
					datatable74.setLockedWidth(false);
					datatable74.getAbsoluteWidths();
					datatable74.setWidths(headerwidths1);
					datatable74.setWidthPercentage(100); // percentage
					datatable74.getDefaultCell().setPadding(3);
					datatable74.getDefaultCell().setBorderWidth(1);
					datatable74.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable74.addCell("Kvarh_lag");
					datatable74.addCell(decFormat.format(Double.parseDouble(obj[30].toString())));
					document.add(datatable74);

					PdfPTable datatable75 = new PdfPTable(2);
					datatable75.setTotalWidth(300);
					datatable75.setLockedWidth(false);
					datatable75.getAbsoluteWidths();
					datatable75.setWidths(headerwidths1);
					datatable75.setWidthPercentage(100); // percentage
					datatable75.getDefaultCell().setPadding(3);
					datatable75.getDefaultCell().setBorderWidth(1);
					datatable75.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable75.addCell("Kvarh_lead");
					datatable75.addCell(decFormat.format(Double.parseDouble(obj[31].toString())));
					document.add(datatable75);

					PdfPTable datatable76 = new PdfPTable(2);
					datatable76.setTotalWidth(300);
					datatable76.setLockedWidth(false);
					datatable76.getAbsoluteWidths();
					datatable76.setWidths(headerwidths1);
					datatable76.setWidthPercentage(100); // percentage
					datatable76.getDefaultCell().setPadding(3);
					datatable76.getDefaultCell().setBorderWidth(1);
					datatable76.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable76.addCell("Power off count");
					datatable76.addCell((int) Double.parseDouble(String.valueOf(obj[32])) + "");
					document.add(datatable76);

					PdfPTable datatable77 = new PdfPTable(2);
					datatable77.setTotalWidth(300);
					datatable77.setLockedWidth(false);
					datatable77.getAbsoluteWidths();
					datatable77.setWidths(headerwidths1);
					datatable77.setWidthPercentage(100); // percentage
					datatable77.getDefaultCell().setPadding(3);
					datatable77.getDefaultCell().setBorderWidth(1);
					datatable77.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable77.addCell("Power off duration");
					datatable77.addCell((int) Double.parseDouble(obj[33].toString()) + "");
					document.add(datatable77);

					PdfPTable datatable78 = new PdfPTable(2);
					datatable78.setTotalWidth(300);
					datatable78.setLockedWidth(false);
					datatable78.getAbsoluteWidths();
					datatable78.setWidths(headerwidths1);
					datatable78.setWidthPercentage(100); // percentage
					datatable78.getDefaultCell().setPadding(3);
					datatable78.getDefaultCell().setBorderWidth(1);
					datatable78.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable78.addCell("Md reset count");
					datatable78.addCell((int) Double.parseDouble(String.valueOf(obj[34])) + "");
					document.add(datatable78);

					PdfPTable datatable79 = new PdfPTable(2);
					datatable79.setTotalWidth(300);
					datatable79.setLockedWidth(false);
					datatable79.getAbsoluteWidths();
					datatable79.setWidths(headerwidths1);
					datatable79.setWidthPercentage(100); // percentage
					datatable79.getDefaultCell().setPadding(3);
					datatable79.getDefaultCell().setBorderWidth(1);
					datatable79.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable79.addCell("Tamper count");
					datatable79.addCell((int) Double.parseDouble(String.valueOf(obj[36])) + "");
					document.add(datatable79);

					PdfPTable datatable80 = new PdfPTable(2);
					datatable80.setTotalWidth(300);
					datatable80.setLockedWidth(false);
					datatable80.getAbsoluteWidths();
					datatable80.setWidths(headerwidths1);
					datatable80.setWidthPercentage(100); // percentage
					datatable80.getDefaultCell().setPadding(3);
					datatable80.getDefaultCell().setBorderWidth(1);
					datatable80.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable80.addCell("Md reset date");
					datatable80.addCell(sdf1.format(date2));
					document.add(datatable80);

					PdfPTable datatable59 = new PdfPTable(2);
					datatable59.setTotalWidth(300);
					datatable59.setLockedWidth(false);
					datatable59.getAbsoluteWidths();
					datatable59.setWidths(headerwidths1);
					datatable59.setWidthPercentage(100); // percentage
					datatable59.getDefaultCell().setPadding(3);
					datatable59.getDefaultCell().setBorderWidth(1);
					datatable59.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable59.addCell("Current R-Phase angle");
					datatable59.addCell(decFormat.format(Double.parseDouble(obj[10].toString())));
					document.add(datatable59);

					PdfPTable datatable60 = new PdfPTable(2);
					datatable60.setTotalWidth(300);
					datatable60.setLockedWidth(false);
					datatable60.getAbsoluteWidths();
					datatable60.setWidths(headerwidths1);
					datatable60.setWidthPercentage(100); // percentage
					datatable60.getDefaultCell().setPadding(3);
					datatable60.getDefaultCell().setBorderWidth(1);
					datatable60.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable60.addCell("Current Y-Phase angle");
					datatable60.addCell(decFormat.format(Double.parseDouble(obj[11].toString())));
					document.add(datatable60);

					PdfPTable datatable61 = new PdfPTable(2);
					datatable61.setTotalWidth(300);
					datatable61.setLockedWidth(false);
					datatable61.getAbsoluteWidths();
					datatable61.setWidths(headerwidths1);
					datatable61.setWidthPercentage(100); // percentage
					datatable61.getDefaultCell().setPadding(3);
					datatable61.getDefaultCell().setBorderWidth(1);
					datatable61.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					datatable61.addCell("Current B-Phase angle");
					datatable61.addCell(decFormat.format(Double.parseDouble(obj[12].toString())));
					document.add(datatable61);

					/*
					 * Paragraph p = new Paragraph(); p.add( Chunk.NEWLINE ); document.add(p);
					 * 
					 * PdfContentByte content = pdfWriter.getDirectContent(); PdfTemplate
					 * pdfTemplateChartHolder = content.createTemplate(505,225); Graphics2D
					 * graphicsChart = pdfTemplateChartHolder.createGraphics(505,225,new
					 * DefaultFontMapper()); Rectangle2D chartRegion = new
					 * Rectangle2D.Double(0,0,500,225);
					 * generatePieChart(decFormat.format(Double.parseDouble(obj[10].toString())),
					 * decFormat.format(Double.parseDouble(obj[11].toString())),decFormat.format(
					 * Double.parseDouble(obj[12].toString()))).draw(graphicsChart,chartRegion);
					 * graphicsChart.dispose(); Image chartImage =
					 * Image.getInstance(pdfTemplateChartHolder);
					 * chartImage.setAlignment(Element.ALIGN_CENTER); document.add(chartImage);
					 * 
					 * Paragraph p17 = new Paragraph(); p17.add(new
					 * Chunk("This is a computer generated pdf",new Font(Font.FontFamily.TIMES_ROMAN
					 * ,16, Font.NORMAL))); ColumnText.showTextAligned(content,
					 * Element.ALIGN_CENTER,p17,(document.right() - document.left()) / 2 +
					 * document.leftMargin(),document.bottom() - 10, 0);
					 */

				}

				// document.add(parameterTable);

				document.close();
				response.setHeader("Content-disposition",
						"attachment; filename=InstantaneousParameters_" + mainEntity1.getMtrno() + ".pdf");
				response.setContentType("application/pdf");
				ServletOutputStream outstream = response.getOutputStream();
				baos.writeTo(outstream);
				outstream.flush();
				outstream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public PdfPCell getCell(String text, int alignment) {
		PdfPCell cell = new PdfPCell(new Phrase(text));
		cell.setPadding(5);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static JFreeChart generatePieChart(String r, String y, String b) {
		DefaultPieDataset dataSet = new DefaultPieDataset();

		/* VectorXYDataset.class. */

		dataSet.setValue("r= " + r, Double.parseDouble(r));
		dataSet.setValue("y= " + y, Double.parseDouble(y));
		dataSet.setValue("b= " + b, Double.parseDouble(b));

		JFreeChart chart = ChartFactory.createPieChart("Vector Diagram", dataSet, true, true, false);
		return chart;
	}

	@RequestMapping(value = "/getRMSSyncStatus", method = { RequestMethod.GET })
	public @ResponseBody Object getRMSSyncStatus(HttpServletRequest request) {
		String qry = "SELECT \"count\"(m.*) as total, \n" + "\"count\"(CASE WHEN mm.rflag=1 THEN 1 END) as syncd,\n"
				+ "\"count\"(CASE WHEN mm.rflag=0 THEN 1 END) as nsyncd\n"
				+ " FROM meter_data.master_main m LEFT JOIN\n" + "(\n"
				+ "SELECT metrno, CAST(COALESCE(rmsflag,'0') as INTEGER) as rflag FROM meter_data.metermaster WHERE rdngmonth='201811'\n"
				+ ") mm on m.mtrno=mm.metrno";

		List<?> li = entityManager.createNativeQuery(qry).getResultList();
		return li;
	}

	@RequestMapping(value = "/getDashBoardStatData", method = { RequestMethod.GET })
	public @ResponseBody List<?> getDashBoardStatData(HttpServletRequest request) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
//		String town = request.getParameter("town");
		String type = request.getParameter("type");
		String whreStatement = "WHERE a.tp_towncode=m.town_code and m.zone='" + zone + "' and m.circle='" + circle + "' ";
		
				
		if ("RAPDRP".equalsIgnoreCase(type) || "IPDS".equalsIgnoreCase(type)) {
			whreStatement += " and m.fdrtype='" + type + "' and m.fdrcategory='FEEDER METER'";
		} else if ("FEEDER METER".equalsIgnoreCase(type) || "BOUNDARY METER".equalsIgnoreCase(type)
				|| "DT".equalsIgnoreCase(type) || "HT".equalsIgnoreCase(type) || "LT".equalsIgnoreCase(type)) {
			whreStatement += " and m.fdrcategory='" + type + "'";
		} else if (type.contains("DLMS")) {
			whreStatement += " and m.dlms='" + type + "'";
		}else if ("RAPDRP".equalsIgnoreCase(type) || "NON_IPDS".equalsIgnoreCase(type)) {
			whreStatement += " and m.fdrtype!='IPDS' and m.fdrcategory='FEEDER METER'";
		}

		String qry = "SELECT DISTINCT on (mtrno)  m.zone, m.circle, m.division, m.subdivision, COALESCE(m.substation,'') as substation,m.mtrno,a.town_ipds  FROM meter_data.master_main m,meter_data.amilocation a "
				+ whreStatement;
		
//		System.out.println("qryyyyy=="+qry);
		List<?> li = entityManager.createNativeQuery(qry).getResultList();
		return li;
	}
	
	/*
	 * @RequestMapping(value = "/getNonIpdsData", method = { RequestMethod.GET })
	 * public @ResponseBody List<?> getNonIpdsData(HttpServletRequest request) {
	 * String qry
	 * ="SELECT m.zone,m.circle,m.division,m.subdivision,COALESCE(m.substation,'') as substation,a.town_ipds as town,m.mtrno "
	 * +
	 * " FROM meter_data.master_main m,meter_data.amilocation a WHERE m.fdrtype!='IPDS' and m.fdrcategory='FEEDER METER' AND m.zone=a.zone AND m.circle=a.circle AND a.tp_towncode=m.town_code"
	 * ; System.out.println("NON-IPDS=======>"+qry); List<?> list =
	 * entityManager.createNativeQuery(qry).getResultList(); return list; }
	 */

	@RequestMapping(value = "/getHESMeterStatus/{hes_type}/{status}/{circle}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<?> getHESMeterStatus(@PathVariable String hes_type, @PathVariable String status,@PathVariable String circle,
			ModelMap model, HttpServletRequest request)// get active and inactive count
	{
		String cis = "";
		String subLocationWhere = queryLocationAndCountWhere.replace("WHERE", " AND x.");
		// System.out.println(hes_type+"##################################333333333333333
		// "+status);
		// levelName=levelName.replace("@@@", "/");
//		System.out.println(hes_type+" SSSSSSSSSSSSSSSSS");
//		String sqlqry="SELECT zone, circle, division, subdivision, COALESCE(substation,'') as substation,mtrno, lcom FROM meter_data.master_main m "
//				+ "LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number "
//				+ "WHERE hes_type='"+hes_type+"'";
		
		if(circle.equalsIgnoreCase("0"))
		{
			cis = "%";
		}
		else
		{
			cis = circle;
		}
		

		String sqlqry = "";
		if (hes_type.equalsIgnoreCase("Others")) {
//			sqlqry="SELECT zone, circle, division, subdivision, COALESCE(substation,'') as substation,mtrno, lcom FROM meter_data.master_main m "
//				+ "LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number WHERE hes_type is null";
			sqlqry = "select x.zone, x.circle, x.division, x.subdivision,x.substation,x.mtrno,x.town_ipds as town, lcom ,x.fdrname,x.location_id\r\n"
					+ "from (SELECT m.zone, m.circle, m.division, m.subdivision,m.fdrname,m.location_id, \r\n"
					+ "COALESCE(m.substation,'') as substation,\r\n" + "mtrno,hes_type,a.town_ipds\r\n"
					+ "FROM meter_data.master_main m ,meter_data.amilocation a \r\n"
					+ "where a.tp_towncode=m.town_code group by m.zone, m.circle, m.division, m.subdivision,m.fdrname,m.location_id, \r\n" + 
					"COALESCE(m.substation,''),\r\n" + 
					"mtrno,hes_type,a.town_ipds)x\r\n"
					+ "LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc \r\n"
					+ "ON x.mtrno=mc.meter_number WHERE x.hes_type is null "+subLocationWhere+" and x.circle like '"+cis+"' ";

		} else {
			sqlqry = "select x.zone, x.circle, x.division, x.subdivision,x.substation,x.mtrno,x.town_ipds as town, lcom,x.fdrname,x.location_id \r\n"
					+ "from (SELECT m.zone, m.circle, m.division, m.subdivision,m.fdrname,m.location_id, \r\n"
					+ "COALESCE(m.substation,'') as substation,\r\n" + "mtrno,hes_type,a.town_ipds\r\n"
					+ "FROM meter_data.master_main m ,meter_data.amilocation a \r\n"
					+ "where a.tp_towncode=m.town_code group by m.zone, m.circle, m.division, m.subdivision,m.fdrname,m.location_id, \r\n" + 
					"COALESCE(m.substation,''),\r\n" + 
					"mtrno,hes_type,a.town_ipds)x\r\n"
					+ "LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc \r\n"
					+ "ON x.mtrno=mc.meter_number WHERE x.hes_type ='" + hes_type + "' "+subLocationWhere+" and x.circle like '"+cis+"' ";
//		 sqlqry="SELECT zone, circle, division, subdivision, COALESCE(substation,'') as substation,mtrno, lcom FROM meter_data.master_main m "
//		 		+ "LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number "
//		 	+ "WHERE hes_type='"+hes_type+"'";
		}

		if (status.equalsIgnoreCase("active")) {
			sqlqry += " and mc.lcom is not null";
		} else if (status.equalsIgnoreCase("inactive")) {
			sqlqry += " and mc.lcom is null";
		} else if (status.equalsIgnoreCase("commTdy")) {
			sqlqry += " and date(mc.lcom) =CURRENT_DATE";
		} else if (status.equalsIgnoreCase("notcommTdy")) {
			sqlqry += " and date(mc.lcom) <CURRENT_DATE";
		}
		System.out.println(sqlqry);

		Query query = entityManager.createNativeQuery(sqlqry);

		List<Object> li = query.getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> activityMap = null;
		for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			activityMap = new HashMap<>();
//			System.out.println("Zone=" + values[0] + " circle=" + values[1] + " division=" + values[2] + " subdivision"+ values[3]);
			activityMap.put("zone", (values[0] == null) ? "--" : values[0]);
			activityMap.put("circle", (values[1] == null) ? "--" : values[1]);
			activityMap.put("division", (values[2] == null) ? "--" : values[2]);
			activityMap.put("subdivision", (values[3] == null) ? "--" : values[3]);
			activityMap.put("substation", (values[4] == null) ? "--" : values[4]);
			activityMap.put("mtrno", (values[5] == null) ? "--" : values[5]);
			activityMap.put("town", (values[6] == null) ? "--" : values[6]);
			activityMap.put("lastComm", (values[7] == null) ? "--" : values[7]);
			activityMap.put("fdrname", (values[6] == null) ? "--" : values[8]);
			activityMap.put("fdrcode", (values[7] == null) ? "--" : values[9]);
			result.add(activityMap);
		}
		return result;
	}
	
	
	

	@RequestMapping(value = "/cdfImportManager", method = { RequestMethod.GET, RequestMethod.POST })
	public String cdfImportManager(HttpServletRequest request, Model model) {
//		System.out.println("enter cdfImportManager method");
		model.addAttribute("result", "notDisplay");
		return "cdfimportmanager";
	}

	@RequestMapping(value = "/getMeterTypeSummaryDetails/{type}/{status}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<?> getMeterTypeSummaryDetails(@PathVariable String type, @PathVariable String status,
			ModelMap model, HttpServletRequest request)// get active and inactive count
	{
		// System.out.println(type+"##################################333333333333333
		// "+status);
		// levelName=levelName.replace("@@@", "/");
		// System.out.println(type+" SSSSSSSSSSSSSSSSS");
//		String sqlqry="SELECT zone, circle, division, subdivision, COALESCE(substation,'') as substation,mtrno, lcom FROM meter_data.master_main m "
//				+ "LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number "
//				+ "WHERE fdrcategory='"+type+"'";
		String subLocationWhere = queryLocationAndCountWhere.replace("WHERE", " AND m.");
		System.err.println(subLocationWhere);
		String sqlqry = "select x.zone, x.circle, x.division, x.subdivision,x.substation,x.mtrno,x.town_ipds as town, lcom,x.fdrname,x.location_id \r\n"
				+ "from (SELECT m.zone, m.circle, m.division, m.subdivision,m.fdrname,m.location_id,  \r\n"
				+ "COALESCE(m.substation,'') as substation,\r\n" + "mtrno,fdrcategory,a.town_ipds\r\n"
				+ "FROM meter_data.master_main m ,meter_data.amilocation a \r\n"
				+ "where a.tp_towncode=m.town_code "+subLocationWhere+" group by m.zone, m.circle, m.division, m.subdivision,m.fdrname,m.location_id , \r\n" + 
				"COALESCE(m.substation,''),\r\n" + 
				"mtrno,fdrcategory,a.town_ipds)x	\r\n"
				+ "LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc ON x.mtrno=mc.meter_number\r\n"
				+ "WHERE x.fdrcategory='" + type + "'";

		if (status.equalsIgnoreCase("active")) {
			sqlqry += " and mc.lcom is not null";
		} else if (status.equalsIgnoreCase("inactive")) {
			sqlqry += " and mc.lcom is null";
		} else if (status.equalsIgnoreCase("commTdy")) {
			sqlqry += " and date(mc.lcom) =CURRENT_DATE";
		} else if (status.equalsIgnoreCase("notcommTdy")) {
			sqlqry += " and date(mc.lcom) <CURRENT_DATE";
		}
     	System.out.println("Query=== "+sqlqry);
		Query query = entityManager.createNativeQuery(sqlqry);
		List<?> li = query.getResultList();
		List<Map<String, Object>> result = new ArrayList<>();
		for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) {
			Object[] values = (Object[]) iterator.next();
			Map<String, Object> activityMap = new HashMap<>();

			activityMap.put("zone", (values[0] == null) ? "--" : values[0]);
			activityMap.put("circle", (values[1] == null) ? "--" : values[1]);
			activityMap.put("division", (values[2] == null) ? "--" : values[2]);
			activityMap.put("subdivision", (values[3] == null) ? "--" : values[3]);
			activityMap.put("substation", (values[4] == null) ? "--" : values[4]);
			activityMap.put("mtrno", (values[5] == null) ? "--" : values[5]);
			activityMap.put("town", (values[6] == null) ? "--" : values[6]);
			activityMap.put("lastComm", (values[7] == null) ? "--" : values[7]);
			activityMap.put("fdrname", (values[8] == null) ? "--" : values[8]);
			activityMap.put("fdrcode", (values[9] == null) ? "--" : values[9]);
			result.add(activityMap);
		}
		return result;
	}
	
	//method to get metrno and other details
	@RequestMapping(value = "/mappedMetersDetailsPopUp", method = { RequestMethod.POST })
	public @ResponseBody List<?> getMappedMetersDetailsPopUp(HttpServletRequest request, Model model) {
		String date = request.getParameter("mappeddate[]").trim();
		try {
			String query = "SELECT b.mtrno,b.mtrmake,b.fdrcategory,a.updateddate\n"
					+ "from meter_data.initial_meter_info a,meter_data.master_main b\n"
					+ "WHERE to_char((a.updateddate), 'Mon-DD' ) = '" + date + "'\n"
					+ "and a.sync_status=1 and a.data_type='MasterInfo' and a.meterid=b.mtrno  \n"
					+ "and updateddate is not null";
//			System.out.println("query===>" + query);
			Query sql = entityManager.createNativeQuery(query);
			List<?> list = sql.getResultList();
			List<Map<String, Object>> result = new ArrayList<>();
			for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
				Object[] values = (Object[]) iterator.next();
				Map<String, Object> activityMap = new HashMap<>();

				activityMap.put("mtrno", (values[0] == null) ? "--" : values[0]);
				activityMap.put("mtrmake", (values[1] == null) ? "--" : values[1]);
				activityMap.put("fdrcategory", (values[2] == null) ? "--" : values[2]);
				result.add(activityMap);
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	@RequestMapping(value = "/getLosses/{type}/{status}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<?> getLossDetails(@PathVariable String type, @PathVariable String status,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) {
		
	//	System.out.println("inside losses............................");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		  Calendar cal = Calendar.getInstance();
		  cal.add(Calendar.MONTH, -1);
		
		String billmonth = format.format(cal.getTime());
		
		
		String sqlquery = "";

		if(status.equalsIgnoreCase("FDRLSR15")) {
			 sqlquery = "select x.zone,x.circle,x.division,x.subdivision,x.substation,x.town_ipds as town,x.mtrno,x.atc_loss_percent from (select zone,circle,division,subdivision,substation,town_ipds,mtrno,round((1-(a.ActualBilling_efficiency*a.Actualcollection_efficiency))*100,2) as  atc_loss_percent from "
			 		+ "(Select inv.zone,inv.circle,inv.division,inv.subdivision,inv.substation,s.town_ipds, inv.mtrno,  round((unit_billed/NULLIF(unit_supply,0)),2) AS ActualBilling_efficiency,round((amt_collected/NULLIF(amt_billed,0)),2) AS Actualcollection_efficiency from meter_data.master_main inv,meter_data.rpt_eamainfeeder_losses_02months v,meter_data.amilocation s where inv.mtrno=v.meter_sr_number and v.month_year='"+billmonth+"' and s.tp_towncode=inv.town_code and inv.fdrcategory='"+type+"')a)x where atc_loss_percent < 15";	
		}else if(status.equalsIgnoreCase("FDRGTR15")) {
			 sqlquery = "select x.zone,x.circle,x.division,x.subdivision,x.substation,x.town_ipds as town,x.mtrno,x.atc_loss_percent from (select zone,circle,division,subdivision,substation,town_ipds,mtrno,round((1-(a.ActualBilling_efficiency*a.Actualcollection_efficiency))*100,2) as  atc_loss_percent from"
			 		+ "(Select inv.zone,inv.circle,inv.division,inv.subdivision,inv.substation,s.town_ipds, inv.mtrno,  round((unit_billed/NULLIF(unit_supply,0)),2) AS ActualBilling_efficiency,round((amt_collected/NULLIF(amt_billed,0)),2) AS Actualcollection_efficiency from meter_data.master_main inv,meter_data.rpt_eamainfeeder_losses_02months v,meter_data.amilocation s where inv.mtrno=v.meter_sr_number and v.month_year='"+billmonth+"' and s.tp_towncode=inv.town_code and inv.fdrcategory='"+type+"')a)x where atc_loss_percent > 15";	
		
		}
		
		System.out.println("query :::" + sqlquery);

		Query query = entityManager.createNativeQuery(sqlquery);
		List<?> li = query.getResultList();
		List<Map<String, Object>> result = new ArrayList<>();
		for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) {
			Object[] values = (Object[]) iterator.next();
			Map<String, Object> activityMap = new HashMap<>();

			activityMap.put("zone", (values[0] == null) ? "--" : values[0]);
			activityMap.put("circle", (values[1] == null) ? "--" : values[1]);
			activityMap.put("division", (values[2] == null) ? "--" : values[2]);
			activityMap.put("subdivision", (values[3] == null) ? "--" : values[3]);
			activityMap.put("substation", (values[4] == null) ? "--" : values[4]);
			activityMap.put("town", (values[5] == null) ? "--" : values[5]);
			activityMap.put("mtrno", (values[6] == null) ? "--" : values[6]);
			activityMap.put("atc_loss_percent", (values[7] == null) ? "--" : values[7]);
			result.add(activityMap);
		}
		
		return result;

	}

	@RequestMapping(value = "/getDTLosses/{type}/{status}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<?> getLossDTDetails(@PathVariable String type, @PathVariable String status,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) {
		
		System.out.println("inside losses............................");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		  Calendar cal = Calendar.getInstance();
		  cal.add(Calendar.MONTH, -1);
		
		String billmonth = format.format(cal.getTime());
		
		
		String sqlquery = "";

		 if(status.equalsIgnoreCase("DTLSR15")) {
			 sqlquery = "Select distinct (y2.*) from (\n" +
					 "					Select y.* from(\n" +
					 "					SELECT tpdt_id, mmmain.zone,mmmain.circle,mmmain.division,mmmain.subdivision,mmmain.substation, meterno as mtrno, round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
					 "					(Select tpdt_id ,meterno,round((total_unit_billed/NULLIF(total_unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
					 "					round((total_amount_collected/NULLIF(total_amount_billed,0)),2) AS Actualcollection_efficiency	\n" +
					 "					from  meter_data.rpt_eadt_losses_02months where month_year = '"+billmonth+"' )A\n" +
					 "					left join\n" +
					 "					(Select *from meter_data.master_main)mmmain on mmmain.location_id =A.tpdt_id where fdrcategory='"+type+"' )y )y2 where atc_loss_percent <15 order by tpdt_id\n" ;
					 
		 
		 }else if(status.equalsIgnoreCase("DTGTR15")) {
			 sqlquery = "Select distinct (y2.*) from (\n" +
					 "	Select y.* from(\n" +
					 "	SELECT tpdt_id, mmmain.zone,mmmain.circle,mmmain.division,mmmain.subdivision,mmmain.substation, meterno as mtrno, round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
					 "	(Select tpdt_id ,meterno,round((total_unit_billed/NULLIF(total_unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
					 "	round((total_amount_collected/NULLIF(total_amount_billed,0)),2) AS Actualcollection_efficiency	\n" +
					 "	from  meter_data.rpt_eadt_losses_02months where month_year = '"+billmonth+"' )A\n" +
					 "	left join\n" +
					 "	(Select *from meter_data.master_main)mmmain on mmmain.location_id = A.tpdt_id where fdrcategory='"+type+"' )y )y2 where atc_loss_percent >15 order by tpdt_id\n" ;
					 
		 
		}
		
		System.out.println("query :::" + sqlquery);

		Query query = entityManager.createNativeQuery(sqlquery);
		List<?> li = query.getResultList();
		List<Map<String, Object>> result = new ArrayList<>();
		for (Iterator<?> iterator = li.iterator(); iterator.hasNext();) {
			Object[] values = (Object[]) iterator.next();
			Map<String, Object> activityMap = new HashMap<>();

			activityMap.put("tpdt_id", (values[0] == null) ? "--" : values[0]);
			activityMap.put("zone", (values[1] == null) ? "--" : values[1]);
			activityMap.put("circle", (values[2] == null) ? "--" : values[2]);
			activityMap.put("division", (values[3] == null) ? "--" : values[3]);
			activityMap.put("subdivision", (values[4] == null) ? "--" : values[4]);
			activityMap.put("substation", (values[5] == null) ? "--" : values[5]);
			activityMap.put("mtrno", (values[6] == null) ? "--" : values[6]);
			activityMap.put("atc_loss_percent", (values[7] == null) ? "--" : values[7]);
			result.add(activityMap);
		}
		
		return result;

	}
	
	@RequestMapping(value = "/meterLocationData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object meterLocationData(@RequestParam String zone, @RequestParam String circle,
			@RequestParam String division, @RequestParam String sdoCode) {

		return MasterMainService.getCustomEntityManager("postgresMdas").createNativeQuery(
				"select zone,circle,division,subdivision,customer_name,mtrno,longitude,latitude from meter_data.master_main where zone like '"
						+ zone + "' and circle like '" + circle + "' and division like '" + division
						+ "' and subdivision like '" + sdoCode + "'")
				.getResultList();
	}

	@RequestMapping(value = "/gisview", method = { RequestMethod.GET, RequestMethod.POST })
	public String gisview(ModelMap model, HttpServletRequest request) {
		// System.out.println("===========feedersOnMap info==============");
		/*
		 * List<FeederMasterEntity> subdivList=feederService.getDistinctSubDivision();
		 * String
		 * subdiv=request.getParameter("sdoCode"),subStation=request.getParameter(
		 * "subStation");; model.put("subdivList", subdivList);
		 * model.put("subStationList", feederService.getSStationBySub(subdiv,model));
		 * 
		 * model.addAttribute("subdiv",subdiv);
		 * model.addAttribute("subStation",subStation); model.addAttribute("results",
		 * "notDisplay");
		 */
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String btnSLD = request.getParameter("viewModem");
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode"),
				subStation = request.getParameter("subStation");
		;
		model.put("zoneList", zoneList);
		model.put("circleList", feederService.getCircleByZone(zone, model));
		model.put("divisionList", feederService.getDivisionByCircle(zone, circle, model));
		model.put("subdivList", feederService.getSubdivByDivisionByCircle(zone, circle, division, model));

		model.addAttribute("zone", zone);
		model.addAttribute("circle", circle);
		model.addAttribute("division", division);
		model.addAttribute("subdiv", subdiv);
		model.addAttribute("results", "notDisplay");
		return "gisview";
	}

	// **USED IN MOBILE APPLICATION. DONT CHANGE WITHOUT MOB DEVELOPER PERMISSION
	@RequestMapping(value = "/getViewDataBySubdivision", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getViewDataBySubdivision(ModelMap model, HttpServletRequest request) {
		return MasterMainService.getViewDataBySubdivision(request);

	}

	// get survey data
	@RequestMapping(value = "/getSurveyDataImages", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getSurveyDataImages(@RequestParam String kno, ModelMap model,
			HttpServletRequest request) {
		System.out.println("kno--" + kno);
		return MasterMainService.getSurveyDataImages(kno);

	}

	@RequestMapping(value = "/meterImageDisplay/{id}", method = RequestMethod.GET)
	public void getViewOnMapMtrImage(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable String id) throws IOException {
		System.out.println("In getViewOnMapMtrImage===>" + id);
		MasterMainService.findOnlyImage(model, request, response, id);

	}

	@RequestMapping(value = "/dcuDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String dcuDetails(ModelMap model, HttpServletRequest request) {
		return "DCUDetailsMDAS";

	}

	@RequestMapping(value = "/dcuList", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object dcuList(ModelMap model, HttpServletRequest request) {
		return sns.dcuList();

	}

	@RequestMapping(value = "/dcuCommu", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object dcuCommu() {
		// String sql="select count(gateway_node_id),sum(case when status='LOGIN' then 1
		// else 0 end ) as communicate,sum(case when status='LOGOUT' then 1 else 0 end )
		// as not_comm from meter_data.search_nodes where node_type='GATEWAY' and id in
		// (select max(id) from meter_data.search_nodes where node_type='GATEWAY' and
		// gateway_node_id is not null GROUP BY gateway_node_id )";
		String sql = "select count(gateway_node_id),sum(case when status='LOGIN' then 1 else 0 end ) as communicate,sum(case when status='LOGOUT'  then 1 else 0 end ) as not_comm,\n"
				+ "sum( case when status='LOGOUT' and to_char(time_stamp,'YYYY-MM-DD')=to_char(CURRENT_TIMESTAMP,'YYYY-MM-DD')   then 1 else 0 end ) as today_not_comm_c,\n"
				+ "sum( case when status='LOGOUT'  and to_char(time_stamp,'YYYY-MM-DD')>=to_char(CURRENT_DATE-interval '5 days','YYYY-MM-DD')  then 1 else 0 end ) as today_not_comm_5,\n"
				+ "sum( case when status='LOGOUT' and  to_char(update_time,'YYYY-MM-DD')>=to_char(CURRENT_DATE -interval '10 days','YYYY-MM-DD')  then 1 else 0 end ) as today_not_comm_10\n"
				+ "from meter_data.search_nodes where node_type='GATEWAY' and id in (select max(id)  from meter_data.search_nodes where node_type='GATEWAY' and gateway_node_id is not null  GROUP BY gateway_node_id )";
		List<Object[]> connCount = sns.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		String sql1 = "select (select dcu_sn_number from meter_data.dcu_master where node_id=gateway_node_id ) as dcu_sr_number,gateway_node_id,update_time,(case when status='LOGIN' then 'Connected' else 'Disconnected' end ) as status\n"
				+ "from meter_data.search_nodes where node_type='GATEWAY' and id in (select max(id)  from meter_data.search_nodes where node_type='GATEWAY' and gateway_node_id is not null  GROUP BY gateway_node_id )";
		List<Object[]> dcuData = sns.getCustomEntityManager("postgresMdas").createNativeQuery(sql1).getResultList();
		List<List<Object[]>> l = new ArrayList<>();
		l.add(connCount);
		l.add(dcuData);
		return l;
	}

	@RequestMapping(value = "/dcuWiseCommuMeters/{dcuid}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object dcuWiseCommuMeters(@PathVariable String dcuid) {

		if (dcuid.equalsIgnoreCase("All")) {
			dcuid = "%";
		}
		String sql = "select distinct sn.node_id,sn.gateway_node_id,case when  np.meter_serial_number is null then '' else np.meter_serial_number end  from meter_data.search_nodes sn LEFT JOIN meter_data.name_plate np on sn.node_id=np.node_id where sn.gateway_node_id like '"
				+ dcuid
				+ "' and to_char(time_stamp,'YYYY-MM-DD')=to_char(CURRENT_DATE,'YYYY-MM-DD') and sn.node_type<>'GATEWAY'";
		return sns.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
	}

	// MeterReplaceMent Screen
	@RequestMapping(value = "/meterReplacement", method = { RequestMethod.GET, RequestMethod.POST })
	public String meterReplacement(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "meterReplacement";
	}

	@RequestMapping(value = "/getMeterDataForReplace/{meterno}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object retriveMrName(@PathVariable String meterno, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		return MasterMainService.getMeterDataForReplacement(meterno);
	}

	@RequestMapping(value = "/replaceMeterNo", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String replaceMeterNo(HttpServletResponse response, HttpServletRequest request, ModelMap model)
			throws java.text.ParseException {
		String oldmeterno = request.getParameter("oldmeterno");
		String accno = request.getParameter("accno");
		String consumerName = request.getParameter("consumerName");
		String lastCommDate = request.getParameter("lastCommDate");
		String lastInstaData = request.getParameter("lastInstaData");
		String lastLoadDate = request.getParameter("lastLoadDate");
		String lastLoad = request.getParameter("lastLoad");
		String newmeterno = request.getParameter("newmeterno");
		String initialReading = request.getParameter("initialReading");
		String mtrChangeDate = request.getParameter("mtrChangeDate");
		Timestamp mtrChangeTime = null;
//		System.out.println("oldmeterno="+oldmeterno+" accno="+accno+" consumerName="+consumerName+" lastCommDate="+lastCommDate);
//		System.out.println("lastInstaData="+lastInstaData+" lastLoadDate="+lastLoadDate+" lastLoad="+lastLoad+" newmeterno="+newmeterno);
//		System.out.println("initialReading="+initialReading+" mtrChangeDate="+mtrChangeDate);

		String msg = "";
		String mf = "";
		int count = MasterMainService.mtrReplaceUpdateNewMtrno(oldmeterno, newmeterno, mtrChangeDate, mf);
		if (count > 0) {

			try {
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date date;
				date = (Date) formatter.parse(mtrChangeDate);
				mtrChangeTime = new Timestamp(date.getTime());
				System.out.println(mtrChangeTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {

				MeterChangeTransHistory change = new MeterChangeTransHistory();
				change.setOldmeterno(oldmeterno);
				change.setNewmeterno(newmeterno);
				change.setAccno(accno);
				change.setConsumername(consumerName);
				change.setEntryby(request.getSession().getAttribute("username") + "");
				if (lastInstaData != "") {
					change.setLastinstaneouskwh(Double.parseDouble(lastInstaData));
				}
				if (initialReading != "") {
					change.setInitialreading(Double.parseDouble(initialReading));
				}
				if (lastLoad != "") {
					change.setLastloadkwh(Double.parseDouble(lastLoad));
				}
				if (lastCommDate != "") {
					change.setLastinstataneousdate(Timestamp.valueOf(lastCommDate));
				}
				if (lastLoadDate != "") {
					change.setLastloaddate(Timestamp.valueOf(lastLoadDate));
				}
				if (mtrChangeTime != null) {
					change.setMtrchangedate(mtrChangeTime);
				}
				change.setEntrydate(new Timestamp(System.currentTimeMillis()));
				System.out.println("change--" + change.toString());

				meterChangeTransHistoryService.save(change);
				msg = "MeterNo Changed Successfully";
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			msg = "MeterNo Not Changed";
		}
		return msg;
	}

	@RequestMapping(value = "/getMeterDataByNodeId", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object dcuWiseCommuMeters(ModelMap model, HttpServletRequest request) {

		return MasterMainService.getmeterDataByNOdeID(request);

	}

	@RequestMapping(value = "/getAllMeterChangeData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getAllMeterChangeData(ModelMap model, HttpServletRequest request) {

		return MasterMainService.getAllMeterChangeData();

	}
	
	@RequestMapping(value="/meterdetailspdf",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void wiringdatapdf(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		String sdiv="",div="",cir="",  twnCode="";
		String cr=request.getParameter("cr");
		String dvs=request.getParameter("dvs");
		String subdvn=request.getParameter("subdvn");
		String towncode=request.getParameter("tn");
		String townname=request.getParameter("townname");
		String location=request.getParameter("location");
		

		if(cr.equalsIgnoreCase("ALL"))
			{
				cir="%";
			}else
			{
				cir=cr;
			}
		if (dvs.equalsIgnoreCase("ALL")) 
		    {
			    div="%";
		    }else 
		    {
	            div=dvs;
		    }
		 if(subdvn.equalsIgnoreCase("ALL"))
			{
				sdiv="%";
			}else
			{
				sdiv=subdvn;
			}
		
		 if(towncode.equalsIgnoreCase("ALL"))
			{
			 twnCode="%";
			}else
			{
				twnCode=towncode;
			}

		MasterMainService.getMeterDetailsPdf(request, response, cir, div, sdiv, twnCode,townname,location);
	
	}

	@RequestMapping(value="/exportToExcelmanagemeters",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object exportToExcelmanagemeters(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		System.out.println("hi controller---");
		try {
			String zone = request.getParameter("zone");
			String circle=request.getParameter("cr");
			String division= request.getParameter("dvs");
			String subdiv=request.getParameter("subdvn");
			String towncode=request.getParameter("towncode");
			String townname=request.getParameter("townname");
			String location = request.getParameter("location");
			System.out.println(zone);
			  String fileName = "ManageMeters";
			  XSSFWorkbook wb = new XSSFWorkbook();
	          XSSFSheet sheet = wb.createSheet(fileName);
	          XSSFRow header  = sheet.createRow(0); 
	            
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
	 
					header.createCell(0).setCellValue("MeterNo");
					header.createCell(1).setCellValue("Location Name");
					header.createCell(2).setCellValue("Location Id");
					header.createCell(3).setCellValue("Location Type");
					header.createCell(4).setCellValue("Division");
					header.createCell(5).setCellValue("Circle");
					
					List<?> meterdetails=null;
					 meterdetails=modemCommService.getAllMetersBasedOnZoneCircle(zone, circle, towncode,location);
	            
					 int count =1;
						//int cellNO=0;
			            for(Iterator<?> iterator=meterdetails.iterator();iterator.hasNext();){
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
			      		if(values[1]==null)
			      		{
			      			row.createCell(1).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      			row.createCell(1).setCellValue(String.valueOf(values[1]));
			      		}
			      		if(values[2]==null)
			      		{
			      			row.createCell(2).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      			row.createCell(2).setCellValue(String.valueOf(values[2]));
			      		}
			      		if(values[3]==null)
			      		{
			      			row.createCell(3).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      			row.createCell(3).setCellValue(String.valueOf(values[3]));
			      		}
			      		if(values[4]==null)
			      		{
			      			row.createCell(4).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      			row.createCell(4).setCellValue(String.valueOf(values[4]));
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
						response.setHeader("Content-Disposition", "inline;filename=\"ManageMetersReport.xlsx"+"\"");
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
	
	
	@RequestMapping(value="/meterSyncStatusReport", method = {RequestMethod.GET , RequestMethod.POST})
	public String meterSyncStatusReport (HttpServletRequest request,HttpServletResponse Response) {
		
		
	return "meterDataSyncReport";
	}
	
	@RequestMapping(value="/getMeterSyncStatusReport" , method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List getMeterSyncStatusReport (HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		String reportId = request.getParameter("reportId").trim();
		
		//List<?> list = null;
		
		String sqlquery = "";

		
		if("TOTAL METERS INTEGRATION STATUS FOR GENUS".equalsIgnoreCase(reportId) ) {
			sqlquery = "select max(coalesce(m.last_sync_inst,last_sync_inst)) as last_sync_inst,\r\n" + 
					"					max(coalesce(m.last_sync_load,last_sync_load)) as last_sync_load,\r\n" + 
					"					max(coalesce(m.last_sync_event,last_sync_event)) as last_sync_event,\r\n" + 
					"					max(coalesce(m.last_sync_bill,last_sync_bill)) as last_sync_bill,\r\n" + 
					"					(select max(coalesce(v.time_stamp,time_stamp)) as last_sync_meterinfo from meter_data.initial_meter_info v where meterid similar to '(50|19|62|36|37|51)%' and data_type='MasterInfo'),\r\n" + 
					"					(select max(coalesce(v.time_stamp,time_stamp)) as last_sync_nameplate from meter_data.initial_meter_info v where meterid similar to '(50|19|62|36|37|51)%' and data_type='NamePlate'),\r\n" + 
					"					(select count(distinct meter_number) from meter_data.modem_communication where date(last_communication)=CURRENT_DATE and meter_number similar to '(50|19|62|36|37|51)%') as meter_count\r\n" + 
					"					FROM meter_data.modem_communication m where meter_number similar to '(50|19|62|36|37|51)%'";
		}else if("TOTAL METERS INTEGRATION STATUS FOR ANALOGICS".equalsIgnoreCase(reportId) ) {
			sqlquery = "select max(coalesce(m.last_sync_inst,last_sync_inst)) as last_sync_inst, \r\n" + 
					"	max(coalesce(m.last_sync_load,last_sync_load)) as last_sync_load, \r\n" + 
					"	max(coalesce(m.last_sync_event,last_sync_event)) as last_sync_event, \r\n" + 
					"	max(coalesce(m.last_sync_bill,last_sync_bill)) as last_sync_bill,  \r\n" + 
					"	(select max(coalesce(v.time_stamp,time_stamp)) as last_sync_meterinfo from meter_data.initial_meter_info v where  data_type='MasterInfo' and clientid='1'), \r\n" + 
					"	(select max(coalesce(v.time_stamp,time_stamp)) as last_sync_nameplate from meter_data.initial_meter_info v where data_type='NamePlate' and clientid='1'), \r\n" + 
					"	(select count(distinct meter_number) from meter_data.modem_communication where date(last_communication)=CURRENT_DATE and meter_number in (select distinct mtrno from meter_data.master_main where fdrcategory != 'DT'))  as meter_count	FROM meter_data.modem_communication m where meter_number similar to '(18|03|X0|04)%'";		
			}
		
		else if("TOTAL METERS INTEGRATION STATUS FOR GENUS WITH TODAY COMMUNICATION TIME".equalsIgnoreCase(reportId) ) {
			sqlquery = "select max(coalesce(m.last_sync_inst,last_sync_inst)) as last_sync_inst, \r\n" + 
					"										max(coalesce(m.last_sync_load,last_sync_load)) as last_sync_load, \r\n" + 
					"										max(coalesce(m.last_sync_event,last_sync_event)) as last_sync_event, \r\n" + 
					"										max(coalesce(m.last_sync_bill,last_sync_bill)) as last_sync_bill, \r\n" + 
					"										(select max(coalesce(v.time_stamp,time_stamp)) as last_sync_meterinfo from meter_data.initial_meter_info v where data_type='MasterInfo' and clientid='2'), \r\n" + 
					"										(select max(coalesce(v.time_stamp,time_stamp)) as last_sync_nameplate from meter_data.initial_meter_info v where data_type='NamePlate' and clientid='2'), \r\n" + 
					"										(SELECT \"count\"(case WHEN date(mc.lcom)=CURRENT_DATE THEN 1 END) as comm_today\r\n" + 
					"FROM meter_data.master_main m LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) as lcom FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number  and m.fdrcategory='DT' ) as meter_count \r\n" + 
					"										FROM meter_data.modem_communication m where meter_number similar to '(50|19|62|36|37|51)%' and date(last_communication)=current_date";
		}
		
		System.out.println(sqlquery);
		Query query = entityManager.createNativeQuery(sqlquery);
		List<?> list = query.getResultList();
		List<Map<String , Object>> result = new ArrayList<>();
		for(Iterator<?> iterator = list.iterator(); iterator.hasNext(); ) {
			Object[] values = (Object[]) iterator.next();
			Map<String, Object> activityMap = new HashMap<>();
			activityMap.put("last_sync_inst",  values[0]);
			activityMap.put("last_sync_load",  values[1]);
			activityMap.put("last_sync_event",  values[2]);
			activityMap.put("last_sync_bill",  values[3]);
			activityMap.put("last_sync_meterinfo",  values[4]);
			activityMap.put("last_sync_nameplate",  values[5]);
			activityMap.put("meter_count", values[6]);
			result.add(activityMap);

		}
		
		return result;
	}

	
	
	
	/*@RequestMapping(value = "/getfeederLess15DetailsData", method = RequestMethod.GET)
	public @ResponseBody Object getfeederLess15DetailsData(
			ModelMap model, HttpServletRequest request) {
		
		//Below code is to display best and worst feeder and  DT list(Top 10 AT&C losses.) 	
		String billmonth = "201911";
		
		List<?> FeederLess15AtcData = null;
		List<?> FeederGreater15AtcData = null;
		List<?> DTLess15AtcData = null;
		List<?> DTGreater15AtcData = null;
		
		
		try {
			FeederLess15AtcData = feederMasterService.getFeederLess15AtcData(billmonth);	
		} catch (Exception e) {
		e.printStackTrace();
		
		}
		try {
			FeederGreater15AtcData = feederMasterService.getFeederGreater15AtcData(billmonth);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			DTLess15AtcData = feederMasterService.getDTLess15AtcData(billmonth); 	
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			DTGreater15AtcData= feederMasterService.getDTGreater15AtcData(billmonth); 	
		} catch (Exception e) {
			// TODO: handle exception
		}
		model.addAttribute("bestFeederATC", FeederLess15AtcData);
		model.addAttribute("worstFeederATC", FeederGreater15AtcData);
		model.addAttribute("bestDTatc", DTLess15AtcData);
		model.addAttribute("worstDTatc", DTGreater15AtcData);
		
		
		List<?> list = null;
		return list;
	}*/
	
	
	@RequestMapping(value = "/fullView360MDAS", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> fullView360MDAS(ModelMap model, HttpServletRequest request, HttpSession session) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String metrNo = request.getParameter("meterNum");
		String frmDate = request.getParameter("frmDate");
		String tDate = request.getParameter("tDate");
	
		model.addAttribute("zone",request.getParameter("zone"));
		model.addAttribute("circle",request.getParameter("circle"));
		model.addAttribute("town",request.getParameter("town"));
		model.addAttribute("meterNum",request.getParameter("meterNum"));
		model.addAttribute("fromDate", request.getParameter("frmDate"));
		model.addAttribute("toDate", request.getParameter("tDate"));

		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String presentDate = dateFormat.format(Calendar.getInstance().getTime());
		String yesterDay = dateFormat.format(c.getTime());

		String[] str = presentDate.split("-");

		model.addAttribute("firstDate", presentDate);
		model.addAttribute("presentDate", str[0] + "-" + str[1] + "-" + "01");
      
		List<?> comm = null;
			try {
				//String sql1 = "SELECT meter_number, max(last_communication),\"count\"(*) FROM meter_data.modem_communication WHERE meter_number IN (SELECT distinct mtrno from meter_data.master_main where mtrno='"+metrNo+"') GROUP BY meter_number";
				String sql1="SELECT meter_number,min(last_communication) as fst,max(last_communication) as last,\"count\"(*) FROM meter_data.modem_communication WHERE meter_number IN (SELECT distinct mtrno from meter_data.master_main where mtrno='"+metrNo+"') and to_char(last_communication,'YYYY-MM-DD')>='2019-01-01' GROUP BY meter_number\r\n" + 
						" ";
				comm=entityManager.createNativeQuery(sql1).getResultList();
				System.out.println(sql1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			model.addAttribute("comList", comm);
		return comm;

	}
	
	@RequestMapping(value = "/getConsumerdetails1", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getConsumerdetails1(ModelMap model, HttpServletRequest request, HttpSession session) {
		String metrNo = request.getParameter("meterNum");
		List<MasterMainEntity> masterList = null;
		try {
			/*
			 * String sql = "select * from meter_data.master_main where mtrno='"+metrNo+"'";
			 */
			String sql="select ms.*,tm.towncode||'-'||tm.town_name townname,ami.section,dt.dtname from meter_data.master_main as ms left outer join meter_data.town_master as tm on  ms.town_code=tm.towncode left outer join meter_data.amilocation ami on tm.towncode=ami.tp_towncode left outer join  meter_data.dtdetails dt on(ms.mtrno=dt.meterno)  where mtrno='"+metrNo+"'";
			System.out.println("sql------->"+sql);
			masterList=entityManager.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return masterList;
	}
	@RequestMapping(value = "/checkmail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String checkmail(ModelMap model, HttpServletRequest request, HttpSession session) {
		
		String sendermailid = null;
		String sendermailpassword = null;
		String body=null;
		try {
		 String queryString = "select sendermailid,sendermailpassword from meter_data.emailgatewaysettings";
		 List<Object[]> queryUpload = entityManager.createNativeQuery(queryString).getResultList();
		 
		 if(queryUpload.size()>0){
			 sendermailid =(String)queryUpload.get(0)[0];
			 sendermailpassword=(String)queryUpload.get(0)[1];	
			}
	
		 String mesg="SDOCODE<12133> ML: <Consumer> <SOLAR GLASS PVT LTD> <event> <Y Phase  CT Open Occurrence> Priority:<M>";
		 
		String from = sendermailid;
		String password = sendermailpassword;
		String to="se1.tneb@bcits.in";
		String cc="kesavbalaji28@gmail.com";
		String subject=" Alarm (low) at Consumer  - 	013-911407076-MANGALAM SS V 250,013-911407076-MANGALAM SS V 250";
		String newbody="";
		  body=" <span>Dear Sir,</span><br><br>\r\n" ; 
		body+=newbody;	
		body+=	"<div style='overflow: scroll;'> <table style='font-size: 9pt;'>\r\n" + 
	     		" <tr>\r\n" + 
	     		"  <th style='text-align: center;'>Subdivision</th><td>"+"12133"+"</td></tr>\r\n" + 
	     		"  <th >Location Type</th><td>"+"Consumer"+"</td></tr>\r\n" + 
	     		"  <th >Location Identity</th>  <td>"+"911407076"+"</td></tr>\r\n" + 
	     		"  <th >Location Name</th><td>"+"013-911407076-MANGALAM SS V 250,013-911407076-MANGALAM SS V 250"+"</td></tr>\r\n" + 
	     		"  <th >Alarm Setting </th><td>"+"CT open"+"</td></tr>\r\n" +
	     		"  <th >Alarm Type</th><td>"+"Event"+"</td></tr>\r\n" +
	     		"  <th >Alarm Name</th><td>"+"Y Phase CT Open Occurrence"+"</td></tr>\r\n" +
	     		"  <th >Alarm Priority</th><td>"+"LOW"+"</td></tr>\r\n" +
	     		" </tr>\r\n" ; 
		
		new Thread(new SendModemAlertViaMail(subject,body,to,cc,sendermailid,sendermailpassword)).run();
		
		}catch(Exception e) {
			
			e.printStackTrace();

		}
		 
		return body;
	}
	
	@RequestMapping(value = "/getDashBoardTownbasedStatData", method = { RequestMethod.GET })
	public @ResponseBody List<?> getDashBoardTownbasedStatData(HttpServletRequest request) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String type = request.getParameter("type");
		String whreStatement = "WHERE a.tp_towncode=m.town_code and m.zone='" + zone + "' and m.circle='" + circle + "' and a.town_ipds='" + town+ "' ";
		
				
		if ("RAPDRP".equalsIgnoreCase(type) || "IPDS".equalsIgnoreCase(type)) {
			whreStatement += " and m.fdrtype='" + type + "' and m.fdrcategory='FEEDER METER'";
		} else if ("FEEDER METER".equalsIgnoreCase(type) || "BOUNDARY METER".equalsIgnoreCase(type)
				|| "DT".equalsIgnoreCase(type) || "HT".equalsIgnoreCase(type) || "LT".equalsIgnoreCase(type)) {
			whreStatement += " and m.fdrcategory='" + type + "'";
		} else if (type.contains("DLMS")) {
			whreStatement += " and m.dlms='" + type + "'";
		}else if ("RAPDRP".equalsIgnoreCase(type) || "NON_IPDS".equalsIgnoreCase(type)) {
			whreStatement += " and m.fdrtype!='IPDS' and m.fdrcategory='FEEDER METER'";
		}

		String qry = "SELECT DISTINCT on (mtrno)  m.zone, m.circle, m.division, m.subdivision, COALESCE(m.substation,'') as substation,m.mtrno,a.town_ipds  FROM meter_data.master_main m,meter_data.amilocation a "
				+ whreStatement;
		
//		System.out.println("qryyyyy=="+qry);
		List<?> li = entityManager.createNativeQuery(qry).getResultList();
		return li;
	}
	
	
	
	/*
	 * @Transactional(propagation = Propagation.REQUIRED)
	 * 
	 * @RequestMapping(value="/last30days",method={RequestMethod.POST,RequestMethod.
	 * GET}) public List<?> last30days() { try { Timestamp insertDate = (Timestamp)
	 * new Date(); Last30DaysEntity ldr = null; String communicating =
	 * ldr.getCommunicating(); String noncommunicating = ldr.getNoncommunicating();
	 * String total = ldr.getTotal(); // Timestamp inserttime =
	 * ldr.getInsert_time(); Date date = ldr.getDate();
	 * 
	 * String sql0 =
	 * "select a.total,a.comm_yesterday,(a.total-a.comm_yesterday) as non_comm , a.date from\r\n"
	 * + "(SELECT count(*) as total,\r\n" +
	 * "(select count(*) as comm_yesterday from meter_data.master_main where mtrno in\r\n"
	 * +
	 * "(select distinct meter_number from meter_data.modem_communication where date(last_communication)=CURRENT_DATE-60) and date(create_time)<=CURRENT_DATE-1),\r\n"
	 * + "(CURRENT_DATE-1) as date\r\n" +
	 * "FROM meter_data.master_main m WHERE date(m.create_time)<=CURRENT_DATE-1)a";
	 * System.out.println(sql0);
	 * 
	 * List<?> list = entityManager.createNativeQuery(sql0).getResultList(); ldr =
	 * new Last30DaysEntity();
	 * 
	 * for(int i=0;i<list.size();i++) { Object[] li=(Object[]) list.get(i);
	 * ldr.setTotal((String) li[0]); ldr.setCommunicating((String) li[1]);
	 * ldr.setNoncommunicating(((String)li[2])); ldr.setDate(date);
	 * ldr.setInsert_time(insertDate); last30dayservice.update(ldr);
	 * 
	 * return null; }
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * return null; } return null; }
	 */
}
