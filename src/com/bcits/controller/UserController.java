package com.bcits.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.lingala.zip4j.core.ZipFile;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import com.bcits.entity.Assesment;
import com.bcits.entity.CMRIEntity;
import com.bcits.entity.MeterMaster;
import com.bcits.entity.MobileGenStatusEntity;
import com.bcits.entity.Mrname;
import com.bcits.entity.User;
import com.bcits.entity.UserAccessType;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.service.AssesmentService;
import com.bcits.service.CMRIService;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.D5DataService;
import com.bcits.service.D9DataService;
import com.bcits.service.MasterService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.MobileGenStatusService;
import com.bcits.service.MrnameService;
import com.bcits.service.RdngMonthService;
import com.bcits.service.ReadingRemarkService;
import com.bcits.service.SdoJccService;
import com.bcits.service.UserAccessTypeService;
import com.bcits.service.UserService;
import com.bcits.service.cmriDeviceService;
import com.bcits.utility.MDMLogger;

@Controller
public class UserController {

	/*
	 * @Autowired private GenericService genericService;
	 */

	@Autowired
	private cmriDeviceService cmriDeviceService;

	@Autowired
	public ReadingRemarkService readingRemarkService;

	@Autowired
	public MrnameService mrnameService;

	@Autowired
	private CMRIService cmriService;

	@Autowired
	private UserService userService;
	@Autowired
	private MasterService masterService;

	@Autowired
	private D9DataService d9DataService;

	@Autowired
	private RdngMonthService rdngMonthService;

	@Autowired
	private AssesmentService assesmentService;

	@Autowired
	private D5DataService d5DataService;

	@Autowired
	private SdoJccService sdoJccService;

	@Autowired
	private UserAccessTypeService userAccessTypeService;

	@Autowired
	private MobileGenStatusService mobileGenStatusService;

	@Autowired
	private MeterMasterService metMasterService;

	@Autowired
	private FeederMasterService feederService;

	@Autowired
	ConsumerMasterService consumerMasterService;

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	/*
	 * @PersistenceContext(unitName="mdm") protected EntityManager PGentityManager;
	 */

	public static String uploadPath = "MIP_uploads";
	public static String unZipPath = "MIP_unzippedFiles";

	/*
	 * public static String parseXml = ""; public static String duplicate = "";
	 * public static String meterNotExist = ""; public static int parsedCount = 0;
	 * public static int duplicateCount = 0; public static int meterNotExistCount =
	 * 0; public static double mainTime = 0;
	 * 
	 * public static int corruptedFileCount = 0;
	 * 
	 * public static String corruptedFile = "";
	 */
	int addedSucces;
	public static double mainTime = 0;
	@Autowired
	private MeterMasterService meterMasterService;

	@RequestMapping(value = "/fullview", method = { RequestMethod.GET, RequestMethod.POST })
	public String fulDegree(HttpServletRequest request) {
		return "360degreeview";
	}

	private String zone = "", circle = "", division = "", subDivision = "";

	@RequestMapping(value = "/dashboard", method = { RequestMethod.GET, RequestMethod.POST })
	public String dashboard(@RequestParam String type, @RequestParam String value, HttpServletRequest request,
			ModelMap model) {
		System.out.println("inside mdm dashboard");
		String label = null;
		type = type.trim();
		value = value.trim();
		List<Map<String, Object>> finaldata = new ArrayList<Map<String, Object>>();

		/************************************************/

		long totalConsumers = masterService.FindTotalConsumerCount();
		SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = dateFormat.format(cal.getTime());
		cal.add(Calendar.MONTH, -1);
		String billmonth = sdfBillDate.format(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		String billmonth1 = sdfBillDate.format(cal.getTime());
		Date d1 = new Date();
		String today = dateFormat.format(d1);
		System.out.println("today--" + today);
		model.put("todayDate", today);

		/*
		 * if ((value.equalsIgnoreCase("zone"))) //||
		 * (value.equalsIgnoreCase("subDivisionNew") ) ){ --circle { String zoneQuery =
		 * "SELECT DISTINCT(M.zone) FROM meter_data.master M"; List<String> totalZones=
		 * PGentityManager.createNativeQuery(zoneQuery).getResultList();
		 * 
		 * 
		 * for(String zoneLevel : totalZones){ Map<String, Object> map=new
		 * HashMap<String,Object>();
		 * 
		 * String zoneTotalMetere =
		 * " SELECT count(*) as total_meters FROM meter_data.master M INNER JOIN meter_data.metermaster MM ON M .accno = MM.accno WHERE  M.zone='"
		 * +zoneLevel+"' AND  MM.rdngmonth='"+billmonth1+"' "; int meterCount=
		 * Integer.parseInt(String.valueOf(PGentityManager.createNativeQuery(
		 * zoneTotalMetere).getSingleResult()));
		 * 
		 * String uploadedCount =
		 * "SELECT 	COUNT (CASE 	WHEN mstat.create_status = 1 THEN 1	END	) AS uploaded FROM	meter_data.xml_upload_status mstat,	meter_data.master_main mm WHERE	mm.mtrno = mstat.meter_number\n"
		 * + "AND mstat.file_date = (CURRENT_DATE - 1) AND mm.zone = '"+zoneLevel+"' ";
		 * 
		 * String uploadedCount = "SELECT \"count\"(*) FROM \n" +
		 * "(select  meterno, accno as cdf_accno  from meter_data.cdf_data where to_char(readdate, 'dd-mm-yyyy') = '"
		 * +yesterday+"')A \n" + "INNER JOIN \n" +
		 * "(select MR.accno,MR.zone,MR.circle from meter_data.master MR WHERE MR.zone='"
		 * +zoneLevel+"' GROUP BY MR.accno,MR.zone,MR.circle )B \n" +
		 * "ON B.accno=A.cdf_accno\n" + "WHERE B.zone='"+zoneLevel+"' ";
		 * 
		 * 
		 * int uploadCountData=
		 * Integer.parseInt(String.valueOf(consumerMasterService.getCustomEntityManager(
		 * "postgresMdas").createNativeQuery(uploadedCount).getSingleResult()));
		 * 
		 * map.put("uploadCount", uploadCountData); map.put("total_meters", meterCount);
		 * map.put("zone", zoneLevel); finaldata.add(map); value = "circle"; label =
		 * "Zone"; }
		 * 
		 * 
		 * }else
		 */

		if (value.equalsIgnoreCase("circle")) {
			String totalCircles = "SELECT DISTINCT(M.circle) FROM meter_data.master M ";

			List<String> totalCircle = entityManager.createNativeQuery(totalCircles).getResultList();
			for (String circle : totalCircle) {
				Map<String, Object> map = new HashMap<String, Object>();

				// System.out.println(circle);
				// String circleMeterCount = "SELECT count(*) as total_meters FROM
				// meter_data.master M INNER JOIN meter_data.metermaster MM ON M.accno =
				// MM.accno WHERE M.circle='"+circle+"' AND MM.rdngmonth='"+billmonth1+"' GROUP
				// BY M.circle";
				String circleMeterCount = "SELECT count(*) as total_meters FROM  meter_data.metermaster MM  WHERE \n"
						+ "  MM.circle='" + circle + "' AND  MM.rdngmonth='" + billmonth1 + "' GROUP BY  MM.circle";
				int meterCount = Integer
						.parseInt(String.valueOf(entityManager.createNativeQuery(circleMeterCount).getSingleResult()));
				// System.err.println("meterCount=="+circleMeterCount+ " --"+meterCount);

				String uploadedCount = "SELECT count(*) as total_meters FROM meter_data.master M INNER JOIN meter_data.metermaster MM ON M.accno = MM.accno \n"
						+ "WHERE   M.circle='" + circle + "' AND  MM.rdngmonth='" + billmonth1 + "' AND MM.metrno IN(\n"
						+ " SELECT DISTINCT meter_number FROM meter_data.load_survey WHERE to_char(read_time,'dd-MM-YYYY')='"
						+ today + "'\n" + ")";

				/*
				 * String uploadedCount = "SELECT \"count\"(*) FROM \n" +
				 * "(select  meterno, accno as cdf_accno  from meter_data.cdf_data where to_char(readdate, 'dd-mm-yyyy') = '"
				 * +yesterday+"')A \n" + "INNER JOIN \n" +
				 * "(select MR.accno,MR.zone,MR.circle from meter_data.master MR WHERE MR.circle='"
				 * +circle+"' GROUP BY MR.accno,MR.zone,MR.circle )B \n" +
				 * "ON B.accno=A.cdf_accno\n" + "WHERE B.zone='"+type+"' ";
				 */

				// String uploadedCount=
				System.err.println("circle wise upload count--" + uploadedCount);
				int uploadCountData = Integer.parseInt(String.valueOf(consumerMasterService
						.getCustomEntityManager("postgresMdas").createNativeQuery(uploadedCount).getSingleResult()));
				// System.err.println("uploadedCount=="+uploadedCount);

				map.put("uploadCount", uploadCountData);
				map.put("total_meters", meterCount);
				map.put("zone", circle);

				finaldata.add(map);
			}

			value = "division";
			label = "Circle";
			zone = type;
		} else if (value.equalsIgnoreCase("division")) {

			// System.out.println("type--"+type+" billmonth1==="+billmonth1+" ---
			// billmonth-"+billmonth);

			String totaldivisons = "SELECT DISTINCT(M.division) FROM meter_data.master M WHERE  M.circle='" + type
					+ "'  ";
			List<String> totaldivison = entityManager.createNativeQuery(totaldivisons).getResultList();

			for (String division : totaldivison) {
				Map<String, Object> map = new HashMap<String, Object>();

				// System.out.println("divisionss--"+division);
				String divisionMeterCount = "SELECT count(*) as total_meters FROM meter_data.master M INNER JOIN meter_data.metermaster MM ON M .accno = MM.accno WHERE M.circle='"
						+ type + "' and m.division ='" + division + "' AND  MM.rdngmonth='" + billmonth1
						+ "' GROUP BY  M.division";
				int meterCount = Integer.parseInt(
						String.valueOf(entityManager.createNativeQuery(divisionMeterCount).getSingleResult()));

				// String uploadedCount = "SELECT COUNT (CASE WHEN mstat.create_status = 1 THEN
				// 1 END ) AS uploaded FROM meter_data.xml_upload_status mstat,
				// meter_data.master_main mm WHERE mm.mtrno = mstat.meter_number\n" +
				// "AND mstat.file_date = (CURRENT_DATE - 1) AND mm.zone = '"+zone+"' AND
				// mm.circle ='"+type+"' AND mm.division ='"+division+"' ";

				String uploadedCount = "SELECT count(*) as total_meters FROM meter_data.master M INNER JOIN meter_data.metermaster MM ON M.accno = MM.accno\n"
						+ "		    			 WHERE   M.circle='" + type + "' AND M.division='" + division
						+ "' AND  MM.rdngmonth='" + billmonth1 + "' AND MM.metrno IN( \n"
						+ "		    			  SELECT DISTINCT meter_number FROM meter_data.load_survey WHERE to_char(read_time,'dd-MM-YYYY')='"
						+ today + "'\n" + "		    			 )";

				// System.out.println("uploadedCount--"+uploadedCount);
				int uploadCountData = Integer.parseInt(String.valueOf(consumerMasterService
						.getCustomEntityManager("postgresMdas").createNativeQuery(uploadedCount).getSingleResult()));

				map.put("uploadCount", uploadCountData);
				map.put("total_meters", meterCount);
				map.put("zone", division);
				finaldata.add(map);
			}

			value = "subDivision";
			label = "Division";
			circle = type;
			model.put("divFinal", "divFinal");
		} else if (value.equalsIgnoreCase("subDivision")) {

			String totalSubdivisons = "SELECT DISTINCT(M.subdiv) FROM meter_data.master M WHERE M.circle='" + circle
					+ "'  AND M.division =  '" + type + "' ";
			List<String> totalSubdivison = entityManager.createNativeQuery(totalSubdivisons).getResultList();

			for (String subDivision : totalSubdivison) {
				Map<String, Object> map = new HashMap<String, Object>();

				String subDivsionMeterCount = "SELECT count(*) as total_meters FROM meter_data.master M INNER JOIN meter_data.metermaster MM ON M .accno = MM.accno WHERE m.zone='"
						+ zone + "' and M.circle='" + circle + "' and m.division ='" + type + "'  AND m.subdiv ='"
						+ subDivision + "' AND MM.rdngmonth='" + billmonth1 + "' GROUP BY  M.subdiv";
				int meterCount = Integer.parseInt(
						String.valueOf(entityManager.createNativeQuery(subDivsionMeterCount).getSingleResult()));

				// String uploadedCount = "SELECT COUNT (CASE WHEN mstat.create_status = 1 THEN
				// 1 END ) AS uploaded FROM meter_data.xml_upload_status mstat,
				// meter_data.master_main mm WHERE mm.mtrno = mstat.meter_number\n" +
				// "AND mstat.file_date = (CURRENT_DATE - 1) AND mm.zone = '"+zone+"' AND
				// mm.circle ='"+circle+"' AND mm.division ='"+type+"' AND mm.subdivision
				// ='"+subDivision+"' ";

				/*
				 * String uploadedCount = "SELECT \"count\"(*) FROM \n" +
				 * "(select  meterno, accno as cdf_accno  from meter_data.cdf_data where to_char(readdate, 'dd-mm-yyyy') = '"
				 * +yesterday+"')A \n" + "INNER JOIN \n" +
				 * "(select MR.accno,MR.zone,MR.circle,MR.division from meter_data.master MR WHERE MR.subdiv='"
				 * +subDivision+"' GROUP BY MR.accno,MR.zone,MR.circle,MR.division )B \n" +
				 * "ON B.accno=A.cdf_accno\n" +
				 * "WHERE B.zone='"+zone+"' AND B.circle = '"+circle+"' AND B.division ='"
				 * +type+"' ";
				 */
				// System.out.println("uploadedCount" +uploadedCount);

				// int uploadCountData=
				// Integer.parseInt(String.valueOf(consumerMasterService.getCustomEntityManager("postgresMdas").createNativeQuery(uploadedCount).getSingleResult()));

				map.put("uploadCount", meterCount);
				map.put("total_meters", meterCount);
				map.put("zone", subDivision);
				finaldata.add(map);
			}
			value = "subDivisionNew";
			label = "Sub-Division";
		}
		model.addAttribute("value", value);
		model.addAttribute("finaldata", finaldata);
		model.addAttribute("label", label);

		// d9DataService.tamperEventData(billmonth1, model);
		// model.put("d5DataList",d5DataService.showEventDetails(billmonth1,model));
		masterService.FindMakewiseConsumerCount(billmonth1, model);
		model.addAttribute("total", totalConsumers);
		model.put("installDetails", meterMasterService.getInstallationDetails(billmonth1, "UHBVN", model, request));

		// model.put("installDetails",meterMasterService.getInstallationDetails(billmonth,"UHBVN",model,request));
		return "userPage";
	}

	@RequestMapping(value = "/dashboardLazy", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object dashboardView(HttpServletRequest request, HttpSession session, ModelMap model,
			@RequestParam String dateVal) {
		Calendar cal = Calendar.getInstance();

		System.out.println("inside dashboardlazy");

		List<List<Object>> l3 = new ArrayList<>();
		/* if(dateVal.equalsIgnoreCase("billmonth1")){ */
		model.put("d5DataList", d5DataService.showEventDetails(dateVal, model));
		List<Object> l1 = (List<Object>) model.get("d5DataList");
		List<Object> l2 = (List<Object>) model.get("defectiveList");
		l3.add(l1);
		l3.add(l2);

		return l3;

	}

	// static String billmon=null;
	@RequestMapping(value = "/getMonthDashboardData", method = { RequestMethod.GET, RequestMethod.POST })
	public String getMonthDashboardData(HttpServletRequest request, ModelMap model, HttpSession session) {
		/*
		 * long totalConsumers = masterService.FindTotalConsumerCount();
		 * 
		 * String billmonth = request.getParameter("month"); List list1 =
		 * meterMasterService.getMeterMakeWiseData(Integer.parseInt(billmonth));
		 * d9DataService.tamperEventData(billmonth, model);
		 * model.addAttribute("meterMakeData", list1); model.addAttribute("total",
		 * totalConsumers);
		 * model.addAttribute("selectedMonth",request.getParameter("month"));
		 * //model.put("d5DataList",d5DataService.showEventDetails(billmonth,model));
		 * masterService.FindMakewiseConsumerCount(billmonth, model);
		 */
		long totalConsumers = masterService.FindTotalConsumerCount();

		String billmonth = request.getParameter("month");
		/*
		 * List list1 =
		 * meterMasterService.getMeterMakeWiseData(Integer.parseInt(billmonth));
		 */
		// billmon=billmonth;
		System.err.println("billmon----------- " + session.getAttribute("selectedMonth"));
		// billmon=billmonth;

		if (billmonth == null) {
			billmonth = session.getAttribute("selectedMonth").toString();
		}
		/*
		 * Date d1=new Date(); SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		 * String todayDate=sdf.format(d1); System.out.println(todayDate);
		 * model.put("todayDate", todayDate);
		 */
		session.setAttribute("selectedMonth", billmonth);
		System.err.println("month----------- " + billmonth);
		System.err.println("billmon----------- " + session.getAttribute("selectedMonth"));
		model.put("selectedMonth", billmonth);
		// d9DataService.tamperEventData(billmonth, model);
		/* model.addAttribute("meterMakeData", list1); */
		model.addAttribute("total", totalConsumers);
		model.put("d5DataList", d5DataService.showEventDetails(billmonth, model));
		masterService.FindMakewiseConsumerCount1(billmonth, model);
		model.put("installDetails", meterMasterService.getInstallationDetails(billmonth, "UHBVN", model, request));
		return "userPage";

	}

	@RequestMapping(value = "/estimationProposed", method = RequestMethod.GET)
	public String estimationProposed(HttpServletRequest request, ModelMap model) {

		System.out.println("----------------estimationProposed --------");
		List<Assesment> list = assesmentService.findAll();
		String billmonth = "", totalAss = "", totalReads = "", totalAssConsumers = "";
		String billmonth1 = "";
		for (int i = 0; i < list.size(); i++) {
			// System.out.println("********"+list.get(i).getBillmonth());
			String month = list.get(i).getBillmonth();
			try {
				Date date = new SimpleDateFormat("yyyyMM").parse(month);
				billmonth1 = new SimpleDateFormat("MMM-yy").format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			billmonth = billmonth + "'" + billmonth1 + "'" + ",";
			// billmonth = billmonth + list.get(i).getBillmonth() + ",";
			totalAss = totalAss + list.get(i).getTotalAssesment() + ",";
			totalReads = totalReads + list.get(i).getTotalReads() + ",";
			totalAssConsumers = totalAssConsumers + list.get(i).getTotalAssesedConsumers() + ",";
		}
		billmonth = billmonth.substring(0, billmonth.length() - 1);
		totalAss = totalAss.substring(0, totalAss.length() - 1);
		totalReads = totalReads.substring(0, totalReads.length() - 1);
		totalAssConsumers = totalAssConsumers.substring(0, totalAssConsumers.length() - 1);
		// model.addAttribute("billmonth",billmonth);
		model.put("totalAss", totalAss);
		request.setAttribute("billmonth", billmonth);
		request.setAttribute("totalAss", totalAss);
		request.setAttribute("totalReads", totalReads);
		request.setAttribute("totalAssConsumers", totalAssConsumers);
		// model.addAttribute("totalReads",totalReads);
		// model.addAttribute("totalAssConsumers",totalAssConsumers);

		return "estimationProposed";
	}

	/*
	 * @RequestMapping(value="/cdfImportManager",method={RequestMethod.GET,
	 * RequestMethod.POST}) public String cdfImportManager(HttpServletRequest
	 * request,Model model) { System.out.println("enter cdfImportManager method");
	 * model.addAttribute("result","notDisplay"); return "cdfimportmanager"; }
	 */

	// mobile Parsing
	@RequestMapping(value = "/MobileParsing", method = { RequestMethod.GET, RequestMethod.POST })
	public String MobileParsing(HttpServletRequest request, Model model) {
		System.out.println("enter MobileParsing method");
		model.addAttribute("result", "notDisplay");
		return "MobileParsing";
	}

	@RequestMapping(value = "/systemSecurity", method = RequestMethod.GET)
	public String systemSecurity(@ModelAttribute("users") User users, HttpServletRequest request, ModelMap model) {
		List user = userService.findAllUser();
		model.put("UserList", user);
		return "systemsecurity";
	}

	@RequestMapping(value = "/cumulativeAnalysis", method = RequestMethod.GET)
	public String cumulativeAnalysis(HttpServletRequest request) {
		return "cumulativeanalysis";
	}

	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public String locationsManager(HttpServletRequest request) {
		return "locations";
	}

	@RequestMapping(value = "/electrical_hierarchy", method = RequestMethod.GET)
	public String electricalhierarchyManager(HttpServletRequest request) {
		return "electrical_hierarchy";
	}

	@RequestMapping(value = "/meterdataProfiling", method = RequestMethod.GET)
	public String meterdataProfiling(HttpServletRequest request) {
		return "meterdataprofiling";
	}

	@RequestMapping(value = "/vee", method = RequestMethod.GET)
	public String vee(HttpServletRequest request) {
		return "vee";
	}

	@RequestMapping(value = "/meterdatAmr", method = RequestMethod.GET)
	public String meterdatAmr(HttpServletRequest request) {
		return "meterdata_amr";
	}

	@RequestMapping(value = "/meterdataSmartmeter", method = RequestMethod.GET)
	public String meterdataSmartmeter(HttpServletRequest request) {
		return "meterdata_smartmeter";
	}

	@RequestMapping(value = "/meterdataDLMS", method = RequestMethod.GET)
	public String meterdataDLMS(HttpServletRequest request) {
		return "meterdata_dlmsmeter";
	}

	@RequestMapping(value = "/meterdataFTP", method = RequestMethod.GET)
	public String meterdataFTP(HttpServletRequest request) {
		return "meterdata_ftpserver";
	}

	@RequestMapping(value = "/meterdataCMRI", method = RequestMethod.GET)
	public String meterdataCMRI(HttpServletRequest request) {
		return "meterdata_cmri";
	}

	// String userName=null;

	/*
	 * @RequestMapping(value="/uploadFile",method=RequestMethod.POST) public String
	 * uploadFile(HttpServletRequest request,Model model) {
	 * 
	 * //HttpSession session=request.getSession(false); // userName=(String)
	 * session.getAttribute("username");
	 * //System.out.println("userName-->"+userName);
	 * System.out.println("enter to upload file controller"); Document
	 * docForMetrNo=null; try{ SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyyMM"); String billmonth =
	 * request.getParameter("billmonth");//sdf.format(d);
	 * System.out.println("billMonth is : "+billmonth); MultipartHttpServletRequest
	 * multipartRequest = (MultipartHttpServletRequest) request; MultipartFile
	 * myFile = multipartRequest.getFile("fileUpload");
	 * 
	 * System.out.println("MultipartFile myFile " +myFile);
	 * 
	 * String fileName = myFile.getOriginalFilename();
	 * 
	 * System.out.println("fileName=="+fileName);
	 * 
	 * String extension = ""; //String meterNumber = ""; int dotIndex =
	 * fileName.lastIndexOf("."); extension = fileName.substring(dotIndex+1);
	 * System.out.println("File name : "+fileName + " extension : "+extension);
	 * 
	 * //create folder for zip File folder1 = new File(uploadPath);
	 * if(!folder1.exists()) { folder1.mkdir(); } //create unzip folder
	 * 
	 * File folder2 = new File(unZipPath);
	 * 
	 * System.out.println("folder2==>"+folder2); if(!folder2.exists()) {
	 * System.out.println("folder not present"); folder2.mkdir(); } else {
	 * System.out.println("folder-------> exist"); }
	 * 
	 * if(!extension.equalsIgnoreCase("zip")) {
	 * model.addAttribute("result","Invalid File Type");
	 * 
	 * } else{ String filePath = uploadPath; if(!fileName.equals("")) {
	 * System.out.println("Server path:" +filePath+" fileName : "+fileName);
	 * //Create file File fileToCreate = new File(filePath, fileName); //If file
	 * does not exists create file
	 * 
	 * FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
	 * 
	 * fileOutStream.write(myFile.getBytes());
	 * 
	 * fileOutStream.flush();
	 * 
	 * fileOutStream.close();
	 * 
	 * } System.out.println("end of upload before try block");
	 * 
	 * try { Date dt = new Date(); SimpleDateFormat sdf1 = new
	 * SimpleDateFormat("yyyy_MM_dd_kk_mm_ss"); String currentTime =
	 * sdf1.format(dt);
	 * 
	 * //create output directory is not exists //File folder = new
	 * File(getServlet().getServletContext().getRealPath("/") +"/unzipped");
	 * 
	 * // Initiate ZipFile object with the path/name of the zip file. ZipFile
	 * zipFile = new
	 * ZipFile(uploadPath+"/"+fileName);//getServlet().getServletContext().
	 * getRealPath("/") +"/uploads/"+fileName);
	 * 
	 * // Extracts all files to the path specified
	 * 
	 * zipFile.extractAll(unZipPath +
	 * "\\" + currentTime);//getServlet().getServletContext().getRealPath("/") +"
	 * /unzipped");
	 * 
	 * String[] fname = fileName.split("\\.");
	 * //System.out.println("fname-->"+fname); System.out.println("Done");
	 * 
	 * System.out.println("UnZipFolderPath=="+currentTime);
	 * System.out.println("UnZipFilename=="+fname[0]);
	 * System.out.println("filename=="+fileName);
	 * System.out.println("billmonth==="+billmonth);
	 * 
	 * model.addAttribute("UnZipFolderPath",currentTime);
	 * model.addAttribute("UnZipFilename",fname[0]);
	 * model.addAttribute("filename",fileName);
	 * model.addAttribute("billmonth",billmonth);
	 * 
	 * String files = ""; String msg=""; String unZipFolderPath =
	 * unZipPath+"/"+currentTime+"/"+fname[0]; String path = unZipFolderPath;
	 * System.out.println("path-->"+path); File folder = new File(path); File[]
	 * listOfFiles = folder.listFiles();
	 * 
	 * if(listOfFiles != null) { for(int i =0;i < listOfFiles.length;i++) {
	 * if(listOfFiles[i].isFile()) { files = listOfFiles[i].getName();
	 * if(!(files.endsWith(".xml") ||files.endsWith(".XML"))) { msg="error"; } }
	 * else { //System.out.println("inside else false"); msg="error"; } }
	 * if("error".equalsIgnoreCase(msg)) {
	 * model.addAttribute("result","Uploaded File contains other than  XML files.");
	 * } else { model.addAttribute("parse","parse");
	 * model.addAttribute("result","File Uploaded Successfully.."); }
	 * 
	 * } else {
	 * 
	 * model.addAttribute("result","No files.."); }
	 * 
	 * } catch(Exception ex) { ex.printStackTrace(); }
	 * 
	 * System.out.println("end of upload");
	 * model.addAttribute("selectedMonth",billmonth);
	 * 
	 * } DocumentBuilder dBuilderForMetrNo =
	 * DocumentBuilderFactory.newInstance().newDocumentBuilder(); docForMetrNo =
	 * dBuilderForMetrNo.parse(myFile.getInputStream());
	 * //userService.parseTheFile(docForMetrNo, billmonth,model); } catch(Exception
	 * e) { e.printStackTrace(); } return "cdfimportmanager"; }
	 */

	String unZipFolderPath = null;

	@RequestMapping(value = "/parseFile/{path}/{filename}/{filee}/{billmonth}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object parseFile(@PathVariable String path, @PathVariable String filename,
			@PathVariable String filee, @PathVariable String billmonth, HttpServletRequest request, ModelMap model) {

		HttpSession session = request.getSession();

		System.out.println("----------parseFile---------- : " + path + " : " + filee);
		// String billmonth = request.getParameter("billmonth");
		unZipFolderPath = unZipPath + "/" + path + "/" + filee;
		System.out.println("unZipFolderPath==" + unZipFolderPath);

		/*
		 * parseXml = "";duplicate = "";meterNotExist = ""; corruptedFile="";
		 * parsedCount = 0;duplicateCount = 0;meterNotExistCount = 0;mainTime =
		 * 0;corruptedFileCount=0;
		 */
		List timeStatus = getUnzippedXmls(unZipFolderPath, billmonth, model);
		int parselen = timeStatus.size() - 1;
		double time = (double) timeStatus.get(parselen);
		timeStatus.remove(parselen);
		time = ((time / 1000) / 60);
		time = Math.round(time * 100.0) / 100.0;
		System.out.println("the time taken is : " + time);
		String time1 = time + "";
		timeStatus.add(time);
		// model.addAttribute("result","Parsing Completed.. Time Taken is : "+time);
		return timeStatus;
	}

	@RequestMapping(value = "/parseFileDirect", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List parseFile(HttpServletRequest request, ModelMap model) {
		System.out.println("inside parseFileDirect");
		String unZipFolderPath = AmrController.meterFileFolder;
		String billmonth = request.getParameter("billmonth");
		System.out.println("billmonth-->" + billmonth);
		// this.getUnzippedXmls(unZipFolderPath, "201803", model);
		List timeStatus = getUnzippedXmls(unZipFolderPath, billmonth, model);

		System.out.println(timeStatus);
		int parselen = timeStatus.size() - 1;
		double time = (double) timeStatus.get(parselen);
		timeStatus.remove(parselen);
		time = ((time / 1000) / 60);
		time = Math.round(time * 100.0) / 100.0;
		System.out.println("the time taken is : " + time);
		String time1 = time + "";
		timeStatus.add(time);
		// model.addAttribute("result","Parsing Completed.. Time Taken is : "+time);
		return timeStatus;

	}

	public List getUnzippedXmls(String unZipFolderPath, String billMonth, ModelMap model) {
		System.out.println("-----inside getUnzippedXmls ");
		double lIteratorDifference = 0;
		String files = "";
		List parseMainStatus = new ArrayList<>();
		try {
			System.out.println("-----inside 1 ");
			double lIteratorStartTime = new Date().getTime();
			// System.out.println("lIteratorStartTime--->"+lIteratorStartTime);
			double mainTime = lIteratorStartTime;
			Document docForMetrNo = null;
			String path = unZipFolderPath;
			File folder = new File(path);

			File[] listOfFiles = folder.listFiles();

			System.out.println("No. files : " + listOfFiles);
			if (listOfFiles != null) {

				String parseXml = "";
				String duplicate = "";
				String meterNotExist = "";
				String corruptedFile = "";
				int parsedCount = 0;
				int duplicateCount = 0;
				int meterNotExistCount = 0;
				int corruptedFileCount = 0;
				System.out.println("-----inside 2 ");
				for (int i = 0; i < listOfFiles.length; i++) {
					System.out.println("-----inside 3 ");
					if (listOfFiles[i].isFile()) {

						System.out.println("-----inside 4 ");
						files = listOfFiles[i].getName();
						if (files.endsWith(".xml") || files.endsWith(".XML")) {
							System.out.println("----- file name is : " + files);
							File fileForMetrNo = new File(path + "/" + files);
							DocumentBuilder dBuilderForMetrNo = DocumentBuilderFactory.newInstance()
									.newDocumentBuilder();
							try {
								docForMetrNo = dBuilderForMetrNo.parse(fileForMetrNo);
							} catch (SAXParseException e) {
								docForMetrNo = null;
							}

							// String status = "";
							// System.out.println("fileForMetrNo---"+fileForMetrNo);
							// System.out.println("----- docForMetrNo : "+docForMetrNo);
							// System.out.println("unzipfolderpath---->"+unZipFolderPath);
							String status = userService
									.parseTheFile(docForMetrNo, billMonth, model, unZipFolderPath, files).trim();

							String[] st = status.split("/");
							System.out.println("statusss==" + status);
							System.out.println("st[0]--" + st[0]);
							System.out.println("st[1]--" + st[1]);
							// userService.importBillingParameters(docForMetrNo);

							MobileGenStatusEntity mobileStatus = new MobileGenStatusEntity();
							System.out.println("----- status namestatusstatusstatusstatus is : " + st[0]);
							if (st[0].equalsIgnoreCase("parsed")) {
								// System.out.println("meterno==>"+);
								parsedCount++;
								parseXml = parseXml + files + "<br/>";
								mobileStatus.setBillmonth(billMonth);
								mobileStatus.setMeterno(st[1]);
								mobileStatus.setStatus("parsed");
								mobileStatus.setCreatedate(new Date());
							} else if (st[0].equalsIgnoreCase("meterDoesNotExist")) {
								meterNotExistCount++;
								meterNotExist = meterNotExist + files + "<br/>";
								mobileStatus.setBillmonth(billMonth);
								mobileStatus.setMeterno(st[1]);
								mobileStatus.setStatus("MtrNotExist");
								mobileStatus.setCreatedate(new Date());
							} else if (st[0].equalsIgnoreCase("duplicate")) {
								duplicateCount++;
								duplicate = duplicate + files + "<br/>";
								mobileStatus.setBillmonth(billMonth);
								mobileStatus.setMeterno(st[1]);
								mobileStatus.setStatus("Duplicate");
								mobileStatus.setCreatedate(new Date());
							} else if (st[0].equalsIgnoreCase("corrupted")) {
								corruptedFileCount++;
								corruptedFile = corruptedFile + files + "<br/>";
								mobileStatus.setBillmonth(billMonth);
								mobileStatus.setMeterno(st[1]);
								mobileStatus.setStatus("Corrupted");
								mobileStatus.setCreatedate(new Date());
							}
							// mobileGenStatusService.save(mobileStatus);

							String time1 = "";
							String mainStatus = parseXml + "$" + duplicate + "$" + meterNotExist + "$" + corruptedFile;
							String countStatus = parsedCount + "$" + duplicateCount + "$" + meterNotExistCount + "$"
									+ corruptedFileCount;

							mainStatus = mainStatus + "@" + countStatus + "@" + time1;
							parseMainStatus.add(mainStatus);
							parseXml = "";
							duplicate = "";
							meterNotExist = "";
							corruptedFile = "";
							parsedCount = 0;
							duplicateCount = 0;
							meterNotExistCount = 0;
							mainTime = 0;
							corruptedFileCount = 0;
							// System.out.println(" parsed : "+parseXml);
							System.out.println(fileForMetrNo);
							/*
							 * File RenameFile = new File( path+"/"+files+"_parsed");
							 * System.out.println("RenameFile--"+RenameFile);
							 * fileForMetrNo.renameTo(RenameFile);
							 * System.out.println("fileForMetrNo--"+fileForMetrNo);
							 */
						} else {
							model.addAttribute("result", "No files..");
						}
					}
				}
			}

			System.out.println("-------------- end of file list");
			double lIteratorEndTime = new Date().getTime();
			System.out.println("lIteratorEndTime--->" + lIteratorEndTime);
			lIteratorDifference = lIteratorEndTime - lIteratorStartTime;
			System.out.println("Time taken in milliseconds: " + lIteratorDifference);
			parseMainStatus.add(lIteratorDifference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parseMainStatus;
	}

	@RequestMapping(value = "/AllXmls", method = RequestMethod.GET)
	public @ResponseBody Object AllXmls(HttpServletRequest request) {
		// System.out.println("--- unZipFolderPath==>"+unZipFolderPath);
		double lIteratorDifference = 0;
		double lIteratorEndTime = new Date().getTime();
		lIteratorDifference = lIteratorEndTime - mainTime;
		lIteratorDifference = ((lIteratorDifference / 1000) / 60);
		lIteratorDifference = Math.round(lIteratorDifference * 100.0) / 100.0;

		/*
		 * String time1 = lIteratorDifference+""; String mainStatus = parseXml + "$" +
		 * duplicate + "$" + meterNotExist + "$" + corruptedFile; String countStatus =
		 * parsedCount + "$" +duplicateCount+ "$" +meterNotExistCount+ "$"
		 * +corruptedFileCount; mainStatus = mainStatus + "@" + countStatus +
		 * "@"+time1+" mins";
		 */

		return "mainStatus";
	}

	/*
	 * @RequestMapping(value="/addNewUser",method=RequestMethod.POST) public String
	 * addNewUser(@ModelAttribute("users")User users, HttpServletRequest
	 * request,ModelMap model) {
	 * 
	 * MDMLogger.logger.info("In :::::::::::::::::::::::::userAccessManagement  ");
	 * 
	 * Boolean flag= userService.findDuplicate(users, model); if(flag==true)
	 * model.put("msg","User email id is already exist"); else{
	 * userService.save(users); model.put("msg",users.getUsername()+
	 * " User Added succsessfully ");}
	 * 
	 * List user=userService.findAllUser(); model.put("UserList",user);
	 * model.put("users", new User());
	 * 
	 * 
	 * return "systemsecurity";
	 * 
	 * }
	 */

	@RequestMapping(value = "/dataModification", method = { RequestMethod.POST, RequestMethod.GET })
	public String dataModification(HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::dataModification ");
		return "dataModification";
	}

	// From here Ved Prakash
	@RequestMapping(value = "/manualReadingEntry", method = { RequestMethod.POST, RequestMethod.GET })
	public String manualReadingEntry(@ModelAttribute("getManulReadingData") MeterMaster meterMaster,
			HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::manualReadingEntry");
		meterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));

		int readingmonth = metMasterService.getMaxRdgMonthYear(request);
		model.put("mrNames", mrnameService.findAll());
		model.put("mrNames", masterService.findMr());
		model.put("readingremark", readingRemarkService.selectReadingRemark());
		model.put("totalinst", masterService.countTotalInst(meterMaster));
		model.put("totalnoi", meterMasterService.countNOI(meterMaster));
		model.put("totalpending", meterMasterService.countPending(meterMaster));
		model.put("manualReadingDate", "");
		model.put("readingMonth", readingmonth);
		return "manualReadingEntry";
	}

	@RequestMapping(value = "/getCmriDepandsOnMrname/{readingDate}/{name}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object getCmriDepandsOnMrname(@PathVariable String readingDate, @PathVariable String name,
			HttpServletRequest request, ModelMap model) {
		System.out.println("getCmriDepandsOnMrname : " + readingDate + " : " + name);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<CMRIEntity> list = null;
		try {
			list = cmriService.getCMRIBasedOnMrName(name.trim(), sdf.parse(readingDate));
			System.out.println("-------------------------- : " + list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	String userAdded = null;

	@RequestMapping(value = "/usersAndMrNames", method = { RequestMethod.POST, RequestMethod.GET })
	public String usersAndMrNames(@ModelAttribute("addUserAndMrName") User users,
			@ModelAttribute("addUserAndMrName1") Mrname mrname, HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In users and MrNames ");
		List user = userService.findAllUser();
		System.err.println(user.size());
		model.put("UserList", user);
		String discomQuery = "SELECT distinct discom from meter_data.amilocation";
		String circleListQry = "select DISTINCT circle  from  meter_data.amilocation";
		String divisonlistQry = "select DISTINCT division  from  meter_data.amilocation";
		String subdivisonQry = "select DISTINCT subdivision  from  meter_data.amilocation";
		String designationListQry = "select DISTINCT role_name from   meter_data.business_roles";

		List circleList = feederService.getCustomEntityManager("postgresMdas").createNativeQuery(circleListQry)
				.getResultList();
		List divisionList = feederService.getCustomEntityManager("postgresMdas").createNativeQuery(divisonlistQry)
				.getResultList();
		List subdivisionList = feederService.getCustomEntityManager("postgresMdas").createNativeQuery(subdivisonQry)
				.getResultList();
		List designationList = feederService.getCustomEntityManager("postgresMdas")
				.createNativeQuery(designationListQry).getResultList();
		List<?> discomList = feederService.getCustomEntityManager("postgresMdas").createNativeQuery(discomQuery)
				.getResultList();

		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		// List mrnamelist=mrnameService.findAll();
		// model.put("mrnamelist", mrnamelist);
		model.put("userTypeList", userAccessTypeService.findAll());

		model.put("zoneList", zoneList);
		model.put("circleList", circleList);
		model.put("divisionList", divisionList);
		model.put("subdivisionList", subdivisionList);
		model.put("designationList", designationList);
		model.put("discom", discomList);
		if (addedSucces == 1) {
			model.put("msg", userAdded + " User Added succsessfully ");
			addedSucces = 0;
		}
		/*
		 * model.put("circleList", userAccessTypeService.findAll());
		 * model.put("divisionList", userAccessTypeService.findAll());
		 * model.put("subdivisionList", userAccessTypeService.findAll());
		 */
		return "userAndMrnames";
	}

	@RequestMapping(value = "/search", method = { RequestMethod.POST, RequestMethod.GET })
	public String sarch(@ModelAttribute("metermasterSearch") MeterMaster meterMaster, HttpServletRequest request,
			ModelMap model, BindingResult result) {
		MDMLogger.logger.info("In ::::::::::::::::::::::User Serach");
		meterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		model.addAttribute("circles", sdoJccService.getAllCircle());
		return "search";
	}
	// From here Ved Prakash

	@RequestMapping(value = "/manualReadingEntryUpdate", method = RequestMethod.POST)
	public String manualReadingEntryUpdate(@ModelAttribute("getManulReadingData") MeterMaster meterMaster,
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) throws IOException {
		MDMLogger.logger.info("In usersController ::::::::::::::::::: manulaReadingEantry");

		String oldsl = request.getParameter("oldsel");
		System.out.println("Redefining Date " + request.getParameter("readingdateVal"));
		System.out.println("oldseaaaaaaaaaaal" + oldsl);

		model.put("manualReadingDate", meterMaster.getReadingdate());
		String oldxcurrdngkwh = request.getParameter("oldxcurrdngkwh");
		if (meterMasterService.updateManualReadingData(request, meterMaster, oldsl, model, oldxcurrdngkwh) == 1) {
			model.put("msg", "Account updated succsessfuly");
			// model.put("getManulReadingData",meterMaster=null);
		} else {
			model.put("msg", "Account cannot be updated ");
		}
		int readingmonth = rdngMonthService.findAll();
		model.put("sdoaccno", meterMaster.getAccno().substring(0, 4));
		model.put("readingMonth", readingmonth);
		model.put("getManulReadingData", new MeterMaster());
		// model.put("mrNames", mrnameService.findAll());
		model.put("mrNames", masterService.findMr());

		model.put("totalinst", masterService.countTotalInst(meterMaster));
		model.put("totalnoi", meterMasterService.countNOI(meterMaster));
		model.put("totalpending", meterMasterService.countPending(meterMaster));
		model.put("readingremark", readingRemarkService.selectReadingRemark());
		return "manualReadingEntry";
	}

	@RequestMapping(value = { "/getManualReading", "/getManualReading1" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getManualReadingData(@ModelAttribute("getManulReadingData") MeterMaster meterMaster,
			HttpServletRequest request, ModelMap model) {

		MDMLogger.logger.info("In ::::::::::::Get ManualReadingData from MeterMaster ");

		String uri = request.getRequestURI();
		System.out.println("uuuuuuuuuuuuuu" + uri);
		if (uri.equals("/bsmartmdm/getManualReading")) {
			meterMasterService.getMeterMasterData(request, meterMaster, model);
		} else {
			meterMasterService.getMeterMasterData1(request, meterMaster, model);
		}

		// model.put("mrNames", mrnameService.findAll());
		// model.put("cmrino",cmriDeviceService.findAllCmriNumber());
		// model.put("sdoname",masterService.findSDOName(meterMaster, model));
		model.put("readingremark", readingRemarkService.selectReadingRemark());
		// model.put("tarrifcode",masterService.findTarrifCode(meterMaster, model));
		return "manualReadingEntry";

	}

	@RequestMapping(value = "/getManualReadingDataAjax/{readingMonth}/{accno}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody List getManualReadingDataAjax(@PathVariable String readingMonth, @PathVariable String accno,
			HttpServletRequest request, ModelMap model) {
		System.out.println("------------------------- : " + readingMonth + " : " + accno);

		List list = meterMasterService.getMeterMasterDetailsForManualReadingData(readingMonth, accno);

		int readingmonth = rdngMonthService.findAll();
		System.out.println("*------------ : " + getLastMonth(readingmonth));
		// String previousRemark = meterMasterService.getPreviousRemark(accno,
		// getLastMonth(readingmonth));
		System.out.println("list--" + list);
		// System.out.println("pmark--"+previousRemark);
		// String circle= masterService.getcircleByAccno(accno);
		// list.add(previousRemark);
		// list.add(circle);

		return list;
	}

	public int getLastMonth(int month) {
		String m = month + "";
		if (m.endsWith("01")) {
			int lastMonth = Integer.parseInt(m.substring(0, 4)) - 1;
			m = lastMonth + "12";

		} else {

			month = month - 1;
			m = month + "";

		}
		return Integer.parseInt(m);
	}

	@RequestMapping(value = "/getManualReadingDataAjaxMeterBased/{readingMonth}/{metrno}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getManualReadingDataAjaxMeterBased(@PathVariable String readingMonth,
			@PathVariable String metrno, HttpServletRequest request, ModelMap model) {
		System.out.println("------------------------- : " + readingMonth + " : " + metrno);
		List list = meterMasterService.getMeterMasterDetailsForManualReadingDataMeter(readingMonth, metrno);
		Object[] obj = (Object[]) list.get(0);
		int readingmonth = rdngMonthService.findAll();
		System.out.println("*------------ : " + getLastMonth(readingmonth));

		String previousRemark = meterMasterService.getPreviousRemark(obj[0] + "", getLastMonth(readingmonth));

		list.add(previousRemark);
		return list;
	}

	@RequestMapping(value = "/getCmriData/{operation}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<CMRIEntity> getCmriData(@PathVariable String operation, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		MDMLogger.logger.info("In ::::::::::::::::::::::::In Edit Mrname   ");
		System.out.println("tiiiiiiiiiiiiiiiiiiddddddd" + operation);

		List<CMRIEntity> cmri = cmriService.findCmriData(operation);
		System.out.println(cmri);
		return cmri;

	}

	/*
	 * @RequestMapping(value="/getcmriData",method={RequestMethod.POST,RequestMethod
	 * .GET}) public String getcmriData(@ModelAttribute("getManulReadingData")
	 * MeterMaster meterMaster,@RequestParam("rdate")String
	 * operation,HttpServletResponse response,HttpServletRequest request,ModelMap
	 * model) throws JsonParseException, JsonMappingException, IOException {
	 * MDMLogger.logger.info("In ::::::::::::::::::::::::In Delete User Details   "
	 * +operation); try { cmriService.findAllCmriData1(operation,model); }
	 * catch(Exception e) { e.printStackTrace(); } return "manualReadingEntry"; }
	 * 
	 */

	/*
	 * @RequestMapping(value = "/getCmriNo/{operation}", method =
	 * RequestMethod.POST) public @ResponseBody List<CMRIEntity>
	 * getCmriNo(@ModelAttribute("getManulReadingData") MeterMaster
	 * meterMaster,@PathVariable("operation") String operation,HttpServletRequest
	 * request,ModelMap model) { List<CMRIEntity> cmri=null;
	 * MDMLogger.logger.info("In ::::::::::::getMar name basde Cmri number");
	 * cmri=cmriService.findAllCmriData(operation,model); return cmri; }
	 */

	/* From here Ved Prakash Mishra */

	@RequestMapping(value = "/addNewMRName", method = { RequestMethod.POST, RequestMethod.GET })
	public String addMrName(@ModelAttribute("addUserAndMrName") User users,
			@ModelAttribute("addUserAndMrName1") Mrname mrname, HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::::::::::::::: MrNames ");

		System.out.println("MMMMMMMMRRRRRRRNNName" + mrname.getMrname());
		String flag = mrnameService.findDuplicateMrname(mrname.getMrname(), model);
		if (flag != null) {
			model.put("msg", "Mrname is already exist");
			return null;
		} else {
			mrname.setSdo("0");
			mrnameService.save(mrname);
			model.put("msg", "MRname is added succsessfully");

		}
		List user = userService.findAllUser();
		model.put("UserList", user);
		List mrnamelist = mrnameService.findAll();
		model.put("mrnamelist", mrnamelist);
		model.put("addUserAndMrName1", new Mrname());
		model.put("userTypeList", userAccessTypeService.findAll());

		return "userAndMrnames";
	}

	@RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
	public String addNewUser(@ModelAttribute("addUserAndMrName") User users, HttpServletRequest request, ModelMap model,
			BindingResult result) {

		MDMLogger.logger.info("In :::::::::::::::::::::::::User And MRname Management   ");

		/*
		 * Boolean flag= userService.findDuplicate(users, model); if(flag==true)
		 * model.put("msg","User email id is already exist"); else{
		 */

		try {

			userService.save(users);
			addedSucces = 1;
			userAdded = users.getUsername();
			model.put("msg", users.getUsername() + " User Added succsessfully ");
			List user = userService.findAllUser();
			model.put("UserList", user);
			model.put("addUserAndMrName", new User());
			model.put("userTypeList", userAccessTypeService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "userAndMrnames";
		return "redirect:/usersAndMrNames";

	}

	@RequestMapping(value = "/checkMrname/{operation}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object retriveMrName(@PathVariable Integer operation, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		MDMLogger.logger.info("In ::::::::::::::::::::::::In Edit Mrname   ");

		User mrname = userService.find(operation);

		return mrname;

	}

	@RequestMapping(value = "/updateMrname", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateMrName(@ModelAttribute("addUserAndMrName") User users,
			@ModelAttribute("addUserAndMrName1") Mrname mrname, HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::::::::::::::: MrNamesUpdates ");

		mrname.setSdo("0");
		mrnameService.update(mrname);
		model.put("msg", "Mr name updated succsessfully");
		List mrnamelist = mrnameService.findAll();
		model.put("mrnamelist", mrnamelist);
		model.put("mrname", new Mrname());
		model.put("userTypeList", userAccessTypeService.findAll());

		return "userAndMrnames";
	}

	@RequestMapping(value = "/editUserDetails/{operation}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object editUserDetails(@PathVariable Integer operation, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		MDMLogger.logger.info("In ::::::::::::::::::::::::In Edit User Details   ");
		User user = userService.find(operation);
		System.out.println("dddddddddddddddddddddd" + user);
		return user;
	}

	@RequestMapping(value = "/updateUserDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public String updatUserDetails(@ModelAttribute("addUserAndMrName") User users,
			@ModelAttribute("addUserAndMrName1") Mrname mrname, HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::::::::::::::: UserUpdates ");
		userService.customUpdate(users);
		List user = userService.findAllUser();
		model.put("UserList", user);
		model.put("users", new User());
		model.put("msg", "User details  updated succsessfully");
		model.put("userTypeList", userAccessTypeService.findAll());
		return "userAndMrnames";
	}

	@RequestMapping(value = "/detletUserDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteUserDetails(@ModelAttribute("addUserAndMrName") User users,
			@ModelAttribute("addUserAndMrName1") Mrname mrname, @RequestParam("delUId") Integer operation,
			HttpServletResponse response, HttpServletRequest request, ModelMap model)
			throws JsonParseException, JsonMappingException, IOException {
		MDMLogger.logger.info("In ::::::::::::::::::::::::In Delete User Details   ");
		userService.delete(operation);
		List user = userService.findAllUser();
		model.put("msg", "User deleted succsessfully");
		model.put("UserList", user);
		model.put("users", new User());
		model.put("userTypeList", userAccessTypeService.findAll());

		return "userAndMrnames";
	}

	@RequestMapping(value = "/searchMeterMaster", method = { RequestMethod.POST, RequestMethod.GET })
	public String sarchMeterMaster(@ModelAttribute("metermasterSearch") MeterMaster meterMaster,
			HttpServletRequest request, ModelMap model, BindingResult result) {
		MDMLogger.logger.info("In ::::::::::::::::::::::MeterMaster Serach");

		System.out.println("ACCDDDataaaaaaaa===================" + meterMaster.getAccno());

		model.addAttribute("meterMasterData", meterMasterService.findaccnomonthData(meterMaster, model));
		model.addAttribute("masterdata", masterService.customfind(meterMaster.getAccno()));
		String meterno = meterMasterService.getmeterno(meterMaster.getRdngmonth(), meterMaster.getAccno());
		/*
		 * int getReadBy=meterMasterService.getAMRORCMR(meterMaster.getRdngmonth(),
		 * meterno); System.out.println("getReadBy-->"+getReadBy); String ReadBy="CMRI";
		 * if(getReadBy==1) { ReadBy="AMR"; } if(getReadBy==5) {
		 * ReadBy="BOTH(AMR&CMRI)"; } model.put("ReadBy", ReadBy);
		 */

		return "search";

	}

	@RequestMapping(value = "/searchMeterMasterToVarEnergy", method = { RequestMethod.POST, RequestMethod.GET })
	public String sarchMeterMaster111(@ModelAttribute("metermasterSearch") MeterMaster meterMaster,
			HttpServletRequest request, ModelMap model, BindingResult result) {
		MDMLogger.logger.info("In ::::::::::::::::::::::MeterMaster Serach");

		System.out.println("ACCDDDataaaaaaaa===================" + meterMaster.getAccno());
		model.addAttribute("meterMasterData", meterMasterService.findaccnomonthData(meterMaster, model));
		model.addAttribute("masterdata", masterService.find(meterMaster.getAccno()));
		String yearMonth = meterMasterService.getCurrentMonthyear();
		String category = request.getParameter("category");
		if (request.getParameter("month") != null) {
			yearMonth = request.getParameter("month");
		} else {
			yearMonth = meterMasterService.getCurrentMonthyear();
		}
		if (category != null) {
			String sdocode = request.getParameter("subDivision");
			String circle = request.getParameter("circle");
			String division = request.getParameter("division");
			model.put("circle", circle);
			model.put("division", division);
			model.put("sdoCode", sdoJccService.getsubDivisionName(sdocode));
			model.put("VariationOfEnergyList",
					meterMasterService.showVariationOfEnergy(yearMonth, model, category, sdocode));
		}
		model.put("selectedMonth", yearMonth);
		model.put("circles", sdoJccService.getAllCircle());
		return "variationOfEnergy";
	}

	@RequestMapping(value = "/searchMeterMaster1", method = { RequestMethod.POST, RequestMethod.GET })
	public String sarchMeterMaster1(@ModelAttribute("metermasterSearch") MeterMaster meterMaster,
			HttpServletRequest request, ModelMap model, BindingResult result) {
		MDMLogger.logger.info("In ::::::::::::::::::::::MeterMaster Serach111");
		System.out.println("meterno=======" + meterMaster.getMetrno());

		model.addAttribute("meterMasterData", meterMasterService.findaccnomonthData1(meterMaster, model));
		/*
		 * int getReadBy=meterMasterService.getAMRORCMR(meterMaster.getRdngmonth(),
		 * meterMaster.getMetrno());
		 * 
		 * String ReadBy="CMRI"; if(getReadBy==1) { ReadBy="AMR"; } if(getReadBy==5) {
		 * ReadBy="BOTH(AMR&CMRI)"; } model.put("ReadBy", ReadBy);
		 */

		return "search";
	}

	@RequestMapping(value = "/searchMeterMaster2", method = { RequestMethod.POST, RequestMethod.GET })
	public String searchMeterMaster2(@ModelAttribute("metermasterSearch") MeterMaster meterMaster,
			HttpServletRequest request, ModelMap model, BindingResult result) {
		MDMLogger.logger.info("In ::::::::::::::::::::::MeterMaster Serach22");

		System.out.println("MMDdddddddddddddddd=======" + meterMaster.getNewseal());
		model.addAttribute("meterMasterData", meterMasterService.findaccnomonthData2(meterMaster, model));
		/*
		 * int getReadBy=meterMasterService.getAMRORCMR(meterMaster.getRdngmonth(),
		 * meterMaster.getMetrno());
		 * 
		 * String ReadBy="CMRI"; if(getReadBy==1) { ReadBy="AMR"; }
		 * 
		 * if(getReadBy==5) { ReadBy="BOTH(AMR&CMRI)"; } model.put("ReadBy", ReadBy);
		 */

		return "search";
	}

	/* 201114 Ved Prakash Mishra */
	@RequestMapping(value = "/assessmentData", method = { RequestMethod.POST, RequestMethod.GET })
	public String addAndUpdateAssessment(@ModelAttribute("assessmentAddUpdate") Assesment assesment,
			HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::::::::::::Add And Update Assessment");
		List<Assesment> list = assesmentService.findAll();
		model.put("assList", list);
		return "addAndUpdateAssessment";

	}

	@RequestMapping(value = "/addNewAssesment", method = { RequestMethod.POST, RequestMethod.GET })
	public String addAssessment(@ModelAttribute("assessmentAddUpdate") Assesment assesment, HttpServletRequest request,
			ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::::::::::::Add And Update Assessment");
		assesmentService.save(assesment);
		List<Assesment> list = assesmentService.findAll();
		model.put("assList", list);
		model.put("assessmentAddUpdate", new Assesment());
		model.put("msg", "New Assessment Added Succesfully ");
		return "addAndUpdateAssessment";

	}

	@RequestMapping(value = "/editAssessment/{operation}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object editAssessment(@PathVariable Integer operation, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		MDMLogger.logger.info("In ::::::::::::::::::::::::In Updeate Assessment    ");
		Assesment assesment = assesmentService.find(operation);
		System.out.println("dddddddddddddddddddddd" + assesment);
		return assesment;
	}

	@RequestMapping(value = "/updateAsessment", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateAssessment(@ModelAttribute("assessmentAddUpdate") Assesment assesment,
			HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::::::::::::Add And Update Assessment");
		assesmentService.update(assesment);
		List<Assesment> list = assesmentService.findAll();
		model.put("assList", list);
		model.put("assessmentAddUpdate", new Assesment());
		model.put("msg", "New Assessment Updated Succesfully ");
		return "addAndUpdateAssessment";

	}

	@RequestMapping(value = "/myProfile", method = { RequestMethod.POST, RequestMethod.GET })
	public String myProfile(@ModelAttribute("myUserProfile") User user, BindingResult result,
			HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In MyProfile  :::::::::::::::::::Controller");
		String username = (String) request.getSession().getAttribute("username");
		if (username != null) {
			model.put("myUserProfile", userService.customfind(userService.getUserID(username)));
		} else {
			return "redirect:./?sessionVal=expired";
		}

		return "myProfile";
	}

	@RequestMapping(value = "/changepwd", method = { RequestMethod.POST, RequestMethod.GET })
	public String changepwd(@ModelAttribute("myUserProfile") User user, BindingResult result,
			HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In MyProfile  :::::::::::::::::::Controller");
		String username = (String) request.getSession().getAttribute("username");
		if (username != null) {
			model.put("myUserProfile", userService.customfind(userService.getUserID(username)));
		} else {
			return "redirect:./?sessionVal=expired";
		}

		return "changepwd";
	}

	@RequestMapping(value = "/updateMyProfile", method = { RequestMethod.POST, RequestMethod.GET })
	public String updatMyProfile(@ModelAttribute("myUserProfile") User users, BindingResult result,
			HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::::::::::::::: UserUpdates ");

		String userid = request.getParameter("userid");
		String userPassword = request.getParameter("userPassword");
		User newUser = userService.getDataById(userid);
		newUser.setUserPassword(userPassword);

		userService.customupdateBySchema(newUser, "postgresMdas");
		String username = (String) request.getSession().getAttribute("username");
		model.put("myUserProfile", userService.customfind(userService.getUserID(username)));
		model.put("msg", "Profile has been updated succsessfully");
		return "myProfile";
	}

	@RequestMapping(value = "/addUserTypeData", method = { RequestMethod.GET, RequestMethod.POST })
	public String addUserTypeData(@ModelAttribute("addUserAndMrName") User users,
			@ModelAttribute("addUserAndMrName1") Mrname mrname, HttpServletRequest request, ModelMap model) {
		String accessRoles = request.getParameter("userAccessTypeRoles");
		String userAccessType = request.getParameter("newUserType");
		System.err.println(accessRoles);
		long n = userAccessTypeService.checkUserType(userAccessType);
		if (n > 0) {
			model.put("msg", "UserType Already Exist...");
		} else {
			UserAccessType uAT = new UserAccessType();
			uAT.setUserType(userAccessType);
			uAT.setAccessTypeId(accessRoles);
			userAccessTypeService.customSave(uAT);
			model.put("msg", "UserType Created  Successfully...");
		}
		List user = userService.findAllUser();
		model.put("UserList", user);

		model.put("userTypeList", userAccessTypeService.findAll());
		return "userAndMrnames";
	}

	String res = "";

	@RequestMapping(value = "/accountNoUpdate", method = RequestMethod.GET)
	public String accountNoUpdate(HttpServletRequest request, ModelMap model) {
		model.put("accountNos", mrnameService.AccountNos());
		model.addAttribute("results", "notDisplay");
		if (res.equalsIgnoreCase("success")) {
			model.addAttribute("results", "Account Number Updated Succesfully");
		}
		res = "";

		return "accountUpdate";
	}

	@RequestMapping(value = "/UpdateNewAccNo", method = RequestMethod.GET)
	public String UpdateNewAccNo(HttpServletRequest request, ModelMap model) {
		System.out.println("inside UpdateNewAccNo ");
		String oldAccNo = request.getParameter("accNo");
		String newAccNo = request.getParameter("newaccNo");
		System.out.println("oldAccNo==" + oldAccNo + "newAccNo==" + newAccNo);
		int count = mrnameService.updateAccountNO(oldAccNo, newAccNo);
		if (count > 0) {
			System.out.println("count=" + count);
			res = "success";
		}
		model.put("accountNos", mrnameService.AccountNos());
		return "redirect:accountNoUpdate";

	}

	/*---------------------  KNO Search --------------------------------*/

	@RequestMapping(value = "/searchMeterMaster3", method = { RequestMethod.POST, RequestMethod.GET })
	public String sarchKno(@ModelAttribute("metermasterSearch") MeterMaster meterMaster, HttpServletRequest request,
			ModelMap model, BindingResult result) {
		MDMLogger.logger.info("In KNO Serach Method");

		System.out.println(" Kno Search===================" + meterMaster.getkno());

		System.out.println("Kno From JSP=======" + meterMaster.getkno());

		model.addAttribute("meterMasterData", meterMasterService.findaccnomonthDataKno(meterMaster, model));
		int getReadBy = meterMasterService.getAMRORCMR(meterMaster.getRdngmonth(), meterMaster.getMetrno());

		String ReadBy = "CMRI";
		if (getReadBy == 1) {
			ReadBy = "AMR";
		}
		if (getReadBy == 5) {
			ReadBy = "BOTH(AMR&CMRI)";
		}
		model.put("ReadBy", ReadBy);

		return "search";

	}

	@RequestMapping(value = "/showAsciiVal", method = { RequestMethod.GET, RequestMethod.POST })
	public String showAsciiVal(HttpServletRequest request, ModelMap model, HttpServletResponse response) {
		model.addAttribute("result", "notDisplay");
		// masterService.generateAsciiVal(request.getParameter("month"),response,request);
		// model.put("subdivlist",masterService.getAllSubDiv(model));
		String circle = (String) request.getSession(false).getAttribute("dhbvnUserCircle");
		String division = (String) request.getSession(false).getAttribute("dhbvnUserDivision");
		String subdivision = (String) request.getSession(false).getAttribute("dhbvnUserSubdiv");
		MDMLogger.logger.info("circle" + circle + "division" + division + "subdivision" + subdivision);
		masterService.getAllSubDiv(model, request);
		if (circle == null) {
			circle = "%";
		}
		if (division == null) {
			division = "%";
		}
		if (subdivision == null) {
			subdivision = "%";
		}
		if (subdivision != null && !subdivision.equalsIgnoreCase("%")) {
			model.put("subdivlist", subdivision);
		} else {
			model.put("subdivlist", masterService.getDistinctSubdivision(circle, division, request));
		}
		return "asciiFile";
	}

	@RequestMapping(value = "/showSubdivGroupValue/{subdiv}/{month}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object showSubdivGroupValue(@PathVariable String subdiv, @PathVariable String month,
			HttpServletRequest request, ModelMap model) {
		return masterService.getGroupValue(subdiv, month, model);
	}

	@RequestMapping(value = "/generateAsciiVal", method = { RequestMethod.GET, RequestMethod.POST })
	public String generateAsciiVal(HttpServletRequest request, ModelMap model, HttpServletResponse response) {
		model.addAttribute("result", "notDisplay");
		String month = request.getParameter("month");
		String subdivisionVal = request.getParameter("subdivision").trim();
		// String groupVal=request.getParameter("group").trim();
		String categoryVal = request.getParameter("category").trim();
		System.out.println("categoryVal" + categoryVal + "====category" + request.getParameter("category"));
		String billingCatVal = request.getParameter("billingCategory").trim();
		System.out.println("billingCatVal--->" + request.getParameter("billingCategory").trim());

		model.put("month", month);
		model.put("subdivisionVal", subdivisionVal);
		// model.put("groupVal", groupVal);
		model.put("categoryVal", categoryVal);
		model.put("billingCatVal", billingCatVal);

		String value = masterService.generateAsciiVal(month, subdivisionVal, categoryVal, billingCatVal, response,
				request);
		if (value.equals("noData")) {
			model.addAttribute("result", "No Data Available.");
		}
		String circle = (String) request.getSession(false).getAttribute("dhbvnUserCircle");
		String division = (String) request.getSession(false).getAttribute("dhbvnUserDivision");
		String subdivision = (String) request.getSession(false).getAttribute("dhbvnUserSubdiv");
		MDMLogger.logger.info("circle" + circle + "division" + division + "subdivision" + subdivision);
		masterService.getAllSubDiv(model, request);
		model.put("groupList", masterService.getGroupValue(subdivisionVal, month, model));
		if (circle == null) {
			circle = "%";
		}
		if (division == null) {
			division = "%";
		}
		if (subdivision == null) {
			subdivision = "%";
		}
		if (subdivision != null && !subdivision.equalsIgnoreCase("%")) {
			model.put("subdivlist", subdivision);
		} else {
			model.put("subdivlist", masterService.getDistinctSubdivision(circle, division, request));
		}
		return "asciiFile";
	}

	@RequestMapping(value = "/getCategoryData/{month}", method = RequestMethod.GET)
	public @ResponseBody Object getCategorywiseData(@PathVariable String month, HttpServletRequest request) {
		return meterMasterService.getCategorywiseData(month, request);
	}

	@RequestMapping(value = "/getAciveMeterData", method = RequestMethod.GET)
	public @ResponseBody Object getAciveMeterData(HttpServletRequest request) {
		System.out.println(
				"getAciveMeterData--" + request.getParameter("sdoname") + "  month==" + request.getParameter("month"));
		String sdoname = request.getParameter("sdoname");
		String month = request.getParameter("month");
		return meterMasterService.getActieMeters(sdoname, month, request);

	}

	@Transactional
	@RequestMapping(value = "/passwordUpdation", method = RequestMethod.POST)
	public @ResponseBody Object passwordUpdation(@RequestParam("pwd") String pwd, HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("username");
		entityManager.unwrap(Session.class).getTransaction().begin();

		String s = "update meter_data.users set userpassword='" + pwd.trim() + "' where username='" + username.trim()
				+ "'";
		entityManager.createNativeQuery(s).executeUpdate();
		// entityManager.unwrap(Session.class).getTransaction().commit();
		return "succ";
	}

	@RequestMapping(value = "/isUserNameExists", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String isUserNameExists(@RequestParam("userName") String userName, HttpServletRequest request,
			ModelMap model) {

		List<?> resultList = null;

		String s = "Select *from meter_data.users where username = '" + userName + "' ";

		try {
			resultList = entityManager.createNativeQuery(s).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resultList.size() >= 1) {
			return "exits";

		} else {
			return "Notexits";
		}

	}

	@RequestMapping(value = "/updateUserDetailsNew", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String updateUserDetails(@RequestParam("userName") String userName,
			@RequestParam("name") String name, @RequestParam("userType") String userType,
			@RequestParam("mobileNo") String mobileNo, @RequestParam("emailId") String emailId,
			/* @RequestParam("discom") String discom, */
			@RequestParam("officeType") String officeType, @RequestParam("officeCode") String officeCode,
			@RequestParam("id") String id, @RequestParam("EditaccessType") String EditaccessType,

			HttpServletRequest request, ModelMap model) {

		Integer result = Integer.parseInt(id);
		String res = null;

		User userE = userService.find(result);
		userE.setName(name);
		userE.setUserType(userType);
		userE.setMobileNo(mobileNo);
		userE.setEmailId(emailId);
		userE.setOfficeType(officeType);
//	  userE.setDiscom(discom);
		userE.setOffice(officeCode);
		userE.setEditAccess(EditaccessType);
		try {
			userService.update(userE);
			res = "success";
		} catch (Exception e) {
			e.printStackTrace();
			res = "fail";
		}

		return res;
	}

	@RequestMapping(value = "/getLocationNames", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String getLocationNames(@RequestParam("officeCode") String locationCode,
			@RequestParam("officeType") String locationType, ModelMap model, HttpServletRequest request) {

		/*
		 * String locationCode = request.getParameter("officeCode"); String locationType
		 * = request.getParameter("officeType");
		 */
		String resultList = null;
		String qry = null;

		if (locationType.equalsIgnoreCase("corporate")) {
			qry = "SELECT DISTINCT discom FROM meter_data.amilocation where DISCOM_code = '" + locationCode + "' ";
		} else if (locationType.equalsIgnoreCase("region")) {
			qry = "SELECT DISTINCT zone FROM meter_data.amilocation where zone_code = '" + locationCode + "' ";
		} else if (locationType.equalsIgnoreCase("circle")) {
			qry = "SELECT DISTINCT circle FROM meter_data.amilocation where circle_code = '" + locationCode + "' ";
		} else if (locationType.equalsIgnoreCase("division")) {
			qry = "SELECT DISTINCT division FROM meter_data.amilocation where division_code = '" + locationCode + "' ";
		} else if (locationType.equalsIgnoreCase("subdivision")) {
			qry = "SELECT DISTINCT subdivision FROM meter_data.amilocation where sitecode = '" + locationCode + "' ";
		}

		try {
			resultList = (String) userService.getCustomEntityManager("postgresMdas").createNativeQuery(qry)
					.getSingleResult().toString();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return resultList;

	}

	@RequestMapping(value = "/userdetailsexcel", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getUserDetails(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		try {

			String fileName = "UserDetails";
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

			header.createCell(0).setCellValue("Name");
			header.createCell(1).setCellValue("UserName");
			header.createCell(2).setCellValue("UserType");
			header.createCell(3).setCellValue("OfficeType");
			header.createCell(4).setCellValue("Office");

			List<User> userdata = null;
			userdata = userService.findAllUser();
			System.err.println(userdata);
			int count = 1;
			// int cellNO=0;
			for (Iterator<User> iterator = userdata.iterator(); iterator.hasNext();) {
				User values = iterator.next();

				// System.err.println(values.getUsername()+valus);

				XSSFRow row = sheet.createRow(count);
				// cellNO++;
				// row.createCell(0).setCellValue(String.valueOf(cellNO+""));
				if (values.getName() == null) {
					row.createCell(0).setCellValue(String.valueOf(""));
				} else {
					row.createCell(0).setCellValue(String.valueOf(values.getName()));
				}

				if (values.getUsername() == null) {
					row.createCell(1).setCellValue(String.valueOf(""));
				} else {
					row.createCell(1).setCellValue(String.valueOf(values.getUsername()));
				}

				if (values.getUserType() == null) {
					row.createCell(2).setCellValue(String.valueOf(""));
				} else {
					row.createCell(2).setCellValue(String.valueOf(values.getUserType()));
				}

				if (values.getOfficeType() == null) {
					row.createCell(3).setCellValue(String.valueOf(""));
				} else {
					row.createCell(3).setCellValue(String.valueOf(values.getOfficeType()));
				}

				if (values.getOffice() == null) {
					row.createCell(4).setCellValue(String.valueOf(""));
				} else {
					row.createCell(4).setCellValue(String.valueOf(values.getOffice()));
				}

				count++;
			}
			System.err.println("end of controller");
			FileOutputStream fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();

			ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"UserDetails.xlsx" + "\"");
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
