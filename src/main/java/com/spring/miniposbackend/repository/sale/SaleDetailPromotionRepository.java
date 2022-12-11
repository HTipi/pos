package com.spring.miniposbackend.repository.sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.SaleDetailPromotion;
import com.spring.miniposbackend.model.sale.SaleDetailPromotionIdentity;

@Repository
public interface SaleDetailPromotionRepository extends JpaRepository<SaleDetailPromotion, SaleDetailPromotionIdentity>{
	
}
