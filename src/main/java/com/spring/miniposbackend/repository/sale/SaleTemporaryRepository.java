package com.spring.miniposbackend.repository.sale;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.SaleTemporary;

@Repository
public interface SaleTemporaryRepository extends JpaRepository<SaleTemporary, Long>{
	
	List<SaleTemporary> findBySeatId(Integer seatId);
	@Query(value = "select s from SaleTemporary s where s.seat.id = ?1 and s.isPrinted = ?2")
	List<SaleTemporary> findBySeatIdWithisPrinted(Integer seatId, boolean isPrinted);
	@Query(value = "select s from SaleTemporary s where s.seat.id = ?1 and s.isPrinted = ?2 and s.cancel = ?3")
	List<SaleTemporary> findBySeatIdWithIsPrintedCancel(Integer seatId, boolean isPrinted, boolean cancel);
	List<SaleTemporary> findByItemId(Integer itemId);
	@Query(value = "select s from SaleTemporary s where s.item.id = ?1 and s.isPrinted = ?2")
	List<SaleTemporary> findByItemId(Integer itemId, boolean isPrinted);
	@Query(value = "select s from SaleTemporary s where s.item.id = ?1 and s.isPrinted = ?2 and s.cancel = ?3")
	List<SaleTemporary> findByItemId(Integer itemId, boolean isPrinted, boolean cancel);
	
	
//	@Query(value = "upate SaleTemporary s set s.isPrinted=true where s.seat.id= ?1 and s.isPrinted=false")
//	List<SaleTemporary> print(Integer seatId);
	
//	Integer changeSeat(Long seatId, Long newSeatId);
	
}
