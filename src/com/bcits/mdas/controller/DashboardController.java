package com.bcits.mdas.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.util.UriComponentsBuilder;

import com.bcits.mdas.service.DashboardService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.utility.FilterUnit;
import com.bcits.service.MasterService;

@Controller
public class DashboardController {
	
	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	DashboardService ds;
	
	@Autowired
	private FeederDetailsService feederdetailsservice;
	
	@Autowired
	private FeederMasterService feederMasterService;
	
	@RequestMapping(value="/meterTypeWiseSummary")
	public String meterTypeSummary(){
		return "meterTypeWiseSummary";
	}
	@RequestMapping(value="/zoneList")
    public @ResponseBody Object zoneList(){
    	return ds.zoneList();
    }
	
	@RequestMapping(value="/zoneList2")
    public @ResponseBody Object zoneList2(HttpServletRequest request){
	
		HttpSession session = request.getSession();
    	return ds.zoneList2(""+session.getAttribute("newRegionName"));
    }
	@RequestMapping(value="/circleList")
    public @ResponseBody Object circleList(@RequestParam String circle){
    	return ds.circleList(circle);
    }

	
	 @RequestMapping(value="/circleList2") public @ResponseBody Object
	  circleList2( HttpServletRequest request,@RequestParam String circle){
	  HttpSession session = request.getSession();
	  return ds.circleList2(""+session.getAttribute("newRegionName")); 
	  }
	 
	@RequestMapping(value="/divisionList")
    public @ResponseBody Object divisionList(@RequestParam String div){
		String[] s=div.split("@");
    	return ds.divisionList(s[0],s[1]);
    }
	@RequestMapping(value="/subdivList")
    public @ResponseBody Object subdivList(@RequestParam String subdiv){
		String[] s=subdiv.split("@");
    	return ds.subdivList(s[0],s[1],s[2]);
    }
	
	@RequestMapping(value="/townList")
    public @ResponseBody Object townList(@RequestParam String town){
		String[] s=town.split("@");
    	return ds.townList(s[0],s[1],s[2],s[3]);
    }
	
	@RequestMapping(value="/getregionWiseMeterList")
    public @ResponseBody Object getregionWiseMeterList(@RequestParam String region){		
    	return ds.getregionWiseMeterList(region);
    }
	
	@RequestMapping(value="/getcircleWiseMeterList")
    public @ResponseBody Object getcircleWiseMeterList(@RequestParam String circle){
		String[] s=circle.split("@");
    	return ds.getCircleWiseMeterList(s[0],s[1]);
    }
	
	@RequestMapping(value="/getDivisionWiseMeterList")
    public @ResponseBody Object getDivisionWiseMeterList(@RequestParam String division){
		String[] s=division.split("@");
    	return ds.getDivisionWiseMeterList(s[0],s[1],s[2]);
    }
	
	@RequestMapping(value="/getSubDivisionWiseMeterList")
    public @ResponseBody Object getSubDivisionWiseMeterList(@RequestParam String subdivision){
		String[] s=subdivision.split("@");
    	return ds.getSubDivisionWiseMeterList(s[0],s[1],s[2],s[3]);
    }
	
	@RequestMapping(value="/getTownWiseMeterList")
    public @ResponseBody Object getTownWiseMeterList(@RequestParam String town){
		String[] s=town.split("@");
    	return ds.getTownWiseMeterList(s[0],s[1],s[2],s[3],s[4]);
    }
	
	@RequestMapping(value="/periodWiseCommSummary")
	public String periodWiseCommSummary(ModelMap model){
		List<?> ZoneList = feederdetailsservice.getDistinctZone();
		model.addAttribute("ZoneList", ZoneList);
		return "periodWiseCommSummary";
	}
	@RequestMapping(value="/pCommSummary")
	public @ResponseBody Object pCommSummary(HttpServletRequest request){
		List<?> summaryList=new ArrayList<>();
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("subdivision");
		String town = request.getParameter("town");
		String fromdate = request.getParameter("fromdate");
		String todate = request.getParameter("todate");
		//System.out.println(zone+circle+division+subdivision+fromdate+todate);
		summaryList=ds.pCommSummary(zone,circle,town,fromdate,todate);
		return summaryList;
	}
	
	@RequestMapping(value="/unmappedMeters")
	public String unmappedmetres(ModelMap model)
	{
		//List<Object []> list = ds.unmappedMeters();
		Object total = ds.gettotalunm();
		model.put("totalunm", total);
		//model.put("meterdetails", list);
		return "unmappedmeters";
	}
	@RequestMapping(value="/getunmappedMetersdetails", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody  List<Object[]> getunmappedmetres(HttpServletRequest request)
	{
		System.err.println("in methoss");
		List<Object []> list = ds.unmappedMeters();
		return list;
	}
	
	
	
	  //pdf
	  
	  @RequestMapping(value="/unmappedmeterspdf") 
	  public void unmappedmeterspdf(ModelMap model,HttpServletRequest request,HttpServletResponse response) 
	  { 

			feederMasterService.unmappedmeterspdf(request, response);
	  }
	 
	 
	
	@RequestMapping(value="/getUnMappedMetersDashboard",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int getSyncDataFromRMS(RequestMappingHandlerAdapter request,ModelMap model,HttpSession session)
	{
//		System.out.println(" ---calling getUnMappedMetersDashboard--- ");
		
		
			String serverResponse="";
			int count =0;
			try 
			{
			List<Object []> list = ds.unmappedMeters();
			count=list.size();
				return count;
			}
			catch (Exception e) {
				e.printStackTrace();
				return count;
			}
			
	
}
	
	
	  @RequestMapping(value="/MtrTypeCirWiseSummPDF",method={RequestMethod.GET,
	  RequestMethod.POST}) public @ResponseBody void
	  MtrTypeCirWiseSummPDF(HttpServletRequest request,HttpServletResponse
	  response,ModelMap model,HttpSession session) { 
		  String zone = request.getParameter("zone");
		  String officeType = (String) session.getAttribute("officeType");
		  if (officeType.equalsIgnoreCase("corporate"))
		  {
		  ds.getMtrtypewiseSummpdf(request, response); 
		  }
		  else 
		  {
			  ds.getMtrtypewiseSummpdfforregion(request, response,zone); 
		  }
		  
	  }
	 
	
	
	  @RequestMapping(value="/CircleWiseSummPDF",method={RequestMethod.GET,
	  RequestMethod.POST}) public @ResponseBody void
	  CircleWiseSummPDF(HttpServletRequest request,HttpServletResponse
	  response,ModelMap model) { String circle=request.getParameter("circle");
	 // System.out.println("circle----" +circle);
	  ds.getCirclewiseSummpdf(request, response, circle); }
	  
	  
	  @RequestMapping(value="/DivisionWiseSummPDF",method={RequestMethod.GET,
			  RequestMethod.POST}) public @ResponseBody void
	  DivisionWiseSummPDF(HttpServletRequest request,HttpServletResponse
			  response,ModelMap model) {
		  String division=request.getParameter("division");
		  String[] ch=division.split("@");
		//  System.out.println(ch[1]);
			  ds.getDivisionWiseSummPDF(request, response,ch[1]); }
	  
	  
	  @RequestMapping(value="/SubdivisionWiseSummPDF",method={RequestMethod.GET,
			  RequestMethod.POST}) public @ResponseBody void
	  SubdivisionWiseSummPDF(HttpServletRequest request,HttpServletResponse
			  response,ModelMap model) {
		  String subdivision=request.getParameter("subdivision");
		  String[] ch=subdivision.split("@");
		//  System.out.println(ch[1]);
			  ds.getSubdivisionWiseSummPDF(request, response,ch[2]); }
	  
	  
	  @RequestMapping(value="/TownWiseSummPDF",method={RequestMethod.GET,
			  RequestMethod.POST}) public @ResponseBody void
	  TownWiseSummPDF(HttpServletRequest request,HttpServletResponse
			  response,ModelMap model) {
		  String town=request.getParameter("town");
		  System.out.println(town);
		  String[] ch=town.split("@");
		  System.out.println(ch);
			  ds.getTownWiseSummPDF(request, response,ch[3]); }
	 
	 
	 
	private String zone = "", circle = "", division = "", subDivision = "", subStation = "";
	private String selectedLevel = "", selectedLevelName = "";
	String queryLocationAndCountWhere = "";
	
	@RequestMapping(value="/healthDashboard", method = RequestMethod.GET)
	public String openDadhBoard(@RequestParam String type, @RequestParam String value, ModelMap model,
			HttpServletRequest request) {
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		selectedLevelName = value.trim();
		System.out.println("-----------------------selected sub level--"+value+"-----type---"+type+"---------------------------");
		String level = "", sublevel = "";
		String columnNameSubLevel = "",regionLevel="", regionSubLevel="";
		String urlLavel = "";
		switch (type) {
		
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
			break;

		case "circle":
			selectedLevel = "circle";
			circle = value;
			queryLocationAndCountWhere = "WHERE  circle='" + value + "'";
			columnNameSubLevel = "division";
			level = "circle";
			sublevel = "circle";
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
			
			break;

		
		}


		model.addAttribute("level", level);
		model.addAttribute("sub_level", sublevel);
		model.addAttribute("value", value);
	//	model.addAttribute("sublevel", listSubLocation);
		model.addAttribute("columnName", columnNameSubLevel);
		model.addAttribute("urlLavel", urlLavel);	
		
		//below code to get billmonth 
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		  Calendar cal = Calendar.getInstance();
		  cal.add(Calendar.MONTH, -1);
		
		String billmonth = format.format(cal.getTime());
		// Below qry to display AT&C losses for Town DT and feeder.

			String feederGrt15qry = "Select count(*) from (\n" +
					"SELECT fdr_name, round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
					"(Select fdr_name,round((unit_billed/NULLIF(unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
					"round((amt_collected/NULLIF(amt_billed,0)),2) AS Actualcollection_efficiency	\n" +
					"from  meter_data.rpt_eamainfeeder_losses_02months where month_year = '"+billmonth+"' )A)x where atc_loss_percent > 15";
			String feederLess15qry = "Select count(*) from (\n" +
					"SELECT fdr_name, round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
					"(Select fdr_name,round((unit_billed/NULLIF(unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
					"round((amt_collected/NULLIF(amt_billed,0)),2) AS Actualcollection_efficiency	\n" +
					"from  meter_data.rpt_eamainfeeder_losses_02months where month_year = '"+billmonth+"' )A)x where atc_loss_percent <= 15";
			
			String DTGrt15qry = "	\n" +
					"	Select count(*) from (\n" +
					"	SELECT  round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
					"	(Select round((total_unit_billed/NULLIF(total_unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
					"	round((total_amount_collected/NULLIF(total_amount_billed,0)),2) AS Actualcollection_efficiency	\n" +
					"	from  meter_data.rpt_eadt_losses_02months where month_year = '"+billmonth+"' )A)x where atc_loss_percent > 15 ";
					
			
			String DTless15qry = "Select count(*) from (\n" +
					"SELECT  round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
					"(Select round((total_unit_billed/NULLIF(total_unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
					"round((total_amount_collected/NULLIF(total_amount_billed,0)),2) AS Actualcollection_efficiency		\n" +
					"from  meter_data.rpt_eadt_losses_02months where month_year = '"+billmonth+"' )A)x where atc_loss_percent <= 15";
			
			BigInteger feederGrt15 = null;
			BigInteger feederLess15 = null;
			BigInteger DtGrt15 = null;
			BigInteger DtLessGrt15 = null;
			
		try {
			feederGrt15=	(BigInteger) entityManager.createNativeQuery(feederGrt15qry).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			 
		}
		try {
			feederLess15=	(BigInteger) entityManager.createNativeQuery(feederLess15qry).getSingleResult();
				} catch (Exception e) {
					// TODO: handle exception
				}
		try {
			DtGrt15=	(BigInteger) entityManager.createNativeQuery(DTGrt15qry).getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			DtLessGrt15=	(BigInteger) entityManager.createNativeQuery(DTless15qry).getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
			  
			model.addAttribute("feederGrt15",feederGrt15);
			model.addAttribute("feederLess15",feederLess15);
			model.addAttribute("DtGrt15",DtGrt15);
			model.addAttribute("DtLessGrt15",DtLessGrt15);		
			
			
			//Below code is to display best and worst feeder and  DT list(Top 10 AT&C losses.) 	
			
			
			List<?> bestFeederATC = null;
			List<?> worstFeederATC = null;
			List<?> bestDTatc = null;
			List<?> worstDTatc = null;
			
			
			try {
				bestFeederATC = feederMasterService.getBestATClossesFeeder(billmonth);	
			} catch (Exception e) {
			e.printStackTrace();
			
			}
			try {
				worstFeederATC = feederMasterService.getWorstATClossesFeeder(billmonth);	
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				bestDTatc = feederMasterService.getBestATClossesDT(billmonth); 	
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				worstDTatc= feederMasterService.getWorstATClossesDT(billmonth); 	
			} catch (Exception e) {
				// TODO: handle exception
			}
			model.addAttribute("bestFeederATC", bestFeederATC);
			model.addAttribute("worstFeederATC", worstFeederATC);
			model.addAttribute("bestDTatc", bestDTatc);
			model.addAttribute("worstDTatc", worstDTatc);
			
			

			try {
				String qrycomm = "";

//				qrycomm = "SELECT circle, \"count\"(*),\n" +
//						"\"count\"(CASE WHEN c.meter_number is not null then 1 END) as active,\n" +
//						"\"count\"(CASE WHEN c.meter_number is null then 1 END) as inactive,\n" +
//						"\"count\"(CASE WHEN c.difhr>24 AND c.ldate is not NULL AND c.difhr<=120 THEN 1 END) as inc24h,\n" +
//						"\"count\"(CASE WHEN c.difhr>120 AND c.ldate is not NULL AND c.difhr<=240 THEN 1 END) as inc5d,\n" +
//						"\"count\"(CASE WHEN c.difhr>240 AND c.ldate is not NULL AND c.difhr<=480 THEN 1 END) as inc10d,\n" +
//						"\"count\"(CASE WHEN c.difhr>480 AND c.ldate is not NULL AND c.difhr<=720 THEN 1 END) as inc20d,\n" +
//						"\"count\"(CASE WHEN c.difhr>720  THEN 1 END) as inc30d \n" +
//						"from meter_data.master_main m LEFT JOIN\n" +
//						"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
//						"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
//						"  FROM meter_data.modem_communication GROUP BY meter_number) c on m.mtrno=c.meter_number "+queryLocationAndCountWhere+" \n" +
//						"GROUP BY circle ORDER BY circle";
				
				
				
				qrycomm="select distinct * from meter_data.dash_comm";

				List<?> listcomm = entityManager.createNativeQuery(qrycomm).getResultList();
			System.out.println("Qry for communicationList = "+qrycomm);
				model.addAttribute("commReportList", listcomm);

				String qryNew = "SELECT zone, circle, \n" +
						"count(CASE WHEN fdrtype='RAPDRP' THEN 1 END) as rapdrp,\n" +
						"count(CASE WHEN fdrtype='IPDS' and fdrcategory='FEEDER METER' THEN 1 END) as ipds,\n" +
						"count(CASE WHEN fdrtype!='IPDS' and fdrcategory='FEEDER METER' THEN 1 END) as non_ipds,\n" +
						"count(*) as total2,\n" +
						"count(CASE WHEN fdrtype='IPDS' OR fdrtype='RAPDRP' THEN 1 END) as total1,\n" +
						"count(CASE WHEN mc.meter_number is NOT NULL THEN 1 END) as active,\n" +
						"count(CASE WHEN mc.meter_number is NULL THEN 1 END) as inactive,\n" +
						"count(CASE WHEN m.dlms='DLMS' THEN 1 END) as dlms,\n" +
						"count(CASE WHEN m.dlms='Non-DLMS' THEN 1 END) as nondlms,\n" +
						"count(CASE WHEN m.fdrcategory='FEEDER METER' THEN 1 END) as fdr_mtr,\n" +
						"count(CASE WHEN m.fdrcategory='BOUNDARY METER' THEN 1 END) as bdr_mtr,\n" +
						"count(CASE WHEN m.fdrcategory='DT' THEN 1 END) as dt,\n" +
						"count(CASE WHEN m.fdrcategory='HT' THEN 1 END) as ht,\n" +
						"count(CASE WHEN m.fdrcategory='LT' THEN 1 END) as lt\n" +
						"FROM meter_data.master_main m LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) \n" +
						"FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number "+queryLocationAndCountWhere+" GROUP BY zone, \n" +
						"circle ORDER BY zone, circle";
				
				List<?> listNew = entityManager.createNativeQuery(qryNew).getResultList();
			System.err.println("LISTNEW--------"+qryNew);
				
				model.addAttribute("rapNonrap", listNew);
				
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		
		return "healthDashboard";
	}
	
	@RequestMapping(value="/dtWiseDashboard",method={RequestMethod.POST,RequestMethod.GET})
	public String dtWiseDashboard(ModelMap model,HttpServletRequest request){
		
		return "dtWiseDashboard";
	}
	
	@RequestMapping(value = "/getMtrDetailsStatusBasedCir", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody List<?> getMtrDetailsStatusBasedCir(HttpServletRequest request) 
	{
		List<?> li =null;
		try {
			String circle = request.getParameter("circle");
			
			String qrycomm = "select mm.*,a.town_ipds as town from (SELECT circle,division,subdivision,town_code, \"count\"(*),\r\n"
					+ "\"count\"(CASE WHEN c.meter_number is not null then 1 END) as active,\r\n"
					+ "\"count\"(CASE WHEN c.meter_number is null then 1 END) as inactive,\r\n"
					+ "\"count\"(CASE WHEN c.difhr>24 AND c.ldate is not NULL AND c.difhr<=120 THEN 1 END) as inc24h,\r\n"
					+ "\"count\"(CASE WHEN c.difhr>120 AND c.ldate is not NULL AND c.difhr<=240 THEN 1 END) as inc5d,\r\n"
					+ "\"count\"(CASE WHEN c.difhr>240 AND c.ldate is not NULL AND c.difhr<=480 THEN 1 END) as inc10d,\r\n"
					+ "\"count\"(CASE WHEN c.difhr>480 AND c.ldate is not NULL AND c.difhr<=720 THEN 1 END) as inc20d,\r\n"
					+ "\"count\"(CASE WHEN c.difhr>720  THEN 1 END) as inc30d \r\n"
					+ "from meter_data.master_main m LEFT JOIN\r\n"
					+ "(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\r\n"
					+ "(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \r\n"
					+ "  FROM meter_data.modem_communication GROUP BY meter_number) c on m.mtrno=c.meter_number\r\n"
					+ "GROUP BY circle,division,subdivision,town_code ORDER BY circle,division,subdivision,town_code) mm,meter_data.amilocation a \r\n"
					+ "where a.tp_towncode=mm.town_code  AND mm.circle = '"+circle+"' GROUP BY mm.circle,mm.division,mm.subdivision,mm.town_code,mm.count,mm.active,mm.inactive,mm.inc24h,mm.inc5d,mm.inc10d,mm.inc20d,mm.inc30d,a.town_ipds ";			
		
//			System.err.println("LISTNEW-------"+qrycomm);
			li = entityManager.createNativeQuery(qrycomm).getResultList();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	}
	
	@RequestMapping(value = "/getCircleWiseDetailsStatus", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody List<?> getCircleWiseDetailsStatus(HttpServletRequest request) 
	{
		List<?> li =null;
		try {
			String circle = request.getParameter("circle");
			String zone = request.getParameter("zone");
			
			String qryNew = "select  mm.zone,mm.circle,mm.town_code,mm.rapdrp,mm.ipds,mm.total2,mm.active,mm.inactive,mm.dlms,mm.nondlms,mm.fdr_mtr,mm.bdr_mtr,mm.dt,mm.ht,mm.lt,\n" +
			"a.town_ipds as town,mm.non_ipds,mm.total1 from (SELECT zone, circle, town_code, \n" +
			"	count(CASE WHEN fdrtype='RAPDRP' THEN 1 END) as rapdrp,\n" +
			"	count(CASE WHEN fdrtype='IPDS' and fdrcategory='FEEDER METER' THEN 1 END) as ipds,\n" +
			"	count(CASE WHEN fdrtype!='IPDS' and fdrcategory='FEEDER METER' THEN 1 END) as non_ipds,\n" +
			"	 count(*) as total2,\n" +
			"	 count(CASE WHEN fdrtype='IPDS' OR fdrtype='RAPDRP' THEN 1 END) as total1,\n" +
			"	count(CASE WHEN mc.meter_number is NOT NULL THEN 1 END) as active,\n" +
			"	count(CASE WHEN mc.meter_number is NULL THEN 1 END) as inactive,\n" +
			"	count(CASE WHEN m.dlms='DLMS' THEN 1 END) as dlms,\n" +
			"	count(CASE WHEN m.dlms='Non-DLMS' THEN 1 END) as nondlms,\n" +
			"	count(CASE WHEN m.fdrcategory='FEEDER METER' THEN 1 END) as fdr_mtr,\n" +
			"	count(CASE WHEN m.fdrcategory='BOUNDARY METER' THEN 1 END) as bdr_mtr,\n" +
			"	count(CASE WHEN m.fdrcategory='DT' THEN 1 END) as dt,\n" +
			"	count(CASE WHEN m.fdrcategory='HT' THEN 1 END) as ht,\n" +
			"	count(CASE WHEN m.fdrcategory='LT' THEN 1 END) as lt\n" +
			"	FROM meter_data.master_main m LEFT JOIN (SELECT DISTINCT meter_number, max(last_communication) \n" +
			"	FROM meter_data.modem_communication GROUP BY meter_number) mc ON m.mtrno=mc.meter_number WHERE zone like '"+zone+"' and  circle like '"+circle+"'  GROUP BY zone, \n" +
			"	circle,town_code ORDER BY zone, circle,town_code) mm,meter_data.amilocation a\n" +
			"	where a.tp_towncode=mm.town_code GROUP BY mm.zone,mm.circle,mm.town_code,mm.rapdrp,mm.ipds,mm.total1,mm.active,mm.inactive,mm.dlms,mm.nondlms,mm.fdr_mtr,mm.bdr_mtr,mm.dt,mm.ht,mm.lt,a.town_ipds,mm.non_ipds,mm.total2";

			System.err.println("LISTNEW-------"+qryNew);
			li = entityManager.createNativeQuery(qryNew).getResultList();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	}
	
	@RequestMapping(value = "/viewDTWiseJasperDashboard", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String viewDTWiseJasperDashboard(@RequestParam("reportName") String reportName,@RequestParam("region") String region,@RequestParam("circle") String circle) 
	{
		
		try {
			String url = FilterUnit.jasperUrl + "getJasperReportTNEBEA";
			//System.out.println("Hiiiiiii");
			
			String zone ="";
			String cir ="";
			
			if(region.equalsIgnoreCase("ALL")) {
				zone="%";
			}else {
				zone=region;
			}
			if(circle.equalsIgnoreCase("ALL")) {
				cir="%";
			}else {
				cir=circle;
			}
			
			JSONObject obj = new JSONObject();
			obj.put("reportName", reportName);
			obj.put("region",zone);
			obj.put("circle", cir);
			//System.out.println(region);
			//System.out.println(circle);
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);
			System.out.println("Response=== " + res);

			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping(value = "/viewDTWiseJasperSubReportDashboard", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String viewDTWiseJasperSubReportDashboard(@RequestParam("reportname") String reportName,@RequestParam("pageNumber") String pageNumber, @RequestParam("region") String region, @RequestParam("circle") String circle) 
	{
		try {
			String url = FilterUnit.jasperUrl + "getJasperSubReportDTDashboard";
			//System.out.println("Hiiiiiii");
			JSONObject obj = new JSONObject();
			obj.put("reportName", reportName);
			obj.put("pageNumber", pageNumber);
			obj.put("zone", region);
			obj.put("circle", circle);

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);
			System.out.println("Response=== " + res);

			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/downLoadDTWiseSubReports", method = { RequestMethod.GET, RequestMethod.POST },produces="application/text")
	public @ResponseBody String downLoadDTWiseSubReports(HttpServletRequest request, HttpServletResponse response) throws JSONException {
		
		
	   	String reportName=request.getParameter("reportName");
	   	
	   	String exporttype=request.getParameter("exporttype");
	 	
	   	String zone=request.getParameter("region");
	   	
	   	String circle=request.getParameter("circle");
	   	
	   
	   	
		String	pathname = "/Application/bcits/jasper_reports/";

		JSONObject obj = new JSONObject();
		try {
		String url =FilterUnit.jasperUrl+"downloadJasperSubReportDTDashboard?exporttype=" + exporttype + "&reportName=" +reportName + "&zone=" + zone + "&circle="+circle ;
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
			} catch (Exception e) {
				e.printStackTrace();
				obj.put("FAILED", "MSG :" + e.getMessage());
				return obj.toString();
			}
			if ("EoR".equalsIgnoreCase(serverResponse + "")) {

				obj.put("FAILED", "Download is Failed Due to Network Issue");
				return obj.toString();
			}
			String fileName =  reportName.substring(reportName.lastIndexOf("/")+1)+"." + exporttype.toLowerCase();

			File newTextFile = new File(pathname + fileName);
			/*File downloadFile = new File(pathname + fileName);
			FileInputStream inputStream = new FileInputStream(downloadFile);*/ 
			
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
