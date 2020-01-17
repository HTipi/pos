package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.service.admin.BranchService;


import org.springframework.stereotype.Component;

@Component
public class BranchMutation implements GraphQLMutationResolver {

    private final BranchService branchService;

    public BranchMutation(BranchService branchService) {
        this.branchService = branchService;
    }

    public Branch createBranch(Branch data, int cop_id) {
        return this.branchService.create(data, cop_id);
    }

    public Branch updateBranch(Branch data, int cop_id) {
        return this.branchService.update(data, cop_id);
    }

    public Branch showBranch(int id) {
        return this.branchService.show(id);
    }

    public Branch enableBranch(int id) {
        return this.branchService.enable(id);
    }

    public Branch disableBranch(int id) {
        return this.branchService.disable(id);
    }

}
