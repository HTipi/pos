package com.spring.miniposbackend.repository.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.miniposbackend.model.sale.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {


}
