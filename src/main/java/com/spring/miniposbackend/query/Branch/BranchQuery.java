//package com.spring.miniposbackend.query.Branch;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.spring.miniposbackend.models.Branch;
//import com.spring.miniposbackend.services.BranchService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class BranchQuery implements GraphQLQueryResolver {
//
//    private final BranchService branchService;
//
//    public BranchQuery(BranchService branchService) {
//        this.branchService = branchService;
//    }
//
//    public Branch getBranch(int id) {
//        return this.branchService.getBranch(id);
//    }
//}
