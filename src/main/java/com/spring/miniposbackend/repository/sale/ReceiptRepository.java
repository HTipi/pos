package com.spring.miniposbackend.repository.sale;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>{
	Optional<Receipt> findFirstByBranchIdOrderByIdDesc(Integer branchId);
}
