package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BranchSummaryDetail {

	private Integer branchId;
	private String branchName;
	private String branchNameKh;
	private Integer monthlySale;
	private Integer weeklySale;
	private Integer dailySale;

	private Double monthlySaleAmount;
	private Double weeklySaleAmount;
	private Double dailySaleAmount;

	private Double monthlyDiscountAmount;
	private Double weeklyDiscountAmount;
	private Double dailyDiscountAmount;

	public BranchSummaryDetail() {
		branchId = null;
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

	public BranchSummaryDetail(Integer branchId, String branchName, String branchNameKh, Integer monthlySale,
			Integer weeklySale, Integer dailySale, Double monthlySaleAmount, Double weeklySaleAmount,
			Double dailySaleAmout, Double monthlyDiscountAmount, Double weeklyDiscountAmount,
			Double dailyDiscountAmount) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchNameKh = branchNameKh;
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
