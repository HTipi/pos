package com.spring.miniposbackend.model.admin;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;

@Entity
@Table(name = "users_roles")
@Getter @Setter
public class UserRole extends AuditModel {

	private static final long serialVersionUID = 1L;
	
//	@EmbeddedId
//	private UserRoleId userRoleId;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id",nullable = false)
    @JsonIgnore
    private Role role;
	
	@Column(name = "enable", nullable = false, columnDefinition = "boolean default true")
	private boolean enable;
}