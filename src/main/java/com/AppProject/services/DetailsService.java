package com.AppProject.services;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.AppProject.entities.Role;
import com.AppProject.entities.User;
import com.AppProject.repositories.UserRepository;


@Service
public class DetailsService implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(DetailsService.class);
	@Autowired
	private UserRepository users;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = users.findByUsername(username);
		if (user == null) {
			System.err.println(username + " was not found");
			throw new UsernameNotFoundException(username + " was not found");
		}else {
			if(user.getActive()!=1) {
				throw new UsernameNotFoundException(username + " was not found");
			}
			System.err.println(username + " was found");
		}
		return new org.springframework.security.core.userdetails.User(user.getMobile(), user.getPassword(),(getGrandedAuthorities(user)));
	}

	private Collection<GrantedAuthority> getGrandedAuthorities(User user) {
		Collection<GrantedAuthority> grandedAuths=new ArrayList<>();
		if(user!=null) {
			for(Role roles : user.getRoles()) {
				logger.debug(""+roles.getName());
				grandedAuths.add(new SimpleGrantedAuthority("ROLE_"+roles.getName()));
			}
		}
		return grandedAuths;
	}
}
