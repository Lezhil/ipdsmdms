/**
 * 
 */
package com.bcits.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bcits.entity.FeederEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.BoundaryDetailsService;
import com.bcits.mdas.service.FeederBoundaryDetailsService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.utility.FilterUnit;

/**
 * @author Tarik
 *
 */
@Controller
public class BoundaryDetailsController {

	@Autowired
	private FeederDetailsService feederdetailsservice;

	@Autowired
	private FeederBoundaryDetailsService feederboundarydetailsservice;

	@Autowired
	private MasterMainService masterMainService;

	@Autowired
	private BoundaryDetailsService boundaryDetailsService;

	String msg = "";
	String msg1= "";

	int UpdateFlag = 0;

	int uploadFlag = 0;

	@RequestMapping(value = "/showTownByCircle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getTowndetails(HttpServletRequest request) {
		return feederboundarydetailsservice.getTownByCircle(request.getParameter("circle"),request.getParameter("zone"));
	}

	@RequestMapping(value = "/showSubStaTionByTown", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getSubStaTionByTown(HttpServletRequest request) {
		String town=request.getParameter("town");
		String circle=request.getParameter("circle");
		String zone=request.getParameter("zone");
		
		return feederboundarydetailsservice.getSubStaTionByTown(town,zone,circle);
	}

	@RequestMapping(value = "/showFeederDetailsData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List showFeederDetailsData(HttpServletRequest request, Model model) {
		String ssid = request.getParameter("ssid").trim();
		String tp_towncode = request.getParameter("town").trim();
		List<?> feederdetails = feederboundarydetailsservice.getfeederdetails(ssid, tp_towncode);
		// System.out.println(feederdetails);
		return feederdetails;
	}

	@RequestMapping(value = "/getlatestBoundaryId", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getlatestBoundaryId(HttpServletRequest request, Model model) {
		String feedercode = request.getParameter("feedercode").trim();
		String latestboundaryRuleId = boundaryDetailsService.getlatestBoundaryId(feedercode);
		// System.out.println(latestboundaryRuleId);
		return latestboundaryRuleId;
	}

	@Transactional
	@RequestMapping(value = "/addBoundaryDetailsData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String addBoundaryDetailsData(HttpServletRequest request, Model model) {
		String fdrid = request.getParameter("fdrid").trim();
		String feedercode = request.getParameter("feedercode").trim();
		String feedername = request.getParameter("feedername").trim();
		String bdrid = request.getParameter("bdrid").trim();
		String bdrname = request.getParameter("bdrname").trim();
		String bdrloc = request.getParameter("bdrloc").trim();
		String meterStatus = request.getParameter("meterStatus").trim();

		String town_code = request.getParameter("town").trim();

		int status;
		if (meterStatus.equalsIgnoreCase("True")) {
			status = 1;
		} else {
			status = 0;
		}
		String mtrno = request.getParameter("mtrno").trim();
		String mtrmkr = request.getParameter("mtrmkr").trim();
		String ctno = request.getParameter("ctno").trim();
		String ptno = request.getParameter("ptno").trim();
		// Integer mfid =
		// ("".equals(request.getParameter("mfid").trim())?null:Integer.valueOf(request.getParameter("mfid").trim()));
		String mf = request.getParameter("mfid").trim();

		String mtrratio = request.getParameter("mtrratio").trim();
		String expImp = request.getParameter("expImp").trim();

		String sscode = request.getParameter("sscode").trim();
		String tp_sscode = request.getParameter("tp_sscode").trim();
		Integer sdocode = Integer.valueOf(request.getParameter("sdocode").trim());
		String tp_sdocode = request.getParameter("tp_sdocode").trim();
		String cp = request.getParameter("cp").trim();
		String voltagelevel = request.getParameter("voltagelevel").trim();

		HttpSession session = request.getSession(false);
		model.addAttribute("userType", session.getAttribute("userType"));

		try {
//			BoundaryMetersEntity v = new BoundaryMetersEntity();
//			v.setFeedercode(fdrid);
//			v.setTp_feedercode(feedercode);	
//			v.setFeedername(feedername);
//			v.setSs_code(sscode);
//			v.setTp_sscode(tp_sscode);
//			v.setSdocode(sdocode);
//			v.setTp_sdocode(tp_sdocode);
//			v.setBoundary_name(bdrname);
//			v.setBoundary_location(bdrloc);
//			v.setBoundary_id(bdrid);
//			v.setMeter_installed(status);
//			v.setMeter_no(mtrno);
//			v.setMeter_make(mtrmkr);
//			v.setCt_ratio(ctno);
//			v.setPt_ratio(ptno);
//			v.setMf(mfid);		
//			v.setMeter_ratio(mtrratio);
//			v.setImp_exp(expImp);
//			v.setCreatedby(session.getAttribute("username").toString());
//			v.setCreateddate(new Timestamp(System.currentTimeMillis()));
//			boundaryDetailsService.save(v);

			FeederEntity fe = new FeederEntity();
			fe.setVoltagelevel(Double.parseDouble(voltagelevel));
			fe.setFeedername(feedername);
			fe.setOfficeid(sdocode);
			fe.setParentid(sscode);
			fe.setTpparentid(tp_sscode);
			fe.setManufacturer(mtrmkr);
			fe.setMeter_installed(status);
			fe.setMeterno(mtrno);
			if (!"".equals(mf)) {
				fe.setMf(Double.parseDouble(mf));
			}
//			if (cp != "") {
//				fe.setConsumppercent(Double.parseDouble(cp));
//			}
			fe.setCrossfdr(1);
			fe.setTpfdrid(feedercode);
			fe.setEntryby(session.getAttribute("username").toString());
			fe.setEntrydate(new Timestamp(System.currentTimeMillis()));
			if (fdrid != null && fdrid != "") {
				fe.setFdrid(fdrid);
			}
			fe.setBoundary_id(bdrid);
			fe.setBoundary_name(bdrname);
			fe.setBoundary_location(bdrloc);
			fe.setCt_ratio(ctno);
			fe.setPt_ratio(ptno);
			fe.setBoundry_feeder(true);
			fe.setMeter_ratio(mtrratio);
			fe.setImp_exp(expImp);
			fe.setDeleted(0);
			fe.setTpTownCode(town_code);
			// fe.setTp_sdocode(tp_sdocode);
			if ("".equals(mtrno)) {
				fe.setMeterno(null);
				fe.setManufacturer(null);
				fe.setCt_ratio(null);
				fe.setPt_ratio(null);
				fe.setMeter_ratio(null);
				fe.setImp_exp(null);
			}
			feederdetailsservice.save(fe);
			// System.out.println(mtrno+"===mtrno");
			if (!"".equals(mtrno)) {
				feederdetailsservice.setMeterInventoflag(mtrno, session.getAttribute("username").toString());
			}

			// Saving data to master main
			/*
			 * MasterMainEntity masterMain = new MasterMainEntity();
			 * 
			 * HashMap<String, String> hh =
			 * boundaryDetailsService.getlocationHireachy(feedercode, tp_sdocode); String
			 * zone = hh.get("ZONE"); String circle = hh.get("CIRCLE"); String division =
			 * hh.get("DIVISION"); String subdivision = hh.get("SUBDIVISION"); String discom
			 * = hh.get("DISCOM"); String sdocode1 = hh.get("SDOCODE"); String ss_name =
			 * hh.get("SS_NAME"); //System.out.println("sdocode1="+sdocode1);
			 * 
			 * masterMain.setZone(zone); masterMain.setCircle(circle);
			 * masterMain.setDivision(division); masterMain.setSubdivision(subdivision);
			 * masterMain.setSubstation(ss_name); masterMain.setFdrname(feedername);
			 * masterMain.setFdrcode(fdrid); masterMain.setMtrno(mtrno);
			 * masterMain.setMtrmake(mtrmkr); masterMain.setCt_ratio(ctno);
			 * masterMain.setPt_ratio(ptno); masterMain.setMla(bdrid);
			 * masterMain.setMf(request.getParameter("mfid").trim());
			 * masterMain.setDiscom(discom);
			 * masterMain.setSdocode(request.getParameter("sdocode").trim());
			 * masterMain.setFdrcategory("BOUNDARY METER");
			 * masterMainService.save(masterMain);
			 */

			return "succ";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}

	}

	@Transactional
	@RequestMapping(value = "/editBoundaryDetailsData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String editBoundaryDetailsData(HttpServletRequest request, Model model) {
//		String fdrid  = request.getParameter("fdrid").trim();
		String feedercode = request.getParameter("feedercode").trim();
		String feedername = request.getParameter("feedername").trim();

		String bdrid = request.getParameter("bdrid").trim();
		String bdrname = request.getParameter("bdrname").trim();
		String bdrloc = request.getParameter("bdrloc").trim();
		String meterStatus = request.getParameter("meterStatus").trim();

		int status;
		if (meterStatus.equalsIgnoreCase("True")) {
			status = 1;
		} else {
			status = 0;
		}
		String mtrno = request.getParameter("mtrno").trim();
		String mtrmkr = request.getParameter("mtrmkr").trim();
		String ctno = request.getParameter("ctno").trim();
		String ptno = request.getParameter("ptno").trim();
		String mf = request.getParameter("mfid").trim();
		String mtrratio = request.getParameter("mtrratio").trim();
		String expImp = request.getParameter("expImp").trim();
		String cp = request.getParameter("cp").trim();

		HttpSession session = request.getSession(false);
		model.addAttribute("userType", session.getAttribute("userType"));

		try {
			FeederEntity v = feederdetailsservice.getDetailsById(bdrid);
			if (v != null) {
				v.setManufacturer(mtrmkr);
				v.setMeter_installed(status);
				v.setMeterno(mtrno);
				if (mf != "") {
					v.setMf(Double.parseDouble(mf));
				}
//				if (cp != "") {
//					v.setConsumppercent(Double.parseDouble(cp));
//				}
				v.setUpdateby(session.getAttribute("username").toString());
				v.setUpdatedate(new Timestamp(System.currentTimeMillis()));
				v.setBoundary_name(bdrname);
				v.setBoundary_location(bdrloc);
				v.setCt_ratio(ctno);
				v.setPt_ratio(ptno);
				v.setMeter_ratio(mtrratio);
				v.setImp_exp(expImp);
				System.out.println("Hiii");
				feederdetailsservice.update(v);
			}

			// Updating data to master main
			/*
			 * MasterMainEntity masterMain = masterMainService.getFeederDetailsByID(bdrid);
			 * if (masterMain != null) { masterMain.setFdrname(feedername);
			 * masterMain.setMtrno(mtrno); masterMain.setMtrmake(mtrmkr);
			 * masterMain.setCt_ratio(ctno); masterMain.setPt_ratio(ptno);
			 * masterMain.setMf(request.getParameter("mfid").trim());
			 * masterMainService.update(masterMain); }
			 */

			return "succ";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}

	}

	@Transactional
	@RequestMapping(value = "/editBoundaryDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String editBoundaryDetails(HttpServletRequest request, Model model) {

		String feedercode = request.getParameter("feedercode").trim();
		// String feedername = request.getParameter("feedername").trim();

		String bdrid = request.getParameter("bdrid").trim();
		String bdrname = request.getParameter("bdrname").trim();
		String bdrloc = request.getParameter("bdrloc").trim();
		// String meterStatus = request.getParameter("meterStatus").trim();

//		int status;
//		if (meterStatus.equalsIgnoreCase("True")) {
//			status = 1;
//		} else {
//			status = 0;
//		}
		String mtrno = request.getParameter("mtrno").trim();
		String mtrmkr = request.getParameter("mtrmkr").trim();
		String ctno = request.getParameter("ctno").trim();
		String ptno = request.getParameter("ptno").trim();
		String mf = request.getParameter("mfid").trim();
		// String mtrratio = request.getParameter("mtrratio").trim();
		String expImp = request.getParameter("expImp").trim();
		// String cp = request.getParameter("cp").trim();

		HttpSession session = request.getSession(false);
		model.addAttribute("userType", session.getAttribute("userType"));

		try {
			FeederEntity v = feederdetailsservice.getDetailsById(bdrid);
			if (v != null) {
				v.setManufacturer(mtrmkr);
				// v.setMeter_installed(status);
				v.setMeterno(mtrno);
				if (mf != "") {
					v.setMf(Double.parseDouble(mf));
				}
//				if (cp != "") {
//					v.setConsumppercent(Double.parseDouble(cp));
//				}
				v.setUpdateby(session.getAttribute("username").toString());
				v.setUpdatedate(new Timestamp(System.currentTimeMillis()));
				v.setBoundary_name(bdrname);
				v.setBoundary_location(bdrloc);
				v.setCt_ratio(ctno);
				v.setPt_ratio(ptno);
				// v.setMeter_ratio(mtrratio);
				v.setImp_exp(expImp);
				feederdetailsservice.update(v);
			}

			// Updating data to master main
			MasterMainEntity masterMain = masterMainService.getFeederDetailsByID(bdrid);
			if (masterMain != null) {
				// masterMain.setFdrname(feedername);
				masterMain.setAccno(bdrname);
				masterMain.setMla(bdrid);
				masterMain.setMtrno(mtrno);
				masterMain.setMtrmake(mtrmkr);
				masterMain.setCt_ratio(ctno);
				masterMain.setPt_ratio(ptno);
				masterMain.setMf(request.getParameter("mfid").trim());
				masterMainService.update(masterMain);
			}

			return "succ";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}

	}

	@Transactional
	@RequestMapping(value = "/deleteBoundaryMeterData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String deleteBoundaryMeterData(HttpServletRequest request, Model model) {

		String bdrid = request.getParameter("bdrid").trim();
		String bdrname = request.getParameter("bdrname").trim();

		HttpSession session = request.getSession(false);
		model.addAttribute("userType", session.getAttribute("userType"));

		try {
			FeederEntity v = feederdetailsservice.getDetailsById(bdrid);
			if (v != null) {
				feederdetailsservice.delete(v.getId());
				// v.setDeleted(1);
				// feederdetailsservice.update(v);
			}
			// System.out.println("bdrid=="+bdrid);
			MasterMainEntity masterMain = masterMainService.getFeederDetailsByID(bdrid);
			/*
			 * if (masterMain != null) { masterMainService.delete(masterMain.getId()); }
			 */
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}

	}

	@RequestMapping(value = "/boundaryDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public String fdrdata(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
//			List<?> zoneList = feederdetailsservice.getDistinctZone();
//			model.addAttribute("zoneList", zoneList);
		try {

			HttpSession session = request.getSession(false);
			if (session == null) {
				return "redirect:./?sessionVal=expired";
			}

			List<?> circleList = feederdetailsservice.getcircle();
			model.put("circleList", circleList);

			String officeType = (String) session.getAttribute("officeType");
			String officeCode = (String) session.getAttribute("officeCode");
			String userType = (String) session.getAttribute("userType");
			String qry = null;
			String ssNameQry = null;

			if (officeType.equalsIgnoreCase("circle")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE circle_code = '" + officeCode + "' ";
			} else if (officeType.equalsIgnoreCase("division")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE division_code = '" + officeCode + "' ";
			} else if (officeType.equalsIgnoreCase("subdivision")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE sitecode = '" + officeCode + "' ";
			}

			String SubdivName = null;

			if (officeType.equalsIgnoreCase("subdivision")) {
				SubdivName = (java.lang.String) feederdetailsservice.getCustomEntityManager("postgresMdas")
						.createNativeQuery(qry).getSingleResult();
			}

			model.put("msg", msg);
			model.put("officeType", officeType);
			model.addAttribute("userType", userType);
			model.addAttribute("SubdivName", SubdivName);

			msg = "";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "boundaryDetails";
	}

	@RequestMapping(value = "/boundary", method = { RequestMethod.POST, RequestMethod.GET })
	public String newboundaryDetails(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		List<?> zoneList = feederdetailsservice.getDistinctZone();
			model.addAttribute("zoneList", zoneList);
		try {

			HttpSession session = request.getSession(false);
			if (session == null) {
				return "redirect:./?sessionVal=expired";
			}

			List<?> circleList = feederdetailsservice.getcircle();
			//model.put("circleList", circleList);

			String officeType = (String) session.getAttribute("officeType");
			String officeCode = (String) session.getAttribute("officeCode");
			String userType = (String) session.getAttribute("userType");
			String qry = null;
			String ssNameQry = null;

			if (officeType.equalsIgnoreCase("circle")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE circle_code = '" + officeCode + "' ";
			} else if (officeType.equalsIgnoreCase("division")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE division_code = '" + officeCode + "' ";
			} else if (officeType.equalsIgnoreCase("subdivision")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE sitecode = '" + officeCode + "' ";
			}

			String SubdivName = null;

			if (officeType.equalsIgnoreCase("subdivision")) {
				SubdivName = (java.lang.String) feederdetailsservice.getCustomEntityManager("postgresMdas")
						.createNativeQuery(qry).getSingleResult();
			}

		/*	if (uploadFlag == 1) {
				model.addAttribute("alert_type", "success");
				model.addAttribute("results", "Data Updated Sucessfully");
				uploadFlag = 0;
			} else if (uploadFlag == 2) {
				model.addAttribute("alert_type", "success");
				model.addAttribute("results", "Records are not avaliable in excel to upload");
				uploadFlag = 0;
			} else if (uploadFlag == 3) {
				model.addAttribute("results", "OOPS! Something went wrong!!");
				model.addAttribute("alert_type", "error");
				uploadFlag = 0;
			} else {
				model.addAttribute("results", "notDisplay");
			}*/

			model.put("msg", msg);
			model.put("msg1", msg);

			model.put("officeType", officeType);
			model.addAttribute("userType", userType);
			model.addAttribute("SubdivName", SubdivName);

			msg = "";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "boundary";
	}

//	@RequestMapping(value = "/getBoundaryDetailsData", method = { RequestMethod.GET, RequestMethod.POST})
//	public @ResponseBody List<BoundaryMetersEntity> getBoundaryDetailsData(HttpServletRequest request, Model model) 
//	{
//		String fdrcode = request.getParameter("fdrcode").trim();
//		String fdrname = request.getParameter("fdrname").trim();
//		List<BoundaryMetersEntity> list=boundaryDetailsService.getMeterDetailsByFdrcode(fdrcode);
//		
//	    return list;
//	}

	@RequestMapping(value = "/getBoundaryDetailsData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getBoundaryDetailsData(HttpServletRequest request, Model model) {
		String fdrcode = request.getParameter("fdrcode").trim();
		String fdrname = request.getParameter("fdrname").trim();
		List<?> list = feederdetailsservice.getMeterDetailsByFdrcode(fdrcode);

		return list;
	}

	@RequestMapping(value = "/getBoundaryData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getBoundaryData(HttpServletRequest request, Model model) {
		String town = request.getParameter("town").trim();
	String ssid = request.getParameter("ssid").trim();
	String circle = request.getParameter("circle");
	String zone = request.getParameter("zone");
		List<?> list = feederdetailsservice.getBoundaryMeterDetailsByTown(town, ssid,circle,zone);

		return list;
	}

	@RequestMapping(value = "/boundaryDetailsReport", method = { RequestMethod.POST, RequestMethod.GET })
	public String boundaryDetailsReport(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				// return "redirect:/login";
				return "redirect:./?sessionVal=expired";
			}

			List<?> circleList = feederdetailsservice.getcircle();
			model.put("circleList", circleList);

			String officeType = (String) session.getAttribute("officeType");
			String officeCode = (String) session.getAttribute("officeCode");
			String userType = (String) session.getAttribute("userType");
			String qry = null;

			if (officeType.equalsIgnoreCase("circle")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE circle_code = '" + officeCode + "' ";
			} else if (officeType.equalsIgnoreCase("division")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE division_code = '" + officeCode + "' ";
			} else if (officeType.equalsIgnoreCase("subdivision")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE sitecode = '" + officeCode + "' ";
			}

			String SubdivName = null;

			if (officeType.equalsIgnoreCase("subdivision"))
				SubdivName = (java.lang.String) feederdetailsservice.getCustomEntityManager("postgresMdas")
						.createNativeQuery(qry).getSingleResult();

			model.put("msg", msg);
			model.put("officeType", officeType);
			model.addAttribute("userType", userType);
			model.addAttribute("SubdivName", SubdivName);

			msg = "";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "boundaryDetailsReport";
	}

	@RequestMapping(value = "/generateReportData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List generateReportData(HttpServletRequest request, Model model) {
		// String circle = request.getParameter("circle").trim();
		// String townid = request.getParameter("townid").trim();
		// System.out.println("circle="+circle+"townid="+townid);
		List<?> feederdetails = feederboundarydetailsservice.generateBoundaryReportData();
		// System.out.println(feederdetails);
		return feederdetails;
	}

	@RequestMapping(value = "/boundarydetailspdf/{circle}/{town}/{ssid}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody void boundarydetailspdf(HttpServletRequest request, HttpServletResponse response,
			ModelMap model, @PathVariable String circle, @PathVariable String town, @PathVariable String ssid) {
		feederboundarydetailsservice.getBoundaryDetailsPdf(request, response, circle, town, ssid);
	}

	@RequestMapping(value = "/boundarypdf", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void boundarypdf(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String ssid = request.getParameter("ssid");
		String townnames=request.getParameter("townnames");
		String substationname= request.getParameter("substationname");
		//System.out.println(townnames);
		String zonename="",cirname="",townname="",substa="";
		
		if (zone.equalsIgnoreCase("ALL")){
			zonename="%";
		}else {
			zonename=zone;
		}
		if (circle.equalsIgnoreCase("ALL")){
			cirname="%";
		}else {
			cirname=circle;
		}
		if (town.equalsIgnoreCase("ALL")){
			townname="%";
		}else {
			townname=town;
		}
		if (ssid.equalsIgnoreCase("ALL")){
			substa="%";
		}else {
			substa=ssid;
		}
		feederboundarydetailsservice.getBoundaryPdf(request, response, zonename,cirname, townname, substa,townnames,substationname);
	}

	@RequestMapping(value = "/RFMReportPDF", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void RFMReportPDF(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		feederboundarydetailsservice.getRFMReportPDF(request, response);
	}

	@RequestMapping(value = "/downLoadSampleFile", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void downLoadSampleFile(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		try {
			PrintWriter out = response.getWriter();
			String type = request.getParameter("type").trim();

			if ("boundarySample".equalsIgnoreCase(type)) {
				File fileToDownload = new File(FilterUnit.sldImageServerPath + "/" + type + ".xlsx");
				FileInputStream fileInputStream = new FileInputStream(fileToDownload);
				int i;
				while ((i = fileInputStream.read()) != -1) {
					out.write(i);
				}
				fileInputStream.close();
				out.close();

			} else if ("dtSample".equalsIgnoreCase(type)) {

			} else if ("substationSample".equalsIgnoreCase(type)) {

			} else if ("townSample".equalsIgnoreCase(type)) {

			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/uploadBoundaryFile", method = { RequestMethod.GET, RequestMethod.POST })
	public String uploadBoundaryFile(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session) {
		// HttpSession session = request.getSession();
		String extension = null;
		String excel = "xlsx";

		try {

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myFile = multipartRequest.getFile("fileUpload");

			// File temp_file = new File(myFile.getOriginalFilename());
			String fileName = myFile.getOriginalFilename();

			if (myFile != null && !myFile.isEmpty() && myFile.getOriginalFilename() != " ") {

				extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

				if (!extension.equals(excel)) {
					uploadFlag = 1;
					model.addAttribute("alert_type", "success");
					model.addAttribute("results", "Please choose only .xlsx file to upload.!!!!!");
				} else {

					InputStream file = myFile.getInputStream();
					// FileInputStream file = new FileInputStream(getTempFile(myFile));
					// Get the workbook instance for XLSX file
					XSSFWorkbook workbook = new XSSFWorkbook(file);
					// Get first sheet from the workbook
					XSSFSheet sheet = workbook.getSheetAt(0);
					// Get iterator to all the rows in current sheet

					List<String> values;
					int noOfSheets = workbook.getNumberOfSheets();
					int lastRows = 0;
					int lastColumn = 0;
					String SheetName = "";
					System.out.println(sheet.getLastRowNum());
					if (sheet.getLastRowNum() != 0) {

						for (int i = 0; i < noOfSheets; i++) {
							try {

								SheetName = workbook.getSheetName(i);
								lastRows = workbook.getSheetAt(i).getLastRowNum();
								System.out.println("LastRow=" + lastRows);
								values = new ArrayList<String>();

								String[] Feeder_Code = new String[lastRows + 1];
								String[] Boundary_ID = new String[lastRows + 1];
								String[] Boundary_Name = new String[lastRows + 1];
								String[] Boundary_Location = new String[lastRows + 1];
								String[] MeterNo = new String[lastRows + 1];
								String[] Meter_Make = new String[lastRows + 1];
								String[] CT = new String[lastRows + 1];
								String[] PT = new String[lastRows + 1];
								String[] MF = new String[lastRows + 1];

								/* String regexForString = "/^[a-zA-Z]*$/";
								 	Pattern u = Pattern.compile(regexForString);
								 	
								    Matcher r = u.matches("/^[a-zA-Z]*$/", Boundary_Name);
							        Matcher s = u.matcher(Boundary_Location);*/
						       /* List<String> numbers = Arrays.asList(CT);
						    	System.out.println("value" + numbers);
						        for (String number : numbers) {
						    		
						            if (number.matches("/^[a-zA-Z]*$/")) {
						            //	System.out.println("value" + number);
						               */
						            

								if (workbook.getSheetAt(i).getRow(0) != null) {

									lastColumn = workbook.getSheetAt(i).getRow(0).getLastCellNum();
									System.out.println("lastClmn"+lastColumn);

									String cellGotValue = "";

									int Feeder_Codecol = 0;
									int Boundary_IDcol = 0;
									int Boundary_Namecol = 0;
									int Boundary_Locationcol = 0;
									int MeterNocol = 0;
									int Meter_Makecol = 0;
									int CTcol = 0;
									int PTcol = 0;
									int MFcol = 0;

									if (lastRows == 0) {
										uploadFlag = 2;
//										model.addAttribute("alert_type", "success");
//										model.addAttribute("results", "Records are not avaliable in excel to upload");
									}

									for (int j = 0; j <= lastRows; j++) {

										if (j == 0)// To get Column Names First row in Excel
										{
											for (int k = j; k < lastColumn; k++) {
												XSSFCell cellNull = workbook.getSheetAt(i).getRow(j).getCell(k);
												
												//System.out.println("Column Name=== "+cellNull.getStringCellValue());
												
												if (cellNull != null) {
													cellGotValue = cellNull.getStringCellValue();
													cellGotValue = cellGotValue.trim();

												}
												if (cellGotValue.equalsIgnoreCase("Feeder_Code")) {
													Feeder_Codecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Boundary_ID")) {
													Boundary_IDcol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Boundary_Name")) {
													Boundary_Namecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Boundary_Location")) {
													Boundary_Locationcol = k;

												}
												if (cellGotValue.equalsIgnoreCase("MeterNo")) {
													MeterNocol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Meter_Make")) {
													Meter_Makecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("CT")) {
													CTcol = k;

												}
												if (cellGotValue.equalsIgnoreCase("PT")) {
													PTcol = k;

												}
												if (cellGotValue.equalsIgnoreCase("MF")) {
													
													MFcol = k;

												}
											}
										}

										else {
											String nextLine[] = null;

											for (int k = 0; k < lastColumn; k++) {

												XSSFCell cellNull = workbook.getSheetAt(i).getRow(j).getCell(k);

												if (cellNull != null) {

													if (cellNull.getCellType() == Cell.CELL_TYPE_NUMERIC) {
														if (DateUtil.isCellDateFormatted(cellNull)) {
															Date date = cellNull.getDateCellValue();
															SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
															cellGotValue = sdf.format(date);
															cellGotValue = cellGotValue.trim();
														} else {
															if (cellNull.getCellType() == Cell.CELL_TYPE_NUMERIC) {
																cellNull.setCellType(Cell.CELL_TYPE_STRING);
															}
															cellGotValue = cellNull.getStringCellValue();

														}
													}

													if (cellNull.getCellType() == Cell.CELL_TYPE_FORMULA) {

														switch (cellNull.getCachedFormulaResultType()) {
														case Cell.CELL_TYPE_NUMERIC:

															break;
														case Cell.CELL_TYPE_STRING:

															break;
														}
													} else {

														DataFormatter formatter = new DataFormatter();
														Cell cell = cellNull;
														cellGotValue = formatter.formatCellValue(cell);
														// cellGotValue=String.valueOf(cellNull.getStringCellValue());
														cellGotValue = cellGotValue.trim();

													}

												} else {
													cellGotValue = "";
												}

												if (k == Feeder_Codecol) {
													Feeder_Code[j - 1] = cellGotValue.trim();
												}
												if (k == Boundary_IDcol) {
													Boundary_ID[j - 1] = cellGotValue.trim();
												}
												if (k == Boundary_Namecol) {
													Boundary_Name[j - 1] = cellGotValue.trim();
												}
												if (k == Boundary_Locationcol) {
													Boundary_Location[j - 1] = cellGotValue.trim();
												}
												if (k == MeterNocol) {
													MeterNo[j - 1] = cellGotValue.trim();

												}
												if (k == Meter_Makecol) {
													Meter_Make[j - 1] = cellGotValue.trim();
												}
												if (k == CTcol) {
													CT[j - 1] = cellGotValue.trim();
												}
												if (k == PTcol) {
													PT[j - 1] = cellGotValue.trim();

												}
												if (k == MFcol) {
													MF[j - 1] = cellGotValue.trim();
												}

											}

										}
									}

									for (int n = 0; n < lastRows; n++) {

										try {
											
											List<String> numbers = Arrays.asList(Boundary_Name[n].trim());
											List<String> bl = Arrays.asList(Boundary_Location[n].trim());
									

											
									        for (String number : numbers) {
									    		
									            if (number.matches("(?i)[a-z]+([,.\\s]+[a-z]+)*")) {
									            	
									            	for(String boundloc : bl) {
									            	
									            	if(boundloc.matches("(?i)[a-z]+([,.\\s]+[a-z]+)*")) {
									            	
									         
											String regexForInteger = "[+-]?[0-9][0-9]*"; 
											/* String regexForString = "/^[a-zA-Z]*$/";
											 	Pattern u = Pattern.compile(regexForString);*/
										        Pattern p = Pattern.compile(regexForInteger);
										        Matcher m = p.matcher(CT[n].trim());
										        Matcher o = p.matcher(PT[n].trim()); 
										        Matcher q = p.matcher(MF[n].trim()); 
										     /*   Matcher r = u.matcher(Boundary_Name[n].trim());
										        Matcher s = u.matcher(Boundary_Location[n].trim());*/
															/*
															 * System.err.println(CT[n].trim());
															 * System.out.println(PT[n].trim());
															 * System.err.println(MF[n].trim());
															 */

											if((m.find() && m.group().equals(CT[n].trim())) && ((o.find() && o.group().equals(PT[n].trim()))
													&& ((q.find() && q.group().equals(MF[n].trim()))))) {
												/*if(r.find() && r.group().equals(Boundary_Name[n].trim())
														&& (s.find() && s.equals(Boundary_Location[n].trim()))) {*/

//											System.out.println("Feeder_Code=" + Feeder_Code[n].trim());
//											System.out.println("Boundary_ID=" + Boundary_ID[n].trim());
//											System.out.println("Boundary_Name =" + Boundary_Name[n].trim());
//											System.out.println("Boundary_Location =" + Boundary_Location[n].trim());
//											System.out.println("MeterNo= " + MeterNo[n].trim());
//											System.out.println("Meter_Make = " + Meter_Make[n].trim());
//											System.out.println("CT = " + CT[n].trim());
//											System.out.println("PT = " + PT[n].trim());
//											System.out.println("MF =" + MF[n].trim());

//											BoundaryDetailsXLSXUpload trans=  new BoundaryDetailsXLSXUpload();
//
//											trans.setFeeder_Code(Feeder_Code[n].trim());
//											trans.setBoundary_ID(Boundary_ID[n].trim());
//											trans.setBoundary_Name(Boundary_Name[n].trim());
//											trans.setBoundary_Location(Boundary_Location[n].trim());
//											trans.setMeterNo(MeterNo[n].trim());
//											trans.setMeter_Make(Meter_Make[n].trim());
//											trans.setCT(Double.valueOf(CT[n].trim()));
//											trans.setPT(Double.valueOf(PT[n].trim()));
//											trans.setMF(Double.valueOf(MF[n].trim()));

											FeederEntity boundaryEntity = feederdetailsservice.getBoundaryByFeederMrtId(Boundary_ID[n].trim(), Feeder_Code[n].trim(), MeterNo[n].trim());
											if (boundaryEntity != null) {
												boundaryEntity.setBoundary_name(Boundary_Name[n].trim());
												boundaryEntity.setBoundary_location(Boundary_Location[n].trim());
												boundaryEntity.setManufacturer(Meter_Make[n].trim());
//												boundaryEntity.setCt_ratio(CT[n].trim());
//												boundaryEntity.setPt_ratio(PT[n].trim());
												
											
												
												if (!"".equals(CT[n].trim()) && CT[n].trim() != null) {
													boundaryEntity.setCt_ratio(CT[n].trim());
//													if (CT[n].trim().contains("/")) {
//														boundaryEntity.setCt_ratio(CtPtRatioCalculation(CT[n].trim()));
//													} else {
//														boundaryEntity.setCt_ratio(CT[n].trim());
//													}
												}
												if (!"".equals(PT[n].trim()) && PT[n].trim() != null) {
													boundaryEntity.setPt_ratio(PT[n].trim());
//													if (PT[n].trim().contains("/")) {
//														boundaryEntity.setPt_ratio(CtPtRatioCalculation(PT[n].trim()));
//													} else {
//														boundaryEntity.setPt_ratio(PT[n].trim());
//													}

												}
												boundaryEntity.setMf(Double.parseDouble(MF[n].trim()));
												boundaryEntity.setUpdateby(session.getAttribute("username").toString());
												boundaryEntity.setUpdatedate(new Timestamp(System.currentTimeMillis()));
												try {
													feederdetailsservice.update(boundaryEntity);

												} catch (Exception e) {
													e.printStackTrace();
												}
											}

											MasterMainEntity master = masterMainService.getEntityByMtrNO(MeterNo[n].trim());
											if (master != null) {

												if (!"".equals(CT[n].trim()) && CT[n].trim() != null) {
													master.setCt_ratio(CT[n].trim());
//													if (CT[n].trim().contains("/")) {
//														master.setCt_ratio(CtPtRatioCalculation(CT[n].trim()));
//													} else {
//														master.setCt_ratio(CT[n].trim());
//													}
												}
												if (!"".equals(PT[n].trim()) && PT[n].trim() != null) {
													master.setPt_ratio(PT[n].trim());
//													if (PT[n].trim().contains("/")) {
//														master.setPt_ratio(CtPtRatioCalculation(PT[n].trim()));
//													} else {
//														master.setPt_ratio(PT[n].trim());
//													}

												}
												master.setMtrmake(Meter_Make[n].trim());
												master.setMf(MF[n].trim());
												master.setCustomer_name(Boundary_Name[n].trim());

												try {
													uploadFlag = 1;

													masterMainService.update(master);
													model.addAttribute("alert_type", "success");
													model.addAttribute("results", "Data Added Successfully...");

												} catch (Exception e) {
													e.printStackTrace();
												}
											}
									/*}else {
										uploadFlag = 3;
										model.addAttribute("alert_type", "error");
										model.addAttribute("results", "Boundary name,location should contain Alphabets!!");
									}*/
											}else {
												uploadFlag = 3;
												model.addAttribute("alert_type", "error");
												model.addAttribute("results", "Check CT,PT,MF should contains only number!!!");
											}
									            }else {
									            	uploadFlag = 3;
													model.addAttribute("alert_type", "error");
													model.addAttribute("results", "please check boundary location it should contains only alphabets!!!");
									            }
									          }
									        }
											else {
												uploadFlag = 3;
												model.addAttribute("alert_type", "error");
												model.addAttribute("results", "please check boundary name it should contains only alphabets!!!");
											}
									            }
										} catch (Exception e) {
											uploadFlag = 3;
											model.addAttribute("alert_type", "error");
											model.addAttribute("results", "Something went wrong !!!!!");
											e.printStackTrace();
										}

									}

								//	uploadFlag = 1;

								}
						            /*}
						            else {
						            	uploadFlag = 3;
										model.addAttribute("alert_type", "error");
										model.addAttribute("results", "Check boundary name!!");
									}
						        
						      }*/

							} catch (Exception e) {
								uploadFlag = 3;
//								model.addAttribute("alert_type", "error");
//								model.addAttribute("results", "Something went wrong !!!!!");
								e.printStackTrace();
							}

						}
					} else {
						uploadFlag = 2;
					}

//						model.addAttribute("alert_type", "suecess");
//						model.addAttribute("results", "Data Uploaded Sucessfully !!!!!");

				}

			}

		} catch (Exception e) {
			uploadFlag = 3;
//			model.addAttribute("alert_type", "error");
//			model.addAttribute("results", "Something went wrong !!!!!");
			e.printStackTrace();
		}
		return "boundary";

	}

	public String CtPtRatioCalculation(String pt) {

		String ratio = "";
		pt = pt.toUpperCase();
		pt = pt.replace("K", "000");
		pt = pt.replace("A", "");
		pt = pt.replace("V", "");
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		try {
			ratio = String.valueOf(engine.eval(pt));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratio;
	}
	
	@RequestMapping(value="/boundaryexcel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object boundaryexcel(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String town = request.getParameter("town").trim();
  		String ssid = request.getParameter("ssid").trim();
  		String circle=request.getParameter("circle");
  		String zone=request.getParameter("zone");
  		
  		if (zone.equalsIgnoreCase("ALL")){
  			zone="%";
		}
  		if (circle.equalsIgnoreCase("ALL")){
  			circle="%";
		}
		if (town.equalsIgnoreCase("ALL")){
			town="%";
		}
		if (ssid.equalsIgnoreCase("ALL")){
			ssid="%";
		}
		
		try {
			  String fileName = "BoundaryDetails";
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
             
             header.createCell(0).setCellValue("SL No");
             header.createCell(1).setCellValue("Feeder ID");
             header.createCell(2).setCellValue("Boundary ID");
             header.createCell(3).setCellValue("Boundary Name");
             header.createCell(4).setCellValue("Boundary Location");
             header.createCell(5).setCellValue("Town");
             header.createCell(6).setCellValue("Feeder Name");
             header.createCell(7).setCellValue("Latitude");
             header.createCell(8).setCellValue("Longitude");
          
             header.createCell(9).setCellValue("Meter No");
             header.createCell(10).setCellValue("Meter Make");
             header.createCell(11).setCellValue("CT");
             header.createCell(12).setCellValue("PT");
             header.createCell(13).setCellValue("MF");
             header.createCell(14).setCellValue("Export/Import");
             
            
     		List<?> feederdetails = feederdetailsservice.getBoundaryMeterDetailsByTown(town, ssid,circle,zone);
             


            int count =1;
				int cellNO=0;
	            for(Iterator<?> iterator=feederdetails.iterator();iterator.hasNext();){
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
	      		row.createCell(5).setCellValue(String.valueOf(values[12]));
	      		}
	      		if(values[5]==null)
	      		{
	      			row.createCell(6).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(6).setCellValue(String.valueOf(values[13]));
	      		}
	      		
	      		if(values[6]==null)
	      		{
	      			row.createCell(7).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(7).setCellValue(String.valueOf(values[14]));
	      		}
	      		
	      		if(values[7]==null)
	      		{
	      			row.createCell(8).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(8).setCellValue(String.valueOf(values[15]));
	      		}
	      		
	      		if(values[8]==null)
	      		{
	      			row.createCell(9).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(9).setCellValue(String.valueOf(values[4]));
	      		}
	      		if(values[9]==null)
	      		{
	      			row.createCell(10).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(10).setCellValue(String.valueOf(values[5]));
	      		}
	      		if(values[10]==null)
	      		{
	      			row.createCell(11).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(11).setCellValue(String.valueOf(values[6]));
	      		}
	      		
	      		if(values[11]==null)
	      		{
	      			row.createCell(12).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(12).setCellValue(String.valueOf(values[7]));
	      		}
	      		
	      		if(values[12]==null)
	      		{
	      			row.createCell(13).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(13).setCellValue(String.valueOf(values[8]));
	      		}
	      		
	      		if(values[13]==null)
	      		{
	      			row.createCell(14).setCellValue(String.valueOf(""));
	      		}else
	      		{
	      		row.createCell(14).setCellValue(String.valueOf(values[10]));
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
				response.setHeader("Content-Disposition", "inline;filename=\"BoundaryDetails.xlsx"+"\"");
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
	
	
	
	
	



	@RequestMapping(value = "/exportToExcelBoundaryDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object exportToExcelTownDetails(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		try {
			String town = request.getParameter("town").trim();
	  		String ssid = request.getParameter("ssid").trim();
	  		String circle=request.getParameter("circle");
	  		String zone=request.getParameter("zone");
	  		
	  		if (zone.equalsIgnoreCase("ALL")){
	  			zone="%";
			}
	  		if (circle.equalsIgnoreCase("ALL")){
	  			circle="%";
			}
			if (town.equalsIgnoreCase("ALL")){
				town="%";
			}
			if (ssid.equalsIgnoreCase("ALL")){
				ssid="%";
			}

			String fileName = "BoundarySampleDetails";
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

			header.createCell(0).setCellValue("Feeder_Code");
			header.createCell(1).setCellValue("Boundary_ID");
			header.createCell(2).setCellValue("Boundary_Name");
			header.createCell(3).setCellValue("Boundary_Location");
			header.createCell(4).setCellValue("MeterNo");
			header.createCell(5).setCellValue("Meter_Make");
			header.createCell(6).setCellValue("CT");
			header.createCell(7).setCellValue("PT");
			header.createCell(8).setCellValue("MF");

			List<?> boundaryData = null;

			boundaryData = feederdetailsservice.getBoundaryMeterDetailsByTown(town, ssid,circle,zone);

			int count = 1;
			// int cellNO=0;
			for (Iterator<?> iterator = boundaryData.iterator(); iterator.hasNext();) {
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
					row.createCell(8).setCellValue(String.valueOf(values[8]).split("\\.")[0]);
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
			response.setHeader("Content-Disposition", "inline;filename=\"BoundarySampleDetails.xlsx" + "\"");
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
