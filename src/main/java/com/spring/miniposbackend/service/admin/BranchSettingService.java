package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.BranchSetting;
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.BranchSettingRepository;
import com.spring.miniposbackend.repository.admin.SettingRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchSettingService {

	@Autowired
	private BranchSettingRepository settingBranchRepository;
	@Autowired
	private SettingRepository settingRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private UserProfileUtil userProfile;

	public List<BranchSetting> showByBranchId(Integer branchId, boolean enable) {
		return settingBranchRepository.findByBranchIdWithEnable(branchId, enable);
	}
	@Transactional
	public BranchSetting delete(Integer branchSettingId, Integer branchId, Integer settingId) {
		return branchRepository.findById(branchId).map((branch) -> {
			if (userProfile.getProfile().getCorporate().getId() != branch.getCorporate().getId()) {
				throw new UnauthorizedException("Branch is unauthorized", "02");
			}
			return settingRepository.findById(settingId).map((setting) -> {
				return settingBranchRepository.findById(branchSettingId).map((branchSetting) -> {
					branchSetting.setEnable(false);
					return settingBranchRepository.save(branchSetting);
				}).orElseThrow(() -> new ResourceNotFoundException("Branch Setting does not exist", "01"));
			}).orElseThrow(() -> new ResourceNotFoundException("Setting does not exist", "01"));

		}).orElseThrow(() -> new ResourceNotFoundException("branch does not exist", "01"));
	}

}
