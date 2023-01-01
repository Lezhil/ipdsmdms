package com.bcits.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.service.ExceptionMangService;

public class SendMailForExceptions implements Runnable {
	
	@Autowired
	private ExceptionMangService exceptionMangService;


		private final String receiverAddress;
		private final String accno;
		private final String meterno;
//		private final String zone;
		private final String subdivision;
		private final String tamper;
		private final String status;
		private final String name;
		//private final String division;
		//private final String circle;
//		private final String billmonth;
		private final String id;
		
		
		public SendMailForExceptions(String receiverAddress, String accno, String meterno, String subdivision , String tamper,String status,String name,String id)
		{
			BCITSLogger.logger.info("Entering Send Mail..........");
			this.receiverAddress = receiverAddress;
			this.accno = accno;	
			this.meterno = meterno;
//			this.zone = zone;
			this.subdivision = subdivision;
			this.tamper = tamper;
			this.status=status;
			this.name=name;
		//	this.division=division;
		//	this.circle=circle;
//			this.billmonth=billmonth;
			this.id=id;
			BCITSLogger.logger.info("receiver Address is......"+receiverAddress);
		}

		@Override
		public void run()
		{
			//System.out.println("in mail settt "+req.getSession(true).getAttribute("serverPathValue"));
			Properties props = new Properties();
			 props.put("mail.smtp.host", "smtp.gmail.com");
		     props.put("mail.smtp.socketFactory.port", "465");
		     props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		     props.put("mail.smtp.auth", "true");
		     props.put("mail.smtp.port", "465");
			
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						@Override
						protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("shrinivasbk07@gmail.com", "btkpadma");
						}
			});

			try {

				
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("bcitsiworknotification@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(receiverAddress));
				
				message.setRecipient(Message.RecipientType.CC, new InternetAddress("shrinivas.bk@bcits.in"));
				message.setSubject("Service Order Status mail");
				message.setContent("<html>"
						+ "<style type=\"text/css\">"
						+ "table.hovertable {"
						+ "font-family: verdana,arial,sans-serif;"
						+ "font-size:11px;"
						+ "color:#333333;"
						+ "border-width: 1px;"
						+ "border-collapse: collapse;"
						+ "}"
						+ "table.hovertable th {"
						+ "background-color:#c3dde0;"
						+ "border-width: 1px;"
						+ "padding: 8px;"
						+ "border-style: solid;"
						+ "border-color: #394c2e;"
						+ "}"
						+ "table.hovertable tr {"
						+ "background-color:#88ab74;"
						+ "}"
						+ "table.hovertable td {"
						+ "border-width: 100%;"
						+ "padding: 8px;"
						+ "border-style: collapse;"
						+ "border-color: #394c2e;"
						+ "}"
						+ "</style><body>"
						+ "<h3  align=\"left\"  style=\"background-color:#1ac6ff;\">Service Order Status Report</h3> <br/><br /> Dear <b>"+name+",</b><br/> <br/> "
						+ "<table border=\"1\">"
						+ "<tr><td>NAME</td>"
						+ "<td>ACCNO </td>"
						+ "<td>METERNO </td>"
//						+ "<td>BILLMONTH </td>"
						
//						+ "<td>ZONE</td>"
//						+ "<td>DIVISION</td>"
						+ "<td>SUB-DIVISION</td>"
						+ "<td>TAMPER</td>"
						+ "<td>STATUS</td>"
						+ "</tr>"
						
						+ "<tr>"
						+ "<td>"+name+ "</td>"
						+ "<td>"+accno+ "</td>"
						+ "<td>"+meterno+ "</td>"
//						+ "<td>"+billmonth+ "</td>"
//						+ "<td>"+zone+ "</td>"
						
//						+"<td>"+division+"</td>"
						+"<td>"+subdivision+"</td>"
						+"<td><b>"+tamper+"</b></td>"
						+"<td>"+status+"</td>"
						
						+ "</tr></table>"
						
						/*+ " "+content+" task of  "+department+" is <b>  Not Completed </b> "
						+ " and is BaseCompletion Date is "
						+ status + " in the "+ProjectName+ "Project"
						+ ",IREOSTA."
						+ ""
						+ "" */
						+ "<br/><br/>"
						+ "</body></html>"
						+ "<br/><br/>"
						+ "<br/>Thanks,<br/>"
						+ "AMI Team <br/> <br/>",
				"text/html; charset=ISO-8859-1");
			
				Transport.send(message);
				System.out.println("mail sent................................");
				
			} catch (MessagingException e) {
				throw new RuntimeException((e));
			}
			
		}
	}


