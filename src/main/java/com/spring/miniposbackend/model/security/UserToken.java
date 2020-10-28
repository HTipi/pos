package com.spring.miniposbackend.model.security;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_tokens")
@Getter @Setter
public class UserToken extends AuditModel{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@JsonIgnore
	private ClientAppUserIdentity clientAppUserIdentity;
	
	@Column(name="api_token", nullable = true, unique = true)
    @JsonIgnore
    private String apiToken;
}
