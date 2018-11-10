package com.AppProject.models;

public enum RoleInfo {
	SUPERADMIN("SUPER"),
	PROJECTADMIN("ADMIN"),
	PROJECTLEAD("LEAD");
	
	private final String value;

	private RoleInfo(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}
}
