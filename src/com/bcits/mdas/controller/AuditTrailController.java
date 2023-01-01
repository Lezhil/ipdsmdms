package com.bcits.mdas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import com.bcits.service.AuditTrailEntityService;


@Controller
public class AuditTrailController {

	@Autowired
	AuditTrailEntityService auditTrailEntityService;
	
	
	@RequestMapping(value = "/auditTrailsAMR", method = {RequestMethod.POST, RequestMethod.GET  })
	public	String getauditTrails(HttpServletRequest request,Model map) {
		
		
		return "audittrail";

	}
	
	@RequestMapping(value = "/getAuditTrailDataAMR", method = {RequestMethod.POST, RequestMethod.GET  })
	public	String getAuditTrailData(HttpServletRequest request,Model map)  {
		
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String qry = "select * from meter_data.audit_trails  WHERE  to_char(date_time, 'yyyy-MM-dd') BETWEEN '"+fromDate+"' AND '"+toDate+"'";
		List<?> list = null ;
		
		try {
			list =   auditTrailEntityService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			System.out.println(list.toString());
			System.out.println("This is the qry ---->"+qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.addAttribute("list" , list);
		map.addAttribute("fromDate",request.getParameter("fromDate"));
		map.addAttribute("toDate",request.getParameter("toDate"));
		return "auditTrailsAMR";
	}
	
	@RequestMapping(value="/getAuditDetails", method= {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getAuditDetails(HttpServletRequest request){
		String locationType=request.getParameter("locationType");
		String mtrNum=request.getParameter("mtrNum");
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		List<?> auditDetails=new ArrayList<>();
		auditDetails=auditTrailEntityService.getAuditDetails(mtrNum,fromDate,toDate);
		return auditDetails;
	}
	@RequestMapping(value="/getDataAquistion",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getDataAquDetails(HttpServletRequest request){
		List<?> list=new ArrayList<>();
		String mtrNum=request.getParameter("mtrNum");
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		list=auditTrailEntityService.getDataAquisition(mtrNum, fromDate, toDate);
		return list;
	}
	@RequestMapping(value="/getMtrCommData/{mtrNum}/{date}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMtrCommData(@PathVariable String mtrNum,@PathVariable String date){
		//List<?> list=new ArrayList<>();
		return auditTrailEntityService.getMtrData(mtrNum, date);
	}
	@RequestMapping(value="/getValidationAuditData/{locationType}/{mtrNum}/{fromDate}/{toDate}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getValidationData(@PathVariable String locationType,@PathVariable String mtrNum, @PathVariable String fromDate,@PathVariable String toDate){
		List<?> list=new ArrayList<>();
		
		list=auditTrailEntityService.getValidationTransactions(mtrNum, fromDate, toDate);
		return list;
	}
	@RequestMapping(value="/getSingleValidationData/{id}/{date}")
	public @ResponseBody Object getSingleValidationData(@PathVariable String id,@PathVariable String date){
		List<?> list=new ArrayList<>();
		list=auditTrailEntityService.getSingleValidationData(id, date);
		return list;
	}

	@RequestMapping(value="/getEstimationAuditData",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getEstimationData(HttpServletRequest request){
		List<?> list=new ArrayList<>();
		String locationType=request.getParameter("locationType");
		String mtrNum=request.getParameter("mtrNum");
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		list=auditTrailEntityService.getEstimationTransactions(mtrNum, fromDate, toDate,locationType);
		return list;
	}
	@RequestMapping(value="/getSingleEstimationData/{id}/{date}")
	public @ResponseBody Object getSingleEstimationData(@PathVariable String id,@PathVariable String date){
		List<?> list=new ArrayList<>();
		list=auditTrailEntityService.getSingleEstimationData(id, date);
		return list;
	}
	
}
