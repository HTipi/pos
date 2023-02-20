package com.spring.miniposbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.security.CustomUserDetail;
import com.spring.miniposbackend.service.admin.InitService;
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
	@Autowired
	private InitService initService;
	
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
	public SuccessResponse init() throws Exception {
		return new SuccessResponse("00", "fetch Init", initService.showInitHorPao());
		
	}
}
