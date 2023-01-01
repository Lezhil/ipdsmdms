package com.bcits.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.AuditTrailEntity;
import com.bcits.entity.LoadSurveyEstimated;
import com.bcits.entity.Master;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.AssignValidationRuleEntity;
import com.bcits.mdas.entity.AssignValidationRuleEntity.KeyValidation;
import com.bcits.mdas.entity.AssignsEstimationRuleEntity;
import com.bcits.mdas.entity.AssignsEstimationRuleEntity.EstKeyValidation;
import com.bcits.mdas.entity.EstimationRuleEntity;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.VeeRuleEntity;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.AssignEstimationRuleService;
import com.bcits.mdas.service.AssignValidationRuleService;
import com.bcits.mdas.service.EstimationRuleService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.VeeRuleService;
import com.bcits.service.AuditTrailEntityService;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.D4LoadDataService;
import com.bcits.service.LoadEstimationService;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
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

@Controller
public class VEEController {
	@Autowired
	private D4LoadDataService d4LoadDataService;
	@Autowired
	private ConsumerMasterService consumerMasterService;

	@Autowired
	private FeederMasterService feederMasterService;

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	private AmrLoadService amrLoadService;

	@Autowired
	private LoadEstimationService loadEstimationService;

	@Autowired
	private AuditTrailEntityService auditTrailService;

	@Autowired
	private FeederMasterService feederService;

	@Autowired
	private VeeRuleService addveeservice;

	@Autowired
	private EstimationRuleService addestservice;
	
	@Autowired
	private AssignValidationRuleService assgvalservice;
	
	@Autowired
	private AssignEstimationRuleService assgestservice;

	int veeUpdateFlag = 0;

	@RequestMapping(value = "/veeValidation", method = { RequestMethod.GET, RequestMethod.POST })
	public String validation(HttpServletRequest request, Model model) {
		return "validation";
	}

	@RequestMapping(value = "/veeEstimation", method = { RequestMethod.GET, RequestMethod.POST })
	public String estimation(HttpServletRequest request, Model model) {

		return "estimation";
	}

	@RequestMapping(value = "/veeEditing", method = { RequestMethod.GET, RequestMethod.POST })
	public String editing(HttpServletRequest request, Model model) {
		return "editing";
	}

	@RequestMapping(value = "/veeAggregation", method = { RequestMethod.GET, RequestMethod.POST })
	public String aggregation(HttpServletRequest request, Model model) {
		List<Master> l = consumerMasterService.getZone();
		model.addAttribute("zone", l);
		return "aggregation";
	}

	@RequestMapping(value = "/veeAggregationDATA", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object aggregationDATA(HttpServletRequest request, @RequestParam("zoneid") String zone,
			@RequestParam("cirid") String circle, @RequestParam("divid") String division, Model model) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
		String cmonth = sdf.format(d).toString();
		int i = Integer.parseInt(cmonth) - 1;
		String lmonth = String.valueOf(i);

		String qry = "select sd.subdiv,(select sum(case when mm.currdngkwh=null then 0 else  mm.currdngkwh end) from mdm_test.metermaster mm where mm.subdiv=sd.subdiv)- "
				+ "(select sum(case when mm.currdngkwh=null then 0 else  mm.currdngkwh end) from mdm_test.metermaster mm where mm.subdiv=sd.subdiv and rdngmonth='"
				+ lmonth + "') as ckwh " + " from "
				+ "(select DISTINCT subdiv from mdm_test.metermaster where  circle='" + circle + "' and division='"
				+ division + "' and rdngmonth='" + cmonth + "' and accno not in ('#N/A','N/A')) sd ";

		List<Object[]> l = consumerMasterService.getCustomEntityManager("postgresMdas").createNativeQuery(qry)
				.getResultList();

		return l;
	}

	@RequestMapping(value = "/veeEstimationActual", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object veeEstimationActual(HttpServletRequest request,
			@RequestParam(value = "mtrno", required = true) String mtrno,
			@RequestParam(value = "bm", required = true) String billmonth, Model model) {
		/*
		 * String mtrno=(String) request.getAttribute("mtrno"); String
		 * billmonth=(String) request.getAttribute("bm");
		 */
		List<Object[]> l = d4LoadDataService.getVeeEstimation(mtrno, billmonth);
		return l;

	}

	@RequestMapping(value = "/veeValidationCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public String veeValidationCheck(HttpServletRequest request, Model model) {
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("results", "notDisplay");
		return "veeValidationCheck";
	}

	@RequestMapping(value = "/getVeeValidationChecked", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getVeeValidationChecked(HttpServletRequest request) {

		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("subdiv");
		String type = request.getParameter("type");

		String qryLast = "";
		String sql = "SELECT * FROM meter_data.vee_rule_config where rule_name='";
		String ruleDesp = "";
		if (!"".equals(type) && type != null) {
			sql += type + "'";
		//	System.out.println("sql query==" + sql);
			List<?> ruleList = entityManager.createNativeQuery(sql).getResultList();
			if (ruleList.size() > 0) {
			//	System.out.println(ruleList.get(0));
				Object[] obj = (Object[]) ruleList.get(0);
				String con1 = String.valueOf(obj[3]);
				String param = String.valueOf(obj[2]);
				String con2 = String.valueOf(obj[4]);
				ruleDesp = String.valueOf(obj[5]);
				System.out.println("String.valueOf(obj[0])==" + String.valueOf(obj[0]) + "  String.valueOf(obj[1])=="
						+ String.valueOf(obj[1]) + "  String.valueOf(obj[2])==" + String.valueOf(obj[2])
						+ "  String.valueOf(obj[3]==" + String.valueOf(obj[3]));
				if (!"".equals(con2) && con2 != null && !"null".equals(con2)) {
					qryLast = " WHERE (" + param + con1 + " OR " + param + con2 + ")";
					System.out.println("QuertLast if sql==" + qryLast);
				} else {
					qryLast = " WHERE " + param + con1 + "";
					System.out.println("QuertLast else sql==" + qryLast);

				}
			}
		}
		String qrylevel = "";
		if (zone != null && !"".equals(zone)) {
			qrylevel += " AND zone='" + zone + "'";
			if (circle != null && !"".equals(circle)) {
				qrylevel += " AND circle='" + circle + "'";
				;
				if (division != null && !"".equals(division)) {
					qrylevel += " AND division='" + division + "'";
					;
					if (subdivision != null && !"".equals(subdivision)) {
						qrylevel += " AND subdivision='" + subdivision + "'";
						;

					}
				}
			}
		}

//		String sql1="SELECT m.mtrno,m.accno,m.kno,m.customer_name,m.sanload,m.contractdemand,v.* FROM meter_data.master_main m, \n" +
//				"meter_data.vee_rp_valid v WHERE m.mtrno=v.meter_number "+qryLast +qrylevel;

		String sql1 = "SELECT * FROM\n" + "(\n"
				+ "SELECT m.mtrno,m.accno,m.kno,m.customer_name,m.sanload,CAST(COALESCE(NULLIF(m.contractdemand,''),'0') as NUMERIC) as contractdemand,\n"
				+ "v.* ,CAST(COALESCE(NULLIF(m.mf,''),'1') as NUMERIC) as mf\n"
				+ "FROM meter_data.master_main m, meter_data.vee_rp_valid v WHERE m.mtrno=v.meter_number " + qrylevel
				+ " \n" + ")D " + qryLast;

		System.out.println("Final Query ===" + sql1);

		List<?> list = entityManager.createNativeQuery(sql1).getResultList();

		/*
		 * try { if(!"".equals(ruleDesp)) { AuditTrailEntity audit=new
		 * AuditTrailEntity();
		 * 
		 * audit.setTransactionId(numbGen()+""); audit.setValidation(ruleDesp);
		 * audit.setFlag("00000"); audit.setTimeStamp(new Date());
		 * audit.setStatus("success"); auditTrailService.customsaveBySchema(audit,
		 * "postgresMdas"); }
		 * 
		 * }catch (Exception e) { // TODO: handle exception }
		 */

		return list;
	}

	@RequestMapping(value = "/veeRulesConfig", method = { RequestMethod.GET, RequestMethod.POST })
	public String veeRulesConfig(HttpServletRequest request, ModelMap model) {

		model.addAttribute("results", "notDisplay");
		List<Map<String, String>> ruleList = getAllVeeRules();

		model.addAttribute("ruleList", ruleList);
		return "veeRulesConfig";
	}

	private List<Map<String, String>> getAllVeeRules() {
		String sql = "SELECT * FROM meter_data.vee_rule_config order by id";
		// System.out.println(sql);
		List<Map<String, String>> ruleList = new ArrayList<>();
		List<?> list = entityManager.createNativeQuery(sql).getResultList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, String> map = new HashMap<>();
			map.put("id", String.valueOf(obj[0]));
			map.put("name", String.valueOf(obj[5]));
			if (String.valueOf(obj[2]).equals("percentage")) {
				map.put("parameters", "Consumption Percentage");
			} else if (String.valueOf(obj[2]).equals("consumption")) {
				map.put("parameters", "Consumption");
			} else {
				map.put("parameters", String.valueOf(obj[2]));
			}
			map.put("con1", String.valueOf(obj[3]));
			map.put("con2", String.valueOf(obj[4]));
			ruleList.add(map);
		}
		return ruleList;
	}

	@Transactional
	@RequestMapping(value = "/updateVeeRulesConfigs", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object updateVeeRulesConfigs(HttpServletRequest request) {

		String type = request.getParameter("type");
		String parameters = request.getParameter("parameters");
		String condition = request.getParameter("condition");
		String param_val = request.getParameter("param_val");
		String con1;
		String con2 = "";

		if ("±".equals(condition)) {
			con1 = ">" + param_val;
			con2 = "<-" + param_val;
		} else {
			con1 = condition + param_val;
		}

		String sql = "update meter_data.vee_rule_config set parameter_name='" + parameters + "' , condition1='" + con1
				+ "', condition2='" + con2 + "' where rule_name='" + type + "';";
		int i = 0;
		// System.out.println(sql);
		try {
			entityManager.unwrap(Session.class).getTransaction().begin();
			i = entityManager.createNativeQuery(sql).executeUpdate();
			// entityManager.unwrap(Session.class).getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String status = "failed";
		if (i > 0) {
			status = "success";
		}
		return status;
	}

	@RequestMapping(value = "/getAllVeeRules", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getAllVeeRules(HttpServletRequest request) {

		List<Map<String, String>> ruleList = getAllVeeRules();
		return ruleList;
	}

	@RequestMapping(value = "/currentEstimation", method = { RequestMethod.GET, RequestMethod.POST })
	public String currentValidation(HttpServletRequest request, ModelMap model) {

		return "currentEstimation";
	}

	@RequestMapping(value = "/getCurrentEstimationData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getCurrentEstimationData(HttpServletRequest request) {

		String from = request.getParameter("fromDate");
		String to = request.getParameter("toDate");
		String type = request.getParameter("type");

		String s = "";
		if ("i".equals(type)) {
			s = "current";
		} else if ("v".equals(type)) {
			s = "voltage";
		}

		/*
		 * String
		 * sql="SELECT * FROM meter_data.load_survey_estimated WHERE date(read_time)>='"
		 * +from+"' and date(read_time)<='"+to+"' AND (("+type+"_r is NULL OR "
		 * +type+"_y is NULL OR "+type+"_b is NULL) \n" +
		 * " OR ("+type+"_r<0 OR "+type+"_y<0 OR "+type+"_b<0))"+
		 * " AND kwh is not null\n" + "ORDER BY meter_number, read_time DESC";
		 */

		String sql = "SELECT *, (CASE WHEN (SELECT phase FROM meter_data.master_main WHERE mtrno=meter_number)='1' THEN 'Single Phase' ELSE 'Three Phase' END) as phase\r\n"
				+ " FROM meter_data.load_survey_estimated WHERE date(read_time)>='" + from + "' and date(read_time)<='"
				+ to + "' \n" + "AND (((" + type + "_r is NULL OR " + type + "_y is NULL OR " + type
				+ "_b is NULL)  AND average_" + s + " is null ) \n" + " OR ((" + type + "_r<0 OR " + type + "_y<0 OR "
				+ type + "_b<0) AND CAST(average_" + s + " as NUMERIC) <0) ) AND kwh is not null\n"
				+ "ORDER BY meter_number, read_time DESC;";

		 System.out.println(sql);
		List<?> list = entityManager.createNativeQuery(sql).getResultList();

		try {
			AuditTrailEntity audit = new AuditTrailEntity();

			audit.setTransactionId(numbGen() + "");
			audit.setValidation(type.equals("v") ? "Voltage" : "Current");
			audit.setFlag("10000");
			audit.setTimeStamp(new Date());
			audit.setStatus("success");
			auditTrailService.customsaveBySchema(audit, "postgresMdas");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return list;
	}

	@RequestMapping(value = "/getLoadIntervaldataById", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getLoadIntervaldataById(HttpServletRequest request) {

		String id = request.getParameter("id");
		String type = request.getParameter("type");

		String sql = "SELECT l.id,l.meter_number,l.read_time,l.i_r,l.i_y,l.i_b, \"lead\"(" + type
				+ ") OVER (ORDER BY l.read_time DESC) as estimated \n" + ",v_r,v_y,v_b FROM meter_data.load_survey l,\n"
				+ "(\n" + "SELECT meter_number, read_time FROM meter_data.load_survey WHERE id='" + id + "'\n"
				+ ")A WHERE l.meter_number=A.meter_number AND l.read_time<=A.read_time ORDER BY l.read_time DESC LIMIT 2";
		// System.out.println(sql);
		List<?> list = entityManager.createNativeQuery(sql).getResultList();

		return list;
	}

	@RequestMapping(value = "/saveEstimationChanges", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveEstimationChanges(HttpServletRequest request) {

		String col = request.getParameter("col");
		String colid = request.getParameter("colid");
		String val = request.getParameter("val");

		AmrLoadEntity l = amrLoadService.findEntityById(colid);
		if (l != null) {
			Gson gson = new Gson();
			String jsonInString = gson.toJson(l);

			LoadSurveyEstimated le = gson.fromJson(jsonInString, LoadSurveyEstimated.class);
			String jsonInString1 = gson.toJson(le);
			// le.setId(null);
			le.setLoad_id(Long.parseLong(colid));
			if ("i_r".equals(col)) {
				le.setiR(Double.parseDouble(val));
			} else if ("i_y".equals(col)) {
				le.setiY(Double.parseDouble(val));
			} else if ("i_b".equals(col)) {
				le.setiB(Double.parseDouble(val));
			} else if ("v_r".equals(col)) {
				le.setvR(Double.parseDouble(val));
			} else if ("v_y".equals(col)) {
				le.setvY(Double.parseDouble(val));
			} else if ("v_b".equals(col)) {
				le.setvB(Double.parseDouble(val));
			}
			le.setEstimation_status("11100");

			loadEstimationService.customupdatemdas(le);

			// System.out.println(jsonInString1);
			return "success";
		}

		return "failed";
	}

	public long numbGen() {
		while (true) {
			long numb = (long) (Math.random() * 100000000 * 1000000); // had to use this as int's are to small for a 13
																		// digit number.
			if (String.valueOf(numb).length() == 13)
				return numb;
		}
	}

	@RequestMapping(value = "/missingIntervals", method = { RequestMethod.GET, RequestMethod.POST })
	public String missingIntervals(HttpServletRequest request, ModelMap model) {

		List<?> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);
		return "missingIntervals";
	}

	@RequestMapping(value = "/getMissingIntervalReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getMissingIntervalReport(HttpServletRequest request) {
		String zone = request.getParameter("zone");
		String loadDate = request.getParameter("loadDate");
		String circle = request.getParameter("circle");
		String qry = "SELECT * FROM \n" + "(\n"
				+ "SELECT \"zone\", circle,division, subdivision,substation, customer_name,mtrno,to_date('" + loadDate
				+ "','YYYY-MM-DD') as ldate,COALESCE(lcount,0) as lcount ,"
				+ " (CASE WHEN lcount is NULL THEN 0 WHEN lcount>48 THEN 15 ELSE 30 END) as intrvl, accno FROM  \n"
				+ "(SELECT * FROM meter_data.master_main)m LEFT JOIN \n" + "(\n"
				+ "SELECT meter_number, \"count\"(*) as lcount FROM meter_data.load_survey WHERE date(read_time)='"
				+ loadDate + "' GROUP BY meter_number\n" + ")l ON m.mtrno=l.meter_number \n"
				+ ")A WHERE (A.lcount<>48 AND A.lcount<>96) AND ZONE like '%" + zone + "%' AND circle like '%" + circle
				+ "%'";
		// System.out.println(qry);
		try {
			List<?> list = entityManager.createNativeQuery(qry).getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/energySumCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public String energySumCheck(HttpServletRequest request, ModelMap model) {

		List<?> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);
		return "energySumCheck";
	}

	@RequestMapping(value = "/getEnergySumCheckReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getEnergySumCheckReport(HttpServletRequest request) {
		String zone = request.getParameter("zone");
		String month = request.getParameter("month");
		String circle = request.getParameter("circle");
		String qry = "SELECT m.\"zone\", m.circle,m.division,m.subdivision,m.substation,m.accno, m.customer_name,m.kno,m.contractdemand,m.sanload, \n"
				+ "COALESCE(X.meter_number,Y.meter_number) as col1, COALESCE(l_consumption,0) as col2,billing_date,kwh,prev_kwh,consumption,COALESCE((consumption-l_consumption),0) as diff FROM meter_data.master_main m,\n"
				+ "(\n"
				+ "SELECT meter_number, \"sum\"(kwh) as l_consumption FROM meter_data.load_survey WHERE to_char(read_time, 'YYYYMM')='"
				+ month + "'\n" + "GROUP BY meter_number\n"
				+ ")X FULL JOIN (SELECT A.*,(A.kwh-A.prev_kwh) as consumption FROM\n"
				+ "(SELECT meter_number, billing_date, kwh, lead(kwh) OVER (ORDER BY meter_number,billing_date DESC) as prev_kwh\n"
				+ " FROM meter_data.bill_history WHERE  to_char(billing_date, 'YYYY-MM-DD hh24:mi:ss') like '%00:00:00' \n"
				+ "ORDER BY meter_number,billing_date DESC\n" + ")A WHERE to_char(billing_date, 'YYYYMM')>'" + month
				+ "'\n" + ")Y on x.meter_number =y.meter_number WHERE m.mtrno=x.meter_number AND m.ZONE like '%" + zone
				+ "%' AND m.circle like '%" + circle + "%'";
		// System.out.println(qry);
		try {
			List<?> list = entityManager.createNativeQuery(qry).getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//@Scheduled(cron = "0 0/30 * * * ?")
	public void updateAuditTrail() {

		String[] s = { "Current", "Voltage" };

		for (String type : s) {

			try {
				List<LoadSurveyEstimated> list = loadEstimationService.getDataForEstimationCheck(type);
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					LoadSurveyEstimated estimated = (LoadSurveyEstimated) iterator.next();
					// estimated.setEstimation_status("00000");

				}
				AuditTrailEntity audit = new AuditTrailEntity();

				audit.setTransactionId(numbGen() + "");
				audit.setValidation(type);
				audit.setFlag("10000");
				audit.setTimeStamp(new Date());
				audit.setStatus("success");
				auditTrailService.customsaveBySchema(audit, "postgresMdas");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/********************** TARIK IMPLEMENTATION **********************/
	@RequestMapping(value = "/defineValidation", method = { RequestMethod.GET, RequestMethod.POST })
	public String defineValidation(HttpServletRequest request, Model model) {

		if (veeUpdateFlag == 1) {
			model.addAttribute("results", "Data Saved Successfully");
			model.addAttribute("alert_type", "success");
			veeUpdateFlag = 0;
		} else if (veeUpdateFlag == 2) {
			model.addAttribute("results", "Data Update Successfully");
			model.addAttribute("alert_type", "success");
			veeUpdateFlag = 0;
		} else if (veeUpdateFlag == 3) {
			model.addAttribute("results", "OOPS! Something went wrong!!");
			model.addAttribute("alert_type", "error");
			veeUpdateFlag = 0;
		} else {
			model.addAttribute("results", "notDisplay");
		}

		return "defineValidation";
	}

	@RequestMapping(value = "/estimationRule", method = { RequestMethod.GET, RequestMethod.POST })
	public String estimationRule(HttpServletRequest request, Model model) {
		if (veeUpdateFlag == 1) {
			model.addAttribute("results", "Data Saved Successfully");
			model.addAttribute("alert_type", "success");
			veeUpdateFlag = 0;
		} else if (veeUpdateFlag == 2) {
			model.addAttribute("results", "Data Update Successfully");
			model.addAttribute("alert_type", "success");
			veeUpdateFlag = 0;
		} else if (veeUpdateFlag == 3) {
			model.addAttribute("results", "OOPS! Something went wrong!!");
			model.addAttribute("alert_type", "error");
			veeUpdateFlag = 0;
		} else {
			model.addAttribute("results", "notDisplay");
		}

		return "estimationRule";
	}

	@RequestMapping(value = "/getlatestRuleId", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getlatestRuleId(HttpServletRequest request, Model model) {
		model.addAttribute("results", "notDisplay");
		String ruleID = addveeservice.getlatestRuleId();
		model.addAttribute("ruleIDs", ruleID);
		return ruleID;
	}

	@RequestMapping(value = "/getESTlatestRuleId", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getESTlatestRuleId(HttpServletRequest request, Model model) {
		model.addAttribute("results", "notDisplay");
		String ruleID = addestservice.getlatestRuleId();
		model.addAttribute("eruleID", ruleID);
		return ruleID;
	}

	@RequestMapping(value = "/getEstimationActiveRule", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<EstimationRuleEntity> getEstimationActiveRule(HttpServletRequest request, Model model) {
		// System.out.println("hii");
		model.addAttribute("results", "notDisplay");
		List<EstimationRuleEntity> vrEnt = addestservice.getEstimationActiveRule();
		
//		  for (EstimationRuleEntity abc : vrEnt) {
//		  
//		  }
		 
		model.addAttribute("EstimationRule", vrEnt);
		return vrEnt;
	}

	@RequestMapping(value = "/addVEERule", method = { RequestMethod.POST, RequestMethod.GET })
	public String addVEERule(@RequestParam(value = "v_uni_radio", required = false) String v_uni_radio1,
			@RequestParam(value = "v_auto_radio", required = false) String v_auto_radio1,
			@RequestParam(value = "v_alarm_radio", required = false) String v_alarm_radio1,
			@RequestParam(value = "v_trigger_radio", required = false) String v_trigger_radio1, ModelMap model,
			HttpServletRequest request) {
		String ruleid = request.getParameter("RLID").trim();
		String rulename = request.getParameter("RLNAME").trim();
		Double lwthrlimit = Double.valueOf(request.getParameter("LWTLMT")==""?"0":request.getParameter("LWTLMT"));
		Double hgthrlimit = Double.valueOf(request.getParameter("HGTLMT")==""?"0":request.getParameter("HGTLMT"));
		Boolean v_uni_radio = null;
		Boolean v_auto_radio = null;
		Boolean v_alarm_radio = null;
		Boolean v_trigger_radio = null;
		

		if (v_uni_radio1 != null) {
			v_uni_radio = true;
		}
		if (v_uni_radio1 == null) {
			v_uni_radio = false;
		}

		if (v_auto_radio1 != null) {
			v_auto_radio = true;
		}
		if (v_auto_radio1 == null) {
			v_auto_radio = false;
		}
		if (v_alarm_radio1 != null) {
			v_alarm_radio = true;
		}
		if (v_alarm_radio1 == null) {
			v_alarm_radio = false;
		}

		if (v_trigger_radio1 != null) {
			v_trigger_radio = true;
		}
		if (v_trigger_radio1 == null) {
			v_trigger_radio = false;
		}

		String erulename = request.getParameter("ERLNAME");
		HttpSession session = request.getSession(false);
		model.addAttribute("userType", session.getAttribute("userType"));
		try {
			VeeRuleEntity v = new VeeRuleEntity();
			v.setRuleid(ruleid);
			v.setRulename(rulename);
			v.setLwthrlimit(lwthrlimit);
			v.setHgthrlimit(hgthrlimit);

			v.setUniversal_v_rule(v_uni_radio);
			v.setAuto_v_rule(v_auto_radio);
			v.setAlarm_v_rule(v_alarm_radio);
			v.setTrigger_v_rule(v_trigger_radio);
			v.setErulename(erulename);
			v.setIs_active(Boolean.valueOf("True"));
			v.setEntry_by(session.getAttribute("username").toString());
			v.setEntry_date(new Timestamp(System.currentTimeMillis()));
			addveeservice.save(v);
			veeUpdateFlag = 1;
		} catch (Exception e) {
			e.printStackTrace();
			veeUpdateFlag = 3;
		}
		return "redirect:/defineValidation";

	}

	@RequestMapping(value = "/addESTRule", method = { RequestMethod.POST, RequestMethod.GET })
	public String addESTRule(ModelMap model, HttpServletRequest request) {
		String eruleid = request.getParameter("ERLID");
		String erulename = request.getParameter("ERLNAME");
		HttpSession session = request.getSession(false);
		try {
			EstimationRuleEntity e = new EstimationRuleEntity();
			e.setEruleid(eruleid);
			e.setErulename(erulename);
			e.setIs_active(Boolean.valueOf("True"));
			e.setEntry_by(session.getAttribute("username").toString());
			e.setEntry_date(new Timestamp(System.currentTimeMillis()));
			addestservice.save(e);
			veeUpdateFlag = 1;
		} catch (Exception e) {
			e.printStackTrace();
			veeUpdateFlag = 3;
		}
		return "redirect:/estimationRule";

	}

	@RequestMapping(value = "/getVeeRuleDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<VeeRuleEntity> getVeeRuleDetails(HttpServletRequest request, Model model) {
		// System.out.println("hii");
		model.addAttribute("results", "notDisplay");
		List<VeeRuleEntity> vrEnt = addveeservice.getActiveVeeRule();
//		for (VeeRuleEntity abc : vrEnt) {
//			System.err.println(abc.getRuleid());
//		}

		// model.addAttribute("Re", vrEnt);
		return vrEnt;
	}
	
	
	@RequestMapping(value = "/getAllVeeRuleDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<VeeRuleEntity> getAllVeeRuleDetails(HttpServletRequest request, Model model) {
		// System.out.println("hii");
		model.addAttribute("results", "notDisplay");
		List<VeeRuleEntity> vrEnt = addveeservice.getVeeRule();
//		for (VeeRuleEntity abc : vrEnt) {
//			System.err.println(abc.getRuleid());
//		}

		// model.addAttribute("Re", vrEnt);
		return vrEnt;
	}

	@RequestMapping(value = "/getEstimationRule", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<EstimationRuleEntity> getEstimationRule(HttpServletRequest request, Model model) {
		// System.out.println("hii");
		model.addAttribute("results", "notDisplay");
		List<EstimationRuleEntity> vrEnt = addestservice.getEstimationRule();
//		for (EstimationRuleEntity abc : vrEnt) {
//			 System.err.println(abc.getEruleid());
//		}

		return vrEnt;
	}

	@RequestMapping(value = "/editVEERule", method = { RequestMethod.POST, RequestMethod.GET })
	public String editVEERule(@RequestParam(value = "uv_uni_radio", required = false) String uv_uni_radio1,
			@RequestParam(value = "uv_auto_radio", required = false) String uv_auto_radio1,
			@RequestParam(value = "uv_alarm_radio", required = false) String uv_alarm_radio1,
			@RequestParam(value = "uv_trigger_radio", required = false) String uv_trigger_radio1,
			@RequestParam(value = "isActive_radio", required = false) String isActive_radio1, ModelMap model,
			HttpServletRequest request) {
		String ruleid = request.getParameter("URLID").trim();
		String rulename = request.getParameter("URLNAME").trim();
		String ULWTLMT=request.getParameter("ULWTLMT");
		String UHGTLMT=request.getParameter("UHGTLMT");
		if("NA".equalsIgnoreCase(ULWTLMT) || ULWTLMT=="" ) {
			ULWTLMT="0";
		}
		if("NA".equalsIgnoreCase(UHGTLMT) || UHGTLMT=="") {
			UHGTLMT="0";
		}
//		System.out.println("lwthrlimit="+request.getParameter("ULWTLMT")+"hgthrlimit="+request.getParameter("UHGTLMT"));
		Double lwthrlimit = Double.valueOf(ULWTLMT=="" ?"0" : ULWTLMT);
		Double hgthrlimit = Double.valueOf(UHGTLMT=="" ?"0" : UHGTLMT);
		String erulename = request.getParameter("UERLNAME").trim();
		Boolean uv_uni_radio = null;
		Boolean uv_auto_radio = null;
		Boolean uv_alarm_radio = null;
		Boolean uv_trigger_radio = null;
		Boolean isActive_radio = null;
//		System.out.println("lwthrlimit="+lwthrlimit+"hgthrlimit="+hgthrlimit);
		if (uv_uni_radio1 != null) {
			uv_uni_radio = true;
		}
		if (uv_uni_radio1 == null) {
			uv_uni_radio = false;
		}

		if (uv_auto_radio1 != null) {
			uv_auto_radio = true;
		}
		if (uv_auto_radio1 == null) {
			uv_auto_radio = false;
		}
		if (uv_alarm_radio1 != null) {
			uv_alarm_radio = true;
		}
		if (uv_alarm_radio1 == null) {
			uv_alarm_radio = false;
		}

		if (uv_trigger_radio1 != null) {
			uv_trigger_radio = true;
		}
		if (uv_trigger_radio1 == null) {
			uv_trigger_radio = false;
		}

		if (isActive_radio1 != null) {
			isActive_radio = true;
		}
		if (isActive_radio1 == null) {
			isActive_radio = false;
		}

		HttpSession session = request.getSession(false);

		try {
			VeeRuleEntity v = addveeservice.getVeeRuleById(ruleid);
			v.setRulename(rulename);
			v.setLwthrlimit(lwthrlimit);
			v.setHgthrlimit(hgthrlimit);
			v.setUniversal_v_rule(uv_uni_radio);
			v.setAuto_v_rule(uv_auto_radio);
			v.setAlarm_v_rule(uv_alarm_radio);
			v.setTrigger_v_rule(uv_trigger_radio);
			v.setErulename(erulename);
			v.setIs_active(isActive_radio);
			v.setUpdate_by(session.getAttribute("username").toString());
			v.setUpdate_date(new Timestamp(System.currentTimeMillis()));

			addveeservice.update(v);
			veeUpdateFlag = 2;
		} catch (Exception e) {
			e.printStackTrace();
			veeUpdateFlag = 3;
		}
		return "redirect:/defineValidation";

	}

	@RequestMapping(value = "/editESTRule", method = { RequestMethod.POST, RequestMethod.GET })
	public String editESTRule(@RequestParam(value = "isActive_radio", required = false) String isActive_radio1,
			ModelMap model, HttpServletRequest request) {
		String ruleid = request.getParameter("UERLID").trim();
		String rulename = request.getParameter("UERLNAME").trim();
		String data_type = request.getParameter("UDATA_TYPE").trim();
		String parameter = request.getParameter("UPARAMETER").trim();
		String condname = request.getParameter("UCONDNAME").trim();
		String condval = request.getParameter("UCONDVAL").trim();
		Boolean isActive_radio = null;

		if (isActive_radio1 != null) {
			isActive_radio = true;
		}
		if (isActive_radio1 == null) {
			isActive_radio = false;
		}

		HttpSession session = request.getSession(false);
		// System.out.println(session.getAttribute("username")+" "+new
		// Timestamp(System.currentTimeMillis()));
		try {
			EstimationRuleEntity ve = addestservice.getESTRuleById(ruleid);
			ve.setErulename(rulename);
			ve.setIs_active(isActive_radio);
			ve.setCondtion(condname);
			ve.setCondval(condval);
			ve.setData_type(data_type);
			ve.setParameter(parameter);
			ve.setUpdate_by(session.getAttribute("username").toString());
			ve.setUpdate_date(new Timestamp(System.currentTimeMillis()));

			addestservice.update(ve);
			veeUpdateFlag = 2;
		} catch (Exception e) {
			e.printStackTrace();
			veeUpdateFlag = 3;
		}
		return "redirect:/estimationRule";

	}

	@RequestMapping(value = "/assignValidation", method = { RequestMethod.GET, RequestMethod.POST })
	public String assignValidation(HttpServletRequest request, Model model, HttpServletResponse response) {
		
		  List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		  model.addAttribute("zoneList", zoneList); 
		  model.addAttribute("results", "notDisplay");
		  
		/*
		 * if (veeUpdateFlag == 1) { model.addAttribute("results",
		 * "Data Saved Successfully"); model.addAttribute("alert_type", "success");
		 * veeUpdateFlag = 0; } else if (veeUpdateFlag == 2) {
		 * model.addAttribute("results", "Data Update Successfully");
		 * model.addAttribute("alert_type", "success"); veeUpdateFlag = 0; } else if
		 * (veeUpdateFlag == 3) { model.addAttribute("results",
		 * "OOPS! Something went wrong!!"); model.addAttribute("alert_type", "error");
		 * veeUpdateFlag = 0; } else { model.addAttribute("results", "notDisplay"); }
		 */

//		return "defineValidation";
		
		return "assignValidation";
	}
	
	@RequestMapping(value = "/downloadAssignValidationPdforDT", method = { RequestMethod.GET, RequestMethod.POST })
	public void downloadAssignValidationPdf(HttpServletRequest request, Model model, HttpServletResponse response) 
	{
		String town = request.getParameter("t");
		String ruletype = request.getParameter("ruleType");
		String dtcode = request.getParameter("dtcode");
		String mtrno = request.getParameter("dtmtrno");
		String zone = request.getParameter("z");
		String circle = request.getParameter("c").trim();
		List<FileInputStream> list=new ArrayList<FileInputStream>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String crossdt = request.getParameter("crossdt").trim();
		String active = request.getParameter("active").trim();
		String active_tab = "";
		String crdt="";
		if(crossdt.equals("true")) {
			crdt="1";
		}
		else {
			crdt="0";
		}
		String z = "";
		String c = "";
		
		String t = "";
		if(zone.equalsIgnoreCase("All"))
		{
			z = "%";
		}
		else {
			z = zone;
		}
		if(circle.equalsIgnoreCase("All"))
		{
			c = "%";
		}
		else
		{
			c = circle;
		}
		if(town.equalsIgnoreCase("All"))
		{
			t = "%";
		}
		else
		{
			t = town;
		}

		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+z+"' AND circle LIKE  '"+c+"'  and tp_towncode like '"+t+"'";
		//System.out.println("hi=="+sdocode);
        
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		//System.out.println("hi=="+sdocode);
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
		
		if(active.equalsIgnoreCase("tab_1_1"))
		{
			active_tab = "not in";
		}
		else 
		{
			active_tab = "in";
		}
	//	System.out.println(subcode);
			
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,CAST(dt.crossdt AS varchar),COALESCE(dt.dttpid,''),dt.meterno,mm.town_code,COALESCE(dt.dt_id,'') as dtid \r\n" + 
				"from meter_data.dtdetails dt ,meter_data.master_main mm  \r\n" + 
				"where  mm.sdocode = CAST(dt.officeid AS varchar) and mm.mtrno=dt.meterno and CAST(dt.tp_town_code AS varchar) in (" + towncode +") " +
				"AND dt.dt_id "+active_tab+" (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='DT' and status=1 AND v_rule_id='"+ruletype+"')";
 
	  if (!dtcode.isEmpty()) { 
		  sqlqry+= " and dt.dttpid='"+dtcode+"'";
		  } if (!crdt.isEmpty()) {
			  sqlqry+= " and CAST(dt.crossdt AS varchar)='"+crdt+"'"; 
		  } if (!mtrno.isEmpty()) { 
			  sqlqry+= " and dt.meterno='"+mtrno+"'";  
		  }
  
	  
	
		     
		 
		finalQury+=sqlqry+";";
		System.err.println("Final DT query---:" + finalQury);
		

		
		
		
		try 
		{
			


			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			 baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			
		        Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		        Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
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
		        pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		        cell1.setBorder(Rectangle.NO_BORDER);
		        cell1.addElement(pstart);
		        pdf2.addCell(cell1);
		        pstart.add(new Chunk(glue));
		        pstart.add(new Phrase("Reading Month : "+new SimpleDateFormat("MMM-YYYY").format(new SimpleDateFormat("yyyyMM").parse("202009")),new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		        
		        document.add(pdf2);
		        PdfPCell cell2 = new PdfPCell();
		        Paragraph p1 = new Paragraph();
		        p1.add(new Phrase("Assign Validation Rules : " +ruletype,new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		        p1.setAlignment(Element.ALIGN_CENTER);
		        cell2.addElement(p1);
		        cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		        pdf1.addCell(cell2);
		        document.add(pdf1);
		        
		        PdfPTable header = new PdfPTable(4);
	             header.setWidthPercentage(100);
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));

	             
	             
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             document.add(header);

		 			List<Object[]> eventDataDateList=entityManager.createNativeQuery(finalQury).getResultList();
			        				
			        				PdfPTable parameterTable = new PdfPTable(6);
			   	                 parameterTable.setWidths(new int[]{1,1,1,1,1,1});
			   	                 parameterTable.setWidthPercentage(100);
			   		             PdfPCell parameterCell;
			   		             parameterCell = new PdfPCell(new Phrase("SR NO.",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			   		             parameterTable.addCell(parameterCell);
			   		             
			   		          parameterCell = new PdfPCell(new Phrase("Circle",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
			   		          parameterTable.addCell(parameterCell);
			   		           
			   		          parameterCell = new PdfPCell(new Phrase("Division",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
			   		             parameterTable.addCell(parameterCell);
			   		             
			   		          parameterCell = new PdfPCell(new Phrase("Sub Division",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
			   		             parameterTable.addCell(parameterCell);
			   		             
			   		          
			   		          
			   		       parameterCell = new PdfPCell(new Phrase("DT Code",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		          parameterTable.addCell(parameterCell);
		   		          
		   		       parameterCell = new PdfPCell(new Phrase("Meter Number",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	 		             parameterCell.setFixedHeight(25f);
	 		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	 		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
	 		          parameterTable.addCell(parameterCell);
	 		          
	 	
		          
		       
			   		          
			        				for (int i = 0; i < eventDataDateList.size(); i++) 
			    	                {
			        					parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		   								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   								 parameterTable.addCell(parameterCell);
			    	                	Object[] obj=eventDataDateList.get(i);
			    	                	for (int j = 0; j < obj.length; j++) 
			    	                	{
			    	                		if(j==0)
			    	                		{
			    								 parameterCell = new PdfPCell(new Phrase(obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								 parameterCell = new PdfPCell(new Phrase(obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								 
			    								
			    								 
			    								 parameterCell = new PdfPCell(new Phrase(obj[2]==null?"":obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								 
			    							
			    	                		
			    	                		
			    	                		parameterCell = new PdfPCell(new Phrase(obj[4]==null?"":obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 
		    							
		    	                		
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj[5]==null?"":obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    	                		
		    								
		    								 
		    								 
		    	                		
			    	        
		    	                		
		    	                		}
		    							
		    						}
		    					} 
		        				
		    	                document.add(parameterTable);
		       
			document.close();
			
			
		
			response.setHeader("Content-disposition", "attachment; filename=Assign_Validation_Rule_DT_"+ruletype+".pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();
		    
		/*	response.setHeader("Content-disposition", "attachment; filename=InstantaneousParameters_"+meterno+"-"+month+".pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();*/

			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	@RequestMapping(value = "/downloadAssignValidationPdforFeeder", method = { RequestMethod.GET, RequestMethod.POST })
	public void downloadAssignValidationPdforFeeder(HttpServletRequest request, Model model, HttpServletResponse response) 
	{
		String town = request.getParameter("t");
		String ruletype = request.getParameter("ruleType");
		String feedercode = request.getParameter("feedercode");
		String mtrno = request.getParameter("fdrmtrno");
		String zone = request.getParameter("z");
		String circle = request.getParameter("c").trim();
		List<FileInputStream> list=new ArrayList<FileInputStream>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String crossfeeder = request.getParameter("crossfeeder").trim();
		String active = request.getParameter("active").trim();
		String active_tab = "";
		String crdt="";
		if(crossfeeder.equals("true")) {
			crdt="1";
		}
		else {
			crdt="0";
		}
		
		String z = "";
		String c = "";
		
		String t = "";
		if(zone.equalsIgnoreCase("All"))
		{
			z = "%";
		}
		else
		{
			z = zone;
		}
		if(circle.equalsIgnoreCase("All"))
		{
			c = "%";
		}
		else
		{
			c = circle;
		}
		if(town.equalsIgnoreCase("All"))
		{
			t = "%";
		}
		else
		{
			t = town;
		}

		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+z+"' AND circle LIKE  '"+c+"'  and tp_towncode like '"+t+"'";
		//System.out.println("hi=="+sdocode);
        
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		//System.out.println("hi=="+sdocode);
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
		
		if(active.equalsIgnoreCase("tab_1_1"))
		{
			active_tab = "not in";
		}
		else 
		{
			active_tab = "in";
		}
	//	System.out.println(subcode);
			
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,COALESCE(fd.feedername,'') as feedername,COALESCE(fd.tp_fdr_id,'') as fdr_id,CAST(fd.crossfdr AS varchar),fd.meterno,mm.town_code,COALESCE(fd.fdr_id,'') as fdrid\r\n" + 
				"from meter_data.feederdetails fd ,meter_data.master_main mm  \r\n" + 
				"where  mm.sdocode = CAST(fd.officeid AS varchar) and mm.mtrno=fd.meterno and CAST(fd.tp_town_code AS varchar) in (" + towncode +")" +
				"AND fd.fdr_id  "+active_tab+" (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='Feeder' and status=1 AND v_rule_id='"+ruletype+"')";

		     if (!feedercode.isEmpty()) { 
			  sqlqry+= " and fd.tp_fdr_id='"+feedercode+"'";
			  } if (!crossfeeder.isEmpty()) {
				  sqlqry+= " and CAST(fd.crossfdr AS varchar)='"+crdt+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and fd.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		System.err.println("Final DT query---:" + finalQury);
		

		
		
		
		try 
		{
			


			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			 baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			
		        Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		        Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
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
		        pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		        cell1.setBorder(Rectangle.NO_BORDER);
		        cell1.addElement(pstart);
		        pdf2.addCell(cell1);
		        pstart.add(new Chunk(glue));
		        pstart.add(new Phrase("Town Code : "+towncode,new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		        
		        document.add(pdf2);
		        PdfPCell cell2 = new PdfPCell();
		        Paragraph p1 = new Paragraph();
		        p1.add(new Phrase("Assign Validation Rules : " +ruletype,new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		        p1.setAlignment(Element.ALIGN_CENTER);
		        cell2.addElement(p1);
		        cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		        pdf1.addCell(cell2);
		        document.add(pdf1);
		        
		        PdfPTable header = new PdfPTable(4);
	             header.setWidthPercentage(100);
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));

	             
	             
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             document.add(header);

		 			List<Object[]> eventDataDateList=entityManager.createNativeQuery(finalQury).getResultList();
			        				
			        				PdfPTable parameterTable = new PdfPTable(8);
			   	                 parameterTable.setWidths(new int[]{1,1,1,1,3,1,1,1});
			   	                 parameterTable.setWidthPercentage(100);
			   		             PdfPCell parameterCell;
			   		             parameterCell = new PdfPCell(new Phrase("SR NO.",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			   		             parameterTable.addCell(parameterCell);
			   		             
			   		          parameterCell = new PdfPCell(new Phrase("Circle",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
			   		          parameterTable.addCell(parameterCell);
			   		           
			   		          parameterCell = new PdfPCell(new Phrase("Division",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
			   		             parameterTable.addCell(parameterCell);
			   		             
			   		          parameterCell = new PdfPCell(new Phrase("Sub Division",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
			   		             parameterCell.setFixedHeight(25f);
			   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
			   		             parameterTable.addCell(parameterCell);
			   		             
			   		          
			   		          
			   		       parameterCell = new PdfPCell(new Phrase("Feeder Name",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		          parameterTable.addCell(parameterCell);
		   		          
		   		       parameterCell = new PdfPCell(new Phrase("Boundary  Feeder",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
	 		             parameterCell.setFixedHeight(25f);
	 		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	 		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
	 		          parameterTable.addCell(parameterCell);
	 		          
	 		         parameterCell = new PdfPCell(new Phrase("Feeder Code",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
 		             parameterCell.setFixedHeight(25f);
 		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
 		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
 		          parameterTable.addCell(parameterCell);
 		          
 		          
 		         parameterCell = new PdfPCell(new Phrase("Meter Number",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		             parameterCell.setFixedHeight(25f);
		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		          parameterTable.addCell(parameterCell);
		          
	 		          
	 	
		          
		       
			   		          
			        				for (int i = 0; i < eventDataDateList.size(); i++) 
			    	                {
			        					parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		   								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   								 parameterTable.addCell(parameterCell);
			    	                	Object[] obj=eventDataDateList.get(i);
			    	                	for (int j = 0; j < obj.length; j++) 
			    	                	{
			    	                		if(j==0)
			    	                		{
			    								 parameterCell = new PdfPCell(new Phrase(obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								 parameterCell = new PdfPCell(new Phrase(obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								 
			    								
			    								 
			    								 parameterCell = new PdfPCell(new Phrase(obj[2]==null?"":obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								 
			    							
			    	                		
			    	                		
			    	                		parameterCell = new PdfPCell(new Phrase(obj[3]==null?"":obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 
		    							
		    	                		
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj[5]==null?"":obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj[4]==null?"":obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    	                		
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj[6]==null?"":obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    	                		
		    	                		
		    								
		    								 
		    								 
		    	                		
			    	        
		    	                		
		    	                		}
		    							
		    						}
		    					} 
		        				
		    	                document.add(parameterTable);
		       
			document.close();
			
			
		
			response.setHeader("Content-disposition", "attachment; filename=Assign_Validation_Rule_Feeder_"+ruletype+".pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();
		    
		/*	response.setHeader("Content-disposition", "attachment; filename=InstantaneousParameters_"+meterno+"-"+month+".pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();*/

			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	public PdfPCell getCell(String text, int alignment) 
	 {
		    PdfPCell cell = new PdfPCell(new Phrase(text));
		    cell.setPadding(5);
		    cell.setHorizontalAlignment(alignment);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    return cell;
		}

	@RequestMapping(value = "/assignEstimation", method = { RequestMethod.GET, RequestMethod.POST })
	public String assignEstimation(HttpServletRequest request, Model model) {
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		
		//  model.addAttribute("zoneList", zoneList);
		  model.addAttribute("results", "notDisplay");
		 
		return "assignEstimation";
	}

	@RequestMapping(value = "/getConsumercategory", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getConsumercategory(HttpServletRequest request, Model model) {
		// System.out.println("hii");
		model.addAttribute("results", "notDisplay");
	//	String sqlqry = "select DISTINCT(tadesc) from meter_data.consumermaster where tadesc is not null";
		
		String sqlqry = "select DISTINCT(COALESCE(tadesc,'')) from meter_data.consumermaster";

		// String sqlqry="select DISTINCT(fdrcategory) from meter_data.master_main where
		// fdrcategory not in('BORDER METER','FEEDER METER','DT')";

		Query query = entityManager.createNativeQuery(sqlqry);
		List li = query.getResultList();
		// model.addAttribute("Re", vrEnt);
//		System.out.println(li);
		return li;
	}

	@RequestMapping(value = "/getConsumerData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getConsumerData(HttpServletRequest request, Model model) {
		 System.err.println("hii");
		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("sdoCode").trim();
		String consumerCatgry = request.getParameter("consumerCatgry").trim();
		String acno = request.getParameter("acno").trim();
		String kno = request.getParameter("kno").trim();
		String mtrno = request.getParameter("mtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
		
		//String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct sitecode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' ";
       // System.err.println("---query feeder sun station---:" + sql);
		
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		//System.out.println("hi=="+sdocode);
		String subcode="";
		for(Object item : sdocode){
			subcode+="'"+item+"',";		
		}
		if(subcode.endsWith(","))
		{
			subcode = subcode.substring(0,subcode.length() - 1);
		}
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,cm.accno,cm.kno,cm.name,COALESCE(cm.tadesc,''),cm.meterno " + 
				"from meter_data.consumermaster cm,meter_data.master_main mm WHERE mm.sdocode = cm.sdocode and mm.accno=cm.accno" + 
				" AND cm.sdocode  in (" + subcode +") AND cm.accno not in (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='Consumer' and status=1 AND v_rule_id='"+ruleid+"')";
		if (!consumerCatgry.isEmpty()) {
			sqlqry+= " and cm.tadesc='"+consumerCatgry+"'";
         }
		if (!acno.isEmpty()) {
			sqlqry+= " and cm.accno='"+acno+"'";
        }
		if ( !kno.isEmpty()) {
			sqlqry+= " and cm.kno='"+kno+"'";
        }
		if (!mtrno.isEmpty()) {
			sqlqry+=  " and cm.meterno='"+mtrno+"'";
        }
		
		
		finalQury+=sqlqry+";";
		//System.err.println("Final query---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	
	@RequestMapping(value = "/getAssignConsumerData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getAssignConsumerData(HttpServletRequest request, Model model) {
//		 System.err.println("hii");
		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division").trim();
		String subdivision = request.getParameter("sdoCode").trim();
		String consumerCatgry = request.getParameter("consumerCatgry").trim();
		String acno = request.getParameter("acno").trim();
		String kno = request.getParameter("kno").trim();
		String mtrno = request.getParameter("mtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();

//		String queryTail = "";	
//		if("ALL".equalsIgnoreCase(zone)) {
//			queryTail="";
//		} else if("ALL".equalsIgnoreCase(circle)) {
//			queryTail=" WHERE zone='"+zone+"' ";
//		} else if("ALL".equalsIgnoreCase(division)) {
//			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"'";
//		} else if("ALL".equalsIgnoreCase(subdivision)) {
//			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"'";
//		} else {
//			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"' AND subdivision = '"+subdivision+"' ";
//		}
//		String sql="SELECT distinct sdocode from meter_data.master_main"+queryTail;
		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct sitecode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' ";
//		System.out.println("sql=="+sql);
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		String subcode="";
		for(Object item : sdocode){
			subcode+="'"+item+"',";		
		}
		if(subcode.endsWith(","))
		{
			subcode = subcode.substring(0,subcode.length() - 1);
		}
//		System.out.println("subcode= "+subcode);
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,cm.accno,cm.kno,cm.name,COALESCE(cm.tadesc,''),cm.meterno " + 
				"from meter_data.consumermaster cm,meter_data.master_main mm WHERE mm.sdocode = cm.sdocode and mm.accno=cm.accno" + 
				" AND cm.sdocode  in (" + subcode +") AND cm.accno in (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='Consumer' AND status=1 AND v_rule_id='"+ruleid+"')";

		if (!consumerCatgry.isEmpty()) {
			sqlqry+= " and cm.tadesc='"+consumerCatgry+"'";
         }
		if (!acno.isEmpty()) {
			sqlqry+= " and cm.accno='"+acno+"'";
        }
		if ( !kno.isEmpty()) {
			sqlqry+= " and cm.kno='"+kno+"'";
        }
		if (!mtrno.isEmpty()) {
			sqlqry+=  " and cm.meterno='"+mtrno+"'";
        }
		
		
		finalQury+=sqlqry+";";
		//System.err.println("Final query---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	@RequestMapping(value = "/getDTData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getDTData(HttpServletRequest request, Model model) {
		 System.err.println("hii DT");
		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle").trim();
		//String division = request.getParameter("division").trim();
		//String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String dtcode = request.getParameter("dtcode").trim();
		String crossdt = request.getParameter("crossdt").trim();
		String mtrno = request.getParameter("dtmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
	//	System.out.println("dtcode=="+dtcode +"  crossdt== "+crossdt+" mtrno== "+mtrno);
		String crdt="";
		if(crossdt.equals("true")) {
			crdt="1";
		}
		else {
			crdt="0";
		}

		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"'  and tp_towncode like '"+town+"'";
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		//System.out.println("hi=="+sdocode);
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
	//	System.out.println(subcode);
			
		String finalQury="";
		String sqlqry="select distinct on (dt.meterno)mm.circle,mm.division,mm.subdivision,CAST(dt.crossdt AS varchar),COALESCE(dt.dttpid,''),dt.meterno,mm.town_code,COALESCE(dt.dt_id,'') as dtid,dt.dtname,aa.tp_towncode,aa.town_ipds \r\n" + 
				"from meter_data.dtdetails dt ,meter_data.master_main mm,meter_data.amilocation  aa  \r\n" + 
				"where   mm.mtrno=dt.meterno and mm.town_code=aa.tp_towncode and CAST(dt.tp_town_code AS varchar) in (" + towncode +") " +
				"AND dt.dt_id not in (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='DT' and status=1 AND v_rule_id='"+ruleid+"')";

		     if (!dtcode.isEmpty()) { 
			  sqlqry+= " and dt.dttpid='"+dtcode+"'";
			  } if (!crdt.isEmpty()) {
				  sqlqry+= " and CAST(dt.crossdt AS varchar)='"+crdt+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and dt.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		System.err.println("Final DT queryfgtyy8977---sms:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	
	@RequestMapping(value = "/getAssignDTData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getAssignDTData(HttpServletRequest request, Model model) {

		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle").trim();
		//String division = request.getParameter("division").trim();
		//String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String dtcode = request.getParameter("dtcode").trim();
		String crossdt = request.getParameter("crossdt").trim();
		String mtrno = request.getParameter("dtmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
		//System.out.println("dtcode=="+dtcode +"  crossdt== "+crossdt+" mtrno== "+mtrno);
		String crdt="";
		if(crossdt.equals("true")) {
			crdt="1";
		}
		else {
			crdt="0";
		}

		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
//		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' and tp_towncode like '"+town+"'";
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"'  and tp_towncode like '"+town+"'";
        
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		//System.out.println("hi=="+sdocode);
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
		//System.out.println(subcode);
			
		String finalQury="";
		String sqlqry="select distinct on (dt.meterno) mm.circle,mm.division,mm.subdivision,CAST(dt.crossdt AS varchar),COALESCE(dt.dttpid,''),dt.meterno, mm.town_code,COALESCE(dt.dt_id,'') as dtid,dtname,aa.tp_towncode,aa.town_ipds\r\n" + 
				"from meter_data.dtdetails dt ,meter_data.master_main mm,meter_data.amilocation  aa   \r\n" + 
				"where  mm.mtrno=dt.meterno and  mm.town_code=aa.tp_towncode and CAST(dt.tp_town_code AS varchar) in (" + towncode +") " +
				"AND dt.dt_id in (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='DT' and status=1 AND v_rule_id='"+ruleid+"')";

		     if (!dtcode.isEmpty()) { 
			  sqlqry+= " and dt.dttpid='"+dtcode+"'";
			  } if (!crdt.isEmpty()) {
				  sqlqry+= " and CAST(dt.crossdt AS varchar)='"+crdt+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and dt.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		System.err.println("Final DT query125689---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	
	@RequestMapping(value = "/getUnassignFeederData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getUnassignFeederData(HttpServletRequest request, Model model) {

		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle").trim();
		//String division = request.getParameter("division").trim();
		//String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String feedercode = request.getParameter("feedercode").trim();
		String crossfeeder = request.getParameter("crossfeeder").trim();
		String mtrno = request.getParameter("fdrmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
        System.err.println(      "   VALU " +"                       "+crossfeeder);
		if(crossfeeder.equals("true")) {
			crossfeeder="1";
		}
		else {
			crossfeeder="0";
		}

//		String queryTail = "";
//		
//		if("ALL".equalsIgnoreCase(zone)) {
//			queryTail="";
//		} else if("ALL".equalsIgnoreCase(circle)) {
//			queryTail=" WHERE zone='"+zone+"' ";
//		} else if("ALL".equalsIgnoreCase(division)) {
//			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"'";
//		} else if("ALL".equalsIgnoreCase(subdivision)) {
//			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"'";
//		} else {
//			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"' AND subdivision = '"+subdivision+"' ";
//		}
//		String sql="SELECT distinct sdocode from meter_data.master_main"+queryTail;
		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
//		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' and tp_towncode like '"+town+"'";
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"'  and tp_towncode like '"+town+"'";
        
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
//		System.err.println("Final Feeder subcode---:" + towncode);
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,COALESCE(fd.feedername,'') as feedername,COALESCE(fd.tp_fdr_id,'')  as fdr_id,CAST(fd.crossfdr AS varchar),fd.meterno,mm.town_code,COALESCE(fd.fdr_id,'') as fdrid,aa.tp_towncode,aa.town_ipds\r\n" + 
				"from meter_data.feederdetails fd ,meter_data.master_main mm,meter_data.amilocation  aa   \r\n" + 
				"where  mm.sdocode = CAST(fd.officeid AS varchar) and mm.mtrno=fd.meterno and mm.town_code=aa.tp_towncode and CAST(fd.tp_town_code AS varchar) in (" + towncode +")" +
				"AND fd.fdr_id not in (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='Feeder' and status=1 AND v_rule_id='"+ruleid+"')";

		     if (!feedercode.isEmpty()) { 
			  sqlqry+= " and fd.tp_fdr_id='"+feedercode+"'";
			  } if (!crossfeeder.isEmpty()) {
				  sqlqry+= " and CAST(fd.crossfdr AS varchar)='"+crossfeeder+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= "and fd.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		System.err.println("Final Feeder query789456---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	@RequestMapping(value = "/getUnassignboundaryData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getUnassignboundaryData(HttpServletRequest request, Model model) {

		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle").trim();
		//String division = request.getParameter("division").trim();
		//String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String boundarycode = request.getParameter("boundarycode").trim();
		String boundarycrossfeeder = request.getParameter("crossboundaryfeeder").trim();
		String mtrno = request.getParameter("bdrmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
System.out.println("boundarycrossfeeder"+boundarycrossfeeder);
		if(boundarycrossfeeder.equalsIgnoreCase("true")) {
			boundarycrossfeeder="1";
		}
		else {
			boundarycrossfeeder="0";
		}

//		String queryTail = "";
//		
//		if("ALL".equalsIgnoreCase(zone)) {
//			queryTail="";
//		} else if("ALL".equalsIgnoreCase(circle)) {
//			queryTail=" WHERE zone='"+zone+"' ";
//		} else if("ALL".equalsIgnoreCase(division)) {
//			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"'";
//		} else if("ALL".equalsIgnoreCase(subdivision)) {
//			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"'";
//		} else {
//			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"' AND subdivision = '"+subdivision+"' ";
//		}
//		String sql="SELECT distinct sdocode from meter_data.master_main"+queryTail;
		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
//		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' and tp_towncode like '"+town+"'";
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"'  and tp_towncode like '"+town+"'";
        
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
		System.err.println("Final Feeder subcode---:" + towncode);
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,COALESCE(fd.feedername,'') as feedername,COALESCE(fd.tp_fdr_id,'')  as fdr_id,CAST(fd.crossfdr AS varchar),fd.meterno,mm.town_code,COALESCE(fd.fdr_id,'') as fdrid,aa.tp_towncode,aa.town_ipds\r\n" + 
				"from meter_data.feederdetails fd ,meter_data.master_main mm,meter_data.amilocation  aa   \r\n" + 
				"where  mm.sdocode = CAST(fd.officeid AS varchar) and mm.mtrno=fd.meterno and mm.town_code=aa.tp_towncode and CAST(fd.tp_town_code AS varchar) in (" + towncode +")" +
				"AND fd.fdr_id not in (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='Boundary' and status=1 AND v_rule_id='"+ruleid+"') and fd.crossfdr='"+boundarycrossfeeder+"' ";

		     if (!boundarycode.isEmpty()) { 
			  sqlqry+= " and fd.tp_fdr_id='"+boundarycode+"'";
			  } if (!boundarycode.isEmpty()) {
				  sqlqry+= " and CAST(fd.crossfdr AS varchar)='"+boundarycrossfeeder+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= "and fd.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		System.err.println("Final boundary query789456---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/getAssignFeederData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getAssignFeederData(HttpServletRequest request, Model model) {

		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone").trim();
		String circle = request.getParameter("circle").trim();
//		String division = request.getParameter("division").trim();
//		String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String feedercode = request.getParameter("feedercode").trim();
		String crossfeeder = request.getParameter("crossfeeder").trim();
		String mtrno = request.getParameter("fdrmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();

		if(crossfeeder.equals("true")) {
			crossfeeder="1";
		}
		else {
			crossfeeder="0";
		}

		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
//		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' and tp_towncode like '"+town+"' ";
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"'  and tp_towncode like '"+town+"'";
        
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
//		System.err.println("Final AssignFeeder subcode---:" + towncode);
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,COALESCE(fd.feedername,'') as feedername,COALESCE(fd.tp_fdr_id,'') as fdr_id,CAST(fd.crossfdr AS varchar),fd.meterno,mm.town_code,COALESCE(fd.fdr_id,'') as fdrid,aa.tp_towncode,aa.town_ipds\r\n" + 
				"from meter_data.feederdetails fd ,meter_data.master_main mm,meter_data.amilocation aa   \r\n" + 
				"where  mm.sdocode = CAST(fd.officeid AS varchar) and mm.mtrno=fd.meterno and mm.town_code=aa.tp_towncode and CAST(fd.tp_town_code AS varchar) in (" + towncode +")" +
				"AND fd.fdr_id  in (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='Feeder' and status=1 AND v_rule_id='"+ruleid+"')";

		     if (!feedercode.isEmpty()) { 
			  sqlqry+= " and fd.tp_fdr_id='"+feedercode+"'";
			  } if (!crossfeeder.isEmpty()) {
				  sqlqry+= " and CAST(fd.crossfdr AS varchar)='"+crossfeeder+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and fd.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		System.err.println("Final Assign Feeder query78963---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	@RequestMapping(value = "/getAssignBoundaryData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getAssignBoundaryData(HttpServletRequest request, Model model) {

		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone").trim();
		String circle = request.getParameter("circle").trim();
//		String division = request.getParameter("division").trim();
//		String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String boundarycode = request.getParameter("boundarycode").trim();
		String crossfeeder = request.getParameter("boundarycrossfeeder").trim();
		String mtrno = request.getParameter("bdrmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();

		if(crossfeeder.equals("true")) {
			crossfeeder="1";
		}
		else {
			crossfeeder="0";
		}

		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
//		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' and tp_towncode like '"+town+"' ";
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"'  and tp_towncode like '"+town+"'";
        
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
//		System.err.println("Final AssignFeeder subcode---:" + towncode);
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,COALESCE(fd.feedername,'') as feedername,COALESCE(fd.tp_fdr_id,'') as fdr_id,CAST(fd.crossfdr AS varchar),fd.meterno,mm.town_code,COALESCE(fd.fdr_id,'') as fdrid,aa.tp_towncode,aa.town_ipds\r\n" + 
				"from meter_data.feederdetails fd ,meter_data.master_main mm,meter_data.amilocation aa   \r\n" + 
				"where  mm.sdocode = CAST(fd.officeid AS varchar) and mm.mtrno=fd.meterno and mm.town_code=aa.tp_towncode and CAST(fd.tp_town_code AS varchar) in (" + towncode +")" +
				"AND fd.fdr_id  in (SELECT location_code FROM meter_data.ml_to_validation_rule_map WHERE location_type='Boundary' and status=1 AND v_rule_id='"+ruleid+"') and fd.crossfdr='"+crossfeeder+"' ";

		     if (!boundarycode.isEmpty()) { 
			  sqlqry+= " and fd.tp_fdr_id='"+boundarycode+"'";
			  } if (!crossfeeder.isEmpty()) {
				  sqlqry+= " and CAST(fd.crossfdr AS varchar)='"+crossfeeder+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and fd.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		//System.err.println("Final Assign Feeder query78963---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/assignValidationData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String assignValidationData(HttpServletRequest request, Model model) {
		model.addAttribute("results", "notDisplay");
		String ruleType = request.getParameter("ruleType").trim();
		String locationType = request.getParameter("locationType").trim();
		String locationCode = request.getParameter("locationCode").trim();
		String result="";
		 JSONObject data = new JSONObject();
		 HttpSession session = request.getSession(false);
          try {
			data = new JSONObject(locationCode);
			JSONArray ary=data.getJSONArray("locationCode");
			System.out.println(ary);
			for (int i = 0; i < ary.length(); i++) {
				AssignValidationRuleEntity v = assgvalservice.getAssignRuleId(ruleType, locationType, (String) ary.get(i));
				if(v != null) {
					v.setStatus(1);
					v.setUpdate_by(session.getAttribute("username").toString());
					v.setUpdate_date(new Timestamp(System.currentTimeMillis()));
					assgvalservice.update(v);
				}else {
					AssignValidationRuleEntity asgvrule=new AssignValidationRuleEntity();
					asgvrule.setMyKey(new KeyValidation(ruleType, locationType, (String) ary.get(i)));
					asgvrule.setStatus(1);
					asgvrule.setEntry_by(session.getAttribute("username").toString());
					asgvrule.setEntry_date(new Timestamp(System.currentTimeMillis()));
					assgvalservice.update(asgvrule);
				}
				
			}
			result="success";
          } catch (Exception e) {
        	  result="Some error occured.";
  			 e.printStackTrace();
  		}
          return result;
		
	}
	
	
	
	@RequestMapping(value = "/unassignValidationData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String unassignValidationData(HttpServletRequest request, Model model) {
		model.addAttribute("results", "notDisplay");
		//System.out.println("hii unasscon");
		String ruleType = request.getParameter("ruleType").trim();
		String locationType = request.getParameter("locationType").trim();
		String locationCode = request.getParameter("locationCode").trim();
		//System.out.println("ruleType= "+ruleType+" locationType= "+locationType+" locationCode = "+locationCode);
		String result="";
		 JSONObject data = new JSONObject();
		 HttpSession session = request.getSession(false);
          try {
			data = new JSONObject(locationCode);
			JSONArray ary=data.getJSONArray("locationCode");
			
			for (int i = 0; i < ary.length(); i++) {
				AssignValidationRuleEntity v = assgvalservice.getAssignRuleId(ruleType, locationType, (String) ary.get(i));
				if(v != null) {
					v.setStatus(0);
					v.setUpdate_by(session.getAttribute("username").toString());
					v.setUpdate_date(new Timestamp(System.currentTimeMillis()));
					assgvalservice.update(v);
				}

			}
			result="success";
          } catch (Exception e) {
        	 result="Some error occured.";
  			 e.printStackTrace();
  		}
      return result;
		
	}
	
	/*************************Estimation Implementation By Tarik**************************************/
	
	@RequestMapping(value = "/getEstConsumerData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getEstConsumerData(HttpServletRequest request, Model model) {
//		 System.err.println("hii");
		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("sdoCode").trim();
		String consumerCatgry = request.getParameter("consumerCatgry").trim();
		String acno = request.getParameter("acno").trim();
		String kno = request.getParameter("kno").trim();
		String mtrno = request.getParameter("mtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' ";
//        System.err.println("---query feeder sun station---:" + sql);
		
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
//		System.out.println("hi=="+sdocode);
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";		
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,cm.accno,cm.kno,cm.name,COALESCE(cm.tadesc,''),cm.meterno " + 
				"from meter_data.consumermaster cm,meter_data.master_main mm WHERE mm.sdocode = cm.sdocode and mm.accno=cm.accno" + 
				" AND CAST(fd.tp_town_code AS varchar)  in (" + towncode +") AND cm.accno not in (SELECT location_code FROM meter_data.ml_to_estimation_rule_map WHERE location_type='Consumer' and status=1 AND e_rule_id='"+ruleid+"')";
		if (!consumerCatgry.isEmpty()) {
			sqlqry+= " and cm.tadesc='"+consumerCatgry+"'";
         }
		if (!acno.isEmpty()) {
			sqlqry+= " and cm.accno='"+acno+"'";
        }
		if ( !kno.isEmpty()) {
			sqlqry+= " and cm.kno='"+kno+"'";
        }
		if (!mtrno.isEmpty()) {
			sqlqry+=  " and cm.meterno='"+mtrno+"'";
        }
		
		
		finalQury+=sqlqry+";";
	//	System.err.println("Final query---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	
	@RequestMapping(value = "/getEstAssignConsumerData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getEstAssignConsumerData(HttpServletRequest request, Model model) {
//		 System.err.println("hii");
		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone").trim();
		String circle = request.getParameter("circle").trim();
		String division = request.getParameter("division");
		String subdivision = request.getParameter("sdoCode");
		String consumerCatgry = request.getParameter("consumerCatgry").trim();
		String acno = request.getParameter("acno").trim();
		String kno = request.getParameter("kno").trim();
		String mtrno = request.getParameter("mtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' ";
//		System.out.println("sql=="+sql);
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";		
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
//		System.out.println("subcode= "+subcode);
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,cm.accno,cm.kno,cm.name,COALESCE(cm.tadesc,''),cm.meterno " + 
				"from meter_data.consumermaster cm,meter_data.master_main mm WHERE mm.sdocode = cm.sdocode and mm.accno=cm.accno" + 
				" AND cm.sdocode  in (" + towncode +") AND cm.accno in (SELECT location_code FROM meter_data.ml_to_estimation_rule_map WHERE location_type='Consumer' AND status=1 AND e_rule_id='"+ruleid+"')";

		if (!consumerCatgry.isEmpty()) {
			sqlqry+= " and cm.tadesc='"+consumerCatgry+"'";
         }
		if (!acno.isEmpty()) {
			sqlqry+= " and cm.accno='"+acno+"'";
        }
		if ( !kno.isEmpty()) {
			sqlqry+= " and cm.kno='"+kno+"'";
        }
		if (!mtrno.isEmpty()) {
			sqlqry+=  " and cm.mtrno='"+mtrno+"'";
        }
		
		
		finalQury+=sqlqry+";";
		//System.err.println("Final query---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	@RequestMapping(value = "/getEstDTData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getEstDTData(HttpServletRequest request, Model model) {
//		 System.err.println("hii DT");
		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone").trim();
		String circle = request.getParameter("circle").trim();
		String division = request.getParameter("division").trim();
		String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String dtcode = request.getParameter("dtcode").trim();
		String crossdt = request.getParameter("crossdt").trim();
		String mtrno = request.getParameter("dtmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
	//	System.out.println("dtcode=="+dtcode +"  crossdt== "+crossdt+" mtrno== "+mtrno);
		String crdt="";
		if(crossdt.equals("true")) {
			crdt="1";
		}
		else {
			crdt="0";
		}
		
		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' and tp_towncode like '"+town+"' ";

        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		//System.out.println("hi=="+sdocode);
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
	//	System.out.println(subcode);
			
		String finalQury="";
		String sqlqry="select distinct on (dt.meterno)mm.circle,mm.division,mm.subdivision,CAST(dt.crossdt AS varchar),COALESCE(dt.dttpid,''),dt.meterno,mm.town_code,COALESCE(dt.dt_id,'') as dtid\r\n" + 
				"from meter_data.dtdetails dt ,meter_data.master_main mm  \r\n" + 
				"where mm.mtrno=dt.meterno and  CAST(dt.tp_town_code AS varchar) in (" + towncode +") " +
				"AND dt.dt_id not in (SELECT location_code FROM meter_data.ml_to_estimation_rule_map WHERE location_type='DT' and status=1 AND e_rule_id='"+ruleid+"')";

		     if (!dtcode.isEmpty()) { 
			  sqlqry+= " and dt.dttpid='"+dtcode+"'";
			  } if (!crdt.isEmpty()) {
				  sqlqry+= " and CAST(dt.crossdt AS varchar)='"+crdt+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and dt.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		System.err.println("Final DT query125---sys:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	
	@RequestMapping(value = "/getEstAssignDTData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getEstAssignDTData(HttpServletRequest request, Model model) {

		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone").trim();
		String circle = request.getParameter("circle").trim();
		String division = request.getParameter("division").trim();
		String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String dtcode = request.getParameter("dtcode").trim();
		String crossdt = request.getParameter("crossdt").trim();
		String mtrno = request.getParameter("dtmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
		//System.out.println("dtcode=="+dtcode +"  crossdt== "+crossdt+" mtrno== "+mtrno);
		String crdt="";
		if(crossdt.equals("true")) {
			crdt="1";
		}
		else {
			crdt="0";
		}
		
		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' and tp_towncode like '"+town+"'";

        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		//System.out.println("hi=="+sdocode);
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
		//System.out.println(subcode);
			
		String finalQury="";
		String sqlqry="select distinct on (dt.meterno) mm.circle,mm.division,mm.subdivision,CAST(dt.crossdt AS varchar),COALESCE(dt.dttpid,''),dt.meterno,  mm.town_code,COALESCE(dt.dt_id,'') as dtid\r\n" + 
				"from meter_data.dtdetails dt ,meter_data.master_main mm  \r\n" + 
				"where  mm.mtrno=dt.meterno and CAST(dt.tp_town_code AS varchar) in (" + towncode +") " +
				"AND dt.dt_id in (SELECT location_code FROM meter_data.ml_to_estimation_rule_map WHERE location_type='DT' and status=1 AND e_rule_id='"+ruleid+"')";

		     if (!dtcode.isEmpty()) { 
			  sqlqry+= " and dt.dttpid='"+dtcode+"'";
			  } if (!crdt.isEmpty()) {
				  sqlqry+= " and CAST(dt.crossdt AS varchar)='"+crdt+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and dt.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		//System.err.println("Final DT query5879---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	
	@RequestMapping(value = "/getEstUnassignFeederData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getEstUnassignFeederData(HttpServletRequest request, Model model) {

		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone").trim();
		String circle = request.getParameter("circle").trim();
		String division = request.getParameter("division").trim();
		String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String feedercode = request.getParameter("feedercode").trim();
		String crossfeeder = request.getParameter("crossfeeder").trim();
		String mtrno = request.getParameter("fdrmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
        String locType="";
		if(crossfeeder.equals("true")) {
			crossfeeder="1";
			locType="Boundary";
		}
		else {
			crossfeeder="0";
			locType="Feeder";
		}

		
//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"'  and tp_towncode like '"+town+"' ";

        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,COALESCE(fd.feedername,'') as feedername,COALESCE(fd.tp_fdr_id,'') as fdr_id,CAST(fd.crossfdr AS varchar),fd.meterno,mm.town_code,COALESCE(fd.fdr_id,'') as fdrid \r\n" + 
				"from meter_data.feederdetails fd ,meter_data.master_main mm  \r\n" + 
				"where  mm.sdocode = CAST(fd.officeid AS varchar) and mm.mtrno=fd.meterno and CAST(fd.tp_town_code AS varchar) in (" + towncode +")" +
				"AND fd.fdr_id not in (SELECT location_code FROM meter_data.ml_to_estimation_rule_map WHERE location_type='"+locType+"' and status=1 AND e_rule_id='"+ruleid+"')";

		     if (!feedercode.isEmpty()) { 
			  sqlqry+= " and fd.tp_fdr_id='"+feedercode+"'";
			  } if (!crossfeeder.isEmpty()) {
				  sqlqry+= " and CAST(fd.crossfdr AS varchar)='"+crossfeeder+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and fd.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		//System.err.println("Final Feeder query---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	
	@RequestMapping(value = "/getEstAssignFeederData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getEstAssignFeederData(HttpServletRequest request, Model model) {

		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone").trim();
		String circle = request.getParameter("circle").trim();
		String division = request.getParameter("division").trim();
		String subdivision = request.getParameter("sdoCode").trim();
		String town = request.getParameter("town").trim();
		String feedercode = request.getParameter("feedercode").trim();
		String crossfeeder = request.getParameter("crossfeeder").trim();
		String mtrno = request.getParameter("fdrmtrno").trim();
		String ruleid = request.getParameter("ruleType").trim();
		//System.err.println(feedercode +"             "+ subdivision);
        String locType="";
		if(crossfeeder.equals("true")) {
			crossfeeder="1";
			locType="Boundary";
		}
		else {
			crossfeeder="0";
			locType="Feeder";
		}

//		String sql=getSubdivisionQuery(zone,circle,division,subdivision);
		String sql="SELECT distinct tp_towncode from meter_data.amilocation  WHERE zone LIKE '"+zone+"' AND circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"'  and tp_towncode like '"+town+"' ";
        List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		String towncode="";
		for(Object item : sdocode){
			towncode+="'"+item+"',";	
		}
		if(towncode.endsWith(","))
		{
			towncode = towncode.substring(0,towncode.length() - 1);
		}
		String finalQury="";
		String sqlqry="select mm.circle,mm.division,mm.subdivision,COALESCE(fd.feedername,'') as feedername,COALESCE(fd.tp_fdr_id,'') as fdr_id,CAST(fd.crossfdr AS varchar),fd.meterno,mm.town_code,COALESCE(fd.fdr_id,'') as fdrid\r\n" + 
				"from meter_data.feederdetails fd ,meter_data.master_main mm  \r\n" + 
				"where  mm.sdocode = CAST(fd.officeid AS varchar) and mm.mtrno=fd.meterno and  CAST(fd.tp_town_code AS varchar) in (" + towncode +")" +
				"AND fd.fdr_id  in (SELECT location_code FROM meter_data.ml_to_estimation_rule_map WHERE location_type='"+locType+"' and status=1 AND e_rule_id='"+ruleid+"')";

		     if (!feedercode.isEmpty()) { 
			  sqlqry+= " and fd.tp_fdr_id='"+feedercode+"'";
			  } if (!crossfeeder.isEmpty()) {
				  sqlqry+= " and CAST(fd.crossfdr AS varchar)='"+crossfeeder+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and fd.meterno='"+mtrno+"'";  
			  }
		 
		finalQury+=sqlqry+";";
		//System.err.println("Final Feeder query---:" + finalQury);
		List<?> list  = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}
	
	
	@RequestMapping(value = "/assignEstimationData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String assignEstimationData(HttpServletRequest request, Model model) {
		model.addAttribute("results", "notDisplay");
		String ruleType = request.getParameter("ruleType").trim();
		String locationType = request.getParameter("locationType").trim();
		String locationCode = request.getParameter("locationCode").trim();
		String result="";
		 JSONObject data = new JSONObject();
		 HttpSession session = request.getSession(false);
          try {
			data = new JSONObject(locationCode);
			JSONArray ary=data.getJSONArray("locationCode");
			
			for (int i = 0; i < ary.length(); i++) {
				AssignsEstimationRuleEntity v = assgestservice.getAssignRuleId(ruleType, locationType, (String) ary.get(i));
				if(v != null) {
					v.setStatus(1);
					v.setUpdate_by(session.getAttribute("username").toString());
					v.setUpdate_date(new Timestamp(System.currentTimeMillis()));
					assgestservice.update(v);
				}else {
					AssignsEstimationRuleEntity asgerule=new AssignsEstimationRuleEntity();
					asgerule.setMyKey(new EstKeyValidation(ruleType, locationType, (String) ary.get(i)));
					asgerule.setStatus(1);
					asgerule.setEntry_by(session.getAttribute("username").toString());
					asgerule.setEntry_date(new Timestamp(System.currentTimeMillis()));
					assgestservice.update(asgerule);
				}
				
			}
			result="success";
          } catch (Exception e) {
        	  result="Some error occured.";
  			 e.printStackTrace();
  		}
          return result;
		
	}
	
	
	
	@RequestMapping(value = "/unassignEstimationData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String unassignEstimationData(HttpServletRequest request, Model model) {
		model.addAttribute("results", "notDisplay");
		String ruleType = request.getParameter("ruleType").trim();
		String locationType = request.getParameter("locationType").trim();
		String locationCode = request.getParameter("locationCode").trim();
		//System.out.println("ruleType= "+ruleType+" locationType= "+locationType+" locationCode = "+locationCode);
		String result="";
		 JSONObject data = new JSONObject();
		 HttpSession session = request.getSession(false);
          try {
			data = new JSONObject(locationCode);
			JSONArray ary=data.getJSONArray("locationCode");
			
			for (int i = 0; i < ary.length(); i++) {
				AssignsEstimationRuleEntity v = assgestservice.getAssignRuleId(ruleType, locationType, (String) ary.get(i));
				if(v != null) {
					v.setStatus(0);
					v.setUpdate_by(session.getAttribute("username").toString());
					v.setUpdate_date(new Timestamp(System.currentTimeMillis()));
					assgestservice.update(v);
				}

			}
			result="success";
          } catch (Exception e) {
        	 result="Some error occured.";
  			 e.printStackTrace();
  		}
      return result;
		
	}
	
	public static String getSubdivisionQuery(String zone,String circle,String division,String subdivision) {
		
		String queryTail = "";
		
		if("ALL".equalsIgnoreCase(zone)) {
			queryTail="";
		} else if("ALL".equalsIgnoreCase(circle)) {
			queryTail=" WHERE zone='"+zone+"' ";
		} else if("ALL".equalsIgnoreCase(division)) {
			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"'";
		} else if("ALL".equalsIgnoreCase(subdivision)) {
			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"'";
		} else {
			queryTail=" WHERE zone='"+zone+"' AND circle = '"+circle+"' AND division= '"+division+"' AND subdivision = '"+subdivision+"' ";
		}
		String sql="SELECT distinct sdocode from meter_data.master_main"+queryTail;

	
		return sql;
		
		
	}
	
	
	
public static String getSubdivisionQuery(String zone,String circle,String town) {
		
	/*System.out.println("zone ------------ "+zone);
	System.out.println("circle ------------ "+circle);
	System.out.println("town ------------ "+town);*/
	String reg ="",cir="",tow="";
	
		//String queryTail = "";
		
		/*if("ALL".equalsIgnoreCase(circle)) {
			queryTail="";
		} else if("ALL".equalsIgnoreCase(division)) {
			queryTail=" WHERE circle = '"+circle+"'";
		} else if("ALL".equalsIgnoreCase(subdivision)) {
			queryTail=" WHERE circle = '"+circle+"' AND division= '"+division+"'";
		} else {
			queryTail=" WHERE circle = '"+circle+"' AND division= '"+division+"' AND subdivision = '"+subdivision+"' ";
		}*/
	
		if(zone.equalsIgnoreCase("ALL")) {
			reg="%";
		}else {
			reg=zone;
		}if(circle.equalsIgnoreCase("ALL")){
			cir="%";
		}else {
			cir=circle;
		}if(town.equalsIgnoreCase("ALL")) {
			tow="%";
		}else {
			tow=town;
		}
		String sql="SELECT distinct sitecode from meter_data.amilocation WHERE zone like '"+reg+"' AND circle like '"+cir+"' AND tp_towncode like '"+tow+"' ";
	System.out.println("sql" + sql);
		return sql;
		
		
	}

	@RequestMapping(value = "/dataestimationreport", method = { RequestMethod.GET, RequestMethod.POST })
	public String dataEstimationReport(HttpServletRequest request, Model model) {
		
		List<?> rules=new ArrayList<>();
		List<EstimationRuleEntity> vrEnt = addestservice.getEstimationActiveRule();
		/*String sql="select e_rule_id from meter_data.estimation_rule_mst ORDER BY e_rule_id";
		try {
			rules=entityManager.createNativeQuery(sql).getResultList();
			//List<EstimationRuleEntity> vrEnt = addestservice.getEstimationActiveRule();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}*/
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctAmiZone();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("rules", vrEnt);
		return "dataEstimationReport";
		
		
	}
	@RequestMapping(value="/getestimatedrules", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getEstimationRules(){
		List<?> rules=new ArrayList<>();
		String sql="select e_rule_id from meter_data.estimation_rule_mst ORDER BY e_rule_id";
		try {
			rules=entityManager.createNativeQuery(sql).getResultList();
			List<EstimationRuleEntity> vrEnt = addestservice.getEstimationActiveRule();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return rules;
	}
	@RequestMapping(value = "/getestimatedReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getEstimationReport(HttpServletRequest request, Model model) {
		List<?> list=new ArrayList<>();
		String zone=request.getParameter("zone");
		String circle=request.getParameter("circle");
		/*
		 * String division=request.getParameter("division"); String
		 * subdiv=request.getParameter("subdiv");
		 */
		String town=request.getParameter("town");
		String ruleId=request.getParameter("ruleId");
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		
		//System.out.println(circle+division+subdiv+ruleId+fromDate+toDate);
		if ("All".equalsIgnoreCase(zone)){
			zone="%";
		}
		if ("All".equalsIgnoreCase(circle)){
			circle="%";
		}
		if("All".equalsIgnoreCase(town)){
			town="%";
		}
		if("All".equalsIgnoreCase(ruleId)){
			ruleId="%";
		}
		
	String sql="select * from meter_data.estimation_process_rpt where\n" +
			" circle like '"+circle+"'  and town_code like '"+town+"' \n" +
			"AND rule_id like '"+ruleId+"' and to_char(est_date,'YYYY-MM-DD') BETWEEN '"+fromDate+"' and '"+toDate+"'";
	try {
		System.out.println("query = "+sql);
	list=entityManager.createNativeQuery(sql).getResultList();
		
	} catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	return list;

	}

	public  String getSubdivisionQueryFrFeeder(String zone, String circle, String town) {
		/*String queryTail = "";
		
		if("%".equalsIgnoreCase(circle)) {
			queryTail="";
		} else if("%".equalsIgnoreCase(division)) {
			queryTail=" WHERE circle like '"+circle+"'";
		} else if("%".equalsIgnoreCase(subdivision)) {
			queryTail=" WHERE circle like '"+circle+"' AND division like '"+division+"'";
		} else if("%".equalsIgnoreCase(town)) {
			queryTail=" WHERE circle like '"+circle+"' AND division like  '"+division+"' AND subdivision like '"+subdivision+"'";
		}
		else {
			queryTail=" WHERE circle like '"+circle+"' AND division like  '"+division+"' AND subdivision like '"+subdivision+"'  and tp_towncode like '"+town+"'";
	
		}
		String sql="SELECT distinct tp_towncode from meter_data.amilocation"+queryTail;*/
		String reg ="",cir="",tow="";

		
		if(zone.equalsIgnoreCase("ALL")) {
			reg="%";
		}else {
			reg=zone;
		}if(circle.equalsIgnoreCase("ALL")){
			cir="%";
		}else {
			cir=circle;
		}if(town.equalsIgnoreCase("ALL")) {
			tow="%";
		}else {
			tow=town;
		}
		String sql="SELECT distinct sitecode from meter_data.amilocation WHERE zone like '"+reg+"' AND circle like '"+cir+"' AND tp_towncode like '"+tow+"' ";
	System.out.println("sql" + sql);
		
		return sql;
	}

	
}
