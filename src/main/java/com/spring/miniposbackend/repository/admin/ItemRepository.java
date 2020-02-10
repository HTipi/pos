package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	@Query(value = "select i from Item i where i.enable=?1")
	List<Item> findAllWithEnable(boolean enable);
	
	List<Item> findByBranchId(Integer branchId);
	@Query(value = "select i from Item i where i.branch.id = ?1 and i.enable = ?2")
	List<Item> findByBranchIdWithEnable(Integer branchId, boolean enable);
	List<Item> findByItemTypeId(Integer itemTypeId);
	@Query(value = "select i from Item i where i.itemType.id = ?1 and i.enable = ?2")
	List<Item> findByItemTypeIdWithEnable(Integer itemTypeId, boolean enable);
	@Query(value = "select i from Item i where i.branch.id = ?1 and i.itemType.id = ?2")
	List<Item> findByBranchItemTypeId(Integer branchId, Integer itemTypeId);
	@Query(value = "select i from Item i where i.branch.id = ?1 and i.itemType.id = ?2 and i.enable = ?3")
	List<Item> findByBranchItemTypeIdWithEnable(Integer branchId, Integer itemTypeId, boolean enable);
}
