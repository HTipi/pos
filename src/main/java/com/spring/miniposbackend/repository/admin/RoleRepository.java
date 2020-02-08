package com.spring.miniposbackend.repository.admin;

import com.spring.miniposbackend.model.admin.Social;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.admin.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    @Query(value = "select r from Role r where r.enable=true")
    List<Role> findAllActive();

}
