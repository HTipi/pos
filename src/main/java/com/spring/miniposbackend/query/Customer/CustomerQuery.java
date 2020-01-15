//package com.spring.miniposbackend.query.Customer;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.spring.miniposbackend.models.Customer;
//import com.spring.miniposbackend.services.CustomerService;
//
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class CustomerQuery implements GraphQLQueryResolver {
//
//    private final CustomerService customerService;
//
//    public CustomerQuery(CustomerService customerService){
//        this.customerService = customerService;
//    }
//
//    public List<Customer> getCustomers(final int count){
//        return this.customerService.getAllCustomers(count);
//    }
//
//    public Optional<Customer> getCustomer(final int id){
//        return this.customerService.getCustomer(id);
//    }
//}
