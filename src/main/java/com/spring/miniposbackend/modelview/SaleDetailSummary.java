package com.spring.miniposbackend.modelview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDetailSummary {

	private Integer voidInvoice;
	private Integer paidInvoice;
	private Double subTotal;
	private Double discountAmount;
	private Double discountSaleDetail;

	public SaleDetailSummary(Integer voidInvoice, Integer paidInvoice, Double subTotal, Double discountAmount,
			Double discountSaleDetail) {
		this.voidInvoice = voidInvoice;
		this.paidInvoice = paidInvoice;
		this.subTotal = subTotal;
		this.discountAmount = discountAmount;
		this.discountSaleDetail = discountSaleDetail;
	}

	public Double getDiscountTotal() {
		return discountAmount + discountSaleDetail;
	}

	public Double getTotal() {
		return subTotal - getDiscountTotal();
	}
}
