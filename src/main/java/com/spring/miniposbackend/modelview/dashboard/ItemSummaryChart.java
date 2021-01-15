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
	private Double disAmt;
	private Integer saleItem;

	public ItemSummaryChart() {
		itemId = null;
		itemName = null;
		itemNameKh = null;
		saleAmt = null;
		disAmt = null;
		saleItem = null;
	}

	public ItemSummaryChart(Long itemId, String itemName, String itemNamekh, Double saleAmt,Double disAmt, Integer saleItem) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemNameKh = itemNamekh;
		this.saleAmt = saleAmt;
		this.disAmt = disAmt;
		this.saleItem = saleItem;
	}
}
