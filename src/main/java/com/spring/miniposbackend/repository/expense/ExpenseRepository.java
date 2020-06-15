package com.spring.miniposbackend.repository.expense;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.expense.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer>{
	
	@Query(value = "select bc from Expense bc where bc.branch.id = ?1")
	List<Expense> findByBranchId(Integer branchId);
}
