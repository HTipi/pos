package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.admin.ItemBranchPromotionService;
import com.spring.miniposbackend.util.UserProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

//import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("branch-promotion")
public class ItemBranchPromotionController {

	@Autowired
	private ItemBranchPromotionService branchPaymentChannelService;

	@Autowired
	private UserProfileUtil userProfile;


	@GetMapping("by-branch")
	public SuccessResponse getByBranchId() {

			return new SuccessResponse("00", "Branch Payment Retrieve",
					branchPaymentChannelService.showAllPromotionByBranch(userProfile.getProfile().getBranch().getId()));
	}
	@GetMapping("by-item-branch")
	public SuccessResponse getByItemBranchId(@RequestParam Long itemBranchId) {

			return new SuccessResponse("00", "Branch Payment Retrieve",
					branchPaymentChannelService.showAllPromotionById(itemBranchId));
	}
}
