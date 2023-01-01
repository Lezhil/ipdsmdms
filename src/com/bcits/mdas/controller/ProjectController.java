package com.bcits.mdas.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@RequestMapping(value = "/getSurveyAndInstallStatus", method = { RequestMethod.GET })
	public @ResponseBody String getSurveyAndInstallStatus(ModelMap model, HttpServletRequest request)throws JSONException// get active and inactive count
	{
		System.out.println("################################## getSurveyAndInstallStatus ");

		String active = "SELECT 'total' as category ,count(*) as total from meter_data.master UNION ALL "
				+ " (SELECT 'surveyed' as category , count(*) as totalSurveyed from vcloudengine.feeder_output)UNION ALL "
				+ " (SELECT 'installed' as category , count(*) as installed from meter_data.master_main)";

		System.err.println("GET SURVEY INSTALL COUNT : " + active);
		Query queryUpload = entityManager.createNativeQuery(active);
		List<Object[]> li = queryUpload.getResultList();
		
		int total=0,surveyed=0, installed=0;
		for (Object[] columns : li) { 
			String category = columns[0].toString().trim();
			int count = Integer.parseInt(columns[1].toString()); 
			if(category.equalsIgnoreCase("total")){
				total=count;
			}else if(category.equalsIgnoreCase("surveyed")){
				surveyed=count;
			}else if(category.equalsIgnoreCase("installed")){ 
				installed=count;
			}  
		}

		// LAST 30 DAYS STATUS
		/*String queryLast30 = "select to_char((CURRENT_DATE + i), 'Mon DD') as date ,COALESCE(modem.total,0) as surveyed,COALESCE(install.total,0) as installed from generate_series(-29,0 ) i LEFT JOIN (SELECT count(*) as total,split_part(timestaken, ' ', 1) as timestaken FROM vcloudengine.feeder_output GROUP BY split_part(timestaken, ' ', 1)) modem on  to_date(modem.timestaken , 'DD/MM/YYYY')=CURRENT_DATE + i  LEFT JOIN (SELECT count(*) as total,split_part(timestaken, ' ', 1) as timestaken FROM vcloudengine.modem_installation  GROUP BY split_part(timestaken, ' ', 1)) install on to_date(install.timestaken , 'DD/MM/YYYY')=CURRENT_DATE + i";*/
		String queryLast30 ="SELECT tbl1.date,tbl1.surveyed,tbl1.installed,COALESCE(tbl2.active,0) as active from (select to_char((CURRENT_DATE + i), 'Mon DD') as date ,COALESCE(modem.total,0) as surveyed,i,COALESCE(install.total,0) as installed from generate_series(-29,0 ) i LEFT JOIN (SELECT count(*) as total,split_part(timestaken, ' ', 1) as timestaken FROM vcloudengine.feeder_output GROUP BY split_part(timestaken, ' ', 1)) modem on  to_date(modem.timestaken , 'DD/MM/YYYY')=CURRENT_DATE + i LEFT JOIN (SELECT count(*) as total,split_part(timestaken, ' ', 1) as timestaken FROM vcloudengine.modem_installation  GROUP BY split_part(timestaken, ' ', 1)) install on to_date(install.timestaken , 'DD/MM/YYYY')=CURRENT_DATE + i)tbl1 LEFT JOIN (SELECT count(DISTINCT meter_no) as active,split_part(timestaken, ' ', 1)as timestaken from vcloudengine.modem_installation inst,meter_data.modem_communication mc where mc.meter_number=inst.meter_no GROUP BY split_part(inst.timestaken, ' ', 1))tbl2 on to_date(tbl2.timestaken , 'DD/MM/YYYY')=CURRENT_DATE+i";
		
		System.err.println("GET SURVEY INSTALL LAST 30 DAYS : " + queryLast30);
		Query query30 = entityManager.createNativeQuery(queryLast30);
		List<Object[]> list30 = query30.getResultList();
		
		JSONArray installs = new JSONArray();
		JSONArray surveys = new JSONArray();
		JSONArray dates = new JSONArray();
		JSONArray communicated = new JSONArray();
		int maxCount=0;
		for (int i = 0; i < list30.size(); i++) {
			Object[] objects = list30.get(i);
			String dat = objects[0].toString().trim();
			Integer surv = Integer.parseInt(objects[1].toString());
			Integer inst = Integer.parseInt(objects[2].toString());
			Integer com=Integer.parseInt(objects[3].toString());
			//System.out.println("com=========="+com);
			if(surv >maxCount){
				maxCount=surv;
			}
			if(inst >maxCount){
				maxCount=inst;
			}
			if(com>maxCount){maxCount=com;}
			
			dates.put(dat);
			surveys.put(surv);
			installs.put(inst);
			communicated.put(com);
		}
		
		System.out.println("MAX COUNT ========= "+maxCount);
		
		//MONTHWISE COUNT
		String monthWiseCount = "SELECT 'surveyedThisMonth' as category , count(*) as totalSurveyed from vcloudengine.feeder_output where  "
+" to_char((to_date(split_part(timestaken, ' ', 1), 'DD/MM/YYYY')), 'YYYYMM')=to_char(CURRENT_DATE, 'YYYYMM')"
+" UNION ALL  (SELECT 'installedThisMonth' as category , count(*) as installed from vcloudengine.modem_installation where  "
+" to_char((to_date(split_part(timestaken, ' ', 1), 'DD/MM/YYYY')), 'YYYYMM')=to_char(CURRENT_DATE, 'YYYYMM'))"
+" UNION ALL SELECT 'surveyedLastMonth' as category , count(*) as totalSurveyed from vcloudengine.feeder_output where  "
+" to_char((to_date(split_part(timestaken, ' ', 1), 'DD/MM/YYYY')), 'YYYYMM')=to_char(CURRENT_DATE- interval '1 month', 'YYYYMM')"
+" UNION ALL  (SELECT 'installedLastMonth' as category , count(*) as installed from vcloudengine.modem_installation where  "
+" to_char((to_date(split_part(timestaken, ' ', 1), 'DD/MM/YYYY')), 'YYYYMM')=to_char(CURRENT_DATE- interval '1 month', 'YYYYMM'))";

		System.err.println("GET SURVEY MONTH COUNT : " + monthWiseCount);
		Query queryMonth = entityManager.createNativeQuery(monthWiseCount);
		List<Object[]> listMonth = queryMonth.getResultList();
		
		int surveyedLastMonth=0,surveyedThisMonth=0, installedLastMonth=0, installedThisMonth=0;
		for (Object[] columns : listMonth) { 
			String category = columns[0].toString().trim();
			int count = Integer.parseInt(columns[1].toString()); 
			if(category.equalsIgnoreCase("surveyedThisMonth")){
				surveyedThisMonth=count;
			}else if(category.equalsIgnoreCase("installedThisMonth")){
				installedThisMonth=count;
			}else if(category.equalsIgnoreCase("surveyedLastMonth")){ 
				surveyedLastMonth=count;
			}else if(category.equalsIgnoreCase("installedLastMonth")){ 
				installedLastMonth=count;
			}  
		}
		
		
		
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject(); 
		int survey =surveyed-installed;
		obj.put("name", "Surveyed");
		obj.put("y", survey);
		array.put(obj);
		 
		obj=new JSONObject();
		int pending =total-surveyed;
		obj.put("name", "Pending");
		obj.put("y",  pending);
		array.put(obj);
		
		obj=new JSONObject();
		obj.put("name", "Installed");
		obj.put("y", installed);
		array.put(obj); 
		
		JSONObject object = new JSONObject();
		object.put("graphData", array);
		object.put("total", total);
		object.put("surveyed", surveyed);
		int surveyPending = total-surveyed;
		object.put("surveyPending", surveyPending);
		object.put("installed", installed);
		int installPending = total-installed;
		object.put("installPending", installPending);
		//BAR GRAPH ITEMS
		object.put("graph_max", maxCount);
		object.put("dates_graph", dates);
		object.put("survey_graph", surveys);
		object.put("install_graph", installs);
		object.put("communi_graph", communicated);
		//MONTH COUNT
		object.put("surveyedLastMonth", surveyedLastMonth);
		object.put("surveyedThisMonth", surveyedThisMonth);
		object.put("installedLastMonth", installedLastMonth);
		object.put("installedThisMonth", installedThisMonth);
		return object.toString();
	}

}
