package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.StockType;

@Repository
public interface StockTypeRepository extends JpaRepository<StockType, String>{

}
