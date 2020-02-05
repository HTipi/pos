package com.spring.miniposbackend.controller.admin;

import java.util.List;

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

import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.service.admin.ItemTypeService;

@RestController
@RequestMapping("item-type")
public class ItemTypeController {

	@Autowired
	private ItemTypeService itemTypeService;
	
	@GetMapping
	public List<ItemType> getItemTypes(){
		return itemTypeService.shows();
	}
	
	@GetMapping("active")
	public List<ItemType> getActiveItemTypes(){
		return itemTypeService.showActiveOnly();
	}
	
	@GetMapping("{itemTypeId}")
	public ItemType getItemType(@PathVariable Integer itemTypeId) {
		return itemTypeService.show(itemTypeId);
	}
	
	@PostMapping
	public ItemType createItemType(@RequestBody ItemType itemType) {
		return itemTypeService.create(itemType);
	}
	
	@PutMapping("{itemTypeId}")
	public ItemType updateItemType(@PathVariable Integer itemTypeId, @RequestBody ItemType itemType) {
		return itemTypeService.update(itemTypeId, itemType);
	}
	
	@PatchMapping("{itemTypeId}")
	public ItemType updateEnable(@PathVariable Integer itemTypeId,@RequestParam boolean enable) {
		return itemTypeService.setEnable(itemTypeId, enable);
	}
}
