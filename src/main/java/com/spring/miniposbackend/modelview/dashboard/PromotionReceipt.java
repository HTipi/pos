package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionReceipt {

	private String promotion;
	private double discountAmt;
	private double qty;

	public PromotionReceipt() {
		this.promotion=null;
		this.discountAmt=0.00;
		this.qty=0.00;
	}
	
	public PromotionReceipt(String promotion, double discountAmt,double qty) {
		this.promotion = promotion;
		this.discountAmt = discountAmt;
		this.qty = qty;
	}
}
