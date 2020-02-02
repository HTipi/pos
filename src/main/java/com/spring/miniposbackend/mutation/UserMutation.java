package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.service.admin.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class UserMutation implements GraphQLMutationResolver {
    @Autowired
    private UserService userService;

    public User createUser(User user) {
        return this.userService.create(user);
    }

}
