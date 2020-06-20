package com.spring.miniposbackend.repository.expense;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import com.spring.miniposbackend.model.expense.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer>{
	
	@Query(value = "select bc from Expense bc where bc.branch.id = ?1")
	List<Expense> findByBranchId(Integer branchId);
	
	@Query(value = "select bc from Expense bc where bc.branch.id = ?1 and date_trunc('day',value_date) between ?2 and ?3")
	List<Expense> findByBranchIdWithDate(Integer branchId,@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate);
}
