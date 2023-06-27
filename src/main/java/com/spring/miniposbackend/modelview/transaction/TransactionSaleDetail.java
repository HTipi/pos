package com.spring.miniposbackend.modelview.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSaleDetail {

	private String itemName;
	private double quantity;
	private Double subTotal;
	private Double discountTotal;

	public TransactionSaleDetail(
			String itemName, double quanity, Double subTotal, Double discountTotal) {
		this.itemName = itemName;
		this.quantity = quanity;
		this.subTotal = subTotal;
		this.discountTotal = discountTotal;
	}
	
	public Double getTotal() {
		return subTotal - discountTotal;
	}
}
