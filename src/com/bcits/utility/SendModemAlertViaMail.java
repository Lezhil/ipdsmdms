package com.bcits.utility;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;

public class SendModemAlertViaMail implements Runnable {
	
	private final String FROM = "noreply@bijlimitra.com";

	private final String FROMNAME = "bijliprabhandh";

	static final String SMTP_USERNAME = "AKIAJIEYQXDQMY6QHSIA";

    static final String SMTP_PASSWORD = "AkWyEUbO+OC754k8fo97CRCsupo0zGxiROGrQ3s08aJY";
    
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";
    
    static final int PORT = 587;
	  String SUBJECT ="";
	  String BODY = "";
	  String TO="";
	  String CC="";
	public SendModemAlertViaMail(String to,String cc,String subject,String body) {
		
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
			props.put("mail.smtp.port", PORT);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");

			// properties.
			Session session = Session.getDefaultInstance(props);
			MimeMessage msg = new MimeMessage(session);
			BodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(BODY,"text/html");
			msg.setFrom(new InternetAddress(FROM, FROMNAME));
			//msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			
			msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse(CC));
			//msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse("cto@bcits.co.in,rajesh@bcits.co.in"));
			msg.setSubject(SUBJECT);
			msg.setContent(BODY, "text/html");
			Transport transport = session.getTransport();

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
