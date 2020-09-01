package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseSummaryDetail {

	private Integer branchId;
	private String branchName;
	private String branchNameKh;
	private Double dailyExpenseAmount;

	public ExpenseSummaryDetail() {
		this.branchId=null;
		this.branchName=null;
		this.branchNameKh=null;
		this.dailyExpenseAmount=null;
	}
	
	public ExpenseSummaryDetail(Integer branchId, String branchName, String branchNameKh, Double dailyExpenseAmount) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchNameKh = branchNameKh;
		this.dailyExpenseAmount = dailyExpenseAmount;
	}
}
