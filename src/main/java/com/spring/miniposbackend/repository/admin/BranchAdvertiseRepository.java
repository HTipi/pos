package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.BranchAdvertise;

@Repository
public interface BranchAdvertiseRepository extends JpaRepository<BranchAdvertise, Integer> {

	@Query(value = "select ba from BranchAdvertise ba where ba.id=?1 and ba.branch.id=?2")
	BranchAdvertise findByIdandBranchId(Long id, Integer branchid);

	@Query(value = "select ba from BranchAdvertise ba where ba.branch.id=?1 and ba.enable=?2")
	List<BranchAdvertise> findByBranchIdandenable(Integer branchid, boolean enable);

	@Query(value = "select ba from BranchAdvertise ba where ba.branch.id=?1")
	List<BranchAdvertise> findByBranchId(Integer branchid);

	@Query(value = "select ba.sortOrder from BranchAdvertise ba where ba.sortOrder =?1")
	Integer findBysortOrder(Integer shortOrder);

	@Query(value = "select ba.branch.id from BranchAdvertise ba where ba.branch.id=?1")
	Long findByOnlyBranchId(int branchid);

}