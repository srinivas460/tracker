package com.AppProject.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.AppProject.entities.Role;

public interface RolesRepository extends JpaRepository<Role, String>{

	@Query("SELECT c FROM Role c WHERE c.name=:role")
	Set<Role> getRoles(@Param("role") String role);

	@Query("SELECT c.fullName FROM Role c WHERE c.active=1")
	List<String> getAllRole();
	
	@Query("SELECT c FROM Role c WHERE (c.fullName IN :roles) AND c.active=1")
	Set<Role> getRolesDetails(@Param("roles") List<String> roles);
}
