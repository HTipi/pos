package com.spring.miniposbackend.modelview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDetailSummary {

	private Integer voidInvoice;
	private Integer paidInvoice;
	private Double subTotal;
	private Double discountTotal;

	public SaleDetailSummary(Integer voidInvoice, Integer paidInvoice, Double subTotal, Double discountTotal) {
		this.voidInvoice = voidInvoice;
		this.paidInvoice = paidInvoice;
		this.subTotal = subTotal;
		this.discountTotal = discountTotal;
	}

	public Double getTotal() {
		return subTotal - discountTotal;
	}
}
