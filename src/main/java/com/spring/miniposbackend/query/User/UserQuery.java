//package com.spring.miniposbackend.query.User;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.spring.miniposbackend.models.User;
//import com.spring.miniposbackend.services.UserService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
//@Component
//public class UserQuery implements GraphQLQueryResolver {
//
//    @Autowired
//    private UserService userService;
//
//    public List<User> Users()   {
//
//        return this.userService.getAllUsers();
//
//    }
//    public User User(int user_id){
//        return  this.userService.getUser(user_id);
//    }
//
//
//}
