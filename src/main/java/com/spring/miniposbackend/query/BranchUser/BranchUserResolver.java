package com.spring.miniposbackend.query.BranchUser;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchUser;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.service.admin.BranchService;
import com.spring.miniposbackend.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchUserResolver implements GraphQLResolver<BranchUser> {

    @Autowired
    private BranchService branchService;
    @Autowired
    private UserService userService;

    public Branch branch(BranchUser branchUser) {
        return this.branchService.show(branchUser.getBranch().getId());
    }

    public User user(BranchUser branchUser) {
        return this.userService.show(branchUser.getUser().getId());
    }
}
