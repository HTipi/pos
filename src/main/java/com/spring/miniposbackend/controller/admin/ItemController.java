package com.spring.miniposbackend.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.service.admin.ItemService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private UserProfileUtil userProfile;

	@PostMapping("{itemId}/upload")
	@PreAuthorize("hasAnyRole('OWNER')")
	public Item uploadImage(@PathVariable Long itemId, @RequestParam("imageFile") MultipartFile file) {
		return itemService.uploadImage(itemId, file);
	}
	
	@PatchMapping("{itemId}/set-image/{imageId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse updateImage(@PathVariable Long itemId, @PathVariable UUID imageId) {
		return new SuccessResponse("00", "update Image Item", itemService.updateImage(itemId, imageId));
	}

	@GetMapping("by-corporate")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getByCorporateId() {
		return new SuccessResponse("00", "fetch Item",
				itemService.showByCorpoateId(userProfile.getProfile().getCorporate().getId(), Optional.of(true)));

	}

	@GetMapping("{itemId}/get-image")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getImage(@PathVariable Long itemId) {
		return new SuccessResponse("00", "fetch Item Image", itemService.getImage(itemId));
	}

	@GetMapping("image-list")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getImages(@RequestParam Integer corporateId) {
		return new SuccessResponse("00", "fetch Item Image List",
				itemService.getImages(userProfile.getProfile().getCorporate().getId()));
	}

	@PostMapping("image-update")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getUpdatedImages(@Valid @RequestBody List<ImageRequest> requestImages) {
		return new SuccessResponse("00", "Update Item Image", itemService.getImages(requestImages));
	}

	@PostMapping("{itemTypeId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse create(@PathVariable Integer itemTypeId, @RequestBody Item item) {
		return new SuccessResponse("00", "Create Item", itemService.create(itemTypeId, item));
	}

	@PutMapping("{itemId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse update(@PathVariable Long itemId, @RequestBody Item item) {
		return new SuccessResponse("00", "Update Item", itemService.update(itemId, item));
	}
	@PatchMapping("delete/{itemId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse disable(@PathVariable Long itemId) {
		return new SuccessResponse("00", "Update Item", itemService.disable(itemId));
	}
}
