package com.spring.miniposbackend.repository.sale;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.SaleTemporary;

@Repository
public interface SaleTemporaryRepository extends JpaRepository<SaleTemporary, Long>{
	
	List<SaleTemporary> findBySeatId(Long seatId);
	
	
//	Integer changeSeat(Long seatId, Long newSeatId);
//	List<SaleTemporary> findBySeatId(Long seatId, boolean isPrinted);
//	List<SaleTemporary> cancel(Long seatId);
	
}
