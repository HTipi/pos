package com.spring.miniposbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
