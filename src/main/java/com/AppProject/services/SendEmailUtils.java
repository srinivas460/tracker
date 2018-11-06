package com.AppProject.services;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.AppProject.models.EmailModel;

@Service
public class SendEmailUtils {

	private final Logger logger = LoggerFactory.getLogger(SendEmailUtils.class);

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
	private boolean MAIL_DEBUG=false;



}
