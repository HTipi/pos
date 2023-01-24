package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.PrinterItemType;

@Repository
public interface PrinterItemTypeRepository extends JpaRepository<PrinterItemType, Integer>{
	
	@Modifying
	@Query(value = "delete from printers_item_types where printer_id=?1", nativeQuery = true)
	void deleteByPrinterId(Integer printerId);
}
