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
	private Integer itemTypeId;
	private boolean isStock;
	private Integer stocks;

	public SaleDetailTransaction(Long itemId, String itemName, Integer quanity, Double subTotal, Double discountTotal,Integer itemTypeId,boolean isStock,Integer stocks) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quanity;
		this.subTotal = subTotal;
		this.discountTotal = discountTotal;
		this.itemTypeId = itemTypeId;
		this.isStock = isStock;
		this.stocks = stocks;
	}

	public Double getTotal() {
		return subTotal - discountTotal;
	}
}
