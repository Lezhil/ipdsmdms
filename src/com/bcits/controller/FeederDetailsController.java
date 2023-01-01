package com.bcits.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bcits.entity.FeederDetailsHistory;
import com.bcits.entity.FeederEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.FeederDetailsHistoryService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterInventoryService;

@Controller
public class FeederDetailsController {
	@Autowired
	private FeederDetailsService feederdetailsservice;

	@Autowired
	private FeederDetailsHistoryService feederDetailsHistoryService;

	@Autowired
	private MasterMainService masterMainService;

	@Autowired
	private MeterInventoryService mtrinventorydetails;

	String msg = "";

	int uploadFlag = 0;

	@RequestMapping(value = "/fdrdata", method = { RequestMethod.POST, RequestMethod.GET })
	public String fdrdata(HttpServletResponse response, HttpServletRequest request, HttpSession session,
			ModelMap model) {
		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		List<?> subdivisionList = feederdetailsservice.getDistinctsubdivision();
		model.put("subdivisionList", subdivisionList);
		List<?> circleList = feederdetailsservice.getcircle();
		//model.put("circleList", circleList);

		// List<?> feederdetails =feederdetailsservice.getfeederdetails();
		/*
		 * System.out.println(feederdetails); model.addAttribute("feederDetails",
		 * feederdetails);
		 */
		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		String userType = (String) session.getAttribute("userType");
		String qry = null;
		String ssNameQry = null;
		// System.out.println("officeType####" +officeType);
		if (officeType.equals(null)) {
			return "redirect:/logout";
		}

		/*
		 * List<?> fdrlist=feederdetailsservice.getFeederidForDD();
		 * System.out.println(fdrlist.size()); model.put("fdrlist", fdrlist);
		 */

		if (officeType.equalsIgnoreCase("circle")) {
			qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE circle_code = '" + officeCode + "' ";
		} else if (officeType.equalsIgnoreCase("division")) {
			qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE division_code = '" + officeCode + "' ";
		} else if (officeType.equalsIgnoreCase("subdivision")) {
			qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE sitecode = '" + officeCode + "' ";
		}

		/*
		 * if(officeType.equalsIgnoreCase("subdivision")) { ssNameQry=
		 * "select ss_name from meter_data.substation_details where office_id = '"
		 * +officeCode+"'";
		 * 
		 * List<?> ssName = null; try { ssName = (List<?>)
		 * feederdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery
		 * (ssNameQry).getResultList();
		 * 
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * model.addAttribute("ssName", ssName); }
		 */

		String SubdivName = null;
		try {
			if (officeType.equalsIgnoreCase("subdivision"))
				SubdivName = (java.lang.String) feederdetailsservice.getCustomEntityManager("postgresMdas")
						.createNativeQuery(qry).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("msg", msg);

		model.put("officeType", officeType);
		model.addAttribute("userType", userType);
		model.addAttribute("SubdivName", SubdivName);

		msg = "";

		return "feederDetails";
	}
	
	@RequestMapping(value = "/getAllFeederList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getAllFeederList(ModelMap model, HttpSession session, HttpServletRequest request) {
		String Region = request.getParameter("region");
		String Circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String feederType = request.getParameter("feedertype");
		// String town=request.getParameter("town").split("\\,")[1];
		// System.out.println("town-----"+town);
		List<?> feederList = feederdetailsservice.getfeederdetails(Region, Circle, town,feederType);

		return feederList;
	}

	@RequestMapping(value = "/getAllipdsnonipdsFeederList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getAllipdsnonipdsFeederList(ModelMap model, HttpSession session,
			HttpServletRequest request) {
		String Region = request.getParameter("region");
		String Circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String text = request.getParameter("checked");
		//System.out.println("text----"+text);

		String category = text.toString().replace("[", "").replace("]", "").replace("\"", "");
		List<?> feederList = feederdetailsservice.getAllipdsnonipdsFeederList(Region, Circle, town, category);

		return feederList;
	}

	@RequestMapping(value = "/addfdrdetailsno", method = { RequestMethod.POST, RequestMethod.GET })
	public String addfdrdetailsno(HttpServletRequest request, HttpSession session, ModelMap model) {

		long currtime = System.currentTimeMillis();
		String mtrsrlno = request.getParameter("mtrsrlno1");
		String subdiv = request.getParameter("subdiv1");
		String ssname = request.getParameter("subid1").split("-")[1];
		//System.out.println("value" + ssname.split("-")[1]);
		String qry = "select sitecode  from meter_data.amilocation where subdivision ='" + subdiv + "' ";
		String SubdivName = feederdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry)
				.getSingleResult().toString();
		// String qry1 = "select ss_id,ss_name from meter_data.substation_details where
		// ss_name='"+ssname+"'";
		// System.out.println(qry1);
		// String parentid =
		// feederdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry1).getSingleResult().toString();
		String fdrname = request.getParameter("fdrname1");
		String mtrmanufacturer = request.getParameter("mtrmanufacturer1");
		String voltglvl = request.getParameter("voltglvl1");
		String mf = request.getParameter("mf1");
		String tpfdrcode = request.getParameter("tpfdrcode");
		String tpprntcode = request.getParameter("tpprntcode");
		String ParentTown = request.getParameter("ParentTown1");
		String Region = request.getParameter("region");
		String Circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String fdrtype = request.getParameter("fdrtype");
		// model.put("subdivisionList",feederdetailsservice.getDistinctsubdivision());
		// model.put("circleList", feederdetailsservice.getcircle());

		FeederEntity fe = new FeederEntity();
		MasterMainEntity masterMain = new MasterMainEntity();

		fe.setFeedername(fdrname);
		fe.setManufacturer(mtrmanufacturer);
		fe.setTpparentid(tpprntcode);
		fe.setTpfdrid(tpfdrcode);
		fe.setVoltagelevel(Double.parseDouble(voltglvl));
		fe.setMeterno(mtrsrlno);
		fe.setOfficeid(Integer.parseInt(SubdivName));
		fe.setParentid(ssname);
		fe.setMf(Double.parseDouble(mf));
		fe.setCrossfdr(0);
		fe.setDeleted(0);
		String userName = (String) session.getAttribute("username");
		fe.setTpTownCode(ParentTown);
		fe.setFeeder_type(fdrtype);

		fe.setEntryby(userName);
		fe.setEntrydate(new Timestamp(currtime));
//	fe.setUpdateby(userName);
//	fe.setUpdatedate(new Timestamp(currtime));
		fe.setVolt_mf(0.0);
		fe.setCurrent_mf(0.0);

		String fdrid = feederdetailsservice.getfdrid();
		try {
			if (fdrid != null && fdrid != "") {
				fe.setFdrid(fdrid);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Inserting and adding flag to Master main table.

		HashMap<String, String> hh = feederdetailsservice.getlocationHireachy(subdiv);
		String zone = hh.get("ZONE");
		String circle = hh.get("CIRCLE");
		String division = hh.get("DIVISION");
		String subdivision = hh.get("SUBDIVISION");

		masterMain.setZone(zone);
		masterMain.setCircle(circle);
		masterMain.setDivision(division);
		masterMain.setSubdivision(subdivision);
		masterMain.setMtrno(mtrsrlno);
		masterMain.setMtrmake(mtrmanufacturer);
		masterMain.setFdrname(fdrname);
		masterMain.setFdrcode(fdrid);
		masterMain.setAccno(fdrid);
		masterMain.setSubstation(ssname);
		masterMain.setMf(mf);
		masterMain.setFdrtype(fdrtype);
		masterMain.setFeeder_type("Regular Feeder");
		masterMain.setTown_code(ParentTown);
		masterMain.setCustomer_name(fdrname);
		masterMain.setFeeder_status("Feeder Live");
		masterMain.setHes_type("ANALOGICS");
		masterMain.setDiscom("TNEB");
		masterMain.setSdocode(SubdivName);

		masterMain.setFdrcategory("FEEDER METER");
		masterMain.setVoltage_kv(voltglvl);
		masterMain.setLocation_id(tpfdrcode);
		try {
			feederdetailsservice.save(fe);
			feederdetailsservice.setMeterInventoflag(mtrsrlno, userName);
			masterMainService.save(masterMain);

			msg = "FEEDER DETAILS ADDED SUCCESSFULLY";

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<?> feederdetails = feederdetailsservice.getfeederdetails(Region, Circle, town,fdrtype);
		model.addAttribute("feederDetails", feederdetails);

		// List<?> feederdetails =feederdetailsservice.getfeederdetails();
		// System.out.println(feederdetails);
		// model.addAttribute("feederDetails", feederdetails);

		return "redirect:/fdrdata";

	}

	@RequestMapping(value = "/addfdrdetailsyes", method = { RequestMethod.POST, RequestMethod.GET })
	public String addfdrdetailsyes(HttpServletRequest request, HttpSession session, ModelMap model) {

		long currtime = System.currentTimeMillis();
		String mtrsrlno = request.getParameter("mtrsrlno2");
		String subdiv = request.getParameter("subdiv2");
		String ssname = request.getParameter("subid2");
		String fdrtype = request.getParameter("fdrtype");

		String fdr1[] = request.getParameter("fdr1").split("-");
		String feederCode = fdr1[0];
		String feederName = fdr1[1];
		String crossFeederSubdivision = request.getParameter("crossFeedersubDiv");
		String tpParentCode = request.getParameter("tpParentCode");

		String qry = "select sitecode  from meter_data.amilocation where subdivision ='" + crossFeederSubdivision
				+ "' ";
		String SubdivName = feederdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry)
				.getSingleResult().toString();

		String mtrmanufacturer = request.getParameter("mtrmanufacturer2");
		String mf = request.getParameter("mf2");
		String cp = request.getParameter("cp");
		// List<?> fdrlist=feederdetailsservice.getFeederidForDD();
		// System.out.println(fdrlist.size()); //model.put("fdrlist", fdrlist);
		// List<?> fdrname=feederdetailsservice.getFeederNameForDD();
		// model.put("fdrname", fdrname);

		// model.put("subdivisionList",feederdetailsservice.getDistinctsubdivision());

		FeederEntity fe = new FeederEntity();
		fe.setFeedername(feederName);
		fe.setOfficeid(Integer.parseInt(SubdivName));
		fe.setParentid(ssname);
		fe.setManufacturer(mtrmanufacturer);
		fe.setMeterno(mtrsrlno);
		if (mf != "")
			fe.setMf(Double.parseDouble(mf));
		/*
		 * if (cp != "") fe.setConsumppercent(Double.parseDouble(cp));
		 */
		fe.setCrossfdr(1);
		fe.setTpfdrid(tpParentCode);

		String userName = (String) session.getAttribute("username");

		fe.setEntryby(userName);
		fe.setEntrydate(new Timestamp(currtime));
		fe.setUpdateby(userName);
		fe.setUpdatedate(new Timestamp(currtime));

		String fdrid = feederCode;
		try {
			if (fdrid != null && fdrid != "") {
				fe.setFdrid(fdrid);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Saving data to master main
		MasterMainEntity masterMain = new MasterMainEntity();

		HashMap<String, String> hh = feederdetailsservice.getlocationHireachy(subdiv);
		String zone = hh.get("ZONE");
		String circle = hh.get("CIRCLE");
		String division = hh.get("DIVISION");
		String subdivision = hh.get("SUBDIVISION");

		masterMain.setZone(zone);
		masterMain.setCircle(circle);
		masterMain.setDivision(division);
		masterMain.setSubdivision(subdivision);
		masterMain.setMtrno(mtrsrlno);
		masterMain.setMtrmake(mtrmanufacturer);

		masterMain.setFdrcategory("BORDER METER");

		try {
			feederdetailsservice.save(fe);
			feederdetailsservice.setMeterInventoflag(mtrsrlno, userName);
			masterMainService.save(masterMain);

			msg = "FEEDER DETAILS ADDED SUCCESSFULLY";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// List<?> feederdetails = feederdetailsservice.getfeederdetails();
		// System.out.println(feederdetails);
		// model.addAttribute("feederDetails", feederdetails);
		return "redirect:/fdrdata";
	}

	/*
	 * @RequestMapping(value="/addfdrdetailsyes",method={RequestMethod.POST,
	 * RequestMethod.GET}) public String addfdrdetailsyes(HttpServletResponse
	 * response,HttpServletRequest request,HttpSession session,ModelMap model) {
	 * long currtime = System.currentTimeMillis(); String mtrsrlno =
	 * request.getParameter("mtrsrlno2"); String subdiv =
	 * request.getParameter("subdiv2"); String ssname =
	 * request.getParameter("subid2"); String qry =
	 * "select sitecode  from meter_data.amilocation where subdivision ='"
	 * +subdiv+"' "; String SubdivName =
	 * feederdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery
	 * (qry).getSingleResult().toString(); String mtrmanufacturer =
	 * request.getParameter("mtrmanufacturer2"); String mf =
	 * request.getParameter("mf2"); String cp=request.getParameter("cp"); String
	 * feederMainId = request.getParameter("feederMainId");
	 * 
	 * int feederID =Integer.parseInt(feederMainId);
	 * model.put("subdivisionList",feederdetailsservice.getDistinctsubdivision());
	 * 
	 * FeederEntity fe= feederdetailsservice.find(feederID);
	 * 
	 * if(cp != "") fe.setConsumppercent(Double.parseDouble(cp));
	 * 
	 * fe.setCrossfdr(1); String userName = (String)
	 * session.getAttribute("username"); fe.setUpdateby(userName);
	 * fe.setUpdatedate(new Timestamp(currtime));
	 * 
	 * feederdetailsservice.update(fe); msg =
	 * "FEEDER CHANGED TO CROSS FEEDER SUCCESSFULLY";
	 * 
	 * List<?> feederdetails =feederdetailsservice.getfeederdetails();
	 * System.out.println(feederdetails); model.addAttribute("feederDetails",
	 * feederdetails); return "redirect:/fdrdata"; }
	 * 
	 * 
	 */

	/*
	 * @RequestMapping(value="/addfdrdetailscpm",method={RequestMethod.POST,
	 * RequestMethod.GET}) public String addfdrdetailscpm(HttpServletResponse
	 * response,HttpServletRequest request,HttpSession session,ModelMap model) {
	 * 
	 * long currtime = System.currentTimeMillis(); String
	 * cp=request.getParameter("cp"); String subdiv =
	 * request.getParameter("subdiv3"); String ssname =
	 * request.getParameter("subid3"); String qry =
	 * 
	 * "select sitecode  from meter_data.amilocation where subdivision ='"
	 * +subdiv+"' "; String SubdivName =
	 * feederdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery
	 * (qry).getSingleResult().toString(); FeederEntity fe= new FeederEntity();
	 * 
	 * 
	 * fe.setOfficeid(Integer.parseInt(SubdivName)); fe.setParentid(ssname);
	 * fe.setConsumppercent(Double.parseDouble(cp)); String userName = (String)
	 * session.getAttribute("username"); String
	 * 
	 * fdrid=feederdetailsservice.getfdrid();
	 * 
	 * try { if(fdrid!=null && fdrid!="") { fe.setFdrid(fdrid); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } fe.setEntryby(userName);
	 * 
	 * fe.setEntrydate(new Timestamp(currtime)); feederdetailsservice.save(fe); msg
	 * = "FEEDER DETAILS ADDED SUCCESSFULLY";
	 * 
	 * List<?> feederdetails =feederdetailsservice.getfeederdetails();
	 * System.out.println(feederdetails); model.addAttribute("feederDetails",
	 * feederdetails);
	 * 
	 * return "redirect:/fdrdata";
	 * 
	 * }
	 * 
	 */
	@RequestMapping(value = "/editfeederdetails/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object geteditdetails(@PathVariable("id") String id, HttpServletRequest request) {

		return feederdetailsservice.getEditDetailsById(id);
	}

	@RequestMapping(value = "/deletefeederdetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getdeletedetails(@RequestParam("id") String id, HttpServletRequest request) {
		int deleteId = Integer.parseInt(id);
		List<?> resultSubstation = null;
		List<?> resultDT = null;
		List<?> resultConsumer = null;

		try {
			resultSubstation = (List<?>) feederdetailsservice.isfeederAttached(deleteId);
			resultDT = (List<?>) feederdetailsservice.isDtAttached(deleteId);
			resultConsumer = (List<?>) feederdetailsservice.isConsumerAttached(deleteId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resultSubstation.size() == 0 && resultDT.size() == 0 && resultConsumer.size() == 0) {

			try {
				feederdetailsservice.deletemethod(deleteId);
				mtrinventorydetails.updateMtrStatus(deleteId);
				return "deleted";
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return "failed";
	}

	@RequestMapping(value = "/Modifyfeederdetails", method = { RequestMethod.POST, RequestMethod.GET })
	public String Modifydtdetails(ModelMap model, HttpServletRequest request, HttpSession session) {
		long currtime = System.currentTimeMillis();
		String id = request.getParameter("editfdrId");
		String editfdrname = request.getParameter("editfdrname");
		String editvoltagelvl = request.getParameter("editvoltagelvl");
		String editfdrcode = request.getParameter("editfdrcode");
		String edittpprntcode = request.getParameter("edittpprntcode");
		String editmtrno = request.getParameter("editmtrno");
		String editmf = request.getParameter("editmf");
		String mfChangeDate = request.getParameter("selectedMFDateName");
		String meterChangeDate = request.getParameter("selectedMeterDateName");
		String efdrtype = request.getParameter("efdrtype");
		String userName = (String) session.getAttribute("username");
		String  latitude=(request.getParameter("editfdrlat"));
		String longitude=(request.getParameter("editfdrlong"));
		String manufacturer=(request.getParameter("editmanufac"));
	
		// To update feeder details table
		FeederEntity fe = feederdetailsservice.find(Integer.parseInt(id));
		fe.setFeeder_type(efdrtype);
		fe.setFeedername(editfdrname);
		if (latitude != null && latitude != "") {
			fe.setGeo_cord_x(Double.parseDouble(latitude));
		}
		if (longitude != null && longitude != "") {
			fe.setGeo_cord_y(Double.parseDouble(longitude));
		}
			
		if (editvoltagelvl != null && editvoltagelvl != "") {
			fe.setVoltagelevel(Double.parseDouble(editvoltagelvl));
		}
		fe.setMeterno(editmtrno);
		if (editmf != null && editmf != "") {
			fe.setMf(Double.parseDouble(editmf));
		}
		
		fe.setManufacturer(manufacturer);
		// fe.setTpfdrid(editfdrcode);
		// fe.setTpparentid(edittpprntcode);

		fe.setUpdateby(userName);
		fe.setUpdatedate(new Timestamp(currtime));

		if (!meterChangeDate.isEmpty()) {
			fe.setMeterchangeflag(1);
			try {
				fe.setMeterchangedate(convertDate(meterChangeDate));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!mfChangeDate.isEmpty()) {
			fe.setMfflag(1);
			try {
				fe.setMfchangedate(convertDate(mfChangeDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// To update master main table
		MasterMainEntity mm = masterMainService.getEntityByMtrNO(fe.getMeterno());
		if (mm != null) {
			if (fe.getMeterno().equalsIgnoreCase(editmtrno)) {
				mm.setFdrname(editfdrname);
				mm.setVoltage_kv(editvoltagelvl);
				mm.setFdrtype(efdrtype);
				// mm.setFdrcode(editfdrcode);
				mm.setMf(editmf);
				mm.setLatitude(latitude);
				mm.setLongitude(longitude);
				mm.setMtrmake(manufacturer);

			} else {
				mm.setFdrname(editfdrname);
				mm.setVoltage_kv(editvoltagelvl);
				// mm.setFeeder_type(efdrtype);
				mm.setFdrtype(efdrtype);
				// mm.setFdrcode(editfdrcode);
				mm.setMtrno(editmtrno);
				mm.setMf(editmf);
				mm.setOld_mtr_no(fe.getMeterno());
				mm.setMtr_change_date(meterChangeDate);

			}
		}

		// Below code is to update Feeder history table to record old data.
		FeederDetailsHistory fdh = new FeederDetailsHistory();

		fdh.setBoundary_id(fe.getBoundary_id());
		fdh.setVoltagelevel(fe.getVoltagelevel());
		fdh.setFeedername(fe.getFeedername());
		fdh.setOfficeid(fe.getOfficeid());
		fdh.setParentid(fe.getParentid());
		fdh.setTpparentid(fe.getTpparentid());
		fdh.setMeterno(fe.getMeterno());
		fdh.setManufacturer(fe.getManufacturer());
		fdh.setMf(fe.getMf());
		fdh.setDeleted(fe.getDeleted());
		fdh.setEntryby(fe.getEntryby());
		fdh.setEntrydate(fe.getEntrydate());
		fdh.setUpdateby(fe.getUpdateby());
		fdh.setUpdatedate(fe.getUpdatedate());
		fdh.setTpfdrid(fe.getTpfdrid());
		fdh.setBoundry_feeder(fe.getBoundry_feeder());
		fdh.setFdrid(fe.getFdrid());
		fdh.setCrossfdr(fe.getCrossfdr());
		fdh.setVolt_mf(fe.getVolt_mf());
		fdh.setCurrent_mf(fe.getCurrent_mf());
		fdh.setBoundary_id(fe.getBoundary_id());
		fdh.setBoundary_name(fe.getBoundary_name());
		fdh.setBoundary_location(fe.getBoundary_location());
		fdh.setCt_ratio(fe.getCt_ratio());
		fdh.setPt_ratio(fe.getPt_ratio());
		fdh.setMeter_ratio(fe.getMeter_ratio());
		fdh.setImp_exp(fe.getImp_exp());
		fdh.setMeter_installed(fe.getMeter_installed());
		fdh.setTpTownCode(fe.getTpTownCode());
		fdh.setMeterchangedate(fe.getMeterchangedate());
		fdh.setMfchangedate(fe.getMfchangedate());
		fdh.setMfflag(fe.getMfflag());
		fdh.setMeterchangeflag(fe.getMeterchangeflag());
		fdh.setFeeder_type(fe.getFeeder_type());
		fdh.setManufacturer(fe.getManufacturer());

		try {
			feederDetailsHistoryService.save(fdh);
			masterMainService.update(mm);
			feederdetailsservice.update(fe);
		} catch (Exception e) {
			msg = "SOME ERROR OCCURED!!!";
			e.printStackTrace();
		}

		if (!fe.getMeterno().equalsIgnoreCase(editmtrno)) {
			feederdetailsservice.setMeterInventoflag(editmtrno, userName);
		}

		/* List<?> feederdetails=feederdetailsservice.getfeederdetails(); */

		/* model.addAttribute("feederDetails", feederdetails); */
		msg = "FEEDER DETAILS UPDATED SUCCESSFULLY";

		return "redirect:/fdrdata";
	}
	

	@RequestMapping(value = "/fdrMasterDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public String fdrMasterDetails(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		   
		
		return "fdrmaster";
	}
	
	@RequestMapping(value = "/getFeederMaster", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDTHealthDataload(HttpServletRequest request) {
		List<?> list = new ArrayList<>();
		
		String region = request.getParameter("zone");
		String subdiv = request.getParameter("subdiv");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		
		
		
	//	System.out.println(town);
		String towncode=request.getParameter("town");
		list = feederdetailsservice.getFeederMaster(region,subdiv, circle,town,towncode);
		return list;
	}
	
	
	/*
	 * String dt = "10-05-2017"; public Timestamp convertDate(String modifiedDate)
	 * throws ParseException{ SimpleDateFormat inputFormat = new
	 * SimpleDateFormat("dd-MM-yyyy"); SimpleDateFormat outputFormat = new
	 * SimpleDateFormat("dd-MM-yyyy"); Date date = null; try { date =
	 * inputFormat.parse(dt); } catch (ParseException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }//hardcode here and check // Timestamp
	 * validDate = outputFormat.format(date);
	 * 
	 * System.out.println("$$$$####  "+validDate ); return validDate; }
	 */

	public Timestamp convertDate(String str_date) throws ParseException {

		try {

			DateFormat formatter;
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date) formatter.parse(str_date);
			java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
			return timeStampDate;

		} catch (ParseException e) {
			return null;

		}

	}

	@RequestMapping(value = "/showSubdivByCircle/{circle}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getsubdivdetails(@PathVariable("circle") String circle, HttpServletRequest request) {
		return feederdetailsservice.getsubdivByCircle(circle);
	}

	@RequestMapping(value = "/showSSnameBySubdiv", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getssdetails(HttpServletRequest request) {
		String town = request.getParameter("town");
		return feederdetailsservice.getSSnameBySubdiv(town);
	}

	@RequestMapping(value = "/showSSnameByofficeID", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object showSSnameByofficeID(HttpServletRequest request) {

		String officeId = request.getParameter("officeId");

		return feederdetailsservice.getSSnameByofficeID(officeId);
	}

	@RequestMapping(value = "/showfdrIdNameBySubdivAndSSname", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getfdridnamedetails(HttpServletRequest request) {

		String parentid = request.getParameter("parentid");
		String sdoCode = request.getParameter("sdoCode");
		System.out.println("This is the response " + feederdetailsservice.getFdrIdNamenameBySubdiv(parentid, sdoCode));

		return feederdetailsservice.getFdrIdNamenameBySubdiv(parentid, sdoCode);
	}

	@RequestMapping(value = "/showfdrIdNameBySubdivAndSSnameNew", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getfdridnamedetailsNew(HttpServletRequest request) {

		String parentid = request.getParameter("parentid");
		String officeId = request.getParameter("sitecode");
		System.out.println(
				"This is the response " + feederdetailsservice.getFdrIdNamenameBySubdivandSubstat(parentid, officeId));

		return feederdetailsservice.getFdrIdNamenameBySubdivandSubstat(parentid, officeId);
	}

	@RequestMapping(value = "/getFeederDetails/{feederId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getFeederDetails(@PathVariable("feederId") String feederId,
			HttpServletRequest request) {

		List<?> result = null;
		try {
			result = (List<?>) feederdetailsservice.getFeederDetails(feederId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/checkMeterNoexistance", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object retriveMrName(@RequestParam String meterno, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		return feederdetailsservice.isMeterNoAvailable(meterno);
	}

	@RequestMapping(value = "/validateMeterfromMM", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object validateMeterfromMM(@RequestParam String meterno, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		return feederdetailsservice.validateMeterNO(meterno);
	}

	@RequestMapping(value = "/checkFeederCode/{fdrCode}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object checkFeederCode(@PathVariable String fdrCode, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		return feederdetailsservice.isFeederCodeAvailable(fdrCode);
	}

	@RequestMapping(value = "/FeederDetailsPDF", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void FeederDetailsPDF(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		String Region = request.getParameter("reg");
		String Circle = request.getParameter("cir");
		String town = request.getParameter("twn");
		String feederType = request.getParameter("feeder");
		String townname = request.getParameter("townname");
//		System.out.println("region----> "+ Region);
//		System.out.println("Circle----> "+ Circle);
//		System.out.println("town----> "+ town);
//		System.out.println("feederType----> "+ feederType);
		
		String reg="",cir="",twn="",feeder="";
		if (Region.equalsIgnoreCase("ALL")) {
			reg = "%";
		} else {
			reg = Region;
		}
		if (Circle.equalsIgnoreCase("ALL")) {
			cir = "%";
		} else {
			cir = Circle;
		}
		if (town.equalsIgnoreCase("ALL")) {
			twn = "%";
		} else {
			twn = town;
		}
		if (feederType.equalsIgnoreCase("ALL")) {
			feeder = "%";
		} else {
			feeder = feederType;
		}
		feederdetailsservice.getFdrDtlspdf(request, response, reg, cir, twn,feeder,townname);
	}

	@RequestMapping(value = "/FeederDetailsExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object FeederDetailsExcel(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		try {

			String Region = request.getParameter("reg");
			String Circle = request.getParameter("cir");
			String town = request.getParameter("twn");
			String feederType = request.getParameter("feeder");
			
//			System.out.println("region----> "+ Region);
//			System.out.println("Circle----> "+ Circle);
//			System.out.println("town----> "+ town);
			
			String reg="",cir="",twn="",feeder="";
			if (Region.equalsIgnoreCase("ALL")) {
				reg = "%";
			} else {
				reg = Region;
			}
			if (Circle.equalsIgnoreCase("ALL")) {
				cir = "%";
			} else {
				cir = Circle;
			}
			if (town.equalsIgnoreCase("ALL")) {
				twn = "%";
			} else {
				twn = town;
			}
			
			if (feederType.equalsIgnoreCase("ALL")) {
				feeder = "%";
			} else {
				feeder = feederType;
			}

			String fileName = "FeederDetails";
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

			header.createCell(0).setCellValue("SL No");
			header.createCell(3).setCellValue("Circle Name");
			header.createCell(1).setCellValue("Feeder Name");
			header.createCell(2).setCellValue("Voltage Level");
			header.createCell(4).setCellValue("Town Name");
			header.createCell(5).setCellValue("Feeder Code");      
			header.createCell(6).setCellValue("Sub Station Code");
			header.createCell(7).setCellValue("Meter Sr Number");
			header.createCell(8).setCellValue("Meter Manufacturer");
			header.createCell(9).setCellValue("MF");
			header.createCell(10).setCellValue("Latitude");
			header.createCell(11).setCellValue("Longitude");
			header.createCell(12).setCellValue("Feeder Type");
			/* header.createCell(10).setCellValue("Consumption Percentage"); */
			header.createCell(13).setCellValue("Entry By");
			header.createCell(14).setCellValue("Entry Date");
			header.createCell(15).setCellValue("Update By");
			header.createCell(16).setCellValue("Update Date");
			
			

			List<?> feederList = null;
			feederList = feederdetailsservice.getfeederdetails(reg, cir, twn,feeder);

			int count = 1;
			//int cellNO = 0;
			for (Iterator<?> iterator = feederList.iterator(); iterator.hasNext();) {
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
				if (values[9] == null) {
					row.createCell(7).setCellValue(String.valueOf(""));
				} else {
					row.createCell(7).setCellValue(String.valueOf(values[9]));
				}
				if (values[10] == null) {
					row.createCell(8).setCellValue(String.valueOf(""));
				} else {
					row.createCell(8).setCellValue(String.valueOf(values[10]));
				}
				if (values[11] == null) {
					row.createCell(9).setCellValue(String.valueOf(""));
				} else {
					row.createCell(9).setCellValue(String.valueOf(values[11]).split("\\.")[0]);
				}
				
				if (values[22] == null) {
					row.createCell(10).setCellValue(String.valueOf(""));
				} else {
					row.createCell(10).setCellValue(String.valueOf(values[22]));
				}
				if (values[23] == null) {
					row.createCell(11).setCellValue(String.valueOf(""));
				} else {
					row.createCell(11).setCellValue(String.valueOf(values[23]));
				}
				if (values[19] == null) {
					row.createCell(12).setCellValue(String.valueOf(""));
				} else {
					row.createCell(12).setCellValue(String.valueOf(values[19]));
				}
				if (values[14] == null) {
					row.createCell(13).setCellValue(String.valueOf(""));
				} else {
					row.createCell(13).setCellValue(String.valueOf(values[14]));
				}
				if (values[15] == null) {
					row.createCell(14).setCellValue(String.valueOf(""));
				} else {
					row.createCell(14).setCellValue(String.valueOf(values[15]));
				}
				
				if (values[16] == null) {
					row.createCell(15).setCellValue(String.valueOf(""));
				} else {
					row.createCell(15).setCellValue(String.valueOf(values[16]));
				}
				if (values[17] == null) {
					row.createCell(16).setCellValue(String.valueOf(""));
				} else {
					row.createCell(16).setCellValue(String.valueOf(values[17]));
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/uploadFeederFile", method = { RequestMethod.GET, RequestMethod.POST })
	public String uploadFeederFile(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			HttpSession session) {
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
					msg = "Please choose only .xlsx file to upload.!!!!!";
//						model.addAttribute("alert_type", "success");
//						model.addAttribute("results", "Please choose only .xlsx file to upload.!!!!!");
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

								String[] Feeder_Name = new String[lastRows + 1];
								String[] Voltage_Level = new String[lastRows + 1];
//									String[] Circle_Name = new String[lastRows + 1];
//									String[] Subdivision_Name = new String[lastRows + 1];
								String[] Feeder_Code = new String[lastRows + 1];
								String[] Substation_Code = new String[lastRows + 1];
								String[] Meter_Sr_Number = new String[lastRows + 1];
								String[] Meter_Manufacturer = new String[lastRows + 1];
								String[] MF = new String[lastRows + 1];
								String[] Town_Code = new String[lastRows + 1];
								String[] Feeder_Type = new String[lastRows + 1];
								String[] latitude = new String[lastRows + 1];
								String[] longitude = new String[lastRows + 1];

								if (workbook.getSheetAt(i).getRow(0) != null) {

									lastColumn = workbook.getSheetAt(i).getRow(0).getLastCellNum();

									String cellGotValue = "";

									int Feeder_Namecol = 0;
									int Voltage_Levelcol = 0;
//										int Circle_Namecol = 0;
//										int Subdivision_Namecol = 0;
									int Feeder_Codecol = 0;
									int Substation_Codecol = 0;
									int Meter_Sr_Numbercol = 0;
									int Meter_Manufacturercol = 0;
									int MFcol = 0;
									int Town_Codecol = 0;
									int Feeder_Typecol = 0;
									int latitudecol = 0;
									int longitudecol = 0;
									if (lastRows == 0) {
										uploadFlag = 2;
										model.put("msg", "Records are not avaliable in excel to upload");
//											model.addAttribute("alert_type", "error");
//											model.addAttribute("alert_type", "success");
//											model.addAttribute("results", "Records are not avaliable in excel to upload");
									}

									for (int j = 0; j <= lastRows; j++) {

										//
										if (j == 0)// To get Column Names First row in Excel
										{
											for (int k = j; k < lastColumn; k++) {
												XSSFCell cellNull = workbook.getSheetAt(i).getRow(j).getCell(k);
												if (cellNull != null) {
													cellGotValue = cellNull.getStringCellValue();
													cellGotValue = cellGotValue.trim();

												}
												if (cellGotValue.equalsIgnoreCase("Feeder_Name")) {
													Feeder_Namecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Voltage_Level")) {
													Voltage_Levelcol = k;

												}
//													if (cellGotValue.equalsIgnoreCase("Circle_Name")) {
//														Circle_Namecol = k;
//
//													}
//													if (cellGotValue.equalsIgnoreCase("Subdivision_Name")) {
//														Subdivision_Namecol = k;
//
//													}
												if (cellGotValue.equalsIgnoreCase("Feeder_Code")) {
													Feeder_Codecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Substation_Code")) {
													Substation_Codecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Meter_Sr_Number")) {
													Meter_Sr_Numbercol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Meter_Manufacturer")) {
													Meter_Manufacturercol = k;

												}
												if (cellGotValue.equalsIgnoreCase("MF")) {
													MFcol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Town_Code")) {
													Town_Codecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Feeder_Type")) {
													Feeder_Typecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("latitude")) {
													latitudecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("longitude")) {
													longitudecol = k;

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
														cellGotValue = cellGotValue.trim();

													}

												} else {
													cellGotValue = "";
												}

												if (k == Feeder_Namecol) {
													Feeder_Name[j - 1] = cellGotValue.trim();
												}
												if (k == Voltage_Levelcol) {
													Voltage_Level[j - 1] = cellGotValue.trim();
												}
//													if (k == Circle_Namecol) {
//														Circle_Name[j - 1] = cellGotValue.trim();
//													}
//													if (k == Subdivision_Namecol) {
//														Subdivision_Name[j - 1] = cellGotValue.trim();
//													}
												if (k == Feeder_Codecol) {
													Feeder_Code[j - 1] = cellGotValue.trim();
												}
												if (k == Substation_Codecol) {
													Substation_Code[j - 1] = cellGotValue.trim();
												}
												if (k == Meter_Sr_Numbercol) {
													Meter_Sr_Number[j - 1] = cellGotValue.trim();
												}
												if (k == Meter_Manufacturercol) {
													Meter_Manufacturer[j - 1] = cellGotValue.trim();
												}
												if (k == MFcol) {
													MF[j - 1] = cellGotValue.trim();
												}
												if (k == Town_Codecol) {
													Town_Code[j - 1] = cellGotValue.trim();
												}
												if (k == Feeder_Typecol) {
													Feeder_Type[j - 1] = cellGotValue.trim();
												}

												if (k == latitudecol) {
													latitude[j - 1] = cellGotValue.trim();
												}
												if (k == longitudecol) {
													longitude[j - 1] = cellGotValue.trim();
												}
											}

										}
									}

									for (int n = 0; n < lastRows; n++) {

										try {

											List<String> numbers = Arrays.asList(Feeder_Type[n].trim());
											List<String> bl = Arrays.asList(Meter_Manufacturer[n].trim());

											
									        for (String number : numbers) {
									    		
									            if (number.matches("^[A-Za-z_-]*$")) {
									            	
									            	for(String boundloc : bl) {
									            	
									            	if(boundloc.matches("(?i)[a-z]+([,.\\s]+[a-z]+)*")) {
											
											
											String regexForInteger = "[0-9]*['.']?[0-9]*"; 
										
										        Pattern p = Pattern.compile(regexForInteger);
										        Matcher m = p.matcher(Voltage_Level[n].trim());
										        Matcher o = p.matcher(MF[n].trim()); 
										        Matcher q = p.matcher(latitude[n].trim()); 
										        Matcher r = p.matcher(longitude[n].trim()); 

											if((m.find() && m.group().equals(Voltage_Level[n].trim())) && (o.find() && o.group().equals(MF[n].trim()))
													&& (q.find() && q.group().equals(latitude[n].trim())) && (r.find() && r.group().equals(longitude[n].trim()))) {
											
											
//											System.out.println("Feeder_Name=" + Feeder_Name[n].trim());
//											System.out.println("Voltage_Level=" + Voltage_Level[n].trim());
//											// System.out.println("Circle_Name =" + Circle_Name[n].trim());
//											// System.out.println("Subdivision_Name =" + Subdivision_Name[n].trim());
//											System.out.println("Feeder_Code= " + Feeder_Code[n].trim());
//											System.out.println("Substation_Code = " + Substation_Code[n].trim());
//											System.out.println("Meter_Sr_Number = " + Meter_Sr_Number[n].trim());
//											System.out.println("Meter_Manufacturer = " + Meter_Manufacturer[n].trim());
//											System.out.println("MF =" + MF[n].trim());
//											System.out.println("Town_Code =" + Town_Code[n].trim());
//											System.out.println("Feeder_Type =" + Feeder_Type[n].trim());

											FeederEntity feederEntity = feederdetailsservice.getFeederBySubstationMrtId(
													Town_Code[n].trim(), Feeder_Code[n].trim(),
													Substation_Code[n].trim(), Meter_Sr_Number[n].trim());

											if (feederEntity != null) {
												feederEntity.setFeedername(Feeder_Name[n].trim().toUpperCase());
												feederEntity.setVoltagelevel(Double.valueOf(Voltage_Level[n].trim()));
												feederEntity.setManufacturer(Meter_Manufacturer[n].trim().toUpperCase());
												if(feederEntity.getMf().toString().equalsIgnoreCase(MF[n].trim())) {
													feederEntity.setMf(Double.valueOf(MF[n].trim()));
												}else {
													feederEntity.setMf(Double.valueOf(MF[n].trim()));
													feederEntity.setMfflag(1);
													feederEntity.setMfchangedate(new Timestamp(System.currentTimeMillis()));
												}
												if(!(latitude[n].isEmpty())) {
												feederEntity.setGeo_cord_x(Double.parseDouble(latitude[n].trim()));
												}
												if(!(longitude[n].isEmpty())) {
												feederEntity.setGeo_cord_y(Double.parseDouble(longitude[n].trim()));
												}
												feederEntity.setFeeder_type(Feeder_Type[n].trim().toUpperCase());
												feederEntity.setUpdateby(session.getAttribute("username").toString());
												feederEntity.setUpdatedate(new Timestamp(System.currentTimeMillis()));

												MasterMainEntity master = masterMainService.getEntityByMtrNOandTcode(
														Town_Code[n].trim(), Meter_Sr_Number[n].trim());
												if (master != null) {

													// master.setCircle(Circle_Name[n].trim());
													// master.setSubdivision(Subdivision_Name[n].trim());
													master.setFdrname(Feeder_Name[n].trim().toUpperCase());
													master.setCustomer_name(Feeder_Name[n].trim().toUpperCase());
													master.setVoltage_kv(Voltage_Level[n].trim());
													master.setMtrmake(Meter_Manufacturer[n].trim().toUpperCase());
													master.setMf(MF[n].trim());
													// master.setFeeder_type(Feeder_Type[n].trim().toUpperCase());
													master.setFdrtype(Feeder_Type[n].trim().toUpperCase());
													if(!(latitude[n].isEmpty())) {
													master.setLatitude(latitude[n].trim());
													}
													if(!(longitude[n].isEmpty())) {
													master.setLatitude(longitude[n].trim());
													}

													try {
														masterMainService.update(master);

													} catch (Exception e) {
														e.printStackTrace();
													}
												}
												try {
													feederdetailsservice.update(feederEntity);

												} catch (Exception e) {
													e.printStackTrace();
												}

											}
											msg = "Data Uploaded Successfully";
											model.put("msg", "Data Uploaded Successfully");
											// model.addAttribute("alert_type", "error");
											}else {
												uploadFlag = 3;
												msg = "OOPS! Check Voltage rating,MF,Latitude and longitude shouls contain only numbers!!";
												model.put("msg", "Check Voltage rating,MF,Latitude and longitude shouls contain only numbers!!");
											}
									            }else {
									            	uploadFlag = 3;
													msg = "OOPS! Check Manufacturer it should contains only Alphabets!!";
													model.put("msg", "Check Manufacturer it should contains only Alphabets!!");
									            }
									            	}
									            	
									            }else {
									            	
									            	uploadFlag = 3;
													msg = "OOPS! Check Feeder Type it should contains only Alphabets!!";
													model.put("msg", "Check Feeder Type it should contains only Alphabets!!");
									            	
									            }
									        }
										} catch (Exception e) {
											uploadFlag = 3;
											msg = "OOPS! Something went wrong!!";
											model.put("msg", "OOPS! Something went wrong!!");
											// model.addAttribute("alert_type", "error");
											e.printStackTrace();
										}

									}

									uploadFlag = 1;
										}
									
								

							} catch (Exception e) {
								uploadFlag = 3;
								msg = "OOPS! Something went wrong!!";
								model.put("msg", "OOPS! Something went wrong!!");
								// model.addAttribute("alert_type", "error");
								e.printStackTrace();
							}
										
						}
					} else {
						uploadFlag = 2;
					}

				}

			}

		} catch (Exception e) {
			uploadFlag = 3;
			e.printStackTrace();
		}

		return "redirect:/fdrdata";
	}

	@RequestMapping(value = "/exportToExcelFeederDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object exportToExcelFeederDetails(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		try {

			String Region = request.getParameter("reg");
			String Circle = request.getParameter("cir");
			String town = request.getParameter("twn");
			String feederType = request.getParameter("feeder");
			

			String reg="",cir="",twn="",feeder="";
			if (Region.equalsIgnoreCase("ALL")) {
				reg = "%";
			} else {
				reg = Region;
			}
			if (Circle.equalsIgnoreCase("ALL")) {
				cir = "%";
			} else {
				cir = Circle;
			}
			if (town.equalsIgnoreCase("ALL")) {
				twn = "%";
			} else {
				twn = town;
			}
			
			if (feederType.equalsIgnoreCase("ALL")) {
				feeder = "%";
			} else {
				feeder = feederType;
			}

			String fileName = "FeederSampleDetails";
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

			header.createCell(0).setCellValue("Feeder_Name");
			header.createCell(1).setCellValue("Voltage_Level");
//					header.createCell(2).setCellValue("Circle_Name");
//					header.createCell(3).setCellValue("Subdivision_Name");
			header.createCell(2).setCellValue("Feeder_Code");
			header.createCell(3).setCellValue("Substation_Code");
			header.createCell(4).setCellValue("Meter_Sr_Number");
			header.createCell(5).setCellValue("Meter_Manufacturer");
			header.createCell(6).setCellValue("MF");
			header.createCell(7).setCellValue("Town_Code");
			header.createCell(8).setCellValue("Feeder_Type");
			header.createCell(9).setCellValue("Latitude");
			header.createCell(10).setCellValue("Longitude");
		

			List<?> feederData = null;
			feederData = feederdetailsservice.getfeederdetails(reg, cir, twn,feeder);
			System.out.println("feeder data----"+feederData);

			int count = 1;
			// int cellNO=0;
			for (Iterator<?> iterator = feederData.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();

				XSSFRow row = sheet.createRow(count);
				// cellNO++;
				// row.createCell(0).setCellValue(String.valueOf(cellNO+""));
				if (values[1] == null) {
					row.createCell(0).setCellValue(String.valueOf(""));
				} else {
					row.createCell(0).setCellValue(String.valueOf(values[1]));
				}

				if (values[2] == null) {
					row.createCell(1).setCellValue(String.valueOf(""));
				} else {
					row.createCell(1).setCellValue(String.valueOf(values[2]));
				}

//		      		if(values[3]==null)
//		      		{
//		      			row.createCell(2).setCellValue(String.valueOf(""));
//		      		}else
//		      		{
//		      			row.createCell(2).setCellValue(String.valueOf(values[3]));
//		      		}
//		      		
//		      		if(values[4]==null)
//		      		{
//		      			row.createCell(3).setCellValue(String.valueOf(""));
//		      		}else
//		      		{
//		      			row.createCell(3).setCellValue(String.valueOf(values[4]));
//		      		}

				if (values[7] == null) {
					row.createCell(2).setCellValue(String.valueOf(""));
				} else {
					row.createCell(2).setCellValue(String.valueOf(values[7]));
				}
				if (values[8] == null) {
					row.createCell(3).setCellValue(String.valueOf(""));
				} else {
					row.createCell(3).setCellValue(String.valueOf(values[8]));
				}
				if (values[9] == null) {
					row.createCell(4).setCellValue(String.valueOf(""));
				} else {
					row.createCell(4).setCellValue(String.valueOf(values[9]));
				}
				if (values[10] == null) {
					row.createCell(5).setCellValue(String.valueOf(""));
				} else {
					row.createCell(5).setCellValue(String.valueOf(values[10]));
				}
				if (values[11] == null) {
					row.createCell(6).setCellValue(String.valueOf(""));
				} else {
					row.createCell(6).setCellValue(String.valueOf(values[11]).split("\\.")[0]);
				}
				if (values[20] == null) {
					row.createCell(7).setCellValue(String.valueOf(""));
				} else {
					row.createCell(7).setCellValue(String.valueOf(values[20]));
				}
				if (values[19] == null) {
					row.createCell(8).setCellValue(String.valueOf(""));
				} else {
					row.createCell(8).setCellValue(String.valueOf(values[19]));
				}
				
				if (values[22] == null) {
					row.createCell(9).setCellValue(String.valueOf(""));
				} else {
					row.createCell(9).setCellValue(String.valueOf(values[22]));
				}
				if (values[23] == null) {
					row.createCell(10).setCellValue(String.valueOf(""));
				} else {
					row.createCell(10).setCellValue(String.valueOf(values[23]));
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
			response.setHeader("Content-Disposition", "inline;filename=\"SampleFeederDetails.xlsx" + "\"");
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

	@RequestMapping(value = "/checkFdrNoexistance", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object retriveFdrcode(@RequestParam String fdrcode, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		return feederdetailsservice.isFdrcodeAvail(fdrcode);
	}
	
	@RequestMapping(value = "/feederOutage", method = { RequestMethod.POST, RequestMethod.GET })
	public String feederOutage(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return "redirect:./?sessionVal=expired";
			}

			List<?> zoneList = feederdetailsservice.getDistinctZone();
			model.put("zoneList", zoneList);
			List<?> subdivisionList = feederdetailsservice.getDistinctsubdivision();
			model.put("subdivisionList", subdivisionList);
			List<?> circleList = feederdetailsservice.getcircle();
			//model.put("circleList", circleList);

			String officeType = (String) session.getAttribute("officeType");
			String officeCode = (String) session.getAttribute("officeCode");
			String userType = (String) session.getAttribute("userType");
			String qry = null;
			String ssNameQry = null;

			if (officeType.equals(null)) {
				return "redirect:/logout";
			}

			if (officeType.equalsIgnoreCase("circle")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE circle_code = '" + officeCode + "' ";
			} else if (officeType.equalsIgnoreCase("division")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE division_code = '" + officeCode + "' ";
			} else if (officeType.equalsIgnoreCase("subdivision")) {
				qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE sitecode = '" + officeCode + "' ";
			}

			String SubdivName = null;
			try {
				if (officeType.equalsIgnoreCase("subdivision"))
					SubdivName = (java.lang.String) feederdetailsservice.getCustomEntityManager("postgresMdas")
							.createNativeQuery(qry).getSingleResult();

			} catch (Exception e) {
				e.printStackTrace();
			}
			model.put("msg", msg);

			model.put("officeType", officeType);
			model.addAttribute("userType", userType);
			model.addAttribute("SubdivName", SubdivName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		msg = "";

		return "feederOutage";
	}
	
	
	@RequestMapping(value = "/getfeederOutageReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getfeederOutageReport(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		List<?> feederList=null;
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return "redirect:./?sessionVal=expired";
			}
			
			String month = request.getParameter("month");
			String feeder = request.getParameter("feeder");
			String town = request.getParameter("town");
			String circle = request.getParameter("circle");
			feederList = feederdetailsservice.getfeederOutageReport(month, feeder, town ,circle);	

		} catch (Exception e) {
			e.printStackTrace();
		}
		return feederList;
	}
	
	//feederoutagepdf
	
	@RequestMapping(value = "/FeederOutagePDF", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody  void getfeederOutagePdf(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		String circle ="";
		String town ="";
		String feeder ="";
			String month = request.getParameter("month");
			  feeder = request.getParameter("feeder");
			  town = request.getParameter("town");
			 circle = request.getParameter("circle");
					if(circle==null) { 
						circle ="%";	
					}
					if(feeder==null) {	 
						feeder ="%";	
					}
					if(town==null) {	 
						town ="%";	
					}
					
					
			 feederdetailsservice.getfeederOutagePdf(month, feeder, town ,circle,response, request);	

	}
	
	
	
	@RequestMapping(value = "/getMonthWisefeederOutageReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getMonthWisefeederOutageReport(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		List<?> feederList=null;
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return "redirect:./?sessionVal=expired";
			}
			
			String month = request.getParameter("month");
			String feeder = request.getParameter("feeder");
			String meterno = request.getParameter("meterno");
		//	System.out.println(meterno);
			
			feederList = feederdetailsservice.getMonthWisefeederOutageReport(month, feeder,meterno);	

		} catch (Exception e) {
			e.printStackTrace();
		}
		return feederList;
	}
	
	
	@RequestMapping(value = "/getMonthDurationWisefeederOutageReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getMonthDurationWisefeederOutageReport(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		List<?> feederList=null;
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return "redirect:./?sessionVal=expired";
			}
			
			String month = request.getParameter("month");
			String feeder = request.getParameter("feeder");
			String meterno = request.getParameter("meterno");
		//	System.out.println(meterno);
			
			feederList = feederdetailsservice.getMonthDurationWisefeederOutageReport(month, feeder,meterno);	

		} catch (Exception e) {
			e.printStackTrace();
		}
		return feederList;
	}
}
