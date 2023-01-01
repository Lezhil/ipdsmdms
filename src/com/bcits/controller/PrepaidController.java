package com.bcits.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.MeterMaster;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.PrepaidMaster;
import com.bcits.mdas.entity.PrepaidReadings;
import com.bcits.mdas.entity.PrepaidReadings.KeyReadings;
import com.bcits.mdas.ftp.Scheduler;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.PrepaidMasterService;
import com.bcits.mdas.service.PrepaidPaymentsService;
import com.bcits.mdas.service.PrepaidReadingsService;
import com.bcits.service.MeterMasterService;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
public class PrepaidController {

	@PersistenceContext(unitName="POSTGREDataSource")
	private EntityManager entityManager;
	
	@Autowired
	private MasterMainService masterMainService;
	
	@Autowired
	private PrepaidMasterService prepaidMasterService;
	
	@Autowired
	private PrepaidPaymentsService prepaidPaymentsService;
	
	@Autowired
	private PrepaidReadingsService prepaidReadingsService;
	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@RequestMapping(value="/getBillAmount")
	public @ResponseBody Object getMeterAlarmByDate(HttpServletRequest request){
		String tariffCode="2000XA" ;
		String lastCom="50";
		String todaysCom="26";
		String amt="600";
		String res=getRMSBillingAmount(tariffCode, lastCom, todaysCom,amt);
		return res;
		
		//return null;
		
	}


	public String getRMSBillingAmount(String tariffCode,String lastCom, String todaysCom,String balance) {
		
		/*tariffCode="2000XA" ;
		lastCom="50";
		todaysCom="26";*/
		
		try {
			//String url="http://192.168.4.169:8080/bsmartjvvnl/amiPrepaidBillService";
			String url="http://1.23.144.187:8081/bsmartjvvnl/amiPrepaidBillService";
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			// {"tariffCode":"2000XA","lastconsumption":"50","todayconsumption":"26"}

			JSONObject obj = new JSONObject();
			obj.put("tariffCode", tariffCode);
			obj.put("lastconsumption", lastCom);
			obj.put("todayconsumption", todaysCom);
			obj.put("balanceamount", balance);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Transactional
	@RequestMapping(value="/generatePrepaidReadingAll/{date}")
	public @ResponseBody Object generateReadingAll(@PathVariable String date,HttpServletRequest request){
		
		List<String> ary=generateReadingAllByDate(date);
		return ary;
		
		//return null;
		
	}
	
	
//	@Scheduled(cron="0 30 00 * * ?")
	public void generateReadingLedgerCron() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(Scheduler.yesterday());
		generateReadingAllByDate(date);
	}
	
	
	public List<String> generateReadingAllByDate(String date) {
		
		List<MasterMainEntity> masters=masterMainService.getDataByMeterType("prepaid");
		
		String yrmth=date.split("-")[0]+date.split("-")[1];
		
		List<String> ary=new ArrayList<>();
		
		
		for (Iterator iterator = masters.iterator(); iterator.hasNext();) {
			MasterMainEntity master = (MasterMainEntity) iterator.next();
			

			String qry="SELECT mtrno,bdate,round(kwh/1000,3) as kwh,round(p_kwh/1000,3) as p_kwh,\n" +
					"round(kwh_con/1000,3) as kwh_con,round(kvah/1000,3) as kvah, round(p_kvah/1000,3) as p_kvah,\n" +
					"round(kvah_con/1000,3) as kvah_con\n" +
					"FROM\n" +
					"(\n" +
					"SELECT A.*, (kwh-p_kwh) as kwh_con, (kvah-p_kvah) as kvah_con FROM\n" +
					"(\n" +
					"SELECT mtrno,date(rtc_date_time) as ldate,date(rtc_date_time  - interval '1 minute') as bdate, cum_active_import_energy as kwh,COALESCE(lead(cum_active_import_energy) OVER( ORDER BY mtrno, date(rtc_date_time) DESC),0) as p_kwh, \n" +
					"cum_apparent_import_energy as kvah, COALESCE(lead(cum_apparent_import_energy) OVER( ORDER BY mtrno, date(rtc_date_time) DESC),0) as p_kvah FROM meter_data.daily_load WHERE mtrno='"+master.getMtrno()+"'\n" +
					")A WHERE to_char(bdate,'YYYYMM')='"+yrmth+"'\n" +
					")B WHERE bdate<='"+date+"' ORDER BY bdate ";
			
			List<?> list=entityManager.createNativeQuery(qry).getResultList();
			
			double p_con=0;
			double t_con=0;
			
			for (int i = 0; i < list.size(); i++) {
				Object[] obj=(Object[]) list.get(i);
				p_con+=Double.valueOf(String.valueOf(obj[4]));
				t_con=Double.valueOf(String.valueOf(obj[4]));
			}
			p_con=p_con-t_con;
			
			
			
			
			try {
				double pbalance=0;
				double punitbal=0;
				double cunitbal=0;
				List<PrepaidReadings> readingList=prepaidReadingsService.getPreviousDaysReading(master.getMtrno(), date);
				PrepaidMaster prepaidMaster=prepaidMasterService.getDataByMtrno(master.getMtrno());
				
				double amt=0;
				double unit_rem=0;
				
				if (readingList.size()>0) {
					PrepaidReadings pr=readingList.get(0);
					pbalance=pr.getBalance();
					punitbal=(pr.getComsumption_remaining()==null?0:pr.getComsumption_remaining());
				} else {
					pbalance=(prepaidMaster.getBalance()==null?0:prepaidMaster.getBalance());
					punitbal=(prepaidMaster.getUnit_balance()==null?0:prepaidMaster.getUnit_balance());
				}
				
				String res=getRMSBillingAmount(master.getTariffcode(), p_con+"", t_con+"",pbalance+"");
				JSONObject jobj;
				
				try {
					jobj = new JSONObject(res);
					amt=jobj.optDouble("ECAMT");
					unit_rem=jobj.optDouble("UNITS");
					ary.add(res);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				double cbalance=pbalance-amt;
				PrepaidReadings readings=new PrepaidReadings();
				readings.setKeyReadings(new KeyReadings(master.getMtrno(), new SimpleDateFormat("yyyy-MM-dd").parse(date)));
				readings.setConsumption(t_con);
				readings.setAmount(amt);
				readings.setBalance(cbalance);
				
				readings.setComsumption_remaining(unit_rem);
				/*if(pbalance<=0) {
					readings.setComsumption_remaining(cunitbal);
				}*/
				
				prepaidMaster.setBalance(cbalance);
				prepaidMaster.setUnit_balance(unit_rem);
				
				prepaidReadingsService.customupdateBySchema(readings, "postgresMdas");
				prepaidMasterService.customupdateBySchema(prepaidMaster, "postgresMdas");
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return ary;
	}
	
	
	
	
	
	@RequestMapping(value="/prepaidMeters")
	public String prepaidMeters(HttpServletRequest request, ModelMap model) {
		
		String radioVal=request.getParameter("radioVal");
		System.out.println(radioVal);
		
		model.addAttribute("fromDate",request.getParameter("fromDate"));
		model.addAttribute("toDate",request.getParameter("toDate"));
		model.addAttribute("radioVal",request.getParameter("radioVal"));
		
		
		List <MasterMainEntity> masterList=null;
		List<MeterMaster> meterMasterList=null;
		PrepaidMaster prepaidMaster=null;
		String mtrno;
		
		System.out.println(request.getParameter("meterNum"));
		
		if("meterno".equals(radioVal)) {
			masterList=masterMainService.getFeederData(request.getParameter("meterNum"));
		} else {
			masterList=masterMainService.getMeterDataByKno(request.getParameter("meterNum"));
		}
		
		System.out.println("meterData--"+masterList.size());
		
		String mtrType="";
		if(masterList.size()>0) {
			MasterMainEntity m=masterList.get(0);
			mtrno=m.getMtrno();
			mtrType=m.getMeter_type();
			if("prepaid".equalsIgnoreCase(mtrType)) {
				model.addAttribute("mtrno",m.getMtrno());
				model.addAttribute("accno",m.getAccno());
				try {
					meterMasterList=meterMasterService.getMeterDataByMeterNo(mtrno);
					prepaidMaster=prepaidMasterService.getDataByMtrno(mtrno);
					
				} catch (Exception e) {
					
				}
				model.put("mtrFdrList", masterList);
				model.addAttribute("meterMaster",meterMasterList.get(0));
				model.addAttribute("phase",meterMasterList.get(0).getPhase());
				model.addAttribute("balance",prepaidMaster.getBalance());
				model.addAttribute("unitbalance",prepaidMaster.getUnit_balance());
			} else {
				model.addAttribute("msg","Entered Meter No/K No is not a Prepaid Meter.");
			}
			
		}
		
		
		return "prepaidMeters";
	}
	
	@RequestMapping(value="/getTodaysConsumption/{mtrNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getTodaysConsumption(@PathVariable String mtrNo,HttpServletRequest request,ModelMap model)
	{
		String tariff_code=request.getParameter("tariff_code");
		
		String qry="SELECT l.*,i.read_time, round(i.kwh/1000,3) as kwh, round(i.kvah/1000, 3) as kvah,(round(i.kwh/1000,3)-l.l_kwh) as c_kwh_con,(round(i.kvah/1000,3)-l.l_kvah) as c_kvah_con FROM \n" +
				"(\n" +
				"SELECT mtrno,round(\"max\"(kwh)/1000,3) as l_kwh,round(\"sum\"(kwh_con)/1000,3) as t_kwh_con,round(\"max\"(kvah)/1000,3) as l_kvah, round(\"sum\"(kvah_con)/1000,3) as t_kvah_con FROM\n" +
				"(\n" +
				"SELECT A.*, (kwh-p_kwh) as kwh_con, (kvah-p_kvah) as kvah_con FROM\n" +
				"(\n" +
				"SELECT mtrno,date(rtc_date_time) as ldate,date(rtc_date_time  - interval '1 minute') as bdate, cum_active_import_energy as kwh,lead(cum_active_import_energy) OVER( ORDER BY mtrno, date(rtc_date_time) DESC) as p_kwh, \n" +
				"cum_apparent_import_energy as kvah, lead(cum_apparent_import_energy) OVER( ORDER BY mtrno, date(rtc_date_time) DESC) as p_kvah FROM meter_data.daily_load WHERE mtrno='"+mtrNo+"'\n" +
				")A WHERE to_char(bdate,'YYYYMM')=to_char(CURRENT_DATE, 'YYYYMM')\n" +
				")B GROUP BY mtrno\n" +
				")l LEFT JOIN \n" +
				"(\n" +
				"SELECT meter_number,read_time,kwh,kvah FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' ORDER BY read_time desc LIMIT 1\n" +
				")i ON l.mtrno=i.meter_number;";
		
		List<?> list=entityManager.createNativeQuery(qry).getResultList();
		Map<String, String> map=new HashMap<>();
		String p_con="";
		String t_con="";
		if(list.size()>0) {
			Object[] obj=(Object[]) list.get(0);
			map.put("mtrno", String.valueOf(obj[0]));
			map.put("last_kwh", String.valueOf(obj[1]));
			map.put("t_kwh_con", String.valueOf(obj[2]));
			p_con=String.valueOf(obj[2]);
			map.put("last_kvah", String.valueOf(obj[3]));
			map.put("t_kvah_con", String.valueOf(obj[4]));
			map.put("read_time", String.valueOf(obj[5]));
			map.put("c_kwh", String.valueOf(obj[6]));
			map.put("c_kvah", String.valueOf(obj[7]));
			map.put("c_kwh_con", String.valueOf(obj[8]));
			t_con=String.valueOf(obj[8]);
			map.put("c_kvah_con", String.valueOf(obj[9]));
		}
		
		List<Map<String, String>> res=new ArrayList<>();
		res.add(map);
		try {
			double amt=0;
			double unit_rem=0;
			PrepaidMaster prepaidMaster=prepaidMasterService.getDataByMtrno(mtrNo);
			String result=getRMSBillingAmount(tariff_code, p_con, t_con, prepaidMaster.getBalance()+"");
			JSONObject jobj;
			jobj = new JSONObject(result);
			amt=jobj.optDouble("ECAMT");
			unit_rem=jobj.optDouble("UNITS");
			Map<String, String> map1=new HashMap<>();
			map1.put("amount",amt+"");
			
			double remBal=prepaidMaster.getBalance()-amt;
			map1.put("balance",remBal+"");
			map1.put("unitbalance",unit_rem+"");
			res.add(map1);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		return res;
	}
	
	@RequestMapping(value="/getConsumptionHistory/{mtrNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getConsumptionHistory(@PathVariable String mtrNo,HttpServletRequest request,ModelMap model)
	{
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		
		
		
		List<PrepaidReadings> list=prepaidReadingsService.getAllReadingsByMtrnoDate(mtrNo, fromDate, toDate);
		
		return list;
	}
	
}
