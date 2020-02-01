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

    public Branch createBranch(int corporateId, int addressId, Branch data) {
        return this.branchService.create(corporateId, addressId, data);
    }

    public Branch updateBranch(int corporateId, int addressId, Branch data) {
        return this.branchService.update(corporateId, addressId, data);
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
