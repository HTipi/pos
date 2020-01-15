//package com.spring.miniposbackend.query.SocialBranch;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.spring.miniposbackend.models.SocialBranch;
//import com.spring.miniposbackend.services.SocialBranchService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class SocialBranchQuery implements GraphQLQueryResolver {
//
//    private final SocialBranchService socialBranchService;
//
//    public SocialBranchQuery(SocialBranchService socialBranchService) {
//        this.socialBranchService = socialBranchService;
//    }
//
//    public SocialBranch getSocialBranch(int id) {
//        return this.socialBranchService.getSocialBranch(id);
//    }
//
//}
