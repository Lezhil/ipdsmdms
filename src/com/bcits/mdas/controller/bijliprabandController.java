package com.bcits.mdas.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.utility.DateOrTimestampConversion;
import com.bcits.service.GenericService;
import com.google.gson.Gson;
@Controller
public class bijliprabandController {
	@Autowired
	AmrLoadService als;
	public static final String bijpraUrl = "http://1.23.144.187:8081/bsmartjvvnl";
	
	@Transactional
	@RequestMapping(value="/dailyConsumptionCharges/{kno}/{date}",method={RequestMethod.GET,RequestMethod.POST})
	public  @ResponseBody String dailyConsumptionUnitsPriceZoneWise(@PathVariable("kno") String mtrno,@PathVariable("date") String date) throws ParseException{
		String url = bijpraUrl + "/amitpBillService";
		String nd=DateOrTimestampConversion.addDays(date,"yyyy-MM-dd",1);
		/*String ksql="select mtrno from meter_data.master_main where kno='"+kno+"'";
		String mtrno=(String) als.getCustomEntityManager("postgresMdas").createNativeQuery(ksql).getSingleResult();*/
		Map<String,String> m=new HashMap<>();
		String res=null;
		try {
			/*String s="select(select kno from meter_data.master_main where mtrno='"+mtrno+"') as kno,\n" +
					"(select(select cum_active_import_energy from meter_data.daily_load where mtrno='"+mtrno+"' and rtc_date_time='"+date+" 00:00:00')-\n" +
					"(select cum_active_import_energy from meter_data.daily_load where mtrno='"+mtrno+"' and rtc_date_time='2019-"+date.split("-")[1]+"-01 00:00:00'))/1000 as consumption,sum(case when (read_time>'"+date+" 02:00:00' and read_time<='"+date+" 06:00:00') then kwh else 0 end)/1000 as tz1,\n" +
					" sum(case  when (read_time>'"+date+" 06:00:00' and read_time<='"+date+" 10:00:00') then kwh else 0  end)/1000 as tz2,\n" +
					" sum(case  when (read_time>'"+date+" 10:00:00' and read_time<='"+date+" 14:00:00') then kwh  else 0 end)/1000 as tz3,\n" +
					" sum(case when (read_time>'"+date+" 14:00:00' and read_time<='"+date+" 18:00:00') then kwh  else 0 end)/1000 as tz4,\n" +
					" sum(case  when (read_time>'"+date+" 18:00:00' and read_time<='"+date+" 22:00:00') then kwh  else 0 end)/1000 as tz5,\n" +
					" sum(case when ((read_time>'"+date+" 22:00:00' and read_time<='"+nd+" 00:00:00') or (read_time>'"+date+" 00:00:00' and read_time<='"+date+" 02:00:00') ) then kwh  else 0  end)/1000 as tz6\n" +
					"from meter_data.load_survey where meter_number='"+mtrno+"' and to_char(read_time,'yyyy-MM-dd')='"+date+"' ";
			
			*/String s="select(select kno from meter_data.master_main where mtrno='"+mtrno+"') as kno,\n" +
					"(select(select cum_active_import_energy from meter_data.daily_load where mtrno='"+mtrno+"' and rtc_date_time='"+date+" 00:00:00')-\n" +
					"(select cum_active_import_energy from meter_data.daily_load where mtrno='"+mtrno+"' and rtc_date_time='2019-"+date.split("-")[1]+"-01 00:00:00'))/1000 as consumption,sum(case when (read_time>'"+date+" 00:00:00' and read_time<='"+date+" 04:00:00') then kwh else 0 end)/1000 as tz1,\n" +
					" sum(case  when (read_time>'"+date+" 04:00:00' and read_time<='"+date+" 08:00:00') then kwh else 0 end)/1000 as tz2,\n" +
					" sum(case  when (read_time>'"+date+" 08:00:00' and read_time<='"+date+" 12:00:00') then kwh else 0 end)/1000 as tz3,\n" +
					" sum(case when (read_time>'"+date+" 12:00:00' and read_time<='"+date+" 16:00:00') then kwh else 0 end)/1000 as tz4,\n" +
					" sum(case  when (read_time>'"+date+" 16:00:00' and read_time<='"+date+" 20:00:00') then kwh else 0 end)/1000 as tz5,\n" +
					" sum(case when (read_time>'"+date+" 20:00:00' and read_time<='"+nd+" 00:00:00')  then kwh else 0 end)/1000 as tz6\n" +
					"from meter_data.load_survey where meter_number='"+mtrno+"' and to_char(read_time,'yyyy-MM-dd')='"+date+"'";
			List<Object[]> l=als.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
			List<String> l1=als.getCustomEntityManager("postgresMdas").createNativeQuery(s).getResultList();
			Object v[]=l.get(0);
			//Map<String,String> m=new HashMap<>();
			 m.put("kno", v[0].toString());
			 m.put("consumption", v[1].toString());
			 m.put("tz1_consumption", v[2].toString());
			 m.put("tz2_consumption", v[3].toString());
			 m.put("tz3_consumption", v[4].toString());
			 m.put("tz4_consumption", v[5].toString());
			 m.put("tz5_consumption", v[6].toString());
			 m.put("tz6_consumption", v[7].toString());
			 
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			/*httpRequest.setHeader("Authorization", "Basic " + authStringenc);*/
            /*
            List<Double> anotherList = Arrays.asList(5.1d, 12d, 9d, 3d, 15d, 88d);
            List<Double> list=new ArrayList<>();
            list.addAll(anotherList);
            m.put("210463039616", list);*/
			Gson gson=new Gson();
			StringEntity body = new StringEntity(gson.toJson(m));
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			 res = new BasicResponseHandler().handleResponse(response);
			 
			//System.out.println("res: " + res);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return res;
		
	}
	

}
