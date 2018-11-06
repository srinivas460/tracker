package com.AppProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.AppProject.entities.User;

public interface UserRepository extends JpaRepository<User, String>{
	@Query("SELECT c FROM User c WHERE c.username=:username")
	User findByUsername(@Param("username") String username);

}
