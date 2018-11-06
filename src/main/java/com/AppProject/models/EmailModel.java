package com.AppProject.models;

import java.util.ArrayList;
import java.util.List;

public class EmailModel {

	private List<String> toEmails=new ArrayList<>();
	private List<String> ccEmails=new ArrayList<>();
	private List<String> bccEmails=new ArrayList<>();
	
	private String subject;
	private String body;
	private String attachment;
	
	private String mailServer;
	private String mailHost;
	private int mailPort;
	private String mailUser;
	private String mailPwd;
	private boolean mailDebug;
	
	public List<String> getToEmails() {
		return toEmails;
	}
	public void setToEmail(String toEmail) {
		this.toEmails.add(toEmail);
	}
	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}
	public List<String> getCcEmails() {
		return ccEmails;
	}
	public void setCcEmails(List<String> ccEmails) {
		this.ccEmails = ccEmails;
	}
	public List<String> getBccEmails() {
		return bccEmails;
	}
	public void setBccEmails(List<String> bccEmails) {
		this.bccEmails = bccEmails;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	
	public String getMailServer() {
		return mailServer;
	}
	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}
	public String getMailHost() {
		return mailHost;
	}
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	public int getMailPort() {
		return mailPort;
	}
	public void setMailPort(int mailPort) {
		this.mailPort = mailPort;
	}
	public String getMailUser() {
		return mailUser;
	}
	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}
	public String getMailPwd() {
		return mailPwd;
	}
	public void setMailPwd(String mailPwd) {
		this.mailPwd = mailPwd;
	}
	
	
	public boolean isMailDebug() {
		return mailDebug;
	}
	public void setMailDebug(boolean mailDebug) {
		this.mailDebug = mailDebug;
	}
	@Override
	public String toString() {
		return "EmailModel [toEmails=" + toEmails + ", ccEmails=" + ccEmails + ", bccEmails=" + bccEmails + ", subject="
				+ subject + ", body=" + body + ", attachment=" + attachment + "]";
	}
	
	
	
}
