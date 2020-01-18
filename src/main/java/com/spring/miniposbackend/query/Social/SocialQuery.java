package com.spring.miniposbackend.query.Social;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.spring.miniposbackend.model.admin.Social;
import com.spring.miniposbackend.service.admin.SocialService;
import org.springframework.stereotype.Component;

@Component
public class SocialQuery implements GraphQLQueryResolver {


    private final SocialService socialService;


    public SocialQuery(SocialService socialService) {
        this.socialService = socialService;
    }

    public Social getSocial(int id) {
        return this.socialService.show(id);
    }
}
