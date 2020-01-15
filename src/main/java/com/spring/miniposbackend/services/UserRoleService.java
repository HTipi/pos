//package com.spring.miniposbackend.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.spring.miniposbackend.models.UserRole;
//import com.spring.miniposbackend.repositories.UserRoleRepository;
//
//import java.util.List;
//
//@Service
//public class UserRoleService {
//
//    @Autowired
//    private UserRoleRepository userRoleRepository;
//
//    public UserRole createRole(UserRole userRole) {
//
//        return  this.userRoleRepository.save(userRole);
//
//    }
//    public List<UserRole> getAllRoles(){
//        return this.userRoleRepository.findAll();
//    }
//
//}
