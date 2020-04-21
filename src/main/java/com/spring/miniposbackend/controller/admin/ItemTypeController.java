package com.spring.miniposbackend.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.service.admin.ItemTypeService;

@RestController
@RequestMapping("item-type")
public class ItemTypeController {

	@Autowired
	private ItemTypeService itemTypeService;
	
	@PostMapping("by-corporate")
	public List<ItemType> getByCorporate(@RequestParam("cop") Integer corporateId){ // will get from user
		return itemTypeService.showByCorporateId(corporateId, Optional.of(true));
	}
	
	@PostMapping("{itemTypeId}/upload")
	public ItemType uploadImage(@PathVariable Integer itemTypeId, @RequestParam("imageFile") MultipartFile file) {
		return itemTypeService.uploadImage(itemTypeId, file);
	}
	
	@GetMapping("{itemTypeId}/get-image")
	public ImageResponse getImage(@PathVariable Integer itemTypeId) {
		return itemTypeService.getImage(itemTypeId);
	}
	
	@GetMapping("image-list")
	public List<ImageResponse> getImages(@RequestParam Integer corporateId){
		return itemTypeService.getImages(corporateId);
	}
	
	@GetMapping("image-update")
	public List<ImageResponse> getUpdatedImages(@Valid @RequestBody List<ImageRequest> requestImages){
		return itemTypeService.getImages(requestImages);
	}
	
}
