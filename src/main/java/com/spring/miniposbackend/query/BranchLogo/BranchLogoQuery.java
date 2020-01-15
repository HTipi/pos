//package com.spring.miniposbackend.query.BranchLogo;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.spring.miniposbackend.models.BranchLogo;
//import com.spring.miniposbackend.services.BranchLogoService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class BranchLogoQuery implements GraphQLQueryResolver {
//
//    private final BranchLogoService branchLogoService;
//
//    public BranchLogoQuery(BranchLogoService branchLogoService) {
//        this.branchLogoService = branchLogoService;
//    }
//
//    public BranchLogo getBranchLogo(int id) {
//        return this.branchLogoService.getBranchLogo(id);
//    }
//
//}
