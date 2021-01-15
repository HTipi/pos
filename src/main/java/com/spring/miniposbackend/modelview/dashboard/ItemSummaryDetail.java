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
	
	private Double monthlyDiscountAmount;
	private Double weeklyDiscountAmount;
	private Double dailyDiscountAmount;

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
		monthlyDiscountAmount = null;
		weeklyDiscountAmount = null;
		dailyDiscountAmount = null;
	}

	public ItemSummaryDetail(Long itemId, String itemName, String itemNamekh, Integer monthlySale, Integer weeklySale,
			Integer dailySale, Double monthlySaleAmount, Double weeklySaleAmount, Double dailySaleAmout,Double monthlyDiscountAmount, Double weeklyDiscountAmount,
			Double dailyDiscountAmount) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemNameKh = itemNamekh;
		this.monthlySale = monthlySale;
		this.weeklySale = weeklySale;
		this.dailySale = dailySale;
		this.monthlySaleAmount = monthlySaleAmount;
		this.weeklySaleAmount = weeklySaleAmount;
		this.dailySaleAmount = dailySaleAmout;
		this.monthlyDiscountAmount = monthlyDiscountAmount;
		this.weeklyDiscountAmount = weeklyDiscountAmount;
		this.dailyDiscountAmount = dailyDiscountAmount;
	}
}
