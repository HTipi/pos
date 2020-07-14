package com.spring.miniposbackend.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.spring.miniposbackend.modelview.ImageResponse;
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
	public Item uploadImage(@PathVariable Long itemId, @RequestParam("imageFile") MultipartFile file) {
		return itemService.uploadImage(itemId, file);
	}

	@GetMapping("by-corporate")
	public SuccessResponse getByCorporateId() {
		return new SuccessResponse("00", "fetch Item",
				itemService.showByCorpoateId(userProfile.getProfile().getCorporate().getId(), Optional.of(true)));

	}

	@GetMapping("{itemId}/get-image")
	public SuccessResponse getImage(@PathVariable Long itemId) {
		return new SuccessResponse("00", "fetch Item Image", itemService.getImage(itemId));
	}

	@GetMapping("image-list")
	public SuccessResponse getImages(@RequestParam Integer corporateId) {
		return new SuccessResponse("00", "fetch Item Image List",
				itemService.getImages(userProfile.getProfile().getCorporate().getId()));
	}

	@PostMapping("image-update")
	public SuccessResponse getUpdatedImages(@Valid @RequestBody List<ImageRequest> requestImages) {
		return new SuccessResponse("00", "Update Item Image", itemService.getImages(requestImages));
	}

	@PostMapping("{itemTypeId}")
	public SuccessResponse create(@PathVariable Integer itemTypeId, @RequestBody Item item) {
		return new SuccessResponse("00", "Create Item", itemService.create(itemTypeId, item));
	}

	@PutMapping("{itemId}")
	public SuccessResponse update(@PathVariable Long itemId, @RequestBody Item item) {
		return new SuccessResponse("00", "Update Item", itemService.update(itemId, item));
	}
	@PatchMapping("delete/{itemId}")
	public SuccessResponse disable(@PathVariable Long itemId) {
		return new SuccessResponse("00", "Update Item", itemService.disable(itemId));
	}
}
