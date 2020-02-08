package com.spring.miniposbackend.controller.admin;

import java.math.BigDecimal;
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

import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.service.admin.ItemService;

@RestController
@RequestMapping("item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@GetMapping("active")
	public List<Item> getAllActive(){
		return itemService.showActiveOnly();
	}
	
	@GetMapping("{itemId}")
	public Item get(@PathVariable Long itemId) {
		return itemService.show(itemId);
	}
	
	@GetMapping("by-branch")
	public List<Item> getByBranch(@RequestParam Integer branchId){
		return itemService.showByBranchId(branchId,true);
	}
	@GetMapping("by-type")
	public List<Item> getByItem(@RequestParam Integer itemTypeId){
		return itemService.showByItemTypeId(itemTypeId,true);
	}
	
	@GetMapping("by-branch-type")
	public List<Item> getByBranchItem(@RequestParam Integer branchId,@RequestParam Integer itemTypeId){
		return itemService.showByBranchItemTypeId(branchId, itemTypeId, true);
	}
	
	@PostMapping
	public Item create(@RequestParam Integer branchId,@RequestParam Integer itemTypeId,@RequestBody Item item) {
		return itemService.create(branchId, itemTypeId,item);
	}
	
	@PutMapping("{itemId}")
	public Item update(@PathVariable Long itemId,@RequestParam Integer branchId,@RequestParam Integer itemTypeId,@RequestBody Item item) {
		return itemService.update(itemId, branchId, itemTypeId, item);
	}
	
	@PatchMapping("{itemId}/patch-price")
	public Item updatePrice(@PathVariable Long itemId,@RequestParam BigDecimal price) {
		return itemService.setPrice(itemId, price);
	}

	@PatchMapping("{itemId}/patch-discount")
	public Item updateDiscount(@PathVariable Long itemId,@RequestParam Short discount) {
		return itemService.setDiscount(itemId, discount);
	}

	@PatchMapping("{itemId}/patch-enable")
	public Item updateEnable(@PathVariable Long itemId,@RequestParam boolean enable) {
		return itemService.setEnable(itemId, enable);
	}

	
}
