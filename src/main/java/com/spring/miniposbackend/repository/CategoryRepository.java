package com.spring.miniposbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
	@Query(value = "select c from Category c where c.enable=true")
	List<Category> findAllActive();
	
}

