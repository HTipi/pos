package com.spring.miniposbackend.query.UserRole;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.spring.miniposbackend.model.admin.Role;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.service.admin.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserRoleQuery implements GraphQLQueryResolver {

    @Autowired
    private UserRoleService userRoleService;


    public UserRole userRole(Long userRoleId) {
        return this.userRoleService.show(userRoleId);
    }

}
