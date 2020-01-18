package com.spring.miniposbackend.query.ClaimPoint;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.spring.miniposbackend.model.customer.ClaimPoint;
import com.spring.miniposbackend.service.customer.ClaimPointService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClaimPointQuery implements GraphQLQueryResolver {

    private final ClaimPointService claimPointService;

    public ClaimPointQuery(ClaimPointService claimPointService) {
        this.claimPointService = claimPointService;
    }

    public Optional<ClaimPoint> getClaimPoint(int id){
        return this.claimPointService.show(id);
    }
}
