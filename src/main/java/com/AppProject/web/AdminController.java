package com.AppProject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AppProject.entities.Client;
import com.AppProject.entities.Project;
import com.AppProject.models.DataModel;
import com.AppProject.models.ResponseObject;
import com.AppProject.models.UserModel;
import com.AppProject.repositories.RolesRepository;
import com.AppProject.repositories.UserRepository;
import com.AppProject.services.AdminService;

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
	
	@PostMapping(path="/createproject")
	public Object createProject(@RequestBody Project project) {
		return adminService.addNewProject(project);
	}

	
}
