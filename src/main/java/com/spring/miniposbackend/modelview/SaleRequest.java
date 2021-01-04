package com.spring.miniposbackend.modelview;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleRequest {
	
	Long saleTmpId;
	Integer seatId;
	Long itemId;
	BigDecimal price;
	Short quantity;
	Short discount;
	Double discountAmount;
	List<SaleRequest> addOns;

}
