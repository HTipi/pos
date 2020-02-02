package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.repository.admin.RoleRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.admin.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<UserRole> shows() {
        return this.userRoleRepository.findAll();
    }

    public UserRole show(Long userRoleId) {
        return this.userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + userRoleId));
    }

    public UserRole create(Integer userId, Integer roleId, UserRole userRole) {

        Boolean user = this.userRepository.existsById(userId);

        if (!user)
            throw new ResourceNotFoundException("The User is not found!"+ userId);

        Boolean role = this.roleRepository.existsById(roleId);

        if (!role)
            throw new ResourceNotFoundException("The User is not found!"+ userId);

        return this.userRepository.findById(userId)
                .map(userData -> {

                    return this.roleRepository.findById(roleId)
                            .map(roleData -> {

                                userRole.setUser(userData);
                                userRole.setRole(roleData);
                                return this.userRoleRepository.save(userRole);

                            }).orElseThrow(() -> new ResourceNotFoundException("Role Id not found with id " + roleId));

                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    public UserRole enable(Long userRoleId) {
        return this.userRoleRepository.findById(userRoleId)
                .map(userRole -> {
                    userRole.setEnable(true);
                    return this.userRoleRepository.save(userRole);
                }).orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + userRoleId));
    }

    public UserRole disable(Long userRoleId) {
        return this.userRoleRepository.findById(userRoleId)
                .map(userRole -> {
                    userRole.setEnable(false);
                    return this.userRoleRepository.save(userRole);
                }).orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + userRoleId));
    }

}
