package com.spring.miniposbackend.query.Branch;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.service.admin.BranchService;
import org.springframework.stereotype.Component;

@Component
public class BranchQuery implements GraphQLQueryResolver {

    private final BranchService branchService;

    public BranchQuery(BranchService branchService) {
        this.branchService = branchService;
    }

    public Branch getBranch(int id) {
        return this.branchService.show(id);
    }

}
