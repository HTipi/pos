//package com.spring.miniposbackend.controller.admin;
//
////import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.PatchMapping;
////import org.springframework.web.bind.annotation.PathVariable;
////import org.springframework.web.bind.annotation.PostMapping;
////import org.springframework.web.bind.annotation.PutMapping;
////import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.spring.miniposbackend.model.admin.Item;
//import com.spring.miniposbackend.service.admin.ItemService;
//
//@RestController
//@RequestMapping("item")
//public class ItemController {
//
//	@Autowired
//	private ItemService itemService;
//	
//	@GetMapping
//	public List<Item> show(@RequestParam Optional<Boolean> enable){
//		if(enable.isPresent()) {
//			return itemService.shows(enable.get());
//		}else {
//			return itemService.shows();
//		}
//	}
//	
//	@GetMapping("by-corporate")
//	public List<Item> getCorporate(@RequestParam Integer corporateId){
//		return itemService.showByCorpoateId(corporateId, true);
//	}
//	
////	@GetMapping("{itemId}")
////	public Item get(@PathVariable Integer itemId) {
////		return itemService.show(itemId);
////	}
////	
////	@GetMapping("by-type")
////	public List<Item> getByItemType(@RequestParam Integer itemTypeId){
////		return itemService.showByItemTypeId(itemTypeId,true);
////	}
////	
////	@PostMapping
////	public Item create(@RequestParam Integer branchId,@RequestParam Integer itemTypeId,@RequestBody Item item) {
////		return itemService.create(itemTypeId,item);
////	}
////	
////	@PutMapping("{itemId}")
////	public Item update(@PathVariable Integer itemId,@RequestParam Integer itemTypeId,@RequestBody Item item) {
////		return itemService.update(itemId, itemTypeId, item);
////	}
////	
////	@PatchMapping("{itemId}/patch-price")
////	public Item updatePrice(@PathVariable Integer itemId,@RequestParam BigDecimal price) {
////		return itemService.setPrice(itemId, price);
////	}
////
////	@PatchMapping("{itemId}/patch-discount")
////	public Item updateDiscount(@PathVariable Integer itemId,@RequestParam Short discount) {
////		return itemService.setDiscount(itemId, discount);
////	}
////
////	@PatchMapping("{itemId}/patch-enable")
////	public Item updateEnable(@PathVariable Integer itemId,@RequestParam boolean enable) {
////		return itemService.setEnable(itemId, enable);
////	}
//
//	
//}
