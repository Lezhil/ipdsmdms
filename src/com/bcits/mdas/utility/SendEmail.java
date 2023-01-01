package com.bcits.mdas.utility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;

import com.sun.xml.internal.bind.v2.Messages;

public class SendEmail implements Runnable {

	private final String toAddressEmail;
	private final String mailId;
	private final String mailIdPwd;	
	private final String mailSmtpHost;
	private final String mailSmtpPort;
	private final String content;
	
	 
	public SendEmail(String toemail, String content) throws UnsupportedEncodingException {
	
		this.toAddressEmail=toemail;
		this.mailId=StaticProperties.Email_gateWay_username1;
		this.mailIdPwd=StaticProperties.Email_gateWay_password1;
		this.mailSmtpHost=StaticProperties.mail_smtp_smtp_host;
		this.mailSmtpPort=StaticProperties.mail_smtp_port1;
		this.content=content;
		
	}

	@Override
	public void run() {
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", mailSmtpPort);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		BodyPart htmlPart = new MimeBodyPart();
		 String appName=ResourceBundle.getBundle("messages").getString("smsEmailMsgHeader");
		try{
			message.setFrom(new InternetAddress("noreply@bijlimitra.com",appName));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddressEmail));
			message.setSubject(appName);
			
				String messageContent = "<html>"
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
						+ "<h2  align=\"center\"  style=\"background-color:#409fde; color:white; padding:5px;\">"+appName+"</h2>"
						+ "<p style='font-size: 16px;'>"+content+"</p>"
						+ "<br/>Thank you,<br/>"
						+ appName+". <br/> <br/>";
			 
			message.setContent(messageContent, "text/html");
			htmlPart.setContent(messageContent,"text/html");
			
			MimeMultipart multipart = new MimeMultipart("mixed");

			htmlPart.setDisposition(BodyPart.INLINE);
			multipart.addBodyPart(htmlPart);
			Transport transport = session.getTransport();
			// Send the message.
			System.out.println("Sending...");

			// Connect to Amazon SES using the SMTP username and password you specified
			// above.
			transport.connect(mailSmtpHost, mailId, mailIdPwd);

			// Add the parent container to the message.
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
