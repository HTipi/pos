package com.spring.miniposbackend.repository.sale;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
	 List<Sale> findByBranchId(Integer branchId);
	 List<Sale> findByUserId(Integer userId);
	 
	 @Query(value = "select s from Sale s where s.user.id = ?1 and s.id = ?2")
	    List<Sale> findByIdWithUserId(Integer userId, Long saleId);
	 
	 @Query(value = "select s from Sale s where s.user.id = ?1 and  date_trunc('day',value_date)=?2")
	    List<Sale> findByIdWithValueDate(Integer userId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
	 
	 
	 @Query(value = "select s from Sale s where s.user.id = ?1 and  date_trunc('day',value_date)=?2 and s.paymentChannel.id=null")
	    List<Sale> findByIdWithValueDateAndPaymentNullId(Integer userId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
	 
	 @Query(value = "select s from Sale s where s.user.id = ?1 and  date_trunc('day',value_date)=?2 and s.paymentChannel.id=?3")
	    List<Sale> findByIdWithValueDateAndPaymentId(Integer userId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,Integer paymentId);
	 
	 
	 @Query(value = "select s from Sale s where s.branch.id = ?1 and  date_trunc('day',value_date)=?2 and s.paymentChannel.id=?3")
	    List<Sale> findByBranchIdWithValueDateAndPaymentId(Integer userId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,Integer paymentId);
	 
	 @Query(value = "select s from Sale s where s.branch.id = ?1 and  date_trunc('day',value_date)=?2 and s.paymentChannel.id=null")
	    List<Sale> findByBranchIdWithValueDateAndPaymentNullId(Integer userId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
	 
	 @Query(value = "select s from Sale s where s.branch.id = ?1 and  date_trunc('day',value_date)=?2")
	    List<Sale> findByBranchIdWithValueDate(Integer userId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
	 

}
