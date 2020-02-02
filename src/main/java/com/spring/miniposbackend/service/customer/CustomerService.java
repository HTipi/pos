package com.spring.miniposbackend.service.customer;

import com.spring.miniposbackend.model.customer.Customer;
import com.spring.miniposbackend.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<Customer> shows(final int count) {
        return this.customerRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Customer> showAll() {
        return this.customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer show(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + customerId));
    }

    public Customer enable(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setEnable(true);
                    return this.customerRepository.save(customer);
                }).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    public Customer disable(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setEnable(false);
                    return this.customerRepository.save(customer);
                }).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    public Customer create(Customer customer) {
        String password = customer.getPassword();
        customer.setPassword(password);
        return customerRepository.save(customer);
    }

    public Customer update(Long customerId, Customer requestCustomer) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    customer.setFirstName(requestCustomer.getFirstName());
                    customer.setLastName(requestCustomer.getLastName());
                    return customerRepository.save(customer);
                }).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
    }

}
