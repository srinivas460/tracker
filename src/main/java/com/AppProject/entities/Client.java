package com.AppProject.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "of_client_info")
public class Client  extends AbstractAuditingEntity implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String descirption;
	private String mobile;
	private String email;
	private Set<User> users;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescirption() {
		return descirption;
	}

	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(mappedBy="userClient", fetch=FetchType.EAGER)
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public User addUser(User user) {
		getUsers().add(user);
		user.setUserClient(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserClient(null);
		return user;
	}
	
/*	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "map_user_clients",
	joinColumns = {@JoinColumn(name = "client_id", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})*/
//	@OneToMany(mappedBy = "clients")
//	public Set<User> getUser() {
//		return clientusers;
//	}

//	public void setUser(Set<User> user) {
//		this.clientusers = user;
//	}
	
	
}
