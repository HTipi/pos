//package com.spring.miniposbackend.query.CustomerPoint;
//
//import com.coxautodev.graphql.tools.GraphQLResolver;
//import com.spring.miniposbackend.models.Customer;
//import com.spring.miniposbackend.models.CustomerPoint;
//import com.spring.miniposbackend.services.CustomerService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class CustomerPointResolver implements GraphQLResolver<CustomerPoint> {
//
//    @Autowired
//    private CustomerService customerService;
//
//    public Optional<Customer> customer(CustomerPoint customerPoint){
//        return this.customerService.getCustomer(customerPoint.getCustomer().getId());
//    }
//}
