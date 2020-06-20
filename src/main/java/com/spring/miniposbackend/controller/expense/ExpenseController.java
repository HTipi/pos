package com.spring.miniposbackend.controller.expense;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	public List<Expense> getByBranchM(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date valueDate){ // will get from user
		return expenseService.showByBranchIdAndMonthly(userProfile.getProfile().getBranch().getId(),valueDate);
	}
	@GetMapping("by-branch-date")
	public List<Expense> getByBranch(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){ // will get from user
		return expenseService.showByBranchIdAndDate(userProfile.getProfile().getBranch().getId(),startDate,endDate);
	}
	
	@PostMapping
	public Expense create(@RequestParam Integer expenseTypeId,@RequestBody Expense requestItem) {
		return expenseService.create(requestItem,
				userProfile.getProfile().getUser().getId(),expenseTypeId);
	}
	@PatchMapping("reverse/{expenseId}")
	public Expense delete(@PathVariable Integer expenseId) {
		return expenseService.reverse(expenseId,userProfile.getProfile().getUser().getId());
	}

}
