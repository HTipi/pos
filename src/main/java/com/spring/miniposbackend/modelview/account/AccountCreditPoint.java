package com.spring.miniposbackend.modelview.account;
import java.math.BigDecimal;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountCreditPoint {
	private Long accountId;
	private BigDecimal balance;
	private int accountTypeId;
	

}
