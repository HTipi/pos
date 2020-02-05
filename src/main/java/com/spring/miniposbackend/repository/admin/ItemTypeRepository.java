package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.ItemType;

public interface ItemTypeRepository extends JpaRepository<ItemType, Integer>{
	@Query(value = "select t from ItemType t where t.enable=true")
	List<ItemType> findAllActive();
}
