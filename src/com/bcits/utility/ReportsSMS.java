package com.bcits.utility;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.scheduling.annotation.Async;

public class ReportsSMS implements Runnable {
	
	private final String FROM = "mdas.bcits@gmail.com";
	private final String FROMNAME = "AMR Notification";
	static final String SMTP_USERNAME = "mdas.bcits@gmail.com";
	static final String SMTP_PASSWORD = "bcits@123";
	static final String HOST = "smtp.gmail.com";
	static final int PORT = 465;

	  String SUBJECT ="";
	  String BODY = "";
	  String TO="";
	  String CC="";
	public ReportsSMS(String to,String cc,String subject,String body) {
		this.BODY=body;
		this.SUBJECT=subject;
		this.TO=to;
		this.CC=cc;
	}
    

	@Override
	@Async
	public void run() {

		try {
			
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", HOST);
			props.put("mail.smtp.socketFactory.port", PORT);
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.starttls.enable", "false");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", PORT);


			// properties.
			Session session = Session.getDefaultInstance(props);
			MimeMessage msg = new MimeMessage(session);
			BodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(BODY,"text/html");
			msg.setFrom(new InternetAddress(FROM, FROMNAME));
		//	msg.setRecipient(Message.RecipientType.TO, new InternetAddress("chaitra.hk@bcits.in"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress("pm.uhbvn@bcits.in"));
			
			msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse("venkatesh@bcits.co.in"));
			msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse("amr.rohtakzone@bcits.in"));
			msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse("pmblr-01.tech@bcits.co.in"));
			msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse("ceo@bcits.co.in"));
			msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse("chaitra.hk@bcits.in"));
			
			msg.setSubject(SUBJECT);
			msg.setContent(BODY, "text/html");
			Transport transport = session.getTransport();
			   Multipart multipart = new MimeMultipart();

		         // Set text message part
		         multipart.addBodyPart(htmlPart);

		         // Part two is attachment
		         htmlPart = new MimeBodyPart();
		         String filename ="/backupfiles/apache-tomcat/bin/AMR_FILES/amrreport.xls";
		     //   String filename = "D:/AMR_FILES/amrreport.xls";
		         DataSource source = new FileDataSource(filename);
		         htmlPart.setDataHandler(new DataHandler(source));
		         htmlPart.setFileName(filename);
		         multipart.addBodyPart(htmlPart);

		         // Send the complete message parts
		         msg.setContent(multipart);
			// Send the message.
			System.out.println("Sending...");
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Email sent!");
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + e.getMessage());
		}

	}

}
