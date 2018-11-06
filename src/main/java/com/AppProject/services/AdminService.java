package com.AppProject.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppProject.Utils.Constants;
import com.AppProject.Utils.Utils;
import com.AppProject.entities.Client;
import com.AppProject.entities.Role;
import com.AppProject.entities.User;
import com.AppProject.models.ResponseObject;
import com.AppProject.models.RoleModel;
import com.AppProject.models.UserModel;
import com.AppProject.repositories.ClientsRepository;
import com.AppProject.repositories.RolesRepository;
import com.AppProject.repositories.UserRepository;

@Service
public class AdminService {
	

	@Autowired
	UserRepository userRepository;
	@Autowired
	RolesRepository roleRepository;

	@Autowired
	ClientsRepository clientRepository;

	@Autowired
	EmailService emailService;

	
	public Object populateUsers() {
		
		List<User> allUsers=userRepository.findAll();
		List<UserModel> users = new ArrayList<>();
		for(User user : allUsers) {
			UserModel umod=new UserModel();
			umod.setEmail(user.getEmail());
			umod.setMobile(user.getMobile());
			umod.setName(user.getName());
			List<RoleModel> roleslist = new ArrayList<>();
			for(Role role:user.getRoles()) {
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
			umod.setRoleModel(roleslist);
			users.add(umod);
		}
		
		return users;
	}

	public Object addClient(Client clientData) {
		ResponseObject object =new ResponseObject();
		if(clientData==null || !check(clientData.getEmail()) || !check(clientData.getName()) || !check(clientData.getMobile())) {
			object.setStatus(Constants.CODE_ERROR);
			object.setMessage("Required fields are missing");
			return object;
		}
		
		clientData.setCreatedAt(new Date());
		clientData.setActive(1);
		clientRepository.save(clientData);
		object.setStatus(Constants.CODE_SUCCESS);
		object.setMessage("Client Created Successfully");
		
		return object;
	}

	public Object addUser(UserModel usermodel) {
		ResponseObject object = new ResponseObject();
		if(usermodel==null) {
			object.setStatus(Constants.CODE_ERROR);
			object.setMessage("Required fields are missing");
			return object;
		}
		
		User user=userRepository.findByUsername(usermodel.getMobile());
		if(user!=null) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("User aleady exists.");
			return object;
		}
		
		if(usermodel.getRoles()==null || usermodel.getRoles().size()==0) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("Please select the Client to the User");
			return object;
		}
		
		if(StringUtils.isEmpty(usermodel.getClientId())) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("Please select the Client to the User");
			return object;
		}

		if(!Constants.isEmail(usermodel.getEmail())) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("Please enter a Valid Email Address");
			return object;
		}
		if(!Constants.isMobile(usermodel.getMobile())) {
			object.setStatus(Constants.CODE_INFO);
			object.setMessage("Please enter a Valid Mobile Number");
			return object;
		}
		

		String randamPassword = Utils.getPassword(6,true,false);
		
		user = new User();
		user.setEmail(usermodel.getEmail());
		user.setMobile(usermodel.getMobile());
		user.setName(usermodel.getName());
		user.setUsername(usermodel.getMobile());
		user.setCreatedAt(new Date());
		user.setPassword(User.PASSWORD_ENCODER.encode(randamPassword));
		user.setRoles(roleRepository.getRolesDetails(usermodel.getRoles()));
//		user=userRepository.save(user);
		
        Set<User> userClients = new HashSet<User>();
        Set<Client> ClientUser = new HashSet<Client>();
        Client client = clientRepository.getUserClient(Long.valueOf(usermodel.getClientId()));
        if(client==null) {
        	object.setStatus(Constants.CODE_ERROR);
			object.setMessage("Client doesn't Exist, Please check with Administrator Developer");
			return object;
        }
		 ClientUser.add(client);
	        user.setUserClient(client);
	        user= userRepository.save(user);
	        userClients.add(user);
	        client.setUsers(userClients);
	        clientRepository.save(client);
		
		UserModel model = new UserModel();
		model.setEmail(user.getEmail());
		model.setName(user.getName());
		model.setPassword(randamPassword);
		model.setUsername(user.getUsername());
		
		try {
			emailService.sendWelcomeEmail(model);
			object.setStatus(Constants.CODE_SUCCESS);
			object.setMessage("A Mail was sent to "+model.getName()+" registered email address with login credentails");
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
	
	private boolean check(String data) {
		if(StringUtils.isNotEmpty(data)) {
			return true;
		}
		return false;
	}

	

}
