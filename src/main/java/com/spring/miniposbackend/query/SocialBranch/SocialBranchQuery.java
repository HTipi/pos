//package com.spring.miniposbackend.query.SocialBranch;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.spring.miniposbackend.model.admin.SocialBranch;
//import com.spring.miniposbackend.service.admin.SocialBranchService;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SocialBranchQuery implements GraphQLQueryResolver {
//
//    private final SocialBranchSer socialBranchService;
//
//    public SocialBranchQuery(SocialBranchService socialBranchService) {
//        this.socialBranchService = socialBranchService;
//    }
//
//    public SocialBranch getSocialBranch(int id) {
//        return this.socialBranchService.show(id);
//    }
//
//}
