package com.spring.miniposbackend.repository.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.ItemBranch;

@Repository
public interface ItemBranchRepository extends  JpaRepository<ItemBranch, Long>{
	List<ItemBranch> findByBranchId(Integer branchId);
	@Query(value = "select ib from ItemBranch ib where ib.branch.id = ?1 and ib.enable= ?2 and ib.item.enable=true")
	List<ItemBranch> findByBranchId(Integer branchId, boolean enable);
	List<ItemBranch> findByItemId(Long itemId);
	@Query(value = "select ib from ItemBranch ib where ib.item.id = ?1 and ib.enable= ?2")
	List<ItemBranch> findByItemId(Long itemId, boolean enable);
	@Query(value = "select ib from ItemBranch ib where ib.item.id = ?1 and ib.branch.isMain =false")
	List<ItemBranch> findByItemCheckListId(Long itemId);
	
	Optional<ItemBranch> findFirstByBranchIdAndItemIdOrderByIdDesc(Integer branchId, Long itemId);
	
	
	@Query(value = "select * from item_branches ib where ?1 = any (ib.add_on)", nativeQuery = true)
	List<ItemBranch> findAnyAddOn(Long addOnId);
	
	@Query(value = "select ib from ItemBranch ib where ib.item.code = ?1")
	  Optional<ItemBranch> findByitemCode(String code);
	  @Query(value = "select ib from ItemBranch ib where ib.item.code = ?1")
	  Optional<ItemBranch> findByitemCode(List<String> arrStrr);
	  
	  @Query(value="select ib from ItemBranch ib where ib.item.id=?1 and ib.item.type='INVENTORY'")
	  Optional<ItemBranch> findbyinventory(Long inventoryId);
	  
	  @Query(value="select ib from ItemBranch ib where ib.branch.id=?1 and ib.id=?2")
	   Optional<ItemBranch> findByBranchIdandId(Integer branchId, Long itemId);
	  

	
}
