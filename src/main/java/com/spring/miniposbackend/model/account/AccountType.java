package com.spring.miniposbackend.model.account;

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
@Table(name = "account_types")
@Setter
@Getter
public class AccountType extends AuditModel{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "name_kh", nullable = false)
	private String nameKh;

	
}
