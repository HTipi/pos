package com.spring.miniposbackend.model.admin;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;

import java.util.Date;

@Entity
@Table(name = "users")
@Setter @Getter
public class User extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;

    @Column(name = "username", nullable = false, unique = true, length = 128)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;
    
    @Column(name="api_token", nullable = true)
    private String apiToken;

    @Column(name = "telephone", nullable = false, length = 32)
    private String telephone;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @ManyToOne()
    @JoinColumn(name = "branch_id",nullable = false)
    @JsonIgnore
    private Branch branch;

    @Column(name = "lock", nullable = false)
    @ColumnDefault("false")
    private boolean lock;
    
    @Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;

}
