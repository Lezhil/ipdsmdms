/**
 * 
 */
package com.bcits.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Tarik
 *
 */
@Controller
public class ConsumptionAnalysisController {

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	private VEEController veecon;

	@RequestMapping(value = "/getConsumerConsumpDetailsData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getConsumerConsumpDetailsData(HttpServletRequest request, Model model) {

		List<?> list = new ArrayList<>();
		try {

			model.addAttribute("results", "notDisplay");
			String circle = request.getParameter("circle");
			String division = request.getParameter("division");
			String subdivision = request.getParameter("sdoCode").trim();
			String consumerCatgry = request.getParameter("consumerCatgry").trim();
			String acno = request.getParameter("acno").trim();
			String kno = request.getParameter("kno").trim();
			String mtrno = request.getParameter("mtrno").trim();

			String queryTail = "";
			if ("ALL".equalsIgnoreCase(circle)) {
				queryTail = "";
			} else if ("ALL".equalsIgnoreCase(division)) {
				queryTail = " WHERE circle = '" + circle + "'";
			} else if ("ALL".equalsIgnoreCase(subdivision)) {
				queryTail = " WHERE  circle = '" + circle + "' AND division= '" + division + "'";
			} else {
				queryTail = " WHERE  circle = '" + circle + "' AND division= '" + division + "' AND subdivision = '"
						+ subdivision + "' ";
			}
		//	String sql = "SELECT distinct sdocode from meter_data.master_main" + queryTail;
			 //System.err.println("---query feeder sun station---:" + sql);
			String sql="SELECT distinct sitecode from meter_data.amilocation  WHERE circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' ";

			List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
			String subcode = "";
			for (Object item : sdocode) {
				subcode += "'" + item + "',";
			}
			if (subcode.endsWith(",")) {
				subcode = subcode.substring(0, subcode.length() - 1);
			}
			String finalQury = "";
			String sqlqry = "select mm.id,mm.circle,mm.division,mm.subdivision,cm.accno,cm.kno,cm.name,COALESCE(cm.tadesc,''),cm.meterno "
					+ "from meter_data.consumermaster cm,meter_data.master_main mm WHERE mm.sdocode = cm.sdocode and mm.accno=cm.accno"
					+ " AND cm.sdocode  in (" + subcode + ")";
			if (!consumerCatgry.isEmpty()) {
				sqlqry += " and cm.tadesc='" + consumerCatgry + "'";
			}
			if (!acno.isEmpty()) {
				sqlqry += " and cm.accno='" + acno + "'";
			}
			if (!kno.isEmpty()) {
				sqlqry += " and cm.kno='" + kno + "'";
			}
			if (!mtrno.isEmpty()) {
				sqlqry += " and cm.meterno='" + mtrno + "'";
			}

			finalQury += sqlqry + ";";
			//System.err.println("Final query of getConsumerConsumpDetailsData()---:" + finalQury);
			list = entityManager.createNativeQuery(finalQury).getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}

	@RequestMapping(value = "/getDTConsumpDetailsData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getDTConsumpDetailsData(HttpServletRequest request) {
		List<?> list = new ArrayList<>();
		try {
			//String zone = "All";
			String crdt = "";
			String zone = request.getParameter("region").trim();
			String circle = request.getParameter("circle").trim();
			//String division = request.getParameter("division").trim();
			//String subdivision = request.getParameter("sdoCode").trim();
			String town = request.getParameter("town").trim();
			String dtcode = request.getParameter("dtcode").trim();
			String crossdt = request.getParameter("crossdt").trim();
			//String mtrno = request.getParameter("dtmtrno").trim();
			// String divQry=veecon.getSubdivisionQuery(zone,circle,division,subdivision);
			
			String zon="",cir="";
			
			if(zone.equalsIgnoreCase("ALL")) {
				zon="%";
			}else {
				zon=zone;
			}
			if(circle.equalsIgnoreCase("ALL")) {
				cir="%";
			}else {
				cir=circle;
			}

			String queryTail = "";
			if ("ALL".equalsIgnoreCase(circle)) {
				queryTail = "";
			} 
			/*
			 * else if ("ALL".equalsIgnoreCase(division)) { queryTail = " WHERE circle = '"
			 * + circle + "'"; }
			 */ 
			/*
			 * else if ("ALL".equalsIgnoreCase(subdivision)) { queryTail =
			 * " WHERE  circle = '" + circle + "' AND division= '" + division + "'"; }
			 */ 
			else {
				queryTail = " WHERE  circle = '" + circle + "' ";
			}
			//String sql = "SELECT distinct sdocode from meter_data.master_main" + queryTail;
			String sql="SELECT distinct sitecode from meter_data.amilocation  WHERE circle LIKE  '"+circle+"' AND tp_towncode like '"+town+"' ";

			List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
			//System.out.println("hi divQry==" + sql);
			String subcode = "";
			for (Object item : sdocode) {
				if (item != null) {
					subcode += "'" + item + "',";
				}
			}
			if (subcode.endsWith(",")) {
				subcode = subcode.substring(0, subcode.length() - 1);
			}
			// System.out.println(subcode);

			if (crossdt.equals("true")) {
				crdt = "1";
			} else {
				crdt = "0";
			}
			String finalQury = "";
//			String sqlqry = "select mm.circle,mm.division,mm.subdivision,CAST(cm.crossdt AS varchar),COALESCE(cm.dttpid,''),\n"
//					+ "cm.dtcapacity,cm.meterno,cm.dtname from meter_data.dtdetails cm,meter_data.master_main mm \n"
//					+ "WHERE cast(mm.sdocode as INT) = cm.officeid and cm.meterno=mm.mtrno and\n"
//					+ "cm.officeid  in (" + subcode + ")";
			
			String sqlqry="select mm.circle,mm.division,mm.subdivision,\n" +
					"CAST(d.crossdt AS varchar),COALESCE(d.dttpid,''),string_agg(distinct CAST(d.dtcapacity as varchar), ',') as dtcapacity,string_agg(d.meterno, ',') as meterno,string_agg(distinct d.dtname, ',') as dtname\n" +
					"from meter_data.dtdetails d,meter_data.master_main mm \n" +
					"WHERE cast(mm.sdocode as INT) = d.officeid and d.meterno=mm.mtrno and d.officeid  in (" + subcode + ") and mm.zone like '"+zon+"' and mm.circle like '"+cir+"' \n" +
					" ";

			if (!dtcode.isEmpty()) {
				sqlqry += " and d.dttpid='" + dtcode + "'";
			}
			if (!crdt.isEmpty()) {
				sqlqry += " and CAST(d.crossdt AS varchar)='" + crdt + "'";
			}
//			if (!mtrno.isEmpty()) {
//				sqlqry += " and cm.meterno='" + mtrno + "'";
//			}
			finalQury += sqlqry + " GROUP BY mm.circle,mm.division,mm.subdivision,d.dttpid,d.crossdt";
			System.err.println("Final getDTConsumpDetailsData query---:" + finalQury);
			list = entityManager.createNativeQuery(finalQury).getResultList();
			//System.out.println("final qry----"+finalQury);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}

	@RequestMapping(value = "/getFeederConsumpDetailsData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getFeederConsumpDetailsData(HttpServletRequest request) {
		List<?> list = new ArrayList<>();

		try {
			//String zone = "All";
			String zone = request.getParameter("region").trim();
			String crfdr = "";
			String circle = request.getParameter("circle").trim();
			//String division = request.getParameter("division").trim();
			//String subdivision = request.getParameter("sdoCode").trim();
			String town = request.getParameter("town").trim();
			String fdrcode = request.getParameter("fdrcode").trim();
			String crossfdr = request.getParameter("crossfdr").trim();
			String mtrno = request.getParameter("fdrmtrno").trim();

			String queryTail = "";
			if ("ALL".equalsIgnoreCase(circle)) {
				queryTail = "";
			} 
			/*
			 * else if ("ALL".equalsIgnoreCase(division)) { queryTail = " WHERE circle = '"
			 * + circle + "'"; }
			 */
			/*
			 * else if ("ALL".equalsIgnoreCase(subdivision)) { queryTail =
			 * " WHERE  circle = '" + circle + "' AND division= '" + division + "'"; }
			 */
			else {
				queryTail = " WHERE  circle = '" + circle + "' ";
			}
			//String sql = "SELECT distinct sdocode from meter_data.master_main" + queryTail;
			String sql="SELECT distinct sitecode from meter_data.amilocation  WHERE circle LIKE  '"+circle+"' and tp_towncode like '"+town+"'";

			// String divQry=veecon.getSubdivisionQuery(zone,circle,division,subdivision);
			//System.out.println(circle + "div-" + division + "sub--" + subdivision + "dtcode==" + fdrcode + "crodd=="
			//		+ crossfdr + "mtr=" + mtrno + "sql==" + sql);

			List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
			//System.out.println("hi==" + sdocode);
			String subcode = "";
			for (Object item : sdocode) {
				if (item != null) {
					subcode += "'" + item + "',";
				}
			}
			if (subcode.endsWith(",")) {
				subcode = subcode.substring(0, subcode.length() - 1);
			}
			//System.out.println(subcode);

			if (crossfdr.equals("true")) {
				crfdr = "1";
			} else {
				crfdr = "0";
			}
			String finalQury = "";
			String sqlqry = "select distinct  mm.circle,mm.division,mm.subdivision,CAST(cm.crossfdr AS varchar),COALESCE(cm.tp_fdr_id,''),\n"
					+ "cm.meterno,cm.feedername  from meter_data.feederdetails cm,meter_data.master_main mm \n"
					+ "WHERE cast(mm.sdocode as INT) = cm.officeid and cm.meterno=mm.mtrno and\n"
					+ "cm.officeid  in (" + subcode + ") and mm.zone like '"+zone+"' and circle like '"+circle+"'";

			if (!fdrcode.isEmpty()) {
				sqlqry += " and cm.tp_fdr_id='" + fdrcode + "'";
			}
			if (!crfdr.isEmpty()) {
				sqlqry += " and CAST(cm.crossfdr AS varchar)='" + crfdr + "'";
			}
			if (!mtrno.isEmpty()) {
				sqlqry += " and cm.meterno='" + mtrno + "'";
			}
			finalQury += sqlqry + ";";
			System.err.println("Final getFeederConsumpDetailsData query---:" + finalQury);
			list = entityManager.createNativeQuery(finalQury).getResultList();
			//System.err.println("Final getFeederConsumpDetailsData query---:" + finalQury + "\n===" + list.size());

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}

	}

	@RequestMapping(value = "/viewDayWiseConsmpData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> viewDayWiseConsmpData(HttpServletRequest request, ModelMap model) {
		// System.out.println("inside------");

		List<Map<String, Object>> alist = new ArrayList<Map<String, Object>>();
		try {
			String crfdr = "";
			String meterNo = request.getParameter("meterNo").trim();
			String reportDate = request.getParameter("reportDate").trim();
			String locationType = request.getParameter("locationType").trim();
			String crossfdr = request.getParameter("crossfdr").trim();
			if (crossfdr.equals("true")) {
				crfdr = "1";
			} else {
				crfdr = "0";
			}
			System.out.println("meterNo==" + meterNo + "  locationType=" + locationType);
			String interval=null;
			if(locationType.equalsIgnoreCase("DT")) {
				interval="30 min";
				String[] dtTpCode = meterNo.split(",");
				for (int i = 0; i < dtTpCode.length; i++) {

					String sqlqry = "select a.dates,COALESCE(b.kwh,'0.000') as KWH from (SELECT dates FROM generate_series(CAST('"
							+ reportDate + "' as TIMESTAMP), CAST(date('" + reportDate
							+ "')+ integer '1' as TIMESTAMP),  interval '"+interval+"') AS dates where date(dates)='" + reportDate
							+ "') a LEFT JOIN\r\n"
							+ "					(select kwh,yearmonth from meter_data.load_survey_dt \r\n"
							+ "					where dttpid='" + dtTpCode[i] + "' and date(yearmonth)='" + reportDate
							+ "' \r\n" + "					order by yearmonth)b ON a.dates=b.yearmonth ORDER BY a.dates asc ";
	
					List<?> list = entityManager.createNativeQuery(sqlqry).getResultList();
//					System.err.println("Final getFeederConsumpDetailsData query---:" + sqlqry + "\n===" + list.size());
					Map<String, Object> jmp = new HashMap<>();
					jmp.put("meterno", dtTpCode[i]);
					jmp.put("data", list);
					alist.add(jmp);
	
				}
				
			}
			if(locationType.equalsIgnoreCase("Feeder") && crfdr.equalsIgnoreCase("0")) {
				interval="30 min";
				String[] meterno = meterNo.split(",");
				//System.out.println(meterno.length);

				for (int i = 0; i < meterno.length; i++) {
//						String sqlqry="select kwh,read_time from meter_data.load_survey \r\n" + 
//								"where meter_number ='"+meterno[i]+"' and date(read_time)='"+reportDate+"' \r\n" + 
//								"order by read_time desc";

					String sqlqry = "select a.dates,COALESCE(b.kwh,'0.000') as KWH from (SELECT dates FROM generate_series(CAST('"
							+ reportDate + "' as TIMESTAMP), CAST(date('" + reportDate
							+ "')+ integer '1' as TIMESTAMP),  interval '"+interval+"') AS dates where date(dates)='" + reportDate
							+ "') a LEFT JOIN\r\n"
							+ "					(select kwh,read_time from meter_data.load_survey \r\n"
							+ "					where meter_number='" + meterno[i] + "' and date(read_time)='" + reportDate
							+ "' \r\n" + "					order by read_time)b ON a.dates=b.read_time ORDER BY a.dates asc";

					List<?> list = entityManager.createNativeQuery(sqlqry).getResultList();
					Map<String, Object> jmp = new HashMap<>();
					jmp.put("meterno", meterno[i]);
					jmp.put("data", list);
					alist.add(jmp);

				}
			}
			if(locationType.equalsIgnoreCase("Feeder") && crfdr.equalsIgnoreCase("1")) {
				interval="15 min";
				String[] meterno = meterNo.split(",");
				//System.out.println(meterno.length);

				for (int i = 0; i < meterno.length; i++) {
//						String sqlqry="select kwh,read_time from meter_data.load_survey \r\n" + 
//								"where meter_number ='"+meterno[i]+"' and date(read_time)='"+reportDate+"' \r\n" + 
//								"order by read_time desc";

					String sqlqry = "select a.dates,COALESCE(b.kwh,'0.000') as KWH from (SELECT dates FROM generate_series(CAST('"
							+ reportDate + "' as TIMESTAMP), CAST(date('" + reportDate
							+ "')+ integer '1' as TIMESTAMP),  interval '"+interval+"') AS dates where date(dates)='" + reportDate
							+ "') a LEFT JOIN\r\n"
							+ "					(select kwh,read_time from meter_data.load_survey \r\n"
							+ "					where meter_number='" + meterno[i] + "' and date(read_time)='" + reportDate
							+ "' \r\n" + "					order by read_time)b ON a.dates=b.read_time ORDER BY a.dates asc";

					List<?> list = entityManager.createNativeQuery(sqlqry).getResultList();
//					System.err.println("Final getFeederConsumpDetailsData query---:" + sqlqry + "\n===" + list.size());

					Map<String, Object> jmp = new HashMap<>();
					jmp.put("meterno", meterno[i]);
					jmp.put("data", list);
					alist.add(jmp);

				}
			}
			

			//System.out.println("alist==" + alist);

			return alist;

		} catch (Exception e) {
			e.printStackTrace();
			return alist;
		}

	}

	@RequestMapping(value = "/viewDailyWiseConsmpData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> viewDailyWiseConsmpData(HttpServletRequest request, ModelMap model) {
		// System.out.println("inside------");

		List<Map<String, Object>> alist = new ArrayList<Map<String, Object>>();
		try {

			String meterNo = request.getParameter("meterNo").trim();
			String reportFromDate = request.getParameter("reportFromDate").trim();
			String reportToDate = request.getParameter("reportToDate").trim();
			String locationType = request.getParameter("locationType").trim();
			//System.out.println("meterNo==" + meterNo + "  reportDate=" + reportToDate);
			
			if(locationType.equalsIgnoreCase("DT")) {
				
				String[] dtTpCode = meterNo.split(",");
				for (int i = 0; i < dtTpCode.length; i++) {

					String sqlqry="select date(a.dates),COALESCE(b.kwh,'0.000') as KWH from \r\n" + 
							"(SELECT dates FROM generate_series(CAST('" + reportFromDate + "' as TIMESTAMP),\r\n" + 
							"CAST(date('" + reportToDate + "')+ integer '1' as TIMESTAMP),  interval '1 Day') AS dates where date(dates)<='" + reportToDate + "') a LEFT JOIN\r\n" + 
							"(select sum(kwh) as kwh,date(yearmonth) as yearmonth  from meter_data.load_survey_dt \r\n" + 
							"where dttpid='" + dtTpCode[i] + "' and date(yearmonth) BETWEEN '" + reportFromDate + "' and  '" + reportToDate + "' GROUP BY date(yearmonth)\r\n" + 
							"order by date(yearmonth))b \r\n" + 
							"ON a.dates=b.yearmonth ";
					List<?> list = entityManager.createNativeQuery(sqlqry).getResultList();
					System.err.println("Final getFeederConsumpDetailsData query---:" + sqlqry + "\n===" + list.size());
					Map<String, Object> jmp = new HashMap<>();
					jmp.put("meterno", dtTpCode[i]);
					jmp.put("data", list);
					alist.add(jmp);
	
				}
				
				
			}else {
				String[] meterno = meterNo.split(",");
				//System.out.println(meterno.length);

				for (int i = 0; i < meterno.length; i++) {
					String sqlqry = "select date(a.dates) as date,COALESCE(b.kwh_imp,'0.000') as KWH from\r\n"
							+ "(SELECT dates FROM generate_series(CAST('" + reportFromDate + "' as TIMESTAMP), CAST('"
							+ reportToDate
							+ "' as TIMESTAMP),  interval '1 Day') AS dates ) a LEFT JOIN (select * from meter_data.daily_consumption \r\n"
							+ "where mtrno ='" + meterno[i] + "' and date BETWEEN '" + reportFromDate + "' and  '"
							+ reportToDate + "'\r\n" + "order by date)b ON a.dates=b.date ";

					List<?> list = entityManager.createNativeQuery(sqlqry).getResultList();
					System.err.println("Final getFeederConsumpDetailsData query---:" + sqlqry + "\n===" + list.size());

					Map<String, Object> jmp = new HashMap<>();
					jmp.put("meterno", meterno[i]);
					jmp.put("data", list);
					alist.add(jmp);

				}
			}
			//System.out.println("alist==" + alist);

			return alist;

		} catch (Exception e) {
			e.printStackTrace();
			return alist;
		}

	}

	
	@RequestMapping(value = "/viewWeeklyWiseConsmpData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> viewWeeklyWiseConsmpData(HttpServletRequest request, ModelMap model) {
		List<Map<String, Object>> alist = new ArrayList<Map<String, Object>>();
		try {

			String meterNo = request.getParameter("meterNo").trim();
			String fmonth = request.getParameter("fwmonth").trim();
			String tmonth = request.getParameter("twmonth").trim();
			String WeklyFWId[] = request.getParameter("WeklyFWId").trim().split("-");
			String WeklyTFWId[] = request.getParameter("WeklyTFWId").trim().split("-");
			
			String WeklyFStartDate=WeklyFWId[0];
			String WeklyFEndDate=WeklyFWId[1];
			String WeklyTStartDate=WeklyTFWId[0];
			String WeklyTEndDate=WeklyTFWId[1];

	
			if(WeklyFStartDate.length()==1) {
				WeklyFStartDate=0+WeklyFStartDate;
			}
			if(WeklyTStartDate.length()==1) {
				WeklyTStartDate=0+WeklyTStartDate;
				
			}
			if(WeklyFEndDate.length()==1) {
				WeklyFEndDate=0+WeklyFEndDate;
			}
			if(WeklyTEndDate.length()==1) {
				WeklyTEndDate=0+WeklyTEndDate;
				
			}
			String StartDate=fmonth+WeklyFStartDate;
			String EndDate=tmonth+WeklyTEndDate;
			
			
			//System.out.println("meterNo==" + meterNo + "  fmonth=" + fmonth+ "  WeklyFStartDate=" + WeklyFStartDate+ " WeklyFEndDate=" + WeklyFEndDate+ " tmonth=" + tmonth+ " WeklyTStartDate=" + WeklyTStartDate+ " WeklyTEndDate=" + WeklyTEndDate);
			String[] meterno = meterNo.split(",");
			//System.out.println("StartDate= "+StartDate+" EndDate= "+EndDate);

			for (int i = 0; i < meterno.length; i++) {
				String sqlqry = "select date(a.dates) as date,COALESCE(b.kwh_imp,'0.000') as KWH from\r\n" + 
						"(SELECT dates FROM generate_series(CAST('" + StartDate + "' as TIMESTAMP), CAST('" + EndDate + "' as TIMESTAMP),  interval '1 Day') AS dates ) a LEFT JOIN (select * from meter_data.daily_consumption \r\n" + 
						"where mtrno ='" + meterno[i] + "' and date BETWEEN '" + StartDate + "' and  '" + EndDate + "'\r\n" + 
						"order by date)b ON a.dates=b.date ";

				List<?> list = entityManager.createNativeQuery(sqlqry).getResultList();
				//System.err.println("Final viewWeeklyWiseConsmpData query---:" + sqlqry + "\n===" + list.size());

				Map<String, Object> jmp = new HashMap<>();
				jmp.put("meterno", meterno[i]);
				jmp.put("data", list);
				alist.add(jmp);

			}
			//System.out.println("alist==" + alist);

			return alist;

		} catch (Exception e) {
			e.printStackTrace();
			return alist;
		}

	}
	
	
	@RequestMapping(value = "/viewMonthlyWiseConsmpData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> viewMonthlyWiseConsmpData(HttpServletRequest request, ModelMap model) {
		// System.out.println("inside------");

		List<Map<String, Object>> alist = new ArrayList<Map<String, Object>>();
		try {

			String meterNo = request.getParameter("meterNo").trim();
			String fmonth = request.getParameter("fmonth").trim();
			String tmonth = request.getParameter("tmonth").trim();
			String locationType = request.getParameter("locationType").trim();
//			System.out.println("meterNo==" + meterNo + "  tmonth=" + tmonth);
			
			if(locationType.equalsIgnoreCase("DT")) {
				
				String[] dtTpCode = meterNo.split(",");
				for (int i = 0; i < dtTpCode.length; i++) {

//					String sqlqry="select a.\"day\" as billmonth,COALESCE(b.kwh_imp,'0.00') as kwh_imp from (\r\n" + 
//							"SELECT to_char(generate_series(to_date('" + fmonth + "','YYYYMM'), to_date('" + tmonth + "','YYYYMM'), '1 month'),'YYYYMM') AS day )a \r\n" + 
//							"LEFT JOIN\r\n" + 
//							"(select CAST(billmonth as TEXT) ,kwh_imp from meter_data.monthly_consumption \r\n" + 
//							"where mtrno ='"+ dtTpCode[i] + "' and billmonth BETWEEN '" + fmonth + "' and  '" + tmonth + "'\r\n" + 
//							"order by billmonth) b on b.billmonth=a.day ";
					String sqlqry="select a.\"day\" as billmonth,COALESCE(b.kwh,'0.00') as kwh from (\n" +
							"SELECT to_char(generate_series(to_date('" + fmonth + "','YYYYMM'), to_date('" + tmonth + "','YYYYMM'), '1 month'),'YYYYMM') AS day \n" +
							")a LEFT JOIN(\n" +
							"select dttpid,to_char(yearmonth,'yyyyMM') as yearmonth,sum(COALESCE(kwh,'0.00')) as kwh\n" +
							"from meter_data.load_survey_dt WHERE dttpid ='"+ dtTpCode[i] + "' and to_char(yearmonth,'yyyyMM') \n" +
							"BETWEEN '" + fmonth + "' and  '" + tmonth + "'  GROUP BY dttpid,to_char(yearmonth,'yyyyMM')\n" +
							") b on b.yearmonth=a.day";
					
					List<?> list = entityManager.createNativeQuery(sqlqry).getResultList();
//					System.err.println("Final viewMonthlyWiseConsmpData query---:" + sqlqry + "\n===" + list.size());

					Map<String, Object> jmp = new HashMap<>();
					jmp.put("meterno", dtTpCode[i]);
					jmp.put("data", list);
					alist.add(jmp);

				}
				
			}else {
				String[] meterno = meterNo.split(",");
//				System.out.println(meterno.length);

				for (int i = 0; i < meterno.length; i++) {
//					String sqlqry = "select billmonth,kwh_imp from meter_data.monthly_consumption \r\n" + "where mtrno ='"
//							+ meterno[i] + "' and billmonth BETWEEN '" + fmonth + "' and  '" + tmonth + "'\r\n"
//							+ "order by billmonth";
					String sqlqry="select a.\"day\" as billmonth,COALESCE(b.kwh_imp,'0.00') as kwh_imp from (\r\n" + 
							"SELECT to_char(generate_series(to_date('" + fmonth + "','YYYYMM'), to_date('" + tmonth + "','YYYYMM'), '1 month'),'YYYYMM') AS day )a \r\n" + 
							"LEFT JOIN\r\n" + 
							"(select CAST(billmonth as TEXT) ,kwh_imp from meter_data.monthly_consumption \r\n" + 
							"where mtrno ='"+ meterno[i] + "' and billmonth BETWEEN '" + fmonth + "' and  '" + tmonth + "'\r\n" + 
							"order by billmonth) b on b.billmonth=a.day ";
//					System.out.println("sqlqry=="+sqlqry);

					List<?> list = entityManager.createNativeQuery(sqlqry).getResultList();
//					System.err.println("Final viewMonthlyWiseConsmpData query---:" + sqlqry + "\n===" + list.size());

					Map<String, Object> jmp = new HashMap<>();
					jmp.put("meterno", meterno[i]);
					jmp.put("data", list);
					alist.add(jmp);

				}
				
			}
		
//			System.out.println("alist==" + alist);

			return alist;

		} catch (Exception e) {
			e.printStackTrace();
			return alist;
		}

	}

}
