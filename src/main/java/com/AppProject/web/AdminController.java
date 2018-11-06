package com.AppProject.web;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.AppProject.Utils.Constants;
import com.AppProject.Utils.Utils;
import com.AppProject.entities.Client;
import com.AppProject.entities.User;
import com.AppProject.models.DataModel;
import com.AppProject.models.ResponseObject;
import com.AppProject.models.UserModel;
import com.AppProject.repositories.RolesRepository;
import com.AppProject.repositories.UserRepository;
import com.AppProject.services.AdminService;
import com.AppProject.services.EmailService;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RolesRepository roleRepository;

	@Autowired
	AdminService adminService;
	

	@GetMapping("/test")
	public String getMarketData() {
		return "foobar";
	}
	
	@GetMapping("/getroles")
	public Object getRolesList() {
		ResponseObject object = new ResponseObject();
		object.setStatus(1005);
		object.setMessage("Fetched Successfully Roles Info");
		DataModel model=new DataModel();
		model.setRoleslist(roleRepository.getAllRole());
		object.setData(model);
		return object;
	}
	
	@GetMapping(path="/getusers")
	public Object getAllUser() {
		ResponseObject object = new ResponseObject();
		DataModel mod = new DataModel();
		mod.setDetails(adminService.populateUsers());
		object.setData(mod);
		
		return object;
	}
	
	@PostMapping(path="/addclient")
	public Object addClient(@RequestBody Client clientData) {
		return adminService.addClient(clientData);
	}

	@PostMapping(path="/addnewuser")
	public Object addUser(@RequestBody UserModel usermodel) {
		return adminService.addUser(usermodel);
	}

	
}
