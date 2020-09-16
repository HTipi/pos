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
	
	@GetMapping("/test")
	public void getTest() throws InterruptedException {
		CustomUserDetail user = userProfile.getProfile();
		int i;
		for(i=0;i<10;i++) {
			System.out.println(user.getUsername());
			Thread.sleep(5 * 1000);
		}
		
	}
	
	@GetMapping("/me")
	public  CustomUserDetail getMe() throws InterruptedException {
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
