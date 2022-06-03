package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.admin.BranchPaymentChannelService;
import com.spring.miniposbackend.util.UserProfileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

//import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("branch-payment")
public class BranchPaymentChannelController {

	@Autowired
	private BranchPaymentChannelService branchPaymentChannelService;

	@Autowired
	private UserProfileUtil userProfile;


	@GetMapping()
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse getByCorporateId() {

			return new SuccessResponse("00", "Branch Payment Retrieve",
					branchPaymentChannelService.showAllChannels(userProfile.getProfile().getBranch().getId()));
	}
}
