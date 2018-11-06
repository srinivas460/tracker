package com.AppProject.models;

public class RoleModel {


	private String name;
	private String fullName;
	
	private Integer isAdmin=0;
	private Integer isClient=0;
	private Integer isManager=0;
	private Integer isDev=0;
	private Integer isSuperDev=0;
	private Integer canComment=0;
	private Integer canCreateuser=0;
	private Integer cancreateTicket=0;
	private Integer canAssignuser=0;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Integer getIsClient() {
		return isClient;
	}
	public void setIsClient(Integer isClient) {
		this.isClient = isClient;
	}
	public Integer getIsManager() {
		return isManager;
	}
	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}
	public Integer getIsDev() {
		return isDev;
	}
	public void setIsDev(Integer isDev) {
		this.isDev = isDev;
	}
	public Integer getIsSuperDev() {
		return isSuperDev;
	}
	public void setIsSuperDev(Integer isSuperDev) {
		this.isSuperDev = isSuperDev;
	}
	public Integer getCanComment() {
		return canComment;
	}
	public void setCanComment(Integer canComment) {
		this.canComment = canComment;
	}
	public Integer getCanCreateuser() {
		return canCreateuser;
	}
	public void setCanCreateuser(Integer canCreateuser) {
		this.canCreateuser = canCreateuser;
	}
	public Integer getCancreateTicket() {
		return cancreateTicket;
	}
	public void setCancreateTicket(Integer cancreateTicket) {
		this.cancreateTicket = cancreateTicket;
	}
	public Integer getCanAssignuser() {
		return canAssignuser;
	}
	public void setCanAssignuser(Integer canAssignuser) {
		this.canAssignuser = canAssignuser;
	}
	
	
}
