package com.spring.miniposbackend.repository.sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
	 List<Sale> findByBranchId(Integer branchId);
	 List<Sale> findByUserId(Integer userId);
	 
	 @Query(value = "select s from Sale s where s.user.id = ?1 and s.id = ?2")
	    List<Sale> findByIdWithUserId(Integer userId, Long saleId);
	 
	 

}
