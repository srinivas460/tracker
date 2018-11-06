package com.AppProject.models;

import java.util.List;

public class DataModel {

	private List<String> roleslist;
	private String token;
	private List<RoleModel> roles;
	private Object details;
	
	
	public Object getDetails() {
		return details;
	}
	public void setDetails(Object details) {
		this.details = details;
	}
	public List<String> getRoleslist() {
		return roleslist;
	}
	public void setRoleslist(List<String> roleslist) {
		this.roleslist = roleslist;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<RoleModel> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleModel> roles) {
		this.roles = roles;
	}

	
	
	
}
