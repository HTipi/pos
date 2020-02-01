package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.admin.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
