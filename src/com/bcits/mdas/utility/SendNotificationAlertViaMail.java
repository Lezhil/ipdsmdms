package com.bcits.mdas.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.scheduling.annotation.Async;

import com.bcits.entity.EmailGateway;

public class SendNotificationAlertViaMail implements Runnable {

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	String SUBJECT = "";
	String BODY = "";
	String TO = "";
	String CC = "";
	String FILE = "";
	String SENDERMAILID = "";
	String SENDERMAILPASSWORD = "";
	String HOST="";
	String PORT="";


	public SendNotificationAlertViaMail(String subject, String body, String to, String cc, String file) {

		this.BODY = body;
		this.SUBJECT = subject;
		this.TO = to;
		this.CC = cc;
		this.FILE = file;
	}

	@Override
	@Async
	public void run() {

		try {
			
				
				
				
				EmailGateway e = entityManager.createNamedQuery("EmailGateway.findUserById", EmailGateway.class).setParameter("id", 1).getSingleResult();
				PORT = e.getSmtpPort();
				SENDERMAILID = e.getMailId();
				HOST = e.getSmtpHost();
				SENDERMAILPASSWORD = e.getMailPassword();
			
			
			System.out.println("Port--"+PORT +"Mailid--"+  SENDERMAILID +"host--"+ HOST +"Password--"+SENDERMAILPASSWORD  );

			final Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", HOST);
			props.put("mail.smtp.socketFactory.port", PORT);
			props.put("mail.smtp.starttls.enable", "false");
			props.put("mail.smtp.auth", "false");
			props.put("mail.smtp.port", PORT);
			final Session session = Session.getDefaultInstance(props);
			final MimeMessage message = new MimeMessage(session);

			Multipart emailContent = new MimeMultipart();
			MimeBodyPart textBodyFiled = new MimeBodyPart();
			textBodyFiled.setContent(BODY, "text/html");
			emailContent.addBodyPart(textBodyFiled);
			message.setContent(emailContent);

			message.setFrom(new InternetAddress(SENDERMAILID, SENDERMAILPASSWORD));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC));

			message.setSubject(SUBJECT);

			Transport transport = session.getTransport();

			System.out.println("Sending...");

			transport.connect(HOST, SENDERMAILID, SENDERMAILPASSWORD);

			transport.sendMessage((Message) message, message.getAllRecipients());
			System.out.println("Email sent!");
			transport.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + e.getMessage());
		}

	}

}
