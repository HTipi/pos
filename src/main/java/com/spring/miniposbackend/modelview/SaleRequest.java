package com.spring.miniposbackend.modelview;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleRequest {
	
	Long saleTmpId;
	Long itemId;
	Short quantity;
	Short discountPercentage;
	Double discountAmount;
	Double price = Double.valueOf(0);
	List<SaleRequest> addOns;

}
