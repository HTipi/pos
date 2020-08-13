package com.spring.miniposbackend.service.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.model.admin.UserRoleIdentity;
import com.spring.miniposbackend.repository.admin.RoleRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.admin.UserRoleRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class UserRoleService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private UserProfileUtil userProfile;

	public List<UserRole> showByUserId(Integer userId, Optional<Boolean> enable) {
		return userRepository.findById(userId).map((user) -> {
			if (userProfile.getProfile().getCorporate().getId() != user.getBranch().getCorporate().getId()) {
				throw new UnauthorizedException("You are not authorized");
			}
			if (enable.isPresent()) {
				return userRoleRepository.findByUserRoleIdentityUserId(userId, enable.get());
			} else {
				return userRoleRepository.findByUserRoleIdentityUserId(userId);
			}
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
	}

	public UserRole create(Integer userId, Integer roleId) {
		return userRepository.findById(userId).map((user) -> {
			if (userProfile.getProfile().getCorporate().getId() != user.getBranch().getCorporate().getId()) {
				throw new UnauthorizedException("You are not authorized");
			}
			return roleRepository.findById(roleId).map((role) -> {
				UserRole userRole = new UserRole();
				userRole.setUserRoleIdentity(new UserRoleIdentity(user, role));
				return userRoleRepository.save(userRole);
			}).orElseThrow(() -> new ResourceNotFoundException("Role does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
	}

	public UserRole delete(Integer userId, Integer roleId) {
		return userRepository.findById(userId).map((user) -> {
			if (userProfile.getProfile().getCorporate().getId() != user.getBranch().getCorporate().getId()) {
				throw new UnauthorizedException("You are not authorized");
			}
			return roleRepository.findById(roleId).map((role) -> {
				return userRoleRepository.findById(new UserRoleIdentity(user, role)).map((userRole) -> {
					userRoleRepository.delete(userRole);
					return userRole;
				}).orElseThrow(() -> new ResourceNotFoundException("User Role does not exist"));

			}).orElseThrow(() -> new ResourceNotFoundException("Role does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
	}
}
