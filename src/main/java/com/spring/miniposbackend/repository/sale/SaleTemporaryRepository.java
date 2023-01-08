package com.spring.miniposbackend.repository.sale;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.SaleTemporary;

@Repository
public interface SaleTemporaryRepository extends JpaRepository<SaleTemporary, Long> {

	@Query(value = "select s from SaleTemporary s where s.invoice.id=?1 and s.parentSaleTemporary is null")
	List<SaleTemporary> findByInvoiceId(Long invoiceId);
	
	@Query(value = "select s from SaleTemporary s where s.invoice.id = ?1 and s.isPrinted = ?2 and s.parentSaleTemporary is null")//update
	List<SaleTemporary> findByInvoiceIdWithisPrinted(Long invoiceId, boolean isPrinted); 
	
	@Query(value = "select s from SaleTemporary s where s.invoice.id = ?1 and s.isPrinted = ?2 and s.cancel = ?3 and s.parentSaleTemporary is null") //update
	List<SaleTemporary> findByInvoiceIdWithIsPrintedCancel(Long invoiceId, boolean isPrinted, boolean cancel);
	
	@Query(value = "select s from SaleTemporary s where s.seat.id=?1 and s.parentSaleTemporary is null and s.invoice is null") // update
	List<SaleTemporary> findBySeatId(Integer seatId);
	
	@Query(value = "select s from SaleTemporary s where s.seat.id=:seatId and s.parentSaleTemporary is null and s.invoice is null and s.id in (:items)") // update
	List<SaleTemporary> findSplitBySeatId(@Param("seatId") Integer seatId,@Param("items") List<Long> items);
	
	@Query(value = "select s from SaleTemporary s where s.seat.id=?1 and s.parentSaleTemporary is null") // update
	List<SaleTemporary> findBySeatForPrintId(Integer seatId);
	
	@Query(value = "select s from SaleTemporary s where s.parentSaleTemporary is null and s.invoice.id=?1") // update
	List<SaleTemporary> findBySeatForPrintIdWithInvoice(Long invoiceId);
	
	@Query(value = "select s from SaleTemporary s where s.seat.id = ?1 and s.isPrinted = ?2 and s.invoice is null and s.parentSaleTemporary is null")//update
	List<SaleTemporary> findBySeatIdWithisPrinted(Integer seatId, boolean isPrinted); 
	
	@Query(value = "select s from SaleTemporary s where s.seat.id = ?1 and s.isPrinted = ?2 and s.cancel = ?3 and s.invoice is null and s.parentSaleTemporary is null") //update
	List<SaleTemporary> findBySeatIdWithIsPrintedCancel(Integer seatId, boolean isPrinted, boolean cancel);
	
	@Query(value = "select s from SaleTemporary s where s.itemBranch.id = ?1")
	Optional<SaleTemporary> findByItemId(Long itemId);

	
	@Query("select case when count(s)> 0 then true else false end from SaleTemporary s where s.seat.id = ?1 and s.userEdit.id=?2")
	boolean existsBySeatIdWithUserEditId(Integer SeatId,Integer userEditId);
	
	@Query("select case when count(s)> 0 then true else false end from SaleTemporary s where s.itemBranch.id = ?1")
	boolean existsByItemId(Long itemId);
	
	@Query("select case when count(s)> 0 then true else false end from SaleTemporary s where s.itemBranch.item.itemType.id = ?1")
	boolean existsByItemTypeId(Integer itemTypeId);
	
	@Query("select case when count(s)> 0 then true else false end from SaleTemporary s where s.itemBranch.item.id = ?1")
	boolean existsByMainItemId(Long itemId);

	

	@Query(value = "select * from Sales_temp where parent_sale_id is null and  seat_id=(select seat_id from Sales_temp where user_id=?1 and is_printed=false and parent_sale_id is null and invoice_id is null order by value_date desc limit 1)", nativeQuery = true)
	List<SaleTemporary> findBySeatUserId(Integer userId);

	@Query(value = "select s from SaleTemporary s where s.user.id=?1 and s.parentSaleTemporary is null and s.invoice is null and s.seat.id is null") // already update
	List<SaleTemporary> findByUserId(Integer userId);
	
	@Query(value = "select s from SaleTemporary s where s.user.id=?1 and s.parentSaleTemporary is null and s.seat.id is null and s.invoice is null") // already update
	List<SaleTemporary> findByUserIdForPrint(Integer userId);
	@Query(value = "select s from SaleTemporary s where  s.parentSaleTemporary is null and s.seat.id is null and s.invoice.id=?1") // already update
	List<SaleTemporary> findByUserIdForPrintWithInvoice(Long invoiceId);
	
//	@Query(value = "select s from SaleTemporary s where s.user.id=?1 and s.seat.id=?2 and s.parentSaleTemporary is null")
//	List<SaleTemporary> findByUserId(Integer userId, Integer seatId);

	@Query(value = "select * from Sales_temp where parent_sale_id is null and seat_id=(select seat_id from Sales_temp where user_id=?1 and is_printed=?2 and cancel=?3 and parent_sale_id is null order by value_date desc limit 1)", nativeQuery = true)
	List<SaleTemporary> findByUserIdSeatWithIsPrintedCancel(Integer userId, boolean isPrinted, boolean cancel);

	@Query(value = "select s from SaleTemporary s where s.user.id = ?1 and s.isPrinted=?2 and s.cancel=?3 and s.parentSaleTemporary is null and s.seat.id is null")
	List<SaleTemporary> findByUserIdWithIsPrintedCancel(Integer userId, boolean isPrinted, boolean cancel);

	@Query(value = "select * from Sales_temp where seat_id=(select seat_id from Sales_temp where user_id=?1 and is_printed=?2 and parent_sale_id is null  order by value_date desc limit 1)", nativeQuery = true)
	List<SaleTemporary> findByUserIdSeatWithisPrinted(Integer userId, boolean isPrinted);

	@Query(value = "select s from SaleTemporary s where s.user.id = ?1 and s.isPrinted=?2 and s.parentSaleTemporary is null and s.seat.id is null")
	List<SaleTemporary> findByUserIdWithisPrinted(Integer userId, boolean isPrinted);

	@Modifying
	@Query(value = "delete from Sales_temp where invoice_id=?1", nativeQuery = true)
	void deleteByInvoiceId(Long invoiceId);
	@Modifying
	@Query(value = "delete from Sales_temp where seat_id=?1 and invoice_id is null", nativeQuery = true)
	void deleteBySeatId(Integer seatId);
	@Modifying
	@Query(value = "delete from Sales_temp where user_id=?1 and seat_id is null and invoice_id is null", nativeQuery = true)
	void deleteByUserId(Integer userId);
	@Modifying
	@Query(value = "delete from Sales_temp where id=?1", nativeQuery = true)
	void deleteBySaleTempId(Long saleTmpId);
	
	@Modifying
	@Query(value = "update Sales_temp set price=price-?2 where id=?1", nativeQuery = true)
	void deductPriceBySaleTempId(Long saleTmpId,BigDecimal price);
	
	@Modifying
	@Query(value = "update Sales_temp set useredit_id=?1 where seat_id=?2 and invoice_id is null", nativeQuery = true)
	void updateUserEditSeat(Integer userId, Integer seatId);
	
	@Modifying
	@Query(value = "update Sales_temp set useredit_id=?1 where invoice_id=?2", nativeQuery = true)
	void updateUserEditInvoice(Integer userId, Long invoiceId);
	
	@Modifying
	@Query(value = "update Sales_temp set invoice_id=?1 where seat_id=?2 and invoice_id is null", nativeQuery = true)
	void updateInvoiceBySeatId(Long invoiceId, Integer seatId);
	
	@Modifying
	@Query(value = "update Sales_temp set invoice_id=?1,customer_id=?3 where seat_id=?2 and invoice_id is null", nativeQuery = true)
	void updateInvoiceBySeatId(Long invoiceId, Integer seatId,Long customerId);
	
	@Modifying
	@Query(value = "update Sales_temp set invoice_id=?1 where user_id=?2 and invoice_id is null", nativeQuery = true)
	void updateInvoiceByUserId(Long invoiceId, Integer userId);
	
	@Modifying
	@Query(value = "update Sales_temp set invoice_id=?1,customer_id=?3 where user_id=?2 and invoice_id is null", nativeQuery = true)
	void updateInvoiceByUserId(Long invoiceId, Integer userId,Long customerId);

//	@Query(value = "select count(s) from SaleTemporary s where s.user.branch.id = ?1")
//	Optional<Integer> findByBranchId(Integer branchId);
	
	@Query(value = "select sum(s.quantity) from SaleTemporary s where s.user.id = ?1 and s.itemBranch.id=?2")
	Optional<Integer> findItemBalanceByUserId(Integer userId,Long itemBranchId);
	@Query(value = "select sum(s.quantity) from SaleTemporary s where s.seat.id = ?1 and s.itemBranch.id=?2")
	Optional<Integer> findItemBalanceBySeatId(Integer seatId,Long itemBranchId);
	
	@Query(value = "select s.seat.id from SaleTemporary s where s.user.branch.id = ?1 and s.invoice is null")
	List<Integer> findStatusSeatByBranchId(Integer branchId);
	
	
}
