package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.admin.Social;
import com.spring.miniposbackend.service.admin.SocialService;
import org.springframework.stereotype.Component;

@Component
public class SocialMutation implements GraphQLMutationResolver {

    private final SocialService socialService;

    public SocialMutation(SocialService socialService) {
        this.socialService = socialService;
    }

    public Social createSocial(Social data) {
        return this.socialService.create(data);
    }

    public Social updateSocial(Social data, int social_id) {
        return this.socialService.update(social_id, data);
    }

}
