package com.spring.miniposbackend.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.miniposbackend.model.AuditModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin_users")
@Setter @Getter
public class AdminUser extends AuditModel{

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "name", nullable = false, length = 128)
    private String name;
	
	@Column(name = "email", nullable = false, unique = true)
    private String email;
	
	@Column(name = "password", nullable = false)
    private String password;
}
