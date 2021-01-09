package com.spring.miniposbackend.modelview;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDetailSummary {

	private Integer voidInvoice;
	private Integer paidInvoice;
	private String startDate;
	private String endDate;
	private Double subTotal;
	private Double discountAmount;
	private Double discountSaleDetail;

	public SaleDetailSummary(Integer voidInvoice, Integer paidInvoice, String startDate,String endDate, Double subTotal, Double discountAmount,
			Double discountSaleDetail) {
		this.voidInvoice = voidInvoice;
		this.paidInvoice = paidInvoice;
		this.startDate = startDate;
		this.endDate = endDate;
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
