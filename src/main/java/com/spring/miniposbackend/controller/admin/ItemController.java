package com.spring.miniposbackend.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	public List<Item> getByCorporateId(){
		return itemService.showByCorpoateId(userProfile.getProfile().getCorporate().getId(), Optional.of(true));
	}
	
	@GetMapping("{itemId}/get-image")
	public ImageResponse getImage(@PathVariable Long itemId) {
		return itemService.getImage(itemId);
	}
	
	@GetMapping("image-list")
	public List<ImageResponse> getImages(@RequestParam Integer corporateId){
		return itemService.getImages(userProfile.getProfile().getCorporate().getId());
	}
	
	@PostMapping("image-update")
	public List<ImageResponse> getUpdatedImages(@Valid @RequestBody List<ImageRequest> requestImages){
		return itemService.getImages(requestImages);
	}
	
	@PostMapping("{itemTypeId}")
	public Item create(@PathVariable Integer itemTypeId,@RequestBody Item item) {
		return itemService.create(itemTypeId, item);
	}
	
	@PutMapping("{itemId}")
	public Item update(@PathVariable Long itemId,@RequestBody Item item) {
		return itemService.update(itemId, item);
	}
}
