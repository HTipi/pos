package com.spring.miniposbackend.repository.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,UUID>{

	@Query("select i from Image i where i.type = ?1")
	Page<Image> findByType(String type, Pageable pageable);
	@Query("select i from Image i where i.imageType.id = ?1 and i.type='item'")
	Page<Image> findByCategory(Integer categoryId, Pageable pageable);
}
