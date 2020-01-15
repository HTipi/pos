//package com.spring.miniposbackend.query.ClaimPoint;
//
//import com.coxautodev.graphql.tools.GraphQLResolver;
//import com.spring.miniposbackend.models.ClaimPoint;
//import com.spring.miniposbackend.models.CustomerPoint;
//import com.spring.miniposbackend.services.CustomerPointService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class ClaimPointResolver implements GraphQLResolver<ClaimPoint> {
//
//    @Autowired
//    private CustomerPointService customerPointService;
//
//    public Optional<CustomerPoint> customerPoint(ClaimPoint claimPoint){
//        return this.customerPointService.getCustomerPoint(claimPoint.getCustomerPoint().getId());
//    }
//}
