package com.spring.miniposbackend.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
