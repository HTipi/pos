package com.spring.miniposbackend.model.admin;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserRoleIdentity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	@JsonIgnore
	private Role role;

	public UserRoleIdentity() {
		user = null;
		role = null;
	}

	public UserRoleIdentity(User user, Role role) {
		this.user = user;
		this.role = role;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserRoleIdentity)) return false;
        UserRoleIdentity that = (UserRoleIdentity) obj;
        return Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getRole(), that.getRole());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getRole());
    }
}
