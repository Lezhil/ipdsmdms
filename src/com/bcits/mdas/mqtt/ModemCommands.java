package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.ModemTransactionEntity;
import com.bcits.mdas.service.ModemTransactionService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.mdas.utility.SendDocketInfoSMS;
import com.bcits.mdas.utility.SendSMSFromSI;
import com.bcits.mdas.utility.SmsDetailsSIBean;

@Controller
public class ModemCommands {
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager entityManager;
	@Autowired
	ModemTransactionService modemTransactionService;
	
	enum CommandType {
		SCAN, CHANGE_IP, CHANGE_PORT, FREQUENCY, RESTART, CHANGE_METER, ON_DEMAND
	};

	
	@RequestMapping(value="/scanModem/{modemNo}/{via_type}/{username}",method=RequestMethod.GET)
	public @ResponseBody String scanModem(@PathVariable String modemNo,@PathVariable String via_type,@PathVariable String username,ModelMap model, HttpServletRequest request)
	{
		String response="smsFail";
		System.out.println( "scan Modem==================="+modemNo+";3GC;SCAN_PARAM");
		
		
		ModemTransactionEntity entity= new ModemTransactionEntity();
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setIs_single_modem(1);
		entity.setCommand(""+modemNo+";3GC;SCAN_PARAM");
		entity.setLocation_breadcrumbs("n");
		entity.setMedia(via_type);
		entity.setPurpose("scanModem");
		entity.setModem_number(modemNo);
		entity.setUser_id(username);
		
		if(modemTransactionService.customupdateBySchema(entity, "postgresMdas") instanceof ModemTransactionEntity)
		{
		System.out.println("===============IP CHANGE COMMAND INSTERTED IN SCAN==================");	
		}
		else
		{
			System.out.println("===============IP CHANGE COMMAND NOT INSTERTED ==================");
		}
		
		
		
		String msg=modemNo+";3GC;SCAN_PARAM";
		
		if(via_type.equals("mqtt")){
			 response= Subscriber.sendMqttMessage("scan", msg);
		}
		else
		{
			response="smsSuccess";
			System.out.println("SMS mode");
			String phoneNo=modemTransactionService.getPhonenoByImei(modemNo);
			SMSAlertsToNigamConsumers(msg,phoneNo,"BCITS");//8762795468
		}
		 
		 
		return response;
	}
	
	@RequestMapping(value="/changeIP/{IP}/{modemNo}/{via_type}/{username}",method=RequestMethod.GET)
	
	public @ResponseBody String changeIP(@PathVariable String IP,@PathVariable String modemNo,@PathVariable String via_type,@PathVariable String username,ModelMap model, HttpServletRequest request)
	{
		HttpSession session=null;
		List<String> list=null;
		String x=via_type;
		String response="sms";
		String cmd="3gc SETT;04:"+IP+";";
		
		System.out.println( "IP Msg=================="+cmd);
		//System.out.println("username : "+user.getUserMailId());
		System.out.println("Get Username===>"+username);
		                   
		
		ModemTransactionEntity entity= new ModemTransactionEntity();
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setIs_single_modem(1);
		entity.setCommand(cmd);
		entity.setLocation_breadcrumbs("n");
		entity.setMedia(via_type);
		entity.setPurpose("ChangeIP");
		entity.setModem_number(modemNo);
		entity.setUser_id(username);
		
		if(modemTransactionService.update(entity) instanceof ModemTransactionEntity)
		{
		System.out.println("===============IP CHANGE COMMAND INSTERTED ==================");	
		}
		else
		{
			System.out.println("===============IP CHANGE COMMAND NOT INSTERTED ==================");
		}
		
		
		if(x.equals("mqtt"))
		{
			System.out.println("mqtt mode");
			System.out.println("IP topic================"+"ir/"+modemNo);
			System.out.println("IP message================"+"3gc SETT;04:"+IP);
			
		 response= Subscriber.sendMqttMessage("ir/"+modemNo, cmd);
		System.out.println("Response=====IN MQTT=========="+response);
		}
		else
		{
			response="smsSuccess";
			System.out.println("SMS mode");
			String phoneNo=modemTransactionService.getPhonenoByImei(modemNo);
			SMSAlertsToNigamConsumers(cmd,phoneNo,"BCITS");//8762795468
		}
		return response;
	}
	
	@RequestMapping(value="/changePort/{port}/{modemNo}/{via_type}/{username}",method=RequestMethod.GET)
	public @ResponseBody String changePort(@PathVariable String port,@PathVariable String modemNo,@PathVariable String via_type,@PathVariable String username,ModelMap model, HttpServletRequest request)
	{
		String response="SMSFail";
		String cmd="3gc SETT;05:"+port+";";
		/*System.out.println("inside port");
		System.out.println("modemNo========================"+modemNo);
		System.out.println( " port Topic=================="+"ir/"+modemNo);
		System.out.println( "port Msg=================="+"3gc SETT;05:"+port);
		System.out.println("port topic================"+"ir/"+modemNo);*/
		System.out.println("port message================"+cmd);
		//System.out.println("USER================"+username);
		
		ModemTransactionEntity entity= new ModemTransactionEntity();
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setIs_single_modem(1);
		entity.setCommand(cmd);
		entity.setLocation_breadcrumbs("n");
		entity.setMedia(via_type);
		entity.setPurpose("changePort");
		entity.setModem_number(modemNo);
		entity.setUser_id(username+"");
		
		if(modemTransactionService.update(entity) instanceof ModemTransactionEntity)
		{
		System.out.println("===============changePort COMMAND INSTERTED IN SCAN==================");	
		}
		else
		{
			System.out.println("===============changePort COMMAND NOT INSTERTED ==================");
		}
		
		
		
		
		
		
		
		
	   if(via_type.equals("mqtt"))
		{
		  response= Subscriber.sendMqttMessage("ir/"+modemNo, cmd);
		  System.out.println("port response================"+response);
		}
		else
		{
			System.out.println("SMS mode");
			String phoneNo=modemTransactionService.getPhonenoByImei(modemNo);
			SMSAlertsToNigamConsumers(cmd,phoneNo,"BCITS");
		}
		return response;
	}
	
	
	
	@RequestMapping(value="/restartModem/{modemNo}/{via_type}/{username}",method=RequestMethod.GET)
	public @ResponseBody String restartModem(@PathVariable String modemNo,@PathVariable String via_type,@PathVariable String username,ModelMap model, HttpServletRequest request)
	{
		String response="sms";

		System.out.println("inside restartModem");
		System.out.println( " port Topic=================="+"ir/"+modemNo);
		System.out.println( " via_type=================="+via_type);
		
		
		ModemTransactionEntity entity= new ModemTransactionEntity();
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setIs_single_modem(1);
		entity.setCommand("3gc RESTART;");
		entity.setLocation_breadcrumbs("n");
		entity.setMedia(via_type);
		entity.setPurpose("restartModem");
		entity.setModem_number(modemNo);
		entity.setUser_id(username+"");
		
		if(modemTransactionService.update(entity) instanceof ModemTransactionEntity)
		{
		System.out.println("===============restartModem COMMAND INSTERTED ==================");	
		}
		else
		{
			System.out.println("===============restartModem COMMAND NOT INSTERTED ==================");
		}
		
			
		
		if(via_type.equals("mqtt"))
		{
			System.out.println("mqtt mode");
			 response= Subscriber.sendMqttMessage("ir/"+modemNo, "3gc RSRT");
		}
		else{
			response="smsSuccess";
			System.out.println("SMS mode");
			String phoneNo=modemTransactionService.getPhonenoByImei(modemNo);
			SMSAlertsToNigamConsumers("3gc RESTART",phoneNo,"BCITS");//8762795468
		}
		
		return response;
	}
	
	
	@RequestMapping(value="/changeFrequency", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String changeFrequency(ModelMap model, HttpServletRequest request)
	{
		System.out.println("================777777777777788888888888");
		String type=request.getParameter("typeFrq");
		System.out.println("type==============="+type);
		String mIDSetFrq=request.getParameter("modemID");
		String interval=request.getParameter("frq");
		String frqMsg=request.getParameter("frqMsg");
		String frqSms=request.getParameter("frqSms");
	
		 HttpSession session=request.getSession(false);  
	        String username=(String)session.getAttribute("username");  
		System.out.println("username123==========="+username);
		System.out.println("mIDSetFrq==========="+mIDSetFrq);
		String response="smsFail";
		String msg=null;
		switch (type)
		{
		case "Instant Time":msg="3gc SETT;51:";
			break;
		case "Billing Time":msg="3gc SETT;52:";
			break;
		case "Load Time":msg="3gc SETT;53:";
			break;
		case "Event Time":msg="3gc SETT;54:";
			break;
			
		}
		
		switch (interval)
		{
		case "24hrs":
			msg+="96";
		break;
		case "1hrs":
			msg+="04";
		break;
		case "30 mins":
			msg+="02";
		break;
		case "15 mins":
			msg+="01";
			
		break;
		}
		
		System.out.println("frqMsg==============="+frqMsg);
		System.out.println("frqSms==============="+frqSms);
		System.out.println("Interval==============="+interval);
		System.out.println("type==============="+type);
		System.out.println("msg==============="+msg);
		
		
		msg+=";";
		
		ModemTransactionEntity entity= new ModemTransactionEntity();
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setIs_single_modem(1);
		entity.setCommand(msg+"");
		entity.setLocation_breadcrumbs("n");
		if(frqMsg==null)
		{
			entity.setMedia("SMS");
		}
		else
		{
			entity.setMedia("mqtt");	
		}
		
		entity.setPurpose("changeFrequency");
		entity.setModem_number(mIDSetFrq+"");
		entity.setUser_id(username+"");
		
		if(modemTransactionService.update(entity) instanceof ModemTransactionEntity)
		{
		System.out.println("===============changePort COMMAND INSTERTED IN SCAN==================");	
		}
		else
		{
			System.out.println("===============changePort COMMAND NOT INSTERTED ==================");
		}
		
	
		if(frqMsg==null)
		{
			response="smsSuccess";
			System.out.println("SMS mode");
			System.out.println("msg====================="+msg);
			String phoneNo=modemTransactionService.getPhonenoByImei(mIDSetFrq);
			SMSAlertsToNigamConsumers(msg,"9071764934","BCITS");
		}
		else
		{
			 response= Subscriber.sendMqttMessage("ir/"+mIDSetFrq, msg);	
		}
		
	
		 
		return response;
	}
	
	
	
	
	@RequestMapping(value="/changeMeter/{modemNo}/{via_type}/{username}",method=RequestMethod.GET)
	public @ResponseBody String changeMeter(@PathVariable String modemNo,@PathVariable String via_type,@PathVariable String username,ModelMap model, HttpServletRequest request)
	{
		String response="smsFail";
		System.out.println("inside changeMeter");
		System.out.println( " changeMeter command=================="+"ir/"+modemNo);
		System.out.println( " via_type=================="+via_type);
		System.out.println( " username=================="+username);
		
		
		
		ModemTransactionEntity entity= new ModemTransactionEntity();
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setIs_single_modem(1);
		entity.setCommand("3gc CHMT;");
		entity.setLocation_breadcrumbs("n");
		entity.setMedia(via_type);
		entity.setPurpose("changeMeter");
		entity.setModem_number(modemNo);
		entity.setUser_id(username+"");
		
		if(modemTransactionService.update(entity) instanceof ModemTransactionEntity)
		{
		System.out.println("===============restartModem COMMAND INSTERTED ==================");	
		}
		else
		{
			System.out.println("===============restartModem COMMAND NOT INSTERTED ==================");
		}
		
		
		if(via_type.equals("mqtt"))
		{
			response= Subscriber.sendMqttMessage("ir/"+modemNo, "3gc CHNGMETR");	
		}
		else
		{
			response="smsSuccess";
			System.out.println("SMS mode");
			String phoneNo=modemTransactionService.getPhonenoByImei(modemNo);
			SMSAlertsToNigamConsumers("3gc CHNGMETR",phoneNo,"BCITS");//8762795468	
		}
		
	 
		 
		return response;
	}
	
	
	
	
	
	
	@RequestMapping(value="/OnDemandPull", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String OnDemandPull(ModelMap model, HttpServletRequest request) throws ParseException
	{
		System.out.println("================777777777777788888888888");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd MMMM yyyy - HH:mm");
		SimpleDateFormat sdf1=new SimpleDateFormat("YY:MM:dd:HH:mm");
		String onDemTyp=request.getParameter("onDemTyp");
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		String reqtype=request.getParameter("reqtype");
		Date from=sdf.parse(fromDate);
		Date to=sdf.parse(toDate);
		String fDate=sdf1.format(from)+":00:";
		String tDate=sdf1.format(to)+":00:";
		String modemID=request.getParameter("mIDOndem");
		/*String ODMsg=request.getParameter("ODMsg");
		String ODSms=request.getParameter("ODSms");*/
		String txnId=this.numbGen()+"";
		
		System.out.println("onDemTyp==============="+onDemTyp);
		
		System.out.println("fromDate==============="+fDate);
		
		
		System.out.println("toDate==============="+tDate);
		
		System.out.println("modemNoID==============="+modemID);
		System.out.println("reqtype==============="+reqtype);
		/*System.out.println("ODSms==============="+ODSms);*/
	
		String response="smsFail";
		String msg="3GC;PULL;";
		switch (onDemTyp)
		{
		case "Instant":msg="3GC;PULL;"+"INST;"+txnId+";"+fDate+";"+tDate+";";
		
			break;
		case "Billing":msg="3GC;PULL;"+"BILL;"+txnId+";"+fDate+";"+tDate+";";
			break;
		case "Load":msg="3GC;PULL;"+"LOAD;"+txnId+";"+fDate+";"+tDate+";";
			break;
		case "Event":msg="3GC;PULL;"+"EVNT;"+txnId+";"+fDate+";"+tDate+";";
			break;
			
		}
		
		
		HttpSession session=request.getSession(false);  
        String username=(String)session.getAttribute("username");  
		
		ModemTransactionEntity entity= new ModemTransactionEntity();
		entity.setTimeStamp(new Timestamp(new Date().getTime()));
		entity.setIs_single_modem(1);
		entity.setCommand(msg+"");
		entity.setLocation_breadcrumbs("n");
		if("sms".equalsIgnoreCase(reqtype))
		{
			entity.setMedia("SMS");
		}
		else
		{
			entity.setMedia("mqtt");	
		}
		
		entity.setPurpose("OnDemandPull");
		entity.setModem_number(modemID+"");
		entity.setUser_id(username+"");
		
		
		String phoneNo=modemTransactionService.getPhonenoByImei(modemID);
		
		String cmd1="3gc SETT;01:REC;";
		/*String cmd2="3gc SETT;04:10.247.163.236;";
		String cmd3="3gc SETT;05:1883;";*/
		
		if("sms".equalsIgnoreCase(reqtype))
		{
			response="smsSuccess";
			System.out.println("SMS mode");
			System.out.println("msg====================="+msg);
			entity.setPhone_no(phoneNo);
			SMSAlertsToNigamConsumers(msg,phoneNo,"BCITS");
			//SMSAlertsToNigamConsumers(msg,"9844377977","BCITS");
			
		}
		else
		{
		 response= Subscriber.sendMqttMessage("ir/"+modemID, msg);	
		}
		
		System.out.println("msg==============="+msg);
		System.out.println("response==============="+response);
		
		
		if(modemTransactionService.update(entity) instanceof ModemTransactionEntity)
		{
		System.out.println("===============onDemand COMMAND INSTERTED ==================");	
		}
		else
		{
			System.out.println("===============onDemand COMMAND NOT INSTERTED ==================");
		}
		
		return response;
	}
	
	
	public void SMSAlertsToNigamConsumers(String mailMessage,String mobNo,String  userName){
		
		System.out.println("======= SMSAlertsToNigamConsumers ========"+mailMessage);
		BCITSLogger.logger.info("******* SMSAlertsToNigamConsumers *******");
		com.bcits.utility.SmsCredentialsDetailsBean smsCredentialsDetailsBean = new com.bcits.utility.SmsCredentialsDetailsBean();
		smsCredentialsDetailsBean.setNumber(mobNo); 
		smsCredentialsDetailsBean.setUserName(userName);
		/*String mailMessage="Dear Customer,JVVNL announces award u
		 * p to Rs. 5000* on online electricity bill payments. "
				+ " Avail your chance to win lottery by paying your energy bills online. *T&C apply."
				+ "   "
				+"      Warm Regards, "
				+" JVVNL-Administration services";*/
	//	String mailMessage="711151 : 3gc SETT; 04:"+chIp+"";
		//String mailMessage="3gc SETT; 04:"+chIp+"";
		smsCredentialsDetailsBean.setMessage(mailMessage); 
		new Thread(new SendDocketInfoSMS(smsCredentialsDetailsBean)).start(); 
	}
	
	public void SMSCommandsToModem(String mailMessage,String mobNo){
		
		BCITSLogger.logger.info("******* SMSAlertsToNigamConsumers *******");
		SmsDetailsSIBean smsDetailsSIBean = new SmsDetailsSIBean();
		smsDetailsSIBean.setTo(mobNo); 
		smsDetailsSIBean.setMessage(mailMessage); 
		new Thread(new SendSMSFromSI(smsDetailsSIBean)).start(); 
	}
	
	public long numbGen() {
	    while (true) {
	        long numb = (long)(Math.random() * 100000000 * 1000000); // had to use this as int's are to small for a 13 digit number.
	        if (String.valueOf(numb).length() == 13)
	            return numb;
	    }
	}
}
