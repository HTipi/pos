package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTypeSummaryDetail {
	private Long itemTypeId;
	private String itemTypeName;
	private String itemTypeNameKh;
	private Integer monthlySale;
	private Integer weeklySale;
	private Integer dailySale;

	private Double monthlySaleAmount;
	private Double weeklySaleAmount;
	private Double dailySaleAmount;

	private Double monthlyDiscountAmount;
	private Double weeklyDiscountAmount;
	private Double dailyDiscountAmount;

	public ItemTypeSummaryDetail() {
		itemTypeId = null;
		itemTypeName = null;
		itemTypeNameKh = null;
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

	public ItemTypeSummaryDetail(Long itemTypeId, String itemTypeName, String itemTypeNameKh, Integer monthlySale,
			Integer weeklySale, Integer dailySale, Double monthlySaleAmount, Double weeklySaleAmount,
			Double dailySaleAmout, Double monthlyDiscountAmount, Double weeklyDiscountAmount,
			Double dailyDiscountAmount) {
		this.itemTypeId = itemTypeId;
		this.itemTypeName = itemTypeName;
		this.itemTypeNameKh = itemTypeNameKh;
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
