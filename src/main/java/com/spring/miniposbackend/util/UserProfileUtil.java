package com.spring.miniposbackend.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.spring.miniposbackend.model.security.CustomUserDetail;

@Component
public class UserProfileUtil {

	public CustomUserDetail getProfile() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (CustomUserDetail) auth.getPrincipal();
	}
}
