package com.spring.miniposbackend.modelview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDetailTransaction {

	private Long itemId;
	private String itemName;
	private double quantity;
	private Double subTotal;
	private Double discountTotal;
	private Integer itemTypeId;
	private boolean isStock;
	private double stocks;

	public SaleDetailTransaction(Long itemId, String itemName, double quanity, Double subTotal, Double discountTotal,Integer itemTypeId,boolean isStock,double stocks) {
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
