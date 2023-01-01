package com.bcits.mdas.utility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;





public class SendEBillRegistrationOTPMail implements Runnable {

/*	
	private final String FROM = "noreply@bijlimitra.com";

	private final String FROMNAME = "JVVNL";
	private String TO = "toAddressEmail";
	static final String SMTP_USERNAME = "AKIAJIEYQXDQMY6QHSIA";
	static final String SMTP_PASSWORD = "AkWyEUbO+OC754k8fo97CRCsupo0zGxiROGrQ3s08aJY";
	static final String HOST = "email-smtp.us-west-2.amazonaws.com";

	static final int PORT = 587;

	String SUBJECT ="";

	String BODY = "";*/
	
	
	private final String toAddressEmail;
	private final String mailId;
	private final String mailIdPwd;	
	private final String mailSmtpHost;
	private final String mailSmtpSocketFactoryPort;
	private final String mailSmtpSocketFactoryClass;
	private final String mailSmtpAuth;
	private final String mailSmtpPort;
	private final InternetAddress fromAddress;
	private final String customerName;
	private final String opt;
	private String source;
	//private final String password;
	//
	
	
	public SendEBillRegistrationOTPMail(String toemail,String customerName,String opt,String source) throws UnsupportedEncodingException {
		
		this.toAddressEmail=toemail;
		this.mailId=StaticProperties.Email_gateWay_username1;
		this.mailIdPwd=StaticProperties.Email_gateWay_password1;
		this.mailSmtpHost=StaticProperties.mail_smtp_smtp_host;
		this.mailSmtpSocketFactoryPort=StaticProperties.mail_smtp_socketFactory_port1;
		this.mailSmtpSocketFactoryClass=StaticProperties.mail_smtp_socketFactory_class1;
		this.mailSmtpAuth=StaticProperties.mail_smtp_auth1;
		this.mailSmtpPort=StaticProperties.mail_smtp_port1;
		this.fromAddress=new InternetAddress("AKIAJIEYQXDQMY6QHSIA","PGRS-JVVNL");
		this.customerName=customerName;
		this.opt=opt;
		this.source=source;
		//this.password=password;
	}

	public SendEBillRegistrationOTPMail(String toemail,String customerName,String opt) throws UnsupportedEncodingException {
	
		this.toAddressEmail=toemail;
		this.mailId=StaticProperties.Email_gateWay_username1;
		this.mailIdPwd=StaticProperties.Email_gateWay_password1;
		this.mailSmtpHost=StaticProperties.mail_smtp_smtp_host;
		this.mailSmtpSocketFactoryPort=StaticProperties.mail_smtp_socketFactory_port1;
		this.mailSmtpSocketFactoryClass=StaticProperties.mail_smtp_socketFactory_class1;
		this.mailSmtpAuth=StaticProperties.mail_smtp_auth1;
		this.mailSmtpPort=StaticProperties.mail_smtp_port1;
		this.fromAddress=new InternetAddress("AKIAJIEYQXDQMY6QHSIA","PGRS-JVVNL");
		this.customerName=customerName;
		this.opt=opt;
		//this.password=password;
	}

	@Override
	public void run() {
/*		Properties props = new Properties();
		props.put("mail.smtp.host", mailSmtpHost);
		props.put("mail.smtp.socketFactory.port", mailSmtpSocketFactoryPort);
		props.put("mail.smtp.socketFactory.class", mailSmtpSocketFactoryClass);
		props.put("mail.smtp.auth", mailSmtpAuth);
		props.put("mail.smtp.port", mailSmtpPort);
		*/
		
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", mailSmtpPort);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		
		
		/*Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(mailId, mailIdPwd);
			}
		});	*/
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		BodyPart htmlPart = new MimeBodyPart();
		try{
			
			/*message.setFrom(fromAddress);
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddressEmail));
			message.setSubject("E-Bill Registration");*/
			
			message.setFrom(new InternetAddress("noreply@bijlimitra.com", "JVVNL"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddressEmail));

			//message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(ccMailId));
			//msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse("cto@bcits.co.in,rajesh@bcits.co.in"));


			message.setSubject("Bijli Mitra");
			
			String messageContent="";
			if(source.equals("reg"))
				messageContent = "<html>"
						+ "<style type=\"text/css\">"
						+ "table.hovertable {"
						+ "font-family: verdana,arial,sans-serif;"
						+ "font-size:11px;"
						+ "color:#333333;"
						+ "border-width: 1px;"
						+ "border-color: #999999;"
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
						+ "border-width: 1px;"
						+ "padding: 8px;"
						+ "border-style: solid;"
						+ "border-color: #394c2e;"
						+ "}"
						+ "</style>"
						+ "<h2  align=\"center\"  style=\"background-color:#409fde; color:white; padding:5px;\">JVVNL-NCMS Administration Department</h2> Dear "+ customerName + ", <br/><br/>"
						+ "Your OTP for Bijli Mitra App is : <b style='font-size: 18px;'>"+opt+"</b>.<br/>"
						+ "Please enter the above OTP to Register.<br/><br/>"
						+ "<br/>Thank you,<br/>"
						+ "JVVNL-NCMS Administration Services. <br/> <br/>";
			else
				messageContent = "<html>"
						+ "<style type=\"text/css\">"
						+ "table.hovertable {"
						+ "font-family: verdana,arial,sans-serif;"
						+ "font-size:11px;"
						+ "color:#333333;"
						+ "border-width: 1px;"
						+ "border-color: #999999;"
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
						+ "border-width: 1px;"
						+ "padding: 8px;"
						+ "border-style: solid;"
						+ "border-color: #394c2e;"
						+ "}"
						+ "</style>"
						+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">JVVNL-NCMS Administration Department</h2> Dear "+ customerName + ", <br/><br/>"
						+ "Your E-Bill Registration OTP is :"+opt+".<br/>"
						+ "Please enter the above OTP to activate your account with E-Bill Services.<br/><br/>"
						+ "<br/>Thank you,<br/>"
						+ "JVVNL-NCMS Administration Services. <br/> <br/>";			
				
			message.setContent(messageContent, "text/html");
			htmlPart.setContent(messageContent,"text/html");
			
			MimeMultipart multipart = new MimeMultipart("mixed");

			htmlPart.setDisposition(BodyPart.INLINE);
			multipart.addBodyPart(htmlPart);
			//message.setContent(messageContent,"text/html; charset=ISO-8859-1");
			// Create a transport.
			Transport transport = session.getTransport();

			 

			// Send the message.
			System.out.println("Sending...");

			// Connect to Amazon SES using the SMTP username and password you specified
			// above.
			transport.connect(mailSmtpHost, mailId, mailIdPwd);

			// Add the parent container to the message.


			// Send the email.
			//System.setProperty("https.protocols", "TLSv1.1");
			transport.sendMessage(message, message.getAllRecipients());
			System.out.println("Email sent!");
			transport.close();

		//	Transport.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
}
