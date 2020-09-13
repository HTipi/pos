package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseTypeSummaryDetail {

	private Integer expenseTypeId;
	private String expenseTypeName;
	private String expenseTypeKh;
	private Double expenseAmt;
	private Integer expenseItem;

	public ExpenseTypeSummaryDetail() {
		this.expenseTypeId=null;
		this.expenseTypeName=null;
		this.expenseTypeKh=null;
		this.expenseAmt=null;
		this.expenseItem=null;
	}
	
	public ExpenseTypeSummaryDetail(Integer expenseTypeId, String expenseTypeName, String expenseTypeKh,Double expenseAmt,Integer expenseItem) {
		this.expenseTypeId = expenseTypeId;
		this.expenseTypeName = expenseTypeName;
		this.expenseTypeKh = expenseTypeKh;
		this.expenseAmt = expenseAmt;
		this.expenseItem = expenseItem;
	}
}
