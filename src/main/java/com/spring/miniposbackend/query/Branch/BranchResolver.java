//package com.spring.miniposbackend.query.Branch;
//
//import com.coxautodev.graphql.tools.GraphQLResolver;
//import com.spring.miniposbackend.models.Branch;
//import com.spring.miniposbackend.models.Corporate;
//import com.spring.miniposbackend.services.CorporateService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BranchResolver implements GraphQLResolver<Branch> {
//
//    @Autowired
//    private CorporateService corporateService;
//
//    public Corporate corporate(Branch branch) {
//        return this.corporateService.getCorporate(branch.getCorporate().getId());
//    }
//
//}
