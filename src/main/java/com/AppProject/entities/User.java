package com.AppProject.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "user")
public class User extends AbstractAuditingEntity implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	

	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	private Long id;
	private String username;
	private String password;
	private Set<Role> roles;
//	private Set<Client> clients;
	private String name;
	private String email;
	private String fcm;
	private String mobile;
	private String macid;
	private int status=0;//1 : need to change password,0-nothing
	private Long client_id;
//	private Client userClient;
	@Transient
	private List<String> rolesList;
	
	
	@LastModifiedDate
	@Column(name = "last_logged_date")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date loggedIn;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public String getUsername() {
		return username;
	}

	@Column(insertable = true, updatable = false, unique = true)
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "map_user_role",
	joinColumns = {@JoinColumn(name = "user_id"/*, referencedColumnName = "id"*/)},
	inverseJoinColumns = {@JoinColumn(name = "role_id"/*, referencedColumnName = "id"*/)})
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Transient
	public List<String> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<String> rolesList) {
		this.rolesList = rolesList;
	}
	
	
	
	
	/*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")*/
//	@ManyToOne(targetEntity=Client.class)
//	@JoinColumn(name = "client_id")
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinTable(name = "map_user_client",
//	joinColumns = {@JoinColumn(name = "user_id"/*, referencedColumnName = "id"*/)},
//	inverseJoinColumns = {@JoinColumn(name = "client_id"/*, referencedColumnName = "id"*/)})
//	public Set<Client> getClients() {
//		return clients;
//	}
//
//	public void setClients(Set<Client> clients) {
//		this.clients = clients;
//	}
/*	@ManyToOne
	@JoinColumn(name = "client_id")
	public Client getUserClient() {
		return userClient;
	}

	public void setUserClient(Client userClient) {
		this.userClient = userClient;
	}*/
	

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		if (!username.equals(user.username)) {
			return false;
		}
		return true;
	}


	@Override
	public int hashCode() {
		return username.hashCode();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFcm() {
		return fcm;
	}

	public void setFcm(String fcm) {
		this.fcm = fcm;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMacid() {
		return macid;
	}

	public void setMacid(String macid) {
		this.macid = macid;
	}

	public java.util.Date getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(java.util.Date loggedIn) {
		this.loggedIn = loggedIn;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	
}
