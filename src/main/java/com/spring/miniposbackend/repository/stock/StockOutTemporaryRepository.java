package com.spring.miniposbackend.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.stock.StockOutTemporary;

public interface StockOutTemporaryRepository extends JpaRepository<StockOutTemporary, Long>{
	
	List<StockOutTemporary> findByBranchId(Integer branchId);
	
	@Query(value = "select s from StockOutTemporary s where s.branch.id = ?1 and s.isPrinted = ?2")
	List<StockOutTemporary> findByBranchIdWithIsPrinted(Integer branchId, boolean isPrinted);
	
	@Query(value = "select s from StockOutTemporary s where s.branch.id = ?1 and s.isPrinted = ?2 and s.cancel= ?3")
	List<StockOutTemporary> findByBranchIdWithIsPrintedCancel(Integer branchId, boolean isPrinted, boolean cancel);

}
