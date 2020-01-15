//package com.spring.miniposbackend.mutation;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.spring.miniposbackend.models.CustomerPoint;
//import com.spring.miniposbackend.services.CustomerPointService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomerPointMutation implements GraphQLMutationResolver {
//
//    private final CustomerPointService customerPointService;
//
//    public CustomerPointMutation(CustomerPointService customerPointService){
//        this.customerPointService = customerPointService;
//    }
//
//    public CustomerPoint createCustomerPoint(CustomerPoint data, int cust_id){
//        return this.customerPointService.createCustomerPoint(data, cust_id);
//    }
//
//    public CustomerPoint updateCustomerPoint(CustomerPoint data, int cust_id){
//        return this.customerPointService.updateCustomerPoint(data, cust_id);
//    }
//}
