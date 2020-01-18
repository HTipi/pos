package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.customer.CustomerPoint;
import com.spring.miniposbackend.service.customer.CustomerPointService;
import org.springframework.stereotype.Component;

@Component
public class CustomerPointMutation implements GraphQLMutationResolver {

    private final CustomerPointService customerPointService;

    public CustomerPointMutation(CustomerPointService customerPointService) {
        this.customerPointService = customerPointService;
    }

    public CustomerPoint createCustomerPoint(CustomerPoint data, Long cust_id) {
        return this.customerPointService.create(data, cust_id);
    }

    public CustomerPoint updateCustomerPoint(CustomerPoint data, Long cust_id) {
        return this.customerPointService.update(data, cust_id);
    }
}
