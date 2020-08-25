package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSummaryDetail {
	private Integer branchId;
	private Double monthlySale;
	private Double weeklySale;
	private Double dailySale;
	
	public ItemSummaryDetail() {
		branchId=null;
		monthlySale=null;
		weeklySale=null;
		dailySale=null;
	}
	public ItemSummaryDetail(Integer branchId, Double monthlySale, Double weeklySale, Double dailySale) {
		this.branchId=branchId;
		this.monthlySale=monthlySale;
		this.weeklySale=weeklySale;
		this.dailySale=dailySale;
	}
}
