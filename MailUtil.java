/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	MailUtil.java
 **	Description		:	The java Class MailUtil is a class that is responsible for sending auto-generated e-mails
 *						for leave application and leave approval.
 **	Author			:	Jayesh Patil
 **	Created Date	:	Friday October 06, 2017
 **********************************************************************************************************************
 **	Change History Header:
 **********************************************************************************************************************
 **	Date			Author    		  Version 		Description:
 **	-------			--------   		  --------		------------
 ** 06/10/2017      Jayesh Patil		  1.0           Created
 *********************************************************************************************************************/


package ai.rnt.lms.util;

import java.beans.PropertyVetoException;
import java.security.Security;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import ai.rnt.lms.model.User;

public class MailUtil {

	final String SMTP_HOST_NAME = "smtp.zoho.com"; 
	final String SMTP_PORT = "465";  
	final String emailFromAddress = "d.tare@rabbitandtortoise.com"; 
	final String emailPassword = "Darshana@12345";
	/* final String emailPassword = "LMS@1234"; */
	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	static Session session;
	
	private static final Logger log = LogManager.getLogger(MailUtil.class);
	
	@SuppressWarnings("restriction")
	public Message init() throws MessagingException {
		
		log.info("Inside init");
		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		Properties props = new Properties(); 
		props.put("mail.smtp.host", SMTP_HOST_NAME); 
		
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.port", SMTP_PORT); 
		
		props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
		props.setProperty("mail.smtp.socketFactory.port", SMTP_PORT); 
		props.put("mail.smtp.startssl.enable", "true"); 

		log.info("after props");
		
		session = Session.getInstance(props, 
		new javax.mail.Authenticator() { 
		protected PasswordAuthentication getPasswordAuthentication() { 
			return new PasswordAuthentication(emailFromAddress, "Darshana@12345"); 
		} 
		}); 
		
		//session.setDebug(debug);
	
		Message msg = new MimeMessage(session); 
		InternetAddress addressFrom = new InternetAddress(emailFromAddress); 
		msg.setFrom(addressFrom);
		
		return msg;
		
	}

	public void transportInit(Message msg, InternetAddress[] addressTo) throws MessagingException {
		
		Transport transport = session.getTransport("smtp");  
		transport.connect(SMTP_HOST_NAME, 465, emailFromAddress, "Darshana@12345"); 
		Transport.send(msg);
		transport.close(); 		
	}
	
	public String getMailMessageBodyForApplyLeave() {	
		String messageForApplyLeave = "Your leave application is forward to your manager for approval.";
		return messageForApplyLeave;
	}
	
	public String getMailMessageBodyForApproveLeave() {
		String messageForApproveLeave = "Your leave application has been approved by your manager.";
		return messageForApproveLeave;
	}
	
	public String getMailMessageBodyForRejectLeave() {
		String messageForApproveLeave = "Your leave application has been rejected by your manager.";
		return messageForApproveLeave;
	}
	
	public void sendMailForApplyLeave(User user) throws SQLException, PropertyVetoException, MessagingException {

		Message msg = init();

		final String emailSubjectTxt = "Leave Applied";
		final String[] sendTo = {user.getEmailID()}; 
		final String[] sendCC = {user.getManagerEmailID()};
		
		
		// TO method - Employee
		InternetAddress[] addressTo = new InternetAddress[sendTo.length]; 
		for (int i = 0; i < sendTo.length; i++) { 
			addressTo[i] = new InternetAddress(sendTo[i]); 
		} 
		
		// CC method - Manager
		InternetAddress[] addressCC = new InternetAddress[sendCC.length]; 
		for (int i = 0; i < sendCC.length; i++) { 
			addressCC[i] = new InternetAddress(sendCC[i]); 
		}
		
		msg.setRecipients(Message.RecipientType.TO, addressTo); 
		msg.setRecipients(Message.RecipientType.CC, addressCC);
		
		// Setting the Subject and Content Type 
		msg.setSubject(emailSubjectTxt); 
		msg.setContent(getMailMessageBodyForApplyLeave(), "text/plain"); 
		
		transportInit(msg, addressTo);
		
	}
	
	public void sendMailForApproveLeave(User manager, User employee) throws SQLException, PropertyVetoException, MessagingException {
		
		log.info("Inside sendMailForApproveLeave");
		log.info("Inside sendMailForApproveLeave");
		Message msg = init();

		final String emailSubjectTxt = "Leave Approved";
		final String[] sendCC = {manager.getManagerEmailID()}; 
		final String[] sendTo = {employee.getEmailID()};
		
		// TO method - Manager
		InternetAddress[] addressTo = new InternetAddress[sendTo.length]; 
		for (int i = 0; i < sendTo.length; i++) { 
			addressTo[i] = new InternetAddress(sendTo[i]); 
		} 
		
		// CC method - Employee
		InternetAddress[] addressCC = new InternetAddress[sendCC.length]; 
		for (int i = 0; i < sendCC.length; i++) { 
			addressCC[i] = new InternetAddress(sendCC[i]); 
		}
		
		msg.setRecipients(Message.RecipientType.TO, addressTo); 
		msg.setRecipients(Message.RecipientType.CC, addressCC);
		
		// Setting the Subject and Content Type 
		msg.setSubject(emailSubjectTxt); 
		msg.setContent(getMailMessageBodyForApproveLeave(), "text/plain"); 
		
		transportInit(msg, addressTo);
	}
	
	public void sendMailForRejectLeave(User manager, User employee) throws SQLException, PropertyVetoException, MessagingException {

		Message msg = init();
		
		log.info("Inside sendMailForRejectLeave");
		log.info("Inside sendMailForRejectLeave");
		
		final String emailSubjectTxt = "Leave Rejected";
		final String[] sendCC = {manager.getEmailID()}; 
		final String[] sendTo = {employee.getEmailID()};
		
		
		// TO method - Manager
		InternetAddress[] addressTo = new InternetAddress[sendTo.length]; 
		for (int i = 0; i < sendTo.length; i++) { 
			addressTo[i] = new InternetAddress(sendTo[i]); 
		} 
		
		// CC method - Employee
		InternetAddress[] addressCC = new InternetAddress[sendCC.length]; 
		for (int i = 0; i < sendCC.length; i++) { 
			addressCC[i] = new InternetAddress(sendCC[i]); 
		}
		
		msg.setRecipients(Message.RecipientType.TO, addressTo); 
		msg.setRecipients(Message.RecipientType.CC, addressCC);
		
		// Setting the Subject and Content Type 
		msg.setSubject(emailSubjectTxt); 
		msg.setContent(getMailMessageBodyForRejectLeave(), "text/plain"); 
		
		transportInit(msg, addressTo);
	}

	
}
