package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.ItemType;

public interface ItemTypeRepository extends JpaRepository<ItemType, Integer>{
	@Query(value = "select t from ItemType t where t.enable=?1")
	List<ItemType> findAll(boolean enable);
	
	List<ItemType> findByCorporateId(Integer corporateId);
	@Query(value = "select t from ItemType t where t.corporate.id = ?1 and t.enable = ?2")
	List<ItemType> findByCorporateId(Integer corporateId, boolean enable);
	
//	@Query(value = "select t from ItemType t where t.item and t.enable= ?2")
//	List<ItemType> findByBranchId(Integer branchId, boolean enable);
}
