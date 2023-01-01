package com.bcits.mdas.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.NamePlate;
import com.bcits.mdas.jsontoobject.SearchMeterJson;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.service.EnergyAccountingService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.OnDemandServicenamepla;
import com.bcits.service.AlarmHistoryService;
import com.bcits.service.AlarmService;
import com.bcits.service.DTHealthService;
import com.bcits.service.FeederHealthService;
import com.bcits.service.LoadAvailabilityRptService;
import com.bcits.service.PowerFactorAnalysisService;
import com.bcits.service.VoltageRegulationService;
import com.bcits.utility.MDMLogger;
import com.google.gson.Gson;

@Controller
public class ScheduledMeterSearch {
	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	HESController hesc;
	@Autowired
	private LoadAvailabilityRptService loadAvailabilityRptService;

	@Autowired
	OnDemandServicenamepla odp;

	@Autowired
	private FeederDetailsService feederdetailsservice;

	@Autowired
	private AlarmService alarmService;

	@Autowired
	private AlarmHistoryService alarmHstAervice;
	@Autowired
	private FeederHealthService feederHealthService;
	@Autowired
	private PowerFactorAnalysisService pfanalysisService;
	@Autowired
	private VoltageRegulationService voltageRegulationService;

	@Autowired
	private AmiLocationService amiLocationService;

	@Autowired
	private MasterMainService masterMainService;

	@Autowired
	private DTHealthService dTHealthService;

	@Autowired
	private EnergyAccountingService energyAccountingService;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

	// @Scheduled(cron = "0 0/5 * * * ?")
	public void scheduledMeterSearchProfile() throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String s = hesc.searchMeters();
		Gson gson = new Gson();
		SearchMeterJson[] smjl = gson.fromJson(s, SearchMeterJson[].class);

		/*
		 * Type collectionType = new TypeToken<List<SearchMeterJson>>() { }.getType();
		 */

		for (SearchMeterJson smj : smjl) {
			NamePlate entity = new NamePlate();
			entity.setMeter_serial_number(smj.getMeterId());
			entity.setManufacturer_name(smj.getVendor());
			entity.setFirmware_version(smj.getFirmwareVersion());
			entity.setHardwareVersion(smj.getHardwareVersion());
			entity.setNodeId(smj.getNodeId());
			String[] s1 = smj.getCreateTime().replace("T", " ").split("\\+");
			String[] s2 = smj.getUpdateTime().replace("T", " ").split("\\+");
			entity.setCreatedTime(new Timestamp(format.parse(s1[0]).getTime()));
			entity.setUpdatedTime(new Timestamp(format.parse(s2[0]).getTime()));

			entity.setFlag("SD");

			odp.customupdateBySchema(entity, "postgresMdas");

		}

		/*
		 * for (Iterator iterator = smm.iterator(); iterator.hasNext();) { NamePlate
		 * entity = new NamePlate(); SearchMeterJson searchMeterJson = (SearchMeterJson)
		 * iterator.next();
		 * 
		 * entity.setMeter_serial_number(searchMeterJson.getMeterId());
		 * entity.setManufacturer_name(searchMeterJson.getVendor());
		 * entity.setFirmware_version(searchMeterJson.getFirmwareVersion());
		 * entity.setHardwareVersion(searchMeterJson.getHardwareVersion());
		 * entity.setNodeId(searchMeterJson.getNodeId()); String[]
		 * s1=searchMeterJson.getCreateTime().replace("T", " ").split("\\+"); String[]
		 * s2=searchMeterJson.getUpdateTime().replace("T", " ").split("\\+");
		 * entity.setCreatedTime(new Timestamp(format.parse(s1[0]).getTime()));
		 * entity.setUpdatedTime(new Timestamp(format.parse(s2[0]).getTime()));
		 * 
		 * entity.setFlag("SD");
		 * 
		 * odp.customupdateBySchema(entity, "postgresMdas");
		 * 
		 * 
		 * 
		 * }
		 */

	}

	// @Scheduled(cron = "0 0/5 * * * ?")
	public void scheduledInsertData() {

		String qry = "insert into meter_firsttime_read_data  (\n" + "SELECT A.* FROM \n"
				+ "(select * from amiinstantaneous)A,\n"
				+ "(SELECT meter_number, min(read_time) as mindate FROM amiinstantaneous  where meter_number in (select DISTINCT meterno from meter_inventory where meterno not in (SELECT meter_number FROM \"meter_firsttime_read_data\"))  GROUP BY   meter_number \n"
				+ ")B \n" + "WHERE A.meter_number=B.meter_number AND A.read_time=B.mindate)";

		int x = feederdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();

	}

//	@Scheduled(cron = "0 0 0/1 * * *")
	@RequestMapping(value = "/scheduleAlarmData", method = { RequestMethod.POST, RequestMethod.GET })
	public void processAlarms() {
		alarmService.processEventAlarmData();
		// alarmService.processValidationsAlarmData();
	}

	// @Scheduled(cron = "0 0/1 * * * ?")
	public void refreshLoadSurveyReport() {
		System.out.println("inside refreshLoadSurveyReport-- ");

		String month = dateFormat.format(new Date());
		Integer year = Integer.parseInt(month.split("-")[0]);
		Integer mon = Integer.parseInt(month.split("-")[1]);
		Integer day = Integer.parseInt(month.split("-")[2]);
		ArrayList<String> dates = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		/*
		 * cal.set(Calendar.YEAR, year); cal.set(Calendar.MONTH, (mon - 1));
		 * cal.set(Calendar.DAY_OF_MONTH, 1);
		 */

		cal.setTime(new Date());
		cal.add(Calendar.DATE, -15);
		// int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// System.out.println(df.format(cal.getTime()));
		dates.add(df.format(cal.getTime()));
		for (int i = 1; i <= day; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			// System.out.println(df.format(cal.getTime()));
			dates.add(df.format(cal.getTime()));
		}
		ReportController reportController = new ReportController();
		for (Iterator iterator = dates.iterator(); iterator.hasNext();) {
			String date = (String) iterator.next();

			String qry = "SELECT m.zone, m.circle, m.division,m.subdivision, m.substation,m.fdrname, m.mtrno, " +

					"COALESCE(A.lcount,0) as lcount, "
					+ "(CASE WHEN A.lcount is NULL THEN 0 WHEN A.lcount>48 THEN 15 ELSE 30 END) as intrvl "
					+ "FROM meter_data.master_main m LEFT JOIN " + "( "
					+ "SELECT meter_number,date(read_time) as ldate,  count (*) as lcount  FROM meter_data.load_survey  "
					+ "WHERE date(read_time)='" + date + "' " + " GROUP BY meter_number, date(read_time) "
					+ " ORDER BY meter_number,date(read_time) " + ") A ON m.mtrno=A.meter_number "
					+ "ORDER BY  m. zone , m.circle, m.division,m.subdivision, m.substation";

			List<?> li = loadAvailabilityRptService.executeSelectQueryRrnList(qry);

			reportController.generateLoadSurveyReportByMonth(li, loadAvailabilityRptService, date);

		}

		System.out.println("Load Survey Report Refreshed.");
		try {
			refreshLoadSurveyReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//DT health report schedular

	// @Scheduled(cron = "0 0/2 * * * ?")
	// @Scheduled(cron = "0 0 0 20 * ?")

	// @Scheduled(cron = "0 0/1 * * * ?")
	// @Scheduled(cron = "0 0 0 20 * ?")

	// @Scheduled(cron = "0 0/1 * * * ?")
//	@Scheduled(cron = "0 0 2 3 1/1 *")
	// @Scheduled(cron = "0 13 12 * * ?")

	public void dtHealthData() throws JsonGenerationException, JsonMappingException, IOException {
		final String fdrCategory = "DT";
		List<String> siteCodesList = new ArrayList<>();
		List<String> meterList = new ArrayList<>();
		siteCodesList = amiLocationService.getAllSiteCodes();
		/*
		 * for (int i = 0; i < siteCodesList.size(); i++) { meterList =
		 * masterMainService.getAllMeterNumByCategoryAndSubDIV(String.valueOf(
		 * siteCodesList.get(i)), fdrCategory); if (!meterList.isEmpty()) {
		 * dTHealthService.proceessDTHealthData(String.valueOf(siteCodesList.get(i)),
		 * meterList); } else { } }
		 */
		dTHealthService.proceessDTHealthData();

	}

	// @Scheduled(cron = "0 23 16 * * ?")
	@RequestMapping(value = "/scheduleFeederHealthData/{month}", method = { RequestMethod.POST, RequestMethod.GET })
	public void fedderHealthDataProcess(@PathVariable String month)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<String> meterList = new ArrayList<>();
		meterList = feederdetailsservice.getFeederAllMeters();
		System.err.println(month);
		if (!meterList.isEmpty()) {
			feederHealthService.proceessFeederHealthData(meterList, month);
		} else {
			MDMLogger.logger.info("No feeder meters found");
		}

	}

//	@Scheduled(cron = "0 56 0 * * ?")
	public void PowerFactorAnalysisProcess() {
		pfanalysisService.savePfAnalysisData();
	}

//	@Scheduled(cron = "0 42 0 * * ?")
	public void VoltageVariationAnalysisProcess() {
		voltageRegulationService.saveVoltageVariationData();
	}

	// dthealth report manual push
	@RequestMapping(value = "/dtHealthDataProcess", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody public String dtHealthDataProcess(HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		String month = request.getParameter("billmonth");
		System.out.println(month);
		String msg;
		try {
		dTHealthService.manualproceessDTHealthData(month);
		msg = "success";
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "failed";
			
		}
		return msg;

	}

	// Load survey Dt manual push month wise
	@RequestMapping(value = "/loadSurveyDtDataPush/{month}", method = {RequestMethod.GET, RequestMethod.POST})
	public String loadSurveyDtDataPush(@PathVariable String month)
			throws JsonGenerationException, JsonMappingException, IOException {
		dTHealthService.loadSurveyDtDataPush(month);
		return "Success";
	}

	// @Scheduled(cron="0 1 1 * * *")
	// @Scheduled(cron = "0 0 12 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/loadSurveyDtDataPushPreviousDay", method = { RequestMethod.GET, RequestMethod.POST })
	public void dailyPushLoadSurveyDT() {
		dTHealthService.loadSurveyDtDailyPush();
	}

	// Load survey Dt manual push day wise
	@RequestMapping(value = "/loadSurveyDtDataPushDayWise", method = { RequestMethod.GET, RequestMethod.POST })
	public String loadSurveyDtDataPushDayWise() throws JsonGenerationException, JsonMappingException, IOException {
		dTHealthService.loadSurveyDtDataPushDayWise();
		return "Success";
	}

	// Below Schedular to push data for EA reports Main feeders monthly once

//	@Scheduled(cron = "0 0 0 8 * ?")
	public void MainFeederEAdataPush() {
		energyAccountingService.scheduledataPushMainFeeder();
	}

	// Below Schedular to to push data for EA reports BOUNDARY feeders monthly once
	// @Scheduled(cron = "0 0 0 8 * ?")
	public void BoundaryFeederEAdataPush() {
		energyAccountingService.scheduleDataPushBoundaryFeeder();
	}

	// Below Schedular to to push data for EA reports DT monthly once
//	@Scheduled(cron = "0 0 0 8 * ?")
	public void DTEAdataPush() {
		energyAccountingService.scheduledataPushDT();
	}

	// Below schedular is push data to LOAD_SURVEY_DT monthly once
//	@Scheduled(cron = "0 0 0 5 * ?")
	public void pushLoadSurveyDT() {
		dTHealthService.pushLoadSurveyDt();
	}

	// @Scheduled(cron = "0 0 9 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	public void dailyConsumption_tneb() {
		System.out.println("Daily Trigger DailyConsumption....");
		dTHealthService.dailyConsumption_tneb();
	}

	/* DT_DASBOARD DAILY TRIGGER -- KESAV */

	// @Scheduled(cron = "0 0 5 * * ?")
	// @Scheduled(cron = "0 0/30 * * * ?")
	// @Scheduled(cron="0 0 0/1 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	public void dtpowerfailure() {

		System.out.println("dtpowerfailure...");
		dTHealthService.dtpowerfailure();
	}

	// @Scheduled(cron = "0 0 5/30 * * ?")
	// @Scheduled(cron = "0 0/30 * * * ?")
	// @Scheduled(cron="0 0 0/1 * * ?")
	public void lfpfuf() {
		System.out.println("lfpfuf");
		dTHealthService.lfpfuf();
	}

	// @Scheduled(cron = "0 0 6 * * ?")
	// @Scheduled(cron = "0 0/30 * * * ?")
	// @Scheduled(cron="0 0 0/1 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	public void underload_overload() {
		System.out.println("underload_overload");
		dTHealthService.underload_overload();
	}

	// @Scheduled(cron = "0 0 6/30 * * ?")
	// @Scheduled(fixedRate = 5000)
	// @Scheduled(cron = "0 0/30 * * * ?")
	// @Scheduled(cron="0 0 0/1 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	public void underload_overload_seventy() {
		System.out.println("underload_overload_seventy");
		dTHealthService.underload_overload_seventy();
	}

	//@Scheduled(cron = "0 */40 * ? * *")
	@Transactional(propagation = Propagation.REQUIRED)
	public void workingstatus() {
		System.out.println("workingstatus");
		dTHealthService.workingstatus();
		
		

	}
	
		//@Scheduled(cron = "0 */35 * ? * *")
		@Transactional(propagation = Propagation.REQUIRED)
		public void dailydtcomm() {
			System.out.println("Daily dt Comm Status");
			dTHealthService.dailydtcomm();

		}

	// @Scheduled(cron = "0 0 7 * * ?")
	// @Scheduled(cron = "0 0/30 * * * ?")
	// @Scheduled(cron="0 0 0/1 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	public void unbalance() {

		System.out.println("underload...");
		dTHealthService.unbalance();
	}

	//@Scheduled(cron = "0 0 6 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/dailyConsumption_tneb1", method = { RequestMethod.GET, RequestMethod.POST })
	public void dailyConsumption_tneb1() {
		System.out.println("Daily Consumption " + " - " + new Date());
		try {
			String sql = "insert into meter_data.daily_consumption (location_code,kno,date,mtrno,kwh_imp,kwh_exp,kvah_imp,kvah_exp,create_time)	\r\n"
					+ "select mma.sdocode,mma.kno,(CURRENT_DATE-interval '1 day'),l.meter_number,sum(l.kwh) as kwh_imp,sum(to_number(l.block_energy_kwh_exp,'9999999')) as kwh_exp ,sum(l.kvah) as kvah_imp,sum(to_number(l.block_energy_kvah_exp,'9999999')) as kvah_exp,CURRENT_TIMESTAMP as time_stamp from meter_data.load_survey l left join meter_data.master_main mma on l.meter_number=mma.mtrno\r\n"
					+ "where l.read_time>to_timestamp(to_char(CURRENT_DATE-interval '1 day','yyyy-MM-DD')||' 00:00:00','yyyy-MM-DD HH24:MI:SS') and l.read_time<=to_timestamp(to_char(CURRENT_DATE,'yyyy-MM-DD')||' 00:00:00','yyyy-MM-DD HH24:MI:SS')  GROUP BY l.meter_number,mma.sdocode,mma.kno";
			int j = entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//@Scheduled(cron = "0 0 5 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/dailyPushLoadSurveyDTs", method = { RequestMethod.GET, RequestMethod.POST })
	public void dailyPushLoadSurveyDTs() {

		System.out.println("Load Survey DT" + " - " + new Date());

		String towncode = null;

		for (int i = 5; i >= 0; i -= 1) {

			System.out.println("This is I " + i);

			if (i == 5) {
				towncode = "5%";
			}

			if (i == 4) {
				towncode = "4%";
			}

			if (i == 3) {
				towncode = "3%";
			}
			if (i == 2) {
				towncode = "2%";
			}

			if (i == 1) {
				towncode = "1%";
			}

			if (i == 0) {
				towncode = "0%";
			}
			List<Object[]> list = new ArrayList<>();

			String sql = "Insert into meter_data.load_survey_dt (circle,\r\n"
					+ "				tp_town_code,dttpid,yearmonth,\r\n"
					+ "				kwh,kvah,kw,kva,pf,ir,iy,ib,vr,vy,vb,billmonth\r\n" + "				)\r\n"
					+ "			\r\n" + "		\r\n"
					+ "		SELECT distinct Y.circle, X.*,to_char(CURRENT_TIMESTAMP,'yyyyMM') as billmonth\r\n"
					+ "				FROM\r\n" + "				(\r\n"
					+ "				SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n"
					+ "				SUM ( b.kwh ) AS kwh,    SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n"
					+ "				round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 7 ) pf,\r\n"
					+ "				SUM ( i_r ) AS ir,SUM ( i_y ) AS iy,SUM ( i_b ) AS ib,\r\n"
					+ "				AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb\r\n"
					+ "				FROM\r\n"
					+ "				( SELECT dttpid,meterno,tp_town_code FROM meter_data.dtdetails  WHERE\r\n"
					+ "				dttpid IN    ( SELECT dttpid FROM\r\n"
					+ "				( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n"
					+ "				 meterno IS NOT NULL AND meterno != '' ) b\r\n"
					+ "				) and tp_town_code like '" + towncode
					+ "' and dttpid IS NOT NULL AND dttpid != '' AND\r\n"
					+ "				 meterno IS NOT NULL AND meterno != '') A,\r\n" + "				 ( \r\n"
					+ "				 \r\n" + "				 SELECT  meter_number, read_time AS yearmonth,\r\n"
					+ "				kwh, kvah, kw, i_r,i_y,i_b,\r\n"
					+ "				COALESCE ( v_r, 0 ) AS v_r, COALESCE ( v_y, 0 ) AS v_y, COALESCE ( v_b, 0 ) AS v_b, frequency,\r\n"
					+ "				 kva FROM meter_data.load_survey\r\n"
					+ "				WHERE date(read_time)=CURRENT_DATE-1 and meter_number in \r\n"
					+ "				(select meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n"
					+ "				 meterno IS NOT NULL AND meterno != '' and tp_town_code like '" + towncode
					+ "')\r\n"
					+ "				GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH25:mi:ss' ), kwh, frequency,read_time,\r\n"
					+ "				kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva \r\n" + "				\r\n"
					+ "				\r\n" + "				) b\r\n"
					+ "				WHERE     A.meterno = b.meter_number \r\n"
					+ "				GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n"
					+ "				( SELECT *  FROM meter_data.master_main am\r\n"
					+ "				) Y WHERE Y.location_id = X.dttpid\r\n" + "				\r\n"
					+ "				\r\n" + "";

			 System.out.println("load_survey_dt_push----->"+ sql);

			try {
				int j = entityManager.createNativeQuery(sql).executeUpdate();
				System.out.println("Data inserted successfully.");

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Data not inserted successfully.");
			}
		}

	}

	// @Scheduled(cron = "0 0 0 5 * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/monthlyConsumptionDatapush", method = { RequestMethod.GET, RequestMethod.POST })
	public void monthlyConsumptionDataPush() {

		System.out.println("Monthy Consumption DataPush" + " - " + new Date());

		try {

			String query = "insert into meter_data.monthly_consumption (billmonth,mtrno,kwh_imp,kwh_exp,kvah_imp,kvah_exp,create_time,min_current,\r\n"
					+ "max_current,\r\n" + "min_voltage,\r\n" + "max_voltage\r\n" + ")\r\n" + "\r\n" + "\r\n"
					+ "select distinct on (B.meter_number) B.month, B.meter_number,(B.kwhimp*B.mf) AS kwh_imp,(B.kwhexp*B.mf) AS kwh_exp,\r\n"
					+ "				(B.kvahimp*B.mf) AS kvah_imp,(B.kvahexp*B.mf) AS kvah_exp,B.time_stamp,round(B.mincurrent,2)mincurrent,round(B.maxcurrent*B.mf,2)as maxcurrent,round(B.minvolt,2)as minvolt,round(B.maxvolt,2)as maxvolt\r\n"
					+ "				FROM\r\n"
					+ "			(select A.month,A.meter_number,A.kwhimp,A.kwhexp,A.kvahimp,A.kvahexp,\r\n"
					+ "			m.mtrno,COALESCE(cast(m.mf AS NUMERIC),1) AS mf,A.time_stamp,A.mincurrent,A.maxcurrent,A.minvolt,A.maxvolt from\r\n"
					+ "			(\r\n"
					+ "			select to_number((to_char((read_time-INTERVAL '0 MONTH'),'yyyyMM')),'999999') as month,\r\n"
					+ "			meter_number,\r\n"
					+ "			sum(kwh) AS kwhimp ,sum(kwh_exp) AS kwhexp,sum(kvah_exp) AS kvahexp,sum(kvah) AS kvahimp,CURRENT_TIMESTAMP as time_stamp,min(i_r+i_y+i_b)/3 as mincurrent,\r\n"
					+ "	max (i_r+i_y+i_b)/3 as maxcurrent,min (v_r+v_y+v_b)/3 as minvolt,max(v_r+v_y+v_b)/3 as maxvolt \r\n"
					+ "			from meter_data.load_survey l WHERE  to_char(read_time,'yyyyMM')=to_char(CURRENT_TIMESTAMP-INTERVAL '1 MONTH','yyyyMM')\r\n"
					+ "			GROUP BY month,meter_number)A,\r\n" + "			meter_data.master_main m\r\n"
					+ "			WHERE m.mtrno=A.meter_number AND\r\n"
					+ "			m.mtrno NOT IN(select distinct mtrno from meter_data.monthly_consumption WHERE to_char(billmonth,'yyyyMM') = to_char(CURRENT_TIMESTAMP-INTERVAL '1 MONTH','yyyyMM')))B;\r\n"
					+ "			";

			int j = entityManager.createNativeQuery(query).executeUpdate();
			System.out.println("Data inserted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}
	}

	//@Scheduled(cron = "0 0 9 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/dtDashboardReportQueryReferench", method = { RequestMethod.GET, RequestMethod.POST })
	public void dtDashboardReportQueryReferench() {
		System.out.println("inside scheduled meter controller");
		dTHealthService.dtDashboardReportQueryReferench();

	}
	

	//@Scheduled(cron = "0 0 7 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/dtDashboardReportQueryReferench1", method = { RequestMethod.GET, RequestMethod.POST })
	public void dtDashboardReportQueryReferench1() {
		System.out.println("inside scheduled meter controller");
		dTHealthService.dtDashboardReportQueryReferench1();

	}


	//@Scheduled(cron = "0 0/45 * * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/dtDashboardReportQueryReferench2", method = { RequestMethod.GET, RequestMethod.POST })
	public void dtDashboardReportQueryReferench2() {
		System.out.println("inside scheduled meter controller");
		dTHealthService.dtDashboardReportQueryReferench2();

	}
	
	//@Scheduled(cron = "0 0/15 * * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/fdrlist", method = { RequestMethod.GET, RequestMethod.POST })
	public void fdrlist() {
		System.out.println("inside scheduled meter controller");
		dTHealthService.fdrlist();

	}
	
	//@Scheduled(cron = "0 0 3 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/last30DaysCircleLevelPush", method = { RequestMethod.GET, RequestMethod.POST })
	public void last30DaysCommunication() {

		try {

			String sql = "insert into meter_data.last_30_days_data (\r\n" + 
					"location_name,\r\n" + 
					"total,\r\n" + 
					"communicating, \r\n" + 
					"non_communicating,\r\n" + 
					"date,\r\n" + 
					"insert_time, \r\n" + 
					"flag) \r\n" + 
					"\r\n" + 
					"SELECT circle as location_name , count(*) as total, \r\n" + 
					"count(CASE WHEN c.meter_number is not null AND c.ldate is not NULL then 1 END) as communicating, \r\n" + 
					"count(CASE WHEN c.meter_number is null then 1 END) as non_communicating,\r\n" + 
					"(select CURRENT_DATE-1)as date, now() as insert_time,'0' \r\n" + 
					"from meter_data.master_main m LEFT JOIN \r\n" + 
					"(SELECT meter_number, max(last_communication) as ldate \r\n" + 
					"FROM meter_data.modem_communication \r\n" + 
					"where date(last_communication)=CURRENT_DATE-1\r\n" + 
					"GROUP BY meter_number) c on m.mtrno=c.meter_number \r\n" + 
					"GROUP BY circle ORDER BY circle;\r\n" + 
					"";
			
			

			int j = entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}

	}
	
	
	//@Scheduled(cron = "0 0 6 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/last30daysCorparateLevelPush", method = { RequestMethod.GET, RequestMethod.POST })
	public void last30daysCorparateLevelPush() {

		try {

			String sql = "insert into meter_data.last_30_days_data (\r\n" + 
					"communicating,\r\n" + 
					"non_communicating,\r\n" + 
					"total,\r\n" + 
					"date,\r\n" + 
					"insert_time,\r\n" + 
					"flag)\r\n" + 
					"\r\n" + 
					"select a.comm_yesterday as communicating,(a.total-a.comm_yesterday) as non_communicating ,a.total as total, a.date,now() as insert_time,'1' from\r\n" + 
					"(SELECT count(*) as total,\r\n" + 
					"(select count(*) as comm_yesterday from meter_data.master_main where mtrno in\r\n" + 
					"(select distinct meter_number from meter_data.modem_communication where date(last_communication)=CURRENT_DATE-1) and date(create_time)<=CURRENT_DATE-1),\r\n" + 
					"(CURRENT_DATE-1) as date\r\n" + 
					"FROM meter_data.master_main m WHERE date(m.create_time)<=CURRENT_DATE-1)a;";

			int j = entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}

	}
	
	//@Scheduled(cron = "0 0 4 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/last30daysRegionLevelPush", method = { RequestMethod.GET, RequestMethod.POST })
	public void last30daysRegionLevelPush() {

		try {

			String sql = "insert into meter_data.last_30_days_data ( \r\n" + 
					"					location_name, \r\n" + 
					"					total, \r\n" + 
					"					communicating,  \r\n" + 
					"					non_communicating, \r\n" + 
					"					date, \r\n" + 
					"					insert_time,  \r\n" + 
					"					flag)  \r\n" + 
					"					 \r\n" + 
					"\r\n" + 
					"SELECT zone as location_name , count(*) as total,  \r\n" + 
					"					count(CASE WHEN c.meter_number is not null AND c.ldate is not NULL then 1 END) as communicating,  \r\n" + 
					"					count(CASE WHEN c.meter_number is null then 1 END) as non_communicating, \r\n" + 
					"					(select CURRENT_DATE-1)as date, now() as insert_time,'2'  \r\n" + 
					"					from meter_data.master_main m LEFT JOIN  \r\n" + 
					"					(SELECT meter_number, max(last_communication) as ldate  \r\n" + 
					"					FROM meter_data.modem_communication  \r\n" + 
					"					where date(last_communication)=CURRENT_DATE-1 \r\n" + 
					"					GROUP BY meter_number) c on m.mtrno=c.meter_number  \r\n" + 
					"					GROUP BY zone ORDER BY zone;";

			int j = entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}

	}
	
	//@Scheduled(cron = "0 0 2 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/periodWiseCommunicationSummary", method = { RequestMethod.GET, RequestMethod.POST })
	public void periodWiseCommunicationSummary() {

		try {
			String sql = "insert into meter_data.period_wise_data (\r\n" + 
					"town_code,\r\n" + 
					"date,\r\n" + 
					"total_meter,\r\n" + 
					"communicating,\r\n" + 
					"non_communicating,\r\n" + 
					"total_dt_meter,\r\n" + 
					"dt_communicating,\r\n" + 
					"dt_non_communicating,\r\n" + 
					"total_feeder_meter,\r\n" + 
					"fm_commuicating,\r\n" + 
					"fm_non_communicating,\r\n" + 
					"total_boundary_meter,\r\n" + 
					"bm_communicating,\r\n" + 
					"bm_non_communicating,\r\n" + 
					"insert_time,\r\n" + 
					"zone,\r\n" + 
					"circle\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"select aa.town_code as town_code,CURRENT_DATE-1 as date,aa.total as total_meter,aa.comm_today as communicating,aa.not_com_today as non_communicating,aa.total_dt_meter as total_dt_meter,aa.dt_communicating as dt_communicating, aa.dt_non_communicating as dt_non_communicating,aa.total_feeder_meter as total_feeder_meter,aa.fm_commuicating as fm_commuicating,aa.fm_non_communicating as fm_non_communicating, aa.total_boundary_meter as total_boundary_meter, aa.bm_communicating as bm_communicating,aa.bm_non_communicating as bm_non_communicating,now() as insert_time,aa.zone,aa.circle from (\r\n" + 
					"select mm.zone,mm.circle, mm.town_code,\r\n" + 
					"(CURRENT_DATE-1) as date ,\r\n" + 
					"count(*) as total,\r\n" + 
					"count(case WHEN date(mm.lcom)=CURRENT_DATE-1 THEN 1 END) as comm_today,\r\n" + 
					"count(case WHEN mm.lcom is null THEN 1 END) as not_com_today,\r\n" + 
					"count(CASE WHEN mm.fdrcategory='FEEDER METER' THEN 1 END) as total_feeder_meter,\r\n" + 
					"count(CASE WHEN mm.fdrcategory='FEEDER METER' and date (mm.lcom)=CURRENT_DATE-1 THEN 1 END) as fm_commuicating,\r\n" + 
					"count(CASE WHEN mm.fdrcategory='FEEDER METER' and mm.lcom is null THEN 1 END) as fm_non_communicating,\r\n" + 
					"count(CASE WHEN mm.fdrcategory='BOUNDARY METER' THEN 1 END) as total_boundary_meter,\r\n" + 
					"count(CASE WHEN mm.fdrcategory='BOUNDARY METER' and date (mm.lcom)=CURRENT_DATE-1 THEN 1 END) as bm_communicating,\r\n" + 
					"count(CASE WHEN mm.fdrcategory='BOUNDARY METER' and mm.lcom is null THEN 1 END) as bm_non_communicating,\r\n" + 
					"count(CASE WHEN mm.fdrcategory='DT' THEN 1 END) as total_dt_meter,\r\n" + 
					"count(CASE WHEN mm.fdrcategory='DT' and date (mm.lcom)=CURRENT_DATE-1 THEN 1 END) as dt_communicating,\r\n" + 
					"count(CASE WHEN mm.fdrcategory='DT' and mm.lcom is null THEN 1 END) as dt_non_communicating\r\n" + 
					"from\r\n" + 
					"(SELECT m.town_code,m.fdrcategory,m.mtrno,mc.lcom,m.zone,m.circle FROM\r\n" + 
					"(SELECT fdrcategory,mtrno,town_code,zone,circle from meter_data.master_main\r\n" + 
					")m\r\n" + 
					"left join\r\n" + 
					"(SELECT meter_number,max(last_communication) as lcom fROM meter_data.modem_communication where date(last_communication)=CURRENT_DATE-1 GROUP BY meter_number)mc\r\n" + 
					"on m.mtrno = mc.meter_number order by m.town_code,m.zone,m.circle)mm\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"GROUP BY mm.town_code,mm.zone,mm.circle) AA";

			int j = entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data not inserted successfully.");
		}

	}
	
	

	//@Scheduled(cron="0 0 0/1 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/unmaprefresh", method = { RequestMethod.GET, RequestMethod.POST })
	public void unmaprefresh() {

		try {

			String sql = "REFRESH MATERIALIZED VIEW meter_data.unmapped";
			int j = entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Scheduled(cron="0 0 0/1 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/ondemandrefresh", method = { RequestMethod.GET, RequestMethod.POST })
	public void ondemandrefresh() {

		try {

			String sql = "REFRESH MATERIALIZED VIEW meter_data.ondemand";
			int j = entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		//@Scheduled(cron = "0 */3 * ? * *")
		@Transactional(propagation = Propagation.REQUIRED)
		@RequestMapping(value = "/unmapped", method = { RequestMethod.GET, RequestMethod.POST })
		public void unmapped() {

			try {

				String sql = "REFRESH MATERIALIZED VIEW meter_data.unmapped";
				int j = entityManager.createNativeQuery(sql).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	 
	// @Scheduled(cron = "0 0 5 * * ?")
		@Transactional(propagation = Propagation.REQUIRED)
		@RequestMapping(value = "/pendingDailyLoadSurveyDataPush", method = { RequestMethod.GET, RequestMethod.POST })
		public void pendingDailyLoadSurveyDataPush() {

			System.out.println("Load Survey DT" + " - " + new Date());

			String towncode = null;

			for (int i = 5; i >= 0; i -= 1) {

				System.out.println("This is I " + i);

				if (i == 5) {
					towncode = "5%";
				}

				if (i == 4) {
					towncode = "4%";
				}

				if (i == 3) {
					towncode = "3%";
				}
				if (i == 2) {
					towncode = "2%";
				}

				if (i == 1) {
					towncode = "1%";
				}

				if (i == 0) {
					towncode = "0%";
				}
				List<Object[]> list = new ArrayList<>();

				String sql = "Insert into meter_data.load_survey_dt (circle,\r\n" + 
						"							tp_town_code,dttpid,yearmonth,\r\n" + 
						"							kwh,kvah,kw,kva,pf,ir,iy,ib,vr,vy,vb,billmonth)\r\n" + 
						"select * from\r\n" + 
						"(SELECT distinct Y.circle, X.*,to_char(CURRENT_TIMESTAMP,'yyyyMM') as billmonth\r\n" + 
						"									FROM 				(\r\n" + 
						"									SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" + 
						"									SUM ( b.kwh ) AS kwh,    SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n" + 
						"									round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 7 ) pf,\r\n" + 
						"									SUM ( i_r ) AS ir,SUM ( i_y ) AS iy,SUM ( i_b ) AS ib,\r\n" + 
						"									AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb\r\n" + 
						"									FROM\r\n" + 
						"									( SELECT dttpid,meterno,tp_town_code FROM meter_data.dtdetails  WHERE\r\n" + 
						"									dttpid IN    ( SELECT dttpid FROM\r\n" + 
						"									( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
						"									 meterno IS NOT NULL AND meterno != '' ) b\r\n" + 
						"									) and tp_town_code like '"+towncode+"' and dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
						"									 meterno IS NOT NULL AND meterno != '') A, 				 ( \r\n" + 
						"									  				 SELECT  meter_number, read_time AS yearmonth,\r\n" + 
						"									kwh, kvah, kw, i_r,i_y,i_b,\r\n" + 
						"									COALESCE ( v_r, 0 ) AS v_r, COALESCE ( v_y, 0 ) AS v_y, COALESCE ( v_b, 0 ) AS v_b, frequency,\r\n" + 
						"									 kva FROM meter_data.load_survey\r\n" + 
						"									WHERE date(read_time)=CURRENT_DATE-1 and meter_number in \r\n" + 
						"									(select meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
						"									 meterno IS NOT NULL AND meterno != '' and tp_town_code like '"+towncode+"')\r\n" + 
						"									GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH25:mi:ss' ), kwh, frequency,read_time,\r\n" + 
						"									kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva  				\r\n" + 
						"									 				) b\r\n" + 
						"									WHERE     A.meterno = b.meter_number \r\n" + 
						"									GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + 
						"									( SELECT *  FROM meter_data.master_main am\r\n" + 
						"									) Y WHERE Y.location_id = X.dttpid ) B Where B.dttpid not in \r\n" + 
						"									(select dttpid from meter_data.load_survey_dt where date(yearmonth)=CURRENT_DATE-1)\r\n" + 
						"									\r\n" + 
						"								";

				// System.out.println("load_survey_dt_push----->"+ sql);

				try {
					int j = entityManager.createNativeQuery(sql).executeUpdate();
					System.out.println("Data inserted successfully.");

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Data not inserted successfully.");
				}
			}

		}
	 
		
		
		// @Scheduled(cron = "0 0 5 * * ?")
				@Transactional(propagation = Propagation.REQUIRED)
				@RequestMapping(value = "/pendingweeklyLoadSurveyDataPush", method = { RequestMethod.GET, RequestMethod.POST })
				public void pendingweeklyLoadSurveyDataPush() {

					System.out.println("Load Survey DT" + " - " + new Date());

					String towncode = null;

					for (int i = 5; i >= 0; i -= 1) {

						System.out.println("This is I " + i);

						if (i == 5) {
							towncode = "5%";
						}

						if (i == 4) {
							towncode = "4%";
						}

						if (i == 3) {
							towncode = "3%";
						}
						if (i == 2) {
							towncode = "2%";
						}

						if (i == 1) {
							towncode = "1%";
						}

						if (i == 0) {
							towncode = "0%";
						}
						List<Object[]> list = new ArrayList<>();

						String sql = "Insert into meter_data.load_survey_dt (circle,\r\n" + 
								"							tp_town_code,dttpid,yearmonth,\r\n" + 
								"							kwh,kvah,kw,kva,pf,ir,iy,ib,vr,vy,vb,billmonth)							\r\n" + 
								"									\r\n" + 
								"									select * from\r\n" + 
								"(SELECT distinct Y.circle, X.*,to_char(CURRENT_TIMESTAMP,'yyyyMM') as billmonth\r\n" + 
								"									FROM 				(\r\n" + 
								"									SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" + 
								"									SUM ( b.kwh ) AS kwh,    SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n" + 
								"									round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 7 ) pf,\r\n" + 
								"									SUM ( i_r ) AS ir,SUM ( i_y ) AS iy,SUM ( i_b ) AS ib,\r\n" + 
								"									AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb\r\n" + 
								"									FROM\r\n" + 
								"									( SELECT dttpid,meterno,tp_town_code FROM meter_data.dtdetails  WHERE\r\n" + 
								"									dttpid IN    ( SELECT dttpid FROM\r\n" + 
								"									( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
								"									 meterno IS NOT NULL AND meterno != '' ) b\r\n" + 
								"									) and tp_town_code like '"+towncode+"' and dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
								"									 meterno IS NOT NULL AND meterno != '') A, 				 ( \r\n" + 
								"									  				 SELECT  meter_number, read_time AS yearmonth,\r\n" + 
								"									kwh, kvah, kw, i_r,i_y,i_b,\r\n" + 
								"									COALESCE ( v_r, 0 ) AS v_r, COALESCE ( v_y, 0 ) AS v_y, COALESCE ( v_b, 0 ) AS v_b, frequency,\r\n" + 
								"									 kva FROM meter_data.load_survey\r\n" + 
								"									WHERE date(read_time) between (date_trunc('week', ((CURRENT_DATE - 7)))) AND (date_trunc('week', ((CURRENT_DATE - 7)))) and meter_number in \r\n" + 
								"									(select meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
								"									 meterno IS NOT NULL AND meterno != '' and tp_town_code like '"+towncode+"')\r\n" + 
								"									GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH25:mi:ss' ), kwh, frequency,read_time,\r\n" + 
								"									kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva  				\r\n" + 
								"									 				) b\r\n" + 
								"									WHERE     A.meterno = b.meter_number \r\n" + 
								"									GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + 
								"									( SELECT *  FROM meter_data.master_main am\r\n" + 
								"									) Y WHERE Y.location_id = X.dttpid ) B Where B.dttpid not in \r\n" + 
								"									(select dttpid from meter_data.load_survey_dt where date(yearmonth) between (date_trunc('week', ((CURRENT_DATE - 7)))) AND (date_trunc('week', ((CURRENT_DATE - 1)))))";

						// System.out.println("load_survey_dt_push----->"+ sql);

						try {
							int j = entityManager.createNativeQuery(sql).executeUpdate();
							System.out.println("Data inserted successfully.");

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Data not inserted successfully.");
						}
					}

				}
				
				
				// @Scheduled(cron = "0 0 5 * * ?")
				@Transactional(propagation = Propagation.REQUIRED)
				@RequestMapping(value = "/pendingCurrentMonthLoadSurveyDataPush", method = { RequestMethod.GET, RequestMethod.POST })
				public void pendingCurrentMonthLoadSurveyDataPush() {

					System.out.println("Load Survey DT" + " - " + new Date());

					String towncode = null;

					for (int i = 5; i >= 0; i -= 1) {

						System.out.println("This is I " + i);

						if (i == 5) {
							towncode = "5%";
						}

						if (i == 4) {
							towncode = "4%";
						}

						if (i == 3) {
							towncode = "3%";
						}
						if (i == 2) {
							towncode = "2%";
						}

						if (i == 1) {
							towncode = "1%";
						}

						if (i == 0) {
							towncode = "0%";
						}
						List<Object[]> list = new ArrayList<>();

						String sql = "	Insert into meter_data.load_survey_dt (circle,\r\n" + 
								"							tp_town_code,dttpid,yearmonth,\r\n" + 
								"							kwh,kvah,kw,kva,pf,ir,iy,ib,vr,vy,vb,billmonth)							\r\n" + 
								"									\r\n" + 
								"									select * from\r\n" + 
								"(SELECT distinct Y.circle, X.*,to_char(CURRENT_TIMESTAMP,'yyyyMM') as billmonth\r\n" + 
								"									FROM 				(\r\n" + 
								"									SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" + 
								"									SUM ( b.kwh ) AS kwh,    SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n" + 
								"									round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 7 ) pf,\r\n" + 
								"									SUM ( i_r ) AS ir,SUM ( i_y ) AS iy,SUM ( i_b ) AS ib,\r\n" + 
								"									AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb\r\n" + 
								"									FROM\r\n" + 
								"									( SELECT dttpid,meterno,tp_town_code FROM meter_data.dtdetails  WHERE\r\n" + 
								"									dttpid IN    ( SELECT dttpid FROM\r\n" + 
								"									( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
								"									 meterno IS NOT NULL AND meterno != '' ) b\r\n" + 
								"									) and tp_town_code like '"+towncode+"' and dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
								"									 meterno IS NOT NULL AND meterno != '') A, 				 ( \r\n" + 
								"									  				 SELECT  meter_number, read_time AS yearmonth,\r\n" + 
								"									kwh, kvah, kw, i_r,i_y,i_b,\r\n" + 
								"									COALESCE ( v_r, 0 ) AS v_r, COALESCE ( v_y, 0 ) AS v_y, COALESCE ( v_b, 0 ) AS v_b, frequency,\r\n" + 
								"									 kva FROM meter_data.load_survey\r\n" + 
								"									WHERE date(read_time) > (CURRENT_DATE - INTERVAL '1' MONTH) and meter_number in \r\n" + 
								"									(select meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
								"									 meterno IS NOT NULL AND meterno != '' and tp_town_code like '"+towncode+"')\r\n" + 
								"									GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH25:mi:ss' ), kwh, frequency,read_time,\r\n" + 
								"									kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva  				\r\n" + 
								"									 				) b\r\n" + 
								"									WHERE     A.meterno = b.meter_number \r\n" + 
								"									GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + 
								"									( SELECT *  FROM meter_data.master_main am\r\n" + 
								"									) Y WHERE Y.location_id = X.dttpid ) B Where B.dttpid not in \r\n" + 
								"									(select dttpid from meter_data.load_survey_dt where date(yearmonth) > (CURRENT_DATE - INTERVAL '1' MONTH))	";

						// System.out.println("load_survey_dt_push----->"+ sql);

						try {
							int j = entityManager.createNativeQuery(sql).executeUpdate();
							System.out.println("Data inserted successfully.");

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Data not inserted successfully.");
						}
					}

				}
				
				 //@Scheduled(cron = "0 0 5 * * ?")
				@Transactional(propagation = Propagation.REQUIRED)
				@RequestMapping(value = "/pendingPreviousMonthLoadSurveyDataPush", method = { RequestMethod.GET, RequestMethod.POST })
				public void pendingPreviousMonthLoadSurveyDataPush() {

					System.out.println("Load Survey DT" + " - " + new Date());

					String towncode = null;

					for (int i = 5; i >= 0; i -= 1) {

						System.out.println("This is I " + i);

						if (i == 5) {
							towncode = "5%";
						}

						if (i == 4) {
							towncode = "4%";
						}

						if (i == 3) {
							towncode = "3%";
						}
						if (i == 2) {
							towncode = "2%";
						}

						if (i == 1) {
							towncode = "1%";
						}

						if (i == 0) {
							towncode = "0%";
						}
						List<Object[]> list = new ArrayList<>();

						String sql = "	Insert into meter_data.load_survey_dt (circle,\r\n" + 
								"							tp_town_code,dttpid,yearmonth,\r\n" + 
								"							kwh,kvah,kw,kva,pf,ir,iy,ib,vr,vy,vb,billmonth)							\r\n" + 
								"									\r\n" + 
								"									select * from\r\n" + 
								"(SELECT distinct Y.circle, X.*,to_char(CURRENT_TIMESTAMP,'yyyyMM') as billmonth\r\n" + 
								"									FROM 				(\r\n" + 
								"									SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" + 
								"									SUM ( b.kwh ) AS kwh,    SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n" + 
								"									round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 7 ) pf,\r\n" + 
								"									SUM ( i_r ) AS ir,SUM ( i_y ) AS iy,SUM ( i_b ) AS ib,\r\n" + 
								"									AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb\r\n" + 
								"									FROM\r\n" + 
								"									( SELECT dttpid,meterno,tp_town_code FROM meter_data.dtdetails  WHERE\r\n" + 
								"									dttpid IN    ( SELECT dttpid FROM\r\n" + 
								"									( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
								"									 meterno IS NOT NULL AND meterno != '' ) b\r\n" + 
								"									) and tp_town_code like '"+towncode+"' and dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
								"									 meterno IS NOT NULL AND meterno != '') A, 				 ( \r\n" + 
								"									  				 SELECT  meter_number, read_time AS yearmonth,\r\n" + 
								"									kwh, kvah, kw, i_r,i_y,i_b,\r\n" + 
								"									COALESCE ( v_r, 0 ) AS v_r, COALESCE ( v_y, 0 ) AS v_y, COALESCE ( v_b, 0 ) AS v_b, frequency,\r\n" + 
								"									 kva FROM meter_data.load_survey\r\n" + 
								"									WHERE date(read_time) > (CURRENT_DATE - INTERVAL '2' MONTH) and meter_number in \r\n" + 
								"									(select meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND\r\n" + 
								"									 meterno IS NOT NULL AND meterno != '' and tp_town_code like '"+towncode+"')\r\n" + 
								"									GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH25:mi:ss' ), kwh, frequency,read_time,\r\n" + 
								"									kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva  				\r\n" + 
								"									 				) b\r\n" + 
								"									WHERE     A.meterno = b.meter_number \r\n" + 
								"									GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + 
								"									( SELECT *  FROM meter_data.master_main am\r\n" + 
								"									) Y WHERE Y.location_id = X.dttpid ) B Where B.dttpid not in \r\n" + 
								"									(select dttpid from meter_data.load_survey_dt where date(yearmonth) > (CURRENT_DATE - INTERVAL '2' MONTH))";

						// System.out.println("load_survey_dt_push----->"+ sql);

						try {
							int j = entityManager.createNativeQuery(sql).executeUpdate();
							System.out.println("Data inserted successfully.");

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Data not inserted successfully.");
						}
					}

				}
	 

}
