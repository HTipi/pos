package com.spring.miniposbackend.model.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.admin.UserRole;

public class CustomUserDetail implements UserDetails {
	String ROLE_PREFIX = "ROLE_";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private List<UserRole> userRoles;
	private Branch branch;
	private Corporate corporate;
	

	public CustomUserDetail(User user, Branch branch, Corporate corporate, List<UserRole> userRoles) {
		this.user = user;
		this.userRoles = userRoles;
		this.branch = branch;
		this.corporate = corporate;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
		userRoles.forEach((userRole) -> {
			authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole.getRole().getName()));
		});
		return authorities;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !user.isLock();
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnable();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	@JsonIgnore
	public Branch getBranch() {
		return branch;
	}
	
	@JsonIgnore
	public Corporate getCorporate() {
		return corporate;
	}
	
	@JsonIgnore
	public List<UserRole> getUserRole() {
		return userRoles;
	}
}
