package com.spring.miniposbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.Corporate;

@Repository
public interface CorporateRepository extends JpaRepository<Corporate,Integer> {
}
