package com.bcits.controller;

import java.io.InputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

import com.bcits.entity.DtDetailsEntity;
import com.bcits.mdas.entity.InitialMeterInfo;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.SubstationDetailsEntity;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.substationdetailsservice;
import com.bcits.service.TownMasterService;

@Controller
public class SubstationDetailsController {

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager postgresMdas;

	@Autowired
	substationdetailsservice substationDetails;

	@Autowired
	private FeederMasterService feederMasterService;

	@Autowired
	private TownMasterService townMasterService;

	String msg = "";

	int uploadFlag = 0;

	@Autowired
	private FeederDetailsService feederdetailsservice;
	private java.lang.String officeid;
	private java.lang.String subdivision;

	int SSadded = 0;

	@RequestMapping(value = "/editSubStationDetails/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> editSubStationDetails(@PathVariable String id, ModelMap model,
			HttpServletRequest request) {

		List<?> changedlist = substationDetails.getChangedDetails(Integer.parseInt(id));
		return changedlist;
	}

	@RequestMapping(value = "/editSubStationDetailsNew/{ssid}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> editSubStationDetailsNew(@PathVariable String ssid, ModelMap model,
			HttpServletRequest request) {

		String qry = "SELECT \n"
				+ "ss_name,ss_capacity,sstp_id, string_agg(DISTINCT tp_parent_id, ',') as subdivision,\n"
				+ "string_agg(DISTINCT parent_town, ',') as parent_town,latitude,longitude,dcuno,substation_mva\n"
				+ "FROM meter_data.substation_details where sstp_id='" + ssid + "'\n"
				+ "GROUP BY ss_name,ss_capacity,sstp_id,latitude,longitude,dcuno,substation_mva";
		//System.out.println(qry);
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@RequestMapping(value = "/modifySubstationDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object modifySubStationDetails(ModelMap model, HttpServletRequest request,
			HttpSession session) {
		// System.out.println("modify-substation-details..");
		long currtime = System.currentTimeMillis();
		String ssid = request.getParameter("substaidd");
		String ssname = request.getParameter("subStatName");
		String ssCapacity = request.getParameter("substacapp");
       //Double ssCapacity = Double.parseDouble(sscapacity);
		//Double latitude=Double.parseDouble(request.getParameter("latitude"));
		String latitude=request.getParameter("latitude");
		//Double longitude=Double.parseDouble(request.getParameter("longitude"));
		//Double dcuno=Double.parseDouble(request.getParameter("dcuno"));
		String longitude=request.getParameter("longitude");
		String dcuno=request.getParameter("dcuno");
		
		String editSubstationCapacityinMVA=request.getParameter("editSubstationCapacityinMVA");
		 
		//System.out.println(dcuno);
		
		// String tpsubstacode=request.getParameter("substacodee");
		// System.out.println("tpsubstacode..."+tpsubstacode);
		String tpparcode = request.getParameter("tpparcodee");
		// System.out.println("tpparcode..."+tpparcode);
		String userName = (String) session.getAttribute("username");

		substationDetails.getModifyDetails(ssid, ssname, ssCapacity, null, tpparcode, userName,latitude,longitude,dcuno,editSubstationCapacityinMVA);
		msg = "SUBSTATION DETAILS UPDATED SUCCESFULLY";

		return "substationdetails";
	}

	@RequestMapping(value = "/substationdetails", method = { RequestMethod.POST, RequestMethod.GET })
	public String substationDetails(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession(false);
		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		String userType = (String) session.getAttribute("userType");
		if (officeType == null) {
			return "redirect:/?sessionVal=expired";
		}

		String qry = null;
		String SubdivName = null;
		if (officeType.equalsIgnoreCase("circle")) {
			qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE circle_code = '" + officeCode + "' ";
		} else if (officeType.equalsIgnoreCase("division")) {
			qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE division_code = '" + officeCode + "' ";
		} else if (officeType.equalsIgnoreCase("subdivision")) {
			qry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE sitecode = '" + officeCode + "' ";
		}
		List<?> resultList = null;
		try {
			if (qry != null) {
				resultList = postgresMdas.createNativeQuery(qry).getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (officeType.equalsIgnoreCase("subdivision")) {
			if (qry != null) {
				SubdivName = String.valueOf(postgresMdas.createNativeQuery(qry).getSingleResult());
			}
		}

		model.addAttribute("resultList", resultList);
		model.put("officeType", officeType);
		model.addAttribute("userType", userType);
		model.put("subdivisionList", feederdetailsservice.getDistinctsubdivision());
		model.put("zoneList", feederdetailsservice.getDistinctZone());
		model.addAttribute("SubdivName", SubdivName);
		model.put("msg", msg);
		msg = "";
		return "substationdetails";

	}

	@RequestMapping(value = "/getsubstationdetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object substationdetails(HttpServletRequest request ,ModelMap model,String zone,String circle,String town) {
		HttpSession session = request.getSession(false);
		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		String userType = (String) session.getAttribute("userType");

		System.out.println("officeType=" + officeType + " officeCode= " + officeCode + " userType= " + userType);

		if (officeType.equalsIgnoreCase("circle")) {
			String SitecodeQry = "SELECT SUBDIVISION FROM meter_data.amilocation WHERE circle_code = '" + officeCode
					+ "' ";
		}

		List<?> substationlist = substationDetails.getSubstationDetailsNew();
		 model.put("substationList",substationlist);
		 model.put("officeType",officeType);
		String qry = null;
		 String msg="";
		String SubdivName = null;
		if (officeType.equalsIgnoreCase("circle")) {
			qry = "SELECT DISTINCT SUBDIVISION FROM meter_data.amilocation WHERE circle_code = '" + officeCode + "' ";
		} else if (officeType.equalsIgnoreCase("division")) {
			qry = "SELECT DISTINCT SUBDIVISION FROM meter_data.amilocation WHERE division_code = '" + officeCode + "' ";
		} else if (officeType.equalsIgnoreCase("subdivision")) {
			qry = "SELECT DISTINCT SUBDIVISION FROM meter_data.amilocation WHERE sitecode = '" + officeCode + "' ";
		}

		List<?> resultList = null;
		try {
			if (qry != null) {
				resultList = postgresMdas.createNativeQuery(qry).getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (officeType.equalsIgnoreCase("subdivision")) {
			if (qry != null) {
				SubdivName = String.valueOf(postgresMdas.createNativeQuery(qry).getSingleResult());
			}
		}

		 model.addAttribute("resultList", resultList);
		 List<?> parentfeedervol=substationDetails.getSubdivVolBySubdiv();
		 model.addAttribute("parentfeedervol",parentfeedervol);
		 List<?> zonelist=substationDetails.getDistinctZone();
		 model.addAttribute("zonelist",zonelist);
		 model.addAttribute("SubdivName",SubdivName);
		 model.addAttribute("userType",userType);
		 model.put("msg", msg);
		 msg = "";

		 if (SSadded == 1 ) { model.addAttribute("SSaddedMsg",
		 "Sub-Station added successfully."); SSadded = 0; }
		
		 model.put("subdivisionList",feederdetailsservice.getDistinctsubdivision());
		return substationlist;

	}
	
	@RequestMapping(value = "/mastersubstationdetails", method = { RequestMethod.POST, RequestMethod.GET })
	public String  mastersubstationDetails(HttpServletRequest request, ModelMap model) {
		
		return "mastersubstationDetailsForCirclewise";
		
	}
	@RequestMapping(value = "/getmastersubstationdetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object mastersubstationDetails1(HttpServletRequest request ,ModelMap model) {
		
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town").trim();
		
	
			List<?> list = substationDetails.getmasterSubstationDetailsNew(zone,circle,town);

			return list;
	}
	
	
	@Transactional
	@RequestMapping(value = "/addSubstationdetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object addSubstationdetails(ModelMap model, HttpServletRequest request, Object SubdivisionCode,
			HttpSession session) {
		// System.out.println("in controllee");
		List<?> stationsList = new ArrayList<>();
		String ssname = request.getParameter("ssname");
		String sscapacity = request.getParameter("sscapacity");
		String sstpid = request.getParameter("tpsscode");
		String tpparentid = request.getParameter("tppcode");
		String feedervol = request.getParameter("pfvoltge");
		String pfeeder = request.getParameter("pfeeder");
		String userName = (String) session.getAttribute("username");
		String subdivisionName = request.getParameter("subdiv1");
		String tpTownCode = request.getParameter("TownCode");
		
		String substationCapacityinMVA = request.getParameter("substationCapacityinMVA");

		//System.out.println("substationCapacityinMVA-valu...."+substationCapacityinMVA);
		String ssid = substationDetails.getSsid();
		String psdivision = request.getParameter("psubdiv");
		// System.out.println(ssname+"sscapacity"+sscapacity+"sstpid"+sstpid+"tpparentid"+tpparentid+"feedervol"+feedervol+"pfeeder"+pfeeder);

		// code to get sitecode
		String sitecodeQry = "select sitecode from meter_data.amilocation where subdivision = '" + subdivisionName
				+ "' ";

		String sitecode = null;
		try {
			sitecode = substationDetails.getCustomEntityManager("postgresMdas").createNativeQuery(sitecodeQry)
					.getSingleResult().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SubstationDetailsEntity sde = new SubstationDetailsEntity();
		try {
			if (ssid != null && ssid != "") {
				sde.setSsid(ssid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		sde.setSs_name(ssname);
		sde.setSscapacity(Double.parseDouble(sscapacity));
		if(!substationCapacityinMVA.isEmpty()) {
		sde.setSubstationMva(Double.parseDouble(substationCapacityinMVA));
		}
		sde.setSstpid(sstpid);
		if (tpparentid != "")
			sde.setTpparentid(tpparentid);
		sde.setPfvoltage(Double.parseDouble(feedervol));
		sde.setPfeeder(pfeeder);
		sde.setEntry_by(userName);
		sde.setEntry_date(new Timestamp(new Date().getTime()));
		sde.setUpdate_by(userName);
		sde.setUpdate_date(new Timestamp(new Date().getTime()));

		sde.setOfficeid(Long.parseLong(sitecode));
		sde.setPsdivision(psdivision);

		sde.setParent_town(tpTownCode);
		
		

		try {
			substationDetails.save(sde);
			stationsList = substationDetails.getSubstationDetails();
			SSadded = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stationsList;
	}

	@RequestMapping(value = "/showCircle/{zone}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> showCircle(@PathVariable String zone, HttpServletRequest request, ModelMap model) {
		List<?> circlelist = substationDetails.getCircleByZone(zone, model);
		return circlelist;
	}

	@RequestMapping(value = "/showDivision/{circle}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> showDivision(@PathVariable String circle, HttpServletRequest request, ModelMap model) {
		List<?> divisionlist = substationDetails.getDivisionByCircle(circle, model);
		return divisionlist;
	}

	@RequestMapping(value = "/showSubdivision/{division}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> showSubdivision(@PathVariable String division, HttpServletRequest request,
			ModelMap model) {
		List<?> subdivlist = substationDetails.getSubdivisionByDivision(division, model);
		return subdivlist;
	}

	@RequestMapping(value = "/checkDuplicateParent/{tpSubstaCode}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String checkDuplicate(@PathVariable String tpSubstaCode, HttpServletRequest request,
			ModelMap model) {
		// System.out.println("entered in to check..");
		String qry = null;
		qry = "select  * from meter_data.substation_details  where sstp_id= '" + tpSubstaCode + "'";
		List resultList = null;
		try {
			resultList = substationDetails.getCustomEntityManager("postgresMdas").createNativeQuery(qry)
					.getResultList();
			// System.out.println(resultList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resultList.size() >= 1) {
			return "Code Exist";
		} else {
			return "Code not Exist ";
		}
	}

	@RequestMapping(value = "/deleteSubStationDetails/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String deleteSubStationDetails(@PathVariable String id, String parentid,
			HttpServletRequest request, ModelMap model) {
		String qry = null;
		qry = "Select from meter_data.feederdetails where parentid = '" + id + "'";

		List resultList = null;
		try {
			resultList = substationDetails.getCustomEntityManager("postgresMdas").createNativeQuery(qry)
					.getResultList();
			// System.out.println("result..."+resultList);
			// System.out.println(resultList.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resultList.size() == 0) {
			String qry1 = "update meter_data.substation_details set deleted='yes' where ss_id='" + id + "'";
			// System.out.println("qry1");
			int list;
			try {

				int i = substationDetails.subStationDel(qry1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Deleted";
		} else {
			return "Record Exist ";
		}
	}

	@RequestMapping(value = "/deleteSubStationDetailsNew/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String deleteSubStationDetailsNew(@PathVariable String id, HttpServletRequest request,
			ModelMap model) {
		String qry = "Select * from meter_data.feederdetails where tpparentid = '" + id + "'";
		List<?> list = postgresMdas.createNativeQuery(qry).getResultList();
		if (!list.isEmpty()) {
			return "Record Exist ";
		} else {
			String qry1 = "update meter_data.substation_details set deleted=1 where sstp_id='" + id + "'";
			int i = postgresMdas.createNativeQuery(qry1).executeUpdate();
			if (i > 0) {
				return "Deleted";
			} else {
				return "OOPs Something went wrong!";
			}
		}
	}
	
	@Transactional
	@RequestMapping(value = "/subStationDetails/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String subStationDetails(@PathVariable String id, HttpServletRequest request,
			ModelMap model) {
		    //System.err.println("Hii controller"+id);
			String qry1 = "update meter_data.substation_details set deleted=1 where sstp_id='" + id + "'";
			System.out.println(qry1);
			try {
				int i = postgresMdas.createNativeQuery(qry1).executeUpdate();
				if (i > 0) {
					return "Deleted";
				} else {
					return "OOPs Something went wrong!";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "OOPs Something went wrong!"; 
				
			}
	}

	@RequestMapping(value = "/showParentFeeder/{parentfeedervol}/{subdiv}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody List<?> showParentFeeder(@PathVariable Double parentfeedervol, @PathVariable String subdiv,
			String sitecode, HttpServletRequest request, ModelMap model) {
		String qry = "select sitecode from meter_data.amilocation where subdivision = '" + subdiv + "'";
		String result = null;
		try {
			sitecode = substationDetails.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult()
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<?> feederlist = substationDetails.getParentfeeder(parentfeedervol, sitecode);
		return feederlist;
	}

	@RequestMapping(value = "/getSubdivisionForSubstation", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getSubdivisionForSubstation(HttpServletRequest request) {
		String tpparentid = request.getParameter("ssid");
//		String qry = 
//						  "SELECT DISTINCT tp_subdivcode, subdivision FROM meter_data.amilocation WHERE tp_subdivcode in \n"
//						  + "(\n" +
//						  "SELECT DISTINCT tp_parent_id FROM meter_data.substation_details WHERE sstp_id='"
//						  + ssid + "'\n" + ") ORDER BY tp_subdivcode, subdivision;";
					 
			
//		String qry ="\r\n" + 
//				"select DISTINCT feedername,tpparentid from meter_data.feederdetails WHERE tpparentid='"+tpparentid+"' and meterno IN (select mtrno from meter_data.master_main WHERE fdrcategory='FEEDER METER');";
		
		
	String qry = "SELECT distinct tpparentid,feedername FROM meter_data.feederdetails WHERE tpparentid='"+tpparentid+"'";	
		System.out.println(qry);
		List<?> list = postgresMdas.createNativeQuery(qry).getResultList();
		return list;

	}

	@RequestMapping(value = "/getTownForSubstation", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getTownForSubstation(HttpServletRequest request) {
		String ssid = request.getParameter("ssid");
		String qry = "SELECT DISTINCT tp_towncode, town_ipds FROM meter_data.amilocation WHERE tp_towncode in \n"
				+ "(\n" + "SELECT DISTINCT parent_town FROM meter_data.substation_details WHERE sstp_id='" + ssid
				+ "'\n" + ") ORDER BY tp_towncode, town_ipds;";

		List<?> list = postgresMdas.createNativeQuery(qry).getResultList();
		return list;

	}
	
	@RequestMapping(value = "/getSubdivisionforfeeder", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getSubdivisionforfeeder(HttpServletRequest request) {
		String tpparentid = request.getParameter("tpparentid");
					 
			
		String qry ="\r\n" + 
				"select DISTINCT feedername,tpparentid from meter_data.feederdetails WHERE tpparentid='"+tpparentid+"' and meterno IN (select mtrno from meter_data.master_main WHERE fdrcategory='FEEDER METER');";
		System.out.println(qry);
		List<?> list = postgresMdas.createNativeQuery(qry).getResultList();
		return list;

	}
	
	
	@RequestMapping(value = "/SubstationDetailsPDF", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void SubstationDetailsPDF(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		
		String zonename="",cirname="",townname="";
		
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
		
		substationDetails.getSubstationDtlspdf(request, response, zonename,cirname, townname);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/uploadSubstationFile", method = { RequestMethod.GET, RequestMethod.POST })
	public String uploadSubstationFile(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			HttpSession session) {
		String extension = null;
		String excel = "xlsx";
		try {

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myFile = multipartRequest.getFile("fileUpload");

			String fileName = myFile.getOriginalFilename();

			if (myFile != null && !myFile.isEmpty() && myFile.getOriginalFilename() != " ") {

				extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

				if (!extension.equals(excel)) {
					uploadFlag = 1;
					msg = "Please choose only .xlsx file to upload.!!!!!";
					model.put("msg", "Please choose only .xlsx file to upload.!!!!!");
					
				} else {

					InputStream file = myFile.getInputStream();
					XSSFWorkbook workbook = new XSSFWorkbook(file);
					XSSFSheet sheet = workbook.getSheetAt(0);

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

								String[] Substation_Name = new String[lastRows + 1];
								String[] Capacity = new String[lastRows + 1];
								String[] Substation_Code = new String[lastRows + 1];
								String[] latitude = new String[lastRows + 1];
								String[] longitude = new String[lastRows + 1];
								String[] DcuNo = new String[lastRows + 1];

								if (workbook.getSheetAt(i).getRow(0) != null) {

									lastColumn = workbook.getSheetAt(i).getRow(0).getLastCellNum();

									String cellGotValue = "";

									int Substation_Namecol = 0;
									int Capacitycol = 0;
									int Substation_Codecol = 0;
									int latitudecol= 0;
									int longitudecol = 0;
									int DcuNocol=0;
									

									if (lastRows == 0) {
										uploadFlag = 2;
										msg = "Records are not avaliable in excel to upload";
										model.put("msg", "Records are not avaliable in excel to upload");
									}

									for (int j = 0; j <= lastRows; j++) {

										if (j == 0)// To get Column Names First row in Excel
										{
											for (int k = j; k < lastColumn; k++) {
												XSSFCell cellNull = workbook.getSheetAt(i).getRow(j).getCell(k);
												if (cellNull != null) {
													cellGotValue = cellNull.getStringCellValue();
													cellGotValue = cellGotValue.trim();

												}
												if (cellGotValue.equalsIgnoreCase("Substation_Name")) {
													Substation_Namecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Voltage_level")) {
													Capacitycol = k;

												}
												if (cellGotValue.equalsIgnoreCase("Substation_Code")) {
													Substation_Codecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("latitude")) {
													latitudecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("longitude")) {
													longitudecol = k;

												}
												
												if (cellGotValue.equalsIgnoreCase("dcuno")) {
													DcuNocol = k;

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

												if (k == Substation_Namecol) {
													Substation_Name[j - 1] = cellGotValue.trim();
												}
												if (k == Capacitycol) {
													Capacity[j - 1] = cellGotValue.trim();
												}
												if (k == Substation_Codecol) {
													Substation_Code[j - 1] = cellGotValue.trim();
												}
												if (k == latitudecol) {
													latitude[j - 1] = cellGotValue.trim();
												}
												if (k == longitudecol) {
													longitude[j - 1] = cellGotValue.trim();
												}
												
												if (k == DcuNocol) {
													DcuNo[j - 1] = cellGotValue.trim();
												}

											}

										}
									}

									for (int n = 0; n < lastRows; n++) {

										try {

											System.out.println("Substation_Name=" + Substation_Name[n].trim());
											System.out.println("Capacity=" + Capacity[n].trim());
											System.out.println("Substation_Code =" + Substation_Code[n].trim());

											List<SubstationDetailsEntity> info = substationDetails
													.getSubstationBySSTpCode(Substation_Code[n].trim());

											if (!info.isEmpty()) {
												System.out.println("Entity Size=" + info.size());
												for (SubstationDetailsEntity item : info) {

													try {
														item.setSs_name(Substation_Name[n].trim());
														if(!(Capacity[n].isEmpty())) {
														item.setSscapacity(Double.valueOf(Capacity[n].trim()));
														}
														item.setUpdate_by(session.getAttribute("username").toString());
														item.setUpdate_date(new Timestamp(System.currentTimeMillis()));
														if(!(latitude[n].isEmpty())) {
													   item.setLatitude(Double.parseDouble(latitude[n]));}
														if(!(longitude[n].isEmpty())) {
													   item.setLongitude(Double.parseDouble(longitude[n]));
														}
														/*
														 * if(!(DcuNo[n].isEmpty())) {
														 * item.setDcuno(Double.parseDouble(DcuNo[n])); }
														 */

														try {
															substationDetails.update(item);
														} catch (Exception e) {
															e.printStackTrace();
														}
													} catch (Exception e) {
														e.printStackTrace();

													}

												}

											}
											msg = "Data Uploaded Successfully";
											model.put("msg", "Data Uploaded Successfully");

										} catch (Exception e) {
											msg = "OOPS! Something went wrong!!";
											model.put("msg", "OOPS! Something went wrong!!");
											e.printStackTrace();
										}

									}


								}

							} catch (Exception e) {
								msg = "OOPS! Something went wrong!!";
								model.put("msg", "OOPS! Something went wrong!!");
								e.printStackTrace();
							}

						}
					} 

				}

			}

		} catch (Exception e) {
			msg = "OOPS! Something went wrong!!";
			model.put("msg", "OOPS! Something went wrong!!");
			e.printStackTrace();
		}
		return "redirect:/substationdetails";
	}

	@RequestMapping(value = "/SubstationExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object SubstationExcel(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		
	
		
		
		try {

			String fileName = "SubstationDetails";
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
	
			header.createCell(1).setCellValue("Substation Name");
			header.createCell(2).setCellValue("Voltage level");
			header.createCell(3).setCellValue("Substation Code");
			header.createCell(4).setCellValue("Substation Count");
			header.createCell(5).setCellValue("Town Count");
			header.createCell(6).setCellValue("Latitude");
			header.createCell(7).setCellValue("Longitude");
			header.createCell(8).setCellValue("DCUNo");
			header.createCell(9).setCellValue("Capacityin (in MVA)");
			header.createCell(10).setCellValue("Entry By");
			header.createCell(11).setCellValue("Entry Date");
			header.createCell(12).setCellValue("Update BY");
			header.createCell(13).setCellValue("Update Date");

			List<?> substationlist = null;
			substationlist = substationDetails.getSubstationDetailsNew();

			int count = 1;
			int cellNO = 0;
			for (Iterator<?> iterator = substationlist.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();

				XSSFRow row = sheet.createRow(count);
				cellNO++;
				row.createCell(0).setCellValue(String.valueOf(cellNO + ""));

				/*
				 * if(values[0]==null) { row.createCell(0).setCellValue(String.valueOf(""));
				 * }else { row.createCell(0).setCellValue(String.valueOf(values[0])); }
				 */
		
				
				
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
				
				if (values[9] == null) {
					row.createCell(6).setCellValue(String.valueOf(""));
				} else {
					row.createCell(6).setCellValue(String.valueOf(values[9]));
				}
				
				if (values[10] == null) {
					row.createCell(7).setCellValue(String.valueOf(""));
				} else {
					row.createCell(7).setCellValue(String.valueOf(values[10]));
				}
				
				if (values[11] == null) {
					row.createCell(8).setCellValue(String.valueOf(""));
				} else {
					row.createCell(8).setCellValue(String.valueOf(values[11]));
				}
				
				if (values[12] == null) {
					row.createCell(9).setCellValue(String.valueOf(""));
				} else {
					row.createCell(9).setCellValue(String.valueOf(values[12]));
				}
				
				
				if (values[5] == null) {
					row.createCell(10).setCellValue(String.valueOf(""));
				} else {
					row.createCell(10).setCellValue(String.valueOf(values[5]));
				}
				if (values[6] == null) {
					row.createCell(11).setCellValue(String.valueOf(""));
				} else {
					row.createCell(11).setCellValue(String.valueOf(values[6]));
				}
				if (values[7] == null) {
					row.createCell(12).setCellValue(String.valueOf(""));
				} else {
					row.createCell(12).setCellValue(String.valueOf(values[7]));
				}

				if (values[8] == null) {
					row.createCell(13).setCellValue(String.valueOf(""));
				} else {
					row.createCell(13).setCellValue(String.valueOf(values[8]));

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
			response.setHeader("Content-Disposition", "inline;filename=\"SubstationDetails.xlsx" + "\"");
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
	
	@RequestMapping(value = "/SubstationmasterExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object SubstationmasterExcel(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		
		String zone=request.getParameter("zone");
		String circle=request.getParameter("circle");
		String town_code = request.getParameter("town_code").trim();
	
  		
  		if (zone.equalsIgnoreCase("ALL")){
  			zone="%";
		}
  		if (circle.equalsIgnoreCase("ALL")){
  			circle="%";
		}
		if (town_code.equalsIgnoreCase("ALL")){
			town_code="%";
		}
		
		
		try {

			String fileName = "SubstationDetails";
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
			header.createCell(1).setCellValue("Substation Name");
			header.createCell(2).setCellValue("Voltage level");
			header.createCell(3).setCellValue("Substation Code");
			header.createCell(4).setCellValue("Substation Count");
			header.createCell(5).setCellValue("Town Count");
			header.createCell(6).setCellValue("Latitude");
			header.createCell(7).setCellValue("Longitude");
			header.createCell(8).setCellValue("DCUNo");
			header.createCell(9).setCellValue("Capacityin (in MVA)");
			header.createCell(10).setCellValue("Entry By");
			header.createCell(11).setCellValue("Entry Date");
			header.createCell(12).setCellValue("Update BY");
			header.createCell(13).setCellValue("Update Date");
			header.createCell(14).setCellValue("Region");
			header.createCell(15).setCellValue("Circle");
			header.createCell(16).setCellValue("Town");

			List<?> substationlist = null;
			substationlist = substationDetails.getmasterSubstationDetailsNew(zone,circle,town_code);

			int count = 1;
			int cellNO = 0;
			for (Iterator<?> iterator = substationlist.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();

				XSSFRow row = sheet.createRow(count);
				cellNO++;
				row.createCell(0).setCellValue(String.valueOf(cellNO + ""));

				/*
				 * if(values[0]==null) { row.createCell(0).setCellValue(String.valueOf(""));
				 * }else { row.createCell(0).setCellValue(String.valueOf(values[0])); }
				 */
		
				
				
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
				
				if (values[9] == null) {
					row.createCell(6).setCellValue(String.valueOf(""));
				} else {
					row.createCell(6).setCellValue(String.valueOf(values[9]));
				}
				
				if (values[10] == null) {
					row.createCell(7).setCellValue(String.valueOf(""));
				} else {
					row.createCell(7).setCellValue(String.valueOf(values[10]));
				}
				
				if (values[11] == null) {
					row.createCell(8).setCellValue(String.valueOf(""));
				} else {
					row.createCell(8).setCellValue(String.valueOf(values[11]));
				}
				
				if (values[12] == null) {
					row.createCell(9).setCellValue(String.valueOf(""));
				} else {
					row.createCell(9).setCellValue(String.valueOf(values[12]));
				}
				
				
				if (values[5] == null) {
					row.createCell(10).setCellValue(String.valueOf(""));
				} else {
					row.createCell(10).setCellValue(String.valueOf(values[5]));
				}
				if (values[6] == null) {
					row.createCell(11).setCellValue(String.valueOf(""));
				} else {
					row.createCell(11).setCellValue(String.valueOf(values[6]));
				}
				if (values[7] == null) {
					row.createCell(12).setCellValue(String.valueOf(""));
				} else {
					row.createCell(12).setCellValue(String.valueOf(values[7]));
				}

				if (values[8] == null) {
					row.createCell(13).setCellValue(String.valueOf(""));
				} else {
					row.createCell(13).setCellValue(String.valueOf(values[8]));

				}
				

				if (values[13] == null) {
					row.createCell(14).setCellValue(String.valueOf(""));
				} else {
					row.createCell(14).setCellValue(String.valueOf(values[13]));
				}
				

				if (values[14] == null) {
					row.createCell(15).setCellValue(String.valueOf(""));
				} else {
					row.createCell(15).setCellValue(String.valueOf(values[14]));
				}
				
				if (values[15] == null) {
					row.createCell(16).setCellValue(String.valueOf(""));
				} else {
					row.createCell(16).setCellValue(String.valueOf(values[16]));
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
			response.setHeader("Content-Disposition", "inline;filename=\"SubstationmasterDetails.xlsx" + "\"");
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

	@RequestMapping(value = "/SampleSubstationExcelDownload", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object SampleSubstationExcelDownload(HttpServletRequest request, HttpServletResponse response,
			ModelMap model,String zone,String circle,String town) {
		try {

			String fileName = "SubstationDetails";
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

			header.createCell(0).setCellValue("Substation_Name");
			header.createCell(1).setCellValue("Voltage_level");
			header.createCell(2).setCellValue("Substation_Code");
			header.createCell(3).setCellValue("Latitude");
			header.createCell(4).setCellValue("Longitude");

			List<?> substationlist = null;
			substationlist = substationDetails.getSubstationDetailsNew();

			int count = 1;
			// int cellNO=0;
			for (Iterator<?> iterator = substationlist.iterator(); iterator.hasNext();) {
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
				
				if (values[9] == null ) {
					row.createCell(3).setCellValue(String.valueOf(""));
				} else {
					row.createCell(3).setCellValue(String.valueOf(values[9]));
				}
				if (values[10] == null) {
					row.createCell(4).setCellValue(String.valueOf(""));
				} else {
					row.createCell(4).setCellValue(String.valueOf(values[10]));
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
			response.setHeader("Content-Disposition", "inline;filename=\"SampleSubstationDetails.xlsx" + "\"");
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
