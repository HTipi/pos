package com.spring.miniposbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
