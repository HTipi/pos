package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.modelview.ImageResponse;
//import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.service.admin.BranchService;
import com.spring.miniposbackend.util.UserProfileUtil;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//import java.util.List;

@RestController
@RequestMapping("branch")
public class BranchController {

    @Autowired
    private BranchService branchService;
    
	@Autowired
	private UserProfileUtil userProfile;

	@PostMapping("{branchId}/upload")
	public Branch uploadImage(@PathVariable Integer branchId, @RequestParam("imageFile") MultipartFile file) {
		return branchService.uploadImage(branchId, file);
	}
	
	@GetMapping("{branchId}/get-image")
	public ImageResponse getImage(@PathVariable Integer branchId) {
		return branchService.getImage(branchId);
	}
	
	@GetMapping("by-corporate")
	public SuccessResponse getByCorporateId() {
		return new SuccessResponse("00", "Branch Retrieve", branchService.showByCorpoateId(userProfile.getProfile().getCorporate().getId(), Optional.of(true)));
	}
    
//    @GetMapping
//    public List<Branch> shows() {
//        return this.branchService.shows();
//    }
//
//    @GetMapping("active")
//    public List<Branch> showAllActive() {
//        return this.branchService.showAllActive();
//    }
//
//    @GetMapping("main-branch")
//    public List<Branch> showAllActiveMainBranch(){
//        return this.branchService.showAllActiveMainBranch();
//    }
//
//    @GetMapping("{branchId}")
//    public Branch get(@PathVariable Integer branchId) {
//        return this.branchService.show(branchId);
//    }
//
//    @PostMapping
//    public Branch create(@RequestParam Integer corporateId,@RequestParam Integer addressId, @RequestBody Branch branch) {
//        return this.branchService.create(corporateId, addressId, branch);
//    }
//
//    @PutMapping
//    public Branch update(@RequestParam Integer branchId, @RequestParam Integer corporateId,@RequestParam Integer addressId,@RequestBody Branch branch){
//        return this.branchService.update(branchId, corporateId, addressId, branch);
//    }
//
//    @PatchMapping("{branchId}")
//    public Branch updateStatus(@PathVariable Integer branchId, @RequestBody boolean status) {
//        return this.branchService.updateStatus(branchId, status);
//    }

}
