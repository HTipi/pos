package com.spring.miniposbackend.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.stock.StockEntry;

public interface StockEntryRepository extends JpaRepository<StockEntry, Long>{

}
