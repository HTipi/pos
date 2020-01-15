//package com.spring.miniposbackend.query.CustomerPoint;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.spring.miniposbackend.models.CustomerPoint;
//import com.spring.miniposbackend.services.CustomerPointService;
//
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class CustomerPointQuery implements GraphQLQueryResolver {
//
//    private final CustomerPointService customerPointService;
//
//
//    public CustomerPointQuery(CustomerPointService customerPointService) {
//        this.customerPointService = customerPointService;
//    }
//
//    public Optional<CustomerPoint> getCustomerPoint(final int id){
//       return this.customerPointService.getCustomerPoint(id);
//    }
//}
