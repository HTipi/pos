package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.admin.BranchSetting;
import com.spring.miniposbackend.service.admin.BranchSettingService;
import com.spring.miniposbackend.util.UserProfileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("branch-setting")
public class BranchSettingController {
	
	@Autowired
	private BranchSettingService branchSettingService;
	@Autowired
	private UserProfileUtil userProfile;
	
	@GetMapping("by-branch")
	public List<BranchSetting> getByBranchId(){
		return branchSettingService.showByBranchId(userProfile.getProfile().getBranch().getId(),true);
	}
}
