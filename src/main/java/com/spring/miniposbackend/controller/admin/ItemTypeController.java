package com.spring.miniposbackend.controller.admin;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.spring.miniposbackend.service.admin.ItemTypeService;

@RestController
@RequestMapping("item-type")
public class ItemTypeController {

	@Autowired
	private ItemTypeService itemTypeService;
	
	@GetMapping("by-corporate")
	public List<ItemType> getByCorporate(@RequestParam Integer corporateId){ // will get from user
		return itemTypeService.showByCorporateId(corporateId, Optional.of(true));
	}
	
	@PostMapping("{itemTypeId}/upload")
	public BodyBuilder uploadImage(@PathVariable Integer itemTypeId, @RequestParam("imageFile") MultipartFile file) {
		if( itemTypeService.uploadImage(itemTypeId, file)) {
			return ResponseEntity.status(HttpStatus.OK);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{itemTypeId}/get-image")
	public String getImage(@PathVariable Integer itemTypeId) {
		return Base64.getEncoder().encodeToString(itemTypeService.getImage(itemTypeId));
	}
}
