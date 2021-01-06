package com.spring.miniposbackend.modelview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDetailTransaction {

	private Long itemId;
	private String itemName;
	private Integer quantity;
	private Double subTotal;
	private Double discountTotal;

	public SaleDetailTransaction(Long itemId, String itemName, Integer quanity, Double subTotal, Double discountTotal) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quanity;
		this.subTotal = subTotal;
		this.discountTotal = discountTotal;
	}

	public Double getTotal() {
		return subTotal - discountTotal;
	}
}
