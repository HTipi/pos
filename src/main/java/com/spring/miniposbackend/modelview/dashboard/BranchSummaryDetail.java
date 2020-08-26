package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BranchSummaryDetail {

	private Integer branchId;
	private String branchName;
	private String branchNameKh;
	private Double monthlySale;
	private Double weeklySale;
	private Double dailySale;

	public BranchSummaryDetail() {
		branchId = null;
		monthlySale = null;
		weeklySale = null;
		dailySale = null;
	}

	public BranchSummaryDetail(Integer branchId, String branchName, String branchNameKh, Double monthlySale,
			Double weeklySale, Double dailySale) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchNameKh = branchNameKh;
		this.monthlySale = monthlySale;
		this.weeklySale = weeklySale;
		this.dailySale = dailySale;
	}

}
