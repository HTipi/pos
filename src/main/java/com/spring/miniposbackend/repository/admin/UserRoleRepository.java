package com.spring.miniposbackend.repository.admin;

import com.spring.miniposbackend.model.admin.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.UserRole;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    @Query(value = "select ur from UserRole ur where ur.enable=true")
    List<UserRole> findAllActive();
    @Query(value = "select ur from UserRole ur where ur.enable=true and ur.user.id = ?1")
    List<UserRole> findAllByUserId(Long userId);
}
