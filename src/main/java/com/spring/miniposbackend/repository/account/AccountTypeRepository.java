package com.spring.miniposbackend.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.account.AccountType;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer>{

	@Query(value = " select at from AccountType at where at.id = 2")
	AccountType findByPoint();
	
	@Query(value = " select at from AccountType at where at.id = 1")
	AccountType findByCredit();
}
