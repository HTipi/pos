package com.spring.miniposbackend.model;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customers")
@Setter @Getter
public class Customer extends AuditModel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false,length = 128)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 128)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telephone", nullable = false, length = 20)
    private String telephone;

    @Column(name = "is_login", nullable = false)
    @ColumnDefault("false")
    private boolean islogin;
    
    @Column(name = "login_date")
    private Date loginDate;
    
    @Column(name = "logout_date")
    private Date logoutDate;

    @Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;

}
