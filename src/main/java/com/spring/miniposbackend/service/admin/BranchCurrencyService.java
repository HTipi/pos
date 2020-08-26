package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.repository.admin.BranchCurrencyRepository;

@Service
public class BranchCurrencyService {
	
	@Autowired
	private BranchCurrencyRepository branchCurrencyRepository;
	
	public List<BranchCurrency> showByBranchId(Integer branchId, boolean currencyEnable, boolean enable){
		return branchCurrencyRepository.findByBranchId(branchId, currencyEnable, enable);
	}

}
