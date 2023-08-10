package com.spring.miniposbackend.modelview;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockEntryRequest {

	private Long itemId;
	private BigDecimal price;
	private double quantity;
	private BigDecimal discountAmount;
	private BigDecimal total;
	private Short discount;
}
