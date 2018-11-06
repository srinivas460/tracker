package com.AppProject.exceptions;

import com.AppProject.models.ResponseObject;

public class AppException  {

	public ResponseObject showError(String message) {
		ResponseObject object = new ResponseObject();
		object.setStatus(1003);
		object.setMessage(message);
		return object;
	}
}
