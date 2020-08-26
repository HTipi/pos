package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSummaryDetail {
	private Long itemId;
	private String itemName;
	private String itemNameKh;
	private Double monthlySale;
	private Double weeklySale;
	private Double dailySale;

	public ItemSummaryDetail() {
		itemId = null;
		itemName = null;
		itemNameKh = null;
		monthlySale = null;
		weeklySale = null;
		dailySale = null;
	}

	public ItemSummaryDetail(Long itemId, String itemName, String itemNamekh, Double monthlySale, Double weeklySale,
			Double dailySale) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemNameKh = itemNamekh;
		this.monthlySale = monthlySale;
		this.weeklySale = weeklySale;
		this.dailySale = dailySale;
	}
}
