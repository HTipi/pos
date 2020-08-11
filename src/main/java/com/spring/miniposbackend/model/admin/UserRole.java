package com.spring.miniposbackend.model.admin;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.spring.miniposbackend.model.AuditModel;

@Entity
@Table(name = "user_roles")
@Getter @Setter
public class UserRole extends AuditModel {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private UserRoleIdentity userRoleIdentity;
	
	public Role getRole() {
		return userRoleIdentity.getRole();
	}
	
	public User getUser() {
		return userRoleIdentity.getUser();
	}
}