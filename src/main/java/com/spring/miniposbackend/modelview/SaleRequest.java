package com.spring.miniposbackend.modelview;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleRequest {
	
	Long saleTmpId;
	Integer seatId;
	Long itemId;
	Short quantity;
	Short discount;
	Double discountAmount;
	Long parentSaleId;

}
