package com.spring.miniposbackend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.models.CustomerPoint;

@Repository
public interface CustomerPointRepository extends JpaRepository<CustomerPoint, Integer> {

}
