package com.spring.miniposbackend.repository.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.stock.StockEntry;
public interface StockEntryRepository extends JpaRepository<StockEntry, Long>{

	List<StockEntry> findByStockId(Long stockId);
	
	@Query(value = "select s from StockEntry s where s.stock.id = ?1 and s.stock.posted = ?2")
	List<StockEntry> findByStockIdWithPosted(Long stockId, boolean posted);
	
	@Query(value = "select sum(s.quantity) from StockEntry s where s.itemBranch.id=?1 and s.stock.id = ?2 and s.stock.posted =false")
	Optional<Integer> findByItemBranchId(Long itemBranchId,Long stockId);
}
