package com.spring.miniposbackend.repository.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

}
