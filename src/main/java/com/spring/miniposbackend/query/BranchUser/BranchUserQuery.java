package com.spring.miniposbackend.query.BranchUser;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.spring.miniposbackend.model.admin.BranchUser;
import com.spring.miniposbackend.service.admin.BranchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchUserQuery implements GraphQLQueryResolver {

    @Autowired
    private BranchUserService branchUserService;

    public BranchUser branchUser(Long branchUserId) {
        return this.branchUserService.show(branchUserId);
    }
}
