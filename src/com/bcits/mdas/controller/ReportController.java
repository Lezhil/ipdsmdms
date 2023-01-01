package com.bcits.mdas.controller;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bcits.entity.LoadAvailabilityReportEntity;
import com.bcits.entity.LoadAvailabilityReportEntity.KeyLoadAvl;
import com.bcits.entity.NPPDataEntity;
import com.bcits.entity.NPPDataEntity.KeyNPPData;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.NonCommunicationReason;
import com.bcits.mdas.entity.NonCommunicationReason.KeyNonCommunication;
import com.bcits.mdas.entity.SaidiSaifiEntity;
import com.bcits.mdas.entity.SaidiSaifiEntity.KeySaidi;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.NonCommunicationReasonService;
import com.bcits.mdas.service.SaidiSaifiService;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.DTHealthService;
import com.bcits.service.FeederHealthService;
import com.bcits.service.LoadAvailabilityRptService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.NPPDataService;
import com.bcits.service.PfcService;
import com.bcits.service.ReportService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.icu.text.DecimalFormat;


@Controller
public class ReportController {

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	private FeederMasterService feederService;

	@Autowired
	private MeterMasterService meterMasterService;

	@Autowired
	private MasterMainService masterMainService;
	@Autowired
	private ReportService reportService;

	@Autowired
	private NPPDataService nppDataService;
	@Autowired
	private PfcService pfcservice;

	@Autowired
	private NonCommunicationReasonService nonCommunicationReasonService;

	@Autowired
	private AmrLoadService amrLoadService;

	@Autowired
	private ConsumerMasterService consumerMasterService;
	@Autowired
	private FeederHealthService feederHealthService;
	@Autowired
	private AmiLocationService amiLocationService;
	@Autowired
	private FeederDetailsService feederdetailsservice;
	@Autowired
	private FeederMasterService feederMasterService;
	@Autowired
	private DTHealthService dtHealthService;
	
	@Autowired
	private SaidiSaifiService saidiSaifiService;

	@RequestMapping(value = "/sanloadVSmd", method = { RequestMethod.POST, RequestMethod.GET })
	public String showMdiExceedRpt(HttpServletRequest request, ModelMap model) {
		model.addAttribute("subdivlist", masterMainService.getAllSubdivisions());
		 List<?> zonelist=feederdetailsservice.getDistinctZone();
		//List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zonelist", zonelist);
		return "sanloadVSmd";
	}

	@RequestMapping(value = "/getSanloadVSmdReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getSanloadVSmdReport(HttpServletRequest request) {
		// String circle=request.getParameter("circle");
		// String division=request.getParameter("division");
		// String subdivi=request.getParameter("sdoCode");
		String subdiv = request.getParameter("subdiv");
		String monthid = request.getParameter("monthid");
		String qryLast = "";
		/*
		 * if(circle.equalsIgnoreCase("All")) { circle="%"; }
		 * if(division.equalsIgnoreCase("All")) { division="%"; }
		 * if(subdivi.equalsIgnoreCase("All")) { subdivi="%"; }
		 */

		if (!"ALL".equalsIgnoreCase(subdiv)) {
			qryLast = " AND subdivision= '" + subdiv + "' ";
		}
		String sql = "select b.time_stamp,c.mtrno,c.kno,c.customer_name,c.accno,c.subdivision,c.sanload,(b.md_kw*c.mf)as md,b.date_md_kw from (SELECT m.accno,m.mtrno,m.kno, \n"
				+ "m.customer_name,m.subdivision, (case WHEN m.kworhp='KW' THEN CAST(m.sanload as NUMERIC) ELSE CAST(m.sanload as NUMERIC)*1.3404825737 END) as sanload,cm.mf FROM meter_data.master_main m,meter_data.consumermaster cm where m.mtrno=cm.meterno and cm.consumerstatus like 'R')c, \n"
				+ "(SELECT i.time_stamp,i.meter_number,i.read_time,round(max(i.md_kw)/1000,3) as md_kw, i.date_md_kw FROM meter_data.amiinstantaneous i,\n"
				+ "(SELECT meter_number, max(read_time) as maxrdate FROM meter_data.amiinstantaneous WHERE to_char(time_stamp,'yyyy-MM')='"
				+ monthid + "' GROUP BY meter_number)a \n"
				+ "WHERE i.meter_number=a.meter_number AND i.read_time=a.maxrdate GROUP BY i.time_stamp,i.meter_number,i.read_time,i.date_md_kw)b\n"
				+ "WHERE c.mtrno=b.meter_number  " + qryLast + " and c.sanload<(b.md_kw*c.mf) ORDER BY subdivision";
		return entityManager.createNativeQuery(sql).getResultList();
	}

	@RequestMapping(value = "/meterCommunication", method = { RequestMethod.POST, RequestMethod.GET })
	public String meterCommunication(HttpServletRequest request, ModelMap model) {
		model.addAttribute("subdivlist", masterMainService.getAllSubdivisions());
		return "meterCommunicationReport";
	}

	@RequestMapping(value = "/getMeterCommunicationReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getMeterCommunicationReport(HttpServletRequest request) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("subdiv");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String qryLast = "";
		if (!"ALL".equalsIgnoreCase(subdiv)) {
			qryLast = " AND subdivision= '" + subdiv + "' ";
		}

		String sql = "SELECT mtrno, customer_name,accno,kno,subdivision,division, circle, ( SELECT \"max\"(last_communication) FROM meter_data.modem_communication WHERE meter_number = mtrno) as lcom\n"
				+ "FROM meter_data.master_main WHERE mtrno not in \n" + "(\n"
				+ "SELECT meter_number FROM meter_data.modem_communication WHERE \"date\" BETWEEN '" + fromDate
				+ "' AND '" + toDate + "' GROUP BY meter_number \n" + ") " + qryLast;

		return entityManager.createNativeQuery(sql).getResultList();
	}

	/*@RequestMapping(value = "/nonReadMetersReport", method = { RequestMethod.POST, RequestMethod.GET })
	public String nonReadMetersReport(ModelMap model, HttpServletRequest request) {
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);
		return "nonReadMetersReport";
	}*/

	@RequestMapping(value = "/nonReadMetersReportAnalyticsData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<Object[]> nonReadMetersReportAnalyticsData(ModelMap model, HttpServletRequest request) {

		//System.out.println("inside readmeter method");
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode"),
				fromDate = request.getParameter("fromDate"), ToDate = request.getParameter("toDate"),
				townCode = request.getParameter("townCode"), town = request.getParameter("town");

		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();

		model.put("zoneList", zoneList);
		List<Object[]> result = null;
		/*
		 * String sql =
		 * "SELECT 	mtrno,	subdivision,	kno,	accno,	customer_name,	consumerstatus,	modem_sl_no,	last_communicated_date FROM	meter_data.master_main WHERE mtrno NOT IN "
		 * +
		 * "(SELECT meter_number	FROM 	meter_data.modem_communication WHERE DATE BETWEEN  '"
		 * + fromDate + "'  AND '" + ToDate + "' ) AND subdivision IN " + "( '" + subdiv
		 * + "')ORDER BY mtrno";
		 */
		/*
		 * String sql
		 * ="select AA.mtrno,AA.subdivision,AA.kno,AA.accno,AA.customer_name,AA.consumerstatus,AA.modem_sl_no,AA.last_communicated_date, BB.signal from \n"
		 * +
		 * "(SELECT 	mtrno,subdivision,kno,accno,customer_name,consumerstatus,modem_sl_no,last_communicated_date FROM	meter_data.master_main WHERE mtrno NOT IN (SELECT meter_number FROM 	meter_data.modem_communication WHERE DATE BETWEEN   '"
		 * + fromDate + "'  AND '" + ToDate + "') AND subdivision IN('" + subdiv +
		 * "')ORDER BY mtrno)AA\n" + "left join\n" +
		 * "(select meter_number,signal from meter_data.modem_communication )BB\n" +
		 * "on AA.mtrno=BB.meter_number";
		 */

		if (zone.equalsIgnoreCase("All")) {
			zone = "%";
		}
		if (circle.equalsIgnoreCase("All")) {
			circle = "%";
		}
		if (division.equalsIgnoreCase("All")) {
			division = "%";
		}
		if (subdiv.equalsIgnoreCase("All")) {
			subdiv = "%";
		}
		if (town.equalsIgnoreCase("All")) {
			town = "%";
		}
		
//		String sql="select AA.mtrno,AA.subdivision,AA.kno,AA.accno,AA.customer_name,AA.consumerstatus,AA.modem_sl_no,AA.last_communicated_date, BB.signal from \n" +
//				"(SELECT 	mtrno,subdivision,kno,accno,customer_name,consumerstatus,modem_sl_no,last_communicated_date FROM	meter_data.master_main WHERE mtrno NOT IN (SELECT DISTINCT meter_number FROM 	meter_data.modem_communication WHERE DATE BETWEEN '"+fromDate+"' AND '"+ToDate+"') AND zone like '"+zone+"' AND division like '"+division+"' AND subdivision like'"+subdiv+"' AND circle like '"+circle+"' ORDER BY mtrno)AA\n" +
//				"left join\n" +
//				"(select DISTINCT meter_number,signal from meter_data.modem_communication )BB\n" +
//				"on AA.mtrno=BB.meter_number";
		String sql="select AA.mtrno,AA.subdivision,AA.kno,AA.accno,AA.customer_name,AA.consumerstatus,AA.modem_sl_no,AA.last_communicated_date, BB.signal from \r\n" + 
				"(SELECT 	mtrno,subdivision,kno,\r\n" + 
				"(CASE WHEN (fdrcategory='FEEDER METER' OR fdrcategory='BOUNDARY METER') THEN fdrcode ELSE accno END) as accno,\r\n" + 
				"(CASE WHEN (fdrcategory='FEEDER METER' OR fdrcategory='BOUNDARY METER') THEN fdrname ELSE customer_name END) as customer_name,\r\n" + 
				"consumerstatus,modem_sl_no,last_communicated_date from(select * \r\n" + 
				"FROM	meter_data.master_main WHERE mtrno NOT IN (SELECT DISTINCT meter_number FROM 	meter_data.modem_communication WHERE DATE BETWEEN '"+fromDate+"' AND '"+ToDate+"') AND zone like '"+zone+"' AND division like '"+division+"' AND subdivision like'"+subdiv+"' AND circle like '"+circle+"' and town_code like  '"+town+"' ORDER BY mtrno) a)AA\r\n" + 
				"left join\r\n" + 
				"(select DISTINCT meter_number,signal,last_communication from meter_data.modem_communication where DATE BETWEEN '"+fromDate+"' AND '"+ToDate+"')BB\r\n" + 
				"on AA.mtrno=BB.meter_number";
		//System.out.println("sql------------->"+sql);
		
		try {
			result = entityManager.createNativeQuery(sql).getResultList();
			//System.out.println("Response--" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// model.addAttribute("reslutList", result);
		return result;
	}

	@RequestMapping(value = "/meterNotReadEvenOnce", method = { RequestMethod.POST, RequestMethod.GET })
	public String meterNotReadEvenOnce(ModelMap model, HttpServletRequest request) {
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);

		return "meterNotReadEvenOnce";
	}

	@RequestMapping(value = "/meterNotReadEvenOnceData", method = { RequestMethod.POST, RequestMethod.GET })
	public String meterNotReadEvenOnceData(ModelMap model, HttpServletRequest request) {
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode");
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);

		String sql = "SELECT 	mtrno,	subdivision, kno, accno, customer_name,	consumerstatus,	modem_sl_no  FROM 	meter_data.master_main WHERE mtrno NOT IN (SELECT 	meter_number "
				+ "	FROM	meter_data.modem_communication ) AND subdivision IN ('" + subdiv + "') ORDER BY	mtrno";

		List<?> result = null;
		try {
			result = entityManager.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("reslutList", result);
		return "meterNotReadEvenOnce";
	}

	@RequestMapping(value = "/excptionalenergyusageConsumers", method = { RequestMethod.POST, RequestMethod.GET })
	public String excpUsage(ModelMap model, HttpServletRequest request) {

		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		return "exceptionalEnergyUsage";
	}

	@RequestMapping(value = "/exceptionalenergyusage", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object exceptionalEnergyUsage(ModelMap model, HttpServletRequest request) {
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdiv = request.getParameter("subdiv");
		String rdngmnth = request.getParameter("rdngmnth");
		String year = rdngmnth.substring(0, 4);
		String month = rdngmnth.substring(4);
		String sql = "select\n" +
				"fs.mtrnum,fs.kno,fs.accn,fs.subdivision,fs.customer_name,fs.consumerstatus,fs.fkwh,fs.sanld,fs.division,fs.circle,fs.zone\n" +
				"from\n" +
				"(select\n" +
				"ab.mtrnum,ab.kno,ab.accn,ab.subdivision,ab.customer_name,mas.consumerstatus,ab.division,ab.circle,ab.zone,\n" +
				"case when ab.fkwh is null then 0 else ab.fkwh end*CAST(mfac as NUMERIC) as fkwh ,case when\n" +
				"mas.kworhp='HP' then CAST(mas.sanload as NUMERIC)*0.7457*24*meter_data.num_days("+year+", "+month+")\n" +
				"when mas.kworhp='KW' then CAST(mas.sanload as NUMERIC)*24*meter_data.num_days("+year+", "+month+")\n" +
				"end as sanld from (select\n" +
				"A.mtrnum,A.fkwh,mma.kno,mma.subdivision,mma.customer_name,mma.accno as\n" +
				"accn,mma.mf as mfac,mma.division,mma.circle,mma.zone from (select bh.meter_number as mtrnum,\n" +
				"((select b.kwh from meter_data.bill_history b where\n" +
				"b.meter_number=bh.meter_number\n" +
				" and to_char(b.billing_date,'yyyyMM')=to_char(bh.billing_date+ INTERVAL\n" +
				"'1 month','yyyyMM')\n" +
				"and to_char(b.billing_date,'HH24:MI:SS')='00:00:00')-kwh)/1000 as fkwh\n" +
				"from meter_data.bill_history bh\n" +
				"where to_char(bh.billing_date,'yyyyMM')='"+rdngmnth+"' and\n" +
				"to_char(bh.billing_date,'HH24:MI:SS')='00:00:00') A\n" +
				"left join meter_data.master_main mma  on A.mtrnum=mma.mtrno ) ab left join meter_data.master_main mas on\n" +
				"mas.accno=ab.accn ) fs where fs.fkwh>fs.sanld and fs.zone like '"+zone+"' and fs.circle like '"+circle+"'  AND fs.division like '"+division+"' AND fs.subdivision like '"+subdiv+"' ";

		List<?> result = null;
		try {
			result = entityManager.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("reslutList", result); // return
													// "exceptionalEnergyUsage";
		return result;

	}

	@RequestMapping(value = "/roundCompleteCasesReport", method = { RequestMethod.POST, RequestMethod.GET })
	public String roundCompleteCasesReport(ModelMap model, HttpServletRequest request) {
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);
		return "roundCompleteCasesReport";
	}

	@RequestMapping(value = "/roundCompleteCasesReportData", method = { RequestMethod.POST, RequestMethod.GET })
	public String roundCompleteCasesReportData(ModelMap model, HttpServletRequest request)
			throws java.text.ParseException {
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);

		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode"),
				rdngMonth = request.getParameter("reportFromDate");

		Date d1 = new Date();
		Date d2 = new Date();
		String formattedDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");

		SimpleDateFormat gotSdf = new SimpleDateFormat("yyyyMM");

		try {
			d2 = gotSdf.parse(rdngMonth);
			formattedDate = gotSdf.format(d2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		String currentMonthFile = sdf.format(d1) + "-01 00:00:00";
		String currentDayBillFile = sdf1.format(d1); // 06-01-2019 12:24

		//System.out.println("currentMonthFile=======>" + currentMonthFile);
		//System.out.println("currentDayBillFile=======>" + currentDayBillFile);
		//System.out.println("formattedDate=======>" + formattedDate);

		// if else conditions

		String sql = " SELECT BB.*,CC.hardware_version,mm.subdivision,mm.accno,mm.kno,mm.customer_name,mm.consumerstatus FROM (SELECT AA.kwh AS curren_KWH,	AA.PreviousKwh AS prev_KWH, "
				+ "	AA.kwh - AA.PreviousKwh AS kwh_consumption,		AA.kvah AS curr_kvah,	AA.Previouskvah AS prev_kvah, AA.kvah - AA.Previouskvah AS kvah_consumption,"
				+ "	CASE WHEN AA.kwh < AA.PreviousKwh THEN	'YES'ELSE	'NO'END AS Kwh_Round_Complete, CASE WHEN AA.kvah < AA.Previouskvah "
				+ " THEN 	'YES' ELSE 	'NO' END AS Kvah_Round_Complete,AA.mtrno FROM (SELECT *, LAG (kwh) OVER (ORDER BY billing_date) AS PreviousKwh, LAG (kvah) "
				+ "OVER (ORDER BY billing_date) AS PreviousKvah, LAG (billing_date) OVER (ORDER BY billing_date) AS PEV_billing_date,meter_number as mtrno "
				+ "FROM 	meter_data.bill_history A WHERE  A .meter_number IN ( SELECT mtrno FROM  meter_data.master_main where subdivision IN ( '"
				+ subdiv + "')  )"
				+ "  	AND A .billing_date IN ('2018-12-01 11:04:00', '2019-01-01 00:00:00')  ORDER BY billing_date ) AA WHERE 	"
				+ "AA.PreviousKwh IS NOT NULL ORDER BY 	AA.billing_date DESC)BB LEFT JOIN "
				+ " (SELECT hardware_version,meter_serial_number FROM meter_data.name_plate)CC ON CC.meter_serial_number=BB.mtrno"
				+ " LEFT JOIN ( SELECT * FROM meter_data.master_main)mm ON CC.meter_serial_number=mm.mtrno";

		List<?> result = null;
		try {
			result = entityManager.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("reslutList1", zone);
		model.addAttribute("reslutList1", circle);
		model.addAttribute("reslutList1", division);
		model.addAttribute("reslutList1", subdiv);

		model.addAttribute("reslutList1", result);
		//System.out.println(result.toString());
		return "roundCompleteCasesReport";
	}

	@RequestMapping(value = "/suspectedenergyReadinghistory", method = { RequestMethod.POST, RequestMethod.GET })
	public String suspectedEnergy(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		return "suspectedEnergyReadingHistory";
	}

	@RequestMapping(value = "/suspectedenergyReadinghistoryDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object suspectedEnergyDetails(HttpServletRequest request, HttpServletResponse response) {
		List<?> l = null;

		String subdiv = request.getParameter("subdiv");
		String rdngmnth = request.getParameter("rdngmnth");
		String sql = "select distinct a.mtrno,a.kno,a.accno,a.phase,a.customer_name,a.subdivision,a.consumerstatus,b.meter_number,b.billing_date,b.kva*cast(d.mf as numeric) as kvas,b.kwh,b.kvah,d.mf FROM\n"
				+ "(select mtrno,kno,accno,phase,customer_name,subdivision,consumerstatus from meter_data.master_main where subdivision='"
				+ subdiv + "')a,\n"
				+ "(select meter_number,billing_date,kva,kwh,kvah FROM meter_data.bill_history WHERE to_char(billing_date,'dd HH24:MI:SS')!='01 00:00:00' AND to_char(billing_date,'yyyyMM')='"
				+ rdngmnth + "' ) b,\n" + "(select mf, mtrno FROM meter_data.master_main) d\n"
				+ "WHERE a.mtrno=b.meter_number AND\n" + "a.mtrno=d.mtrno ORDER BY meter_number";
		try {
			l = entityManager.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} // entityManager.createNativeQuery(sql).getResultList();
		return l;
	}

	// deepa
	@RequestMapping(value = "/ConsistentCommunicationFailMeters", method = { RequestMethod.POST, RequestMethod.GET })
	public String ConsistentCommunicationFailMeters(ModelMap model, HttpServletRequest request) {
		/*
		 * String zone=request.getParameter("zone"),circle=request.getParameter("circle"
		 * ),division=request.getParameter("division"),
		 * subdiv=request.getParameter("sdoCode") ;
		 */
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.put("zoneList", zoneList);
		/*
		 * String sql=
		 * "SELECT circle,division,customer_name,accno,mtrno FROM meter_data.master_main where zone like 'JVVNL' and circle like '"
		 * +circle+"' AND\n" + "division like '"+division+"' and subdivision like\n" +
		 * "'"+subdiv+"' and mtrno  NOT IN (SELECT 	meter_number 	\n" +
		 * "FROM	meter_data.modem_communication WHERE last_communication >= NOW() + INTERVAL '-3' DAY\n"
		 * + "   AND last_communication <  NOW() + INTERVAL  '0' DAY)";
		 * 
		 * List<?> result= null; try { result =
		 * entityManager.createNativeQuery(sql).getResultList(); } catch (Exception e) {
		 * e.printStackTrace(); } model.addAttribute("reslutList", result);
		 */
		return "ConsistentCommunicationFailMeters";
	}

	@RequestMapping(value = "/consistentCommunicationFailMetersList", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object ConsistentCommunicationFailMetersList(@RequestParam String zone,
			@RequestParam String circle, @RequestParam String division, @RequestParam String sdoCode) {
		String sql = "SELECT circle,division,subdivision,customer_name,accno,mtrno FROM meter_data.master_main where zone like 'JVVNL' and circle like '"
				+ circle + "' AND\n" + "division like '" + division + "' and subdivision like\n" + "'" + sdoCode
				+ "' and mtrno  NOT IN (SELECT 	meter_number 	\n"
				+ "FROM	meter_data.modem_communication WHERE last_communication >= NOW() + INTERVAL '-3' DAY\n"
				+ "   AND last_communication <  NOW() + INTERVAL  '0' DAY)";

		List<?> result = null;
		try {
			result = entityManager.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/powerfactoranalysis", method = { RequestMethod.POST, RequestMethod.GET })
	public String powerfactoranalysis(ModelMap model) {

		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		List<?> locationList = feederdetailsservice.getDistinctCategory();
		model.put("locationList", locationList);
		return "powerfactoranalysisrpt";
	}

	@RequestMapping(value = "/meterChangeReport", method = { RequestMethod.POST, RequestMethod.GET })
	public Object meterChangeReport(HttpServletRequest request, Model model) {

		List<?> circleList = null;

		try {
			circleList = meterMasterService.getAllCirclesInAmiLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("circleList", circleList);

		return "meterChangeReport";
	}

	// Meter replacement report

	@RequestMapping(value = "/getMeterReplacementdata", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getMeterReplacementdata(HttpServletRequest request) {
		String accno = request.getParameter("accno");
		String newMetrNo = request.getParameter("newMetrNo");

		//System.out.println("This is the output " + accno + "--" + newMetrNo);
		String sql;

		sql = "SELECT sdocode,kno,accno,consumername, newmeterno,survey_timings from meter_data.survey_output where accno = '"
				+ accno + "' and newmeterno = '" + newMetrNo + "'";

		List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();

		return list;

	}

	@RequestMapping(value = "/getMeterReportData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getMeterReportData(HttpServletRequest request) {
		// String accno=request.getParameter("accno");
		String newMetrNo = request.getParameter("meterNo");
		String accNo = request.getParameter("accno");
		String kno = request.getParameter("kno");

		String circle = request.getParameter("circle");
		String division = request.getParameter("divis");
		String subdivision = request.getParameter("subdivi");

		String sql;
		
		/*if(circle == null){
			if(accNo != null  && newMetrNo != null){
				//qry with both accountNo and new meter no 
				
			}else if (newMetrNo == null){
				//qry with meter  new meterno 
			} else{
				//qry with accNo
			}
				
					
		}else if(subdivision == null){
			
			if(circle != null  && division != null && newMetrNo != null && accNo !=null ){
				//qry with circle and division newmwterno and accno 
			}else if(newMetrNo == null && accNo ==null){
				// qry with circle and division 
			}else if (division == null){
				//qry with division accno and circle and meterno 
			} else{
				//qry with both accountNo and new meter no  
			}
				
		}
		*/
		
		
		

		/*sql = " select   CAST( '" + circle + "' AS varchar(50)) as circlename,  CAST( '" + division
				+ "' AS varchar(50))   as divisionName, CAST(  '" + subdivision
				+ "' AS varchar(50)) as subdivisionName,   kno, accno, consumername,address,land_mark,latitude,longitude,meterno,newmeterno,survey_timings from meter_data.survey_output where newmeterno = '"
				+ newMetrNo + "' and accno = '" + accNo + "' and   kno = '" + kno + "' ";

		*/
		
		
		sql = " select   CAST( '" + circle + "' AS varchar(50)) as circlename,  CAST( '" + division
				+ "' AS varchar(50))   as divisionName, CAST(  '" + subdivision
				+ "' AS varchar(50)) as subdivisionName, sdocode, kno, accno, consumername, address, mobileno, meterno, "
				+ "connectiontype, poleno, dtcno, latitude, longitude, premise, sticker_no, land_mark, meter_image, premise_image, id,"
				+ " mrcode, appversion, imei, survey_timings, observation, old_metermake, old_mf, old_ctrn, old_ctrd, currdngkwh, finalreading,"
				+ " newmeterno, newmetermake, newmetertype, newmf, newctratio, newinitialreading, newmeterimage, mcrreportimage, newmeterkvah,"
				+ " newmeterkva, oldmeterkva, oldmeterno_correction, oldmeterkvah, tenderno from meter_data.survey_output where newmeterno = '"+newMetrNo+"' and accno = '"+accNo+"' and   kno = '"+kno+"' ";

		
		
		
		//System.out.println("This is sql " + sql);

		List<?> list = entityManager.createNativeQuery(sql).getResultList();
		return list;

	}

	@RequestMapping(value = "/pfanalysisreport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object pfreport(HttpServletRequest request) {
		List<?> list = new ArrayList<>();
		
		String region = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String subdiv = request.getParameter("subdiv");
		String rdngmnth = request.getParameter("rdngmnth");
		String metertype = request.getParameter("metertype");
		String town = request.getParameter("town");
		//System.err.println("Townnn-----"+town);
		list = reportService.pfanalysisreport(region,circle,town, rdngmnth, metertype);
		return list;

	}

	@RequestMapping(value = "/voltageVariationanalysisreport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> voltageVariationanalysisreport(HttpServletRequest request) {
		//List<?> list = new ArrayList<>();
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String div = request.getParameter("div");
		String subdiv = request.getParameter("subdiv");
		String rdngmnth = request.getParameter("rdngmnth");
		String metertype = request.getParameter("metertype");
		String town = request.getParameter("town");
		//System.err.println("Townnn-----"+town);
		List<?> list = reportService.voltageVariationAnalysisRep(town, rdngmnth, metertype,zone,circle);
	    //System.out.println(subdiv + " " + rdngmnth + " " + metertype + "  " + list.size());
		return list;

	}

	@RequestMapping(value = "/PFCreportD4", method = { RequestMethod.POST, RequestMethod.GET })
	public String PFCreportD4(ModelMap model, HttpServletRequest request) {
		try {
			String li = pfcservice.getState();
			String ls = pfcservice.getDiscom();
			List<?> periodList = pfcservice.getDistinctPeriod();

			model.put("periodList", periodList);
			model.put("stateName", li);
			model.put("discomName", ls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PFCreportD4";
	}

	/*
	 * @RequestMapping(value = "/getPFCreportD4data", method = {
	 * RequestMethod.GET,RequestMethod.POST }) public @ResponseBody Object
	 * getgetPFCreportD4data(@PathVariable String period, @PathVariable String town)
	 * {
	 * 
	 * String sql ;
	 * 
	 * sql
	 * ="select rec_id,town, baseline_loss, bill_eff, coll_eff,atc_loss from meter_data.pfc_d1_rpt where month_year = '"
	 * +period +"' and town = '"+town+"'  ";
	 * 
	 * List<Object[]> list =
	 * businessRoleService.getCustomEntityManager("postgresMdas").createNativeQuery(
	 * sql).getResultList(); return list;
	 * 
	 * }
	 */

	@RequestMapping(value = "/testHigh", method = { RequestMethod.POST, RequestMethod.GET })
	public String testHigh(ModelMap model, HttpServletRequest request) {

		return "testHigh";
	}

	@RequestMapping(value = "/getTestHighData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getTestHighData(HttpServletRequest request) {

		String sql = "SELECT read_time, kwh FROM meter_data.load_survey WHERE meter_number='00329023' AND date(read_time)='2019-01-20' ORDER BY read_time ASC;";

		List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
		return list;

	}

	@RequestMapping(value = "/nppDataGeneration", method = { RequestMethod.POST, RequestMethod.GET })
	public String nppDataGeneration(HttpServletRequest request, ModelMap model) {
		List<?> monthyearList = nppDataService.getDistinctMonthYear();
		model.put("monthyearList", monthyearList);
		return "nppDataGeneration";
	}

	@RequestMapping(value = "/nppFeederDataReport", method = { RequestMethod.POST, RequestMethod.GET })
	public String nppDataReport(HttpServletRequest request, ModelMap model) {
		// List<NPPDataEntity> nppData=new ArrayList<>();
		List<?> monthyearList = nppDataService.getDistinctMonthYear();
		model.put("monthyearList", monthyearList);
		String li = pfcservice.getState();
		model.put("stateName", li);
		String ls = pfcservice.getDiscom();
		model.put("discomName", ls);
		// model.put("mf_value", nppData.get(0).get);
		return "nppDataReport";
	}

	@RequestMapping(value = "/triggerNPPDataGeneration", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String triggerNPPDataGeneration(HttpServletRequest request, ModelMap model) {

		String monthyear = request.getParameter("monthyear");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		String status = generateNppData(monthyear, fromDate, toDate);
		return status;
	}

	public String generateNppData(String my, String fdate, String todate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String getFdrQry = "SELECT fdrname, mtrno, fdrcode, fdrtype, mf FROM meter_data.master_main WHERE fdrcategory='FEEDER METER' "
					+ "and fdrcode is NOT null;";

			List<?> allFdr = entityManager.createNativeQuery(getFdrQry).getResultList();
			for (Iterator iterator = allFdr.iterator(); iterator.hasNext();) {
				Object[] obj = (Object[]) iterator.next();

				String fdrname = String.valueOf(obj[0]);
				String mtrno = String.valueOf(obj[1]);
				String fdrcode = String.valueOf(obj[2]);
				String fdrtype = String.valueOf(obj[3]);
				int mf = 1;
				String mf_string = String.valueOf(obj[4]);
				try {
					mf = Integer.parseInt(mf_string);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					String fdrMtrDataQry = "SELECT l.*,i.power_off_count, i.power_off_duration FROM \n" + "(\n"
							+ "SELECT meter_number, min(min_vol) min_vol, max(max_curr) max_curr, SUM (kwh) FROM (\n"
							+ "SELECT meter_number, read_time,LEAST(v_r, v_r, v_y) as min_vol, GREATEST(i_r,i_y,i_b) as max_curr, kwh\n"
							+ "FROM meter_data.load_survey WHERE meter_number='" + mtrno
							+ "' AND date(read_time) BETWEEN '" + fdate + "' AND '" + todate + "' \n"
							+ "ORDER BY read_time DESC\n" + ")a GROUP BY a.meter_number\n" + ") l FULL JOIN\n" + "(\n"
							+ "SELECT meter_number, \"max\"(power_off_count)-min(power_off_count) as power_off_count, \n"
							+ "\"max\"(power_off_duration)-min(power_off_duration) as power_off_duration\n" + "FROM \n"
							+ "(\n"
							+ "SELECT meter_number,read_time, power_off_count, power_off_duration FROM meter_data.instantaneous \n"
							+ "WHERE meter_number='" + mtrno + "' AND date(read_time) BETWEEN '" + fdate + "' AND '"
							+ todate + "' ORDER BY read_time DESC\n" + ") b GROUP BY b.meter_number\n"
							+ ")i ON l.meter_number=i.meter_number;";

					double min_voltage = 0, max_current = 0, kwh_input = 0, kwh_export = 0, power_off_count = 0,
							power_off_duration = 0;

					List<?> fdrMtrData = entityManager.createNativeQuery(fdrMtrDataQry).getResultList();
					if (fdrMtrData.size() > 0) {
						Object[] obj1 = (Object[]) fdrMtrData.get(0);
						if (obj1[1] != null) {
							min_voltage = Double.parseDouble(String.valueOf(obj1[1]));
						}
						if (obj1[2] != null) {
							max_current = Double.parseDouble(String.valueOf(obj1[2]));
						}
						if (obj1[3] != null) {
							kwh_input = Double.parseDouble(String.valueOf(obj1[3]));
							kwh_input = kwh_input * mf;
						}
						if (obj1[4] != null) {
							power_off_count = Double.parseDouble(String.valueOf(obj1[4]));
						}
						if (obj1[5] != null) {
							power_off_duration = Double.parseDouble(String.valueOf(obj1[5]));
						}

					}

					double brdr_kwh = 0, brdr_kwh_exp = 0;
					String bndryMtrDataQry = "SELECT meter_number, SUM (kwh) as kwh, sum(kwh_exp) as kwh_exp, sum(kwh_imp) as kwh_imp FROM ( \n"
							+ "SELECT meter_number, read_time, kwh, kwh_exp, kwh_imp\n"
							+ "FROM meter_data.load_survey WHERE meter_number in \n"
							+ "(SELECT mtrno FROM meter_data.master_main WHERE fdrcategory='BOUNDARY METER' AND fdrname='"
							+ fdrname + "')\n" + "AND date(read_time) BETWEEN '" + fdate + "' AND '" + todate + "' \n"
							+ "ORDER BY read_time DESC\n" + ")a GROUP BY a.meter_number;";

					List<?> bndryMtrData = entityManager.createNativeQuery(bndryMtrDataQry).getResultList();

					if (bndryMtrData.size() > 0) {
						Object[] obj2 = (Object[]) bndryMtrData.get(0);
						if (obj2[1] != null) {
							brdr_kwh = Double.parseDouble(String.valueOf(obj2[1]));
							brdr_kwh = brdr_kwh * mf;
						}
						if (obj2[2] != null) {
							brdr_kwh_exp = Double.parseDouble(String.valueOf(obj2[2]));
							brdr_kwh_exp = brdr_kwh_exp * mf;
						}

					}

					double input_energy = 0, export_energy = 0;

					input_energy = kwh_input + brdr_kwh - brdr_kwh_exp;
					export_energy = kwh_export;

					NPPDataEntity npp = new NPPDataEntity();

					npp.setKeyNPPData(new KeyNPPData(Integer.parseInt(my), fdrcode));
					npp.setFeeder_name(fdrname);
					npp.setFeedertype(fdrtype);
					try {
						npp.setStart_billing_date(sdf.parse(fdate));
						npp.setEnd_billling_date(sdf.parse(todate));
					} catch (Exception e) {
						// TODO: handle exception
					}
					npp.setNo_of_power_faliure(((Double) power_off_count).longValue());
					npp.setDuration_of_power_faliure(((Double) power_off_duration).longValue());
					npp.setMax_current(max_current);
					npp.setMin_voltage(min_voltage);
					npp.setInput_energy(input_energy);
					npp.setExport_energy(export_energy);
					npp.setMtrno(mtrno);
					nppDataService.update(npp);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			return "Success";

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}

	}

	@RequestMapping(value = "/downloadNPPDataGeneration", method = { RequestMethod.POST, RequestMethod.GET },produces="application/text")
	public @ResponseBody String generateExportReport(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws FileNotFoundException {

		String monthyear = request.getParameter("monthyear");
		String govtscheme = request.getParameter("govtscheme");
		String town = request.getParameter("town");

		//System.out.println("town: "+town);
		
		//String filename = "TNEB_NPP_" + new SimpleDateFormat("yyyyMMdd_HH.mm.ss.SSS").format(new Date()) + ".txt";
		
		String filename  = "TNEB_ufcc_"+monthyear+".txt";
		String json = downloadNppData(monthyear,govtscheme,town);
		
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setContentType("text/plain");
		try {
			// String filename = "Demo";
			//String filepath = com.bcits.mdas.utility.FilterUnit.nppDataSourceFolder + "/" + filename;
			//File file = new File(filepath);
			//InputStream inputStream = new FileInputStream(file);
			////System.out.println(file.getName());
			try {
				
				//OutputStream out = response.getOutputStream();
				//IOUtils.copy(inputStream, out);
				//response.flushBuffer();
				//out.flush();
				//out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	//JSON export for npp data feeder
	@RequestMapping(value = "/downloadNPPDataGenerationFeeder", method = { RequestMethod.POST, RequestMethod.GET },produces="application/text")
	public @ResponseBody String downloadNPPDataGenerationFeeder(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws FileNotFoundException {

		String monthyear = request.getParameter("monthyear");
		String govtscheme = request.getParameter("govtscheme");
		String town = request.getParameter("town");

		//System.out.println("town: "+town);
		
		//String filename = "TNEB_NPP_" + new SimpleDateFormat("yyyyMMdd_HH.mm.ss.SSS").format(new Date()) + ".txt";
		
		String filename  = "TNEB_ufcc_"+monthyear+".txt";
		String json = downloadNppDataFeeder(monthyear,govtscheme,town);
		
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setContentType("text/plain");
		try {
			// String filename = "Demo";
			//String filepath = com.bcits.mdas.utility.FilterUnit.nppDataSourceFolder + "/" + filename;
			//File file = new File(filepath);
			//InputStream inputStream = new FileInputStream(file);
			////System.out.println(file.getName());
			try {
				
				//OutputStream out = response.getOutputStream();
				//IOUtils.copy(inputStream, out);
				//response.flushBuffer();
				//out.flush();
				//out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
/*	@SuppressWarnings("unchecked")
	public String downloadNppData(String my, String scheme,String town) {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//List<NPPDataEntity> list = nppDataService.getDataByMonthYear(my);
		//List<NPPDataEntity> list = nppDataService.getDataByTownMonthYear(town, my);
		
		

		// JSONObject mainObj = new JSONObject();
		// JSONObject header = new JSONObject();
		// JSONObject transaction_data = new JSONObject();
		// JSONObject footer = new JSONObject();
		// JSONArray transactionAry = new JSONArray();

		Map<String, Object> mainObj = new LinkedHashMap<String, Object>();
		Map<String, Object> header = new LinkedHashMap<String, Object>();
		Map<String, Object> transaction_data = new LinkedHashMap<String, Object>();
		Map<String, Object> footer = new LinkedHashMap<String, Object>();

		List<Object> transactionAry = new LinkedList<>();

		String filename = "tneb_" + new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(new Date()) + ".txt";

		header.put("file_Name", filename);
		header.put("file_generation_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
		header.put("no_of_records", list.size());
		header.put("version", "1");

		footer.put("file_Name", filename);

		mainObj.put("header", header);

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			NPPDataEntity nppDataEntity = (NPPDataEntity) iterator.next();
			// JSONObject fdrData = new JSONObject();

			Map<String, Object> fdrData = new LinkedHashMap<String, Object>();

			fdrData.put("Feeder_Code", nppDataEntity.getKeyNPPData().getFeedercode());
			fdrData.put("Feeder_Type(U/R/M)", nppDataEntity.getFeedertype().substring(0, 1));
			fdrData.put("Start_Billing_Period", sdf.format(nppDataEntity.getStart_billing_date()));
			fdrData.put("End_Billing_Period", sdf.format(nppDataEntity.getEnd_billling_date()));
			fdrData.put("No_Of_Power_Failure", nppDataEntity.getNo_of_power_faliure());
			fdrData.put("Duration_Of_Power_Failure(Sec)", nppDataEntity.getDuration_of_power_faliure());
			fdrData.put("Minimum_Voltage(V)", nppDataEntity.getMin_voltage());
			fdrData.put("Max_Current(A)", nppDataEntity.getMax_current());
			fdrData.put("Input_Energy(kwh)", nppDataEntity.getInput_energy());
			fdrData.put("Export_Energy(kwh)", nppDataEntity.getExport_energy());
			fdrData.put("HT_Industrial_Consumer_Count", nppDataEntity.getHt_ind_con_count());
			fdrData.put("HT_Commercial_Consumer_Count", nppDataEntity.getHt_ind_con_count());
			fdrData.put("LT_Industrial_Consumer_Count", nppDataEntity.getLt_ind_con_count());
			fdrData.put("LT_Commercial_Consumer_Count", nppDataEntity.getLt_com_con_count());
			fdrData.put("LT_Domestic_Consumer_Count", nppDataEntity.getLt_dom_con_count());
			fdrData.put("Govt_Consumer_Count", nppDataEntity.getGovt_con_count());
			fdrData.put("Agri_Consumer_Count", nppDataEntity.getAgri_con_count());
			fdrData.put("Others_Consumer_Count", nppDataEntity.getOther_con_count());
			fdrData.put("HT_Industrial_Energy_Billed", nppDataEntity.getHt_ind_energy_bill());
			fdrData.put("HT_Commercial_Energy_Billed", nppDataEntity.getHt_com_energy_bill());
			fdrData.put("LT_Industrial_Energy_Billed", nppDataEntity.getLt_ind_energy_bill());
			fdrData.put("LT_Commercial_Energy_Billed", nppDataEntity.getLt_com_energy_bill());
			fdrData.put("LT_Domestic_Energy_Billed(kwh)", nppDataEntity.getLt_dom_energy_bill());
			fdrData.put("Govt_Energy_Billed(kwh)", nppDataEntity.getGovt_energy_bill());
			fdrData.put("Agri_Energy_Billed(kwh)", nppDataEntity.getAgri_energy_bill());
			fdrData.put("Others_Energy_Billed(kwh)", nppDataEntity.getOther_energy_bill());
			fdrData.put("HT_Industrial_Amount_Billed", nppDataEntity.getHt_ind_amount_bill());
			fdrData.put("HT_Commercial_Amount_Billed", nppDataEntity.getHt_com_amount_bill());
			fdrData.put("LT_Industrial_Amount_Billed", nppDataEntity.getLt_ind_amount_bill());
			fdrData.put("LT_Commercial_Amount_Billed", nppDataEntity.getLt_com_amount_bill());
			fdrData.put("LT_Domestic_Amount_Billed", nppDataEntity.getLt_dom_amount_bill());
			fdrData.put("Govt_Amount_Billed", nppDataEntity.getGovt_amount_bill());
			fdrData.put("Agri_Amount_Billed", nppDataEntity.getAgri_amount_bill());
			fdrData.put("Others_Amount_Billed", nppDataEntity.getOther_amount_bill());
			fdrData.put("HT_Industrial_Amount_Collected", nppDataEntity.getHt_ind_amount_collected());
			fdrData.put("HT_Commercial_Amount_Collected", nppDataEntity.getHt_com_amount_collected());
			fdrData.put("LT_Industrial_Amount_Collected", nppDataEntity.getLt_ind_amount_collected());
			fdrData.put("LT_Commercial_Amount_Collected", nppDataEntity.getLt_com_amount_collected());
			fdrData.put("LT_Domestic_Amount_Collected", nppDataEntity.getLt_dom_amount_collected());
			fdrData.put("Govt_Amount_Collected", nppDataEntity.getGovt_amount_collected());
			fdrData.put("Agri_Amount_Collected", nppDataEntity.getAgri_amount_collected());
			fdrData.put("Others_Amount_Collected", nppDataEntity.getOther_amount_collected());

			transactionAry.add(fdrData);

		}

		// transaction_data.put("transaction_data", transactionAry);

		mainObj.put("transaction_data", transactionAry);
		mainObj.put("footer", footer);

		// //System.out.println(mainObj.toString());

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mainObj);

		//System.out.println(json);

		//writeNPPData(com.bcits.mdas.utility.FilterUnit.nppDataSourceFolder, filename, json);
		return json;
	}*/

	
	@SuppressWarnings("unchecked")
	public String downloadNppData(String my, String scheme,String town) {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//List<NPPDataEntity> list = nppDataService.getDataByMonthYear(my);
		//List<NPPDataEntity> list = nppDataService.getDataByTownMonthYear(town, my);
		
		List<Object[]> list = (List<Object[]>) pfcservice.getNppReportDetails(my,town,scheme);

		String month_year =my.substring(0,2)+"-"+my.substring(2,6); 
		 		
				
		
		
		// JSONObject mainObj = new JSONObject();
		// JSONObject header = new JSONObject();
		// JSONObject transaction_data = new JSONObject();
		// JSONObject footer = new JSONObject();
		// JSONArray transactionAry = new JSONArray();

		Map<String, Object> mainObj = new LinkedHashMap<String, Object>();
		Map<String, Object> header = new LinkedHashMap<String, Object>();
		Map<String, Object> transaction_data = new LinkedHashMap<String, Object>();
		Map<String, Object> footer = new LinkedHashMap<String, Object>();

		List<Object> transactionAry = new LinkedList<>();

		String filename =   "TNEB_ufcc_"+my+".txt";

		header.put("file_Name", filename);
		header.put("file_generation_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
		header.put("no_of_records", list.size());
		header.put("version", "1.1");

		footer.put("file_Name", filename);
		footer.put("no_of_records", list.size());

		mainObj.put("header", header);

		for (Object[] object : list) {
			// JSONObject fdrData = new JSONObject();

			Map<String, Object> fdrData = new LinkedHashMap<String, Object>();

			
			

			
			fdrData.put("State_Code","TNEB");
			fdrData.put("DISCOM_Code",object[1]);
			fdrData.put("Town_Code", object[0]);
			fdrData.put("Month_Year", month_year);
			fdrData.put("Con_Pending_P_M", object[2]);
			fdrData.put("Con_Applied_C_M", object[3]);
			fdrData.put("Tot_Con_Rel_C_M", object[4]);
			fdrData.put("Con_Rel_SERC", object[5]);
			fdrData.put("Com_Pen_P_M", object[6]);
			fdrData.put("Com_Rec_C_M", object[7]);
			fdrData.put("Tot_Com_Close_C_M", object[8]);
			fdrData.put("Com_Closed_SERC", object[9]);
		

			transactionAry.add(fdrData);

		}

		// transaction_data.put("transaction_data", transactionAry);

		mainObj.put("transaction_data", transactionAry);
		mainObj.put("footer", footer);

		// //System.out.println(mainObj.toString());

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mainObj);

		//System.out.println(json);

		//writeNPPData(com.bcits.mdas.utility.FilterUnit.nppDataSourceFolder, filename, json);
		return json;
	}
	
	
	
	
	//JSON Format for Npp feeder data download .txt 
	
	@SuppressWarnings("unchecked")
	public String downloadNppDataFeeder(String my, String scheme,String town) {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//List<NPPDataEntity> list = nppDataService.getDataByMonthYear(my);
		//List<NPPDataEntity> list = nppDataService.getDataByTownMonthYear(town, my);
		
		List<Object[]> list = (List<Object[]>) nppDataService.getNPPReportRapdrpDetails(town, my);

		String month_year =my.substring(0,2)+"-"+my.substring(2,6); 
		 		
				
		
		
		// JSONObject mainObj = new JSONObject();
		// JSONObject header = new JSONObject();
		// JSONObject transaction_data = new JSONObject();
		// JSONObject footer = new JSONObject();
		// JSONArray transactionAry = new JSONArray();

		Map<String, Object> mainObj = new LinkedHashMap<String, Object>();
		Map<String, Object> header = new LinkedHashMap<String, Object>();
		Map<String, Object> transaction_data = new LinkedHashMap<String, Object>();
		Map<String, Object> footer = new LinkedHashMap<String, Object>();

		List<Object> transactionAry = new LinkedList<>();

		String filename =   "TNEB_uf_"+my+".txt";

		header.put("file_Name", filename);
		header.put("file_generation_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
		header.put("no_of_records", list.size());
		header.put("version", "1.1");

		footer.put("file_Name", filename);
		footer.put("no_of_records", list.size());

		mainObj.put("header", header);

		for (Object[] object : list) {
			// JSONObject fdrData = new JSONObject();

			Map<String, Object> fdrData = new LinkedHashMap<String, Object>();
		
		
			
			fdrData.put("Feeder_Code", object[0]);
			fdrData.put("Feeder_Type(U/R/M)", object[44]);
			fdrData.put("Start_Billing_Period", object[42]);
			fdrData.put("End_Billing_Period", object[43]);
			fdrData.put("No_Of_Power_Failure", object[02]);
			fdrData.put("Duration_Of_Power_Failure(Sec)", object[04]);
			fdrData.put("Minimum_Voltage(V)", object[40]);
			fdrData.put("Max_Current(A)", object[41]);
			fdrData.put("Input_Energy(kwh)", object[38]);
			fdrData.put("Export_Energy(kwh)", object[39]);
			fdrData.put("HT_Industrial_Consumer_Count", object[6]);
			fdrData.put("HT_Commercial_Consumer_Count", object[7]);
			fdrData.put("LT_Industrial_Consumer_Count", object[8]);
			fdrData.put("LT_Commercial_Consumer_Count",object[9]);
			fdrData.put("LT_Domestic_Consumer_Count", object[10]);
			fdrData.put("Govt_Consumer_Count", object[11]);
			fdrData.put("Agri_Consumer_Count", object[12]);
			fdrData.put("Others_Consumer_Count", object[13]);
			fdrData.put("HT_Industrial_Energy_Billed", object[14]);
			fdrData.put("HT_Commercial_Energy_Billed", object[15]);
			fdrData.put("LT_Industrial_Energy_Billed", object[16]);
			fdrData.put("LT_Commercial_Energy_Billed", object[17]);
			fdrData.put("LT_Domestic_Energy_Billed(kwh)",object[18]);
			fdrData.put("Govt_Energy_Billed(kwh)", object[19]);
			fdrData.put("Agri_Energy_Billed(kwh)", object[20]);
			fdrData.put("Others_Energy_Billed(kwh)", object[21]);
			fdrData.put("HT_Industrial_Amount_Billed", object[22]);
			fdrData.put("HT_Commercial_Amount_Billed", object[23]);
			fdrData.put("LT_Industrial_Amount_Billed", object[24]);
			fdrData.put("LT_Commercial_Amount_Billed", object[25]);
			fdrData.put("LT_Domestic_Amount_Billed", object[26]);
			fdrData.put("Govt_Amount_Billed", object[27]);
			fdrData.put("Agri_Amount_Billed", object[28]);
			fdrData.put("Others_Amount_Billed", object[29]);
			fdrData.put("HT_Industrial_Amount_Collected", object[30]);
			fdrData.put("HT_Commercial_Amount_Collected", object[31]);
			fdrData.put("LT_Industrial_Amount_Collected",object[32]);
			fdrData.put("LT_Commercial_Amount_Collected", object[33]);
			fdrData.put("LT_Domestic_Amount_Collected", object[34]);
			fdrData.put("Govt_Amount_Collected", object[35]);
			fdrData.put("Agri_Amount_Collected", object[36]);
			fdrData.put("Others_Amount_Collected", object[37]);
		

			transactionAry.add(fdrData);

		}

		// transaction_data.put("transaction_data", transactionAry);

		mainObj.put("transaction_data", transactionAry);
		mainObj.put("footer", footer);

		// //System.out.println(mainObj.toString());

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mainObj);

		//System.out.println(json);

		//writeNPPData(com.bcits.mdas.utility.FilterUnit.nppDataSourceFolder, filename, json);
		return json;
	}
	
	
	
	private void writeNPPData(String path, String fileName, String data) {
		try {
			PrintWriter fileWriter = null;
			try {
				fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(path + "/" + fileName, true)));
				fileWriter.println(data);
			} catch (IOException ex) {
				throw new RuntimeException(ex.getMessage());
			} finally {
				if (fileWriter != null) {
					fileWriter.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/showGovtSchemetownValue", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> showGovtSchemetownValue(HttpServletRequest request, ModelMap model) {
		List<Object[]> list1 = null;
		List<Object[]> list2 = null;
//		System.err.println("inside-");
		String scheme = request.getParameter("scheme");
		System.err.println(scheme);
		List<Object[]> array = new ArrayList<Object[]>();

		if (scheme.equals("RAPDRP")) {
			list1 = (List<Object[]>) nppDataService.getRAPDRPTown();
			array.addAll(list1);
		} else if (scheme.equals("IPDS")) {
			list2 = (List<Object[]>) nppDataService.getIPDSTown();
			array.addAll(list2);
		}

		return array;
	}

	@RequestMapping(value = "/showNPPFeederData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> showNPPFeederData(HttpServletRequest request, ModelMap model) {
		List<Object[]> list1 = null;
		List<Object[]> list2 = null;
		//System.err.println("hiiii");
		String town = request.getParameter("town");
		//System.out.println("town ALL : " + town);
		/*String twonsString = "";
		String[] towns = town.split(",");
		for (int i = 0; i < towns.length; i++) {
			if (i < towns.length - 1) {
				twonsString += "'" + towns[i] + "',";
			} else if (i == towns.length - 1) {
				twonsString += "'" + towns[i] + "'";
			}
		}*/

		String period = request.getParameter("period");
		List<Object[]> array = new ArrayList<Object[]>();
		/*
		 * if (town.equals("RAPDRP")) { System.err.println("innnnn"); list1 =
		 * (List<Object[]>)
		 * nppDataService.getNPPReportRapdrpDetails(twonsString,period);
		 * array.addAll(list1); } else if (town.equals("IPDS")) { list2 =
		 * (List<Object[]>) nppDataService.getNPPReportipdsDetails(twonsString,period);
		 * array.addAll(list2); }
		 */
		list1 = (List<Object[]>) nppDataService.getNPPReportRapdrpDetails(town, period);
		return list1;
	}

	/*
	 * @RequestMapping(value = "/consumerReadingSummRep", method = {
	 * RequestMethod.POST,RequestMethod.GET }) public String
	 * consumerReadingSummRep(HttpServletRequest request, ModelMap model,HttpSession
	 * session) { System.err.println("Hiiii"); String officeType = (String)
	 * session.getAttribute("officeType");
	 * //System.out.println("officeType"+officeType);
	 * 
	 * String officeCode = (String) session.getAttribute("officeCode");
	 * //System.out.println("officeCode"+officeCode); String qry=""; List res = null;
	 * List<?> cirRes=null; //List<AmiLocation> circleData=new ArrayList<>();
	 * //model.put("circlevalue", ((AmiLocation) circleData).getCircle());
	 * 
	 * ////System.out.println("circlee----------"+circleData.get(0).getCircle()); try
	 * { //System.out.println("----------"); if
	 * (officeType.equalsIgnoreCase("circle")) {
	 * //System.out.println("----------++++++++");
	 * 
	 * qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a" +
	 * " WHERE circle_code = '"+ officeCode + "' ORDER BY CIRCLE"; res =
	 * entityManager.createNativeQuery(qry).getResultList();
	 * //System.out.println(res.get(0)); List circle=res; //model.put("circleName",
	 * qry);
	 * 
	 * String circleString=""; String[] cir=qry.split(","); for (int i = 0; i <
	 * cir.length; i++) { if(i<cir.length-1) { circleString += "'"+cir[i]+"',"; }
	 * else if(i==cir.length-1) { circleString += "'"+cir[i]+"'"; } }
	 * 
	 * cirRes=consumerMasterService.getCirleConsumerCount(circle); } else {
	 * cirRes=consumerMasterService.gettotalConsumerCount(); } }catch (Exception e)
	 * { // TODO: handle exception } //System.out.println(cirRes); String circleres =
	 * Arrays.toString(cirRes.toArray()).replace("[", "").replace("]", "");
	 * System.err.println(circleres); //Object [] objNew= (Object[]) res.get(0); int
	 * consumerCount=new BigInteger(cirRes.get(0).toString()).intValue();
	 * ////System.out.println(consumerCount);
	 * //model.addAttribute("consumer",consumerCount); double
	 * totalCons=((consumerCount/consumerCount)*100); model.put("totalCons",
	 * totalCons); model.put("total",circleres);
	 * //model.addAttribute("consumerCount",Math.round((consumerCount/consumerCount)
	 * *100)); return "consumerReadingSummary"; }
	 * 
	 * 
	 * @RequestMapping(value = "/getTotalConsumerDetail", method = {
	 * RequestMethod.GET, RequestMethod.POST }) public @ResponseBody List<?>
	 * getTotalConsumerDetail(HttpServletRequest request, ModelMap model) { List<?>
	 * conList=null; try { conList =consumerMasterService.getCustomerDetails();
	 * //System.out.println(" list is----"+conList); }catch (Exception e) { // TODO:
	 * handle exception } return conList;
	 * 
	 * }
	 */

	/*
	 * @RequestMapping(value = "/consumerReadingSummRep", method = {
	 * RequestMethod.POST,RequestMethod.GET }) public String
	 * consumerReadingSummRep(HttpServletRequest request, ModelMap model,HttpSession
	 * session) { String officeType = (String) session.getAttribute("officeType");
	 * String officeCode = (String) session.getAttribute("officeCode"); String
	 * month=new SimpleDateFormat("yyyyMM").format(new Date()); String qry="";
	 * Object res = null; List<?> cirRes=null; List<?> readingAvail=null; List<?>
	 * totalConsumerAvail=null; List<?> readingNotAvail=null; List<?>
	 * uploadRMS=null; List<?> pendingRMS=null; String count=null;
	 * 
	 * try { if (officeType.equalsIgnoreCase("circle")) {
	 * //System.out.println("----------++++++++"); qry =
	 * "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a" +
	 * " WHERE circle_code = '"+ officeCode + "' ORDER BY CIRCLE"; res =
	 * entityManager.createNativeQuery(qry).getSingleResult();
	 * //System.out.println(qry); Object circle=res;
	 * cirRes=consumerMasterService.getCirleConsumerCount(circle);
	 * readingAvail=consumerMasterService.getTotalCircleReadingAvailCons(circle);//
	 * total consumer of reading available
	 * totalConsumerAvail=consumerMasterService.getTotalCircleConsumerAvail(circle);
	 * //total consumer in consumer master n redaing
	 * readingNotAvail=consumerMasterService.getTotalCircleNotReadingAvailCons(
	 * circle);//total consumer of reading not available }
	 * 
	 * 
	 * else if(officeType.equalsIgnoreCase("division")) {
	 * //System.out.println("++++++++"); qry =
	 * "SELECT DISTINCT division  FROM meter_data.amilocation a" +
	 * " WHERE circle_code = '"+ officeCode + "' ORDER BY division"; res =
	 * entityManager.createNativeQuery(qry).getResultList();
	 * //System.out.println(res.get(0)); List circle=res; }
	 * 
	 * 
	 * else{ cirRes=consumerMasterService.gettotalConsumerCount();//total Consumer
	 * count
	 * readingAvail=consumerMasterService.getTotalReadingAvailCons(month);//total
	 * consumer of reading available
	 * totalConsumerAvail=consumerMasterService.getTotalConsumerAvail(month);//total
	 * consumer in consumer master n redaing
	 * readingNotAvail=consumerMasterService.getTotalNotReadingAvailCons(month);//
	 * total consumer of reading not available
	 * uploadRMS=consumerMasterService.getRMSUpload(month);
	 * pendingRMS=consumerMasterService.getRMSpending(month);
	 * //count=getSyncDataFromRMS1(); } }catch (Exception e) { // TODO: handle
	 * exception }
	 * 
	 * String circleres = Arrays.toString(cirRes.toArray()).replace("[",
	 * "").replace("]", ""); String readingAvailcount =
	 * Arrays.toString(readingAvail.toArray()).replace("[", "").replace("]", "");
	 * String totalConsumerAvailCount =
	 * Arrays.toString(totalConsumerAvail.toArray()).replace("[", "").replace("]",
	 * ""); String notreadingAvailcount =
	 * Arrays.toString(readingNotAvail.toArray()).replace("[", "").replace("]", "");
	 * //String uploadRMScount = Arrays.toString(uploadRMS.toArray()).replace("[",
	 * "").replace("]", ""); //String pendingRMScount =
	 * Arrays.toString(pendingRMS.toArray()).replace("[", "").replace("]", "");
	 * System.err.println(circleres+"--"+readingAvailcount+"--"+
	 * totalConsumerAvailCount+"--"+notreadingAvailcount); //Object [] objNew=
	 * (Object[]) res.get(0);
	 * 
	 * 
	 * int consumerCount=new BigInteger(cirRes.get(0).toString()).intValue(); int
	 * conReadingAvailCount=new
	 * BigInteger(readingAvail.get(0).toString()).intValue(); int
	 * totalConsAvailCount=new
	 * BigInteger(totalConsumerAvail.get(0).toString()).intValue(); int
	 * conReadingNotAvailCount=new
	 * BigInteger(readingNotAvail.get(0).toString()).intValue();
	 * 
	 * // //System.out.println(conReadingAvailCount+"-"+totalConsAvailCount+"-"+
	 * conReadingNotAvailCount); //int i = Integer.parseInt(input);
	 * //if(totalConsumerAvailCount!='0') //{ int totalCons=Integer.parseInt(new
	 * DecimalFormat("#").format((Double.parseDouble(totalConsumerAvailCount)/Double
	 * .parseDouble(totalConsumerAvailCount)*100))); int
	 * totalConsReading=Integer.parseInt(new
	 * DecimalFormat("#").format((Double.parseDouble(readingAvailcount)/Double.
	 * parseDouble(totalConsumerAvailCount)*100))); int
	 * totalConsNotReading=Integer.parseInt(new
	 * DecimalFormat("#").format((Double.parseDouble(notreadingAvailcount)/Double.
	 * parseDouble(totalConsumerAvailCount)*100)));
	 * 
	 * try {
	 * 
	 * //System.out.println("==========="); if (circleres.equalsIgnoreCase("0")) {
	 * model.put("total",circleres); model.put("totalCons", totalCons); // return
	 * "consumerReadingSummary";
	 * 
	 * } else if(readingAvailcount.equalsIgnoreCase("0")) {
	 * model.put("readingAvail",readingAvailcount);
	 * model.put("totalReading",totalConsReading); // return
	 * "consumerReadingSummary";
	 * 
	 * } else if(totalConsumerAvailCount.equalsIgnoreCase("0")) {
	 * model.put("totalCustAvail",totalConsumerAvailCount);
	 * model.put("total",totalConsumerAvailCount); //return"consumerReadingSummary";
	 * 
	 * }
	 * 
	 * else if(notreadingAvailcount.equalsIgnoreCase("0")) {
	 * model.put("readingNotAvail",notreadingAvailcount);
	 * model.put("totalNotReading", totalConsNotReading); return
	 * "consumerReadingSummary"; }
	 * 
	 * } catch (Exception e) { // TODO: handle exception } //int
	 * totalCons=Integer.parseInt(new
	 * DecimalFormat("#").format((Double.parseDouble(totalConsumerAvailCount)/Double
	 * .parseDouble(totalConsumerAvailCount)*100))); // int
	 * totalConsReading=Integer.parseInt(new
	 * DecimalFormat("#").format((Double.parseDouble(readingAvailcount)/Double.
	 * parseDouble(totalConsumerAvailCount)*100))); //int
	 * totalConsNotReading=Integer.parseInt(new
	 * DecimalFormat("#").format((Double.parseDouble(notreadingAvailcount)/Double.
	 * parseDouble(totalConsumerAvailCount)*100))); // int
	 * totalUploadRMS=Integer.parseInt(new
	 * DecimalFormat("#").format((Double.parseDouble(uploadRMScount)/Double.
	 * parseDouble(totalConsumerAvailCount)*100))); //int
	 * totalpendingRMS=Integer.parseInt(new
	 * DecimalFormat("#").format((Double.parseDouble(pendingRMScount)/Double.
	 * parseDouble(totalConsumerAvailCount)*100))); // double
	 * totalCons=((consumerCount/consumerCount)*100); // double
	 * totalConsReading=((conReadingAvailCount/totalConsAvailCount)*100); //int
	 * totalConsReading=Integer.parseInt(conReadingAvailCount)/(totalConsAvailCount)
	 * *100); //double
	 * totalConsNotReading=((conReadingNotAvailCount/totalConsAvailCount)*100);
	 * model.put("totalCons", totalCons);
	 * model.put("readingAvail",readingAvailcount);
	 * model.put("readingNotAvail",notreadingAvailcount);
	 * model.put("total",totalConsumerAvailCount); model.put("totalReading",
	 * totalConsReading); model.put("totalNotReading", totalConsNotReading);
	 * //model.put("rmsCountper",totalUploadRMS);
	 * //model.put("rmsCount",uploadRMScount);
	 * //model.put("rmspendingper",totalpendingRMS);
	 * //model.put("rmspending",pendingRMScount); model.put("month",month);
	 * //model.addAttribute("consumerCount",Math.round((consumerCount/consumerCount)
	 * *100)); return "consumerReadingSummary";
	 * 
	 * }
	 */
	int updateflag=0;
	@RequestMapping(value = "/consumerReadingSummRep", method = { RequestMethod.POST, RequestMethod.GET })
    public String consumerReadingSummRep(HttpServletRequest request, ModelMap model, HttpSession session) {
        String officeType = (String) session.getAttribute("officeType");
        String officeCode = (String) session.getAttribute("officeCode");
       // //System.out.println(officeType + "--" + officeCode);
        String month = "";
        String month1 = request.getParameter("month");
       // System.err.println(month1);
        if (month1 == null) {
            month = new SimpleDateFormat("yyyyMM").format(new Date());
        } else {
            month = month1.replaceAll("[\\s\\-()]", "");
        }

       // System.err.println(month);
        String qry = "";
        List res = null;
        List<?> cirRes = null;
        List<?> readingAvail = null;
        List<?> totalConsumerAvail = null;
        List<?> readingNotAvail = null;
        List<?> uploadRMS = null;
        List<?> pendingRMS = null;
        String count = null;

        model.put("billmonth", month);
        try {
            if (officeType.equalsIgnoreCase("circle")) {
                //System.out.println("----------++++++++");
                qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a" + " WHERE circle_code = '" + officeCode
                        + "' ORDER BY CIRCLE";
                res = entityManager.createNativeQuery(qry).getResultList();
               // //System.out.println(res.get(0));
                List circle = res;
                // cirRes=consumerMasterService.getCirleConsumerCount(circle);
                totalConsumerAvail = consumerMasterService.getTotalCircleConsumerAvail(circle, month);
                readingAvail = consumerMasterService.getTotalCircleReadingAvailCons(circle, month);
                readingNotAvail = consumerMasterService.getTotalCircleNotReadingAvailCons(circle, month);
                uploadRMS = consumerMasterService.getCircleRMSUpload(circle, month);
                pendingRMS = consumerMasterService.getCircleRMSpending(circle, month);
            }

            else if (officeType.equalsIgnoreCase("division")) {
                //System.out.println("++++++++");
                qry = "SELECT DISTINCT division ,circle  FROM meter_data.amilocation a" + " WHERE division_code = '"
                        + officeCode + "' ORDER BY division";
                List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
                ////System.out.println(qry);
                String cicle = "";
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < result.size(); i++) {
                    Object[] obj = result.get(i);
                    String division = obj[0] + "";
                    builder.append(division + ",");
                    if (i == 0) {
                        cicle = obj[1] + "";
                    }

                }
                String divisionlist = builder.substring(0, builder.length() - 1);
               // System.err.println("divisionlist : " + divisionlist);

                totalConsumerAvail = consumerMasterService.getTotalDivisionConsumerAvail(cicle, divisionlist, month);
                readingAvail = consumerMasterService.getTotalDivisionReadingAvailCons(cicle, divisionlist, month);
                readingNotAvail = consumerMasterService.getTotalDivisionNotReadingAvailCons(cicle, divisionlist, month);
                uploadRMS = consumerMasterService.getDivisionRMSUpload(cicle, divisionlist, month);
                pendingRMS = consumerMasterService.getDivisionRMSpending(cicle, divisionlist, month);
//                //System.out.println(totalConsumerAvail);
            }

            else if (officeType.equalsIgnoreCase("sub division")) {
//                //System.out.println("++++++++");
                qry = "SELECT DISTINCT division ,circle,subdivision  FROM meter_data.amilocation a"
                        + " WHERE sitecode = '" + officeCode + "' ORDER BY subdivision";
                List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
                //System.out.println(qry);
                String cicle = "";
                String divisionlist = "";
                String subdivlist = "";
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < result.size(); i++) {
                    Object[] obj = result.get(i);
                    divisionlist = obj[0] + "";
                    subdivlist = obj[2] + "";
                    // builder.append(division+",");
                    // builder.append(subdiv+",");
                    if (i == 0) {
                        cicle = obj[1] + "";
                    }

                }
                

                totalConsumerAvail = consumerMasterService.getTotalSubdivConsumerAvail(cicle, divisionlist, subdivlist,
                        month);// total consumer in consumer master n redaing
                readingAvail = consumerMasterService.getTotalSubdivReadingAvailCons(cicle, divisionlist, subdivlist,
                        month);// total consumer of reading available
                readingNotAvail = consumerMasterService.getTotalSubdivNotReadingAvailCons(cicle, divisionlist,
                        subdivlist, month);// total consumer of reading not available
                uploadRMS = consumerMasterService.getSubdivRMSUpload(cicle, divisionlist, subdivlist, month);
                pendingRMS = consumerMasterService.getSubdivRMSpending(cicle, divisionlist, subdivlist, month);
        //        //System.out.println(totalConsumerAvail);
            }

            else {
                // cirRes=consumerMasterService.gettotalConsumerCount();//total Consumer count
                totalConsumerAvail = consumerMasterService.gettotalConsumerCount();
                readingAvail = consumerMasterService.getTotalReadingAvailCons(month);
                readingNotAvail = consumerMasterService.getTotalNotReadingAvailCons(month);
                uploadRMS = consumerMasterService.getRMSUpload(month);
                pendingRMS = consumerMasterService.getRMSpending(month);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        // String circleres = Arrays.toString(cirRes.toArray()).replace("[",
        // "").replace("]", "");
        String totalConsumerAvailCount = Arrays.toString(totalConsumerAvail.toArray()).replace("[", "").replace("]","");
        String readingAvailcount = Arrays.toString(readingAvail.toArray()).replace("[", "").replace("]", "");
        String notreadingAvailcount = Arrays.toString(readingNotAvail.toArray()).replace("[", "").replace("]", "");
        String uploadRMScount = Arrays.toString(uploadRMS.toArray()).replace("[", "").replace("]", "");
        String pendingRMScount = Arrays.toString(pendingRMS.toArray()).replace("[", "").replace("]", "");

//        //System.out.println(totalConsumerAvailCount + "-" + readingAvailcount + "-" + notreadingAvailcount + "-"
//                + uploadRMScount + "-" + pendingRMScount);
        try {
            if (totalConsumerAvailCount.equalsIgnoreCase("0")) {
                String a="1";
                int totalCons = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(totalConsumerAvailCount)/ Double.parseDouble(a) * 100)));
                int totalConsReading = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(readingAvailcount) / Double.parseDouble(a) * 100)));
                int totalConsNotReading = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(notreadingAvailcount)/ Double.parseDouble(a) * 100)));
                int totalUploadRMS = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(uploadRMScount) / Double.parseDouble(a) * 100)));
                int totalpendingRMS = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(pendingRMScount) / Double.parseDouble(a) * 100)));
                model.put("total", totalConsumerAvailCount);
                model.put("readingAvail", readingAvailcount);
                model.put("readingNotAvail", notreadingAvailcount);
                model.put("rmsCount", uploadRMScount);
                model.put("rmspending", pendingRMScount);
                // percent
                model.put("totalCons", totalCons);
                model.put("totalReading", totalConsReading);
                model.put("totalNotReading", totalConsNotReading);
                model.put("rmsCountper", totalUploadRMS);
                model.put("rmspendingper", totalpendingRMS);
                

            }else {
                int totalCons = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(totalConsumerAvailCount)/ Double.parseDouble(totalConsumerAvailCount) * 100)));
                int totalConsReading = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(readingAvailcount) / Double.parseDouble(totalConsumerAvailCount) * 100)));
                int totalConsNotReading = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(notreadingAvailcount)/ Double.parseDouble(totalConsumerAvailCount) * 100)));
                int totalUploadRMS = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(uploadRMScount) / Double.parseDouble(totalConsumerAvailCount) * 100)));
                int totalpendingRMS = Integer.parseInt(new DecimalFormat("#").format((Double.parseDouble(pendingRMScount) / Double.parseDouble(totalConsumerAvailCount) * 100)));

                // total count
                model.put("total", totalConsumerAvailCount);
                model.put("readingAvail", readingAvailcount);
                model.put("readingNotAvail", notreadingAvailcount);
                model.put("rmsCount", uploadRMScount);
                model.put("rmspending", pendingRMScount);
                // percent
                model.put("totalCons", totalCons);
                model.put("totalReading", totalConsReading);
                model.put("totalNotReading", totalConsNotReading);
                model.put("rmsCountper", totalUploadRMS);
                model.put("rmspendingper", totalpendingRMS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "consumerReadingSummary";

    }
	
	
	@RequestMapping(value = "/getTotalConsumerDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getTotalConsumerDetail(HttpServletRequest request, ModelMap model,
			HttpSession session) {
		List<?> conList = null;
		String officeType = (String) session.getAttribute("officeType");

		String officeCode = (String) session.getAttribute("officeCode");
		String qry = "";
		List res = null;
		List<?> cirRes = null;
		String month = "";
		String month1 = request.getParameter("month");
//		System.err.println(month1);
		if (month1 == null) {
			month = new SimpleDateFormat("yyyyMM").format(new Date());
		} else {
			month = month1.replaceAll("[\\s\\-()]", "");
		}

		try {
			if (officeType.equalsIgnoreCase("circle")) {

				qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a" + " WHERE circle_code = '" + officeCode
						+ "' ORDER BY CIRCLE";
				res = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(res.get(0));
				List circle = res;
				conList = consumerMasterService.getcircleCustomerDetails(circle, month);
			} else if (officeType.equalsIgnoreCase("division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle  FROM meter_data.amilocation a" + " WHERE division_code = '"
						+ officeCode + "' ORDER BY division";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					String division = obj[0] + "";
					builder.append(division + ",");
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				String divisionlist = builder.substring(0, builder.length() - 1);
//				System.err.println("divisionlist : " + divisionlist);
				conList = consumerMasterService.getDivisionCustomerDetails(cicle, divisionlist, month);
			} else if (officeType.equalsIgnoreCase("sub division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle,subdivision  FROM meter_data.amilocation a"
						+ " WHERE sitecode = '" + officeCode + "' ORDER BY subdivision";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				String divisionlist = "";
				String subdivlist = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					divisionlist = obj[0] + "";
					subdivlist = obj[2] + "";
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				conList = consumerMasterService.getSubdivCustomerDetails(cicle, divisionlist, subdivlist, month);
			} else {

				conList = consumerMasterService.getCustomerDetails();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return conList;

	}

	@RequestMapping(value = "/getReadingConsumerDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getReadingConsumerDetail(HttpServletRequest request, ModelMap model,
			HttpSession session) {
		List<?> readingList = null;
		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		String qry = "";
		List res = null;
		String month = "";
		String month1 = request.getParameter("month");
//		System.err.println(month1);
		if (month1 == null) {
			month = new SimpleDateFormat("yyyyMM").format(new Date());
		} else {
			month = month1.replaceAll("[\\s\\-()]", "");
		}
		try {
			if (officeType.equalsIgnoreCase("circle")) {

				qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a" + " WHERE circle_code = '" + officeCode
						+ "' ORDER BY CIRCLE";
				res = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(res.get(0));
				List circle = res;
				readingList = consumerMasterService.getcircleReadingDetails(circle, month);
			} else if (officeType.equalsIgnoreCase("division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle  FROM meter_data.amilocation a" + " WHERE division_code = '"
						+ officeCode + "' ORDER BY division";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					String division = obj[0] + "";
					builder.append(division + ",");
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				String divisionlist = builder.substring(0, builder.length() - 1);
//				System.err.println("divisionlist : " + divisionlist);
				readingList = consumerMasterService.getDivisionReadingDetails(cicle, divisionlist, month);
			} else if (officeType.equalsIgnoreCase("sub division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle,subdivision  FROM meter_data.amilocation a"
						+ " WHERE sitecode = '" + officeCode + "' ORDER BY subdivision";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				String divisionlist = "";
				String subdivlist = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					divisionlist = obj[0] + "";
					subdivlist = obj[2] + "";
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				readingList = consumerMasterService.getSubdivReadingDetails(cicle, divisionlist, subdivlist, month);
			} else {

				readingList = consumerMasterService.getReadingCustomerDetails(month);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return readingList;
	}

	@RequestMapping(value = "/getnotReadingConsumerDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getnotReadingConsumerDetail(HttpServletRequest request, ModelMap model,
			HttpSession session) {
		List<?> notreadingList = null;
		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		String qry = "";
		List res = null;
		String month = "";
		String month1 = request.getParameter("month");
//		System.err.println(month1);
		if (month1 == null) {
			month = new SimpleDateFormat("yyyyMM").format(new Date());
		} else {
			month = month1.replaceAll("[\\s\\-()]", "");
		}
		try {
			if (officeType.equalsIgnoreCase("circle")) {

				qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a" + " WHERE circle_code = '" + officeCode
						+ "' ORDER BY CIRCLE";
				res = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(res.get(0));
				List circle = res;
				notreadingList = consumerMasterService.getCircleNotReadingCustomerDetails(circle, month);
			} else if (officeType.equalsIgnoreCase("division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle  FROM meter_data.amilocation a" + " WHERE division_code = '"
						+ officeCode + "' ORDER BY division";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					String division = obj[0] + "";
					builder.append(division + ",");
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				String divisionlist = builder.substring(0, builder.length() - 1);
//				System.err.println("divisionlist : " + divisionlist);
				notreadingList = consumerMasterService.getDivivsionNotReadingCustomerDetails(cicle, divisionlist,
						month);
			} else if (officeType.equalsIgnoreCase("sub division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle,subdivision  FROM meter_data.amilocation a"
						+ " WHERE sitecode = '" + officeCode + "' ORDER BY subdivision";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				String divisionlist = "";
				String subdivlist = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					divisionlist = obj[0] + "";
					subdivlist = obj[2] + "";
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				notreadingList = consumerMasterService.getSubdivNotReadingCustomerDetails(cicle, divisionlist,
						subdivlist, month);
			} else {
				notreadingList = consumerMasterService.getNotReadingCustomerDetails(month);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return notreadingList;
	}

	@RequestMapping(value = "/getRMSUploadDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getRMSUploadDetail(HttpServletRequest request, HttpSession session) {
		List<?> rmsUploadList = null;
		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		String qry = "";
		List res = null;
		String month = "";
		String month1 = request.getParameter("month");
		System.err.println(month1);
		if (month1 == null) {
			month = new SimpleDateFormat("yyyyMM").format(new Date());
		} else {
			month = month1.replaceAll("[\\s\\-()]", "");
		}
		try {
			if (officeType.equalsIgnoreCase("circle")) {

				qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a" + " WHERE circle_code = '" + officeCode
						+ "' ORDER BY CIRCLE";
				res = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(res.get(0));
				List circle = res;
				rmsUploadList = consumerMasterService.getCircleRMSUploadDetails(circle, month);
			} else if (officeType.equalsIgnoreCase("division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle  FROM meter_data.amilocation a" + " WHERE division_code = '"
						+ officeCode + "' ORDER BY division";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					String division = obj[0] + "";
					builder.append(division + ",");
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				String divisionlist = builder.substring(0, builder.length() - 1);
				//System.err.println("divisionlist : " + divisionlist);
				rmsUploadList = consumerMasterService.getDivisionRMSUploadDetails(cicle, divisionlist, month);
			} else if (officeType.equalsIgnoreCase("sub division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle,subdivision  FROM meter_data.amilocation a"
						+ " WHERE sitecode = '" + officeCode + "' ORDER BY subdivision";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				String divisionlist = "";
				String subdivlist = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					divisionlist = obj[0] + "";
					subdivlist = obj[2] + "";
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				rmsUploadList = consumerMasterService.getSubdivRMSUploadDetails(cicle, divisionlist, subdivlist, month);
			} else {
				rmsUploadList = consumerMasterService.getRMSUploadDetails(month);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rmsUploadList;
	}

	@RequestMapping(value = "/getRMSPendingDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getRMSPendingDetail(HttpServletRequest request, HttpSession session) {
		List<?> rmsPendingList = null;
		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		String qry = "";
		List res = null;
		String month = "";
		String month1 = request.getParameter("month");
		//System.err.println(month1);
		if (month1 == null) {
			month = new SimpleDateFormat("yyyyMM").format(new Date());
		} else {
			month = month1.replaceAll("[\\s\\-()]", "");
		}
		try {
			if (officeType.equalsIgnoreCase("circle")) {

				qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a" + " WHERE circle_code = '" + officeCode
						+ "' ORDER BY CIRCLE";
				res = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(res.get(0));
				List circle = res;
				rmsPendingList = consumerMasterService.getCircleRMSPendingDetails(circle, month);
			} else if (officeType.equalsIgnoreCase("division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle  FROM meter_data.amilocation a" + " WHERE division_code = '"
						+ officeCode + "' ORDER BY division";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					String division = obj[0] + "";
					builder.append(division + ",");
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				String divisionlist = builder.substring(0, builder.length() - 1);
				//System.err.println("divisionlist : " + divisionlist);
				rmsPendingList = consumerMasterService.getDivisionRMSPendingDetails(cicle, divisionlist, month);
			} else if (officeType.equalsIgnoreCase("sub division")) {
				//System.out.println("++++++++");
				qry = "SELECT DISTINCT division ,circle,subdivision  FROM meter_data.amilocation a"
						+ " WHERE sitecode = '" + officeCode + "' ORDER BY subdivision";
				List<Object[]> result = entityManager.createNativeQuery(qry).getResultList();
				//System.out.println(qry);
				String cicle = "";
				String divisionlist = "";
				String subdivlist = "";
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < result.size(); i++) {
					Object[] obj = result.get(i);
					divisionlist = obj[0] + "";
					subdivlist = obj[2] + "";
					if (i == 0) {
						cicle = obj[1] + "";
					}

				}
				rmsPendingList = consumerMasterService.getSubdivRMSPendingDetails(cicle, divisionlist, subdivlist,
						month);
			} else {
				rmsPendingList = consumerMasterService.getRMSPendingDetails(month);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rmsPendingList;
	}

	@RequestMapping(value = "/refreshNonCommunicationReason/{date}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String refreshNonCommunicationReason(@PathVariable String date, HttpServletRequest request,
			ModelMap model) {

		try {
			generateNonCommunicationReasonData(date);
			return "succ";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}

	}

	void generateNonCommunicationReasonData(String date) throws java.text.ParseException {

		String qry = "SELECT ndate,sdocode,fdrcategory,accno,\n"
				+ "CASE WHEN fdrcategory in ('HT','LT') THEN customer_name ELSE fdrname END as loc_name,\n"
				+ "hes_type,mtrno, modem_sl_no,\n"
				+ "(CASE WHEN e.event_status='R' THEN 'OTHERS' ELSE e.category END) as reason\n" + "FROM\n" + "(\n"
				+ "SELECT CAST('" + date
				+ "' as text) as ndate,sdocode,fdrcategory,accno,customer_name, fdrname,hes_type,mtrno, modem_sl_no,\n"
				+ "(SELECT event_code FROM meter_data.events WHERE meter_number=mtrno AND event_time=(SELECT max(event_time) FROM meter_data.events WHERE meter_number=mtrno)) as event_code\n"
				+ "FROM meter_data.master_main WHERE mtrno not in \n" + "(\n"
				+ "SELECT meter_number FROM meter_data.modem_communication WHERE date='" + date + "'\n" + ")\n"
				+ ")a LEFT JOIN meter_data.event_master e ON a.event_code=CAST(e.event_code as TEXT)";

		List<?> list = entityManager.createNativeQuery(qry).getResultList();
		if (list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] obj = (Object[]) iterator.next();

				NonCommunicationReason reason = new NonCommunicationReason();

				reason.setKeyNonCommunication(new KeyNonCommunication(String.valueOf(obj[6]),
						new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(obj[0]))));
				reason.setOffice_id(String.valueOf(obj[1]));
				reason.setLoc_type(String.valueOf(obj[2]));
				reason.setLoc_idenity(String.valueOf(obj[3]));
				reason.setLoc_name(String.valueOf(obj[4]));
				reason.setHes_type(String.valueOf(obj[5]));
				reason.setModem_sl_no(String.valueOf(obj[5]));
				reason.setReason_type(
						"null".equals(String.valueOf(obj[8])) ? "OTHERS" : String.valueOf(obj[8]).toUpperCase());
				reason.setTime_stamp(new Date());
				nonCommunicationReasonService.customupdateBySchema(reason, "postgresMdas");

			}
		}

	}

	@Scheduled(cron = "0 10 1 * * ?")
	void generateNonCommunicationReasonDataCron() {

		String qry = "SELECT ndate,sdocode,fdrcategory,accno,\n"
				+ "CASE WHEN fdrcategory in ('HT','LT') THEN customer_name ELSE fdrname END as loc_name,\n"
				+ "hes_type,mtrno, modem_sl_no,\n"
				+ "(CASE WHEN e.event_status='R' THEN 'OTHERS' ELSE e.category END) as reason\n" + "FROM\n" + "(\n"
				+ "SELECT CURRENT_DATE-1 as ndate,sdocode,fdrcategory,accno,customer_name, fdrname,hes_type,mtrno, modem_sl_no,\n"
				+ "(SELECT event_code FROM meter_data.events WHERE meter_number=mtrno AND event_time=(SELECT max(event_time) FROM meter_data.events WHERE meter_number=mtrno)) as event_code\n"
				+ "FROM meter_data.master_main WHERE mtrno not in \n" + "(\n"
				+ "SELECT meter_number FROM meter_data.modem_communication WHERE date=CURRENT_DATE-1\n" + ")\n"
				+ ")a LEFT JOIN meter_data.event_master e ON a.event_code=CAST(e.event_code as TEXT)";

		List<?> list = entityManager.createNativeQuery(qry).getResultList();
		if (list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] obj = (Object[]) iterator.next();

				NonCommunicationReason reason = new NonCommunicationReason();
				try {
					reason.setKeyNonCommunication(new KeyNonCommunication(String.valueOf(obj[6]),
							new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(obj[0]))));
					reason.setOffice_id(String.valueOf(obj[1]));
					reason.setLoc_type(String.valueOf(obj[2]));
					reason.setLoc_idenity(String.valueOf(obj[3]));
					reason.setLoc_name(String.valueOf(obj[4]));
					reason.setHes_type(String.valueOf(obj[5]));
					reason.setModem_sl_no(String.valueOf(obj[5]));
					reason.setReason_type(
							"null".equals(String.valueOf(obj[8])) ? "OTHERS" : String.valueOf(obj[8]).toUpperCase());
					reason.setTime_stamp(new Date());
					nonCommunicationReasonService.customupdateBySchema(reason, "postgresMdas");
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	@RequestMapping(value = "/consMonthReadingReport", method = { RequestMethod.POST, RequestMethod.GET })
	public String consumerMonthlyReadingReport(ModelMap model, HttpServletRequest request, HttpSession session) {
		// List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String officeType = (String) session.getAttribute("officeType");
		try {
			String sql = "SELECT DISTINCT zone from meter_data.amilocation order by zone";
			List<?> list = entityManager.createNativeQuery(sql).getResultList();
			model.put("zoneList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "consMonthReadingReport";
	}

	@RequestMapping(value = "/getConsMonthlyReportData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getConsMonthlyReportData(ModelMap model, HttpServletRequest request) {

		model.addAttribute("results", "notDisplay");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division").trim();
		String subdivision = request.getParameter("sdoCode").trim();
		String acno = request.getParameter("acno").trim();
		String kno = request.getParameter("kno").trim();
		String mtrno = request.getParameter("mtrno").trim();
		String month = request.getParameter("month").trim();
		// //System.out.println("month="+month);

	//	String sql = VEEController.getSubdivisionQuery(circle, division, subdivision);
		String sql="SELECT distinct sitecode from meter_data.amilocation  WHERE circle LIKE  '"+circle+"' AND division LIKE  '"+division+"' AND subdivision LIKE  '"+subdivision+"' ";

		////System.out.println("sql==" + sql);
		List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		String subcode = "";
		for (Object item : sdocode) {
			subcode += "'" + item + "',";
		}
		if (subcode.endsWith(",")) {
			subcode = subcode.substring(0, subcode.length() - 1);
		}
		// //System.out.println("subcode= "+subcode);
		String sqlqry = "select meterno,kno,accno,name,sdocode,mf from meter_data.consumermaster where sdocode in ("
				+ subcode + ")";
		if (!acno.isEmpty()) {
			sqlqry += " and accno='" + acno + "'";
		}
		if (!kno.isEmpty()) {
			sqlqry += " and kno='" + kno + "'";
		}
		if (!mtrno.isEmpty()) {
			sqlqry += " and meterno='" + mtrno + "'";
		}

		String finalQury = "select distinct a.accno,a.kno,a.name,b.meterno,a.mf,b.billmonth,b.rdate,b.kwh,b.kvah,b.kva FROM\r\n"
				+ "(" + sqlqry + ")a,\r\n"
				+ "(select meterno,billmonth,rdate,kwh,kvah,kva FROM meter_data.consumerreadings WHERE billmonth='"
				+ month + "')b\r\n" + "WHERE a.meterno=b.meterno ORDER BY meterno";

		//System.err.println("Final query---:" + finalQury);
		List<?> list = entityManager.createNativeQuery(finalQury).getResultList();
		return list;

	}

/*	@RequestMapping(value = "/getPFCreportD4data", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getPFCreportD4data(ModelMap model, HttpServletRequest request) {
		List<?> list = null;
		String town = request.getParameter("town");
		String period = request.getParameter("period");
//		System.err.println(town);
		String twonsString = "";
		String[] towns = town.split(",");
		
		
		for (int i = 0; i < towns.length; i++) {
			if (i < towns.length - 1) {
				twonsString += "'" + towns[i] + "',";
			} else if (i == towns.length - 1) {
				twonsString += "'" + towns[i] + "'";
			}
		}
		try {
			list = pfcservice.getPfcD4RepData(twonsString, period);
			// System.err.println(list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;

	}*/

	
		@RequestMapping(value = "/getPFCreportD4data", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getPFCreportD4data(@RequestParam String period,@RequestParam("TownId[]") String[] TownIdMultiple,ModelMap model, HttpServletRequest request) {
		List<?> list = null;
		/*String town = request.getParameter("town");
		String period = request.getParameter("period");*/
	/*	System.err.println(town);
		String twonsString = "";
		String[] towns = town.split(",");
		
		
		for (int i = 0; i < towns.length; i++) {
			if (i < towns.length - 1) {
				twonsString += "'" + towns[i] + "',";
			} else if (i == towns.length - 1) {
				twonsString += "'" + towns[i] + "'";
			}
		}*/
		try {
			list = pfcservice.getPfcD4RepData(TownIdMultiple, period);
			// System.err.println(list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;

	}
	
	
	@RequestMapping(value = "/suspectedpowertheftreport", method = { RequestMethod.POST, RequestMethod.GET })
	public String suspectedPowerTheftReport(ModelMap model, HttpServletRequest request, HttpSession session) {
		// List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		String officeType = (String) session.getAttribute("officeType");
		// //System.out.println("officeType="+officeType);
//		try {
//			String sql = "SELECT DISTINCT zone from meter_data.amilocation order by zone";
//			List<?> list = entityManager.createNativeQuery(sql).getResultList();
//			model.put("zoneList", list);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return "powerTheftReport";
	}

	@RequestMapping(value = "/getPowerTheftReportData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getPowerTheftReportData(ModelMap model, HttpServletRequest request,
			HttpSession session) {

		List<Object[]> list = null;
		List<Object[]> list2 = null;
		List<Map<String, Object>> alist = new ArrayList<Map<String, Object>>();
		String circle = request.getParameter("circle");
		String division = request.getParameter("division").trim();
		String subdivision = request.getParameter("sdoCode").trim();
		// //System.out.println("circle="+circle+" division="+division+" subdivision=
		// "+subdivision);
		try {
			String sql = "SELECT cm.meterno FROM meter_data.consumermaster cm,meter_data.amilocation ami \r\n"
					+ "WHERE cm.consumerstatus in ('P','D') and CAST(cm.sdocode as text) = CAST(ami.sitecode as text) \r\n"
					+ "and ami.subdivision='" + subdivision + "'";
			list = entityManager.createNativeQuery(sql).getResultList();
			String meter = "";
			if (!list.isEmpty()) {
				for (Object item : list) {
					meter += "'" + (String) (item) + "',";
				}
			}
			if (meter.endsWith(",")) {
				meter = meter.substring(0, meter.length() - 1);
			}
		//	 //System.out.println("meterno==="+meter);

//			String sql1 = "select meter_number,to_char(rdate,'YYYY-MM-DD') as date,max(kwh) as kwh from meter_data.amiinstantaneous \r\n" + 
//					"where meter_number in ("+meter+") and rdate>=(CURRENT_DATE-interval '6 days') GROUP BY to_char(rdate,'YYYY-MM-DD'),meter_number \r\n" + 
//					"ORDER BY to_char(rdate,'YYYY-MM-DD') desc";

			String sql1 = "SELECT kno,accno,name,consumerstatus,meterno,mf,a.* FROM\r\n"
					+ "(SELECT meter_number, (\"max\"(kwh)- \"min\"(kwh)) as consumption, string_agg(CAST(kwh as TEXT), ',') as com_val,  \r\n"
					+ "string_agg(CAST(date as TEXT), ',') as date_val  FROM\r\n"
					+ "(select meter_number,date(rdate) as date,max(kwh) as kwh from meter_data.amiinstantaneous \r\n"
					+ "where meter_number in (" + meter
					+ ") and rdate>= (CURRENT_DATE-interval '6 days') GROUP BY meter_number,date(rdate)  ORDER BY meter_number,date(rdate) desc\r\n"
					+ ")q GROUP BY meter_number)a,\r\n"
					+ "(select kno,accno,name,consumerstatus,meterno,mf FROM meter_data.consumermaster where meterno in ("
					+ meter + "))b\r\n" + "WHERE a.meter_number=b.meterno AND mf*consumption>0;";
			list2 = entityManager.createNativeQuery(sql1).getResultList();
			if (!list2.isEmpty()) {
				for (Object[] item : list2) {
					Map<String, Object> jmp = new HashMap<>();
					jmp.put("kno", (String) item[0]);
					jmp.put("accno", (String) item[1]);
					jmp.put("name", (String) item[2]);
					jmp.put("consumerstatus", (String) item[3]);
					jmp.put("meterno", (String) item[4]);
					jmp.put("mf", (String) item[5].toString());
					jmp.put("consumption", (String) item[7].toString());
					String dateArray[] = item[9].toString().split(",");
					for (int i = 0; i < dateArray.length; i++) {
						jmp.put("Date" + i, dateArray[i]);
					}
					String date_val[] = item[8].toString().split(",");
					for (int j = 0; j < date_val.length; j++) {
						jmp.put("KwhDay" + j, date_val[j]);
					}
					alist.add(jmp);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alist;
	}

	@RequestMapping(value = "/suspectedtamperreport", method = RequestMethod.GET)
	public String criticalalarmnew(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<?> circlelist = masterMainService.getallcirclelist();
//		System.err.println("=================>" + circlelist.size());
		model.put("cirlelist", circlelist);

		// List<Object[]> criticaldata=masterMainService.getALLcriticaldata();
		// model.put("criticaldata",criticaldata);

		return "suspectedTamperReport";

	}

	@RequestMapping(value = "/showsubdivbycircle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object showsubdivbycircle(HttpServletRequest request, HttpSession session) {

		return masterMainService.getSubdivisionbycircle(request.getParameter("circle"));

	}

	@RequestMapping(value = "/feederHealthReport", method = { RequestMethod.POST, RequestMethod.GET })
	public String feederHealthReport(ModelMap model, HttpServletRequest request, HttpSession session) {

		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		return "feederHealthReport";
	}

	@RequestMapping(value = "/getfeederHealthData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getFeederHealthReport(HttpServletRequest request) {
		List<Object[]> list = new ArrayList<>();
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String rdngmnth = request.getParameter("rdngmnth");
		list = feederHealthService.getFeederHealthReport(zone,circle, rdngmnth,town);
		return list;
	}

	
	public String getSyncDataFromRMS1() {
		//System.out.println(" ---calling getSyncDataFromRMS--- ");

		String url = "http://1.23.144.187:8081/bsmartjvvnl/sendingMeteSynchDataToAMI";
		String serverResponse = "";
		try {
			RestTemplate template = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			serverResponse = template.getForObject(builder.build().encode().toUri(), String.class);
			//System.out.println("--sererResponse--" + serverResponse);
			if (serverResponse == null || "null".equals(serverResponse) || "".equals(serverResponse)) {
				return "0";
			} else {
				return serverResponse;
			}
		} catch (Exception e) {
			return "0";
		}
	}

	
	@RequestMapping(value = "/testGeneric", method = { RequestMethod.POST, RequestMethod.GET })
	public String testGeneric(HttpServletRequest request) {

		return "genericHierarchy1";
	}


	public void generateLoadSurveyReportByMonth(List<?> list, LoadAvailabilityRptService loadAvailabilityRptService,
			String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (list.size() > 0) {
			for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
				Object[] obj = (Object[]) iterator2.next();

				LoadAvailabilityReportEntity entity = new LoadAvailabilityReportEntity();
				entity.setZone(String.valueOf(obj[0]));
				entity.setCircle(String.valueOf(obj[1]));
				entity.setDivision(String.valueOf(obj[2]));
				entity.setSubdivision(String.valueOf(obj[3]));
				entity.setSubstation(String.valueOf(obj[4]));
				entity.setFdrname(String.valueOf(obj[5]));

				try {
					entity.setKeyLoadAvl(new KeyLoadAvl(String.valueOf(obj[6]), df.parse(date)));
				} catch (java.text.ParseException e1) {
					e1.printStackTrace();
				}

				entity.setLoad_count(Integer.parseInt(String.valueOf(obj[7])));
				entity.setLoad_interval(Integer.parseInt(String.valueOf(obj[8])));

				try {
					loadAvailabilityRptService.update(entity);
				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}
	}

	
	/*
	 * @RequestMapping(value = "/dtHealthReport", method = { RequestMethod.POST,
	 * RequestMethod.GET }) public String dtHealthReport(ModelMap model,
	 * HttpServletRequest request, HttpSession session) {
	 * 
	 * List<?> zoneList = feederdetailsservice.getDistinctZone();
	 * model.put("zoneList", zoneList); return "dtHealthReport"; }
	 * 
	 * @RequestMapping(value = "/getDTHealthData", method = { RequestMethod.POST,
	 * RequestMethod.GET }) public @ResponseBody Object
	 * getDTHealthData(HttpServletRequest request) { List<?> list = new
	 * ArrayList<>(); String subdiv = request.getParameter("subdiv"); String
	 * rdngmnth = request.getParameter("rdngmnth"); String circle =
	 * request.getParameter("circle"); String division =
	 * request.getParameter("division"); list =
	  dtHealthService.getDTHealthReport(subdiv, rdngmnth,circle,division); return
	 * list; }
	 */ 


	@RequestMapping(value = "/test2", method = { RequestMethod.POST, RequestMethod.GET })
	public String test2(HttpServletRequest request) {

		return "test2";
	}
	@RequestMapping(value = "/multipleMeterDTreport", method = { RequestMethod.POST, RequestMethod.GET })
	public String multipleMeterDTreport(HttpServletRequest request, Model model ) {
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("results", "notDisplay");
		return "multipleMeterDTreport";
	}
	
	@RequestMapping(value = "/getDtTpIDbyTown", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getDtTpIDbyTown(@RequestParam String townCode, HttpServletRequest request, Model model ) {
		
		List dtTpIDbyTowncode = feederMasterService.getdtTpIDbyTowncode(townCode);
		return dtTpIDbyTowncode;
	}
	
	@RequestMapping(value = "/getMultipleMeterDTMonthWise", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getMultipleMeterDTMonthWise(@RequestParam String zone,
			@RequestParam String circle,
			@RequestParam String division,
			@RequestParam String sdoCode,
			@RequestParam String townCode,
			@RequestParam String dtTpCode,
			@RequestParam String billmonth,
			HttpServletRequest request, Model model ) {
		
		List getMultipleMeterDTMonthWiseList = reportService.getMultipleMeterDTMonthWiseResult(zone,circle,division,sdoCode,townCode,dtTpCode,billmonth);
		return getMultipleMeterDTMonthWiseList;
	}
	
	
	@RequestMapping(value = "/getMultipleMeterDTDateWise", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getMultipleMeterDTDateWise(@RequestParam String fromDate,
			@RequestParam String dtTpCode,
			@RequestParam String toDate,
			HttpServletRequest request, Model model ) {
		
		List getMultipleMeterDTMonthWiseList = reportService.getMultipleMeterDTDateWiseResult(fromDate,toDate,dtTpCode);
		return getMultipleMeterDTMonthWiseList;
	}
	
	@RequestMapping(value = "/getMultipleMeterDTMeterWise", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getMultipleMeterDTMeterWise(@RequestParam String billDate,
			@RequestParam String dtTpCode,
			HttpServletRequest request, Model model ) {
		
		List getMultipleMeterDTMonthWiseList = reportService.getMultipleMeterDTMeterWiseResult(billDate,dtTpCode);
		return getMultipleMeterDTMonthWiseList;
	}	
	
	@RequestMapping(value="/PFCreportD4PDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void PFCreportD4PDF(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String scheme=request.getParameter("scheme");
		String town=request.getParameter("town");
		String period=request.getParameter("period");
		String state=request.getParameter("state");
		String discom=request.getParameter("discom");
		String month=request.getParameter("month");
		String ieperiod=request.getParameter("ieperiod");
		String townname=request.getParameter("townname");
		String[] arrSplit = town.split(",");
		
		String twn="";
		if(town.equalsIgnoreCase("All"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
    	String firsttest =null;
    	String test = null;
    	int size = town.length();
    	String finalString = null;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    }
		
		pfcservice.getPfcreportD4Pdf(request, response, scheme, finalString, period, state, discom, month, ieperiod,townname,twn);
		
	}
	
	@RequestMapping(value="/NPPReportFeederPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void NPPReportFeederPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String scheme=request.getParameter("scheme");
		String town=request.getParameter("town");
		String period=request.getParameter("period");
		String state=request.getParameter("state");
		String discom=request.getParameter("discom");
		String month=request.getParameter("month");
		String ieperiod=request.getParameter("ieperiod");
		String townname=request.getParameter("townname");
		String[] arrSplit = town.split(",");
		
		String twn="";
		if(town.equalsIgnoreCase("All"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
    	String firsttest =null;
    	String test = null;
    	int size = town.length();
    	String finalString = null;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	    		int x = (arrSplit.length)-1;
				String[] splittest1 = arrSplit[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				 	
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
	    }
		
		pfcservice.getNPPReportFeederPdf(request, response, scheme, finalString, period, state, discom, month, ieperiod,townname,twn);
		
	}
	
	
	@RequestMapping(value = "/saifiSaidi", method = RequestMethod.GET)
	public String saifiSaidi(ModelMap model, HttpServletRequest request)// get active and inactive count
	{
		List<FeederMasterEntity> zoneList = feederService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		return "saifiSaidi";
	}
	
	
	
	
	//pdf feeder

	@RequestMapping(value = "/ReliabilityIndicesFeederSummary",method= {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void getSaidiSaifiReportpdf(HttpServletRequest request ,HttpServletResponse response) 
	{
		String townfeeder=request.getParameter("townfeeder");
	//	String reportRange=request.getParameter("reportRange");
		String zone=request.getParameter("zne");
		String circle =request.getParameter("cir");
		String town=request.getParameter("twn");
		String frommonth=request.getParameter("frommonth");
		String feeder=request.getParameter("feeder");
		String tomonth=request.getParameter("tomonth");
		String townname=request.getParameter("townname1");
		//System.out.println(townfeeder+town+monthyr+feeder);
		String feedername=request.getParameter("feedername");
		String zne="",cir="",twn="",townname1="";
		System.out.println(townfeeder);
		
		
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		//System.err.println("hii");
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="%";
		}else {
			cir=circle;
		}
		//System.err.println("hiiii");
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
		/*
		 * if(townfeeder.equalsIgnoreCase("town")) { twn="%"; }else { twn=zone; }
		 */

	//	String current_monthyear=new SimpleDateFormat("yyyyMM").format(new Date());
		//List<?> res = getSaidiDataFromTable(townfeeder, reportRange, town, monthyr, feeder, yearid);
	   reportService.getSaidiSaifiReportpdf(townfeeder,  twn,zne, cir,frommonth, feeder,townname1,feedername, request,response,tomonth);
		
		

		
		
	}
	@RequestMapping(value="/triggerSadidiGeneration/{monthyear}/{freeze_status}",method= {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object triggerSadidiGeneration(@PathVariable("monthyear") String monthyear,@PathVariable("freeze_status") int freeze_status,HttpServletRequest request) 
	{
    	generateSaidiSaifiReport(monthyear,freeze_status);
    	return "success";
	}
	
private void generateSaidiSaifiReport(String monthyear, int freeze_status ) {
    	
    	String sql="SELECT \n" +
    			"CAST('"+monthyear+"' as NUMERIC) as monthyear,\n" +
    			"c.dttpid, c.dtname, c.parentid as feeder_id, c.tpparentid as feeder_code,c.town,c.fdrcategory,\n" +
    			"COALESCE(e.intr_count,0) as intr_count, COALESCE(intr_duration,0) as intr_duration,\n" +
    			"c.cons_count,0,\n" +
    			"cast(COALESCE(e.value,((SELECT value from meter_data.report_config WHERE name='minInterruptionTime'))) as NUMERIC),c.town_code\n" +
    			"FROM\n" +
    			"(\n" +
    			"SELECT a.*,COALESCE(n.consumer_count,0) cons_count ,\n" +
    			"(SELECT DISTINCT town_ipds FROM meter_data.amilocation WHERE tp_towncode=town_code) as  town\n" +
    			"FROM\n" +
    			"(\n" +
    			"SELECT dttpid,dtname,parentid, tpparentid, accno, town_code, fdrcategory,mtrno FROM meter_data.master_main m, meter_data.dtdetails d WHERE m.accno=d.dt_id AND m.fdrcategory='DT'\n" +
    			") a LEFT JOIN \n" +
    			"(\n" +
    			"SELECT tpdtid, sum(ht_industrial_consumer_count+ht_commercial_consumer_count+lt_industrial_consumer_count+lt_commercial_consumer_count+lt_domestic_consumer_count+govt_consumer_count+agri_consumer_count+others_consumer_count+hut_consumer_count) as consumer_count FROM meter_data.npp_dt_rpt_monthly_calculation GROUP BY tpdtid\n" +
    			")n ON a.dttpid=n.tpdtid \n" +
    			") c LEFT JOIN\n" +
    			"(\n" +
    			"SELECT value,meter_sr_number, COUNT(*) as intr_count, SUM(EXTRACT(EPOCH FROM duration)) as intr_duration FROM\n" +
    			"(\n" +
    			"SELECT * FROM\n" +
    			"(SELECT value from meter_data.report_config WHERE name='minInterruptionTime')a ,\n" +
    			"(\n" +
    			"SELECT * FROM meter_data.event_details WHERE to_char(event_restore_date, 'yyyyMM')='"+monthyear+"' AND event_code='101' AND event_restore_date is not NULL\n" +
    			")b WHERE EXTRACT(EPOCH FROM duration)>=CAST(a.value as NUMERIC)\n" +
    			")a GROUP BY value,meter_sr_number\n" +
    			") e ON c.mtrno=e.meter_sr_number";
    	
    	List<?> list=entityManager.createNativeQuery(sql).getResultList();
    	
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			SaidiSaifiEntity entity=new SaidiSaifiEntity();
			
			entity.setKeySaidi(new KeySaidi(Integer.parseInt(String.valueOf(obj[0])), String.valueOf(obj[1])));
			
			entity.setDt_name(String.valueOf(obj[2]));
			entity.setParent_feeder_id(String.valueOf(obj[3]));
			entity.setParent_feeder_code(String.valueOf(obj[4]));
			entity.setTown_name(String.valueOf(obj[5]));
			entity.setLocation_type(String.valueOf(obj[6]));
			entity.setInterruption_count(Integer.parseInt(String.valueOf(obj[7])));
			//entity.setInterruption_duration(Integer.parseInt(String.valueOf(obj[8])));
			entity.setInterruption_duration(((Double)obj[8]).intValue());
			entity.setConsumer_count(Integer.parseInt(String.valueOf(obj[9])));
			entity.setFreeze_status(freeze_status);
			if(obj[11]!=null) {
				entity.setMin_interruption_time(Integer.parseInt(String.valueOf(obj[11])));
			}
			entity.setTown_code(String.valueOf(obj[12]));
			try {
				saidiSaifiService.update(entity);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
    	
    }

@RequestMapping(value="/getSaidiSaifiReport",method= {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getSaidiSaifiReport(HttpServletRequest request) 
	{
   	String townfeeder=request.getParameter("townfeeder");
   	String reportRange=request.getParameter("reportRange");
   	String zone=request.getParameter("zone");
   	String circle =request.getParameter("circle ");
   	String town=request.getParameter("town");
   	//String monthyr=request.getParameter("monthyr");
   	String feeder=request.getParameter("feeder");
  // 	String yearid=request.getParameter("yearid");
   	String fromdatee=request.getParameter("frommonth");
	String todatee=request.getParameter("tomonth");
	System.out.println(fromdatee + " coming "+    todatee);

   	String current_monthyear=new SimpleDateFormat("yyyyMM").format(new Date());
   	
		/*
		 * if(monthyr.equals(current_monthyear)) {
		 * generateSaidiSaifiReport(current_monthyear, 0); }
		 */
   	
   	//List<?> res = getSaidiDataFromTable(townfeeder, reportRange, town, monthyr, feeder, yearid);
	List<?> res = getSaidiDataFromTableUpdate(townfeeder,  town, fromdatee, feeder,todatee);
   	
   	
   	return res;
   	
   	
	}

	/*private List<?> getSaidiDataFromTable(String townfeeder, String reportRange, String town, String monthyr,String feeder, String yearid) {
		
		String sql="";
   	String myCondition="";
   	
   	if("month".equals(reportRange)) {
   		myCondition="monthyear="+monthyr+"";
		} else if("year".equals(reportRange)) {
			myCondition="to_char(to_date(CAST(monthyear as TEXT), 'YYYYMM'),'YYYY')='"+yearid+"'";
		}
   	
   	
   	if("town".equals(townfeeder)) {
   		
   		sql="SELECT head,\n" +
   				"interruption_count, \n" +
   				"(round(interruption_duration/60)||' Min : '||mod(interruption_duration,60)||' Sec') as duration,\n" +
   				"consumer_count,\n" +
   				"(CAST(saifi as DECIMAL)/consumer_count) as saifi,\n" +
   				"(CAST(saidi as DECIMAL)/consumer_count) as saidi,\n" +
   				" monthyear "+
   				"FROM\n" +
   				"(\n" +
   				"SELECT town_name as head,monthyear, sum(interruption_count) as interruption_count,sum(interruption_duration) as interruption_duration,\n" +
   				"sum(consumer_count) as consumer_count,\n" +
   				"SUM(interruption_count*consumer_count) as saifi,\n" +
   				"SUM((CAST(interruption_duration as DECIMAL)/3600 )*consumer_count) as saidi\n" +
   				"FROM meter_data.saidi_saifi_rpt WHERE town_code='"+town+"'\n" +
   				"and "+myCondition+" GROUP BY town_name,monthyear\n" +
   				")s ORDER BY s.monthyear;";
   		
   	} else if("feeder".equals(townfeeder)) {
   		sql="SELECT head,\n" +
   				"interruption_count, \n" +
   				"(round(interruption_duration/60)||' Min : '||mod(interruption_duration,60)||' Sec') as duration,\n" +
   				"consumer_count,\n" +
   				"(CAST(saifi as DECIMAL)/consumer_count) as saifi,\n" +
   				"(CAST(saidi as DECIMAL)/consumer_count) as saidi,\n" +
   				" monthyear "+
   				"FROM\n" +
   				"(\n" +
   				"SELECT parent_feeder_code as head,monthyear, sum(interruption_count) as interruption_count,sum(interruption_duration) as interruption_duration,\n" +
   				"sum(consumer_count) as consumer_count,\n" +
   				"SUM(interruption_count*consumer_count) as saifi,\n" +
   				"SUM((CAST(interruption_duration as DECIMAL)/3600 )*consumer_count) as saidi\n" +
   				"FROM meter_data.saidi_saifi_rpt WHERE parent_feeder_id='"+feeder+"'\n" +
   				"and "+myCondition+" GROUP BY parent_feeder_code,monthyear\n" +
   				")s ORDER BY s.monthyear;";
   		
   	}
   	List<?> res=null;
   	if(!"".equals(sql)) {
   		res=entityManager.createNativeQuery(sql).getResultList();
   	}
		return res;
	}*/
	


private List<?> getSaidiDataFromTableUpdate(String townfeeder, String town, String fromdatee,String feeder,String todatee) {
	
	String sql="";
	String myCondition="";
	

	
	if("town".equals(townfeeder)) {
		
		sql="Select xa.*, kwh_imp, kwh_exp,  min_voltage,max_current ,\n" +
				"to_char(to_date('"+fromdatee+"', 'YYYYMM') - INTERVAL '0 MONTH' , 'yyyy-mm-dd') as start_period,\n" +
				"to_char(to_date('"+todatee+"', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'yyyy-mm-dd') as end_period,"
				+ "round((totalnooccurence/total_consumers),2) as SAIFI,"
				+ "round((totalpowerfailuredurationinmin/total_consumers),2) as SAIDI,'U' as feeder_type from  (\n" +
				"Select feedercode,monthyear,sum(totalnooccurence) as totalnooccurence,meterid,\n" +
				"Sum(totalpowerfailureduration) as totalpowerfailureduration ,\n" +
				"ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,\n" +
				"lt_dom_con_count,govt_con_count,agri_con_count,other_con_count,\n" +
				"(ht_ind_con_count + ht_com_con_count + lt_ind_con_count + lt_com_con_count + lt_dom_con_count + govt_con_count + agri_con_count + other_con_count) as total_consumers,"
				+ " Sum(totalpowerfailureduration/60) as totalpowerfailuredurationinmin from meter_data.power_onoff po\n" +
				"LEFT JOIN meter_data.npp_data_monthly_calculation npp on npp.tp_feeder_code = po.feedercode and to_char(date, 'yyyymm') = monthyear\n" +
				"where to_char(date, 'yyyymm') >= '"+fromdatee+"'and to_char(date,'yyyymm')<='"+todatee+"' and po.towncode like '"+town+"' \n" +
				"GROUP BY feedercode,ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,lt_dom_con_count,govt_con_count,\n" +
				"agri_con_count,other_con_count ,meterid,monthyear) xa \n" +
				"LEFT JOIN \n" +
				"meter_data.monthly_consumption mc on xa.meterid= mc.mtrno and CAST(monthyear as numeric) = mc.billmonth\n" +
				" ";
		
	} else if("feeder".equals(townfeeder)) {
		sql="Select xa.*, kwh_imp, kwh_exp, min_voltage,max_current , \n" +
				"to_char(to_date('"+fromdatee+"', 'YYYYMM') - INTERVAL '0 MONTH' , 'yyyy-mm-dd') as start_period,\n" +
				"to_char(to_date('"+todatee+"', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'yyyy-mm-dd') as end_period, "
				+ "round((totalnooccurence/total_consumers),2) as SAIFI,"
				+ "round((totalpowerfailuredurationinmin/total_consumers),2) as SAIDI ,'U' as feeder_type from  (\n" +
				"Select feedercode,monthyear,sum(totalnooccurence) as totalnooccurence,meterid,\n" +
				"Sum(totalpowerfailureduration) as totalpowerfailureduration ,\n" +
				"ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,\n" +
				"lt_dom_con_count,govt_con_count,agri_con_count,other_con_count,\n" +
				"(ht_ind_con_count + ht_com_con_count + lt_ind_con_count + lt_com_con_count + lt_dom_con_count + govt_con_count + agri_con_count + other_con_count) as total_consumers, "
				+ "Sum(totalpowerfailureduration/60) as totalpowerfailuredurationinmin from meter_data.power_onoff po\n" +
				"LEFT JOIN meter_data.npp_data_monthly_calculation npp on npp.tp_feeder_code = po.feedercode and to_char(date, 'yyyymm') = monthyear\n" +
				"where to_char(date, 'yyyymm') = '"+fromdatee+"' and to_char(date,'yyyymm')<='"+todatee+"'and feedercode =  '"+feeder+"'\n" +
				"GROUP BY feedercode,ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,lt_dom_con_count,govt_con_count,\n" +
				"agri_con_count,other_con_count ,meterid,monthyear) xa \n" +
				"LEFT JOIN \n" +
				"meter_data.monthly_consumption mc on xa.meterid= mc.mtrno and CAST(monthyear as numeric) = mc.billmonth\n" +
				" ";
		
	}
	
	System.out.println("SAIFI/SAIDI query" +sql );
	List<?> res=null;
	if(!"".equals(sql)) {
		res=entityManager.createNativeQuery(sql).getResultList();
	}
	return res;
}


//Getting JSON export to SAIFI/SAIDI 

@RequestMapping(value = "/exportjsonSaifiSaidiFeeder", method = { RequestMethod.POST, RequestMethod.GET },produces="application/text")
public @ResponseBody String exportjsonSaifiSaidiFeeder(HttpServletRequest request, HttpServletResponse response,
		ModelMap model) throws FileNotFoundException {

   	String townfeeder=request.getParameter("townfeeder");
   	String town=request.getParameter("town");
   //	String monthyr=request.getParameter("monthyr");
   	String feeder=request.getParameter("feeder");
   	String fromdatee=request.getParameter("frommonth");
   	String todatee=request.getParameter("tomonth");
	
   	String jsonDate = null;
	// JSONObject mainObj = new JSONObject();
	// JSONObject header = new JSONObject();
	// JSONObject transaction_data = new JSONObject();
	// JSONObject footer = new JSONObject();
	// JSONArray transactionAry = new JSONArray();
	if(!fromdatee.isEmpty()&& fromdatee != null){
	  String mm = fromdatee.substring(4);
	  String yyyy = fromdatee.substring(0,4);
	  jsonDate  = mm+yyyy;
	  System.out.println(jsonDate);
	  System.out.println(yyyy);
	 	}
   	
   	
	String filename = "TNEB_ufss_"+jsonDate+".txt";
	String json = generatejsonSaifiSaidi(fromdatee,townfeeder, town, feeder, todatee);
	
	response.setHeader("Content-Disposition", "attachment; filename=" + filename);
	response.setContentType("text/plain");
	try {
		// String filename = "Demo";
		//String filepath = com.bcits.mdas.utility.FilterUnit.nppDataSourceFolder + "/" + filename;
		//File file = new File(filepath);
		//InputStream inputStream = new FileInputStream(file);
		////System.out.println(file.getName());
		try {
			
			//OutputStream out = response.getOutputStream();
			//IOUtils.copy(inputStream, out);
			//response.flushBuffer();
			//out.flush();
			//out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	return json;
}


//Generating JSON for SAIFI/SAIDI

@SuppressWarnings("unchecked")
public String generatejsonSaifiSaidi( String fromdatee,String townfeeder, String town,String feeder,String todatee) {
	
	System.out.println("town-------->" +town);
	System.out.println("fromdate------>");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//List<NPPDataEntity> list = nppDataService.getDataByMonthYear(my);
	List<Object[]> list = (List<Object[]>) getSaidiDataFromTableUpdate(townfeeder, town,fromdatee, feeder,todatee);
	
	String jsonDate = null;
	// JSONObject mainObj = new JSONObject();
	// JSONObject header = new JSONObject();
	// JSONObject transaction_data = new JSONObject();
	// JSONObject footer = new JSONObject();
	// JSONArray transactionAry = new JSONArray();
	if(!fromdatee.isEmpty() 	&& fromdatee != null){
	  String mm = fromdatee.substring(4);
	  String yyyy = fromdatee.substring(0,4);
	  jsonDate  = mm+yyyy;
	 	}

	Map<String, Object> mainObj = new LinkedHashMap<String, Object>();
	Map<String, Object> header = new LinkedHashMap<String, Object>();
	Map<String, Object> transaction_data = new LinkedHashMap<String, Object>();
	Map<String, Object> footer = new LinkedHashMap<String, Object>();

	List<Object> transactionAry = new LinkedList<>();

	String filename = "TNEB_ufss_"+jsonDate+ ".txt";

	header.put("file_Name", filename);
	header.put("file_generation_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
	header.put("no_of_records", list.size());
	header.put("version", "1.0");

	footer.put("file_Name", filename);
	footer.put("no_of_records", list.size());

	mainObj.put("header", header);
	
	
	

for (Object[] object : list) {
		
	
	
		// JSONObject fdrData = new JSONObject();

		Map<String, Object> fdrData = new LinkedHashMap<String, Object>();

		fdrData.put("Feeder_Code",object[0]);
		fdrData.put("Feeder_Type(U/R/M)",object[23]);
		fdrData.put("Start_Period", object[19]);
		fdrData.put("End_Period", object[20]);
		fdrData.put("No_Of_Power_Failure", object[2]);
		fdrData.put("Duration_Of_Power_Failure(Sec)",object[4]);
		fdrData.put("Minimum_Voltage(V)", object[17]);
		fdrData.put("Max_Current(A)", object[18]);
		fdrData.put("Input_Energy(kwh)",object[15]);
		fdrData.put("Export_Energy(kwh)", object[16]);
		fdrData.put("HT_Industrial_Consumer_Count", object[5]);
		fdrData.put("HT_Commercial_Consumer_Count", object[6]);
		fdrData.put("LT_Industrial_Consumer_Count", object[7]);
		fdrData.put("LT_Commercial_Consumer_Count", object[8]);
		fdrData.put("LT_Domestic_Consumer_Count",object[9]);
		fdrData.put("Govt_Consumer_Count", object[10]);
		fdrData.put("Agri_Consumer_Count", object[11]);
		fdrData.put("Others_Consumer_Count",object[12]);
	

		transactionAry.add(fdrData);

	}

	// transaction_data.put("transaction_data", transactionAry);

	mainObj.put("transaction_data", transactionAry);
	mainObj.put("footer", footer);

	// //System.out.println(mainObj.toString());

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String json = gson.toJson(mainObj);

	//System.out.println(json);

	//writeNPPData(com.bcits.mdas.utility.FilterUnit.nppDataSourceFolder, filename, json);
	return json;
}



	@RequestMapping(value = "/getFeederMetersByTown", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getFeederByTown(HttpServletRequest request, ModelMap model) {
		String town = request.getParameter("town");
		String sql="select distinct m.tp_fdr_id, m.feedername from meter_data.feederdetails m where  m.tp_town_code like '"+town+"' and feeder_type= 'IPDS'";
		List<?> list= entityManager.createNativeQuery(sql).getResultList();
		return list;
	}
	
	@RequestMapping(value = "/getDTMetersByTown", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDTMetersByTown(HttpServletRequest request, ModelMap model) {
		String town = request.getParameter("town");
		String sql="Select distinct dttpid , dtname from meter_data.dtdetails where  tp_town_code = '"+town+"' ";
		List<?> list= entityManager.createNativeQuery(sql).getResultList();
		return list;
	}
	
	@RequestMapping(value="/VoltageVarRepPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void VoltageVarRepPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("crcl");
		String town=request.getParameter("twn");
		String loctype=request.getParameter("metertype");
		String rdngmonth=request.getParameter("rdngmnth");
		String reptype=request.getParameter("reporttype");
		String townname=request.getParameter("townname1");
		String zon="",cir="",tow="",townname1="";
		if (zone.equalsIgnoreCase("ALL")) {
			zon = "%";
		}else {
			zon = zone;
		}
		if (circle.equalsIgnoreCase("ALL")) {
			cir = "%";
		}else {
			cir = circle;
		}
		if (town.equalsIgnoreCase("ALL")) {
			tow = "%";
		}else {
			tow = town;
		}

		if(townname.equalsIgnoreCase("ALL"))
		{
			townname1="%";
		}else {
			townname1=townname;
		}
		
		reportService.getVolVarReppdf(request, response, zone, cir, tow, loctype, rdngmonth,townname1);
		
	}
	
	@RequestMapping(value="/PowerFactorRepPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void PowerFactorRepPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("crcl");
		String town=request.getParameter("twn");
		String loctype=request.getParameter("metertype");
		String rdngmonth=request.getParameter("rdngmnth");
		String reptype=request.getParameter("reporttype");
		String townname=request.getParameter("townname1");
		String zon="",cir="",tow="",townname1="";
		if (zone.equalsIgnoreCase("ALL")) {
			zon = "%";
		}else {
			zon = zone;
		}
		if (circle.equalsIgnoreCase("ALL")) {
			cir = "%";
		}else {
			cir = circle;
		}
		if (town.equalsIgnoreCase("ALL")) {
			tow = "%";
		}else {
			tow = town;
		}

		if(townname.equalsIgnoreCase("ALL"))
		{
			townname1="%";
		}else {
			townname1=townname;
		}
		
		reportService.getPowerFactorReppdf(request, response, zon, cir, tow, loctype, rdngmonth,townname1);
		
	}
	
	@RequestMapping(value = "/consisCommFailRep", method = { RequestMethod.POST, RequestMethod.GET })
	public String consisCommFailRep(ModelMap model, HttpServletRequest request) {
		String qry;
		  String qryRegion;
	        Object res;
		 List<?> circleList=new ArrayList<>();
		 List<?> zoneList=new ArrayList<>();

  
		 qryRegion = "SELECT DISTINCT ZONE  FROM meter_data.amilocation ORDER BY ZONE ";
            qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation ORDER BY CIRCLE ";
            circleList = entityManager.createNativeQuery(qry).getResultList();
            zoneList = entityManager.createNativeQuery(qryRegion).getResultList();
            model.put("circles", circleList);
            model.put("zoneList", zoneList);
		return "consisCommFailRep";
	
     }
	
	@RequestMapping(value = "/showCommFailmtrs", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> showCommFailmtrs(ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		String zone=request.getParameter("zone");
		String circle=request.getParameter("circle");
		String towncode=request.getParameter("town");
	
		List<?> listcomm=feederService.getCommFailList(zone, circle, towncode);
		return listcomm;
	}
	
	@RequestMapping(value = "/showCommFailConsumers", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> showCommFailConsumers(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		String towncode=request.getParameter("towncodee");
		String id = request.getParameter("Id");
		List<?> consumfailcount=feederService.getTotalConsumersFailData(id,towncode);

		return consumfailcount;
	}
	@RequestMapping(value="/viewfeederhealthpdf",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void ViewAlarmsPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("townn");
		String rdngmnth=request.getParameter("rdngmnth");
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
		
		/*if(townname.equalsIgnoreCase("ALL"))
		{
			townname1="%";
		}else {
			townname1=townname;
		}*/
	 feederHealthService.getFeederHealthReportPdf(request, response, zone1,crcl, twn, rdngmnth,townname);
	}
	
}
