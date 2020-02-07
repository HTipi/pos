package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.model.admin.ItemType;

public interface ItemRepository extends JpaRepository<Item, Long>{
	@Query(value = "select i from Item i where i.enable=?1")
	List<Item> findActive(boolean enable);
	
	@Query(value = "select i from Item i where i.branch = ?1 and i.enable = ?2")
	List<Item> findByBranchId(Branch branch, boolean enable);
	
	@Query(value = "select i from Item i where i.itemType = ?1 and i.enable = ?2")
	List<Item> findByItemTypeId(ItemType itemType, boolean enable);
	
	@Query(value = "select i from Item i where i.branch = ?1 and i.itemType = ?2 and i.enable = ?3")
	List<Item> findByBranchItemTypeId(Branch branch, ItemType itemType, boolean enable);
}
