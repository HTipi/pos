package com.spring.miniposbackend.repository.sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.SaleDetail;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long>{
	 List<SaleDetail> findBySaleId(Long saleId);
	 List<SaleDetail> findByUserId(Integer userId);
}
