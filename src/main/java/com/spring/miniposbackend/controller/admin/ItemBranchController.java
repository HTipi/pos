package com.spring.miniposbackend.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.service.admin.ItemBranchService;

@RestController
@RequestMapping("item")
public class ItemBranchController {
	
	@Autowired
	private ItemBranchService itemBranchService;
	
	@GetMapping("by-branch")
	public List<ItemBranch> getByBranchId(@RequestParam Integer branchId){
		return itemBranchService.showByBranchId(branchId,Optional.of(true));
	}

}
