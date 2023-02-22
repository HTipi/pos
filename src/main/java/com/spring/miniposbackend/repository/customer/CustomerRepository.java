package com.spring.miniposbackend.repository.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value = "select bc from Customer bc where (bc.primaryPhone like %:query%  or bc.name like %:query% or bc.nameKh like %:query%) and bc.branch.id=:branchId")
	List<Customer> findByCustomerQuery(@Param("query") String query,@Param("branchId") Integer branchId);

}
