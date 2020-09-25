package com.spring.miniposbackend.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.admin.SettingService;

@RestController
@RequestMapping("setting")
public class SettingController {

	@Autowired
	private SettingService settingService;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getAll(){
		return new SuccessResponse("00", "fetch setting by branch", settingService.showByAll());
	}
	/*
	 * @PostMapping public SuccessResponse create(@RequestBody Setting requestItem)
	 * { return new SuccessResponse("00", "Setting create",
	 * settingService.create(requestItem,
	 * userProfile.getProfile().getUser().getId())); }
	 * 
	 * @PutMapping("{settingId}") public SuccessResponse update(@PathVariable
	 * Integer settingId, @RequestBody Setting setting) { return new
	 * SuccessResponse("00", "Setting updated", settingService.update(settingId,
	 * setting, userProfile.getProfile().getUser().getId()));
	 * 
	 * }
	 * 
	 * @PatchMapping("delete/{settingId}") public SuccessResponse
	 * delete(@PathVariable Integer settingId) { return new SuccessResponse("00",
	 * "Setting disabled", settingService.delete(settingId,
	 * userProfile.getProfile().getUser().getId()));
	 * 
	 * }
	 */
	
}
