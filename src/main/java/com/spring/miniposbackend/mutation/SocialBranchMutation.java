//package com.spring.miniposbackend.mutation;
//
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.spring.miniposbackend.models.SocialBranch;
//import com.spring.miniposbackend.services.SocialBranchService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class SocialBranchMutation implements GraphQLMutationResolver {
//
//    private final SocialBranchService socialBranchService;
//
//    public SocialBranchMutation(SocialBranchService socialBranchService) {
//        this.socialBranchService = socialBranchService;
//    }
//
//    public SocialBranch createSocialBranch(SocialBranch data, int soc_id, int brn_id, int cop_id) {
//        return this.socialBranchService.createSocialBranch(data, soc_id, brn_id, cop_id);
//    }
//
//    public SocialBranch updateSocialBranch(SocialBranch data, int soc_brn_id, int soc_id, int brn_id, int cop_id) {
//        data.setId(soc_brn_id);
//        return this.socialBranchService.createSocialBranch(data, soc_id, brn_id, cop_id);
//    }
//}
