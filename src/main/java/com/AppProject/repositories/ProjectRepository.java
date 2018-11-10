package com.AppProject.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.AppProject.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, String>{


	@Query("SELECT c FROM Project c WHERE c.id=:id")
	Optional<Project> findById(@Param("id")Long id);
	
	@Query("SELECT CONCAT(c.id,' , ',c.name) FROM Project c WHERE c.active=1")
	List<String> getAll();
}
