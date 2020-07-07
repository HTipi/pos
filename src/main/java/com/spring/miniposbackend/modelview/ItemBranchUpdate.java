package com.spring.miniposbackend.modelview;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class ItemBranchUpdate {

	private boolean useItemConfiguration;
	private boolean enable;
	private BigDecimal price;
	private Short discount;
}
