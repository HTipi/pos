//package com.spring.miniposbackend.mutation;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.spring.miniposbackend.models.Branch;
//import com.spring.miniposbackend.services.BranchService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class BranchMutation implements GraphQLMutationResolver {
//
//    private final BranchService branchService;
//
//    public BranchMutation(BranchService branchService) {
//        this.branchService = branchService;
//    }
//
//    public Branch createBranch(Branch data, int cop_id) {
//        return this.branchService.createBranch(data, cop_id);
//    }
//
//    public Branch updateBranch(Branch data, int cop_id) {
//        return this.branchService.updateBranch(data, cop_id);
//    }
//}
