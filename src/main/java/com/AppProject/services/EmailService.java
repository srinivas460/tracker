package com.AppProject.services;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.velocity.VelocityContext;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.AppProject.Utils.Utils;
import com.AppProject.jobs.EmailJobs;
import com.AppProject.models.EmailModel;
import com.AppProject.models.UserModel;

@Service
public class EmailService {

	private final Logger logger = LoggerFactory.getLogger(EmailService.class);
	@Value("${spring.application.name}")
	private String APP_NAME;

	@Value("${spring.mail.protocol}")
	private String MAIL_SERVER;

	@Value("${spring.mail.port}")
	private int MAIL_PORT;

	@Value("${spring.mail.host}")
	private String MAIL_HOST;
	
	@Value("${spring.mail.username}")
	private String MAIL_USER;

	@Value("${spring.mail.password}")
	private String MAIL_PWD;
	
	@Value("${app.mail.debug.enable}")
	private Boolean MAIL_DEBUG;

	@Autowired
	CreateVelocityTemplate velocityTemplate;
	
	public void sendForgetEmail(UserModel userInfo) throws AddressException, MessagingException, SchedulerException {
		VelocityContext velocityContext=new VelocityContext();
		velocityContext.put("fullName", userInfo.getName());
		velocityContext.put("AppName", APP_NAME);
		velocityContext.put("username", userInfo.getUsername());
		velocityContext.put("password", userInfo.getPassword());
		String content=velocityTemplate.getTemplate("forgot_template.vm", velocityContext);
		
		EmailModel emodel=new EmailModel();
		emodel.setToEmail(userInfo.getEmail());
		emodel.setBody(content);
		emodel.setSubject(APP_NAME+"Login Credentials info!!!");
		emodel.setMailDebug(MAIL_DEBUG);
		emodel.setMailHost(MAIL_HOST);
		emodel.setMailPort(MAIL_PORT);
		emodel.setMailServer(MAIL_SERVER);
		emodel.setMailUser(MAIL_USER);
		emodel.setMailPwd(MAIL_PWD);
		sendFromGMail(emodel);
	}
	
	public void sendWelcomeEmail(UserModel userInfo) throws AddressException, MessagingException, SchedulerException {

		VelocityContext velocityContext=new VelocityContext();
		velocityContext.put("fullName", userInfo.getName());
		velocityContext.put("AppName", APP_NAME);
		velocityContext.put("LoginId", userInfo.getUsername());
		velocityContext.put("LoginPwd", userInfo.getPassword());
		String content=velocityTemplate.getTemplate("welcome_user_template.vm", velocityContext);
		
		EmailModel emodel=new EmailModel();
		emodel.setToEmail(userInfo.getEmail());
		emodel.setBody(content);
		emodel.setSubject(APP_NAME+"Login Credentials info!!!");
		emodel.setMailDebug(MAIL_DEBUG);
		emodel.setMailHost(MAIL_HOST);
		emodel.setMailPort(MAIL_PORT);
		emodel.setMailServer(MAIL_SERVER);
		emodel.setMailUser(MAIL_USER);
		emodel.setMailPwd(MAIL_PWD);
		sendFromGMail(emodel);
	}

	private  void sendFromGMail(EmailModel emailProp) throws AddressException, MessagingException, SchedulerException {
		

		String objectKey = Utils.generateRandomObjectKey();
		
		logger.debug("Assigning Email Job with Key :"+objectKey);
	

		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler;

		scheduler = factory.getScheduler();
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("data", emailProp);
		jobDataMap.put("key", objectKey);
		JobDetail jobDetails = JobBuilder.newJob(EmailJobs.class)
							.withIdentity(objectKey,"email-jobs")
							.withDescription("Sending email Jobs")
							.usingJobData(jobDataMap)
							.storeDurably()
							.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
						.forJob(jobDetails)
						.withIdentity(objectKey,"email-jobs")
						.withDescription("Sending email trigger")
						.startNow()
//						.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
						.build();

		scheduler.start();
		scheduler.scheduleJob(jobDetails,trigger);
		
		logger.debug("Email sending Job Scheduled Now..");


	}

	
}
