package com.spring.miniposbackend.model.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.model.admin.Currency;
import com.spring.miniposbackend.model.customer.Person;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Setter
@Getter
public class Account extends AuditModel {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false)
	@JsonIgnore
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accountType_id", nullable = true)
	@JsonIgnore
	private AccountType accountType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_Id", nullable = false)
	@JsonIgnore
	private Branch branch;

	@Column(name = "balance", nullable = true)
	private BigDecimal balance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "corporate_id", nullable = false)
	@JsonIgnore
	private Corporate corporate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "currency_id")
	@JsonIgnore
	private Currency currency;
	
	public void setBranch() {
	}

	public String getCorporateName() {
		return corporate.getName();
	}

	public String getBranchName() {
		return branch.getName();
	}

	public int getBranchId() {
		return branch.getId();
		 
	}
	
	public int getCorporateId() {
		return corporate.getId();
	}
	
	public String getBranchAdress() {
		return branch.getAddressDesc();
	}
	
	public String getBranchTelephone() {
		return branch.getTelephone();
	}
	public String getPhoneNumber() {
		return person.getPrimaryPhone();
	}
	public String getPersonName() {
		return person.getName();
	}
	public String getPersonNameKh() {
		return person.getNameKh();
	}
	public int getSexId()
	{
		return person.getSexId();
	}
	public Long getPersonId()
	{
		return person.getId();
	}

	
	
}
