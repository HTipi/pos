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
	private Double monthlyExpenseAmount;
	private Double weeklyExpenseAmount;

	public ExpenseSummaryDetail() {
		this.branchId=null;
		this.branchName=null;
		this.branchNameKh=null;
		this.dailyExpenseAmount=null;
		this.monthlyExpenseAmount=null;
		this.weeklyExpenseAmount=null;
	}
	
	public ExpenseSummaryDetail(Integer branchId, String branchName, String branchNameKh,Double monthlyExpenseAmount,Double weeklyExpenseAmount, Double dailyExpenseAmount) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchNameKh = branchNameKh;
		this.dailyExpenseAmount = dailyExpenseAmount;
		this.monthlyExpenseAmount = monthlyExpenseAmount;
		this.weeklyExpenseAmount = weeklyExpenseAmount;
	}
}
