package AppProject.AppProject;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.AppProject.entities.Client;
import com.AppProject.entities.Role;
import com.AppProject.entities.User;
import com.AppProject.repositories.ClientsRepository;
import com.AppProject.repositories.RolesRepository;
import com.AppProject.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AppProjectApplicationTests extends AbstractServiceForTesting{
	@Autowired
	UserRepository userRepository;
	@Autowired
	RolesRepository roleRepository;
	@Autowired
	ClientsRepository clientRepository;
	@Test
	public void contextLoads() {
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


//		client=clientRepository.save(client);
//		ClientUser.add(client);
//		user.setClients(ClientUser);
//		userClients.add(user);
//		client.setUser(userClients);

		client=clientRepository.save(client);
		userRepository.save(user);
		roleRepository.save(role);
		
		
		ClientUser = new HashSet<Client>();
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


//			client=clientRepository.save(client);
//			ClientUser.add(client);
//			user.setClients(ClientUser);
//			userClients.add(user);
//			client.setUser(userClients);

			userRepository.save(user);
			roleRepository.save(role);
		
		System.err.println("Dummy Data Inserted : ");
		
	}

}
