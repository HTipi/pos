//package com.spring.miniposbackend.query.User;
//
//import com.coxautodev.graphql.tools.GraphQLResolver;
//import com.spring.miniposbackend.exception.NotFoundException;
//import com.spring.miniposbackend.models.User;
//import com.spring.miniposbackend.models.UserRole;
//import com.spring.miniposbackend.repositories.UserRoleRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserResolver implements GraphQLResolver<User> {
//
//
//    @Autowired
//    private UserRoleRepository corporateRoleRepository;
//
//
//    public UserRole role(User user) {
//
//        return this.corporateRoleRepository.findById(user.getCorporateRole().getId()).
//        orElseThrow(() -> new NotFoundException("Not Found", user.getCorporateRole().getId()));
//    }
//
//}
