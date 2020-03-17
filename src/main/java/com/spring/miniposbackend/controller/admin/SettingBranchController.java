package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.SettingBranch;
import com.spring.miniposbackend.service.admin.ItemBranchService;
import com.spring.miniposbackend.service.admin.SettingBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("setting")
public class SettingBranchController {
	
	@Autowired
	private SettingBranchService settingBranchService;
	
	@GetMapping("by-branch")
	public List<SettingBranch> getByBranchId(@RequestParam Integer branchId){
		return settingBranchService.showByBranchId(branchId);
	}

}
