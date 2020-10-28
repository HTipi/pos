package com.spring.miniposbackend.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.security.ClientApplication;

@Repository
public interface ClientApplicationRepository extends JpaRepository<ClientApplication, Integer>{
	
	Optional<ClientApplication> findFirstByName(String name);
}
