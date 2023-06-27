package com.spring.miniposbackend.repository.transaction;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.spring.miniposbackend.model.transaction.TransactionSale;


@Repository
public interface TransactionSaleRepository extends JpaRepository<TransactionSale, Long>{

	
	@Query("select case when count(s)> 0 then true else false end from TransactionSale s where s.qrNumber=?1")
	boolean existsByQr(UUID qr);
	
	@Query("select s from TransactionSale s where s.qrNumber=?1")
	Optional<TransactionSale> findByQr(UUID qr);
	
	@Query("select case when count(s)> 0 then true else false end from TransactionSale s where s.sale.id=?1")
	boolean existsBySaleId(long saleId);
}