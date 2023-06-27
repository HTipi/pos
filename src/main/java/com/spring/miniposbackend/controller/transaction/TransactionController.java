package com.spring.miniposbackend.controller.transaction;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.transaction.TransactionRequest;
import com.spring.miniposbackend.service.transaction.TransactionService;


@RestController
@RequestMapping("transaction")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@PostMapping
	@PreAuthorize("hasAnyRole('SALE','BRANCH','OWNER')")
	public SuccessResponse create(@RequestBody TransactionRequest transactionview) {
		return new SuccessResponse("code", "created account completed", transactionService.create(transactionview));
	}

	@GetMapping("by-transaction")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public SuccessResponse showTransactionSummary(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,@RequestParam Long accountId) {
		return new SuccessResponse("00", "fetch transaction", transactionService.getTransactionByDate(from, to,accountId));
	}
}

