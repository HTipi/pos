package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.sale.SaleTemporary;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Integer> {

}
