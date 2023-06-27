package com.spring.miniposbackend.modelview;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.model.customer.Person;

public class UserResponse {

	private User user;
	private Branch branch;
	private Corporate corporate;
	private byte[] image;
	private List<UserRole> userRoles;
	private List<BranchPaymentChannel> paymentChannels;
	private String token;
	private byte[] imageQr;
	private Person person;
	private byte[] profile;
	
	
	public UserResponse(User user, List<UserRole> userRoles, byte[] image,byte[] imageQr) {
		this.user = user;
		this.branch = user.getBranch();
		this.corporate = branch.getCorporate();
		this.paymentChannels= branch.getBranchPaymentChannels();
		this.userRoles = userRoles;
		this.image = image;
		this.imageQr = imageQr;
	}
	public UserResponse(User user, List<UserRole> userRoles, byte[] image, byte[] imageQr, Person person,
			byte[] profile) {
		this.user = user;
		this.branch = user.getBranch();
		this.corporate = branch.getCorporate();
		this.paymentChannels = branch.getBranchPaymentChannels();
		this.userRoles = userRoles;
		this.image = image;
		this.imageQr = imageQr;
		this.person = person;
		this.profile = profile;
	}
	public UserResponse(User user, List<UserRole> userRoles,String token, byte[] image,byte[] imageQr) {
		this.user = user;
		this.branch = user.getBranch();
		this.corporate = branch.getCorporate();
		this.paymentChannels= branch.getBranchPaymentChannels();
		this.userRoles = userRoles;
		this.image = image;
		this.token = token;
		this.imageQr = imageQr;
	}
	public UserResponse(User user, List<UserRole> userRoles, String token, byte[] image, byte[] imageQr, Person person,
			byte[] profile) {
		this.user = user;
		this.branch = user.getBranch();
		this.corporate = branch.getCorporate();
		this.paymentChannels = branch.getBranchPaymentChannels();
		this.userRoles = userRoles;
		this.image = image;
		this.token = token;
		this.imageQr = imageQr;
		this.person = person;
		this.profile = profile;
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
	
	public List<BranchPaymentChannel> getBranchPaymentChannels(){
		return paymentChannels;
	}
	public byte[] getQr() {
		return imageQr;
	}
	public String getLastName() {
		if (person == null)
			return "";
		return person.getName();
	}

	public String getPersonNameKh() {
		if (person == null)
			return "";
		return person.getNameKh();
	}

	public int getSexId() {
		if (person == null)
			return 0;
		return person.getSexId();
	}

	public Date getDob() {
		if (person == null)
			return null;
		return person.getDateOfBirth();
	}

	public BigDecimal getPointExchange() {
		
		return branch.getPointExchange();
	}

	public byte[] getPersonProfile() {
		if (person == null)
			return null;
		return profile;
	}

	public String getFirstName() {
		if (person == null)
			return null;
		return person.getFirstName();
	}

}
