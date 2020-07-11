package com.spring.miniposbackend.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.stock.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{
}
