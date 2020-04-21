package com.spring.miniposbackend.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.repository.admin.RoleRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.spring.miniposbackend.model.admin.User user = userRepository.findFirstByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND"));
		String roleName = roleRepository.findById(user.getRole().getId()).orElse(null).getName();
		UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword())
				.disabled(!user.isEnable()).accountLocked(user.isLock()).roles(roleName).build();
		return userDetails;

	}
}