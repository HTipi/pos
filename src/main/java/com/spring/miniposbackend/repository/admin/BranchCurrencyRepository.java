package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.BranchCurrency;

public interface BranchCurrencyRepository extends JpaRepository<BranchCurrency, Integer>{
	
	@Query(value = "select bc from BranchCurrency bc where bc.branch.id = ?1 and bc.currency.enable = ?2 and bc.enable = ?3")
	List<BranchCurrency> findByBranchId(Integer branchId, boolean currencyEnable, boolean enable);
}
