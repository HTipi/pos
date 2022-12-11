package com.spring.miniposbackend.model.sale;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.admin.BranchPromotion;
import com.spring.miniposbackend.model.admin.ItemBranchPromotion;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sale_detail_promotion")
@Getter
@Setter
public class SaleDetailPromotion {

	@EmbeddedId
	@JsonIgnore
	private SaleDetailPromotionIdentity saleDetailPromotionIdentity;

	@JsonIgnore
	public SaleDetail getSaleDetail() {
		return saleDetailPromotionIdentity.getSaleDetail();
	}

	@JsonIgnore
	public BranchPromotion getBranchPromotion() {
		return saleDetailPromotionIdentity.getBranchPromotion();
	}
	
	
	
	@Column(name = "discount", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
	private BigDecimal discount;

}
