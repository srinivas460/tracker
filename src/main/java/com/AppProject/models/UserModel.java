package com.AppProject.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String fcm;
	private String macid;
	
	private String name;
	private String mobile;
	private String email;
	private String clientId;
	private List<String> roles;
	private List<RoleModel> roleModel;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFcm() {
		return fcm;
	}
	public void setFcm(String fcm) {
		this.fcm = fcm;
	}
	public String getMacid() {
		return macid;
	}
	public void setMacid(String macid) {
		this.macid = macid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<RoleModel> getRoleModel() {
		return roleModel;
	}
	public void setRoleModel(List<RoleModel> roleModel) {
		this.roleModel = roleModel;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	} 
	
	

}
