package com.AppProject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AppProject.models.DataModel;
import com.AppProject.models.ResponseObject;
import com.AppProject.repositories.ProjectRepository;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {
	@Autowired
	ProjectRepository projectRepository;

	@GetMapping("/test")
	public String getMarketData() {
		return "foobar";
	}
	
	@GetMapping("/all")
	public Object getRolesList() {
		ResponseObject object = new ResponseObject();
		object.setStatus(1005);
		object.setMessage("Fetched All  Product Info");
		DataModel model=new DataModel();
		model.setProjects(projectRepository.getAll());
		object.setData(model);
		return object;
	}
	
//	@PostMapping(path="/addclient")
//	public Object addClient(@RequestBody Client clientData) {
//		return adminService.addClient(clientData);
//	}

	
}
