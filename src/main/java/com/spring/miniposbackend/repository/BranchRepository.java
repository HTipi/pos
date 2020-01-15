package com.spring.miniposbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Integer> {

}
