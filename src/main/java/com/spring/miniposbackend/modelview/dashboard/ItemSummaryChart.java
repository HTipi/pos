package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSummaryChart {
	private Long itemId;
	private String itemName;
	private String itemNameKh;
	private Double saleAmt;
	private Integer saleItem;

	public ItemSummaryChart() {
		itemId = null;
		itemName = null;
		itemNameKh = null;
		saleAmt = null;
		saleItem = null;
	}

	public ItemSummaryChart(Long itemId, String itemName, String itemNamekh, Double saleAmt, Integer saleItem) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemNameKh = itemNamekh;
		this.saleAmt = saleAmt;
		this.saleItem = saleItem;
	}
}
