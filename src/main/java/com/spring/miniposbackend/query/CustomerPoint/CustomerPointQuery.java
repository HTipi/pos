package com.spring.miniposbackend.query.CustomerPoint;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.spring.miniposbackend.model.customer.CustomerPoint;
import com.spring.miniposbackend.service.customer.CustomerPointService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerPointQuery implements GraphQLQueryResolver {

    private final CustomerPointService customerPointService;


    public CustomerPointQuery(CustomerPointService customerPointService) {
        this.customerPointService = customerPointService;
    }

    public Optional<CustomerPoint> getCustomerPoint(final int id){
       return this.customerPointService.show(id);
    }
}
