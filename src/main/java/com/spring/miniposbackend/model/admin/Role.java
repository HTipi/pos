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
@Table(name = "roles")
@Setter @Getter
public class Role extends AuditModel{

	private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name = "id", nullable = false)
	 private Integer id;
	 
	 @Column(name = "name", nullable = false, unique = true)
	 private String name;
	 
	 @Column(name = "name_kh", nullable = false, unique = true)
	 private String nameKh;
	
	 @Column(name = "enable", nullable = false, columnDefinition = "boolean default true")
	 private boolean enable;

}
