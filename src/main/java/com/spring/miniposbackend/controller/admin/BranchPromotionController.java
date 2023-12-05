package com.spring.miniposbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.BranchPromotionView;
import com.spring.miniposbackend.service.admin.BranchPromotionService;

@RestController
@RequestMapping("branch-promotions")
public class BranchPromotionController {
	
	@Autowired
	private BranchPromotionService branchPromotionService;
	
	@GetMapping("show")
	public SuccessResponse showBranchPromotion() {
		return new SuccessResponse("00","Create BranchPromotion successful",branchPromotionService.showBranchPromotion());
	}
	
	@PostMapping("create")
	public SuccessResponse createBranchPromotion(@RequestBody BranchPromotionView branchProView) {
		return new SuccessResponse("00","Create BranchPromotion successful",branchPromotionService.createBranchPromotion(branchProView));
	}
	
	@PutMapping("update/{branchPromotionId}")
	public SuccessResponse updateBranchPromotion(@RequestBody BranchPromotionView branchProView,@PathVariable Integer branchPromotionId) {
		return new SuccessResponse("00","Udpate BranchPromotion successful",branchPromotionService.updateBranchPromotion(branchProView, branchPromotionId));
	}
	
	@PatchMapping("delete/{branchPromotionId}")
	public SuccessResponse deleteBranchPromotion(@PathVariable Integer branchPromotionId) {
		return new SuccessResponse("00","delete BranchPromotion successful",branchPromotionService.deleteBranchPromotion(branchPromotionId));
	}
}
