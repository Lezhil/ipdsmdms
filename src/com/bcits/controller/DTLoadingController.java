package com.bcits.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.DTHealthService;
import com.bcits.service.DtDetailsService;
import com.bcits.service.MasterService;
import com.bcits.service.RdngMonthService;
import com.bcits.utility.MDMLogger;

@Controller
public class DTLoadingController {
	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	private FeederMasterService feederService;
	@Autowired
	private DtDetailsService dtDeatailsService;
	@Autowired
	private ConsumerMasterService consumermasterService;
	@Autowired
	private FeederDetailsService feederdetailsservice;
	@Autowired
	private DTHealthService dtHealthService;


	/*
	 * @RequestMapping (value="/dtLoading", method = { RequestMethod.POST,
	 * RequestMethod.GET }) public String dtLoadingDetails(HttpServletRequest
	 * request, ModelMap model,HttpSession session) { String officeType = (String)
	 * session.getAttribute("officeType"); String officeCode = (String)
	 * session.getAttribute("officeCode"); System.out.println(officeType + "--" +
	 * officeCode); String qry; Object res; List<?> circleList=new ArrayList<>(); //
	 * circles=consumermasterService.getCircle(); //model.put("circles", circles);
	 * Object rdngMonth = dtDeatailsService.getMaxRdngmnth();
	 * model.addAttribute("rdngMonth", rdngMonth); try { if
	 * (officeType.equalsIgnoreCase("circle")) {
	 * System.out.println("----------++++++++"); qry =
	 * "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a" +
	 * " WHERE circle_code = '" + officeCode + "' ORDER BY CIRCLE"; res =
	 * entityManager.createNativeQuery(qry).getSingleResult(); model.put("circles",
	 * res); } else { circleList=consumermasterService.getCircle();
	 * model.put("circles", circleList); } } catch (Exception e) {
	 * 
	 * } return "dtLoading"; }
	 */
	
	@RequestMapping (value="/dtLoading", method = { RequestMethod.POST, RequestMethod.GET })
	public String dtLoadingDetails(HttpServletRequest request, ModelMap model,HttpSession session)
	{
		 String officeType = (String) session.getAttribute("officeType");
	        String officeCode = (String) session.getAttribute("officeCode");
	//        System.out.println("offtype---"+officeType);
	  //      System.out.println("offcode---"+officeCode);
	        System.out.println(officeType + "--" + officeCode);
	        String qry;
	        Object res;
		 List<?> circleList=new ArrayList<>();
		// circles=consumermasterService.getCircle();
		//model.put("circles", circles);
		Object rdngMonth = dtDeatailsService.getMaxRdngmnth();
		model.addAttribute("rdngMonth", rdngMonth);
		List<?> ZoneList = feederdetailsservice.getDistinctZone();
		model.addAttribute("ZoneList", ZoneList);
		
		try {
            if (officeType.equalsIgnoreCase("circle")) {
                System.out.println("----------++++++++");
                qry = "SELECT DISTINCT zone FROM meter_data.amilocation ORDER BY zone";
               // System.out.println("qry");
                res = entityManager.createNativeQuery(qry).getSingleResult();
                model.put("circles", res);
            }
            else
            {
            	circleList=consumermasterService.getCircle();
        		model.put("circles", circleList);
            }
		}
            catch (Exception e) {
		
			}
		return "dtLoading";
	}
	
	@RequestMapping(value = "/showdivbycircle/{circle}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> showdivbycircle(@PathVariable String circle, HttpServletRequest request,
			ModelMap model) {
		// System.out.println("inside------");
		List<?> divisionList = dtDeatailsService.getDivByCircle(circle, model);
		model.put("divisionList", divisionList);
		return divisionList;
	}

	@RequestMapping(value = "/showSubByDiv/{circle}/{division}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> showSubByDiv(@PathVariable String circle, @PathVariable String division,
			HttpServletRequest request, ModelMap model) {
		// System.out.println("inside notifyyy------");
		List<?> subdivList = dtDeatailsService.getSubdivByDivByCircle(circle, division, model);
		model.put("subdivList", subdivList);
		return subdivList;
	}

	@RequestMapping(value = "/getDtLoadingSummaryDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getDtLoadingSummaryDetail(HttpServletRequest request) {
		 //System.out.println("inside notifyyy------");
		String zone=request.getParameter("zone");
		//System.out.println("zone is---"+zone);
		String circle = request.getParameter("circle");
		String subdiv = request.getParameter("subdiv");
		String division = request.getParameter("division");
		String town = request.getParameter("town");
		String month = request.getParameter("month");
		String monthAlt=month.replaceAll("[\\s\\-()]", "");
		//System.err.println("month is-------"+monthAlt);
		List<?> DtList = dtDeatailsService.getDtHealthRep(zone,monthAlt,circle,town);
		//System.out.println("dt list is----"+DtList);
		return DtList;
	}
	
	/*
	 * @RequestMapping(value = "/getNtwrkAssetSummaryDetail", method = {
	 * RequestMethod.GET, RequestMethod.POST }) public @ResponseBody List<?>
	 * getNtwrkAssetSummaryDetail(HttpServletRequest request, ModelMap model) {
	 * System.out.println("inside ntwrk------"); String officeid =
	 * request.getParameter("id"); String subdiv =request.getParameter("subdiv");
	 * String month = request.getParameter("month"); String
	 * monthAlt=month.replaceAll("[\\s\\-()]", "");
	 * System.err.println("month is-------"+monthAlt); List<?> DtList
	 * =dtDeatailsService.getNtwrkDetails(officeid,subdiv,monthAlt,model);
	 * System.out.println("dt list is----"+DtList); return DtList; }
	 */
	
	@RequestMapping(value = "/getoverloadedSummaryDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getoverloadedSummaryDetail(HttpServletRequest request, ModelMap model) {
		 System.out.println("inside ntwrk------");
		  String officeid = request.getParameter("id"); 
		  String subdiv =request.getParameter("subdiv"); 
		  String month = request.getParameter("month");
		  String town = request.getParameter("town");
		  String monthAlt=month.replaceAll("[\\s\\-()]", "");
		  String towncode = request.getParameter("towncode");
		  String zone = request.getParameter("zone");
		  String circle = request.getParameter("circle");
		  String zonecode = request.getParameter("zonecode");
		  String circlecode = request.getParameter("circlecode");
		  System.err.println("month is-------"+monthAlt);
		  
		  if(towncode.equalsIgnoreCase("undefined")) {
			  
			  String qry = "select distinct towncode from meter_data.town_master where towncode like '"+officeid+"'";
			  
			  System.out.println(qry);
			  
			 String result = (String) entityManager.createNativeQuery(qry).getSingleResult();
			 
			 List<?> DtList =dtDeatailsService.getoverloadedDetails(officeid,subdiv,monthAlt,model,town,result,zone,circle);
			 
			 return DtList;
			 
			// System.out.println(result);
		  }
		  
		  List<?> DtList =dtDeatailsService.getoverloadedDetails(officeid,subdiv,monthAlt,model,town,towncode,zone,circle);
		  System.out.println("dt list is----"+DtList);
		return DtList;
	}
	
	@RequestMapping(value = "/getunderloadedSummaryDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getunderloadedSummaryDetail(HttpServletRequest request, ModelMap model) {
		 System.out.println("inside ntwrk------");
		  String officeid = request.getParameter("id"); 
		  String subdiv =request.getParameter("subdiv"); 
		  String month = request.getParameter("month");
		  String town = request.getParameter("town");
		  String monthAlt=month.replaceAll("[\\s\\-()]", "");
		  String zone = request.getParameter("zone");
		  String circle = request.getParameter("circle");
		  String towncode = request.getParameter("towncode");
		  System.err.println("month is-------"+zone);
		  
		  
		  	if(towncode.equalsIgnoreCase("undefined")) {
			  
			  String qry = "select distinct towncode from meter_data.town_master where towncode like '"+officeid+"'";
			  
			  System.out.println(qry);
			  
			 String result = (String) entityManager.createNativeQuery(qry).getSingleResult();
			 
			 List<?> DtList =dtDeatailsService.getunderloadedDetails(officeid,subdiv,monthAlt,model,town,circle,zone,result);
			 
			 return DtList;
			 
			// System.out.println(result);
		  }
		  
		  List<?> DtList =dtDeatailsService.getunderloadedDetails(officeid,subdiv,monthAlt,model,town,circle,zone,towncode);
		  System.out.println("dt list is----"+DtList);
		return DtList;
	}
	
	@RequestMapping(value = "/getunbalancedSummaryDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getunbalancedSummaryDetail(HttpServletRequest request, ModelMap model) {
		 System.out.println("inside ntwrk------");
		  String officeid = request.getParameter("id"); 
		  String subdiv =request.getParameter("subdiv"); 
		  String month = request.getParameter("month");
		  String town = request.getParameter("town");
		  String monthAlt=month.replaceAll("[\\s\\-()]", "");
		  System.err.println("month is-------"+monthAlt);
		  String zone = request.getParameter("zone");
		  String circle = request.getParameter("circle");
		  String towncode = request.getParameter("towncode");
		  
		  System.out.println(circle);
		  
		  	if(towncode.equalsIgnoreCase("undefined")) {
			  
			  String qry = "select distinct towncode from meter_data.town_master where towncode like '"+officeid+"'";
			  
			  System.out.println(qry);
			  
			 String result = (String) entityManager.createNativeQuery(qry).getSingleResult();
			 
			 List<?> DtList =dtDeatailsService.getunbalancedDetails(officeid,subdiv,monthAlt,model,town,zone,circle,result);
			 
			 return DtList;
			 
			// System.out.println(result);
		  }
		  
		  List<?> DtList =dtDeatailsService.getunbalancedDetails(officeid,subdiv,monthAlt,model,town,zone,circle,towncode);
		  System.out.println("dt list is----"+DtList);
		return DtList;
	}
	
	@RequestMapping(value = "/gettotalDTDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> gettotalDTDetail(HttpServletRequest request, ModelMap model) {
		 System.out.println("inside ntwrk------");
		  String officeid = request.getParameter("id"); 
		  String subdiv =request.getParameter("subdiv"); 
		  String month = request.getParameter("month");
		  String monthAlt=month.replaceAll("[\\s\\-()]", "");
		  String town = request.getParameter("town");
		  String otown = request.getParameter("otown");
		  String zone = request.getParameter("zone");
		  String circle = request.getParameter("circle");
		  String towncode = request.getParameter("towncode");
		  String circlecode = request.getParameter("circlecode");
		  String zonecode = request.getParameter("zonecode");
		  
		  System.out.println(officeid);
		  
		  if(towncode.equalsIgnoreCase("undefined")) {
			  
			  String qry = "select distinct towncode from meter_data.town_master where towncode like '"+officeid+"'";
			  
			  System.out.println(qry);
			  
			 String result = (String) entityManager.createNativeQuery(qry).getSingleResult();
			 
			 List<?> DtList =dtDeatailsService.gettotalDTDetails(officeid,subdiv,monthAlt,model,town,otown,zone,circle,result,circlecode,zonecode);
			 
			 return DtList;
			 
			// System.out.println(result);
		  }
		  
		//  System.err.println("month is-------"+monthAlt);
		  List<?> DtList =dtDeatailsService.gettotalDTDetails(officeid,subdiv,monthAlt,model,town,otown,zone,circle,towncode,circlecode,zonecode);
		  System.out.println("dt list is----"+DtList);
		return DtList;
	}
	
	@RequestMapping(value = "/dtHealthReport", method = { RequestMethod.POST, RequestMethod.GET })
	public String dtHealthReport(ModelMap model, HttpServletRequest request, HttpSession session) {

		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		return "dtHealthReport";
	}

	@RequestMapping(value = "/getDTHealthData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDTHealthData(HttpServletRequest request) {
		List<?> list = new ArrayList<>();
		
		String region = request.getParameter("zone");
		String subdiv = request.getParameter("subdiv");
		String rdngmnth = request.getParameter("rdngmnth");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String town = request.getParameter("town");
	//	System.out.println(town);
		String towncode=request.getParameter("town");
		list = dtHealthService.getDTHealthReport(region,subdiv, rdngmnth, circle, division,town,towncode);
		return list;
	}
	
	@RequestMapping(value="/DThealthreportPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void DThealthreportPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("townn");
		String month=request.getParameter("month");
		String townname=request.getParameter("townname");
		String zone1="",crcl="",twn="",townname1="";
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
		if(townname.equalsIgnoreCase("ALL"))
		{
			townname1="%";
		}else {
			townname1=townname;
			//System.err.println();
		
		
	}
		
		dtHealthService.getDThealthreportPDF(request, response, zone1,crcl, twn, month,townname1);
	}
	@RequestMapping(value = "/dtConsumption", method = { RequestMethod.POST, RequestMethod.GET })
	public String dtConsumption(ModelMap model, HttpServletRequest request, HttpSession session) {

		List<?> circleList = consumermasterService.getCircle();
	    model.put("circlelist", circleList);
	   // List<?> dtList=consumermasterService.getdtdata();
		//model.put("dtlist", dtList);
		return "dtConsumption";
	}
	
	@RequestMapping(value = "/getTownsBaseOnSubdiv", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getTownsBaseOnSubdiv(ModelMap model, HttpServletRequest request, HttpSession session)
	{
        String circle=request.getParameter("circle");
        String division=request.getParameter("division");
        String subdivision=request.getParameter("subdivision");
        List<?> townlist = consumermasterService.getTownNameandCodebySubdiv(circle, division, subdivision);
        model.put("townlist", townlist);
		return townlist;
	}
	
	@RequestMapping(value = "/individualdtConsumptionData", method = { RequestMethod.POST, RequestMethod.GET })
	public String individualdtConsumptionData(ModelMap model, HttpServletRequest request, HttpSession session) {
      

		List<?> circleList = consumermasterService.getCircle();
	    model.put("circlelist", circleList);

		return "individualdtConsumptionData";
	}
	
	@RequestMapping(value = "/viewDTConsumptionData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> viewDTConsumptionData(@RequestParam String selmonth,ModelMap model, HttpServletRequest request, HttpSession session) {

		List<?> list = new ArrayList<>();
		String zone=request.getParameter("zone");
		String circlee=request.getParameter("circle");
		String divisionn=request.getParameter("division");
		String subdivisionn=request.getParameter("subdiv");
		String towncode=request.getParameter("town").split("\\,")[0];
		//String townname=request.getParameter("town").split("\\,")[1];
		String feederTpId = request.getParameter("feederTpId").trim();
		String periodd=request.getParameter("period");
		String seldatee=request.getParameter("seldate");
		String selmonthh=request.getParameter("selmonth");
		
		list = dtDeatailsService.getdtconsumdata(zone,circlee, divisionn, subdivisionn, towncode,feederTpId,periodd, seldatee, selmonthh);

		return list;
		
	} 
	
	
	
	
	@RequestMapping(value = "/dtConsumptionpdf", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody void  dtConsumptionpdf(HttpServletResponse response, HttpServletRequest request, HttpSession session) {

		List<?> list = new ArrayList<>();
		String circlee=request.getParameter("circle");
		String divisionn=request.getParameter("division");
		String subdivisionn=request.getParameter("subdiv");
		String towncode=request.getParameter("town").split("\\,")[0];
		//String townname=request.getParameter("town").split("\\,")[1];
		String periodd=request.getParameter("period");
		String seldatee=request.getParameter("seldate");
		String selmonthh=request.getParameter("selmonth");
		String zone = request.getParameter("zone");
		String frommonthh=request.getParameter("frommonth");
		String tomonthh=request.getParameter("tomonth");
		String dtt=request.getParameter("dt");
		
		System.out.println(frommonthh);
		System.out.println(tomonthh);
		System.out.println(zone);
		System.out.println(frommonthh);
		System.out.println(frommonthh);
		
		
		 dtDeatailsService.dtConsumptionpdf( request,response,circlee, towncode, periodd, seldatee, selmonthh,zone,frommonthh,tomonthh,dtt);


		
	} 
	
	
	@RequestMapping(value = "/viewIndividualDTConsumptiondata", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> viewIndividualDTConsumptiondata(ModelMap model, HttpServletRequest request, HttpSession session) {
	
		String circlee=request.getParameter("circle");
		String divisionn=request.getParameter("division");
		String subdivv=request.getParameter("subdiv");
		String towncode=request.getParameter("town");
		String townname="";
		String periodd=request.getParameter("period");
		System.out.println("period is----"+periodd);
		String dtt=request.getParameter("dt");
		String fromdatee=request.getParameter("fromdate");
		String todatee=request.getParameter("todate");
		String frommonthh=request.getParameter("frommonth");
		String tomonthh=request.getParameter("tomonth");
		String ipwisedatee=request.getParameter("ipwisedate");
		
		 List result = new ArrayList<>();
		 List list = new ArrayList<>();
		 List list1 = new ArrayList<>();
			
			if("Date".equalsIgnoreCase(periodd)) {
				System.out.println("inside date");
				list = dtDeatailsService.getIndividualDtConsumData1(circlee, divisionn, subdivv, towncode, townname,dtt, periodd, fromdatee, todatee);
				list1=dtDeatailsService.getNoOfMeterCount(dtt);
				
			}else if("Month".equalsIgnoreCase(periodd)) {
				System.out.println("inside month");
				list = dtDeatailsService.getIndividualDtConsumData2(circlee, divisionn, subdivv, towncode, townname,dtt, periodd, frommonthh, tomonthh);
				list1=dtDeatailsService.getNoOfMeterCount(dtt);
			}else if("IP Wise".equalsIgnoreCase(periodd)){
				System.out.println("inside ipwise");
				list = dtDeatailsService.getIndividualDtConsumData3(circlee, divisionn, subdivv, towncode, townname,dtt, periodd, ipwisedatee);
				list1=dtDeatailsService.getNoOfMeterCount(dtt);
			}
			result.add(list);
			result.add(list1);
			System.out.println(result.size());
		return result;
		
	}
	
	@RequestMapping(value = "/viewIndividualDTConsumdata1", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> viewIndividualDTConsumdata1(@RequestParam String town,ModelMap model, HttpServletRequest request, HttpSession session) {
		    List<?> list = null;
			String towncode=request.getParameter("town").split("\\,")[0];
			String townname=request.getParameter("town").split("\\,")[1];

			list = dtDeatailsService.getIndividualDtConsumData11(towncode);
		
            return list;
		
	}
	
	@RequestMapping(value = "/getDTBaseOnTowns", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getDTBaseOnTowns(ModelMap model, HttpServletRequest request, HttpSession session)
	{
        String towncode=request.getParameter("towncode");
         List<?> townlist = consumermasterService.getdtdata(towncode);
        model.put("townlist", townlist);
		return townlist;
	}
	
	@RequestMapping(value = "/getDTDataBasedOnDtcode", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getDTDataBasedOnDtcode(ModelMap model, HttpServletRequest request, HttpSession session)
	{
		 List list1 = new ArrayList<>();
        String dtcode=request.getParameter("dtcode");
        list1=dtDeatailsService.getNoOfMeterCount(dtcode);
		return list1;
	}
	
	@RequestMapping(value="/DTLoadingSummPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void DTLoadingSummPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("townn");
		String month=request.getParameter("month");
		String townname=request.getParameter("townname1");

		String zone1="",crcl="",twn="",townname1="";
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
		
		if(townname.equalsIgnoreCase("ALL"))
		{
			townname1="ALL";
		}else {
			townname1=townname;
		}
		
		dtDeatailsService.getdtLoadSummpdf(request, response, zone1,crcl, twn, month,townname1);
	}
	
	
	@RequestMapping(value = "/checkDthealth/{billmonth}",
			method = { RequestMethod.POST, RequestMethod.GET }
			)
	public String checkDthealth(@PathVariable String billmonth){

		dtHealthService.processDTHealthData(billmonth);
			return "Sucess";
}
	
}

