package com.AppProject.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class ResponseObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;
	private DataModel data;

	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public DataModel getData() {
		return data;
	}
	public void setData(DataModel data) {
		this.data = data;
	}

}
