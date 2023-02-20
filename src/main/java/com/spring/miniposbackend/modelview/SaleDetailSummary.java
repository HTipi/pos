package com.spring.miniposbackend.modelview;

import java.util.List;

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
	private Double vat;
	private Double serviceCharge;
	
	private List<SaleDetailTransaction> details;

	public SaleDetailSummary(Integer voidInvoice, Integer paidInvoice, String startDate,String endDate, Double subTotal, Double discountAmount,
			Double discountSaleDetail,Double vat,Double serviceCharge) {
		this.voidInvoice = voidInvoice;
		this.paidInvoice = paidInvoice;
		this.startDate = startDate;
		this.endDate = endDate;
		this.subTotal = subTotal;
		this.discountAmount = discountAmount;
		this.discountSaleDetail = discountSaleDetail;
		this.vat = vat;
				this.serviceCharge = serviceCharge;
	}

	public Double getDiscountTotal() {
		return discountAmount + discountSaleDetail;
	}

	public Double getTotal() {
		return subTotal - getDiscountTotal();
	}
	public Double getTotalVS() {
				return subTotal - getDiscountTotal() + vat + serviceCharge;
			}
			
			public Double getTotalVat() {
				return subTotal - getDiscountTotal() + vat;
			}
}
