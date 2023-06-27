package com.spring.miniposbackend.repository.transaction;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.transaction.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	
	@Query(value = "select t from Transaction t where t.account.id=?1 and date_trunc('day',value_date) between ?2 and ?3")
	List<Transaction> findAllByAccountAndDate(Long accountId,@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate);
}