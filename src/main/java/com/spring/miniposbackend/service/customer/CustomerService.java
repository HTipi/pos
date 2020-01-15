package com.spring.miniposbackend.service.customer;
//package com.spring.miniposbackend.services;
//
//import org.mindrot.jbcrypt.BCrypt;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import com.spring.miniposbackend.exception.ResourceNotFoundException;
//import com.spring.miniposbackend.models.Customer;
//import com.spring.miniposbackend.repositories.CustomerRepository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomerService {
//
//	@Autowired
//    private CustomerRepository customerRepository;
//
//    @Transactional(readOnly = true)
//    public List<Customer> shows(final int count) {
//        return this.customerRepository.findAll().stream().limit(count).collect(Collectors.toList());
//    }
//
//    @Transactional(readOnly = true)
//    public Customer show(Long customerId) {
//    	return customerRepository.findById(customerId)
//        		.orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + customerId));
//    }
//
//    public Customer create(Customer customer) {
//        String password = BCrypt.hashpw(customer.getPassword(),BCrypt.gensalt());
//        customer.setPassword(password);
//       return customerRepository.save(customer);
//    }
//
//    public Customer update(Long customerId,Customer requestCustomer) {
//    	 return  customerRepository.findById(customerId)
// 				.map(customer -> {
// 					customer.setFirstName(requestCustomer.getFirstName());
// 					customer.setLastName(requestCustomer.getLastName());
// 					return customerRepository.save(customer);
// 				}).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
//    }
//
//}
