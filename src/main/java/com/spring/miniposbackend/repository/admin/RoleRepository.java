package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.admin.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    @Query(value = "select r from Role r where r.enable=?1")
    List<Role> findAll(boolean enable);

}
