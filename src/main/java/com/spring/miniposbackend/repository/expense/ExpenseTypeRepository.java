package com.spring.miniposbackend.repository.expense;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.expense.ExpenseType;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Integer>{
	
	@Query(value = "select bc from ExpenseType bc where bc.branch.id = ?1 and bc.enable = ?2")
	List<ExpenseType> findByBranchIdWithEnable(Integer branchId, boolean enable);
}
