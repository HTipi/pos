//package com.spring.miniposbackend.query.BranchLogo;
//
//import com.coxautodev.graphql.tools.GraphQLResolver;
//import com.spring.miniposbackend.models.Branch;
//import com.spring.miniposbackend.models.BranchLogo;
//import com.spring.miniposbackend.models.Corporate;
//import com.spring.miniposbackend.services.BranchService;
//import com.spring.miniposbackend.services.CorporateService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BranchLogoResolver implements GraphQLResolver<BranchLogo> {
//
//    @Autowired
//    private BranchService branchService;
//
//    @Autowired
//    private CorporateService corporateService;
//
//
//    public Branch branch(BranchLogo branchLogo) {
//        return this.branchService.getBranch(branchLogo.getId());
//    }
//
//    public Corporate corporate(BranchLogo branchLogo) {
//        return this.corporateService.getCorporate(branchLogo.getId());
//    }
//
//}
