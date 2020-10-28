package com.spring.miniposbackend.model.security;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.admin.User;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ClientAppUserIdentity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@ManyToOne
	@JoinColumn(name = "client_application_id", nullable = false)
	@JsonIgnore
	private ClientApplication clientApplication;

	public ClientAppUserIdentity() {
		this.clientApplication = null;
		user = null;
	}

	public ClientAppUserIdentity(ClientApplication clientApplication, User user) {
		this.clientApplication = clientApplication;
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ClientAppUserIdentity))
			return false;
		ClientAppUserIdentity that = (ClientAppUserIdentity) obj;
		return Objects.equals(getClientApplication(), that.getClientApplication()) && Objects.equals(getUser(), that.getUser());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClientApplication(),getUser());
	}

}
