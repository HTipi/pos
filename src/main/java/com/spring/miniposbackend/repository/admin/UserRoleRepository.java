package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.model.admin.UserRoleIdentity;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,UserRoleIdentity> {
	
    List<UserRole> findByUserRoleIdentityUserId(Integer userId);
    
    @Query(value = "select ur from UserRole ur where ur.userRoleIdentity.user.id = ?1 and ur.userRoleIdentity.role.enable = ?2")
    List<UserRole> findByUserRoleIdentityUserId(Integer userId, boolean enable);
}
