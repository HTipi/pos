//package com.spring.miniposbackend.mutation;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.spring.miniposbackend.models.Customer;
//import com.spring.miniposbackend.services.CustomerService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomerMutation implements GraphQLMutationResolver {
//
//    private final CustomerService customerService;
//
//    public CustomerMutation(CustomerService customerService){
//        this.customerService = customerService;
//    }
//
//    public Customer createCustomer(Customer customer){
//        return this.customerService.createCustomer(customer);
//    }
//
//    public Customer updateCustomer(Customer customer){
//        return this.customerService.updateCustomer(customer);
//    }
//}
