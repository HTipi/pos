package com.spring.miniposbackend.repository.account;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.spring.miniposbackend.model.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	@Query(value="select acc from Account acc where acc.branch.id=?1 and acc.person.id=?2 and acc.accountType.id=1")
	Account findBybranchId(Integer branchId, Long person);

	@Query(value="select acc from Account acc where acc.person.id=?1")
	Optional<Account> findByPersonId(Long person);
	
	@Query(value="select acc from Account acc where acc.person.id=?1")
	List<Account> findByPersonAccount(Long person);
	
	@Query(value="select acc from Account acc where acc.accountType.id=1 and acc.branch.id=?1 and acc.person.id=?2")
	Optional<Account> findByCredit(int branchId,Long personId);
	
	@Query(value="select acc from Account acc where acc.accountType.id=2 and acc.branch.id=?1 and acc.person.id=?2")
	Optional<Account> findByPoint(int branchId,Long personId);
	
	@Query(value="select acc from Account acc where acc.branch.name=?1")
	Account findBySearch(String branchName);
	
	@Query(value = "select acc from Account acc where CONCAT(acc.branch.telephone,acc.branch.name,acc.corporate.name) like %:query%  and acc.person.id=:personId")
	List<Account> findByAccountQuery(@Param("query") String query,@Param("personId") Long personId);
	
	@Query(value="select acc from Account acc where acc.accountType.id=1 and acc.person.id=?1")
	Optional<Account> findByCreditAccount(long personId);
	
	@Query(value="select acc from Account acc where acc.accountType.id=2 and acc.person.id=?1")
	Optional<Account> findByPointAccount(long personId);
	
	@Query(value = "select bc from Account bc where bc.person.primaryPhone like %:query% and bc.branch.id=:branchId and bc.accountType.id=1")
	List<Account> findByAccountCreditQuery(@Param("query") String query,@Param("branchId") Integer branchId);

	@Query("select case when count(s)> 0 then true else false end from Account s where s.person.primaryPhone=?1 and s.branch.id=?2")
	boolean existsByPhone(String primaryphone,int branchId);

	@Query(value="select acc from Account acc where acc.person.id=?1 and acc.branch.id=?2")
	List<Account> findByPersonAccInBranch(Long person,int branchId);
	
	@Query(value="select acc from Account acc where acc.id=?1 and acc.accountType.id=2")
	Optional<Account> findPointById(Long accountId);
	
}


