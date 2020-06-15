package com.spring.miniposbackend.controller.expense;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.expense.Expense;
import com.spring.miniposbackend.service.admin.BranchCurrencyService;
import com.spring.miniposbackend.service.expense.ExpenseService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("expense")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private UserProfileUtil userProfile;
	
	@GetMapping("by-branch")
	public List<Expense> getByBranch(){ // will get from user
		return expenseService.showByBranchId(userProfile.getProfile().getBranch().getId());
	} 

}
