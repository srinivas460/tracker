package com.AppProject;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.AppProject.entities.Role;
import com.AppProject.entities.User;
import com.AppProject.repositories.RolesRepository;
import com.AppProject.repositories.UserRepository;
@SpringBootApplication
@EnableAutoConfiguration
//@EnableJpaAuditing
//@PropertySource(value = { "classpath:application.properties" })
//@EntityScan( basePackages = {"com.AppProject.entities"} )
////@EnableAutoConfiguration
@ComponentScan(basePackages = "com.AppProject.*")
public class AppProjectApplication extends SpringBootServletInitializer{
	@Autowired
	private Environment env;
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppProjectApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(AppProjectApplication.class, args);
	}
	
	
//	@Bean
	 public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(
	          env.getProperty("spring.jpa.properties.hibernate.dialect"));
	        dataSource.setUrl(env.getProperty("spring.datasource.url"));
	        dataSource.setUsername(env.getProperty("spring.datasource.username"));
	        dataSource.setPassword(env.getProperty("spring.datasource.password"));

	        return dataSource;
	    }
	 
	 
	 
	protected CommandLineRunner initRoles(final UserRepository userRepository,final RolesRepository rolesRepository) {

		return args -> {
			boolean load = env.getProperty("load.app.default.info")!=null ? Boolean.valueOf(env.getProperty("load.app.default.info")):false;

			System.err.println("Dummy Data  : "+load);
			if(load) {
				Role role = new Role();
				role.setActive(1);
				role.setName("ROLE_DEV");
				role.setCanComment(1);
				role.setIsDev(1);
				rolesRepository.save(role);

				role = new Role();
				role.setActive(1);
				role.setName("ROLE_ADMIN");
				role.setCanComment(1);
				role.setIsAdmin(1);
				role.setCanAssignuser(1);
				role.setCanComment(1);
				role.setCancreateTicket(1);
				role.setCanCreateuser(1);
				rolesRepository.save(role);

				User user = new User();
				user.setEmail("aaa@gmail.com");
				user.setMobile("9676462929");
				user.setName("Varma");
				user.setPassword("dummy");
				user.setRoles(rolesRepository.getRoles("ADMIN"));
				userRepository.save(user);
				
				System.err.println("Dummy Data Inserted : ");
			}

		};
	}
}
