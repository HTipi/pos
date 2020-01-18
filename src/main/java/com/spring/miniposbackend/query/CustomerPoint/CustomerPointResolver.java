package com.spring.miniposbackend.query.CustomerPoint;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.spring.miniposbackend.model.customer.Customer;
import com.spring.miniposbackend.model.customer.CustomerPoint;
import com.spring.miniposbackend.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerPointResolver implements GraphQLResolver<CustomerPoint> {

    @Autowired
    private CustomerService customerService;

    public Customer customer(CustomerPoint customerPoint){
        return this.customerService.show(customerPoint.getCustomer().getId());
    }
}
