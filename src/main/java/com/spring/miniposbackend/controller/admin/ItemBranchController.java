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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.service.admin.ItemBranchService;
import com.spring.miniposbackend.service.admin.ItemService;

@RestController
@RequestMapping("item")
public class ItemBranchController {
	
	@Autowired
	private ItemBranchService itemBranchService;
	@Autowired
	private ItemService itemService;
	
	@GetMapping("by-branch")
	public List<ItemBranch> getByBranchId(@RequestParam Integer branchId){
		return itemBranchService.showByBranchId(branchId,Optional.of(true));
	}
	@PostMapping("{itemId}/upload")
	public Item uploadImage(@PathVariable Long itemId, @RequestParam("imageFile") MultipartFile file) {
		return itemService.uploadImage(itemId, file);
	}
	
	@GetMapping("{itemBranchId}/get-image")
	public ImageResponse getImage(@PathVariable Long itemBranchId) {
		return itemBranchService.getImage(itemBranchId);
	}
	
	@GetMapping("image-list")
	public List<ImageResponse> getImages(@RequestParam Integer branchId){
		return itemBranchService.getImages(branchId);
	}
	
	@GetMapping("image-update")
	public List<ImageResponse> getUpdatedImages(@Valid @RequestBody List<ImageRequest> requestImages){
		return itemBranchService.getImages(requestImages);
	}

}
