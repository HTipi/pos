package com.spring.miniposbackend.modelview.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.account.Account;
import com.spring.miniposbackend.model.customer.Person;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter
public class AccountModel {

	@JsonIgnore
	private Account account;
	
	private byte[] logo;
	private byte[] profile;
	private AccountCreditPoint credit;
	private AccountCreditPoint point;
	private Person person;
	private List<Integer> branchAdvertiseId;

	public AccountModel(AccountCreditPoint credit,AccountCreditPoint point,Account account, byte[] logo,List<Integer> branchAdvertiseId) {
		this.credit = credit;
		this.point = point;
		this.account = account;
		this.logo=logo;
		this.branchAdvertiseId = branchAdvertiseId;
		}
	

	public AccountModel() {
		// TODO Auto-generated constructor stub
	}


	public AccountCreditPoint getCredit() {
		return credit;
	}
	public AccountCreditPoint getPoint() {
		return point;
	}
	
	public String getBranchName() {
		return account.getBranchName();
		}
	
	public String getCorporateName() {
		return account.getCorporateName();
	}
	
	public int getBranchId() {
		return account.getBranchId();
	}
	
	public byte[] getLogo() {
		return logo;
		
	}
	
	public Person getPerson() {
		return person;
		
	}
	
	public byte[] getProfile() {
		return profile;
		
	}
	public List<Integer> getBranchAdvertiseId(){
		return branchAdvertiseId;
	}

	
}
