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
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.service.admin.ItemTypeService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("item-type")
public class ItemTypeController {

	@Autowired
	private ItemTypeService itemTypeService;
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping("by-corporate")
	@PreAuthorize("hasAnyRole('OWNER','USER')")
	public SuccessResponse getByCorporate() { // will get from user

		return new SuccessResponse("00", "fetch Item Type",
				itemTypeService.showByCorporateId(userProfile.getProfile().getCorporate().getId(), Optional.of(true)));
	}
	@PostMapping
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse create(@RequestBody ItemType itemType) {
		return new SuccessResponse("00", "Create Item Type",
				itemTypeService.create(userProfile.getProfile().getCorporate().getId(), itemType));
	}
	@PutMapping("{itemTypeId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse update(@PathVariable Integer itemTypeId, @RequestBody ItemType itemType) {
		return new SuccessResponse("00", "Update Item Type", itemTypeService.update(itemTypeId, itemType));
	}
	@PatchMapping("delete/{itemTypeId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse disable(@PathVariable Integer itemTypeId) {
		return new SuccessResponse("00", "Disable Item Type", itemTypeService.disable(itemTypeId));
	}

	@PatchMapping("{itemTypeId}/set-image/{imageId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse updateImage(@PathVariable Integer itemTypeId, @PathVariable UUID imageId) {
		return new SuccessResponse("00", "update Image Item Type", itemTypeService.updateImage(itemTypeId, imageId));
	}
	
	@PostMapping("{itemTypeId}/upload") /// Need to validate
	@PreAuthorize("hasAnyRole('OWNER')")
	public ItemType uploadImage(@PathVariable Integer itemTypeId, @RequestParam("imageFile") MultipartFile file) {
		return itemTypeService.uploadImage(itemTypeId, file);
	}

	@GetMapping("{itemTypeId}/get-image")
	@PreAuthorize("hasAnyRole('OWNER','USER')")
	public ImageResponse getImage(@PathVariable Integer itemTypeId) {
		return itemTypeService.getImage(itemTypeId);
	}

	@GetMapping("image-list")
	@PreAuthorize("hasAnyRole('OWNER','USER')")
	public List<ImageResponse> getImages(@RequestParam Integer corporateId) {
		return itemTypeService.getImages(userProfile.getProfile().getCorporate().getId());
	}

	@PostMapping("image-update")
	@PreAuthorize("hasAnyRole('OWNER')")
	public List<ImageResponse> getUpdatedImages(@Valid @RequestBody List<ImageRequest> requestImages) {
		return itemTypeService.getImages(requestImages);
	}
	
	@PostMapping("image-list")
	public SuccessResponse getImagesList(@RequestBody List<ImageRequest> requestImages) {
		return new SuccessResponse("00", "fetch Image List ItemType", itemTypeService.getImagesFromList(requestImages));
	}
}
