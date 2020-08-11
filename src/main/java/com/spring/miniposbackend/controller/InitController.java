package com.spring.miniposbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.model.security.CustomUserDetail;
import com.spring.miniposbackend.service.admin.ItemTypeService;
import com.spring.miniposbackend.service.admin.SeatService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("init")
public class InitController {

	@Autowired
	private ItemTypeService itemTypeService;
	@Autowired
	private SeatService seatService;
	@Autowired
	private UserProfileUtil userProfile;
	
	@GetMapping("/me")
	public CustomUserDetail getMe() {
		return userProfile.getProfile();
	}
	
	@GetMapping
	public Map<String, Object> init(){
//		int userId=1;
		int branchId = 1;
		int corporateId = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		// load itemType along with item;
		// load branch along with seat
		
		List<ItemType> itemTypes = itemTypeService.showByCorporateId(corporateId, Optional.of(true));
		List<Seat> seats = seatService.showByBranchId(branchId);
		map.put("itemTypes", itemTypes);
		map.put("seats", seats);
		return map;
	}
}
