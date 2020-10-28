package com.spring.miniposbackend.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client_applications")
@Getter @Setter
public class ClientApplication {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "name", nullable = false, length = 128, unique = true)
    private String name;
    
    @Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private boolean enable;
}
