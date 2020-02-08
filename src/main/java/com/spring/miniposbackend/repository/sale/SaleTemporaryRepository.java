package com.spring.miniposbackend.repository.sale;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.SaleTemporary;

@Repository
public interface SaleTemporaryRepository extends JpaRepository<SaleTemporary, Long>{
	
	List<SaleTemporary> findBySeatId(Long seatId);
	@Query(value = "select s from SaleTemporary s where s.seat.id = ?1 and s.isPrinted = ?2")
	List<SaleTemporary> findBySeatId(Long seatId, boolean isPrinted);
	List<SaleTemporary> findByItemId(Long itemId);
	@Query(value = "select s from SaleTemporary s where s.item.id = ?1 and s.isPrinted = ?2")
	List<SaleTemporary> findByItemId(Long itemId, boolean isPrinted);
	
	
//	Integer changeSeat(Long seatId, Long newSeatId);
	
}
