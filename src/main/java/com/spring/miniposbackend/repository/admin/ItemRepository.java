package com.spring.miniposbackend.repository.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	@Query(value = "select i from Item i where i.enable=?1")
	List<Item> findAll(boolean enable);
	List<Item> findByItemTypeId(Integer itemTypeId);
	@Query(value = "select i from Item i where i.itemType.id = ?1 and i.enable = ?2")
	List<Item> findByItemTypeId(Integer itemTypeId, boolean enable);
	
	@Query(value = "select i from Item i where i.itemType.corporate.id = ?1")
	List<Item> findByCorporateId(Integer corporateId);
	@Query(value = "select i from Item i where i.itemType.corporate.id = ?1 and i.enable = ?2")
	List<Item> findByCorporateId(Integer corporateId, boolean enable);
	
	List<Item> findBytype(String type);
	
	@Query(value = "select i from Item i where i.code=?1")
	Optional<Item> findByCode(String code);
	
	
	@Query(value = "select i from Item i where i.id=?1 and i.type='INVENTORY' ")
	Optional<Item> findByitemId(Long inventoryId);

	
}
