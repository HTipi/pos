package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.ItemBranch;

@Repository
public interface ItemBranchRepository extends  JpaRepository<ItemBranch, Long>{
	List<ItemBranch> findByBranchId(Integer branchId);
	@Query(value = "select ib from ItemBranch ib where ib.branch.id = ?1 and ib.enable= ?2")
	List<ItemBranch> findByBranchIdWithEnable(Integer branchId, boolean enable);
	List<ItemBranch> findByItemId(Integer itemId);
	@Query(value = "select ib from ItemBranch ib where ib.item.id = ?1 and ib.enable= ?2")
	List<ItemBranch> findByItemIdWithEnable(Integer itemId, boolean enable);
}
