package com.spring.miniposbackend.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
//	@GetMapping
//	public List<ItemType> getAll(@RequestParam Optional<Boolean> enable){
//		if(enable.isPresent()) {
//			return itemTypeService.shows(enable.get());
//		}else {
//			return itemTypeService.shows();
//		}
//		
//	}
	
//	@GetMapping("{itemTypeId}")
//	public ItemType get(@PathVariable Integer itemTypeId) {
//		return itemTypeService.show(itemTypeId);
//	}
//	
//	@PostMapping
//	public ItemType create(@RequestBody ItemType itemType) {
//		return itemTypeService.create(itemType);
//	}
//	
//	@PutMapping("{itemTypeId}")
//	public ItemType update(@PathVariable Integer itemTypeId, @RequestBody ItemType itemType) {
//		return itemTypeService.update(itemTypeId, itemType);
//	}
//	
//	@PatchMapping("{itemTypeId}/patch-enable")
//	public ItemType updateEnable(@PathVariable Integer itemTypeId,@RequestParam boolean enable) {
//		return itemTypeService.setEnable(itemTypeId, enable);
//	}
}
