package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchSummaryChart {
	private Long branchId;
	private String branchName;
	private String branchNameKh;
	private Double saleAmt;
	private Integer saleItem;

	public BranchSummaryChart() {
		branchId = null;
		branchName = null;
		branchNameKh = null;
		saleAmt = null;
		saleItem = null;
	}

	public BranchSummaryChart(Long branchId, String branchName, String branchNameKh, Double saleAmt, Integer saleItem) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchNameKh = branchNameKh;
		this.saleAmt = saleAmt;
		this.saleItem = saleItem;
	}
}
