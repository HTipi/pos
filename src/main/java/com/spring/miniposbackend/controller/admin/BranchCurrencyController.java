package com.spring.miniposbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.admin.BranchCurrencyService;
import com.spring.miniposbackend.util.UserProfileUtil;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("currency")
public class BranchCurrencyController {
	
	@Autowired
	private BranchCurrencyService branchCurrencyServie;
	@Autowired
	private UserProfileUtil userProfile;
	@PreAuthorize("hasAnyRole('OWNER','SALE','BRANCH')")
	@GetMapping("by-branch")
	public SuccessResponse getByBranch(){ // will get from user
		return new SuccessResponse("00", "fetch Currency", branchCurrencyServie.showByBranchId(userProfile.getProfile().getBranch().getId(), true, true));
	} 
        @PreAuthorize("hasAnyRole('OWNER','BRANCH')")
        @PatchMapping("{currencyId}/set-rate")
        public SuccessResponse changeRate(@PathVariable Integer currencyId, @RequestParam BigDecimal rate){
            return new SuccessResponse("00", "fetch Currency", branchCurrencyServie.changeRate(currencyId, rate));
        }

}
