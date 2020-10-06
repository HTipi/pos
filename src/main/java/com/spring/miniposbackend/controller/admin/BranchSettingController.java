package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;
import com.spring.miniposbackend.service.admin.BranchSettingService;
import com.spring.miniposbackend.util.UserProfileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("branch-setting")
public class BranchSettingController {

	@Autowired
	private BranchSettingService branchSettingService;
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping("by-branch")
	public SuccessResponse getByBranchId() {
		return new SuccessResponse("00", "fetch Setting", branchSettingService.showByBranchId(userProfile.getProfile().getBranch().getId(), Optional.empty()));
	}
	@PatchMapping
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse modifyEnable(@RequestParam Integer branchId, @RequestParam Integer settingId,
			@RequestParam Boolean enable) {
		return new SuccessResponse("00", "Record is updated",
				branchSettingService.updateEnable(branchId, settingId, enable));
	}
	@PatchMapping("{settingId}/set-value")
	@PreAuthorize("hasAnyRole('BRANCH','OWNER')")
	public SuccessResponse modifySettingValue(@PathVariable Integer settingId,
			@RequestParam Boolean enable) {
		return new SuccessResponse("00", "Record is updated",
				branchSettingService.updateSettingValue(userProfile.getProfile().getBranch().getId(), settingId, enable));
	}
}


