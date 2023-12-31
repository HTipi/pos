package com.spring.miniposbackend.service.security;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.model.security.CustomUserDetail;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.admin.UserRoleRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return userRepository.findFirstByUsername(username).map((user) -> {
			Branch branch = user.getBranch();
			Corporate corporate = branch.getCorporate();
			List<UserRole> userRoles = userRoleRepository.findByUserRoleIdentityUserId(user.getId(),true);
			return new CustomUserDetail(user, branch, corporate, userRoles);
		}).orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND"));
		// String roleName =
		// roleRepository.findById(user.getRole().getId()).orElse(null).getName();
//		UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword())
//				.disabled(!user.isEnable()).accountLocked(user.isLock()).roles(roleName).build();
//		return userDetails;

	}
}