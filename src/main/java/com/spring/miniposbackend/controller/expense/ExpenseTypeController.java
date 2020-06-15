package com.spring.miniposbackend.controller.expense;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ExpenseType create(@RequestBody Map<String, Integer> requestItem) {
		return expenseTypeService.create(requestItem, userProfile.getProfile().getBranch().getId(),
				userProfile.getProfile().getUser().getId());
	}

}
