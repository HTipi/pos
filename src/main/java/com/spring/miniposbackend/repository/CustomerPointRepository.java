package com.spring.miniposbackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.customer.CustomerPoint;

@Repository
public interface CustomerPointRepository extends JpaRepository<CustomerPoint, Integer> {

}
