package com.spring.miniposbackend.controller.expense;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.expense.Expense;
import com.spring.miniposbackend.service.expense.ExpenseService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("expense")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping("by-branch-monthly")
	@PreAuthorize("hasAnyRole('USER','OWNER')")
	public SuccessResponse getByBranchM(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date valueDate,
			@RequestParam Optional<Boolean> isReversed) {
		return new SuccessResponse("00", "fetch Expense", expenseService
				.showByBranchIdAndMonthly(userProfile.getProfile().getBranch().getId(), valueDate, isReversed));
	}

	@GetMapping("by-branch-date")
	@PreAuthorize("hasAnyRole('USER','OWNER')")
	public SuccessResponse getByBranch(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			@RequestParam Optional<Boolean> isReversed) { // will get from user
		return new SuccessResponse("00", "fetch Expense", expenseService
				.showByBranchIdAndDate(userProfile.getProfile().getBranch().getId(), startDate, endDate, isReversed));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('USER','OWNER')")
	public SuccessResponse create(@RequestParam Integer expenseTypeId, @RequestBody Expense requestItem) {
		return new SuccessResponse("00", "Create Expense",
				expenseService.create(requestItem, userProfile.getProfile().getUser().getId(), expenseTypeId));
	}

	@PatchMapping("reverse/{expenseId}")
	@PreAuthorize("hasAnyRole('USER','OWNER')")
	public SuccessResponse delete(@PathVariable Integer expenseId) {
		return new SuccessResponse("00", "Reverse Expense",
				expenseService.reverse(expenseId, userProfile.getProfile().getUser().getId()));
	}

}
