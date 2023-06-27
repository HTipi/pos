package com.spring.miniposbackend.modelview.transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionSalePointView {

	private long saleId;
	private short point;
	private int transactionTypeId;
	private long accountId;

}