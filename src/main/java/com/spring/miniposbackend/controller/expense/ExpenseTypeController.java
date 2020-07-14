package com.spring.miniposbackend.controller.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.expense.ExpenseType;
import com.spring.miniposbackend.service.expense.ExpenseTypeService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("expense-type")
public class ExpenseTypeController {

	@Autowired
	private ExpenseTypeService expenseTypeService;
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping("by-branch")
	public SuccessResponse getByBranch() { // will get from user

		return new SuccessResponse("00", "fetch Expense Type",
				expenseTypeService.showByBranchId(userProfile.getProfile().getBranch().getId(), true));
	}

	@PostMapping
	public SuccessResponse create(@RequestBody ExpenseType requestItem) {
		return new SuccessResponse("00", "Expense Type Created",
				expenseTypeService.create(requestItem, userProfile.getProfile().getUser().getId()));
	}

	@PutMapping("{expenseTypeId}")
	public SuccessResponse update(@PathVariable Integer expenseTypeId, @RequestBody ExpenseType expenseType) {
		return new SuccessResponse("00", "Expense Type Updated",
				expenseTypeService.update(expenseTypeId, expenseType, userProfile.getProfile().getUser().getId()));

	}

	@PatchMapping("delete/{expenseTypeId}")
	public SuccessResponse delete(@PathVariable Integer expenseTypeId) {
		return new SuccessResponse("00", "Expense Type Disabled",
				expenseTypeService.delete(expenseTypeId, userProfile.getProfile().getUser().getId()));

	}

}
