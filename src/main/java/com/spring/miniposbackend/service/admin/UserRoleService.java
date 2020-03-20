//package com.spring.miniposbackend.service.admin;
//
//import com.spring.miniposbackend.exception.ResourceNotFoundException;
//import com.spring.miniposbackend.model.admin.UserRole;
//import com.spring.miniposbackend.repository.admin.RoleRepository;
//import com.spring.miniposbackend.repository.admin.UserRepository;
//import com.spring.miniposbackend.repository.admin.UserRoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class UserRoleService {
//
//    @Autowired
//    private UserRoleRepository userRoleRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Transactional(readOnly = true)
//    public List<UserRole> shows() {
//        return this.userRoleRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public List<UserRole> showAllActive() {
//        return this.userRoleRepository.findAllActive();
//    }
//
//    @Transactional(readOnly = true)
//    public List<UserRole> showAllActiveByUserId(Long userId){
//        return this.userRoleRepository.findAllByUserId(userId);
//    }
//
//    @Transactional(readOnly = true)
//    public UserRole show(Long userRoleId) {
//        return this.userRoleRepository.findById(userRoleId)
//                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + userRoleId));
//    }
//
//    public UserRole create(Integer userId, Integer roleId, UserRole userRole) {
//
//        if (!this.userRepository.existsById(userId))
//            throw new ResourceNotFoundException("The User is not found!" + userId);
//
//
//        if (!this.roleRepository.existsById(roleId))
//            throw new ResourceNotFoundException("The User is not found!" + userId);
//
//        if (!this.userRepository.existsById(userId))
//            throw new ResourceNotFoundException("The User is not found!" + userId);
//
//        return this.userRepository.findById(userId)
//                .map(user -> {
//
//                    userRole.setUser(user);
//
//                    return this.roleRepository.findById(roleId)
//                            .map(role -> {
//
//                                userRole.setRole(role);
//                                return this.userRoleRepository.save(userRole);
//
//                            }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
//
//                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
//
//    }
//
//    public UserRole update(Long userRoleId, Integer userId, Integer roleId, UserRole userRole) {
//
//        if (!this.userRepository.existsById(userId))
//            throw new ResourceNotFoundException("The User is not found!" + userId);
//
//
//        if (!this.roleRepository.existsById(roleId))
//            throw new ResourceNotFoundException("The Role is not found!" + userId);
//
//
//        if (!this.userRoleRepository.existsById(userRoleId))
//            throw new ResourceNotFoundException("The User Role is not found!" + userId);
//
//        return this.userRoleRepository.findById(userRoleId)
//                .map(userRoleData -> {
//
//                    return this.userRepository.findById(userId)
//                            .map(user -> {
//
//                                return this.roleRepository.findById(roleId)
//                                        .map(role -> {
//                                            userRoleData.setUser(user);
//                                            userRoleData.setRole(role);
//                                            return this.userRoleRepository.save(userRoleData);
//
//                                        }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
//
//                            }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
//
//                }).orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + userRoleId));
//
//    }
//
//    public UserRole updateStatus(Long userRoleId, Boolean status) {
//        return this.userRoleRepository.findById(userRoleId)
//                .map(userRole -> {
//
//                    userRole.setEnable(status);
//
//                    return this.userRoleRepository.save(userRole);
//
//                }).orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + userRoleId));
//    }
//
//    public UserRole enable(Long userRoleId) {
//        return this.userRoleRepository.findById(userRoleId)
//                .map(userRole -> {
//                    userRole.setEnable(true);
//                    return this.userRoleRepository.save(userRole);
//                }).orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + userRoleId));
//    }
//
//    public UserRole disable(Long userRoleId) {
//        return this.userRoleRepository.findById(userRoleId)
//                .map(userRole -> {
//                    userRole.setEnable(false);
//                    return this.userRoleRepository.save(userRole);
//                }).orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + userRoleId));
//    }
//
//}
