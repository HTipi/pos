package com.spring.miniposbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.models.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
