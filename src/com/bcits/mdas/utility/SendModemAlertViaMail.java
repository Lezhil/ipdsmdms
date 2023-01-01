package com.bcits.mdas.utility;

import java.util.List;
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
/*import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;*/

import com.bcits.entity.EmailGateway;

public class SendModemAlertViaMail implements Runnable {
	
	/*private final String FROM = "mdas.bcits@gmail.com";
	private final String FROMNAME = "AMR-MDAS Notification";
	static final String SMTP_USERNAME = "mdas.bcits@gmail.com";
    static final String SMTP_PASSWORD = "bcits@123";
    static final String HOST = "smtp.mail.com";
    static final int PORT = 587;*/
	
//	final static String FROM = "ae3itcell@tnebnet.org";
//	final static String FROMNAME = "TANGEDCO Notification";
//	static final String SMTP_USERNAME = "ae3itcell@tnebnet.org";
//	static final String SMTP_PASSWORD = "ae3!Tcell@";
	static final String HOST = "10.2.4.100";
	static final String PORT = "25";
	
   // private final String FROM = "kesavbalaji28@gmail.com";
	//private final String FROMNAME = "TANGEDCO Notification";
	//static final String SMTP_USERNAME = "kesavbalaji28@gmail.com";
	//static final String SMTP_PASSWORD = "bcits@123";
//	static final String HOST = "smtp.gmail.com";
//	static final String PORT = "465";
    
    String SUBJECT ="";
    String BODY = "";	
    String TO="";
    String CC="";
    String FILE="";
    String SENDERMAILID="ae3ipdsmdms@tnebnet.org";
    String SENDERMAILPASSWORD="P@$$w0rd@123";

	
	public SendModemAlertViaMail(String subject,String body,String to,String cc,String sendermailid,String sendermailpassword) {
		
		this.BODY=body;
		this.SUBJECT=subject;
		this.TO=to;
		this.CC=cc;
		this.SENDERMAILID=sendermailid;
		this.SENDERMAILPASSWORD=sendermailpassword;
		
	}
	

	public SendModemAlertViaMail(String subject,String body,String to,String cc,String file) {
		
		this.BODY=body;
		this.SUBJECT=subject;
		this.TO=to;
		this.CC=cc;
		this.FILE=file;
	}
	
	public SendModemAlertViaMail(String subject, String body, String to, String cc) {

		this.BODY = body;
		this.SUBJECT = subject;
		this.TO = to;
		this.CC = cc;
	}
    
	
	@Override
	@Async
	public void run() {

		try {
			// Create a Properties object to contain connection configuration information.
			//Properties props = System.getProperties();
//			props.put("mail.transport.protocol", "smtp");
//			props.put("mail.smtp.port", PORT);
//			props.put("mail.smtp.starttls.enable", "true");
//			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			props.put("mail.smtp.auth", "false");
//			final Properties props = new Properties();
//			props.put("mail.transport.protocol", "smtp");
//			props.put("mail.smtp.host", HOST);
//			props.put("mail.smtp.socketFactory.port", PORT);
////			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			props.put("mail.smtp.starttls.enable", "false");
//			props.put("mail.smtp.auth", "false");
//			props.put("mail.smtp.port", PORT);
//
//			Session session = Session.getDefaultInstance(props);
//			MimeMessage message = new MimeMessage(session);
		
		
			final Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "10.2.4.100");
            props.put("mail.smtp.socketFactory.port", "25");
            props.put("mail.smtp.starttls.enable", "false");
            props.put("mail.smtp.auth", "false");
            props.put("mail.smtp.port", "25");
            final Session session = Session.getDefaultInstance(props);
            final MimeMessage message = new MimeMessage(session);
            
			Multipart emailContent = new MimeMultipart();
			MimeBodyPart textBodyFiled = new MimeBodyPart();
	        textBodyFiled.setContent(BODY,"text/html");
	        emailContent.addBodyPart(textBodyFiled);
	        message.setContent(emailContent);
	        
		        message.setFrom(new InternetAddress(SENDERMAILID, SENDERMAILPASSWORD));
		        message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		      //  message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO));
		        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC));
		     //   message.addRecipient(Message.RecipientType.CC, new InternetAddress("eecc@tnebnet.org"));
		      //  message.addRecipient(Message.RecipientType.CC, new InternetAddress("aee4itcell@tnebnet.org"));
		      //  message.addRecipient(Message.RecipientType.CC, new InternetAddress(""));
		       // message.addRecipient(Message.RecipientType.CC, new InternetAddress(""));

		        message.setSubject(SUBJECT);
	
		        Transport transport = session.getTransport();

		        System.out.println("Sending...");

				transport.connect(HOST, SENDERMAILID, SENDERMAILPASSWORD);

		      //  transport.sendMessage(message, message.getAllRecipients());
		        transport.sendMessage((Message)message, message.getAllRecipients());
		        System.out.println("Email sent!");
		        transport.close();

		        }catch(Exception e){
		        	e.printStackTrace();
					System.out.println("The email was not sent.");
					System.out.println("Error message: " + e.getMessage());		        }

	}
	
	
	/*@Async
	private void sendSMSonMailexception(String msg) throws ParseException {
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
	 	smsCredentialsDetailsBean.setNumber("9035997204"); 
		smsCredentialsDetailsBean.setUserName("SACHIN");
		String message="Dear User,"
					+" "+msg+" not sent";
                   
                 
		smsCredentialsDetailsBean.setMessage(message);
		new Thread(new SendDocketInfoSMS(smsCredentialsDetailsBean)).start(); 
	}*/
	

}






