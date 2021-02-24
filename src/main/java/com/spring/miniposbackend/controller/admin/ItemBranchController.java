package com.spring.miniposbackend.controller.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ItemBranchCheckList;
import com.spring.miniposbackend.modelview.ItemBranchUpdate;
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
	public SuccessResponse getByBranchId() {
		return new SuccessResponse("00", "fetch item branch",
				itemBranchService.showByBranchId(userProfile.getProfile().getBranch().getId(), Optional.of(true)));
	}

	@GetMapping("{itemBranchId}/get-image")
	public SuccessResponse getImage(@PathVariable Long itemBranchId) {
		return new SuccessResponse("00", "fetch image", itemBranchService.getImage(itemBranchId));
	}

	@GetMapping("{itemId}/get-item-checklist")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getItemCheckList(@PathVariable Long itemId) {
		return new SuccessResponse("00", "fetch image", itemBranchService.showByItemCheckListId(itemId));
	}

	@PostMapping("item-checklist")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse create(@RequestBody List<ItemBranchCheckList> checkLists) {
		return new SuccessResponse("00", "check List ItemBranch", itemBranchService.doCheckList(checkLists));
	}

	@GetMapping("image-list")
	public SuccessResponse getImages() {
		return new SuccessResponse("00", "fetch images",
				itemBranchService.getImages(userProfile.getProfile().getBranch().getId()));
	}

	@PostMapping("image-update")
	@PreAuthorize("hasAnyRole('BRANCH')")
	public SuccessResponse getUpdatedImages(@Valid @RequestBody List<ImageRequest> requestImages) {
		return new SuccessResponse("00", "Image updated", itemBranchService.getImages(requestImages));
	}

	@PostMapping("image-list")
	public SuccessResponse getImagesList(@RequestBody List<ImageRequest> requestImages) {
		return new SuccessResponse("00", "fetch Image Item List", itemBranchService.getImagesFromList(requestImages));
	}

	@PatchMapping("{itemBranchId}/set-price")
	@PreAuthorize("hasAnyRole('BRANCH','OWNER')")
	public SuccessResponse setPrice(@PathVariable Long itemBranchId, @RequestParam BigDecimal price,
			@RequestParam Short discount) {
		return new SuccessResponse("00", "update Price Discount",
				itemBranchService.setPrice(itemBranchId, price, discount));
	}

	@PatchMapping("{itemBranchId}/set-discount")
	@PreAuthorize("hasAnyRole('BRANCH','OWNER')")
	public ItemBranch setDiscount(@PathVariable Long itemBranchId, @RequestParam Short discount) {
		return itemBranchService.setDiscount(itemBranchId, discount);
	}
	/*
	 * Admin Controller
	 */

	@GetMapping("{branchId}/by-branch")
	@PreAuthorize("hasAnyRole('OWNER')")
	public List<ItemBranch> byBranch(@PathVariable Integer branchId) {
		return itemBranchService.showByBranchId(branchId, Optional.empty());
	}

	@GetMapping("refresh")
	@PreAuthorize("hasAnyRole('OWNER')")
	public void refresh() {
		itemBranchService.refresh();
	}

	@PatchMapping("{itemBranchId}/set-enable")
	@PreAuthorize("hasAnyRole('OWNER')")
	public ItemBranch setEnable(@PathVariable Long itemBranchId, @RequestParam Boolean enable) {
		return itemBranchService.setEnable(itemBranchId, enable);
	}

	@PutMapping("{itemBranchId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public ItemBranch update(@PathVariable Long itemBranchId, @RequestBody ItemBranchUpdate itemBranch) {
		return itemBranchService.update(itemBranchId, itemBranch);
	}
	
	@PatchMapping("{itemBranchId}/update-add-on")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse updateAddOn(@PathVariable Long itemBranchId, @RequestBody List<Long> addOnItems) {
		return new SuccessResponse("00", "update Sub Item",
				itemBranchService.updateAddOn(itemBranchId, addOnItems));
	}

}
