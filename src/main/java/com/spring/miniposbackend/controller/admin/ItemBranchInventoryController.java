package com.spring.miniposbackend.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.ItemBranchInventoryView;
import com.spring.miniposbackend.service.admin.ItemBranchInventoryService;

@RestController
@RequestMapping("item-inventory")
public class ItemBranchInventoryController {

	@Autowired
	private ItemBranchInventoryService itembranchinventoryService;
	
	@PostMapping
	public SuccessResponse insert(@RequestBody List<ItemBranchInventoryView> itembranchinventoryview,@RequestParam("itembranchId") Long itembranchId) {
		return new SuccessResponse("00", "fetch image", itembranchinventoryService.insert(itembranchinventoryview,itembranchId));
	}
}
