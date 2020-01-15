//package com.spring.miniposbackend.mutation;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.spring.miniposbackend.models.User;
//import com.spring.miniposbackend.services.UserService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserMutation implements GraphQLMutationResolver {
//    @Autowired
//    private UserService userService;
//
//
//
//    public User createUser(User user, int role_id) {
//
//        return this.userService.createUser(user, role_id);
//    }
//}
