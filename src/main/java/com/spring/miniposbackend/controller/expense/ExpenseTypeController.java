package com.spring.miniposbackend.controller.expense;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	public List<ExpenseType> getByBranch() { // will get from user
		return expenseTypeService.showByBranchId(userProfile.getProfile().getBranch().getId(), true);
	}

	@PostMapping
	public ExpenseType create(@RequestBody ExpenseType requestItem) {
		return expenseTypeService.create(requestItem,
				userProfile.getProfile().getUser().getId());
	}
	@PatchMapping("{expenseTypeId}")
	public ExpenseType update(@PathVariable Integer expenseTypeId,@RequestBody ExpenseType expenseType) {
		return expenseTypeService.update(expenseTypeId, expenseType,userProfile.getProfile().getUser().getId());
	}
	@PatchMapping("delete/{expenseTypeId}")
	public ExpenseType delete(@PathVariable Integer expenseTypeId) {
		return expenseTypeService.delete(expenseTypeId,userProfile.getProfile().getUser().getId());
	}


}
