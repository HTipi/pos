package com.spring.miniposbackend.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.service.admin.ItemBranchService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("item-branch")
public class ItemBranchController {
	
	@Autowired
	private ItemBranchService itemBranchService;
	@Autowired
	private UserProfileUtil userProfile;
	
	@GetMapping("by-branch")
	public List<ItemBranch> getByBranchId(){
		return itemBranchService.showByBranchId(userProfile.getProfile().getBranch().getId(),Optional.of(true));
	}
	
	@GetMapping("{itemBranchId}/get-image")
	public ImageResponse getImage(@PathVariable Long itemBranchId) {
		return itemBranchService.getImage(itemBranchId);
	}
	
	@GetMapping("image-list")
	public List<ImageResponse> getImages(){
		return itemBranchService.getImages(userProfile.getProfile().getBranch().getId());
	}
	
	@PostMapping("image-update")
	public List<ImageResponse> getUpdatedImages(@Valid @RequestBody List<ImageRequest> requestImages){
		return itemBranchService.getImages(requestImages);
	}

}
