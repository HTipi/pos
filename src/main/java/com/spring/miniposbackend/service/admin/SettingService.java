package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.Setting;
import com.spring.miniposbackend.repository.admin.SettingRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class SettingService {

	@Autowired
	private SettingRepository settingRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserProfileUtil userProfile;

	@Transactional(readOnly = true)
	public List<Setting> showByAll() {
		return settingRepository.findAll();
	}
	/*
	 * @Transactional public Setting create(Setting requestItem, Integer userId) {
	 * return userRepository.findById(userId).map((user) -> { Setting setting = new
	 * Setting(); setting.setCode(requestItem.getCode());
	 * setting.setName(requestItem.getName());
	 * setting.setSequence(requestItem.getSequence()); setting.setEnable(true);
	 * return settingRepository.save(setting); }).orElseThrow(() -> new
	 * ResourceNotFoundException("user does not exist", "01"));
	 * 
	 * }
	 * 
	 * @Transactional public Setting update(Integer settingId, Setting set, Integer
	 * userId) { return settingRepository.findById(settingId).map((setting) -> {
	 * return userRepository.findById(userId).map((user) -> { if
	 * (userProfile.getProfile().getBranch().getId() != user.getBranch().getId()) {
	 * throw new UnauthorizedException("Setting is unauthorized", "02"); }
	 * setting.setName(set.getName()); setting.setSequence(set.getSequence());
	 * setting.setCode(set.getCode()); return settingRepository.save(setting);
	 * }).orElseThrow(() -> new ResourceNotFoundException("User does not exist",
	 * "01")); }).orElseThrow(() -> new
	 * ResourceNotFoundException("Setting does not exist", "01"));
	 * 
	 * }
	 * 
	 * @Transactional public Setting delete(Integer settingId, Integer userId) {
	 * return settingRepository.findById(settingId).map((setting) -> { return
	 * userRepository.findById(userId).map((user) -> { if
	 * (userProfile.getProfile().getBranch().getId() != user.getBranch().getId()) {
	 * throw new UnauthorizedException("Setting is unauthorized", "02"); }
	 * setting.setEnable(false); return settingRepository.save(setting);
	 * }).orElseThrow(() -> new ResourceNotFoundException("User does not exist",
	 * "01")); }).orElseThrow(() -> new
	 * ResourceNotFoundException("Setting does not exist", "01"));
	 * 
	 * }
	 */
}
