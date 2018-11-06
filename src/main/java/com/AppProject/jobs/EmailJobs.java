package com.AppProject.jobs;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.AppProject.models.EmailModel;

public class EmailJobs implements Job {

	private final Logger logger = LoggerFactory.getLogger(EmailJobs.class);


	@Override
	public void execute(JobExecutionContext context) {

		JobDataMap jobmap = context.getJobDetail().getJobDataMap();

		EmailModel emailProp = (EmailModel) jobmap.get("data");

		if (emailProp != null) {
			logger.debug("Email Job Triggered and Started Now for sending user " + emailProp.getToEmails());
			try {
					sendEmail(emailProp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	

	public void sendEmail(EmailModel emailProp) {

		// ---------------------------------------------STEP
		// 1---------------------------------------------

		logger.debug("---------------------------------------------");
		logger.debug("Setup SMTP Mail Server Properties..!");
		logger.debug("---------------------------------------------");
		
		String[] to = emailProp.getToEmails().toArray(new String[emailProp.getToEmails().size()]);
		String[] cc = emailProp.getCcEmails().toArray(new String[emailProp.getCcEmails().size()]);
		String[] bcc = emailProp.getBccEmails().toArray(new String[emailProp.getBccEmails().size()]);
		;

		// Get system properties
		// Setup mail server
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", emailProp.getMailHost());
		properties.put("mail.smtp.user", emailProp.getMailUser());
		properties.put("mail.smtp.password", emailProp.getMailPwd());
		properties.put("mail.smtp.port", emailProp.getMailPort());
		properties.put("mail.smtp.auth", "true");


		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailProp.getMailUser(), emailProp.getMailPwd());
			}
		});
		session.setDebug(emailProp.isMailDebug());
		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(emailProp.getMailUser()));

			// ---------------------------------------------

			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of toaddresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			// Set To: header field of the header.
			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			InternetAddress[] ccAddress = new InternetAddress[cc.length];

			// To get the array of ccaddresses
			for (int i = 0; i < cc.length; i++) {
				ccAddress[i] = new InternetAddress(cc[i]);
			}

			// Set cc: header field of the header.
			for (int i = 0; i < ccAddress.length; i++) {
				message.addRecipient(Message.RecipientType.CC, ccAddress[i]);
			}

			InternetAddress[] bccAddress = new InternetAddress[bcc.length];

			// To get the array of bccaddresses
			for (int i = 0; i < bcc.length; i++) {
				bccAddress[i] = new InternetAddress(bcc[i]);
			}

			// Set bcc: header field of the header.
			for (int i = 0; i < bccAddress.length; i++) {
				message.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
			}

			// Set Subject: header field
			message.setSubject(emailProp.getSubject());

			// Now set the date to actual message
			message.setSentDate(new Date());

			// Now set the actual message
			message.setContent(emailProp.getBody(), "text/html");

			Transport.send(message);
			logger.debug("---------------------------------------------");
			logger.debug("Sent Message Successfully....");
			logger.debug("---------------------------------------------");
		} catch (AddressException e) {
			logger.error("Error while sending email : {}",e);
		} catch (MessagingException e) {
			logger.error("Error while sending email : {}",e);
		}

	}

	protected SchedulerContext getSchedulerContext(JobExecutionContext context) {
		try {
			return context.getScheduler().getContext();
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
