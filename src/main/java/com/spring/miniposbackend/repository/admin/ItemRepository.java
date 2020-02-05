package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	@Query(value = "select i from Item i where i.enable=true")
	List<Item> findAllActive();
}
