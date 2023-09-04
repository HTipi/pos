package com.spring.miniposbackend.modelview.sale;

import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SalePaymentRequest {
	
	Double discount;
	Integer currencyId;
	Double discountAmount;
	Integer userId;
	String remark;
	Double serviceCharge;
	BigDecimal total;
	double discountPercentage;
	long accountId;
	short point;
	int transactionTypeId;
	double vat;
	Integer branchId;
	BigDecimal grandTotal;
	@JsonFormat(pattern = "yyyy-MM-dd") Optional<String> expirydate;
}