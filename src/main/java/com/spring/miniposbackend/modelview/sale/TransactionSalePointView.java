package com.spring.miniposbackend.modelview.sale;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionSalePointView {

	private long saleId;
	private short point;
	private int transactionTypeId;
	private long accountId;
	private int branchId;

}
