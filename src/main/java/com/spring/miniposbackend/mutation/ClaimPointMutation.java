//package com.spring.miniposbackend.mutation;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.spring.miniposbackend.models.ClaimPoint;
//import com.spring.miniposbackend.services.ClaimPointService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class ClaimPointMutation implements GraphQLMutationResolver {
//
//    private final ClaimPointService claimPointService;
//
//
//    public ClaimPointMutation(ClaimPointService claimPointService) {
//        this.claimPointService = claimPointService;
//    }
//
//    public ClaimPoint createClaimPoint(ClaimPoint data, int point_id){
//        return this.claimPointService.createClaimPoint(data, point_id);
//    }
//
//    public ClaimPoint updateClaimPoint(ClaimPoint data, int point_id){
//        return this.claimPointService.updateClaimPoint(data, point_id);
//    }
//}
