package com.spring.miniposbackend.service.expense;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.expense.Expense;
import com.spring.miniposbackend.model.sale.Receipt;
import com.spring.miniposbackend.repository.expense.ExpenseRepository;
import com.spring.miniposbackend.repository.sale.ReceiptRepository;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	public List<Expense> showByBranchId(Integer branchId) {
		return expenseRepository.findByBranchId(branchId);
	}
}
