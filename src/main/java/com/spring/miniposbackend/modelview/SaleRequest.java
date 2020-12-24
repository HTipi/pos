package com.spring.miniposbackend.modelview;

import java.util.List;

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
	List<SaleRequest> addOns;

}
