package com.bcits.mdas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;

@Controller
public class DailyOrMonthlyConsumptionController {
	@Autowired
	AmiLocationService als;
	
	@Autowired
	private FeederDetailsService feederdetailsservice;
	
	@RequestMapping(value="/dailyConsumption",method={RequestMethod.GET,RequestMethod.POST})
	public String dailyConsumption(ModelMap model){
		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		List<?> fdrcatList = feederdetailsservice.getDistinctCategory();
		model.put("fdrcatList", fdrcatList);
		return "DailyConsumption";
	}
	@RequestMapping(value="/dailyConsumptionList" ,method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object dailyConsumptionList(HttpServletRequest request){
		System.err.println("hiiiiii");
		String zone=request.getParameter("zone");
		String circle=request.getParameter("circle");
		String division=request.getParameter("division");
		String sdoname=request.getParameter("sdoname");
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		String sitecode="21%";
			if(!circle.equalsIgnoreCase("")&&division.equalsIgnoreCase("")&&sdoname.equalsIgnoreCase("")){
			
				sitecode=als.circlecode(circle).substring(0, 4);
			
		}
			if(!circle.equalsIgnoreCase("")&&!division.equalsIgnoreCase("")&&sdoname.equalsIgnoreCase("")){
				
					sitecode=als.divisionCode(division).substring(0, 5);
				
			}
			if(!circle.equalsIgnoreCase("")&&!division.equalsIgnoreCase("")&&!sdoname.equalsIgnoreCase("")){
				
					sitecode=als.subDivisionCode(sdoname);
				
			}
		String sql="select \n" +
				"(select zone from meter_data.amilocation where to_char(sitecode,'9999999') like '%'||location_code||'%') as zone,\n" +
				"(select circle from meter_data.amilocation where to_char(sitecode,'9999999') like '%'||location_code||'%') as circle,\n" +
				"(select division from meter_data.amilocation where to_char(sitecode,'9999999') like '%'||location_code||'%') as division,\n" +
				"(select subdivision from meter_data.amilocation where to_char(sitecode,'9999999') like '%'||location_code||'%') as subdivision,\n" +
				"kno,mtrno,date,kwh_imp,kwh_exp,kvah_imp,kvah_exp from meter_data.daily_consumption where location_code LIKE '"+sitecode+"%' and date>='"+fdate+"' and date<='"+tdate+"'";
		System.out.println(sql);
		List<Object[]> l=als.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		
		return l;
	}
	@RequestMapping(value="/monthlyConsumptionReport",method={RequestMethod.GET,RequestMethod.POST})
	public String monthlyConsumption(HttpServletRequest r,ModelMap model){
		List<?> zoneList = feederdetailsservice.getDistinctZone();
		model.put("zoneList", zoneList);
		return "MonthlyConsumptionReport";
	}
	@RequestMapping(value="/monthlyConsumptionList" ,method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object monthlyConsumptionList(@RequestParam String zone,@RequestParam String circle,@RequestParam String division,@RequestParam String sdoname,@RequestParam String mothID){
		String sitecode="21%";
			if(!circle.equalsIgnoreCase("")&&division.equalsIgnoreCase("")&&sdoname.equalsIgnoreCase("")){
			
				sitecode=als.circlecode(circle).substring(0, 4);
			
		}
			if(!circle.equalsIgnoreCase("")&&!division.equalsIgnoreCase("")&&sdoname.equalsIgnoreCase("")){
				
					sitecode=als.divisionCode(division).substring(0, 5);
				
			}
			if(!circle.equalsIgnoreCase("")&&!division.equalsIgnoreCase("")&&!sdoname.equalsIgnoreCase("")){
				
					sitecode=als.subDivisionCode(sdoname);
				
			}
		String sql="select \n" +
				"(select zone from meter_data.amilocation where to_char(sitecode,'9999999') like '%'||location_code||'%') as zone,\n" +
				"(select circle from meter_data.amilocation where to_char(sitecode,'9999999') like '%'||location_code||'%') as circle,\n" +
				"(select division from meter_data.amilocation where to_char(sitecode,'9999999') like '%'||location_code||'%') as division,\n" +
				"(select subdivision from meter_data.amilocation where to_char(sitecode,'9999999') like '%'||location_code||'%') as subdivision,\n" +
				"kno,mtrno,billmonth,kwh_imp,kwh_exp,kvah_imp,kvah_exp,\n" +
				"case when kvarh_imp_act_imp is null then 0 end as kvarh_imp_act_imp , case when kvarh_imp_act_exp is null then 0 end as kvarh_imp_act_exp,case when  kvarh_exp_act_imp is null then 0 end as kvarh_exp_act_imp ,  case when  kvarh_exp_act_exp is null then 0 end as kvarh_exp_act_exp from meter_data.monthly_consumption "+ " where location_code LIKE '"+sitecode+"%' and billmonth="+mothID+" ";
		List<Object[]> l=als.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		
		return l;
	}
}
