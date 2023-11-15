package com.spring.miniposbackend.modelview;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleRequest {
	
	Long saleTmpId;
	Long itemId;
	float quantity;
	Short discountPercentage;
	Optional<Short> addPercentage;
	Double discountAmount;
	Double price = Double.valueOf(0);
	List<SaleRequest> addOns;
	List<Integer> addPromo = new ArrayList<Integer>();
	String remark;

}
