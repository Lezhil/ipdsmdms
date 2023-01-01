package com.bcits.mdas.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.firebase.FireMessageCordova;
import com.bcits.mdas.service.AndroidService;
import com.businessobjects.report.web.json.JSONObject;
import com.ibm.icu.text.DecimalFormat;

@Controller
public class AndroidController {

	
	@Autowired
	AndroidService androidService;

	@RequestMapping(value = "/getLastReadingBasedOnKnoMitra", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String getLastReadingBasedOnKnoMitra(ModelMap model,Model model1, HttpServletRequest request)throws Exception// get active and inactive count
	{
		System.out.println("getLastReadingBasedOnKnoMitra");
		String kno = request.getParameter("kno");
		System.out.println("KNO : "+kno);

		String query="SELECT meter_number, max(kwh/1000), max(time_stamp) as time_stamp  FROM meter_data.amiinstantaneous where meter_number in (select mtrno from meter_data.master_main where kno='"+kno+"') GROUP BY meter_number ORDER BY time_stamp desc";

		List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();

		if (list.size() > 0) {
			Object[] object =list.get(0);
			String meter_number = (object[0]==null)? null :String.valueOf(object[0]).trim();
			String kwh = (object[1]==null)?"0" : (new DecimalFormat("#.##").format(Double.parseDouble(String.valueOf(object[1]).trim())));
			String time_stamp = (object[2]==null)? null :String.valueOf(object[2]).trim();
			JSONObject obj = new JSONObject();
			obj.put("meter_number", meter_number);
			obj.put("kwh", kwh);
			obj.put("time_stamp", time_stamp);
			return obj.toString();
		}else{
			return "NO_DATA_FOUND";
		}

	}

	@RequestMapping(value = "/getAmiConsumptionMobileGraph", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String getAmiConsumptionMobileGraph(ModelMap model,Model model1, HttpServletRequest request)throws Exception// get active and inactive count
	{
		System.out.println("getAmiConsumptionMobileGraph");
		String kno = request.getParameter("kno");
		//		String kno = "210444023250";
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		JSONObject resonse= new JSONObject();
		resonse.put("hourChartTime", "NA");
		resonse.put("hourChartkWh", "NA");
		resonse.put("dayChartTime", "NA");
		resonse.put("dayChartkWh", "NA");
		resonse.put("monthChartTime", "NA");
		resonse.put("monthChartkWh", "NA");
	
		System.out.println("KNO : "+kno);

		try {
			
			//current day
			String query="select read_time,(COALESCE(kwh,0)/1000)  from meter_data.load_survey where meter_number = (select mtrno from meter_data.master_main where kno='"+kno+"')  AND to_char(read_time, 'yyyy-MM-dd') = '"+date+"' ORDER BY read_time ASC";
			List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
			if (list.size() > 0) {
				ArrayList<String> hourTimes= new ArrayList<>();
				ArrayList<Double> hourkWhs= new ArrayList<>();
				for(int i=0 ; i<list.size() ; i++){
					Object[] object =list.get(i);
					String time = (object[0]==null)? null :String.valueOf(object[0]).trim();
					Double kwh = (object[1]==null)? 0.0 : (Double.parseDouble(String.valueOf(object[1]).trim()));
					hourTimes.add(time);
					hourkWhs.add(kwh);
				}
				resonse.put("hourChartTime", hourTimes);
				resonse.put("hourChartkWh", hourkWhs);

			} 
			
			//current month
			query="SELECT to_date(to_char(read_time,'yyyy-MM-dd'),'yyyy-MM-dd') as date ,SUM (COALESCE(kwh,0)/1000) FROM meter_data.load_survey WHERE meter_number = (select mtrno  from meter_data.master_main where kno='"+kno+"') AND to_date(to_char(read_time,'yyyy-MM-dd'),'yyyy-MM-dd')  >= current_date - interval '30' day GROUP BY date order by date DESC";
			list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
			if (list.size() > 0) {
				ArrayList<String> dayTimes= new ArrayList<>();
				ArrayList<Double> daykWhs= new ArrayList<>();
				for(int i=0 ; i<list.size() ; i++){
					Object[] object =list.get(i);
					String time = (object[0]==null)? null :String.valueOf(object[0]).trim();
					Double kwh = (object[1]==null)? 0.0 : (Double.parseDouble(String.valueOf(object[1]).trim()));
					dayTimes.add(time);
					daykWhs.add(kwh);
				}
				resonse.put("dayChartTime", dayTimes);
				resonse.put("dayChartkWh", daykWhs);

			}

					
			//current year
			query=" SELECT to_date(to_char(read_time,'yyyy-MM'),'yyyy-MM') as date ,SUM (COALESCE(kwh,0)/1000)"
 +" FROM meter_data.load_survey WHERE meter_number ="
 +" (select mtrno  from meter_data.master_main where kno='"+kno+"') AND to_date(to_char(read_time,'yyyy-MM'),'yyyy-MM')  "
 +" >= current_date - interval '12' month GROUP BY date order by date DESC";
			list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();

			if (list.size() > 0) {
				ArrayList<String> monthTimes= new ArrayList<>();
				ArrayList<Double> monthkWhs= new ArrayList<>();
				for(int i=0 ; i<list.size() ; i++){
					Object[] object =list.get(i);
					String time = (object[0]==null)? null :String.valueOf(object[0]).trim();
					Double kwh = (object[1]==null)? 0.0 : (Double.parseDouble(String.valueOf(object[1]).trim()));
					monthTimes.add(time);
					monthkWhs.add(kwh);
				}
				resonse.put("monthChartTime", monthTimes);
				resonse.put("monthChartkWh", monthkWhs);

			}
			

			
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return resonse.toString();
	}
	private Date yesterday() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    return cal.getTime();
	}

	@RequestMapping(value = "/getAmiConsumptionMobileGraphPrev", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String getAmiConsumptionMobileGraphPrev(ModelMap model,Model model1, HttpServletRequest request)throws Exception// get active and inactive count
	{
		System.out.println("getAmiConsumptionMobileGraphPrev");
		String kno = request.getParameter("kno");
		//		String kno = "210444023250";
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String prevdate= dateFormat.format(yesterday());
	    System.out.println("prevdate=="+prevdate);

		JSONObject resonse= new JSONObject();
		
		//prev
		resonse.put("prevhourChartTime", "NA");
		resonse.put("prevhourChartkWh", "NA");
		resonse.put("prevdayChartTime", "NA");
		resonse.put("prevdayChartkWh", "NA");
		resonse.put("prevmonthChartTime", "NA");
		resonse.put("prevmonthChartkWh", "NA");
		System.out.println("KNO : "+kno);

		try {			
			//previous day
			String query="select read_time,(COALESCE(kwh,0)/1000)  from meter_data.load_survey where meter_number = (select mtrno from meter_data.master_main where kno='"+kno+"')  AND to_char(read_time, 'yyyy-MM-dd') = '"+prevdate+"' ORDER BY read_time ASC";
			List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
			if (list.size() > 0) {
				ArrayList<String> hourTimes= new ArrayList<>();
				ArrayList<Double> hourkWhs= new ArrayList<>();
				for(int i=0 ; i<list.size() ; i++){
					Object[] object =list.get(i);
					String time = (object[0]==null)? null :String.valueOf(object[0]).trim();
					Double kwh = (object[1]==null)? 0.0 : (Double.parseDouble(String.valueOf(object[1]).trim()));
					hourTimes.add(time);
					hourkWhs.add(kwh);
				}
				resonse.put("prevhourChartTime", hourTimes);
				resonse.put("prevhourChartkWh", hourkWhs);
			
			} 
						
			//previous month
			query="SELECT to_date(to_char(read_time,'yyyy-MM-dd'),'yyyy-MM-dd') as date ,SUM (COALESCE(kwh,0)/1000) FROM meter_data.load_survey WHERE meter_number = (select mtrno  from meter_data.master_main where kno='"+kno+"') AND to_date(to_char(read_time,'yyyy-MM-dd'),'yyyy-MM-dd')  >= current_date - interval '60' day AND to_date(to_char(read_time,'yyyy-MM-dd'),'yyyy-MM-dd')  <= current_date - interval '30' day GROUP BY date order by date DESC";
			list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
			if (list.size() > 0) {
				ArrayList<String> hourTimes= new ArrayList<>();
				ArrayList<Double> hourkWhs= new ArrayList<>();
				for(int i=0 ; i<list.size() ; i++){
					Object[] object =list.get(i);
					String time = (object[0]==null)? null :String.valueOf(object[0]).trim();
					Double kwh = (object[1]==null)? 0.0 : (Double.parseDouble(String.valueOf(object[1]).trim()));
					hourTimes.add(time);
					hourkWhs.add(kwh);
				}
				resonse.put("prevdayChartTime", hourTimes);
				resonse.put("prevdayChartkWh", hourkWhs);

			} 
					
		
			//previous year
			query=" SELECT to_date(to_char(read_time,'yyyy-MM'),'yyyy-MM') as date ,SUM (COALESCE(kwh,0)/1000) FROM meter_data.load_survey WHERE meter_number =(select mtrno  from meter_data.master_main where kno='210474032347') AND to_date(to_char(read_time,'yyyy-MM'),'yyyy-MM')  >= current_date - interval '24' month AND to_date(to_char(read_time,'yyyy-MM'),'yyyy-MM')  <= current_date - interval '12' month GROUP BY date order by date DESC";
			list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
			if (list.size() > 0) {
				ArrayList<String> monthTimes= new ArrayList<>();
				ArrayList<Double> monthkWhs= new ArrayList<>();
				for(int i=0 ; i<list.size() ; i++){
					Object[] object =list.get(i);
					String time = (object[0]==null)? null :String.valueOf(object[0]).trim();
					Double kwh = (object[1]==null)? 0.0 : (Double.parseDouble(String.valueOf(object[1]).trim()));
					monthTimes.add(time);
					monthkWhs.add(kwh);
				}
				resonse.put("prevmonthChartTime", monthTimes);
				resonse.put("prevmonthChartkWh", monthkWhs);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return resonse.toString();
	}
	
	
	@RequestMapping(value = "/getAmiDashboardMobile", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String getAmiDashboardMobile(ModelMap model,Model model1, HttpServletRequest request)throws Exception// get active and inactive count
	{
		System.out.println("getAmiDashboardMobile");
		String kno = request.getParameter("kno");
		//String kno = "210444023250";

		JSONObject resonse= new JSONObject();
		resonse.put("meterNo", "NA");
		resonse.put("meterMake", "NA");
		resonse.put("kwh", "NA");
		resonse.put("kwhDate", "NA");
		resonse.put("totalEvents", "NA");
		resonse.put("totalEventsPowerOff", "NA");
		resonse.put("thisMonthUnits", "NA");
		resonse.put("lastMonthUnits", "NA");
		resonse.put("unitsGrowth", "NA");
		resonse.put("kvah", "NA");
		resonse.put("kva", "NA");
		resonse.put("pf_threephase", "NA");
		resonse.put("md_kw", "NA");
		resonse.put("date_md_kw", "NA");
		resonse.put("md_kva", "NA");
		resonse.put("date_md_kva", "NA");
		resonse.put("frequency", "NA");
		resonse.put("voltage", "NA");
		
		
		try {
			String queryMtr="SELECT mtrno,mtrmake from meter_data.master_main where kno='"+kno+"'";
			List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(queryMtr).getResultList();
			if (list.size() > 0) {
				Object[] object =list.get(0);
				String meterNo = (object[0]==null)? "NA" :String.valueOf(object[0]).trim();
				String meterMake = (object[1]==null)? "NA" :String.valueOf(object[1]).trim();;
				resonse.put("meterNo", meterNo);
				resonse.put("meterMake", meterMake);

				String queryReading="select to_char(read_time, 'DD-MM-YYYY HH:MI AM') mtrTime, (COALESCE(kwh,0)/1000) kwh, (COALESCE(kvah,0)/1000) kvah, (COALESCE(kva,0)/1000) kva, pf_threephase, (COALESCE(md_kw,0)/1000) md_kw, date_md_kw, (COALESCE(md_kva,0)/1000) md_kva, date_md_kva, frequency, power_voltage from meter_data.amiinstantaneous where meter_number='"+meterNo+"' and read_time= (select MAX(read_time)  from meter_data.amiinstantaneous where meter_number='"+meterNo+"')";
			System.out.println(queryReading);
			Double kwh=0.0;
			List<Object[]> list2=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(queryReading).getResultList();
				if (list2.size() > 0) {
					Object[] object2 =list2.get(0);
					String kwhDate = (object2[0]==null)? "NA" :String.valueOf(object2[0]).trim();
					kwh = (object2[1]==null)? 0.0 : (Double.parseDouble(String.valueOf(object2[1]).trim()));
					Double kvah = (object2[2]==null)? 0.0 : (Double.parseDouble(String.valueOf(object2[2]).trim()));
					Double kva = (object2[3]==null)? 0.0 : (Double.parseDouble(String.valueOf(object2[3]).trim()));
					String pf=(object2[4]==null)?"NA" :  String.valueOf(object2[4]).trim();
					Double md_kw = (object2[5]==null)? 0.0 : (Double.parseDouble(String.valueOf(object2[5]).trim()));
					String date_md_kw=(object2[6]==null)?"NA" :  String.valueOf(object2[6]).trim();
					Double md_kva = (object2[7]==null)? 0.0 : (Double.parseDouble(String.valueOf(object2[7]).trim()));
					String date_md_kva=(object2[8]==null)?"NA" :  String.valueOf(object2[8]).trim();
					Double frequency=(object2[9]==null)? 0.0 : (Double.parseDouble(String.valueOf(object2[9]).trim()));
					Double voltage = (object2[10]==null)? 0.0 : (Double.parseDouble(String.valueOf(object2[10]).trim()));
					
					resonse.put("kwh", new DecimalFormat("##.#").format(Math.abs(kwh)));
					resonse.put("kwhDate", kwhDate);
					try {
						resonse.put("kvah", new DecimalFormat("##.#").format(Math.abs(kvah)));
					} catch (Exception e3) {
						e3.printStackTrace();
					}
					try {
						resonse.put("kva", new DecimalFormat("##.#").format(Math.abs(kva)));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					resonse.put("pf", pf);
					try {
						resonse.put("md_kw",new DecimalFormat("##.#").format(Math.abs(md_kw)));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					resonse.put("date_md_kw", date_md_kw);
					try {
						resonse.put("md_kva", new DecimalFormat("##.#").format(Math.abs(md_kva)));
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						resonse.put("voltage", new DecimalFormat("##.#").format(Math.abs(voltage)));
					} catch (Exception e3) {
						e3.printStackTrace();
					}
					resonse.put("date_md_kva", date_md_kva);
					try {
						resonse.put("frequency", new DecimalFormat("##.#").format(Math.abs(frequency)));
					} catch (Exception e3) {
						e3.printStackTrace();
					}
					
					System.out.println(resonse);
				}

				String queryEvent="select count(*) as totalEvents,sum(case when event_code='102' then 1 else 0 end)as totalPowerOff , meter_number from meter_data.events where meter_number='"+meterNo+"' GROUP BY meter_number";
				list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(queryEvent).getResultList();
				if (list.size() > 0) {
					object =list.get(0);
					String totalEvents = (object[0]==null)? "0" :String.valueOf(object[0]).trim();
					String totalEventsPowerOff = (object[1]==null)? "0" :String.valueOf(object[1]).trim();
					resonse.put("totalEvents", totalEvents);
					resonse.put("totalEventsPowerOff", totalEventsPowerOff);
					
				}

				String queryUsage="select meter_number,"
						+" sum(case when to_char(billing_date,'YYYY-MM-DD') = to_char((date_trunc('month', current_date)),'YYYY-MM-DD') then  (COALESCE(kwh,0)/1000) else 0 end) as thisMonthFirst,"
						+" sum(case when to_char(billing_date,'YYYY-MM-DD') = to_char((date_trunc('month', current_date) - interval '1 month'),'YYYY-MM-DD') then  (COALESCE(kwh,0)/1000) else 0 end) as lastMonthFirst"
						+" from meter_data.bill_history where meter_number='"+meterNo+"'  GROUP BY meter_number";
				list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(queryUsage).getResultList();
				if (list.size() > 0) {
					object =list.get(0);
					Double kwhThisMonthFirst = (object[1]==null)? 0.0 : (Double.parseDouble(String.valueOf(object[1]).trim()));
					Double kwhLastMonthFirst = (object[2]==null)? 0.0 : (Double.parseDouble(String.valueOf(object[2]).trim()));
					System.out.println(kwhThisMonthFirst +"          "+kwhLastMonthFirst);
					
					if(kwh>=kwhThisMonthFirst){
						Double thisMonthUnits=kwh-kwhThisMonthFirst;
						resonse.put("thisMonthUnits",  new DecimalFormat("##.#").format(Math.abs(thisMonthUnits)));
						if(kwhThisMonthFirst>=kwhLastMonthFirst){
							Double lastMonthUnits=kwhThisMonthFirst-kwhLastMonthFirst;
							resonse.put("lastMonthUnits",  new DecimalFormat("##.#").format(Math.abs(lastMonthUnits)));
							if(thisMonthUnits>0 && lastMonthUnits>0){
								Double diff=thisMonthUnits - lastMonthUnits;// (finding the amount of change)
								Double percentDiff =diff/ lastMonthUnits * 100;//(dividing by last month's measurement)
								resonse.put("unitsGrowth", new DecimalFormat("##.#").format(Math.abs(percentDiff)));
							}
						}
					}

				}
			} 

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return resonse.toString();
	}
	
	@RequestMapping(value = "/APSDLoginOfficersApp", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String APSDLoginOfficersApp(@RequestParam("userName") String userName, @RequestParam("password")String password)throws Exception// get active and inactive count
	{
		JSONObject response= new JSONObject();
		response.put("Response", "");
		response.put("locationCode", "");
		response.put("locationType", "");
		response.put("locationName", "");
	try {
		
		String queryCredentials="select username, userpassword,office_type,office from meter_data.users  where username = '"+userName+"' and userpassword = '"+password+"';   ";
		List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(queryCredentials).getResultList();
			
		if (list.size() == 0) {
			response.put("Response", "NoData");
			
		}else{
		
			Object[] object =list.get(0);
			String qry = null;
			String receivedUsername = String.valueOf(object[0]).trim();
			String receivedPassword = String.valueOf(object[1]).trim();
			String receivedofficeType = String.valueOf(object[2]).trim();
			String receivedoffice = String.valueOf(object[3]).trim();
			
			 if(receivedofficeType.equalsIgnoreCase("discom")) {
				 qry = "select DISTINCT discom from meter_data.amilocation where discom_code ='"+receivedoffice+"' ";
			 } else if(receivedofficeType.equalsIgnoreCase("zone")) {
		    	 qry = "select DISTINCT Zone from meter_data.amilocation where zone_code = '"+receivedoffice+"' ";
		     } else if(receivedofficeType.equalsIgnoreCase("circle")) {
		    	 qry = "select DISTINCT circle from  meter_data.amilocation where circle_code = '"+receivedoffice+"' ";
		     } else if (receivedofficeType.equalsIgnoreCase("division")) {
		    	 qry = "select DISTINCT division from  meter_data.amilocation where division_code = '"+receivedoffice+"' ";
		     } else if (receivedofficeType.equalsIgnoreCase("subdivision")) {
				 qry = "select DISTINCT subdivision from  meter_data.amilocation where sitecode ='"+receivedoffice+"' ";
			 }
			
			  String locationName=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult().toString();
			/*  Object[] locationObject =locationName.get(0);
			  String receivedLocationName = String.valueOf(locationObject[0]).trim();*/
			  
			if(receivedUsername.equalsIgnoreCase(userName) && receivedPassword.equalsIgnoreCase(password)){
				response.put("Response", "Authenticated");
				response.put("locationCode", receivedoffice);
				response.put("locationType", receivedofficeType);
				response.put("locationName", locationName);
			}else{
				response.put("Response", "InvalidCredentials");
			}
			
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return response.toString();
	}
	

		
	@RequestMapping(value = "/APSDConsumerConsumptionOfficerApp", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody List<Map<String, String>> APSDConsumerConsumptionOfficerApp(@RequestParam("locationName") String locationName, @RequestParam("LocationType")String LocationType,  @RequestParam("Billmonth") String Billmonth)throws Exception
	{
		List<Map<String, String>> finalresult=new ArrayList<>();
		
		Map<String, String> map=null;
		List<Object[]> resultList=androidService.getConsumerConumptionData(LocationType, locationName, Billmonth);
	
		for (Object[] objects : resultList) {
			map=new HashMap<>();
			map.put("date", objects[0]+"");
			map.put("consumption", objects[1]+"");
			finalresult.add(map);
		}
		
		return finalresult;
	}
	
	
	@RequestMapping(value = "/getDailyConsumption", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody List<Map<String, String>> getWSDailyConumptionData(@RequestParam("locationCode") String locationCode, @RequestParam("kno")String kno,
			@RequestParam("billmonth")String billmonth)throws Exception
	{
		List<Map<String, String>> finalresult=new ArrayList<>();
		
		Map<String, String> map=null;
		List<Object[]> resultList=androidService.getDailyConumptionDataServices(locationCode, kno, billmonth);
		if(resultList.size() == 0){
			map=new HashMap<>();
			map.put("response", "No records found for the given paramters");
			finalresult.add(map);
		}else{
		for (Object[] objects : resultList) {
	
			map=new HashMap<>();
			map.put("date", objects[0]+"");
			map.put("consumption", objects[1]+"");
			map.put("kno", objects[2]+"");
			map.put("meterNo", objects[3]+"");
			finalresult.add(map);
			}
		}
		
		return finalresult;
	}
	
	//@Transactional(propagation = Propagation.REQUIRED,value="txManagerpostgre")
	@RequestMapping(value = "/deactivateAccountMob" , method = {RequestMethod.POST })
	public @ResponseBody String AddAccount_new(ModelMap model,Model model1, HttpServletRequest request)
	{
		try
		{
			String UserName=request.getParameter("userName");
			String kno=request.getParameter("kno");
			
			String query="DELETE FROM meter_data.ncpt_registerrrno where consumerlogin='"+UserName+"' and knum='"+kno+"'";
			
			System.out.println(query + "Queryyyyyyyyyyyyyyyyyyyyyy");
            
            int inser_cnt= androidService.ncpt_rrno_insertion(query);

            if(inser_cnt > 0)
            {
            	return "Account Deactivated Successfully";
            }
            else
            {
            	return "Deactivation Failed";
            }

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return  "FAILED";
	}
	
	  @RequestMapping(value = "/checkAppVersionAMI/{version}/{platform}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
	    public @ResponseBody Object recoverPasswordAndroid_new (@PathVariable String version, @PathVariable String platform) {
	     
	        System.out.println("checkAppVersionMitra==========="+version+"==========="+platform);
	        List<Object[]> list = new ArrayList<>();

	        try {
	        	double appVersion=Double.parseDouble(version);
	        	platform=platform.toUpperCase();
	        	
	            String query="SELECT android_version, android_priority FROM meter_data.app_version ";//FOR ANDROID
	        	if(platform.contains("IOS")){
	        		query="SELECT ios_version, ios_priority FROM meter_data.app_version ";//FOR IOS
	        	}
	        	
	            System.out.println(query);
	            list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
	            double dbVersion=Double.parseDouble((list.get(0)[0]).toString());
	            int priority=Integer.parseInt((list.get(0)[1]).toString());
		        System.out.println("dbVersion===="+dbVersion+"=====priority======"+priority);
		        if((appVersion<dbVersion) && priority==1){
		        	return "FORCE_UPDATE";
		        }else if((appVersion<dbVersion) && priority==0){
		        	return "UPDATE";
		        }else{
		        	return "VALID";
		        }
		        
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "VALID";//TO AVOID EXIT OF THE APP IT SOULD BE FAILED
	    }
	  
	  @RequestMapping(value = "/sendMobileNotificationToAll/{title}/{message}", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody Object sendNotificationToTopic(@PathVariable String title,@PathVariable String message) throws Exception {
			String user="SPDCL";
			FireMessageCordova f = new FireMessageCordova(title, message, user, user);
			String topic="apspdcl.consumerapp";
			return f.sendToTopic(topic); //TO TOPIC
		}
		
		@RequestMapping(value = "/triggerAmiNotificationToMobile", method ={ RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String triggerAmiNotificationToMobile(@RequestParam String title,@RequestParam String message,@RequestParam String sdocode,@RequestParam String kno) throws Exception {


			String query="select fcm_token,customer_contact_no,customer_email_id from meter_data.ncpt_customers where fcm_token is not null and  customer_login_name in ("
					+" select consumerlogin from meter_data.ncpt_registerrrno where knum='"+kno+"')";


			List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();

			if (list.size() > 0) {
				String status="TOKEN_NOT_AVAILABLE";
				for (Object[] object : list) {

					String token = (object[0]==null)? null :String.valueOf(object[0]).trim();
//					String phone = (object[1]==null)? null :String.valueOf(object[1]).trim();//FOR FUTURE SMS SERVICE
//					String email = (object[2]==null)? null :String.valueOf(object[2]).trim();//FOR FUTURE EMAIL SERVICE

					if(token!=null){
						FireMessageCordova f = new FireMessageCordova(title, message, kno, kno);
						status = f.sendToToken(token);
					}
				}
				return status;
			}
			else {
				return "NO_REGISTERED_USERS";
			}
		}
		
		
		public  String triggerAmiNotificationToMobileWeb(String kno,String title,String message) throws Exception {
			//System.err.println(kno+"-"+title+"-"+message+"-"+sdocode);


			String query="select fcm_token,customer_contact_no,customer_email_id from meter_data.ncpt_customers where fcm_token is not null and  customer_login_name in ("
					+" select consumerlogin from meter_data.ncpt_registerrrno where knum='"+kno+"')";


			List<Object[]> list=androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
System.out.println(list);
			if (list.size() > 0) {
				String status="TOKEN_NOT_AVAILABLE";
				for (Object[] object : list) {

					String token = (object[0]==null)? null :String.valueOf(object[0]).trim();
//					String phone = (object[1]==null)? null :String.valueOf(object[1]).trim();//FOR FUTURE SMS SERVICE
//					String email = (object[2]==null)? null :String.valueOf(object[2]).trim();//FOR FUTURE EMAIL SERVICE
					System.out.println(token);

					if(token!=null){
						FireMessageCordova f = new FireMessageCordova(title, message, kno, kno);
						status = f.sendToToken(token);
						System.err.println(status);
					}
				}
				return status;
			}
			else {
				return "NO_REGISTERED_USERS";
			}
		}	
}
