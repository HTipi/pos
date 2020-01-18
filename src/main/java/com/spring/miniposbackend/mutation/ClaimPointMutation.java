package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.customer.ClaimPoint;
import com.spring.miniposbackend.service.customer.ClaimPointService;
import org.springframework.stereotype.Component;

@Component
public class ClaimPointMutation implements GraphQLMutationResolver {

    private final ClaimPointService claimPointService;


    public ClaimPointMutation(ClaimPointService claimPointService) {
        this.claimPointService = claimPointService;
    }

    public ClaimPoint createClaimPoint(ClaimPoint data, int point_id){
        return this.claimPointService.create(data, point_id);
    }

    public ClaimPoint updateClaimPoint(ClaimPoint data, int point_id){
        return this.claimPointService.update(data, point_id);
    }
}
