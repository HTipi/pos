//package com.spring.miniposbackend.mutation;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.spring.miniposbackend.models.Social;
//import com.spring.miniposbackend.services.SocialService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class SocialMutation implements GraphQLMutationResolver {
//
//    private final SocialService socialService;
//
//    public SocialMutation(SocialService socialService) {
//        this.socialService = socialService;
//    }
//
//    public Social createSocial(Social data) {
//        return this.socialService.createSocial(data);
//    }
//
//    public Social updateSocial(Social data, int social_id) {
//        return this.socialService.updateSocial(data, social_id);
//    }
//
//}
