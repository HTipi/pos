package com.spring.miniposbackend.repository.sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
	 List<Sale> findByBranchId(Integer branchId);
	 List<Sale> findByUserId(Integer userId);
	 

}
