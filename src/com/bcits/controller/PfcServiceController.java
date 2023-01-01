package com.bcits.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.LegacyTrackerEntity;
import com.bcits.entity.PfcD2IntermediateEntity;
import com.bcits.service.IndFeederMasService;
import com.bcits.service.IndStructureMasService;
import com.bcits.service.IndssMassService;
import com.bcits.service.IndstrChangeService;
import com.bcits.service.IndstrDeleteService;
import com.bcits.service.LegacyTrackerService;
import com.bcits.service.NppDtReportIntermediateService;
import com.bcits.service.NppFeederReportService;
import com.bcits.service.PfcD2InterrmediateService;
import com.bcits.service.PfcD7IntermediateService;
import com.bcits.service.PfcFOCD3Service;
import com.bcits.service.PfcOnlineD3PmtCompService;


@Controller
public class PfcServiceController {
	public static final String pfcurl = "http://10.19.1.150:8080";

	@Autowired
	private PfcD2InterrmediateService pfcD2InterrmediateService;

	@Autowired
	private PfcD7IntermediateService pfcD7IntermediateService;

	@Autowired
	private NppFeederReportService nppFeederReportService;

	@Autowired
	private IndstrChangeService indstrChangeService;

	@Autowired
	private IndstrDeleteService indstrDeleteService;

	@Autowired
	private IndFeederMasService indFeederMasService;

	@Autowired
	private IndStructureMasService indStructureMasService;

	@Autowired
	private IndssMassService indssMassService;
	
	@Autowired
	private PfcOnlineD3PmtCompService pfcOnlineD3PmtCompService;
	
	@Autowired
	private LegacyTrackerService legacyTrackerService;
	
	@Autowired
	private NppDtReportIntermediateService nppDtReportIntermediateService;
	
	@Autowired
	private PfcFOCD3Service pfcFOCD3Service;

	//Testing
	//@Scheduled(cron="0 30 10 1 * ?") 
	/*
	 * @RequestMapping(value = "/IPDSRest/{monthyear}", method = {
	 * RequestMethod.POST, RequestMethod.GET }) public void getPfc(@PathVariable
	 * String monthyear)throws JSONException, ClientProtocolException, IOException {
	 * 
	 * //String[]
	 * s1={"D2report","D7report","ComwebMobile","NppfeederrptLT","NppfeederrptHT",
	 * "IndstrChange","IndstrDelete","Indfeedermas","Indstructuremas","Indssmas",
	 * "ComwebMobile"}; String[] s1={"NppfeederrptLT","NppfeederrptHT"};
	 * 
	 * for (int i = 0; i < s1.length; i++) { System.out.println(s1[i]); String
	 * result =getPfcReportData(s1[i],monthyear);
	 * 
	 * }
	 * 
	 * 
	 * }
	 */
	
		//legacy calling
		//@Scheduled(cron="0 30 10 1 * ?") 
		
		@RequestMapping(value = "/IPDSManual/{monthyear}", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody String  callpfcServices(@PathVariable String monthyear) throws ClientProtocolException, JSONException, IOException
		{
			/*SimpleDateFormat format = new SimpleDateFormat("MMyyyy");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			
			System.out.println(format.format(cal.getTime()));
			String monthyear=format.format(cal.getTime());*/

			System.out.println("monthyear-->"+monthyear);
			//String[] s1={"D2report","D7report","NppfeederrptLT","NppfeederrptHT","IndstrChange","IndstrDelete","Indfeedermas","Indstructuremas","Indssmas","ComwebMobile","Nppfeederrptltdt"};
			String[] s1={"FocD3Report"};
			
			for (int i = 0; i < s1.length; i++) {
				System.out.println(s1[i]);
				String result =getPfcReportData(s1[i],monthyear);
				
			}
			return monthyear;
			
			
		}
		
		
		//legacy calling
				//@Scheduled(cron="0 30 10 1 * ?") 
			
				@RequestMapping(value = "/IPDSManual", method = { RequestMethod.POST, RequestMethod.GET })
				public @ResponseBody String  manualCallpfcServices(  HttpServletRequest request) throws ClientProtocolException, JSONException, IOException
				{
					
					String billmonths = request.getParameter("billmonth");
					String reportType = request.getParameter("reportType");
					
					String year = billmonths.substring(0, 4);
					String month = billmonths.substring(4, 6);
					
					String billmonth = month+year;
					
					String msg = null;
					
					String monthyear = billmonth;
					
					System.out.println(reportType);
					
					/*SimpleDateFormat format = new SimpleDateFormat("MMyyyy");
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MONTH, -1);
					
					System.out.println(format.format(cal.getTime()));
					String monthyear=format.format(cal.getTime());*/

					System.out.println("monthyear-->"+monthyear);
					//String[] s1={"D2report","D7report","NppfeederrptLT","NppfeederrptHT","IndstrChange","IndstrDelete","Indfeedermas","Indstructuremas","Indssmas","ComwebMobile","Nppfeederrptltdt"};
					String[] s1={reportType};
					
					for (int i = 0; i < s1.length; i++) {
						System.out.println(s1[i]);
						String result =getPfcReportData(s1[i],monthyear);
						
					}
					return "success";
					
					
				}
	
	
	
		public String getPfcReportData(String serviceName,  String monthyear)throws JSONException, ClientProtocolException, IOException {
		System.out.println("called..");
		// String url = "http://10.19.1.150:8080/IPDSRest/D7report/";
		// String url = "http://10.19.1.150:8080/IPDSRest/D2report";
		System.out.println("serviceName--" + serviceName+ "monthyear:"+monthyear);
		LegacyTrackerEntity l1=new LegacyTrackerEntity();
		try {
		String url = pfcurl + "/IPDSRest/" + serviceName;
		System.out.println("complete url--" + url);

		HttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		JSONObject json = new JSONObject();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		json.put("username", "ipdsWs");
		json.put("pwd", "Ws@seipt");
		json.put("monyear", monthyear);
		params.add(new BasicNameValuePair("Qin", json.toString()));
		UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(ent);
		HttpResponse response = httpClient.execute(post);
		int code=response.getStatusLine().getStatusCode();
		
		if(code==200)
		{
			String data = new BasicResponseHandler().handleResponse(response);
			JSONArray recs = new JSONArray(data.toString());
			if(recs.length()>0) {
				if (serviceName.equalsIgnoreCase("D2report"))
				  {
				  pfcD2InterrmediateService.insertD2Data(data);
				  }
				  if (serviceName.equalsIgnoreCase("D7report"))
				  {
				  pfcD7IntermediateService.insertD7Data(data); 
				  } 
				  if(serviceName.equalsIgnoreCase("NppfeederrptLT"))
				  {
				  nppFeederReportService.insertNppFeederData(data,"LT"); 
				  } 
				  if(serviceName.equalsIgnoreCase("NppfeederrptHT"))
				  {
				  nppFeederReportService.insertNppFeederData(data,"HT"); 
				  } 
				  if(serviceName.equalsIgnoreCase("IndstrChange"))
				  {
				  indstrChangeService.insertIndstrChangeServiceData(data); 
				  } 
				  if(serviceName.equalsIgnoreCase("IndstrDelete")) 
				  {
				  indstrDeleteService.insertIndstrChangeServiceData(data);
				  } 
				  if(serviceName.equalsIgnoreCase("Indfeedermas"))
				  {
				  indFeederMasService.insertIndssFeederMasData(data); 
				  }
				  if(serviceName.equalsIgnoreCase("Indstructuremas"))
				  {
				  indStructureMasService.insertIndsStructureMas(data); 
				  } 
				  if(serviceName.equalsIgnoreCase("Indssmas"))
				  {
				  indssMassService.insertIndssMass(data); 
				  }
				  if(serviceName.equalsIgnoreCase("ComwebMobile"))
				  {
				  pfcOnlineD3PmtCompService.insertPfcOnline(data,serviceName); 
				  }
				  if(serviceName.equalsIgnoreCase("Nppfeederrptltdt"))
				  {
				  nppDtReportIntermediateService.insertNppDTData(data, "LT");
				  } 
				  if(serviceName.equalsIgnoreCase("FocD3Report"))
				  {
					  pfcFOCD3Service.insertFocD3Report(data,serviceName); 
				  }
		 
			}
			l1.setResponsesize(recs.length());
		}
		l1.setRemarks(response.getStatusLine().toString());
		l1.setMonthyear(monthyear);
		l1.setServicename(serviceName);
		l1.setReadtime(new Timestamp(System.currentTimeMillis()));
		legacyTrackerService.save(l1);
		System.out.println("I am inside Try");
		
		} catch (Exception e) {
			l1.setMonthyear(monthyear);
			l1.setReadtime(new Timestamp(System.currentTimeMillis()));
			l1.setServicename(serviceName);
			l1.setRemarks(e.getMessage());
			legacyTrackerService.save(l1);
			e.printStackTrace();
			System.out.println("I am inside Cache");
		}
		System.out.println("end of legacy service");
		return "success";

	}

		
		@RequestMapping(value = "/IPDSRest", method = { RequestMethod.POST })
		public @ResponseBody String getPfcReportData1(@RequestBody String body)
				throws JSONException, ClientProtocolException, IOException {

			 String data=body;
			 String serviceName="FocD3Report";
			System.out.println("data--->" + data);
			
			if (serviceName.equalsIgnoreCase("D2report"))
			  {
			  pfcD2InterrmediateService.insertD2Data(data);
			  }
			  if (serviceName.equalsIgnoreCase("D7report"))
			  {
			  pfcD7IntermediateService.insertD7Data(data); 
			  } 
			if(serviceName.equalsIgnoreCase("NppfeederrptLT"))
				
			  {
			  nppFeederReportService.insertNppFeederData(data,"LT"); 
			  } 
			  if(serviceName.equalsIgnoreCase("NppfeederrptHT"))
			  {
			  nppFeederReportService.insertNppFeederData(data,"HT"); 
			  } 
			  if(serviceName.equalsIgnoreCase("Nppfeederrptltdt"))
			  {
			  nppDtReportIntermediateService.insertNppDTData(data, "LT");
			  } 
			  if(serviceName.equalsIgnoreCase("FocD3Report"))
			  {
				  pfcFOCD3Service.insertFocD3Report(data,serviceName); 
			  } 

			return "success";
		}
		
	

}
