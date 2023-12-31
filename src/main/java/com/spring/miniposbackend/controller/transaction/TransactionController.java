package com.spring.miniposbackend.controller.transaction;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.transaction.GivePointRequest;
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
	
	@GetMapping("by-person")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public SuccessResponse showTransactionSummaryPerson(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,@RequestParam Integer branchId) {
		return new SuccessResponse("00", "fetch transaction", transactionService.getTransactionByDateAndPerson(from, to,branchId));
	}
	@PostMapping("give-point/{personId}")
	 @PreAuthorize("hasAnyRole('OWNER')")
	 public SuccessResponse givePoint(@PathVariable Long personId, @RequestBody GivePointRequest givePointRequest) {
	  return new SuccessResponse("00", "Give Point Success", transactionService.givePoint(personId, givePointRequest));
	 }
}

