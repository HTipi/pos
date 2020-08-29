package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSummaryDetail {
	private Long itemId;
	private String itemName;
	private String itemNameKh;
	private Integer monthlySale;
	private Integer weeklySale;
	private Integer dailySale;

	private Double monthlySaleAmount;
	private Double weeklySaleAmount;
	private Double dailySaleAmount;

	public ItemSummaryDetail() {
		itemId = null;
		itemName = null;
		itemNameKh = null;
		monthlySale = null;
		weeklySale = null;
		dailySale = null;
		monthlySaleAmount = null;
		weeklySaleAmount = null;
		dailySaleAmount = null;
	}

	public ItemSummaryDetail(Long itemId, String itemName, String itemNamekh, Integer monthlySale, Integer weeklySale,
			Integer dailySale, Double monthlySaleAmount, Double weeklySaleAmount, Double dailySaleAmout) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemNameKh = itemNamekh;
		this.monthlySale = monthlySale;
		this.weeklySale = weeklySale;
		this.dailySale = dailySale;
		this.monthlySaleAmount = monthlySaleAmount;
		this.weeklySaleAmount = weeklySaleAmount;
		this.dailySaleAmount = dailySaleAmout;
	}
}
