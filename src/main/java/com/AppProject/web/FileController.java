package com.AppProject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.AppProject.models.DataModel;
import com.AppProject.models.FileModel;
import com.AppProject.models.ResponseObject;
import com.AppProject.repositories.RolesRepository;
import com.AppProject.services.FileService;

@RestController
@RequestMapping(path = "/file")
public class FileController {

	@Autowired
	RolesRepository roleRepository;

	@Autowired
	FileService fileService;

	@GetMapping("/test")
	public String getMarketData() {
		return "foobar";
	}

	@GetMapping("/projectsize")
	public Object getRolesList() {
		ResponseObject object = new ResponseObject();
		object.setStatus(1005);
		object.setMessage("Fetched Successfully Roles Info");
		DataModel model = new DataModel();
		model.setRoleslist(roleRepository.getAllRole());
		object.setData(model);
		return object;
	}

//	@PostMapping(path = "/upload")
	@PostMapping(path = "/upload")
	public Object createProject(@ModelAttribute("projectid") String projectId,@ModelAttribute("uploadType") String uploadType,@RequestParam("files") MultipartFile[] files) {
//		public Object createProject(@RequestBody FileModel) {
		FileModel model=new FileModel();
		model.setFiles(files);
		model.setProjectid(Long.valueOf(projectId));
		model.setUploadType(Integer.valueOf(uploadType));
		return fileService.addNewUpload(model);
	}

}
