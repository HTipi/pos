package com.spring.miniposbackend.query.UserRole;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.spring.miniposbackend.model.admin.Role;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.service.admin.RoleService;
import com.spring.miniposbackend.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleResolver implements GraphQLResolver<UserRole> {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    public User user(UserRole userRole){
        return this.userService.show(userRole.getUser().getId());
    }

    public Role role(UserRole userRole){
        return this.roleService.show(userRole.getRole().getId());
    }
}
