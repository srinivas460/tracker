package com.AppProject.web;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AppProject.Utils.Constants;
import com.AppProject.Utils.Utils;
import com.AppProject.entities.Client;
import com.AppProject.entities.Role;
import com.AppProject.entities.User;
import com.AppProject.models.DataModel;
import com.AppProject.models.ResponseObject;
import com.AppProject.models.RoleModel;
import com.AppProject.models.UserModel;
import com.AppProject.repositories.ClientsRepository;
import com.AppProject.repositories.RolesRepository;
import com.AppProject.repositories.UserRepository;
import com.AppProject.services.EmailService;

@RestController
@RequestMapping(path = "/app")
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	UserRepository userRepository;
	@Autowired
	RolesRepository roleRepository;
	@Autowired
	ClientsRepository clientRepository;
	

	@Autowired
	EmailService emailService;
	
	@PostMapping(path="/login")
	public Object addUser(@RequestBody UserModel usermodel) {
		ResponseObject object = new ResponseObject();
		if(usermodel==null) {
			object.setStatus(Constants.CODE_ERROR);
			object.setMessage("Required fields are missing");
			return object;
		}
		
		User isUserExist=userRepository.findByUsername(usermodel.getUsername());
		if(isUserExist==null) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("User does not exists.");
			return object;
		}
		
		if(!User.PASSWORD_ENCODER.matches(usermodel.getPassword(), isUserExist.getPassword())) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("Invalid user credentials");
			return object;
		}
		if(usermodel.getPassword().equals("dummy")) {
			isUserExist.setStatus(1);
		}
		
		if(!usermodel.getMacid().equals(isUserExist.getMacid())) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("You are Not Authorized to use this app in this Device");
			return object;
		}
		

		isUserExist.setFcm(usermodel.getFcm());
		isUserExist.setMacid(usermodel.getMacid());
		isUserExist.setLoggedIn(new Date());
		userRepository.save(isUserExist);
		
//		String encoding = Base64.getEncoder().encodeToString((usermodel.getUsername()+":"+User.PASSWORD_ENCODER.encode(usermodel.getPassword())).getBytes());
//		logger.debug("TOKEN GENERATED : "+encoding);
//		logger.debug("TOKEN GENERATED : "+Base64.getDecoder().decode(encoding));
		
		
		List<RoleModel> roleslist = new ArrayList<>();
		for(Role role : isUserExist.getRoles()) {
			RoleModel mod=new RoleModel();
			mod.setCanAssignuser(role.getCanAssignuser());
			mod.setCanComment(role.getCanComment());
			mod.setCancreateTicket(role.getCancreateTicket());
			mod.setCanCreateuser(role.getCanCreateuser());
			mod.setFullName(role.getFullName());
			mod.setIsAdmin(role.getIsAdmin());
			mod.setIsClient(role.getIsClient());
			mod.setIsDev(role.getIsDev());
			mod.setIsManager(role.getIsManager());
			mod.setIsSuperDev(role.getIsSuperDev());
			roleslist.add(mod);
		}
		DataModel model = new DataModel();
//		model.setToken(encoding);
		model.setRoles(roleslist);
		if(isUserExist.getStatus()==1) {
			object.setStatus(Constants.CODE_RESET);
			object.setMessage("You Need to change your password");
		}else {
		object.setStatus(Constants.CODE_SUCCESS);
		object.setMessage("Logged SuccessFully done");
		}
		object.setData(model);
		return object;
	}
	
	@PostMapping(path="/changepassword")
	public Object changePassword(@RequestBody UserModel usermodel) {
		ResponseObject object = new ResponseObject();
		object.setStatus(Constants.CODE_ERROR);
		User isUserExist=userRepository.findByUsername(usermodel.getUsername());
		if(isUserExist==null) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("User does not exists.");
			return object;
		}
		
		if(isUserExist.getActive()!=1) {
			object.setMessage("You are not Authorized to change password");	
		}
		
		isUserExist.setPassword(User.PASSWORD_ENCODER.encode(usermodel.getPassword()));
		if(isUserExist.getStatus()==1) {
			isUserExist.setStatus(0);
		}
		isUserExist.setUpdatedAt(new Date());
		userRepository.save(isUserExist);

		object.setStatus(Constants.CODE_SUCCESS);
		object.setMessage("Request for Password change has successfully done");
		return object;
	}


	@PostMapping(path="/forgot")
	public Object forgotPassword(@RequestBody UserModel usermodel) {
		ResponseObject object = new ResponseObject();
		object.setStatus(Constants.CODE_ERROR);
		User isUserExist=userRepository.findByUsername(usermodel.getUsername());
		if(isUserExist==null) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("User does not exists.");
			return object;
		}
		
		if(isUserExist.getActive()!=1) {
			object.setMessage("You are not Authorized to access this app");	
			return object;
		}
		
		String randamPassword = Utils.getPassword(6,true,false);
		UserModel model = new UserModel();
		model.setEmail(isUserExist.getEmail());
		model.setName(isUserExist.getName());
		model.setPassword(randamPassword);
		model.setUsername(isUserExist.getUsername());
		
		try {
			emailService.sendForgetEmail(model);
			object.setStatus(Constants.CODE_SUCCESS);
			object.setMessage("A Mail was sent to your registered email address with us.");
			isUserExist.setPassword(User.PASSWORD_ENCODER.encode(randamPassword));
			isUserExist.setUpdatedAt(new Date());
			isUserExist.setStatus(1);
			userRepository.save(isUserExist);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			object.setStatus(Constants.CODE_ERROR);
			object.setMessage("Error while sending email : Address Exception "+e.getMessage());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();object.setStatus(Constants.CODE_ERROR);
			object.setMessage("Error while sending email : Messaging Exception "+e.getMessage());
		} catch (SchedulerException e) {
			e.printStackTrace();object.setStatus(Constants.CODE_ERROR);
			object.setMessage("Error while sending email : Scheduler Exception "+e.getMessage());
		}
		
		return object;
	}
	
	@GetMapping("/dummy")
	public Object loadDummy() {
		ResponseObject object = new ResponseObject();
		
		Client client = new Client();
		client.setMobile("9676462920");
		client.setName("AmendSquare Pvt Ltd.");
		client.setDescirption("AmendSquare Pvt Ltd.");
		client.setActive(1);

        Set<User> userClients = new HashSet<User>();
        Set<Client> ClientUser = new HashSet<Client>();
		
		Role role = new Role();
		role.setActive(1);
		role.setName("ADMIN");
		role.setFullName("Adminstrator");
		role.setCanComment(1);
		role.setIsAdmin(1);
		role.setCanAssignuser(1);
		role.setCanComment(1);
		role.setCancreateTicket(1);
		role.setCanCreateuser(1);

		roleRepository.save(role);

		User user = new User();
		user.setEmail("p.srinivasvarma@gmail.com");
		user.setMobile("9676462929");
		user.setName("Varma");
		user.setUsername("9676462929");
		user.setMacid(user.getMobile());
		user.setCreatedAt(new Date());
		user.setPassword(user.PASSWORD_ENCODER.encode("dummy"));
		user.setRoles(roleRepository.getRoles("ADMIN"));
		
		Set<User> roleuser=new HashSet<>();
		roleuser.add(user);
		role.setUsers(roleuser);

		user= userRepository.save(user);
		 ClientUser.add(client);
	        user.setUserClient(client);
	        userClients.add(user);
	        client.setUsers(userClients);
	        clientRepository.save(client);

//		client=clientRepository.save(client);
		userRepository.save(user);
		roleRepository.save(role);
		
		
/*		ClientUser = new HashSet<Client>();
			 role = new Role();
			role.setActive(1);
			role.setName("DEV");
			role.setFullName("DEVELOPER");
			role.setCanComment(1);
			role.setIsAdmin(1);
			role.setCanAssignuser(1);
			role.setCanComment(1);
			role.setCancreateTicket(1);
			role.setCanCreateuser(1);

			roleRepository.save(role);

			 user = new User();
			user.setEmail("prabas.2u@gmail.com");
			user.setMobile("9676462930");
			user.setName("Varma");
			user.setUsername("9676462930");
			user.setMacid(user.getMobile());
			user.setCreatedAt(new Date());
			user.setPassword(user.PASSWORD_ENCODER.encode("dummy"));
			user.setRoles(roleRepository.getRoles("DEV"));
			
			roleuser=new HashSet<>();
			roleuser.add(user);
			role.setUsers(roleuser);


				ClientUser.add(client);
		        user.setUserClient(client);
		        userClients.add(user);
		        client.addUser(user);
		        clientRepository.save(client);
			userRepository.save(user);
			roleRepository.save(role);
		*/
		System.err.println("Dummy Data Inserted : ");
		object.setMessage("Dummy Data Inserted : ");
		return object;
	}
}
