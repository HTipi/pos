package com.spring.miniposbackend.repository.admin;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Integer> {

	List<Branch> findByCorporateId(Integer corporateId);
	@Query(value = "select b from Branch b where b.corporate.id = ?1 and b.enable = ?2")
	List<Branch> findByCorporateId(Integer corporateId, boolean enable);
	@Query(value = "select b from Branch b where b.id = ?1")
	List<Branch> findByBranchId(Integer branchId);
	@Query(value = "select b from Branch b where b.id = ?1 and b.enable = ?2")
	List<Branch> findByBranchId(Integer branchId, boolean enable);
	
	@Query(value="select b from Branch b where b.enable = true")
	List<Branch> findByActiveBranch();
	
}
