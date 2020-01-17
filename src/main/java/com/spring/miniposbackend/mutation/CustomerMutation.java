package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.customer.Customer;
import com.spring.miniposbackend.service.customer.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class CustomerMutation implements GraphQLMutationResolver {

    private final CustomerService customerService;

    public CustomerMutation(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer createCustomer(Customer customer) {
        return this.customerService.create(customer);
    }

    public Customer updateCustomer(Long customerId, Customer customer) {
        return this.customerService.update(customerId, customer);
    }
}
