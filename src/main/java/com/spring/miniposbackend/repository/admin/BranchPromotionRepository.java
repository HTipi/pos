package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.BranchPromotion;


public interface BranchPromotionRepository extends JpaRepository<BranchPromotion, Integer>{
	
	
	@Query(value="select bp from BranchPromotion bp where bp.branch.id=?1")
	List<BranchPromotion> findByBranchId(Integer branchId);
}
