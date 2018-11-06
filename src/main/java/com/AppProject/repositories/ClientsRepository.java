package com.AppProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.AppProject.entities.Client;

public interface ClientsRepository extends JpaRepository<Client, String>{
	@Query("SELECT c FROM Client c WHERE c.id=:id AND c.active=1")
	Client getUserClient(@Param("id") Long id);
}
