package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.admin.BranchUser;
import com.spring.miniposbackend.service.admin.BranchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchUserMutation implements GraphQLMutationResolver {

    @Autowired
    private BranchUserService branchUserService;

    public BranchUser createBranchUser(Integer branchId, Integer userId, BranchUser branchUserRequest) {
        return this.branchUserService.create(branchId, userId, branchUserRequest);
    }

    public BranchUser updateBranchUser(Integer branchId, Integer userId, BranchUser branchUserRequest) {
        return this.branchUserService.create(branchId, userId, branchUserRequest);
    }

}
