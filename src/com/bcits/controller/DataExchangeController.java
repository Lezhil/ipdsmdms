package com.bcits.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RespectBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bcits.mdas.controller.AndroidConsumerController;
import com.bcits.entity.ServiceExceptionEntity;
import com.bcits.entity.ServiceExcpNotificationSetting;
import com.bcits.entity.ServiceStatusEntity;
import com.bcits.mdas.entity.SubstationDetailsEntity;
import com.bcits.service.DtDetailsService;
import com.bcits.service.ServiceExceptionService;
import com.bcits.service.ServiceStatusService;
import com.bcits.service.ServiceexceptionsService;
import com.crystaldecisions.reports.queryengine.Session;
import com.bcits.mdas.utility.StaticProperties;


@Controller
public class DataExchangeController {
	
	@Autowired
	private ServiceStatusService servicestatuservice;
	
	@Autowired
	private  ServiceexceptionsService serviceTest;

	@Autowired
	private ServiceExceptionService ServiceExceptionservice;
	
	@Autowired
	static
	AndroidConsumerController androidConsumerController; 
	
	@RequestMapping(value="/serviceStatus", method = {RequestMethod.POST,RequestMethod.GET })
		public String ServiceStatus(@ModelAttribute("servicestatusId") ServiceStatusEntity  serviceStatusEntity,ModelMap model,HttpServletRequest request){
		List<?> Servicestatus=servicestatuservice.getServiceStatus();
		List<?> serviceName = null ;
		try {
		serviceName = ServiceExceptionservice.getServiceName();
		} catch (Exception e) {
		e.printStackTrace();
		}
		model.addAttribute("ServiceName" , serviceName);
		model.addAttribute("Servicestatus", Servicestatus);
		return "ServiceStatus";
		
	}

	
	


	
	public void servicestatusupdate(String Servicename,String status,Timestamp lastmodfydate,String servicetype){
		Timestamp currenttime = new Timestamp(System.currentTimeMillis());
		ServiceStatusEntity ss=new ServiceStatusEntity();
		 ss.setService_name(Servicename);
		 ss.setService_status(status);
		 ss.setLast_ser_sta_chan_date(lastmodfydate);
		 ss.setEntry_date(currenttime);
		 ss.setService_type(servicetype);
		 servicestatuservice.save(ss);
		 
	}


	/*@RequestMapping(value="/serviceform", method = {RequestMethod.POST,RequestMethod.GET })
	public String serviceform(@ModelAttribute("servicestatusId") ServiceStatusEntity  serviceStatusEntity,ModelMap model,HttpServletRequest request){
      try {
    	  ServiceStatusEntity se=new ServiceStatusEntity();
          se.setService_name(serviceStatusEntity.getService_name());
          System.out.println("servicename:"+serviceStatusEntity.getService_name());
          se.setService_status(serviceStatusEntity.getService_status());
          System.out.println("servicestatus:"+serviceStatusEntity.getService_status());
          se.setLast_ser_sta_chan_date(serviceStatusEntity.getLast_ser_sta_chan_date());
          se.setEntry_by(serviceStatusEntity.getEntry_by());
          se.setEntry_date(serviceStatusEntity.getEntry_date());
          servicestatuservice.save(se);
	} catch (Exception e) {
		e.printStackTrace();
		//result=e.getMessage();
	}
		
	return "ServiceStatus";
	
}*/
	
	
	
	@RequestMapping(value="/addservice",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody  String addservice( ModelMap model,HttpServletRequest request,HttpSession session)   	
	{ 
		
		String servicename=request.getParameter("servicename");
		String servicestatus=request.getParameter("servicestatus");
		String serviceType=request.getParameter("servicetype");
		String username=(String) session.getAttribute("username");
		ServiceStatusEntity sse=new ServiceStatusEntity();
		sse.setService_name(servicename);
		sse.setService_status(servicestatus);
		sse.setService_type(serviceType);
		sse.setEntry_by(username);
		sse.setEntry_date(new Timestamp(new Date().getTime()));
		try {
			servicestatuservice.save(sse);	
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			 return "Fail";
		}
	}
	
	
	@RequestMapping(value="/editServiceStatus",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody  List<?> editServiceStatus( ModelMap model,HttpServletRequest request,HttpSession session) {
		String id=request.getParameter("id");
		List<?> changedList=servicestatuservice.getEditServiceStatus(Integer.parseInt(id));
		return changedList;
		
	}
	
	
	@RequestMapping(value="/modifyServiceStatus",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody  Object modifyServiceStatus(ModelMap model,HttpServletRequest request,HttpSession session){
		String id=request.getParameter("id");
		String sername=request.getParameter("servname");
		String sersta=request.getParameter("servsta");
		String sertyp=request.getParameter("sertype");
		servicestatuservice.getModifyServiceStatus(id, sername, sersta, sertyp);
		return "ServiceStatus";
	}

	//service exception
	@RequestMapping(value = "/serviceException", method = { RequestMethod.POST,RequestMethod.GET })
	public String serviceException(ModelMap model, HttpServletRequest requst,HttpSession session) {
		List<?> getExceptionreports=null;
		try {
			getExceptionreports = ServiceExceptionservice.getServiceName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.put("service", getExceptionreports);
		return "serviceException";
	}
	
	
	@RequestMapping(value = "/existingexception", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> existingexception(HttpServletRequest request, Model model) {
		    
		List<?> existingexception=ServiceExceptionservice.getexistingexception();
		
		return existingexception;
	}
	
	@RequestMapping(value = "/getAckData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getAckData(HttpServletRequest request, Model model) {
		String fdate=request.getParameter("DailyFDId");
		String tdate=request.getParameter("DailyTDId");
		String serviceId=request.getParameter("serviceId");
		List<?> Ackdata=ServiceExceptionservice.getAckData(fdate,tdate,serviceId);
		return Ackdata;
		
	}
	
	/*@RequestMapping(value = "/onAcknowledge/{id}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> onAcknowledge(@PathVariable String id, ModelMap model,HttpServletRequest request) {
		
		List<?> ackMsg=ServiceExceptionservice.tosaveack(Integer.parseInt(id));
		//System.out.println("ackMsg--------------"+ackMsg);
	return ackMsg;
	}*/
	
	@RequestMapping(value = "/tosaveAck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object TosaveAck(HttpServletRequest request, Model model,HttpSession session) {
		String AckMsg = request.getParameter("Acknowledged");
		String ackId = request.getParameter("ackId");
		String username=(String) session.getAttribute("username");
		int ackMsg=ServiceExceptionservice.tosaveack(AckMsg,Integer.parseInt(ackId),username);
		
		/*if(ackMsg>=0){
			return "exist";
		}
		else{
			return "not exist";
		}
		 */
		if(ackMsg == 1){
			 
			 String qry="update meter_data.service_exception_log set ack_status='true',notified='true' WHERE id='"+ackId+"';";
	    	  
	    	  List list = null;
	    	 try {
				
	    			int i =  ServiceExceptionservice.setackstatus(qry);		
	            
				} catch (Exception e) {
					e.printStackTrace();
				}
			 return "exist";
			 
		 }
		 else {
			 return "not exist";
		 }
		
	
	}
	public  void Servicelog(String ServiceName,String Requester,String Provider,String Exception,Timestamp exception_time) {
		
		Timestamp currenttime = new Timestamp(System.currentTimeMillis());
		ServiceExceptionEntity se=new ServiceExceptionEntity();
		se.setService_name(ServiceName);
		se.setRequester(Requester);
		se.setProvider(Provider);
		se.setException(Exception);
		se.setTime_stamp(currenttime);
	    se.setExceptiontime(exception_time);
		se.setAck_status(true);
		List<?> mobileno=serviceTest.getmobileno();
		//System.out.println(mobileno);
		String message = "Exception "+ServiceName+" has been occured ";
		
		
		String targetURL = StaticProperties.domainNameBijliPrabandh + "sendSMSGeneric/" + message + "/" + mobileno;
		String sms=androidConsumerController.sendSmsNew(targetURL);
		try {
			if(sms!=null && sms!=""){
				 
				se.setNotified(true);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			serviceTest.save(se);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}

	/*@RequestMapping(value= "/checkServiceLog", method = { RequestMethod.GET, RequestMethod.POST } )
	public  String checkServiceLog (HttpServletRequest request, Model model) {
{
	
	Timestamp exceptimestamp = new Timestamp(System.currentTimeMillis());
	System.out.println(exceptimestamp);
		int num1=30, num2=0;
		try {
			  int output=num1/num2;
				
				
		} catch (Exception e) {
			Servicelog("MetereplacementServices","MDMS","RMS","nullpointerexception",exceptimestamp);
		}
	    return "Success";
	}
	}
	*/
	
	
	@RequestMapping(value= "/serviceExceptionNotifSetting", method = { RequestMethod.GET, RequestMethod.POST } )
	public  String addNewNotificationSetting (	HttpServletRequest request, Model model) {
		
		List<?> serviceName = null ;
		List<?> serviceNotification = null ;
		try {
		serviceName = ServiceExceptionservice.getServiceName();
		serviceNotification = ServiceExceptionservice.getAllServiceExceNotifData();
		} catch (Exception e) {
		e.printStackTrace();
		}
		model.addAttribute("ServiceName" , serviceName);
		model.addAttribute("serviceNotification" , serviceNotification);
		return "serviceExceptionNotifSetting";
	}
	
	@RequestMapping(value = "/saveServiceExceptionNotifSetting", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveServiceExceptionNotifSetting(HttpServletRequest request, Model model,HttpSession session) {
		
		String serviceName = request.getParameter("serviceName");
		String emailID = request.getParameter("emailId");
		String mobileNo = request.getParameter("mobileId");
		String userName = (String) session.getAttribute("username");
		
	ServiceExcpNotificationSetting serviceExcepObject = new ServiceExcpNotificationSetting();
	
	serviceExcepObject.setServiceName(serviceName);
	serviceExcepObject.setEmails(emailID);
	serviceExcepObject.setSmss(mobileNo);
	serviceExcepObject.setEntryDate(new Timestamp(new Date().getTime()));	
	serviceExcepObject.setEntryby(userName);
	
	try {
		ServiceExceptionservice.save(serviceExcepObject);	
		return "success";
	} catch (Exception e) {
		e.printStackTrace();
		 return "Fail";
	}
		
	}
	@RequestMapping(value="/editServiceException",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody  List<?> editServiceException( ModelMap model,HttpServletRequest request)   	
	{  
		String id = request.getParameter("id");
		List<?> changedlist=ServiceExceptionservice.getEditDetils(Integer.parseInt(id));
		return changedlist;
	}
	
	@RequestMapping(value="/modifyServiceException",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody  String  modifyServiceException( ModelMap model,HttpServletRequest request)   	
	{  
		
		String id = request.getParameter("id");
		String serviceName = request.getParameter("serviceName");
		String emailId = request.getParameter("emailId");
		String mobileNo = request.getParameter("mobileNo");
		String update = null;
		
		int  changedlist=ServiceExceptionservice.UpdateEditDetils(mobileNo , emailId,  id, serviceName);
		if(changedlist == 0){
			return update= "Success";
		}else {
			return update= "failed";	
		}
		
	}
	
	/*@RequestMapping(value="/checkSaveMethod",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkSaveMethod(){
		  
		Timestamp exceptimestamp = new Timestamp(System.currentTimeMillis());
		  ServiceExceptionEntity se = new ServiceExceptionEntity();
		  se.setService_name("Test");
		  se.setRequester("ReqTest");
		  se.setProvider("ProvTest");
		  se.setException("ExcepTest");
		  se.setNotified(true);
		  se.setAck_status(true);
		  se.setTime_stamp(exceptimestamp);
		  
		  try {
			  serviceTest.save(se);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("This is exception Method ########ssss" +e.getMessage());
		}
		  
		  
		  return "Success";
	  }*/
}
