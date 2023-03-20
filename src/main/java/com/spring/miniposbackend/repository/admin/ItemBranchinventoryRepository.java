package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.ItemBranchInventory;

@Repository
public interface ItemBranchinventoryRepository extends JpaRepository<ItemBranchInventory, Long>{

	@Modifying
	@Query(value = "delete from item_branch_inventory where item_branch_id=?1", nativeQuery = true)
	void deleteByitembranchId(Long itembranchid);
	
	@Modifying
	@Query(value = "delete from item_branch_inventory where branch_id=?1", nativeQuery = true)
	void deleteBybranchId(int branchid);

}
