package com.spring.miniposbackend.repository.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.BranchAdvertise;


@Repository
public interface BranchAdveriseRepository extends JpaRepository<BranchAdvertise, Integer>{

	@Query(value="select ba from BranchAdvertise ba where ba.id=?1 and ba.branch.id=?2")
	BranchAdvertise findByIdandBranchId(int id,Integer branchid);
	 
	@Query(value="select ba from BranchAdvertise ba where ba.branch.id=?1 and ba.enable=?2")
	List<BranchAdvertise> findByBranchIdandEnable(Integer branchid,boolean enable);
	
	@Query(value="select ba from BranchAdvertise ba where ba.branch.id=?1 and ba.enable=true")
	List<BranchAdvertise> findByBranchIdandEnableTrue(Integer branchid);
	
	@Query(value="select ba.id from BranchAdvertise ba where ba.branch.id=?1 and ba.image!=null")
	List<Integer> findByBranchId(Integer branchid);
	
	@Query(value="select ba from BranchAdvertise ba where ba.branch.id=?1 and ba.image!=null")
	List<BranchAdvertise> findByBranchIds(Integer branchid);
	
	@Query(value="select ba from BranchAdvertise ba where ba.branch.id=?1 and ba.sortOrder =?2")
	Optional<Integer> findBysortOrder(int branchId,int shortOrder);
	
	@Query(value="select ba.id from BranchAdvertise ba where ba.branch.id=?1")
	List<Long> findByBranchIdAndId(int branchId);
	
	@Query(value="select ba.id from BranchAdvertise ba where ba.branch.id=?1 and ba.image != null")
	List<Long> findByOnlyBranchId(int branchid);
	
	@Query(value="select ba from BranchAdvertise ba where ba.branch.id=?1 and ba.id=?2")
	Optional<BranchAdvertise> findByID(int branchId ,long branchAdvertiseId);
	
	@Query(value="select ba from BranchAdvertise ba where ba.id=?1 and ba.branch.id=1 ")
	BranchAdvertise findByBranch1(long branchAdvertiseId);

}
