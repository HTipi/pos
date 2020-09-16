package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.service.admin.UserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user-role")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;

	@GetMapping("user/{userId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public List<UserRole> getRoles(@PathVariable Integer userId) {
		return userRoleService.showByUserId(userId, Optional.of(true));
	}

	@DeleteMapping("user/{userId}/role/{roleId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public UserRole delete(@PathVariable Integer userId, @PathVariable Integer roleId) {
		return userRoleService.delete(userId, roleId);
	}

	@PostMapping("user/{userId}/role/{roleId}")
	@PreAuthorize("hasAnyRole('OWNER')")
	public UserRole create(@PathVariable Integer userId, @PathVariable Integer roleId) {
		return userRoleService.create(userId, roleId);
	}
}
