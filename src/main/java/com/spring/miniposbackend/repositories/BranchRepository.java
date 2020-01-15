package com.spring.miniposbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.models.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Integer> {

}
