package com.spring.miniposbackend.modelview;

import java.util.List;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.admin.UserRole;

public class UserResponse {

	private User user;
	private Branch branch;
	private Corporate corporate;
	private byte[] image;
	private List<UserRole> userRoles;
	private String token;
	
	public UserResponse(User user, List<UserRole> userRoles, byte[] image) {
		this.user = user;
		branch = user.getBranch();
		corporate = branch.getCorporate();
		this.userRoles = userRoles;
		this.image = image;
	}
	
	public UserResponse(User user, List<UserRole> userRoles,String token, byte[] image) {
		this.user = user;
		this.token = token;
		branch = user.getBranch();
		corporate = branch.getCorporate();
		this.userRoles = userRoles;
		this.image = image;
	}

	public Integer getUserId() {
		return user.getId();
	}

	public String getUserName() {
		return user.getUsername();
	}

	public String getCorporateName() {
		return corporate.getName();
	}

	public String getCorporateKh() {
		return corporate.getNameKh();
	}

	public String getBranchName() {
		return branch.getName();
	}
	
	public String getBranchNameKh() {
		return branch.getNameKh();
	}

	public byte[] getBranchLogo() {
		return image;
	}

	public String getToken() {
		return token;
	}

	public List<UserRole> getRoles() {
		return userRoles;
	}
	public Integer getBranchId() {
		return branch.getId();
	}
	public String getBranchTelephone() {
		return branch.getTelephone();
	}
	public String getBranchAddressDesc() {
		
		return branch.getAddressDesc();
	}
	public String getFullName() {
		
		return user.getFullName();
	}
}
