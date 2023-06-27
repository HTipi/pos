package com.spring.miniposbackend.modelview;

import java.util.List;

import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.transaction.TransactionType;
import com.spring.miniposbackend.modelview.account.AccountModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitPointViewModel {

	private UserResponse user;
	private AccountModel account;
	private BranchCurrency branchCurrency;
	private List<TransactionType> transactionType;

	public InitPointViewModel(UserResponse userResponse,AccountModel accountModel,BranchCurrency branchCurrency,List<TransactionType> transactionType) {

		this.user = userResponse;
		this.account = accountModel;
		this.branchCurrency = branchCurrency;
		this.transactionType = transactionType;

	}
}
