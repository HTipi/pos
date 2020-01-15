package com.spring.miniposbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
