package com.spring.miniposbackend.model.admin;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.customer.Person;

import java.util.Date;

@Entity
@Table(name = "users")
@Setter @Getter
public class User extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;

    @Column(name = "username", nullable = false, unique = true, length = 128)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Transient
    private String confirmPassword;
    
    @Column(name="api_token", nullable = true)
    @JsonIgnore
    private String apiToken;

    @Column(name = "telephone", nullable = false, length = 32)
    private String telephone;

    @Column(name = "date_of_birth", nullable = true)
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
    
	@ManyToOne()
	@JoinColumn(name = "person_id", nullable = true)
	@JsonIgnore
	private Person person;
	
	@Column(name = "reset_date", nullable = true)
	@ColumnDefault("19991231")
	private Integer resetDate;

	@Column(name = "time_count", nullable = true)
	@ColumnDefault("0")
	private Integer timeCount;

	@Column(name = "one_time_code", nullable = true, unique = true)
	@JsonIgnore
	private Integer oneTimePasswordCode;

	@Column(name = "expiry_date_time", nullable = true)
	private Date expiry;

	@Column(name = "password_reset", nullable = true)
	@JsonIgnore
	private String passwordReset;

	@Column(name = "first_login", nullable = true)
	private Boolean firstLogin;

	@Column(name = "phone_model", nullable = true)
	private String phoneModel;

	@Column(name = "imei", nullable = true)
	private String imei;

	 @Column(name = "otp", nullable = false)
	 @ColumnDefault("true")
	 private Boolean otp;

}
