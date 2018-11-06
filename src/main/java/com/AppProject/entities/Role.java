package com.AppProject.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role extends AbstractAuditingEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String fullName;
	private Set<User> users;

	private Integer isAdmin=0;
	private Integer isClient=0;
	private Integer isManager=0;
	private Integer isDev=0;
	private Integer isSuperDev=0;
	private Integer canComment=0;
	private Integer canCreateuser=0;
	private Integer cancreateTicket=0;
	private Integer canAssignuser=0;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getIsSuperDev() {
		return isSuperDev;
	}

	public void setIsSuperDev(Integer isSuperDev) {
		this.isSuperDev = isSuperDev;
	}

	@ManyToMany(mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getIsClient() {
		return isClient;
	}

	public void setIsClient(Integer isClient) {
		this.isClient = isClient;
	}

	public Integer getIsManager() {
		return isManager;
	}

	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}

	public Integer getIsDev() {
		return isDev;
	}

	public void setIsDev(Integer isDev) {
		this.isDev = isDev;
	}

	public Integer getCanComment() {
		return canComment;
	}

	public void setCanComment(Integer canComment) {
		this.canComment = canComment;
	}

	public Integer getCanCreateuser() {
		return canCreateuser;
	}

	public void setCanCreateuser(Integer canCreateuser) {
		this.canCreateuser = canCreateuser;
	}

	public Integer getCancreateTicket() {
		return cancreateTicket;
	}

	public void setCancreateTicket(Integer cancreateTicket) {
		this.cancreateTicket = cancreateTicket;
	}

	public Integer getCanAssignuser() {
		return canAssignuser;
	}

	public void setCanAssignuser(Integer canAssignuser) {
		this.canAssignuser = canAssignuser;
	}
	
	

}
