package com.spring.miniposbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.service.admin.ItemTypeService;
import com.spring.miniposbackend.service.admin.SeatService;

@RestController
@RequestMapping("init")
public class InitController {

	@Autowired
	private ItemTypeService itemTypeService;
	@Autowired
	private SeatService seatService;
	
	@GetMapping
	public Map<String, Object> init(){
//		int userId=1;
		int branchId = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		// load itemType along with item;
		// load branch along with seat
		
		List<ItemType> itemTypes = itemTypeService.shows(true);
		List<Seat> seats = seatService.showByBranchId(branchId);
		map.put("itemTypes", itemTypes);
		map.put("seats", seats);
		return map;
	}
}
