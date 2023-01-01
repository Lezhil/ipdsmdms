package com.bcits.controller;

import java.text.ParseException;
import java.util.List;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.EventMaster;
import com.bcits.entity.ServiceOrder;
import com.bcits.entity.ServiceOrderConfig;
import com.bcits.service.ExceptionMangService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.ServiceOrderConfigService;
import com.bcits.service.ServiceOrderService;
import com.bcits.utility.SendMailForExceptions;


@Controller
public class ExceptionManagementController {
	
	@Autowired
	private ExceptionMangService exceptionMangService;
	
	@Autowired
	private ServiceOrderService serviceOrderService;
	
	@Autowired
	private MeterMasterService meterMasterService;
	@Autowired
	private ServiceOrderConfigService sos;
	
	@RequestMapping(value="/exceptionmanagement",method={RequestMethod.GET,RequestMethod.POST})
	public String meterChangeInfo(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{
		//System.out.println("===inside Exception Management===");
		
		//List result= exceptionMangService.findALL();
		List<ServiceOrder> result= serviceOrderService.findALL();
		model.put("ServiceOrder", result);
		return "ExceptionManagement";
	}
	
	@RequestMapping(value="/getdata",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<ServiceOrder> getdata(HttpServletRequest req,HttpServletResponse response) throws ParseException
	{
		String zone=req.getParameter("zone");
		String circle=req.getParameter("circle");
		String division=req.getParameter("division");
		String sdoname=req.getParameter("sdoname");
		String fdate=req.getParameter("fdate");
		String tdate=req.getParameter("tdate");
		System.err.println(zone+"-"+circle+"-"+division+"-"+sdoname+"-"+fdate+"-"+tdate);
		List<ServiceOrder> result= serviceOrderService.findalldata(zone,circle,division,sdoname,fdate,tdate,req);// business logic 
		System.out.println("the list size--->"+result.size());
		return result;
	}
	@RequestMapping(value="/sendMailForExceptions",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String sendMailForExceptions(HttpServletRequest req,HttpServletResponse response)
	{
		/*
		System.out.println("--come inside sendMailForExceptions--");
		System.out.println(req.getParameter("accno"));
		System.out.println(req.getParameter("meterno"));
		System.out.println(req.getParameter("zone"));
		System.out.println(req.getParameter("subdivision"));
		System.out.println(req.getParameter("exception"));*/
		
		String accno=req.getParameter("accno");
		String meterno=req.getParameter("meterno");
//		String zone=req.getParameter("zone");
		String subdivision=req.getParameter("subdivision");
		String tamper=req.getParameter("tamper");
		
		String status=req.getParameter("status");
		String name=req.getParameter("name");
		//String division=req.getParameter("division");
	//	String circle=req.getParameter("circle");
//		String billmonth=req.getParameter("billmonth");
		String id=req.getParameter("id");
		System.out.println("id-->"+id);
		int flagId=Integer.parseInt(id);
		//System.out.println("flagId--"+flagId);
		exceptionMangService.UpdateFlag(flagId);
		new Thread(new SendMailForExceptions("cto@bcits.co.in", accno, meterno, subdivision, tamper,status,name,id)).start();
		
		return null;
	}
	@RequestMapping(value="/getAddress",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List getMetrDetailsAstMngt(HttpServletRequest request,ModelMap model)
	{
		String meterno=request.getParameter("meterno");
		System.out.println("inside getMetrDetailsAstMngt--"+meterno);
		List result=meterMasterService.getAddress(meterno);
		return result;
	}
	@RequestMapping(value="/eventsConfigSet",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object eventConfigSet(HttpServletRequest req,@RequestParam("adate") String date,@RequestParam("event[]") String[] eventl) throws ParseException
	{
		/*String[] s=(String[]) req.getAttribute("event");
		String adate=(String) req.getAttribute("adate");*/
		DateFormat formatter;
	      formatter = new SimpleDateFormat("dd-MM-yyyy");
	      Date d = (Date) formatter.parse(date);
	      java.sql.Timestamp timeStampDate = new Timestamp(d.getTime());
		try{
		for(String s:eventl){
			ServiceOrderConfig soc=new ServiceOrderConfig(); 
			soc.setEvent_code(Integer.parseInt(s));
			soc.setSctime(timeStampDate);
			soc.setStatus("Active");
			sos.save(soc);
		}
			return "Succ";	
		}
		catch(Exception e){
			e.printStackTrace();
			return "Fail";
		}
		
	}
	
	@RequestMapping(value="/eventsConfigList",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object eventSetList(){
		String sql="select sc.event_code,(select em.event from meter_data.event_master em where em.event_code=sc.event_code ),sc.so_config_actice_or_deactive_time,sc.status from meter_data.so_config sc where sc.status='Active' ";
		return serviceOrderService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
	}
	@RequestMapping(value="/inactiveEventsList",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object inactiveEventsList(HttpServletRequest request,ModelMap model)
	{
		String sql="select event_code,event  from meter_data.event_master where event_code not in (select event_code from meter_data.so_config where status='Active' order by event )";
		return serviceOrderService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
	}

}
