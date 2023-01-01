package com.bcits.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.VeeRuleService;
import com.bcits.service.DtDetailsService;
import com.bcits.service.ReportService;

@Controller
public class MDMdashboardController {

	/*
	 * @Autowired private EntityManager entityManager;
	 */

	@Autowired
	private ReportService reportService;

	@Autowired
	private FeederMasterService feederMasterService;

	@Autowired
	private AmiLocationService amiLocattinService;

	@Autowired
	private FeederDetailsService feederdetailsservice;

	@RequestMapping(value = "/consumptionLoadAnalysis", method = { RequestMethod.POST, RequestMethod.GET })
	public String consumptionLoadAnalysis(ModelMap model, HttpServletRequest requst, HttpSession session) {

		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		return "consumptionLoadAnalysis";
	}

	@RequestMapping(value = "/getconsumerData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getconsumerData(HttpServletRequest request, Model model) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("sdoCode").trim();
		String consumerCatgry = request.getParameter("consumerCatgryId").trim();
		String acno = request.getParameter("accnoId").trim();
		String kno = request.getParameter("knoId").trim();
		String mtrno = request.getParameter("ConsumermtrnoId").trim();
		List<?> consumerData = reportService.getconsumerData(zone, circle, division, subdivision, kno, acno, mtrno,
				consumerCatgry);
		return consumerData;

	}

	@RequestMapping(value = "/getdtdata", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getdtdata(HttpServletRequest request, Model model) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("sdoCode").trim();
		String crossdt = request.getParameter("crossdt").trim();
		String DtcodeId = request.getParameter("DtcodeId").trim();
		String dtmtrnoId = request.getParameter("dtmtrnoId").trim();
		List<?> dtdata = reportService.getdtdata(zone, circle, division, subdivision, crossdt, DtcodeId, dtmtrnoId);
		// System.out.println("dtdata--------"+dtdata);
		return dtdata;

	}

	@RequestMapping(value = "/getfeederdata", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getfeederdata(HttpServletRequest request, Model model) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("sdoCode").trim();
		String FedrcodeId = request.getParameter("FedrcodeId").trim();
		String crossfeeder = request.getParameter("crossfeeder").trim();
		String fedrmtrnoId = request.getParameter("fedrmtrnoId").trim();
		List<?> feederdata = reportService.getfeederdata(zone, circle, division, subdivision, FedrcodeId, crossfeeder,
				fedrmtrnoId);
		return feederdata;
	}

	@RequestMapping(value = "/netwotkperformance", method = { RequestMethod.POST, RequestMethod.GET })
	public String networkPerformance(ModelMap model, HttpServletRequest requst, HttpSession session) {
		List<?> ZoneList = feederdetailsservice.getDistinctZone();
		model.addAttribute("ZoneList", ZoneList);
		return "networkAssetPerformance";
	}

	@RequestMapping(value = "/getnetworkperformancedata/{locType}/{rdngmonth}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object getDtNetworkData(@PathVariable String locType, @PathVariable String rdngmonth,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("subdivision");
		String town = request.getParameter("town");
		List<Integer[]> sitecodes = new LinkedList<>();
		String officeCode = "";
		String crossfdr="1";
		if (division.equalsIgnoreCase("")) {
			division = "%";
			subdivision = "%";
		}
		if (subdivision.equalsIgnoreCase("")) {
			subdivision = "%";
		}
		if (subdivision.equalsIgnoreCase("%") && division.equalsIgnoreCase("%")) {

			// circle
			// circle="'%'";
			sitecodes = amiLocattinService.getSitcCodeByCircle(circle,zone,town);
			for (int i = 0; i < sitecodes.size(); i++) {
				if (i < sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "',";
				} else if (i == sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "'";
				}
			}
		} else if (subdivision.equalsIgnoreCase("%") && !division.equalsIgnoreCase("%")) {

			// division
			sitecodes = amiLocattinService.getSiteCodeByDivision(circle, division,zone, town);

			for (int i = 0; i < sitecodes.size(); i++) {
				if (i < sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "',";
				} else if (i == sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "'";
				}
			}
		}

		else if (!subdivision.equalsIgnoreCase("%")) {
			officeCode = "'" + amiLocattinService.subDivisionCode(subdivision) + "'";
		}
		// System.err.println("in ===="+subdivision+" "+circle+division);

		if (("dt").equalsIgnoreCase(locType)) {
			//System.err.println("hiii DT.......");
			return reportService.getDtHealthRptData(zone,circle,rdngmonth, officeCode, town);
		} else if (("FEEDER METER".equalsIgnoreCase(locType)|| "BOUNDARY METER".equalsIgnoreCase(locType)) ) {
			//System.err.println("hiii feeder and doundary.......");
			if("FEEDER METER".equalsIgnoreCase(locType)) {
				crossfdr="0";
			} 
			return reportService.getFeederHealthRptData(zone,circle,rdngmonth, officeCode, town,crossfdr);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getgraphicalnetwork/{locType}/{rdngmonth}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object getGraphicalNetworkData(@PathVariable String locType, @PathVariable String rdngmonth,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("subdivision");
		String town = request.getParameter("town");
		List<Integer[]> sitecodes = new LinkedList<>();
		String officeCode = "";
		if (division.equalsIgnoreCase("")) {
			System.out.println("in ifff" + division + subdivision);
			division = "%";
			subdivision = "%";
		}
		if (subdivision.equalsIgnoreCase("")) {
			subdivision = "%";
		}
		if (subdivision.equalsIgnoreCase("%") && division.equalsIgnoreCase("%")) {
			sitecodes = amiLocattinService.getSitcCodeByCircle(circle, zone, town);
			// ListIterator<Integer[]> listIterator = sitecodes.listIterator();
			for (int i = 0; i < sitecodes.size(); i++) {
				if (i < sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "',";
				} else if (i == sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "'";
				}
			}
		} else if (subdivision.equalsIgnoreCase("%") && !division.equalsIgnoreCase("%")) {
			// division
			sitecodes = amiLocattinService.getSiteCodeByDivision(circle, division,zone,town);
			for (int i = 0; i < sitecodes.size(); i++) {
				if (i < sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "',";
				} else if (i == sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "'";
				}
			}
		} else if (!subdivision.equalsIgnoreCase("%")) {
			officeCode = "'" + amiLocattinService.subDivisionCode(subdivision) + "'";
		}
		System.out.println("id gggg"+officeCode);
		return reportService.getNetworkGraphicalData(rdngmonth, officeCode, locType);

	}

	

	@RequestMapping(value = "/NetworkAssetDetailsPdf", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object NetworkAssetDetailsPdf(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		String zone = request.getParameter("zne");
		String circle = request.getParameter("cir");
		String division = request.getParameter("div");
		String subdivision = request.getParameter("subdvn");
		String loctype = request.getParameter("locType");
		String month = request.getParameter("rdngmonth");
		String town = request.getParameter("town");
		String townname = request.getParameter("townname");
		
		//String townname = request.getParameter("townname");
System.out.println(subdivision +"--------------------"+ division );
		List<Integer[]> sitecodes = new LinkedList<>();
		String officeCode = "";
		if (zone.equalsIgnoreCase("ALL")) {
			zone = "%";
		}
		if (circle.equalsIgnoreCase("ALL")) {
			circle = "%";
		}

		if (division.equalsIgnoreCase("ALL")) {
			division = "%";
		}
		if (subdivision.equalsIgnoreCase("ALL")) {
			subdivision = "%";
		}
		
		if (town.equalsIgnoreCase("ALL")) {
			town = "%";
		}

		/*
		 * if (division.equalsIgnoreCase("") || division.equalsIgnoreCase("ALL")) {
		 * division = "%"; subdivision = "%"; } if (subdivision.equalsIgnoreCase("") ||
		 * subdivision.equalsIgnoreCase("ALL")) { subdivision = "%"; }
		 */
		if (subdivision.equalsIgnoreCase("%") && division.equalsIgnoreCase("%")) {

			// circle
			// circle="'%'";
			sitecodes = amiLocattinService.getSitcCodeByCircle(circle, zone, town);
			for (int i = 0; i < sitecodes.size(); i++) {
				if (i < sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "',";
				} else if (i == sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "'";
				}
			}
		} else if (subdivision.equalsIgnoreCase("%") && !division.equalsIgnoreCase("%")) {

			// division
			sitecodes = amiLocattinService.getSiteCodeByDivision(circle, division,zone,town);

			for (int i = 0; i < sitecodes.size(); i++) {
				if (i < sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "',";
				} else if (i == sitecodes.size() - 1) {
					officeCode += "'" + sitecodes.get(i) + "'";
				}
			}
		}

		else if (!subdivision.equalsIgnoreCase("%")) {
			officeCode = "'" + amiLocattinService.subDivisionCode(subdivision) + "'";
		}

		if (("dt").equalsIgnoreCase(loctype)) {
			return reportService.getDtHealthRptPdf(request, response, month, officeCode, zone,circle, 
					loctype, town);
		} else if ("FEEDER METER".equalsIgnoreCase(loctype) || "BOUNDARY METER".equalsIgnoreCase(loctype)) {
			return reportService.getFeederHealthRptPdf(request, response, month, officeCode, zone,circle, loctype, town,townname);
		} else {
			return null;
		}

	}
	
	
	@RequestMapping(value = "/frequencyobviation", method = { RequestMethod.POST, RequestMethod.GET })
	public String frequencyObviation(ModelMap model, HttpServletRequest requst, HttpSession session) {
		List<?> ZoneList = feederdetailsservice.getDistinctZone();
	model.addAttribute("ZoneList", ZoneList);
		return "frequencyobviation";
	}
	
	@RequestMapping(value = "/getfrequencyobliviondata/{locType}/{rdngmonth}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object getfrequencyobliviondatagetfrequencyobliviondata(@PathVariable String locType, @PathVariable String rdngmonth,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String date = request.getParameter("date");
		//String locTypes = request.getParameter("locType");
		
		System.out.println("locType----------"+locType);
		return reportService.getfrequencyobliviondata(zone,circle,town,date, locType);

	}
	
	
	@RequestMapping(value = "/saidicaidi", method = { RequestMethod.POST, RequestMethod.GET })
	public String saidiCaidi(ModelMap model, HttpServletRequest requst, HttpSession session) {
		List<?> ZoneList = feederdetailsservice.getDistinctZone();
		model.addAttribute("ZoneList", ZoneList);
		return "saidicaidi";
	}
	
	@RequestMapping(value = "/getSaidiData", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object getSaidiData(HttpServletRequest request)  {
		String town_code = request.getParameter("town");
		String loc_type = request.getParameter("locType");
		String month_year = request.getParameter("monthyear");
		return reportService.getSaidiData(town_code, loc_type,month_year);

	}

}
