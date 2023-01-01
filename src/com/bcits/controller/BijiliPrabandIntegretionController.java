package com.bcits.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bcits.entity.MeterMaster;
import com.bcits.service.BijiliPrabandService;
import com.bcits.service.MasterService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.RdngMonthService;


@Controller
public class BijiliPrabandIntegretionController {
	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@Autowired
	private BijiliPrabandService bijiliPrabandService;
	
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private RdngMonthService rdngMonthService;
	
	@RequestMapping(value="/addNewMeterFromPrabandh",method={RequestMethod.POST,RequestMethod.GET})
   	public @ResponseBody String getIntervalD4LoadData( HttpServletResponse response,HttpServletRequest request,ModelMap model){
		
		System.out.println("sateesh");
		String accno=request.getParameter("accno");
		String meterNo=request.getParameter("meterno");
		String kno=request.getParameter("kno");
		String billMonth=request.getParameter("billmonth");
		String ctrn=request.getParameter("ctrn");
		String ctrd=request.getParameter("ctrd");
		String intialReading=request.getParameter("ir");
		String typeOfMtr=request.getParameter("tom");
		String sdocode=request.getParameter("sdocode");
		
		System.out.println(accno+ "--" +meterNo+ "m--" +kno+ "--" +billMonth+ "--" +ctrn+ "--" +ctrd+ "--" +intialReading+ "--" +typeOfMtr+ "--" +sdocode );
		
		if(accno!=null && meterNo!=null && kno!=null && billMonth!=null && ctrn!=null && ctrd!=null && intialReading!=null && typeOfMtr!=null && sdocode!=null )
		{
			System.out.println("inside--if");
			return "success";
		}
		else{
			return "fail";
		}
		
    }
	
	@RequestMapping(value="/meterChangeFromPrabandh",method={RequestMethod.POST,RequestMethod.GET})
   	public @ResponseBody String meterChangeFromPrabandh( HttpServletResponse response,HttpServletRequest request,ModelMap model){
		
		System.out.println("METER CHANGED");
			return "success";
    }
	
	@RequestMapping(value="/getBillingData",method={RequestMethod.POST,RequestMethod.GET})
   	public @ResponseBody String getBillingData( HttpServletResponse response,HttpServletRequest request,ModelMap model){
		
		String meterNo=request.getParameter("meterno");
		String kno=request.getParameter("kno");
		String billMonth=request.getParameter("billmonth");
		JSONObject obj=new JSONObject();
		obj= meterMasterService.getBillDataByMeterNo(request);
		
		System.out.println("obj--"+obj.toString());
		return obj.toString();
    }
	
	@RequestMapping(value="/getBillingDataMonthwise",method={RequestMethod.POST,RequestMethod.GET})
   	public @ResponseBody String getBillingDataMonthwise( HttpServletResponse response,HttpServletRequest request,ModelMap model){
		
		
		JSONArray obj= bijiliPrabandService.getBillDataByMonthly(request);
		
		System.out.println("obj--"+obj.toString());
		return obj.toString();
    }

	@RequestMapping(value="/getBillingDataSyncDetails",method={RequestMethod.POST,RequestMethod.GET})
   	public  String getBillingDataSyncDetails( HttpServletResponse response,HttpServletRequest request,ModelMap model){
		
		System.out.println("inside getBillingDataSyncDetails");
		int billmonth=rdngMonthService.findAll();
		List<MeterMaster> result=meterMasterService.getMetrSyncData(billmonth);
		long count=masterService.FindTotalConsumerCount();
		//long count=2600;
		String resultSyncCount=getSyncDataFromRMS1();
		//String resultSyncCount="10";
		if(resultSyncCount!=null)
		{
			int rmsCount=Integer.parseInt(resultSyncCount);
			System.out.println("rmsCount-"+rmsCount+" --count-"+count);
			int totalMtrs=(int) (((double)count/(double)count)*100);
			int totalRMSCountPers= (int) (((double)rmsCount/(double)count)*100);
			int PendingRMSPers=(int) (((double)(count-rmsCount)/(double)count)*100);
			
			model.put("totalMtrs", totalMtrs);
			model.put("totalRMSCountPers", totalRMSCountPers);
			model.put("PendingRMSPers", PendingRMSPers);
		}
		
		model.put("resultSyncCount", resultSyncCount);
		model.put("total",count);
		model.put("month",billmonth);
		model.put("SycData", result);
		return "BillDashboard";
    }
	
	
	@RequestMapping(value="/getMonthDashboardDataBillData",method={RequestMethod.GET,RequestMethod.POST})
	public String getMonthDashboardData(HttpServletRequest request,ModelMap model,HttpSession session)
	{
		
		int billmonth = Integer.parseInt(request.getParameter("month"));
		System.out.println("month--"+billmonth);
		List<MeterMaster> result=meterMasterService.getMetrSyncData(billmonth);
		long count=masterService.FindTotalConsumerCount();
		String resultSyncCount=getSyncDataFromRMS1();
		System.out.println("---->"+resultSyncCount);
		if(resultSyncCount!=null)
		{
			int rmsCount=Integer.parseInt(resultSyncCount);
			System.out.println("rmsCount-"+rmsCount+" --count-"+count);
			double totalMtrs= (((double)count/(double)count)*100);
			double totalRMSCountPers=  (((double)rmsCount/(double)count)*100);
			double PendingRMSPers= (((double)(count-rmsCount)/(double)count)*100);
			
			model.put("totalMtrs", totalMtrs);
			model.put("totalRMSCountPers", totalRMSCountPers);
			model.put("PendingRMSPers", PendingRMSPers);
		}
		
		model.put("resultSyncCount", resultSyncCount);
		System.out.println(count);
		model.put("total",count);
		model.put("month",billmonth);
		model.put("SycData", result);
		return "BillDashboard";
		
	}
	
	@RequestMapping(value="/getSyncDataFromRMS",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getSyncDataFromRMS(HttpServletRequest request,ModelMap model,HttpSession session)
	{
		System.out.println(" ---calling getSyncDataFromRMS--- ");
		
		 String url = "http://1.23.144.187:8081/bsmartjvvnl/sendingMeteSynchDataToAMI";
			String serverResponse="";
			try 
			{
				RestTemplate template = new RestTemplate();
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
				serverResponse = template.getForObject(builder.build().encode().toUri(), String.class);
				System.out.println("--sererResponse--"+serverResponse);
				if(serverResponse==null || "null".equals(serverResponse) || "".equals(serverResponse)) {
					return "0";
				} else {
					return serverResponse;
				}
			}catch (Exception e) 
			{
				return "0";
			}
	}
	
	
	public String getSyncDataFromRMS1()
	{
		System.out.println(" ---calling getSyncDataFromRMS--- ");
		
		 String url = "http://1.23.144.187:8081/bsmartjvvnl/sendingMeteSynchDataToAMI";
			String serverResponse="";
			try 
			{
				RestTemplate template = new RestTemplate();
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
				serverResponse = template.getForObject(builder.build().encode().toUri(), String.class);
				System.out.println("--sererResponse--"+serverResponse);
				if(serverResponse==null || "null".equals(serverResponse) || "".equals(serverResponse)) {
					return "0";
				} else {
					return serverResponse;
				}
			}catch (Exception e) 
			{
				return "0";
			}
	}
	
}
