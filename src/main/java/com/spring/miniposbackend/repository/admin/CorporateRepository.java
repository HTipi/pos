package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.Corporate;


@Repository
public interface CorporateRepository extends JpaRepository<Corporate,Integer> {
//    @Query(value = "select c from Corporate c where c.enable=true")
//    List<Corporate> findAllActive();
//    @Query(value = "select c from Corporate c where c.enable=true and c.category.id=?1")
//    List<Corporate> findAllActiveByCategoryId(Integer categoryId);
}
