package com.spring.miniposbackend.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.stock.StockEntry;
public interface StockEntryRepository extends JpaRepository<StockEntry, Long>{

	List<StockEntry> findByStockId(Long stockId);
	
	@Query(value = "select s from StockEntry s where s.stock.id = ?1 and s.stock.posted = ?2")
	List<StockEntry> findByStockIdWithPosted(Long stockId, boolean posted);
}
