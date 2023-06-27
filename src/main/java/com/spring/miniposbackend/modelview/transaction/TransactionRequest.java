package com.spring.miniposbackend.modelview.transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequest {
	
	
	private String remark;
	private int transactionTypeId;
	private Long accountId;
	private double transactionAmount;
	

}
