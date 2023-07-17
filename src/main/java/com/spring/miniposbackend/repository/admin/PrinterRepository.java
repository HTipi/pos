package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.Printer;

@Repository
public interface PrinterRepository extends JpaRepository<Printer, Integer>{
	
	List<Printer> findByBranchId(Integer branchId);
	
	@Modifying
	@Query(value = "delete from printers where id=?1", nativeQuery = true)
	void deleteByPrinterId(Integer printerId);
}
