package com.bcits.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.controller.ReportController;
import com.bcits.service.LoadAvailabilityRptService;

@Controller
public class LoadsurveyController {
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager entityManager;
	@Autowired
	private LoadAvailabilityRptService loadAvailabilityRptService;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	
	@RequestMapping(value="/loadAvailabilityReport",method=RequestMethod.GET)
	
	public String loadAvailabilityReport(ModelMap model, HttpServletRequest request)
	{
		
		List<?> zoneList=loadAvailabilityRptService.getDistinctZone();
		model.put("zoneList", zoneList);
		
		return "loadAvailabilityReport";
	}
	@RequestMapping(value="/showCircleloadavailability/{zone}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object showCircle(@PathVariable String zone,HttpServletRequest request,ModelMap model)
	{
		//System.out.println("==--showCircle--==List=="+zone);
		return loadAvailabilityRptService.getCircleByZone(zone,model);
	}
	@RequestMapping(value="/showDivisionloadavailability/{zone}/{circle}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object showDivision(@PathVariable String zone,@PathVariable String circle,HttpServletRequest request,ModelMap model)
	{
		//System.out.println("==--showDivision--==List=="+zone+"==>>"+circle);
		return loadAvailabilityRptService.getDivisionByCircle(zone,circle,model);
	}
	@RequestMapping(value="/showSubdivByDivloadavailability/{zone}/{circle}/{division}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object showSubdivByDiv(@PathVariable String zone,@PathVariable String circle,@PathVariable String division,HttpServletRequest request,ModelMap model)
	{
		//System.out.println("==--showSubdivByDiv--==List=="+zone+"==>>"+circle+"==>>"+division);
		return loadAvailabilityRptService.getSubdivByDivisionByCircle(zone,circle,division,model);
	}
	@RequestMapping(value = "/getAvailabilityReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getAvailabilityReport(HttpServletRequest request) 
	{
		String zone = request.getParameter("zone"), circle = request.getParameter("circle"),
				division = request.getParameter("division"), subdiv = request.getParameter("sdoCode"),
				fDate = request.getParameter("fromDate"), tDate = request.getParameter("toDate");		
  return loadAvailabilityRptService.getloadavailabilityreport(zone,circle,division,subdiv,fDate,tDate);	
		}
	
	@RequestMapping(value = "/getLoadSummaryReport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getLoadSummaryReport(HttpServletRequest request) 
	{
		System.out.println("hi");
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		
	return loadAvailabilityRptService.getLoadSummaryReport(fromDate,toDate);
		}
	
	//@Scheduled(cron = "0 0/1 * * * ?")
    public void refreshLoadSurveyReport() {
		System.out.println("inside refreshLoadSurveyReport-- ");
		
		String month = dateFormat.format(new Date());
		Integer year = Integer.parseInt(month.split("-")[0]);
		Integer mon = Integer.parseInt(month.split("-")[1]);
		Integer day = Integer.parseInt(month.split("-")[2]);
		ArrayList<String> dates = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		/*cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, (mon - 1));
		cal.set(Calendar.DAY_OF_MONTH, 1);*/
		
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -15);
		//int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// System.out.println(df.format(cal.getTime()));
		dates.add(df.format(cal.getTime()));
		for (int i = 1; i <= day; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			//System.out.println(df.format(cal.getTime()));
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
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
		
	}

	

