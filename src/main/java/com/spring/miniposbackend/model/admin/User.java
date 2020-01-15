package com.spring.miniposbackend.model.admin;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Setter @Getter
public class User extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "name_kh", nullable = false)
    private String nameKh;

    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BranchUser> branchUsers = new ArrayList<>();

    @Column(name = "enable", nullable = false, columnDefinition = "boolean default true")
    private boolean enable;

}
