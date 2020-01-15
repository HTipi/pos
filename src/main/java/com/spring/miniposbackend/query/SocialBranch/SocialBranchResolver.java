//package com.spring.miniposbackend.query.SocialBranch;
//
//import com.coxautodev.graphql.tools.GraphQLResolver;
//import com.spring.miniposbackend.models.Branch;
//import com.spring.miniposbackend.models.Corporate;
//import com.spring.miniposbackend.models.Social;
//import com.spring.miniposbackend.models.SocialBranch;
//import com.spring.miniposbackend.services.BranchService;
//import com.spring.miniposbackend.services.CorporateService;
//import com.spring.miniposbackend.services.SocialService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SocialBranchResolver implements GraphQLResolver<SocialBranch> {
//
//    @Autowired
//    private SocialService socialService;
//
//    @Autowired
//    private BranchService branchService;
//
//    @Autowired
//    private CorporateService corporateService;
//
//
//    public Social social(SocialBranch socialBranch) {
//        return this.socialService.getSocial(socialBranch.getSocial().getId());
//    }
//
//    public Branch branch(SocialBranch socialBranch) {
//        return this.branchService.getBranch(socialBranch.getBranch().getId());
//    }
//
//    public Corporate corporate(SocialBranch socialBranch) {
//        return this.corporateService.getCorporate(socialBranch.getCorporate().getId());
//    }
//
//}
