package com.spring.miniposbackend.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.stock.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{
	
	List<Stock> findByBranchId(Integer branchId);

	@Query(value = "select s from Stock s where s.branch.id = ?1 and s.stockIn = ?2")
	List<Stock> findByBranchId(Integer branchId, boolean stockIn);
	
	@Query(value = "select s from Stock s where s.branch.id = ?1 and s.stockIn = ?2 and s.posted = ?3")
	List<Stock> findByBranchId(Integer branchId, boolean stockIn, boolean posted);
	
}
