package com.spring.miniposbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.admin.BranchCurrencyService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("currency")
public class BranchCurrencyController {
	
	@Autowired
	private BranchCurrencyService branchCurrencyServie;
	@Autowired
	private UserProfileUtil userProfile;
	
	@GetMapping("by-branch")
	public SuccessResponse getByBranch(){ // will get from user
		return new SuccessResponse("00", "fetch Currency", branchCurrencyServie.showByBranchId(userProfile.getProfile().getBranch().getId(), true, true));
	} 

}
