package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Role;
import com.spring.miniposbackend.repository.admin.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role show(Integer roleId) {
        return this.roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
    }

    public List<Role> shows() {
        return this.roleRepository.findAll();
    }

    public List<Role> showAllActive() {
        return this.roleRepository.findAllActive();
    }

    public Role create(Role role) {
        return this.roleRepository.save(role);
    }

    public Role update(Integer roleId, Role role) {
        return this.roleRepository.findById(roleId)
                .map(roleData -> {

                    roleData.setName(role.getName());
                    roleData.setNameKh(role.getNameKh());

                    return this.roleRepository.save(roleData);

                }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
    }

    public Role updateStatus(Integer roleId, Boolean status) {
        return this.roleRepository.findById(roleId)
                .map(role -> {
                    role.setEnable(status);
                    return this.roleRepository.save(role);
                }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
    }

}
