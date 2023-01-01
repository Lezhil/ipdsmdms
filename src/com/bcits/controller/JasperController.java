package com.bcits.controller;

import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JasperController {
	
//	@PersistenceContext
//	protected EntityManager postgresMdas;


	@RequestMapping(value="/jasper")
	public String reportPage() {
		return "ViewReports";
	}
	
	@RequestMapping(value="/reportResourcesPage", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String reportResourcesPage(HttpServletRequest request) {
		
		String folderuri = request.getParameter("folderuri").trim();
		String url="http://192.168.10.205:8083/ReportingEngine/JasperReport/resources?folderuri="+folderuri;
		String result="";
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(httpRequest);
			result = new BasicResponseHandler().handleResponse(response);	

		} catch (Exception e) {
			result=null;
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value="/searchReourcePage", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String searchReourcePage(HttpServletRequest request) {
		
		String folderuri = request.getParameter("folderuri").trim();
		String url="http://192.168.10.205:8083/ReportingEngine/JasperReport/resources?folderuri="+folderuri;
		String result="";
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(httpRequest);
			result = new BasicResponseHandler().handleResponse(response);	

		} catch (Exception e) {
			result=null;
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
//	
//	@RequestMapping(value="/getReportDetails")
//	public @ResponseBody List<?> getReportDetails(@RequestParam("level")String level_code) {
//		System.out.println("level======"+level_code);
//		try {
//			return postgresMdas.createNativeQuery("SELECT * from  meter_data.report3("+level_code+")").getResultList();
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	@RequestMapping(value="/getLocationHierarchy")
//	public @ResponseBody List<?> getLocationHierarchy() {
//		String query1="SELECT * FROM meter_data.location_levels ORDER BY level_code";
//		try {
//			return postgresMdas.createNativeQuery(query1).getResultList();			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	@RequestMapping(value="/getNextLocationOrLevel")
//	public @ResponseBody List<?> getNextLocationOrLevel(@RequestParam("level")String level,@RequestParam("code")String code) {
//		//System.out.println("level======="+level+"  ::::::: code====="+code);
//		String query1 ="SELECT lm.level_name,lm.location_name,lm.mapping_code as code FROM meter_data.location_master lm,meter_data.location_levels ll WHERE ";	
//		if(level.equalsIgnoreCase("1")){
//			query1+="lm.level_code="+level+" AND lm.level_code=ll.level_code ORDER BY lm.id";			
//		}
//		else{
//			query1+="lm.level_code="+level+" AND mapping_code like '"+code+"%' AND lm.level_code=ll.level_code ORDER BY lm.id";			
//		}
//		try{
//			return postgresMdas.createNativeQuery(query1).getResultList();			
//		}
//		catch (Exception e){
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
}
