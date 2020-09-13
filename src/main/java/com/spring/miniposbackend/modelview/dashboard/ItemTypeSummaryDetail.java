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
	}

	public ItemTypeSummaryDetail(Long itemTypeId, String itemTypeName, String itemTypeNameKh, Integer monthlySale,
			Integer weeklySale, Integer dailySale, Double monthlySaleAmount, Double weeklySaleAmount,
			Double dailySaleAmout) {
		this.itemTypeId = itemTypeId;
		this.itemTypeName = itemTypeName;
		this.itemTypeNameKh = itemTypeNameKh;
		this.monthlySale = monthlySale;
		this.weeklySale = weeklySale;
		this.dailySale = dailySale;
		this.monthlySaleAmount = monthlySaleAmount;
		this.weeklySaleAmount = weeklySaleAmount;
		this.dailySaleAmount = dailySaleAmout;
	}
}
