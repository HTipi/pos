package com.spring.miniposbackend.modelview;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.model.admin.User;

public class UserResponse {
	
	
	private User user;
	private Branch branch;
	private Corporate corporate;
	private byte[] image;
	
	public UserResponse(User user, byte[] image) {
		this.user = user;
		branch = user.getBranch();
		corporate = branch.getCorporate();
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

	public byte[] getBranchLogo() {
		return image;
	}
	
	public String getToken() {
		return user.getApiToken();
	}
}
