package com.spring.miniposbackend.model.admin;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;

@Entity
@Table(name = "user_roles")
@Getter @Setter
public class UserRole extends AuditModel {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@JsonIgnore
	private UserRoleIdentity userRoleIdentity;
	
	@JsonIgnore
	public Role getRole() {
		return userRoleIdentity.getRole();
	}
	@JsonIgnore
	public User getUser() {
		return userRoleIdentity.getUser();
	}
	
	public Integer getRoleId() {
		return userRoleIdentity.getRole().getId(); 
	}
	public String getRoleName() {
		return userRoleIdentity.getRole().getName();
	}
}