package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.service.admin.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMutation implements GraphQLMutationResolver {
    @Autowired
    private UserRoleService userRoleService;

    public UserRole createUserRole(Integer userId, Integer roleId, UserRole userRoleRequest) {
        return this.userRoleService.create(userId, roleId, userRoleRequest);
    }

    public UserRole updateUserRole(Integer userId, Integer roleId, UserRole userRoleRequest) {
        return this.userRoleService.create(userId, roleId, userRoleRequest);
    }
}
